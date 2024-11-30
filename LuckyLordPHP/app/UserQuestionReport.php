<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 8/5/2017
 * Time: 11:47 AM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;
use Log;

class UserQuestionReport extends Model
{
    use SoftDeletes;
    //
    protected $table = 'user_question_report';
    protected $primaryKey = 'user_question_report_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;


    protected $fillable = ['code,user_id,question_id'];

    public function intialize()
    {
        $this -> user_question_report_id = null;
        $this -> user_question_report_guid = null;
        $this -> question_id = null;
        $this -> user_id = null;
        $this -> code = null;
        $this -> description = null;
        $this -> table_code = 0;
    }

    public function intializeByRequest(Request $request)
    {
        $this -> user_question_report_id = $request ->input('user_question_report_id');
        $this -> user_question_report_guid = $request ->input('user_question_report_guid');
        $this -> question_id = $request ->input('question_id');
        $this -> user_id = $request ->input('user_id');
        $this -> code = $request ->input('code');
        $this -> description = $request ->input('description');
        if(!is_null($request ->input('table_code')))
            $this -> table_code = $request ->input('table_code');
    }

    public function store()
    {
        $this->user_question_report_guid = uniqid('',true);
        $this->save();

    }
}