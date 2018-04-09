/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ecandle.firebase.sosmapfire1.incidentes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.adapters.IncidenteAdapter;
import com.ecandle.firebase.sosmapfire1.addincidente.AddIncidenteActivity;
import com.ecandle.firebase.sosmapfire1.models.DataIncidente;
import com.ecandle.firebase.sosmapfire1.models.Incidente;

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
 * Display a grid of {@link DataIncidente}s
 */
public class IncidentesFragment extends Fragment {
    private String LOG_TAG = IncidentesFragment.class.getSimpleName();
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVIncidente;
    private IncidenteAdapter mAdapter;
    private View root;
    public IncidentesFragment() {
        // Requires empty public constructor
    }

    public static IncidentesFragment newInstance() {
        return new IncidentesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mAdapter = new IncidenteAdapter(new ArrayList<DataIncidente>(0), mItemListener);
        //mActionsListener = new IncidentesPresenter(Injection.provideIncidentesRepository(), this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_incidentes, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.ListaIncindencias);
        recyclerView.setAdapter(mAdapter);

        //int numColumns = getContext().getResources().getInteger(R.integer.num_incidentes_columns);

        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddIncidenteActivity.class);
                startActivity(i);

            }
        });
        new loadDataTask().execute();
        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mActionsListener.loadIncidentes(true);
                new loadDataTask().execute();
            }
        });
        return root;
    }
    /**
     * Listener for clicks on incidentes in the RecyclerView.
     */
    IncidenteItemListener mItemListener = new IncidenteItemListener() {
        @Override
        public void onIncidenteClick(Incidente clickedIncidente) {
            //mActionsListener.openIncidenteDetails(clickedIncidente);
        }
    };

    public interface IncidenteItemListener {

        void onIncidenteClick(Incidente clickedIncidente);
    }
    private class loadDataTask extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://ecandlemobile.com/Test/incidente.json");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

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

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataIncidente> data = new ArrayList<>();

            pdLoading.dismiss();
            try {

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

                // Setup and Handover data to recyclerview
                mRVIncidente = (RecyclerView) root.findViewById(R.id.ListaIncindencias);
                mAdapter = new IncidenteAdapter(getContext(), data);
                mRVIncidente.setAdapter(mAdapter);
                mRVIncidente.setLayoutManager(new LinearLayoutManager(getContext()));

            } catch (JSONException e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }

}
