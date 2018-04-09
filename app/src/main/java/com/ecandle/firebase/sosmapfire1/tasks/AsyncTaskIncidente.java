package com.ecandle.firebase.sosmapfire1.tasks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ecandle.firebase.sosmapfire1.adapters.IncidenteAdapter;
import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.models.DataIncidente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juantomaylla on 1/02/17.
 */

public class AsyncTaskIncidente extends android.os.AsyncTask<String, String, List<DataIncidente>> {
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVIncidente;
    private IncidenteAdapter mAdapter;
    public DatabaseHelper db;

    HttpURLConnection conn;
    URL url = null;
    Context mycontext;
    //ProgressDialog pdLoading = new ProgressDialog(mycontext);


    public AsyncTaskIncidente(Context context) {
        super();
        mycontext = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
//        pdLoading.setMessage("\tLoading...");
//        pdLoading.setCancelable(false);
//        pdLoading.show();

    }

    @Override
    protected List<DataIncidente> doInBackground(String... params) {

        try {

            // Enter URL address where your json file resides
            // Even you can make call to php file which returns json data
            url = new URL("http://ecandlemobile.com/Test/incidente.json");

        } catch (MalformedURLException eURL) {
            // TODO Auto-generated catch block
            eURL.printStackTrace();
            //return e.toString();
        }
        try {

            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");

            // setDoOutput to true as we recieve data from json file
            conn.setDoOutput(true);

        } catch (IOException eIO) {
            // TODO Auto-generated catch block
            eIO.printStackTrace();
            //return e1.toString();
        }

        try {

            int response_code = conn.getResponseCode();
            List<DataIncidente> data = new ArrayList<>();
            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                //pdLoading.dismiss();
                // Process data
                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataIncidente incidenteData = new DataIncidente();
                    incidenteData.tipo_incidente_img = json_data.getString("tipo_incidente_img");
                    incidenteData.tipo_incidente = json_data.getString("tipo_incidente");
                    incidenteData.descripcion_incidente = json_data.getString("descripcion_incidente");
                    incidenteData.fecha_incidente = json_data.getString("fecha_incidente");
                    incidenteData.hora_incidente = json_data.getString("hora_incidente");
                    incidenteData.latitud = json_data.getString("latitud");
                    incidenteData.longitud = json_data.getString("longitud");
                    data.add(incidenteData);
                }

                //
                return (data);

            } else {

                return (null);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            //return e.toString();
        }   catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                //return e1.toString();}

        } finally {
            conn.disconnect();
        }


        return null;
    }

    //@Override
//    protected void onPostExecute(String result) {
//
//        //this method will be running on UI thread
//
//        pdLoading.dismiss();
//        List<DataIncidente> data = new ArrayList<>();
//
//        pdLoading.dismiss();
//        try {
//
//            JSONArray jArray = new JSONArray(result);
//
//            // Extract data from json and store into ArrayList as class objects
//            for (int i = 0; i < jArray.length(); i++) {
//                JSONObject json_data = jArray.getJSONObject(i);
//                DataIncidente incidenteData = new DataIncidente();
//                incidenteData.tipo_incidente_img = json_data.getString("tipo_incidente_img");
//                incidenteData.tipo_incidente = json_data.getString("tipo_incidente");
//                incidenteData.descripcion_incidente = json_data.getString("descripcion_incidente");
//                incidenteData.fecha_incidente = json_data.getString("fecha_incidente");
//                incidenteData.hora_incidente = json_data.getString("hora_incidente");
//                incidenteData.latitud = json_data.getString("latitud");
//                incidenteData.longitud = json_data.getString("longitud");
//                data.add(incidenteData);
//            }
//
//            // Setup and Handover data to recyclerview
//            //mRVIncidente = (RecyclerView) findViewById(R.id.ListaIncindencias);
//            mAdapter = new IncidenteAdapter(mycontext, data);
//            mRVIncidente.setAdapter(mAdapter);
//            mRVIncidente.setLayoutManager(new LinearLayoutManager(mycontext));
//
//        } catch (JSONException e) {
//            Toast.makeText(mycontext, e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//    }

}