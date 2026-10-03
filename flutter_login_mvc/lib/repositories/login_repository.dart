import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/usuario_model.dart';
import '../models/login_response.dart';
import 'api_service.dart';

class LoginRepository {
  final ApiService apiService;
  final SharedPreferences prefs;

  LoginRepository({
    required this.apiService,
    required this.prefs,
  });

  /// Realiza login com usuário e senha
  Future<LoginResponse> loginWithCredentials({
    required String login,
    required String senha,
    required String deviceId,
  }) async {
    try {
      // Valida formato antes de chamar API
      if(!_isValidInput(login) || !_isValidInput(senha)) {
        return LoginResponse(
          statusCode: 400,
          message: 'Campos inválidos',
          error: 'Login e senha devem ter no mínimo 2 caracteres',
        );
      }

      final isEmail = _isValidEmail(login);
      
      final usuario = await apiService.logarAPI(
        deviceId,
        '', // idUsuario será obtido do localStorage
        isEmail ? '-' : login.toUpperCase(),
        senha,
        isEmail ? login.toLowerCase() : '-',
      );

      // Sucesso - Salva dados localmente
      await _saveLoginCredentials(login, senha, usuario);

      return LoginResponse(
        statusCode: 200,
        message: 'Login realizado com sucesso',
        usuario: usuario,
      );
    } on DioException catch (e) {
      return _handleDioError(e);
    }
  }

  /// Realiza login com biometria
  Future<LoginResponse> loginWithBiometrics({
    required String deviceId,
  }) async {
    try {
      final savedLogin = prefs.getString('saved_login') ?? '';
      final savedSenha = prefs.getString('saved_senha') ?? '';

      if (savedLogin.isEmpty || savedSenha.isEmpty) {
        return LoginResponse(
          statusCode: 401,
          message: 'Credenciais não salvas',
          error: 'Primeiro faça login com usuário e senha',
        );
      }

      final usuario = await apiService.logarAPI(
        deviceId,
        '', // idUsuario
        '-',
        '-',
        '-',
      );

      return LoginResponse(
        statusCode: 200,
        message: 'Login biométrico realizado',
        usuario: usuario,
      );
    } on DioException catch (e) {
      return _handleDioError(e);
    }
  }

  /// Recupera credenciais salvas
  Future<Map<String, String>> getSavedCredentials() async {
    return {
      'login': prefs.getString('saved_login') ?? '',
      'senha': prefs.getString('saved_senha') ?? '',
    };
  }

  /// Verifica se existe biometria habilitada
  Future<bool> hasBiometricsEnabled() async {
    return prefs.getBool('biometria_habilitada') ?? false;
  }

  /// Habilita biometria
  Future<void> enableBiometrics(bool enabled) async {
    await prefs.setBool('biometria_habilitada', enabled);
  }

  // ============= Métodos Privados =============

  bool _isValidInput(String input) {
    return input.trim().length >= 2;
  }

  bool _isValidEmail(String email) {
    final emailRegex = RegExp(
      r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
    );
    return emailRegex.hasMatch(email.toLowerCase());
  }

  Future<void> _saveLoginCredentials(
    String login,
    String senha,
    Usuario usuario,
  ) async {
    await prefs.setString('saved_login', login);
    await prefs.setString('saved_senha', senha);
    await prefs.setString('current_user_id', usuario.idUsuario);
    await prefs.setString('current_user_name', usuario.nomeUsuario);
  }

  LoginResponse _handleDioError(DioException e) {
    String mensagem = 'Erro ao conectar';
    int statusCode = 500;

    if (e.response != null) {
      statusCode = e.response!.statusCode ?? 500;
      
      if (statusCode == 500) {
        return LoginResponse(
          statusCode: 500,
          message: 'Senha incorreta',
          error: 'Verifique suas credenciais',
        );
      } else if (statusCode == 404) {
        return LoginResponse(
          statusCode: 404,
          message: 'Usuário não encontrado',
          error: 'Este usuário não existe',
        );
      }
    }

    if (e.type == DioExceptionType.connectionTimeout) {
      mensagem = 'Conexão expirou. Verifique sua internet';
    } else if (e.type == DioExceptionType.receiveTimeout) {
      mensagem = 'Servidor demorando. Tente novamente';
    } else if (e.type == DioExceptionType.unknown) {
      mensagem = 'Você precisa estar conectado a internet/rede local';
    }

    return LoginResponse(
      statusCode: statusCode,
      message: mensagem,
      error: e.message ?? 'Erro desconhecido',
    );
  }
}

