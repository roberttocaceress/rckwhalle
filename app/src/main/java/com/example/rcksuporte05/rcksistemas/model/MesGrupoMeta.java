package com.example.rcksuporte05.rcksistemas.model;


public class MesGrupoMeta {
	private String nome_vendedor;
    private String id_vendedor;
    private String nome_mes;
	private String id_grupo;
    private String nome_grupo;
    //private String ini_mes;
    //private String fin_mes;
    private String venda_bruta;
    private String venda_liquida;
    private String e_entrada_s_saida;

	public String getId_vendedor() {
		return id_vendedor;
	}

	public void setId_vendedor(String id_vendedor) {
		this.id_vendedor = id_vendedor;
	}

	public String getNome_mes() {
		return nome_mes;
	}

	public void setNome_mes(String nome_mes) {
		this.nome_mes = nome_mes;
	}

	public String getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(String id_grupo) {
		this.id_grupo = id_grupo;
	}

	public String getNome_grupo() {
		return nome_grupo;
	}

	public void setNome_grupo(String nome_grupo) {
		this.nome_grupo = nome_grupo;
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

	public String getNome_vendedor() {
		return nome_vendedor;
	}

	public void setNome_vendedor(String nome_vendedor) {
		this.nome_vendedor = nome_vendedor;
	}

	private String figura_rel_comercial;

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
