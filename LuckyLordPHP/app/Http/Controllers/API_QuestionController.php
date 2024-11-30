<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App\LogEvent;
use App\Question;
use App\Question_Puzzle;
use App\UserCategory;
use App\UserQuestion;
use App\User;
use App\RequestResponseAPI;
use App\UserQuestionReport;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Carbon\Carbon;



class API_QuestionController extends Controller
{
    protected $question;



    public function __construct(Question $question)
    {
        $this->question = $question;
    }

    /**
     * Store a new $question.
     *
     * @param  Request $request
     * @return Response
     */
    public function apiNext(Request $request)
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
        if (!$request->has('tag') || !$request->has('luck') ||
            !$request->has('hazel') || !$request->has('is_correct') ||
             !$request->has('next_category_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
        ////////////////////////////////////////////

        try {
            ////begin Transaction for 5 query
            DB::beginTransaction();

            $firstchar='';
            $desc='';


            //////////////////////////////////////////////// get one random question ghati pati category
            //////////////////////////////////////////////// get one random question ghati pati category
            //////////////////////////////////////////////// get one random question ghati pati category
            if($request ->input('next_category_id')==0 || $request ->input('next_category_id')=="0"){

                $random_Category_Array=[
                    ["1","1"],
                    ["2","2"],
                    ["3","3"],
                    ["4","4"],
                    ["5","5"],
                    ["6","6"],
                    ["7","8"],
                    ["8","9"],
                    ["9","12"],
                    ["10","9"],
                    ["11","10"],
                    ["12","other"],
                ];
                $limited_random_Category_Array=[
                    ["1","7"],
                    ["2","11"],
                    ["3","13"],
                ];
                $random_Category_Id=rand(1,count($random_Category_Array));
                $cat_id=$random_Category_Array[$random_Category_Id-1][1];
                if($random_Category_Id==count($random_Category_Array)){
                    $random_Category_Id=rand(1,count($limited_random_Category_Array));
                    $cat_id=$limited_random_Category_Array[$random_Category_Id-1][1];
                }
//                $cat_id=10;
                ////////////////****0*****   get normal(Random) for App question
                ////////////////****0*****   get normal(Random) for App question
                ////////////////****0*****   get normal(Random) for App question
                ////////////////****0*****   get normal(Random) for App question
//                $query_for_getQuestion="SELECT question_id,question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward FROM question WHERE `deleted_at` is NULL ORDER BY RAND() LIMIT 1";
//                $questions = DB::select($query_for_getQuestion);


                $query_for_getQuestion="SELECT question_id,question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward FROM question
                      WHERE category_id = :id AND `deleted_at` is NULL
                     ORDER BY RAND() LIMIT 1";
                $questions = DB::select($query_for_getQuestion, ['id' => $cat_id]);



                ////////////////****1*****   get last question
                ////////////////****1*****   get last question
                ////////////////****1*****   get last question
                ////////////////****1*****   get last question
                // get last question
//                $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                if($myUser != null && $myUser->user_name=='user11'){
//                    $ques = DB::select("select question_id,question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward from question  ORDER BY `question_id` ASC");
//
//                    $query_for_getQuestion="SELECT question_id,question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward FROM question
//                   WHERE question_id = :id";
//                    $questions = DB::select($query_for_getQuestion, ['id' =>$ques[count($ques)-1]->question_id ]);
//
//                }


                ////////////////****2*****   get successive question from certain id
                ////////////////****2*****   get successive question from certain id
                ////////////////****2*****   get successive question from certain id
                ////////////////****2*****   get successive question from certain id
//                DB::table('user')->where('user_id','50')->increment('tel',1);
//                $question_id = DB::table('user')->where('user_id','50')->value('tel');
//                $questions = DB::select($query_for_getQuestion, ['id' => $question_id]);

                ////////////////****3*****   get random question between(min,max)
                ////////////////****3*****   get random question between(min,max)
                ////////////////****3*****   get random question between(min,max)
                ////////////////****3*****   get random question between(min,max)
//                $ques = DB::select('select * from question');
//                $query_for_getQuestion="SELECT question_id,question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward FROM question
//                   WHERE question_id = :id";
//                $questions = DB::select($query_for_getQuestion, ['id' => rand(2673,2673)]);
            }else{


                $query_for_getQuestion="SELECT question_id,question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward FROM question
                      WHERE category_id = :id AND `deleted_at` is NULL
                     ORDER BY RAND() LIMIT 1";
                $questions = DB::select($query_for_getQuestion, ['id' => $request ->input('next_category_id')]);
            }
            $question = null;
            $image=null;
            //////////////////if question is null ===>> this is image question
            if(count($questions) > 0)
            {
                $question = $questions[0];
                //////get image from storage
//                $firstchar = substr($question->description, 0, 1);
//                $desc=substr($question->description,  1);


                if((strpos($question->description, '#')) !== false){
                    $image=$this->question->getQuestionImage($question->question_id);
                }
            }
             else
                $question=null;

            if($request->has('question_id') && $request->has('question_guid')){
                //////////////////////////////////// update luck and hazel for user
                //////////////////////////////////// update luck and hazel for user
                //////////////////////////////////// update luck and hazel for user
                $previus_question = DB::table('question')->select('category_id','min_luck_reward', 'max_luck_reward','min_hazel_reward','max_hazel_reward')
                    ->where('question_id',$request ->input('question_id'))
                    ->where('question_guid',$request ->input('question_guid'))
                    ->first();
                if(count($previus_question)!=0){
                    if($previus_question->max_luck_reward < $request ->input('luck') ||
                        $previus_question->max_hazel_reward < $request ->input('hazel')){
                        return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);
                    }
                }
                    $user_for_Luck_and_hazel=new User();
                    $user_for_Luck_and_hazel->newQuery()->where(['user_id'=> $user->user_id])->update([
                        'hazel' => \DB::raw( 'hazel +'.$request ->input('hazel') ),
                        'luck' => \DB::raw( 'luck +'.$request ->input('luck') ),
                    ]);

                /////////////////////////// update old question (is_correct) if exist in user_category table
                if($request ->input('is_correct')!= null){
                    $userCategorIncrease=new UserCategory();
                    if($request ->input('is_correct')==1){
                        $userCategorIncrease->newQuery()->where(['category_id'=>$previus_question->category_id,'user_id'=> $user->user_id])->update([
                            'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                            'correct_answered_count' => \DB::raw( 'correct_answered_count + 1' ),
                            'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                            'daily_correct_answered_count' => \DB::raw( 'daily_correct_answered_count + 1' ),
                            'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                            'weekly_correct_answered_count' => \DB::raw( 'weekly_correct_answered_count + 1' ),
                        ]);
                    }else{
                        $userCategorIncrease->newQuery()->where(['category_id'=> $previus_question->category_id,'user_id'=> $user->user_id])->update([
                            'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                            'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                            'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                        ]);
                    }
                }
            }

            ////////////////////////end of query
            DB::commit();
            if((strpos($question->description, '#')) !== false){
                $question->image=$image;
                $desc=substr($question->description,  1);
                $question->description=$desc;
            }

//            return json_encode(['image'=>$image ,'question' => $question, 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

            return json_encode([ 'question' => $question, 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);
        } catch (\Exception $ex) {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);
        }
    }

    public function apiReport(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('code') ||
            !$request->has('description') || !$request->has('question_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_REPORT_QUESTION]);
        }


        try {
            // -1 darhantable
            //-2 hadsekalame
            //-3 kalame yabi
            //-4 moteghate
            $UserQuestionReport = UserQuestionReport::where('user_id', '=', $user->user_id)->where('question_id', '=', $request['question_id'])->first();
            if ($UserQuestionReport == null) {
                $reportQ=new UserQuestionReport();
                $request['user_id']=$user->user_id;
                $reportQ->intializeByRequest($request);
                $reportQ->store();
            }

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_REPORT_QUESTION]);
        } catch (\Exception $ex) {
//            DB::rollback();

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_REPORT_QUESTION]);

        }


    }

    public function apiRate(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('rate') || !$request->has('question_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_RATE_QUESTION]);
        }


        try {
            Question::likesOrhatesQuestion($request['question_id'],$request['rate']);

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_RATE_QUESTION]);
        } catch (\Exception $ex) {
//            DB::rollback();

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_RATE_QUESTION]);

        }
    }

    public function apiTableReport(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('code') ||
            !$request->has('description') || !$request->has('question_puzzle_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_REPORT_TABLE_QUESTION]);
        }


        try {
            $UserQuestionReport = UserQuestionReport::where('user_id', '=', $user->user_id)->where('question_id', '=', $request['question_puzzle_id'])
                ->where('table_code', '=', 1)->first();
            if ($UserQuestionReport == null) {
                $reportQ=new UserQuestionReport();
                $request['user_id']=$user->user_id;
                $request['question_id']=$request['question_puzzle_id'];
                $request['table_code']=1;
                $reportQ->intializeByRequest($request);
                $reportQ->store();
            }

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_REPORT_TABLE_QUESTION]);
        } catch (\Exception $ex) {
//            DB::rollback();

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_REPORT_TABLE_QUESTION]);

        }


    }

    public function apiTableRate(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('rate') || !$request->has('question_puzzle_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_RATE_TABLE_QUESTION]);
        }
        try {
            Question_Puzzle::likesOrhatesQuestion($request['question_puzzle_id'],$request['rate']);

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_RATE_TABLE_QUESTION]);
        } catch (\Exception $ex) {
//            DB::rollback();

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_RATE_TABLE_QUESTION]);

        }
    }

    public function storeQuestion(){

    }

    public function apiWordTable(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('question_count') || !$request->has('hazel') || !$request->has('luck')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_WORD_TABLE_QUESTION]);
        }


        try {
            if($request['luck']>0){
                $userCategorIncrease=new UserCategory();
                $userCategorIncrease->newQuery()->where(['category_id'=>14,'user_id'=> $user->user_id])->update([
                    'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                    'correct_answered_count' => \DB::raw( 'correct_answered_count + 1' ),
                    'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                    'daily_correct_answered_count' => \DB::raw( 'daily_correct_answered_count + 1' ),
                    'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                    'weekly_correct_answered_count' => \DB::raw( 'weekly_correct_answered_count + 1' ),
                ]);
            }
            if(!($request['hazel']==0 and $request['luck']==0))
                User::IncreaseDecreaseHazelLuck($user->user_id,$request['hazel'],$request['luck']);

            $question_query='select question_puzzle_id as question_id,question_puzzle_guid as question_guid,category_id,description,answer,penalty,min_luck_reward,max_luck_reward,min_hazel_reward,max_hazel_reward from question_puzzle WHERE CHAR_LENGTH(answer)<11  AND answer NOT LIKE "%[^a-zA-Z0-9]%" ORDER BY RAND() LIMIT :id';
            $questions = DB::select($question_query, ['id' => $request['question_count']]);
            return json_encode(['questions'=>$questions,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_WORD_TABLE_QUESTION]);
        } catch (\Exception $ex) {
//            DB::rollback();

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_WORD_TABLE_QUESTION]);

        }
    }

    public function apiUniversalReport(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('code') ||
            !$request->has('description') || !$request->has('item_id')
            || !$request->has('type')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_REPORT_UNIVERSAL]);
        }


        try {
//            $UserQuestionReport = UserQuestionReport::where('user_id', '=', $user->user_id)
//                ->where('question_id', '=', $request['item_id'])
//                ->where('table_code', '=', $request->input('type'))->first();
//            if ($UserQuestionReport == null) {
                $reportQ=new UserQuestionReport();
                $request['user_id']=$user->user_id;
                $request['question_id']=$request['item_id'];
                $request['table_code']=$request->input('type');
                $reportQ->intializeByRequest($request);
                $reportQ->store();


            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_REPORT_UNIVERSAL]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_REPORT_UNIVERSAL]);

        }


    }

    public function apiCrossTable(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('luck') || !$request->has('hazel')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_CROSS_TABLE_QUESTION]);
        }


        try {
            $easy_table=[1,2,5,6,7];
            $middle_table=[1,2,5,6,7,4,8,9,10,11,12];

            $level_of_user = DB::table('user')->select('level')->where('user_id', $user->user_id)->first();
            if($level_of_user->level<500){
                    $tableTempRand=DB::table('moteghate_table')
                    ->where('table_base_id',$easy_table[rand(0,4)])
                    ->orderBy(DB::raw('RAND()'))->limit(1)->first();
            }elseif ($level_of_user->level<2000 and $level_of_user->level>500){
                    $tableTempRand=DB::table('moteghate_table')
                    ->where('table_base_id',$middle_table[rand(0,10)])
                    ->orderBy(DB::raw('RAND()'))->limit(1)->first();
            }else{
            $tableTempRand = DB::table('moteghate_table')
                ->where('table_base_id', rand(1,15))
                ->orderBy(DB::raw('RAND()'))->limit(1)->first();
            }

//            $tableTempRand=DB::table('moteghate_table')
//                ->where('table_base_id', rand(1,15))
////                ->where('moteghate_Table_id', 45961)
//                ->orderBy(DB::raw('RAND()'))->limit(1)->first();

            $tableTemp = DB::table('moteghate_table')
                ->join('table_base', 'table_base.table_base_id', '=', 'moteghate_table.table_base_id')
                ->join('cell_info_table', 'cell_info_table.table_base_id', '=', 'table_base.table_base_id')
                ->select('moteghate_table.*', 'cell_info_table.*', 'table_base.*')
                ->where('table_base.table_base_id', '=', $tableTempRand->table_base_id)
                ->where('moteghate_table.moteghate_Table_id', '=', $tableTempRand->moteghate_Table_id)
//                ->where('moteghate_table.moteghate_Table_id', '=', 47172)
                ->get();

            User::IncreaseDecreaseHazelLuck($user->user_id,$request['hazel'],$request['luck']);
            $questions=[];
            $questions2= explode(",",$tableTemp[0]->questions);
            $table=[];
            $question_index=0;
            for($index1=0;$index1<count($tableTemp);$index1++){
                $desc_ans= explode("*",$questions2[$question_index]);
//                if($tableTemp[$index1]->)
                $firstQ=[];
                $secondQ=[];
                $firstQ['description']=$desc_ans[1];
                $firstQ['answer']=$desc_ans[0];
                $dir=explode(",",$tableTemp[$index1]->first_direction);

                $firstQ['answer_position_cells']='';
                if($dir[0]=='down')
                    $firstQ['position_code']='d,';
                elseif($dir[0]=='left')
                    $firstQ['position_code']='l,';
                $firstQ['position_code'].=$dir[1].','.$dir[2];
                if($tableTemp[$index1]->question_count==1)
                    $firstQ['question_position']=$tableTemp[$index1]->cell_index;
                elseif($tableTemp[$index1]->question_count==2)
                    $firstQ['question_position']=$tableTemp[$index1]->cell_index.',u';
                $answerIndex=explode(",",$tableTemp[$index1]->first_answer_index_cell);
                for($index2=0;$index2<count($answerIndex);$index2++){
                    $firstQ['answer_position_cells'].= $answerIndex[$index2].',';
                }
//                $firstQ['answerIndex']=$answerIndex;
                $cell=[];
                $cell['firstQ']=$firstQ;
                array_push($questions,$firstQ);



                $question_index++;
                if($tableTemp[$index1]->question_count==2){
                    $dir=explode(",",$tableTemp[$index1]->second_direction);
                    $answerIndex=explode(",",$tableTemp[$index1]->second_answer_index_cell);
                    $desc_ans= explode("*",$questions2[$question_index]);
                    $secondQ['description']=$desc_ans[1];
                    $secondQ['answer']=$desc_ans[0];



                    $secondQ['answer_position_cells']='';
                    if($dir[0]=='down')
                        $secondQ['position_code']='d,';
                    elseif($dir[0]=='left')
                        $secondQ['position_code']='l,';
                    $secondQ['position_code'].=$dir[1].','.$dir[2];

                    $secondQ['question_position']=$tableTemp[$index1]->cell_index.',d';
                    $answerIndex=explode(",",$tableTemp[$index1]->second_answer_index_cell);
                    for($index2=0;$index2<count($answerIndex);$index2++){
                        $secondQ['answer_position_cells'].= $answerIndex[$index2].',';
                    }
//                $firstQ['answerIndex']=$answerIndex;
                    array_push($questions,$secondQ);


                    $question_index++;
                }
                if($tableTemp[$index1]->question_count==1)
                    $table[$tableTemp[$index1]->cell_index]=$cell;


            }
            return json_encode(['cross_table_id'=>$tableTemp[0]->moteghate_Table_id,'questions'=>$questions,'width' => $tableTemp[0]->width_size,'height' => $tableTemp[0]->height_size,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_CROSS_TABLE_QUESTION]);

        } catch (\Exception $ex) {

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_CROSS_TABLE_QUESTION]);

        }
    }


    public function apiCrossTablefor1000time(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('luck') || !$request->has('hazel')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_CROSS_TABLE_QUESTION]);
        }


        try {
            $tableTempRand=DB::table('moteghate_table')
                ->where('table_base_id', rand(1,15))
//                ->where('moteghate_Table_id', 45961)
                ->orderBy(DB::raw('RAND()'))->limit(1)->first();

            $tableTemp = DB::table('moteghate_table')
                ->join('table_base', 'table_base.table_base_id', '=', 'moteghate_table.table_base_id')
                ->join('cell_info_table', 'cell_info_table.table_base_id', '=', 'table_base.table_base_id')
                ->select('moteghate_table.*', 'cell_info_table.*', 'table_base.*')
                ->where('table_base.table_base_id', '=', $tableTempRand->table_base_id)
                ->where('moteghate_table.moteghate_Table_id', '=', $tableTempRand->moteghate_Table_id)
//                ->where('moteghate_table.moteghate_Table_id', '=', 47172)
                ->get();

            User::IncreaseDecreaseHazelLuck($user->user_id,$request['hazel'],$request['luck']);
            $questions=[];
            $questions2= explode(",",$tableTemp[0]->questions);
            $table=[];
            $question_index=0;
            for($index1=0;$index1<count($tableTemp);$index1++){
                $desc_ans= explode("*",$questions2[$question_index]);
//                if($tableTemp[$index1]->)
                $firstQ=[];
                $secondQ=[];
                $firstQ['description']=$desc_ans[1];
                $firstQ['answer']=$desc_ans[0];
                $dir=explode(",",$tableTemp[$index1]->first_direction);

                $firstQ['answer_position_cells']='';
                if($dir[0]=='down')
                    $firstQ['position_code']='d,';
                elseif($dir[0]=='left')
                    $firstQ['position_code']='l,';
                $firstQ['position_code'].=$dir[1].','.$dir[2];
                if($tableTemp[$index1]->question_count==1)
                    $firstQ['question_position']=$tableTemp[$index1]->cell_index;
                elseif($tableTemp[$index1]->question_count==2)
                    $firstQ['question_position']=$tableTemp[$index1]->cell_index.',u';
                $answerIndex=explode(",",$tableTemp[$index1]->first_answer_index_cell);
                for($index2=0;$index2<count($answerIndex);$index2++){
                    $firstQ['answer_position_cells'].= $answerIndex[$index2].',';
                }
//                $firstQ['answerIndex']=$answerIndex;
                $cell=[];
                $cell['firstQ']=$firstQ;
                array_push($questions,$firstQ);



                $question_index++;
                if($tableTemp[$index1]->question_count==2){
                    $dir=explode(",",$tableTemp[$index1]->second_direction);
                    $answerIndex=explode(",",$tableTemp[$index1]->second_answer_index_cell);
                    $desc_ans= explode("*",$questions2[$question_index]);
                    $secondQ['description']=$desc_ans[1];
                    $secondQ['answer']=$desc_ans[0];



                    $secondQ['answer_position_cells']='';
                    if($dir[0]=='down')
                        $secondQ['position_code']='d,';
                    elseif($dir[0]=='left')
                        $secondQ['position_code']='l,';
                    $secondQ['position_code'].=$dir[1].','.$dir[2];

                    $secondQ['question_position']=$tableTemp[$index1]->cell_index.',d';
                    $answerIndex=explode(",",$tableTemp[$index1]->second_answer_index_cell);
                    for($index2=0;$index2<count($answerIndex);$index2++){
                        $secondQ['answer_position_cells'].= $answerIndex[$index2].',';
                    }
//                $firstQ['answerIndex']=$answerIndex;
                    array_push($questions,$secondQ);


                    $question_index++;
                }
                if($tableTemp[$index1]->question_count==1)
                    $table[$tableTemp[$index1]->cell_index]=$cell;


            }
            return json_encode(['cross_table_id'=>$tableTemp[0]->moteghate_Table_id,'questions'=>$questions,'width' => $tableTemp[0]->width_size,'height' => $tableTemp[0]->height_size,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_CROSS_TABLE_QUESTION]);

        } catch (\Exception $ex) {

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_CROSS_TABLE_QUESTION]);

        }
    }

}