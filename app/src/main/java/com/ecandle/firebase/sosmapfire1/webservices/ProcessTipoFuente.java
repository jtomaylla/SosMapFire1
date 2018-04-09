package com.ecandle.firebase.sosmapfire1.webservices;

import android.util.Log;

import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.models.Tipo_Fuente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.ecandle.example.sosmap.helper.DatabaseHelper;
//import com.ecandle.example.sosmap.models.Tipo_Fuente;

/**
 * Created by juantomaylla on 30/01/17.
 */

public class ProcessTipoFuente extends GetIncidenteData {

    DatabaseHelper db;

    private List<Tipo_Fuente> mTipo_Fuente;

    public ProcessTipoFuente(DatabaseHelper mydb, String strURL) {
        super(strURL);
          db = mydb;
        mTipo_Fuente = new ArrayList<Tipo_Fuente>();
    }

    public void execute() {
        //super.execute();
        ProcessData processData = new ProcessData();
        processData.execute();
    }

    public class ProcessData extends DownloadJsonData {

        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            String TIPO_FUENTE_ID = "tipo_fuente_id";
            String NOMBRE_FUENTE = "nombre_fuente";

            Log.i("webData",webData);
            try {


                JSONArray jArray = new JSONArray(webData);

                // Extract data from json and store into TipoFuenteObject as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Tipo_Fuente TipoFuenteObject = new Tipo_Fuente();
                    TipoFuenteObject.nombre = json_data.getString(NOMBRE_FUENTE);
                    TipoFuenteObject.anulado = 0;

                    long inc = db.createTipo_Fuente(TipoFuenteObject);
                    if (inc<0){
                        Log.e("db.createTipo_Fuente","Error creating sqlite data");
                    }
                }

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e("webData","Error processing Json data");
            }


        }
    }


}
