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

class UserMatch extends Model
{
    use SoftDeletes;
    //
    protected $table = 'user_match';
    protected $primaryKey = 'user_match_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;

    const l = 100;
    const m = 500;
    const h = 1000;
    const u=10;
    const n=2000;

    const l_key = "l";
    const m_key = "m";
    const h_key = "h";

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

    protected $fillable = ['user_id_1,user_id_2,questions,is_ended,user_1_correct_count,user_2_correct_count,user_1_spent_time,user_2_spent_time,bet'];

    public function intialize()
    {
        $this -> user_match_id = null;
        $this -> user_match_guid = null;
        $this -> user_id_1 = null;
        $this -> user_id_2 = null;
        $this -> questions = null;
        $this -> is_ended = null;
        $this -> user_1_correct_count = null;
        $this -> user_2_correct_count = null;
        $this -> user_1_spent_time = null;
        $this -> user_2_spent_time = null;
        $this -> bet = null;
    }

    public function intializeByRequest(Request $request)
    {
        $this -> user_match_id = $request ->input('user_match_id');
        $this -> user_match_guid = $request ->input('user_match_guid');
        $this -> user_id_1 = $request ->input('user_id_1');
        $this -> user_id_2 = $request ->input('user_id_2');
        $this -> questions = $request ->input('questions');
        $this -> is_ended = $request ->input('is_ended');
        $this -> user_1_correct_count = $request ->input('user_1_correct_count');
        $this -> user_2_correct_count = $request ->input('user_2_correct_count');
        $this -> user_1_spent_time = $request ->input('user_1_spent_time');
        $this -> user_2_spent_time = $request ->input('user_2_spent_time');
        $this -> bet = $request ->input('bet');

    }

    public static function translate_bet($bet){
        if($bet=='l')
            return self::l;
        if($bet=='m')
            return self::m;
        if($bet=='h')
            return self::h;
        if($bet == 'u'){
            return self::u;
        }

        if($bet=='n')
            return self::n;
    }

    public function store()
    {
        $this->user_match_guid = uniqid('',true);
        $this->save();
    }

    public static function findByIdAndGuid($id,$guid)
    {
        $userMatch = new UserMatch();
        $query = $userMatch->newQuery();
        $query
            ->where('user_match_id', 'like', $id)
            ->where('user_match_guid', 'like', $guid);

        $userMatchs = $query->get();
        if (count($userMatchs) == 0){
            return false;
        }
        else{
            return $userMatchs[0];
        }
    }

    public static function set_user_match_after_15min($id,$guid){
        $userMatch = new UserMatch();
        $query = $userMatch->newQuery();
        $query
            ->where('user_match_id', 'like', $id)
            ->where('user_match_guid', 'like', $guid);

        $userMatchs = $query->get();
        if($userMatchs[0]->user_1_spent_time== -1 && $userMatchs[0]->user_id_1 > 0){
            $userMatchs[0]->user_1_spent_time=self::Time_OF_Match;
            $userMatchs[0]->user_1_correct_count=0;
            $userMatchs[0]->is_ended=1;
        }
        if($userMatchs[0]->user_2_spent_time == -1 && $userMatchs[0]->user_id_2 > 0){
            $userMatchs[0]->user_2_spent_time=self::Time_OF_Match;
            $userMatchs[0]->user_2_correct_count=0;
            $userMatchs[0]->is_ended=1;
        }
        $userMatchs[0]->save();
    }

    public static function get_winner_id($match_id,$set_Total_match_count){
        $userMatch = new UserMatch();
        $query = $userMatch->newQuery();
        $query->where('user_match_id', 'like', $match_id);

        $userMatchs = $query->get();
        if($userMatchs[0]->user_id_1<0){
            ////this is robot
            if($userMatchs[0]->bet=='n')
                $second_spent_id_1=rand(55,60);
            else
                $second_spent_id_1=rand(100,300);
            $user_1_correct_count=rand(UserMatch::minAnswerBot,$userMatch::maxAnswerBot);
            if($userMatchs[0]->user_1_spent_time == -1)
                $userMatchs[0]->user_1_spent_time=$second_spent_id_1;
            if($userMatchs[0]->user_1_correct_count == 0)
                $userMatchs[0]->user_1_correct_count=$user_1_correct_count;
        }else{
            $second_spent_id_1=$userMatchs[0]->user_1_spent_time;
            $user_1_correct_count=$userMatchs[0]->user_1_correct_count;
        }

        if($userMatchs[0]->user_id_2 < 0){
                ////this is robot
            if($userMatchs[0]->bet=='n')
                $second_spent_id_2=rand(55,60);
            else
                $second_spent_id_2=rand(100,300);

            $user_2_correct_count=rand(UserMatch::minAnswerBot,$userMatch::maxAnswerBot);
            if($userMatchs[0]->user_2_spent_time == -1 )
                $userMatchs[0]->user_2_spent_time=$second_spent_id_2;
            if($userMatchs[0]->user_2_correct_count == 0)
                $userMatchs[0]->user_2_correct_count=$user_2_correct_count;


        }
        else{
            $second_spent_id_2=$userMatchs[0]->user_2_spent_time;
            $user_2_correct_count=$userMatchs[0]->user_2_correct_count;
        }
        $userMatchs[0]->is_ended=1;

        if($userMatchs[0]->user_2_spent_time ==-1 && $userMatchs[0]->user_id_2 >0)
            $userMatchs[0]->user_2_spent_time =UserMatch::Time_OF_Match;
        if($userMatchs[0]->user_1_spent_time ==-1 && $userMatchs[0]->user_id_1 >0)
            $userMatchs[0]->user_1_spent_time =UserMatch::Time_OF_Match;


//        $score_id_1=(UserMatch::Time_OF_Match/60-$userMatchs[0]->user_1_spent_time/60) + $userMatchs[0]->user_1_correct_count*2;
//        $score_id_2=(UserMatch::Time_OF_Match/60-$userMatchs[0]->user_2_spent_time/60) + $userMatchs[0]->user_2_correct_count*2;
        $score_id_1= $userMatchs[0]->user_1_correct_count;
        $score_id_2= $userMatchs[0]->user_2_correct_count;

        if($score_id_1==$score_id_2 && $score_id_1!=0){
            $score_id_1=UserMatch::Time_OF_Match/60-$userMatchs[0]->user_1_spent_time/60;
            $score_id_2=UserMatch::Time_OF_Match/60-$userMatchs[0]->user_2_spent_time/60;
        }
        if($score_id_1>$score_id_2){
            if($userMatchs[0]->user_id_1>0 && $set_Total_match_count)
                self::setTotal_match_count_And_win_match_count_And_hazel($userMatchs[0]->bet,$userMatchs[0]->user_id_1);

            $userMatchs[0]->winner_id=$userMatchs[0]->user_id_1;
            $userMatchs[0]->save();

            DB::table('user_universal_match_statistics')->where('user_id',$userMatchs[0]->user_id_1)->increment('win_count', 1);
            DB::table('user_universal_match_statistics')->where('user_id',$userMatchs[0]->user_id_2)->increment('lost_count', 1);

            return 1;

        }
        else if($score_id_1<$score_id_2){
            if($userMatchs[0]->user_id_2>0 && $set_Total_match_count)
                self::setTotal_match_count_And_win_match_count_And_hazel($userMatchs[0]->bet,$userMatchs[0]->user_id_2);

            $userMatchs[0]->winner_id=$userMatchs[0]->user_id_2;
            $userMatchs[0]->save();

            DB::table('user_universal_match_statistics')->where('user_id',$userMatchs[0]->user_id_2)->increment('win_count', 1);
            DB::table('user_universal_match_statistics')->where('user_id',$userMatchs[0]->user_id_1)->increment('lost_count', 1);
            return 2;
        }
        else{
            self::setTotal_match_count_And_equal_match_count_And_hazel($userMatchs[0]->bet,$userMatchs[0]->user_id_2,$userMatchs[0]->user_id_1);

            $userMatchs[0]->winner_id=0;
            $userMatchs[0]->save();

            DB::table('user_universal_match_statistics')->where('user_id',$userMatchs[0]->user_id_2)->increment('equal_count', 1);
            DB::table('user_universal_match_statistics')->where('user_id',$userMatchs[0]->user_id_1)->increment('equal_count', 1);
            return 0;
        }


    }

    public static function get_winner_id_for_result($match_id,$set_Total_match_count,$time){
        $userMatch = new UserMatch();
        $query = $userMatch->newQuery();
        $query->where('user_match_id', 'like', $match_id);

        $userMatchs = $query->get();
        if($userMatchs[0]->user_id_1<0){
            ////this is robot
            if($userMatchs[0]->bet=='n')
                $second_spent_id_1=rand(55,60);
            else{
                $second_spent_id_1=rand(100,300);
                if($second_spent_id_1>$time){
                    if($time>50){
                        $second_spent_id_1=rand(50,$time);
                    }
                }
            }

            $user_1_correct_count=rand(UserMatch::minAnswerBot,$userMatch::maxAnswerBot);
            if($userMatchs[0]->user_1_spent_time == -1)
                $userMatchs[0]->user_1_spent_time=$second_spent_id_1;
            if($userMatchs[0]->user_1_correct_count == 0)
                $userMatchs[0]->user_1_correct_count=$user_1_correct_count;
        }else{
            $second_spent_id_1=$userMatchs[0]->user_1_spent_time;
            $user_1_correct_count=$userMatchs[0]->user_1_correct_count;
        }

        if($userMatchs[0]->user_id_2 < 0){
            ////this is robot
            if($userMatchs[0]->bet=='n')
                $second_spent_id_2=rand(55,60);
            else{
                $second_spent_id_2=rand(100,300);
                if($second_spent_id_2>$time){
                    if($time>50){
                        $second_spent_id_2=rand(50,$time);
                    }
                }
            }

            $user_2_correct_count=rand(UserMatch::minAnswerBot,$userMatch::maxAnswerBot);
            if($userMatchs[0]->user_2_spent_time == -1 )
                $userMatchs[0]->user_2_spent_time=$second_spent_id_2;
            if($userMatchs[0]->user_2_correct_count == 0)
                $userMatchs[0]->user_2_correct_count=$user_2_correct_count;


        }
        else{
            $second_spent_id_2=$userMatchs[0]->user_2_spent_time;
            $user_2_correct_count=$userMatchs[0]->user_2_correct_count;
        }
        $userMatchs[0]->is_ended=1;

        if($userMatchs[0]->user_2_spent_time ==-1 && $userMatchs[0]->user_id_2 >0)
            $userMatchs[0]->user_2_spent_time =UserMatch::Time_OF_Match;
        if($userMatchs[0]->user_1_spent_time ==-1 && $userMatchs[0]->user_id_1 >0)
            $userMatchs[0]->user_1_spent_time =UserMatch::Time_OF_Match;


//        $score_id_1=(UserMatch::Time_OF_Match/60-$userMatchs[0]->user_1_spent_time/60) + $userMatchs[0]->user_1_correct_count*2;
//        $score_id_2=(UserMatch::Time_OF_Match/60-$userMatchs[0]->user_2_spent_time/60) + $userMatchs[0]->user_2_correct_count*2;
        $score_id_1= $userMatchs[0]->user_1_correct_count;
        $score_id_2= $userMatchs[0]->user_2_correct_count;
        if($score_id_1==$score_id_2 && $score_id_1!=0){
            $score_id_1=UserMatch::Time_OF_Match/60-$userMatchs[0]->user_1_spent_time/60;
            $score_id_2=UserMatch::Time_OF_Match/60-$userMatchs[0]->user_2_spent_time/60;
        }
        if($score_id_1>$score_id_2){
            if($userMatchs[0]->user_id_1>0 && $set_Total_match_count)
                self::setTotal_match_count_And_win_match_count_And_hazel($userMatchs[0]->bet,$userMatchs[0]->user_id_1);

            $userMatchs[0]->winner_id=$userMatchs[0]->user_id_1;
            $userMatchs[0]->save();
            return 1;

        }
        else if($score_id_1<$score_id_2){
            if($userMatchs[0]->user_id_2>0 && $set_Total_match_count)
                self::setTotal_match_count_And_win_match_count_And_hazel($userMatchs[0]->bet,$userMatchs[0]->user_id_2);

            $userMatchs[0]->winner_id=$userMatchs[0]->user_id_2;
            $userMatchs[0]->save();
            return 2;
        }
        else{
            self::setTotal_match_count_And_equal_match_count_And_hazel($userMatchs[0]->bet,$userMatchs[0]->user_id_2,$userMatchs[0]->user_id_1);

            $userMatchs[0]->winner_id=0;
            $userMatchs[0]->save();
            return 0;
        }


    }

    public static function update_time_match($id,$guid,$user_id_1_or_2){
        $userMatch = new UserMatch();
        $query = $userMatch->newQuery();
        $query
            ->where('user_match_id', 'like', $id)
            ->where('user_match_guid', 'like', $guid);

        $userMatchs = $query->get();
        if($user_id_1_or_2==1){
            $userMatchs[0]->user_1_spent_time=self::Time_OF_Match;
        }else{
            $userMatchs[0]->user_2_spent_time=self::Time_OF_Match;
        }
        $userMatchs[0]->save();
    }

    public static function setTotal_match_count_And_win_match_count_And_hazel($bet,$user_id){
        $hazel_reward=1.9*self::translate_bet($bet);
        $user_for_setTotal_match=new User();
        if($bet=='u'){
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id])->update([
                'win_match_count' => \DB::raw( 'win_match_count + 1' ),
                'luck' => \DB::raw( 'luck +'.$hazel_reward )
            ]);
        }
        else{
            $user_for_setTotal_match->newQuery()->where(['user_id'=> $user_id])->update([
                'win_match_count' => \DB::raw( 'win_match_count + 1' ),
                'hazel' => \DB::raw( 'hazel +'.$hazel_reward )
            ]);
        }

    }

    public static function setTotal_match_count_And_equal_match_count_And_hazel($bet,$user_id1,$user_id2){
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

    }

    public function edit(Request $request)
    {

        $oldMatch = self::findByIdAndGuid( $this -> user_match_id,$this->user_match_guid);



        if($request -> has('user_1_correct_count'))
            $oldMatch->user_1_correct_count=$request -> input('user_1_correct_count');

        if($request -> has('user_2_correct_count'))
            $oldMatch->user_2_correct_count=$request -> input('user_2_correct_count');

        if($request -> has('user_1_spent_time'))
            $oldMatch->user_1_spent_time=$request -> input('user_1_spent_time');

        if($request -> has('user_2_spent_time'))
            $oldMatch->user_2_spent_time=$request -> input('user_2_spent_time');

        $oldMatch->save();

    }

    public function getAmountByKey($key)
    {
        if($key == UserMatch::h_key)
            return UserMatch::h;
        if($key == UserMatch::l_key)
            return UserMatch::l;
        if($key == UserMatch::m_key)
            return UserMatch::m;
    }

    public function generateBetList()
    {
        // here generate list
    }

}