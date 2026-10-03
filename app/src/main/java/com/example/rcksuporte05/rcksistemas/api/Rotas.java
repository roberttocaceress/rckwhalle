package com.example.rcksuporte05.rcksistemas.api;

import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.EstoqueSaldo;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Foto;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesGrupoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.model.NotaFiscal;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.Segmento;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.TabelaRCK;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoAnalise;
import com.example.rcksuporte05.rcksistemas.util.classesGeocoderUtil.RespostaGeocoder;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public interface Rotas {

    @GET("usuario/listar")
    Call<List<Usuario>> getListaUsuarios();

    @GET("usuario/listar/encrypt")
    Call<List<String>> getListaUsuariosCryptografado();

    @GET("usuario/lista")
    Call<List<Usuario>> getLista();

    @GET("usuario/login/{idandroid}/{idusuario}/{usuarioLogin}/{senha}/{emailVendedor}")
    Call<Usuario> logarAPI(@Path("idandroid") String idandroid, @Path("idusuario") String idusuario, @Path("usuarioLogin") String usuarioLogin,
                           @Path("senha") String senha, @Path("emailVendedor") String emailVendedor);

    @GET("usuario/update/alerta/{id_usuario}/{key_firebase}/{versao_app}/{notifica_update_app}/{notifica_status_ped}/{notifica_vencto_fin}")
    Call<Usuario> updateUsuario(@Path("usuario") Usuario usuario);

    @GET("usuario/update/biometria/{login}/{senha}/{email}")
    Call<Usuario> updateBiometria( @Path("login") String login,
                                   @Path("senha") String senha,
                                   @Path("email") String email);

    @GET("usuario/updateAvaliacaoAPP/{idusuario}/{avaliacao}")
    Call<List<Usuario>> updateAvalicaoAPP(@Path("idusuario") String idusuario, @Path("avaliacao") String avaliacao);


    /*
    Call<Usuario> update(@Path("id_usuario") String id_usuario,
                        @Path("key_firebase") String key_firebase,
                        @Path("versao_app") String versao_app,
                        @Path("notifica_update_app") String notifica_update_app,
                        @Path("notifica_status_ped") String notifica_status_ped,
                        @Path("notifica_vencto_fin") String notifica_vencto_fin);
     */
    @GET("usuario/recuperarSenha/{idandroid}/{emailVendedor}")
    Call<Usuario> recuperarSenha(@Path("idandroid") String idandroid, @Path("emailVendedor") String emailVendedor);

    @GET("tabelas/alterar")
    Call<TabelaRCK> alterarTabelas();



    @POST("sincronia/{id}/{key_firebase}/{versao_app}")
    Call<Sincronia> sincroniaApi(@Path("id") int id, @Path("key_firebase") String key_firebase, @Path("versao_app") String versao_app, @HeaderMap Map<String, String> chaveDeAcesso, @Body Sincronia sincronia);

    @POST("sincronia/checkin/{id}/{key_firebase}/{versao_app}/{latitude}/{longitude}")
    Call<Sincronia> sincroniaApiChekin(@Path("id") int id,
                                       @Path("key_firebase") String key_firebase,
                                       @Path("versao_app") String versao_app,
                                       @Path("latitude") String latitude,
                                       @Path("longitude") String longitude,
                                       @HeaderMap Map<String, String> chaveDeAcesso, @Body Sincronia sincronia);

    @POST("webpedido/faturar/")
    Call<List<WebPedido>> enviarPedidos(@Body List<WebPedido> webPedidos, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("webpedido/consultar/{data_inicial}/{data_final}")
    Call<List<WebPedido>> consultarPedidos(@Path("data_inicial") String data_inicial, @Path("data_final") String data_final, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("webpedido/visualizar/{data_inicial}/{data_final}/{id_vendedor}")
    Call<List<WebPedido>> visualizarPedidos(@Path("data_inicial") String data_inicial, @Path("data_final") String data_final,
                                            @Path("id_vendedor") String id_vendedor, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("webpedido/listaenviados/{id_usuario}/{id_cadastro}")
    Call<List<WebPedido>> listaEnviadosCliente(@Path("id_usuario") String id_usuario,
                                            @Path("id_cadastro") String id_cadastro,
                                            @HeaderMap Map<String, String> chaveDeAcesso);

    //visualizar/{data_inicial}/{data_final}/{id_vendedor}

    @POST("webpedido/sincronizar/{id}")
    Call<Sincronia> sincronizaPedido(  @Path("id") String idUsuario, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);

    @POST("webpedido/analisar/{id}")
    Call<Sincronia> analisaPedido(  @Path("id") String idUsuario, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("webpedido/listarAnalise/{id_pedido}/{id_ordem}")
    Call<WebPedidoAnalise> listaAnalise(@Path("id_pedido") String id_pedido, @Path("id_ordem") String id_ordem, @HeaderMap Map<String, String> chaveDeAcesso);
    ///analisar/{idPedido}/{idOrdem}

    @GET("historicofinanceiro/listar/{id}/{dias_hist_financeiro}")
    Call<HistoricoFinanceiro> getHistoricoFinanceiro(@Path("id") int idCliente, @Path("dias_hist_financeiro") int dias_hist_financeiro, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/listar/{tipo}/{id}/{dias_hist_financeiro}")
    Call<List<Financeiro>> getFinanceiro(@Path("tipo") String tipo, @Path("id") int idCliente,
                                         @Path("dias_hist_financeiro") int dias_hist_financeiro,

                                         @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/listarpdf/{id}/{tipo}/{id_cliente}/{dias_hist_financeiro}")
    Call<List<Financeiro>> getFinanceiroPdf(@Path("id") String id,
                                            @Path("tipo") String tipo,
                                            @Path("id_cliente") int idCliente,
                                            @Path("dias_hist_financeiro") int dias_hist_financeiro,
                                            @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/listarpdf2/{id}/{tipo}/{id_cliente}/{dias_hist_financeiro}/{versao_app}")
    Call<List<Financeiro>> getFinanceiroPdf2(@Path("id") String id,
                                            @Path("tipo") String tipo,
                                            @Path("id_cliente") int idCliente,
                                            @Path("dias_hist_financeiro") int dias_hist_financeiro,
                                            @Path("versao_app") String versao_app,
                                            @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/pendente/{id}")
    Call<List<CadastroFinanceiroResumo>> getFinanceiroPendente(@Path("id") int idCliente, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/listarhistoricocliente/{idusuario}/{idcliente}/{situacao}")
    Call<List<FinanceiroResumo>> gethistoricoFinanceiroCliente(@Path("idusuario") String idUsuario,
                                                               @Path("idcliente") int idCliente,
                                                               @Path("situacao") String situacao,@HeaderMap Map<String, String> chaveDeAcesso);



    @POST("foto/salvarimagem")
    Call<Foto> salvarImagem(@Body Foto foto);

    @GET("segmento/listar")
    Call<List<Segmento>> buscarTodosSegmentos(@HeaderMap Map<String, String> chaveDeAcesso);

    @GET("motivonaocadastramento/listar")
    Call<List<MotivoNaoCadastramento>> buscarTodosMotivos(@HeaderMap Map<String, String> chaveDeAcesso);

    @POST("prospect/salvar")
    Call<List<Prospect>> salvarProspect(@HeaderMap Map<String, String> chaveDeAcesso, @Body List<Prospect> prospect);

    @GET("maps/api/geocode/json?key=AIzaSyATLB7h2anOZofvV4KCfrhqMuZ9-1hr4HM")
    Call<RespostaGeocoder> getGeocoder(@Query("latlng") String latlng, @Query("sensor") Boolean sensor, @Query("language") String language);

    @POST("visita/salvar")
    Call<List<VisitaProspect>> salvarVisita(@HeaderMap Map<String, String> chaveAcesso, @Body List<VisitaProspect> listaVisita);

    @GET("cadastrofinanceiroresumo/listar/{id}")
    Call<CadastroFinanceiroResumo> sincronizaFinanceiro(@Path("id") int id, @HeaderMap Map<String, String> chaveAcesso);

    @POST("cliente/salvar")
    Call<List<Cliente>> salvarClientes(@HeaderMap Map<String, String> chaveAcesso, @Body List<Cliente> clientes);

    @POST("generico/cliente/salvar")  //Usado na api kotlin
    Call<List<Cliente>> addClientes(@HeaderMap Map<String, String> chaveAcesso, @Body List<Cliente> clientes);

    @POST("cliente/sincronizar/{id_usuario}/{id_cadastro}")
    Call<List<Cliente>> buscarCliente(@Path("id_usuario") String id_usuario, @Path("id_cadastro") int id_cadastro,  @HeaderMap Map<String, String> chaveAcesso);

    @GET("cliente/verificacpfcnpj/{cpfcnpj}")
    Call<Cliente> verificaCpfCnpj(@Path("cpfcnpj") String cpfCnpj, @HeaderMap Map<String, String> chaveAcesso);

    @GET("generico/cliente/verificacpfcnpj/{cpfcnpj}")  //Usado na api kotlin
    Call<Cliente> analisaCpfCnpj(@Path("cpfcnpj") String cpfCnpj, @HeaderMap Map<String, String> chaveAcesso);

    @POST("prospect/validar")
    Call<Prospect> validaProspect(@Body Prospect prospect, @HeaderMap Map<String, String> chaveAcesso);

    @GET("produtoFoto/listarPorID/{id}")
    Call<List<ProdutoFoto>> produtoImagemID(@Path("id") String id, @HeaderMap Map<String, String> chaveAcesso);

    @GET("generico/produto/anexo/{id}")
    Call<List<ProdutoFoto>> produtoAnexo(@Path("id") String id, @HeaderMap Map<String, String> chaveAcesso);

    @POST("produtoFoto/salvarProdutoFoto/")
    Call<List<ProdutoFoto>> salvarProdutoFoto(@Body List<ProdutoFoto> listaProdutoFoto, @HeaderMap Map<String, String> chaveDeAcesso);

    //Relatorio resumo entrada/saida
    //Roberto Caceres

    @GET("notafiscal/sinteticoperiodo/{datainicial}/{datafinal}/{movestoque}/{movfinanceiro}/{pagacomissao}/{id}")  // Data inicial e final dentro do mes e id do usuario
    Call<List<NotaFiscal>> getNotaSinteticoPeriodo(@Path("datainicial") String datainicial, @Path("datafinal") String datafinal,
                                            @Path("movestoque") String movestoque, @Path("movfinanceiro") String movfinanceiro,
                                            @Path("pagacomissao") String pagacomissao, @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("notafiscal/analiticoperiodo/{datainicial}/{datafinal}/{idoperacao}/{tipo}/{id}")  // Data inicial e final dentro do mes e id do usuario idoperacao = compra/venda etc... tipo entrada ou saida
    Call<List<NotaFiscal>> getNotaAnaliticoPeriodo(@Path("datainicial") String datainicial, @Path("datafinal") String datafinal,
                                                     @Path("idoperacao") int idoperacao, @Path("tipo") String tipo,
                                                     @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("notafiscal/analiticodata/{dataemissao}/{idoperacao}/{tipo}/{id}")  // Data  dentro do mes e id do usuario idoperacao = compra/venda etc... tipo entrada ou saida
    Call<List<NotaFiscal>> getNotaAnaliticoData(@Path("dataemissao") String dataemissao, @Path("idoperacao") int idoperacao, @Path("tipo") String tipo, @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    //Relatorio resumo financeiro
    //Roberto Caceres
    @GET("financeiro/sinteticoperiodo/{datainicial}/{datafinal}/{periodo}/{situacao}/{razaoSocial}/{documento}/{cheque}/{idconta}/{especie}/{id}")  // Data inicial e final dentro do mes e id do usuario
    Call<List<FinanceiroResumo>> getSinteticoPeriodo(@Path("datainicial") String datainicial, @Path("datafinal") String datafinal,
                                                     @Path("periodo") String periodo, @Path("situacao") String situacao,
                                                     @Path("razaoSocial") String razaoSocial, @Path("documento") String documento,  @Path("cheque") String cheque,
                                                     @Path("idconta") String idconta,  @Path("especie") String especie,
                                                     @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/analiticoperiodo/{datainicial}/{datafinal}/{periodo}/{situacao}/{razaoSocial}/{documento}/{cheque}/{idconta}/{tipo}/{especie}/{id}")  // Data inicial e final dentro do mes e id do usuario idconta = banco,caixa interno etc... tipo debito ou Credito
    Call<List<FinanceiroResumo>> getAnaliticoPeriodo(@Path("datainicial") String datainicial, @Path("datafinal") String datafinal,
                                                     @Path("periodo") String periodo,  @Path("situacao") String situacao,
                                                     @Path("razaoSocial") String razaoSocial, @Path("documento") String documento,  @Path("cheque") String cheque,
                                                     @Path("idconta") int idconta, @Path("tipo") String tipo, @Path("especie") String especie,
                                                     @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/analiticodata/{data}/{periodo}/{situacao}/{razaoSocial}/{documento}/{cheque}/{idconta}/{tipo}/{especie}/{id}")  // Data  dentro do mes e id do usuario idconta = banco,caixa interno etc... tipo debito ou Credito
    Call<List<FinanceiroResumo>> getAnaliticoData(@Path("data") String data,
                                                  @Path("periodo") String periodo,@Path("situacao") String situacao,
                                                  @Path("razaoSocial") String razaoSocial, @Path("documento") String documento,  @Path("cheque") String cheque,
                                                  @Path("idconta") int idconta, @Path("tipo") String tipo,  @Path("especie") String especie,
                                                  @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("produto/estoqueSaldo/{status}/{nomeProduto}/{codigoProduto}/{codigoEAN}/{idDeposito}/{idGrupo}/{tipo}/{idUsuario}/{idDeposito}/{idGrupo}")
    Call<List<EstoqueSaldo>> getEstoqueSaldo(  @Path("status") String status, @Path("nomeProduto") String nomeProduto, @Path("codigoProduto") String codigoProduto, @Path("codigoEAN") String codigoEAN,
                                               @Path("idDeposito") String idDeposito, @Path("idGrupo") String idGrupo, @Path("tipo") String tipo,
                                               @Path("idUsuario") String idUsuario,@HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/cobrancadata/{datainicial}/{datafinal}/{periodo}/{razaoSocial}/{documento}/{idconta}/{idmoeda}/{idbairro}/{id}")  // Data inicial e final dentro do mes e id do usuario idconta = banco,caixa interno etc... tipo debito ou Credito
    Call<List<FinanceiroResumo>> getCobrancaPeriodo(@Path("datainicial") String datainicial, @Path("datafinal") String datafinal,
                                                     @Path("periodo") String periodo,
                                                     @Path("razaoSocial") String razaoSocial, @Path("documento") String documento,
                                                     @Path("idconta") String idconta,  @Path("idmoeda") String idmoeda,
                                                     @Path("idbairro") String idbairro,
                                                     @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("financeiro/listarcliente/{idcadastro}/{id}")
    Call<List<FinanceiroResumo>> getCobrancaCliente(@Path("idcadastro") String idcadastro, @Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso);  //id = IdUsuario

    @POST("financeiro/sincronizar/{idcadastro}/{id}")
    Call<Sincronia> sincronizaCobrancaRecebimento( @Path("idcadastro") String idcadastro, @Path("id") int id, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);

    @POST("financeiro/sincronizar2/{idcadastro}/{id}")
    Call<Sincronia> sincronizaSemCobranca( @Path("idcadastro") String idcadastro, @Path("id") int id, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);

    @POST("cobranca/sincronizar/{idcadastro}/{id}")
    Call<Sincronia> getAddCobrancaRecebimento( @Path("idcadastro") String idcadastro, @Path("id") int id, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);

    @POST("cobranca/sincronizar2/{idcadastro}/{id}")
    Call<Sincronia> getSemCobrancaRecebimento( @Path("idcadastro") String idcadastro, @Path("id") int id, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);

    //@POST("financeiro/sincronizar2/{idcadastro}/{id}")
    //Call<Sincronia> sincronizaSemCobranca( @Path("idcadastro") String idcadastro, @Path("id") int id, @Body Sincronia sincronia, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("generico/financeiroresumo/listar/{id}")
    Call<CadastroFinanceiroResumo> getFinanceiroResumo(@Path("id") int id, @HeaderMap Map<String, String> chaveAcesso);

    @GET("generico/financeirotitulos/listar/{tipo}/{id}/{dias_hist_financeiro}")
    Call<List<Financeiro>> getFinanceiroTitulos(@Path("tipo") String tipo, @Path("id") int idCliente, @Path("dias_hist_financeiro") int dias_hist_financeiro, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/totalvendasgrupo/{id}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getTotalVendaPorGrupo(@Path("id") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/totalvendascliente/{id}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getTotalVendaPorCliente(@Path("id") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("pedido/somatotalvendasgrupo/{id}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getSomaTotalVendaPorGrupo(@Path("id") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalvendascliente/{id}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getSomaTotalVendaPorCliente(@Path("id") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalvendasporcliente/{id}/{idCliente}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getSomaTotalVendaPorCliente(@Path("id") int idUsuario, @Path("idCliente") int idCliente, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalvendasperiodo/{id}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getSomaTotalVendaPeriodo(@Path("id") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    //Usado no grafico financeiro
    @GET("pedido/somatotalrealizado/financeiro/periodo/{idUsuario}/{idCliente}/{qtdePeriodo}")
    Call<List<List<Financeiro>>> getSomaTotalFinanceiroPeriodo(@Path("idUsuario") int idUsuario,
                                                               @Path("idCliente") String idCliente,
                                                               @Path("qtdePeriodo") int qtdePeriodo, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("pedido/totalvendasmarca/{id}/{dataInicial}/{dataFinal}")
    Call<List<ParticipacaoFaturamento>> getTotalVendaPorMarca(@Path("id") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/expansao/carteira/{idUsuario}/{dataInicial}/{dataFinal}")
    Call<List<com.example.rcksuporte05.rcksistemas.model.ExpansaoCarteira>> getExpansaoCarteira(@Path("idUsuario") int idUsuario, @Path("dataInicial") String dataInicial, @Path("dataFinal") String dataFinal, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/totalvendasmes/{id}")
    Call<List<EvolucaoFaturamento>> getTotalVendaMes(@Path("id") int idUsuario, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/totalvendasmesames/{id}")
    Call<List<EvolucaoFaturamento>> getTotalVendaMesMes(@Path("id") int idUsuario, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/totalvendasmesamesgrupo/{id}")
    Call<List<EvolucaoFaturamento>> getTotalVendaMesMesGrupo(@Path("id") int idUsuario, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("pedido/totalvendassemanaasemana/{id}")
    Call<List<EvolucaoFaturamento>> getTotalVendaSemanaSemana(@Path("id") int idUsuario, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("pedido/somatotalrealizadoperiodogrupo/{id}/{anoSelecionado}/{idGrupo}")
    Call<List<MesAno>> getSomaTotalRealizadoPeriodoGrupo(@Path("id") int idUsuario, @Path("anoSelecionado") int anoSelecionado, @Path("idGrupo") int idGrupo, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalrealizadoperiodomarca/{id}/{anoSelecionado}/{idMarca}")
    Call<List<MesAno>> getSomaTotalRealizadoPeriodoMarca(@Path("id") int idUsuario, @Path("anoSelecionado") int anoSelecionado, @Path("idMarca") int idMarca, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalrealizadoperiodo/{id}/{anoSelecionado}")
    Call<List<MesAno>> getSomaTotalRealizadoPeriodo(@Path("id") int idUsuario, @Path("anoSelecionado") int anoSelecionado, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("pedido/somatotalrealizado/semana/periodo/{id}/{mesSelecionado}")
    Call<List<MesSemana>> getSomaTotalRealizadoSemanaPeriodo(@Path("id") int idUsuario, @Path("mesSelecionado") String mesSelecionado, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalrealizado/v2/semana/periodo/{id}/{periodoSelecionado}/{qtdePeriodo}")
    Call<List<MesSemana>> getSomaTotalRealizadoV2SemanaPeriodo(@Path("id") int idUsuario,
                                                               @Path("periodoSelecionado") String periodoSelecionado,
                                                               @Path("qtdePeriodo") String qtdePeriodo, @HeaderMap Map<String, String> chaveDeAcesso);

    //este
    @GET("pedido/somatotalrealizado/mes/periodo/{id}/{periodoSelecionado}/{qtdePeriodo}")
    Call<List<MesAnoMeta>> getSomaTotalRealizadoMesPeriodo(@Path("id") int idUsuario,
                                                           @Path("periodoSelecionado") String periodoSelecionado,
                                                           @Path("qtdePeriodo") String qtdePeriodo, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalrealizadocancelado/mes/periodo/{id}/{periodoSelecionado}/{qtdePeriodo}/{statusPedido}")
    Call<List<MesAnoMeta>> getSomaTotalRealizadoCanceladoMesPeriodo(@Path("id") int idUsuario,
                                                           @Path("periodoSelecionado") String periodoSelecionado,
                                                           @Path("qtdePeriodo") String qtdePeriodo,
                                                           @Path("statusPedido") String statusPedido,
                                                           @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalrealizado/grupo/periodo/{id}/{periodoSelecionado}")
    Call<List<MesGrupoMeta>> getSomaTotalRealizadoGrupoPeriodo(@Path("id") int idUsuario, @Path("periodoSelecionado") String periodoSelecionado, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("pedido/somatotalrealizado/linhacolecao/periodo/{id}/{periodoSelecionado}")
    Call<List<MesGrupoMeta>> getSomaTotalRealizadoLinhaColecaoPeriodo(@Path("id") int idUsuario, @Path("periodoSelecionado") String periodoSelecionado, @HeaderMap Map<String, String> chaveDeAcesso);


    @GET("pedido/somatotalrealizado/cliente/periodo/{id}/{periodoSelecionado}/{qtdePeriodo}")
    Call<List<MesAnoMeta>> getSomaTotalRealizadoClientePeriodo(@Path("id") int idUsuario,
                                                                    @Path("periodoSelecionado") String periodoSelecionado,
                                                                    @Path("qtdePeriodo") String qtdePeriodo,
                                                                    @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("webpedido/analise/{idPedido}/{idOrdem}")
    Call<WebPedidoAnalise> getAnalisePedido(@Path("idPedido") String idPedido, @Path("idOrdem") String idOrdem, @HeaderMap Map<String, String> chaveDeAcesso);
//    /analisar/{idPedido}/{idOrdem}

    @GET("cnpj/{cnpj}")
    Call<JsonObject> consultarCnpjSefaz(@Path("cnpj") String cnpj);


}
