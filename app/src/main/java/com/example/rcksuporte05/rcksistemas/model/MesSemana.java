package com.example.rcksuporte05.rcksistemas.model;


import java.io.Serializable;

public class MesSemana implements Serializable {

    private String id_vendedor;
	private String nome_vendedor;
    private String nome_semana;
    private String ini_semama;
    private String fin_semana;
    private String venda_bruta;
    private String venda_liquida;
    private String qtde_pedidos;

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

	public String getNome_semana() {
		return nome_semana;
	}
	public void setNome_semana(String nome_semana) {
		this.nome_semana = nome_semana;
	}
	public String getIni_semama() {
		return ini_semama;
	}
	public void setIni_semama(String ini_semama) {
		this.ini_semama = ini_semama;
	}

	public String getFin_semana() {
		return fin_semana;
	}

	public void setFin_semana(String fin_semana) {
		this.fin_semana = fin_semana;
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
