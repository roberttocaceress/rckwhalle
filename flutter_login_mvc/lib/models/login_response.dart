class LoginResponse {
  final int statusCode;
  final String message;
  final Usuario? usuario;
  final String? error;

  LoginResponse({
    required this.statusCode,
    required this.message,
    this.usuario,
    this.error,
  });

  bool get isSuccess => statusCode == 200;
  bool get isInvalidPassword => statusCode == 500;
  bool get isNotFound => statusCode == 404;
}

