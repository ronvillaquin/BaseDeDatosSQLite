package com.example.basededatossqlite;
// lo primero que se realiza es importar la clase SQlite open helper
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//heredamos los atributo y metodos de la clase sqlhelper mediante extends
// se coloca SQLiteOpenHelper y marca error luego alt+enter en impor metod para importar metodo oncrate y onupgrade
//luego que se agrega sigue marcando un error ahora hay que agregar el constructor con alt+enter despues de sqllite

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // ahora en oncreate borramos  db y colocamos el nombre que deseemos que tenga nuestra db
    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        //ahora creamos las tablas que tendra la base de datos con las llaves primarias
        BaseDeDatos.execSQL("create table articulos(codigo int, descripcion text, precio real)");
        // ahora realizamos el diseño de la app

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
