package ir.fardan7eghlim.luckylord.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/21/2016.
 */

public class SessionModel {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AttentraPref";

    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // user_name
    public static final String KEY_USER_NAME = "user_name";

    // user_id
    public static final String KEY_USER_ID = "user_id";

    // invite_code
    public static final String KEY_INVITE_CODE= "invite_code";
    // hazel
    public static final String KEY_HAZEL= "hazel";
    // luck
    public static final String KEY_LUCK = "luck";
    // level
    public static final String KEY_LEVEL_SCORE = "level_score";
    // tel
    public static final String KEY_TEL = "tel";
    // birthdate
    public static final String KEY_BIRTH_DATE = "birthdate";
    // allow chat
    public static final String KEY_ALLOW_CHAT = "allow_chat";
//    // total match count
//    public static final String KEY_TOTAL_MATCH_COUNT = "total_match_count";
//    // win match count
//    public static final String KEY_WIN_MATCH_COUNT = "win_match_count";
    // gender
    public static final String KEY_GENDER = "gender";
    // Token (make variable public to access from outside)
    public static final String KEY_TOKEN = "token";

    public static final String KEY_VISITOR_USER_NAME = "visitor_user_name";

    // cups
    public static final String KEY_CUPS = "cups";


    public static final String KEY_UNREAD_MESSAGE_COUNT = "unread_message_count";

    public static final String KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT = "unread_friendship_request_count";

    public static final String KEY_MUSIC_PLAY= "play_music";

    public static final String KEY_FIRST_RUN= "first_run";

    public static final String KEY_TOTURIAL= "toturial";

    public static final String KEY_LAST_CHECK_SERVER_TIME= "last_server_check_time";

    public static final String KEY_SERVER_CHECK_INTERVAL= "server_check_interval";

    public static final String KEY_ONE_SIGNAL_PLAYER_ID= "one_signal_player_id";

    // egg
    public static final String KEY_EGG= "egg";

    // market
    public static final String KEY_MARKET_TOKEN = "market_token";
    public static final String KEY_MARKET_PAYLOAD = "market_payload";
    public static final String KEY_MARKET_ID = "market_id";
    public static final String KEY_MARKET_PERSIAN_TITLE = "market_persian_title";
    public static final String KEY_MARKET_ENGLISH_TITLE = "market_english_title";
    public static final String KEY_MARKET_ENGLISH_DESCRIPTION = "market_english_description";
    public static final String KEY_MARKET_PERSIAN_DESCRIPTION = "market_persian_description";
    public static final String KEY_MARKET_AMOUNT = "market_amount";


    //advertisment
    public static final String KEY_SEND_AD_REWARD_REQUEST = "send_ad_reward";

    // system question atts
    public static final String KEY_QUESTION_ID = "question_id";
    public static final String KEY_QUESTION_GUID = "question_guid";
    public static final String KEY_QUESTION_DESCRIPTION = "description";
    public static final String KEY_QUESTION_ANSWER = "answer";
    public static final String KEY_QUESTION_PENALTY = "penalty";
    public static final String KEY_QUESTION_CATEGORY_ID = "category_id";
    public static final String KEY_QUESTION_MAX_HAZEL_REWARD = "max_hazel_reward";
    public static final String KEY_QUESTION_MIN_HAZEL_REWARD = "min_hazel_reward";
    public static final String KEY_QUESTION_MAX_LUCK_REWARD = "max_luck_reward";
    public static final String KEY_QUESTION_MIN_LUCK_REWARD = "min_luck_reward";
    public static final String KEY_QUESTION_IMAGE = "image";
    public static final String KEY_QUESTION_FINAL_LUCK = "question_final_luck";
    public static final String KEY_QUESTION_FINAL_HAZEL = "question_final_hazel";

    public static final String KEY_QUESTION_REMAINING_TIME = "question_remaining_time";
    public static final String KEY_QUESTION_USED_HAZEL = "question_used_hazel";
    public static final String KEY_QUESTION_USED_LUCK = "question_used_luck";

    public static final String KEY_FINAL_HAZEL = "final_hazel";
    public static final String KEY_FINAL_LUCK = "final_luck";

    public static final String KEY_QUESTION_HATE_LIKE_LIST = "question_hate_like";
    public static final String KEY_PUZZLE_TABLE_QUESTION_HATE_LIKE_LIST = "puzzle_table_question_hate_like";

    public static final String KEY_RANDOM_CATEGORY_ID_1 = "random_category_id_1";
    public static final String KEY_RANDOM_CATEGORY_ID_2 = "random_category_id_2";
    public static final String KEY_RANDOM_CATEGORY_ID_3 = "random_category_id_3";

    public static final String KEY_BET_ID = "bet_id";
    public static final String KEY_BET_TIME = "bet_time";

    //film advertisment information
    public static final String KEY_AD_FILM_MAIN_ALLOWED_COUNT = "ad_film_main_allowed_count";
    public static final String KEY_AD_FILM_QUESTION_LEVEL_ALLOWED_COUNT = "ad_film_question_level_allowed_count";

    public static final String KEY_AD_FILM_MAIN_SPENT_COUNT = "ad_film_main_spent_count";
    public static final String KEY_AD_FILM_QUESTION_LEVEL_SPENT_COUNT = "ad_film_question_level_spent_count";

    public static final String KEY_AD_FILM_MAIN_SPENT_TIME = "ad_film_question_level_spent_time";
    public static final String KEY_AD_FILM_QUESTION_LEVEL_SPENT_TIME = "ad_film_question_level_spent_time";
    /////////////////////////////////

    //////////////////////tangled table///////////////////
    public static final String KEY_TANGLED_TABLE_USED_HAZEL = "tangled_table_used_hazel";
    public static final String KEY_TANGLED_TABLE_USED_LUCK = "tangled_table_used_luck";

    public static final String KEY_TANGLED_TABLE_QUESTION_ID = "tangled_table_question_id_";
    public static final String KEY_TANGLED_TABLE_QUESTION_GUID = "tangled_table_question_guid_";
    public static final String KEY_TANGLED_TABLE_QUESTION_DESCRIPTION = "tangled_table_description_";
    public static final String KEY_TANGLED_TABLE_QUESTION_ANSWER = "tangled_table_answer_";
    public static final String KEY_TANGLED_TABLE_QUESTION_PENALTY = "tangled_table_penalty_";
    public static final String KEY_TANGLED_TABLE_QUESTION_CATEGORY_ID = "tangled_table_category_id_";
    public static final String KEY_TANGLED_TABLE_QUESTION_FINAL_HAZEL_REWARD = "tangled_table_final_hazel_reward_";
    public static final String KEY_TANGLED_TABLE_QUESTION_FINAL_LUCK_REWARD = "tangled_table_final_luck_reward_";
    public static final String KEY_TANGLED_TABLE_QUESTION_IS_ANSWERED = "tangled_table_is_answered_";

    public static final String KEY_TANGLED_TABLE_CELLS = "tangled_table_cells";

    public static final String KEY_TANGLED_TABLE_REMOVED_CELLS = "tangled_table_removed_cells";
    public static final String KEY_TANGLED_TABLE_ANSWERED_CELLS = "tangled_table_answered_cells";
    public static final String KEY_TANGLED_TABLE_HINTED_CELLS = "tangled_table_hinted_cells";

    public static final String KEY_TANGLED_TABLE_FIRST_LETTER_CELLS = "tangled_table_first_letter_cells";
    public static final String KEY_TANGLED_TABLE_AVAILABLE_CELLS = "tangled_table_available_cells";

//    public static final String KEY_GUESS_WORD_CELLS = "guess_word_cells";

    public static final String KEY_TANGLED_TABLE_IS_FIRST_TIME = "tangled_table_is_first_time";
    public static final String KEY_GUESS_WORD_IS_FIRST_TIME = "guess_word_is_first_time";
    public static final String KEY_FIND_WORD_IS_FIRST_TIME = "find_word_is_first_time";
    public static final String KEY_CROSS_TABLE_IS_FIRST_TIME = "cross_table_is_first_time";
    public static final String KEY_CATEGORIES_IS_FIRST_TIME = "categories_is_first_time";
    public static final String KEY_CATEGORIES_INDIVIDUAL_IS_FIRST_TIME = "categories_individual_is_first_time";
    public static final String KEY_MATCH_INDEX_IS_FIRST_TIME = "match_index_is_first_time";
    public static final String KEY_CHAT_IS_FIRST_TIME = "chat_is_first_time";
    public static final String KEY_QUESTION_IS_FIRST_TIME = "question_is_first_time";

    ////////////////////////////////////////////////////////

    //meysam - find word table variables...
    public static final String KEY_FIND_WORD_WORD_COUNT = "find_word_word_count";
    /////////////////////////////////////////////

    public static final String KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT = "guess_word_hinted_wrong_letter_count";
//    public static final String KEY_GUESS_WORD_HINTED_CORRECT_LETTER_COUNT = "guess_word_hinted_correct_letter_count";
//    public static final String KEY_FIND_WORD_FOUNDED_WORDS = "find_word_founded_words";

    //meysam - cross table related constants
    public static final String KEY_CROSS_TABLE_SINGLE_ROW_COUNT = "cross_table_single_row_count";
    public static final String KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT = "cross_table_single_column_count";
    public static final String KEY_CROSS_TABLE_SINGLE_TABLE_ID = "cross_table_single_table_id";
    public static final String KEY_CROSS_TABLE_CURRENT_CELLS = "cross_table_current_cells";

    //meysam - match cross table related constants
    public static final String KEY_MATCH_CROSS_TABLE_SINGLE_ROW_COUNT = "match_cross_table_single_row_count";
    public static final String KEY_MATCH_CROSS_TABLE_SINGLE_COLUMN_COUNT = "match_cross_table_single_column_count";
    public static final String KEY_MATCH_CROSS_TABLE_SINGLE_TABLE_ID = "match_cross_table_single_table_id";
    public static final String KEY_MATCH_CROSS_TABLE_CURRENT_CELLS = "match_cross_table_current_cells";
    public static final String KEY_MATCH_CROSS_TABLE_IS_FIRST_TIME = "match_cross_table_is_first_time";

    // image
    public static final String KEY_PROFILE_IMAGE = "profileImage";

//     user friend ids
//    public static final String KEY_USER_FRIENDS_IDS = "user_friends_ids";

    //meysam - user cups variables...
    public static final String KEY_MATCH_ENTIRELY_TRUE_COUNT = "match_entirely_true_count";
    public static final String KEY_QUESTION_TRUE_COUNT = "question_true_count";

    public static final String KEY_CUPS_FOR_SERVER_UPDATE = "cups_for_server_update";
    public static final String KEY_CUPS_FOR_LOCAL_SHOW = "cups_for_local_show";//meysam - store as comma separated string...
    /////////////////////////////////////////////

    //meysam - for level tracking...
    public static final String KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE = "level_count_for_server_update";
    public static final String KEY_LEVEL_FOR_LOCAL_SHOW = "level_for_local_show";//meysam - level count to play local animation - store as Integer...
    /////////////////////////////////

    //last version update...
    public static final String KEY_LAST_UPDATED_VERSION = "last_updated_version";
    //////////////////////////////////////////////

    //meysam - word database table status
    public static final String KEY_WORD_DATABASE_TABLE_STATUS = "word_database_table_status";// 0 is empty, 1 is in processing, 2 is completed

    public static final String KEY_FRIENDSHIP_SYNC = "friendship_sync";

    //meysam - added in 13961203
    public static final String KEY_FREE_MATCH_TIME = "free_match_time";
    public static final String KEY_FREE_MATCH_COUNT = "free_match_count";


    //meysam - check if it is first time closing the game....
    public static final String KEY_FIRST_TIME_CLOSING = "first_time_closing";

    //meysam - check the number of times that closed the game....
    public static final String KEY_NUMBER_TIME_CLOSING = "number_time_closing";

    //meysam - notification sound....
    public static final String KEY_NOTIFICATION_SOUND = "notification_sound";

    // Constructor
    public SessionModel(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences


        editor.remove(IS_LOGIN);

        editor.remove(KEY_EMAIL);
        editor.remove(KEY_GENDER);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_HAZEL);
        editor.remove(KEY_LUCK);
        editor.remove(KEY_TEL);
        editor.remove(KEY_INVITE_CODE);
        editor.remove(KEY_BIRTH_DATE);
        editor.remove(KEY_PROFILE_IMAGE);
        editor.remove(IS_LOGIN);
        editor.remove(KEY_LEVEL_SCORE);
        editor.remove(KEY_CUPS);
        editor.remove(KEY_ALLOW_CHAT);

        // Storing type in pref
        editor.remove(KEY_TOKEN);

        //meysam - remove everything man!!
//        editor.clear();

        editor.commit();

    }


    /**
     * Create login session
     * */
    public void createLoginSession(String userName, String email, Integer hazel, Integer luck, Integer level, String tel, String inviteCode, String gender , String token
    ,String birthDate, String profileImage, String cups, String allowChat){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_HAZEL, hazel.toString());
        editor.putString(KEY_LUCK, luck.toString());
        editor.putString(KEY_LEVEL_SCORE, level.toString());
        editor.putString(KEY_TEL, tel);
        editor.putString(KEY_INVITE_CODE, inviteCode);
        editor.putString(KEY_BIRTH_DATE, birthDate);
        editor.putString(KEY_PROFILE_IMAGE, profileImage);
        editor.putString(KEY_CUPS, cups.toString());
        editor.putString(KEY_ALLOW_CHAT, allowChat);

        // Storing type in pref
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(UserModel user, String token){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing type in pref
        editor.putString(KEY_TOKEN, token);

        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_USER_NAME, user.getUserName());
        editor.putString(KEY_HAZEL, user.getHazel().toString());
        editor.putString(KEY_LUCK, user.getLuck().toString());
        editor.putString(KEY_TEL, user.getTel());
        editor.putString(KEY_INVITE_CODE, user.getInviteCode());
        editor.putString(KEY_PROFILE_IMAGE, user.getProfilePicture());
        editor.putString(KEY_BIRTH_DATE, user.getBirthDate());
//        editor.putString(KEY_TOTAL_MATCH_COUNT, user.getTotalMatchCount());
//        editor.putString(KEY_WIN_MATCH_COUNT, user.getWinMatchCount());
        editor.putString(KEY_VISITOR_USER_NAME, user.getVisitorUserName());
        editor.putString(KEY_USER_ID, user.getId().toString());
        editor.putString(KEY_LEVEL_SCORE, user.getLevelScore().toString());
        editor.putString(KEY_CUPS, user.getCups());
        editor.putString(KEY_ALLOW_CHAT, user.getAllowChat());


        // commit changes
        editor.commit();
    }


    public void updateUserSession(UserModel user){

        if(user.getEmail() != null)
            editor.putString(KEY_EMAIL, user.getEmail());
        if(user.getGender() != null)
            editor.putString(KEY_GENDER, user.getGender());
        if(user.getUserName() != null)
            editor.putString(KEY_USER_NAME, user.getUserName());
        if(user.getHazel() != null)
            editor.putString(KEY_HAZEL, user.getHazel().toString());
        if(user.getLuck() != null)
            editor.putString(KEY_LUCK, user.getLuck().toString());
        if(user.getTel() != null)
            editor.putString(KEY_TEL, user.getTel());
        if(user.getInviteCode() != null)
            editor.putString(KEY_INVITE_CODE, user.getInviteCode());
        if(user.getProfilePicture() != null)
            editor.putString(KEY_PROFILE_IMAGE, user.getProfilePicture());
        if(user.getBirthDate() != null)
            editor.putString(KEY_BIRTH_DATE, user.getBirthDate());
//        if(user.getTotalMatchCount() != null)
//            editor.putString(KEY_TOTAL_MATCH_COUNT, user.getTotalMatchCount());
//        if(user.getWinMatchCount() != null)
//            editor.putString(KEY_WIN_MATCH_COUNT, user.getWinMatchCount());
        if(user.getVisitorUserName() != null)
            editor.putString(KEY_VISITOR_USER_NAME, user.getVisitorUserName());
        if(user.getLevelScore() != null)
            editor.putString(KEY_LEVEL_SCORE, user.getLevelScore().toString());
        if(user.getCups() != null)
            editor.putString(KEY_CUPS, user.getCups().toString());
        if(user.getAllowChat() != null)
            editor.putString(KEY_ALLOW_CHAT, user.getAllowChat());

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public UserModel getCurrentUser(){

        UserModel current_user = new UserModel();

        // user email id
        current_user.setEmail( pref.getString(KEY_EMAIL, null));
        current_user.setInviteCode( pref.getString(KEY_INVITE_CODE, null));
        current_user.setTel( pref.getString(KEY_TEL, null));
        current_user.setLuck( Integer.valueOf(pref.getString(KEY_LUCK, "0")));
        current_user.setHazel( Integer.valueOf(pref.getString(KEY_HAZEL, "0")));
        current_user.setUserName( pref.getString(KEY_USER_NAME, null));
        current_user.setGender(pref.getString(KEY_GENDER, null));
        current_user.setProfilePicture(pref.getString(KEY_PROFILE_IMAGE, null));
        current_user.setBirthDate(pref.getString(KEY_BIRTH_DATE, null));
//        current_user.setTotalMatchCount(pref.getString(KEY_TOTAL_MATCH_COUNT, null));
//        current_user.setWinMatchCount(pref.getString(KEY_WIN_MATCH_COUNT, null));
        current_user.setVisitorUserName(pref.getString(KEY_VISITOR_USER_NAME, null));
        current_user.setId(new BigInteger(pref.getString(KEY_USER_ID,"-1")));
        current_user.setToken(pref.getString(KEY_TOKEN, null));
        current_user.setLevelScore( Integer.valueOf(pref.getString(KEY_LEVEL_SCORE, "0")));
        current_user.setCups(pref.getString(KEY_CUPS,"000000000"));
        current_user.setAllowChat(pref.getString(KEY_ALLOW_CHAT, "1"));

        // return user
        return current_user;
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isUserNameOrVisitorUserNameExist(){
        String t =  pref.getString(KEY_USER_NAME, null);
        if(t != null)
            return true;
        t =  pref.getString(KEY_VISITOR_USER_NAME, null);
        if(t != null)
            return true;
        return false;
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Quick check for token
     * **/
    // check returned token with current session token
    public boolean checkToken(String new_token){

        return pref.getString(KEY_TOKEN, "").compareTo(new_token) == 0;

    }

    public String getStoredToken()
    {
        return pref.getString(KEY_TOKEN, null);
    }

    public void setToken(String token)
    {
        // Storing type in pref
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }


    /**
     * Create question session
     * */
    public void setQuestion(QuestionModel question){
        // Storing language value as inputed
        editor.putString(KEY_QUESTION_ID, question.getId().toString());
        editor.putString(KEY_QUESTION_GUID, question.getGuid());
        editor.putString(KEY_QUESTION_DESCRIPTION, question.getDescription());
        editor.putString(KEY_QUESTION_ANSWER, question.getAnswer());
        editor.putString(KEY_QUESTION_PENALTY, question.getPenalty().toString());
        editor.putString(KEY_QUESTION_MAX_HAZEL_REWARD, question.getMaxHazelReward().toString());
        editor.putString(KEY_QUESTION_MIN_HAZEL_REWARD, question.getMinHazelReward().toString());
        editor.putString(KEY_QUESTION_MAX_LUCK_REWARD, question.getMaxLuckReward().toString());
        editor.putString(KEY_QUESTION_MIN_LUCK_REWARD, question.getMinLuckReward().toString());
        if(question.getCategoryId() != null)
            editor.putString(KEY_QUESTION_CATEGORY_ID, question.getCategoryId().toString());
        if(question.getImage() != null)
            editor.putString(KEY_QUESTION_IMAGE, Utility.getStringImage(question.getImage()));
        if(question.getFinalHazelReward() != null)
            editor.putString(KEY_QUESTION_FINAL_HAZEL, question.getFinalHazelReward().toString());
        if(question.getFinalLuckReward() != null)
            editor.putString(KEY_QUESTION_FINAL_LUCK, question.getFinalLuckReward().toString());

        // commit changes
        editor.commit();
    }

    /**
     * get stored question
     * */
    public QuestionModel getQuestion(){

        QuestionModel current_question = new QuestionModel();

        if(pref.contains(KEY_QUESTION_ID))
        {
            current_question.setId(new BigInteger(pref.getString(KEY_QUESTION_ID, null)));
            current_question.setGuid(pref.getString(KEY_QUESTION_GUID, null));
            current_question.setDescription(pref.getString(KEY_QUESTION_DESCRIPTION, null));
            current_question.setAnswer(pref.getString(KEY_QUESTION_ANSWER, null));
            current_question.setPenalty(new Integer(pref.getString(KEY_QUESTION_PENALTY, null)));
            current_question.setMaxHazelReward(new Integer(pref.getString(KEY_QUESTION_MAX_HAZEL_REWARD, null)));
            current_question.setMinHazelReward(new Integer(pref.getString(KEY_QUESTION_MIN_HAZEL_REWARD, null)));
            current_question.setMaxLuckReward(new Integer(pref.getString(KEY_QUESTION_MAX_LUCK_REWARD, null)));
            current_question.setMinLuckReward(new Integer(pref.getString(KEY_QUESTION_MIN_LUCK_REWARD, null)));
            current_question.setCategoryId(new Integer(pref.getString(KEY_QUESTION_CATEGORY_ID, null)));
            if(pref.getString(KEY_QUESTION_IMAGE, null)!=null) current_question.setImage(Utility.getBitmapImage(pref.getString(KEY_QUESTION_IMAGE, null)));

            if(pref.getString(KEY_QUESTION_FINAL_HAZEL, null)!=null) current_question.setFinalHazelReward(new Integer(pref.getString(KEY_QUESTION_FINAL_HAZEL, null)));
            if(pref.getString(KEY_QUESTION_FINAL_LUCK, null)!=null) current_question.setFinalLuckReward(new Integer(pref.getString(KEY_QUESTION_FINAL_LUCK, null)));

        }


        // return question
        return current_question;
    }

    /**
     * Create question session
     * */
    public void removeQuestion(){
        // Storing language value as inputed
        editor.remove(KEY_QUESTION_ID);
        editor.remove(KEY_QUESTION_GUID);
        editor.remove(KEY_QUESTION_DESCRIPTION);
        editor.remove(KEY_QUESTION_ANSWER);
        editor.remove(KEY_QUESTION_PENALTY);
        editor.remove(KEY_QUESTION_MAX_HAZEL_REWARD);
        editor.remove(KEY_QUESTION_MIN_HAZEL_REWARD);
        editor.remove(KEY_QUESTION_MAX_LUCK_REWARD);
        editor.remove(KEY_QUESTION_MIN_LUCK_REWARD);
        editor.remove(KEY_QUESTION_CATEGORY_ID);
        editor.remove(KEY_QUESTION_IMAGE);
        editor.remove(KEY_QUESTION_FINAL_HAZEL);
        editor.remove(KEY_QUESTION_FINAL_LUCK);

        // commit changes
        editor.commit();
    }

    /**
     * Create login session
     * */
    public void saveItem(String itemName,Object item){
        if(item instanceof String)
        {
            removeItem(itemName);
            editor.putString(itemName, item.toString());

        }
        else if(item instanceof Float)
        {
            removeItem(itemName);
            editor.putFloat(itemName, new Float(item.toString()));
        }
        else if(item instanceof Double)
        {
            removeItem(itemName);
            editor.putFloat(itemName, new Float(item.toString()));
        }
        else if(item instanceof Integer)
        {
            removeItem(itemName);
            editor.putInt(itemName, new Integer(item.toString()));
        }
        else if(item instanceof BigInteger)
        {
            removeItem(itemName);
            editor.putLong(itemName, (long) item);

        }
        else if(item instanceof Boolean)
        {
            removeItem(itemName);
            editor.putBoolean(itemName, new Boolean(item.toString()));
        }
        else
        {
            removeItem(itemName);
            editor.putString(itemName, item.toString());
        }

        // commit changes
        editor.commit();
    }

    public void removeItem(String key){

        editor.remove(key);
        editor.apply();

        // commit changes
        editor.commit();
    }
    public String getStringItem(String key){

        return  pref.getString(key, null);
    }

    public Boolean getBoolianItem(String key, Boolean defaultValue){

        return  pref.getBoolean(key, defaultValue);
    }

    public int getIntegerItem(String key){
        return  pref.getInt(key, 0);
    }

    public void increaseHazel(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_HAZEL,"0"));
        editor.putString(KEY_HAZEL,(old_amount+new Integer(amount))+"");

        editor.commit();
    }
    public void decreaseHazel(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_HAZEL,"0"));
        editor.putString(KEY_HAZEL,(old_amount-new Integer(amount))+"");

        editor.commit();
    }

    public void decreaseLuck(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_LUCK,"0"));
        editor.putString(KEY_LUCK,(old_amount-new Integer(amount))+"");

        editor.commit();
    }

    public void changeHazel(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_HAZEL,"0"));
        editor.putString(KEY_HAZEL,(old_amount+new Integer(amount))+"");

        editor.commit();
    }

    public void increaseLevel(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_LEVEL_SCORE,"0"));
        editor.putString(KEY_LEVEL_SCORE,(old_amount+new Integer(amount))+"");

        editor.commit();
    }
    public void setUserLevelScore(String amount)
    {
        editor.putString(KEY_LEVEL_SCORE,amount);

        editor.commit();
    }
    public void changeLuck(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_LUCK,"0"));
        editor.putString(KEY_LUCK,(old_amount+new Integer(amount))+"");

        editor.commit();
    }

    public void increaseLuck(String amount)
    {
        Integer old_amount = new Integer(pref.getString(KEY_LUCK,"0"));
        editor.putString(KEY_LUCK,(old_amount+new Integer(amount))+"");

        editor.commit();
    }

    /**
     * Create market session
     * */
    public void saveMarket(MarketModel market){

        editor.putString(KEY_MARKET_ID, market.getMarketId());
        editor.putString(KEY_MARKET_PERSIAN_TITLE, market.getTitlePersian());
        editor.putString(KEY_MARKET_ENGLISH_TITLE, market.getTitleEnglish());
        editor.putString(KEY_MARKET_PERSIAN_DESCRIPTION, market.getDescriptionPersian());
        editor.putString(KEY_MARKET_ENGLISH_DESCRIPTION, market.getDescriptionEnglish());
        editor.putString(KEY_MARKET_AMOUNT, market.getAmount());
        editor.putString(KEY_MARKET_PAYLOAD, market.getPayload());
        editor.putString(KEY_MARKET_TOKEN, market.getToken());

        // commit changes
        editor.commit();
    }

    public void removeMarket()
    {
        // removing payment
        editor.remove(KEY_MARKET_ID);
        editor.remove(KEY_MARKET_PERSIAN_TITLE);
        editor.remove(KEY_MARKET_ENGLISH_TITLE);
        editor.remove(KEY_MARKET_PERSIAN_DESCRIPTION);
        editor.remove(KEY_MARKET_ENGLISH_DESCRIPTION);
        editor.remove(KEY_MARKET_AMOUNT);
        editor.remove(KEY_MARKET_PAYLOAD);
        editor.remove(KEY_MARKET_TOKEN);
        editor.apply();

        // commit changes
        editor.commit();
    }

    public MarketModel getMarket()
    {
        MarketModel market = new MarketModel();
        market.setMarketId(pref.getString(KEY_MARKET_ID, null));
        market.setTitlePersian(pref.getString(KEY_MARKET_PERSIAN_TITLE, null));
        market.setTitleEnglish(pref.getString(KEY_MARKET_ENGLISH_TITLE, null));
        market.setDescriptionPersian(pref.getString(KEY_MARKET_PERSIAN_DESCRIPTION, null));
        market.setDescriptionEnglish(pref.getString(KEY_MARKET_ENGLISH_DESCRIPTION, null));
        market.setAmount(pref.getString(KEY_MARKET_AMOUNT, null));
        market.setPayload(pref.getString(KEY_MARKET_PAYLOAD, null));
        market.setToken(pref.getString(KEY_MARKET_TOKEN, null));

        return market;
    }

    public Boolean hasMarket()
    {
        String tmp =  pref.getString(KEY_MARKET_ID, "");
        if(tmp == "")
            return false;
        return true;
    }

    public Boolean hasItem(String key)
    {
        if(pref.contains(key))
            return true;
        return false;
    }

    public void saveUsedHazel(int amount){

        if(pref.getInt(KEY_QUESTION_USED_HAZEL, 0) != 0)
        {
            editor.putInt(KEY_QUESTION_USED_HAZEL, amount+pref.getInt(KEY_QUESTION_USED_HAZEL, 0));
        }
        else
        {
            editor.putInt(KEY_QUESTION_USED_HAZEL, amount);
        }

        // commit changes
        editor.commit();
    }
    public void saveUsedLuck(int amount){

        if(pref.getInt(KEY_QUESTION_USED_LUCK, 0) != 0)
        {
            editor.putInt(KEY_QUESTION_USED_LUCK, amount+pref.getInt(KEY_QUESTION_USED_LUCK, 0));
        }
        else
        {
            editor.putInt(KEY_QUESTION_USED_LUCK, amount);
        }


        // commit changes
        editor.commit();
    }

    public void saveFinalHazel(int amount){

        if(pref.getInt(KEY_FINAL_HAZEL, 0) != 0)
        {
            editor.putInt(KEY_FINAL_HAZEL, amount+pref.getInt(KEY_FINAL_HAZEL, 0));
        }
        else
        {
            editor.putInt(KEY_FINAL_HAZEL, amount);
        }

        // commit changes
        editor.commit();
    }
    public void saveFinalLuck(int amount){

        if(pref.getInt(KEY_FINAL_LUCK, 0) != 0)
        {
            editor.putInt(KEY_FINAL_LUCK, amount+pref.getInt(KEY_FINAL_LUCK, 0));
        }
        else
        {
            editor.putInt(KEY_FINAL_LUCK, amount);
        }


        // commit changes
        editor.commit();
    }

    public void changeFinalHazel(int amount){

        if(pref.getInt(KEY_FINAL_HAZEL, 0) != 0)
        {
            editor.putInt(KEY_FINAL_HAZEL, amount+pref.getInt(KEY_FINAL_HAZEL, 0));
        }
        else
        {
            editor.putInt(KEY_FINAL_HAZEL, amount);
        }

        // commit changes
        editor.commit();
    }
    public void changeFinalLuck(int amount){

        if(pref.getInt(KEY_FINAL_LUCK, 0) != 0)
        {
            editor.putInt(KEY_FINAL_LUCK, amount+pref.getInt(KEY_FINAL_LUCK, 0));
        }
        else
        {
            editor.putInt(KEY_FINAL_LUCK, amount);
        }


        // commit changes
        editor.commit();
    }

    public void addToList(String key, String value)
    {
        Set<String> set = new HashSet<String>();
        if(pref.contains(key))
        {
            set = pref.getStringSet(key, null);
            //Set the values
            set.add(value);
        }
        else
        {
            //Set the values
            set.add(value);
        }


        editor.putStringSet(key, set);
        editor.commit();
    }

    public ArrayList getStringList(String key)
    {
        Set<String> set ;
        ArrayList<String> list;
        if(pref.contains(key))
        {
            set = pref.getStringSet(key, null);
            list = new ArrayList<String>(set);
        }
        else
        {
            list = new ArrayList<String>();

        }

        return list;

    }

    public void setAllowedFilmCount(Integer allowedAdCount, String key)
    {
        // meysam - set film count advertisment

        editor.putInt(key, allowedAdCount);

        // commit changes
        editor.commit();

    }

    public Integer getAllowedFilmCount(String key)
    {
        // meysam - get film count advertisment

        if(pref.contains(key))
        {
            return  pref.getInt(key,0);

        }
        else
        {
            //default ad count per user
            return 5;
        }

    }

    public void saveSpentedAd(int amount, String key){
        // meysam - set used film ad count
        if(pref.contains(key))
        {
            editor.putInt(key, amount+pref.getInt(key, 0));
        }
        else
        {
            editor.putInt(key, amount);
        }

        // commit changes
        editor.commit();
    }

    public int[] getRandomCategoryIds()
    {

        int[] rnc = new int[3];
        if(pref.contains(KEY_RANDOM_CATEGORY_ID_1))
            rnc[0] = pref.getInt(KEY_RANDOM_CATEGORY_ID_1,0);
        if(pref.contains(KEY_RANDOM_CATEGORY_ID_2))
            rnc[1] = pref.getInt(KEY_RANDOM_CATEGORY_ID_2,0);
        if(pref.contains(KEY_RANDOM_CATEGORY_ID_3))
            rnc[2] = pref.getInt(KEY_RANDOM_CATEGORY_ID_3,0);

        return rnc;

    }

    public void setRandomCategoryIds(int[] values)
    {

        editor.putInt(KEY_RANDOM_CATEGORY_ID_1, values[0]);
        editor.putInt(KEY_RANDOM_CATEGORY_ID_2, values[1]);
        editor.putInt(KEY_RANDOM_CATEGORY_ID_3, values[2]);


        editor.commit();

    }

    public void removeRandomCategoryIds()
    {

        editor.remove(KEY_RANDOM_CATEGORY_ID_1);
        editor.remove(KEY_RANDOM_CATEGORY_ID_2);
        editor.remove(KEY_RANDOM_CATEGORY_ID_3);
        editor.apply();

        // commit changes
        editor.commit();

    }

    public boolean hasRandomCategoryIds()
    {
        if(pref.contains(KEY_RANDOM_CATEGORY_ID_1))
            return true;
        return false;

    }

    public void saveTangledTable(ArrayList<QuestionModel> questions, String cells, String removedCells, String answeredCells, String hintedCells, String firstLetters, String availableCells)
    {
        int i = 0;
        for(QuestionModel question:questions)
        {
            editor.putString(KEY_TANGLED_TABLE_QUESTION_ID+i, question.getId().toString());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_GUID+i, question.getGuid());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_CATEGORY_ID+i, question.getCategoryId().toString());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_ANSWER+i, question.getAnswer());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_DESCRIPTION+i, question.getDescription());
            editor.putBoolean(KEY_TANGLED_TABLE_QUESTION_IS_ANSWERED+i, question.getAnswered() == null?false:question.getAnswered());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_FINAL_HAZEL_REWARD+i, question.getFinalHazelReward().toString());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_FINAL_LUCK_REWARD+i, question.getFinalLuckReward().toString());
            editor.putString(KEY_TANGLED_TABLE_QUESTION_PENALTY+i, question.getPenalty().toString());

            i++;
        }

        if(cells != null)
            editor.putString(KEY_TANGLED_TABLE_CELLS, cells);

        if(removedCells != null)
            editor.putString(KEY_TANGLED_TABLE_REMOVED_CELLS, removedCells);

        if(answeredCells != null)
            editor.putString(KEY_TANGLED_TABLE_ANSWERED_CELLS, answeredCells);

        if(hintedCells != null)
            editor.putString(KEY_TANGLED_TABLE_HINTED_CELLS, hintedCells);

        if(firstLetters != null)
            editor.putString(KEY_TANGLED_TABLE_FIRST_LETTER_CELLS, firstLetters);

        if(availableCells != null)
            editor.putString(KEY_TANGLED_TABLE_AVAILABLE_CELLS, availableCells);


        // commit changes
        editor.commit();
    }

    public void saveTangledTableV2(String cells, String removedCells, String availableCells)
    {
        if(cells != null)
            editor.putString(KEY_TANGLED_TABLE_CELLS, cells);

        if(removedCells != null)
        {
            removeItem(KEY_TANGLED_TABLE_REMOVED_CELLS);
            editor.putString(KEY_TANGLED_TABLE_REMOVED_CELLS, removedCells);

        }

        if(availableCells != null)
        {
            removeItem(KEY_TANGLED_TABLE_AVAILABLE_CELLS);
            editor.putString(KEY_TANGLED_TABLE_AVAILABLE_CELLS, availableCells);

        }


        // commit changes
        editor.commit();
    }


    public ArrayList<QuestionModel> getTangledTable()
    {
        ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
        QuestionModel current_question;
        for(int i = 0; true;i++)
        {
            current_question = new QuestionModel();
            if(pref.getString(KEY_TANGLED_TABLE_QUESTION_ID+i,null) != null)
            {
                current_question.setId(new BigInteger(pref.getString(KEY_TANGLED_TABLE_QUESTION_ID+i, null)));
                current_question.setGuid(pref.getString(KEY_TANGLED_TABLE_QUESTION_GUID+i, null));
                current_question.setDescription(pref.getString(KEY_TANGLED_TABLE_QUESTION_DESCRIPTION+i, null));
                current_question.setAnswer(pref.getString(KEY_TANGLED_TABLE_QUESTION_ANSWER+i, null));
                current_question.setPenalty(new Integer(pref.getString(KEY_TANGLED_TABLE_QUESTION_PENALTY+i, null)));
                current_question.setFinalLuckReward(new Integer(pref.getString(KEY_TANGLED_TABLE_QUESTION_FINAL_LUCK_REWARD+i, null)));
                current_question.setFinalHazelReward(new Integer(pref.getString(KEY_TANGLED_TABLE_QUESTION_FINAL_HAZEL_REWARD+i, null)));
                current_question.setCategoryId(new Integer(pref.getString(KEY_TANGLED_TABLE_QUESTION_CATEGORY_ID+i, null)));
                current_question.setAnswered(pref.getBoolean(KEY_TANGLED_TABLE_QUESTION_IS_ANSWERED+i, false));
                questions.add(current_question);
            }
            else
            {
                return questions;
            }
        }
    }

    public void removeTangledTable()
    {
        for(int i = 0; true;i++)
        {
            if(pref.getString(KEY_TANGLED_TABLE_QUESTION_ID+i,null) != null)
            {

                editor.remove(KEY_TANGLED_TABLE_QUESTION_ID+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_GUID+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_CATEGORY_ID+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_ANSWER+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_DESCRIPTION+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_IS_ANSWERED+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_FINAL_HAZEL_REWARD+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_FINAL_LUCK_REWARD+i);
                editor.remove(KEY_TANGLED_TABLE_QUESTION_PENALTY+i);

            }
            else
            {
                break;
            }
            i++;
        }
        editor.remove(KEY_TANGLED_TABLE_CELLS);

        editor.remove(KEY_TANGLED_TABLE_REMOVED_CELLS);
        editor.remove(KEY_TANGLED_TABLE_ANSWERED_CELLS);
        editor.remove(KEY_TANGLED_TABLE_HINTED_CELLS);

        editor.remove(KEY_TANGLED_TABLE_FIRST_LETTER_CELLS);
        editor.remove(KEY_TANGLED_TABLE_AVAILABLE_CELLS);

        editor.commit();
    }

    public void addCupToServerUpdate(int cupIndex)
    {

            String tmp =  pref.getString(KEY_CUPS_FOR_SERVER_UPDATE, "");
            editor.remove(KEY_CUPS_FOR_SERVER_UPDATE);
            if(tmp == null || tmp.equals(""))
            {
                editor.putString(KEY_CUPS_FOR_SERVER_UPDATE, String.valueOf(cupIndex));
            }
            else
            {
                // Storing in pref
                tmp+=",";
                tmp+=String.valueOf(cupIndex);
                editor.putString(KEY_CUPS_FOR_SERVER_UPDATE,tmp );
            }
            // commit changes
            editor.commit();
    }

    public void addNewCups(ArrayList<Integer> cupIndexes)
    {

        String tmp =  pref.getString(KEY_CUPS, "");


        if(tmp == null || tmp.equals(""))
        {
            tmp = "000000000";
        }
        StringBuilder temp = new StringBuilder(tmp);
        for(int i = 0; i < cupIndexes.size(); i++)
        {
            temp.setCharAt(cupIndexes.get(i), '1');
        }
        editor.putString(KEY_CUPS, temp.toString());
        // commit changes
        editor.commit();
    }

    public void deleteAll()
    {
        editor.clear();
        editor.commit();
    }


}
