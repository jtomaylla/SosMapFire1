package com.ecandle.firebase.sosmapfire1.webservices;

import android.util.Log;

import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.models.Tipo_Via;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juantomaylla on 30/01/17.
 */

public class ProcessTipoVia extends GetIncidenteData {

    DatabaseHelper db;

    private List<Tipo_Via> mTipo_Via;

    public ProcessTipoVia(DatabaseHelper mydb, String strURL) {
        super(strURL);
          db = mydb;
        mTipo_Via = new ArrayList<Tipo_Via>();
    }

    public void execute() {
        //super.execute();
        ProcessData processData = new ProcessData();
        processData.execute();
    }

    public class ProcessData extends DownloadJsonData {

        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            String TIPO_VIA_ID = "tipo_via_id";
            String NOMBRE_VIA = "nombre_via";

            Log.i("webData",webData);
            try {


                JSONArray jArray = new JSONArray(webData);

                // Extract data from json and store into TipoViaObject as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Tipo_Via TipoViaObject = new Tipo_Via();
                    TipoViaObject.nombre = json_data.getString(NOMBRE_VIA);
                    TipoViaObject.anulado = 0;

                    long inc = db.createTipo_Via(TipoViaObject);
                    if (inc<0){
                        Log.e("db.createTipo_Via","Error creating sqlite data");
                    }
                }

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e("webData","Error processing Json data");
            }


        }
    }


}
