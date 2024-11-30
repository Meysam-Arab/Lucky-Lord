package ir.fardan7eghlim.luckylord.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import static android.R.attr.value;

/**
 * Created by Meysam on 08/08/2017.
 */

public class InternetModel  extends AsyncTask<Void, Integer, Void> {

    private Context cntx;
    private boolean isAvailable;
    public InternetModel(Context icntx)
    {
        cntx = icntx;
        isAvailable = false;
    }

    @Override
    protected Void doInBackground(Void... params) {
//        ConnectivityManager cm = (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//
//        if (netInfo != null && netInfo.isConnected()) {
//            // Network is available but check if we can get access from the
//            // network.
//            URL url = null;
//            try {
//                url = new URL("http://www.google.com");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            HttpURLConnection urlc = null;
//            try {
//                urlc = (HttpURLConnection) url
//                        .openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            urlc.setRequestProperty("Connection", "close");
//            urlc.setConnectTimeout(2000); // Timeout 2 seconds.
//            try {
//                urlc.connect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                if (urlc.getResponseCode() == 200) // Successful response.
//                {
//                    isAvailable = true;
//                } else {
//                    isAvailable = false;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            isAvailable = false;
//        }
        try {
//            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

//            if (ipAddr.equals("")) {
//                isAvailable = false;
//            } else {
//                if(InetAddress.getByName("google.com").isReachable(2000))
//                    isAvailable = true;
//                else
//                    isAvailable = false;
//            }
            if(InetAddress.getByName("http://google.com").isReachable(2000))
                isAvailable = true;
            else
                isAvailable = false;
        } catch (Exception e) {
            isAvailable = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        Intent intent = new Intent("check_internet_broadcast");
        // You can also include some extra data.
        intent.putExtra("isAvailable", String.valueOf(isAvailable));
        LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
    }
}
