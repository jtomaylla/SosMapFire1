package com.ecandle.firebase.sosmapfire1.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ecandle.firebase.sosmapfire1.R;
import com.nispok.snackbar.Snackbar;

//import com.ecandle.example.sosmap.R;

public class MapaActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private WebView fweBview;
    Context con;
    ProgressBar mapprogress;
    public static String lat = "-12.1087667";
    public static String lng = "-77.039364";
    String latitud,longitud;
    String url = "https://maps.google.com/?q="+ lat+","+ lng+"";

    //String url = "https://maps.google.com/?saddr="+AllConstants.UPlat+","+AllConstants.UPlng+"&daddr="+ AllConstants.lat+","+ AllConstants.lng+"";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mapa);
        Bundle data = getIntent().getExtras();
        if (!data.getString("lat").isEmpty() && !data.getString("lon").isEmpty()  )
            url = "https://maps.google.com/?q="+ data.getString("lat")+","+ data.getString("lon")+"";
            con = this;
        mapprogress = (ProgressBar) findViewById(R.id.MprogressBar);

        try {
            updateWebView(url);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private class HelloWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            mapprogress.setVisibility(View.GONE);
        }
    }

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && fweBview.canGoBack()) {
//			fweBview.goBack();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//
//	}

    private long lastBackPressTime = 0;
    @Override
    public void onBackPressed() {

        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            showSnack(getResources().getString(R.string.confirm_back));
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
    private void updateWebView(String url) {
        // TODO Auto-generated method stub

        fweBview = (WebView) findViewById(R.id.mapView);
        fweBview.getSettings().setJavaScriptEnabled(true);
        fweBview.getSettings().setDomStorageEnabled(true);
        fweBview.loadUrl(url);

        fweBview.setWebViewClient(new HelloWebViewClient());

    }

    private void showSnack(String msg) {
        Snackbar.with(getApplicationContext()) // context
                .text(msg) // text to display
                .show(this); // activity where it is displayed
    }
}