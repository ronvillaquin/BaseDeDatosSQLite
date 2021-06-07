package com.example.basededatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // antes de desarrollar hay que crear una clase para la base de datos
    //en la carpeta java donde esta le main activity new class java

    private EditText caja_codigo, caja_desripcion, caja_precio;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caja_codigo = (EditText)findViewById(R.id.caja_codigo);
        caja_desripcion = (EditText)findViewById(R.id.caja_descripcion);
        caja_precio = (EditText)findViewById(R.id.caja_precio);

        textView = (TextView)findViewById(R.id.textView);
    }

    public void btn_guardar(View vista){

        // lo primero es crear un objeto de la clase queya creamos de sqlite
        // al crear el objeto pide 4 parametris hacemos referencia a this, nombre de la bd, null, version de la bd(como es la primera se coloca 1

        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //ahora abrimos la bd de modo lectura y escritura creando un objeto de clase SQLitedatabase
        SQLiteDatabase BaseDeDatos = adminDB.getWritableDatabase();

        // ahora creamos als variables para poder recibir y pasarla a las base de datos
        String c_codigo = caja_codigo.getText().toString();
        String c_descripcion = caja_desripcion.getText().toString();
        String c_precio = caja_precio.getText().toString();

        // al colocarlo de esta forma ! decimos si la variable codigo es diferente de vacio
        // con el ! decimos que si es diferente a vacia osea si trae algo
        // sin el ! le decimos que si es vacia

        if (!c_codigo.isEmpty() && !c_descripcion.isEmpty() && !c_precio.isEmpty()){
            // para guardarlo cramos un objeto de la clase contentValues
            ContentValues registro = new ContentValues();
            //ahora para asignar el valor a la variable de la tabla llamamos el objeto que se creo
            //entre "" colocamos el mismo nombre donde guardaremos en la tabla de la bd
            registro.put("codigo", c_codigo);
            registro.put("descripcion", c_descripcion);
            registro.put("precio", c_precio);

            // ahora para poder guardarlos dentro de la base de datos llamaos al nombre de la base de datos.insert
            // con registro hacemos referencia a los put que ta creamos
            BaseDeDatos.insert("articulos", null, registro);
            // ahora es importante siemre cerrarla o matar el proceso cuando la utilizamos
            BaseDeDatos.close();
            caja_codigo.setText("");
            caja_desripcion.setText("");
            caja_precio.setText("");

            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Debe Llenar Todos Los Campos", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_buscar(View vista){

        //abrimos la base de datos mediante un objeto
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //le decimos que vamos a escribir o colsutar en ella
        SQLiteDatabase BaseDeDatos = adminDB.getWritableDatabase();

        //usaremos solo lo que el usuario introdujo en codigo apra consultar lo q esta en la db
        String c_codigo = caja_codigo.getText().toString();

        //validamos si esta vacia o no
        if (!c_codigo.isEmpty()){
            // cramos un objeto de la clase cursor para hacer un select en la tabla
            Cursor fila = BaseDeDatos.rawQuery
                    ("select rowid, codigo, descripcion, precio from articulos where codigo =" + c_codigo, null);

            // ahora vemos si encuentra datos dentro de la tabla si es asi lo asignamos al campo correpondiente para msotrar
            if (fila.moveToFirst()){
                textView.setText(fila.getString(0));
                caja_desripcion.setText(fila.getString(1));
                caja_precio.setText(fila.getString(2));
                BaseDeDatos.close();
            }else {
                Toast.makeText(this, "El preoducto o registro no existe", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }

        }else {
            Toast.makeText(this, "Debe Ingresar Un Codigo Para Consultar", Toast.LENGTH_LONG).show();
        }

    }

    public void btn_eliminar(View vista){
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = adminDB.getWritableDatabase();

        String c_codigo = caja_codigo.getText().toString();

        if (!c_codigo.isEmpty()){
            // el metodo delete retorna valores enteros de la cantidad de filas q se borran
            int cantidad = BaseDeDatos.delete("articulos", "codigo="+ c_codigo,null);
            BaseDeDatos.close();

            caja_codigo.setText("");
            caja_desripcion.setText("");
            caja_precio.setText("");

            if (cantidad != 0){
                Toast.makeText(this, "Producto Eliminado", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Producto No Existe", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "Debe Ingresar Un Codigo", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_modificar(View vista){
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = adminDB.getWritableDatabase();

        String c_codigo = caja_codigo.getText().toString();
        String c_descripcion = caja_desripcion.getText().toString();
        String c_precio = caja_precio.getText().toString();

        if (!c_codigo.isEmpty() && !c_descripcion.isEmpty() && !c_precio.isEmpty()){
            // se realiza casi lo mismo que ara guardar un nuevo producto
            ContentValues registro = new ContentValues();
            registro.put("codigo", c_codigo);
            registro.put("descripcion", c_descripcion);
            registro.put("precio", c_precio);

            // ahora hacemos la line apara poder modificar
            int cantidad = BaseDeDatos.update("articulos", registro, "codigo="+ c_codigo, null);
            BaseDeDatos.close();

            if (cantidad != 0){
                Toast.makeText(this, "Producto Modificado", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Producto no Existe", Toast.LENGTH_LONG).show();
            }


        }else{
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }




}
