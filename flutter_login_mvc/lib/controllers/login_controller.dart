import 'package:get/get.dart';
import 'package:local_auth/local_auth.dart';
import 'package:device_info_plus/device_info_plus.dart';
import '../models/usuario_model.dart';
import '../repositories/login_repository.dart';

class LoginController extends GetxController {
  final LoginRepository repository;

  // Estados reativos
  final isLoading = false.obs;
  final isBiometricsAvailable = false.obs;
  final isBiometricsEnabled = false.obs;
  final showPassword = false.obs;
  final loginError = ''.obs;
  final senhaError = ''.obs;
  final showLoginButton = false.obs;
  final currentUser = Rx<Usuario?>(null);

  // Controllers de texto
  late final loginController = TextEditingController();
  late final senhaController = TextEditingController();

  // Serviço de biometria
  final LocalAuthentication _localAuth = LocalAuthentication();

  LoginController({required this.repository});

  @override
  void onInit() {
    super.onInit();
    _initializeBiometrics();
    _loadSavedCredentials();
    _setupTextWatchers();
  }

  @override
  void onClose() {
    loginController.dispose();
    senhaController.dispose();
    super.onClose();
  }

  /// Inicializa verificação de biometria
  Future<void> _initializeBiometrics() async {
    try {
      final isDeviceSupported = await _localAuth.canCheckBiometrics;
      final isDeviceCredentialSupported = await _localAuth.deviceSupportsBiometrics;
      
      isBiometricsAvailable.value = isDeviceSupported || isDeviceCredentialSupported;
      
      if (isBiometricsAvailable.value) {
        final enabled = await repository.hasBiometricsEnabled();
        isBiometricsEnabled.value = enabled;
      }
    } catch (e) {
      print('Erro ao verificar biometria: $e');
      isBiometricsAvailable.value = false;
    }
  }

  /// Carrega credenciais salvas
  Future<void> _loadSavedCredentials() async {
    final credentials = await repository.getSavedCredentials();
    if (credentials['login']?.isNotEmpty ?? false) {
      loginController.text = credentials['login'] ?? '';
    }
  }

  /// Configura watchers para campos de entrada
  void _setupTextWatchers() {
    loginController.addListener(_validateInputs);
    senhaController.addListener(_validateInputs);
  }

  /// Valida e habilita botão de login
  void _validateInputs() {
    final loginValid = loginController.text.length >= 2;
    final senhaValid = senhaController.text.length >= 2;
    
    showLoginButton.value = loginValid && senhaValid;

    // Limpa erros enquanto digita
    if (loginValid) loginError.value = '';
    if (senhaValid) senhaError.value = '';
  }

  /// Login com credenciais
  Future<void> login() async {
    if (!_validateFields()) return;

    try {
      isLoading.value = true;
      loginError.value = '';
      senhaError.value = '';

      final deviceId = await _getDeviceId();
      
      final response = await repository.loginWithCredentials(
        login: loginController.text.trim(),
        senha: senhaController.text,
        deviceId: deviceId,
      );

      if (response.isSuccess) {
        currentUser.value = response.usuario;
        await repository.enableBiometrics(true);
        // Navega para próxima tela
        Get.offNamed('/principal');
      } else if (response.isInvalidPassword) {
        senhaError.value = 'Senha incorreta';
        senhaController.clear();
      } else if (response.isNotFound) {
        loginError.value = 'Usuário não encontrado';
      } else {
        Get.snackbar(
          'Erro',
          response.message,
          snackPosition: SnackPosition.BOTTOM,
        );
      }
    } finally {
      isLoading.value = false;
    }
  }

  /// Login com biometria
  Future<void> loginWithBiometrics() async {
    if (!isBiometricsEnabled.value || !isBiometricsAvailable.value) {
      Get.snackbar(
        'Biometria',
        'Biometria não está habilitada',
        snackPosition: SnackPosition.BOTTOM,
      );
      return;
    }

    try {
      final isAuthenticated = await _localAuth.authenticate(
        localizedReason: 'Autentique-se para fazer login',
        options: const AuthenticationOptions(
          stickyAuth: true,
          biometricOnly: true,
        ),
      );

      if (isAuthenticated) {
        isLoading.value = true;
        
        final deviceId = await _getDeviceId();
        final response = await repository.loginWithBiometrics(deviceId: deviceId);

        if (response.isSuccess) {
          currentUser.value = response.usuario;
          Get.offNamed('/principal');
        } else {
          Get.snackbar(
            'Erro',
            response.message,
            snackPosition: SnackPosition.BOTTOM,
          );
        }
      }
    } catch (e) {
      Get.snackbar(
        'Erro na Biometria',
        'Falha de autenticação biométrica',
        snackPosition: SnackPosition.BOTTOM,
      );
    } finally {
      isLoading.value = false;
    }
  }

  /// Toggle visibilidade da senha
  void togglePasswordVisibility() {
    showPassword.value = !showPassword.value;
  }

  // ============= Métodos Privados =============

  bool _validateFields() {
    bool isValid = true;

    if (loginController.text.length < 2) {
      loginError.value = 'Insira seu usuário ou email';
      isValid = false;
    }

    if (senhaController.text.length < 2) {
      senhaError.value = 'Insira sua senha';
      isValid = false;
    }

    return isValid;
  }

  Future<String> _getDeviceId() async {
    final deviceInfo = DeviceInfoPlugin();
    try {
      final androidInfo = await deviceInfo.androidInfo;
      return androidInfo.id;
    } catch (e) {
      return 'DEVICE_ID_DEFAULT';
    }
  }
}

