<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:36 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;
use Log;
use DB;

class UserFriend extends Model
{
    use SoftDeletes;


    const USER_FRIEND_STATUS_NORMAL=0;//user didn't do anything...default value
    const USER_FRIEND_STATUS_REQUESTED=1;
    const USER_FRIEND_STATUS_ACCEPTED=2;
    const USER_FRIEND_STATUS_BLOCKED=3;
    const USER_FRIEND_STATUS_DECLINED=4;
    const USER_FRIEND_STATUS_DELETED=5;
    //
    protected $table = 'user_friend';
    protected $primaryKey = 'user_friend_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;


    protected $fillable = ['user_1_id,user_2_id,user_1_status,user_2_status'];

    public function intialize()
    {
        $this -> user_friend_id = null;
        $this -> user_friend_guid = null;
        $this -> user_1_id = null;
        $this -> user_2_id = null;
        $this -> user_1_status = null;
        $this -> user_2_status = null;
    }

    public function intializeByRequest(Request $request)
    {
        $this -> user_friend_id = $request ->input('user_friend_id');
        $this -> user_friend_guid = $request ->input('user_friend_guid');
        $this -> user_1_id = $request ->input('user_1_id');
        $this -> user_2_id = $request ->input('user_2_id');
        if(!is_null($request ->input('user_1_status')))
            $this -> user_1_status = $request ->input('user_1_status');
        if(!is_null($request ->input('user_2_status')))
            $this -> user_2_status = $request ->input('user_2_status');

    }

    public function findFriendsByUserId($user_id)
    {
        $query = $this->newQuery();
        $query->where('user_1_id', 'like', $user_id);
        $query->orWhere('user_2_id', 'like', $user_id);

        $UserFriends = $query->get();
        return $UserFriends;
    }

    public function store()
    {
        $this->user_friend_guid = uniqid('',true);
        $this->save();

    }

    public function User()
    {
        return $this->belongsTo(User::class);
    }

    public function checkExist($user1,$user2){
        $query = $this->newQuery();
        $query->where([
            ['user_1_id', 'like', $user1],
            ['user_2_id', 'like', $user2],
        ]);
        $query->orWhere([
            ['user_1_id', 'like', $user2],
            ['user_2_id', 'like', $user1],
        ]);

        $UserFriends = $query->first();
        return $UserFriends;
    }

    public function index($status_code,$user_id){
        $query = $this->newQuery();
//        $query->whereIn("risk", $json->risks);
//        $query->select('users.*', 'contacts.phone', 'orders.price');
        $query->where([
            ['user_1_id', 'like', $user_id],
            ['user_1_status', 'like', $status_code],
        ]);
        $query->orWhere([
            ['user_2_id', 'like', $user_id],
            ['user_2_status', 'like', $status_code],
        ]);

        $UserFriends = $query->get();

        $a=array();
        for($index=0;$index<count($UserFriends);$index++){
            if ($UserFriends[$index]->user_1_id==$user_id){
                array_push($a,$UserFriends[$index]->user_2_id);
            }elseif ($UserFriends[$index]->user_2_id==$user_id){
                array_push($a,$UserFriends[$index]->user_1_id);
            }
        }

        $users = DB::table('user')
            ->select('user_id','visitor_user_name','user_name', 'hazel','luck','image','level','cups','friends_count','chat_allow as allowChat')
            ->whereIn('user_id', $a)
            ->get();
        for ($index=0;$index < count($users);$index++) {
            if($users[$index]->user_name==null)
                $users[$index]->user_name=$users[$index]->visitor_user_name;
            unset($users[$index]->visitor_user_name);
        }
        return $users;
    }

    public function Recommend($user_id){
        $user = DB::table('user')
            ->select('user_id','visitor_user_name','user_name', 'hazel','luck','image','level','cups','chat_allow as allowChat')
            ->where('user_id',$user_id )
            ->first();
        $user_friend=DB::table('user_friend')
            ->select('user_1_id','user_2_id')
            ->where('user_1_id',$user->user_id )
            ->orWhere('user_2_id',$user->user_id )
            ->get();
        $notExisrtUser=[];
        for($index=0;$index<count($user_friend);$index++){
            if($user_friend[$index]->user_1_id==$user->user_id)
                array_push($notExisrtUser,$user_friend[$index]->user_2_id);
            elseif($user_friend[$index]->user_2_id==$user->user_id)
                array_push($notExisrtUser,$user_friend[$index]->user_1_id);
        }

        $users = DB::table('user')
            ->select('user_id','visitor_user_name','user_name', 'hazel','luck','image','level','cups','chat_allow as allowChat')
            ->where('level','<', $user->level+5000)
            ->where('level','>', $user->level-5000)
            ->where('user_id','<>', $user->user_id)
//            ->where('password','<>', 'zxcvbnm')
            ->whereNotIn('user_id',$notExisrtUser)
            ->Where('visitor_user_name',NULL )
            ->orderByRaw("RAND()")
            ->take(rand(3,10))
            ->get();
        for ($index=0;$index < count($users);$index++) {
            if($users[$index]->user_name==null)
                $users[$index]->user_name=$users[$index]->visitor_user_name;
            unset($users[$index]->visitor_user_name);
        }
        return $users;
    }

    public function countOfRequest($user_id){
        $query = $this->newQuery();
        $query->where([
            ['user_1_id', 'like', $user_id],
            ['user_2_status', '=', UserFriend::USER_FRIEND_STATUS_REQUESTED],
            ['user_1_status', '=', UserFriend::USER_FRIEND_STATUS_NORMAL],
            ['deleted_at', '=', null],
        ]);
        $query->orWhere([
            ['user_2_id', 'like', $user_id],
            ['user_1_status', '=', UserFriend::USER_FRIEND_STATUS_REQUESTED],
            ['user_2_status', '=', UserFriend::USER_FRIEND_STATUS_NORMAL],
            ['deleted_at', '=', null],
        ]);

        $UserFriends = $query->get()->count();
        return $UserFriends;
    }
}