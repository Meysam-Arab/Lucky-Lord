package ir.fardan7eghlim.luckylord.controllers;

import android.content.Context;
import android.text.TextUtils;

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
import java.util.Observable;

import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.MessageModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.AppConfig;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/21/2016.
 */

public class UserFriendController extends Observable {

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    public Context cntx = null;

    public UserFriendController(Context cntx)
    {
        this.cntx = cntx;
    }



    public void request(UserModel user)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("opposite_user_id", user.getId().toString());
            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_REQUEST);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendRequest,RequestRespondModel.TAG_USER_FRIEND_REQUEST,null);

        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void status(UserModel user)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("opposite_user_id", user.getId().toString());
            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_STATUS);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendStatus,RequestRespondModel.TAG_USER_FRIEND_STATUS,null);

        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void changeStatus(UserModel user, Integer statusCode)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("opposite_user_id", user.getId().toString());
            bodyParams.put("status_code", statusCode.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_CHANGE_STATUS);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendChangeStatus,RequestRespondModel.TAG_USER_FRIEND_CHANGE_STATUS,null);

        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }
    public void list( Integer statusCode)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("status_code", statusCode.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_LIST);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendList,RequestRespondModel.TAG_USER_FRIEND_LIST,null);

        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }
    public void revoke(UserModel user)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("opposite_user_id", user.getId().toString());
            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_REVOKE);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendRevoke,RequestRespondModel.TAG_USER_FRIEND_REVOKE,null);

        }
        catch (Exception ex)
        {
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }
    public void recommend()
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_RECOMMEND);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendRecommend,RequestRespondModel.TAG_USER_FRIEND_RECOMMEND,null);

        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }
    public void sync()
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_SYNC);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendSync,RequestRespondModel.TAG_USER_FRIEND_SYNC,null);

        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }
    public void validateSync(ArrayList<UserFriendModel> userFriends)
    {
        try
        {

            ArrayList<String> userFriendIds = new ArrayList<>();
            for(int i = 0;i < userFriends.size(); i++)
            {
                userFriendIds.add(userFriends.get(i).getUserFriendId().toString());
            }

            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_USER_FRIEND_VALIDATE_SYNC);
            bodyParams.put("user_friend_ids", TextUtils.join(",", userFriendIds));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserFriendValidateSync,RequestRespondModel.TAG_USER_FRIEND_VALIDATE_SYNC,null);

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

                            case RequestRespondModel.TAG_USER_FRIEND_STATUS:
                                ArrayList<Object> s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_STATUS);
                                s.add(1, (ArrayList<Object>) rrm.getItems());

                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_REQUEST:
                                s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_REQUEST);
                                s.add(true);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_CHANGE_STATUS:
                                s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_CHANGE_STATUS);
                                s.add(true);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_LIST:
                                s = new ArrayList<>();
                                s.add((ArrayList<Object>) rrm.getItems());
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_LIST);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_REVOKE:
                                s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_REVOKE);
                                s.add(true);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_RECOMMEND:
                                s = new ArrayList<>();
                                s.add((ArrayList<Object>) rrm.getItems());
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_RECOMMEND);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_SYNC:
                                s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_SYNC);
                                s.add((ArrayList<Object>) rrm.getItems());
                                result = s;
                                break;
                            case RequestRespondModel.TAG_USER_FRIEND_VALIDATE_SYNC:
                                s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_USER_FRIEND_VALIDATE_SYNC);
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
////                    log.setUserId(BigInteger.valueOf(Long.parseLong(new SessionModel(cntx).getUserDetails().get(ir.fardan7eghlim.attentra.models.SessionModel.KEY_ID).toString())));
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
                        message += "پیام";
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
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                Utility.TIMEOUT_TIME,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        sr.setRetryPolicy(mRetryPolicy);

        AppController.getInstance().addToRequestQueue(sr);

    }

}
