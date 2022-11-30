package com.mobile.megakons.sa.BaseDatos_SQL_LITE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//importar la clase DatosHelper para la lectura y escritura de la base de datos

import com.mobile.megakons.sa.ProductosOfflineActivity;

import java.util.ArrayList;
import java.util.List;

import entidades.ProductoOffline;

import static com.mobile.megakons.sa.BaseDatos_SQL_LITE.DatosHelper.tablaArticulos.TABLA_ARTICULOS;



public class DatosArticulosConexion {

    //Variables principales para llamar a la base de Datos y llamado a la clase DatosHelper
    private SQLiteDatabase baseDatos;
    private DatosHelper datosHelper;
    private Context context;

    //Lista de Tipo Articulos
    List<ProductoOffline> productosOfflineLista;

    //Realiza la conexion
    public DatosArticulosConexion(Context context) {
        this.context = context;
    }
    //Abre la conexion a la base de datos
    public DatosArticulosConexion open(){
        datosHelper = new DatosHelper(context);
        baseDatos = datosHelper.getWritableDatabase(); //permite la escritura en la base de datos
        return this;
    }

    //cierra la conexion a la base de datos
    public void close(){
        datosHelper.close();
    }

    //Realiza la insercion de datos uno a uno gracioas a metodos predefinidos
    public boolean registrarDatosTablaArticulos(ContentValues contentValues){ //contentValues recibe los valores a insertar
        boolean estado = true;
        int resultadoConsulta = (int) baseDatos.insert(TABLA_ARTICULOS, null, contentValues); //insert es un metodo predefinido que evita colocar la sentencia sql para una mayor seguridad
        if (resultadoConsulta < 0){
            estado = false;
        }
        return estado;
    }

    public void eliminaCrea_TB_ARTICULOS(){
        try{
            datosHelper.eliminaCrea_TB_ARTICULOS(baseDatos);
        }catch (Exception e){
            Log.e("Error al eliminar y crear TB_ARTICULOS: ", e.getMessage());
        }
    }

    //Realiza la insercion de todos los datos gracioas a metodos predefinidos
    public void insertarDatosTablaArticulos(ContentValues contentValues){ //contentValues recibe los valores a insertar
        try {
            baseDatos.insert(TABLA_ARTICULOS, null, contentValues); //insert es un metodo predefinido que evita colocar la sentencia sql para una mayor seguridad
        }catch (Exception e){
            Log.e("Error al insertar datos en SQLITE: ", e.getMessage());
        }
    }

    //Mostrar Datos de la TablaArticulos
    public boolean listarArticulos(){
        productosOfflineLista = new ArrayList<>();
        boolean estado = true;
        ProductoOffline productoOffline;
        Cursor cursor= baseDatos.rawQuery("SELECT * FROM " + TABLA_ARTICULOS, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){ //mientras el cursor este antes del final
                productoOffline = new ProductoOffline();
                productoOffline.setCodigo(cursor.getString(0));
                productoOffline.setNombre(cursor.getString(1));
                productoOffline.setExistencia(cursor.getDouble(2));
                productoOffline.setPrecio(cursor.getDouble(3));
                productoOffline.setDescuento(cursor.getDouble(4));
                productoOffline.setMedida(cursor.getString(5));
                productoOffline.setPresentacion(cursor.getString(6));
                productoOffline.setUbicacion(cursor.getString(7));
                productoOffline.setCodigoAlterno(cursor.getString(8));
                productosOfflineLista.add(productoOffline);
                cursor.moveToNext(); //moverse al siguiente registro evitando un bucle infinito
            }
        }else{
            estado = false;
        }
        return estado;
    }

    public List<ProductoOffline> getProductosOfflineLista(){
        return productosOfflineLista;
    }

}
