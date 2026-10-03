// Comparação estrutural: Android (Java) → Flutter (Dart)

/*

╔══════════════════════════════════════════════════════════════════════════════╗
║                    ARQUITETURA MVC - ANDROID vs FLUTTER                     ║
╚══════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────┐
│ 1. LAYER: MODEL                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  Android (Java):                    Flutter (Dart):                        │
│  ────────────────                   ────────────────                       │
│  class Usuario {                    @JsonSerializable()                    │
│    String id_usuario;               class Usuario {                       │
│    String login;                      @JsonKey(name: 'ID_USUARIO')        │
│    String senha;    (não salva)        String idUsuario;                  │
│    String nome_usuario;               String login;                       │
│    // 40+ campos                       String nomeUsuario;                │
│    getLogin() {}                       // Same fields...                  │
│    setLogin() {}                       factory fromJson() {}              │
│  }                                     Map toJson() {}                    │
│                                      }                                     │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ 2. LAYER: API CLIENT (Service)                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  Android (Java):                    Flutter (Dart):                        │
│  ────────────────                   ────────────────                       │
│  Retrofit retrofit = new              @RestApi()                          │
│    Retrofit.Builder()                abstract class ApiService {          │
│    .baseUrl(url)                       factory ApiService(Dio dio)        │
│    .addConverterFactory(                                                   │
│      GsonConverterFactory.create())   @GET('/usuario/login/{...}')        │
│    .build();                          Future<Usuario> logarAPI(          │
│                                        @Path('idandroid') String...       │
│  Call<Usuario> logarAPI(...)          @Path('idusuario') String...      │
│    -> Callback com onResponse()        );                                 │
│    -> Callback com onFailure()       }                                    │
│                                                                             │
│  • Usa CALLBACKS                      • Usa FUTURES (async/await)         │
│  • Tipagem explícita com GsonConverter • Tipagem automática com           │
│                                         json_serializable                 │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ 3. LAYER: REPOSITORY (Camada de Negócio)                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  Android (Java) - IMPLÍCITO em:      Flutter (Dart) - EXPLÍCITO:          │
│  ────────────────────────────────    ──────────────────────────────       │
│  class MainActivity {                class LoginRepository {             │
│    void logarNoApp() {                Future<LoginResponse>              │
│      1. getListaUsuarios()             loginWithCredentials() {          │
│      2. valida campos                 1. _isValidInput()               │
│      3. busca no SQLite            2. _isValidEmail()               │
│      4. chama API                  3. apiService.logarAPI()        │
│      5. trata resposta 200, 500    4. _saveLoginCredentials()     │
│      6. persiste em BD             5. return LoginResponse         │
│      7. seta UsuarioHelper         }                               │
│      8. navega para principal                                       │
│    }                               Future<void> enableBiometrics() {}│
│  }                                 Future<Map> getSavedCredentials(){}
│                                  }                                      │
│                                                                         │
│  • Lógica espalhada na Activity    • Lógica centralizad no Repository │
│  • Validação + API + Persistência  • Separação clara de responsabilidades
│  • Intensa manipulação de UI      • Retorna objetos de resposta       │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ 4. LAYER: CONTROLLER (Orquestração de Estado)                               │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  Android (Java) - IMPLÍCITO:         Flutter (Dart) - EXPLÍCITO:          │
│  ───────────────────────────         ──────────────────────────────       │
│  class MainActivity               class LoginController               │
│    extends AppCompatActivity {     extends GetxController {          │
│      // Variáveis membro:           // Estados reativos:             │
│      EditText edtLogin;             final isLoading = false.obs;    │
│      EditText edtSenha;             final showPassword = false.obs; │
│      Button btnEntrar;              final loginError = ''.obs;      │
│      ProgressDialog progressBar;    final showLoginButton           │
│                                       = false.obs;                  │
│      // Métodos:                     final currentUser = Rx<...>(); │
│      void onClick() {}                                               │
│      void logarNoApp() {}            // Controllers:                 │
│      void isCamposValidos() {}       late TextEditingController     │
│      void showHideProgressBar() {}   loginController;               │
│                                       late TextEditingController     │
│      // Listeners:                   senhaController;               │
│      edtLogin.addTextWatcher({       final LocalAuthentication      │
│        onTextChanged() {             _localAuth = ...;              │
│          if (length >= 2) {                                         │
│            btnEntrar.visibility       // Métodos:                   │
│          }                            Future<void> login() async {}  │
│        }                              Future<void>                   │
│      })                               loginWithBiometrics() {}       │
│    }                                  void togglePasswordVisibility()│
│                                       Future<void>                   │
│                                       _initializeBiometrics() {}    │
│                                      }                               │
│                                                                       │
│  • Tudo misturado (UI + Lógica)      • Separação: Estado + Lógica   │
│  • Listeners manuais                 • Watchers automáticos com obs │
│  • findViewById para cada view        • Controlar via late           │
│  • Validação ad-hoc em eventos       • Validação estruturada        │
│                                                                       │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ 5. LAYER: VIEW (Apresentação)                                               │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  Android (XML):                     Flutter (Dart):                        │
│  ──────────────                     ─────────────────                      │
│  <LinearLayout>                     class LoginView extends GetView<    │
│    <TextInputLayout>                  LoginController> {                │
│      <TextInputEditText                @override                        │
│        android:id="@+id/edtLogin"    Widget build(BuildContext) {      │
│  </TextInputLayout>                   return Scaffold(                 │
│                                         body: Column(                  │
│  <Button                                children: [                    │
│    android:id="@+id/btnEntrar"         TextField(                      │
│    android:text="ENTRAR"                controller: loginController,   │
│  </Button>                              onChanged: (_) =>              │
│                                           controller.update(),         │
│  <ProgressBar                          ),                              │
│    android:id="@+id/progressBar"       Obx(() =>                       │
│  </ProgressBar>                        ElevatedButton(                 │
│  </LinearLayout>                       onPressed: ctrl.isLoading.value │
│                                          ? null : ctrl.login,          │
│  • XML estático                        child: ...                      │
│  • Listeners em Activity               ),                              │
│  • Atualização via método              ]                               │
│  • Sem reatividade nativa              ),                              │
│                                        );                              │
│                                     }                                  │
│                                   }                                    │
│                                                                         │
│  • Dart reativo                     │
│  • Listeners integrados com Obx()   │
│  • Atualização automática com obs   │
│  • Responsivo by default            │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║                          FLUXO DE DADOS - LOGIN SUCCESS                      ║
╚══════════════════════════════════════════════════════════════════════════════╝

ANDROID:                            FLUTTER:
────────                            ────────

1. User digita login
   ↓                                1. User digita login
   edtLogin.addTextWatcher(...)        ↓
   ↓                                   TextField(onChanged: update())
   
2. Button ativa                     2. Button ativa
   ↓                                   ↓
   if (length >= 2)                   showLoginButton.obs = true
   btnEntrar.startAnimation()         → Obx() atualiza view
   ↓
   
3. User clica "Entrar"              3. User clica "Entrar"
   ↓                                   ↓
   onClick() → logarNoApp()           controller.login()
   ↓                                   ↓
   
4. Validação                        4. Validação
   ↓                                   ↓
   isCamposValidos()                  repository._validateFields()
   ↓
   
5. Busca SQLite                     5. Carrega stored credentials
   ↓                                   ↓
   UsuarioDAO.getLista(...)           getSavedCredentials()
   ↓
   
6. Chama API                        6. Chama API
   ↓                                   ↓
   apiRotas.logarAPI(...)             apiService.logarAPI(...)
   ↓                                   ↓
   
7. Callback onResponse               7. Future resolvido
   ↓                                   ↓
   switch (statusCode)                if (response.isSuccess)
   ┌─────→ case 200:                  ↓
   │         usuario.setLogado('S')   repository._saveLoginCredentials()
   │         LoginDAO.add()           ↓
   │         UsuarioHelper.set...()   controller.currentUser = usuario
   │         startActivity()          ↓
   │         finish()                 Get.offNamed('/principal')
   │
   └─────→ case 500:
           edtSenha.setError()

8. Navega para Principal            8. Navega para Principal
   ↓                                   ↓
   ActivityPrincipal abre             HomeView renderiza
   
══════════════════════════════════════════════════════════════════════════════

RESUMO DE VANTAGENS DO MVC EM FLUTTER:

✓ Separação clara de responsabilidades
✓ Testabilidade melhorada (unittests isolados)
✓ Reusabilidade de controllers
✓ Estado centralizado (GetX)
✓ Menos código boilerplate que Android
✓ Hot reload para desenvolvimento rápido
✓ Tipagem forte com null-safety
✓ Performance superior em animações

*/

