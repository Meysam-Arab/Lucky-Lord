<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\User;
use App\RequestResponseAPI;
use App\UserFriend;
use Illuminate\Foundation\Auth\AuthenticatesUsers;
use File;
use Illuminate\Http\Request;
use Log;
use DB;
use Tymon\JWTAuth\Facades\JWTAuth;

class API_LoginController extends Controller
{

    /*
    |--------------------------------------------------------------------------
    | Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles authenticating users for the application and
    | redirecting them to your home screen. The controller uses a trait
    | to conveniently provide its functionality to your applications.
    |
    */
    use AuthenticatesUsers;

    /**
     * Where to redirect users after login.
     *
     * @var string
     */
    protected $redirectTo = '/user';

    protected $username = 'user_name';

    public function username()
    {
        return 'user_name';
    }

    public function apiLogIn(Request $request)
    {

        $credentials = $request->only('user_name','password');

        if(! $token=JWTAuth::attempt($credentials))
        {
            return json_encode(['error'=>RequestResponseAPI::ERROR_LOGIN_FAIL_CODE]);
        }



//        $user = new User();
//        $user->initialize();
//        $user->user_name = $request['user_name'];
//        $users =$user->select('chat_allow as allowChat','user.*' );
//        $user = $users[0];

        $user = DB::table('user')
            ->select('user_id','user_guid','visitor_user_name','user_name','invite_code','email','tel','birth_date','gender', 'hazel','luck','level','cups','image','chat_allow as allowChat' )
            ->where('user_name', $request['user_name'])
            ->first();

//        //meysam - added in 13970118
//        User::updateToken($token,$user->user_id,$user->user_guid);

        $payloadable = [
            'user_id' => $user->user_id,
            'user_guid' => $user->user_guid
        ];
        $token = JWTAuth::fromUser($user,$payloadable);

//        $user->setHidden(['user_guid','user_id', 'visitor_user_name', 'password',
//            'egg_date_time', 'egg_score', 'remember_token', 'api_token', 'is_active',
//            'created_at', 'updated_at', 'deleted_at']);



//        $list_users_requested_friendship=

        $UserFriends = DB::table('user_friend')
            ->where([
            ['user_1_id', 'like', $user->user_id],
            ['deleted_at', '=', null],
            ])
            ->orWhere([
                ['user_2_id', 'like', $user->user_id],
                ['deleted_at', '=', null],
            ])
            ->get();

        $a=array();
        for($index=0;$index<count($UserFriends);$index++){
            if ($UserFriends[$index]->user_1_id==$user->user_id){
                array_push($a,$UserFriends[$index]->user_2_id);
            }elseif ($UserFriends[$index]->user_2_id==$user->user_id){
                array_push($a,$UserFriends[$index]->user_1_id);
            }
        }
        $users = DB::table('user')
            ->select('user_id','visitor_user_name','user_name', 'hazel','luck','level','cups','image','friends_count','chat_allow as allowChat' )
            ->whereIn('user_id', $a)
            ->get();
        for ($index=0;$index < count($users);$index++) {
            if($users[$index]->user_name==null)
                $users[$index]->user_name=$users[$index]->visitor_user_name;
            unset($users[$index]->visitor_user_name);
        }

        $list_users_requested_friendship=[];
        $list_users_received_friendship_requests=[];
        $list_users_friends=[];
        $list_users_blocked=[];
        for($index=0;$index<count($UserFriends);$index++){
            ///1 for set $list_users_requested_friendship
            if ($UserFriends[$index]->user_1_id==$user->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_NORMAL ){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                        array_push($list_users_requested_friendship,$users[$index2]);
                        break;
                    }
            }elseif ($UserFriends[$index]->user_2_id==$user->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_NORMAL){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                        array_push($list_users_requested_friendship,$users[$index2]);
                        break;
                    }
            }
            ///end of set $list_users_requested_friendship

            ///2 for set $list_users_received_friendship_requests
            if ($UserFriends[$index]->user_1_id==$user->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_NORMAL ){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                        array_push($list_users_received_friendship_requests,$users[$index2]);
                        break;
                    }
            }elseif ($UserFriends[$index]->user_2_id==$user->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_NORMAL){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                        array_push($list_users_received_friendship_requests,$users[$index2]);
                        break;
                    }
            }
            ///end of set $list_users_received_friendship_requests

            ///3 for set $list_users_friends
            if ($UserFriends[$index]->user_1_id==$user->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED ){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                        array_push($list_users_friends,$users[$index2]);
                        break;
                    }
            }elseif ($UserFriends[$index]->user_2_id==$user->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                        array_push($list_users_friends,$users[$index2]);
                        break;
                    }
            }
            ///end of set $list_users_friends

            ///4 for set $list_users_blocked
            if ($UserFriends[$index]->user_1_id==$user->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_BLOCKED  ){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                        array_push($list_users_blocked,$users[$index2]);
                        break;
                    }
            }elseif ($UserFriends[$index]->user_2_id==$user->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_BLOCKED ){
                for($index2=0;$index2<count($users);$index2++)
                    if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                        array_push($list_users_blocked,$users[$index2]);
                        break;
                    }
            }
            ///end of set $list_users_blocked
        }

        return json_encode(['token'=>$token,'error'=>0,'user'=>$user,'list_users_requested_friendship'=>$list_users_requested_friendship, 'list_users_received_friendship_requests'=>$list_users_received_friendship_requests, 'list_users_friends'=>$list_users_friends, 'list_users_blocked'=>$list_users_blocked, 'tag'=>RequestResponseAPI::TAG_LOGIN_USER]);
    }

    public function apilogout()
    {
        JWTAuth::invalidate(JWTAuth::getToken());

    }

}
