<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 6/1/2017
 * Time: 11:41 AM
 */

namespace App\Http\Controllers;
use App\Advertisment;
use App\LogEvent;
use App\Question;
use App\UserCategory;
use App\UserQuestion;
use App\User;
use App\RequestResponseAPI;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use App\UserFriend;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Carbon\Carbon;

class API_HomeController extends Controller
{
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

        try {
            //////////////////////////////////get Main_Advertise_Of_Bottom


            $Main_Advertise_Of_Bottom=new Advertisment();
            $Main_Advertise_Of_Bottom->title='0';
            $Main_Advertise_Of_Bottom->description=base64_encode(Advertisment::getImage('amir'));
            $Main_Advertise_Of_Bottom->link="http://mahak-charity.org/main/index.php/fa/";


//            $Main_Advertise_Of_Bottom=new Advertisment();
//            $Main_Advertise_Of_Bottom->title='شرکت فردان';
//            $Main_Advertise_Of_Bottom->description='یک شرکت برنامه نویسی است';
//            $Main_Advertise_Of_Bottom->link="http://www.fardan7eghlim.ir";
            //////////////////////////////////get Main_Advertise_Of_Bottom
            $Main_Advertise_Of_Top=new Advertisment();
            $Main_Advertise_Of_Top->title='لاکی لرد';
            $Main_Advertise_Of_Top->description='تخفیف فندق - هزار و پانصد تا هزار تومان';
            $Main_Advertise_Of_Top->link="http://www.fardan7eghlim.ir";

            //////////////////////////////////get data of user According to "Luck"
            $query_for_get__user="SELECT egg_date_time,level,cups,egg_score,hazel,luck FROM user WHERE user_id = :id";
            $currentUser = DB::select($query_for_get__user, ['id' => $user->user_id]);

            $egg_hazel=null;
            if($request->input('egg') == "1"){
                $egg_date_time = new Carbon($currentUser[0]->egg_date_time);
//                if($egg_date_time->toDateString()!= Carbon::now()->toDateString()){
                  if(Carbon::now()->subDay(1)>$egg_date_time->toDateString() ){
                    $user_for_set_egg_date_time=new User();

                    if(rand(1,1000)<800){
                        $chance=rand(1,1000);
                        if($chance<=500){
                            $egg_hazel=rand(1,20);
                        }else{
                            $egg_hazel=rand(21,50);
                        }
                    }else{
                        $egg_hazel=null;
                    }
                    if($egg_hazel==null)
                        $user_for_set_egg_date_time->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'egg_date_time' => Carbon::now(),
                            'egg_score'=>0,
                        ]);
                    else
                        $user_for_set_egg_date_time->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'egg_date_time' => Carbon::now(),
                            'egg_score'=>$egg_hazel,
                        ]);
                }
            }

            ////////////////////////////////////get unread_message_count
            $result=$results = DB::table('user_message')
                ->select(DB::raw('count(*) as unread_message_count'))
                ->where('user_id', '=', $user->user_id)
                ->where('is_readed', '=', '0')
                ->whereRaw('deleted_at IS NULL')
                ->get();
            $unread_message_count=$result[0]->unread_message_count;

            $version = "3.11";
//            $link = "https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa";
            $link = "http://luckylord.ir/getAPK";
            //$link = "";
//            $message = "متن اضطراری";
//            $message = "به دلیل اعمال تغییرات در سرور ظرف امروز و فردا ممکن است در بازی اختلال ایجاد شود - پیشاپیش از صبر و حوصله شما سپاسگذاریم";

            $message = "";

            $received_friend_request_count=new UserFriend();

            return json_encode([
                'message'=>$message,
                'level'=>$currentUser[0]->level,
                'cups'=>$currentUser[0]->cups,
                'received_friend_request_count'=> $received_friend_request_count->countOfRequest($user->user_id),
//                'ad_question_level_count'=>Advertisment::AD_QUESTION_LEVEL_COUNT,
                'ad_main_count'=>Advertisment::AD_MAIN_COUNT,
                'server_check_interval'=>2,
                'version'=>$version,
                'link'=>$link,
                'unread_message_count' => $unread_message_count,
                'egg_reward'=>$egg_hazel,
                'hazel'=>$currentUser[0]->hazel,
                'luck'=>$currentUser[0]->luck,
                'bottom_advertisment'=>$Main_Advertise_Of_Bottom,
                'top_advertisment'=>$Main_Advertise_Of_Top,
                'token' => $token,
                'error' => 0,
                'tag' => RequestResponseAPI::TAG_HOME_INDEX,

            ]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_HOME_INDEX]);
        }
    }

    public function apiGetVersion(Request $request){
        $version = "3.11";
//            $link = "https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa";
        $link = "http://luckylord.ir/getAPK";
        //$link = "";
//            $message = "متن اضطراری";
//        $message = "به دلیل اعمال تغییرات در سرور ظرف امروز و فردا ممکن است در بازی اختلال ایجاد شود - پیشاپیش از صبر و حوصله شما سپاسگذاریم";

        $message = "";

        return json_encode([
            'message'=>$message,
            'version'=>$version,
            'link'=>$link,
            'error' => 0,
            'tag' => RequestResponseAPI::TAG_GET_VERSION_HOME,

        ]);
    }

    public function apiEgg(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('ad')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_EGG_HOME]);
        }
        ////////////////////////////////////////////

        try {
            $egg_score=DB::table('user')->where('user_id', $user->user_id)->value('egg_score');


            $use_hazel_Increase_for_egg=new User();
            if($request['ad']==1){
                $use_hazel_Increase_for_egg->newQuery()->where(['user_id'=>$user->user_id])->update([
                    'egg_score' => 0,
                    'hazel' => \DB::raw( 'hazel +'.$egg_score*2),
                ]);
            }else{
                $use_hazel_Increase_for_egg->newQuery()->where(['user_id'=>$user->user_id])->update([
                    'egg_score' => 0,
                    'hazel' => \DB::raw( 'hazel +'.$egg_score),
                ]);
            }


            return json_encode(['egg_score'=>$egg_score,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_EGG_HOME]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_EGG_HOME]);
        }

    }

    public function test(){

        try {


            $value="hello meysam";
            return json_encode(['value' => $value]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_EGG_HOME]);
        }

    }

    public function apiUnread(Request $request){
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
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNREAD_HOME]);
        }
        ////////////////////////////////////////////


        try {
            ////////////////////////////////////get unread_message_count
            $result=$results = DB::table('user_message')
                ->select(DB::raw('count(*) as unread_message_count'))
                ->where('user_id', '=', $user->user_id)
                ->where('is_readed', '=', '0')
                ->whereRaw('deleted_at IS NULL')
                ->get();
            $unread_message_count=$result[0]->unread_message_count;


//            //        select company data with
//            $paramsObj1 = array(
//                array("se","user", "user_id")
//            );
//
//            //join
//            $paramsObj2 = null;
//            //conditions
//
//            $paramsObj3 = array(
//                array("orderBy",
//                    "user.luck", "DESC"
//                )
//            );
//            $user_Rank=new User();
//            $user_Rank->initialize();
//            $users = $user_Rank->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);
//            $total=count($users);
//            $rank=$total;
//            for ($index=0;$index < count($users);$index++) {
//                if($users[$index]->user_id==$user->user_id){
//                    $rank=$index+1;
//                    return json_encode(['unread_message_count'=>$unread_message_count,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNREAD_HOME]);
//                }
//
//            }
            $received_friend_request_count=new UserFriend();
            return json_encode(['received_friend_request_count'=> $received_friend_request_count->countOfRequest($user->user_id),'unread_message_count'=>$unread_message_count,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNREAD_HOME]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNREAD_HOME]);
        }
    }

    public function apiAdHazel(Request $request){
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
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_ADVERTISMENT_HOME]);
        }
        ////////////////////////////////////////////


        try {
            User::IncreaseHazel($user->user_id,Advertisment::AD_REWARD);
            return json_encode(['ad_reward'=>Advertisment::AD_REWARD,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_ADVERTISMENT_HOME]);

        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_ADVERTISMENT_HOME]);
        }
    }

    public function apiPlayerId(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('player_id')
        ) {
            return json_encode(['token'=>$token,'error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME]);
        }
        ////////////////////////////////////////////


        try {
            $user_for_set_one_signal_player_id=new User();


            $user_for_set_one_signal_player_id->newQuery()->where(['user_id'=> $user->user_id])->update([
                'one_signal_player_id' => $request['player_id']
            ]);
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME]);

        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['token'=>$token,'error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME]);
        }
    }
}