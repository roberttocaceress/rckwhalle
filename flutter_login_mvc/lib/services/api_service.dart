import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';
import '../models/usuario_model.dart';

part 'api_service.g.dart';

@RestApi()
abstract class ApiService {
  factory ApiService(Dio dio, {String baseUrl}) = _ApiService;

  /// Login com usuário e senha
  /// Tipos de login:
  /// - "US": Usuário e Senha
  /// - "AB": Aparelho + Biometria
  @GET('/usuario/login/{idandroid}/{idusuario}/{usuarioLogin}/{senha}/{emailVendedor}')
  Future<Usuario> logarAPI(
    @Path('idandroid') String idAndroid,
    @Path('idusuario') String idUsuario,
    @Path('usuarioLogin') String usuarioLogin,
    @Path('senha') String senha,
    @Path('emailVendedor') String emailVendedor,
  );

  /// Atualiza dados biométricos
  @GET('/usuario/update/biometria/{login}/{senha}/{email}')
  Future<Usuario> updateBiometria(
    @Path('login') String login,
    @Path('senha') String senha,
    @Path('email') String email,
  );

  /// Lista usuários criptografados
  @GET('/usuario/listar/encrypt')
  Future<List<String>> getListaUsuariosCryptografado();

  /// Lista usuários normais
  @GET('/usuario/listar')
  Future<List<Usuario>> getListaUsuarios();
}

