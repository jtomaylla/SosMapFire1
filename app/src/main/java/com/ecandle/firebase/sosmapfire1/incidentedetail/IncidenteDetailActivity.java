/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.ecandle.firebase.sosmapfire1.incidentedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ecandle.firebase.sosmapfire1.R;

//import com.ecandle.example.sosmap.R;

/**
 * Displays incidente details screen.
 */
public class IncidenteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_INCIDENTE_ID = "INCIDENTE_ID";
    public static final String EXTRA_INCIDENTE = "INCIDENTE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested incidente id
//        String incidenteId = getIntent().getStringExtra(EXTRA_INCIDENTE_ID);
//        intent.putExtra("Descripcion", incidente.getDescripcion_incidente());
//        intent.putExtra("Fecha", incidente.getFecha_incidente());
//        intent.putExtra("Hora", incidente.getHora_incidente());
//        intent.putExtra("Tipo_incidente", incidente.getTipo_incidente();
//        intent.putExtra("Tipo_incidente_img", incidente.getTipo_incidente_img());
        String Descripcion = getIntent().getStringExtra("Descripcion");
        String Fecha = getIntent().getStringExtra("Fecha");
        String Hora = getIntent().getStringExtra("Hora");
        String Tipo_incidente = getIntent().getStringExtra("Tipo_incidente");
        String Tipo_incidente_img = getIntent().getStringExtra("Tipo_incidente_img");

        initFragment(IncidenteDetailFragment.newInstance(Tipo_incidente,Tipo_incidente_img,Descripcion,Fecha,Hora));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(Fragment detailFragment) {
        // Add the IncidentesDetailFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }

}
