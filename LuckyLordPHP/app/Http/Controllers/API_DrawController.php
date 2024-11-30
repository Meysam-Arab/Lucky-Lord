<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 6/1/2017
 * Time: 11:41 AM
 */

namespace App\Http\Controllers;

use App\Draw;
use App\LogEvent;
use App\Reward;
use App\RequestResponseAPI;
use App\User;
use App\UserDraw;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Carbon\Carbon;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;

class API_DrawController extends Controller
{
    protected $draw;

    public function __construct(Draw $draw)
    {
        $this->draw = $draw;
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
        if (!$request->has('tag') || !$request->has('skip') || !$request->has('is_ended')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_INDEX_DRAW]);
        }
        ////////////////////////////////////////////



        try {

            //////////////////////////get list of draws that user exist on or not exist
//            SELECT draw.draw_date_time,draw.draw_id, draw.draw_guid,draw.cost,draw.sponser,draw.description,draw.link ,IF(draw.draw_id IN(SELECT user_draw.draw_id from user_draw where user_draw.user_id =15115 AND user_draw.deleted_at IS NULL), TRUE, FALSE) as is_participate FROM draw WHERE draw.draw_date_time > 0 AND draw.deleted_at IS NULL ORDER BY draw.draw_date_time DESC
            if($request['is_ended']==0){
//                $query_for_get_Draws="SELECT DISTINCT draw.draw_date_time,draw.draw_id, draw.draw_guid,draw.cost,draw.sponser,draw.description,draw.link,IF(user_draw.draw_id IS Not NULL AND user_draw.user_id =:user_id AND user_draw.deleted_at IS NULL, TRUE, FALSE) as is_participate FROM draw LEFT JOIN user_draw ON (draw.draw_id = user_draw.draw_id) WHERE draw.draw_date_time > :currentTime AND draw.deleted_at IS NULL ORDER BY draw.draw_date_time DESC ";
                $query_for_get_Draws="SELECT draw.draw_date_time,draw.draw_id, draw.draw_guid,draw.cost,draw.sponser,draw.description,draw.link ,IF(draw.draw_id IN(SELECT user_draw.draw_id from user_draw where user_draw.user_id =:user_id AND user_draw.deleted_at IS NULL), TRUE, FALSE) as is_participate FROM draw WHERE draw.draw_date_time > :currentTime  AND draw.deleted_at IS NULL ORDER BY draw.draw_date_time ASC ";
                $draws = DB::select($query_for_get_Draws, ['user_id'=>$user->user_id,'currentTime'=>Carbon::now()]);
            }else{
                $query_for_get_Draws="SELECT DISTINCT draw.draw_date_time,draw.draw_id, draw.draw_guid,draw.cost,draw.sponser,draw.description,draw.link,IF(user_draw.draw_id IS Not NULL AND user_draw.user_id =:user_id AND user_draw.deleted_at IS NULL, TRUE, FALSE) as is_participate FROM draw LEFT JOIN user_draw ON (draw.draw_id = user_draw.draw_id) WHERE draw.draw_date_time < :currentTime AND  draw.deleted_at IS NULL ORDER BY draw.draw_date_time DESC LIMIT 10 OFFSET :offset";
                $draws = DB::select($query_for_get_Draws, ['offset' =>$request['skip'],'user_id'=>$user->user_id,'currentTime'=>Carbon::now()]);
            }



            $array_draw_ids=[];
            foreach ($draws as $draw){
                array_push($array_draw_ids,$draw->draw_id);
            }

            $Rewards = Reward::select('draw_id','description','count','unit')->whereIn('draw_id', $array_draw_ids)->get();
            $index=0;
            foreach ($draws as $draw){
                $this_draw_rewards=[];
                foreach ($Rewards as $Reward){
                    if($draw->draw_id==$Reward->draw_id)
                        array_push($this_draw_rewards,["description" => $Reward->description,"count" => $Reward->count,"unit" => $Reward->unit]);


                }
                $image=$this->draw->getSponserImage($draw->draw_guid);
                if($image!=null){
                    $draw->image=$image;
                }
//                array_push($draws[$index],$this_draw_rewards);
                $draw->rewards = $this_draw_rewards;
                $index++;
            }

            return json_encode(['draws'=>$draws, 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_INDEX_DRAW]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INDEX_DRAW]);
        }
    }


    public function apiWinners(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') ||
            !$request->has('draw_id') || !$request->has('draw_guid')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_WINNERS_DRAW]);
        }


        try {
            //        select company data with
            $paramsObj1 = array(
                array("se","user", "user_name"),
                array("se","user", "visitor_user_name"),
                array("se","user", "tel"),
                array("se","user", "gender"),
                array("se","user", "image"),
                array("as","reward", "description", "reward_description")
            );

            //join
            $paramsObj2 = array(
                array("join",
                    "user_draw",
                    array("user_draw.user_id", "=", "user.user_id")
                ),
                array("join",
                    "draw",
                    array("user_draw.draw_id", "=", "draw.draw_id")
                ),
                array("join",
                    "reward",
                    array("reward.draw_id", "=", "draw.draw_id")
                )
            );
            //conditions

            $paramsObj3 = array(
                array("whereRaw",
                    "draw.draw_id=".$request['draw_id']
                ),
                array("whereRaw",
                    "draw.draw_guid='".$request['draw_guid']."'"
                ),
                array("where",
                    "user_draw.reward_id",'<>',NULL
                ),
                array("whereRaw",
                    "user_draw.reward_id = reward.reward_id"
                ),
                array("orderBy",
                    "draw.draw_date_time", "DESC"
                )
            );

            $user = new User();
            $user->initialize();
            $winners = $user->getFullDetail($paramsObj1,$paramsObj2,$paramsObj3);
            for ($index=0;$index < count($winners);$index++) {
                if($winners[$index]->user_name==null)
                    $winners[$index]->user_name=$winners[$index]->visitor_user_name;
                unset($winners[$index]->visitor_user_name);
            }
            return json_encode(['winners' => $winners,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_WINNERS_DRAW]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_WINNERS_DRAW]);

        }

    }


    public function apiParticipate(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('draw_id') || !$request->has('draw_guid')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_PARTICIPATE_DRAW]);
        }
        ////////////////////////////////////////////


        try{
            $draw = Draw::where('draw_id', '=',$request['draw_id'])->where('draw_guid', '=', $request['draw_guid'])->first();
            $user_in_draw=User::where('user_id', '=',$user->user_id)->first();
            $user_draw = UserDraw::where('user_id', '=', $user->user_id)->where('draw_id', '=', $request['draw_id'])->where('deleted_at', '=', null)->first();
            if ($user_draw==null){
//                insert new record into database
                    IF($user_in_draw->hazel>$draw->cost){
                        ///// decrease cost OF draw in USER HAZEL
                        $user_in_draw->hazel=$user_in_draw->hazel - $draw->cost;
                        $user_in_draw->push();

                        $addUser_draw=new UserDraw();
                        $addUser_draw->user_id=$user->user_id;
                        $addUser_draw->draw_id=$request['draw_id'];
                        $addUser_draw->store();
                    }else{
                        return json_encode(['error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_PARTICIPATE_DRAW]);

                    }
            }else {
                ////DELETE USER IN COST
                $user_draw->forceDelete();


//                    $user_draw->deleted_at=Carbon::now();
//                    $user_draw->push();

                    ///// INSERT MIDDLE OF COST TO USER HAZEL
                    $user_in_draw->hazel=$user_in_draw->hazel + $draw->cost/2;
                    $user_in_draw->push();

            }
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_PARTICIPATE_DRAW]);

        }
        catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_PARTICIPATE_DRAW]);
        }
    }
}