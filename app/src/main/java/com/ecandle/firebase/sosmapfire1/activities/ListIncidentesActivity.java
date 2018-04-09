package com.ecandle.firebase.sosmapfire1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.adapters.IncidenteAdapter;
import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.models.DataIncidente;

import java.util.List;

//import com.ecandle.example.sosmap.R;
//import com.ecandle.example.sosmap.adapters.IncidenteAdapter;
//import com.ecandle.example.sosmap.helper.DatabaseHelper;
//import com.ecandle.example.sosmap.models.DataIncidente;

public class ListIncidentesActivity extends AppCompatActivity {
    // Database Helper

    private DatabaseHelper db;
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    private String LOG_TAG = ListIncidentesActivity.class.getSimpleName();
    private RecyclerView mRVIncidente;
    private IncidenteAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
        db = new DatabaseHelper(getApplicationContext());
        if (!db.tableExists("Incidente",true)) {
            Toast.makeText(this, "tabla Incidente no existe", Toast.LENGTH_SHORT).show();
            //db.loadData();
        } else {
            loadListIncidentes();
        }

    }

    private void loadListIncidentes(){
        try {
            db = new DatabaseHelper(getApplicationContext());

            List<DataIncidente> data = db.getListIncidentes();

            // Setup and Handover data to recyclerview
            mRVIncidente = (RecyclerView) findViewById(R.id.ListaIncindencias);
            mAdapter = new IncidenteAdapter(ListIncidentesActivity.this, data);
            mRVIncidente.setAdapter(mAdapter);
            mRVIncidente.setLayoutManager(new LinearLayoutManager(ListIncidentesActivity.this));

        }
        catch (Exception e) {

            e.printStackTrace();

        }


    }

    public static class Item {
        private final int value;

        public Item(int value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(value);
        }
    }



}
