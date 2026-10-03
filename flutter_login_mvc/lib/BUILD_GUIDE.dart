// BUILDER DE CONFIGURAÇÃO - Execute após adicionar/alterar modelos

// 1. Gere a parte do modelo:
// flutter pub run build_runner build --delete-conflicting-outputs

// 2. Ou use watch para desenvolvimento:
// flutter pub run build_runner watch

// ============= ESTRUTURA COMPLETA DO PROJETO =============

/*
flutter_login_mvc/
├── lib/
│   ├── main.dart                              # Entry point & Setup de dependências
│   ├── models/
│   │   ├── usuario_model.dart                # Modelo do usuário (Serializable)
│   │   └── login_response.dart               # Response do login
│   ├── services/
│   │   └── api_service.dart                  # Cliente Retrofit para API
│   ├── repositories/
│   │   └── login_repository.dart             # Camada de negócio com validações
│   ├── controllers/
│   │   └── login_controller.dart             # Controller GetX com lógica
│   └── views/
│       └── login_view.dart                   # Interface de login moderna
│
├── assets/
│   ├── images/                               # Logos e imagens
│   └── icons/                                # Ícones customizados
│
├── pubspec.yaml                              # Dependências do projeto
└── android/build.gradle                      # Config Android (MIN SDK 21)

============= FLUXO MVC =============
1. VIEW (login_view.dart):
   - Exibe campos de entrada (login, senha)
   - Exibe botões (entrar, biometria, ajuda)
   - Observa mudanças do controller
   - Valida animações (zoom in/out do botão)

2. CONTROLLER (login_controller.dart):
   - Gerencia estado (isLoading, errors, showPassword)
   - Orquestra fluxo de login
   - Interage com repositório
   - Manipula biometria

3. REPOSITORY (login_repository.dart):
   - Valida entradas (min 2 caracteres)
   - Detecta email vs usuário
   - Chama API corretamente
   - Persiste credenciais localmente

4. MODEL (usuario_model.dart):
   - Serialização JSON ↔ Dart
   - Mapeia campos da API

============= REGRAS IMPLEMENTADAS =============
✓ Validação: Login e senha min 2 caracteres
✓ Animação: Zoom in/out do botão entrar
✓ Email/Login: Detecta automaticamente
✓ Dupla API: Fallback para servidor 2
✓ Biometria: Suporte nativo Android
✓ Offline: Salva credenciais localmente
✓ Erros: Mensagens específicas por código
  - 200: Sucesso
  - 500: Senha incorreta
  - 404: Usuário não encontrado
✓ UI Moderna: Gradiente, shadows, responsivo

============= INSTALAÇÃO =============
1. flutter pub get
2. flutter pub run build_runner build
3. flutter run
*/

