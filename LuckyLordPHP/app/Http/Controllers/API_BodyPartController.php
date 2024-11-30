<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 6/1/2017
 * Time: 11:41 AM
 */

namespace App\Http\Controllers;

use App\BodyPart;
use App\Draw;
use App\LogEvent;
use App\Reward;
use App\RequestResponseAPI;
use App\User;
use App\UserBodyPart;
use App\UserDraw;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Carbon\Carbon;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;

class API_BodyPartController extends Controller
{
    protected $bodyPart;

    public function __construct(BodyPart $bodyPart)
    {
        $this->bodyPart = $bodyPart;
    }

    public function apiPurchase(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('name')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_BODY_PART_PURCHASE]);
        }
        ////////////////////////////////////////////


        try {

            //////////////////////////get cost of this body part with name
            $body_part = DB::table('body_part_cost')->where('title', $request['name'])->first();
            if($body_part!=null)
                $body_part_cost=$body_part->cost;
            else
                return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_BODY_PART_PURCHASE]);

            //////////////////////////get hazel of this current user
            $current_user = DB::table('user')->where('user_id', $user->user_id)->first();
            $hazel=$current_user->hazel;

            if($hazel>=$body_part_cost){
                $body_part_cost*=-1;
                User::IncreaseDecreaseHazelLuck($user->user_id,$body_part_cost,0);

                $CurrentUserBodyPart=new UserBodyPart();
                $re=new Request();
                $re['body_part_id']=$body_part->body_part_cost_id;
                $re['user_id']=$user->user_id;
                $CurrentUserBodyPart->intializeByRequest($re);
                $CurrentUserBodyPart->store();
            }else{
                return json_encode(['error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_BODY_PART_PURCHASE]);
            }

            return json_encode([ 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_BODY_PART_PURCHASE]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_BODY_PART_PURCHASE]);
        }
    }



    public function apiIndex(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_BODY_PART_INDEX]);
        }
        ////////////////////////////////////////////

        try {

            $query_for_getPartBody="SELECT title,cost FROM body_part_cost WHERE body_part_cost_id NOT IN (SELECT body_part_id FROM user_body_part WHERE user_id = :id)";
            $body_parts = DB::select($query_for_getPartBody, ['id' =>$user ->user_id]);

            return json_encode([ 'body_parts'=>$body_parts,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_BODY_PART_INDEX]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_BODY_PART_INDEX]);
        }
    }
}