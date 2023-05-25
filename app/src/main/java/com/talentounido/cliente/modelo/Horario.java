package com.talentounido.cliente.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Horario implements Parcelable {
    private int id;
    private String inicia;
    private String finaliza;
    private String dia;
    private String usuario;
    private String materia;
    private String laboratorio;
    private String grupo;
    private String carrera;

    public Horario(int id, String inicia, String finaliza, String dia, String usuario, String materia, String laboratorio, String grupo, String carrera ) {
        this.id = id;
        this.inicia = inicia;
        this.finaliza = finaliza;
        this.dia = dia;
        this.grupo = grupo;
        this.carrera = carrera;
        this.materia = materia;
        this.laboratorio = laboratorio;
        this.usuario = usuario;
    }
    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInicia() {
        return inicia;
    }

    public void setInicia(String inicia) {
        this.inicia = inicia;
    }

    public String getFinaliza() {
        return finaliza;
    }

    public void setFinaliza(String finaliza) {
        this.finaliza = finaliza;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public static final Creator<Horario> CREATOR = new Creator<Horario>() {
        @Override
        public Horario createFromParcel(Parcel in) {
            return new Horario(in);
        }

        @Override
        public Horario[] newArray(int size) {
            return new Horario[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    protected Horario(Parcel in) {
        id = in.readInt();
        inicia = in.readString();
        finaliza = in.readString();
        dia = in.readString();
        grupo = in.readString();
        carrera = in.readString();
        materia = in.readString();
        laboratorio = in.readString();
        usuario = in.readString();
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(inicia);
        parcel.writeString(finaliza);
        parcel.writeString(dia);
        parcel.writeString(grupo);
        parcel.writeString(carrera);
        parcel.writeString(materia);
        parcel.writeString(laboratorio);
        parcel.writeString(usuario);
    }
}
