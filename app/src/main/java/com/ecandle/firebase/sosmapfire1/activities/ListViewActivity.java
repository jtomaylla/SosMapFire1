package com.ecandle.firebase.sosmapfire1.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.adapters.AdapterTipoIncidente;
import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;

//import com.ecandle.example.sosmap.R;
//import com.ecandle.example.sosmap.adapters.AdapterTipoIncidente;
//import com.ecandle.example.sosmap.helper.DatabaseHelper;

public class ListViewActivity extends AppCompatActivity {
    // Database Helper

    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        db = new DatabaseHelper(getApplicationContext());
        if (!db.tableExists("Tipo_Incidente",true)) {
            Toast.makeText(this, "tabla Tipo_Incidente no existe", Toast.LENGTH_SHORT).show();
            //db.loadData();
        } else {
            loadListView();
        }
    }

    private void loadListView(){
        try {
            db = new DatabaseHelper(getApplicationContext());

            Cursor tiCursor = db.getCursorAllTipo_Incidentes();

            final TextView textView = (TextView) findViewById(R.id.text);
            textView.setBackgroundColor(Color.LTGRAY);
            textView.setVisibility(View.GONE);

// Find ListView to populate
            ListView lvItems = (ListView) findViewById(R.id.lvItems);
// Setup cursor adapter using cursor from last step
            AdapterTipoIncidente todoAdapter = new AdapterTipoIncidente(this, tiCursor);
// Attach cursor adapter to the ListView
            lvItems.setAdapter(todoAdapter);

            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    textView.setText(String.valueOf(position));
                    textView.setVisibility(View.VISIBLE);
                }
            });

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
