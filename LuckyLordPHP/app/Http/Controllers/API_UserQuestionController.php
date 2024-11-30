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
use App\User;
use App\UserQuestion;
use App\RequestResponseAPI;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Carbon\Carbon;



class API_UserQuestionController extends Controller
{

    protected $userQuestion;

    public function __construct(UserQuestion $userQuestion)
    {
        $this->userQuestion = $userQuestion;
    }

    /**
     * Store a new user.
     *
     * @param  Request $request
     * @return Response
     */
    public function apiStore(Request $request)
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
        if (!$request->has('tag') ||
            !$request->has('user_id') ||
            !$request->has('user_guid') ||
            !$request->has('question_id') ||
            !$request->has('question_guid') ||
            !$request->has('is_correct')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_STORE_USER_QUESTION]);

        }
        ////////////////////////////////////////////
        if(!Question::exist($request->input('question_id'), $request->input('question_guid')))
            return json_encode(['error' => RequestResponseAPI::ERROR_ITEM_NOT_EXIST_CODE, 'tag' => RequestResponseAPI::TAG_STORE_USER_QUESTION]);
        if(!UserQuestion::existByUserAndQuestion($request->input('user_id'), $request->input('question_id')))
            return json_encode(['error' => RequestResponseAPI::ERROR_UNAUTHORIZED_ACCESS_CODE, 'tag' => RequestResponseAPI::TAG_STORE_USER_QUESTION]);

        try {
            DB::beginTransaction();
            $request['is_correct'] = intval($request['is_correct']);

            if($request['is_correct'] == 1)
            {
                //give reward to user
//                $reward = Question::getQuestionReward($request->input('question_id'));
//                User::IncreaseScore($user->user_id,$reward);
            }

            $this->userQuestion->intializeByRequest($request);
            $this->userQuestion->store();
            DB::commit();
            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_STORE_USER_QUESTION]);
        } catch (\Exception $ex) {
            DB::rollback();

            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_STORE_USER_QUESTION]);

        }
    }
}