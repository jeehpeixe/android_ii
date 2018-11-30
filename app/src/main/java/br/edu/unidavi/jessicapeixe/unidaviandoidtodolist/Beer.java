package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Beer implements Parcelable {

    private final int id;
    private final String tipo;
    private final String marca;
    private final String obs;
    private final int nota;
    private final String foto;
    private final double valorPago;

    public Beer(int id, String tipo, String marca, String obs, int nota, String foto, double valorPago) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.obs = obs;
        this.nota = nota;
        this.foto = foto;
        this.valorPago = valorPago;
    }

    protected Beer(Parcel in) {
        id = in.readInt();
        tipo = in.readString();
        marca = in.readString();
        obs = in.readString();
        nota = in.readInt();
        foto = in.readString();
        valorPago = in.readDouble();
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getObs() {
        return obs;
    }

    public int getNota() {
        return nota;
    }

    public String getFoto() {
        return foto;
    }

    public double getValorPago() {
        return valorPago;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tipo);
        dest.writeString(marca);
        dest.writeString(obs);
        dest.writeInt(nota);
        dest.writeString(foto);
        dest.writeDouble(valorPago);
    }
}
