package ir.fardan7eghlim.luckylord.controllers;

/**
 * Created by Meysam on 2/16/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;
//import com.squareup.leakcanary.LeakCanary;

import ir.fardan7eghlim.luckylord.services.ClosingService;
import ir.fardan7eghlim.luckylord.utils.Utility;


public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;



    @Override
    public void onCreate() {
        super.onCreate();

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);


        if(Utility.isNetworkAvailable(getApplicationContext())){
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
        }



        mInstance = this;

        Intent i= new Intent(getApplicationContext(), ClosingService.class);
        getApplicationContext().startService(i);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    @Override
    public void onLowMemory() {
        // meysam - code for low memory detection
//        Utility.displayToast(getApplicationContext(), "کمبود مموری!!", Toast.LENGTH_SHORT);
        // This is called when the overall system is running low on memory, and would like
        // actively running processes to tighten their belts.
        super.onLowMemory();
    }

//    @Override
//    public void onTerminate() {
//        // meysam - code for terminating detection
//        // This method is for use in emulated process environments.
//        // It will never be called on a production Android device, where processes
//        // are removed by simply killing them; no user code (including this callback) is executed when doing so.
//        super.onTerminate();
//    }


}