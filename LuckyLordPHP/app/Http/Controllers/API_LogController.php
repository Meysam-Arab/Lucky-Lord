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
use App\RequestResponseAPI;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
class API_LogController extends Controller
{
    public function apiStore(Request $request)
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
        if (!$request->has('tag') || !$request->has('controller_and_action_name')||
            !$request->has('error_message') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_LOG_STORE]);

        }
        ////////////////////////////////////////////
        try {
//                $request['user_id'] = $user->user_id;
//                $this->contactUs->intializeByRequest($request);
//                $this->contactUs->store();

            $logEvent = new LogEvent($user-> user_id," خطای طرف گوشی ::". $request['controller_and_action_name'], "Main Message: " . $request['error_message']);
            $logEvent->store();

            return json_encode([ 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_LOG_STORE]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_LOG_STORE]);

        }
    }

    public function getErrors(){
        try {

                $messageError=DB::table('user_question_report')->select('user_question_report_id','user_id','question_id', 'code','description','table_code','updated_at')->get();


                return response()->json([
                    'success' => true,
                    'msgs'=>$messageError
                ]);
//            return view('ContactUs.index', ['message' => $messageError]);

        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INFO_USER]);
        }
    }
}