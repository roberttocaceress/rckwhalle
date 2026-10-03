# RCK Sistemas - Flutter Login MVC

Implementação moderna do módulo de login em Flutter seguindo o padrão **MVC (Model-View-Controller)**, baseada na lógica de autenticação da classe `MainActivity.java` do projeto Android.

## 📋 Features

✅ **Autenticação Dupla**
- Login com usuário/email + senha
- Login com biometria (fingerprint/face)

✅ **Validações**
- Login e senha: mínimo 2 caracteres
- Detecção automática email vs usuário
- Validação de formato de email

✅ **UI Moderna**
- Design responsivo com gradiente
- Animações suaves (zoom in/out)
- Campos com validação em tempo real
- Estados de loading e erros

✅ **Offline-First**
- Salva credenciais localmente
- Cache de usuário logado
- Suporta logout e troca de usuário

✅ **Dual API**
- Fallback automático para servidor 2
- Timeouts configuráveis (15s)
- Tratamento de erros por código HTTP

✅ **Segurança**
- Biometria local (Android 6+)
- Senha criptografada no local_auth
- Permissões gerenciadas

## 📁 Estrutura do Projeto

```
flutter_login_mvc/
├── lib/
│   ├── main.dart                    # Setup GetX e entry point
│   ├── models/
│   │   ├── usuario_model.dart       # Model serializable
│   │   └── login_response.dart      # DTO de resposta
│   ├── services/
│   │   └── api_service.dart         # Cliente Retrofit
│   ├── repositories/
│   │   └── login_repository.dart    # Camada de negócio
│   ├── controllers/
│   │   └── login_controller.dart    # Lógica & Estado (GetX)
│   └── views/
│       └── login_view.dart          # Interface UI
├── pubspec.yaml                     # Dependências
└── README.md                        # Este arquivo
```

## 🚀 Como Usar

### 1. Instalação Básica

```bash
# Clone ou crie novo projeto
flutter create flutter_login_mvc

# Instale dependências
flutter pub get

# Gere código Retrofit & Serialização
flutter pub run build_runner build --delete-conflicting-outputs
```

### 2. Configuração da API

Edite `lib/main.dart` e configure a URL do servidor:

```dart
Get.put<ApiService>(
  ApiService(dio, baseUrl: 'http://seu_servidor:porta/rckwhalleAPI2.7.3/ws/')
);
```

### 3. Implementação do MVC

#### **VIEW** (login_view.dart)
- Exibe interface de login
- Observa mudanças do controller via `Obx()`
- Não contém lógica de negócio

```dart
class LoginView extends GetView<LoginController> {
  @override
  Widget build(BuildContext context) {
    return Obx(() => TextField(
      controller: controller.loginController,
      enabled: !controller.isLoading.value,
      // ...
    ));
  }
}
```

#### **CONTROLLER** (login_controller.dart)
- Gerencia estado reativo (`isLoading.obs`, `showPassword.obs`)
- Orquestra fluxo de login
- Interage com repositório

```dart
class LoginController extends GetxController {
  Future<void> login() async {
    isLoading.value = true;
    final response = await repository.loginWithCredentials(...);
    if (response.isSuccess) {
      Get.offNamed('/principal');
    }
    isLoading.value = false;
  }
}
```

#### **REPOSITORY** (login_repository.dart)
- Valida entradas
- Chama API e trata erros
- Persiste dados localmente

```dart
class LoginRepository {
  Future<LoginResponse> loginWithCredentials({
    required String login,
    required String senha,
    required String deviceId,
  }) async {
    // Valida entrada
    if(!_isValidInput(login) || !_isValidInput(senha)) {
      return LoginResponse(
        statusCode: 400,
        message: 'Campos inválidos',
      );
    }
    // Detecta email vs usuário
    final isEmail = _isValidEmail(login);
    // Chama API
    final usuario = await apiService.logarAPI(...);
    // Salva localStorage
    await _saveLoginCredentials(...);
  }
}
```

#### **MODEL** (usuario_model.dart)
- DTO serializável (JSON ↔ Dart)
- Mapeia campos da API

```dart
@JsonSerializable()
class Usuario {
  @JsonKey(name: 'ID_USUARIO')
  String idUsuario;
  // ...
}
```

## 🔐 Fluxo de Autenticação

### Login com Credenciais

1. **Usuário digita** → TextWatcher valida min 2 caracteres
2. **Botão habilitado** → Animação zoom in
3. **Click "Entrar"** → Validação de campos
4. **Chamada API** →
   - Detecção: email vs usuário
   - Request: POST `/usuario/login/{dados}`
5. **Resposta 200** → Sucesso, navega
6. **Resposta 500** → Senha incorreta, limpa campo
7. **Erro rede** → Tenta servidor 2
8. **Erro 404** → Usuário não encontrado

### Login Biométrico

1. **Click fingerprint** → Chama `LocalAuthentication`
2. **Autenticação sucesso** → Envia `-` para todos campos
3. **API reconhece padrão** → Login sem credenciais
4. **Sucesso** → Navega para principal
5. **Falha biometria** → Oferece calibrar novo dedo

## 📊 Mapeamento Android → Flutter

| Android (Java) | Flutter (Dart) | Descrição |
|---|---|---|
| `MainActivity` | `LoginView` | Interface principal |
| `UsuarioBO` | `LoginController` | Orquestração de negócio |
| `LoginRepository` (novo) | `LoginRepository` | Camada de persistência |
| `Usuario` model | `Usuario` (Serializable) | Modelo de dados |
| `EditText` | `TextField` | Campos de entrada |
| `BiometricPrompt` | `LocalAuthentication` | Autenticação biométrica |
| `SharedPreferences` | `SharedPreferences` | Cache local |
| `Retrofit` | `Retrofit` (retrofit2 Dart) | Cliente API |
| `DBHelper` | `SharedPreferences` | Preferências locais |

## 🎨 Customização

### Cores

Edite `login_view.dart`:

```dart
Color(0xFF1E3C72) // Azul primário
Color(0xFF2A5298) // Azul secundário
```

### Timeouts

Edite `main.dart`:

```dart
BaseOptions(
  connectTimeout: Duration(seconds: 15),
  receiveTimeout: Duration(seconds: 15),
)
```

### Validações

Edite `login_repository.dart`:

```dart
bool _isValidInput(String input) {
  return input.trim().length >= 2; // Altere limite
}
```

## 🔧 Dependências

| Pacote | Versão | Uso |
|---|---|---|
| `get` | ^4.6.5 | State management & routing |
| `retrofit` | ^4.2.0 | Cliente HTTP com anotações |
| `dio` | ^5.0.0 | HTTP client base |
| `shared_preferences` | ^2.1.0 | Cache local SQLite |
| `local_auth` | ^2.1.1 | Biometria nativa |
| `connectivity_plus` | ^3.0.0 | Verificar internet |
| `device_info_plus` | ^3.0.0 | ID do dispositivo |

## 🧪 Testes

### Teste de Login

```dart
testWidgets('Login com credenciais válidas', (WidgetTester tester) async {
  await tester.pumpWidget(const MyApp());
  
  // Digita login
  await tester.enterText(find.byType(TextField).first, 'vendedor');
  
  // Digita senha
  await tester.enterText(find.byType(TextField).at(1), 'senha123');
  
  // Verifica se botão apareceu
  expect(find.text('ENTRAR'), findsOneWidget);
  
  // Clica
  await tester.tap(find.text('ENTRAR'));
  await tester.pumpAndSettle();
  
  // Verifica navegação
  expect(find.text('Bem-vindo'), findsNothing);
});
```

## 🛡️ Segurança

- ✅ Validação no cliente E servidor
- ✅ Senha nunca salva em texto plano
- ✅ Biometria usa sistema operacional
- ✅ HTTPS recomendado em produção
- ✅ Tokens armazenados com segurança

## 🐛 Troubleshooting

### "ApiService não registrado no GetX"
```
Solução: Certifique que _setupDependencies() é chamado em main()
```

### "Erro de build_runner"
```bash
# Limpe build anterior
flutter clean

# Regenere
flutter pub run build_runner build --delete-conflicting-outputs
```

### "Biometria não funciona no emulador"
```
Solução: Use device físico ou configure emulador com biometria
```

## 📝 Próximos Passos

1. **Sincronização** → Implementar SincroniaBO em Flutter
2. **Dashboard** → Criar visão principal (ActivityPrincipal)
3. **Clientes** → Tela de listagem de clientes
4. **Pedidos** → Gestão de pedidos
5. **Financeiro** → Relatórios e gráficos

## 📞 Suporte

Para dúvidas sobre a implementação, veja:
- `/AGENTS.md` - Arquitetura geral do projeto
- `BUILD_GUIDE.dart` - Guia de build & estrutura

## 📄 Licença

Desenvolvido por RCK Sistemas

