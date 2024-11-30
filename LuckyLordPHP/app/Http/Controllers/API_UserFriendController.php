<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App\User;
use App\UserFriend;
use App\Utility;
use App\RequestResponseAPI;
use Carbon\Carbon;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Hash;
use App\LogEvent;

class API_UserFriendController extends Controller
{
    protected $userFriend;

    public function __construct(UserFriend $userFriend)
    {
        $this->userFriend = $userFriend;
    }

    public function apiRequest(Request $request){

        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('opposite_user_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REQUEST]);
        }


        try {
            $user_Friends = $this->userFriend->checkExist($user->user_id,$request['opposite_user_id']);

            if(count($user_Friends)==0){
                $r=new Request();
                $r['user_1_id']=$user->user_id;
                $r['user_2_id']=$request['opposite_user_id'];
                $r['user_1_status']=UserFriend::USER_FRIEND_STATUS_REQUESTED;
                $r['user_2_status']=UserFriend::USER_FRIEND_STATUS_NORMAL;
                $r['sync_status']='00';

                $this->userFriend->intializeByRequest($r);
                $this->userFriend->store();

            }
            else if($user_Friends->user_1_id==$request['opposite_user_id'] && $user_Friends->user_2_id==$user->user_id && $user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_REQUESTED){
                $user_Friends->user_1_status=UserFriend::USER_FRIEND_STATUS_ACCEPTED;
                $user_Friends->user_2_status=UserFriend::USER_FRIEND_STATUS_ACCEPTED;
                $user_Friends->sync_status='00';
                $user_Friends->save();
            }
            else if( $user_Friends->user_2_id==$user->user_id && $user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $user_Friends->user_2_status==UserFriend::USER_FRIEND_STATUS_DELETED){
                $user_Friends->user_2_status=UserFriend::USER_FRIEND_STATUS_ACCEPTED;
                $user_Friends->sync_status='00';
                $user_Friends->save();
            }
            else if( $user_Friends->user_1_id==$user->user_id && $user_Friends->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_DELETED){
                $user_Friends->user_1_status=UserFriend::USER_FRIEND_STATUS_ACCEPTED;
                $user_Friends->sync_status='00';
                $user_Friends->save();
            }
            elseif ($user_Friends->user_1_id==$user->user_id and $user_Friends->user_2_status==UserFriend::USER_FRIEND_STATUS_BLOCKED){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_STATUS_BLOCKED_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REQUEST]);
            }
            elseif ($user_Friends->user_2_id==$user->user_id and $user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_BLOCKED){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_STATUS_BLOCKED_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REQUEST]);
            }elseif ($user_Friends->user_1_id==$user->user_id){
                $user_Friends->user_1_status=UserFriend::USER_FRIEND_STATUS_REQUESTED;
                $user_Friends->sync_status='00';
                $user_Friends->save();
            }elseif ($user_Friends->user_2_id==$user->user_id){
                $user_Friends->user_2_status=UserFriend::USER_FRIEND_STATUS_REQUESTED;
                $user_Friends->sync_status='00';
                $user_Friends->save();
            }
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REQUEST]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REQUEST]);

        }
    }

    public function apiStatus(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('opposite_user_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_STATUS]);
        }


        try {
            $user_Friends = $this->userFriend->checkExist($user->user_id,$request['opposite_user_id']);

            if(count($user_Friends)==0){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_ITEM_NOT_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_STATUS]);
            }else{
                $status=0;
                if ($user_Friends->user_1_id==$user->user_id){
                    $status=$user_Friends->user_2_status;
                }elseif ($user_Friends->user_2_id==$user->user_id){
                    $status=$user_Friends->user_1_status;
                }
            }
                return json_encode(['status' => $status,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_STATUS]);
            } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_STATUS]);
        }
    }

    public function apiChangeStatus(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('opposite_user_id') || !$request->has('status_code')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_CHANGE_STATUS]);
        }
        try {
            $user_Friends = $this->userFriend->checkExist($user->user_id,$request['opposite_user_id']);

            if(count($user_Friends)==0){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_ITEM_NOT_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_CHANGE_STATUS]);
            }else{
                if($user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $user_Friends->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED  && $request['status_code']==UserFriend::USER_FRIEND_STATUS_BLOCKED){
                    DB::table('user')->where('user_id', $user_Friends->user_2_id)->orWhere('user_id', $user_Friends->user_1_id)->decrement('friends_count');
                }
                if($user_Friends->user_1_id==$user->user_id){
                    if($request['status_code']==UserFriend::USER_FRIEND_STATUS_DELETED)
                        $user_Friends->user_1_status=UserFriend::USER_FRIEND_STATUS_NORMAL;
                    else
                        $user_Friends->user_1_status=$request['status_code'];
                    if($request['status_code']==UserFriend::USER_FRIEND_STATUS_ACCEPTED ||$request['status_code']==UserFriend::USER_FRIEND_STATUS_BLOCKED ||$request['status_code']==UserFriend::USER_FRIEND_STATUS_DECLINED  )
                        $user_Friends->sync_status='10';
                    if($request['status_code']==UserFriend::USER_FRIEND_STATUS_ACCEPTED){
                        $user_Friends->user_2_status=UserFriend::USER_FRIEND_STATUS_ACCEPTED;
                    }
                    $user_Friends->save();
                }elseif ($user_Friends->user_2_id==$user->user_id){
                    if($request['status_code']==UserFriend::USER_FRIEND_STATUS_DELETED)
                        $user_Friends->user_2_status=UserFriend::USER_FRIEND_STATUS_NORMAL;
                    else
                        $user_Friends->user_2_status=$request['status_code'];
                    if($request['status_code']==UserFriend::USER_FRIEND_STATUS_ACCEPTED ||$request['status_code']==UserFriend::USER_FRIEND_STATUS_BLOCKED ||$request['status_code']==UserFriend::USER_FRIEND_STATUS_DECLINED  )
                        $user_Friends->sync_status='01';
                    if($request['status_code']==UserFriend::USER_FRIEND_STATUS_ACCEPTED)
                        $user_Friends->user_1_status=UserFriend::USER_FRIEND_STATUS_ACCEPTED;
                    $user_Friends->save();
                }
                if($user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $user_Friends->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED  && $request['status_code']==UserFriend::USER_FRIEND_STATUS_ACCEPTED){
                    DB::table('user')->where('user_id', $user_Friends->user_2_id)->orWhere('user_id', $user_Friends->user_1_id)->increment('friends_count');
                }
                return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_CHANGE_STATUS]);
            }

        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_CHANGE_STATUS]);
        }
    }

    public function apiList(Request $request){

        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('status_code')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_LIST]);
        }


        try {
            return json_encode(['users'=>$this->userFriend->index($request['status_code'],$user->user_id),'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_LIST]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_LIST]);
        }
    }


    //this function revoke(cancel) the friend request that user send to other user
    //if user 2 answer to request Dosen't cancel
    public function apiRevoke(Request $request){

        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('opposite_user_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REVOKE]);
        }


        try {
            $user_Friends = $this->userFriend->checkExist($user->user_id,$request['opposite_user_id']);

            if(count($user_Friends)==0){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_ITEM_NOT_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REVOKE]);
            }else{
                if ($user_Friends->user_1_id==$user->user_id && $user_Friends->user_2_status==UserFriend::USER_FRIEND_STATUS_NORMAL){
                    $user_Friends->user_1_status=UserFriend::USER_FRIEND_STATUS_NORMAL;
                    $user_Friends->deleted_at=Carbon::now();
                    $user_Friends->save();
                    return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REVOKE]);
                }elseif ($user_Friends->user_2_id==$user->user_id && $user_Friends->user_1_status==UserFriend::USER_FRIEND_STATUS_NORMAL){
                    $user_Friends->user_2_status=UserFriend::USER_FRIEND_STATUS_NORMAL;
                    $user_Friends->deleted_at=Carbon::now();
                    $user_Friends->save();
                    return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REVOKE]);
                }
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_STATUS_ANSWERED_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REVOKE]);
            }

        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_REVOKE]);
        }
    }

    public function apiRecommend(Request $request){
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
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_RECOMMEND]);
        }


        try {
            return json_encode([ 'users' => $this->userFriend->Recommend($user->user_id) , 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_RECOMMEND]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_RECOMMEND]);
        }
    }

    public function apiSync(Request $request){
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
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_SYNC]);
        }


        try {
            $user_friends=$user_friend=DB::table('user_friend')
                ->select('user_friend_id','user_1_id','user_2_id','user_1_status','user_2_status')
                ->where([
                    ['user_1_id', '=', $user->user_id],
                    ['sync_status', 'like', '0%']
                ])
                ->orWhere([
                    ['user_2_id', '=', $user->user_id],
                    ['sync_status', 'like', '%0']
                ])
                ->get();
            return json_encode(['user_friends'=>$user_friends,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_SYNC]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_SYNC]);
        }
    }

    public function apiValidateSync(Request $request){

        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('user_friend_ids')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_VALIDATE_SYNC]);
        }


        try {
            $user_friends=DB::table('user_friend')
                ->select('user_friend_id','user_1_id','user_2_id','user_1_status','user_2_status','sync_status')
                ->whereIn('user_friend_id',explode(",",$request['user_friend_ids']))
                ->get();

            for($index=0;$index<count($user_friends);$index++){
                $sync_status='';
                if($user_friends[$index]->user_1_id==$user->user_id)
                    $sync_status='1'.mb_substr($user_friends[$index]->sync_status,1,1);
                elseif($user_friends[$index]->user_2_id==$user->user_id)
                    $sync_status=mb_substr($user_friends[$index]->sync_status,0,1).'1';
                DB::table('user_friend')
                    ->where('user_friend_id', $user_friends[$index]->user_friend_id)
                    ->update(['sync_status' => $sync_status]);
            }

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_VALIDATE_SYNC]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_FRIEND_VALIDATE_SYNC]);
        }
    }
}