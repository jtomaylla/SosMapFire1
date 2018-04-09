package com.ecandle.firebase.sosmapfire1.webservices;

import android.net.Uri;
import android.util.Log;

import com.ecandle.firebase.sosmapfire1.models.Tipo_Incidente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.ecandle.example.sosmap.models.Tipo_Incidente;

/**
 * Created by juantomaylla on 26/01/17.
 */

public class GetTipoIncidenteData extends GetRawData {

    private String LOG_TAG = GetTipoIncidenteData.class.getSimpleName();
    private List<Tipo_Incidente> mTipoIncidente;
    //private List<DataModel> mDataModel;
    private Uri mDestinationUri;

    public GetTipoIncidenteData() {
        super(null);
        createAndUpdateUri();
        mTipoIncidente = new ArrayList<Tipo_Incidente>();
    }

    public void execute() {
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdateUri() {

        final String LICENCIA_API_BASE_URL = "http://ecandlemobile.com/Test/tipo_incidente.json";

        mDestinationUri = Uri.parse(LICENCIA_API_BASE_URL).buildUpon()
                .build();

        return mDestinationUri != null;
    }

    public List<Tipo_Incidente> getTipo_Incidente() {
        return mTipoIncidente;
    }

    public void processResult() {


        if(getmDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }
        String TIPO_INCIDENTE_ID = "tipo_incidente_id";
        String NOMBRE_INCIDENTE = "nombre_incidente";
        String TIPO_INCIDENTE_IMG = "tipo_incidente_img";
        //String ANULADO = "anulado";

        //int k = 0;
        try {

            List<Tipo_Incidente> data = new ArrayList<>();

            JSONArray jArray = new JSONArray(getmData());

            // Extract data from json and store into ArrayList as class objects
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                Tipo_Incidente TipoIncidenteObject = new Tipo_Incidente();
                TipoIncidenteObject.imagen = json_data.getString(TIPO_INCIDENTE_IMG);
                TipoIncidenteObject.nombre = json_data.getString(NOMBRE_INCIDENTE);
                TipoIncidenteObject.id = json_data.getInt(TIPO_INCIDENTE_ID);
                data.add(TipoIncidenteObject);
            }
            for(Tipo_Incidente singleTipo_Incidente: mTipoIncidente) {
                Log.v(LOG_TAG, singleTipo_Incidente.toString());
            }

        } catch(JSONException jsone) {
            jsone.printStackTrace();
            Log.e(LOG_TAG,"Error processing Json data");
        }

    }

    public class DownloadJsonData extends DownloadRawData {

        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();

        }

        protected String doInBackground(String... params) {
            String[] par = { mDestinationUri.toString() };
            return super.doInBackground(par);
        }

    }


}
