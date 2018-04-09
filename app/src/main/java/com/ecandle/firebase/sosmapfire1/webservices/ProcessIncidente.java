package com.ecandle.firebase.sosmapfire1.webservices;

import android.util.Log;

import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.models.Incidente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.ecandle.example.sosmap.helper.DatabaseHelper;
//import com.ecandle.example.sosmap.models.Incidente;

/**
 * Created by juantomaylla on 30/01/17.
 */

public class ProcessIncidente extends GetIncidenteData {
    private String LOG_TAG = ProcessIncidente.class.getSimpleName();
    DatabaseHelper db;

    private List<Incidente> mIncidente;

    public ProcessIncidente(DatabaseHelper mydb, String strURL) {
        super(strURL);
          db = mydb;
        mIncidente = new ArrayList<Incidente>();
    }

    public void execute() {
        //super.execute();
        ProcessData processData = new ProcessData();
        processData.execute();
    }

    public class ProcessData extends DownloadJsonData {

        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);

            String TIPO_INCIDENTE = "tipo_incidente";
            String TIPO_VIA = "tipo_via";
            String DESCRIPCION_INCIDENTE = "descripcion_incidente";
            String LATITUD = "latitud";
            String LONGITUD = "longitud";
            String FECHA_INCIDENTE = "fecha_incidente";
            String HORA_INCIDENTE = "hora_incidente";
            String EVIDENCIA = "evidencia";
            
            Log.i("webData",webData);
            try {


                JSONArray jArray = new JSONArray(webData);

                // Extract data from json and store into IncidenteObject as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Incidente IncidenteObject = new Incidente();
                    IncidenteObject.tipo_incidente = json_data.getInt(TIPO_INCIDENTE);
                    IncidenteObject.tipo_via = json_data.getInt(TIPO_VIA);
                    IncidenteObject.descripcion_incidente = json_data.getString(DESCRIPCION_INCIDENTE);
                    IncidenteObject.latitud = json_data.getString(LATITUD);
                    IncidenteObject.longitud = json_data.getString(LONGITUD);
                    IncidenteObject.fecha_incidente = json_data.getString(FECHA_INCIDENTE);
                    IncidenteObject.hora_incidente = json_data.getString(HORA_INCIDENTE);
                    IncidenteObject.evidencia = json_data.getString(EVIDENCIA);
                    IncidenteObject.fuente_id = 0;
                    IncidenteObject.anulado = 0;

                    long inc = db.createIncidente(IncidenteObject);

                    if (inc<0){
                        Log.e("db.createIncidente","Error creating sqlite data");
                    }
                }


                for(Incidente singleIncidente: mIncidente) {
                    Log.v(LOG_TAG, singleIncidente.toString());
                }

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e("webData","Error processing Json data");
            }


        }
    }


}
