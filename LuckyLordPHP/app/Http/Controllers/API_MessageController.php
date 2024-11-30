<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 5/29/2017
 * Time: 9:46 AM
 */

namespace App\Http\Controllers;
use App\LogEvent;
use App\Message;
use App\UserMessage;
use Illuminate\Http\Request;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Log;

use App\RequestResponseAPI;

class API_MessageController extends Controller
{
    protected $message;

    public function __construct(Message $message)
    {
        $this->message = $message;
    }


    public function apiIndex(Request $request)
    {
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('skip')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_INDEX_MESSAGE]);

        }
        ////////////////////////////////////////////

        try {
            //        select company data with
            $paramsObj1 = array(
                array("se","user_message", "user_message_id"),
                array("se","message", "title"),
                array("se","message", "description"),
                array("se","user_message", "is_readed"),
                array("se","message", "created_at")
            );

            //join
            //join
            $paramsObj2 = array(
                array("join",
                    "user_message",
                    array("user_message.message_id", "=", "message.message_id")
                )
            );
            //conditions

            $paramsObj3 = array(
                array("where",
                    "user_message.user_id",'=',$user->user_id
                ),
                array("whereRaw",
                    "user_message.deleted_at IS NULL"
                ),
                array("orderBy",
                    "user_message.created_at", "DESC"
                ),
                array("skip",
                    $request['skip']
                ),
                array("take",
                    "10"
                )
            );
            $this->message->initialize();
            $messages = $this->message->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);
            return json_encode(['messages' => $messages,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_INDEX_MESSAGE]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INDEX_MESSAGE]);

        }
    }

    public function apiRead(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('user_message_id')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_READ_MESSAGE]);

        }
        ////////////////////////////////////////////

        try {
            $user_Message=new UserMessage();
            $user_Message->newQuery()
                ->where(['user_message_id'=>$request['user_message_id']])
                ->update([
                'is_readed' => 1
            ]);
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_READ_MESSAGE]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_READ_MESSAGE]);

        }
    }
}