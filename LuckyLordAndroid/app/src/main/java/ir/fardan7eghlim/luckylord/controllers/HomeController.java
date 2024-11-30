package ir.fardan7eghlim.luckylord.controllers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;

import ir.fardan7eghlim.luckylord.models.ContactUsModel;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.utils.AppConfig;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/21/2016.
 */

public class HomeController extends Observable {

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    public Context cntx = null;

    public HomeController(Context cntx)
    {
        this.cntx = cntx;
    }
    private DialogModel DM;


    public void index(Integer egg)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("egg", egg.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_INDEX_HOME);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.HomeIndex,RequestRespondModel.TAG_INDEX_HOME,null);
        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void unread()
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_UNREAD_HOME);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.HomeUnread,RequestRespondModel.TAG_UNREAD_HOME,null);
        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void ad()
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_ADVERTISMENT_HOME);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.HomeAdvertisement,RequestRespondModel.TAG_ADVERTISMENT_HOME,null);
        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void egg(boolean adWatched)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_EGG_HOME);
            bodyParams.put("ad", (adWatched == true?"1":"0"));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.HomeEgg,RequestRespondModel.TAG_EGG_HOME,null);
        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void oneSiganlPlayerId(String playerId)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME);
            bodyParams.put("player_id", playerId);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.HomeOneSignalPlayerId,RequestRespondModel.TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME,null);
        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void getVersion()
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.HomeGetVersion,RequestRespondModel.TAG_GET_VERSION_HOME,null);
        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    private void ReqResOperation(int method,Map<String, String> headerParams,Map<String, String> bodyParams, String address, String tag, final Object obj)
    {
        final Map<String, String>[] local_params = new Map[2];
        local_params[0] = headerParams;
        local_params[1] = bodyParams;


        StringRequest sr = new StringRequest(Request.Method.POST, address , new Response.Listener<String>() {

            Object result = null;
            @Override
            public void onResponse(String response) {

                RequestRespondModel rrm = new RequestRespondModel(cntx);
                try {


                    rrm.decodeJsonResponse(response.toString());


                    // Check for error node in json
                    if (rrm.getError() == null || rrm.getError() == 0) {

                        switch (rrm.getTag())
                        {

                            case RequestRespondModel.TAG_INDEX_HOME:
                                ArrayList<Object> s;
                                s = (ArrayList<Object>) rrm.getItems();
                                s.add(0,RequestRespondModel.TAG_INDEX_HOME);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_EGG_HOME:
                                s= new ArrayList<>();
                                s.add(0,RequestRespondModel.TAG_EGG_HOME);
                                s.add(true);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_UNREAD_HOME:
                                s = (ArrayList<Object>) rrm.getItems();
                                s.add(0,RequestRespondModel.TAG_UNREAD_HOME);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_ADVERTISMENT_HOME:
                                s= new ArrayList<>();
                                s.add ( rrm.getItem());
                                s.add(0,RequestRespondModel.TAG_ADVERTISMENT_HOME);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME:
                                s= new ArrayList<>();
                                s.add(0,RequestRespondModel.TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME);
                                s.add(true);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_GET_VERSION_HOME:
                                s = (ArrayList<Object>) rrm.getItems();
                                s.add(0,RequestRespondModel.TAG_GET_VERSION_HOME);
                                s.add(true);
                                result = s;
                                break;
                            default:

                                break;
                        }

                    } else {

                        if(rrm.getMust_logout())
                        {
                            result = false;
                        }
                        else
                        {
                            result = rrm.getError();
                            // Error in login. Get the error message
                            LogModel log = new LogModel(cntx);
                            log.setErrorMessage("message: "+ rrm.getError_msg()+" CallStack: non, MeysamErrorCode:" + rrm.getError());
                            log.setContollerName(this.getClass().getName());
                            log.insert();
                        }


                    }

                } catch (Exception ex) {
                    // JSON error
//                    LogModel log = new LogModel(cntx);
//                    log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//                    log.setContollerName(this.getClass().getName());
//                    log.insert();

                    Utility.generateLogError(ex);

                    result = false;
                }

                setChanged();
                notifyObservers(result);
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Object result = false;

                if(error instanceof AuthFailureError ||
                        error instanceof ClientError)
                {
                    result = RequestRespondModel.ERROR_AUTH_FAIL_CODE;

                }
                else{
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {

                        String res = null;
                        try {
                            res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        // Now you can use any deserializer to make sense of data

                        try {
                            JSONObject obj = new JSONObject(res);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        LogModel log = new LogModel(cntx);
//                        log.setErrorMessage("header: "+res+" message: "+error.getMessage()+" CallStack: "+error.getStackTrace());
//                        log.setContollerName(this.getClass().getName());
//                        log.setUserId(null);
//                        log.insert();

                        Utility.generateLogError(error);

                    }
                    else
                    {
//                        LogModel log = new LogModel(cntx);
//                        log.setErrorMessage("message: "+error.getMessage()+" CallStack: "+error.getStackTrace());
//                        log.setContollerName(this.getClass().getName());
//                        log.setUserId(null);
//                        log.insert();

                        Utility.generateLogError(error);
                    }

                }

                setChanged();
                notifyObservers(result);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                if (local_params[1] == null) local_params[1] = new HashMap<>();
                //                return local_params[1];
                return checkParams(local_params[1]);
            }

            private Map<String, String> checkParams(Map<String, String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");

                        String message = "اخطار مقدار نال در پارامتر والی";
                        message += "خانه";
                        message += "paramName:" + pairs.getKey();
                        Exception ex = new Exception(message);
                        Utility.generateLogError(ex);
                    }
                }
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (local_params[0] == null) local_params[0] = new HashMap<>();
                return local_params[0];
            }
        };


        //meysam - volly retrying overrided
//        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
//                Utility.TIMEOUT_TIME,
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        sr.setRetryPolicy(mRetryPolicy);
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                3000,
                3,
                (float) 2.0);
        sr.setRetryPolicy(mRetryPolicy);

        AppController.getInstance().addToRequestQueue(sr);

    }

}
