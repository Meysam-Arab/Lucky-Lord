<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App\LogEvent;
use App\User;
use App\UserMessage;
use App\Utility;
use App\RequestResponseAPI;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Hash;
class API_UserMessageController extends Controller
{
    protected $userMessage;

    public function __construct(UserMessage $userMessage)
    {
        $this->userMessage = $userMessage;
    }


    public function apiDelete(Request $request){
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::authenticate($token);

        if (!$request->has('user_message_id')|| !$request->has('tag') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_DELETE_USER_MESSAGE]);

        }
        ///////////////////////////
        try {
            $user_message_id=$request->input('user_message_id');
            $request = new \Illuminate\Http\Request();
            $request->replace(['user_id' =>$user-> user_id, 'user_message_id' =>$user_message_id]);

            $this->userMessage->intializeByRequest($request);
            if($request['user_message_id']!= null)
                $this->userMessage->remove();
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_DELETE_USER_MESSAGE]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_DELETE_USER_MESSAGE]);

        }
    }

    public function saveUserMessage(Request $request){
        ///////////////////////////
        try {
            $request['is_readed']=0;
            $this->userMessage->intializeByRequest($request);
            $this->userMessage->store();

            DB::table('contact_us')
                ->where('contact_us_id', $request['contact_us_id'])
                ->update(['is_readed' => 1]);

            return response()->json([
                'success' => true,
                'contact_us_id'=>$request['contact_us_id']
            ]);

        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
//            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => 'admin']);

        }
    }

    public function deleteUserMessage(Request $request){
        ///////////////////////////
        try {
//            $request['is_readed']=0;
//            $this->userMessage->intializeByRequest($request);
//            $this->userMessage->store();
            DB::table('contact_us')
                ->where('contact_us_id', $request['contact_us_id'])
                ->where('contact_us_guid', $request['contact_us_guid'])
                ->update(['is_readed' => 1]);

            return response()->json([
                'success' => true
            ]);

        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
//            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => 'admin']);

        }
    }


}