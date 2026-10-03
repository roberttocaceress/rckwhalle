/**

╔════════════════════════════════════════════════════════════════════════════════╗
║                   ESTRUTURA COMPLETA DO PROJETO FLUTTER MVC                   ║
║                         RCK Sistemas - Login Module                           ║
╚════════════════════════════════════════════════════════════════════════════════╝

flutter_login_mvc/                                         # Raiz do projeto
│
├── 📋 IMPLEMENTAÇÃO SUMMARY.md                            # Este arquivo - Índice
├── 📖 README.md                                           # Documentação completa
├── 📄 pubspec.yaml                                        # Dependências
│
├── 📂 lib/
│   ├── ⭐ main.dart                                       # Entry point + Setup GetX
│   ├── 📄 BUILD_GUIDE.dart                               # Guia técnico
│   ├── 📄 ARCHITECTURE_COMPARISON.dart                   # Android vs Flutter
│   │
│   ├── 📂 models/                        [CAMADA: MODEL]
│   │   ├── usuario_model.dart            ← Serializable (JSON)
│   │   └── login_response.dart           ← DTO Response
│   │
│   ├── 📂 services/                      [CAMADA: API CLIENT]
│   │   └── api_service.dart              ← @RestApi (Retrofit)
│   │
│   ├── 📂 repositories/                  [CAMADA: NEGÓCIO]
│   │   └── login_repository.dart         ← Validação + Persistência
│   │
│   ├── 📂 controllers/                   [CAMADA: ORQUESTRAÇÃO]
│   │   └── login_controller.dart         ← GetxController (Estado)
│   │
│   ├── 📂 views/                         [CAMADA: INTERFACE]
│   │   └── login_view.dart               ← GetView (UI moderna)
│   │
│   ├── 📂 config/
│   │   └── app_config.dart               ← Constantes globais
│   │
│   └── 📂 utils/
│       └── extensions.dart               ← Extensões de utilidade
│
├── 📂 test/
│   └── login_repository_test.dart        ← Testes unitários
│
├── 📂 assets/
│   ├── images/                           ← Logos, imagens, backgrounds
│   └── icons/                            ← Ícones customizados
│
└── 📂 android/                           ← Build Android
    ├── app/build.gradle                  ← Config Android
    └── ...


═══════════════════════════════════════════════════════════════════════════════

                         FLUXO DE ARQUIVOS - BY USE CASE

═══════════════════════════════════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────────────────────┐
│ USE CASE: LOGIN COM CREDENCIAIS                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  1. USER TYPE "vendedor" IN TEXTFIELD                                      │
│     ↓                                                                       │
│     login_view.dart                                                         │
│     └─→ TextField(onChanged: controller.update())                          │
│                                                                             │
│  2. textChanged() WATCHED BY TEXTWATCHER                                   │
│     ↓                                                                       │
│     login_controller.dart                                                  │
│     └─→ _validateInputs()                                                  │
│         └─→ showLoginButton.value = true (obs)                            │
│                                                                             │
│  3. UI REACTS TO STATE CHANGE                                              │
│     ↓                                                                       │
│     login_view.dart                                                         │
│     └─→ Obx(() => AnimatedOpacity(opacity: ctrl.showLoginButton))         │
│         └─→ Button animates zoom in                                        │
│                                                                             │
│  4. USER CLICKS "ENTRAR" BUTTON                                            │
│     ↓                                                                       │
│     login_view.dart                                                         │
│     └─→ onTap: controller.login()                                          │
│                                                                             │
│  5. CONTROLLER ORCHESTRATES LOGIN FLOW                                     │
│     ↓                                                                       │
│     login_controller.dart                                                  │
│     ├─→ isLoading.value = true                                            │
│     ├─→ repository.loginWithCredentials(...)                              │
│     └─→ isLoading.value = false                                           │
│                                                                             │
│  6. REPOSITORY VALIDATES & CALLS API                                       │
│     ↓                                                                       │
│     login_repository.dart                                                  │
│     ├─→ _isValidInput(login)     ✓ "vendedor".length >= 2                │
│     ├─→ _isValidEmail(login)     ✗ Not an email                          │
│     ├─→ apiService.logarAPI(     ✓ Call with username                    │
│     │   idandroid=...,                                                     │
│     │   usuarioLogin="VENDEDOR",                                           │
│     │   senha="..."                                                        │
│     │ )                                                                    │
│     └─→ _saveLoginCredentials()  ✓ Save locally                           │
│                                                                             │
│  7. API SERVICE MAKES HTTP REQUEST                                         │
│     ↓                                                                       │
│     api_service.dart                                                       │
│     └─→ POST /usuario/login/{params} → 200 OK                            │
│         Response: { Usuario object with data }                            │
│                                                                             │
│  8. REPOSITORY RETURNS RESPONSE                                            │
│     ↓                                                                       │
│     login_response.dart                                                    │
│     └─→ LoginResponse(                                                     │
│           statusCode: 200,                                                 │
│           message: "Login realizado",                                      │
│           usuario: usuarioObject,                                          │
│         )                                                                  │
│                                                                             │
│  9. CONTROLLER HANDLES SUCCESS                                             │
│     ↓                                                                       │
│     login_controller.dart                                                  │
│     ├─→ if (response.isSuccess)                                           │
│     ├─→ currentUser.value = response.usuario                              │
│     └─→ Get.offNamed('/principal')                                        │
│                                                                             │
│  10. NAVIGATE TO HOME SCREEN                                              │
│      ↓                                                                      │
│      [UserData persisted in SharedPreferences]                            │
│      [UsuarioHelper has current user]                                     │
│      [Next screen can access user data via GetX]                          │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ USE CASE: Login com Erro - Senha Incorreta (HTTP 500)                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  API RETURNS: 500 (Incorrect Password)                                    │
│     ↓                                                                       │
│  repository._handleDioError(e)                                             │
│     ├─→ Detecta statusCode 500                                             │
│     └─→ return LoginResponse(statusCode: 500, message: "Senha incorreta")│
│        ↓                                                                    │
│  controller.login()                                                         │
│     ├─→ if (response.isInvalidPassword)                                   │
│     ├─→ senhaError.value = "Senha incorreta"                              │
│     └─→ senhaController.clear()                                            │
│        ↓                                                                    │
│  login_view.dart                                                            │
│     └─→ Obx(() => TextField(                                              │
│         errorText: controller.senhaError.isEmpty                          │
│           ? null : controller.senhaError.value,                           │
│       ))                                                                    │
│        ↓                                                                    │
│  ERROR MESSAGE DISPLAYED IN CAMPO SENHA                                   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ USE CASE: Biometric Login                                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  USER CLICKS FINGERPRINT BUTTON                                            │
│     ↓                                                                       │
│  login_view.dart                                                            │
│     └─→ Obx(() => isBiometricsAvailable.value && isBiometricsEnabled      │
│         ? ElevatedButton(onTap: controller.loginWithBiometrics())         │
│                                                                             │
│  CONTROLLER INITIATES BIOMETRIC AUTH                                       │
│     ↓                                                                       │
│  login_controller.dart                                                     │
│     ├─→ _localAuth.authenticate()                                         │
│     │   (Opens system biometric dialog)                                    │
│     ├─→ User scans fingerprint                                            │
│     ├─→ If success:                                                        │
│     │   repository.loginWithBiometrics(deviceId)                         │
│     └─→ If fail:                                                          │
│         showSnackbar("Falha na autenticação biométrica")                 │
│                                                                             │
│  REPOSITORY CALLS API WITH MINIMAL DATA                                    │
│     ↓                                                                       │
│  login_repository.dart                                                     │
│     ├─→ apiService.logarAPI(                                              │
│     │   idandroid=...,                                                    │
│     │   usuarioLogin="-",   ← Signals biometric auth                    │
│     │   senha="-",                                                        │
│     │   emailVendedor="-"                                                │
│     │ )                                                                  │
│     └─→ API recognizes pattern → logs in user                            │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘


═══════════════════════════════════════════════════════════════════════════════

                         ARQUIVO-CHAVE POR FEATURE

═══════════════════════════════════════════════════════════════════════════════

┌─────────────────────┬──────────────────────────────────────────────────────┐
│ FEATURE             │ ARQUIVOS PRINCIPAIS                                 │
├─────────────────────┼──────────────────────────────────────────────────────┤
│ Validação campos    │ login_repository.dart (_isValidInput)               │
│                     │ login_controller.dart (_validateFields)             │
│                     │                                                     │
│ Animação botão      │ login_view.dart (AnimatedOpacity)                 │
│                     │ login_controller.dart (showLoginButton.obs)        │
│                     │                                                     │
│ API call           │ api_service.dart (logarAPI)                         │
│                     │ login_repository.dart (loginWithCredentials)        │
│                     │                                                     │
│ Email vs Usuário   │ login_repository.dart (_isValidEmail)              │
│                     │ utils/extensions.dart (isValidEmail extension)     │
│                     │                                                     │
│ Biometria          │ login_controller.dart (loginWithBiometrics)        │
│                     │ login_view.dart (_buildBiometricButton)           │
│                     │                                                     │
│ Persistência       │ login_repository.dart (_saveLoginCredentials)      │
│                     │ config/app_config.dart (Keys storage)             │
│                     │                                                     │
│ Tratamento erros   │ login_repository.dart (_handleDioError)            │
│                     │ models/login_response.dart                         │
│                     │                                                     │
│ UI/UX              │ login_view.dart (build methods)                    │
│                     │ config/app_config.dart (colors, timeouts)         │
│                     │                                                     │
│ Estado             │ login_controller.dart (obs fields)                 │
│                     │ models/usuario_model.dart                          │
│                                                                            │
└─────────────────────┴──────────────────────────────────────────────────────┘


═══════════════════════════════════════════════════════════════════════════════

                         QUICK REFERENCE - IMPORTS

═══════════════════════════════════════════════════════════════════════════════

Usar LoginController em qualquer lugar:
    import 'controllers/login_controller.dart';
    final controller = Get.find<LoginController>();

Usar LoginRepository:
    import 'repositories/login_repository.dart';
    final repo = Get.find<LoginRepository>();

Usar extensões:
    import 'utils/extensions.dart';
    'teste'.isValidEmail;  // true/false

Usar configurações:
    import 'config/app_config.dart';
    AppConfig.primaryColor;
    ErrorMessages.invalidLogin;

═══════════════════════════════════════════════════════════════════════════════

*/

