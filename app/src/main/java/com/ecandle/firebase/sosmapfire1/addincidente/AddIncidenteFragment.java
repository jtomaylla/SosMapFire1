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

package com.ecandle.firebase.sosmapfire1.addincidente;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import com.ecandle.firebase.sosmapfire1.activities.SignInActivity;
import com.ecandle.firebase.sosmapfire1.helper.DatabaseHelper;
import com.ecandle.firebase.sosmapfire1.incidentes.IncidentesActivity;
import com.ecandle.firebase.sosmapfire1.models.DataIncidente1;
import com.ecandle.firebase.sosmapfire1.services.TrackGPS;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.ecandle.firebase.sosmapfire1.editincidente.EditIncidenteFragment.INCIDENTES_LOCATION;

/**
 * Main UI for the add incidente screen. Users can enter a incidente title and description. Images can be
 * added to incidentes by clicking on the options menu.
 */
public class AddIncidenteFragment extends Fragment {
    private String LOG_TAG = AddIncidenteFragment.class.getSimpleName();
    static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID = 2;
    public static final String INCIDENTES_CHILD = "incidente";
    public static final String TIPO_INCIDENTE_CHILD = "tipo_incidente";
    public static final String TIPO_VIA_CHILD = "tipo_via";
    public static final String TIPO_FUENTE_CHILD = "tipo_fuente";

    //private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";
    private static final String LOADING_IMAGE_URL = "http://ecandlemobile.com/Test/images/";
    public static final String ANONYMOUS = "anonymous";

    private String mUsername;
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
    private TextView tvw_latitude;
    private EditText edt_latitude;
    private TextView tvw_longitude;
    private EditText edt_longitude;
    private TextView tvwSourceType;
    private EditText edt_source;
    private FloatingActionButton mFabSaveIncident;
    private ImageView mAddIncidenteImageView;

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
    String tipoIncidenteImgURL;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase1;
    private String mIncidente_id;

    private String value;
    private Map<String, String> urls;

    private static final int REQUEST_IMAGE = 2;
    private static  final int PERMISSION_CODE_1 = 23;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    private TrackGPS gps;
    private String mLatitude;
    private String mLongitude;
    private Intent intent;
    private Bitmap imageBitmap;
    private ImageView mImageLabel;

    public static AddIncidenteFragment newInstance() {
        return new AddIncidenteFragment();
    }

    public AddIncidenteFragment() {
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

        mUsername = ANONYMOUS;
// Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(getContext(), SignInActivity.class));
            //finish();
            //return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                //mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }


        spnIncidentType = (Spinner) root.findViewById(R.id.spnIncidentType);
        spnViaType = (Spinner) root.findViewById(R.id.spnViaType);
        edt_description = (EditText)root.findViewById(R.id.edt_description);
        //edt_source = (EditText)root.findViewById(R.id.edt_source);
        tvwIncidentDate = (TextView) root.findViewById(R.id.tvwIncidentDate);

        tvwIncidentTime = (TextView) root.findViewById(R.id.tvwIncidentTime);

        edt_latitude = (EditText)root.findViewById(R.id.edt_latitude);
        tvw_latitude = (TextView) root.findViewById(R.id.tvw_latitude);
        edt_longitude = (EditText)root.findViewById(R.id.edt_longitude);
        tvw_longitude = (TextView) root.findViewById(R.id.tvw_longitude);
        spnSourceType = (Spinner) root.findViewById(R.id.spnSourceType);
        mImageLabel = (ImageView) root.findViewById(R.id.imageLabel);

        getCurrentLocation();
        setCurrentDateOnView();
        addListenerOntvwIncidentDate();
        setCurrentTimeOnView();
        addListenerOntvwIncidentTime();

        //btnSaveIncident = (Button) root.findViewById(btnSaveIncident);

        loadIncidentTypeSpinner();
        loadViaTypeSpinner();
        loadFuenteTypeSpinner();
        // Spinner click listener
        spnIncidentType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        selIncidentType = parent.getItemAtPosition(position).toString();

                        Log.i(LOG_TAG," pos: "+ selIncidentType + " valor:" + parent.getItemAtPosition(position));

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
                                               android.view.View v, int position, long id) {
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
                                               android.view.View v, int position, long id) {
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
                // Select image for image message on click.
//                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_IMAGE);
                onLaunchCamera();

            }
        });
        
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }
    private void SaveIncident(){

        tipoIncidenteImgURL = LOADING_IMAGE_URL+urls.get(selIncidentType.toString());
        Log.i(LOG_TAG," tipoIncidenteImgURL : "+tipoIncidenteImgURL);

        String incidentDate = tvwIncidentDate.getText().toString();
        Log.i(LOG_TAG,"incidentDate:"+incidentDate);
        String incidentTime = tvwIncidentTime.getText().toString();

        if (mLatitude == null)  mLatitude="-77.0111";

        if (mLongitude == null) mLongitude="-12.1457";

        Log.i(LOG_TAG,"Lat y Long:"+mLatitude+" y " + mLongitude);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(INCIDENTES_CHILD);
        mIncidente_id = mFirebaseDatabase.push().getKey();

        DataIncidente1 dataIncidente = new DataIncidente1(
                selIncidentType.toString(),
                tipoIncidenteImgURL,
                selViaType.toString(),
                edt_description.getText().toString(),
                mLatitude,
                mLongitude,
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
        Toast.makeText(getContext(), "Incident Saved ", Toast.LENGTH_SHORT).show();

    }

    /**
     * Incidente data change listener
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
                    Log.e(LOG_TAG, "Incident data is null!");
                    return;
                }

                Log.e(LOG_TAG, "Incident data is changed!" + di.tipo_incidente + ", " + di.descripcion_incidente);

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

    private void loadIncidentTypeSpinner() {
        final ArrayList<String> inc_names = new ArrayList<>();
        urls = new HashMap<String, String>();

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, inc_names);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnIncidentType.setAdapter(dataAdapter);

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

                dataAdapter.notifyDataSetChanged();
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
                dataAdapter.notifyDataSetChanged();
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
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, via_names);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnViaType.setAdapter(dataAdapter);

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

                dataAdapter.notifyDataSetChanged();
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
                dataAdapter.notifyDataSetChanged();
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
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, names);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSourceType.setAdapter(dataAdapter);
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

                dataAdapter.notifyDataSetChanged();
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
                dataAdapter.notifyDataSetChanged();
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

    private void getCurrentLocation(){
        gps = new TrackGPS(getContext());
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //requestpermisions();
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(getContext(), Manifest.permission.ACCESS_FINE_LOCATION, Toast.LENGTH_SHORT).show();

                    requestpermisions();

                } else {

                    // No explanation needed, we can request the permission.

                    requestpermisions();

                }
            }

            if(gps.canGetLocation()){

                mLatitude = String.valueOf(gps.latitude);
                mLongitude = String.valueOf(gps.longitude);
                Toast.makeText(getContext(),"Longitude:"+ mLongitude +"\nLatitude:"+mLatitude, Toast.LENGTH_SHORT).show();

            }
            else {

                gps.showSettingsAlert();
            }

        } else {
            if(gps.canGetLocation()){

                mLatitude = String.valueOf(gps.latitude);
                mLongitude = String.valueOf(gps.longitude);
                Toast.makeText(getContext(),"Longitude:"+ mLongitude +"\nLatitude:"+mLatitude, Toast.LENGTH_SHORT).show();

            }
            else
            {

                gps.showSettingsAlert();
            }
        }
        edt_latitude.setText(mLatitude);
        edt_longitude.setText(mLongitude);

    }
    public void requestpermisions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_1);

    }
    //@Override


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
