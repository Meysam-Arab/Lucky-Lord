<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App;
use App\User;
use App\UserCategory;
use App\UserFriend;
use App\UserUniversalMatch;
use App\Utility;
use App\RequestResponseAPI;
use Illuminate\Http\Request;
use App\Category;
use App\Question;
use DB;
use Log;
use File;
use phpDocumentor\Reflection\Types\Null_;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Hash;
use Carbon\Carbon;
use App\LogEvent;

class API_UserUniversalMatchController extends Controller
{
    protected $userUniversalMatch;

    public function __construct(UserUniversalMatch $userUniversalMatch)
    {
        $this->userUniversalMatch = $userUniversalMatch;
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
        if (!$request->has('tag') || !$request->has('bet') || ($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION && !$request->has('chosen_category_id')))
        {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
        if($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION && ($request['chosen_category_id']<-1 || $request['chosen_category_id']>Category::Category_Count)){
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
        ////////////////////////////////////////////

        try
        {
            if($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION && $request['opponent_type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                return $this->random_question_match_request($request);
            elseif(($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION && $request['opponent_type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM) || ($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION && $request['opponent_type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND))
                return $this->NONRANDOM_or_FRIEND_question_match_request($request);
            ////for cros table condition
            else if($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE && $request['opponent_type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                return $this->random_crosstable_match_request($request);
            elseif(($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE && $request['opponent_type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM) || ($request['type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE && $request['opponent_type']==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND))
                return $this->NONRANDOM_or_FRIEND_crosstable_match_request($request);
        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
    }

    public function apiResult(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('time') || !$request->has('match_values') || !$request->has('user_universal_match_id')|| !$request->has('used_hazel')|| !$request->has('used_luck')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
        }
        ////////////////////////////////////////////
        try{
            $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);

            $match_values=[];
            if($user_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE)
                $match_values=$request['match_values'];
            else{
                $match_values=json_decode($request['match_values']);


                //////update user_category for questions of this game for
                for($index=0;$index<count($match_values);$index++){
                    $user_category = UserCategory::findBy_UserId_and_CategoryId($user->user_id,$match_values[$index]->category_id);
                    if($match_values[$index]->is_correct==0){
                        //                                $user_category->newQuery()
                        DB::table('user_category')
                            ->where(['user_id'=> $user->user_id,'category_id'=>$match_values[$index]->category_id])
                            ->update([
                                'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                                'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                                'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                            ]);
                    }else{
//                                $user_category->newQuery()
                        DB::table('user_category')
                            ->where(['user_id'=> $user->user_id,'category_id'=>$match_values[$index]->category_id])->update([
                                'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                                'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                                'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                                'correct_answered_count' => \DB::raw( 'correct_answered_count + 1' ),
                                'daily_correct_answered_count' => \DB::raw( 'daily_correct_answered_count + 1' ),
                                'weekly_correct_answered_count' => \DB::raw( 'weekly_correct_answered_count + 1' ),
                            ]);
                    }

                }
            }



            /////CHECK INVALID TIME OF USER AND SET IT

            if($user_match->bet =='n'){
                if($request['time']>UserUniversalMatch::Time_OF_NITRO_MATCH){
                    $request['time']=UserUniversalMatch::Time_OF_NITRO_MATCH;
                }
            }else{
                if($request['time']>UserUniversalMatch::Time_OF_Match){
                    $request['time']=UserUniversalMatch::Time_OF_Match;
                }
            }


            if($user_match!=false)
            {

                /////updte hazel and luck in hint time used user
                /////updte hazel and luck in hint time used user
                /////updte hazel and luck in hint time used user
                User::DecreaseHazelLuck($user->user_id,$request['used_hazel'],$request['used_luck']);

                //IN THIS CONDITION
                //1. count corect answer
                //2. update match
                //3. if match is ended => call get_winner_id_for_result() method
                $corect_answered_count=0;
                if($user_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE)
                    $corect_answered_count=$request['match_values'];
                else{
                    for($index=0;$index<count($match_values);$index++){
                        if($match_values[$index]->is_correct==1)
                            $corect_answered_count++;
                    }
                }
                if($user_match->user_1_id == $user->user_id)
                {
                    ///initialize for update match
                    $request['user_1_correct_count']=$corect_answered_count;
                    $request['user_1_spent_time']=$request['time'];
                    $this->userUniversalMatch->intialize();
                    $this->userUniversalMatch->intializeByRequest($request);
                    $this->userUniversalMatch->edit($request);
                    unset($request['user_1_correct_count']);
                    unset($request['user_1_spent_time']);

                    if($user_match->user_2_spent_time==-1 && $user_match->user_2_id>0){
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                    }
                    if($user_match->user_2_spent_time > 0  || $user_match->user_2_id < 0){
                        ////is ended game
                        if($user_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }
                        if($user_match->bet == 'u' && $user_match->user_2_id < 0 && Carbon::now()->subSecond(userUniversalMatch::min_robat_after_start_nitro_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }
                        $winner_id = userUniversalMatch::get_winner_id_for_result($request['user_universal_match_id'],true,$request['time']);
                        $user_match= userUniversalMatch::findById($request['user_universal_match_id']);


                        $enemy_user=DB::table('user')
                            ->select('chat_allow as allowChat')
                            ->where('user.user_id', $user_match->user_1_id)
                            ->first();
                        if($winner_id==1){
                            ////user_1  player  is winner
                            $opponent=[];
                            $winner_code=1;
                            $opponent['opponent_correct_count']=$user_match->user_2_correct_count;
                            $opponent['opponent_spent_time']=$user_match->user_2_spent_time;
                            if($enemy_user==null)
                                $opponent['allowChat']=0;
                            else
                                $opponent['allowChat']=$enemy_user->allowChat;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }elseif ($winner_id==2){
                            ////user_2  player  is winner
                            $opponent=[];
                            $winner_code=2;
                            $opponent['opponent_correct_count']=$user_match->user_2_correct_count;
                            $opponent['opponent_spent_time']=$user_match->user_2_spent_time;
                            if($enemy_user==null)
                                $opponent['allowChat']=0;
                            else
                                $opponent['allowChat']=$enemy_user->allowChat;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }else{
                            //// players is equal score
                            $opponent=[];
                            $winner_code=3;
                            $opponent['opponent_correct_count']=$user_match->user_2_correct_count;
                            $opponent['opponent_spent_time']=$user_match->user_2_spent_time;
                            if($enemy_user==null)
                                $opponent['allowChat']=0;
                            else
                                $opponent['allowChat']=$enemy_user->allowChat;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }
                    }
                    else{
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                    }

                }
                elseif ($user_match->user_2_id==$user->user_id){
                    $request['user_2_correct_count']=$corect_answered_count;
                    $request['user_2_spent_time']=$request['time'];
                    $this->userUniversalMatch->intialize();
                    $this->userUniversalMatch->intializeByRequest($request);
                    $this->userUniversalMatch->edit($request);
                    unset($request['user_2_correct_count']);
                    unset($request['user_2_spent_time']);


                    if($user_match->user_1_spent_time==-1 && $user_match->user_1_id>0){
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'type'=>$user_match->type,'bet'=>$user_match->bet,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                    }
                    if($user_match->user_1_spent_time > 0 || $user_match->user_1_id < 0){
                        ////is ended game
                        if($user_match->user_1_id < 0 && Carbon::now()->subSecond(userUniversalMatch::min_robat_after_start_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'type'=>$user_match->type,'bet'=>$user_match->bet,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }
                        if($user_match->bet == 'u' && $user_match->user_2_id < 0 && Carbon::now()->subSecond(userUniversalMatch::min_robat_after_start_nitro_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'type'=>$user_match->type,'bet'=>$user_match->bet,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }



                        $winner_id=userUniversalMatch::get_winner_id_for_result($request['user_universal_match_id'],true,$request['time']);
                        $user_match=userUniversalMatch::findById($request['user_universal_match_id']);

                        $enemy_user=DB::table('user')
                            ->select('chat_allow as allowChat')
                            ->where('user.user_id', $user_match->user_1_id)
                            ->first();
                        if($winner_id==1){
                            ////user_1  player  is winner
                            $opponent=[];
                            $winner_code=2;
                            $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                            $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                            if($enemy_user==null)
                                $opponent['allowChat']=0;
                            else
                                $opponent['allowChat']=$enemy_user->allowChat;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }elseif ($winner_id==2){
                            ////user_2  player  is winner
                            $opponent=[];
                            $winner_code=1;
                            $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                            $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                            if($enemy_user==null)
                                $opponent['allowChat']=0;
                            else
                                $opponent['allowChat']=$enemy_user->allowChat;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }else{
                            //// players is equal score
                            $opponent=[];
                            $winner_code=3;
                            $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                            $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                            if($enemy_user==null)
                                $opponent['allowChat']=0;
                            else
                                $opponent['allowChat']=$enemy_user->allowChat;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_match->bet,'type'=>$user_match->type,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                        }
                    }
                    else{
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'type'=>$user_match->type,'bet'=>$user_match->bet,'opponent_type'=>$user_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                    }
                }
                else
                {
                    return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);
                }
            }else
            {
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);

            }



        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_RESULT]);

        }
    }

    public function apiStatus(Request $request){
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('user_universal_match_id')
        ) {

            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
        }
        ////////////////////////////////////////////
        try{
            $UserUniversalMatch_t = new UserUniversalMatch();
            $query = $UserUniversalMatch_t->newQuery();
            $query->where('user_universal_match_id', $request['user_universal_match_id']);
           $user_universal_match = $query->first();

            //get record(match) in mysql with $request['user_universal_match_id']
//            $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);


            ///if dose not exist any record in mysql with this id (zero percent)
            if(!$user_universal_match){

                return json_encode(['error' => RequestResponseAPI::ERROR_OPPONENT_CANCELED_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);

            }

            ///return status with varios of type and opponent_type in if,else,if,else 's
            if(($user_universal_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION || $user_universal_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE) && $user_universal_match->opponent_type==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM){
                return $this->random_question_match_Status($user_universal_match,$user,$token,$request);
            }
            elseif(($user_universal_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION && $user_universal_match->opponent_type==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM) || ($user_universal_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION &&$user_universal_match->opponent_type ==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND))
                return $this->NONRANDOM_or_FRIEND_question_match_Status($user_universal_match,$user,$token,$request);
            //for cross table
            elseif(($user_universal_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE && $user_universal_match->opponent_type==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM) || ($user_universal_match->type==UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE &&$user_universal_match->opponent_type ==UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND))
                return $this->NONRANDOM_or_FRIEND_crosstable_match_Status($user_universal_match,$user,$token,$request);






        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);

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
        if (!$request->has('tag') || !$request->has('opponent_type') || !$request->has('type')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_INDEX]);
        }
        ////////////////////////////////////////////

        try
        {
            //////////////get all of the game that current user play on it! in 2 days ago


            $matches=DB::select('call sel_universal_match("'.$request['type'].'","'.$request['opponent_type'].'","'.$user->user_id.'","'.UserUniversalMatch::Match_count_that_show_in_index_page.'")');

            $user_ides='';
            for ($index=0;$index<count($matches);$index++){
                if($matches[$index]->user_1_id != $user->user_id){
                    $user_ides=$user_ides.$matches[$index]->user_1_id.',';

//                    if( $matches[$index]->user_2_spent_time  == -1 ){
//                        $matches[$index]->user_2_spent_time==UserUniversalMatch::Time_OF_Match;
//                        $matches[$index]->save();
//                    }
                }
                if($matches[$index]->user_2_id != $user->user_id){
                    $user_ides=$user_ides.$matches[$index]->user_2_id.',';

//                    if( $matches[$index]->user_1_spent_time  == -1 ){
//                        $matches[$index]->user_1_spent_time==UserUniversalMatch::Time_OF_Match;
//                        $matches[$index]->save();
//                    }
                }
            }


            $exploded_item=explode(",",$user_ides);
            for ($index=0;$index<count($exploded_item);$index++){
                if($exploded_item[$index]<0)
                    $exploded_item[$index]*=-1;
            }


            ////////////////////get all off opponent (fake or unfake ) that 2 days ago played with current user
            $user_image_lucks=DB::table('user')
                ->select('user_id','user_name','visitor_user_name','image','luck','chat_allow as allowChat')
                ->whereIn('user_id', $exploded_item)
                ->get();

            ////////////update data of required(meysam ceo) for every matches
            for ($index=0;$index<count($matches);$index++){
                if(abs($matches[$index]->user_1_id) != $user->user_id){
                    for ($index2=0;$index2<count($user_image_lucks);$index2++){
                        if($user_image_lucks[$index2]->user_id ==abs($matches[$index]->user_1_id))
                        {
                            $matches[$index]->allowChat=$user_image_lucks[$index2]->allowChat;
                            $matches[$index]->opponent_correct_count=$matches[$index]->user_1_correct_count;
                            $matches[$index]->self_correct_count=$matches[$index]->user_2_correct_count;
                            $matches[$index]->opponent_time=$matches[$index]->user_1_spent_time;
                            if($matches[$index]->user_2_spent_time==-1)
                                $matches[$index]->self_time=UserUniversalMatch::Time_OF_Match;
                            else
                                $matches[$index]->self_time=$matches[$index]->user_2_spent_time;

                            $matches[$index]->opponent_avatar=$user_image_lucks[$index2]->image;
                            $matches[$index]->opponent_luck=$user_image_lucks[$index2]->luck;
//                            $matches[$index]->opponent_user_name=$user_image_lucks[$index2]->user_name;
                            if($user_image_lucks[$index2]->user_name!=null)
                                $matches[$index]->opponent_user_name=$user_image_lucks[$index2]->user_name;
                            else
                                $matches[$index]->opponent_user_name=$user_image_lucks[$index2]->visitor_user_name;
                        }
                    }
                }
                if(abs($matches[$index]->user_2_id) != $user->user_id){
                    for ($index3=0;$index3<count($user_image_lucks);$index3++){
                        if($user_image_lucks[$index3]->user_id == abs($matches[$index]->user_2_id))
                        {
                            $matches[$index]->allowChat=$user_image_lucks[$index3]->allowChat;
                            $matches[$index]->opponent_correct_count=$matches[$index]->user_2_correct_count;
                            $matches[$index]->self_correct_count=$matches[$index]->user_1_correct_count;
                            $matches[$index]->opponent_time=$matches[$index]->user_2_spent_time;
                            if($matches[$index]->user_1_spent_time==-1)
                                $matches[$index]->self_time=UserUniversalMatch::Time_OF_Match;
                            else
                                $matches[$index]->self_time=$matches[$index]->user_1_spent_time;

                            $matches[$index]->opponent_avatar=$user_image_lucks[$index3]->image;
                            $matches[$index]->opponent_luck=$user_image_lucks[$index3]->luck;
//                            $matches[$index]->opponent_user_name=$user_image_lucks[$index3]->user_name;
                            if($user_image_lucks[$index3]->user_name!=null)
                                $matches[$index]->opponent_user_name=$user_image_lucks[$index3]->user_name;
                            else
                                $matches[$index]->opponent_user_name=$user_image_lucks[$index3]->visitor_user_name;
                        }
                    }
                }
            }


            //////////////////unset some unusefull data from $matches Object
            for ($index3=0;$index3<count($matches);$index3++){

                if(is_null($matches[$index3]->winner_id)){
                    unset($matches[$index3]->winner_id);
//                    unset($matches[$index3]->bet);
                }
                else
                {
                    if($matches[$index3]->winner_id==$user->user_id){
                        $matches[$index3]->winner_code=UserUniversalMatch::win;
                    }else
                        $matches[$index3]->winner_code=UserUniversalMatch::lost;

                    if($matches[$index3]->winner_id === 0){
                        $matches[$index3]->winner_code=UserUniversalMatch::equal;
                    }

                    unset($matches[$index3]->winner_id);
                }
//                unset($matches[$index3]->user_1_spent_time);
//                unset($matches[$index3]->user_2_spent_time);
//                unset($matches[$index3]->user_1_id);
//                unset($matches[$index3]->user_2_id);
                unset($matches[$index3]->questions);
//                unset($matches[$index3]->user_2_correct_count);
//                unset($matches[$index3]->user_1_correct_count);
                unset($matches[$index3]->user_universal_match_values);
                unset($matches[$index3]->created_at);
                unset($matches[$index3]->updated_at);
                unset($matches[$index3]->deleted_at);

                if($matches[$index3]->is_ended=='1' || $matches[$index3]->is_ended=='-1'){
//                    unset($matches[$index3]->user_universal_match_id);
                    unset($matches[$index3]->user_universal_match_guid);
                }else{
                    unset($matches[$index3]->opponent_correct_count);
                    unset($matches[$index3]->opponent_time);
                }
            }
            //define varius of bet for game
            $bets=[
                //free Game
                ['bet_id'=>UserUniversalMatch::f_key,'bet_amount'=>UserUniversalMatch::f,'bet_time'=>UserUniversalMatch::Time_OF_Match,'bet_interest'=>5],

                //game on money
                ['bet_id'=>UserUniversalMatch::l_key,'bet_amount'=>UserUniversalMatch::l,'bet_time'=>UserUniversalMatch::Time_OF_Match,'bet_interest'=>5],
                ['bet_id'=>UserUniversalMatch::m_key,'bet_amount'=>UserUniversalMatch::m,'bet_time'=>UserUniversalMatch::Time_OF_Match,'bet_interest'=>5],
                ['bet_id'=>UserUniversalMatch::h_key,'bet_amount'=>UserUniversalMatch::h,'bet_time'=>UserUniversalMatch::Time_OF_Match,'bet_interest'=>5],
                ['bet_id'=>UserUniversalMatch::luck_key,'bet_amount'=>UserUniversalMatch::u,'bet_time'=>UserUniversalMatch::Time_OF_Match,'bet_interest'=>5,'bet_is_on_luck'=>1],
                ['bet_id'=>UserUniversalMatch::nitro_key,'bet_amount'=>UserUniversalMatch::n,'bet_time'=>UserUniversalMatch::Time_OF_NITRO_MATCH,'bet_interest'=>5,'bet_reward_time'=>15],
            ];
            return json_encode(['bets'=>$bets,'matches'=>$matches,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_INDEX]);


        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_INDEX]);

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
        if (!$request->has('tag') || !$request->has('user_universal_match_id') || !$request->has('status')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_CHANGE_STATUS]);

        }

        try
        {
            $user_univesal_match=DB::table('user_universal_match')->where('user_universal_match_id', '=', $request['user_universal_match_id'])->first();


                //status -1 when occur user2 denay the match
                //status -3 when occur user1 cancel the match
                if( $request['status']==-1) {
                    if($user_univesal_match->user_2_id== $user->user_id && $user_univesal_match->deleted_at != null)
                        return json_encode(['token' => $token,'status_changed'=>RequestResponseAPI::ERROR_OPPONENT_ACCEPTED_CODE, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_CHANGE_STATUS]);

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $request['user_universal_match_id'])
                        ->where('user_2_id', $user->user_id)
                        ->update(['is_ended' => -1]);





                }
                if( $request['status']==-3) {
                    if($user_univesal_match->user_1_id== $user->user_id && $user_univesal_match->user_2_spent_time== -1)
                        return json_encode(['token' => $token,'status_changed'=>RequestResponseAPI::ERROR_OPPONENT_ACCEPTED_CODE, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_CHANGE_STATUS]);

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $request['user_universal_match_id'])
                        ->where('user_1_id', $user->user_id)
                        ->where('is_ended', null)
                        ->lockForUpdate()
                        ->update(['deleted_at' => Carbon::now()])
                        ;
                }


                
            ///// update  and hazel  for first player
                        /////new hazel= old hazel - bet;
                        $decreaseMount=UserUniversalMatch::translate_bet($user_univesal_match->bet);
                        $user1 = User::findById($user_univesal_match->user_1_id);
                        if($user_univesal_match->bet == 'u'){
                            $user1->newQuery()->where(['user_id'=> $user_univesal_match->user_1_id ])->update([
                                'luck' => \DB::raw( 'luck +'.$decreaseMount )
                            ]);
                        }else{
                            $user1->newQuery()->where(['user_id'=> $user_univesal_match->user_1_id ])->update([
                                'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                            ]);
                        }


                    return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_CHANGE_STATUS]);



        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_CHANGE_STATUS]);
        }
    }

    public function apiRematchRequest(Request $request)
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
        //opponent_id
        if (!$request->has('opponent_user_name')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }

        ////////////////////////////////////////////

        try
        {
            ///get opponent id
            $op_user=User::findByUsername($request['opponent_user_name']);
            $request['opponent_id']=$op_user['user_id'];


            ///call previous request func!!!
            return self::apiRequest($request);
        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
    }

    






    ///////////////request Games
    ///for general game

    public function random_question_match_request(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('request_count') || !$request->has('bet') || !$request->has('chosen_category_id')
        ) {
           return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

        }
        if($request['chosen_category_id']<-1 || $request['chosen_category_id']>Category::Category_Count){
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
        ////////////////////////////////////////////

        try
        {
            DB::beginTransaction();


            if(UserUniversalMatch::translate_bet($request['bet'])>$user->hazel && $request['bet'] != 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }
            if(UserUniversalMatch::translate_bet($request['bet'])>$user->luck && $request['bet'] == 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }


            if($request['request_count']==1){
                ///check it==>> if this user exist  in previous game is wait not insert extra game for current user
                $UserUniversalMatch = DB::table('user_universal_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('user_2_id', null)
                    ->where('user_1_id', '=',$user->user_id)
                    ->where('type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                    ->where('opponent_type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                    ->where('deleted_at',NULL)
                    ->orderBy('user_universal_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();
                if(count($UserUniversalMatch)!=0){
                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$UserUniversalMatch->user_universal_match_id,'type'=>$UserUniversalMatch->type,'opponent_type'=>$UserUniversalMatch->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }



                //////////////////////////get current game is wait player
                $UserUniversalMatch = DB::table('user_universal_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('user_2_id', null)
                    ->where('user_1_id', '<>',$user->user_id)
                    ->where('type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                    ->where('opponent_type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                    ->where('deleted_at',NULL)
                    ->orderBy('user_universal_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();

                /////////if UserUniversalMatch==null => there aren't any user for do game
                if(count($UserUniversalMatch)==0){
                    /////insert record to user_match table for this bet
                    $request['user_1_id']=$user->user_id;
                    $request['user_universal_match_values']=$request['chosen_category_id'];
                    $request['is_ended']=0;
                    $request['user_1_correct_count']=0;
                    $request['user_2_correct_count']=0;
                    $request['user_1_spent_time']=NULL;
                    $request['user_2_spent_time']=Null;
                    $request['type']=UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION;
                    $request['opponent_type']=UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM;

                    $user_matchs_insert=new UserUniversalMatch();
                    $user_matchs_insert->intializeByRequest($request);
                    $user_matchs_insert->store();

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet

                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_matchs_insert->user_universal_match_id,'type'=>$user_matchs_insert->type,'opponent_type'=>$user_matchs_insert->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }
                else{

                    if($UserUniversalMatch->user_1_id==$user->user_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم" );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = userUniversalMatch::withTrashed()->where('user_universal_match_id', $UserUniversalMatch->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['user_universal_match_id'=>$UserUniversalMatch->user_universal_match_id,'type'=>$UserUniversalMatch->type,'opponent_type'=>$UserUniversalMatch->opponent_type,'error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    ////////////////////fill this record
                    ////////////////////insert second person to user_2_id and return opponent array
                    if($UserUniversalMatch->user_universal_match_values>= -1 && $UserUniversalMatch->user_universal_match_values<=Category::Category_Count)
                        $UserUniversalMatch->user_universal_match_values=Question::get_10_Random_question($UserUniversalMatch->user_universal_match_values,$request['chosen_category_id']);

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        UserUniversalMatch->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $UserUniversalMatch->user_universal_match_id)
                        ->update(['user_2_id' => $user->user_id,
                            'user_2_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$UserUniversalMatch->user_universal_match_values
                        ]);

                    ///get question Object
//                    $q_id=$request['questions'];

                    $questions=DB::table('question')
                        ->select('question_id','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$UserUniversalMatch->user_universal_match_values))
                        ->orderByRaw("FIELD(question_id , ".$UserUniversalMatch->user_universal_match_values.") ASC")
                        ->get();


                    ////get image file if question has image
                    for($index=0;$index<count($questions);$index++){
                        if((strpos($questions[$index]->description, '#')) !== false){
                            $question =new Question();
                            $image=$question->getQuestionImage($questions[$index]->question_id);
                            $questions[$index]->image=$image;
                        }
                    }


                    $enemy_user=DB::table('user')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id', $UserUniversalMatch->user_1_id)
                        ->get();



                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet'] == 'u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }

                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];
                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$UserUniversalMatch->user_universal_match_id,'type'=>$UserUniversalMatch->type,'opponent_type'=>$UserUniversalMatch->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }
            }
            else if($request['request_count']>1){
                //////////////////////////get current game is wait player
                $user_universal_match = DB::table('user_universal_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('is_ended', '=' , 0)
                    ->where('type', UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                    ->where('opponent_type', '=' , UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                    ->where('deleted_at', '=' , null)
                    ->where('user_1_id', '=',$user->user_id)
                    ->orWhere('user_2_id', '=',$user->user_id)
                    ->orderBy('user_universal_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();

                if (count($user_universal_match) == 0)
                {

                    DB::commit();
                    return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE,'line'=>'268_api_user_universalmatchController', 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }

                if($request['request_count'] == 8 && $user_universal_match->user_2_id == null){
//                        return 'return bot';
//                        User::get_random_user();
                    $chance=rand(1,100);
					//TODO: meysam - must comment
//                    $chance = 0;
                    /////////////////////////////////////////
                    if($request['request_count']==8 && $chance<40){//$chance<20
                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }


//                        $rand_user=User::get_random_user($user->user_id);
                    $rand_user=User::get_random_bot_user($user->user_id);
                    $enemy_user=[];
                    $enemy_user['image']=$rand_user[0]->image;
                    if($rand_user[0]->user_name==null)
                        $enemy_user['user_name']=$rand_user[0]->visitor_user_name;
                    else
                        $enemy_user['user_name']=$rand_user[0]->user_name;
                    $enemy_user['luck']=rand(2200,4000);
                    $enemy_user['allowChat']=0;
//                    $enemy_user['total_match_count']=rand(100,300);
//                    $enemy_user['win_match_count']=rand(1,100);

                    $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,rand(1,Category::Category_Count));

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_universal_match->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id' , $user_universal_match->user_universal_match_id)
                        ->update(['user_2_id' => $rand_user[0]->user_id *-1,
                            'user_2_spent_time'=>-1,
                            'user_1_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);

                    $questions=DB::table('question')
                        ->select('question_id','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                        ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                        ->get();


                    ////get image file if question has image
                    for($index=0;$index<count($questions);$index++){
                        if((strpos($questions[$index]->description, '#')) !== false){
                            $question =new Question();
                            $image=$question->getQuestionImage($questions[$index]->question_id);
                            $questions[$index]->image=$image;
                        }
                    }
                    $opponent=$enemy_user;

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet'] == 'u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }
                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }

                if($user_universal_match->user_1_id==$user->user_id && $user_universal_match->user_2_spent_time==-1)
                {

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet']=='u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }


                    if($user_universal_match->user_universal_match_values>= -1 && $user_universal_match->user_universal_match_values <= Category::Category_Count)
                        $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,$request['chosen_category_id']);


                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                        ->update(['user_1_id' => $user->user_id,
                            'user_1_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);


                   ///get question Object
//                    $q_id=$request['questions'];

                    $questions=DB::table('question')
                        ->select('question_id','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                        ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                        ->get();

                    ////get image file if question has image
                    for($index=0;$index<count($questions);$index++){
                        if((strpos($questions[$index]->description, '#')) !== false){
                            $question =new Question();
                            $image=$question->getQuestionImage($questions[$index]->question_id);
                            $questions[$index]->image=$image;
                        }
                    }

                    $enemy_user=DB::table('user')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id',abs($user_universal_match->user_2_id))
                        ->get();

                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];

                    $user_is_equal=DB::table('user_universal_match')->select('*')->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_1_id==$user_is_equal->user_2_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }

                if($user_universal_match->user_2_id==$user->user_id && $user_universal_match->user_1_spent_time==-1)
                {

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet']=='u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }
                    if($user_universal_match->user_universal_match_values>= -1 && $user_universal_match->user_universal_match_values<=Category::Category_Count)
                        $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,$request['chosen_category_id']);

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_universal_match->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                        ->update(['user_1_id' => $user->user_id,
                            'user_1_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);
                    ///get question Object
//                    $q_id=$request['questions'];

                    $questions=DB::table('question')
                        ->select('question_id','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                        ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                        ->get();

                    ////get image file if question has image
                    for($index=0;$index<count($questions);$index++){
                        if((strpos($questions[$index]->description, '#')) !== false){
                            $question =new Question();
                            $image=$question->getQuestionImage($questions[$index]->question_id);
                            $questions[$index]->image=$image;
                        }
                    }


                    $enemy_user=DB::table('user')
//                        ->join('user_category', 'user_category.user_id', '=', 'user.user_id')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id', $user_universal_match->user_1_id)
//                        ->where('user_category.user_id', $user_universal_match->user_1_id)
//                        ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                        ->havingRaw('user_category.user_id='.$user_universal_match->user_1_id)
                        ->get();



                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];


                    $user_is_equal=DB::table('user_universal_match')->select('*')->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_1_id==$user_is_equal->user_2_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }

                if($user_universal_match->user_2_id !=null){
                    if($user_universal_match->user_universal_match_values>= -1 && $user_universal_match->user_universal_match_values<=Category::Category_Count)
                        $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,$request['chosen_category_id']);

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_universal_match->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                        ->update([
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);
                    $questions=DB::table('question')
                        ->select('question_id','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                        ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                        ->get();

//                        $query_for_get_Serach_result="SELECT  question_id,question_id,description,category_id,answer,penalty FROM question WHERE SUBSTRING(description , 1 ,1)='#' AND answer LIKE '%".$request ->input('description')."%'";
//                        $result = DB::select($query_for_get_Serach_result);

                    ////get image file if question has image
                    for($index=0;$index<count($questions);$index++){
                        if((strpos($questions[$index]->description, '#')) !== false){
                            $question =new Question();
                            $image=$question->getQuestionImage($questions[$index]->question_id);
                            $questions[$index]->image=$image;
                        }
                    }

                    $opponent=DB::table('user')
//                            ->join('user_category', 'user_category.user_id', '=', 'user.user_id')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id', $user_universal_match->user_1_id)
//                            ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                            ->havingRaw('user_category.user_id='.$user_universal_match->user_1_id)
                        ->get();

                    if($opponent[0]->user_name!=null)
                        $opponent[0]->user_name=$opponent[0]->user_name;
                    else
                        $opponent[0]->user_name=$opponent[0]->visitor_user_name;

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet']=='u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }


                    $user_is_equal=DB::table('user_universal_match')->select('*')->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_1_id==$user_is_equal->user_2_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }


                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent[0],'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }

                if($user_universal_match->user_2_id ==null){
                    $chance=rand(1,100);
                    //TODO: meysam - must comment
//                    $chance = 100;
                    /////////////////////////////////////////
                    if($request['request_count']==2 && $chance>8){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==3 && $chance>9){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==4 && $chance>10){
                       return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==5 && $chance>11){
                       return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==6 && $chance>12){
                       return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==7 && $chance>13 || $request['request_count']==8){
                       return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else{
                        //return bot in 62% but in 38% percent get nothing!!!!!!!!!!!!!!
                        $rand_user=User::get_random_bot_user($user->user_id);
                        $enemy_user=[];
                        $enemy_user['image']=$rand_user[0]->image;
                        if($rand_user[0]->user_name==null)
                            $enemy_user['user_name']=$rand_user[0]->visitor_user_name;
                        else
                            $enemy_user['user_name']=$rand_user[0]->user_name;
                        $enemy_user['luck']=rand(2200,4000);
                        $enemy_user['allowChat']=0;
//                        $enemy_user['total_match_count']=rand(100,300);
//                        $enemy_user['win_match_count']=rand(1,100);

                        $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,rand(1,Category::Category_Count));

                        ////for test in user11
//                        $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                        if($myUser != null && $myUser->user_name=='user11'){
//                            $user_universal_match->questions='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                        }
                        //////end

                        DB::table('user_universal_match')
                            ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                            ->update(['user_2_id' => $rand_user[0]->user_id *-1,
                                'user_2_spent_time'=>-1,
                                'user_1_spent_time'=>-1,
                                'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                            ]);

                        $questions=DB::table('question')
                            ->select('question_id','description','category_id','answer','penalty')
                            ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                            ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                            ->get();


                        ////get image file if question has image
                        for($index=0;$index<count($questions);$index++){
                            if((strpos($questions[$index]->description, '#')) !== false){
                                $question =new Question();
                                $image=$question->getQuestionImage($questions[$index]->question_id);
                                $questions[$index]->image=$image;
                            }
                        }

                        $opponent=$enemy_user;

                        ///// update  and hazel  for first playe
                        /////new hazel= old hazel - bet;
                        $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                        $user = User::findById($user->user_id);
                        if($request['bet'] == 'u'){
                            $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                                'luck' => \DB::raw( 'luck +'.$decreaseMount )
                            ]);
                        }else{
                            $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                                'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                            ]);
                        }
                        DB::commit();
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);


                    }



                }
            }
        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
    }

    public function NONRANDOM_or_FRIEND_question_match_request(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation

        if (!$request->has('tag') || !$request->has('opponent_id') || !$request->has('bet') || !$request->has('chosen_category_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

        }
        if($request['chosen_category_id']<-1 || $request['chosen_category_id']>Category::Category_Count){
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
        ////////////////////////////////////////////

        try
        {
            DB::beginTransaction();
            if(UserUniversalMatch::translate_bet($request['bet'])>$user->hazel && $request['bet'] != 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }
            if(UserUniversalMatch::translate_bet($request['bet'])>$user->luck && $request['bet'] == 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }


//            $random_que = Question::select("question_id")
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//            $user_universal_match_values='';
//            for($index=0;$index<count($random_que);$index++){
//                $user_universal_match_values=$user_universal_match_values.$random_que[$index]->question_id.',';
//            }


            ///// update  and hazel  for first player
            /////new hazel= old hazel - bet;
            $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
            $user = User::findById($user->user_id);
            if($request['bet'] == 'u'){
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'luck' => \DB::raw( 'luck +'.$decreaseMount )
                ]);
            }else{
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                ]);
            }



            /////insert record to user_match table for this bet
            $request['user_1_id']=$user->user_id;
            $request['user_2_id']=$request['opponent_id'];
            $request['user_universal_match_values']=$request['chosen_category_id'];
            $request['is_ended']=null;
            $request['user_1_correct_count']=0;
            $request['user_2_correct_count']=0;
            $request['type']=$request['type'];
            $request['opponent_type']=$request['opponent_type'];

            $user_matchs_insert=new UserUniversalMatch();
            $user_matchs_insert->intializeByRequest($request);
            $iiid=$user_matchs_insert->store();


            DB::commit();
            return json_encode(['user_universal_match_id'=>$iiid,'type'=>$request['type'],'opponent_type'=>$request['opponent_type'],'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
    }
    //for general and cross table game
    public function random_question_match_Status( $user_universal_match,User $user,$token,Request $request){
        if($user_universal_match!=false)
        {
            //check that time out of 1000 second return winner match
            if(Carbon::now()->subSeconds(1000)>$user_universal_match->created_at && $user_universal_match->is_ended == 0){
                UserUniversalMatch::set_user_match_after_15min($request['user_universal_match_id']);
                $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
            }


            if($user_universal_match->user_1_id==$user->user_id){
                if($user_universal_match->bet != 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
                if($user_universal_match->bet == 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_nitro_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
//                    $winner_id = UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
//                    if($user_match->user_1_spent_time==null || $user_match->user_1_spent_time==-1 ){
//                    UserUniversalMatch::update_time_match($request['user_universal_match_id'],$request['match_guid'],1);
//                    }
                if($user_universal_match->user_2_spent_time != -1  || $user_universal_match->user_2_id < 0){
                    ////is ended game
                    $winner_id=UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
                    $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                    if($winner_id==1){
                        ////user_1  player  is winner
                        $winner_code=1;
                        $opponent=[];

                        $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'bet'=>$user_universal_match->bet,'is_ended'=>$is_ended,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                    elseif ($winner_id==2){
                        ////user_2  player  is winner
                        $winner_code=2;
                        $opponent=[];

                        $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                    else{
                        //// players is equal score
                        $opponent=[];
                        $winner_code=3;
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                }
                else{
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }

            }
            elseif ($user_universal_match->user_2_id==$user->user_id){
                if($user_universal_match->user_1_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
                if($user_universal_match->bet == 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_nitro_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
//                    $winner_id = UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
//                    if($user_match->user_2_spent_time==null || $user_match->user_2_spent_time==-1){
//                        UserUniversalMatch::update_time_match($request['user_universal_match_id'],$request['match_guid'],2);
//                    }
                if($user_universal_match->user_1_spent_time != -1 ||  $user_universal_match->user_1_id < 0){
                    ////is ended game
                    $winner_id=UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
                    $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                    if($winner_id==1){
                        ////user_1  player  is winner
                        $winner_code=2;
                        $opponent=[];

                        $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                        $is_ended=1;


                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }elseif ($winner_id==2){
                        ////user_2  player  is winner

                        $opponent=[];
                        $winner_code=1;

                        $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;;
                        $is_ended=1;

                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }else{
                        //// players is equal score
                        $opponent=[];
                        $winner_code=3;
                        $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_1_id))->first();
                        if($temp->user_name!=null)
                            $opponent['user_name'] = $temp->user_name;
                        else
                            $opponent['user_name'] = $temp->visitor_user_name;
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                }
                else{
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
            }
            else
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
        }
        else{
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
        }

    }

    public function NONRANDOM_or_FRIEND_question_match_Status( $user_universal_match,User $user,$token,Request $request){
       if($user->user_id==$user_universal_match->user_2_id && $user_universal_match->user_2_spent_time==null){
            DB::beginTransaction();
           if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->hazel && $user_universal_match->bet != 'u'){
               DB::commit();
               return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
           }
           if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->luck && $user_universal_match->bet == 'u'){
               DB::commit();
               return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
           }
            $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,$request['chosen_category_id']);
            $user_universal_match->user_2_spent_time=-1;
            $user_universal_match->is_ended=0;
            $user_universal_match->save();

            ///get question Object
//                    $q_id=$request['questions'];

            $questions=DB::table('question')
                ->select('question_id','description','category_id','answer','penalty')
                ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                ->get();


            ////get image file if question has image
            for($index=0;$index<count($questions);$index++){
                if((strpos($questions[$index]->description, '#')) !== false){
                    $question =new Question();
                    $image=$question->getQuestionImage($questions[$index]->question_id);
                    $questions[$index]->image=$image;
                }
            }


            $enemy_user=DB::table('user')
                ->select('user.luck','user.image','user.user_name','user.visitor_user_name'
//                    ,'total_match_count','win_match_count'
                )
                ->where('user.user_id', $user_universal_match->user_1_id)
                ->get();



            ///// update  and hazel  for second player
            /////new hazel= old hazel - bet;
            $decreaseMount=UserUniversalMatch::translate_bet($user_universal_match->bet)*-1;
            $user = User::findById($user->user_id);
            if($user_universal_match->bet == 'u'){
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'luck' => \DB::raw( 'luck +'.$decreaseMount )
                ]);
            }else{
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                ]);
            }

            if($enemy_user[0]->user_name!=null)
                $enemy_user[0]->user_name=$enemy_user[0]->user_name;
            else
                $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
            $opponent=$enemy_user[0];
            DB::commit();
            return json_encode(['is_ended'=>0,'bet'=>$user_universal_match->bet,'user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);


        }
       if($user->user_id==$user_universal_match->user_1_id && $user_universal_match->user_1_spent_time==null){
           DB::beginTransaction();
           if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->hazel && $user_universal_match->bet != 'u'){
               DB::commit();
               return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
           }
           if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->luck && $user_universal_match->bet == 'u'){
               DB::commit();
               return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
           }
//            $user_universal_match->user_universal_match_values=Question::get_10_Random_question($user_universal_match->user_universal_match_values,$request['chosen_category_id']);
            $user_universal_match->user_1_spent_time=-1;
            $user_universal_match->is_ended=0;
            $user_universal_match->save();

            ///get question Object
//                    $q_id=$request['questions'];

            $questions=DB::table('question')
                ->select('question_id','description','category_id','answer','penalty')
                ->whereIn('question_id', explode(",",$user_universal_match->user_universal_match_values))
                ->orderByRaw("FIELD(question_id , ".$user_universal_match->user_universal_match_values.") ASC")
                ->get();


            ////get image file if question has image
            for($index=0;$index<count($questions);$index++){
                if((strpos($questions[$index]->description, '#')) !== false){
                    $question =new Question();
                    $image=$question->getQuestionImage($questions[$index]->question_id);
                    $questions[$index]->image=$image;
                }
            }


            $enemy_user=DB::table('user')
                ->select('user.luck','user.image','user.user_name','user.visitor_user_name'
//                    ,'total_match_count','win_match_count'
                )
                ->where('user.user_id', $user_universal_match->user_2_id)
                ->get();


            if($enemy_user[0]->user_name!=null)
                $enemy_user[0]->user_name=$enemy_user[0]->user_name;
            else
                $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
            $opponent=$enemy_user[0];
            DB::commit();
            return json_encode(['is_ended'=>0,'bet'=>$user_universal_match->bet,'user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'values'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);


        }
       if($user_universal_match!=false)
        {
            if($user_universal_match->user_1_id==$user->user_id){
                if($user_universal_match->bet != 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
                if($user_universal_match->bet == 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_nitro_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
//                    $winner_id = UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
//                    if($user_match->user_1_spent_time==null || $user_match->user_1_spent_time==-1 ){
//                        UserUniversalMatch::update_time_match($request['user_universal_match_id'],$request['match_guid'],1);
//                    }
                if($user_universal_match->user_1_spent_time != -1  && $user_universal_match->user_2_spent_time != -1  && $user_universal_match->user_2_spent_time != null){
                    ////is ended game
                    $winner_id=UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
                    $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                    if($winner_id==1){
                        ////user_1  player  is winner
                        $winner_code=1;
                        $opponent=[];

                        $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'bet'=>$user_universal_match->bet,'is_ended'=>$is_ended,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                    elseif ($winner_id==2){
                        ////user_2  player  is winner
                        $winner_code=2;
                        $opponent=[];

                        $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                    else{
                        //// players is equal score
                        $opponent=[];
                        $winner_code=3;
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                }
                else{
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }

            }
            elseif ($user_universal_match->user_2_id==$user->user_id){
                if($user_universal_match->user_1_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
                if($user_universal_match->bet == 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_nitro_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
//                    $winner_id = UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
//                    if($user_match->user_2_spent_time==null || $user_match->user_2_spent_time==-1){
//                        UserUniversalMatch::update_time_match($request['user_universal_match_id'],$request['match_guid'],2);
//                    }
                if($user_universal_match->user_2_spent_time != -1  && $user_universal_match->user_1_spent_time != -1 &&  $user_universal_match->user_1_spent_time != null){
                    ////is ended game
                    $winner_id=UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
                    $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                    if($winner_id==1){
                        ////user_1  player  is winner
                        $winner_code=2;
                        $opponent=[];

                        $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                        $is_ended=1;


                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }elseif ($winner_id==2){
                        ////user_2  player  is winner

                        $opponent=[];
                        $winner_code=1;

                        $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;;
                        $is_ended=1;

                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }else{
                        //// players is equal score
                        $opponent=[];
                        $winner_code=3;
                        $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_1_id))->first();
                        if($temp->user_name!=null)
                            $opponent['user_name'] = $temp->user_name;
                        else
                            $opponent['user_name'] = $temp->visitor_user_name;
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                }
                else{
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
            }
            else
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
        }
       else{
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
       }
    }


    ///for cross table game
    public function random_crosstable_match_request(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('request_count') || !$request->has('bet')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

        }
        ////////////////////////////////////////////

        try
        {
            DB::beginTransaction();


            if(UserUniversalMatch::translate_bet($request['bet'])>$user->hazel && $request['bet'] != 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }
            if(UserUniversalMatch::translate_bet($request['bet'])>$user->luck && $request['bet'] == 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }


            if($request['request_count']==1){
                ///check it==>> if this user exist  in previous game is wait not insert extra game for current user
                $UserUniversalMatch = DB::table('user_universal_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('user_2_id', null)
                    ->where('user_1_id', '=',$user->user_id)
                    ->where('type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE)
                    ->where('opponent_type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                    ->where('deleted_at',NULL)
                    ->orderBy('user_universal_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();
                if(count($UserUniversalMatch)!=0){
                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$UserUniversalMatch->user_universal_match_id,'type'=>$UserUniversalMatch->type,'opponent_type'=>$UserUniversalMatch->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }



                //////////////////////////get current game is wait player
                $UserUniversalMatch = DB::table('user_universal_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('user_2_id', null)
                    ->where('user_1_id', '<>',$user->user_id)
                    ->where('type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE)
                    ->where('opponent_type', '=',UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                    ->where('deleted_at',NULL)
                    ->orderBy('user_universal_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();

                /////////if UserUniversalMatch==null => there aren't any user for do game
                if(count($UserUniversalMatch)==0){
                    /////insert record to user_match table for this bet
                    $request['user_1_id']=$user->user_id;
                    $request['user_universal_match_values']=Question::get_Random_crosstable();
                    $request['is_ended']=0;
                    $request['user_1_correct_count']=0;
                    $request['user_2_correct_count']=0;
                    $request['user_1_spent_time']=NULL;
                    $request['user_2_spent_time']=Null;
                    $request['type']=UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE;
                    $request['opponent_type']=UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM;

                    $user_matchs_insert=new UserUniversalMatch();
                    $user_matchs_insert->intializeByRequest($request);
                    $user_matchs_insert->store();



                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_matchs_insert->user_universal_match_id,'type'=>$user_matchs_insert->type,'opponent_type'=>$user_matchs_insert->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }
                else{

                    if($UserUniversalMatch->user_1_id==$user->user_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم" );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = userUniversalMatch::withTrashed()->where('user_universal_match_id', $UserUniversalMatch->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['user_universal_match_id'=>$UserUniversalMatch->user_universal_match_id,'type'=>$UserUniversalMatch->type,'opponent_type'=>$UserUniversalMatch->opponent_type,'error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    ////////////////////fill this record
                    ////////////////////insert second person to user_2_id and return opponent array


                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $UserUniversalMatch->user_universal_match_id)
                        ->update(['user_2_id' => $user->user_id,
                            'user_2_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$UserUniversalMatch->user_universal_match_values
                        ]);

                    ///get question Object
//                    $q_id=$request['questions'];
                    $temp_Question=Question::get_crosstable_data($UserUniversalMatch->user_universal_match_values);
                    $questions=$temp_Question['questions'];

                    $enemy_user=DB::table('user')
//                        ->join('user_category', 'user_category.user_id', '=', 'user.user_id')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id', $UserUniversalMatch->user_1_id)
//                        ->where('user_category.user_id', $UserUniversalMatch->user_1_id)
//                        ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                        ->havingRaw('user_category.user_id='.$UserUniversalMatch->user_1_id)
                        ->get();



                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet'] == 'u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }

                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];
                    DB::commit();
                    return json_encode([
                        'user_universal_match_id'=>$UserUniversalMatch->user_universal_match_id,
                        'type'=>$UserUniversalMatch->type,
                        'opponent_type'=>$UserUniversalMatch->opponent_type,
                        'questions'=>$questions,
                        'cross_table_id'=>$temp_Question['cross_table_id'],
                        'width' => $temp_Question['width'],
                        'height' => $temp_Question['height'],
                        'opponent'=>$opponent,
                        'token' => $token,
                        'error' => 0,
                        'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST
                    ]);
                }
            }
            else if($request['request_count']>1){
                //////////////////////////get current game is wait player
                $user_universal_match = DB::table('user_universal_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('is_ended', '=' , 0)
                    ->where('type', UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE)
                    ->where('opponent_type', '=' , UserUniversalMatch::USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM)
                    ->where('deleted_at', '=' , null)
                    ->where('user_1_id', '=',$user->user_id)
                    ->orWhere('user_2_id', '=',$user->user_id)
                    ->orderBy('user_universal_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();

                if (count($user_universal_match) == 0)
                {

                    DB::commit();
                    return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE,'line'=>'268_api_user_universalmatchController', 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }

                if($request['request_count'] == 8 && $user_universal_match->user_2_id == null){
//                        return 'return bot';
//                        User::get_random_user();
                    $chance=rand(1,100);
					//TODO: meysam - must comment
//                    $chance = 0;
                    /////////////////////////////////////////
                    if($request['request_count']==8 && $chance<40){//$chance<40
                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }


//                        $rand_user=User::get_random_user($user->user_id);
                    $rand_user=User::get_random_bot_user($user->user_id);
                    $enemy_user=[];
                    $enemy_user['image']=$rand_user[0]->image;
                    if($rand_user[0]->user_name==null)
                        $enemy_user['user_name']=$rand_user[0]->visitor_user_name;
                    else
                        $enemy_user['user_name']=$rand_user[0]->user_name;
                    $enemy_user['luck']=rand(2200,4000);
                    $enemy_user['allowChat']=0;
//                    $enemy_user['total_match_count']=rand(100,300);
//                    $enemy_user['win_match_count']=rand(1,100);

                    $user_universal_match->user_universal_match_values=Question::get_Random_crosstable();

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_universal_match->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id' , $user_universal_match->user_universal_match_id)
                        ->update(['user_2_id' => $rand_user[0]->user_id *-1,
                            'user_2_spent_time'=>-1,
                            'user_1_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);

                    $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
                    $questions=$temp_Question['questions'];



                    $opponent=$enemy_user;

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet'] == 'u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }
                    DB::commit();
                    return json_encode([
                        'user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                        'type'=>$user_universal_match->type,
                        'opponent_type'=>$user_universal_match->opponent_type,
                        'questions'=>$questions,
                        'cross_table_id'=>$temp_Question['cross_table_id'],
                        'width' => $temp_Question['width'],
                        'height' => $temp_Question['height'],
                        'opponent'=>$opponent,
                        'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }

                if($user_universal_match->user_1_id==$user->user_id && $user_universal_match->user_2_spent_time==-1)
                {

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet']=='u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }


                    if($user_universal_match->user_universal_match_values>= -1 && $user_universal_match->user_universal_match_values <= Category::Category_Count)
                        $user_universal_match->user_universal_match_values=Question::get_Random_crosstable();


                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                        ->update(['user_1_id' => $user->user_id,
                            'user_1_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);


                    ///get question Object
//                    $q_id=$request['questions'];
                    $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
                    $questions=$temp_Question['questions'];

                    $enemy_user=DB::table('user')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id',abs($user_universal_match->user_2_id))
                        ->get();

                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];

                    $user_is_equal=DB::table('user_universal_match')->select('*')->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_1_id==$user_is_equal->user_2_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                        'type'=>$user_universal_match->type,
                        'opponent_type'=>$user_universal_match->opponent_type,
                        'questions'=>$questions,
                        'cross_table_id'=>$temp_Question['cross_table_id'],
                        'width' => $temp_Question['width'],
                        'height' => $temp_Question['height'],
                        'opponent'=>$opponent,
                        'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }

                if($user_universal_match->user_2_id==$user->user_id && $user_universal_match->user_1_spent_time==-1)
                {

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet']=='u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }


                    if($user_universal_match->user_universal_match_values>= -1 && $user_universal_match->user_universal_match_values<=Category::Category_Count)
                        $user_universal_match->user_universal_match_values=Question::get_Random_crosstable();

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_universal_match->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                        ->update(['user_1_id' => $user->user_id,
                            'user_1_spent_time'=>-1,
                            'is_ended'=>0,
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);
                    ///get question Object
//                    $q_id=$request['questions'];

                    $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
                    $questions=$temp_Question['questions'];


                    $enemy_user=DB::table('user')
//                        ->join('user_category', 'user_category.user_id', '=', 'user.user_id')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id', $user_universal_match->user_1_id)
//                        ->where('user_category.user_id', $user_universal_match->user_1_id)
//                        ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                        ->havingRaw('user_category.user_id='.$user_universal_match->user_1_id)
                        ->get();



                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];


                    $user_is_equal=DB::table('user_universal_match')->select('*')->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_1_id==$user_is_equal->user_2_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                        'type'=>$user_universal_match->type,
                        'opponent_type'=>$user_universal_match->opponent_type,
                        'questions'=>$questions,
                        'cross_table_id'=>$temp_Question['cross_table_id'],
                        'width' => $temp_Question['width'],
                        'height' => $temp_Question['height'],
                        'opponent'=>$opponent,
                        'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

                }

                if($user_universal_match->user_2_id !=null){
                    if($user_universal_match->user_universal_match_values>= -1 && $user_universal_match->user_universal_match_values<=Category::Category_Count)
                        $user_universal_match->user_universal_match_values=Question::get_Random_crosstable();


                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_universal_match->user_universal_match_values='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_universal_match')
                        ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                        ->update([
                            'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                        ]);

                    $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
                    $questions=$temp_Question['questions'];

                    $opponent=DB::table('user')
//                            ->join('user_category', 'user_category.user_id', '=', 'user.user_id')
                        ->select('user.luck','user.image','user.user_name','user.visitor_user_name','chat_allow as allowChat'
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id', $user_universal_match->user_1_id)
//                            ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                            ->havingRaw('user_category.user_id='.$user_universal_match->user_1_id)
                        ->get();

                    if($opponent[0]->user_name!=null)
                        $opponent[0]->user_name=$opponent[0]->user_name;
                    else
                        $opponent[0]->user_name=$opponent[0]->visitor_user_name;

                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                    $user = User::findById($user->user_id);
                    if($request['bet']=='u'){
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'luck' => \DB::raw( 'luck +'.$decreaseMount )
                        ]);
                    }else{
                        $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                            'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                        ]);
                    }


                    $user_is_equal=DB::table('user_universal_match')->select('*')->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_1_id==$user_is_equal->user_2_id){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserUniversalMatch::withTrashed()->where('user_universal_match_id', $user_universal_match->user_universal_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }


                    DB::commit();
                    return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                        'type'=>$user_universal_match->type,
                        'opponent_type'=>$user_universal_match->opponent_type,
                        'questions'=>$questions,
                        'cross_table_id'=>$temp_Question['cross_table_id'],
                        'width' => $temp_Question['width'],
                        'height' => $temp_Question['height'],
                        'opponent'=>$opponent[0],'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                }

                if($user_universal_match->user_2_id ==null){
                    $chance=rand(1,100);
                    //TODO: meysam - must comment
//                    $chance = 100;
                    /////////////////////////////////////////

                    if($request['request_count']==2 && $chance>8){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==3 && $chance>9){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==4 && $chance>10){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==5 && $chance>11){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==6 && $chance>12){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==7 && $chance>13 || $request['request_count']==8){
                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
                    }
                    else{
                        //return bot in 62% but in 38% percent get nothing!!!!!!!!!!!!!!
                        $rand_user=User::get_random_bot_user($user->user_id);
                        $enemy_user=[];
                        $enemy_user['image']=$rand_user[0]->image;
                        if($rand_user[0]->user_name==null)
                            $enemy_user['user_name']=$rand_user[0]->visitor_user_name;
                        else
                            $enemy_user['user_name']=$rand_user[0]->user_name;
                        $enemy_user['luck']=rand(2200,4000);
                        $enemy_user['allowChat']=0;
//                        $enemy_user['total_match_count']=rand(100,300);
//                        $enemy_user['win_match_count']=rand(1,100);

                        $user_universal_match->user_universal_match_values=Question::get_Random_crosstable();

                        ////for test in user11
//                        $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                        if($myUser != null && $myUser->user_name=='user11'){
//                            $user_universal_match->questions='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                        }
                        //////end

                        DB::table('user_universal_match')
                            ->where('user_universal_match_id', $user_universal_match->user_universal_match_id)
                            ->update(['user_2_id' => $rand_user[0]->user_id *-1,
                                'user_2_spent_time'=>-1,
                                'user_1_spent_time'=>-1,
                                'user_universal_match_values'=>$user_universal_match->user_universal_match_values
                            ]);

                        $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
                        $questions=$temp_Question['questions'];



                        ///// update  and hazel  for first player
                        /////new hazel= old hazel - bet;
                        $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
                        $user = User::findById($user->user_id);
                        if($request['bet'] == 'u'){
                            $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                                'luck' => \DB::raw( 'luck +'.$decreaseMount )
                            ]);
                        }else{
                            $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                                'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                            ]);
                        }
                        DB::commit();

                        return json_encode(['user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                            'type'=>$user_universal_match->type,
                            'opponent_type'=>$user_universal_match->opponent_type,
                            'questions'=>$questions,
                            'cross_table_id'=>$temp_Question['cross_table_id'],
                            'width' => $temp_Question['width'],
                            'height' => $temp_Question['height'],
                            'opponent'=>$enemy_user,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);


                    }



                }
            }
        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
    }

    public function NONRANDOM_or_FRIEND_crosstable_match_request(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation

        if (!$request->has('tag') || !$request->has('opponent_id') || !$request->has('bet')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
        ////////////////////////////////////////////

        try
        {
            DB::beginTransaction();
            if(UserUniversalMatch::translate_bet($request['bet'])>$user->hazel && $request['bet'] != 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }
            if(UserUniversalMatch::translate_bet($request['bet'])>$user->luck && $request['bet'] == 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
            }

            ///// update  and hazel  for first player
            /////new hazel= old hazel - bet;
            $decreaseMount=UserUniversalMatch::translate_bet($request['bet'])*-1;
            $user = User::findById($user->user_id);
            if($request['bet'] == 'u'){
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'luck' => \DB::raw( 'luck +'.$decreaseMount )
                ]);
            }else{
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                ]);
            }



            /////insert record to user_match table for this bet
            $request['user_1_id']=$user->user_id;
            $request['user_2_id']=$request['opponent_id'];
            $request['user_universal_match_values']=Question::get_Random_crosstable();
            $request['is_ended']=null;
            $request['user_1_correct_count']=0;
            $request['user_2_correct_count']=0;
            $request['type']=$request['type'];
            $request['opponent_type']=$request['opponent_type'];

            $user_matchs_insert=new UserUniversalMatch();
            $user_matchs_insert->intializeByRequest($request);
            $iiid=$user_matchs_insert->store();


            DB::commit();
            return json_encode(['user_universal_match_id'=>$iiid,'type'=>$request['type'],'opponent_type'=>$request['opponent_type'],'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);

        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_REQUEST]);
        }
    }

    public function NONRANDOM_or_FRIEND_crosstable_match_Status($user_universal_match,User $user,$token,Request $request){

        if($user->user_id==$user_universal_match->user_2_id && $user_universal_match->user_2_spent_time==null){
            DB::beginTransaction();
            if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->hazel && $user_universal_match->bet != 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
            }
            if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->luck && $user_universal_match->bet == 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
            }
            $user_universal_match->user_universal_match_values=Question::get_Random_crosstable($user_universal_match->user_universal_match_values,$request['chosen_category_id']);
            $user_universal_match->user_2_spent_time=-1;
            $user_universal_match->is_ended=0;
            $user_universal_match->save();

            ///get question Object
            $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
            $questions=$temp_Question['questions'];


            $enemy_user=DB::table('user')
                ->select('user.luck','user.image','user.user_name','user.visitor_user_name'
//                    ,'total_match_count','win_match_count'
                )
                ->where('user.user_id', $user_universal_match->user_1_id)
                ->get();



            ///// update  and hazel  for second playe
            /////new hazel= old hazel - bet;
            $decreaseMount=UserUniversalMatch::translate_bet($user_universal_match->bet)*-1;
            $user = User::findById($user->user_id);
            if($user_universal_match->bet == 'u'){
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'luck' => \DB::raw( 'luck +'.$decreaseMount )
                ]);
            }else{
                $user->newQuery()->where(['user_id'=> $user->user_id])->update([
                    'hazel' => \DB::raw( 'hazel +'.$decreaseMount )
                ]);
            }

            if($enemy_user[0]->user_name!=null)
                $enemy_user[0]->user_name=$enemy_user[0]->user_name;
            else
                $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
            $opponent=$enemy_user[0];
            DB::commit();
            return json_encode([
                'is_ended'=>0,
                'bet'=>$user_universal_match->bet,
                'user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                'type'=>$user_universal_match->type,
                'opponent_type'=>$user_universal_match->opponent_type,
                'questions'=>$questions,
                'cross_table_id'=>$temp_Question['cross_table_id'],
                'width' => $temp_Question['width'],
                'height' => $temp_Question['height'],
                'opponent'=>$opponent,
                'token' => $token, 'error' => 0,
                'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS
            ]);


        }
        if($user->user_id==$user_universal_match->user_1_id && $user_universal_match->user_1_spent_time==null){
            DB::beginTransaction();
            if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->hazel && $user_universal_match->bet != 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
            }
            if(UserUniversalMatch::translate_bet($user_universal_match->bet)>$user->luck && $user_universal_match->bet == 'u'){
                DB::commit();
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
            }
            $user_universal_match->user_1_spent_time=-1;
            $user_universal_match->is_ended=0;
            $user_universal_match->save();

            ///get question Object
            $temp_Question=Question::get_crosstable_data($user_universal_match->user_universal_match_values);
            $questions=$temp_Question['questions'];

            $enemy_user=DB::table('user')
                ->select('user.luck','user.image','user.user_name','user.visitor_user_name'
//                    ,'total_match_count','win_match_count'
                )
                ->where('user.user_id', $user_universal_match->user_2_id)
                ->get();



            if($enemy_user[0]->user_name!=null)
                $enemy_user[0]->user_name=$enemy_user[0]->user_name;
            else
                $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
            $opponent=$enemy_user[0];
            DB::commit();
            return json_encode(
            [
                'is_ended'=>0,
                'bet'=>$user_universal_match->bet,
                'user_universal_match_id'=>$user_universal_match->user_universal_match_id,
                'type'=>$user_universal_match->type,
                'opponent_type'=>$user_universal_match->opponent_type,
                'questions'=>$questions,
                'cross_table_id'=>$temp_Question['cross_table_id'],
                'width' => $temp_Question['width'],
                'height' => $temp_Question['height'],
                'opponent'=>$opponent,
                'token' => $token,
                'error' => 0,
                'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS
            ]);


        }
        if($user_universal_match!=false)
        {
            if($user_universal_match->user_1_id==$user->user_id){
                if($user_universal_match->bet != 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
                if($user_universal_match->bet == 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_nitro_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
//                    $winner_id = UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
//                    if($user_match->user_1_spent_time==null || $user_match->user_1_spent_time==-1 ){
//                        UserUniversalMatch::update_time_match($request['user_universal_match_id'],$request['match_guid'],1);
//                    }
                if($user_universal_match->user_1_spent_time != -1  && $user_universal_match->user_2_spent_time != -1  && $user_universal_match->user_2_spent_time != null){
                    ////is ended game

                    $winner_id=UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
                    $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                    if($winner_id==1){
                        ////user_1  player  is winner
                        $winner_code=1;
                        $opponent=[];

                        $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'bet'=>$user_universal_match->bet,'is_ended'=>$is_ended,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                    elseif ($winner_id==2){
                        ////user_2  player  is winner
                        $winner_code=2;
                        $opponent=[];

                        $user_universal_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                    else{
                        //// players is equal score
                        $opponent=[];
                        $winner_code=3;
                        $opponent['opponent_correct_count']=$user_universal_match->user_2_correct_count;
                        $opponent['opponent_spent_time']=$user_universal_match->user_2_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                }
                else{
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }

            }
            elseif ($user_universal_match->user_2_id==$user->user_id){
                if($user_universal_match->user_1_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
                if($user_universal_match->bet == 'u' && $user_universal_match->user_2_id < 0 && Carbon::now()->subSecond(UserUniversalMatch::min_robat_after_start_nitro_match) < $user_universal_match->created_at){
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
//                    $winner_id = UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
//                    if($user_match->user_2_spent_time==null || $user_match->user_2_spent_time==-1){
//                        UserUniversalMatch::update_time_match($request['user_universal_match_id'],$request['match_guid'],2);
//                    }
                if($user_universal_match->user_2_spent_time != -1  && $user_universal_match->user_1_spent_time != -1 &&  $user_universal_match->user_1_spent_time != null){
                    ////is ended game
                    $winner_id=UserUniversalMatch::get_winner_id($request['user_universal_match_id'],false);
                    $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                    if($winner_id==1){
                        ////user_1  player  is winner
                        $winner_code=2;
                        $opponent=[];

                        $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                        $is_ended=1;


                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }elseif ($winner_id==2){
                        ////user_2  player  is winner

                        $opponent=[];
                        $winner_code=1;

                        $user_match=UserUniversalMatch::findById($request['user_universal_match_id']);
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;;
                        $is_ended=1;

                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }else{
                        //// players is equal score
                        $opponent=[];
                        $winner_code=3;
                        $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_1_id))->first();
                        if($temp->user_name!=null)
                            $opponent['user_name'] = $temp->user_name;
                        else
                            $opponent['user_name'] = $temp->visitor_user_name;
                        $opponent['opponent_correct_count']=$user_match->user_1_correct_count;
                        $opponent['opponent_spent_time']=$user_match->user_1_spent_time;
                        $is_ended=1;
                        return json_encode(['opponent'=>$opponent,'winner_code'=>$winner_code,'is_ended'=>$is_ended,'bet'=>$user_universal_match->bet,'type'=>$user_universal_match->type,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                    }
                }
                else{
                    ////this game not end
                    $is_ended=0;
                    return json_encode(['is_ended'=>$is_ended,'type'=>$user_universal_match->type,'bet'=>$user_universal_match->bet,'opponent_type'=>$user_universal_match->opponent_type,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
                }
            }
            else
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
        }
        else{
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_UNIVERSAL_MATCH_STATUS]);
        }
    }
}

