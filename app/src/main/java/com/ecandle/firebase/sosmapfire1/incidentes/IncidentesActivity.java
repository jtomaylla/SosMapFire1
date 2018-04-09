package com.ecandle.firebase.sosmapfire1.incidentes;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.activities.ListIncidentesActivity;
import com.ecandle.firebase.sosmapfire1.activities.ListViewActivity;
import com.ecandle.firebase.sosmapfire1.activities.MapsActivity2;
import com.ecandle.firebase.sosmapfire1.activities.SettingsActivity;
import com.ecandle.firebase.sosmapfire1.activities.SignInActivity;
import com.ecandle.firebase.sosmapfire1.tasks.StartServiceAlarmReceiver;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class IncidentesActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private String LOG_TAG = IncidentesActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    public static final String ANONYMOUS = "anonymous";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

//    // Firebase instance variables
//    private DatabaseReference mFirebaseDatabaseReference;
//    private FirebaseRecyclerAdapter<DataIncidente, IncidenteViewHolder>
//            mFirebaseAdapter;

    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

//    private ProgressBar mProgressBar;
//    private RecyclerView mMessageRecyclerView;
//    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (null == savedInstanceState) {
            initFragment(IncidentesFragment1.newInstance());
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    public DataIncidente getItem(int position) {
//        return data.get(position);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_out_menu:
//                mFirebaseAuth.signOut();
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                mUsername = ANONYMOUS;
//                startActivity(new Intent(this, SignInActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.alarm_menu:
                                Toast.makeText(IncidentesActivity.this, "You have selected Alarm Menu", Toast.LENGTH_SHORT).show();
                                //showSetAlarmlDialog(getString(R.string.alarm));
                                showAlarmSettingDialog(getString(R.string.alarm));
                                return true;
                            case R.id.load_incident_menu:
                                //Toast.makeText(this, "You have selected Load Incident Menu", Toast.LENGTH_SHORT).show();
                                //showLoadIncidentDialog(getString(R.string.load_incident_type));
                                return true;
                            case R.id.list_tipo_incidente_menu:
                                //Toast.makeText(this, "You have selected Load source Menu", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IncidentesActivity.this, ListViewActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.list_incidentes_menu:
                                Intent intent1 = new Intent(IncidentesActivity.this, ListIncidentesActivity.class);
                                startActivity(intent1);
                                //Toast.makeText(this, "You have selected Create Menu", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.configuration_menu:
                                //Toast.makeText(this, "You have selected Configuration Menu", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(IncidentesActivity.this, SettingsActivity.class);
                                startActivity(i);
                                return true;
                            case R.id.sign_out_menu:
                                mFirebaseAuth.signOut();
                                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                                mUsername = ANONYMOUS;
                                startActivity(new Intent(IncidentesActivity.this, SignInActivity.class));
                                return true;
                            case R.id.show_map_menu:
                                startActivity(new Intent(IncidentesActivity.this, MapsActivity2.class));
                                return true;

                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void initFragment(Fragment notesFragment) {
        // Add the IncidentesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, notesFragment);
        transaction.commit();
    }

    public void setStartNotificationAt(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Intent reminderIntent = new Intent(this,StartServiceAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, reminderIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);


        Log.i(LOG_TAG, ".setStartNotificationAt:"+ String.valueOf(hour) +":"+ String.valueOf(minute));

    }
    private void setSendNotificationEvery(int minutes){

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this,StartServiceAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        //alarmManager.cancel(pendingIntent);
        long intervalMillSecs = minutes * 60 * 1000;
        // Hopefully your alarm will have a lower frequency than this!
        // Wake up the device to fire the alarm in 0 minutes, and every 1 hour
        //intervalMillSecs = AlarmManager.INTERVAL_HOUR;
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                intervalMillSecs, pendingIntent);

//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis(),
//                intervalMillSecs, pendingIntent);


        Log.i(LOG_TAG,".setSendNotificationEvery:"+ intervalMillSecs +" Millsec");
    }


    public void showAlarmSettingDialog(final String textMenu) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(textMenu);

        // set dialog message
        alertDialogBuilder
                .setMessage("Iniciar Notificaciones en intervalos de X minutos?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Integer intMinutes = Integer.valueOf(prefs.getString("noti_frequency", ""));

                        setSendNotificationEvery(intMinutes);
                        //setStartNotificationAt(0,0);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}
