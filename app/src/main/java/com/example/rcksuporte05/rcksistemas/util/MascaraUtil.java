package com.example.rcksuporte05.rcksistemas.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by RCK 03 on 11/12/2017.
 */
public class MascaraUtil {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static String mascaraReal(Float valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        String valorString = String.valueOf(valor);

        String valorFinal = nf.format(Float.parseFloat(valorString));

        return valorFinal;
    }


    public static String mascaraReal(String valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        String valorFinal;
        try {
            valorFinal = nf.format(Float.parseFloat(valor));
        } catch ( NumberFormatException e ) {
            return valor;
        } catch ( NullPointerException e) {
            return valor;
        }

        return valorFinal;
    }

    public static String mascaraRealSimbolo(String valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        try {
            return nf.format(Double.parseDouble(valor));
        } catch (NumberFormatException | NullPointerException e) {
            return valor;
        }
    }

    public static String mascaraRealSimbolo(Float valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        String valorFinal;
        try {
            valorFinal = nf.format(valor);
        } catch (NumberFormatException e) {
            return String.valueOf(valor);
        } catch (NullPointerException e) {
            return String.valueOf(valor);
        }

        return valorFinal;
    }

    public static String mascaraRealSimboloD(Double valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        String valorFinal;
        try {
            valorFinal = nf.format(valor);
        } catch (NumberFormatException e) {
            return String.valueOf(valor);
        } catch (NullPointerException e) {
            return String.valueOf(valor);
        }

        return valorFinal;
    }

    public static String mascaraRealSemSimbolo(Float valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        try {
            String valorFinal = nf.format(valor);
            return valorFinal.replace("R", "").replace("$", "").replaceAll("\\s+", "");
        } catch (Exception e) {
            return String.valueOf(valor);
        }
    }

    public static String mascaraCPF(String valor) {
        String cpf = "";

        String valorLimpo = valor.trim().replaceAll("[^0-9]", "");
        if (!valorLimpo.equals("") && valorLimpo.length() == 11) {
            cpf = valorLimpo.substring(0, 3) + "." + valorLimpo.substring(3, 6) + "." + valorLimpo.substring(6, 9) + "-" + valorLimpo.substring(9, 11);
        }

        return cpf;
    }

    public static String mascaraCNPJ(String valor) {
        String cnpj = "";

        //String valorLimpo = valor.trim().replaceAll("[^0-9]", "");
        String valorLimpo = Mask.unmask(valor).trim();
        if (!valorLimpo.equals("") && valorLimpo.length() == 14) {
            cnpj = valorLimpo.substring(0, 2) + "." + valorLimpo.substring(2, 5) + "." + valorLimpo.substring(5, 8) + "/" + valorLimpo.substring(8, 12) + "-" + valorLimpo.substring(12, 14);
        }

        return cnpj;
    }                                                                                   

    public static String formataTelefone(String telefone) {
        String telefoneRetorno;
        try {
            telefone = telefone.trim().replaceAll("[^0-9]", "");
            if (telefone.length() == 10) {
                telefoneRetorno = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            } else if (telefone.length() == 11) {
                telefoneRetorno = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
            } else if (telefone.length() == 9 && !telefone.contains("-")) {
                telefoneRetorno = telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
            } else if (telefone.length() == 8) {
                telefoneRetorno = telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
            } else {
                telefoneRetorno = telefone;
            }
        } catch ( NullPointerException e) {
             e.printStackTrace();
            return "";
        }

        return telefoneRetorno;
    }

    public static boolean isAlphanumeric(String str) {
        return str != null && str.matches("[A-Z0-9]+");
    }
    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static int calcularDigitoAlfanumerico(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1; indice >= 0; indice--) {
            char c = str.charAt(indice);
            int valor;
            // Se for número, pega o valor real (0-9). Se for letra, converte conforme regra da RFB.
            if (Character.isDigit(c)) {
                valor = Character.getNumericValue(c);
            } else {
                valor = (int) c - 48;
            }
            soma += valor * peso[peso.length - str.length() + indice];
        }
        int resto = soma % 11;
        int digito = 11 - resto;
        return digito > 9 ? 0 : digito;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) return false;

        if ( cnpj.equalsIgnoreCase("99999999999999") || cnpj.equalsIgnoreCase("00000000000000") ) return true;
        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJAlfaNumerico(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) return false;
        if ( cnpj.equalsIgnoreCase("99999999999999") || cnpj.equalsIgnoreCase("00000000000000") || cnpj.equalsIgnoreCase("AAAAAAAAAAAAAA") ) return true;
        Integer digito1 = calcularDigitoAlfanumerico(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigitoAlfanumerico(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static String duasCasaDecimal(Float valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(valor).replace(",", ".");
    }

    public static String duasCasaDecimal(String valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Float.parseFloat(valor));
    }

    public static String mascaraVirgula(Float valor) {
        return mascaraReal(valor).replace("R$", "");
    }

    public static String mascaraVirgula(String valor) {
       try {
           return mascaraReal(valor).replace("R$", "");
       } catch ( NullPointerException e) {
           return  valor;
       } catch ( Exception e) {
           return  valor;
       }
    }

    public static String mascaraCep(String valor) {
        if (valor.length() == 8)
            return valor.substring(0, 5) + "-" + valor.substring(5, 8);
        else
            return valor;
    }

    public static String numeroZeros(String valor, int quantidade) {
        try {
            if (valor.length() < quantidade) {
                int diferenca = quantidade - valor.length();
                String zeros = "";
                for (int i = 0; diferenca > i; i++) {
                    zeros += "0";
                }
                return zeros + valor;
            } else {
                return valor;
            }
        } catch (Exception e) {
            return valor;
        }
    }
    public static String formataData( String data, String separador) {
        if ( data == null || data.isEmpty()) {
            return null;
        }
        if ( separador.equalsIgnoreCase("-")) {
            data = data.substring(6,10) + separador + data.substring(3,5) + separador + data.substring(0,2);
        } else {
            data = data.substring(8, 10) + separador + data.substring(5,7) + separador + data.substring(0,4);
        }
        return data;
    }

    public static String formataData( String data, String separador, int tamanho) {
        if ( data == null || data.isEmpty()) {
            return null;
        }
        if ( separador.equalsIgnoreCase("-")) {
            data = data.substring(6,10) + separador + data.substring(3,5) + separador + data.substring(0,2);
        } else {
            if (tamanho == 10)
                data = data.substring(8, 10) + separador + data.substring(5, 7) + separador + data.substring(0, 4);
            else
                data = data.substring(8, 10) + separador + data.substring(5, 7) + separador + data.substring(2, 4);
        }
        return data;
    }
    public static String formataData( String data) {
        if ( data == null || data.isEmpty())
            return null;

        data = data.substring(8, 10) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4);

        return data;
    }

    public static String formataCep(String cep) {
        if ( cep == null || cep.isEmpty()) {
            return  "";
        } else {
            return " CEP: " +cep.substring(0,5) + "-" + cep.substring(5);
        }

    }

    public static boolean isCelular(String telefone) {
        if (telefone == null) return false;
        String limpo = telefone.replaceAll("[^0-9]", "");
        return (limpo.length() == 11 && limpo.charAt(2) == '9') ||
                (limpo.length() == 9 && limpo.charAt(0) == '9');
    }

    public static String getSaudacaoHorario() {
        int hora = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        if (hora >= 5 && hora < 12) return "Bom dia";
        if (hora >= 12 && hora < 18) return "Boa tarde";
        return "Boa noite";
    }



}


