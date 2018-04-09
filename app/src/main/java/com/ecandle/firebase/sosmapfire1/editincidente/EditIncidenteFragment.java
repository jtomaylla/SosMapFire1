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

package com.ecandle.firebase.sosmapfire1.editincidente;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.incidentes.IncidentesActivity;
import com.ecandle.firebase.sosmapfire1.models.DataIncidente1;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Main UI for the add incidente screen. Users can enter a incidente title and description. Images can be
 * added to incidentes by clicking on the options menu.
 */
public class EditIncidenteFragment extends Fragment {
    private String LOG_TAG = EditIncidenteFragment.class.getSimpleName();
    static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID = 2;
    public static final String INCIDENTES_CHILD = "incidente";
    public static final String TIPO_INCIDENTE_CHILD = "tipo_incidente";
    public static final String TIPO_VIA_CHILD = "tipo_via";
    public static final String TIPO_FUENTE_CHILD = "tipo_fuente";
    public static final String INCIDENTES_LOCATION = "incidente_location";
    private static final String LOADING_IMAGE_URL = "http://ecandlemobile.com/Test/images/";
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private EditText edt_description;
    private Spinner spnIncidentType;
    private Spinner spnViaType;
    private Spinner spnSourceType;
    //private Button btnSaveIncident;
    private TextView tvwIncidentType;
    private TextView tvwViaType;
    private TextView tvw_description;
    private TextView tvwIncidentDate;
    private TextView tvwIncidentTime;
    private TextView tvwSourceType;
    private EditText edt_source;
    private FloatingActionButton mFabSaveIncident;
    private EditText edt_latitude;
    private EditText edt_longitude;

    private int year;
    private int month;
    private int day;

    //	private TimePicker timePicker1;
    private int hour;
    private int minute;
    private DatabaseHelper db;

    String selIncidentType;
    String selViaType;
    String selSourceType;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String mIncidente_id;

    //private ArrayList<String> names = new ArrayList<>();

    //private ArrayAdapter<String> dataAdapter;

    private String value;

    private String tipo_incidente;
    private String tipo_incidente_img;
    private String tipo_via;
    private String descripcion_incidente;
    private String latitud;
    private String longitud;
    private String fecha_incidente;
    private String hora_incidente;
    private String evidencia;
    private String foto_url;
    private ArrayAdapter<String> inc_Adapter;
    private ArrayAdapter<String> via_Adapter;
    private ArrayAdapter<String> fue_Adapter;

    private ImageView mImageLabel;
    private ImageView mAddIncidenteImageView;
    private Bitmap imageBitmap;
    String tipoIncidenteImgURL;
    private Map<String, String> urls;



    public static EditIncidenteFragment newInstance() {
        return new EditIncidenteFragment();
    }

    public EditIncidenteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addincidente, container, false);
        mIncidente_id = this.getArguments().getString("incidente_id");


        spnIncidentType = (Spinner) root.findViewById(R.id.spnIncidentType);
        spnViaType = (Spinner) root.findViewById(R.id.spnViaType);
        edt_description = (EditText)root.findViewById(R.id.edt_description);
        //edt_source = (EditText)root.findViewById(R.id.edt_source);
        tvwIncidentDate = (TextView) root.findViewById(R.id.tvwIncidentDate);
        tvwIncidentTime = (TextView) root.findViewById(R.id.tvwIncidentTime);
        edt_latitude = (EditText)root.findViewById(R.id.edt_latitude);
        edt_longitude = (EditText)root.findViewById(R.id.edt_longitude);
        tvwIncidentTime = (TextView) root.findViewById(R.id.tvwIncidentTime);
        spnSourceType = (Spinner) root.findViewById(R.id.spnSourceType);
        mImageLabel = (ImageView) root.findViewById(R.id.imageLabel);

        setCurrentDateOnView();
        addListenerOntvwIncidentDate();
        setCurrentTimeOnView();
        addListenerOntvwIncidentTime();

        //btnSaveIncident = (Button) root.findViewById(btnSaveIncident);

        loadIncidentTypeSpinner();
        loadViaTypeSpinner();
        loadFuenteTypeSpinner();
        // Loading Incidente Data
        loadIncidentData(mIncidente_id);

        // Spinner click listener
        spnIncidentType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View v, int position, long id) {
                        selIncidentType = parent.getItemAtPosition(position).toString();
                        Log.i("Incident"," pos: "+ selIncidentType + " valor:" + parent.getItemAtPosition(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Seleccione un Incidente!!", Toast.LENGTH_SHORT).show();
                    }
                });

        spnViaType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View v, int position, long id) {
                        selViaType = parent.getItemAtPosition(position).toString();
                        Log.i("Via"," pos: "+ selViaType + " valor:" + parent.getItemAtPosition(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Seleccione una Via!!", Toast.LENGTH_SHORT).show();
                    }
                });

        spnSourceType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View v, int position, long id) {
                        selSourceType = parent.getItemAtPosition(position).toString();
                        Log.i("Source"," pos: "+ selSourceType + " valor:" + parent.getItemAtPosition(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Seleccione una Source!!", Toast.LENGTH_SHORT).show();
                    }
                });

        // Set up floating action button
        mFabSaveIncident =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_save_incidente);
        mFabSaveIncident.setImageResource(R.drawable.ic_save);
        mFabSaveIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveIncident();
            }
        });
        mAddIncidenteImageView = (ImageView) root.findViewById(R.id.addIncidenteImageView);
        mAddIncidenteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLaunchCamera();

            }
        });

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }
    private void SaveIncident(){
        tipoIncidenteImgURL = LOADING_IMAGE_URL+urls.get(selIncidentType.toString());
        String incidentDate = tvwIncidentDate.getText().toString();
        Log.i(LOG_TAG,"incidentDate:"+incidentDate);

        String incidentTime = tvwIncidentTime.getText().toString();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(INCIDENTES_CHILD);

        //mIncidente_id = mFirebaseDatabase.push().getKey();
        DataIncidente1 dataIncidente = new DataIncidente1(
                selIncidentType.toString(),
                tipoIncidenteImgURL,
                selViaType.toString(),
                edt_description.getText().toString(),
                edt_latitude.getText().toString(),
                edt_longitude.getText().toString(),
                tvwIncidentDate.getText().toString(),
                tvwIncidentTime.getText().toString(),
                selSourceType.toString(),
                "foto_url"
        );
        mFirebaseDatabase.child(mIncidente_id).setValue(dataIncidente);

        //Saving geofire location
        GeoFire geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference(INCIDENTES_LOCATION));
        geoFire.setLocation(mIncidente_id,
                new GeoLocation(Double.valueOf(edt_longitude.getText().toString()),
                        Double.valueOf(edt_latitude.getText().toString())));


        if (imageBitmap != null) {
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }

        addIncidenteChangeListener();

        startActivity(new Intent(getContext(), IncidentesActivity.class));

        Toast.makeText(getContext(), "Incident Updated ", Toast.LENGTH_SHORT).show();
        //finish();

    }

    /**
     * User data change listener
     */
    private void addIncidenteChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(mIncidente_id ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User user = dataSnapshot.getValue(User.class);
                DataIncidente1 di = dataSnapshot.getValue(DataIncidente1.class);
                // Check for null
                if (di == null) {
                    Log.e(LOG_TAG, "User data is null!");
                    return;
                }

                Log.e(LOG_TAG, "User data is changed!" + di.tipo_incidente + ", " + di.descripcion_incidente);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(LOG_TAG, "Failed to read user", error.toException());
            }
        });
    }
    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvwIncidentDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("/").append(month + 1).append("/")
                .append(year).append(" "));

    }
    public void addListenerOntvwIncidentDate() {
        tvwIncidentDate.setOnTouchListener(new View.OnTouchListener(){

            @SuppressWarnings("deprecation")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getActivity().showDialog(DATE_DIALOG_ID);
                return true;
            }


        });


        tvwIncidentDate.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {

                getActivity().showDialog(DATE_DIALOG_ID);

            }

        });

    }

    // display current date
    public void setCurrentTimeOnView() {


        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // set current time into textview
        tvwIncidentTime.setText(
                new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)));

    }

    public void addListenerOntvwIncidentTime() {
        tvwIncidentTime.setOnTouchListener(new View.OnTouchListener(){

            @SuppressWarnings("deprecation")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getActivity().showDialog(TIME_DIALOG_ID);
                return true;
            }


        });


        tvwIncidentTime.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {

                getActivity().showDialog(TIME_DIALOG_ID);

            }

        });

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void loadIncidentData(String incidente_id) {
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(INCIDENTES_CHILD).child(incidente_id);

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();
                Log.d(LOG_TAG, map.toString());
                tipo_incidente = map.get("tipo_incidente");
                Log.d(LOG_TAG, "tipo_incidente:" + map.get("tipo_incidente"));
                tipo_incidente_img = map.get("tipo_incidente_img");

                tipo_via = map.get("tipo_via");
                descripcion_incidente = map.get("descripcion_incidente");
                latitud = map.get("latitud");
                longitud = map.get("longitud");
                fecha_incidente = map.get("fecha_incidente");
                hora_incidente = map.get("hora_incidente");
                evidencia = map.get("evidencia");
                foto_url = map.get("foto_url");
                if (!tipo_incidente.equals(null)) {
                    int spinnerPosition = inc_Adapter.getPosition(tipo_incidente);
                    spnIncidentType.setSelection(spinnerPosition);
                }
                if (!tipo_via.equals(null)) {
                    int spinnerPosition = via_Adapter.getPosition(tipo_via);
                    spnViaType.setSelection(spinnerPosition);
                }
                if (!evidencia.equals(null)) {
                    int spinnerPosition = fue_Adapter.getPosition(evidencia);
                    spnSourceType.setSelection(spinnerPosition);
                }
                edt_description.setText(descripcion_incidente);
                tvwIncidentDate.setText(fecha_incidente);
                tvwIncidentTime.setText(hora_incidente);
                edt_latitude.setText(latitud);
                edt_longitude.setText(longitud);
                if (foto_url == null) {
                    mImageLabel.setImageDrawable(ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    try {
                        Bitmap imageBitmap = decodeFromFirebaseBase64(foto_url);
                        mImageLabel.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void loadIncidentTypeSpinner() {
        final ArrayList<String> inc_names = new ArrayList<>();
        urls = new HashMap<String, String>();
        // Creating adapter for spinner
        inc_Adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, inc_names);

        // Drop down layout style - list view with radio button
        inc_Adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnIncidentType.setAdapter(inc_Adapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TIPO_INCIDENTE_CHILD);
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("nombre_incidente")){
                        value = postSnapshot.getValue(String.class).toString();
                        inc_names.add(value);
                    }
                    if (postSnapshot.getKey().equals("tipo_incidente_img")){
                        String valUrl = postSnapshot.getValue(String.class).toString();
                        urls.put(value,valUrl);
                    }
                }

                inc_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("nombre_incidente")){
                        value = postSnapshot.getValue(String.class).toString();
                        inc_names.remove(value);
                    }
                }
                inc_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadViaTypeSpinner() {
        final ArrayList<String> via_names = new ArrayList<>();
        // Creating adapter for spinner
        via_Adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, via_names);

        // Drop down layout style - list view with radio button
        via_Adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnViaType.setAdapter(via_Adapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TIPO_VIA_CHILD);
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("nombre_via")){
                        value = postSnapshot.getValue(String.class).toString();
                        via_names.add(value);
                    }
                }

                via_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("nombre_via")){
                        value = postSnapshot.getValue(String.class).toString();
                        via_names.remove(value);
                    }
                }
                via_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadFuenteTypeSpinner() {
        final ArrayList<String> names = new ArrayList<>();
        // Creating adapter for spinner
        fue_Adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, names);

        // Drop down layout style - list view with radio button
        fue_Adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSourceType.setAdapter(fue_Adapter);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TIPO_FUENTE_CHILD);
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("nombre_fuente")){
                        value = postSnapshot.getValue(String.class).toString();
                        names.add(value);
                    }
                }

                fue_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("nombre_fuente")){
                        value = postSnapshot.getValue(String.class).toString();
                        names.remove(value);
                    }
                }
                fue_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            tvwIncidentDate.setText(new StringBuilder().append(day)
                    .append("/").append(month + 1).append("/").append(year)
                    .append(" "));

        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            tvwIncidentTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)));

        }

    };

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult New: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                mImageLabel.setImageBitmap(imageBitmap);
            }

        }
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(INCIDENTES_CHILD)
                .child(mIncidente_id)
                .child("foto_url");
        ref.setValue(imageEncoded);
    }
}
