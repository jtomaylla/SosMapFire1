package com.ecandle.firebase.sosmapfire1.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ecandle.firebase.sosmapfire1.models.DataIncidente;
import com.ecandle.firebase.sosmapfire1.models.Fuente;
import com.ecandle.firebase.sosmapfire1.models.Incidente;
import com.ecandle.firebase.sosmapfire1.models.Tipo_Fuente;
import com.ecandle.firebase.sosmapfire1.models.Tipo_Incidente;
import com.ecandle.firebase.sosmapfire1.models.Tipo_Via;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.ecandle.example.sosmap.models.DataIncidente;
//import com.ecandle.example.sosmap.models.Fuente;
//import com.ecandle.example.sosmap.models.Incidente;
//import com.ecandle.example.sosmap.models.Tipo_Fuente;
//import com.ecandle.example.sosmap.models.Tipo_Incidente;
//import com.ecandle.example.sosmap.models.Tipo_Via;

/**
 * Created by juantomaylla on 17/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tipoIncidente
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SosMap";

    // Table Names
    public static final String TABLE_TIPO_INCIDENTE = "Tipo_Incidente";
    public static final String TABLE_TIPO_VIA = "Tipo_Via";
    public static final String TABLE_INCIDENTE = "Incidente";
    public static final String TABLE_FUENTE = "Fuente";
    public static final String TABLE_TIPO_FUENTE = "Tipo_Fuente";
    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_FECHA_CREACION = "fecha_creacion";
    private static final String KEY_ANULADO = "anulado";


    // TIPO_INCIDENTE Table - column names
    private static final String KEY_IMAGEN = "imagen";


    // TIPO_VIA Table - column names


    // INCIDENTE Table - column names
    private static final String KEY_TIPO_INCIDENTE = "tipo_incidente";
    private static final String KEY_TIPO_VIA = "tipo_via";
    private static final String KEY_LATITUD = "latitud";
    private static final String KEY_LONGITUD = "longitud";
    private static final String KEY_INCIDENTE_DESCRIPCION = "descripcion_incidente";
    private static final String KEY_INCIDENTE_FECHA = "fecha_incidente";
    private static final String KEY_INCIDENTE_HORA = "incidente_hora";
    private static final String KEY_EVIDENCIA = "evidencia";
    private static final String KEY_FUENTE = "fuente_id";


    // FUENTE Table - column names
    private static final String KEY_TIPO_FUENTE = "Tipo_Fuente";

    // TIPO_FUENTE Table - column names
    private static final String KEY_TIPO_FUENTE_NOMBRE = "tipo_fuente_nombre";

    // Table Create Statements
    // Tipo_Via table create statement
    private static final String CREATE_TABLE_TIPO_VIA = "CREATE TABLE "
            + TABLE_TIPO_VIA + "(" + KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_NOMBRE + " VARCHAR," +
            KEY_ANULADO + " INTEGER," +
            KEY_FECHA_CREACION + " DATETIME" + ")";

    // Tipo_incidente table create statement
    private static final String CREATE_TABLE_TIPO_INCIDENTE = "CREATE TABLE " + TABLE_TIPO_INCIDENTE +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NOMBRE + " VARCHAR," +
            KEY_IMAGEN + " VARCHAR,"+
            KEY_ANULADO + " INTEGER," +
            KEY_FECHA_CREACION + " DATETIME" + ")";

    // Incidente table create statement
    private static final String CREATE_TABLE_INCIDENTE = "CREATE TABLE " + TABLE_INCIDENTE +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TIPO_INCIDENTE + " INTEGER," +
            KEY_TIPO_VIA + " INTEGER," +
            KEY_LATITUD + " VARCHAR," +
            KEY_LONGITUD + " VARCHAR," +
            KEY_INCIDENTE_DESCRIPCION + " VARCHAR," +
            KEY_INCIDENTE_FECHA + " VARCHAR," +
            KEY_INCIDENTE_HORA + " VARCHAR," +
            KEY_EVIDENCIA + " VARCHAR," +
            KEY_FUENTE + " INTEGER," +
            KEY_ANULADO + " INTEGER" + ")";

    // Fuente table create statement
    private static final String CREATE_TABLE_FUENTE = "CREATE TABLE "
            + TABLE_FUENTE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_NOMBRE + " VARCHAR," +
            KEY_TIPO_FUENTE + " INTEGER," +
            KEY_ANULADO + " INTEGER," +
            KEY_FECHA_CREACION + " DATETIME" + ")";

    // Tipo_Fuente table create statement
    private static final String CREATE_TABLE_TIPO_FUENTE = "CREATE TABLE "
            + TABLE_TIPO_FUENTE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_NOMBRE + " VARCHAR," +
            KEY_ANULADO + " INTEGER," +
            KEY_FECHA_CREACION + " DATETIME" + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    public void open(SQLiteDatabase db) throws SQLException {
//        //mDatabase = mDbHelper.getWritableDatabase();
//        db.getWritableDatabase();
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TIPO_VIA);
        db.execSQL(CREATE_TABLE_TIPO_INCIDENTE);
        db.execSQL(CREATE_TABLE_INCIDENTE);
        db.execSQL(CREATE_TABLE_FUENTE);
        db.execSQL(CREATE_TABLE_TIPO_FUENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_VIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_INCIDENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCIDENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_FUENTE);
        // create new tables
        onCreate(db);
    }

    // ------------------------ "Incidente" table methods ----------------//

    /**
     * Creating tipoIncidente
     */
    public long createIncidente(Incidente Incidente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIPO_INCIDENTE, Incidente.getTipo_incidente());
        values.put(KEY_TIPO_VIA, Incidente.getTipo_via());
        values.put(KEY_LATITUD, Incidente.getLatitud());
        values.put(KEY_LONGITUD, Incidente.getLongitud());
        values.put(KEY_INCIDENTE_DESCRIPCION, Incidente.getDescripcion_incidente());
        values.put(KEY_INCIDENTE_FECHA, Incidente.getFecha_incidente());
        values.put(KEY_INCIDENTE_HORA, Incidente.getHora_incidente());
        values.put(KEY_EVIDENCIA, Incidente.getEvidencia());
        values.put(KEY_FUENTE, Incidente.getFuente_id());
        values.put(KEY_ANULADO,Incidente.getAnulado());

        // insert row
        long id = db.insert(TABLE_INCIDENTE, null, values);

        return id;
    }
    /**
     * getting all tipoIncidentes
     * */
    public List<Incidente> getAllIncidentes() {
        List<Incidente> Incidentes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_INCIDENTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Incidente t = new Incidente();
                t.setid(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setTipo_incidente(c.getInt(c.getColumnIndex(KEY_TIPO_INCIDENTE)));
                t.setTipo_via(c.getInt(c.getColumnIndex(KEY_TIPO_VIA)));
                t.setLatitud(c.getString(c.getColumnIndex(KEY_LATITUD)));
                t.setLongitud(c.getString(c.getColumnIndex(KEY_LONGITUD)));
                t.setDescripcion_incidente(c.getString(c.getColumnIndex(KEY_INCIDENTE_DESCRIPCION)));
                t.setFecha_incidente(c.getString(c.getColumnIndex(KEY_INCIDENTE_FECHA)));
                t.setHora_incidente(c.getString(c.getColumnIndex(KEY_INCIDENTE_HORA)));
                t.setEvidencia(c.getString(c.getColumnIndex(KEY_EVIDENCIA)));
                t.setFuente_id(c.getInt(c.getColumnIndex(KEY_FUENTE)));
                t.setAnulado(c.getInt(c.getColumnIndex(KEY_ANULADO)));
                // adding to tipoIncidentes list
                Incidentes.add(t);
            } while (c.moveToNext());
        }
        return Incidentes;
    }

    public List<DataIncidente> getListIncidentes() {
        List<DataIncidente> Incidentes = new ArrayList<>();
        String selectQuery = "SELECT " +
                "I." + KEY_ID + "," +
                "I." + KEY_INCIDENTE_DESCRIPCION+ "," +
                "TI." + KEY_NOMBRE + " AS nomtipo_incidente," +
                "TI." + KEY_IMAGEN + "," +
                "TV." + KEY_NOMBRE + " AS nomtipo_via," +
                "I." + KEY_LATITUD + "," +
                "I." + KEY_LONGITUD + "," +
                "I." + KEY_INCIDENTE_FECHA + "," +
                "I." + KEY_INCIDENTE_HORA + "," +
                "TF." + KEY_NOMBRE + "," +
                "I." + KEY_FUENTE +
                " FROM " + TABLE_INCIDENTE +
                " I INNER JOIN " + TABLE_TIPO_INCIDENTE +
                " TI ON I."  + KEY_TIPO_INCIDENTE  + " = TI." + KEY_ID +
                " INNER JOIN " + TABLE_TIPO_VIA +
                " TV ON I."  + KEY_TIPO_VIA + " = TV." + KEY_ID +
                " INNER JOIN " + TABLE_TIPO_FUENTE +
                " TF ON I."  + KEY_FUENTE + " = TF." + KEY_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DataIncidente t = new DataIncidente();
                t.setIncidente_id(c.getString((c.getColumnIndex(KEY_ID))));
                t.setTipo_incidente(c.getString(c.getColumnIndex("nom"+KEY_TIPO_INCIDENTE)));
                t.setTipo_via(c.getString(c.getColumnIndex("nom"+KEY_TIPO_VIA)));
                t.setLatitud(c.getString(c.getColumnIndex(KEY_LATITUD)));
                t.setLongitud(c.getString(c.getColumnIndex(KEY_LONGITUD)));
                t.setDescripcion_incidente(c.getString(c.getColumnIndex(KEY_INCIDENTE_DESCRIPCION)));
                t.setFecha_incidente(c.getString(c.getColumnIndex(KEY_INCIDENTE_FECHA)));
                t.setHora_incidente(c.getString(c.getColumnIndex(KEY_INCIDENTE_HORA)));
                t.setTipo_incidente_img(c.getString(c.getColumnIndex(KEY_IMAGEN)));

                // adding to tipoIncidentes list
                Incidentes.add(t);
            } while (c.moveToNext());
        }
        return Incidentes;
    }

    public Cursor getCursorAllIncidentes() {
        String selectQuery = "SELECT * FROM " + TABLE_INCIDENTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Cursor mCursor = db.query(TABLE_INCIDENTE,
                new String[] {KEY_ID,
                    KEY_TIPO_INCIDENTE,
                    KEY_TIPO_VIA,
                    KEY_LATITUD,
                    KEY_LONGITUD,
                    KEY_INCIDENTE_DESCRIPCION,
                    KEY_INCIDENTE_FECHA,
                    KEY_INCIDENTE_HORA,
                    KEY_EVIDENCIA,
                    KEY_FUENTE,
                    KEY_ANULADO},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public Cursor getCursorQueryIncidentes() {

        String selectQuery = "SELECT " +
                "I." + KEY_ID + "," +
                "TI." + KEY_NOMBRE + "," +
                "TI." + KEY_IMAGEN + "," +
                "TV." + KEY_NOMBRE + "," +
                "I." + KEY_LATITUD + "," +
                "I." + KEY_LONGITUD + "," +
                "I." + KEY_INCIDENTE_FECHA + "," +
                "I." + KEY_INCIDENTE_HORA + "," +
                "TF." + KEY_NOMBRE + "," +
                "I." + KEY_FUENTE +
                " FROM " + TABLE_INCIDENTE +
                " I INNER JOIN " + TABLE_TIPO_INCIDENTE +
                " TI ON I."  + KEY_TIPO_INCIDENTE  + " = TI." + KEY_ID +
                " INNER JOIN " + TABLE_TIPO_VIA +
                " TV ON I."  + KEY_TIPO_VIA + " = TV." + KEY_ID +
                " INNER JOIN " + TABLE_TIPO_FUENTE +
                " TF ON I."  + KEY_FUENTE + " = TF." + KEY_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor c = db.rawQuery(selectQuery, null);

        Cursor mCursor = db.rawQuery(
                selectQuery,
                null
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }



    // ------------------------ "Tipo_Vias" table methods ----------------//

    /**
     * Creating a Tipo_Via
     */

    public long createTipo_Via(Tipo_Via tipoVia) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_TIPO_VIA, tipoVia.getid());
        values.put(KEY_NOMBRE, tipoVia.getNombre());
        values.put(KEY_ANULADO, tipoVia.getAnulado());

        long id = db.insert(TABLE_TIPO_VIA, null, values);

        return id;
    }

    /**
     * get single Tipo_Via
     */
    public Tipo_Via getTipo_Via(long tipo_via_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TIPO_VIA + " WHERE "
                + KEY_ID + " = " + tipo_via_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Tipo_Via tv = new Tipo_Via();
        tv.setid(c.getInt(c.getColumnIndex(KEY_ID)));
        tv.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));

        return tv;
    }
    

    /**T
     * getting all Tipo_vias
     * */
    public List<Tipo_Via> getAllTipo_Vias() {
        List<Tipo_Via> tipo_Vias = new ArrayList<Tipo_Via>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPO_VIA;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tipo_Via tv = new Tipo_Via();
                tv.setid(c.getInt((c.getColumnIndex(KEY_ID))));
                tv.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
                tv.setAnulado(c.getInt((c.getColumnIndex(KEY_ANULADO))));

                // adding to Tipo_Via list
                tipo_Vias.add(tv);
            } while (c.moveToNext());
        }

        return tipo_Vias;
    }
    public List<String> getAll_TipoVia_Names(){
        List<String> names = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TIPO_VIA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                names.add(Integer.toString(cursor.getInt(0))+"-"+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return names;
    }

    /**
     * getting Tipo_Via count
     */
    public int getTipo_ViaCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TIPO_VIA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a Tipo_Via
     */
    public int updateTipo_Via(Tipo_Via tipoVia) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, tipoVia.getNombre());
        values.put(KEY_ANULADO, tipoVia.getAnulado());

        // updating row
        return db.update(TABLE_TIPO_VIA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tipoVia.getid()) });
    }

    /**
     * Deleting a Tipo_Via
     */
    public void deleteTipo_Via(long tipo_via_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIPO_VIA, KEY_ID + " = ?",
                new String[] { String.valueOf(tipo_via_id) });
    }

    // ------------------------ "Tipo_Incidente" table methods ----------------//

    /**
     * Creating tipoIncidente
     */
    public long createTipo_Incidente(Tipo_Incidente tipoIncidente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, tipoIncidente.getNombre());
        values.put(KEY_IMAGEN, tipoIncidente.getImagen());
        values.put(KEY_ANULADO, tipoIncidente.getAnulado());

        // insert row
        long id = db.insert(TABLE_TIPO_INCIDENTE, null, values);

        return id;
    }

    public Tipo_Incidente createTipo_Incidente(String nombre, String imagen) {
        String[] mAllColumns = {DatabaseHelper.KEY_ID,
                DatabaseHelper.KEY_NOMBRE, DatabaseHelper.KEY_IMAGEN};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NOMBRE, nombre);
        values.put(DatabaseHelper.KEY_IMAGEN, imagen);
        long id = db.insert(DatabaseHelper.TABLE_TIPO_INCIDENTE, null, values);
        Cursor cursor = db.query(DatabaseHelper.TABLE_TIPO_INCIDENTE,
                mAllColumns, DatabaseHelper.KEY_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        Tipo_Incidente newTipo = cursorToTipo_Incidente(cursor);
        cursor.close();
        return newTipo;
    }
    private Tipo_Incidente cursorToTipo_Incidente(Cursor cursor) {
        Tipo_Incidente tipo = new Tipo_Incidente();
        tipo.setId(cursor.getInt(0));
        tipo.setNombre(cursor.getString(1));
        tipo.setImagen(cursor.getString(2));
        return tipo;
    }
    /**
     * getting all tipoIncidentes
     * */
    public List<Tipo_Incidente> getAllTipo_Incidentes() {
        List<Tipo_Incidente> tipoIncidentes = new ArrayList<Tipo_Incidente>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPO_INCIDENTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tipo_Incidente t = new Tipo_Incidente();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
                t.setImagen(c.getString(c.getColumnIndex(KEY_IMAGEN)));
                t.setAnulado(c.getInt(c.getColumnIndex(KEY_ANULADO)));
                // adding to tipoIncidentes list
                tipoIncidentes.add(t);
            } while (c.moveToNext());
        }
        return tipoIncidentes;
    }

    public Cursor getCursorAllTipo_Incidentes() {
        String selectQuery = "SELECT * FROM " + TABLE_TIPO_INCIDENTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Cursor mCursor = db.query(TABLE_TIPO_INCIDENTE, new String[] {KEY_ID,
                        KEY_NOMBRE, KEY_IMAGEN, KEY_ANULADO},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAll_TipoIncidente_Names(){
        List<String> names = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TIPO_INCIDENTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                names.add(Integer.toString(cursor.getInt(0))+"-"+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return names;
    }
    /**
     * Updating a tipoIncidente
     */
    public int updateTipo_Incidente(Tipo_Incidente tipoIncidente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, tipoIncidente.getNombre());
        values.put(KEY_IMAGEN, tipoIncidente.getImagen());
        values.put(KEY_ANULADO, tipoIncidente.getAnulado());
        // updating row
        return db.update(TABLE_TIPO_INCIDENTE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tipoIncidente.getId()) });
    }

    /**
     * Deleting a tipoIncidente
     */
    public void deleteTipo_Incidente(long tipo_incidente_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIPO_INCIDENTE, KEY_ID + " = ?",
                new String[] { String.valueOf(tipo_incidente_id) });
    }

    // ------------------------ "Fuente" table methods ----------------//

    /**
     * Creating fuente
     */
    public long createFuente(Fuente fuente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, fuente.getNombre());
        values.put(KEY_TIPO_FUENTE, fuente.getTipo_fuente());
        values.put(KEY_ANULADO, fuente.getAnulado());
        // insert row
        long id = db.insert(TABLE_FUENTE, null, values);

        return id;
    }

    /**
     * getting all fuentes
     * */
    public List<Fuente> getAllFuentes() {
        List<Fuente> fuentes = new ArrayList<Fuente>();
        String selectQuery = "SELECT  * FROM " + TABLE_FUENTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Fuente t = new Fuente();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
                t.setTipo_fuente(c.getInt(c.getColumnIndex(KEY_TIPO_FUENTE)));
                t.setAnulado(c.getInt(c.getColumnIndex(KEY_ANULADO)));
                // adding to fuentes list
                fuentes.add(t);
            } while (c.moveToNext());
        }
        return fuentes;
    }

    /**
     * Updating a fuente
     */
    public int updateFuente(Fuente fuente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, fuente.getNombre());
        values.put(KEY_TIPO_FUENTE, fuente.getTipo_fuente());
        values.put(KEY_ANULADO, fuente.getAnulado());
        // updating row
        return db.update(TABLE_FUENTE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(fuente.getId()) });
    }

    /**
     * Deleting a fuente
     */
    public void deleteFuente(long fuente_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FUENTE, KEY_ID + " = ?",
                new String[] { String.valueOf(fuente_id) });
    }

    // ------------------------ "Tipo_Fuentes" table methods ----------------//

    /**
     * Creating a Tipo_Fuente
     */
    public long createTipo_Fuente(Tipo_Fuente tipoFuente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_TIPO_FUENTE, tipo_via_id);
        values.put(KEY_NOMBRE, tipoFuente.getNombre());
        values.put(KEY_ANULADO, tipoFuente.getAnulado());

        long id = db.insert(TABLE_TIPO_FUENTE, null, values);

        return id;
    }

    /**
     * get single Tipo_Fuente
     */
    public Tipo_Fuente getTipo_Fuente(long tipo_via_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TIPO_FUENTE + " WHERE "
                + KEY_ID + " = " + tipo_via_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Tipo_Fuente tv = new Tipo_Fuente();
        tv.setid(c.getInt(c.getColumnIndex(KEY_ID)));
        tv.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));

        return tv;
    }


    /**T
     * getting all Tipo_vias
     * */
    public List<Tipo_Fuente> getAllTipo_Fuentes() {
        List<Tipo_Fuente> tipo_Fuentes = new ArrayList<Tipo_Fuente>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPO_FUENTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tipo_Fuente tv = new Tipo_Fuente();
                tv.setid(c.getInt((c.getColumnIndex(KEY_ID))));
                tv.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
                tv.setAnulado(c.getInt((c.getColumnIndex(KEY_ANULADO))));

                // adding to Tipo_Fuente list
                tipo_Fuentes.add(tv);
            } while (c.moveToNext());
        }

        return tipo_Fuentes;
    }

    public List<String> getAll_TipoFuente_Names(){
        List<String> names = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TIPO_FUENTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                names.add(Integer.toString(cursor.getInt(0))+"-"+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return names;
    }
    /**
     * getting Tipo_Fuente count
     */
    public int getTipo_FuenteCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TIPO_FUENTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a Tipo_Fuente
     */
    public int updateTipo_Fuente(Tipo_Fuente tipoVia) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, tipoVia.getNombre());
        values.put(KEY_ANULADO, tipoVia.getAnulado());

        // updating row
        return db.update(TABLE_TIPO_FUENTE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tipoVia.getid()) });
    }

    /**
     * Deleting a Tipo_Fuente
     */
    public void deleteTipo_Fuente(long tipo_via_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIPO_FUENTE, KEY_ID + " = ?",
                new String[] { String.valueOf(tipo_via_id) });
    }



    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean tableExists(String tableName, boolean openDb) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }
        }

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    public boolean tableEmpty(String tableName, boolean openDb) {

        boolean flag;
        SQLiteDatabase db = this.getReadableDatabase();
        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }
        }
        Cursor cursor = db.rawQuery("select * from '"+tableName+"'", null);
        if (cursor.moveToFirst()){
            flag = false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }

    public void deleteTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
        db.close();
    }
    
    public void loadData(){

        // Creating TipoVia Data
        Tipo_Via tv1 = new Tipo_Via("Condominio",0);
        Tipo_Via tv2 = new Tipo_Via("Domicilio",0);
        Tipo_Via tv3 = new Tipo_Via("Edificio",0);
        Tipo_Via tv4 = new Tipo_Via("Institución educativa",0);
        Tipo_Via tv5 = new Tipo_Via("Local comercial",0);
        Tipo_Via tv6 = new Tipo_Via("Otros",0);
        Tipo_Via tv7 = new Tipo_Via("Transporte publico",0);
        Tipo_Via tv8 = new Tipo_Via("Vehículo",0);
        Tipo_Via tv9 = new Tipo_Via("Vía publica",0);

        // Inserting tags in db
        long tv1_id = createTipo_Via(tv1);
        long tv2_id = createTipo_Via(tv2);
        long tv3_id = createTipo_Via(tv3);
        long tv4_id = createTipo_Via(tv4);
        long tv5_id = createTipo_Via(tv5);
        long tv6_id = createTipo_Via(tv6);
        long tv7_id = createTipo_Via(tv7);
        long tv8_id = createTipo_Via(tv8);
        long tv9_id = createTipo_Via(tv9);

        Log.i("Tipo_Via Count", "Tipo_Via Count: " + getAllTipo_Vias().size());

//        // Creating Tipo_Incidente
        Tipo_Incidente ti1 = new Tipo_Incidente("Abuso de autoridad", "ic_abuso.png",0);
        Tipo_Incidente ti2 = new Tipo_Incidente("Accidente de personas", "ic_accidente.png",0);
        Tipo_Incidente ti3 = new Tipo_Incidente("Accidente de transito", "ic_transito.png",0);
        Tipo_Incidente ti4 = new Tipo_Incidente("Acoso callejero", "ic_acoso.png",0);
        Tipo_Incidente ti5 = new Tipo_Incidente("Actos inmorales", "ic_inmoral.png",0);
        Tipo_Incidente ti6 = new Tipo_Incidente("Animales perdidos", "ic_animal.png",0);
        Tipo_Incidente ti7 = new Tipo_Incidente("Bullying", "ic_bullying.png",0);
        Tipo_Incidente ti8 = new Tipo_Incidente("Deshechos", "ic_desecho.png",0);
        Tipo_Incidente ti9 = new Tipo_Incidente("Emergencia medica", "ic_emergencia.png",0);
        Tipo_Incidente ti10 = new Tipo_Incidente("Homicidio", "ic_homicidio.png",0);
        Tipo_Incidente ti11 = new Tipo_Incidente("Incendio", "ic_incendio.png",0);
        Tipo_Incidente ti12 = new Tipo_Incidente("Maltrato animal", "ic_maltrato.png",0);
        Tipo_Incidente ti13 = new Tipo_Incidente("Manifestaciones", "ic_manifestacion.png",0);
        Tipo_Incidente ti14 = new Tipo_Incidente("Objeto sospechoso", "ic_objeto.png",0);
        Tipo_Incidente ti15 = new Tipo_Incidente("Otros", "ic_otro.png",0);
        Tipo_Incidente ti16 = new Tipo_Incidente("Personas desaparecidas", "ic_desaparecido.png",0);
        Tipo_Incidente ti17 = new Tipo_Incidente("Prófugo", "ic_profugo.png",0);
        Tipo_Incidente ti18 = new Tipo_Incidente("Robo/Hurto", "ic_robo.png",0);
        Tipo_Incidente ti19 = new Tipo_Incidente("Ruidos molestos", "ic_ruido.png",0);
        Tipo_Incidente ti20 = new Tipo_Incidente("Secuestro", "ic_secuestro.png",0);
        Tipo_Incidente ti21 = new Tipo_Incidente("Sospechoso", "ic_sospechoso.png",0);
        Tipo_Incidente ti22 = new Tipo_Incidente("Trafico de armas de fuego", "ic_arma.png",0);
        Tipo_Incidente ti23 = new Tipo_Incidente("Trafico de drogas", "ic_droga.png",0);
        Tipo_Incidente ti24 = new Tipo_Incidente("Trafico de personas", "ic_trata.png",0);
        Tipo_Incidente ti25 = new Tipo_Incidente("Vandalismo/Delincuencia", "ic_vandalismo.png",0);
        Tipo_Incidente ti26 = new Tipo_Incidente("Vía publica", "ic_via.png",0);
        Tipo_Incidente ti27 = new Tipo_Incidente("Violencia", "ic_violencia.png",0);

        long ti1_id = createTipo_Incidente(ti1);
        long ti2_id = createTipo_Incidente(ti2);
        long ti3_id = createTipo_Incidente(ti3);
        long ti4_id = createTipo_Incidente(ti4);
        long ti5_id = createTipo_Incidente(ti5);
        long ti6_id = createTipo_Incidente(ti6);
        long ti7_id = createTipo_Incidente(ti7);
        long ti8_id = createTipo_Incidente(ti8);
        long ti9_id = createTipo_Incidente(ti9);
        long ti10_id = createTipo_Incidente(ti10);
        long ti11_id = createTipo_Incidente(ti11);
        long ti12_id = createTipo_Incidente(ti12);
        long ti13_id = createTipo_Incidente(ti13);
        long ti14_id = createTipo_Incidente(ti14);
        long ti15_id = createTipo_Incidente(ti15);
        long ti16_id = createTipo_Incidente(ti16);
        long ti17_id = createTipo_Incidente(ti17);
        long ti18_id = createTipo_Incidente(ti18);
        long ti19_id = createTipo_Incidente(ti19);
        long ti20_id = createTipo_Incidente(ti20);
        long ti21_id = createTipo_Incidente(ti21);
        long ti22_id = createTipo_Incidente(ti22);
        long ti23_id = createTipo_Incidente(ti23);
        long ti24_id = createTipo_Incidente(ti24);
        long ti25_id = createTipo_Incidente(ti25);
        long ti27_id = createTipo_Incidente(ti27);

        Log.i("Tipo_Incidente Count", "Tipo_Incidente Count: " + getAllTipo_Incidentes().size());

    }


}
