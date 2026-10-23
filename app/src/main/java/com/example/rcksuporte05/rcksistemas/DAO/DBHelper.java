package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Alerta;
import com.example.rcksuporte05.rcksistemas.model.Banco;
import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Contato;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.ReferenciaBancaria;
import com.example.rcksuporte05.rcksistemas.model.ReferenciaComercial;
import com.example.rcksuporte05.rcksistemas.model.Segmento;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoCab;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.util.MyFirebaseInstanceIdService;

import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static String NomeBanco = "RCKWhalle.db";
    private SQLiteDatabase dbf; //foto


    public DBHelper(Context context) {
        super(context, NomeBanco, null, 28);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_USUARIO " +
                "(ID_USUARIO INTEGER PRIMARY KEY," +
                " ATIVO VARCHAR(1)," +
                " NOME_USUARIO VARCHAR(150)," +
                " LOGIN VARCHAR(100)," +
                " SENHA VARCHAR(100)," +
                " SENHA_CONFIRMA VARCHAR(100)," +
                " DATA_CADASTRO TIMESTAMP," +
                " USUARIO_CADATRO VARCHAR(20)," +
                " DATA_ALTERADO TIMESTAMP," +
                " USUARIO_ALTEROU VARCHAR(20)," +
                " APARECE_CAD_USUARIO VARCHAR(1)," +
                " CLIENTE_LISTA_TODOS VARCHAR(1)," +
                " CLIENTE_LISTA_SETOR VARCHAR(1)," +
                " CLIENTE_LISTA_REPRESENTANTE VARCHAR(1)," +
                " PEDIDO_LISTA_TODOS VARCHAR(1)," +
                " PEDIDO_LISTA_SETOR VARCHAR(1)," +
                " PEDIDO_LISTA_REPRESENTANTE VARCHAR(1)," +
                " MENSAGEM_LISTA_FINANCEIRO VARCHAR(1)," +
                " MENSAGEM_LISTA_TODOS VARCHAR(1)," +
                " MENSAGEM_LISTA_SETOR VARCHAR(1)," +
                " MENSAGEM_LISTA_REPRESENTANTE VARCHAR(1)," +
                " ORCAMENTO_LISTA_TODOS VARCHAR(1)," +
                " ORCAMENTO_LISTA_SETOR VARCHAR(1)," +
                " ORCAMENTO_LISTA_REPRESENTANTE VARCHAR(1)," +
                " USUARIO_LISTA_TODOS VARCHAR(1)," +
                " USUARIO_LISTA_SETOR VARCHAR(1)," +
                " USUARIO_LISTA_REPRESENTANTE VARCHAR(1)," +
                " EXCLUIDO VARCHAR(1)," +
                " ID_SETOR INTEGER," +
                " ID_QUANDO_VENDEDOR INTEGER," +
                " APARELHO_ID VARCHAR(20)," +
                " ID_EMPRESA_MULTI_DEVICE INTEGER, " +
                " EMAIL_VENDEDOR VARCHAR(50), " +
                " APARELHO_KEY_FIREBASE VARCHAR(200), " +
                " APARELHO_VERSAO_APP VARCHAR(10), " +
                " APARELHO_NOTIFICA_UPDATE_APP VARCHAR(1), " +
                " APARELHO_NOTIFICA_STATUS_PED VARCHAR(1), " +
                " APARELHO_NOTIFICA_VENCTO_FIN VARCHAR(1), " +
                " APARELHO_LINK_APP VARCHAR(200)," +
                " USUARIO_TIPO VARCHAR(1)," +
                " USUARIO_PERMITE_N_LOGINS VARCHAR(1), " +
                " USUARIO_PERMITE_BIOMETRIA VARCHAR(1), " +
                " USUARIO_AVALIACAO_APP INTEGER, " +
                " USUARIO_EDITA_PRECO_APP VARCHAR(1)," +
                " USUARIO_UF VARCHAR(2)," +
                " USUARIO_ID_CIDADE INTEGER," +
                " USUARIO_ALTERA_CLIENTE_PED VARCHAR(1));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_LOGIN " +
                "(ID_LOGIN INTEGER PRIMARY KEY, " +
                "LOGIN VARCHAR(100), " +
                "SENHA VARCHAR(100), " +
                "LOGADO VARCHAR(1), " +
                "TOKEN VARCHAR(60), " +
                "APARELHO_ID VARCHAR(20), " +
                "DATA_SINCRONIA TIMESTAMP, " +
                "DATA_SINCRONIA_PRODUTO TIMESTAMP, " +
                "DATA_SINCRONIA_CLIENTE TIMESTAMP, " +
                "DATA_SINCRONIA_IMAGEN TIMESTAMP, " +
                "DATA_SINCRONIA_COBRANCA TIMESTAMP," +
                "USUARIO_UF VARCHAR(2)," +
                "USUARIO_ID_CIDADE INTEGER," +
                "USUARIO_LEMBRETE_SINCRONIA CHAR(1)," +
                "USUARIO_LEMBRETE_SINCRONIA_DATA TIMESTAMP);");





        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO " +
                "(ATIVO VARCHAR(1) DEFAULT 'S'  NOT NULL ," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_CADASTRO INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ID_PROSPECT INTEGER ," +
                " ID_SEGMENTO INTEGER," +
                " DESCRICAO_SEGMENTO VARCHAR(300)," +
                " REFERENCIA_BANCARIA INTEGER," +
                " REFERENCIA_COMERCIAL INTEGER," +
                " ID_CADASTRO_SERVIDOR INTEGER ," +
                " PESSOA_F_J VARCHAR(1)," +
                " DATA_ANIVERSARIO DATE," +
                " NOME_CADASTRO VARCHAR(60)," +
                " NOME_FANTASIA VARCHAR(60)," +
                " CPF_CNPJ VARCHAR(14)," +
                " INSCRI_ESTADUAL VARCHAR(20)," +
                " ENDERECO VARCHAR(60)," +
                " ENDERECO_BAIRRO VARCHAR(60)," +
                " ENDERECO_NUMERO VARCHAR(20)," +
                " ENDERECO_COMPLEMENTO VARCHAR(20)," +
                " ENDERECO_UF CHAR(2) NOT NULL," +
                " NOME_MUNICIPIO VARCHAR(50)," +
                " ENDERECO_CEP VARCHAR(8)," +
                " USUARIO_ID INTEGER," +
                " USUARIO_NOME VARCHAR(60)," +
                " USUARIO_DATA TIMESTAMP," +
                " F_CLIENTE VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_FORNECEDOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_FUNCIONARIO VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_VENDEDOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_TRANSPORTADOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " DATA_ULTIMA_COMPRA DATE," +
                " NOME_VENDEDOR VARCHAR(60)," +
                " F_ID_CLIENTE INTEGER," +
                " ID_ENTIDADE INTEGER NOT NULL," +
                " F_ID_FORNECEDOR INTEGER," +
                " F_ID_VENDEDOR SMALLINT," +
                " F_ID_TRANSPORTADOR INTEGER," +
                " TELEFONE_PRINCIPAL VARCHAR(20)," +
                " EMAIL_PRINCIPAL VARCHAR(100)," +
                " ID_PAIS INTEGER ," +
                " F_ID_FUNCIONARIO INTEGER," +
                " AVISAR_COM_DIAS INTEGER DEFAULT 0 ," +
                " OBSERVACOES BLOB," +
                " PADRAO_ID_C_CUSTO INTEGER," +
                " PADRAO_ID_C_GERENCIADORA INTEGER," +
                " PADRAO_ID_C_ANALITICA INTEGER," +
                " COB_ENDERECO VARCHAR(60)," +
                " COB_ENDERECO_BAIRRO VARCHAR(60)," +
                " COB_ENDERECO_NUMERO VARCHAR(20)," +
                " COB_ENDERECO_COMPLEMENTO VARCHAR(20)," +
                " COB_ENDERECO_UF VARCHAR(2)," +
                " NOME_COB_MUNICIPIO VARCHAR(60)," +
                " COB_ENDERECO_CEP VARCHAR(8)," +
                " COB_ENDERECO_ID_PAIS INTEGER ," +
                " LIMITE_CREDITO DECIMAL(12, 2)," +
                " LIMITE_DISPONIVEL DECIMAL(12, 2)," +
                " PESSOA_CONTATO_FINANCEIRO VARCHAR(80)," +
                " EMAIL_FINANCEIRO VARCHAR(80)," +
                " OBSERVACOES_FATURAMENTO VARCHAR(300)," +
                " OBSERVACOES_FINANCEIRO VARCHAR(300)," +
                " TELEFONE_DOIS VARCHAR(20)," +
                " TELEFONE_TRES VARCHAR(20)," +
                " PESSOA_CONTATO_PRINCIPAL VARCHAR(80)," +
                " IND_DA_IE_DESTINATARIO INTEGER," +
                " COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                " ID_SETOR INTEGER," +
                " NFE_EMAIL_ENVIAR VARCHAR(1)," +
                " NFE_EMAIL_UM VARCHAR(60)," +
                " NFE_EMAIL_DOIS VARCHAR(60)," +
                " NFE_EMAIL_TRES VARCHAR(60)," +
                " NFE_EMAIL_QUATRO VARCHAR(60)," +
                " NFE_EMAIL_CINCO VARCHAR(60)," +
                " ID_GRUPO_VENDEDOR INTEGER," +
                " VENDEDOR_USA_PORTAL VARCHAR(1)," +
                " VENDEDOR_ID_USER_PORTAL INTEGER," +
                " F_TARIFA VARCHAR(1)," +
                " F_ID_TARIFA INTEGER," +
                " F_PRODUTOR VARCHAR(1)," +
                " RG_NUMERO VARCHAR(30)," +
                " RG_SSP VARCHAR(10)," +
                " CONTA_CONTABIL VARCHAR(15)," +
                " MOTORISTA VARCHAR(1)," +
                " F_ID_MOTORISTA INTEGER," +
                " HABILITACAO_NUMERO VARCHAR(20)," +
                " HABILITACAO_CATEGORIA VARCHAR(10)," +
                " HABILITACAO_VENCIMENTO DATE," +
                " MOT_ID_TRANSPORTADORA INTEGER," +
                " LOCAL_CADASTRO VARCHAR(20)," +
                " ID_EMPRESA_MULTIDEVICE INTEGER," +
                " ID_CATEGORIA INTEGER," +
                " ID_VENDEDOR INTEGER," +
                " SITUACAO_PREDIO VARCHAR(1)," +
                " ALTERADO VARCHAR(1) DEFAULT 'N', " +
                " FINALIZADO VARCHAR(1) DEFAULT 'S', " +
                " ENDERECO_GPS VARCHAR(300), " +
                " ID_TABELA_PRECO INTEGER, " +
                " NOME_TABELA_PRECO VARCHAR(50), " +
                " UTILIZA_TABELA_PRECO VARCHAR(1), " +
                " TIPO_TABELA_PRECO VARCHAR(1), " +
                " FINANCEIRO_VENCIDO VARCHAR(20), " +
                " FINANCEIRO_VENCENDO VARCHAR(20), " +
                " PEDIDO_ANALISE VARCHAR(20), " +
                " LATITUDE VARCHAR (60), " +
                " LONGITUDE VARCHAR (60), " +
                " ROTA_SEQUENCIA INTEGER, " +
                " ID_REGIAO INTEGER, " +
                " ID_BAIRRO INTEGER, " +
                " ID_CLIENTE_APP_FDB VARCHAR (38));");


        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_CAD_ID_SERVIDOR ON TBL_CADASTRO(ID_CADASTRO_SERVIDOR)");
        } catch ( SQLException e) {
            e.printStackTrace();
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO (ATIVO VARCHAR(1) DEFAULT 'S'  NOT NULL," +
                " ID_PRODUTO VARCHAR(20) PRIMARY KEY," +
                " NOME_PRODUTO VARCHAR(60) NOT NULL," +
                " UNIDADE VARCHAR(4) NOT NULL," +
                " TIPO_CADASTRO INTEGER NOT NULL," +
                " ID_ENTIDADE INTEGER DEFAULT 40  NOT NULL," +
                " NCM VARCHAR(8)," +
                " ID_GRUPO INTEGER," +
                " ID_SUB_GRUPO INTEGER," +
                " PESO_BRUTO DECIMAL(12," +
                " 4) DEFAULT 0 ," +
                " PESO_LIQUIDO DECIMAL(12," +
                " 4) DEFAULT 0 ," +
                " CODIGO_EM_BARRAS VARCHAR(30)," +
                " MOVIMENTA_ESTOQUE CHAR(1) NOT NULL," +
                " NOME_DA_MARCA VARCHAR(60)," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_ORIGEM INTEGER NOT NULL," +
                " CUSTO_PRODUTO DECIMAL(12, 4)," +
                " CUSTO_PER_IPI DECIMAL(12, 4)," +
                " CUSTO_IPI DECIMAL(12, 4)," +
                " CUSTO_PER_FRETE DECIMAL(12, 4)," +
                " CUSTO_FRETE DECIMAL(12, 4)," +
                " CUSTO_PER_ICMS DECIMAL(12, 4)," +
                " CUSTO_ICMS DECIMAL(12, 4)," +
                " CUSTO_PER_FIN DECIMAL(12, 4)," +
                " CUSTO_FIN DECIMAL(12, 4)," +
                " CUSTO_PER_SUBST DECIMAL(12, 4)," +
                " CUSTO_SUBT DECIMAL(12, 4)," +
                " CUSTO_PER_OUTROS DECIMAL(12, 4)," +
                " CUSTO_OUTROS DECIMAL(12, 4)," +
                " VALOR_CUSTO DECIMAL(12, 4)," +
                " EXCLUIDO VARCHAR(1)," +
                " EXCLUIDO_POR VARCHAR(50)," +
                " EXCLUIDO_POR_DATA TIMESTAMP," +
                " EXCLUIDO_CODIGO_NOVO VARCHAR(20)," +
                " AJUSTE_PRECO_DATA TIMESTAMP," +
                " AJUSTE_PRECO_NFE VARCHAR(10)," +
                " AJUSTE_PRECO_USUARIO VARCHAR(30)," +
                " TOTAL_CUSTO DECIMAL(12, 4)," +
                " TOTAL_CREDITO DECIMAL(12, 4)," +
                " VALOR_CUSTO_ESTOQUE DECIMAL(12, 4)," +
                " CUSTO_DATA_INICIAL DATE," +
                " CUSTO_VALOR_INICIAL DECIMAL(12, 4)," +
                " PRODUTO_VENDA VARCHAR(1)," +
                " PRODUTO_INSUMO VARCHAR(1)," +
                " PRODUTO_CONSUMO VARCHAR(1)," +
                " PRODUTO_PRODUCAO VARCHAR(1)," +
                " VENDA_PERC_COMISSAO DECIMAL(12, 6)," +
                " VENDA_PRECO DECIMAL(12, 6)," +
                " VENDA_PERC_COMISSAO_DOIS DECIMAL(12, 4)," +
                " DESCRICAO VARCHAR(20), " +
                " NOME_SUB_GRUPO VARHAR(60), " +
                " NOME_GRUPO VARCHAR(60)," +
                " PRODUTO_MATERIA_PRIMA VARCHAR(1), " +
                " PRODUTO_TERCERIZACAO VARCHAR(1)," +
                " SALDO_ESTOQUE NUMERIC(15, 8), " +
                " ID_LINHA_COLECAO INTEGER," +
                " ANEXO_1 BLOB, " +
                " TRAVAR_FAT_SEM_ESTOQUE VARCHAR(1), " +
                " UTILIZA_TABELA_PRECO VARCHAR(1), " +
                " PRODUTO_EDITA_PRECO_APP VARCHAR(1), " +
                " PRODUTO_ST_ALIQUOTA DECIMAL(12,8), " +
                " PRODUTO_SUJ_ICMS_ST VARCHAR(1));");
        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_ID_GRUPO ON TBL_PRODUTO(ID_GRUPO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_ID_SUB_GRUPO ON TBL_PRODUTO(ID_SUB_GRUPO)");
        } catch ( SQLException e) {
            e.printStackTrace();
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_TABELA_PRECO_CAB (ID_TABELA INTEGER PRIMARY KEY," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ATIVO VARCHAR(1) NOT NULL," +
                " ID_TIPO_TABELA INTEGER," +
                " NOME_TABELA VARCHAR(60)," +
                " DATA_INICIO DATE," +
                " DATA_FIM DATE," +
                " DESCONTO_DE_PERC DECIMAL(12, 4)," +
                " DESCONTO_A_PERC DECIMAL(12, 4)," +
                " COMISSAO_PERC DECIMAL(12, 4)," +
                " VERBA_PERC DECIMAL(12, 4)," +
                " FAIXA_VALOR_DE DECIMAL(12, 4)," +
                " FAIXA_VALOR_A DECIMAL(12, 4)," +
                " USUARIO_ID INTEGER NOT NULL," +
                " USUARIO_NOME VARCHAR(40)," +
                " USUARIO_DATA TIMESTAMP," +
                " DESCONTO_VERBA_MAX DECIMAL(12, 4)," +
                " ID_GRUPO_VENDEDORES INTEGER," +
                " UTILIZA_VERBA CHAR(1)," +
                " FAIXA_VALOR_BRUTO_DE DECIMAL(12, 2)," +
                " FAIXA_VALOR_BRUTO_A DECIMAL(12, 2));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_TABELA_PRECO_ITENS (ID_ITEM INTEGER PRIMARY KEY," +
                " ID_TABELA INTEGER NOT NULL," +
                " PERC_DESC_INICIAL DECIMAL(12, 4)," +
                " PERC_DESC_FINAL DECIMAL(12, 4)," +
                " PERC_COM_INTERNO DECIMAL(12, 4)," +
                " PERC_COM_EXTERNO DECIMAL(12, 4)," +
                " PERC_COM_EXPORTACAO DECIMAL(12, 4)," +
                " PONTOS_PREMIACAO DECIMAL(12, 4)," +
                " COR_PAINEL VARCHAR(15)," +
                " COR_FONTE VARCHAR(15)," +
                " VERBA_PERC DECIMAL(12, 4)," +
                " UTILIZA_VERBA CHAR(1)," +
                " DESCONTO_VERBA_MAX DECIMAL(12, 4)," +
                " ID_USUARIO INTEGER NOT NULL," +
                " USUARIO VARCHAR(30)," +
                " USUARIO_DATA TIMESTAMP," +
                " COR_WEB VARCHAR(20), " +
                " ID_CATEGORIA INTEGER, " +
                " VALOR_UNITARIO DECIMAL(12, 4)," +
                " ID_PRODUTO VARCHAR(20));");

        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_TABELA ON TBL_TABELA_PRECO_ITENS(ID_TABELA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_PRODUTO ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_CATEGORIA ON TBL_TABELA_PRECO_ITENS(ID_CATEGORIA)");

        } catch ( SQLException e) {
            e.printStackTrace();
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_CATEGORIA(ID_CATEGORIA INTEGER NOT NULL PRIMARY KEY," +
                "  ID_EMPRESA     INTEGER    NOT NULL," +
                "  ATIVO          VARCHAR(1) NOT NULL," +
                "  NOME_CATEGORIA VARCHAR(60)," +
                "  USUARIO_ID     INTEGER    NOT NULL," +
                "  USUARIO_NOME   VARCHAR(60)," +
                "  USUARIO_DATA   TIMESTAMP(19)," +
                "  MULTI_DEVICE_ACEITA_NOVO VARCHAR(1)," +
                "  NOME_MULTI_DISPOSITIVO VARCHAR(60));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PROMOCAO_CAB(ID_PROMOCAO INTEGER NOT NULL" +
                "    CONSTRAINT PK_TBL_PROMOCAO_CAB" +
                "    PRIMARY KEY," +
                "  ID_EMPRESA           INTEGER    NOT NULL," +
                "  NUMERO_CLIENTES      INTEGER," +
                "  NUMERO_PRODUTOS      INTEGER," +
                "  ATIVO                VARCHAR(1) NOT NULL," +
                "  APLICACAO_CLIENTE    INTEGER," +
                "  APLICACAO_PRODUTO    INTEGER," +
                "  DESCONTO_PERC        DECIMAL(12, 4)," +
                "  DATA_INICIO_PROMOCAO DATE (10)," +
                "  DATA_FIM_PROMOCAO    DATE (10)," +
                "  NOME_PROMOCAO        VARCHAR(60)," +
                "  USUARIO_ID           INTEGER    NOT NULL," +
                "  USUARIO_NOME         VARCHAR(60)," +
                "  USUARIO_DATA         TIMESTAMP(19));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PROMOCAO_CLIENTE(ID_PROMOCAO  INTEGER NOT NULL," +
                "  ID_CADASTRO  INTEGER NOT NULL," +
                "  ID_EMPRESA   INTEGER NOT NULL," +
                "  ATIVO        VARCHAR(1)," +
                "  USUARIO_ID   INTEGER NOT NULL," +
                "  USUARIO_NOME VARCHAR(60)," +
                "  USUARIO_DATA TIMESTAMP(19)," +
                "  CONSTRAINT PK_TBL_PROMOCAO_CLIENTE" +
                "  PRIMARY KEY (ID_PROMOCAO, ID_CADASTRO));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PROMOCAO_PRODUTO(ID_PROMOCAO INTEGER NOT NULL," +
                "  ID_PRODUTO          VARCHAR(20) NOT NULL," +
                "  ID_EMPRESA          INTEGER     NOT NULL," +
                "  ATIVO               VARCHAR(1)," +
                "  TIPO_DESCONTO       CHAR(1)," +
                "  DESCONTO_PERC       DECIMAL(12, 4)," +
                "  DESCONTO_VALOR      DECIMAL(12, 4)," +
                "  PERC_COM_INTERNO    DECIMAL(12, 4)," +
                "  PERC_COM_EXTERNO    DECIMAL(12, 4)," +
                "  PERC_COM_EXPORTACAO DECIMAL(12, 4)," +
                "  USUARIO_ID          INTEGER     NOT NULL," +
                "  USUARIO_NOME        VARCHAR(60)," +
                "  USUARIO_DATA        TIMESTAMP(19)," +
                "  CONSTRAINT PK_TBL_PROMOCAO_PRODUTO" +
                "  PRIMARY KEY (ID_PROMOCAO, ID_PRODUTO));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CONDICOES_PAG_CAB (ATIVO VARCHAR(1)," +
                "ID_CONDICAO INTEGER PRIMARY KEY," +
                "NOME_CONDICAO VARCHAR(70) NOT NULL," +
                "NUMERO_PARCELAS INTEGER NOT NULL," +
                "INTERVALO_DIAS INTEGER," +
                "TIPO_CONDICAO INTEGER NOT NULL," +
                "NFE_TIPO_FINANCEIRO VARCHAR(20) NOT NULL," +
                "NFE_MOSTRAR_PARCELAS VARCHAR(1) NOT NULL," +
                "USUARIO_ID INTEGER NOT NULL," +
                "USUARIO_NOME VARCHAR(40)," +
                "USUARIO_DATA TIMESTAMP," +
                "PUBLICAR_NA_WEB VARCHAR(1), " +
                "ACEITA_DESCONTO VARCHAR(1), " +
                "DESCONTO_PERC DECIMAL(12, 2));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_VENDEDOR_BONUS_RESUMO (ID_VENDEDOR INTEGER PRIMARY KEY," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " VALOR_CREDITO DECIMAL(12, 2)," +
                " VALOR_DEBITO DECIMAL(12, 2)," +
                " VALOR_BONUS_CANCELADOS DECIMAL(12, 2)," +
                " VALOR_SALDO DECIMAL(12, 2)," +
                " DATA_ULTIMA_ATUALIZACAO DATE);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_PEDIDO (ID_WEB_PEDIDO INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_CADASTRO INTEGER NOT NULL," +
                " ID_VENDEDOR INTEGER NOT NULL," +
                " ID_CONDICAO_PAGAMENTO INTEGER NOT NULL," +
                " ID_OPERACAO INTEGER NOT NULL," +
                " ID_TABELA INTEGER," +
                " NOME_EXTENSO VARCHAR(150)," +
                " DATA_EMISSAO DATE," +
                " VALOR_PRODUTOS DECIMAL(12, 2)," +
                " VALOR_DESCONTO DECIMAL(12, 2)," +
                " VALOR_DESCONTO_ADD DECIMAL(12, 2)," +
                " DESCONTO_PER DECIMAL(12, 2)," +
                " DESCONTO_PER_ADD DECIMAL(12, 2)," +
                " VALOR_TOTAL DECIMAL(12, 3)," +
                " EXCLUIDO VARCHAR(1) NOT NULL," +
                " EXCLUIDO_USUARIO_ID INTEGER," +
                " EXCLUIDO_USUARIO_NOME VARCHAR(40)," +
                " EXCLUIDO_USUARIO_DATA TIMESTAMP," +
                " JUSTIFICATIVA_EXCLUSAO VARCHAR(250)," +
                " USUARIO_LANCAMENTO_ID INTEGER NOT NULL," +
                " USUARIO_LANCAMENTO_NOME VARCHAR(40)," +
                " USUARIO_LANCAMENTO_DATA TIMESTAMP," +
                " OBSERVACOES VARCHAR(300)," +
                " STATUS VARCHAR(1) NOT NULL," +
                " ID_PEDIDO_VENDA INTEGER," +
                " ID_NOTA_FISCAL INTEGER," +
                " ID_TABELA_PRECO_FAIXA INTEGER," +
                " PONTOS_TOTAL DECIMAL(12, 4)," +
                " PONTOS_COEFICIENTE DECIMAL(12, 4)," +
                " PONTOS_COR VARCHAR(15)," +
                " COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                " COMISSAO_VALOR DECIMAL(12, 2)," +
                " ID_FAIXA_FINAL INTEGER," +
                " VALOR_BONUS_CREDOR DECIMAL(12, 2)," +
                " PERC_BONUS_CREDOR DECIMAL(12, 4), " +
                " FATURADO VARCHAR(1)," +
                " PEDIDO_ENVIADO VARCHAR(1) DEFAULT 'N', " +
                " ID_WEB_PEDIDO_SERVIDOR INTEGER," +
                " DATA_PREV_ENTREGA DATE, " +
                " FINALIZADO VARCHAR(1) DEFAULT 'N'," +
                //NOVOS CAMPOS
                " ID_PEDIDO INTEGER, " +
                " WEB_IMPORTADO_DATA DATE, " +
                " ANALISE_CREDITO VARCHAR(1), " +
                " ANALISE_USUARIO_ID INTEGER, " +
                " ANALISE_USUARIO_NOME VARCHAR(50), " +
                " ANALISE_USUARIO_DATA DATE, " +
                " ORDEM_CARREGAMENTO INTEGER," +
                " ID_ORDEM_CARREGAMENTO INTEGER, " +
                " ID_TRANSPORTADORA INTEGER, " +
                " NUMERO_NOTA_FISCAL INTEGER, " +
                " DATA_NOTA_FISCAL DATE, " +
                " ESPECIE VARCHAR(30), " +
                " VOLUMES INTEGER, " +
                " WEB_VALOR_TOTAL DECIMAL(12, 3), " +
                " EXPEDICAO_STATUS VARCHAR(1), " +
                " VALOR_FATURADO DECIMAL(12, 3), " +
                " NOME_TRANSPORTADORA VARCHAR(50), " +
                " ID_MOEDA_PADRAO INTEGER, " +
                " DESCONTO_INDEVIDO VARCHAR(1), " +
                " ID_PEDIDO_APP VARCHAR(38), " +
                " PRAZO_CONFIRMADO VARCHAR(1), " +
                " VALOR_RECIBO DECIMAL(12, 2), "+
                " VALOR_DESCONTO_ADIC DECIMAL(12, 8)," +
                " PERC_DESCONTO_ADIC DECIMAL(12, 8)," +
                " CLIENTE_NOVO VARCHAR(1), " +
                " VALOR_ICMS_ST DECIMAL(12,8), " +
                " ID_OUTRO_VENDEDOR INTEGER, " +
                " QTDE_ITENS VARCHAR(5), " +
                " COPIA_SEGURA VARCHAR(1), "+
                " ID_PEDIDO_APP_FDB VARCHAR(38)," +
                " ID_VISITA_APP VARCHAR(38)," +
                " ANALISE_ESTOQ_EFETUADA VARCHAR(1)," +
                " ANALISE_ESTOQ_DATA_HORA TIMESTAMP);" );
        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_ID_PEDIDO  ON TBL_WEB_PEDIDO(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_ENVIADO    ON TBL_WEB_PEDIDO(PEDIDO_ENVIADO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_FINALIZADO ON TBL_WEB_PEDIDO(FINALIZADO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_USUARIO    ON TBL_WEB_PEDIDO(USUARIO_LANCAMENTO_ID)");
        } catch ( SQLException e) {
            e.printStackTrace();
        }


        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_OPERACAO_ESTOQUE (ATIVO CHAR(1) DEFAULT 'S'  NOT NULL, ID_OPERACAO INTEGER DEFAULT 0 PRIMARY KEY, " +
                "NOME_OPERACAO VARCHAR(60) NOT NULL, NATUREZA_OPERACAO VARCHAR(60));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_PEDIDO_ITENS (ID_WEB_ITEM INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "ID_PEDIDO INTEGER NOT NULL," +
                "ID_PRODUTO VARCHAR(20) NOT NULL," +
                "ID_EMPRESA INTEGER NOT NULL," +
                "QUANTIDADE DECIMAL(12, 8)," +
                "VALOR_UNITARIO DECIMAL(12, 8)," +
                "VALOR_BRUTO DECIMAL(12, 8)," +
                "VALOR_DESCONTO_PER DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL DECIMAL(12, 8)," +
                "VALOR_DESCONTO_PER_ADD DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL_ADD DECIMAL(12, 8)," +
                "VALOR_TOTAL DECIMAL(12, 8)," +
                "DATA_MOVIMENTACAO DATE," +
                "USUARIO_LANCAMENTO_ID INTEGER NOT NULL," +
                "USUARIO_LANCAMENTO_DATA TIMESTAMP," +
                "ID_TABELA_PRECO_FAIXA INTEGER," +
                "ID_ITEM_DESCONTO INTEGER," +
                "PONTOS_UNITARIO DECIMAL(12, 4)," +
                "PONTOS_TOTAL DECIMAL(12, 4)," +
                "PONTOS_COEFICIENTE DECIMAL(12, 6)," +
                "PONTOS_COR VARCHAR(15)," +
                "COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                "COMISSAO_VALOR DECIMAL(12, 2)," +
                "VALOR_BONUS_CREDOR DECIMAL(12, 2)," +
                "PERC_BONUS_CREDOR DECIMAL(12, 4)," +
                "ID_TABELA_PRECO INTEGER," +
                "VALOR_DESCONTO_PER_ORIG DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL_ORIG DECIMAL(12, 8)," +
                "VALOR_DESCONTO_PER_ADD_ORIG DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL_ADD_ORIG DECIMAL(12, 8)," +
                "ID_TABELA_PRECO_FAIXA_ORIG INTEGER," +
                "VALOR_TOTAL_ORIG DECIMAL(12, 8)," +
                "PONTOS_UNITARIO_ORIG DECIMAL(12, 4)," +
                "PONTOS_COEFICIENTE_ORIG DECIMAL(12, 6)," +
                "COMISSAO_PERCENTUAL_ORIG DECIMAL(12, 4)," +
                "VALOR_BONUS_CREDOR_ORIG DECIMAL(12, 2)," +
                "PERC_BONUS_CREDOR_ORIG DECIMAL(12, 4)," +
                "COMISSAO_VALOR_ORIG DECIMAL(12, 2)," +
                "PONTOS_TOTAL_ORIG DECIMAL(12, 4)," +
                "PONTOS_COR_ORIG VARCHAR(15)," +
                "VALOR_PRECO_PAGO DECIMAL(12, 8)," +
                "ITEM_ENVIADO VARCHAR(1) DEFAULT 'N'," +
                "ID_WEB_ITEM_SERVIDOR INTEGER," +
                "TIPO_DESCONTO VARCHAR(1) DEFAULT 'P', " +
                "NOME_PRODUTO VARCHAR(60)," +
                "PRODUTO_MATERIA_PRIMA VARCHAR(1), " +
                "PRODUTO_TERCERIZACAO VARCHAR(1), " +
                "ID_LINHA_COLECAO INTEGER, " +
                "ID_CAMPANHA INTEGER, " +
                "PRODUTO_PRECO_EDITADO DECIMAL(12, 4), " +
                "ID_SKU INTEGER," +
                "NOME_SKU VARCHAR(60)," +
                "VALOR_DESCONTO_ADIC DECIMAL(12, 8)," +
                "PERC_DESCONTO_ADIC DECIMAL(12, 8), " +
                "ICMS_ST_VALOR DECIMAL(12,8), " +
                "ICMS_ST_CALCULA VARCHAR(1), " +
                "ICMS_ST_PERCENTUAL DECIMAL(12, 8)," +
                "ANALISE_ESTOQ_RESULTADO VARCHAR(1)," +
                "ID_GRUPO INTEGER," +
                "ID_SUB_GRUPO );") ;//A atende N não atende ; //


        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPI_ID_PEDIDO ON TBL_WEB_PEDIDO_ITENS(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPI_ID_PRODUTO ON TBL_WEB_PEDIDO_ITENS(ID_PRODUTO)");
        }catch ( SQLException e) {
            e.printStackTrace();
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PROSPECT " +
                "(ATIVO VARCHAR(1) DEFAULT 'S' NOT NULL," +
                "ID_PROSPECT INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID_PROSPECT_SERVIDOR INTEGER," +
                "ID_CADASTRO INTEGER," +
                "ID_SEGMENTO INTEGER," +
                "ID_MOTIVO_NAO_CADASTRAMENTO INTEGER," +
                "REFERENCIA_BANCARIA INTEGER," +
                "REFERENCIA_COMERCIAL INTEGER," +
                "LISTA_CONTATO INTEGER," +
                "NOME_CADASTRO VARCHAR(60)," +
                "NOME_FANTASIA VARCHAR(60)," +
                "PESSOA_F_J VARCHAR(1)," +
                "CPF_CNPJ VARCHAR(20)," +
                "INSCRI_ESTADUAL VARCHAR(20)," +
                "ENDERECO VARCHAR(60)," +
                "ENDERECO_BAIRRO VARCHAR(60)," +
                "ENDERECO_NUMERO VARCHAR(20)," +
                "ENDERECO_COMPLEMENTO VARCHAR(300)," +
                "ENDERECO_UF VARCHAR(2)," +
                "NOME_MUNICIPIO VARCHAR(60)," +
                "ENDERECO_CEP VARCHAR(20)," +
                "ID_PAIS INTEGER," +
                "USUARIO_ID INTEGER," +
                "USUARIO_NOME VARCHAR(60)," +
                "USUARIO_DATA DATE," +
                "SITUACAO_PREDIO VARCHAR(1)," +
                "LIMITE_CREDITO_SUGERIDO DECIMAL(12,2)," +
                "LIMITE_PRAZO_SUGERIDO DECIMAL(12,2)," +
                "ID_EMPRESA INTEGER," +
                "DATA_RETORNO DATE," +
                "IND_DA_IE_DESTINATARIO_PROSPECT INTEGER, " +
                "OBSERVACOES_COMERCIAIS VARCHAR(300)," +
                "LATITUDE VARCHAR (60)," +
                "LONGITUDE VARCHAR (60), " +
                "DESCRICAO_SEGMENTO VARCHAR(300)," +
                "DESCRICAO_MOTIVO_NAO_CAD VARCHAR(300)," +
                "PROSPECT_SALVO VARCHAR(1) DEFAULT 'N', " +
                "ID_VENDEDOR INTEGER, " +
                "ID_CATEGORIA INTEGER, " +
                "FINALIZADO VARCHAR(1) DEFAULT 'S', " +
                "ENDERECO_GPS VARCHAR(300), " +
                "TELEFONE VARCHAR(20), " +
                "ID_PRIMEIRA_VISITA INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_SEGMENTO" +
                "(ATIVO VARCHAR(1) DEFAULT 'S' NOT NULL," +
                "ID_SETOR INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOME_SETOR VARCHAR(60)," +
                "DESCRICAO_OUTROS VARCHAR(300));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_MOTIVO_NAO_CAD" +
                "(ID_ITEM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MOTIVO VARCHAR(300)," +
                "DESCRICAO_OUTROS VARCHAR(300));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PAISES " +
                "(ID_PAIS INTEGER PRIMARY KEY, " +
                "NOME_PAIS VARCHAR(60) NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_BANCOS_FEBRABAN " +
                "(CODIGO_FEBRABAN VARCHAR(6) PRIMARY KEY," +
                " NOME_BANCO VARCHAR(60)," +
                " HOME_PAGE VARCHAR(60));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_REFERENCIA_BANCARIA" +
                "(ID_REFERENCIA_BANCARIA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID_REFERENCIA_BANCARIA_SERVIDOR INTEGER, " +
                "ID_CADASTRO_SERVIDOR INTEGER, " +
                "CODIGO_FEBRABAN INTEGER," +
                "NOME_BANCO VARCHAR(60)," +
                "CONTA_CORRENTE VARCHAR(60)," +
                "AGENCIA VARCHAR(60)," +
                "ID_CADASTRO INTEGER," +
                "USUARIO_ID INTEGER," +
                "NOME_USUARIO VARCHAR(60), " +
                "ID_ENTIDADE INTEGER NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_REFERENCIA_COMERCIAL" +
                "(ID_REFERENCIA_COMERCIAL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID_REFERENCIA_COMERCIAL_SERVIDOR INTEGER," +
                "ID_CADASTRO_SERVIDOR INTEGER," +
                "NOME_FORNECEDOR_REFERENCIA VARCHAR(60)," +
                "TELEFONE VARCHAR(20)," +
                "ID_CADASTRO INTEGER," +
                "USUARIO_ID INTEGER," +
                "NOME_USUARIO VARCHAR(60), " +
                "ID_ENTIDADE INTEGER NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_CONTATO" +
                "(ID_CONTATO INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID_CADASTRO INTEGER," +
                "ID_CADASTRO_SERVIDOR INTEGER," +
                "ID_CONTATO_SERVIDOR INTEGER," +
                "ATIVO VARCHAR(1)," +
                "PESSOA_CONTATO VARCHAR(60)," +
                "FUNCAO VARCHAR(60)," +
                "EMAIL VARCHAR(60)," +
                "TIPO_TELEFONE VARCHAR(60)," +
                "OPERADORA VARCHAR(60)," +
                "NUMERO_TELEFONE VARCHAR(20)," +
                "DATA_ANIVERSARIO DATE," +
                "OBSERVACAO VARCHAR(300)," +
                "USUARIO_ID INTEGER," +
                "USUARIO_NOME VARCHAR(60)," +
                "USUARIO_DATA DATE," +
                "CELULAR VARCHAR(20)," +
                "CELULAR2 VARCHAR(20)," +
                "EMAIL2 VARCHAR(60)," +
                "FORNECEDOR1 VARCHAR(60)," +
                "FORNECEDOR2 VARCHAR(60)," +
                "TEL_FORNEC1 VARCHAR(20)," +
                "TEL_FORNEC2 VARCHAR(20), " +
                "ID_ENTIDADE INTEGER NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_VISITA_PROSPECT (" +
                "ID_VISITA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DESCRICAO_VISTA VARCHAR(300), " +
                "DATA_VISITA DATE, " +
                "USUARIO_ID INTEGER, " +
                "DATA_PROXIMA_VISITA DATE, " +
                "TIPO_CONTATO VARCHAR(20), " +
                "LATITUDE VARCHAR(200), " +
                "LONGITUDE VARCHAR(200)," +
                "ID_CADASTRO_SERVIDOR INTEGER," +
                "ID_VISITA_SERVIDOR INTEGER, " +
                "ID_CADASTRO INTEGER, " +
                "TITULO VARCHAR(50));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_VISITA (" +
                "ID_VISITA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_VISITA_APP VARCHAR(38), " +
                "ID_VENDEDOR INTEGER, " +
                "ID_PEDIDO INTEGER, " +
                "ID_PEDIDO_APP VARCHAR(38), " +
                "ID_PEDIDO_SERVIDOR INTEGER, " +
                "ID_CLIENTE_SERVIDOR INTEGER, " +
                "DATA_HORA_CHECKIN DATETIME, " +
                "LATITUDE_CHECKIN VARCHAR(60), " +
                "LONGITUDE_CHECKIN VARCHAR(60), " +
                "DATA_HORA_CHECKOUT DATETIME, " +
                "LATITUDE_CHECKOUT VARCHAR(60), " +
                "LONGITUDE_CHECKOUT VARCHAR(60), " +
                "FOTO_CHECKIN_1 BLOB, " +
                "FOTO_CHECKIN_2 BLOB, " +
                "STATUS_VISITA VARCHAR(1), " +
                "MOTIVO_NAO_VENDA VARCHAR(50), " +
                "ID_MOTIVO_NAO_VENDA INTEGER, " +
                "VERSAO_APP VARCHAR(10), " +
                "OBSERVACAO TEXT, " +
                "EXCLUIDO VARCHAR(1) DEFAULT 'N');");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_FINANCEIRO_RESUMO (ID_CADASTRO INTEGER NOT NULL PRIMARY KEY," +
                "               LIMITE_CREDITO NUMERIC (18,2)," +
                "               FINANCEIRO_VENCIDO NUMERIC (18,2)," +
                "               FINANCEIRO_VENCER NUMERIC (18,2)," +
                "               FINANCEIRO_QUITADO NUMERIC (18,2)," +
                "               PEDIDOS_LIBERADOS NUMERIC (18,2)," +
                "               LIMITE_UTILIZADO NUMERIC (18,2)," +
                "               LIMITE_DISPONIVEL NUMERIC (18,2)," +
                "               USUARIO_ID INTEGER," +
                "               USUARIO_NOME VARCHAR (60)," +
                "               USUARIO_DATA TIMESTAMP ," +
                "               DATA_ULTIMA_ATUALIZACAO TIMESTAMP);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_ANEXOS(ID_ANEXO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "               ID_ANEXO_SERVIDOR INTEGER , " +
                "               ID_ENTIDADE INTEGER NOT NULL DEFAULT 1, " +
                "               ID_CADASTRO INTEGER, " +
                "               ID_CADASTRO_SERVIDOR INTEGER, " +
                "               NOME_ANEXO VARCHAR(150), " +
                "               ANEXO BLOB, " +
                "               EXCLUIDO VARCHAR(1) DEFAULT 'N', " +
                "               PRINCIPAL VARCHAR(1) DEFAULT 'N');");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PEDIDO_ANEXOS(" +
                "ID_ANEXO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "ID_ANEXO_SERVIDOR INTEGER , " +
                "ID_PEDIDO INTEGER, " +
                "ID_PEDIDO_SERVIDOR INTEGER, " +
                "NOME_ANEXO VARCHAR(150), " +
                "ANEXO BLOB, " +
                "EXCLUIDO VARCHAR(1) DEFAULT 'N', " +
                "PRINCIPAL VARCHAR(1) DEFAULT 'N');");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CAMPANHA_COM_CLIENTES (ID_CAMPANHA_COM_CLIENTES INTEGER NOT NULL PRIMARY KEY," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_CAMPANHA INTEGER NOT NULL ," +
                " ID_CLIENTE INTEGER NOT NULL ," +
                " USUARIO_ID INTEGER NOT NULL," +
                " USUARIO_NOME VARCHAR(60)," +
                " USUARIO_DATA TIMESTAMP(19));");

        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCC_ID_CAMPANHA ON TBL_CAMPANHA_COM_CLIENTES(ID_CAMPANHA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCC_ID_CLIENTE  ON TBL_CAMPANHA_COM_CLIENTES(ID_CLIENTE)");

        } catch ( SQLException e) {
            e.printStackTrace();
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CAMPANHA_COMERCIAL_CAB (ATIVO VARCHAR(1) NOT NULL," +
                " ID_CAMPANHA INTEGER NOT NULL PRIMARY KEY," +
                " ID_TIPO_CAMPANHA INTEGER NOT NULL," +
                " ID_BASE_CAMPANHA INTEGER NOT NULL," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " DATA_INICIO DATE(10)," +
                " DATA_FIM DATE(10)," +
                " NOME_CAMPANHA VARCHAR(60)," +
                " DESCRICAO_CAMPANHA VARCHAR(300)," +
                " USUARIO_ID INTEGER NOT NULL," +
                " USUARIO_NOME VARCHAR(60)," +
                " USUARIO_DATA TIMESTAMP(19));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CAMPANHA_COMERCIAL_ITENS(ID_CAMPANHA_COMERCIAL_ITENS INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " ATIVO VARCHAR(1)," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_TIPO_CAMPANHA INTEGER NOT NULL," +
                " ID_BASE_CAMPANHA INTEGER NOT NULL," +
                " ID_CAMPANHA INTEGER NOT NULL," +
                " ID_LINHA_PRODUTO INTEGER," +
                " ID_PRODUTO_VENDA VARCHAR(20)," +
                " NOME_PRODUTO_LINHA VARCHAR(120)," +
                " QUANTIDADE_VENDA NUMERIC(12,4)," +
                " ID_PRODUTO_BONUS VARCHAR(20) NOT NULL," +
                " NOME_PRODUTO_BONUS VARCHAR(120), " +
                " QUANTIDADE_BONUS NUMERIC(12,4)," +
                " USUARIO_ID INTEGER NOT NULL," +
                " USUARIO_NOME VARCHAR(60)," +
                " USUARIO_DATA TIMESTAMP(19));");
        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCCI_ID_CAMPANHA ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_CAMPANHA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCCI_ID_LINHA    ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_LINHA_PRODUTO)");

        } catch ( SQLException e) {
            e.printStackTrace();
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_LINHA_COLECAO (ATIVO VARCHAR(1) NOT NULL," +
                " ID_LINHA_COLECAO INTEGER NOT NULL PRIMARY KEY," +
                " NOME_DESCRICAO_LINHA VARCHAR(60)," +
                " USUARIO_ID INTEGER," +
                " USUARIO_NOME VARCHAR(40)," +
                " USUARIO_DATA TIMESTAMP(19));");


        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_FOTO ( ATIVO VARCHAR(1), ID_PRODUTO VARCHAR(20), " +
                    "ID_FOTO INTEGER PRIMARY KEY, USUARIO_DATA TIMESTAMP(19), " +
                    "FOTO_ARQUIVO BLOB, FOTO_MINIATURA BLOB, FOTO_PRINCIPAL VARCHAR(1) );");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_PRINCIPAL ON TBL_PRODUTO_FOTO(FOTO_PRINCIPAL)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_ATIVO ON TBL_PRODUTO_FOTO(ATIVO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_ID_PRODUTO ON TBL_PRODUTO_FOTO(ID_PRODUTO)");
        } catch ( SQLException e) {
            e.printStackTrace();
        }


        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CONFIGURACAO (ID INTEGER PRIMARY KEY, USUARIO VARCHAR(50), SENHA VARCHAR(20), URL_IP_1 VARCHAR(50), PORTA_1 INTEGER, URL_IP_DESC_1 VARCHAR(50)," +
                    " URL_IP_2 VARCHAR(50),  PORTA_2 INTEGER, URL_IP_DESC_2 VARCHAR(50), CONEXAO_SEGURA VARCHAR(1)," +
                    " TRABALHA_COM_CAMPANHAS VARCHAR(1), TRABALHA_COM_DSCTO_REAIS VARCHAR(1),TRABALHA_COM_LIMITE_CREDITO VARCHAR(1),TRABALHA_COM_PRE_PEDIDO VARCHAR(1)," +
                    " PESQUISA_PRODUTO_POR_LINHA_GRUPO VARCHAR(1), TRABALHA_COM_UNIDADE_MANUT_ESTOQUE VARCHAR(1), TRABALHA_COM_PAGINACAO_DADOS VARCHAR(1)," +
                    " LAYOUT_VENDA_ERP INTEGER, ID_CON_PGTO_DEFAULT_APP INTEGER, TRABALHA_COM_COBRANCA VARCHAR(1), TRABALHA_COM_ICMS_ST VARCHAR(1), " +
                    " PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP CHAR(1), SINCRONIA_AUTOMATICA_APP VARCHAR(1), TRABALHA_COM_VERBA_PEDIDO VARCHAR(1), LISTA_TODOS_PEDIDOS VARCHAR(1)," +
                    " GIRA_TELA_CLIENTE CHAR(1), GIRA_TELA_PRODUTO CHAR(1) , GIRA_TELA_COBRANCA CHAR(1)," +
                    " GIRA_TELA_PED_PENDENTE CHAR(1), GIRA_TELA_PED_ENVIADO CHAR(1)," +
                    " GIRA_TELA_PEDIDO CHAR(1), CADASTRA_CLIENTE_APP CHAR(1), VISUAL_HIST_FIN_PEDIDO_APP CHAR(1)," +
                    " VISUAL_PRAZO_PGTO_PEDIDO_APP CHAR(1), VISUAL_ESTOQUE_PRODUTO_APP CHAR(1), TIME_CONN INTEGER," +
                    " TIME_READ INTEGER, TIME_WRITE INTEGER, ORDEM_LISTA_CLIENTE CHAR(1)," +
                    " ATUAL_LISTA_PED_EXPORTADO CHAR(1), CONFIRMA_SINCRONIA_DADOS CHAR(1), " +
                    " CNPJ_EMPRESA CHAR(14), MARCA_PRODUTO_JA_COMPRADO CHAR(1), PEDIDO_ENVIADO_ON_LINE_APP CHAR(1), " +
                    " VISUALIZA_GRAFICO_ON_LINE_APP CHAR(1), VISUALIZA_ULTIMO_PRECO_VENDA_APP CHAR(1), " +
                    " VISUALIZA_LEGENDA_CLIENTE CHAR(1), VISUALIZA_LEGENDA_PRODUTO CHAR(1), VISUALIZA_LEGENDA_PEDIDO CHAR(1)," +
                        " CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO CHAR(1), TRABALHA_COM_CHECKIN_APP VARCHAR(1));");
                    ///O ULTIMO CAMPO DEFINE UMA FORMA DE LISTAR TODOS OS PEDIDOS EXISTENTES, INCLUSIVE OS Q NAO FORAM FEITOS PELO VENDEDOR EM QSTAO
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PREFERENCIA (ID INTEGER PRIMARY KEY, DIAS_HIST_VENDA INTEGER, DIAS_HIST_FINANCEIRO INTEGER, DIAS_HIST_PEDIDO INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_ALERTA (ID INTEGER PRIMARY KEY, ALERTA_EMISSAO_DOC VARCHAR(1), ALERTA_VENCIMENTO_DOC VARCHAR(1), ALERTA_NOVO_APP VARCHAR(1));");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_CONDICOES_PAG (ID_CONDICAO INTEGER , ID_CADASTRO INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        /*Alerta alerta = new Alerta();
        alerta.setAlerta_novo_app("N");
        alerta.setAlerta_vencimento_doc("N");
        alerta.setAlerta_emissao_doc("N");
        inserirTBL_ALERTA(alerta);
        */
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_VISITA (" +
                    "ID_VISITA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "ID_VISITA_APP VARCHAR(38), " +
                    "ID_VISITA_SERVIDOR INTEGER, " +
                    "ID_VENDEDOR INTEGER, " +
                    "ID_PEDIDO INTEGER, " +
                    "ID_PEDIDO_APP VARCHAR(38), " +
                    "ID_PEDIDO_SERVIDOR INTEGER, " +
                    "ID_CLIENTE_SERVIDOR INTEGER, " +
                    "DATA_HORA_CHECKIN DATETIME, " +
                    "LATITUDE_CHECKIN VARCHAR(60), " +
                    "LONGITUDE_CHECKIN VARCHAR(60), " +
                    "DATA_HORA_CHECKOUT DATETIME, " +
                    "LATITUDE_CHECKOUT VARCHAR(60), " +
                    "LONGITUDE_CHECKOUT VARCHAR(60), " +
                    "FOTO_CHECKIN_1 BLOB, " +
                    "FOTO_CHECKIN_2 BLOB, " +
                    "STATUS_VISITA VARCHAR(1), " +
                    "MOTIVO_NAO_VENDA VARCHAR(50), " +
                    "ID_MOTIVO_NAO_VENDA INTEGER, " +
                    "VERSAO_APP VARCHAR(10), " +
                    "OBSERVACAO TEXT, " +
                    "EXCLUIDO VARCHAR(1) DEFAULT 'N');");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_SUB_GRUPO (ID INTEGER PRIMARY KEY, NOME_SUB_GRUPO VARCHAR(50), ATIVO VARCHAR(1));");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_GRUPO (ID INTEGER PRIMARY KEY, NOME_GRUPO VARCHAR(50), ATIVO VARCHAR(1));");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_MOEDA (ID INTEGER PRIMARY KEY, NOME_MOEDA VARCHAR(50));");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_EMPRESA_PARAMETRO (ID INTEGER PRIMARY KEY, RAZAO_SOCIAL VARCHAR(50)," +
                    "NOME_FANTASIA VARCHAR(50), EMPRESA_LOGO BLOB, EMPRESA_LOGO_MINI BLOB, EMPRESA_APP_COR VARCHAR(10 )," +
                    "TIPO_TABELA_PRECO VARCHAR(1), UTILIZA_TABELA_PRECO VARCHAR(1), TEMPO_AUTO_SINCRONIA VARCHAR(4)," +
                    "ESTOQUE_AUTO_SINCRONIA VARCHAR(1), UTILIZA_LIMITE_CREDITO VARCHAR(1), ENDERECO_UF VARCHAR(1)," +
                    "PROD_DESCRICAO_PRECO_UM VARCHAR(20), UTILIZA_COBRANCA_OFFLINE VARCHAR(1), " +
                    "UTILIZA_ICMS_ST VARCHAR(1), QTDE_MAIOR_SALDO_PED_APP VARCHAR(1), CONFIRMA_SINCRONIA_CHECKIN_APP VARCHAR(1), ID_CIDADE INTEGER );");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_SETOR ( ID INTEGER NOT NULL, ATIVO VARCHAR(1), NOME_SETOR VARCHAR(60))");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        //IMPLEMENTANDO SKU
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_SKU(ATIVO CHAR(1), ID_EMPRESA INTEGER, ID_SKU INTEGER, NOME_SKU VARCHAR(60))");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_PARAMETRO_SKU(ID_EMPRESA INTEGER, ID_SKU INTEGER, ID_PRODUTO VARCHAR(20), PRECO DECIMAL(12, 8), " +
                    "FATOR DECIMAL(12, 8), PRECO_VENDA_DOIS DECIMAL(12, 8))");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_DEPOSITO(ID_DEPOSITO INTEGER, ID_EMPRESA INTEGER, NOME_DEPOSITO VARCHAR(50)) ");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CONTAS_BANCO(ID_CONTA INTEGER, ID_EMPRESA INTEGER, NOME_CONTA VARCHAR(60)) ");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_ESPECIE(ID_ESPECIE INTEGER, ID_EMPRESA INTEGER, NOME_ESPECIE VARCHAR(60)) ");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_RESUMO (CPF_CNPJ VARCHAR(20), NOME VARCHAR(60))");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_FINANCEIRO_RESUMO (DATA_EMISSAO DATE, DATA_BAIXA DATE, DATA_VENCIMENTO DATE, DATA_COMPETENCIA DATE, " +
                    "VALOR_BRUTO DECIMAL(12, 8), VALOR_TOTAL DECIMAL(12, 8), VALOR_CREDITO DECIMAL(12, 8), VALOR_DEBITO DECIMAL(12, 8), VALOR_JUROS DECIMAL(12, 8), VALOR_DESCONTOS DECIMAL(12, 8), " +
                    "ID_CONTA INTEGER, NOME_CONTA VARCHAR(50), DEBITO_CREDITO VARCHAR(1), DOCUMENTO VARCHAR(20), PARCELA VARCHAR(10), ID_FAVORECIDO INTEGER, NOME_FAVORECIDO VARCHAR(80)," +
                    "ID_BAIRRO INTEGER, ID_CIDADE INTEGER, ID_REGIAO INTEGER, CPF_CNPJ VARCHAR(14)," +
                    "NOME_FANTASIA VARCHAR(60),ENDERECO VARCHAR(60), ENDERECO_BAIRRO VARCHAR(60), ENDERECO_NUMERO VARCHAR(20)," +
                    "ENDERECO_COMPLEMENTO VARCHAR(20), ENDERECO_UF CHAR(2), NOME_MUNICIPIO VARCHAR(50), " +
                    "ENDERECO_CEP VARCHAR(8), TELEFONE_PRINCIPAL VARCHAR(20), TELEFONE_DOIS VARCHAR(20), ATIVO VARCHAR(1), ORDEM INTEGER, DEVEDOR VARCHAR(1)," +
                    "COBRANCA_ID_STATUS INTEGER, COBRANCA_DATA_STATUS TIMESTAMP, COBRANCA_DESCRICAO_STATUS VARCHAR(60), TOTAL_COMPRAS INTEGER, DATA_ULT_COMPRA DATE )");
        } catch ( SQLException e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_BAIRRO(ID INTEGER, ID_CIDADE INTEGER, NOME_BAIRRO VARCHAR(80), ORDEM INTEGER) ");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_REGIAO(ID INTEGER, ID_CIDADE INTEGER, NOME_REGIAO VARCHAR(60), ID_VENDEDOR INTEGER) ");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_MUNICIPIO(ID INTEGER, NOME_MUNICIPIO VARCHAR(50), UF VARCHAR(2)) ");
        } catch ( SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_RECEBER_CAB(ID_WEB_RECEBER INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_EMPRESA INTEGER, " +
                    "ID_VENDEDOR INTEGER, ID_MOEDA INTEGER, STATUS CHAR(1)," +
                    "EXCLUIDO CHAR(1), VALOR DECIMAL(12, 8), " +
                    "USUARIO_ID INTEGER, USUARIO_NOME VARCHAR(60), USUARIO_DATA TIMESTAMP," +
                    "ID_CADASTRO INTEGER , DATA_VENCIMENTO DATE, ID_LOTE INTEGER, ID_ANDROID VARCHAR(50), " +
                    "DATA_REAGENDAMENTO DATE, OBSERVACOES VARCHAR(300), CHAVE_RECEBER_APP VARCHAR(38) )");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_COBRANCA_STATUS(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_STATUS INTEGER, ATIVO CHAR(1), NOME_STATUS VARCHAR(60), EXCLUIDO CHAR(1) )");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_ROTA(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_VENDEDOR INTEGER, DATA_INICIAL DATE, DATA_FINAL DATE, STATUS VARCHAR(1), QTDE_CLIENTE INTEGER, " +
                    "QTDE_PEDIDO INTEGER, VALOR_TOTAL_PEDIDO DECIMAL(12, 8), SIGLA_UF CHAR(2), ID_MUNICIPIO VARCHAR(10), ID_REGIAO INTEGER, " +
                    "ID_BAIRRO INTEGER, ORDEM_BAIRRO INTEGER )");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_ACESSIBILIDADE(ID INTEGER PRIMARY KEY AUTOINCREMENT ,  " +
                    "TAMANHO_EXTRA_FONTE INT DEFAULT 0);");
            SQLiteDatabase banco = this.getReadableDatabase();
            Cursor cursor = banco.rawQuery("SELECT * FROM TBL_ACESSIBILIDADE", null);
            if ( cursor.getCount() <= 0) {
                db.execSQL("INSERT INTO TBL_ACESSIBILIDADE (TAMANHO_EXTRA_FONTE) VALUES (0)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_LOG_SISTEMA (" +
                    "ID_LOG INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "ID_PEDIDO TEXT, " +
                    "ID_PEDIDO_APP TEXT, " +
                    "ID_WEB_PEDIDO_SERVIDOR TEXT, " +
                    "ID_VENDEDOR TEXT, " +
                    "DATA_HORA DATETIME DEFAULT (datetime('now', 'localtime')), " +
                    "TIPO_LOG TEXT, " +
                    "EVENTO TEXT, " +
                    "CLASSE TEXT, " +
                    "METODO TEXT, " +
                    "ID_PRODUTO TEXT, " +
                    "VALOR_ANTERIOR TEXT, " +
                    "VALOR_NOVO TEXT, " +
                    "ERRO_DETALHE TEXT, " +
                    "ST_ENVIADO TEXT DEFAULT 'N');");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.gc();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DELETE FROM TBL_LOGIN");
        //Alteração no codigo tratando excessao
        //Roberto caceres
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_VISITA (" +
                    "ID_VISITA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "ID_VISITA_APP VARCHAR(38), " +
                    "ID_VISITA_SERVIDOR INTEGER, " +
                    "ID_VENDEDOR INTEGER, " +
                    "ID_PEDIDO INTEGER, " +
                    "ID_PEDIDO_APP VARCHAR(38), " +
                    "ID_PEDIDO_SERVIDOR INTEGER, " +
                    "ID_CLIENTE_SERVIDOR INTEGER, " +
                    "DATA_HORA_CHECKIN DATETIME, " +
                    "LATITUDE_CHECKIN VARCHAR(60), " +
                    "LONGITUDE_CHECKIN VARCHAR(60), " +
                    "DATA_HORA_CHECKOUT DATETIME, " +
                    "LATITUDE_CHECKOUT VARCHAR(60), " +
                    "LONGITUDE_CHECKOUT VARCHAR(60), " +
                    "FOTO_CHECKIN_1 BLOB, " +
                    "FOTO_CHECKIN_2 BLOB, " +
                    "STATUS_VISITA VARCHAR(1), " +
                    "MOTIVO_NAO_VENDA VARCHAR(50), " +
                    "ID_MOTIVO_NAO_VENDA INTEGER, " +
                    "VERSAO_APP VARCHAR(10), " +
                    "OBSERVACAO TEXT, " +
                    "EXCLUIDO VARCHAR(1) DEFAULT 'N');");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_ACESSIBILIDADE(ID INTEGER PRIMARY KEY AUTOINCREMENT ,  " +
                    "TAMANHO_EXTRA_FONTE INT DEFAULT 0);");
            SQLiteDatabase banco = this.getReadableDatabase();
            Cursor cursor = banco.rawQuery("SELECT * FROM TBL_ACESSIBILIDADE", null);
            if ( cursor.getCount() <= 0) {
                db.execSQL("INSERT INTO TBL_ACESSIBILIDADE (TAMANHO_EXTRA_FONTE) VALUES (0)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PEDIDO_ANEXOS(" +
                "ID_ANEXO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "ID_ANEXO_SERVIDOR INTEGER , " +
                "ID_PEDIDO INTEGER, " +
                "ID_PEDIDO_SERVIDOR INTEGER, " +
                "NOME_ANEXO VARCHAR(150), " +
                "ANEXO BLOB, " +
                "EXCLUIDO VARCHAR(1) DEFAULT 'N', " +
                "PRINCIPAL VARCHAR(1) DEFAULT 'N');");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_ROTA(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_VENDEDOR INTEGER, DATA_INICIAL DATE, DATA_FINAL DATE, STATUS VARCHAR(1), QTDE_CLIENTE INTEGER, " +
                    "QTDE_PEDIDO INTEGER, VALOR_TOTAL_PEDIDO DECIMAL(12, 8), SIGLA_UF CHAR(2), ID_MUNICIPIO VARCHAR(10), ID_REGIAO INTEGER, " +
                    "ID_BAIRRO INTEGER, ORDEM_BAIRRO INTEGER )");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_FINANCEIRO_RESUMO (DATA_EMISSAO DATE, DATA_BAIXA DATE, DATA_VENCIMENTO DATE, DATA_COMPETENCIA DATE, " +
                    "VALOR_BRUTO DECIMAL(12, 8), VALOR_TOTAL DECIMAL(12, 8), VALOR_CREDITO DECIMAL(12, 8), VALOR_DEBITO DECIMAL(12, 8), VALOR_JUROS DECIMAL(12, 8), VALOR_DESCONTOS DECIMAL(12, 8), " +
                    "ID_CONTA INTEGER, NOME_CONTA VARCHAR(50), DEBITO_CREDITO VARCHAR(1), DOCUMENTO VARCHAR(20), PARCELA VARCHAR(10), ID_FAVORECIDO INTEGER, NOME_FAVORECIDO VARCHAR(80)," +
                    "ID_BAIRRO INTEGER, ID_CIDADE INTEGER, ID_REGIAO INTEGER, CPF_CNPJ VARCHAR(14)," +
                    "NOME_FANTASIA VARCHAR(60),ENDERECO VARCHAR(60), ENDERECO_BAIRRO VARCHAR(60), ENDERECO_NUMERO VARCHAR(20)," +
                    "ENDERECO_COMPLEMENTO VARCHAR(20), ENDERECO_UF CHAR(2), NOME_MUNICIPIO VARCHAR(50), " +
                    "ENDERECO_CEP VARCHAR(8), TELEFONE_PRINCIPAL VARCHAR(20), TELEFONE_DOIS VARCHAR(20), ATIVO VARCHAR(1), ORDEM INTEGER, DEVEDOR VARCHAR(1)," +
                    "COBRANCA_ID_STATUS INTEGER, COBRANCA_DATA_STATUS TIMESTAMP, COBRANCA_DESCRICAO_STATUS VARCHAR(60) )");
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_COBRANCA_STATUS(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ID_STATUS INTEGER, ATIVO CHAR(1), NOME_STATUS VARCHAR(60), EXCLUIDO CHAR(1) )");

            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_RECEBER_CAB (ID_WEB_RECEBER INTEGER PRIMARY KEY AUTOINCREMENT, ID_EMPRESA INTEGER, " +
                        "ID_VENDEDOR INTEGER, ID_MOEDA INTEGER, STATUS CHAR(1)," +
                        "EXCLUIDO CHAR(1), VALOR DECIMAL(12, 8), " +
                        "USUARIO_ID INTEGER, USUARIO_NOME VARCHAR(60), USUARIO_DATA TIMESTAMP," +
                        "ID_CADASTRO INTEGER, DATA_VENCIMENTO DATE, ID_LOTE INTEGER, ID_ANDROID VARCHAR(50)," +
                        "DATA_REAGENDAMENTO DATE, OBSERVACOES VARCHAR(300), CHAVE_RECEBER_APP VARCHAR(38) )");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_VENDEDOR_BONUS_RESUMO (ID_VENDEDOR INTEGER PRIMARY KEY," +
                    " ID_EMPRESA INTEGER NOT NULL," +
                    " VALOR_CREDITO DECIMAL(12, 2)," +
                    " VALOR_DEBITO DECIMAL(12, 2)," +
                    " VALOR_BONUS_CANCELADOS DECIMAL(12, 2)," +
                    " VALOR_SALDO DECIMAL(12, 2)," +
                    " DATA_ULTIMA_ATUALIZACAO DATE);");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_MUNICIPIO(ID INTEGER, NOME_MUNICIPIO VARCHAR(50), UF VARCHAR(2)) ");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_BAIRRO(ID INTEGER, ID_CIDADE INTEGER, NOME_BAIRRO VARCHAR(80), ID_REGIAO INTEGER, ORDEM INTEGER) ");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_REGIAO(ID INTEGER, ID_CIDADE INTEGER, NOME_REGIAO VARCHAR(60),  ID_VENDEDOR INTEGER) ");

            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_RESUMO (CPF_CNPJ VARCHAR(20), NOME VARCHAR(60))");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_ESPECIE(ID_ESPECIE INTEGER, ID_EMPRESA INTEGER, NOME_ESPECIE VARCHAR(60)) ");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CONTAS_BANCO(ID_CONTA INTEGER, ID_EMPRESA INTEGER, NOME_CONTA VARCHAR(60)) ");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_DEPOSITO(ID_DEPOSITO INTEGER, ID_EMPRESA INTEGER, NOME_DEPOSITO VARCHAR(50)) ");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_SKU ( ATIVO CHAR(1), ID_EMPRESA INTEGER, ID_SKU INTEGER, NOME_SKU VARCHAR(60))");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_PARAMETRO_SKU(ID_EMPRESA INTEGER, ID_SKU INTEGER, ID_PRODUTO VARCHAR(20), PRECO DECIMAL(12, 8), " +
                    "FATOR DECIMAL(12, 8), PRECO_VENDA_DOIS DECIMAL(12, 8))");

            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_SUB_GRUPO (ID INTEGER PRIMARY KEY, NOME_SUB_GRUPO VARCHAR(50), ATIVO VARCHAR(1));");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_GRUPO (ID INTEGER PRIMARY KEY, NOME_GRUPO VARCHAR(50), ATIVO VARCHAR(1));");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_MOEDA (ID INTEGER PRIMARY KEY, NOME_MOEDA VARCHAR(50));");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_EMPRESA_PARAMETRO (ID INTEGER PRIMARY KEY, RAZAO_SOCIAL VARCHAR(50)," +
                    "NOME_FANTASIA VARCHAR(50), EMPRESA_LOGO BLOB, EMPRESA_LOGO_MINI BLOB, EMPRESA_APP_COR VARCHAR(10 )," +
                    "TIPO_TABELA_PRECO VARCHAR(1), UTILIZA_TABELA_PRECO VARCHAR(1), TEMPO_AUTO_SINCRONIA VARCHAR(4)," +
                    "ESTOQUE_AUTO_SINCRONIA VARCHAR(1), UTILIZA_LIMITE_CREDITO VARCHAR(1), ENDERECO_UF VARCHAR(1)," +
                    "PROD_DESCRICAO_PRECO_UM VARCHAR(20), UTILIZA_COBRANCA_OFFLINE VARCHAR(1), " +
                    "UTILIZA_ICMS_ST VARCHAR(1), QTDE_MAIOR_SALDO_PED_APP VARCHAR(1), CONFIRMA_SINCRONIA_CHECKIN_APP VARCHAR(1), ID_CIDADE INTEGER);");

            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_CONDICOES_PAG (ID_CONDICAO INTEGER , ID_CADASTRO INTEGER);");
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO_SETOR ( ID INTEGER NOT NULL, ATIVO VARCHAR(1), NOME_SETOR VARCHAR(60))");
        } catch (SQLException e) {
        } catch (Exception e) {
        }



        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "EMAIL_VENDEDOR"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN EMAIL_VENDEDOR VARCHAR(50) ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", " USUARIO_ALTERA_CLIENTE_PED")) {
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_ALTERA_CLIENTE_PED VARCHAR(1) ;");
                db.execSQL("UPDATE TBL_WEB_USUARIO SET USUARIO_ALTERA_CLIENTE_PED = 'S'");
                db.execSQL("DELETE FROM TBL_LOGIN");
            }
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "APARELHO_LINK_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN APARELHO_LINK_APP VARCHAR(200) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "APARELHO_KEY_FIREBASE"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN APARELHO_KEY_FIREBASE VARCHAR(200) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "APARELHO_VERSAO_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN APARELHO_VERSAO_APP VARCHAR(10) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "APARELHO_NOTIFICA_UPDATE_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN APARELHO_NOTIFICA_UPDATE_APP VARCHAR(1) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "APARELHO_NOTIFICA_STATUS_PED"))
            db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN APARELHO_NOTIFICA_STATUS_PED VARCHAR(1) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "APARELHO_NOTIFICA_VENCTO_FIN"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN APARELHO_NOTIFICA_VENCTO_FIN VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_TIPO"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_TIPO VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_PERMITE_N_LOGINS"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_PERMITE_N_LOGINS VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_PERMITE_BIOMETRIA"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_PERMITE_BIOMETRIA VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_AVALIACAO_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_AVALIACAO_APP INTEGER;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_EDITA_PRECO_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_EDITA_PRECO_APP VARCHAR(1);");
        } catch (SQLException e) {
        }
        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_UF"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_UF VARCHAR(2);");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_USUARIO", "USUARIO_ID_CIDADE"))
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN USUARIO_ID_CIDADE INTEGER;");
        } catch (SQLException e) {
        }


        try {
            if (!isExiste(db, "TBL_PRODUTO", "NOME_GRUPO"))
                db.execSQL("ALTER TABLE TBL_PRODUTO ADD COLUMN NOME_GRUPO VARCHAR(60) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_PRODUTO", "TRAVAR_FAT_SEM_ESTOQUE"))
                db.execSQL("ALTER TABLE TBL_PRODUTO ADD COLUMN TRAVAR_FAT_SEM_ESTOQUE VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_PRODUTO", "UTILIZA_TABELA_PRECO"))
                db.execSQL("ALTER TABLE TBL_PRODUTO ADD COLUMN UTILIZA_TABELA_PRECO VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_PRODUTO", "PRODUTO_EDITA_PRECO_APP"))
                db.execSQL("ALTER TABLE TBL_PRODUTO ADD COLUMN PRODUTO_EDITA_PRECO_APP VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_PRODUTO", "PRODUTO_ST_ALIQUOTA"))
                db.execSQL("ALTER TABLE TBL_PRODUTO ADD COLUMN PRODUTO_ST_ALIQUOTA DECIMAL(12,8) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_PRODUTO", "PRODUTO_SUJ_ICMS_ST"))
                db.execSQL("ALTER TABLE TBL_PRODUTO ADD COLUMN PRODUTO_SUJ_ICMS_ST VARCHAR(1) ;");
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_CONDICOES_PAG_CAB", "DESCONTO_PERC"))
                db.execSQL("ALTER TABLE TBL_CONDICOES_PAG_CAB ADD COLUMN DESCONTO_PERC DECIMAL(12,2) ;");
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_CADASTRO", "ID_CLIENTE_APP_FDB"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN ID_CLIENTE_APP_FDB VARCHAR (38);");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "ID_TABELA_PRECO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN ID_TABELA_PRECO INTEGER ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_TABELA_PRECO_ITENS", "VALOR_UNITARIO"))
                db.execSQL("ALTER TABLE TBL_TABELA_PRECO_ITENS ADD COLUMN VALOR_UNITARIO DECIMAL(12, 4) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_TABELA_PRECO_ITENS", "ID_PRODUTO"))
                db.execSQL("ALTER TABLE TBL_TABELA_PRECO_ITENS ADD COLUMN ID_PRODUTO VARCHAR(20) ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_CADASTRO", "UTILIZA_TABELA_PRECO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN UTILIZA_TABELA_PRECO VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "FINANCEIRO_VENCIDO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN FINANCEIRO_VENCIDO VARCHAR(20) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "FINANCEIRO_VENCENDO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN FINANCEIRO_VENCENDO VARCHAR(20) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "TIPO_TABELA_PRECO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN TIPO_TABELA_PRECO VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "PEDIDO_ANALISE"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN PEDIDO_ANALISE VARCHAR(20) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "LATITUDE"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN LATITUDE VARCHAR(60) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "LONGITUDE"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN LONGITUDE VARCHAR(60) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "ROTA_SEQUENCIA"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN ROTA_SEQUENCIA INTEGER ;");
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_CADASTRO", "ID_REGIAO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN ID_REGIAO INTEGER ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO", "ID_BAIRRO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO ADD COLUMN ID_BAIRRO INTEGER ;");
        } catch (Exception e) {
        }

        //ID_BAIRRO INTEGER

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ANALISE_ESTOQ_EFETUADA")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ANALISE_ESTOQ_EFETUADA VARCHAR(1) ;");
                db.execSQL("UPDATE TBL_WEB_PEDIDO SET ANALISE_ESTOQ_EFETUADA = 'N'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ANALISE_ESTOQ_DATA_HORA")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ANALISE_ESTOQ_DATA_HORA TIMESTAMP ;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ID_PEDIDO_APP_FDB")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ID_PEDIDO_APP_FDB VARCHAR(38) ;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            verificarEAdicionarCampoVisitaPedido(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "COPIA_SEGURA")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN COPIA_SEGURA VARCHAR(1) ;");
                db.execSQL("UPDATE TBL_WEB_PEDIDO SET COPIA_SEGURA = 'N'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ID_MOEDA_PADRAO"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ID_MOEDA_PADRAO INTEGER ;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_PREFERENCIA", "DIAS_HIST_PEDIDO"))
                db.execSQL("ALTER TABLE TBL_PREFERENCIA ADD COLUMN DIAS_HIST_PEDIDO INTEGER ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        //CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO   12-08-2026 Usado qdo altera o prazo de pagamento
        //VISUALIZA_ULTIMO_PRECO_VENDA_APP
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_CHECKIN_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_CHECKIN_APP VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_CHECKIN_APP = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "VISUALIZA_ULTIMO_PRECO_VENDA_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUALIZA_ULTIMO_PRECO_VENDA_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET VISUALIZA_ULTIMO_PRECO_VENDA_APP = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "VISUALIZA_GRAFICO_ON_LINE_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUALIZA_GRAFICO_ON_LINE_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET VISUALIZA_GRAFICO_ON_LINE_APP = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "PEDIDO_ENVIADO_ON_LINE_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN PEDIDO_ENVIADO_ON_LINE_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET PEDIDO_ENVIADO_ON_LINE_APP = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "VISUALIZA_LEGENDA_CLIENTE")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUALIZA_LEGENDA_CLIENTE CHAR(1);");
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUALIZA_LEGENDA_PRODUTO CHAR(1);");
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUALIZA_LEGENDA_PEDIDO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET VISUALIZA_LEGENDA_CLIENTE = 'N', VISUALIZA_LEGENDA_PRODUTO = 'N', VISUALIZA_LEGENDA_PEDIDO = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "MARCA_PRODUTO_JA_COMPRADO")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN MARCA_PRODUTO_JA_COMPRADO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET MARCA_PRODUTO_JA_COMPRADO = 'S'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "CONFIRMA_SINCRONIA_DADOS")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CONFIRMA_SINCRONIA_DADOS CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET CONFIRMA_SINCRONIA_DADOS = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "CNPJ_EMPRESA")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CNPJ_EMPRESA VARCHAR(14);");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_PRE_PEDIDO"))
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_PRE_PEDIDO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "PESQUISA_PRODUTO_POR_LINHA_GRUPO")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN PESQUISA_PRODUTO_POR_LINHA_GRUPO VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET PESQUISA_PRODUTO_POR_LINHA_GRUPO = 'G'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_PAGINACAO_DADOS")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_PAGINACAO_DADOS VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_PAGINACAO_DADOS = 'N'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_DSCTO_REAIS"))
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_DSCTO_REAIS VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_LIMITE_CREDITO"))
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_LIMITE_CREDITO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_CONFIRMACAO_PRAZO")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_CONFIRMACAO_PRAZO VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_CONFIRMACAO_PRAZO = 'N'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_UNIDADE_MANUT_ESTOQUE")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_UNIDADE_MANUT_ESTOQUE VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_UNIDADE_MANUT_ESTOQUE = 'N'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_COBRANCA")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_COBRANCA VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_COBRANCA = 'N'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "LAYOUT_VENDA_ERP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN LAYOUT_VENDA_ERP INTEGER;");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET LAYOUT_VENDA_ERP = 0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "ID_CON_PGTO_DEFAULT_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN ID_CON_PGTO_DEFAULT_APP INTEGER;");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET ID_CON_PGTO_DEFAULT_APP = 0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_ICMS_ST")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_ICMS_ST VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_ICMS_ST = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "SINCRONIA_AUTOMATICA_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN SINCRONIA_AUTOMATICA_APP VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET SINCRONIA_AUTOMATICA_APP = 'S'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TRABALHA_COM_VERBA_PEDIDO")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_VERBA_PEDIDO VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TRABALHA_COM_VERBA_PEDIDO = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "LISTA_TODOS_PEDIDOS")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN LISTA_TODOS_PEDIDOS VARCHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET LISTA_TODOS_PEDIDOS = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "CADASTRA_CLIENTE_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CADASTRA_CLIENTE_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET CADASTRA_CLIENTE_APP = 'S'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "TIME_CONN")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TIME_CONN INTEGER;");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TIME_CONN = 10");
            }
            if (!isExiste(db, "TBL_CONFIGURACAO", "TIME_READ")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TIME_READ INTEGER;");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TIME_READ = 180");
            }
            if (!isExiste(db, "TBL_CONFIGURACAO", "TIME_WRITE")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TIME_WRITE INTEGER;");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET TIME_WRITE = 180");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //TIME_CONN INTEGER, TIME_READ INTEGER, TIME_WRITE INTEGER

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "VISUAL_HIST_FIN_PEDIDO_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUAL_HIST_FIN_PEDIDO_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET VISUAL_HIST_FIN_PEDIDO_APP = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "VISUAL_PRAZO_PGTO_PEDIDO_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUAL_PRAZO_PGTO_PEDIDO_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET VISUAL_PRAZO_PGTO_PEDIDO_APP = 'S'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "VISUAL_ESTOQUE_PRODUTO_APP")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUAL_ESTOQUE_PRODUTO_APP CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET VISUAL_ESTOQUE_PRODUTO_APP = 'S'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "ORDEM_LISTA_CLIENTE")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN ORDEM_LISTA_CLIENTE CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET ORDEM_LISTA_CLIENTE = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_CONFIGURACAO", "ATUAL_LISTA_PED_EXPORTADO")) {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN ATUAL_LISTA_PED_EXPORTADO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET ATUAL_LISTA_PED_EXPORTADO = 'N'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PEDIDO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET GIRA_TELA_PEDIDO = 'N'");

                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_CLIENTE CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET GIRA_TELA_CLIENTE = 'N'");

                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PRODUTO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET GIRA_TELA_PRODUTO = 'N'");

                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_COBRANCA CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET GIRA_TELA_COBRANCA = 'N'");

                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PED_PENDENTE CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET GIRA_TELA_PED_PENDENTE = 'N'");

                db.execSQL("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PED_ENVIADO CHAR(1);");
                db.execSQL("UPDATE TBL_CONFIGURACAO SET GIRA_TELA_PED_ENVIADO = 'N'");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ID_OUTRO_VENDEDOR"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ID_OUTRO_VENDEDOR INTEGER;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "DESCONTO_INDEVIDO"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN DESCONTO_INDEVIDO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ID_PEDIDO_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ID_PEDIDO_APP VARCHAR(38);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "PRAZO_CONFIRMADO")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN PRAZO_CONFIRMADO VARCHAR(1);");
                db.execSQL("UPDATE TBL_WEB_PEDIDO SET PRAZO_CONFIRMADO = 'S'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "VALOR_RECIBO")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN  VALOR_RECIBO DECIMAL(12, 2);");
                db.execSQL("UPDATE TBL_WEB_PEDIDO SET VALOR_RECIBO = '0.00'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "VALOR_DESCONTO_ADIC"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN VALOR_DESCONTO_ADIC DECIMAL(12, 8) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "PERC_DESCONTO_ADIC"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN PERC_DESCONTO_ADIC DECIMAL(12, 8) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "CLIENTE_NOVO"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN CLIENTE_NOVO VARCHAR(1) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "VALOR_ICMS_ST"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN VALOR_ICMS_ST DECIMAL(12,8) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", " QTDE_ITENS"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN  QTDE_ITENS VARCHAR(5) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ID_GRUPO")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ID_GRUPO INTEGER;");
                db.execSQL("UPDATE  TBL_WEB_PEDIDO_ITENS SET ID_GRUPO = 0");
            }
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ID_SUB_GRUPO")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ID_SUB_GRUPO INTEGER;");
                db.execSQL("UPDATE  TBL_WEB_PEDIDO_ITENS SET ID_SUB_GRUPO = 0");
            }

        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ANALISE_ESTOQ_RESULTADO"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ANALISE_ESTOQ_RESULTADO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "PRODUTO_PRECO_EDITADO"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN PRODUTO_PRECO_EDITADO DECIMAL(12, 4);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ICMS_ST_VALOR"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ICMS_ST_VALOR DECIMAL(12, 8);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ICMS_ST_PERCENTUAL"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ICMS_ST_PERCENTUAL DECIMAL(12, 8);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ICMS_ST_CALCULA"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ICMS_ST_CALCULA VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }



        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "ID_SKU"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN ID_SKU INTEGER;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "NOME_SKU"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN NOME_SKU VARCHAR(60) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "VALOR_DESCONTO_ADIC"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN VALOR_DESCONTO_ADIC DECIMAL(12, 8) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "PERC_DESCONTO_ADIC"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN PERC_DESCONTO_ADIC DECIMAL(12, 8) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO_ITENS", "PERC_DESCONTO_ADIC"))
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO_ITENS ADD COLUMN PERC_DESCONTO_ADIC DECIMAL(12, 8) ;");
        } catch (SQLException e) {
        } catch (Exception e) {
        }



        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "CONFIRMA_SINCRONIA_CHECKIN_APP")) {
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN CONFIRMA_SINCRONIA_CHECKIN_APP VARCHAR(1);");
                db.execSQL("UPDATE TBL_EMPRESA_PARAMETRO SET CONFIRMA_SINCRONIA_CHECKIN_APP = 'N'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "TIPO_TABELA_PRECO"))
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN TIPO_TABELA_PRECO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "UTILIZA_TABELA_PRECO"))
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN UTILIZA_TABELA_PRECO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "TEMPO_AUTO_SINCRONIA"))
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN TEMPO_AUTO_SINCRONIA VARCHAR(4);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "ESTOQUE_AUTO_SINCRONIA"))
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN ESTOQUE_AUTO_SINCRONIA VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "ENDERECO_UF"))
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN ENDERECO_UF VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "ENDERECO_UF"))
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN ENDERECO_UF VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "PROD_DESCRICAO_PRECO_UM")) {
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN PROD_DESCRICAO_PRECO_UM VARCHAR(20);");
                db.execSQL("UPDATE TBL_EMPRESA_PARAMETRO SET PROD_DESCRICAO_PRECO_UM = ''");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "UTILIZA_COBRANCA_OFFLINE")) {
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN UTILIZA_COBRANCA_OFFLINE VARCHAR(1);");
                db.execSQL("UPDATE TBL_EMPRESA_PARAMETRO SET UTILIZA_COBRANCA_OFFLINE = 'S'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }


        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "ID_CIDADE")) {
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN ID_CIDADE INTEGER;");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "UTILIZA_ICMS_ST")) {
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN  UTILIZA_ICMS_ST VARCHAR(1);");
                db.execSQL("UPDATE TBL_EMPRESA_PARAMETRO SET UTILIZA_ICMS_ST = 'N'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            if (!isExiste(db, "TBL_EMPRESA_PARAMETRO", "QTDE_MAIOR_SALDO_PED_APP")) {
                db.execSQL("ALTER TABLE TBL_EMPRESA_PARAMETRO ADD COLUMN  QTDE_MAIOR_SALDO_PED_APP VARCHAR(1);");
                db.execSQL("UPDATE TBL_EMPRESA_PARAMETRO SET  QTDE_MAIOR_SALDO_PED_APP = 'S'");
            }
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO_CATEGORIA", "MULTI_DEVICE_ACEITA_NOVO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO_CATEGORIA ADD COLUMN MULTI_DEVICE_ACEITA_NOVO VARCHAR(1) ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_CADASTRO_CATEGORIA", "NOME_MULTI_DISPOSITIVO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO_CATEGORIA ADD COLUMN  NOME_MULTI_DISPOSITIVO VARCHAR(60) ;");
        } catch (Exception e) {
        }

        //IMPLEMENTANDO COBRANÇAS

        try {
            if (!isExiste(db, "TBL_CADASTRO_REGIAO", "ID_VENDEDOR"))
                db.execSQL("ALTER TABLE TBL_CADASTRO_REGIAO ADD COLUMN ID_VENDEDOR INTEGER ;");
        } catch (Exception e) {
        }


        //TOTAL_COMPRAS INTEGER, DATA_ULT_COMPRA DATE

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "TOTAL_COMPRAS"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN TOTAL_COMPRAS INTEGER ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "DATA_ULT_COMPRA"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN DATA_ULT_COMPRA DATE ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "ID_REGIAO"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN ID_REGIAO INTEGER ;");
        } catch (Exception e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "ID_FAVORECIDO"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN ID_FAVORECIDO INTEGER ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "CPF_CNPJ"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN CPF_CNPJ VARCHAR(14) ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "TELEFONE_DOIS"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN TELEFONE_DOIS VARCHAR(20) ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "ATIVO"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN ATIVO VARCHAR(1) ;");
        } catch (SQLException e) {
        }


        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "ORDEM"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN ORDEM INTEGER ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "DEVEDOR"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN DEVEDOR VARCHAR(1) ;");
        } catch (SQLException e) {
        }



        try {
            if (!isExiste(db, "TBL_WEB_RECEBER_CAB", "ID_ANDROID"))
                db.execSQL("ALTER TABLE TBL_WEB_RECEBER_CAB ADD COLUMN ID_ANDROID VARCHAR(50) ;");
        } catch (SQLException e) {
        }


        try {
            if (!isExiste(db, "TBL_WEB_RECEBER_CAB", "DATA_REAGENDAMENTO"))
                db.execSQL("ALTER TABLE TBL_WEB_RECEBER_CAB ADD COLUMN DATA_REAGENDAMENTO DATE ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_WEB_RECEBER_CAB", "OBSERVACOES"))
                db.execSQL("ALTER TABLE TBL_WEB_RECEBER_CAB ADD COLUMN OBSERVACOES VARCHAR(300) ;");
        } catch (SQLException e) {
        }


        try {
            if (!isExiste(db, "TBL_WEB_RECEBER_CAB", "CHAVE_RECEBER_APP"))
                db.execSQL("ALTER TABLE TBL_WEB_RECEBER_CAB ADD COLUMN CHAVE_RECEBER_APP VARCHAR(38) ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_LOGIN", "USUARIO_LEMBRETE_SINCRONIA")) {
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN USUARIO_LEMBRETE_SINCRONIA CHAR(1) ;");
                db.execSQL("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'N'");
            }
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_LOGIN", "USUARIO_LEMBRETE_SINCRONIA_DATA")) {
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN USUARIO_LEMBRETE_SINCRONIA_DATA DATE ;");
                db.execSQL("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA_DATA = ''");
            }
        } catch (SQLException e) {
        }
        try {
            if (!isExiste(db, "TBL_LOGIN", "DATA_SINCRONIA_CLIENTE"))
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN DATA_SINCRONIA_CLIENTE TIMESTAMP ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_LOGIN", "DATA_SINCRONIA_IMAGEN"))
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN DATA_SINCRONIA_IMAGEN TIMESTAMP;");
        } catch (SQLException e) {
        }
        try {
            if (!isExiste(db, "TBL_LOGIN", "DATA_SINCRONIA_COBRANCA"))
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN DATA_SINCRONIA_COBRANCA TIMESTAMP;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_LOGIN", "USUARIO_ID_CIDADE"))
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN USUARIO_ID_CIDADE INTEGER ;");
        } catch (SQLException e) {
        }
        try {
            if (!isExiste(db, "TBL_LOGIN", "USUARIO_UF"))
                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN USUARIO_UF VARCHAR(2);");
        } catch (SQLException e) {
        }

        //"USUARIO_UF VARCHAR(2)," +
        //        "USUARIO_ID_CIDADE INTEGER
        try {
            if (!isExiste(db, "TBL_CADASTRO_BAIRRO", "ORDEM"))
                db.execSQL("ALTER TABLE TBL_CADASTRO_BAIRRO ADD COLUMN  ORDEM INTEGER ;");
        } catch (SQLException e) {
        }
        try {
            if (!isExiste(db, "TBL_CADASTRO_BAIRRO", "ID_REGIAO"))
                db.execSQL("ALTER TABLE TBL_CADASTRO_BAIRRO ADD COLUMN  ID_REGIAO INTEGER ;");
        } catch (SQLException e) {
        }

        try {
            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "COBRANCA_ID_STATUS"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN  COBRANCA_ID_STATUS INTEGER ;");

            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "COBRANCA_DATA_STATUS"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN  COBRANCA_DATA_STATUS TIMESTAMP ;");

            if (!isExiste(db, "TBL_FINANCEIRO_RESUMO", "COBRANCA_DESCRICAO_STATUS"))
                db.execSQL("ALTER TABLE TBL_FINANCEIRO_RESUMO ADD COLUMN  COBRANCA_DESCRICAO_STATUS VARCHAR(60) ;");
        } catch (SQLException e) {
        }


      //  "COBRANCA_ID_STATUS CHAR(1), COBRANCA_DATA_STATUS TIMESTAMP, COBRANCA_DESCRICAO_STATUS VARCHAR(60) )");


        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TBL_LOG_SISTEMA (" +
                    "ID_LOG INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "ID_PEDIDO TEXT, " +
                    "ID_PEDIDO_APP TEXT, " +
                    "ID_WEB_PEDIDO_SERVIDOR TEXT, " +
                    "ID_VENDEDOR TEXT, " +
                    "DATA_HORA DATETIME DEFAULT (datetime('now', 'localtime')), " +
                    "TIPO_LOG TEXT, " +
                    "EVENTO TEXT, " +
                    "CLASSE TEXT, " +
                    "METODO TEXT, " +
                    "ID_PRODUTO TEXT, " +
                    "VALOR_ANTERIOR TEXT, " +
                    "VALOR_NOVO TEXT, " +
                    "ERRO_DETALHE TEXT, " +
                    "ST_ENVIADO TEXT DEFAULT 'N');");

            // GARANTE QUE AS COLUNAS NOVAS EXISTAM CASO A TABELA TENHA SIDO CRIADA ANTES
            if (!isExiste(db, "TBL_LOG_SISTEMA", "ID_PEDIDO_APP"))
                db.execSQL("ALTER TABLE TBL_LOG_SISTEMA ADD COLUMN ID_PEDIDO_APP TEXT;");
            if (!isExiste(db, "TBL_LOG_SISTEMA", "ID_WEB_PEDIDO_SERVIDOR"))
                db.execSQL("ALTER TABLE TBL_LOG_SISTEMA ADD COLUMN ID_WEB_PEDIDO_SERVIDOR TEXT;");
            if (!isExiste(db, "TBL_LOG_SISTEMA", "ID_VENDEDOR"))
                db.execSQL("ALTER TABLE TBL_LOG_SISTEMA ADD COLUMN ID_VENDEDOR TEXT;");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        onCreateIndex(db);
        /*
        try {
            db.execSQL("CREATE INDEX IDX_WPD_ID_PEDIDO  ON TBL_WEB_PEDIDO(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IDX_WPD_ENVIADO    ON TBL_WEB_PEDIDO(PEDIDO_ENVIADO)");
            db.execSQL("CREATE INDEX IDX_WPD_FINALIZADO ON TBL_WEB_PEDIDO(FINALIZADO)");
            db.execSQL("CREATE INDEX IDX_WPD_USUARIO    ON TBL_WEB_PEDIDO(USUARIO_LANCAMENTO_ID)");

            db.execSQL("CREATE INDEX IDX_PFOTO_ID_PRODUTO ON TBL_PRODUTO_FOTO(ID_PRODUTO)");

            db.execSQL("CREATE INDEX IDX_PROD_ID_GRUPO ON TBL_PRODUTO(ID_GRUPO)");
            db.execSQL("CREATE INDEX IDX_PROD_ID_SUB_GRUPO ON TBL_PRODUTO(ID_SUB_GRUPO)");

            db.execSQL("CREATE INDEX IDX_CAD_ID_SERVIDOR ON TBL_CADASTRO(ID_CADASTRO_SERVIDOR)");

            db.execSQL("CREATE INDEX IDX_TCCI_ID_CAMPANHA ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_CAMPANHA)");
            db.execSQL("CREATE INDEX IDX_TCCI_ID_LINHA    ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_LINHA_PRODUTO)");

            db.execSQL("CREATE INDEX IDX_TCC_ID_CAMPANHA ON TBL_CAMPANHA_COM_CLIENTES(ID_CAMPANHA)");
            db.execSQL("CREATE INDEX IDX_TCC_ID_CLIENTE  ON TBL_CAMPANHA_COM_CLIENTES(ID_CLIENTE)");

            db.execSQL("CREATE INDEX IDX_WPI_ID_PEDIDO ON TBL_WEB_PEDIDO_ITENS(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IDX_WPI_ID_PRODUTO ON TBL_WEB_PEDIDO_ITENS(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IDX_TPI_ID_TABELA ON TBL_TABELA_PRECO_ITENS(ID_TABELA)");
            db.execSQL("CREATE INDEX IDX_TPI_ID_PRODUTO ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO)");
        } catch ( SQLException e) {
            e.printStackTrace();
        }

         */
        //try {
            //db.execSQL("DROP INDEX IF EXISTS IDX_CAD_ID_SERVIDOR");
        //} catch ( SQLException e) {
           // e.printStackTrace();
        //}
    }
    private boolean isExiste(SQLiteDatabase db, String tabela, String column) {
        String sql = "SELECT COUNT(*) FROM PRAGMA_TABLE_INFO('" + tabela + "') WHERE NAME = '" + column + "';";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) >= 1)
            return true;
        return false;
    }

    public void verificarEAdicionarCampoVisitaPedido(SQLiteDatabase db) {
        try {
            if (!isExiste(db, "TBL_WEB_PEDIDO", "ID_VISITA_APP")) {
                db.execSQL("ALTER TABLE TBL_WEB_PEDIDO ADD COLUMN ID_VISITA_APP VARCHAR(38);");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onDropTable( String nomeTabela) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DROP TABLE IF EXISTS " + nomeTabela);
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onDeleteIndex(SQLiteDatabase db){
        try {
            db.execSQL("DROP INDEX IF EXISTS IDX_WPD_ID_CADASTRO");
            db.execSQL("DROP INDEX IF EXISTS IDX_TPC_ID_TABELA");
            db.execSQL("DROP INDEX IF EXISTS IDX_TCCI_ID_P_VENDA");
            db.execSQL("DROP INDEX IF EXISTS IDX_PROD_C_BARRAS");
            db.execSQL("DROP INDEX IF EXISTS IDX_P_GRUPO_ATIVO");
            db.execSQL("DROP INDEX IF EXISTS IDX_P_GRUPO_NOME");
            db.execSQL("DROP INDEX IF EXISTS IDX_PFOTO_PRINCIPAL");
            db.execSQL("DROP INDEX IF EXISTS IDX_PFOTO_ATIVO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PFOTO_ID_PRODUTO");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPD_ID_WEB_PED_SRV");
            db.execSQL("DROP INDEX IF EXISTS IDX_P_CAD_CLI_NOME_CAD");
            db.execSQL("DROP INDEX IF EXISTS IDX_P_CAD_CLI_NOME_FANT");
            db.execSQL("DROP INDEX IF EXISTS IDX_P_CAD_CLI_CPF_CNPJ");
            db.execSQL("DROP INDEX IF EXISTS IDX_P_CAD_CLI_TEL_PRIN");
            db.execSQL("DROP INDEX IF EXISTS IDX_PROD_ATIVO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PROD_EXCLUIDO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PROD_NOME_PRODUTO");
            db.execSQL("DROP INDEX IF EXISTS IDX_CAD_CLI_FINALIZADO");
            db.execSQL("DROP INDEX IF EXISTS IDX_CAD_CLI_ID_VENDEDOR");
            db.execSQL("DROP INDEX IF EXISTS IDX_PCL_ID_PPROMOCAO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PCL_ID_CADASTRO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PCB_ID_PPROMOCAO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PPD_ID_PROMOCAO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PPD_ID_PRODUTO");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPD_ID_PEDIDO");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPD_ENVIADO");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPD_FINALIZADO");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPD_USUARIO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PROD_ID_GRUPO");
            db.execSQL("DROP INDEX IF EXISTS IDX_PROD_ID_SUB_GRUPO");
            db.execSQL("DROP INDEX IF EXISTS IDX_CAD_ID_SERVIDOR");
            db.execSQL("DROP INDEX IF EXISTS IDX_TCCI_ID_CAMPANHA");
            db.execSQL("DROP INDEX IF EXISTS IDX_TCCI_ID_LINHA");
            db.execSQL("DROP INDEX IF EXISTS IDX_TCC_ID_CAMPANHA");
            db.execSQL("DROP INDEX IF EXISTS IDX_TCC_ID_CLIENTE");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPI_ID_PEDIDO");
            db.execSQL("DROP INDEX IF EXISTS IDX_WPI_ID_PRODUTO");
            db.execSQL("DROP INDEX IF EXISTS IDX_TPI_ID_TABELA");
            db.execSQL("DROP INDEX IF EXISTS IDX_TPI_ID_PRODUTO");
            db.execSQL("DROP INDEX IF EXISTS IDX_TPI_PROD_TABELA");
            db.execSQL("DROP INDEX IF EXISTS IDX_WEB_PEDIDO_ITENS_COMPOSTO");
            db.execSQL("DROP INDEX IF EXISTS IDX_VISITA_ID_APP");
            db.execSQL("DROP INDEX IF EXISTS IDX_VISITA_CLIENTE_SRV");
            db.execSQL("DROP INDEX IF EXISTS IDX_VISITA_VENDEDOR");
            db.execSQL("DROP INDEX IF EXISTS IDX_VISITA_PEDIDO");
            db.execSQL("DROP INDEX IF EXISTS IDX_VISITA_STATUS");
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean reconstruirTodosIndices() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            onDeleteIndex(db);
            onCreateIndex(db);
            db.execSQL("ANALYZE;");
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db.inTransaction()) {
                db.endTransaction();
            }
        }
    }

    public void onCreateIndex(SQLiteDatabase db){

        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_ID_CADASTRO ON TBL_WEB_PEDIDO(ID_CADASTRO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPC_ID_TABELA   ON TBL_TABELA_PRECO_CAB(ID_TABELA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCCI_ID_P_VENDA ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_PRODUTO_VENDA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_C_BARRAS   ON TBL_PRODUTO(CODIGO_EM_BARRAS)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_P_GRUPO_ATIVO ON TBL_PRODUTO_GRUPO(ATIVO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_P_GRUPO_NOME ON TBL_PRODUTO_GRUPO(NOME_GRUPO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_PRINCIPAL ON TBL_PRODUTO_FOTO(FOTO_PRINCIPAL)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_ATIVO ON TBL_PRODUTO_FOTO(ATIVO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_ID_PRODUTO ON TBL_PRODUTO_FOTO(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_ID_WEB_PED_SRV ON TBL_WEB_PEDIDO(ID_WEB_PEDIDO_SERVIDOR)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_P_CAD_CLI_NOME_CAD ON TBL_CADASTRO(NOME_CADASTRO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_P_CAD_CLI_NOME_FANT ON TBL_CADASTRO(NOME_FANTASIA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_P_CAD_CLI_CPF_CNPJ      ON TBL_CADASTRO(CPF_CNPJ)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_P_CAD_CLI_TEL_PRIN ON TBL_CADASTRO(TELEFONE_PRINCIPAL)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_ATIVO    ON TBL_PRODUTO(ATIVO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_EXCLUIDO ON TBL_PRODUTO(EXCLUIDO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_NOME_PRODUTO ON TBL_PRODUTO(NOME_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_CAD_CLI_FINALIZADO ON TBL_CADASTRO(FINALIZADO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_CAD_CLI_ID_VENDEDOR ON TBL_CADASTRO(ID_VENDEDOR)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PCL_ID_PPROMOCAO ON TBL_PROMOCAO_CLIENTE(ID_PROMOCAO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PCL_ID_CADASTRO  ON TBL_PROMOCAO_CLIENTE(ID_CADASTRO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PCB_ID_PPROMOCAO ON TBL_PROMOCAO_CAB(ID_PROMOCAO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PPD_ID_PROMOCAO  ON TBL_PROMOCAO_PRODUTO(ID_PROMOCAO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PPD_ID_PRODUTO   ON TBL_PROMOCAO_PRODUTO(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_ID_PEDIDO  ON TBL_WEB_PEDIDO(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_ENVIADO    ON TBL_WEB_PEDIDO(PEDIDO_ENVIADO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_FINALIZADO ON TBL_WEB_PEDIDO(FINALIZADO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPD_USUARIO    ON TBL_WEB_PEDIDO(USUARIO_LANCAMENTO_ID)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PFOTO_ID_PRODUTO ON TBL_PRODUTO_FOTO(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_ID_GRUPO     ON TBL_PRODUTO(ID_GRUPO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_PROD_ID_SUB_GRUPO ON TBL_PRODUTO(ID_SUB_GRUPO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_CAD_ID_SERVIDOR ON TBL_CADASTRO(ID_CADASTRO_SERVIDOR)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCCI_ID_CAMPANHA ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_CAMPANHA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCCI_ID_LINHA    ON TBL_CAMPANHA_COMERCIAL_ITENS(ID_LINHA_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCC_ID_CAMPANHA ON TBL_CAMPANHA_COM_CLIENTES(ID_CAMPANHA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TCC_ID_CLIENTE  ON TBL_CAMPANHA_COM_CLIENTES(ID_CLIENTE)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPI_ID_PEDIDO  ON TBL_WEB_PEDIDO_ITENS(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WPI_ID_PRODUTO ON TBL_WEB_PEDIDO_ITENS(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_TABELA  ON TBL_TABELA_PRECO_ITENS(ID_TABELA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_PRODUTO ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_PROD_TABELA ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO, ID_TABELA)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_WEB_PEDIDO_ITENS_COMPOSTO ON TBL_WEB_PEDIDO_ITENS (ID_PEDIDO, ID_PRODUTO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_VISITA_ID_APP ON TBL_WEB_VISITA(ID_VISITA_APP)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_VISITA_CLIENTE_SRV ON TBL_WEB_VISITA(ID_CLIENTE_SERVIDOR)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_VISITA_VENDEDOR ON TBL_WEB_VISITA(ID_VENDEDOR)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_VISITA_PEDIDO ON TBL_WEB_VISITA(ID_PEDIDO)");
            db.execSQL("CREATE INDEX IF NOT EXISTS IDX_VISITA_STATUS ON TBL_WEB_VISITA(STATUS_VISITA)");
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }


    public long addDados(String tabela, ContentValues content) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            return db.insert(tabela, null, content);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int updateDados(String tabela, ContentValues content, String where) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            return db.update(tabela, content, where, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int deleteDados(String tabela, String clause, String args[]) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            return db.delete(tabela, clause, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            String exc = e.toString();
            e.printStackTrace();
        }
        return 0;
    }

    //Usado para consultar campo na tabela
    @SuppressLint("Range")
    public String consultaDados(String SQL, String campo) throws android.database.CursorIndexOutOfBoundsException {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(cursor.getColumnIndex(campo));
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }
    //Usar para consulta simples da base de dados
    public Cursor listaDados(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(SQL, null);
    }

    public Cursor listaDados2(String SQL) {
        // Só pede a instância se ela estiver fechada ou nula
        if (dbf == null || !dbf.isOpen()) {
            dbf = this.getReadableDatabase();
        }
        return dbf.rawQuery(SQL, null);
    }

    // No topo da sua classe DatabaseHelper ou DAO

    public Cursor listaPromocao(String SQL) {
        // Só pede a instância se ela estiver fechada ou nula
        if (dbf == null || !dbf.isOpen()) {
            dbf = this.getReadableDatabase();
        }
        return dbf.rawQuery(SQL, null);
    }

    public Cursor listaFotos(String SQL) {
        // Só pede a instância se ela estiver fechada ou nula
        if (dbf == null || !dbf.isOpen()) {
            dbf = this.getReadableDatabase();
        }
        return dbf.rawQuery(SQL, null);
    }

    public String pegaDataAtual() {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT date('now','localtime');", null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }

    public String pegaDias(String SQL) {
        //Roberto caceres
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }

    public String pegaDataAtual(String SQL) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }
    public String subtraiData(String SQL) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        //SQL = "SELECT date('now','localtime', '-10 day');";
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }
    public String[] pegaData(String sql) {
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(sql, null);
        cursor.moveToFirst();
        String resultado[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)};
        return resultado;
    }
    public String pegaDataExclusao(String sql) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(sql, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);//, cursor.getString(1), cursor.getString(2), cursor.getString(3)};
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }

    public String pegaDataHoraAtual() {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT datetime('NOW', 'localtime');", null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
             return cursor.getString(0);
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }

    public String pegaDataInicial(String sql) {
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(sql, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }

    public String pegaDataFinal(String sql) {
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(sql, null);
        if ( cursor.getCount() <= 0)
            return "";
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException e) {
        }
        return "";
    }
    public int pegaTamanhoExtraFonte() {
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT TAMANHO_EXTRA_FONTE FROM TBL_ACESSIBILIDADE", null);
        if ( cursor.getCount() <= 0)
            return 0;
        cursor.moveToFirst();
        try {
            return Math.max(cursor.getInt(0), 0);
        } catch ( CursorIndexOutOfBoundsException | NullPointerException ignored) {
        }
        return 0;
    }

    public Generico pegaUltimoPreco(String SQL) {
        //select max(itens.id_web_item_servidor), itens.id_pedido, itens.nome_produto,itens.valor_unitario, itens.valor_desconto_per,   (itens.valor_desconto_per /100) * itens.valor_unitario
        //from tbl_web_pedido_itens as itens inner join tbl_web_pedido as pedido ON (pedido.id_cadastro = 604 and pedido.id_web_pedido = itens.id_pedido)
        //where itens.id_produto = 1323
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        if ( cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
        try {
            if ( cursor.getString(1) == null)
                return null;
            Generico generico = new Generico();
            generico.setDatae(cursor.getString(1)); //emissao
            generico.setId(cursor.getString(2)); // id pedido
            generico.setNome(cursor.getString(3));  //nome_produto
            generico.setValor(cursor.getString(4)); //valor unitario
            generico.setPerc_desconto(cursor.getString(5)); //percentual de desconto
            generico.setValor_desconto("0");
            generico.setValor_liquido("0");
            generico.setQtde_produto(cursor.getString(6));

            return generico;
        } catch ( CursorIndexOutOfBoundsException | NullPointerException ignored) {
        }
        return null;
    }
    public int ultimoId(String SQL) {
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public int contagem(String SQL) {
        int resultado;
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        resultado =  cursor.getInt(0);
        return resultado;
    }
    public Float soma(String SQL) {
        Float resultado ;
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        resultado = cursor.getFloat(0);
        return resultado;
    }

    public Float saldo(String SQL) {
        Float resultado ;
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        resultado = cursor.getFloat(0);
        return resultado;
    }

    public Integer saldoPDF(String SQL) {
        Float resultado ;
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }
    //Usar apenas para alterar a estrutura da tabela
    public void alterar(String SQL) {
        try {
            SQLiteDatabase banco = this.getWritableDatabase();
            banco.execSQL(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Usar para update simples da base de dados
    public void update(String SQL) {
        try {
            SQLiteDatabase banco = this.getWritableDatabase();
            banco.execSQL(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {
        SQLiteDatabase banco = this.getWritableDatabase();
        banco.enableWriteAheadLogging(); // Ativa modo WAL
        banco.execSQL("PRAGMA synchronous=OFF;");
        banco.beginTransaction();

        //banco.execSQL("PRAGMA journal_mode=WAL;");
        //banco.execSQL("PRAGMA synchronous=NORMAL;");

        // 1. Otimização do Banco (Deve ser feito FORA da transação)
        // Uma vez configurado como WAL, o banco costuma guardar essa config.
        //if (!banco.isWriteAheadLoggingEnabled()) {
        //    banco.enableWriteAheadLogging(); // Método oficial do Android para PRAGMA WAL
        //}

        // 2. Ajuste de Sincronia (Reduz lentidão no Android 15/16)
        //banco.execSQL("PRAGMA synchronous=NORMAL;");

        // 3. Início da Transação
        // Use o método oficial em vez de execSQL("BEGIN") para evitar o erro de "Queries only"
//banco.beginTransaction();

    }
    public void endTransaction() {
        SQLiteDatabase banco = this.getWritableDatabase();
        banco.endTransaction();
    }
    public boolean verificaCnpjCpf(String cpfCnpj) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT COUNT(ID_PROSPECT) AS QUANTIDADE FROM TBL_PROSPECT WHERE CPF_CNPJ = " + cpfCnpj, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("QUANTIDADE")) <= 0;
    }
    //Analisar este com calma
    public List<TabelaPrecoItem> listaTabelaPrecoItem(String SQL) {
        List<TabelaPrecoItem> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();
                    tabelaPrecoItem.setId_item(cursor.getString(cursor.getColumnIndex("ID_ITEM")));
                    tabelaPrecoItem.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
                    tabelaPrecoItem.setPerc_desc_inicial(cursor.getString(cursor.getColumnIndex("PERC_DESC_INICIAL")));
                    tabelaPrecoItem.setPerc_desc_final(cursor.getString(cursor.getColumnIndex("PERC_DESC_FINAL")));
                    tabelaPrecoItem.setPerc_com_interno(cursor.getString(cursor.getColumnIndex("PERC_COM_INTERNO")));
                    tabelaPrecoItem.setPerc_com_externo(cursor.getString(cursor.getColumnIndex("PERC_COM_EXTERNO")));
                    tabelaPrecoItem.setPerc_com_exportacao(cursor.getString(cursor.getColumnIndex("PERC_COM_EXPORTACAO")));
                    tabelaPrecoItem.setPontos_premiacao(cursor.getString(cursor.getColumnIndex("PONTOS_PREMIACAO")));
                    tabelaPrecoItem.setCor_painel(cursor.getString(cursor.getColumnIndex("COR_PAINEL")));
                    tabelaPrecoItem.setCor_fonte(cursor.getString(cursor.getColumnIndex("COR_FONTE")));
                    tabelaPrecoItem.setVerba_perc(cursor.getString(cursor.getColumnIndex("VERBA_PERC")));
                    tabelaPrecoItem.setUtiliza_verba(cursor.getString(cursor.getColumnIndex("UTILIZA_VERBA")));
                    tabelaPrecoItem.setDesconto_verba_max(cursor.getString(cursor.getColumnIndex("DESCONTO_VERBA_MAX")));
                    tabelaPrecoItem.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
                    tabelaPrecoItem.setUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
                    tabelaPrecoItem.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                    tabelaPrecoItem.setCor_web(cursor.getString(cursor.getColumnIndex("COR_WEB")));
                    tabelaPrecoItem.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
                    lista.add(tabelaPrecoItem);
                    System.gc();
                } while (cursor.moveToNext());
                cursor.close();
                //System.gc();
            } else {
                TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();
                tabelaPrecoItem.setPerc_desc_final("0.00");
                lista.add(tabelaPrecoItem);
            }
        } catch (SQLException e) {
            return lista;
        } catch (Exception e) {
            return lista;
        }
        return lista;
    }

}
