<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 2/25/2017
 * Time: 10:34 AM
 */

namespace App;

use Log;
use DB;

class RequestResponseAPI
{

    //tag codes

    //public
    const TAG_UNDEFINED = "undefined";
    const TAG_LOGOUT = "logout";
    const TAG_LOGIN_USER = "login_user";

    // user
    const TAG_INDEX_USER = "index_user";
    const TAG_REGISTER_USER = "register_user";
    const TAG_EDIT_USER = "edit_user";
    const TAG_DELETE_USER = "delete_user";
    const TAG_VISITOR_REGISTER_USER = "visitor_register_user";
    const TAG_USER_RANK = "rank_user";
    const TAG_PROFILE_USER = "profile_user";
    const TAG_UPDATE_PROFILE_USER = "update_profile_user";
    const TAG_DECREASE_HAZEL_LUCK_USER = "decrease_hazel_luck_user";
    const TAG_INFO_USER = "info_user";
    const TAG_CHANGE_HAZEL_LUCK_USER = "change_hazel_luck_user";
    const TAG_SEARCH_USER = "search_user";
    const TAG_CHANGE_LEVEL_USER = "change_level_user";
    const TAG_CUPS_USER = "cups_user";
    const TAG_UPDATE_ALLOW_CHAT_USER = "update_allow_chat_user";

    const TAG_UPDATE_PHONE_USER = "update_phone_user";
    const TAG_UPDATE_EMAIL_USER = "update_email_user";
    const TAG_UPDATE_BIRTH_DATE_USER = "update_birth_date_user";
    const TAG_UPDATE_PASSWORD_USER = "update_password_user";
    const TAG_UPDATE_GENDER_USER = "update_gender_user";
    const TAG_FORGET_PASSWORD_USER = "forget_password_user";

    // contact us
    const TAG_STORE_CONTACT_US = "store_contact_us";

    // question
    const TAG_NEXT_QUESTION = "next_question";
    const TAG_REPORT_QUESTION = "report_question";
    const TAG_RATE_QUESTION = "rate_question";
    const TAG_WORD_TABLE_QUESTION = "word_table_question";
    const TAG_REPORT_TABLE_QUESTION = "report_table_question";
    const TAG_RATE_TABLE_QUESTION = "rate_table_question";
    const TAG_CROSS_TABLE_QUESTION = "cross_table_question";


    // user_question
    const TAG_STORE_USER_QUESTION = "store_user_question";

    // message
    const TAG_INDEX_MESSAGE = "index_message";
    const TAG_READ_MESSAGE= "read_message";

    // user_message
    const TAG_DELETE_USER_MESSAGE = "delete_user_message";

    // draw
    const TAG_INDEX_DRAW = "index_draw";
    const TAG_WINNERS_DRAW = "winners_draw";
    const TAG_PARTICIPATE_DRAW = "participate_draw";

    //home
    const TAG_HOME_INDEX = "index_home";
    const TAG_EGG_HOME="egg_home";
    const TAG_UNREAD_HOME="unread_home";
    const TAG_ADVERTISMENT_HOME ="ad_hazel_home";
    const TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME ="player_id_home";
    const TAG_GET_VERSION_HOME="get_version_home";


    //market
    const TAG_MARKET_INDEX = "index_market";
    const TAG_MARKET_PURCHASE = "purchase_market";
    const TAG_MARKET_CONVERT = "convert_market";
    const TAG_MARKET_KEY = "key_market";

    //body_part
    const TAG_BODY_PART_INDEX = "index_body_part";
    const TAG_BODY_PART_PURCHASE = "purchase_body_part";

    //match
    const TAG_MATCH_REQUEST = "request_match";
    const TAG_MATCH_STATUS = "status_match";
    const TAG_MATCH_RESULT = "result_match";
    const TAG_MATCH_INDEX = "index_match";

    //user friend
    const TAG_USER_FRIEND_REQUEST = "request_user_friend";
    const TAG_USER_FRIEND_STATUS = "status_user_friend";
    const TAG_USER_FRIEND_CHANGE_STATUS = "change_status_user_friend";
    const TAG_USER_FRIEND_LIST = "list_user_friend";
    const TAG_USER_FRIEND_REVOKE = "revoke_user_friend";
    const TAG_USER_FRIEND_RECOMMEND = "recommend_user_friend";
    const TAG_USER_FRIEND_SYNC = "sync_user_friend";
    const TAG_USER_FRIEND_VALIDATE_SYNC = "validate_sync_user_friend";

    //log
    const TAG_LOG_STORE = "store_log";

    //universal_report
    const TAG_REPORT_UNIVERSAL = "report_universal";

    //universal_match
    const TAG_UNIVERSAL_MATCH_REQUEST = "request_universal_match";
    const TAG_UNIVERSAL_MATCH_STATUS = "status_universal_match";
    const TAG_UNIVERSAL_MATCH_RESULT = "result_universal_match";
    const TAG_UNIVERSAL_MATCH_INDEX = "index_universal_match";
    const TAG_UNIVERSAL_MATCH_CHANGE_STATUS = "change_status_universal_match";
    const TAG_UNIVERSAL_MATCH_REMATCH_REQUEST = "rematch_request_universal_match";




    // ERROR codes
    //public
    const ERROR_UNDEFINED_CODE = -1;
    const ERROR_ITEM_EXIST_CODE = 1;
    const ERROR_INSERT_FAIL_CODE = 2;
    const ERROR_TOKEN_MISMACH_CODE = 3;
    const ERROR_DEFECTIVE_INFORMATION_CODE = 4;
    const ERROR_TOKEN_BLACKLISTED_CODE = 5;
    const ERROR_INVALID_FILE_SIZE_CODE = 6;
    const ERROR_UPDATE_FAIL_CODE = 7;
    const ERROR_DELETE_FAIL_CODE = 8;
    const ERROR_OPERATION_FAIL_CODE = 9;
    const ERROR_ITEM_NOT_EXIST_CODE = 13;
    const ERROR_UNAUTHORIZED_ACCESS_CODE = 16;
    const ERROR_EGG_ALREDY_GIVEN_CODE = 18;
    const ERROR_WRONG_CHARSET = 25;
    const ERROR_TOKEN_INVALID = 32;



    //user
    const ERROR_USER_EXIST_CODE = 10;
    const ERROR_REGISTER_FAIL_CODE = 11;
    const ERROR_LOGIN_FAIL_CODE = 12;
    const ERROR_EMAIL_EXIST_CODE = 15;
    const ERROR_NOT_ENOUGH_BALANCE_USER_CODE = 19;
    const ERROR_PASSWORD_MISMATCH_CODE = 23;
    const ERROR_EXCEED_REGISTER_COUNT = 24;

    //draw
    const ERROR_NOT_TIME_DRAW_CODE = 17;

    //match
    const ERROR_NO_OPPONENT_AVAILABLE_CODE = 20;
    const ERROR_OPPONENT_CANCELED_CODE = 30;
    const ERROR_OPPONENT_ACCEPTED_CODE = 31;

    //market
    const ERROR_INVALID_PUBLIC_KEY_CODE = 21;

    //message
    const ERROR_INVALID_CODE_OF_INVITE_CODE = 22;

    //user friend
    const ERROR_STATUS_BLOCKED_CODE = 26;
    const ERROR_STATUS_DELETED_CODE = 27;
    const ERROR_STATUS_DECLINED_CODE = 28;
    const ERROR_STATUS_ANSWERED_CODE = 29;

/////////////////////////////Public Keys////////////////////////////////
    const PUBLIC_KEY_CAFE_BAZAR = "cafe_bazar";
    const PUBLIC_KEY_AVAL_MARKET = "aval_market";
    const PUBLIC_KEY_CHARKHONE = "charkhone";
    /// ///////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////
    public function initialize()
    {
        $this->Code = null;
        $this->Text = null;
    }

    public function initializeByCode($code)
    {
        $this->Code = $code;
        switch ($this->Code)
        {
            case self::ERROR_LOGIN_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorLoginFail');
                break;
            case self::ERROR_REGISTER_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorRegisterFail');
                break;
            case self::ERROR_USER_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorUserExist');
                break;
            case self::ERROR_TOKEN_MISMACH_CODE:
                $this->Text = trans('messages.msg_ErrorTokenMismach');
                break;
            case self::ERROR_DEFECTIVE_INFORMATION_CODE:
                $this->Text = trans('messages.msg_ErrorDefectiveInformation');
                break;
            case self::ERROR_TOKEN_BLACKLISTED_CODE:
                $this->Text = trans('messages.msg_ErrorTokenBlaklisted');
                break;
            case self::ERROR_INSERT_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorRegisterFail');
                break;
            case self::ERROR_ITEM_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorItemExist');
                break;
            case self::ERROR_EMAIL_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorEmailExist');
                break;
            case self::ERROR_ITEM_NOT_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorItemNotExist');
                break;
            case self::ERROR_UNAUTHORIZED_ACCESS_CODE:
                $this->Text = trans('messages.msg_ErrorUnauthorizedAccess');
                break;
            case self::ERROR_EGG_ALREDY_GIVEN_CODE:
                $this->Text = trans('messages.msg_ErrorEggAlredyGiven');
                break;
            case self::ERROR_NOT_TIME_DRAW_CODE:
                $this->Text = trans('messages.msg_ErrorNotTimeDraw');
                break;
            case self::ERROR_NO_OPPONENT_AVAILABLE_CODE:
                $this->Text = trans('messages.msg_ErrorNoOpponentAvailable');
                break;
            case self::ERROR_INVALID_PUBLIC_KEY_CODE:
                $this->Text = trans('messages.msg_ErrorInvalidPublicKey');
                break;
            case self::ERROR_INVALID_CODE_OF_INVITE_CODE:
                $this->Text = trans('messages.msg_ErrorInvalidInviteCode');
                break;
            case self::ERROR_STATUS_BLOCKED_CODE:
                $this->Text = trans('messages.msg_StatusBlocked');
                break;
            case self::ERROR_STATUS_DECLINED_CODE:
                $this->Text = trans('messages.msg_StatusDeclined');
                break;
            case self::ERROR_STATUS_DELETED_CODE:
                $this->Text = trans('messages.msg_StatusDeleted');
                break;
            case self::ERROR_STATUS_ANSWERED_CODE:
                $this->Text = trans('messages.msg_ErrorGoalUserAnswered');
                break;
            case self::ERROR_OPPONENT_CANCELED_CODE:
                $this->Text = trans('messages.msg_ErrorOpponentCanceled');
                break;
            case self::ERROR_OPPONENT_ACCEPTED_CODE:
                $this->Text = trans('messages.msg_ErrorOpponentAccepted');
                break;
            case self::ERROR_TOKEN_INVALID:
                $this->Text = trans('messages.msg_ErrorTokenInvalid');
                break;
            default:
                $this->Text = trans('messages.msg_ErrorUndefined');
                break;
        }
    }

    public function getMessage($code = null)
    {
        if($code != null)
        {
            $this->Code = $code;
        }

        switch ($this->Code)
        {
            case self::ERROR_LOGIN_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorLoginFail');
                break;
            case self::ERROR_REGISTER_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorRegisterFail');
                break;
            case self::ERROR_USER_EXIST_CODE:
                $this->Text =  trans('messages.msg_ErrorUserExist');
                break;
            default:
                $this->Text =  trans('messages.msg_ErrorUndefined');
                break;
        }
    }

    public static function getPublicKeyByName($name)
    {
        switch ($name)
        {
            case self::PUBLIC_KEY_CAFE_BAZAR:
                return "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDYcaEDJNut/1MNWH/n/X2Na+udVnCEba/7oCPj6I0LeUxHC01rwHHiZLtWK+V2pbKHOn6aTu7yq8o57G1LPuI7Rev1wTkPCJTInz1wOieQY2JeW50X7KTN2YkJQh/1o7fxhYdvTPY75LDbfNKrKwMXzJQlC87gB1tFNpO+dKf2ZexDC7QrcbhGIP8Ft3mltvdLFzQamIPPNP7ZSX+rhcFY4/OAyD55Rxxfy7ZTE+0CAwEAAQ==";
            case self::PUBLIC_KEY_AVAL_MARKET:
                return "aval market public key";
            case self::PUBLIC_KEY_CHARKHONE:
                return "charkhone public key";
            default:
                return "";
        }
    }

}