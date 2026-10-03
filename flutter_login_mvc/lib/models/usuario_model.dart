import 'package:json_annotation/json_annotation.dart';

part 'usuario_model.g.dart';

@JsonSerializable()
class Usuario {
  @JsonKey(name: 'ID_USUARIO')
  String idUsuario;

  @JsonKey(name: 'LOGIN')
  String login;

  @JsonKey(name: 'NOME_USUARIO')
  String nomeUsuario;

  @JsonKey(name: 'EMAIL_VENDEDOR')
  String? emailVendedor;

  @JsonKey(name: 'APARELHO_ID')
  String? aparelhoId;

  @JsonKey(name: 'USUARIO_PERMITE_BIOMETRIA')
  String? usuarioPermiteBiometria;

  @JsonKey(name: 'ID_EMPRESA_MULTI_DEVICE')
  String? idEmpresaMultiDevice;

  @JsonKey(name: 'APARELHO_KEY_FIREBASE')
  String? firebaseToken;

  @JsonKey(name: 'LOGADO')
  String logado;

  @JsonKey(name: 'ATIVO')
  String ativo;

  Usuario({
    required this.idUsuario,
    required this.login,
    required this.nomeUsuario,
    this.emailVendedor,
    this.aparelhoId,
    this.usuarioPermiteBiometria,
    this.idEmpresaMultiDevice,
    this.firebaseToken,
    required this.logado,
    required this.ativo,
  });

  factory Usuario.fromJson(Map<String, dynamic> json) =>
      _$UsuarioFromJson(json);

  Map<String, dynamic> toJson() => _$UsuarioToJson(this);
}

