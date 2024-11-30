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
import ir.fardan7eghlim.luckylord.utils.AppConfig;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/21/2016.
 */

public class UserController extends Observable {

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }
    public Context cntx = null;


    public UserController(Context cntx)
    {
        this.cntx = cntx;
    }

    public void login(final UserModel user, final Context cntx){

//        // Tag used to cancel the request
//        String tag_string_req = RequestRespondModel.TAG_LOGIN_USER;
        // Post params to be sent to the server
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> bodyParams = new HashMap<String, String>();

        bodyParams.put("user_name", user.getUserName());
        bodyParams.put("password", user.getPassword());
        bodyParams.put("tag", RequestRespondModel.TAG_LOGIN_USER);
        ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.LoginAddress,RequestRespondModel.TAG_LOGIN_USER,null);

    }

    public void registerVisitor(String gender)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            bodyParams.put("visitor_user_name", Utility.generateVisitorCode(cntx));
            bodyParams.put("gender", gender);

            bodyParams.put("tag", RequestRespondModel.TAG_VISITOR_REGISTER_USER);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserVisitorRegister,RequestRespondModel.TAG_VISITOR_REGISTER_USER,null);
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

    public void register(final UserModel user)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            bodyParams.put("user_name", user.getUserName());
            bodyParams.put("password", user.getPassword());
            if(user.getEmail() != null)
                bodyParams.put("email", user.getEmail());
            bodyParams.put("gender", user.getGender());
            bodyParams.put("invite_code", user.getInviteCode());
            bodyParams.put("visitor_user_name", Utility.generateVisitorCode(cntx));
            bodyParams.put("tag", RequestRespondModel.TAG_REGISTER_USER);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserRegister,RequestRespondModel.TAG_REGISTER_USER,null);
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
    public void rank(Integer type, Integer range)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("type", type.toString());
            bodyParams.put("range", range.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_RANK_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserRank,RequestRespondModel.TAG_RANK_USER,null);
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
    public void profile(String userName)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("tag", RequestRespondModel.TAG_PROFILE_USER);
            if(userName != null)
                bodyParams.put("user_name", userName);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserProfile,RequestRespondModel.TAG_PROFILE_USER,null);
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
    public void updateProfilePicture(String image)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("image", image);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_PROFILE_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdateProfilePicture,RequestRespondModel.TAG_UPDATE_PROFILE_USER,null);
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
    public void updateEmail(String email)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("email", email);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_EMAIL_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdateEmail,RequestRespondModel.TAG_UPDATE_EMAIL_USER,null);
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
    public void updateAllowChat(String allow)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("allow", allow);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_ALLOW_CHAT_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdateAllowChat,RequestRespondModel.TAG_UPDATE_ALLOW_CHAT_USER,null);
        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    public void updatePhone(String phone)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("phone", phone);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_PHONE_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdatePhone,RequestRespondModel.TAG_UPDATE_PHONE_USER,null);
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
    public void updateBirthDate(String birthDate)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("birth_date", birthDate);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_BIRTH_DATE_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdateBirthDate,RequestRespondModel.TAG_UPDATE_BIRTH_DATE_USER,null);
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
    public void updatePassword(String oldPassword, String newPassword)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("old_password", oldPassword);
            bodyParams.put("new_password", newPassword);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_PASSWORD_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdatePassword,RequestRespondModel.TAG_UPDATE_PASSWORD_USER,null);
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
    public void updateGender(String gender)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("gender", gender);
            bodyParams.put("tag", RequestRespondModel.TAG_UPDATE_GENDER_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserUpdateGender,RequestRespondModel.TAG_UPDATE_GENDER_USER,null);
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
    public void decrease(Integer hazel, Integer luck)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("hazel_amount", hazel.toString());
            bodyParams.put("luck_amount", luck.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserDecreaseHazelLuck,RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER,null);
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
    public void change(Integer hazel, Integer luck)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("hazel", hazel.toString());
            bodyParams.put("luck", luck.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserChangeHazelLuck,RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER,null);
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
    public void forgetPassword(String email, String newPassword)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("email", email);
            bodyParams.put("new_password", newPassword);
            bodyParams.put("tag", RequestRespondModel.TAG_FORGET_PASSWORD_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserForgetPassword,RequestRespondModel.TAG_FORGET_PASSWORD_USER,null);
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
    public void info(String userName)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("user_name", userName);
            bodyParams.put("tag", RequestRespondModel.TAG_INFO_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserInfo,RequestRespondModel.TAG_INFO_USER,null);
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
    public void search(UserModel user, Integer skip)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("skip", skip.toString());
            bodyParams.put("user_name", user.getUserName().toString());
            bodyParams.put("tag", RequestRespondModel.TAG_SEARCH_USER);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserSearch,RequestRespondModel.TAG_SEARCH_USER,null);

        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    public void changeLevel(Integer changeAmount)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("value", changeAmount.toString());
            bodyParams.put("tag", RequestRespondModel.TAG_CHANGE_LEVEL_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserChangeLevel,RequestRespondModel.TAG_CHANGE_LEVEL_USER,null);
        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }
    }
    public void updateCups(String cupsToChange)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("indexes", cupsToChange);
            bodyParams.put("tag", RequestRespondModel.TAG_CUPS_USER);
            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.UserCups,RequestRespondModel.TAG_CUPS_USER,null);
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
                            case RequestRespondModel.TAG_LOGIN_USER:
                                // user successfully logged in
                                result=true;
                                SessionModel session = new SessionModel(cntx);
                                session.createLoginSession( rrm.getUser(), rrm.getToken());
                                break;
                            case RequestRespondModel.TAG_REGISTER_USER:
                                // user successfully registered
                                result = true;
                                session = new SessionModel(cntx);
                                session.saveItem( SessionModel.KEY_INVITE_CODE, rrm.getUser().getInviteCode());
                                session.setToken(rrm.getToken());
                                session.saveItem( SessionModel.IS_LOGIN, true);
                                break;
                            case RequestRespondModel.TAG_VISITOR_REGISTER_USER:
                                // guest successfully registered
                                ArrayList<Object> s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_VISITOR_REGISTER_USER);
                                s.add(true);
                                result =s;
                                session = new SessionModel(cntx);
                                session.createLoginSession( rrm.getUser(), rrm.getToken());
                                break;
                            case RequestRespondModel.TAG_EDIT_USER:
                            result =true;
                            break;
                            case RequestRespondModel.TAG_RANK_USER:
                                // user_rank successfully recieved
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_RANK_USER);
                                s.add(rrm.getItems());
                                result =s;
                                break;
                            case RequestRespondModel.TAG_PROFILE_USER:
                                // user profile information successfully received
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_PROFILE_USER);
                                s.add(rrm.getItems());
                                s.add(rrm.getItem());
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_PROFILE_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_PROFILE_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_BIRTH_DATE_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_BIRTH_DATE_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_EMAIL_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_EMAIL_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_PHONE_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_PHONE_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_PASSWORD_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_PASSWORD_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_GENDER_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_GENDER_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER:
                                // guest successfully registered
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_FORGET_PASSWORD_USER:
                                // guest successfully registered
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_FORGET_PASSWORD_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_INFO_USER:
                                // guest successfully registered
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_INFO_USER);
                                s.add(rrm.getItem());
//                                s.add(rrm.getItems());
                                result =s;
                                break;
                            case RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_SEARCH_USER:
                                s= new ArrayList<Object>();
                                s.add(0,RequestRespondModel.TAG_SEARCH_USER);
                                s.add(1, (ArrayList<Object>) rrm.getItems());
                                result = s;
                                break;
                            case RequestRespondModel.TAG_CHANGE_LEVEL_USER:

                                //meysam - update user level score to sync with server...
                                session = new SessionModel(cntx);
                                session.saveItem(SessionModel.KEY_LEVEL_SCORE,rrm.getItem().toString());

                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_CHANGE_LEVEL_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_CUPS_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_CUPS_USER);
                                s.add(true);
                                result =s;
                                break;
                            case RequestRespondModel.TAG_UPDATE_ALLOW_CHAT_USER:
                                s=new ArrayList<Object>();
                                s.add(RequestRespondModel.TAG_UPDATE_ALLOW_CHAT_USER);
                                s.add(true);
                                result =s;
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
                        message += "کاربر";
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
        RetryPolicy mRetryPolicy = null;
        if(tag.equals(RequestRespondModel.TAG_LOGIN_USER) || tag.equals(RequestRespondModel.TAG_RANK_USER) ||
                tag.equals(RequestRespondModel.TAG_PROFILE_USER) ||
                tag.equals(RequestRespondModel.TAG_INFO_USER))
        {
            mRetryPolicy = new DefaultRetryPolicy(
                    3000,
                    3,
                    (float) 2.0);
        }
        else if(tag.equals(RequestRespondModel.TAG_VISITOR_REGISTER_USER) ||
                tag.equals(RequestRespondModel.TAG_REGISTER_USER))
        {
            mRetryPolicy = new DefaultRetryPolicy(
                    15000,
                    3,
                    (float) 2.0);
        }
        else
        {
            mRetryPolicy = new DefaultRetryPolicy(
                    Utility.TIMEOUT_TIME,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            sr.setRetryPolicy(mRetryPolicy);
        }



        sr.setRetryPolicy(mRetryPolicy);

        AppController.getInstance().addToRequestQueue(sr);

    }

}
