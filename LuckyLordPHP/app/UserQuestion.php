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

class UserQuestion extends Model
{

    use SoftDeletes;
    //
    protected $table = 'user_question';
    protected $primaryKey = 'user_question_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;


    protected $fillable = ['is_correct'];

    public function intialize()
    {
        $this -> user_question_id = null;
        $this -> user_question_guid = null;
        $this -> user_id = null;
        $this -> question_id = null;
        $this -> is_correct = null;
    }

    public function intializeByRequest(Request $request)
    {
        $this -> user_question_id = $request ->input('user_question_id');
        $this -> user_question_guid = $request ->input('user_question_guid');
        $this -> user_id = $request ->input('user_id');
        $this -> question_id = $request ->input('question_id');
        $this -> is_correct = $request ->input('is_correct');
    }

    public function store()
    {
        $this->user_question_guid = uniqid('',true);
        $this->save();

    }
    public static function existByUserAndQuestion($userId,$questionId)
    {
        $userQuestion = new UserQuestion();
        $query = $userQuestion->newQuery();
        $query->where('question_id', '=', $questionId);
        $query->where('user_id', '=', $userId);
        $user_questions = $query->get();
        if (count($user_questions) == 0){
            return false;
        }
        else{
            return $user_questions[0]->user_question_id;
        }
    }
    public function Question()
    {
        return $this->belongsTo(Question::class);
    }


}
