package com.mobile.megakons.sa.BaseDatos_SQL_LITE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatosHelper extends SQLiteOpenHelper {

    //Principales variables de inicializacion que definen la infraestructura de la base de datos
    private static DatosHelper datosHelper = null;
    private static String NombreBaseDatos = "db_MEGAKONS";
    private static int VERSION_BD = 1;

    //Constructoy de la clase DatosHelper que permite el llamado para la creacion de la base de datos
    public DatosHelper(@Nullable Context context) {
        super(context, NombreBaseDatos, null, VERSION_BD);
    }

    //Clase que contiene todas las columnas que utilizara la tabla
    public static class tablaArticulos{
        public static String TABLA_ARTICULOS = "TB_ARTICULOS";
        public static String COLUMNA_CODIGO = "CODIGO";
        public static String COLUMNA_NOMBRE = "NOMBRE";
        public static String COLUMNA_PRECIO = "PRECIO";
        public static String COLUMNA_EXISTENCIA = "EXISTENCIA";
        public static String COLUMNA_DESCUENTO = "DESCUENTO";
        public static String COLUMNA_MEDIDA = "MEDIDA";
        public static String COLUMNA_PRESENTACION = "PRESENTACION";
        public static String COLUMNA_UBICACION = "UBICACION";
        public static String COLUMNA_CODIGO_ALTERNO= "ALTERNO";
    }

    //Variable de tipo STRING que permite la creacion de la tabla utilizando una sentencia sql
    public static final String CONSULTA_CREAR_TABLA_ARTICULOS = "CREATE TABLE " + tablaArticulos.TABLA_ARTICULOS + " (" +
            tablaArticulos.COLUMNA_CODIGO + " VARCHAR NOT NULL PRIMARY KEY, " + tablaArticulos.COLUMNA_NOMBRE + " VARCHAR NOT NULL, " +
            tablaArticulos.COLUMNA_EXISTENCIA + " REAL, " + tablaArticulos.COLUMNA_PRECIO + " REAL, " + tablaArticulos.COLUMNA_DESCUENTO + " REAL, " +
            tablaArticulos.COLUMNA_MEDIDA + " REAL, " + tablaArticulos.COLUMNA_PRESENTACION + " REAL, " + tablaArticulos.COLUMNA_UBICACION + " VARCHAR," + tablaArticulos.COLUMNA_CODIGO_ALTERNO + " VARCHAR" + ");";

    //Variable de tipo STRING que permite la acualizacion de la tabla es decir si la borra es porque esa tabla ya existe
    public static final String CONSULTA_ELIMINAR_TABLA = "DROP TABLE IF EXISTS " + tablaArticulos.TABLA_ARTICULOS;

    @Override
    //El siguiente onCreate metodo permite crear la base de datos
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONSULTA_CREAR_TABLA_ARTICULOS); //ejecuta la sentencia sql
    }

    @Override
    //El siguiente onUpgrade metodo permite actualizar la base de datos es decir eliminar y crear una nueva base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CONSULTA_ELIMINAR_TABLA); //ejecuta la sentencia sql
        onCreate(db); //Vuelve a crear la base de datos
    }

    //Metodo que elimina y crea la tabla TB_ARTICULOS
    public void eliminaCrea_TB_ARTICULOS(SQLiteDatabase db){
        db.execSQL(CONSULTA_ELIMINAR_TABLA); //ejecuta la sentencia sql
        db.execSQL(CONSULTA_CREAR_TABLA_ARTICULOS); //ejecuta la sentencia sql
    }

}
