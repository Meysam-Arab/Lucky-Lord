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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import ir.fardan7eghlim.luckylord.models.ContactUsModel;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.utils.AppConfig;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/21/2016.
 */

public class QuestionController extends Observable {

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    private Context cntx = null;

    public QuestionController(Context cntx)
    {
        this.cntx = cntx;
    }



    public void next(Integer luck, Integer hazel, Integer is_correct, Integer category_id, QuestionModel question)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_NEXT_QUESTION);
            bodyParams.put("luck", luck.toString());
            bodyParams.put("hazel", hazel.toString());
            bodyParams.put("is_correct", String.valueOf(is_correct));

            if(question != null && question.getId() != null)
            {
                bodyParams.put("question_id", question.getId().toString());
                bodyParams.put("question_guid", question.getGuid());
            }
            bodyParams.put("next_category_id", category_id.toString());

            // uncomment on real
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionNext,RequestRespondModel.TAG_NEXT_QUESTION,null);

//            Object result = null;
//            QuestionModel tquestion = new QuestionModel(cntx);
//            tquestion.setId(new BigInteger("1"));
//            tquestion.setGuid("5921937593c799.74190651");
//            tquestion.setAnswer("شیراز#*همدان#تبریز#شهرکرد");
//            tquestion.setDescription("مرکز استان همدان کجاست؟");
//            tquestion.setPenalty(50);
//            tquestion.setMinLuckReward(1);
//            tquestion.setMaxLuckReward(3);
//            tquestion.setMaxHazelReward(5);
//            tquestion.setMinHazelReward(2);
//            result = tquestion;
//            setChanged();
//            notifyObservers(result);
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

    public void report(BigInteger questionId, Integer code, String description)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_REPORT_QUESTION);
            bodyParams.put("code", code.toString());
            bodyParams.put("description", description.equals("")?"ندارد":description);
            bodyParams.put("question_id", String.valueOf(questionId));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionReport,RequestRespondModel.TAG_REPORT_QUESTION,null);


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

    public void rate(BigInteger questionId, int rate)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_RATE_QUESTION);
            bodyParams.put("rate", String.valueOf(rate));
            bodyParams.put("question_id", String.valueOf(questionId));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionRate,RequestRespondModel.TAG_RATE_QUESTION,null);


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
    public void wordTable(Integer hazel, Integer luck,int questionCount)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_WORD_TABLE_QUESTION);
            bodyParams.put("luck", String.valueOf(luck));
            bodyParams.put("hazel", String.valueOf(hazel));
            bodyParams.put("question_count", String.valueOf(questionCount));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionWordTable,RequestRespondModel.TAG_WORD_TABLE_QUESTION,null);


        }
        catch (Exception ex)
        {

            Utility.generateLogError(ex);
        }
    }
    public void reportTable(BigInteger questionPuzzleId, Integer code, String description)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_REPORT_TABLE_QUESTION);
            bodyParams.put("code", code.toString());
            bodyParams.put("description", description.equals("")?"ندارد":description);
            bodyParams.put("question_puzzle_id", String.valueOf(questionPuzzleId));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionTableReport,RequestRespondModel.TAG_REPORT_TABLE_QUESTION,null);


        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }

    public void rateTable(BigInteger questionPuzzleId, int rate)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_RATE_TABLE_QUESTION);
            bodyParams.put("rate", String.valueOf(rate));
            bodyParams.put("question_puzzle_id", String.valueOf(questionPuzzleId));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionTableRate,RequestRespondModel.TAG_RATE_TABLE_QUESTION,null);


        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }

    public void universalReport(BigInteger itemId, Integer code, String description, Integer type)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_REPORT_UNIVERSAL);
            bodyParams.put("code", code.toString());
            bodyParams.put("description", description.equals("")?"ندارد":description);
            bodyParams.put("item_id", String.valueOf(itemId));
            bodyParams.put("type", type.toString());

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UniversalReport,RequestRespondModel.TAG_REPORT_UNIVERSAL,null);


        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    public void crossTable(Integer hazel, Integer luck)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_CROSS_TABLE_QUESTION);
            bodyParams.put("luck", String.valueOf(luck));
            bodyParams.put("hazel", String.valueOf(hazel));

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.QuestionCrossTable,RequestRespondModel.TAG_CROSS_TABLE_QUESTION,null);


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

                            case RequestRespondModel.TAG_NEXT_QUESTION:

                                result = rrm.getItem();
                                break;
                            case RequestRespondModel.TAG_REPORT_QUESTION:
                                ArrayList<Object> s;
                                s= new ArrayList<>();
                                s.add ( true);
                                s.add(0,RequestRespondModel.TAG_REPORT_QUESTION);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_RATE_QUESTION:

                                s= new ArrayList<>();
                                s.add ( true);
                                s.add(0,RequestRespondModel.TAG_RATE_QUESTION);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_WORD_TABLE_QUESTION:

                                s= new ArrayList<>();
                                s.add ( rrm.getItems());
                                s.add(0,RequestRespondModel.TAG_WORD_TABLE_QUESTION);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_REPORT_TABLE_QUESTION:
                                s= new ArrayList<>();
                                s.add ( true);
                                s.add(0,RequestRespondModel.TAG_REPORT_TABLE_QUESTION);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_RATE_TABLE_QUESTION:

                                s= new ArrayList<>();
                                s.add ( true);
                                s.add(0,RequestRespondModel.TAG_RATE_TABLE_QUESTION);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_REPORT_UNIVERSAL:
                                s= new ArrayList<>();
                                s.add ( true);
                                s.add(0,RequestRespondModel.TAG_REPORT_UNIVERSAL);
                                result = s;
                                break;
                            case RequestRespondModel.TAG_CROSS_TABLE_QUESTION:
                                s= new ArrayList<>();
                                s.add ( rrm.getItems().get(0));
                                s.add ( rrm.getItems().get(1));
                                s.add ( rrm.getItems().get(2));
                                rrm.getItems().remove(0);
                                rrm.getItems().remove(0);
                                rrm.getItems().remove(0);
                                s.add ( rrm.getItems());
                                s.add(0,RequestRespondModel.TAG_CROSS_TABLE_QUESTION);
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
                        message += "سوال";
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
