<?php

namespace App;


use Carbon\Carbon;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;
use Log;
use DB;
use File;
use Hash;

class User extends Authenticatable
{
    use Notifiable;
    use SoftDeletes;

    const INVITE_AMOUNT = 10;//hazle
    const USER_RANK_TYPE_LUCK='0';
    const USER_RANK_TYPE_LEVEL='1';


    const  RANGE_WHOLE='0';
    const  RANGE_VICINITY='1';
    const  RANGE_DAILY='2';
    const  RANGE_WEEKLY='3';

    const BIRTH_DATE_REWARD=200;
    const TELL_REWARD=500;
    const MOBILE_REWARD_MESSAGE_ID='4';
    const BIRTH_DATE_REWARD_MESSAGE_ID=3;

    const VISITOR_REGISTER_AMMOUNT=2000;
    const USERNAME_REGISTER_AMMOUNT=2000;

    const Person_introducer_INVITE_CODE_hazel_mount=1500;
    const Person_Registered_INVITE_CODE_hazel_mount=500;

    const REGISTER_COUNT=3;

    const MAlE=0;
    const FEMALE=1;


    const PASSWORD_RECOVERY_ENCRYPTION_KEY = "meysamhoomanamir@fardan7e.ir";

    const identifypass=[
        ['hooman','zxcvbnm'],
        ['hooman2','zxcvbnm'],
        ['hooman3','اzxcvbnm'],
    ];

    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'user';
    protected $primaryKey = 'user_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
         'email', 'password', 'invite_code', 'luck','hazel','tel',
        'gender', 'user_name'

    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    //these will not be in seassion...
    protected $hidden = [
        'remember_token', 'password',
        'egg_date_time', 'egg_score', 'api_token','phone_code',
        'created_at', 'updated_at', 'is_active', 'deleted_at',
    ];

    /**
     * Get all of the tasks for the user.
     */
    public function messages()
    {
        return $this->hasMany(Message::class);
    }

    /**
     * Get the tracks for the user.
     */
    public function userDraws()
    {
        return $this->hasMany(UserDraw::class);
    }

    public function userQuestionReportss()
    {
        return $this->hasMany(UserQuestionReport::class);
    }
    public function userFriends()
    {
        return $this->hasMany(UserFriend::class);
    }

    protected static function boot() {
        parent::boot();

        static::deleting(function($user) {
            $user->messages()->delete();
            $user->userDraws()->delete();
            $user->UserQuestionReport()->delete();
            $user->userFriends()->delete();
        });
    }

    public function initialize()
    {

            $this-> user_id = null;
            $this-> user_guid = null;
            $this-> user_name = null;
            $this-> visitor_user_name = null;
            $this-> password = null;
            $this-> invite_code = null;
            $this->luck=null;
            $this->hazel=0;
            $this-> email = null;
            $this-> tel = null;
            $this-> gender = null;
            $this-> image = null;
            $this->birth_date=null;
            $this-> egg_date_time = null;
            $this-> one_signal_player_id = null;
            $this-> egg_score = null;
            $this-> is_active = null;
            $this->visitor_user_name = null;
            $this->deleted_at = null;

    }

    public function initializeByRequest(Request $request)
    {
        $this-> user_id = $request ->input('user_id');
        $this-> user_guid = $request ->input('user_guid');
        $this-> user_name = $request ->input('user_name');
        $this-> visitor_user_name = $request ->input('visitor_user_name');
        $this-> password = $request ->input('password');
        $this-> invite_code = $request ->input('invite_code');
        $this-> hazel = $request ->input('hazel');
        $this-> luck = $request ->input('luck');
        $this-> email = $request ->input('email');
        $this-> birth_date = $request ->input('birth_date');
        $this-> tel = $request ->input('tel');
        $this-> gender = $request ->input('gender');
        $this-> image = $request ->input('image');
        $this-> egg_date_time = "2017-10-01 08:21:41";
        $this-> one_signal_player_id = $request ->input('one_signal_player_id');
        $this-> egg_score = $request ->input('egg_score');
        $this -> is_active = $request ->input('is_active');
        $this -> phone_code = $request['phone_code'];

        $this->deleted_at = null;

    }

    public function Random_initializeByRequest(Request $request)
    {
        $this-> user_id = $request ->input('user_id');
        $this-> user_guid = $request ->input('user_guid');
        $this-> user_name = $request ->input('user_name').rand ( 10000 ,100000);
        $this-> visitor_user_name = $request ->input('visitor_user_name').rand ( 10000 ,100000);
        $this-> password = $request ->input('password');
        $this-> invite_code = $request ->input('invite_code');
        $this-> hazel = $request ->input('hazel');
        $this-> luck = $request ->input('luck');
        $this-> email = $request ->input('email').rand ( 10000 ,100000);
        $this-> birth_date = $request ->input('birth_date');
        $this-> tel = $request ->input('tel');
        $this-> gender = $request ->input('gender');
        $this-> image = $request ->input('image');
        $this-> egg_date_time = "2017-10-01 08:21:41";
        $this-> egg_score = $request ->input('egg_score');
        $this-> is_active = $request ->input('is_active');

        $this->deleted_at = null;

    }

    public function store(){

        $this->user_guid = uniqid('',true);
        $this->is_active = 1;
        $this->luck=0;
        $this->hazel += self::VISITOR_REGISTER_AMMOUNT;
        $this->invite_code = 0;
        $this->egg_score = 0;
        if( $this->gender=='male')
            $this->gender=0;
        else
            $this->gender=1;
        $this->save();
        $user_id = DB::table('user')->where('user_guid', $this->user_guid)->value('user_id');
        $this->user_id = $user_id;


        //////////////////////////////save user_category for this user
        //////////////////////////////save user_category for this user
        //////////////////////////////save user_category for this user
        //////////////////////////////save user_category for this user
        $UserCategory=new UserCategory();
//      $category=new Category();
        if(!$UserCategory->exist_user_id($user_id))
            for($index=0;$index<Category::Category_Count+1;$index++){
                DB::table('user_category')->insert(
                    ['user_category_guid' =>uniqid('',true),
                        'user_id' => $this->user_id,
                        'category_id' => $index +1,
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),]
                );
            }
        return $user_id;
    }

    public function  edit($request)
    {

        $oldUser = $this->find( $this -> user_id);

        if($request -> has('hazel_date_time') && $request -> has('hazel_score'))
        {
            $oldUser->hazel_date_time=$request -> input('hazel_date_time');
            $oldUser->hazel_score=$request -> input('hazel_score');
        }
        if($request -> has('is_active'))
            $oldUser->is_active=$request -> input('is_active');

        if($request -> has('api_token'))
            $oldUser->api_token=$request -> input('api_token');

        if($request -> has('invite_code'))
            $oldUser->invite_code=$request -> input('invite_code');

//        if($request['password']!=null){
//
//            $request['password'] = Hash::make($request['password']);
////            bcrypt($request['password'])
//            $oldUser->password=$request -> input('password');
//        }
        if($request -> has('email'))
            $oldUser->email=$request -> input('email');
        if($oldUser->tel==null && $request -> input('tel')!=null){
            $oldUser->hazel=$oldUser->hazel+USer::TELL_REWARD;
        }
        if($oldUser->birth_date==null && $request -> input('birth_date')!=null){
            $oldUser->hazel=$oldUser->hazel+USer::BIRTH_DATE_REWARD;
        }
        $oldUser->tel=$request -> input('tel');

        $oldUser->birth_date=$request -> input('birth_date');

        if($request -> input('gender')=='male')
            $oldUser->gender=0;
        else
            $oldUser->gender=1;
        $oldUser->save();

    }

    public function delete(){
        $this->find($this->user_id)->delete();
    }

    public function select()
    {
        $query = $this->newQuery();
        if($this->user_id != null){
            $query->where('user_id', '=', $this->user_id);
        }
        if($this->user_guid != null){
            $query->where('user_guid', '=', $this->user_guid);
        }
        if($this->user_name != null){
            $query->where('user_name', '=', $this->user_name);
        }
        $query->where('deleted_at', '=', null);
        $users = $query->get();
        return $users;
    }

    public function getFullDetail( $params1,$params2,$params3)
    {

        $query = $this->newQuery();

        if($params1!=null) {

            $query=\App\Utility::fillQueryAlias($query,$params1);
        }
        $query =Self::makeWhere($query);

        //
        if($params2!=null) {
            $query=\App\Utility::fillQueryJoin($query,$params2);

        }
        //filtering
        if($params3!=null) {
            $query=\App\Utility::fillQueryFilter($query,$params3);
        }

        $users = $query->get();
        return $users;
    }

    ////simplest query
    public function makeWhere($query){
        if($this->user_id != null){
            $query->where('	user.'.'user_id', '=', $this->user_id);
        }
        if($this->user_guid != null){
            $query->where('	user.'.'user_guid', '=', $this->user_guid);
        }
        if($this->user_name != null){
            $query->where('	user.'.'user_name', 'like', $this->user_name);
        }
        if($this->password != null){
            $query->where('	user.'.'password', '=', $this->password);
        }
        if($this->invite_code != null){
            $query->where('	user.'.'invite_code', '=', $this->invite_code);
        }
        if($this->hazel != null){
            $query->where('	user.'.'hazel', '=', $this->hazel);
        }
        if($this->luck != null){
            $query->where('	user.'.'luck', '=', $this->luck);
        }
        if($this->email != null){
            $query->where('	user.'.'email', '=', $this->email);
        }
        if($this->tel != null){
            $query->where('	user.'.'tel', '=', $this->tel);
        }
        if( $this->gender != null){
            $query->where('	user.'.'gender', '=', $this->gender);
        }
        if( $this->image != null){
            $query->where('	user.'.'image', '=', $this->image);
        }
        if($this->hazel_date_time != null){
            $query->where('	user.'.'hazel_date_time', '=', $this->hazel_date_time);
        }
        if($this->hazel_score != null){
            $query->where('	user.'.'hazel_score', '=', $this->hazel_score);
        }
        if( $this->is_active != null){
            $query->where('	user.'.'is_active', '=', $this->user->is_active);
        }

        return $query;
    }

    public function updatePassword($request)
    {
        $oldUser = $this->findByIdAndGuid($request['user_id'],$request['user_guid'] );

        if($request['password']!=null){
//            $password = bcrypt($request['password']);
            $password = Hash::make($request['password']);
//            bcrypt($request['password'])
            $oldUser->password=$password;
        }

        $oldUser->save();
    }

    public static function DecreaseLuck($userId, $amount)
    {
        $user = User::findById($userId);

        $newScore = $user->luck - $amount;
        if($newScore < 0)
            return false;
        else
            $user->luck=$newScore;
        $user->save();
        return true;
    }

    public static function IncreaseLuck($userId, $amount)
    {
        $user = User::findById($userId);
        $newScore=(int)$user->luck + (int)$amount;
        $user->luck=$newScore;
        $user->save();
        return true;
    }

    public static function DecreaseHazel($userId, $amount)
    {
        $user = User::findById($userId);

        $newScore = $user->hazel - $amount;
        if($newScore < 0)
            return false;
        else
            $user->hazel=$newScore;
        $user->save();
        return true;
    }

    public static function IncreaseHazel($userId, $amount)
    {
        $user = User::findById($userId);

        $newScore= $user->hazel + $amount;
        $user->hazel=$newScore;
        $user->save();
        return true;
    }

    public static function IncreaseDecreaseHazelLuck($userId, $hazelamount=0,$luckamount=0)
    {

        $user = User::findById($userId);
        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
            'hazel' => \DB::raw( 'hazel +'.$hazelamount ),
            'luck' => \DB::raw( 'luck +'.$luckamount ),
        ]);
        return true;
    }

    public static function DecreaseHazelLuck($userId, $hazelamount=0,$luckamount=0)
    {

        if($hazelamount==null)
            $hazelamount=0;
        if($luckamount==null)
            $luckamount=0;


        if($hazelamount>0)
            $hazelamount*=-1;
        if($luckamount>0)
            $luckamount*=-1;
        $user = User::findById($userId);
        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
            'hazel' => \DB::raw( 'hazel +'.$hazelamount ),
            'luck' => \DB::raw( 'luck +'.$luckamount ),
        ]);
        return true;
    }

    public static function ChangeHazelLuck($userId, $hazelamount=0,$luckamount=0)
    {
        if($hazelamount==null)
            $hazelamount=0;
        if($luckamount==null)
            $luckamount=0;


        $user = User::findById($userId);
        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
            'hazel' => \DB::raw( 'hazel +'.$hazelamount ),
            'luck' => \DB::raw( 'luck +'.$luckamount ),
        ]);
        return true;
    }

    public static function get_random_user($user_id){
        return User::inRandomOrder()
            ->where('user_id','<>',$user_id)
            ->get();
    }

    public static function get_random_bot_user($user_id){
        return User::inRandomOrder()
            ->where('user_id','<>',$user_id)
            //for test get just visitor
//            ->where('visitor_user_name','<>',null)
//            ->where('user_name','=',null)
            ->where('password','=','zxcvbnm')
            ->get();
    }

    public function exist($Id,$guid)
    {
        $query = $this->newQuery();
        $query->where('user_id', '=', $Id);
        $query->where('user_guid', '=', $guid);
        $users = $query->get();
        if (count($users) == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public static function existByEmail($email, $forEdit, $userId)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('email', 'like', $email);
        if($forEdit)
        {
            $query->where('user_id', '<>', $userId);
        }

        $users = $query->get();
        if (count($users) == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public static function existByUserName($userName, $forEdit, $userId)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('user_name', 'like', $userName);
        if($forEdit)
        {
            $query->where('user_id', '<>', $userId);
        }
        $users = $query->get();
        if (count($users) == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public static function existByVisitorUserName($visitoruserName, $forEdit, $userId)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('visitor_user_name', 'like', $visitoruserName)->lockForUpdate();
        if($forEdit)
        {
            $query->where('user_id', '<>', $userId);
        }
        $users = $query->get();
        if (count($users) == 0){
            return false;
        }
        else{
            return $users[0];
        }
    }

    public static function findByToken($token)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('api_token', 'like', $token);

        $users = $query->get();
        if (count($users) == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public static function updateToken($token, $userId, $userGuid)
    {
        $oldUser = new User();
        $oldUser = $oldUser->findByIdAndGuid($userId,$userGuid );
        $oldUser->api_token=$token;
        $oldUser->save();
    }

    public function findByIdAndGuid($id, $guid)
    {
        $query = $this->newQuery();
        $query->where('user_id', '=', $id);
        $query->where('user_guid', 'like', $guid);
        $users = $query->get();

        return $users[0];
    }

    public static function findByUsername($user_name)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('user_name', 'like', $user_name);
        $user = $query->first();

        return $user;
    }

    public static function findByVisitorUsername($visitor_user_name)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('visitor_user_name', 'like', $visitor_user_name);
        $user = $query->first();

        return $user;
    }

    public static function findById($id)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('user_id', '=', $id);
        $users = $query->get();

        return $users[0];
    }

    public static function findByEmail($mail)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('email', 'like', $mail);
        $users = $query->get();
        if(count($users)!=0)
            return $users[0];
        else
            return null;
    }

    public static function findByInviteCode($inviteCode)
    {
        $user = new User();
        $query = $user->newQuery();
        $query->where('invite_code', '=', $inviteCode);
        $users = $query->get();

        if(count($users)!=0)
            return $users[0];
        else
            return null;
    }

    public function updateAvatar($user_guid,$file)
    {
        self::deleteAvatar($user_guid);

        $f = finfo_open();

        $mime = finfo_buffer($f, $file, FILEINFO_MIME_TYPE);
        $split = explode( '/', $mime );
        $ext = $split[1];
        //insert new logo of user to this path    =>  storage_path().'/app/avatars
        $fileName = $user_guid. '.' .$ext;
        $destinationPath = storage_path().'/app/avatars';
        file_put_contents($destinationPath."/".$fileName, $file);
    }

    public function deleteAvatar($user_guid){
        //retern previous name of companylogo
        $destinationPath = storage_path().'/app/avatars';
        $files1 = scandir($destinationPath);
        $nameOfFile="";
        $search =$user_guid;
        $search_length = strlen($search);
        foreach ($files1 as $key => $value) {
            if (substr($value, 0, $search_length) == $search) {
                $nameOfFile=$value;
                break;
            }
        }

        //delete previou logo
        if($nameOfFile!=null)
            File::delete(storage_path().'/app/avatars/'.$nameOfFile);
    }

    /**
     * @return string
     */
    public function getAuthIdentifierName()
    {
        // Return the name of unique identifier for the user (e.g. "id")
        return 'user_id';
    }

    /**
     * @return mixed
     */
    public function getAuthIdentifier()
    {
        // Return the unique identifier for the user (e.g. their ID, 123)
        return $this->user_id;
    }

    /**
     * @return string
     */
    public function getAuthPassword()
    {
        // Returns the (hashed) password for the user
        return $this->password;
    }

    /**
     * @return string
     */
    public function getRememberToken()
    {
        // Return the token used for the "remember me" functionality
        return $this->remember_token;
    }

    /**
     * @param  string  $value
     * @return void
     */
    public function setRememberToken($value)
    {
        // Store a new token user for the "remember me" functionality
        $this->remember_token = $value;
    }

    /**
     * @return string
     */
    public function getRememberTokenName()
    {
        // Return the name of the column / attribute used to store the "remember me" token
        return 'remember_token';
    }

    public function hasAttribute($attr)
    {
        return array_key_exists($attr, $this->attributes);
    }

    public static function removeVisitorUserName($visitorUserName)
    {
        DB::table('user')
            ->where('visitor_user_name', 'like', $visitorUserName)
            ->update(['visitor_user_name' => null]);
    }

    public static function countOfUserDevice($DeviceCode)
    {
        //SELECT count(*) FROM `user` WHERE `phone_code` like 'Visitor_7cb9516d30c01299#HTC T329d'

        return DB::table('user')
            ->where('phone_code', 'like', $DeviceCode)->get()->count();
    }

}