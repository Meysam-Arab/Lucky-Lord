<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App\Jobs\SendEmail;
use App\LogEvent;
use App\Message;
use App\User;
use App\UserMessage;
use App\UserUniversalMatch;
use App\Utility;
use App\UserFriend;
use App\UserUniversalMatchStatistics;
use App\RequestResponseAPI;
use Carbon\Carbon;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Mockery\Exception;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Hash;
use Validator;
use App\Category;


class API_UserController extends Controller
{

    protected $user;

    public function __construct(User $user)
    {
        $this->user = $user;
    }

    public function apiIndex(Request $request)
    {
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::authenticate($token);

        try {
            $users = $this->user->select(new User());

            return json_encode(['token' => $token, 'error' => 0, 'users' => $users, 'tag' => "user_list"]);
        } catch (\Exception $ex) {
            $logEvent = new LogEventRepository($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INDEX_USER]);

        }
    }

    public function apiRegister(Request $request)
    {
        // Validate the request...
        $request->request->remove('score');
        if (!$request->has('user_name')|| !$request->has('tag') || !$request->has('password')||
             !$request->has('gender')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);

        }
//        if(!$request->has('visitor_user_name')){
//            return json_encode(['error' => RequestResponseAPI::ERROR_UNAUTHORIZED_ACCESS_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
//
//        }


        if (mb_strlen($request->input('user_name'), "UTF-8") < 3 || mb_strlen($request->input('user_name'), "UTF-8") > 20)
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);

        if (mb_strlen($request->input('password'), "UTF-8") < 5)
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);


        if (User::existByUserName($request->input('user_name'),false,null)) {
            return json_encode(['error' => RequestResponseAPI::ERROR_USER_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
        }

        if($request->has('email'))
            if (User::existByEmail($request->input('email'),null,null)) {
                return json_encode(['error' => RequestResponseAPI::ERROR_EMAIL_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
            }
        $countOfUserRegistered=User::countOfUserDevice($request['visitor_user_name']);

        if($countOfUserRegistered>User::REGISTER_COUNT){
            return json_encode(['error' => RequestResponseAPI::ERROR_EXCEED_REGISTER_COUNT, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);
        }
        try {
           DB::beginTransaction();
            $existUser=$this->user->existByVisitorUserName($request->input('visitor_user_name'),null,null);

            if($existUser != false ){
                $user=$existUser;
                ////set hazel for invitecode
                if($request->has('invite_code'))
                {
                    $parentUser = User::findByInviteCode($request['invite_code']);
                    if($parentUser==null){
                        return json_encode(['error' => RequestResponseAPI::ERROR_INVALID_CODE_OF_INVITE_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
                    }
//                User::IncreaseLuck($parentUser->user_id,User::INVITE_AMOUNT);
                    if($parentUser != null){
//                        User::IncreaseDecreaseHazelLuck($parentUser->user_id,User::Person_introducer_INVITE_CODE_hazel_mount,0);

                        $userP = User::findById($parentUser->user_id);
                        $userP->newQuery()->where(['user_id'=> $userP->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.User::Person_introducer_INVITE_CODE_hazel_mount ),
                            'cups' => \DB::raw( "CONCAT(SUBSTRING(cups, 1, 4),REPLACE(SUBSTR(cups,5,1), '0', '1'),SUBSTRING(cups, 6, 4))" ),
                        ]);

                        DB::table('user')
                            ->where('user_id',$user->user_id)
                            ->update([
                                'hazel' => $user->hazel+User::Person_Registered_INVITE_CODE_hazel_mount,
                            ]);
                        $r=new Request();
                        $r['user_id']=$parentUser->user_id;
                        $r['message_id']=5;
                        $r['is_readed']=0;
                        $userMEssage=new UserMessage();
                        $userMEssage->intializeByRequest($r);
                        $userMEssage->store();

                        User::IncreaseDecreaseHazelLuck($user->user_id,User::Person_Registered_INVITE_CODE_hazel_mount,0);
                        $r=new Request();
                        $r['user_id']=$user->user_id;
                        $r['message_id']=6;
                        $r['is_readed']=0;
                        $userMEssage=new UserMessage();
                        $userMEssage->intializeByRequest($r);
                        $userMEssage->store();
                    }


                }
                $user->user_name= $request['user_name'];
                $user->password= Hash::make($request['password']);
                if($request->has('email'))
                    $user->email= $request['email'];
                if( strcmp($request['gender'],"male") == 0)
                    $user->gender=0;
                else
                    $user->gender=1;
                $user->invite_code= rand(0,99).$user->user_id;

                $password = $request->input('password');
                $user_name = $request->input('user_name');
                $message = "دومرتبه به لاکی لرد خوش اومدید";

                try
                {
                    if($request->has('email'))
                         Utility::sendRegisterMailV2($message,$request['email'],$password,$user_name );

                }
                 catch (\Exception $ex)
                {
                    $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
                    $logEvent->store();
                }


                DB::table('user')
                    ->where('user_id',$user->user_id)
                    ->update(['user_name' => $user->user_name,
                                'password' => $user->password,
                                'invite_code' =>$user->invite_code,
                                'email' => $user->email,
                                'gender' => $user->gender,
                                'hazel' => $user->hazel+User::VISITOR_REGISTER_AMMOUNT,
                                'visitor_user_name'=>NULL,
                                'phone_code'=>$request->input('visitor_user_name'),
                        ]);
                $payloadable = [
                    'user_id' => $user->user_id,
                    'user_guid' => $user->user_guid
                ];
                $token = JWTAuth::fromUser($user,$payloadable);

//                //meysam - added in 13970118
//                User::updateToken($token,$user->user_id,$user->user_guid);


                DB::commit();
//                $password = $request->input('password');
//                $user_name = $request->input('user_name');
//                $message = "دومرتبه به لاکی لرد خوش اومدید";
//                Utility::sendRegisterMailV2( $message ,$request['email'], $password, $user_name );
//                $this->dispatch(new SendEmail($message,$request['email'],$password,$user_name))->delay(1);

                return json_encode(['invite_code' => $user->invite_code,'level' => $user->level,'cups' => $user->cups, 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
            }
            else{
                $password = $request['password'];
                $request['password'] =  Hash::make($request['password']);
//                unset($request['visitor_user_name']);

                if( strcmp($request['gender'],"male") == 0){
                    $request['image']='m#1#1#1#1#1#0#0#0#0#0#0#0';
                    $request['gender']=0;
                }else{
                    $request['image']='f#1#1#1#1#1#0#0#0#0#0#0#0';
                    $request['gender']=1;
                }


                $request['phone_code']=$request['visitor_user_name'];
                $request['visitor_user_name']=NULL;
                $this->user->initializeByRequest($request);
                $user_id = $this->user->store();


                for($j=0;$j<5;$j++){
                    $r=new Request();
                    $r['user_id']=$user_id;
                    $r['universal_match_type']=$j;
                    $r['win_count']=0;
                    $r['lost_count']=0;
                    $r['equal_count']=0;
                    $UserUniversalMatchStatistics=new UserUniversalMatchStatistics();
                    $UserUniversalMatchStatistics->intializeByRequest($r);
                    $UserUniversalMatchStatistics->store();
                }


                //////////set message
                $r=new Request();
                $r['user_id']=$user_id;
                $r['message_id']=Message::MESSSAGE_8;
                $r['is_readed']=0;
                $userMEssage=new UserMessage();
                $userMEssage->intializeByRequest($r);
                $userMEssage->store();
                ////update hazel
                $this->user->IncreaseDecreaseHazelLuck($user_id,User::USERNAME_REGISTER_AMMOUNT,0);

                $user = $this->user->find($user_id);
                if($request->has('invite_code'))
                {
                    $parentUser = User::findByInviteCode($request['invite_code']);
                    if($parentUser==null){
                        return json_encode(['error' => RequestResponseAPI::ERROR_INVALID_CODE_OF_INVITE_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
                    }
//                User::IncreaseLuck($parentUser->user_id,User::INVITE_AMOUNT);
                    if($parentUser != null){
                        $parentUser->newQuery()->where(['user_id'=> $parentUser->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.User::Person_introducer_INVITE_CODE_hazel_mount ),
                            'cups' => \DB::raw( "CONCAT(SUBSTRING(cups, 1, 4),REPLACE(SUBSTR(cups,5,1), '0', '1'),SUBSTRING(cups, 6, 4))" ),
                        ]);
                        $r=new Request();
                        $r['user_id']=$parentUser->user_id;
                        $r['message_id']=Message::MESSSAGE_5;
                        $r['is_readed']=0;
                        $userMEssage=new UserMessage();
                        $userMEssage->intializeByRequest($r);
                        $userMEssage->store();

                        User::IncreaseDecreaseHazelLuck($user->user_id,User::Person_Registered_INVITE_CODE_hazel_mount,0);
                        $r=new Request();
                        $r['user_id']=$user->user_id;
                        $r['message_id']=Message::MESSSAGE_6;
                        $r['is_readed']=0;
                        $userMEssage=new UserMessage();
                        $userMEssage->intializeByRequest($r);
                        $userMEssage->store();

                        $logEvent = new LogEvent(null, "ثبت نام با کد دعوت", " خبری - دعوت "." دعوت کننده: " . $parentUser->user_id ." جایزه: " .User::Person_introducer_INVITE_CODE_hazel_mount." دعوت شونده: " . $user->user_id ." جایزه: " .User::Person_Registered_INVITE_CODE_hazel_mount);
                        $logEvent->store();
                    }


                }

                $payloadable = [
                    'user_id' => $user->user_id,
                    'user_guid' => $user->user_guid
                ];

                $token = JWTAuth::fromUser($user,$payloadable);

                $request['api_token'] = $token;
                $request['invite_code'] = rand(0,99).$user->user_id;

                $user_name = $request['user_name'];
                $message = " به لاکی لرد خوش اومدید";
                try
                {
                    if($request->has('email'))
                        Utility::sendRegisterMailV2($message ,$request->input('email'), $password, $user_name );
                }
                catch (\Exception $ex)
                {
                    $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
                    $logEvent->store();
                }
                $this->user->edit($request);
                DB::commit();
                return json_encode(['level' => $user->level,'cups' => $user->cups, 'invite_code'=> $request['invite_code'], 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);
            }


        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_REGISTER_USER]);

        }
    }

    public function apiVisitorRegister(Request $request){


        // Validate the request...
        if ( !$request->has('tag') || !$request->has('gender') ||
            !$request->has('visitor_user_name') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);
        }

        /////////////////////////
        try{
            if($request['gender']=='male'){
                $request['image']='m#1#1#1#1#1#0#0#0#0#0#0#0';
                $request['gender']='0';
            }
            else{
                $request['image']='f#1#1#1#1#1#0#0#0#0#0#0#0';
                $request['gender']='1';
            }

            $this->user->initializeByRequest($request);
            $existUser=$this->user->existByVisitorUserName($request->input('visitor_user_name'),null,null);
//            if(User::countOfUserDevice($request['visitor_user_name'])>=User::REGISTER_COUNT){
//                return json_encode(['error' => RequestResponseAPI::ERROR_EXCEED_REGISTER_COUNT, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);
//            }
            if($existUser){
//                $changeUser = User::find($existUser->user_id);
//                $changeUser->image = (string)$request['image'];
//                $changeUser->gender = (string)$request['gender'];
//                $changeUser->save();
                if($request['gender']=='0'){
                    User::where('user_id', $existUser->user_id)
                        ->update([
                            'gender' => 0,
                            'image' => $request['image'],
                        ]);
                }else{
                    User::where('user_id', $existUser->user_id)
                        ->update([
                            'gender' => 1,
                            'image' => $request['image'],
                        ]);
                }

//                if($existUser->hasAttribute('user_name'))
                if($existUser->user_name != null)
                {
                    User::removeVisitorUserName($request->input('visitor_user_name'));
                    $this->user->visitor_user_name=$request->input('visitor_user_name');


                    $user_id = $this->user->store();
//                    $user = $this->user->findById($user_id);
                    $user = DB::table('user')
                        ->select('user_id','user_guid','visitor_user_name','user_name','invite_code','email','tel','birth_date','gender', 'hazel','luck','level','cups','image','friends_count','chat_allow as allowChat' )
                        ->where('user_name', $request['user_name'])
                        ->first();
                    $payloadable = [
                        'user_id' => $user->user_id,
                        'user_guid' => $user->user_guid
                    ];
                    $token = JWTAuth::fromUser($user,$payloadable);

                    $request['api_token'] = $token;
                    $user->invite_code = $request['invite_code'] = rand(0,99).$user->user_id;
                    $this->user->edit($request);

                    // ******************** set mwssage 10 for user
                    // ******************** set mwssage 10 for user
                    // ******************** set mwssage 10 for user
                    $req=new Request();
                    $req['user_id']=$user_id;
                    $req['message_id']=Message::MESSSAGE_10;
                    $req['is_readed']=0;
                    $user_message=new UserMessage();
                    $user_message->intializeByRequest($req);
                    $user_message->store();
                    //***********************end of set message 10
                    //***********************end of set message 10
                    //***********************end of set message 10

                    // ******************** set mwssage 8 for user
                    // ******************** set mwssage 8 for user
                    // ******************** set mwssage 8 for user
                    $r=new Request();
                    $r['user_id']=$user_id;
                    $r['message_id']=Message::MESSSAGE_8;
                    $r['is_readed']=0;
                    $userMEssage=new UserMessage();
                    $userMEssage->intializeByRequest($r);
                    $userMEssage->store();
                    //***********************end of set message 8
                    //***********************end of set message 8
                    //***********************end of set message 8

                    return json_encode(['user'=>$user,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);

                }
                else
                {

                    //        $list_users_requested_friendship=

                    $UserFriends = DB::table('user_friend')
                        ->where([
                            ['user_1_id', 'like', $existUser->user_id],
                            ['deleted_at', '=', null],
                        ])
                        ->orWhere([
                            ['user_2_id', 'like', $existUser->user_id],
                            ['deleted_at', '=', null],
                        ])
                        ->get();

                    $a=array();
                    for($index=0;$index<count($UserFriends);$index++){
                        if ($UserFriends[$index]->user_1_id==$existUser->user_id){
                            array_push($a,$UserFriends[$index]->user_2_id);
                        }elseif ($UserFriends[$index]->user_2_id==$existUser->user_id){
                            array_push($a,$UserFriends[$index]->user_1_id);
                        }
                    }

                    $users = DB::table('user')
                        ->select('user_id','visitor_user_name','user_name', 'hazel','luck','image','level','cups','friends_count','chat_allow AS allowChat')
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
                        if ($UserFriends[$index]->user_1_id==$existUser->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_NORMAL ){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                                    array_push($list_users_requested_friendship,$users[$index2]);
                                    break;
                                }
                        }elseif ($UserFriends[$index]->user_2_id==$existUser->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_NORMAL){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                                    array_push($list_users_requested_friendship,$users[$index2]);
                                    break;
                                }
                        }
                        ///end of set $list_users_requested_friendship

                        ///2 for set $list_users_received_friendship_requests
                        if ($UserFriends[$index]->user_1_id==$existUser->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_NORMAL ){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                                    array_push($list_users_received_friendship_requests,$users[$index2]);
                                    break;
                                }
                        }elseif ($UserFriends[$index]->user_2_id==$existUser->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_REQUESTED && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_NORMAL){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                                    array_push($list_users_received_friendship_requests,$users[$index2]);
                                    break;
                                }
                        }
                        ///end of set $list_users_received_friendship_requests

                        ///3 for set $list_users_friends
                        if ($UserFriends[$index]->user_1_id==$existUser->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED ){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                                    array_push($list_users_friends,$users[$index2]);
                                    break;
                                }
                        }elseif ($UserFriends[$index]->user_2_id==$existUser->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_ACCEPTED){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                                    array_push($list_users_friends,$users[$index2]);
                                    break;
                                }
                        }
                        ///end of set $list_users_friends

                        ///4 for set $list_users_blocked
                        if ($UserFriends[$index]->user_1_id==$existUser->user_id && $UserFriends[$index]->user_1_status==UserFriend::USER_FRIEND_STATUS_BLOCKED  ){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_2_id){
                                    array_push($list_users_blocked,$users[$index2]);
                                    break;
                                }
                        }elseif ($UserFriends[$index]->user_2_id==$existUser->user_id && $UserFriends[$index]->user_2_status==UserFriend::USER_FRIEND_STATUS_BLOCKED ){
                            for($index2=0;$index2<count($users);$index2++)
                                if($users[$index2]->user_id==$UserFriends[$index]->user_1_id){
                                    array_push($list_users_blocked,$users[$index2]);
                                    break;
                                }
                        }
                        ///end of set $list_users_blocked
                    }

//                    self::generate_100_register_fake_user($request);
//                    $user = $this->user->findById($existUser->user_id);
                    $user = DB::table('user')
                        ->select('user_id','user_guid','visitor_user_name','user_name','invite_code','email','tel','birth_date','gender', 'hazel','luck','level','cups','image','friends_count','chat_allow as allowChat' )
                        ->where('visitor_user_name', $request['visitor_user_name'])
                        ->first();
                    $payloadable = [
                        'user_id' => $user->user_id,
                        'user_guid' => $user->user_guid
                    ];
                    $token = JWTAuth::fromUser($user,$payloadable);
//                //meysam - added in 13970118
//                User::updateToken($token,$user->user_id,$user->user_guid);

                    return json_encode(['user'=>$user,'list_users_requested_friendship'=>$list_users_requested_friendship, 'list_users_received_friendship_requests'=>$list_users_received_friendship_requests, 'list_users_friends'=>$list_users_friends, 'list_users_blocked'=>$list_users_blocked,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);

                }

               }
            else{
                $this->user->visitor_user_name=$request->input('visitor_user_name');
                $this->user->phone_code=$request->input('visitor_user_name');

                $user_id = $this->user->store();

                for($j=0;$j<5;$j++){
                    $r=new Request();
                    $r['user_id']=$user_id;
                    $r['universal_match_type']=$j;
                    $r['win_count']=0;
                    $r['lost_count']=0;
                    $r['equal_count']=0;
                    $UserUniversalMatchStatistics=new UserUniversalMatchStatistics();
                    $UserUniversalMatchStatistics->intializeByRequest($r);
                    $UserUniversalMatchStatistics->store();
                }

                // ******************** set mwssage 10 for user
                // ******************** set mwssage 10 for user
                // ******************** set mwssage 10 for user
                    $req=new Request();
                    $req['user_id']=$user_id;
                    $req['message_id']=Message::MESSSAGE_10;
                    $req['is_readed']=0;
                    $user_message=new UserMessage();
                    $user_message->intializeByRequest($req);
                    $user_message->store();
                //***********************end of set message 10
                //***********************end of set message 10
                //***********************end of set message 10

                // ******************** set mwssage 8 for user
                // ******************** set mwssage 8 for user
                // ******************** set mwssage 8 for user
                $r=new Request();
                $r['user_id']=$user_id;
                $r['message_id']=Message::MESSSAGE_8;
                $r['is_readed']=0;
                $userMEssage=new UserMessage();
                $userMEssage->intializeByRequest($r);
                $userMEssage->store();
                //***********************end of set message 8
                //***********************end of set message 8
                //***********************end of set message 8
//                $user = $this->user->findById($user_id);
                $user = DB::table('user')
                    ->select('user_id','user_guid','visitor_user_name','user_name','invite_code','email','tel','birth_date','gender', 'hazel','luck','level','cups','image','friends_count','chat_allow as allowChat' )
                    ->where('visitor_user_name', $request['visitor_user_name'])
                    ->first();
                $payloadable = [
                    'user_id' => $user->user_id,
                    'user_guid' => $user->user_guid
                ];
                $token = JWTAuth::fromUser($user,$payloadable);

                 $request['api_token'] = $token;
                $user->invite_code = $request['invite_code'] = rand(0,99).$user->user_id;
                $this->user->edit($request);


                return json_encode(['user'=>$user,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);
            }

        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_VISITOR_REGISTER_USER]);
        }
    }

    public function apiEdit(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);


        // Validate the request...
        $request->request->remove('score');
        if ( !$request->has('tag') || !$request->has('birth_date') ||
            !$request->has('email') ||
            !$request->has('user_id') || !$request->has('user_guid')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_EDIT_USER]);

        }
        if (User::existByEmail($request->input('email'),true,$request->input('user_id'))) {
            return json_encode(['error' => RequestResponseAPI::ERROR_EMAIL_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_EDIT_USER]);
        }


//        $file = null;
//        if ($request->input('fileLogo') != null) {
//
//            $size = (int) (strlen(rtrim($request->input('fileLogo'), '=')) * 3 / 4);
//            if ($size > 200000) {
//                return json_encode(['error' => RequestResponseAPI::ERROR_INVALID_FILE_SIZE_CODE, 'tag' => RequestResponseAPI::TAG_EDIT_USER]);
//            }
//
//            //decode base64 string
//            $file = base64_decode($request->input('fileLogo'));
////                if (File::size($file) > 200000) {
////                    return json_encode(['error' => RequestResponseAPI::ERROR_INVALID_FILE_SIZE_CODE, 'tag' => RequestResponseAPI::TAG_EDIT_COMPANY]);
////                }
//            $allowed = array('gif', 'png', 'jpg', 'jpeg', 'bmp', 'svg');
////                $ext = $file->getClientOriginalExtension();
//
//
//            $f = finfo_open();
//
//            $mime = finfo_buffer($f, $file, FILEINFO_MIME_TYPE);
//            $split = explode( '/', $mime );
//            $ext = $split[1];
//
//            if (!in_array($ext, $allowed)) {
//                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_EDIT_USER]);
//            }
//        }

        try {
            $this->user->initializeByRequest($request);
            $this->user->edit($request);
//            if ($file != null) {
//                $this->user->API_UpdateAvatarOfUser($this->user->user_id, $file);
//            }
            return json_encode([ 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_EDIT_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INDEX_USER]);

        }



    }

    public function apiRank(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('type') || !$request->has('range')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_USER_RANK]);

        }
        ////////////////////////////////////////////

        try {
                //////if type==1 return top20 users
                //////if type==1 return top20 users
                //////if type==1 return top20 users
                if($request['type']==User::USER_RANK_TYPE_LUCK && $request['range']==User::RANGE_WHOLE){

                    DB::select("SET @rownum = 0;");
                    $query_for_getQuestion="(SELECT `level`,`cups`,`user_name`, `visitor_user_name`, `luck`,`gender`,`user_id`,`image`,FIND_IN_SET( `luck`, ( SELECT GROUP_CONCAT( `luck` ORDER BY `luck` DESC ) FROM user ) ) AS rank FROM `user` 
                                            ORDER by `luck` DESC LIMIT 20 ) 
                                            UNION 
                                            (SELECT `level`,`cups`,`user_name`, `visitor_user_name`, `luck`,`gender`,`user_id`,`image`, rank 
                                            FROM ( SELECT @rownum := @rownum + 1 AS rank,`user_name`, `visitor_user_name`,`level`,`cups`, `luck`,`gender`,`user_id`,`image` FROM user ORDER BY luck DESC ) AS result 
                                            WHERE `user_id`=:id)";

                    $users = DB::select($query_for_getQuestion, ['id' => $user->user_id,]);


                    for ($index=0;$index < count($users);$index++) {
                        if($users[$index]->user_name==null)
                            $users[$index]->user_name=$users[$index]->visitor_user_name;
                        unset($users[$index]->visitor_user_name);
                    }

                    return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);

                }
                else if($request['type']==User::USER_RANK_TYPE_LUCK && $request['range']==User::RANGE_VICINITY){

                    $paramsObj1 = array(
                        array("se","user", "user_name"),
                        array("se","user", "visitor_user_name"),
                        array("se","user", "luck"),
                        array("se","user", "gender"),
                        array("se","user", "user_id"),
                        array("se","user", "image"),
                        array("se","user", "level"),
                        array("se","user", "cups"),
                    );

                    //join
                    $paramsObj2 = null;
                    //conditions

                    $paramsObj3 = array(
                        array("orderBy",
                            "user.luck", "DESC"
                        ),
                        array("orderBy",
                            "user.user_id", "ASC"
                        ),
                    );
                    $this->user->initialize();
                    $users = $this->user->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);
                    $user_count=count($users);
                    for ($index=0;$index < $user_count;$index++) {
                        $users[$index]->rank=$index+1;
                        if($users[$index]->user_id==$user->user_id)
                            $CurrentUserRank=$users[$index]->rank;
                    }
                    if($user_count<=20){
                        for ($index=0;$index < count($users);$index++) {
                            if($users[$index]->user_name==null)
                                $users[$index]->user_name=$users[$index]->visitor_user_name;
                            unset($users[$index]->visitor_user_name);
                        }
                        return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);
                    }

    //                $min_Rank_User=0;
    //                $max_Rank_User=0;
    //                if($CurrentUserRank-20/2 < 1){
    //                    $min_Rank_User=1;
    //                    $max_Rank_User=20;
    //                }
    //                if($CurrentUserRank+20/2>$user_count){
    //                    $max_Rank_User=$user_count;
    //                    $min_Rank_User=$user_count-20;
    //                }
    //                if($CurrentUserRank+20/2 <= $user_count && $CurrentUserRank-20/2 >= 1){
    //                    $min_Rank_User=$CurrentUserRank-20/2+1;
    //                    $max_Rank_User=$CurrentUserRank+20/2;
    //                }
                    $min_Rank_User=$CurrentUserRank;

                    $paramsObj1 = array(
                        array("se","user", "user_name"),
                        array("se","user", "visitor_user_name"),
                        array("se","user", "luck"),
                        array("se","user", "gender"),
                        array("se","user", "user_id"),
                        array("se","user", "image"),
                        array("se","user", "level"),
                        array("se","user", "cups"),
                    );

                    //join
                    $paramsObj2 = null;
                    //conditions

                    $paramsObj3 = array(
                        array("orderBy",
                            "user.luck", "DESC"
                        ),
                        array("orderBy",
                            "user.user_id", "ASC"
                        ),
                        array("skip",
                            $min_Rank_User-3
                        ),
                        $paramsObj3[] =   array("take",
                            20
                        )
                    );
                    $this->user->initialize();
                    $users = $this->user->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);

                    $users[0]->rank=$min_Rank_User;
                    for ($index=0;$index < count($users);$index++) {
                        if($users[$index]->user_name==null)
                            $users[$index]->user_name=$users[$index]->visitor_user_name;
                        unset($users[$index]->visitor_user_name);
                    }
                    return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);

                }





                else if($request['type']==User::USER_RANK_TYPE_LEVEL && $request['range']==User::RANGE_WHOLE){

                DB::select("SET @rownum = 0;");
                $query_for_getQuestion="(SELECT `level`,`cups`,`user_name`, `visitor_user_name`, `luck`,`gender`,`user_id`,`image`,FIND_IN_SET( `level`, ( SELECT GROUP_CONCAT( `level` ORDER BY `level` DESC ) FROM user ) ) AS rank FROM `user` 
                                            ORDER by `level` DESC LIMIT 20 ) 
                                            UNION 
                                            (SELECT `level`,`cups`,`user_name`, `visitor_user_name`, `luck`,`gender`,`user_id`,`image`, rank 
                                            FROM ( SELECT @rownum := @rownum + 1 AS rank,`user_name`, `visitor_user_name`,`level`,`cups`, `luck`,`gender`,`user_id`,`image` FROM user ORDER BY level DESC ) AS result 
                                            WHERE `user_id`=:id)";

                $users = DB::select($query_for_getQuestion, ['id' => $user->user_id,]);


                for ($index=0;$index < count($users);$index++) {
                    if($users[$index]->user_name==null)
                        $users[$index]->user_name=$users[$index]->visitor_user_name;
                    unset($users[$index]->visitor_user_name);
                }

                return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);

            }





                else if($request['type'] > User::USER_RANK_TYPE_LUCK && $request['range']==User::RANGE_WHOLE){
                    $paramsObj1 = array(
                        array("se","user", "user_name"),
                        array("se","user", "visitor_user_name"),
                        array("se","user_category", "correct_answered_count"),
                        array("se","user", "gender"),
                        array("se","user", "user_id"),
                        array("se","user", "image"),
                        array("se","user", "level"),
                        array("se","user", "cups"),
                    );

                    //join
                     $paramsObj2 = array(
                        array("join",
                            "user_category",
                            array("user_category.user_id", "=", "user.user_id")
                        )
                    );
                    //conditions

                    $paramsObj3 = array(
                        array("orderBy",
                            "user_category.correct_answered_count", "DESC"
                        ),
                        array("orderBy",
                            "user.user_id", "ASC"
                        ),
                        array("where",
                            "user_category.category_id",'=', $request['type']
                        ),
                        $paramsObj3[] =   array("take",
                            '20'
                        )
                    );
                    $this->user->initialize();
                    $users = $this->user->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);
    //                for ($index=0;$index < count($users);$index++) {
    //                    $users[$index]->rank=$index+1;
    //                }

                    //////if type==0 ; first get rank of current user ; then return users of near the current user
                    //////if type==0 ; first get rank of current user ; then return users of near the current user
                    //////if type==0 ; first get rank of current user ; then return users of near the current user
                    for ($index=0;$index < count($users);$index++) {
                        if($users[$index]->user_name==null)
                            $users[$index]->user_name=$users[$index]->visitor_user_name;
                        unset($users[$index]->visitor_user_name);
                    }
                    return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);
                }
                else if($request['type'] > User::USER_RANK_TYPE_LUCK && $request['range']==User::RANGE_VICINITY){
                    //        select company data with
                    $paramsObj1 = array(
                        array("se","user", "user_name"),
                        array("se","user", "visitor_user_name"),
                        array("se","user_category", "correct_answered_count"),
                        array("se","user", "gender"),
                        array("se","user", "user_id"),
                        array("se","user", "image"),
                        array("se","user", "level"),
                        array("se","user", "cups"),
                    );

                    //join
                    $paramsObj2 = array(
                        array("join",
                            "user_category",
                            array("user_category.user_id", "=", "user.user_id")
                        )
                    );
                    //conditions

                    $paramsObj3 = array(
                        array("orderBy",
                            "user_category.correct_answered_count", "DESC"
                        ),
                        array("orderBy",
                            "user.user_id", "ASC"
                        ),
                        array("where",
                            "user_category.category_id",'=', $request['type']
                        ),
                    );
                    $this->user->initialize();
                    $users = $this->user->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);
                    $user_count=count($users);
                    for ($index=0;$index < $user_count;$index++) {
                        $users[$index]->rank=$index+1;
                        if($users[$index]->user_id==$user->user_id)
                            $CurrentUserRank=$users[$index]->rank;
                    }
                    if($user_count<=20){
                        for ($index=0;$index < count($users);$index++) {
                            if($users[$index]->user_name==null)
                                $users[$index]->user_name=$users[$index]->visitor_user_name;
                            unset($users[$index]->visitor_user_name);
                        }
                        return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);
                    }

                    $min_Rank_User=0;
                    $max_Rank_User=0;
                    if($CurrentUserRank-20/2 < 1){
                        $min_Rank_User=1;
                        $max_Rank_User=20;
                    }
                    if($CurrentUserRank+20/2>$user_count){
                        $max_Rank_User=$user_count;
                        $min_Rank_User=$user_count-20;
                    }
                    if($CurrentUserRank+20/2 <= $user_count && $CurrentUserRank-20/2 >= 1){
                        $min_Rank_User=$CurrentUserRank-20/2+1;
                        $max_Rank_User=$CurrentUserRank+20/2;
                    }



                    $paramsObj1 = array(
                        array("se","user", "user_name"),
                        array("se","user", "visitor_user_name"),
                        array("se","user_category", "correct_answered_count"),
                        array("se","user", "gender"),
                        array("se","user", "user_id"),
                        array("se","user", "image"),
                        array("se","user", "level"),
                        array("se","user", "cups"),
                    );

                    //join
                    $paramsObj2 = null;
                    //conditions

                    $paramsObj3 = array(
                        array("orderBy",
                            "user_category.correct_answered_count", "DESC"
                        ),
                        array("orderBy",
                            "user.user_id", "ASC"
                        ),
                        array("skip",
                            $min_Rank_User-1
                        ),
                        $paramsObj3[] =   array("take",
                            20
                        )
                    );
                    $this->user->initialize();
                    $users = $this->user->getFullDetail($paramsObj1, $paramsObj2, $paramsObj3);

                    $users[0]->rank=$min_Rank_User;
                    for ($index=0;$index < count($users);$index++) {
                        if($users[$index]->user_name==null)
                            $users[$index]->user_name=$users[$index]->visitor_user_name;
                        unset($users[$index]->visitor_user_name);
                    }
                    return json_encode(['users' => $users,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_USER_RANK]);
                }

           } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_USER_RANK]);

        }
    }

    public function apiProfile(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_PROFILE_USER]);

        }
        ////////////////////////////////////////////

        try {


            if(isset($request['user_name'])){

                if (strpos($request['user_name'], "Visitor") === 0){
                    $user=User::findByVisitorUsername($request['user_name']);
                }else {
                    $user=User::findByUsername($request['user_name']);
                }
                if($user == null)
                    return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_ITEM_NOT_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_PROFILE_USER]);

                ///get user_universal_match_statistics for profile
                $query_for_getQuestion="SELECT sum(`win_count`) as match_win_count,sum(`lost_count`) as match_lost_count ,sum(`equal_count`) as match_equal_count FROM `user_universal_match_statistics` WHERE user_id=:id";
                $stat = DB::select($query_for_getQuestion, ['id' => $user->user_id,]);
                ///end user_universal_match_statistics for profile

                $categories_information = DB::table('user_category')
                    ->select(DB::raw('category_id,total_answered_count,correct_answered_count,daily_total_answered_count,daily_correct_answered_count,weekly_total_answered_count,weekly_correct_answered_count'))
                    ->where('user_id', '=', $user->user_id)
                    ->whereBetween('category_id', [1, Category::Category_Count])
                    ->get();
                return json_encode(['match_win_count'=>$stat[0]->match_win_count,'match_lost_count'=>$stat[0]->match_lost_count,'match_equal_count'=>$stat[0]->match_equal_count, 'categories_information'=>$categories_information,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_PROFILE_USER]);
            }
            else{
                ///get user_universal_match_statistics for profile
                $query_for_getQuestion="SELECT sum(`win_count`) as match_win_count,sum(`lost_count`) as match_lost_count ,sum(`equal_count`) as match_equal_count FROM `user_universal_match_statistics` WHERE user_id=:id";
                $stat = DB::select($query_for_getQuestion, ['id' => $user->user_id,]);
                ///end user_universal_match_statistics for profile

                $categories_information = DB::table('user_category')
                    ->select(DB::raw('category_id,total_answered_count,correct_answered_count,daily_total_answered_count,daily_correct_answered_count,weekly_total_answered_count,weekly_correct_answered_count'))
                    ->where('user_id', '=', $user->user_id)
                    ->whereBetween('category_id', [1, Category::Category_Count])
                    ->get();
                return json_encode(['match_win_count'=>$stat[0]->match_win_count,'match_lost_count'=>$stat[0]->match_lost_count,'match_equal_count'=>$stat[0]->match_equal_count,'categories_information'=>$categories_information,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_PROFILE_USER]);
            }

            } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_PROFILE_USER]);

        }
    }

    public function apiUpdateProfile(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('image')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PROFILE_USER]);

        }
        ////////////////////////////////////////////

        try {
            $user = User::findById($user->user_id);
            $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                'image' => $request['image']
            ]);
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_PROFILE_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PROFILE_USER]);
        }
    }

    public function generate_100_register_fake_user(Request $request){
        for($index=0;$index<100;$index++){
            $u=new User();
            $u->Random_initializeByRequest($request);
            $u->store();
        }
    }

    public function apiUpdatePhone(Request $request){
    /////////////////////check token
    $token = null;
    if (session('tokenRefreshed'))
        $token = session('token');
    else
        $token = JWTAuth::parseToken()->getToken()->get();
    $user = JWTAuth::parseToken()->authenticate($token);
    //////////////////////////////
    //validation
    if (!$request->has('tag') || !$request->has('phone')) {
        return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PHONE_USER]);

    }
    ////////////////////////////////////////////

    try {
            DB::beginTransaction();
            $CUR_user = User::findById($user->user_id);
            if($CUR_user->tel==null){
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'tel' => $request['phone'],
                    'hazel'=>\DB::raw( 'hazel +'.User::TELL_REWARD ),
                ]);

                $request['user_id']=$user->user_id;
                $request['message_id']=User::MOBILE_REWARD_MESSAGE_ID;
                $request['is_readed']=0;
                $user_message=new UserMessage();
                $user_message->intializeByRequest($request);
                $user_message->store();
            }else{
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                'tel' => $request['phone']
            ]);
            }
            DB::commit();
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_PHONE_USER]);
    } catch (\Exception $ex) {
        DB::rollBack();
        $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
        $logEvent->store();
        return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PHONE_USER]);
    }
}

    public function apiUpdateEmail(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('email')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_EMAIL_USER]);

        }
        if (User::existByEmail($request->input('email'),null,null)) {
            return json_encode(['error' => RequestResponseAPI::ERROR_EMAIL_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_EMAIL_USER]);
        }
        ////////////////////////////////////////////

        try {
            $user = User::findById($user->user_id);
            $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                'email' => $request['email'],
            ]);
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_EMAIL_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_EMAIL_USER]);
        }
    }

    public function apiUpdateBirthDate(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('birth_date')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_BIRTH_DATE_USER]);

        }
        ////////////////////////////////////////////

        try {
            DB::beginTransaction();
            $CUR_user = User::findById($user->user_id);
            if($CUR_user->birth_date==null){
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'birth_date' => $request['birth_date'],
//                    'hazel'=>\DB::raw( 'hazel +'.User::BIRTH_DATE_REWARD ),
                ]);
//
//                $request['user_id']=$user->user_id;
//                $request['message_id']=User::BIRTH_DATE_REWARD_MESSAGE_ID;
//                $request['is_readed']=0;
//                $user_message=new UserMessage();
//                $user_message->intializeByRequest($request);
//                $user_message->store();
            }else{
//                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
//                    'birth_date' => $request['birth_date']
//                ]);
            }
            DB::commit();
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_BIRTH_DATE_USER]);
        } catch (\Exception $ex) {
            DB::rollBack();
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_BIRTH_DATE_USER]);
        }
    }

    public function apiUpdatePassword(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('old_password') || !$request->has('new_password')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PASSWORD_USER]);

        }
        ////////////////////////////////////////////

        try {
            $user = User::findById($user->user_id);
            if (password_verify($request['old_password'], $user->password)) {
//                echo 'valid password.';
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'password' =>Hash::make($request['new_password']),
                ]);
            } else {
                return json_encode(['error' => RequestResponseAPI::ERROR_PASSWORD_MISMATCH_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PASSWORD_USER]);
            }

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_PASSWORD_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_PASSWORD_USER]);
        }
    }

    public function apiDecreaseHazelLuck(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('hazel_amount') || !$request->has('luck_amount')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_DECREASE_HAZEL_LUCK_USER]);

        }
        ////////////////////////////////////////////

        try {
            $user = User::DecreaseHazelLuck($user->user_id,$request['hazel_amount'],$request['luck_amount']);


            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_DECREASE_HAZEL_LUCK_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_DECREASE_HAZEL_LUCK_USER]);
        }
    }

    public function apiChangeHazelLuck(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('hazel') || !$request->has('luck')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_CHANGE_HAZEL_LUCK_USER]);

        }
        ////////////////////////////////////////////

        try {
            $user = User::ChangeHazelLuck($user->user_id,$request['hazel'],$request['luck']);


            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_CHANGE_HAZEL_LUCK_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_CHANGE_HAZEL_LUCK_USER]);
        }
    }

    public function apiUpdateGender(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('gender') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_GENDER_USER]);

        }
        ////////////////////////////////////////////

        try {
            if($request['gender']=='male')
                $gender=User::MAlE;
            else
                $gender=User::FEMALE;


            $Currentuser = $this->user->findById($user->user_id);

            if($Currentuser->gender!=$gender){
                if($Currentuser->gender==User::MAlE){
                    $Currentuser->gender=User::FEMALE;
                    $Currentuser->image='f#1#1#1#1#1#0#0#0#0#0#0#0';
                    $Currentuser->save();
                }
                else{
                    $Currentuser->gender=User::MAlE;
                    $Currentuser->image='m#1#1#1#1#1#0#0#0#0#0#0#0';
                    $Currentuser->save();
                }
            }

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_GENDER_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_GENDER_USER]);
        }
    }

    public function apiForgetPassword(Request $request){

        ////////////////////////////////////////////


        try {
            $data_to_hash = $request['email'].'#'.$request['new_password'].'#'.Carbon::now();

             $hashed_data_in_link = openssl_encrypt(strval($data_to_hash),"AES-128-ECB",User::PASSWORD_RECOVERY_ENCRYPTION_KEY);

//            Utility::sendRecoveryMail($request['email'],$hashed_data_in_link,$request['new_password']);
            $user = User::findByEmail($request['email']);
            if($user != null){
                try
                {
                    Utility::sendRecoveryMailV2($hashed_data_in_link,$request['email'],$request['new_password']);
                }
                catch (\Exception $ex)
                {
                    $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
                    $logEvent->store();
                }
            }

//            $this->dispatch(new SendEmail($hashed_data_in_link,$request['email'],$request['new_password'],null));

            if($user != null){
                $r=new Request();
                $r['user_id']=$user->user_id;
                $r['message_id']=9;
                $r['is_readed']=0;
                $userMEssage=new UserMessage();
                $userMEssage->intializeByRequest($r);
                $userMEssage->store();
            }
            return json_encode([ 'error' => 0, 'tag' => RequestResponseAPI::TAG_FORGET_PASSWORD_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_FORGET_PASSWORD_USER]);
        }
    }

    public function apiRecoverPassword(Request $request){

        ////////////////////////////////////////////


        try {

            $data_to_hash = openssl_decrypt($request['messageText'],"AES-128-ECB",User::PASSWORD_RECOVERY_ENCRYPTION_KEY);
            $a=explode("#",$data_to_hash);

            $email=$a[0];
            $new_password=$a[1];
            $date=$a[2];
            $carbonDate=new Carbon();
            $carbonDate=$date;
//            if(Carbon::now()->subDay(2)<$carbonDate){
                $user = User::findByEmail($email);

                if($user != null){
                    $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                        'password' =>Hash::make($new_password),
                    ]);

//
//                    $r=new Request();
//                    $r['user_id']=$user->user_id;
//                    $r['message_id']=9;
//                    $r['is_readed']=0;
//                    $userMEssage=new UserMessage();
//                    $userMEssage->intializeByRequest($r);
//                    $userMEssage->store();
                }

//            }

            $message = trans('messages.msg_SuccessForgetPassword');
//            return view('admin.index', ['message' => $message]);
//            return redirect()->route('home', ['message' => $message]);
            return view('welcome', ['message' => $message]);
//            return json_encode([ 'error' => 0, 'tag' => RequestResponseAPI::TAG_FORGET_PASSWORD_USER]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            $message = trans('messages.msg_ErrorForgetPassword');
//            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_FORGET_PASSWORD_USER]);
//            return view('admin.index', ['message' => $message]);
//            return redirect()->route('home', ['message' => $message]);
            return view('welcome', ['error' => $message]);


        }
    }

    public function apiInfo(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('user_name') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_INFO_USER]);

        }
        ////////////////////////////////////////////


        try {

            if (strpos($request['user_name'], "Visitor") === 0){
                $user2=User::findByVisitorUsername($request['user_name']);
            }else {
                $user2=User::findByUsername($request['user_name']);
            }
//            $user=User::findByUsername($request['user_name']);
//            $categories_information = DB::table('user_category')
//                ->select(DB::raw('category_id,total_answered_count,correct_answered_count,daily_total_answered_count,daily_correct_answered_count,weekly_total_answered_count,weekly_correct_answered_count'))
//                ->where('user_id', '=', $user->user_id)
//                ->whereBetween('category_id', [1, Category::Category_Count+1])
//                ->get();
            if($user2== null){
                return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INFO_USER]);
            }

            if($user != null)
                return json_encode(['image'=>$user2->image,'friends_count'=>$user2->friends_count,'level'=>$user2->level,'cups'=>$user2->cups,'user_id'=>$user2->user_id, 'token' => $token,'error' => 0,'hazel'=>$user2->hazel,'luck'=>$user2->luck,'allowChat'=>$user2->chat_allow, 'tag' => RequestResponseAPI::TAG_INFO_USER]);
            else
                return json_encode(['image'=>$user2->image,'friends_count'=>$user2->friends_count,'token' => $token,'error' => RequestResponseAPI::ERROR_ITEM_NOT_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_INFO_USER]);


        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INFO_USER]);
        }



    }

    public function ValidateUserForm(Request $request){

        if($request['name']=='name' || $request['name']=='family') {

            if ($request['text']=="") {
                $errors = "وارد کردن این نام الزامی است . . . ";
                return response()->json([
                    'success' => false,
                    'message' => $errors
                ]);
            }

            if (!preg_match('/^[^\x{600}-\x{6FF}]+$/u', str_replace("\\\\","",$request['text']))){
                return response()->json([
                    'success' => true
                ]);
            }else{
                $errors = "فقط مجاز هستید که از حروف پارسی استفاده کنید";
                return response()->json([
                    'success' => false,
                    'message' => $errors
                ]);
            }


        }

    }

    public function loginlToMessageManager(Request $request){

        try {

//            return json_encode($request['national_code'].'         '.$request['password']);
            $flag=false;
            for ($index=0;$index<count(User::identifypass);$index++)
                if(User::identifypass[$index][0]==trim($request['national_code'])  and User::identifypass[$index][1]==trim($request['password']))
                    $flag=true;
            if ($flag) {
                $message=DB::table('message')->select('message_id','message_guid','title', 'description')->get();
                $contact_us= DB::table('contact_us')
//                    ->join('user', 'user.user_id', '=', 'contact_us.user_id')
                    ->select('contact_us.contact_us_id','contact_us.contact_us_guid','contact_us.user_id', 'contact_us.title','contact_us.description','contact_us.email','contact_us.tel')
                    ->where('contact_us.is_readed','0')
                    ->get();

                return view('ContactUs.index', ['message' => $message,'contact_us' => $contact_us]);

            }else {
                $message = "نام کاربری یا رمز عبور اشتباه می باشد";
                return redirect()->back()->with('messages', $message);
            }



        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_INFO_USER]);
        }

    }

    public function apiSearch(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('skip')  ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_SEARCH_USER]);
        }
        try {
            $users='';
            if(trim($request['user_name'])=='' || trim($request['user_name'])==null)
                return json_encode(['users'=>$users,'error' => 0, 'tag' => RequestResponseAPI::TAG_SEARCH_USER,'token'=>$token]);
            $users = DB::table('user')
                ->select('user_id', 'user_name','visitor_user_name', 'image','hazel', 'luck','level', 'cups','friends_count','chat_allow AS allowChat')
                ->where('user_name', 'like', '%'.$request['user_name'].'%')
                ->where('user_name', '<>', null)
                ->where('user_name', '<>', $user->user_name)
                ->skip($request['skip'])
                ->take(10)->get();


            for ($index=0;$index < count($users);$index++) {
                if($users[$index]->user_name==null)
                    $users[$index]->user_name=$users[$index]->visitor_user_name;
                unset($users[$index]->visitor_user_name);
            }
            return json_encode(['users'=>$users,'error' => 0, 'tag' => RequestResponseAPI::TAG_SEARCH_USER,'token'=>$token]);


        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_SEARCH_USER]);
        }

    }

    public function add_statistics_user(){
        ini_set('max_execution_time', 9200);
        $users = DB::table('user')->select('user_id')->get();
        for($index=0;$index<count($users);$index++){


                for($j=0;$j<5;$j++){
                    $r=new Request();
                    $r['user_id']=$users[$index]->user_id;
                    $r['universal_match_type']=$j;
                    $r['win_count']=0;
                    $r['lost_count']=0;
                    $r['equal_count']=0;
                    $UserUniversalMatchStatistics=new UserUniversalMatchStatistics();
                    $UserUniversalMatchStatistics->intializeByRequest($r);
                    $UserUniversalMatchStatistics->store();
                }





        }
    }

    public function apiChangeLevel(Request $request)
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
        if (!$request->has('tag') || !$request->has('value') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_CHANGE_LEVEL_USER]);

        }
        ////////////////////////////////////////////


        try {
                $userUpdate= User::find($user->user_id);
                $userUpdate->level+=$request['value'];
                $userUpdate->save();


                return json_encode(['level'=>$userUpdate->level, 'token' => $token,'error' => 0, 'tag' => RequestResponseAPI::TAG_CHANGE_LEVEL_USER]);

        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_CHANGE_LEVEL_USER]);
        }
    }

    public function apiCups(Request $request)
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
        if (!$request->has('tag') || !$request->has('indexes') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_CUPS_USER]);

        }

        try {

            $cupids=explode(',',$request['indexes']);



            $userUpdateCups = User::find($user->user_id);
            $test=$userUpdateCups->cups;

            for($index=0;$index<count($cupids);$index++) {
                $test[$cupids[$index]]=1;
            }

            $userUpdateCups->cups=$test;
            $userUpdateCups->save();
            return json_encode([ 'token' => $token,'error' => 0, 'tag' => RequestResponseAPI::TAG_CUPS_USER]);

        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_CUPS_USER]);
        }

    }

    public function apiUpdateAllowChat(Request $request)
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
        if (!$request->has('tag') || !$request->has('allow') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_ALLOW_CHAT_USER]);

        }

        try {
            $userUpdateCups = User::find($user->user_id);
            $userUpdateCups->chat_allow=(integer)$request['allow'];
            $userUpdateCups->save();
            return json_encode([ 'token' => $token,'error' => 0, 'tag' => RequestResponseAPI::TAG_UPDATE_ALLOW_CHAT_USER]);

        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UPDATE_ALLOW_CHAT_USER]);
        }

    }



}