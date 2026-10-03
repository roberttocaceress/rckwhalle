import 'package:flutter/material.dart';

/// Configuração centralizada da aplicação
class AppConfig {
  // ============= CONEXÃO API =============
  /// URL do servidor principal (RCK Whalle)
  static const String apiUrlPrimary = 'http://seu_ip:porta/rckwhalleAPI2.7.3/ws/';
  
  /// URL do servidor secundário (fallback)
  static const String apiUrlSecondary = 'http://seu_ip_backup:porta/rckwhalleAPI2.7.3/ws/';
  
  /// Timeout de conexão (segundos)
  static const int connectionTimeout = 15;
  
  /// Timeout de leitura (segundos)
  static const int readTimeout = 15;
  
  /// Timeout de escrita (segundos)
  static const int writeTimeout = 15;

  // ============= AUTENTICAÇÃO =============
  /// Mínimo de caracteres para login
  static const int minLoginLength = 2;
  
  /// Mínimo de caracteres para senha
  static const int minPasswordLength = 2;
  
  /// Habilitar biometria por padrão
  static const bool enableBiometricsDefault = true;
  
  /// Salvar credenciais localmente
  static const bool saveCredentialsLocally = true;

  // ============= UI/UX =============
  /// Cor primária (azul)
  static const Color primaryColor = Color(0xFF1E3C72);
  
  /// Cor secundária
  static const Color secondaryColor = Color(0xFF2A5298);
  
  /// Duração animação zoom (ms)
  static const Duration animationDuration = Duration(milliseconds: 300);

  // ============= VALIDAÇÃO =============
  /// Padrão regex para validar email
  static const String emailPattern = 
    r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$';

  // ============= HTTP STATUS CODES =============
  static const int statusSuccess = 200;
  static const int statusInvalidPassword = 500;
  static const int statusUserNotFound = 404;
  static const int statusBadRequest = 400;

  // ============= CHAVES LOCAL STORAGE =============
  static const String keySavedLogin = 'saved_login';
  static const String keySavedPassword = 'saved_senha';
  static const String keyCurrentUserId = 'current_user_id';
  static const String keyCurrentUserName = 'current_user_name';
  static const String keyBiometricsEnabled = 'biometria_habilitada';
  static const String keyUserLoggedIn = 'user_logado';
}

/// Mensagens de erro padrão
class ErrorMessages {
  static const String invalidLogin = 'Insira seu usuário ou email';
  static const String invalidPassword = 'Insira sua senha';
  static const String passwordTooShort = 'Senha deve ter no mínimo 2 caracteres';
  static const String userNotFound = 'Usuário não encontrado';
  static const String invalidPassword1 = 'Senha incorreta';
  static const String noInternet = 'Você precisa estar conectado a internet/rede local';
  static const String serverError = 'Erro ao conectar com o servidor';
  static const String biometricsUnavailable = 'Biometria não está habilitada';
  static const String biometricsFailed = 'Falha na autenticação biométrica';
  static const String unknownError = 'Erro desconhecido. Tente novamente.';
  static const String connectionTimeout = 'Conexão expirou. Verifique sua internet.';
  static const String companyNotConfigured = 'Usuário não tem código de empresa configurado';
}

/// Textos de successo
class SuccessMessages {
  static const String loginSuccess = 'Login realizado com sucesso';
  static const String biometricSuccess = 'Autenticação biométrica realizada';
  static const String logoutSuccess = 'Você foi desconectado';
}

