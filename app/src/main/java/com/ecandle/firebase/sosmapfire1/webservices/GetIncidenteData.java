package com.ecandle.firebase.sosmapfire1.webservices;

import android.net.Uri;
import android.util.Log;

/**
 * Created by juantomaylla on 26/01/17.
 */

public class GetIncidenteData extends GetRawData {

    private String LOG_TAG = GetTipoIncidenteData.class.getSimpleName();
    private Uri mDestinationUri;


    public GetIncidenteData(String strURL) {
        super(null);
        createAndUpdateUri(strURL);

    }

    public void execute() {
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdateUri(String strURL) {

        final String LICENCIA_API_BASE_URL = strURL;

        mDestinationUri = Uri.parse(LICENCIA_API_BASE_URL).buildUpon()
                .build();

        return mDestinationUri != null;
    }


    public void processResult() {


        if(getmDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
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
