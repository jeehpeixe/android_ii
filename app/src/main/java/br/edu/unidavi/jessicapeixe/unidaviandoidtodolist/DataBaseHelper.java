package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String dbName = "Beers.db";
    private static final int dbVersion = 1;

    public DataBaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTable = "create table beers(_id integer primary key autoincrement, tipo text, marca text, obs text, nota integer, foto blob, valor double)";
        db.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void createBeer(String tipo, String marca, String obs, int nota, String foto, double valor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tipo", tipo);
        values.put("marca", marca);
        values.put("obs", obs);
        values.put("nota", nota);
        values.put("foto", foto);
        values.put("valor", valor);
        db.insert("beers", null, values);
    }

    public List<Beer> fetchBeers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                "beers",
                 new String[] {"_id", "tipo", "marca", "obs", "nota", "foto", "valor"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        List<Beer> lista = new ArrayList<>();

        while(!cursor.isAfterLast()){
            Beer beer = new Beer(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("tipo")),
                cursor.getString(cursor.getColumnIndex("marca")),
                cursor.getString(cursor.getColumnIndex("obs")),
                cursor.getInt(cursor.getColumnIndex("nota")),
                cursor.getString(cursor.getColumnIndex("foto")),
                cursor.getDouble(cursor.getColumnIndex("valor"))
            );
            lista.add(beer);
            cursor.moveToNext();
        }

        cursor.close();
        return lista;
    }

    public void deleteBeer(Beer beer){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("beers", "_id=" + beer.getId(), null);
    }
}
