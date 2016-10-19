package com.example.usuario.agendaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 19/10/2016.
 */

public class EventoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "Eventos";
    private static final String DATABASE = "Agenda";

    public EventoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + TABELA
                + "(eventId INTEGER PRIMARY KEY,"
                + "titulo TEXT NOT NULL,"
                + "descricao TEXT,"
                + "lati LONG,"
                + "longi LONG,"
                + "horario DATE,"
                + "data DATE"
                + "recorrente BOOLEAN);";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1){
            String sql = "ALTER TABLE" + TABELA + "ADD COLUMN recorrente BOOLEAN;";
            db.execSQL(sql);
        }
    }

    public void inserirEvento(Evento evento){
        ContentValues values = new ContentValues();

        values.put("titulo", evento.getTitulo());
        values.put("descricao", evento.getDescricao());
        values.put("lati", evento.getLati());
        values.put("longi", evento.getLongi());
        values.put("horario", evento.getHorario());
        values.put("data", evento.getData());
        values.put("recorrente", evento.getRecorrente());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public void apagarEvento(Evento evento) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {evento.getEventoId().toString()};
        db.delete("eventos", "id=?", args); //Deleta qualquer um que tenha o id igual a args
    }

    public void alterarEvento(Evento evento){
        ContentValues values = new ContentValues();

        values.put("titulo", evento.getTitulo());
        values.put("descricao", evento.getDescricao());
        values.put("lati", evento.getLati());
        values.put("longi", evento.getLongi());
        values.put("horario", evento.getHorario());
        values.put("data", evento.getData());
        values.put("recorrente", evento.getRecorrente());

        String[] idParaAlterar = {evento.getEventoId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", idParaAlterar);
    }

    public boolean isRecorrente(Boolean recorrente){

        String[] parametros = {recorrente.toString()};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT recorrente FROM " + TABELA + "WHERE recorrente=?", parametros);
        int total = cursor.getCount();
        return total > 0;

    }

    public List<Evento> getListaEventos(){

        List<Evento> eventos = new ArrayList<Evento>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";",null);

        while (cursor.moveToNext()){
            Evento evento = new Evento();
            evento.setEventoId(cursor.getLong(cursor.getColumnIndex("eventoId")));
            evento.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            evento.setLati(cursor.getLong(cursor.getColumnIndex("lati")));
            evento.setLongi(cursor.getLong(cursor.getColumnIndex("longi")));
            evento.setHorario(cursor.getString(cursor.getColumnIndex("horario")));
            evento.setData(cursor.getString(cursor.getColumnIndex("data")));
            evento.setRecorrente(cursor.getString(cursor.getColumnIndex("recorrente")));
            eventos.add(evento);
        }
        cursor.close();
        return eventos;
    }
}
