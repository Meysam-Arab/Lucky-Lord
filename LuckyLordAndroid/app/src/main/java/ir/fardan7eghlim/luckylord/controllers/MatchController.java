package ir.fardan7eghlim.luckylord.controllers;

import android.content.Context;
import android.content.Intent;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import ir.fardan7eghlim.luckylord.models.BetModel;
import ir.fardan7eghlim.luckylord.models.ContactUsModel;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.MatchModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.AppConfig;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/21/2016.
 */

public class MatchController extends Observable {

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    public Context cntx = null;

    public MatchController(Context cntx)
    {
        this.cntx = cntx;
    }



//    public void index()
//    {
//        try
//        {
//            // Post params to be sent to the server
//            Map<String, String> headerParams = new HashMap<String, String>();
//            Map<String, String> bodyParams = new HashMap<String, String>();
//
//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());
//
//            bodyParams.put("tag", RequestRespondModel.TAG_MATCH_INDEX);
//
//            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.MatchIndex,RequestRespondModel.TAG_MATCH_INDEX,null);
//
////            ArrayList items = new ArrayList<>();
////            SessionModel session = new SessionModel(cntx);
////            for(int i = 0; i <4; i++ )
////            {
////                MatchModel match = new MatchModel(cntx);
////                match.setId(new BigInteger(String.valueOf(i)));
////                match.setGuid("5921937593c799.74190651");
////                UserModel user = new UserModel();
////                user.setId(new BigInteger(String.valueOf(++i)) );
////                user.setProfilePicture("m#1#1#1#1#1#0#0#0#0#0#0#0");
////                user.setLuck(i*3);
////                match.setOpponent(user);
////                match.setOpponentCorrectCount(String.valueOf(i+2));
////                match.setOpponentSpentTime("500");
////                match.setEnded(true);
////                match.setSelfCorrectCount("9");
////                match.setSelfSpentTime("300");
////                match.setBet("500");
////                match.setWinnerId(session.getCurrentUser().getId());
////
////                items.add(match);
////            }
////            Object result = null;
////            ArrayList<Object> s;
////            s = (ArrayList<Object>) items;
////            s.add(0,RequestRespondModel.TAG_MATCH_INDEX);
////            result = s;
////            setChanged();
////            notifyObservers(result);
//
//
//        }
//        catch (Exception ex)
//        {
////            LogModel log = new LogModel(cntx);
////            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
////            log.setContollerName(this.getClass().getName());
////            log.insert();
//
//             Utility.generateLogError(ex);
//        }
//    }
//
//    public void request(String requestCount, String betId, String categoryId)
//    {
//        try
//        {
//            // Post params to be sent to the server
//            Map<String, String> headerParams = new HashMap<String, String>();
//            Map<String, String> bodyParams = new HashMap<String, String>();
//
//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());
//
//            bodyParams.put("request_count", requestCount);
//            bodyParams.put("bet", betId);
//            bodyParams.put("chosen_category_id", categoryId);
//            bodyParams.put("tag", RequestRespondModel.TAG_MATCH_REQUEST);
//
////            throw new RuntimeException();
//
//            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.MatchRequest,RequestRespondModel.TAG_MATCH_REQUEST,null);
//        }
//        catch (Exception ex)
//        {
////            LogModel log = new LogModel(cntx);
////            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
////            log.setContollerName(this.getClass().getName());
////             Utility.generateLogError(ex);;
//
//            Utility.generateLogError(ex);
//        }
//    }
//
//    public void result(MatchModel match, Integer usedLuck, Integer usedHazel)
//    {
//        try
//        {
//            // Post params to be sent to the server
//            Map<String, String> headerParams = new HashMap<String, String>();
//            Map<String, String> bodyParams = new HashMap<String, String>();
//
//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());
//
//            // Meysam: create question array of just category_id and is_correct
//            bodyParams.put("questions", match.generateJsonArrayResult().toString());
//            bodyParams.put("time", match.getSelfSpentTime());
//            bodyParams.put("match_id", match.getId().toString());
//            bodyParams.put("match_guid", match.getGuid());
//            bodyParams.put("used_luck", usedLuck.toString() );
//            bodyParams.put("used_hazel", usedHazel.toString() );
//            bodyParams.put("tag", RequestRespondModel.TAG_MATCH_RESULT);
//
//            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.MatchResult,RequestRespondModel.TAG_MATCH_RESULT,null);
//
//        }
//        catch (Exception ex)
//        {
////            LogModel log = new LogModel(cntx);
////            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
////            log.setContollerName(this.getClass().getName());
////            log.insert();
//            Utility.generateLogError(ex);
//        }
//    }
//
//    public void status(MatchModel match)
//    {
//        try
//        {
//            // Post params to be sent to the server
//            Map<String, String> headerParams = new HashMap<String, String>();
//            Map<String, String> bodyParams = new HashMap<String, String>();
//
//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());
//
//            bodyParams.put("match_id", match.getId().toString());
//            bodyParams.put("match_guid", match.getGuid());
//            bodyParams.put("tag", RequestRespondModel.TAG_MATCH_STATUS);
//
//            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.MatchStatus,RequestRespondModel.TAG_MATCH_STATUS,null);
//        }
//        catch (Exception ex)
//        {
////            LogModel log = new LogModel(cntx);
////            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
////            log.setContollerName(this.getClass().getName());
////            log.insert();
//
//            Utility.generateLogError(ex);
//        }
//    }
    public void universalIndex(Integer type, Integer opponentType)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX);
            bodyParams.put("type", type.toString());
            bodyParams.put("opponent_type", opponentType.toString());

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalMatchIndex,RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX,null);
        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    public void universalRequest(String requestCount, String betId, String categoryId, BigInteger opponentId, Integer type, Integer opponentType)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("type", type.toString());
            bodyParams.put("opponent_type", opponentType.toString());
            if(opponentId != null)
                bodyParams.put("opponent_id", opponentId.toString());
            if(requestCount != null)
                bodyParams.put("request_count", requestCount);
            bodyParams.put("bet", betId);
            if(categoryId != null)
                bodyParams.put("chosen_category_id", categoryId);

            bodyParams.put("tag", RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalMatchRequest,RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST,null);
        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    public void universalResult(UniversalMatchModel match, Integer usedLuck, Integer usedHazel)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            // Meysam: create question array of just category_id and is_correct
            if(match.getCrossTable() != null)
            {
                bodyParams.put("match_values", match.calculateTrueCellCount().toString());
            }
            else
            {
                bodyParams.put("match_values", match.generateJsonArrayResult().toString());
            }
            bodyParams.put("time", match.getSelfSpentTime());
            bodyParams.put("user_universal_match_id", match.getId().toString());
//            bodyParams.put("match_guid", match.getGuid());
            bodyParams.put("used_luck", usedLuck.toString() );
            bodyParams.put("used_hazel", usedHazel.toString() );
            bodyParams.put("tag", RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalMatchResult,RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT,null);
        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }

    public void universalStatus(UniversalMatchModel match, String categoryId)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("user_universal_match_id", match.getId().toString());
            if(categoryId != null)
                bodyParams.put("chosen_category_id", categoryId);

            bodyParams.put("tag", RequestRespondModel.TAG_UNIVERSAL_MATCH_STATUS);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalMatchStatus,RequestRespondModel.TAG_UNIVERSAL_MATCH_STATUS,null);
        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }

    public void universalChangeStatus(UniversalMatchModel match, Integer status)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("user_universal_match_id", match.getId().toString());
            bodyParams.put("status", status.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_UNIVERSAL_MATCH_CHANGE_STATUS);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalMatchChangeStatus,RequestRespondModel.TAG_UNIVERSAL_MATCH_CHANGE_STATUS,null);
        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }

    public void universalRematchRequest(String betId, String categoryId, String opponentUserName, Integer type, Integer opponentType)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("type", type.toString());
            bodyParams.put("opponent_type", opponentType.toString());
            bodyParams.put("opponent_user_name", opponentUserName);
            bodyParams.put("bet", betId);
            if(categoryId != null)
                bodyParams.put("chosen_category_id", categoryId);

//            bodyParams.put("request_count", "1");

            bodyParams.put("tag", RequestRespondModel.TAG_UNIVERSAL_MATCH_REMATCH_REQUEST);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalReMatchRequest,RequestRespondModel.TAG_UNIVERSAL_MATCH_REMATCH_REQUEST,null);
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

//                            case RequestRespondModel.TAG_MATCH_INDEX:
//                                ArrayList<Object> s = new ArrayList<>();
//                                s.add((ArrayList<Object>) rrm.getItems());
//                                s.add(0,RequestRespondModel.TAG_MATCH_INDEX);
//                                MatchModel.bets = (ArrayList<BetModel>) rrm.getItem();
//
//                                result = s;
//                                break;
//                            case RequestRespondModel.TAG_MATCH_REQUEST:
//                                s = new ArrayList<Object>();
//                                s.add(0,RequestRespondModel.TAG_MATCH_REQUEST);
//                                s.add(rrm.getItem());
//                                result = s;
//                                break;
//                            case RequestRespondModel.TAG_MATCH_RESULT:
//                                s = new ArrayList<Object>();
//                                s.add(0,RequestRespondModel.TAG_MATCH_RESULT);
//                                s.add(rrm.getItem());
//                                result = s;
//                                break;
//                            case RequestRespondModel.TAG_MATCH_STATUS:
//                                s = new ArrayList<Object>();
//                                s.add(0,RequestRespondModel.TAG_MATCH_STATUS);
//                                s.add(rrm.getItem());
//                                result = s;
//                                break;
                            case RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX:
                                ArrayList<Object> s = new ArrayList<>();
                                s = new ArrayList<>();
                                s.add((ArrayList<Object>) rrm.getItems());
                                s.add(0,RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX);
                                UniversalMatchModel.bets = (ArrayList<BetModel>) rrm.getItem();

                                result = s;
                                break;
                            case RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST:
                                s = new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST);
                                s.add(rrm.getItem());
                                result = s;
                                break;
                            case RequestRespondModel.TAG_UNIVERSAL_MATCH_STATUS:
                                s = new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_UNIVERSAL_MATCH_STATUS);
                                s.add(rrm.getItem());
                                result = s;
                                break;
                            case RequestRespondModel.TAG_UNIVERSAL_MATCH_CHANGE_STATUS:
                                s = new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_UNIVERSAL_MATCH_CHANGE_STATUS);
                                s.add(true);
                                s.add(rrm.getItem());
                                result = s;
                                break;
                            case RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT:
                                s = new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);
                                s.add(rrm.getItem());
                                result = s;
                                break;
                                //meysam - attention!!! request tag is being returned!!!!!
//                            case RequestRespondModel.TAG_UNIVERSAL_MATCH_REMATCH_REQUEST:
//                                s = new ArrayList<Object>();
//                                s.add(0,RequestRespondModel.TAG_UNIVERSAL_MATCH_REMATCH_REQUEST);
//                                s.add(true);
//                                result = s;
//                                break;
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
                        message += "مسابقه";
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
