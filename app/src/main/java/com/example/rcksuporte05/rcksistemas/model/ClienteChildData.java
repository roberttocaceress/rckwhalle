package com.example.rcksuporte05.rcksistemas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ClienteChildData implements Parcelable {

    String id_cliente;
    int id_cadastro_servidor;
    String nome_fantasia;
    String nome_cliente;
    String ultima_compra;//Data da ultima compra


    public ClienteChildData(Parcel parcel){

        id_cliente = parcel.readString();
        nome_cliente = parcel.readString();
        nome_fantasia = parcel.readString();
        ultima_compra = parcel.readString();//
    }

    public ClienteChildData(String id_cliente, int id_cadastro_servidor, String nome_cliente, String nome_fantasia, String ultima_compra) {
        this.id_cliente = id_cliente;
        this.id_cadastro_servidor = id_cadastro_servidor;
        this.nome_cliente = nome_cliente;
        this.nome_fantasia = nome_fantasia;
        this.ultima_compra = ultima_compra;

    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getUltima_compra() {
        return ultima_compra;
    }

    public void setUltima_compra(String ultima_compra) {
        this.ultima_compra = ultima_compra;
    }

    public int getId_cadastro_servidor() {
        return id_cadastro_servidor;
    }

    public void setId_cadastro_servidor(int id_cadastro_servidor) {
        this.id_cadastro_servidor = id_cadastro_servidor;
    }

    public static final Creator<ClienteChildData> CREATOR = new Creator<ClienteChildData>() {
        @Override
        public ClienteChildData createFromParcel(Parcel in) {
            return new ClienteChildData(in);
        }

        @Override
        public ClienteChildData[] newArray(int size) {
            return new ClienteChildData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_cliente);
        parcel.writeInt(id_cadastro_servidor);
        parcel.writeString(nome_cliente);
        parcel.writeString(nome_fantasia);
        parcel.writeString(ultima_compra);

    }
}
