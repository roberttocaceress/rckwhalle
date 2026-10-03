package com.example.rcksuporte05.rcksistemas.model;


import java.io.Serializable;

public class MesAnoMeta implements Serializable {
    private String nome_vendedor;
    private String id_vendedor;
    private String nome_mes;
    private String ini_mes;
    private String fin_mes;
    private String venda_bruta;
    private String venda_liquida;
	private String qtde_pedidos;
	private String venda_bruta_media;
	private String venda_liquida_media;
	private String status_pedidos;
	private String qtde_clientes;//
	private String qtde_prospect;//   //  qtde de clientes positivados


	private String nome_cadastro;

	private String e_entrada_s_saida;
	private String figura_rel_comercial;


	public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

	public String getNome_vendedor() {
		return nome_vendedor;
	}

	public void setNome_vendedor(String nome_vendedor) {
		this.nome_vendedor = nome_vendedor;
	}

    public String getNome_mes() {
		return nome_mes;
	}

	public void setNome_mes(String nome_mes) {
		this.nome_mes = nome_mes;
	}

	public String getIni_mes() {
		return ini_mes;
	}

	public void setIni_mes(String ini_mes) {
		this.ini_mes = ini_mes;
	}

	public String getFin_mes() {
		return fin_mes;
	}

	public void setFin_mes(String fin_mes) {
		this.fin_mes = fin_mes;
	}

	public String getVenda_bruta() {
		return venda_bruta;
	}

	public void setVenda_bruta(String venda_bruta) {
		this.venda_bruta = venda_bruta;
	}

	public String getVenda_liquida() {
		return venda_liquida;
	}

	public void setVenda_liquida(String venda_liquida) {
		this.venda_liquida = venda_liquida;
	}

	public String getQtde_pedidos() {
		return qtde_pedidos;
	}

	public void setQtde_pedidos(String qtde_pedidos) {
		this.qtde_pedidos = qtde_pedidos;
	}

	public String getVenda_bruta_media() {
		return venda_bruta_media;
	}

	public void setVenda_bruta_media(String venda_bruta_media) {
		this.venda_bruta_media = venda_bruta_media;
	}

	public String getVenda_liquida_media() {
		return venda_liquida_media;
	}

	public void setVenda_liquida_media(String venda_liquida_media) {
		this.venda_liquida_media = venda_liquida_media;
	}

	public String getStatus_pedidos() {
		return status_pedidos;
	}

	public void setStatus_pedidos(String status_pedidos) {
		this.status_pedidos = status_pedidos;
	}

	public String getQtde_clientes() {
		return qtde_clientes;
	}

	public void setQtde_clientes(String qtde_clientes) {
		this.qtde_clientes = qtde_clientes;
	}

	public String getQtde_prospect() {
		return qtde_prospect;
	}

	public void setQtde_prospect(String qtde_prospect) {
		this.qtde_prospect = qtde_prospect;
	}

	public String getNome_cadastro() {
		return nome_cadastro;
	}

	public void setNome_cadastro(String nome_cadastro) {
		this.nome_cadastro = nome_cadastro;
	}

	public String getE_entrada_s_saida() {
		return e_entrada_s_saida;
	}

	public void setE_entrada_s_saida(String e_entrada_s_saida) {
		this.e_entrada_s_saida = e_entrada_s_saida;
	}

	public String getFigura_rel_comercial() {
		return figura_rel_comercial;
	}

	public void setFigura_rel_comercial(String figura_rel_comercial) {
		this.figura_rel_comercial = figura_rel_comercial;
	}
}
