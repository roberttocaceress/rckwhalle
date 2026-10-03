package com.example.rcksuporte05.rcksistemas.model;

import java.util.List;

public class ExpansaoCarteira {
    private String idVendedor;
    private String nomeVendedor;
    private int totalGeral;
    private List<ExpansaoMensal> meses;

    public ExpansaoCarteira() {}

    public String getIdVendedor() { return idVendedor; }
    public void setIdVendedor(String idVendedor) { this.idVendedor = idVendedor; }

    public String getNomeVendedor() { return nomeVendedor; }
    public void setNomeVendedor(String nomeVendedor) { this.nomeVendedor = nomeVendedor; }

    public int getTotalGeral() { return totalGeral; }
    public void setTotalGeral(int totalGeral) { this.totalGeral = totalGeral; }

    public List<ExpansaoMensal> getMeses() { return meses; }
    public void setMeses(List<ExpansaoMensal> meses) { this.meses = meses; }

    // Sub-classe para agrupar dados por mês
    public static class ExpansaoMensal {
        private String mesNome;
        private int totalMes;
        private List<ClienteExpansao> clientes;

        public String getMesNome() { return mesNome; }
        public void setMesNome(String mesNome) { this.mesNome = mesNome; }

        public int getTotalMes() { return totalMes; }
        public void setTotalMes(int totalMes) { this.totalMes = totalMes; }

        public List<ClienteExpansao> getClientes() { return clientes; }
        public void setClientes(List<ClienteExpansao> clientes) { this.clientes = clientes; }
    }

    // Sub-classe para o detalhe do cliente
    public static class ClienteExpansao {
        private String dataInclusao;
        private String razaoSocial;
        private String nomeFantasia;

        public String getDataInclusao() { return dataInclusao; }
        public void setDataInclusao(String dataInclusao) { this.dataInclusao = dataInclusao; }

        public String getRazaoSocial() { return razaoSocial; }
        public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

        public String getNomeFantasia() { return nomeFantasia; }
        public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    }
}
