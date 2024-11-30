package ir.fardan7eghlim.luckylord.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.AppController;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 2/16/2017.
 */

public class RequestRespondModel {

    // STATUS success code
    private static final int STATUS_SUCCESS_CODE = 1;

    // STATUS fail code
    private static final int STATUS_FAIL_CODE = 2;

    // STATUS error code
    private static final int STATUS_ERROR_CODE = 3;

    // STATUS undefined code
    private static final int STATUS_UNDEFINED_CODE = 0;

    ////////////////////////////////////////////////////////

    // tag codes

    //public
    public static final String TAG_UNDEFINED = "undefined";

    //user
    public static final String TAG_REGISTER_USER = "register_user";
    public static final String TAG_EDIT_USER = "edit_user";
    public static final String TAG_RANK_USER = "rank_user";
    public static final String TAG_LOGIN_USER = "login_user";
    public static final String TAG_VISITOR_REGISTER_USER = "visitor_register_user";
    public static final String TAG_PROFILE_USER = "profile_user";
    public static final String TAG_UPDATE_PROFILE_USER = "update_profile_user";
    public static final String TAG_UPDATE_PHONE_USER = "update_phone_user";
    public static final String TAG_UPDATE_EMAIL_USER = "update_email_user";
    public static final String TAG_UPDATE_BIRTH_DATE_USER = "update_birth_date_user";
    public static final String TAG_UPDATE_PASSWORD_USER = "update_password_user";
    public static final String TAG_DECREASE_HAZEL_LUCK_USER = "decrease_hazel_luck_user";
    public static final String TAG_UPDATE_GENDER_USER = "update_gender_user";
    public static final String TAG_FORGET_PASSWORD_USER = "forget_password_user";
    public static final String TAG_INFO_USER = "info_user";
    public static final String TAG_CHANGE_HAZEL_LUCK_USER = "change_hazel_luck_user";
    public static final String TAG_SEARCH_USER = "search_user";
    public static final String TAG_CHANGE_LEVEL_USER = "change_level_user";
    public static final String TAG_CUPS_USER = "cups_user";
    public static final String TAG_UPDATE_ALLOW_CHAT_USER = "update_allow_chat_user";

    // contact us
    public static final String TAG_STORE_CONTACT_US = "store_contact_us";

    // question
    public static final String TAG_NEXT_QUESTION = "next_question";
    public static final String TAG_REPORT_QUESTION = "report_question";
    public static final String TAG_RATE_QUESTION = "rate_question";
    public static final String TAG_WORD_TABLE_QUESTION = "word_table_question";
    public static final String TAG_REPORT_TABLE_QUESTION = "report_table_question";
    public static final String TAG_RATE_TABLE_QUESTION = "rate_table_question";
    public static final String TAG_WORD_FIND_WORDS = "word_find_words";
    public static final String TAG_CROSS_TABLE_QUESTION = "cross_table_question";


    //home
    public static final String TAG_INDEX_HOME = "index_home";
    public static final String TAG_GET_PUBLIC_KEY_HOME = "get_public_key";
    public static final String TAG_UNREAD_HOME="unread_home";
    public static final String TAG_ADVERTISMENT_HOME ="ad_hazel_home";
    public static final String TAG_EGG_HOME="egg_home";
    public static final String TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME ="player_id_home";
    public static final String TAG_GET_VERSION_HOME="get_version_home";



    // message
    public static final String TAG_INDEX_MESSAGE = "index_message";
    public static final String TAG_READ_MESSAGE= "read_message";

    // chat
    public static final String TAG_INDEX_CHAT_SEND = "index_chat_send";
    public static final String TAG_INDEX_CHAT_RECEIVE= "index_chat_receive";

    // draw
    public static final String TAG_INDEX_DRAW = "index_draw";
    public static final String TAG_WINNERS_DRAW = "winners_draw";
    public static final String TAG_PARTICIPATE_DRAW = "participate_draw";

    //market
    public static final String TAG_MARKET_INDEX = "index_market";
    public static final String TAG_MARKET_PURCHASE = "purchase_market";
    public static final String TAG_MARKET_CONVERT = "convert_market";


    //body_part
    public static final String TAG_BODY_PART_INDEX = "index_body_part";
    public static final String TAG_BODY_PART_PURCHASE = "purchase_body_part";

//    //match
//    public static final String TAG_MATCH_REQUEST = "request_match";
//    public static final String TAG_MATCH_STATUS = "status_match";
//    public static final String TAG_MATCH_RESULT = "result_match";
//    public static final String TAG_MATCH_INDEX = "index_match";

    // user_message
    public static final String TAG_DELETE_USER_MESSAGE = "delete_user_message";

    //user friend
    public static final String TAG_USER_FRIEND_REQUEST = "request_user_friend";
    public static final String TAG_USER_FRIEND_STATUS = "status_user_friend";
    public static final String TAG_USER_FRIEND_CHANGE_STATUS = "change_status_user_friend";
    public static final String TAG_USER_FRIEND_LIST = "list_user_friend";
    public static final String TAG_USER_FRIEND_REVOKE = "revoke_user_friend";
    public static final String TAG_USER_FRIEND_RECOMMEND = "recommend_user_friend";
    public static final String TAG_USER_FRIEND_SYNC = "sync_user_friend";
    public static final String TAG_USER_FRIEND_VALIDATE_SYNC = "validate_sync_user_friend";


    //log
    public static final String TAG_LOG_STORE = "store_log";


    //universal report
    public static final String TAG_REPORT_UNIVERSAL = "report_universal";

    //universal_match
    public static final String TAG_UNIVERSAL_MATCH_REQUEST = "request_universal_match";
    public static final String TAG_UNIVERSAL_MATCH_STATUS = "status_universal_match";
    public static final String TAG_UNIVERSAL_MATCH_RESULT = "result_universal_match";
    public static final String TAG_UNIVERSAL_MATCH_INDEX = "index_universal_match";
    public static final String TAG_UNIVERSAL_MATCH_CHANGE_STATUS = "change_status_universal_match";
    public static final String TAG_UNIVERSAL_MATCH_REMATCH_REQUEST = "rematch_request_universal_match";



    ////////////////////////////////////////////////////////

    // ERROR codes
    //public
    public static final int ERROR_UNDEFINED_CODE = -1;
    public static final int ERROR_ITEM_EXIST_CODE = 1;
    public static final int ERROR_INSERT_FAIL_CODE = 2;
    public static final int ERROR_TOKEN_MISMACH_CODE = 3;
    public static final int ERROR_DEFECTIVE_INFORMATION_CODE = 4;
    public static final int ERROR_TOKEN_BLACKLISTED_CODE = 5;
    public static final int ERROR_INVALID_FILE_SIZE_CODE = 6;
    public static final int ERROR_UPDATE_FAIL_CODE = 7;
    public static final int ERROR_DELETE_FAIL_CODE = 8;
    public static final int ERROR_OPERATION_FAIL_CODE = 9;
    public static final int ERROR_ITEM_NOT_EXIST_CODE = 13;
    public static final int ERROR_AUTH_FAIL_CODE = 14;
    public static final int ERROR_UNAUTHORIZED_ACCESS_CODE = 16;
    public static final int ERROR_EGG_ALREDY_GIVEN_CODE = 18;
    public static final int ERROR_WRONG_CHARSET = 25;
    public static final int ERROR_TOKEN_INVALID = 32;


    //user
    public static final int ERROR_USER_EXIST_CODE = 10;
    public static final int ERROR_REGISTER_FAIL_CODE = 11;
    public static final int ERROR_LOGIN_FAIL_CODE = 12;
    public static final int ERROR_EMAIL_EXIST_CODE = 15;
    public static final int ERROR_NOT_ENOUGH_BALANCE_USER_CODE = 19;
    public static final int ERROR_PASSWORD_MISMATCH_CODE = 23;
    public static final int ERROR_EXCEED_REGISTER_COUNT = 24;


    //match
    public static final int ERROR_NO_OPPONENT_AVAILABLE_CODE = 20;
    public static final int ERROR_OPPONENT_CANCELED_CODE = 30;
    public static final int ERROR_OPPONENT_ACCEPTED_CODE = 31;

    //market
    public static final int ERROR_INVALID_PUBLIC_KEY_CODE = 21;

    //draw
    public static final int ERROR_NOT_TIME_DRAW_CODE = 17;

    //message
    public static final int ERROR_INVALID_CODE_OF_INVITE_CODE = 22;

    //user friend
    public static final int ERROR_STATUS_BLOCKED_CODE = 26;
    public static final int ERROR_STATUS_DELETED_CODE = 27;
    public static final int ERROR_STATUS_DECLINED_CODE = 28;
    public static final int ERROR_STATUS_ANSWERED_CODE = 29;



    /////////////////////////////////////////////////////////



    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public Boolean getMust_logout() {
        return must_logout;
    }

    public void setMust_logout(Boolean must_logout) {
        this.must_logout = must_logout;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }


    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public Boolean getBoolianItem() {
        return boolianItem;
    }

    public void setBoolianItem(Boolean boolianItem) {
        this.boolianItem = boolianItem;
    }

    private UserModel user;

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
    /////////////////////////////////////////////
    private Integer error;
    private String token;
    private String error_msg;
    private String tag;
    private Boolean must_logout;
    private Boolean boolianItem;
    //////
    private Object item;
    private List<UserModel> users;
    private List<Object> items;

    ///////////////////////////////////////
    private Context cntx;

    ////////////////////////////////
    public RequestRespondModel(Context cntx) {
        this.error = null;
        this.error_msg = null;
        this.user = null;
        this.tag = null;
        this.token = null;
        this.users = new ArrayList<>();
        this.must_logout = false;

        this.cntx = cntx;
    }

    public void decodeJsonResponse(String response)
    {
        try
        {
            JSONObject jObj = new JSONObject(response);
            this.error = jObj.getInt("error");


            // Check for error node in json
            if (error == 0)
            {
                this.setTag( jObj.getString("tag"));
                switch (this.getTag().toLowerCase())
                {
                    case TAG_LOGIN_USER:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
                        this.token = jObj.getString("token");

                        JSONObject user = jObj.getJSONObject("user");
                        UserModel user_tmp = new UserModel();
                        user_tmp.setUserName(user.getString("user_name"));
                        user_tmp.setInviteCode(user.getString("invite_code"));
                        user_tmp.setEmail(user.getString("email"));
                        user_tmp.setTel(user.getString("tel"));
                        user_tmp.setBirthDate(user.getString("birth_date"));
                        user_tmp.setHazel(new Integer(user.getString("hazel")));
                        user_tmp.setLuck(new Integer(user.getString("luck")));
                        user_tmp.setLevelScore(new Integer(user.getString("level")));
                        user_tmp.setCups(user.getString("cups"));
                        user_tmp.setAllowChat(user.getString("allowChat"));

                        String[] temp = Utility.tokenDecode(this.token);
                        JSONObject jobjid = new JSONObject(temp[1]);

                        user_tmp.setId(new BigInteger(jobjid.getString("user_id")));

                        user_tmp.setGender((user.isNull( "gender" )?"0":user.getString("gender")));

                        user_tmp.setProfilePicture(user.getString("image"));
                        this.user = user_tmp;
                        this.error = null;

                        // meysam - read all 4 friendshp releated lists ans sync with database...
                        JSONArray users = jObj.getJSONArray("list_users_requested_friendship");
                        UserModel usertmp;
                        ArrayList<UserModel> usersTemp = new ArrayList<>();
                        for(int i = 0; i <users.length(); i++ )
                        {
                            usertmp = new UserModel();
                            JSONObject item = users.getJSONObject(i);

                            if(item.getString("user_name").contains("Visitor_"))
                                usertmp.setVisitorUserName(item.getString("user_name"));
                            else
                                usertmp.setUserName(item.getString("user_name"));

                            usertmp.setLuck(new Integer(item.getString("luck")));
                            usertmp.setLevelScore(new Integer(item.getString("level")));
                            usertmp.setCups(item.getString("cups"));
                            usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                            usertmp.setHazel(new Integer(item.getString("hazel")));
                            usertmp.setId(new BigInteger(item.getString("user_id")));
                            usertmp.setProfilePicture(item.getString("image"));
                            usertmp.setAllowChat(item.getString("allowChat"));


                            usersTemp.add(usertmp);

                        }
                        UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_REQUESTED);
                        UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED,true,cntx);

                        users = jObj.getJSONArray("list_users_received_friendship_requests");
                        usersTemp = new ArrayList<>();
                        for(int i = 0; i <users.length(); i++ )
                        {
                            usertmp = new UserModel();
                            JSONObject item = users.getJSONObject(i);

                            if(item.getString("user_name").contains("Visitor_"))
                                usertmp.setVisitorUserName(item.getString("user_name"));
                            else
                                usertmp.setUserName(item.getString("user_name"));

                            usertmp.setLuck(new Integer(item.getString("luck")));
                            usertmp.setLevelScore(new Integer(item.getString("level")));
                            usertmp.setCups(item.getString("cups"));
                            usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                            usertmp.setHazel(new Integer(item.getString("hazel")));
                            usertmp.setId(new BigInteger(item.getString("user_id")));
                            usertmp.setProfilePicture(item.getString("image"));
                            usertmp.setAllowChat(item.getString("allowChat"));

                            usersTemp.add(usertmp);

                        }
                        UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_NORMAL);
                        UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_NORMAL,true,cntx);

                        users = jObj.getJSONArray("list_users_friends");
                        usersTemp = new ArrayList<>();
                        for(int i = 0; i <users.length(); i++ )
                        {
                            usertmp = new UserModel();
                            JSONObject item = users.getJSONObject(i);

                            if(item.getString("user_name").contains("Visitor_"))
                                usertmp.setVisitorUserName(item.getString("user_name"));
                            else
                                usertmp.setUserName(item.getString("user_name"));

                            usertmp.setLuck(new Integer(item.getString("luck")));
                            usertmp.setLevelScore(new Integer(item.getString("level")));
                            usertmp.setCups(item.getString("cups"));
                            usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                            usertmp.setHazel(new Integer(item.getString("hazel")));
                            usertmp.setId(new BigInteger(item.getString("user_id")));
                            usertmp.setProfilePicture(item.getString("image"));
                            usertmp.setAllowChat(item.getString("allowChat"));

                            usersTemp.add(usertmp);

                        }
                        UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_ACCEPTED);
                        UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED,true,cntx);

                        users = jObj.getJSONArray("list_users_blocked");
                        usersTemp = new ArrayList<>();
                        for(int i = 0; i <users.length(); i++ )
                        {
                            usertmp = new UserModel();
                            JSONObject item = users.getJSONObject(i);

                            if(item.getString("user_name").contains("Visitor_"))
                                usertmp.setVisitorUserName(item.getString("user_name"));
                            else
                                usertmp.setUserName(item.getString("user_name"));

                            usertmp.setLuck(new Integer(item.getString("luck")));
                            usertmp.setLevelScore(new Integer(item.getString("level")));
                            usertmp.setCups(item.getString("cups"));
                            usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                            usertmp.setHazel(new Integer(item.getString("hazel")));
                            usertmp.setId(new BigInteger(item.getString("user_id")));
                            usertmp.setProfilePicture(item.getString("image"));
                            usertmp.setAllowChat(item.getString("allowChat"));

                            usersTemp.add(usertmp);

                        }
                        UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                        UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED,true,cntx);
                        ///////////////////////////////////////////////////////////////////////////////


                        break;
                    }
                    case TAG_REGISTER_USER:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
                        this.token = jObj.getString("token");

                        UserModel user_tmp = new UserModel();
                        user_tmp.setInviteCode( jObj.getString("invite_code"));
                        this.user = user_tmp;
                        this.error = null;
                        break;
                    }
                    case TAG_VISITOR_REGISTER_USER:
                    {

                        // user successfully logged in
                        // Now store the user in SQLite
                        this.token = jObj.getString("token");

                        String[] temp = Utility.tokenDecode(this.token);
                        JSONObject jobjid = new JSONObject(temp[1]);

                        JSONObject user = jObj.getJSONObject("user");
                        UserModel user_tmp = new UserModel();
                        user_tmp.setInviteCode(user.getString("invite_code"));
                        user_tmp.setHazel(new Integer(user.getString("hazel")));
                        user_tmp.setLuck(new Integer(user.getString("luck")));
                        user_tmp.setLevelScore(new Integer(user.getString("level")));
                        user_tmp.setCups(user.getString("cups"));
                        user_tmp.setAllowChat(user.getString("allowChat"));
                        user_tmp.setId(new BigInteger(jobjid.getString("user_id")));
                        user_tmp.setVisitorUserName(Utility.generateVisitorCode(cntx));


                        // meysam - read all 4 friendshp releated lists ans sync with database...
                        if(jObj.has("list_users_requested_friendship"))
                        {
                            JSONArray users = jObj.getJSONArray("list_users_requested_friendship");
                            UserModel usertmp;
                            ArrayList<UserModel> usersTemp = new ArrayList<>();
                            for(int i = 0; i <users.length(); i++ )
                            {
                                usertmp = new UserModel();
                                JSONObject item = users.getJSONObject(i);

                                if(item.getString("user_name").contains("Visitor_"))
                                    usertmp.setVisitorUserName(item.getString("user_name"));
                                else
                                    usertmp.setUserName(item.getString("user_name"));

                                usertmp.setLuck(new Integer(item.getString("luck")));
                                usertmp.setLevelScore(new Integer(item.getString("level")));
                                usertmp.setCups(item.getString("cups"));
                                usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                                usertmp.setHazel(new Integer(item.getString("hazel")));
                                usertmp.setId(new BigInteger(item.getString("user_id")));
                                usertmp.setProfilePicture(item.getString("image"));
                                usertmp.setAllowChat(item.getString("allowChat"));

                                usersTemp.add(usertmp);

                            }
                            UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_REQUESTED);
                            UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED,true,cntx);

                            users = jObj.getJSONArray("list_users_received_friendship_requests");
                            usersTemp = new ArrayList<>();
                            for(int i = 0; i <users.length(); i++ )
                            {
                                usertmp = new UserModel();
                                JSONObject item = users.getJSONObject(i);

                                if(item.getString("user_name").contains("Visitor_"))
                                    usertmp.setVisitorUserName(item.getString("user_name"));
                                else
                                    usertmp.setUserName(item.getString("user_name"));

                                usertmp.setLuck(new Integer(item.getString("luck")));
                                usertmp.setLevelScore(new Integer(item.getString("level")));
                                usertmp.setCups(item.getString("cups"));
                                usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                                usertmp.setHazel(new Integer(item.getString("hazel")));
                                usertmp.setId(new BigInteger(item.getString("user_id")));
                                usertmp.setProfilePicture(item.getString("image"));
                                usertmp.setAllowChat(item.getString("allowChat"));

                                usersTemp.add(usertmp);

                            }
                            UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_NORMAL);
                            UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_NORMAL,true,cntx);

                            users = jObj.getJSONArray("list_users_friends");
                            usersTemp = new ArrayList<>();
                            for(int i = 0; i <users.length(); i++ )
                            {
                                usertmp = new UserModel();
                                JSONObject item = users.getJSONObject(i);

                                if(item.getString("user_name").contains("Visitor_"))
                                    usertmp.setVisitorUserName(item.getString("user_name"));
                                else
                                    usertmp.setUserName(item.getString("user_name"));

                                usertmp.setLuck(new Integer(item.getString("luck")));
                                usertmp.setLevelScore(new Integer(item.getString("level")));
                                usertmp.setCups(item.getString("cups"));
                                usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                                usertmp.setHazel(new Integer(item.getString("hazel")));
                                usertmp.setId(new BigInteger(item.getString("user_id")));
                                usertmp.setProfilePicture(item.getString("image"));
                                usertmp.setAllowChat(item.getString("allowChat"));

                                usersTemp.add(usertmp);

                            }
                            UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_ACCEPTED);
                            UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED,true,cntx);

                            users = jObj.getJSONArray("list_users_blocked");
                            usersTemp = new ArrayList<>();
                            for(int i = 0; i <users.length(); i++ )
                            {
                                usertmp = new UserModel();
                                JSONObject item = users.getJSONObject(i);

                                if(item.getString("user_name").contains("Visitor_"))
                                    usertmp.setVisitorUserName(item.getString("user_name"));
                                else
                                    usertmp.setUserName(item.getString("user_name"));

                                usertmp.setLuck(new Integer(item.getString("luck")));
                                usertmp.setLevelScore(new Integer(item.getString("level")));
                                usertmp.setCups(item.getString("cups"));
                                usertmp.setFriendsCount(new Integer(item.getString("friends_count")));
                                usertmp.setHazel(new Integer(item.getString("hazel")));
                                usertmp.setId(new BigInteger(item.getString("user_id")));
                                usertmp.setProfilePicture(item.getString("image"));
                                usertmp.setAllowChat(item.getString("allowChat"));

                                usersTemp.add(usertmp);

                            }
                            UserModel.setMassFriendshipStatus(usersTemp,UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                            UserModel.saveListFriendWRTStatus(usersTemp,UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED,true,cntx);
                            ///////////////////////////////////////////////////////////////////////////////

                        }

                        this.user = user_tmp;
                        this.error = null;
                        break;
                    }
                    case TAG_EDIT_USER:
                    {
                        // edit user successfully in server
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_RANK_USER:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
                        this.token = jObj.getString("token");

                        JSONArray users = jObj.getJSONArray("users");
                        UserModel user;
                        this.items = new ArrayList<>();
                        for(int i = 0; i <users.length(); i++ )
                        {
                            user = new UserModel();
                            JSONObject item = users.getJSONObject(i);

//                            user.setUserName(item.getString("user_name"));
                            if(item.getString("user_name").contains("Visitor_"))
                                user.setVisitorUserName(item.getString("user_name"));
                            else
                                user.setUserName(item.getString("user_name"));

                            user.setLuck(new Integer(item.getString("luck")));
                            user.setLevelScore(new Integer(item.getString("level")));
                            user.setCups(item.getString("cups"));
                            user.setId(new BigInteger(item.getString("user_id")));
                            if(item.has("rank"))
                                user.setRank(new Integer(item.getString("rank")));
                            user.setGender((item.isNull( "gender" )?"0":item.getString("gender")));

                            user.setProfilePicture(item.getString("image"));

                            this.items.add(user);


                        }

                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_INDEX_HOME:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        this.items.add( jObj.getString("version"));
                        this.items.add( jObj.getString("link"));
                        this.items.add( jObj.getString("egg_reward"));
                        this.items.add( jObj.getString("unread_message_count"));


                        AdvertismentModel advertisment = null;
                        if(!jObj.has("top_advertisment"))
                        {
                            this.items.add("null");
                        }
                        else
                        {
                            JSONObject top_advertisment = jObj.getJSONObject("top_advertisment");
                            advertisment = new AdvertismentModel(cntx);
//                        JSONObject item = top_advertisment.getJSONObject(0);
                            advertisment.setTitle(top_advertisment.getString("title"));
                            advertisment.setDescription(top_advertisment.getString("description"));
                            advertisment.setLink(top_advertisment.getString("link"));
                            this.items.add(advertisment);
                        }

                        if(!jObj.has("bottom_advertisment"))
                        {
                            this.items.add("null");
                        }
                        else
                        {
                            JSONObject botton_advertisment = jObj.getJSONObject("bottom_advertisment");
                            advertisment = new AdvertismentModel(cntx);
                            advertisment.setTitle(botton_advertisment.getString("title"));
                            advertisment.setDescription(botton_advertisment.getString("description"));
                            advertisment.setLink(botton_advertisment.getString("link"));
                            this.items.add(advertisment);
                        }



                        this.items.add( jObj.getString("hazel"));
                        this.items.add( jObj.getString("luck"));

                        this.items.add( jObj.getString("ad_main_count"));
                        this.items.add( jObj.getString("server_check_interval"));
//                        if(jObj.has("received_friend_request_count"))
                            this.items.add( jObj.getString("received_friend_request_count"));
                        this.items.add(jObj.getString("level"));
                        this.items.add(jObj.getString("cups"));
                        if(jObj.getString("message").equals(""))
                        {
                            this.items.add("null");
                        }
                        else
                        {
                            this.items.add( jObj.getString("message"));
                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNREAD_HOME:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        this.items.add( jObj.getString("unread_message_count"));
//                        if(jObj.has("received_friend_request_count"))
                            this.items.add( jObj.getString("received_friend_request_count"));


                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_GET_VERSION_HOME:
                    {
                        this.items = new ArrayList<>();

                        this.items.add( jObj.getString("version"));
                        this.items.add( jObj.getString("link"));

                        if(jObj.getString("message").equals(""))
                        {
                            this.items.add("null");
                        }
                        else
                        {
                            this.items.add( jObj.getString("message"));
                        }

                        this.error = 0;
                        break;
                    }
                    case TAG_EGG_HOME:
                    {
                        this.token = jObj.getString("token");

                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_STORE_CONTACT_US:
                    {
                        // edit user successfully in server
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_GET_PUBLIC_KEY_HOME:
                    {
                        this.items = new ArrayList<>();

                        this.items.add( jObj.getString("public_key"));
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_NEXT_QUESTION:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
                        this.token = jObj.getString("token");

                        JSONObject question = jObj.getJSONObject("question");
                        QuestionModel question_tmp = new QuestionModel();
                        question_tmp.setId(new BigInteger(question.getString("question_id")));
                        question_tmp.setGuid(question.getString("question_guid"));
                        question_tmp.setDescription(question.getString("description"));
                        question_tmp.setAnswer(question.getString("answer"));
                        question_tmp.setMaxHazelReward(new Integer(question.getString("max_hazel_reward")));
                        question_tmp.setMinHazelReward(new Integer(question.getString("min_hazel_reward")));
                        question_tmp.setMaxLuckReward(new Integer(question.getString("max_luck_reward")));
                        question_tmp.setMinLuckReward(new Integer(question.getString("min_luck_reward")));
                        question_tmp.setPenalty(new Integer(question.getString("penalty")));
                        question_tmp.setCategoryId(new Integer(question.getString("category_id")));
                        if(!question.isNull( "image" ))
                        {
                            question_tmp.setImage(Utility.getBitmapImage(question.getString("image")));
                        }

                        if(question_tmp.getMaxLuckReward().equals(question_tmp.getMinLuckReward()))
                            question_tmp.setFinalLuckReward(question_tmp.getMinLuckReward());
                        else
                            question_tmp.setFinalLuckReward(new Random().nextInt(question_tmp.getMaxLuckReward()-question_tmp.getMinLuckReward())+question_tmp.getMinLuckReward());
                        if(question_tmp.getMaxHazelReward().equals(question_tmp.getMinHazelReward()))
                            question_tmp.setFinalHazelReward(question_tmp.getMinHazelReward());
                        else
                            question_tmp.setFinalHazelReward(new Random().nextInt(question_tmp.getMaxHazelReward()-question_tmp.getMinHazelReward())+question_tmp.getMinHazelReward());

                        this.item = question_tmp;
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_INDEX_MESSAGE:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray messages = jObj.getJSONArray("messages");
                        MessageModel message;

                        for(int i = 0; i <messages.length(); i++ )
                        {
                            message = new MessageModel();
                            JSONObject item = messages.getJSONObject(i);
                            message.setTitle(item.getString("title"));
                            message.setDescription(item.getString("description"));
                            message.setReaded(item.getString("is_readed").equals("1")?true:false);
                            message.setUserMessageId(new BigInteger(item.getString("user_message_id")));
//                            message.setGuid(item.getString("message_guid"));

                            this.items.add(message);

                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_READ_MESSAGE:
                    {
                        // set read message successfully in server
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_DELETE_USER_MESSAGE:
                    {
                        // set read message successfully in server
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_INDEX_DRAW:
                    {

                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray draws = jObj.getJSONArray("draws");
                        DrawModel draw;

                        for(int i = 0; i <draws.length(); i++ )
                        {
                            draw = new DrawModel();
                            JSONObject item = draws.getJSONObject(i);
                            draw.setDrawDateTime(item.getString("draw_date_time"));
                            draw.setCost(item.getString("cost"));
                            draw.setSponser(item.getString("sponser"));
                            draw.setParticipated(item.getInt("is_participate") == 0?false:true);
                            draw.setDescription(item.getString("description"));
                            draw.setId(new BigInteger(item.getString("draw_id")));
                            draw.setGuid(item.getString("draw_guid"));
                            if(!item.getString("link").equals("null"))
                                draw.setLink(item.getString("link"));
                            if(!item.isNull( "image" ))
                            {
                                draw.setImage(Utility.getBitmapImage(item.getString("image")));
                            }

                            JSONArray rewards = item.getJSONArray("rewards");
                            for(int j = 0; j <rewards.length(); j++ )
                            {
                                RewardModel reward = new RewardModel();
                                JSONObject reward_j = rewards.getJSONObject(j);
                                reward.setDescription(reward_j.getString("description"));
                                reward.setCount(Integer.parseInt(reward_j.getString("count")));
                                reward.setUnit(reward_j.getString("unit"));

                                draw.getRewards().add(reward);

                            }

                            this.items.add(draw);

                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_WINNERS_DRAW:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray winners = jObj.getJSONArray("winners");
                        UserModel winner;

                        for(int i = 0; i <winners.length(); i++ )
                        {
                            winner = new UserModel();
                            JSONObject item = winners.getJSONObject(i);

                            if(item.getString("user_name").contains("Visitor_"))
                                winner.setUserName(UserModel.getVisitorHashedName(item.getString("user_name")));
                            else
                                winner.setUserName(item.getString("user_name"));

//                            winner.setUserName(item.getString("user_name"));
                            winner.setTel(item.getString("tel"));
                            winner.setGender(item.getString("gender"));
                            winner.setReward(item.getString("reward_description"));
                            winner.setProfilePicture(item.getString("image"));

                            this.items.add(winner);

                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_PARTICIPATE_DRAW:
                    {
                        // set read message successfully in server
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_PROFILE_USER:
                    {
                        // set read message successfully in server
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        ArrayList<String> tmp = new ArrayList<>();

                        tmp.add(jObj.getString("match_win_count"));
                        tmp.add(jObj.getString("match_lost_count"));
                        tmp.add(jObj.getString("match_equal_count"));

                        this.item = tmp;

                        JSONArray categories = jObj.getJSONArray("categories_information");
                        CategoryModel category;

                        for(int i = 0; i <categories.length(); i++ )
                        {
                            category = new CategoryModel(cntx);
                            JSONObject item = categories.getJSONObject(i);
                            category.setId(new Integer(item.getString("category_id")));
                            category.setTotalAnsweredCount(new Integer(item.getString("total_answered_count")));
                            category.setCorrectAnsweredCount(new Integer(item.getString("correct_answered_count")));
                            category.setDailyTotalAnsweredCount(new Integer(item.getString("daily_total_answered_count")));
                            category.setDailyCorrectAnsweredCount(new Integer(item.getString("daily_correct_answered_count")));
                            category.setWeeklyTotalAnsweredCount(new Integer(item.getString("weekly_total_answered_count")));
                            category.setWeeklyCorrectAnsweredCount(new Integer(item.getString("weekly_correct_answered_count")));

                            this.items.add(category);
                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_MARKET_INDEX:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();
                        this.item = jObj.getString("public_key");

                        JSONArray costs = jObj.getJSONArray("costs");
                        MarketModel market;

                        for(int i = 0; i <costs.length(); i++ )
                        {
                            market = new MarketModel();
//                            market.setTitlePersian( costs.getString(i));

                            JSONObject item = costs.getJSONObject(i);
                            market.setTitlePersian( item.getString("product_title"));
                            market.setDescriptionPersian(item.getString("product_description"));
                            market.setAmount( item.getString("product_amount"));
                            market.setMarketId( item.getString("product_id"));
                            market.setCost( item.getString("product_cost"));

                            this.items.add(market);
                        }

                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_MARKET_PURCHASE:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_MARKET_CONVERT:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_BODY_PART_INDEX:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray bodyParts = jObj.getJSONArray("body_parts");
                        BodyPartModel bodyPart;

                        for(int i = 0; i <bodyParts.length(); i++ )
                        {
                            bodyPart = new BodyPartModel(cntx);
                            JSONObject item = bodyParts.getJSONObject(i);
                            bodyPart.setName(item.getString("title"));
                            bodyPart.setCost(new Integer(item.getString("cost")));

                            this.items.add(bodyPart);
                        }

                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_BODY_PART_PURCHASE:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UPDATE_PROFILE_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
//                    case TAG_MATCH_REQUEST:
//                    {
//                        this.token = jObj.getString("token");
//
//                        MatchModel match = new MatchModel(cntx);
//                        ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
//                        UserModel opponent = new UserModel();
//
//                        if(jObj.has("user_match_id"))
//                        {
//                            match.setId(new BigInteger(jObj.getString("user_match_id")));
//                            match.setGuid(jObj.getString("user_match_guid"));
//                            JSONObject opponent_t = jObj.getJSONObject("opponent");
//                            opponent.setLuck(new Integer(opponent_t.getString("luck")));
//                            opponent.setLevelScore(new Integer(opponent_t.getString("level")));
//                            opponent.setCups(opponent_t.getString("cups"));
//                            opponent.setProfilePicture(opponent_t.getString("image"));
////                            opponent.setTotalMatchCount(opponent_t.getString("total_match_count"));
////                            opponent.setWinMatchCount(opponent_t.getString("win_match_count"));
////                            if(opponent_t.getString("user_name").contains("Visitor_"))
////                                opponent.setUserName(UserModel.getVisitorHashedName(opponent_t.getString("user_name")));
////                            else
//                                opponent.setUserName(opponent_t.getString("user_name"));
//                            match.setOpponent(opponent);
//
//                            JSONArray questions_t = jObj.getJSONArray("questions");
//                            QuestionModel question;
//
//                            for(int i = 0; i <questions_t.length(); i++ )
//                            {
//                                question = new QuestionModel();
//                                JSONObject item = questions_t.getJSONObject(i);
//                                question.setGuid(item.getString("question_guid"));
//                                question.setId(new BigInteger(item.getString("question_id")));
//                                question.setDescription(item.getString("description"));
//                                question.setCategoryId(new Integer(item.getString("category_id")));
//                                question.setAnswer(item.getString("answer"));
//                                question.setPenalty(Integer.valueOf(item.getString("penalty")));
//                                question.setCorrect(false);
//                                if(!item.isNull( "image" ))
//                                {
//                                    question.setImage(Utility.getBitmapImage(item.getString("image")));
//                                }
//
//                                questions.add(question);
//                            }
//                            match.setQuestions(questions);
//                            this.item = match;
//                        }
//                        else
//                        {
//                            this.item = null;
//                        }
//
//
//                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
//                        break;
//                    }
//                    case TAG_MATCH_RESULT:
//                    {
//                        this.token = jObj.getString("token");
//                        MatchModel match = new MatchModel();
//                        UserModel user = new UserModel();
//
//                        match.setEnded(jObj.getInt("is_ended") == 0?false:true);
//
//                        if(jObj.has("opponent"))
//                        {
//                            match.setResult(MatchModel.EQUAL);
//                            JSONObject opponenet = jObj.getJSONObject("opponent");
//                            match.setOpponentSpentTime(opponenet.getString("time"));
//                            match.setOpponentCorrectCount(opponenet.getString("correct_count"));
//                            if(opponenet.getString("user_name").contains("Visitor_"))
//                                user.setUserName(UserModel.getVisitorHashedName(opponenet.getString("user_name")));
//                            else
//                                user.setUserName(opponenet.getString("user_name"));
//                            match.setOpponent(user);
//                        }
//                        else if(jObj.has("winner"))
//                        {
//                            JSONObject winner = jObj.getJSONObject("winner");
//                            match.setOpponentSpentTime(winner.getString("time"));
//                            match.setOpponentCorrectCount(winner.getString("correct_count"));
//                            user.setUserName(winner.getString("user_name"));
//                            SessionModel session = new SessionModel(cntx);
//                            if(session.getCurrentUser().getUserName() != null)
//                            {
//                                if(session.getCurrentUser().getUserName().equals(user.getUserName()))
//                                {
//                                    match.setResult(MatchModel.WIN);
//                                    match.setWinner(MatchModel.WIN);
//
//                                }
//                                else
//                                {
//                                    match.setResult(MatchModel.LOST);
//                                    match.setWinner(MatchModel.LOST);
//                                }
//                            }
//                            else
//                            {
//                                if(session.getCurrentUser().getVisitorUserName().equals(user.getUserName()))
//                                {
//                                    match.setResult(MatchModel.WIN);
//                                    match.setWinner(MatchModel.WIN);
//
//                                }
//                                else
//                                {
//                                    match.setResult(MatchModel.LOST);
//                                    match.setWinner(MatchModel.LOST);
//                                }
//                            }
//
//                        }
//                        else
//                        {
//                            match.setResult(MatchModel.IN_PROGRESS);
//                        }
//
//                        this.item = match;
//                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
//                        break;
//                    }
                    case TAG_ADVERTISMENT_HOME:
                    {
                        this.token = jObj.getString("token");

                        this.item =  jObj.getString("ad_reward");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
//                    case TAG_MATCH_STATUS:
//                    {
//                        this.token = jObj.getString("token");
//                        MatchModel match = new MatchModel(cntx);
//                        UserModel user = new UserModel();
//
//                        match.setEnded(jObj.getString("is_ended").equals("1")?true:false);
//
//                        if(jObj.has("opponent"))
//                        {
//                            match.setResult(MatchModel.EQUAL);
//                            JSONObject opponenet = jObj.getJSONObject("opponent");
//                            match.setOpponentSpentTime(opponenet.getString("time"));
//                            match.setOpponentCorrectCount(opponenet.getString("correct_count"));
//                            user.setUserName(opponenet.getString("user_name"));
//                            match.setOpponent(user);
//                        }
//                        else if(jObj.has("winner"))
//                        {
//                            JSONObject winner = jObj.getJSONObject("winner");
//                            match.setOpponentSpentTime(winner.getString("time"));
//                            match.setOpponentCorrectCount(winner.getString("correct_count"));
//                            user.setUserName(winner.getString("user_name"));
//                            SessionModel session = new SessionModel(cntx);
//                            if(session.getCurrentUser().getUserName() == null)
//                            {
//                                if(session.getCurrentUser().getVisitorUserName().equals(user.getUserName()))
//                                {
//                                    match.setResult(MatchModel.WIN);
//                                }
//                                else
//                                {
//                                    match.setResult(MatchModel.LOST);
//                                }
//                            }
//                            else
//                            {
//                                if(session.getCurrentUser().getUserName().equals(user.getUserName()))
//                                {
//                                    match.setResult(MatchModel.WIN);
//
//                                }
//                                else
//                                {
//                                    match.setResult(MatchModel.LOST);
//                                }
//                            }
//                        }
//                        else
//                        {
//                            match.setResult(MatchModel.IN_PROGRESS);
//                        }
//
//                        this.item = match;
//                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
//                        break;
//                    }
//                    case TAG_MATCH_INDEX:
//                    {
//                        this.token = jObj.getString("token");
//                        this.items = new ArrayList<>();
//
//                        JSONArray matchs = jObj.getJSONArray("matches");
//                        MatchModel match;
//
//                        for(int i = 0; i <matchs.length(); i++ )
//                        {
//
//
//                            match = new MatchModel(cntx);
//                            JSONObject item = matchs.getJSONObject(i);
//                            if(item.has("opponent_image") || item.has("opponent_user_name"))
//                            {
//                                match.setEnded(item.getString("is_ended").equals("1")?true:false);
//                                if(!match.getEnded())
//                                {
//                                    match.setId(new BigInteger(item.getString("user_match_id")));
//                                    match.setGuid(item.getString("user_match_guid"));
//
//                                }
//                                else
//                                {
//                                    match.setOpponentCorrectCount(item.getString("opponent_correct_count"));
//                                    match.setOpponentSpentTime(item.getString("opponent_time"));
//                                    if(!item.isNull( "winner" ))
//                                    {
//                                        match.setWinner(new Integer(item.getString("winner")));
//                                        match.setResult(new Integer(item.getString("winner")));
//                                    }
//                                }
//
//                                UserModel user = new UserModel();
//                                user.setProfilePicture(item.getString("opponent_image"));
//                                user.setLuck(new Integer(item.getString("opponent_luck")));
//                                user.setLevelScore(new Integer(item.getString("opponent_level")));
//                                user.setCups(item.getString("opponent_cups"));
//                                user.setUserName(item.getString("opponent_user_name"));
//
//                                match.setOpponent(user);
//                                match.setSelfCorrectCount(item.getString("self_correct_count"));
//                                match.setSelfSpentTime(item.getString("self_time"));
//                                match.setBet(item.getString("bet"));
//
//                                if(user.getUserName() != null)
//                                    this.items.add(match);
//                            }
//
//                        }
//
//                        JSONArray bets = jObj.getJSONArray("bets");
//                        BetModel bet;
//
//                        ArrayList<BetModel> tmp_bets = new ArrayList<>();
//
//                        for(int i = 0; i <bets.length(); i++ )
//                        {
//                            bet = new BetModel(cntx);
//                            JSONObject item = bets.getJSONObject(i);
//                            bet.setBetId(item.getString("bet_id"));
//                            bet.setAmount(new Integer(item.getString("bet_amount")));
//                            bet.setInterest(new Integer(item.getString("bet_interest")));
//                            bet.setTime(new Integer(item.getString("bet_time")));
//                            if(item.has("bet_reward_time"))
//                            {
//                                bet.setRewardTime(new Integer(item.getString("bet_reward_time")));
//                                bet.setNitro(true);
//                            }
//                            if(item.has("bet_is_on_luck"))
//                                bet.setBetLuck(true);
//
//                            tmp_bets.add(bet);
//                        }
//                        this.item =new ArrayList<BetModel>();
//                        this.item = tmp_bets;
//                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
//                        break;
//                    }
                    case TAG_UPDATE_EMAIL_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UPDATE_GENDER_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UPDATE_PHONE_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UPDATE_BIRTH_DATE_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UPDATE_PASSWORD_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_DECREASE_HAZEL_LUCK_USER:
                    {
                        this.token = jObj.getString("token");

                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_LOG_STORE:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_REPORT_QUESTION:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_RATE_QUESTION:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_FORGET_PASSWORD_USER:
                    {
                        this.error = 0;
                        break;
                    }
                    case TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_INFO_USER:
                    {
                        this.token = jObj.getString("token");
//                        this.items = new ArrayList<>();
                        this.item =new UserModel();

                        UserModel user = new UserModel();
                        user.setHazel(new Integer(jObj.getString("hazel")));
                        user.setLuck(new Integer(jObj.getString("luck")));
                        user.setLevelScore(new Integer(jObj.getString("level")));
                        user.setCups(jObj.getString("cups"));
                        user.setFriendsCount(new Integer(jObj.getString("friends_count")));
                        user.setId(new BigInteger(jObj.getString("user_id")));
                        user.setProfilePicture(jObj.getString("image"));
                        user.setAllowChat(jObj.getString("allowChat"));
                        this.item = user;

                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_WORD_TABLE_QUESTION:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();
                        QuestionModel question;
                        JSONArray questions_t = jObj.getJSONArray("questions");

                        ArrayList<QuestionModel> tmpQuestions = new ArrayList<>();
                        for(int i = 0; i <questions_t.length(); i++ )
                        {
                            question = new QuestionModel();
                            JSONObject item = questions_t.getJSONObject(i);
                            question.setGuid(item.getString("question_guid"));
                            question.setId(new BigInteger(item.getString("question_id")));
                            question.setDescription(item.getString("description"));
                            question.setCategoryId(new Integer(item.getString("category_id")));
                            question.setAnswer(item.getString("answer").trim());
                            question.setPenalty(Integer.valueOf(item.getString("penalty")));
                            question.setMaxHazelReward(new Integer(item.getString("max_hazel_reward")));
                            question.setMinHazelReward(new Integer(item.getString("min_hazel_reward")));
                            question.setMaxLuckReward(new Integer(item.getString("max_luck_reward")));
                            question.setMinLuckReward(new Integer(item.getString("min_luck_reward")));
                            question.setCorrect(false);
                            question.setAnswered(false);

//                            items.add(question);
                            tmpQuestions.add(question);
                        }

                        tmpQuestions = QuestionModel.sortQuestionById(tmpQuestions);
                        for(int i = 0; i <tmpQuestions.size(); i++ )
                        {
                            items.add(tmpQuestions.get(i));
                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_CHANGE_HAZEL_LUCK_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_REPORT_TABLE_QUESTION:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_RATE_TABLE_QUESTION:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_USER_FRIEND_REQUEST:
                    {

                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_USER_FRIEND_STATUS:
                    {
                        this.items = new ArrayList<>();

                        this.items.add( jObj.getString("status"));
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_USER_FRIEND_CHANGE_STATUS:
                    {

                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_USER_FRIEND_LIST:
                    {

                        this.token = jObj.getString("token");

                        this.items = new ArrayList<>();

                        JSONArray users = jObj.getJSONArray("users");
                        UserModel user;

                        for(int i = 0; i <users.length(); i++ )
                        {


                            user = new UserModel();
                            JSONObject item = users.getJSONObject(i);

                            user.setId(new BigInteger(item.getString("user_id")));
                            user.setProfilePicture(item.getString("image"));
                            user.setLuck(new Integer(item.getString("luck")));
                            user.setLevelScore(new Integer(item.getString("level")));
                            user.setCups(item.getString("cups"));
                            user.setHazel(new Integer(item.getString("hazel")));
                            user.setUserName(item.getString("user_name"));
                            user.setFriendsCount(new Integer(item.getString("friends_count")));


                            this.items.add(user);


                        }


                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_USER_FRIEND_REVOKE:
                    {

                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_REPORT_UNIVERSAL:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_SEARCH_USER:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray users = jObj.getJSONArray("users");
                        UserModel user;

                        for(int i = 0; i <users.length(); i++ )
                        {
                            user = new UserModel();
                            JSONObject item = users.getJSONObject(i);
                            user.setId(new BigInteger(item.getString("user_id")));
                            user.setProfilePicture(item.getString("image"));
                            user.setLuck(new Integer(item.getString("luck")));
                            user.setLevelScore(new Integer(item.getString("level")));
                            user.setCups(item.getString("cups"));
                            user.setHazel(new Integer(item.getString("hazel")));
                            user.setUserName(item.getString("user_name"));
                            user.setFriendsCount(new Integer(item.getString("friends_count")));


                            this.items.add(user);

                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_USER_FRIEND_RECOMMEND:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray users = jObj.getJSONArray("users");
                        UserModel user;

                        for(int i = 0; i <users.length(); i++ )
                        {
                            user = new UserModel();
                            JSONObject item = users.getJSONObject(i);
                            user.setId(new BigInteger(item.getString("user_id")));
                            user.setProfilePicture(item.getString("image"));
                            user.setLuck(new Integer(item.getString("luck")));
                            user.setLevelScore(new Integer(item.getString("level")));
                            user.setCups(item.getString("cups"));
                            user.setHazel(new Integer(item.getString("hazel")));
                            user.setUserName(item.getString("user_name"));
                            user.setAllowChat(item.getString("allowChat"));

                            this.items.add(user);

                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_CROSS_TABLE_QUESTION:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();
                        this.items.add(jObj.getString("cross_table_id"));
                        this.items.add(jObj.getString("width"));
                        this.items.add(jObj.getString("height"));

                        JSONArray questions = jObj.getJSONArray("questions");
                        QuestionModel question;

                        for(int i = 0; i <questions.length(); i++ )
                        {
                            question = new QuestionModel();
                            JSONObject item = questions.getJSONObject(i);
                            question.setDescription(item.getString("description"));
                            question.setAnswer(item.getString("answer"));
                            question.setAnswerCells(new ArrayList<String>(Arrays.asList(item.getString("answer_position_cells").split("\\s*,\\s*"))));
                            question.setPositionCode(item.getString("position_code"));
                            question.setQuestionPosition(item.getString("question_position"));
                            question.setId(BigInteger.valueOf(i+1));

                            this.items.add(question);

                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNIVERSAL_MATCH_INDEX:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray matchs = jObj.getJSONArray("matches");
                        UniversalMatchModel match;

                        ArrayList<UniversalMatchModel> tmatches = new ArrayList<>();

                        for(int i = 0; i <matchs.length(); i++ )
                        {
                            JSONObject item = matchs.getJSONObject(i);
                            match = new UniversalMatchModel(item, cntx);
//                            this.items.add(match);
                            tmatches.add(match);
                        }

                        // meysam - sort based on turn....
                        tmatches = UniversalMatchModel.sortListByTurn(tmatches);
                        for(int i = 0; i < tmatches.size();i++)
                        {
                            this.items.add(tmatches.get(i));
                        }
                        /////////////////////////////////////////

                        JSONArray bets = jObj.getJSONArray("bets");
                        BetModel bet;

                        ArrayList<BetModel> tmp_bets = new ArrayList<>();

                        for(int i = 0; i <bets.length(); i++ )
                        {
                            bet = new BetModel(cntx);
                            JSONObject item = bets.getJSONObject(i);
                            bet.setBetId(item.getString("bet_id"));
                            bet.setAmount(new Integer(item.getString("bet_amount")));
                            bet.setInterest(new Integer(item.getString("bet_interest")));
                            bet.setTime(new Integer(item.getString("bet_time")));
                            if(item.has("bet_reward_time"))
                            {
                                bet.setRewardTime(new Integer(item.getString("bet_reward_time")));
                                bet.setNitro(true);
                            }
                            if(item.has("bet_is_on_luck"))
                                bet.setBetLuck(true);

                            tmp_bets.add(bet);
                        }
                        this.item =new ArrayList<BetModel>();
                        this.item = tmp_bets;
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNIVERSAL_MATCH_REQUEST:
                    {
                        this.token = jObj.getString("token");

                        UniversalMatchModel match = new UniversalMatchModel();

                        match.setMatchType(new Integer(jObj.getString("type")));
                        match.setOpponentType(new Integer(jObj.getString("opponent_type")));
                        match.setId(new BigInteger(jObj.getString("user_universal_match_id")));
                        UserModel opponent = new UserModel();

                        if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
                        {
                            ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();

//
                            if(match.getOpponentType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM))
                            {

                                if(jObj.has("opponent"))
                                {
                                    JSONObject opponent_t = jObj.getJSONObject("opponent");
                                    opponent.setProfilePicture(opponent_t.getString("image"));

                                    if(opponent_t.getString("user_name").contains("Visitor_"))
                                        opponent.setUserName(UserModel.getVisitorHashedName(opponent_t.getString("user_name")));
                                    else
                                        opponent.setUserName(opponent_t.getString("user_name"));


                                    opponent.setAllowChat(opponent_t.getString("allowChat"));
                                    match.setOpponent(opponent);

                                    JSONArray questions_t = jObj.getJSONArray("values");
                                    QuestionModel question;

                                    for(int i = 0; i <questions_t.length(); i++ )
                                    {
                                        question = new QuestionModel();
                                        JSONObject item = questions_t.getJSONObject(i);
                                        question.setId(new BigInteger(item.getString("question_id")));
                                        question.setDescription(item.getString("description"));
                                        question.setCategoryId(new Integer(item.getString("category_id")));
                                        question.setAnswer(item.getString("answer"));
                                        question.setPenalty(Integer.valueOf(item.getString("penalty")));
                                        question.setCorrect(false);
                                        if(!item.isNull( "image" ))
                                        {
                                            question.setImage(Utility.getBitmapImage(item.getString("image")));
                                        }

                                        questions.add(question);
                                    }
                                    match.setQuestions(questions);
                                }

                            }

                        }
                        if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
                        {
                            ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();

//
                            if(match.getOpponentType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM))
                            {

                                if(jObj.has("opponent"))
                                {
                                    JSONObject opponent_t = jObj.getJSONObject("opponent");
                                    opponent.setProfilePicture(opponent_t.getString("image"));

                                    if(opponent_t.getString("user_name").contains("Visitor_"))
                                        opponent.setUserName(UserModel.getVisitorHashedName(opponent_t.getString("user_name")));
                                    else
                                        opponent.setUserName(opponent_t.getString("user_name"));

                                    opponent.setAllowChat(opponent_t.getString("allowChat"));
                                    match.setOpponent(opponent);

                                    CrossTableModel ctm = new CrossTableModel();
                                    ctm.setId(BigInteger.valueOf(jObj.getInt("cross_table_id")));
                                    ctm.setWidth(Integer.valueOf(jObj.getString("width")));
                                    ctm.setHeight(Integer.valueOf(jObj.getString("height")));

                                    match.setCrossTable(ctm);

                                    JSONArray tquestions = jObj.getJSONArray("questions");
                                    QuestionModel question;
                                    ArrayList<QuestionModel> mquestions = new ArrayList<>();

                                    for(int i = 0; i <tquestions.length(); i++ )
                                    {
                                        question = new QuestionModel();
                                        JSONObject item = tquestions.getJSONObject(i);
                                        question.setDescription(item.getString("description"));
                                        question.setAnswer(item.getString("answer"));
                                        question.setAnswerCells(new ArrayList<String>(Arrays.asList(item.getString("answer_position_cells").split("\\s*,\\s*"))));
                                        question.setPositionCode(item.getString("position_code"));
                                        question.setQuestionPosition(item.getString("question_position"));

                                        mquestions.add(question);

                                    }
                                    match.setQuestions(mquestions);
                                }

                            }

                        }
                        //TODO: meysam - add other types of match...

                        /////////////////////////////////////////////
                        this.item = match;
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNIVERSAL_MATCH_RESULT:
                    {
                        this.token = jObj.getString("token");
                        UniversalMatchModel match = new UniversalMatchModel();
                        UserModel user = new UserModel();

                        match.setEnded(jObj.getInt("is_ended"));
                        match.setMatchType(jObj.getInt("type"));
                        match.setOpponentType(new Integer(jObj.getString("opponent_type")));
                        match.setBet(jObj.getString("bet"));


                        if(match.getEnded().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED))//match is ended
                        {

                                JSONObject opponenet = jObj.getJSONObject("opponent");
                                match.setOpponentSpentTime(opponenet.getString("opponent_spent_time"));
                                if(match.getOpponentSpentTime().equals("-1"))
                                    match.setOpponentSpentTime("null");
                                match.setOpponentCorrectCount(opponenet.getString("opponent_correct_count"));
                                user.setAllowChat(opponenet.getString("allowChat"));
                                match.setOpponent(user);

                                Integer winnerCode = jObj.getInt("winner_code");
                            if(winnerCode.equals(3))
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_EQUAL);
                            }
                            if(winnerCode.equals(1))
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_WIN);
                            }
                            if(winnerCode.equals(2))
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_LOST);
                            }
                        }
                        else if(match.getEnded() == 0)
                        {
                            //match is in progress
                            match.setMatchStatus(UniversalMatchModel.STATUS_OPPONENT_TURN);
                        }
                        else if(match.getEnded() == UniversalMatchModel.USER_UNIVERSAL_MATCH_ENDED_STATUS_DECLINED)
                        {
                            // meysam - opponnent declined match
                            match.setMatchStatus(UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT);
                        }
                        else
                        {
                            // meysam - get ended is null
                            match.setMatchStatus(UniversalMatchModel.STATUS_REQUEST_SENT);
                        }

                        this.item = match;
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNIVERSAL_MATCH_STATUS:
                    {
                        this.token = jObj.getString("token");
                        UniversalMatchModel match = new UniversalMatchModel();
                        UserModel user = new UserModel();

                        match.setEnded(jObj.getInt("is_ended"));
                        match.setMatchType(jObj.getInt("type"));
                        match.setOpponentType(new Integer(jObj.getString("opponent_type")));
                        match.setBet(jObj.getString("bet"));

                        if(match.getEnded().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED))//match is ended
                        {

                            JSONObject opponenet = jObj.getJSONObject("opponent");
                            match.setOpponentSpentTime(opponenet.getString("opponent_spent_time"));
                            if(match.getOpponentSpentTime().equals("-1"))
                                match.setOpponentSpentTime("null");
                            match.setOpponentCorrectCount(opponenet.getString("opponent_correct_count"));

                            match.setOpponent(user);

                            Integer winnerCode = jObj.getInt("winner_code");
                            match.setWinner(winnerCode);
                            if(winnerCode.equals(3))
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_EQUAL);
                            }
                            if(winnerCode.equals(1))
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_WIN);
                            }
                            if(winnerCode.equals(2))
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_LOST);
                            }
                        }
                        else if(match.getEnded() == 0)
                        {
                            //match is in progress
                            if(jObj.has("values") || jObj.has("questions"))//meysam - if values exist it means it's our turn
                            {
                                //meysam - its our turn...

                                if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
                                {
                                    //meysam - question match...
                                    JSONArray questions_t = jObj.getJSONArray("values");
                                    QuestionModel question;
                                    ArrayList<QuestionModel> questions = new ArrayList<>();

                                    for(int i = 0; i <questions_t.length(); i++ )
                                    {
                                        question = new QuestionModel();
                                        JSONObject item = questions_t.getJSONObject(i);
                                        question.setId(new BigInteger(item.getString("question_id")));
                                        question.setDescription(item.getString("description"));
                                        question.setCategoryId(new Integer(item.getString("category_id")));
                                        question.setAnswer(item.getString("answer"));
                                        question.setPenalty(Integer.valueOf(item.getString("penalty")));
                                        question.setCorrect(false);
                                        if(!item.isNull( "image" ))
                                        {
                                            question.setImage(Utility.getBitmapImage(item.getString("image")));
                                        }

                                        questions.add(question);
                                    }
                                    match.setQuestions(questions);

                                }
                                else if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
                                {
                                    // meysam - cross table match...

                                    CrossTableModel ctm = new CrossTableModel();
                                    ctm.setId(BigInteger.valueOf(jObj.getInt("cross_table_id")));
                                    ctm.setWidth(Integer.valueOf(jObj.getString("width")));
                                    ctm.setHeight(Integer.valueOf(jObj.getString("height")));

                                    match.setCrossTable(ctm);

                                    JSONArray questions = jObj.getJSONArray("questions");
                                    QuestionModel question;
                                    ArrayList<QuestionModel> mquestions = new ArrayList<>();

                                    for(int i = 0; i <questions.length(); i++ )
                                    {
                                        question = new QuestionModel();
                                        JSONObject item = questions.getJSONObject(i);
                                        question.setDescription(item.getString("description"));
                                        question.setAnswer(item.getString("answer"));
                                        question.setAnswerCells(new ArrayList<String>(Arrays.asList(item.getString("answer_position_cells").split("\\s*,\\s*"))));
                                        question.setPositionCode(item.getString("position_code"));
                                        question.setQuestionPosition(item.getString("question_position"));

                                        mquestions.add(question);

                                    }
                                    match.setQuestions(mquestions);
                                }
                                else if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_DARHAMTABLE))
                                {
                                    //TODO: meysam - darham table match ...
                                }
                                else if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_FINDWORD))
                                {
                                    //TODO: meysam - find word match ...
                                }
                                else if(match.getMatchType().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_GUESSWORD))
                                {
                                    //TODO: meysam - guss word match ...
                                }
                                else
                                {
                                    //meysam - never must occur...
                                }
                                match.setMatchStatus(UniversalMatchModel.STATUS_YOUR_TURN);
                            }
                            else
                            {
                                match.setMatchStatus(UniversalMatchModel.STATUS_OPPONENT_TURN);
                            }

                        }
                        else if(match.getEnded() == -1)
                        {
                            // meysam - opponnent declined match
                            match.setMatchStatus(UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT);
                        }
                        else
                        {
                            // meysam - get ended is null
                            match.setMatchStatus(UniversalMatchModel.STATUS_REQUEST_SENT);
                        }

                        this.item = match;
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_USER_FRIEND_SYNC:
                    {
                        this.token = jObj.getString("token");
                        this.items = new ArrayList<>();

                        JSONArray userFriends = jObj.getJSONArray("user_friends");
                        UserFriendModel userFriend;

                        for(int i = 0; i <userFriends.length(); i++ )
                        {
                            userFriend = new UserFriendModel();
                            JSONObject item = userFriends.getJSONObject(i);
                            userFriend.setUserFriendId(new BigInteger(item.getString("user_friend_id")));

                            String[] temp = Utility.tokenDecode(this.token);
                            JSONObject jobjid = new JSONObject(temp[1]);

                            if(jobjid.getString("user_id").equals(item.getString("user_1_id")))
                            {
                                userFriend.setFriendId(new BigInteger(item.getString("user_2_id")));
                                userFriend.setFriendStatus(new Integer(item.getString("user_2_status")));
                                userFriend.setUserId(new BigInteger(item.getString("user_1_id")));
                                userFriend.setUserStatus(new Integer(item.getString("user_1_status")));
                            }
                            if(jobjid.getString("user_id").equals(item.getString("user_2_id")))
                            {
                                userFriend.setFriendId(new BigInteger(item.getString("user_1_id")));
                                userFriend.setFriendStatus(new Integer(item.getString("user_1_status")));
                                userFriend.setUserId(new BigInteger(item.getString("user_2_id")));
                                userFriend.setUserStatus(new Integer(item.getString("user_2_status")));
                            }

                            this.items.add(userFriend);
                        }
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_USER_FRIEND_VALIDATE_SYNC:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNIVERSAL_MATCH_CHANGE_STATUS:
                    {

                        if(jObj.has("status_changed"))
                        {
                            this.item =  jObj.getString("status_changed");
                        }
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_CHANGE_LEVEL_USER:
                    {
                        // edit user successfully in server
                        this.item = jObj.getString("level");
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_CUPS_USER:
                    {
                        // edit user successfully in server
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UPDATE_ALLOW_CHAT_USER:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_UNIVERSAL_MATCH_REMATCH_REQUEST:
                    {
                        this.token = jObj.getString("token");
                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    default:
                        break;
                }

            }
            else
            {
//                if(!this.error.equals(RequestRespondModel.ERROR_OPPONENT_ACCEPTED_CODE) &&
//                        !this.error.equals(RequestRespondModel.ERROR_OPPONENT_CANCELED_CODE))
//                {
                    // Error in login. Get the error message
                    this.error_msg = this.getErrorCodeMessage(new Integer(jObj.getString("error")));

//                    if(this.error == RequestRespondModel.ERROR_TOKEN_INVALID)
//                    {
//                        this.must_logout = true;
//                        SessionModel session = new SessionModel(AppController.getInstance().getApplicationContext());
//                        session.logoutUser();
//                    }

                    LogModel log = new LogModel(cntx);
                    log.setErrorMessage("message: "+ this.error_msg+" CallStack: non, MeysamErrorCode:" + this.getError());
                    log.setContollerName(this.getClass().getName());
                    if(new SessionModel(cntx).isLoggedIn())
                        log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
                    log.insert();
//                }


            }

        }
        catch (JSONException e)
        {
            // JSON error
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ e.getMessage()+" CallStack: non");
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
//            log.insert();


            Utility.generateLogError(e);
        }

    }

    public boolean isTokenValid()
    {
        SessionModel session = new SessionModel(AppController.getInstance().getApplicationContext());
        if(this.getToken().isEmpty() || this.getToken() == null)
        {
            this.must_logout = true;
            session.logoutUser();
            return false;
        }
        else
        {
            if(session.getStoredToken().equals(this.getToken()))
            {
                //do nothing - it is correct
            }
            else
            {
                session.setToken(this.token);
            }
            return true;
        }
    }

    public String getErrorCodeMessage(int errorCode)
    {
        String result = "";
        switch (errorCode)
        {
            case ERROR_ITEM_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_item_exist);
                break;
            case ERROR_INSERT_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_insert_fail);
                break;
            case ERROR_USER_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_user_exist);
                break;
            case ERROR_REGISTER_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_register_fail);
                break;
            case ERROR_TOKEN_MISMACH_CODE:
                result = cntx.getResources().getString(R.string.error_token_mismach);
                break;
            case ERROR_LOGIN_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_login_fail);
                break;
            case ERROR_DEFECTIVE_INFORMATION_CODE:
                result = cntx.getResources().getString(R.string.error_defective_information);
                break;
            case ERROR_TOKEN_BLACKLISTED_CODE:
                result = cntx.getResources().getString(R.string.error_token_blacklisted);
                break;
            case ERROR_INVALID_FILE_SIZE_CODE:
                result = cntx.getResources().getString(R.string.error_invalid_file_size);
                break;
            case ERROR_UPDATE_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_update_fail);
                break;
            case ERROR_DELETE_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_delete_fail);
                break;
            case ERROR_OPERATION_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_operation_fail);
                break;

            case ERROR_ITEM_NOT_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_item_not_exist);
                break;

            case ERROR_AUTH_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_auth_fail);
                break;

            case ERROR_EMAIL_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_email_exist);
                break;
            case ERROR_UNAUTHORIZED_ACCESS_CODE:
                result = cntx.getResources().getString(R.string.error_unauthorized_access);
                break;
            case ERROR_NOT_ENOUGH_BALANCE_USER_CODE:
                result = cntx.getResources().getString(R.string.error_not_enough_balance);
                break;
            case ERROR_INVALID_CODE_OF_INVITE_CODE:
                result = cntx.getResources().getString(R.string.error_invalid_invite_code);
                break;
            case ERROR_PASSWORD_MISMATCH_CODE:
                result = cntx.getResources().getString(R.string.error_mismatch_password);
                break;
            case ERROR_EXCEED_REGISTER_COUNT:
                result = cntx.getResources().getString(R.string.error_exceed_register_count);
                break;
            case ERROR_WRONG_CHARSET:
                result = cntx.getResources().getString(R.string.error_wrong_char_set);
                break;
            case ERROR_STATUS_BLOCKED_CODE:
                result = cntx.getResources().getString(R.string.msg_StatusBlocked);
                break;
            case ERROR_STATUS_DECLINED_CODE:
                result = cntx.getResources().getString(R.string.msg_StatusDeclined);
                break;
            case ERROR_STATUS_DELETED_CODE:
                result = cntx.getResources().getString(R.string.msg_StatusDeleted);
                break;
            case ERROR_STATUS_ANSWERED_CODE:
                result = cntx.getResources().getString(R.string.msg_ErrorGoalUserAnswered);
                break;
            case ERROR_NO_OPPONENT_AVAILABLE_CODE:
                result = cntx.getResources().getString(R.string.msg_ErrorNoOpponentAvailableCode);
                break;
            case ERROR_OPPONENT_CANCELED_CODE:
                result = cntx.getResources().getString(R.string.msg_ErrorOpponentCanceledCode);
                break;
            case ERROR_INVALID_PUBLIC_KEY_CODE:
                result = cntx.getResources().getString(R.string.msg_ErrorInvalidPublicKeyCode);
                break;
            case ERROR_NOT_TIME_DRAW_CODE:
                result = cntx.getResources().getString(R.string.msg_ErrorNotTimeDrawCode);
                break;
            case ERROR_OPPONENT_ACCEPTED_CODE:
                result = cntx.getResources().getString(R.string.msg_ErrorOpponentAccepted);
                break;
            case ERROR_TOKEN_INVALID:
                result = cntx.getResources().getString(R.string.error_token_invalid);
                break;
            default:
                result = cntx.getResources().getSystem().getString(R.string.error_undefined);
                break;
        }

        return result;


    }
}
