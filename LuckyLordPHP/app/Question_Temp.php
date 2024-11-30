<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:39 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Log;
use File;
use DB;
use Illuminate\Http\Request;


class Question_Temp extends Model
{

    use SoftDeletes;
    //
    protected $table = 'question_temp';

    protected $primaryKey = 'question_temp_id';

    protected $dates = ['deleted_at'];

    public $timestamps = true;

    protected $fillable = ['description','answer'];


    ///static attributes
    ///


    const REPORT_OTHER_REASONS = 0;
    const REPORT_NO_PICTURE = 1;
    const REPORT_WRONG_PICTURE = 2;
    const REPORT_WRONG_ANSWER = 3;
    const REPORT_WRONG_WRITING = 4;
    const REPORT_WRONG_SPELLING = 5;
    const REPORT_VERY_HARD_QUESTION = 6;
    const REPORT_INVALID_QUESTION= 7;
    const REPORT_VAGUE_QUESTION = 8;
    const REPORT_WRONG_CATEGORY = 9;
    const REPORT_REPEATED_CHOICE = 10;
    const REPORT_MULTIPLE_ANSWER = 11;
    const REPORT_MORAL_PROBLEM = 12;
    const REPORT_CREED_PROBLEM = 13;
    const REPORT_TRIBAL_PROBLEM = 14;
    const REPORT_POLITICAL_PROBLEM = 15;
    const REPORT_AGAINST_LOW = 16;

        /// ////////////////

    public function intialize()
    {
        $this -> question_temp_id = null;
        $this -> question_temp_guid = null;
        $this -> category_id = null;
        $this -> user_temp_id = null;
        $this -> description = null;

        $this -> answer = null;
        $this -> penalty = null;

        $this -> min_luck_reward = null;
        $this -> max_luck_reward = null;

        $this -> min_hazel_reward = null;
        $this -> max_hazel_reward = null;

        $this -> is_approved = null;

        $this -> deleted_at = null;
    }

    public function intializeByRequest(Request $request)
    {

        $this -> question_temp_id = $request ->input('question_temp_id');
        $this -> question_temp_guid = $request ->input('question_temp_guid');
        $this -> category_id = $request ->input('category');
        $this -> user_temp_id = $request ->input('user_temp_id');
        $this -> description = $request ->input('description');
        $this -> answer = $request ->input('answer');
        $this -> penalty = $request ->input('penalty');

        $this -> min_luck_reward = $request ->input('min_luck_reward');
        $this -> max_luck_reward = $request ->input('max_luck_reward');

        $this -> min_hazel_reward = $request ->input('min_hazel_reward');
        $this -> max_hazel_reward = $request ->input('max_hazel_reward');

        $this -> is_approved = $request ->input('is_approved');

        $this -> deleted_at = $request ->input('deleted_at');

    }

    public function store()
    {
        $this->question_temp_guid = uniqid('',true);
        $this->save();
    }

    public function storeByAnswer($answer)
    {
        $this->question_temp_guid = uniqid('',true);
        $this->answer = $answer;
        $this->save();
        return $this->question_temp_id;
    }
//
//    public function storeByAnswerGetId($answer,$question_id)
//    {
//        $this->question_id=$question_id;
//        $this->question_guid = uniqid('',true);
//        $this->answer = $answer;
//        $this->save();
//        return $this->question_id;
//    }
//
//    public function getFullDetail( $params1,$params2,$params3)
//    {
//
//        $query = $this->newQuery();
//
//        if($params1!=null) {
//
//            $query=\App\Utility::fillQueryAlias($query,$params1);
//        }
//        $query =Self::makeWhere($query);
//
//        //
//        if($params2!=null) {
//            $query=\App\Utility::fillQueryJoin($query,$params2);
//
//        }
//        //filtering
//        if($params3!=null) {
//            $query=\App\Utility::fillQueryFilter($query,$params3);
//        }
//
//        $questions = $query->get();
//        return $questions;
//    }
//
//    public function makeWhere($query){
//        if($this->question_id != null){
//            $query->where('	question.'.'question_id', '=', $this->question_id);
//        }
//        if($this->question_guid != null){
//            $query->where('	question.'.'question_guid', '=', $this->question_guid);
//        }
//        if($this->description != null){
//            $query->where('	question.'.'description', 'like', $this->description);
//        }
//        if($this->answer != null){
//            $query->where('	question.'.'answer', 'like', $this->answer);
//        }
//        if($this->penalty != null){
//            $query->where('	question.'.'penalty', '=', $this->penalty);
//        }
//        if($this->min_luck_reward != null){
//            $query->where('	question.'.'min_luck_reward', 'like', $this->min_luck_reward);
//        }
//        if($this->max_luck_reward != null){
//            $query->where('	question.'.'max_luck_reward', '=', $this->max_luck_reward);
//        }
//        if($this->min_hazel_reward != null){
//            $query->where('	question.'.'min_hazel_reward', 'like', $this->min_hazel_reward);
//        }
//        if($this->max_hazel_reward != null){
//            $query->where('	question.'.'max_hazel_reward', '=', $this->max_hazel_reward);
//        }
//
//        return $query;
//    }
//
//    public static function exist($Id,$guid)
//    {
//        $question = new Question();
//        $query = $question->newQuery();
//        $query->where('question_id', '=', $Id);
//        $query->where('question_guid', 'like', $guid);
//        $questions = $query->get();
//        if (count($questions) == 0){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }
//
//    public static function getQuestionMin_luck_reward($questionId)
//    {
//        $question = new Question();
//        $query = $question->newQuery();
//        $query->where('question_id', '=', $questionId);
//        $query->select('min_luck_reward');
//        $min_luck_reward = $query->get()->first();
//        return $min_luck_reward;
//    }
//
//    public static function getQuestionMax_luck_reward($questionId)
//    {
//        $question = new Question();
//        $query = $question->newQuery();
//        $query->where('question_id', '=', $questionId);
//        $query->select('max_luck_reward');
//        $max_luck_reward = $query->get()->first();
//        return $max_luck_reward;
//    }
//
//    public static function getQuestionMin_hazel_reward($questionId)
//    {
//        $question = new Question();
//        $query = $question->newQuery();
//        $query->where('question_id', '=', $questionId);
//        $query->select('min_hazel_reward');
//        $min_hazel_reward = $query->get()->first();
//        return $min_hazel_reward;
//    }
//
//    public static function getQuestionMax_hazel_reward($questionId)
//    {
//        $question = new Question();
//        $query = $question->newQuery();
//        $query->where('question_id', '=', $questionId);
//        $query->select('max_hazel_reward');
//        $max_hazel_reward = $query->get()->first();
//        return $max_hazel_reward;
//    }
//
//    public function userQuestions()
//    {
//        return $this->hasMany(UserQuestion::class);
//    }
//
//    public function userQuestionReports()
//    {
//        return $this->hasMany(UserQuestionReport::class);
//    }
//
//    public function getQuestionImage($question_id){
//        //retern previous name of companylogo
//        $destinationPath = storage_path().'/app/question';
//        $files1 = scandir($destinationPath);
//        $nameOfFile="";
//        $search =$question_id;
//        $search_length = strlen($search);
//        foreach ($files1 as $key => $value) {
//            if (substr($value, 0, $search_length) == $search) {
//                $nameOfFile=$value;
//                break;
//            }
//        }
////        return Image::make(storage_path('question/' . $nameOfFile))->response();
////        delete previou logo
//        if($nameOfFile!=null)
//            return base64_encode(File::get(storage_path('/app/question/'.$nameOfFile)));
////            return File::get(storage_path().'question/'.$nameOfFile);
//        else
//            return null;
//    }
//
//    protected static function boot() {
//        parent::boot();
//
//        static::deleting(function($question) {
//            $question->userQuestions()->delete();
//            $question->userQuestionReports()->delete();
//        });
//    }
//
//    public static function get_10_Random_question($chosen_category_id1,$chosen_category_id2){
//        //where question has image
////            ->where(DB::raw('SUBSTRING(description , 1 ,1)'),'=','#')
//        //where question is  discriptive
////            ->where('answer', 'not like', '%#%')
//        if($chosen_category_id1==0){
//            $random_que1 = Question::select("question_id")
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//        }else if($chosen_category_id1==-1){
//            $random_que1 = Question::select("question_id")->where('category_id','=',rand(1,Category::Category_Count))
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//        }else{
//            $random_que1 = Question::select("question_id")->where('category_id','=',$chosen_category_id1)
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//        }
//
//
//
//        if($chosen_category_id2==0){
//            $random_que2 = Question::select("question_id")
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//        }else if($chosen_category_id2==-1){
//            $random_que2 = Question::select("question_id")->where('category_id','=',rand(1,Category::Category_Count))
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//        }else{
//            $random_que2 = Question::select("question_id")->where('category_id','=',$chosen_category_id2)
//                ->orderBy(DB::raw('RAND()'))->take(Question::COUNT_OF_MATCH_QUESTIONS/2)->get();
//        }
//
//
//        $random_question=Question::select("question_id")
//            ->orderBy(DB::raw('RAND()'))->take(1)->get();
//        $questions='';
//
//
//        for($index=0;$index<count($random_que1);$index++){
//            $questions=$questions.$random_que1[$index]->question_id.',';
//        }
//
//        for($index=0;$index<count($random_que2);$index++){
//            $questions=$questions.$random_que2[$index]->question_id.',';
//        }
//
//
//        return $questions.$random_question[0]->question_id;
//    }
//
//    public static function resize_image($file, $w, $h, $crop=FALSE) {
//        list($width, $height) = getimagesize($file);
//        $r = $width / $height;
//        if ($crop) {
//            if ($width > $height) {
//                $width = ceil($width-($width*abs($r-$w/$h)));
//            } else {
//                $height = ceil($height-($height*abs($r-$w/$h)));
//            }
//            $newwidth = $w;
//            $newheight = $h;
//        } else {
//            if ($w/$h > $r) {
//                $newwidth = $h*$r;
//                $newheight = $h;
//            } else {
//                $newheight = $w/$r;
//                $newwidth = $w;
//            }
//        }
//        $src = imagecreatefromjpeg($file);
//        $dst = imagecreatetruecolor($newwidth, $newheight);
//        imagecopyresampled($dst, $src, 0, 0, 0, 0, $newwidth, $newheight, $width, $height);
//
//        return $dst;
//    }
//
//    public static function likesOrhatesQuestion($question_id, $rate)
//    {
//        $Question = Question::findById($question_id);
//        if($rate==Question::LIKED)
//            $Question->likes ++;
//        else
//            $Question->hates ++;
//        $Question->save();
//    }
//    public static function findById($id)
//    {
//        $Question = new Question();
//        $query = $Question->newQuery();
//        $query->where('question_id', '=', $id);
//        $Questions = $query->get();
//
//        return $Questions[0];
//    }

}