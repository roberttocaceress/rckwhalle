# 🚀 Guia de Inicialização Rápida - Flutter Login MVC

## ⚡ Setup em 5 minutos

### Pré-requisitos
```bash
✓ Flutter SDK instalado (flutter doctor)
✓ Android Studio / Xcode configurado
✓ Emulador ou device físico pronto
```

---

## 📋 Opção 1: Criar Novo Projeto

```bash
# 1. Crie um novo projeto Flutter
flutter create rcksistemas_login

# 2. Entre no diretório
cd rcksistemas_login

# 3. Copie os arquivos de lib/ desta implementação
# para seu projeto

# 4. Copie pubspec.yaml (dependências)

# 5. Instale dependências
flutter pub get

# 6. Gere código Retrofit
flutter pub run build_runner build --delete-conflicting-outputs

# 7. Execute
flutter run
```

---

## 📋 Opção 2: Integrar em Projeto Existente

```bash
# 1. Adicione dependências ao pubspec.yaml
# (copie a seção "dependencies" desta implementação)

# 2. Execute pub get
flutter pub get

# 3. Copie arquivos para lib/
lib/
├── controllers/login_controller.dart
├── models/usuario_model.dart
├── repositories/login_repository.dart
├── services/api_service.dart
├── views/login_view.dart
└── ... (resto dos arquivos)

# 4. Atualize seu main.dart
# Copie _setupDependencies() e execute em main()

# 5. Configure rotas em GetMaterialApp
# Adicione GetPage para '/login'

# 6. Regenere código
flutter pub run build_runner build
```

---

## ⚙️ Configuração Essencial

### 1. URL da API (main.dart)
```dart
void _setupDependencies() async {
  // ...
  Get.put<ApiService>(
    ApiService(
      dio,
      baseUrl: 'http://seu_servidor:porta/rckwhalleAPI2.7.3/ws/'
    ),
  );
}
```

### 2. Minified ProGuard Rules (android/app/proguard-rules.pro)
```
-keep class com.example.rcksuporte05.rcksistemas.model.** { *; }
-keep class retrofit2.** { *; }
-keepclasseswithmembers class retrofit2.** { *; }
```

### 3. AndroidManifest.xml (android/app/src/main/)
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
<uses-permission android:name="android.permission.USE_FINGERPRINT" />
```

---

## 🧪 Teste Rápido

### Teste 1: Build
```bash
flutter clean
flutter pub get
flutter pub run build_runner build
flutter build apk --debug
```

### Teste 2: Run
```bash
flutter run

# Expected output:
# ✓ App inicia na tela de login
# ✓ Campos vazios desabilitam botão
# ✓ 2 caracteres = botão aparece com zoom
# ✓ Biometria visível se device suporta
```

### Teste 3: Login Local
```
Login: vendedor (ou qualquer 2+ caracteres)
Senha: 123456 (ou qualquer 2+ caracteres)
Clique em ENTRAR

Expected:
- Loading mostrado
- API chamada
- Se falhar: mensagem de erro
- Se sucesso: navega para próxima tela
```

---

## 📱 Hardware Testing

| Função | Command |
|---|---|
| **Abrir DevTools** | `flutter run -d <device_id>` |
| **Ver logs** | `flutter logs` |
| **Profiling** | `flutter run --profile` |
| **Release build** | `flutter build apk --release` |

---

## 🔍 Verificação Pós-Setup

### ✅ Checklist
```
□ flutter doctor mostra "No issues"
□ pub get executa sem erros
□ build_runner não reclama de modelos
□ flutter run inicia app
□ Tela de login aparece
□ Campos respondem a input
□ Botão "Entrar" aparece com 2+ caracteres
□ Validações funcionam
□ Sem erros no console
```

### 🐛 Se algo não funcionar

```bash
# 1. Limpe tudo
flutter clean
rm -rf pubspec.lock
rm -rf build/

# 2. Reinstale
flutter pub get

# 3. Regenere código
flutter pub run build_runner build --delete-conflicting-outputs

# 4. Execite
flutter run -v  # verbose mode para mais detalhes
```

---

## 📊 Estrutura Final (Após Setup)

```
seu_projeto/
├── lib/
│   ├── main.dart
│   ├── controllers/
│   ├── models/
│   ├── repositories/
│   ├── services/
│   ├── views/
│   ├── config/
│   └── utils/
├── test/
├── assets/
├── pubspec.yaml
└── android/
    ├── app/
    │   ├── build.gradle
    │   ├── proguard-rules.pro
    │   └── src/main/AndroidManifest.xml
    └── build.gradle
```

---

## 🎬 Próximos Passos

1. **Customize cores** → Edite `config/app_config.dart`
2. **Customize textos** → Edite `ErrorMessages` class
3. **Teste biometria** → Use device físico com fingerprint
4. **Integre com API real** → Configure URL + credenciais
5. **Adicione telas** → Crie novas Views e Controllers
6. **Deploy** → `flutter build apk --release`

---

## 📞 Suporte

### Erro Comum: "ApiService not found in GetX"
**Solução:** Verifique se `_setupDependencies()` é chamado em `main()` ANTES de `runApp()`

### Erro Comum: "DioException: Failed to connect"
**Solução:** Verifique URL em `main.dart` + permissão INTERNET em AndroidManifest.xml

### Erro Comum: "Retrofit generated code error"
**Solução:** 
```bash
flutter pub run build_runner clean
flutter pub run build_runner build --delete-conflicting-outputs
```

### Biometria não funciona no emulador
**Solução:** Use device físico com biometria ou reconfigure emulador com suporte

---

## 📚 Referências

- **Flutter Docs**: https://flutter.dev/docs
- **GetX**: https://pub.dev/packages/get
- **Retrofit**: https://pub.dev/packages/retrofit
- **Dio**: https://pub.dev/packages/dio
- **Local Auth**: https://pub.dev/packages/local_auth

---

## ✨ Resumo da Implementação

Você agora tem um **sistema de login profissional em Flutter** com:

✅ Padrão MVC limpo
✅ State management com GetX
✅ API integration com Retrofit
✅ Biometria nativa
✅ Validações de entrada
✅ UI moderna e responsiva
✅ Testes unitários
✅ Documentação completa

**Tempo estimado de setup: 5-10 minutos** 🚀

Boa sorte! 🎉

