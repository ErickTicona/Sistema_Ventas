package com.example.sistema_ventas.esquemaSqlite.tablas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;

public class DataBaseManager {

    public static final String TABLE_USUARIO_NAME = "usuario";

    public static final String CN_ID = "id";
    public static final String CN_NAME = "name";
    public static final String CN_PASSWORD = "password";
    public static final String CN_STATE = "state";

    public static final String CREATE_TABLE = "create table " + TABLE_USUARIO_NAME + " ("
            + CN_ID + " integer primary key autoincrement, "
            + CN_NAME + " text not null, "
            + CN_PASSWORD + " text not null, "
            + CN_STATE + " text);";

    public static final String ELIMINAR_TABLA_USUARIO = "DROP TABLE IF EXISTS " + TABLE_USUARIO_NAME + ";";

    private ConexionSqliteHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new ConexionSqliteHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues contentValues(String name, String password, String state){
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME, name);
        valores.put(CN_PASSWORD, password);
        valores.put(CN_STATE, state);
        return valores;
    }

    public void createUsuario(String name, String password){
        ContentValues valores= new ContentValues();
        valores.put("name",name);
        valores.put("password",password);
        valores.put("state","Activo");
        db.insert(DataBaseManager.TABLE_USUARIO_NAME,null,valores);
    }

    public Cursor getUsuarioByName(String name){
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PASSWORD,CN_STATE};
        return db.query(TABLE_USUARIO_NAME, columnas, CN_NAME+"=?", new String[]{name},null,null,null);
    }

    public Cursor getAllUsuario(){
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PASSWORD,CN_STATE};
        return db.query(TABLE_USUARIO_NAME, columnas, null, null,null,null,null);
    }

    public Boolean getIsExistByName(String name){
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PASSWORD,CN_STATE};
        Cursor cursor = db.query(TABLE_USUARIO_NAME, columnas, CN_NAME+"=?", new String[]{name},null,null,null);
        cursor.moveToPosition(0);
        if(cursor.getCount() > 0)
            return false;
        else
            return true;
    }
}
