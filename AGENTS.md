# RCKSISTEMAS-APP: Codebase Guide for AI Agents

## Architecture Overview

This is a **multi-module Android sales management application** (`com.example.rcksuporte05.rcksistemas`) with a layered architecture optimized for field sales operations. The app enables order management, client relationship tracking, and financial monitoring.

### Module Structure

- **`:app`**: Main application module (compileSdk 36, targetSdk 33, minSdk 21)
- **`:youtube`**: YouTube player library wrapper (AndroidX)
- **`:appmodel`**: Legacy Android Support library module (compileSdk 28, not modernized)
- **`:YouTubeAndroidPlayerApi`**: Custom YouTube API wrapper

> **Note**: Version mismatch exists—`appmodel` uses old Android Support libraries while `app` uses AndroidX. Exercise caution when modifying `appmodel`.

---

## Layered Architecture

The codebase follows strict horizontal layering by package:

```
com.example.rcksuporte05.rcksistemas/
├── activity/          # Android UI Activities (~40 activities)
├── fragment/          # Android Fragments (paged tabs via adapters)
├── BO/                # Business Objects (5 files: SincroniaBO, PedidoBO, UsuarioBO, etc.)
├── DAO/               # Data Access Objects (DBHelper manages SQLite via DAO pattern)
├── model/             # POJO data classes (~50+ model classes)
├── Helper/            # Static singleton helpers for inter-component state
├── adapters/          # RecyclerView & ViewPager2 adapters
├── api/               # Retrofit API clients (Google Maps, custom backend)
├── ui/                # Custom UI components
└── util/              # Firebase messaging, utilities
```

### Key Architectural Pattern: Helper Class Singletons

**This is NOT standard Android architecture—it's project-specific.**

```java
// Example: ClienteHelper.java
public class ClienteHelper {
    private static Cliente cliente;  // Global state
    
    public static Cliente getCliente() { return cliente; }
    public static void setCliente(Cliente cliente) { ClienteHelper.cliente = cliente; }
}
```

**Why this exists**: Replaces dependency injection/ViewModel pattern. Helpers hold transient UI state across screen navigation.

**Common helpers**:
- `ClienteHelper` — current client being edited
- `ProspectHelper` — current prospect for multi-fragment wizard
- `PedidoHelper` — order data across fragments
- `UsuarioHelper` — logged-in user info

**When modifying**: Changes to Helper state propagate across all fragments—test navigation flows.

---

## Data Flow Architecture

### 1. **Database Layer (SQLite)**

**Primary database**: `RCKWhalle.db` (version 27)

Located in `DBHelper.java` (2,660 lines):
- **40+ tables** via `onCreate()` SQL creation
- User tables (TBL_WEB_USUARIO)
- Client tables (TBL_WEB_CLIENTE, related addresses/contacts)
- Order tables (pedido-related)
- Product catalog (TBL_WEB_PRODUTO with photos, pricing)
- Financial tables (contas a receber, financial status)

**Key pattern**: DAO files wrap SQLite queries (e.g., `ClienteDAO.java` for client CRUD).

### 2. **Business Logic Layer (BO)**

**SincroniaBO** (1,568 lines) — **Most complex class, synchronizes 50+ data types**:
```
Backend API → Retrofit → SincroniaBO.sincronizar() 
  → Updates 50+ DAO operations 
  → Populates SQLite 
  → Updates UI via Helpers/Activities
```

Syncs: clients, products, orders, financial data, campaigns, users, price tables, etc.

**Other BO classes**: Process-specific logic (orders, user auth, file attachments).

### 3. **API Integration**

**Retrofit-based clients**:
- `ApiGeocoder` — Google Maps geocoding (15s timeout)
- Custom backend API (application ID: `com.example.rcksuporte05.rcksistemas`)

**Firebase integration**:
- `google-services.json` configured for project `rckwhalle`
- Firebase Firestore, Messaging, Analytics, Database (v22.1.1, v20.0.2)
- Push notifications via `MyFirebaseInstanceIdService`

### 4. **UI Layer**

**Activity-centric navigation** (~40 activities):
- `MainActivity` — new modern entry point
- `ActivityPrincipal` — main menu hub
- Multi-tab activities using `FragmentPagerAdapter` + tab adapters (e.g., `TabsAdapterCliente` for client 5-tab wizard)
- Dialog activities using `Theme.CriarDialog` for standalone windows

**Fragment patterns**:
- Tab-based forms split across fragments (e.g., client registration spans `CadastroCliente1–9`)
- Data shared via Helper singletons (fixes state across fragment swaps)

---

## Developer Workflows

### Building & Testing

```bash
# Clean build command (Windows)
.\gradlew.bat clean build

# Build debug APK
.\gradlew.bat assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk

# Run on emulator
.\gradlew.bat installDebug

# Run tests (minimal test coverage in codebase)
.\gradlew.bat test
```

**Version configuration** (`gradle.properties`):
- JVM heap: `-Xmx1536m` (moderate complexity)
- AndroidX enabled: `android.useAndroidX=true`
- Jetifier enabled: `android.enableJetifier=true`

**ProGuard enabled** in release builds (includes Google Play Services rules).

### App Versioning

In `app/build.gradle`:
```groovy
versionCode 2
versionName '2.7.3-C DB V27 27/04/2026 AM DESENVOLVIDO POR RCK SISTEMAS ESPECIFICOS'
```

Versioning embeds build date and database schema version (DB V27).

---

## Critical Integration Points

### 1. **Multi-Device Biometric Authentication**
- Uses `androidx.biometric:biometric:1.0.1`
- User table column: `USUARIO_PERMITE_BIOMETRIA` (VARCHAR boolean)
- Location: `ActivityLogin` (implied), biometric check in activities

### 2. **Location Services**
- `LocationActivity.java` & `ActivityVisita.java` use Google Play Services location APIs
- Pattern: Acquire `FusedLocationProviderClient`, request high-accuracy location (30s interval)
- Prospect records capture latitude/longitude

### 3. **Rich Media Handling**
- Images: Picasso (2.5.2) + PhotoView (2.1.3) for zoom
- PDFs: iTextPDF (5.5.13) for document generation
- Charts: MPAndroidChart (3.1.0) for financial/sales graphs (in `GraficoFaturamento*` fragments)

### 4. **Offline-First Design**
- All data syncs to SQLite before rendering
- `ActivitySincronia` orchestrates full sync (likely on app startup)
- UI displays cached data even when offline

---

## Project Conventions & Gotchas

### Naming Conventions

- **Activities**: `Activity` prefix (not `Act`), camelCase (e.g., `ActivityCliente`)
- **Fragments**: Class name only (e.g., `CadastroClienteFoto`)
- **Adapters**: Descriptive type suffix (e.g., `TabsAdapterCliente`, `MainAdapter`)
- **Helpers**: `*Helper` suffix with static methods (e.g., `GraficoFaturamentoHelper`)
- **DAO**: Specific model prefix (e.g., `ClienteDAO`, not `DataAccessCliente`)

### Package Coupling

- **Helpers are god-objects**: `ClienteHelper` is accessed by ~20 activities; changes ripple widely
- **DAO interdependencies**: `SincroniaBO` creates DAOs inline (not injected), making refactoring brittle
- **No dependency injection**: Manual object creation throughout; hard to mock in tests

### Missing/Legacy Code Patterns

- **`activity_/` and `adapters_/` folders**: Old implementations (likely pre-refactor backups). Ignore unless debugging historical issues.
- **Commented Kotlin plugin**: Project was Java-only historically; Kotlin never adopted
- **YouTube library duplication**: Both `lib/YouTubeAndroidPlayerApi-1.2.2/` and `:YouTubeAndroidPlayerApi` module exist—use the module

---

## Common Tasks & Patterns

### Task: Add a new Client field to sync

1. Add column to `DBHelper.onCreate()` (TBL_WEB_CLIENTE table)
2. Add field to `Cliente.java` model class
3. Update `ClienteDAO` insert/update/query methods
4. Add field mapping in `SincroniaBO.sincronizar()` (parse JSON → DAO)
5. Update UI in `CadastroCliente1–9` fragments
6. Increment `DBHelper` version (currently 27) → onCreate won't re-run; use onUpgrade for schema migrations

### Task: Add a Retrofit API call

1. Create interface in `api/` package with `@GET/@POST` methods
2. Use `ApiGeocoder` pattern: lazy-init `Retrofit.Builder()` with `GsonConverterFactory`
3. Call in `SincroniaBO` or specific BO class, parse response, update DAO
4. Handle socket timeouts (default 15s) and retry logic

### Task: Modify multi-tab Fragment dialog

1. Find adapter (e.g., `TabsAdapterCliente`)
2. Modify `getItem(int position)` to return correct fragment
3. Update fragment class (e.g., `CadastroCliente2`) with UI changes
4. Access shared state via `ClienteHelper.getCliente()` (not constructor args)

---

## Important Files to Know

| File | Purpose |
|------|---------|
| `DBHelper.java` | SQLite schema & low-level DB ops (2,660 lines) |
| `SincroniaBO.java` | Backend sync orchestration (1,568 lines) |
| `ActivityPrincipal` | Main menu navigation hub |
| `UsuarioHelper.java` | Current logged-in user state |
| `AndroidManifest.xml` | 458 lines; declares 40+ activities, Firebase service, permissions |
| `app/build.gradle` | 134 lines; Retrofit, Firebase, Glide, Jasypt crypto deps |

---

## Performance & Stability Considerations

1. **Large heap required**: `android.largeHeap="true"` in manifest (SQLite + image processing)
2. **Serialization**: Extensive use of Gson for API JSON → model conversion
3. **Biometric**: Uses deprecated Google Play Services APIs; target SDK 33 enforces new patterns
4. **Multi-module version drift**: `appmodel` on SDK 28 may cause Gradle conflicts; keep separate
5. **Database migrations**: Only `onCreate` defined; schema evolution handled implicitly (risks data loss on version bumps)

---

## Before Modifying Large Components

**Ask yourself**:
- Does this change affect Helper singleton state? → Test all Activities using it
- Is this in `SincroniaBO`? → E2E test full sync workflow
- Adding to `DBHelper` schema? → Increment version & plan migration
- Changing public API method? → Grep for all callers (no IDE refactoring tools in legacy code)
- Using Retrofit? → Test with actual backend; mock responses first

---

## Quick Reference: Module Dependencies

```
app (main)
├── :YouTubeAndroidPlayerApi (custom library)
├── :youtube (YouTube wrapper module)
│   └── androidx libraries
└── External: Firebase, Retrofit, Glide, iTextPDF, Jasypt

appmodel (legacy, unused by app)
└── Old Android Support libs
```

**Do NOT import `appmodel` into `app`** without understanding API version conflicts.

