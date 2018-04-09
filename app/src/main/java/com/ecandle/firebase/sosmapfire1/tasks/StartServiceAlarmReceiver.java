package com.ecandle.firebase.sosmapfire1.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;

import com.ecandle.firebase.sosmapfire1.services.NotificationService;

//import com.ecandle.example.sosmap.services.NotificationService;

// make sure we use a WakefulBroadcastReceiver so that we acquire a partial wakelock
public class StartServiceAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "StartServiceAlarmReceiver";
    private static PowerManager.WakeLock wakeLock = null;
    private static final String LOG_TAG = "sosmap";

    /**
     * Method used to share the <code>WakeLock</code> created by this
     * <code>BroadcastReceiver</code>.
     * Note: this method is <code>synchronized</code> as it lazily creates
     * the <code>WakeLock</code>
     *
     * @param 		ctx			The <code>Context</code> object acquiring the
     * 							lock.
     */
    public static synchronized void acquireLock(Context ctx){
        if (wakeLock == null){
            PowerManager mgr = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
            wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOG_TAG);
            wakeLock.setReferenceCounted(true);
        }
        wakeLock.acquire();
    }

    /**
     * Method used to release the shared <code>WakeLock</code>.
     */
    public static synchronized void releaseLock(){
        if (wakeLock != null){
            wakeLock.release();
        }
    }

    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        acquireLock(context);
        //context.startService(new Intent(context, LocationService.class));
        Log.i(LOG_TAG, "onReceive");
        Intent service1 = new Intent(context, NotificationService.class);
        service1.setData((Uri.parse("custom://"+ System.currentTimeMillis())));
        context.startService(service1);

    }

}
