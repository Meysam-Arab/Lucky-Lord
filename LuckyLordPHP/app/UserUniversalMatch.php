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

class UserUniversalMatch extends Model
{
    use SoftDeletes;

    //type
    const USER_UNIVERSAL_MATCH_TYPE_QUESTION=0;
    const USER_UNIVERSAL_MATCH_TYPE_DARHAMTABLE=1;
    const USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE=2;
    const USER_UNIVERSAL_MATCH_TYPE_GUESSWORD=3;
    const USER_UNIVERSAL_MATCH_TYPE_FINDWORD=4;


    //opponent type
    const USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM=1;
    const USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM=2;
    const USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND=3;


    const l = 100;
    const m = 500;
    const h = 1000;
    const u=10;
    const n=2000;
    const f=0;

    const l_key = "l";
    const m_key = "m";
    const h_key = "h";
    const f_key = "f";

    const luck_key = "u";
    const nitro_key = "n";

    const INTEREST = 5;//interest in %

    const win=1;
    const lost=2;
    const equal=3;

    const minAnswerBot=5;
    const maxAnswerBot=9;

    const Match_count_that_show_in_index_page=10;

    const Time_OF_Match=300;//second
    const Time_OF_NITRO_MATCH=60;//60 Seconds Default
    const min_robat_after_start_match=250;//second
    const min_robat_after_start_nitro_match=90;
    //
    protected $table = 'user_universal_match';
    protected $primaryKey = 'user_universal_match_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;
    public $incrementing = true;


    protected $fillable = ['user_1_id,user_2_id,user_1_correct_count,user_2_correct_count,user_1_spent_time,user_2_spent_time, type, opponent_type'];

    public function intialize()
    {
        $this -> user_universal_match_id = null;
        $this -> user_universal_match_guid = null;
        $this -> user_1_id = null;
        $this -> user_2_id = null;
        $this -> user_1_correct_count = null;
        $this -> user_2_correct_count = null;
        $this -> user_1_spent_time = null;
        $this -> user_2_spent_time = null;
        $this -> user_universal_match_values = null;
        $this -> type = null;
        $this -> is_ended = null;
        $this -> bet = null;
        $this -> opponent_type = null;
    }

    public function intializeByRequest(Request $request)
    {

        $this -> user_universal_match_id = $request ->input('user_universal_match_id');
        $this -> user_universal_match_guid = $request ->input('user_universal_match_guid');
        $this -> user_1_id = $request ->input('user_1_id');
        $this -> user_2_id = $request ->input('user_2_id');
        $this -> user_1_correct_count = $request ->input('user_1_correct_count');
        $this -> user_2_correct_count = $request ->input('user_2_correct_count');
        $this -> user_1_spent_time = $request ->input('user_1_spent_time');
        $this -> user_2_spent_time = $request ->input('user_2_spent_time');
        $this -> user_universal_match_values = $request ->input('user_universal_match_values');
        $this -> type = $request ->input('type');
        $this -> is_ended = $request ->input('is_ended');
        $this -> bet = $request ->input('bet');
        $this->opponent_type = $request ->input('opponent_type');

    }

    public function User()
    {
        return $this->belongsTo(User::class);
    }

    public function store()
    {
        $this->user_universal_match_guid = uniqid('',true);
        $this->save();
        return $this->user_universal_match_id;

    }

    public static function findByIdAndGuid($id,$guid)
    {
        $userUniversalMatch = new UserUniversalMatch();
        $query = $userUniversalMatch->newQuery();
        $query
            ->where('user_universal_match_id', 'like', $id)
            ->where('user_universal_match_guid', 'like', $guid);

        $userUniversalMatchs = $query->get();
        if (count($userUniversalMatchs) == 0){
            return false;
        }
        else{
            return $userUniversalMatchs[0];
        }
    }

    public static function findById($id)
    {
        $UserUniversalMatch = new UserUniversalMatch();
        $query = $UserUniversalMatch->newQuery();
        $query
            ->where('user_universal_match_id', 'like', $id);
//        $UserUniversalMatchs = DB::table('user_universal_match')->where('user_universal_match_id',$id)->get();
        $UserUniversalMatchs = $query->get();

        if (count($UserUniversalMatchs) == 0){
            return false;
        }
        else{
            return $UserUniversalMatchs[0];
        }
    }

    public function edit(Request $request)
    {

        $oldUniversalMatch = self::findById( $this -> user_universal_match_id);

        if($request -> has('user_1_correct_count'))
            $oldUniversalMatch->user_1_correct_count=$request -> input('user_1_correct_count');

        if($request -> has('user_2_correct_count'))
            $oldUniversalMatch->user_2_correct_count=$request -> input('user_2_correct_count');

        if($request -> has('user_1_spent_time'))
            $oldUniversalMatch->user_1_spent_time=$request -> input('user_1_spent_time');

        if($request -> has('user_2_spent_time'))
            $oldUniversalMatch->user_2_spent_time=$request -> input('user_2_spent_time');

        if($request -> has('is_ended'))
            $oldUniversalMatch->is_ended=$request -> input('is_ended');


        $oldUniversalMatch->save();

    }

    public static function translate_bet($bet){
        if($bet=='l')
            return self::l;
        if($bet=='m')
            return self::m;
        if($bet=='h')
            return self::h;
        if($bet == 'u')
            return self::u;
        if($bet == 'f')
            return self::f;

        if($bet=='n')
            return self::n;
    }

    public static function set_user_match_after_15min($id){
        $UserUniversalMatch = new UserUniversalMatch();
        $query = $UserUniversalMatch->newQuery();
        $query->where('user_universal_match_id', 'like', $id);
        $UserUniversalMatchs= $query->get();

//        $UserUniversalMatchs = DB::table('user_universal_match')->where('user_universal_match_id',$id)->get();

//        $UserUniversalMatchs =new UserUniversalMatch(DB::table('user_universal_match')->where('user_universal_match_id',$id)->get()) ;
        if(($UserUniversalMatchs[0]->user_1_spent_time== -1 || $UserUniversalMatchs[0]->user_1_spent_time== null) && $UserUniversalMatchs[0]->user_1_id > 0){
            DB::table('user_universal_match')
                ->where('user_universal_match_id', $id)
                ->update([
                    'user_1_spent_time' => self::Time_OF_Match,
                    'user_1_correct_count' => 0,
                    'is_ended' => 1,
                ]);
        }
        if(($UserUniversalMatchs[0]->user_2_spent_time == -1 || $UserUniversalMatchs[0]->user_2_spent_time == null) && $UserUniversalMatchs[0]->user_2_id > 0){
            DB::table('user_universal_match')
                ->where('user_universal_match_id', $id)
                ->update([
                    'user_2_spent_time' => self::Time_OF_Match,
                    'user_2_correct_count' => 0,
                    'is_ended' => 1,
                ]);
        }



    }

    public static function get_winner_id($match_id,$set_Total_match_count){
        $UserUniversalMatch = new UserUniversalMatch();
        $query = $UserUniversalMatch->newQuery();
        $query->where('user_universal_match_id', 'like', $match_id);

        $UserUniversalMatchs = $query->get();
//        $UserUniversalMatchs = DB::table('user_universal_match')->where('user_universal_match_id',$match_id)->get();
        if($UserUniversalMatchs[0]->user_1_id<0){
            ////this is robot
            if($UserUniversalMatchs[0]->bet=='n')
                $second_spent_id_1=rand(55,60);
            else
                $second_spent_id_1=rand(100,300);
            if($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                $user_1_correct_count=rand(UserUniversalMatch::minAnswerBot,UserUniversalMatch::maxAnswerBot);
            elseif($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE){
                $tableTemp = DB::table('moteghate_table')
                    ->where('moteghate_table.moteghate_Table_id', '=', $UserUniversalMatchs[0]->user_universal_match_values)
                    ->get();
                $crosstable_question_count=count(explode(",",$tableTemp[0]->questions))-1;
                $user_1_correct_count=rand(floor(.8*$crosstable_question_count),$crosstable_question_count);
            }
            if($UserUniversalMatchs[0]->user_1_spent_time == -1)
                $UserUniversalMatchs[0]->user_1_spent_time=$second_spent_id_1;
            if($UserUniversalMatchs[0]->user_1_correct_count == 0)
                $UserUniversalMatchs[0]->user_1_correct_count=$user_1_correct_count;
        }else{
            $second_spent_id_1=$UserUniversalMatchs[0]->user_1_spent_time;
            $user_1_correct_count=$UserUniversalMatchs[0]->user_1_correct_count;
        }

        if($UserUniversalMatchs[0]->user_2_id < 0){
            ////this is robot
            if($UserUniversalMatchs[0]->bet=='n')
                $second_spent_id_2=rand(55,60);
            else
                $second_spent_id_2=rand(100,300);
            if($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                $user_2_correct_count=rand(UserUniversalMatch::minAnswerBot,UserUniversalMatch::maxAnswerBot);
            elseif($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE){
                $tableTemp = DB::table('moteghate_table')
                    ->where('moteghate_table.moteghate_Table_id', '=', $UserUniversalMatchs[0]->user_universal_match_values)
                    ->get();
                $crosstable_question_count=count(explode(",",$tableTemp[0]->questions))-1;
                $user_2_correct_count=rand(floor(.8*$crosstable_question_count),$crosstable_question_count);
            }
            if($UserUniversalMatchs[0]->user_2_spent_time == -1 )
                $UserUniversalMatchs[0]->user_2_spent_time=$second_spent_id_2;
            if($UserUniversalMatchs[0]->user_2_correct_count == 0)
                $UserUniversalMatchs[0]->user_2_correct_count=$user_2_correct_count;


        }
        else{
            $second_spent_id_2=$UserUniversalMatchs[0]->user_2_spent_time;
            $user_2_correct_count=$UserUniversalMatchs[0]->user_2_correct_count;
        }


        if($UserUniversalMatchs[0]->user_2_spent_time ==-1 && $UserUniversalMatchs[0]->user_2_id >0)
            $UserUniversalMatchs[0]->user_2_spent_time =UserUniversalMatch::Time_OF_Match;
        if($UserUniversalMatchs[0]->user_1_spent_time ==-1 && $UserUniversalMatchs[0]->user_1_id >0)
            $UserUniversalMatchs[0]->user_1_spent_time =UserUniversalMatch::Time_OF_Match;


//        $score_id_1=(UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_1_spent_time/60) + $UserUniversalMatchs[0]->user_1_correct_count*2;
//        $score_id_2=(UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_2_spent_time/60) + $UserUniversalMatchs[0]->user_2_correct_count*2;
        $score_id_1= $UserUniversalMatchs[0]->user_1_correct_count;
        $score_id_2= $UserUniversalMatchs[0]->user_2_correct_count;

        if($score_id_1==$score_id_2 && $score_id_1!=0){
            $score_id_1=UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_1_spent_time/60;
            $score_id_2=UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_2_spent_time/60;
        }


        ////for set spent time match that user dont complete it and user_1_spent_time is null
        if($UserUniversalMatchs[0]->user_1_spent_time==0 && $UserUniversalMatchs[0]->bet=='n')
            $UserUniversalMatchs[0]->user_1_spent_time=UserUniversalMatch::Time_OF_NITRO_MATCH;
        if($UserUniversalMatchs[0]->user_1_spent_time==0 && $UserUniversalMatchs[0]->bet != 'n')
            $UserUniversalMatchs[0]->user_1_spent_time=UserUniversalMatch::Time_OF_Match;
        if($UserUniversalMatchs[0]->user_2_spent_time==0 && $UserUniversalMatchs[0]->bet=='n')
            $UserUniversalMatchs[0]->user_2_spent_time=UserUniversalMatch::Time_OF_NITRO_MATCH;
        if($UserUniversalMatchs[0]->user_2_spent_time==0 && $UserUniversalMatchs[0]->bet != 'n')
            $UserUniversalMatchs[0]->user_2_spent_time=UserUniversalMatch::Time_OF_Match;


        if($score_id_1>$score_id_2){
            if( $set_Total_match_count || $UserUniversalMatchs[0]->is_ended==0)
                self::setTotal_match_count_And_win_match_count_And_hazel($UserUniversalMatchs[0]->bet,$UserUniversalMatchs[0]->user_1_id,$UserUniversalMatchs[0]->user_2_id,$UserUniversalMatchs[0]->type);

            $UserUniversalMatchs[0]->winner_id=$UserUniversalMatchs[0]->user_1_id;
            $UserUniversalMatchs[0]->is_ended=1;

            $UserUniversalMatchs[0]->save();
            return 1;

        }
        else if($score_id_1<$score_id_2){
            if( $set_Total_match_count || $UserUniversalMatchs[0]->is_ended==0)
                self::setTotal_match_count_And_win_match_count_And_hazel($UserUniversalMatchs[0]->bet,$UserUniversalMatchs[0]->user_2_id,$UserUniversalMatchs[0]->user_1_id,$UserUniversalMatchs[0]->type);

            $UserUniversalMatchs[0]->winner_id=$UserUniversalMatchs[0]->user_2_id;
            $UserUniversalMatchs[0]->is_ended=1;
            $UserUniversalMatchs[0]->save();
            return 2;
        }
        else{
            self::setTotal_match_count_And_equal_match_count_And_hazel($UserUniversalMatchs[0]->bet,$UserUniversalMatchs[0]->user_2_id,$UserUniversalMatchs[0]->user_1_id,$UserUniversalMatchs[0]->type);

            $UserUniversalMatchs[0]->winner_id=0;
            $UserUniversalMatchs[0]->is_ended=1;
            $UserUniversalMatchs[0]->save();
            return 0;
        }


    }

    public static function get_winner_id_for_result($match_id,$set_Total_match_count,$time){
        $UserUniversalMatch = new UserUniversalMatch();
        $query = $UserUniversalMatch->newQuery();
        $query->where('user_universal_match_id', 'like', $match_id);

        $UserUniversalMatchs = $query->get();
        if($UserUniversalMatchs[0]->user_1_id<0){
            ////this is robot
            if($UserUniversalMatchs[0]->bet=='n')
                $second_spent_id_1=rand(55,60);
            else{
                $second_spent_id_1=rand(100,300);
                if($second_spent_id_1>$time){
                    if($time>50){
                        $second_spent_id_1=rand(50,$time);
                    }
                }
            }

            if($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                $user_1_correct_count=rand(UserUniversalMatch::minAnswerBot,UserUniversalMatch::maxAnswerBot);
            elseif($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE){
                $tableTemp = DB::table('moteghate_table')
                    ->where('moteghate_table.moteghate_Table_id', '=', $UserUniversalMatchs[0]->user_universal_match_values)
                    ->get();
                $crosstable_question_count=count(explode(",",$tableTemp[0]->questions))-1;
                $user_1_correct_count=rand(floor(.8*$crosstable_question_count),$crosstable_question_count);
            }
            if($UserUniversalMatchs[0]->user_1_spent_time == -1)
                $UserUniversalMatchs[0]->user_1_spent_time=$second_spent_id_1;
            if($UserUniversalMatchs[0]->user_1_correct_count == 0)
                $UserUniversalMatchs[0]->user_1_correct_count=$user_1_correct_count;
        }else{
            $second_spent_id_1=$UserUniversalMatchs[0]->user_1_spent_time;
            $user_1_correct_count=$UserUniversalMatchs[0]->user_1_correct_count;
        }

        if($UserUniversalMatchs[0]->user_2_id < 0){
            ////this is robot
            if($UserUniversalMatchs[0]->bet=='n')
                $second_spent_id_2=rand(55,60);
            else{
                $second_spent_id_2=rand(100,300);
                if($second_spent_id_2>$time){
                    if($time>50){
                        $second_spent_id_2=rand(50,$time);
                    }
                }
            }

            if($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_QUESTION)
                $user_2_correct_count=rand(UserUniversalMatch::minAnswerBot,UserUniversalMatch::maxAnswerBot);
            elseif($UserUniversalMatchs[0]->type==$UserUniversalMatch::USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE){
                $tableTemp = DB::table('moteghate_table')
                    ->where('moteghate_table.moteghate_Table_id', '=', $UserUniversalMatchs[0]->user_universal_match_values)
                    ->get();
                $crosstable_question_count=count(explode(",",$tableTemp[0]->questions))-1;
                $user_2_correct_count=rand(floor(.8*$crosstable_question_count),$crosstable_question_count);
            }
            if($UserUniversalMatchs[0]->user_2_spent_time == -1 )
                $UserUniversalMatchs[0]->user_2_spent_time=$second_spent_id_2;
            if($UserUniversalMatchs[0]->user_2_correct_count == 0)
                $UserUniversalMatchs[0]->user_2_correct_count=$user_2_correct_count;


        }
        else{
            $second_spent_id_2=$UserUniversalMatchs[0]->user_2_spent_time;
            $user_2_correct_count=$UserUniversalMatchs[0]->user_2_correct_count;
        }


        if($UserUniversalMatchs[0]->user_2_spent_time ==-1 && $UserUniversalMatchs[0]->user_2_id >0)
            $UserUniversalMatchs[0]->user_2_spent_time =UserUniversalMatch::Time_OF_Match;
        if($UserUniversalMatchs[0]->user_1_spent_time ==-1 && $UserUniversalMatchs[0]->user_1_id >0)
            $UserUniversalMatchs[0]->user_1_spent_time =UserUniversalMatch::Time_OF_Match;


//        $score_id_1=(UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_1_spent_time/60) + $UserUniversalMatchs[0]->user_1_correct_count*2;
//        $score_id_2=(UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_2_spent_time/60) + $UserUniversalMatchs[0]->user_2_correct_count*2;
        $score_id_1= $UserUniversalMatchs[0]->user_1_correct_count;
        $score_id_2= $UserUniversalMatchs[0]->user_2_correct_count;
        if($score_id_1==$score_id_2 && $score_id_1!=0){
            $score_id_1=UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_1_spent_time/60;
            $score_id_2=UserUniversalMatch::Time_OF_Match/60-$UserUniversalMatchs[0]->user_2_spent_time/60;
        }
        if($score_id_1>$score_id_2){
            if( $set_Total_match_count || $UserUniversalMatchs[0]->is_ended==0)
                self::setTotal_match_count_And_win_match_count_And_hazel($UserUniversalMatchs[0]->bet,$UserUniversalMatchs[0]->user_1_id,$UserUniversalMatchs[0]->user_2_id,$UserUniversalMatchs[0]->type);

            $UserUniversalMatchs[0]->winner_id=$UserUniversalMatchs[0]->user_1_id;
            $UserUniversalMatchs[0]->is_ended=1;
            $UserUniversalMatchs[0]->save();
            return 1;

        }
        else if($score_id_1<$score_id_2){
            if( $set_Total_match_count || $UserUniversalMatchs[0]->is_ended==0)
                self::setTotal_match_count_And_win_match_count_And_hazel($UserUniversalMatchs[0]->bet,$UserUniversalMatchs[0]->user_2_id,$UserUniversalMatchs[0]->user_1_id,$UserUniversalMatchs[0]->type);

            $UserUniversalMatchs[0]->winner_id=$UserUniversalMatchs[0]->user_2_id;
            $UserUniversalMatchs[0]->is_ended=1;
            $UserUniversalMatchs[0]->save();
            return 2;
        }
        else{
            self::setTotal_match_count_And_equal_match_count_And_hazel($UserUniversalMatchs[0]->bet,$UserUniversalMatchs[0]->user_2_id,$UserUniversalMatchs[0]->user_1_id,$UserUniversalMatchs[0]->type);

            $UserUniversalMatchs[0]->winner_id=0;
            $UserUniversalMatchs[0]->is_ended=1;
            $UserUniversalMatchs[0]->save();
            return 0;
        }


    }

    public static function update_time_match($id,$guid,$user_1_id){
        $UserUniversalMatch = new UserUniversalMatch();
        $query = $UserUniversalMatch->newQuery();
        $query
            ->where('user_universal_match_id', 'like', $id)
            ->where('user_universal_match_guid', 'like', $guid);

        $UserUniversalMatchs = $query->get();
        if($user_1_id==1){
            $UserUniversalMatchs[0]->user_1_spent_time=self::Time_OF_Match;
        }else{
            $UserUniversalMatchs[0]->user_2_spent_time=self::Time_OF_Match;
        }
        $UserUniversalMatchs[0]->save();
    }

    public static function setTotal_match_count_And_win_match_count_And_hazel($bet,$user_id,$loser_user_id,$type_of_match){
        $hazel_reward=1.9*self::translate_bet($bet);
        $user_for_setTotal_match=new User();


        if($bet=='u'){
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id])->update([
                'luck' => \DB::raw( 'luck +'.$hazel_reward )
            ]);

        }
        else{
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id])->update([
                'hazel' => \DB::raw( 'hazel +'.$hazel_reward )
            ]);
        }

        ///update statics in useruniversalmatchstatistics table for winner ond loser id

        DB::table('user_universal_match_statistics')
            ->where([
                'user_id'=> $user_id,
                'universal_match_type' => $type_of_match
            ])->update([
                'win_count' => \DB::raw( 'win_count + 1' )
            ]);

        DB::table('user_universal_match_statistics')
            ->where([
                'user_id'=> $loser_user_id,
                'universal_match_type' => $type_of_match
            ])->update([
                'lost_count' => \DB::raw( 'lost_count + 1' )
            ]);



        $query_for_getQuestion="SELECT sum(`win_count`) as match_win_count FROM `user_universal_match_statistics` WHERE user_id=:id";
        $stat = DB::select($query_for_getQuestion, ['id' => $user_id]);
        if($stat==100){
            //update cups of winner user if win_match_count==100
            $user_for_setTotal_match->newQuery()->where([
                ['user_id',"=", $user_id],
            ])->update([
                'cups' => \DB::raw("CONCAT(SUBSTRING(cups, 1, 2),REPLACE(SUBSTR(cups,3,1), '0', '1'),SUBSTRING(cups, 4, 6))")
            ]);
        }elseif ($stat==1000){
            //update cups of winner user if win_match_count==1000
            $user_for_setTotal_match->newQuery()->where([
                ['user_id',"=", $user_id],
            ])->update([
                'cups' => \DB::raw("CONCAT(SUBSTRING(cups, 1, 5),REPLACE(SUBSTR(cups,6,1), '0', '1'),SUBSTRING(cups, 7, 3))")
            ]);
        }else{

        }


    }

    public static function setTotal_match_count_And_equal_match_count_And_hazel($bet,$user_id1,$user_id2,$type_of_match){
        $hazel_reward=0.95*self::translate_bet($bet);
        $user_for_setTotal_match=new User();
        if($bet=='u'){
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id1])->update([
                'luck' => \DB::raw( 'luck +'.$hazel_reward )
            ]);

            $user_for_setTotal_match=new User();
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id2])->update([
                'luck' => \DB::raw( 'luck +'.$hazel_reward )
            ]);
        }
        else{
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id1])->update([
                'hazel' => \DB::raw( 'hazel +'.$hazel_reward )
            ]);

            $user_for_setTotal_match=new User();
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id2])->update([
                'hazel' => \DB::raw( 'hazel +'.$hazel_reward )
            ]);
        }

        ///update statics in useruniversalmatchstatistics table for winner ond loser id

        DB::table('user_universal_match_statistics')
            ->where([
                'user_id'=> $user_id1,
                'universal_match_type' => $type_of_match
            ])->update([
                'equal_count' => \DB::raw( 'equal_count + 1' )
            ]);

        DB::table('user_universal_match_statistics')
            ->where([
                'user_id'=> $user_id2,
                'universal_match_type' => $type_of_match
            ])->update([
                'equal_count' => \DB::raw( 'equal_count + 1' )
            ]);

    }

    public function getAmountByKey($key)
    {
        if($key == UserUniversalMatch::h_key)
            return UserUniversalMatch::h;
        if($key == UserUniversalMatch::l_key)
            return UserUniversalMatch::l;
        if($key == UserUniversalMatch::m_key)
            return UserUniversalMatch::m;
    }

    public function generateBetList()
    {
        // here generate list
    }

}