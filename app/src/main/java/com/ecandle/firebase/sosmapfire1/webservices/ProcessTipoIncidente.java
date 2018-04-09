package com.ecandle.firebase.sosmapfire1.webservices;

import android.util.Log;

import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.models.Tipo_Incidente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.ecandle.example.sosmap.helper.DatabaseHelper;
//import com.ecandle.example.sosmap.models.Tipo_Incidente;

/**
 * Created by juantomaylla on 30/01/17.
 */

public class ProcessTipoIncidente extends GetIncidenteData {

    DatabaseHelper db;

    private List<Tipo_Incidente> mTipo_Incidente;

    public ProcessTipoIncidente(DatabaseHelper mydb, String strURL) {
        super(strURL);
          db = mydb;
        mTipo_Incidente = new ArrayList<Tipo_Incidente>();
    }

    public void execute() {
        //super.execute();
        ProcessData processData = new ProcessData();
        processData.execute();
    }

    public class ProcessData extends DownloadJsonData {

        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            String TIPO_INCIDENTE_ID = "tipo_incidente_id";
            String NOMBRE_INCIDENTE = "nombre_incidente";
            String TIPO_INCIDENTE_IMG = "tipo_incidente_img";
            Log.i("webData",webData);
            try {


                JSONArray jArray = new JSONArray(webData);

                // Extract data from json and store into TipoIncidenteObject as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Tipo_Incidente TipoIncidenteObject = new Tipo_Incidente();
                    TipoIncidenteObject.imagen = json_data.getString(TIPO_INCIDENTE_IMG);
                    TipoIncidenteObject.nombre = json_data.getString(NOMBRE_INCIDENTE);
                    TipoIncidenteObject.anulado = 0;

                    long inc = db.createTipo_Incidente(TipoIncidenteObject);
                    if (inc<0){
                        Log.e("db.createTipo_Incidente","Error creating sqlite data");
                    }
                }

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e("webData","Error processing Json data");
            }


        }
    }


}
