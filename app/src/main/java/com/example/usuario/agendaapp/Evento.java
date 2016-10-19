package com.example.usuario.agendaapp;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.provider.CalendarContract;

import java.io.Serializable;

/**
 * Created by Usuario on 19/10/2016.
 */

public class Evento implements Serializable {

    private String titulo;
    private String descricao;
    private String localizacao;
    private String horario;
    private String data;
    private String recorrente;

    private Long lati;
    private Long longi;

    private Long eventoId;

    @Override
    public String toString() {
        return "(" + eventoId + ")" + titulo;
    }

    public String locToString() {
        return lati.toString() + longi.toString();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return locToString();
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRecorrente() {
        return recorrente;
    }

    public void setRecorrente(String recorrente) {
        this.recorrente = recorrente;
    }

    public Long getLati() { return lati; }

    public void setLati(Long lati) { this.lati = lati; }

    public Long getLongi() { return longi; }

    public void setLongi(Long longi) { this.longi = longi; }

}

