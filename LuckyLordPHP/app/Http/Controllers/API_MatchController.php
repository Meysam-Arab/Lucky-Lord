<?php
namespace App\Http\Controllers;
use App\Category;
use App\HazelCost;
use App\Question;
use App\UserCategory;
use App\UserMatch;
use Carbon\Carbon;
use App\User;
use Illuminate\Http\Request;
use App\OperationMessage;
use Validator;
use Redirect;
use Session;
use Auth;
use DB;
use File;
use Log;
use App\RequestResponseAPI;
use Tymon\JWTAuth\Facades\JWTAuth;
use Exception;
use Route;
use App\LogEvent;
class API_MatchController extends Controller
{
    protected $userMatch;

    public function __construct(UserMatch $userMatch)
    {
        $this->userMatch = $userMatch;
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
        if (!$request->has('tag') || !$request->has('request_count') || !$request->has('bet') || !$request->has('chosen_category_id')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);

        }
        if($request['chosen_category_id']<-1 || $request['chosen_category_id']>Category::Category_Count){
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
        }
        ////////////////////////////////////////////

        try
        {
            DB::beginTransaction();

            if(UserMatch::translate_bet($request['bet'])>$user->hazel && $request['bet'] != 'u'){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
            }
            if(UserMatch::translate_bet($request['bet'])>$user->luck && $request['bet'] == 'u'){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
            }
            if($request['request_count']==1){
                ///check it==>> if this user exist  in previous game is wait not insert extra game for current user
//                if($user_matchs->user_id_1==$user->user_id && $user_matchs->user_2_spent_time==-1)
//                {
//
//                }
                $user_matchs = DB::table('user_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('user_id_2', null)
                    ->where('user_id_1', '=',$user->user_id)
                    ->orderBy('user_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();
                if(count($user_matchs)!=0){
                    return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                }

                //////////////////////////get current game(record) is wait player
                $user_matchs = DB::table('user_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('user_id_2', null)
                    ->where('user_id_1', '<>',$user->user_id)
                    ->orderBy('user_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();

                /////////if $user_matchs==null => there aren't any user for do game thus insert new record
                if(count($user_matchs)==0){
                    /////insert record to user_match table for this bet
                        $request['user_id_1']=$user->user_id;
                        $request['questions']=$request['chosen_category_id'];
                        $request['is_ended']=0;
                        $request['user_1_correct_count']=0;
                        $request['user_2_correct_count']=0;
                        $user_matchs_insert=new UserMatch();
                        $user_matchs_insert->intializeByRequest($request);
                        $user_matchs_insert->store();

                        DB::commit();
                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);

                }
                else{

                    if($user_matchs->user_id_1==$user->user_id){
                        //in the special mode that in accure very 00%percent!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم" );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserMatch::withTrashed()->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    ////////////////////fill this record
                    ////////////////////insert second person to user_id_2 and return opponent array
                    if($user_matchs->questions>= -1 && $user_matchs->questions<=Category::Category_Count)
                        $user_matchs->questions=Question::get_10_Random_question($user_matchs->questions,$request['chosen_category_id']);



                    DB::table('user_match')
                        ->where('user_match_id', $user_matchs->user_match_id)
                        ->update(['user_id_2' => $user->user_id,
                                    'user_2_spent_time'=>-1,
                                    'user_1_spent_time'=>-1,
                                    'questions'=>$user_matchs->questions
                            ]);

                    ///get question Object
                    $q_id=$request['questions'];

                    $questions=DB::table('question')
                        ->select('question_id','question_guid','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_matchs->questions))
                        ->orderByRaw("FIELD(question_id , ".$user_matchs->questions.") ASC")
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
                        ->where('user.user_id', $user_matchs->user_id_1)
//                        ->where('user_category.user_id', $user_matchs->user_id_1)
//                        ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                        ->havingRaw('user_category.user_id='.$user_matchs->user_id_1)
                        ->get();



                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserMatch::translate_bet($request['bet'])*-1;
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
                    return json_encode(['user_match_id'=>$user_matchs->user_match_id,'user_match_guid'=>$user_matchs->user_match_guid,'questions'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);

                }
            }
            else if($request['request_count']>1){

                //////////////////////////get current game is wait player
                $user_matchs = DB::table('user_match')
                    ->select('*')
                    ->where('bet', $request['bet'])
                    ->where('is_ended', '=' , 0)
                    ->where('user_id_1', '=',$user->user_id)
                    ->orWhere('user_id_2', '=',$user->user_id)
                    ->orderBy('user_match_id', 'DESC')
                    ->lockForUpdate()
                    ->first();

                if (count($user_matchs) == 0)
                {
                    return json_encode(['token' => $token,'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                }

                if($request['request_count'] == 8 && $user_matchs->user_id_2 == null){
//                        return 'return bot';
//                        User::get_random_user();

//                        $rand_user=User::get_random_user($user->user_id);
                    $rand_user=User::get_random_bot_user($user->user_id);
                    $enemy_user=[];
                    $enemy_user['image']=$rand_user[0]->image;
                    if($rand_user[0]->user_name==null)
                        $enemy_user['user_name']=$rand_user[0]->visitor_user_name;
                    else
                        $enemy_user['user_name']=$rand_user[0]->user_name;
                    $enemy_user['luck']=rand(2200,4000);
//                    $enemy_user['total_match_count']=rand(100,300);
//                    $enemy_user['win_match_count']=rand(1,100);

                    $user_matchs->questions=Question::get_10_Random_question($user_matchs->questions,rand(1,Category::Category_Count));

                    ////for test in user11
//                    $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                    if($myUser != null && $myUser->user_name=='user11'){
//                        $user_matchs->questions='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                    }
                    //////end

                    DB::table('user_match')
                        ->where('user_match_id', $user_matchs->user_match_id)
                        ->update(['user_id_2' => $rand_user[0]->user_id *-1,
                            'user_2_spent_time'=>-1,
                            'user_1_spent_time'=>-1,
                            'questions'=>$user_matchs->questions
                        ]);

                    $questions=DB::table('question')
                        ->select('question_id','question_guid','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_matchs->questions))
                        ->orderByRaw("FIELD(question_id , ".$user_matchs->questions.") ASC")
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
                    $decreaseMount=UserMatch::translate_bet($request['bet'])*-1;
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
                    return json_encode(['user_match_id'=>$user_matchs->user_match_id,'user_match_guid'=>$user_matchs->user_match_guid,'questions'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);

                }

                if($user_matchs->user_id_1==$user->user_id && $user_matchs->user_2_spent_time==-1)
                {
                    DB::beginTransaction();
                    ///// update  and hazel  for first player
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserMatch::translate_bet($request['bet'])*-1;
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
                    if($user_matchs->questions>= -1 && $user_matchs->questions<=Category::Category_Count)
                        $user_matchs->questions=Question::get_10_Random_question($user_matchs->questions,$request['chosen_category_id']);



                    DB::table('user_match')
                        ->where('user_match_id', $user_matchs->user_match_id)
                        ->update(['user_id_1' => $user->user_id,
                            'user_1_spent_time'=>-1,
                            'questions'=>$user_matchs->questions
                        ]);



                    ///get question Object
                    $q_id=$request['questions'];

                    $questions=DB::table('question')
                        ->select('question_id','question_guid','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_matchs->questions))
                        ->orderByRaw("FIELD(question_id , ".$user_matchs->questions.") ASC")
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
//                            ,'total_match_count','win_match_count'
                        )
                        ->where('user.user_id',abs($user_matchs->user_id_2))
                        ->get();


                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];

                    $user_is_equal=DB::table('user_match')->select('*')->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()
                        ->first();
                    if($user_is_equal->user_id_1==$user_is_equal->user_id_2){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserMatch::withTrashed()->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    DB::commit();
                    return json_encode(['user_match_id'=>$user_matchs->user_match_id,'user_match_guid'=>$user_matchs->user_match_guid,'questions'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);

                }

                if($user_matchs->user_id_2==$user->user_id && $user_matchs->user_1_spent_time==-1)
                {

                    ///// update  and hazel  for first playe
                    /////new hazel= old hazel - bet;
                    $decreaseMount=UserMatch::translate_bet($request['bet'])*-1;
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
                    if($user_matchs->questions>= -1 && $user_matchs->questions<=Category::Category_Count)
                         $user_matchs->questions=Question::get_10_Random_question($user_matchs->questions,$request['chosen_category_id']);



                    DB::table('user_match')
                        ->where('user_match_id', $user_matchs->user_match_id)
                        ->update(['user_id_1' => $user->user_id,
                            'user_1_spent_time'=>-1,
                            'questions'=>$user_matchs->questions
                        ]);
                    ///get question Object
                    $q_id=$request['questions'];

                    $questions=DB::table('question')
                        ->select('question_id','question_guid','description','category_id','answer','penalty')
                        ->whereIn('question_id', explode(",",$user_matchs->questions))
                        ->orderByRaw("FIELD(question_id , ".$user_matchs->questions.") ASC")
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
                        ->where('user.user_id', $user_matchs->user_id_2)
//                        ->where('user_category.user_id', $user_matchs->user_id_1)
//                        ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                        ->havingRaw('user_category.user_id='.$user_matchs->user_id_1)
                        ->get();



                    if($enemy_user[0]->user_name!=null)
                        $enemy_user[0]->user_name=$enemy_user[0]->user_name;
                    else
                        $enemy_user[0]->user_name=$enemy_user[0]->visitor_user_name;
                    $opponent=$enemy_user[0];

                    $user_is_equal=DB::table('user_match')->select('*')->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()
                        ->first();;
                    if($user_is_equal->user_id_1==$user_is_equal->user_id_2){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserMatch::withTrashed()->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                    DB::commit();
                    return json_encode(['user_match_id'=>$user_matchs->user_match_id,'user_match_guid'=>$user_matchs->user_match_guid,'questions'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);

                }

                if($user_matchs->user_id_2 !=null){
                    if($user_matchs->questions>= -1 && $user_matchs->questions<=Category::Category_Count)
                         $user_matchs->questions=Question::get_10_Random_question($user_matchs->questions,$request['chosen_category_id']);


                    DB::table('user_match')
                        ->where('user_match_id', $user_matchs->user_match_id)
                        ->update([
                            'questions'=>$user_matchs->questions
                        ]);
                        $questions=DB::table('question')
                            ->select('question_id','question_guid','description','category_id','answer','penalty')
                            ->whereIn('question_id', explode(",",$user_matchs->questions))
                            ->orderByRaw("FIELD(question_id , ".$user_matchs->questions.") ASC")
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
                            ->select('user.luck','user.image','user.user_name','user.visitor_user_name'
//                                ,'total_match_count','win_match_count'
                            )
                            ->where('user.user_id', $user_matchs->user_id_1)
//                            ->groupBy('user_category.user_id','user.luck','user.image','user.user_name')
//                            ->havingRaw('user_category.user_id='.$user_matchs->user_id_1)
                            ->get();

                    if($opponent[0]->user_name!=null)
                        $opponent[0]->user_name=$opponent[0]->user_name;
                    else
                        $opponent[0]->user_name=$opponent[0]->visitor_user_name;

                        ///// update  and hazel  for first player
                        /////new hazel= old hazel - bet;
                        $decreaseMount=UserMatch::translate_bet($request['bet'])*-1;
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


                    $user_is_equal=DB::table('user_match')->select('*')->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()
                        ->first();;
                    if($user_is_equal->user_id_1==$user_is_equal->user_id_2){
                        //in the special mode that in accure very 0000000000%!!!
                        $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: یک حالت خاص مسابقه با خود اتفاق افتاد که رکورد را پاک کردیم"."  REQUEST COUNT : ".$request['request_count'] );
                        $logEvent->store();

                        /////delete record and return nothing user exists
                        $uum = UserMatch::withTrashed()->where('user_match_id', $user_matchs->user_match_id)->lockForUpdate()->first();
                        $uum->delete();
                        DB::commit();
                        return json_encode(['error' => 0,'token' => $token,  'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }


                        DB::commit();
                        return json_encode(['user_match_id'=>$user_matchs->user_match_id,'user_match_guid'=>$user_matchs->user_match_guid,'questions'=>$questions,'opponent'=>$opponent[0],'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }

                if($user_matchs->user_id_2 ==null){
                    $chance=rand(1,100);
                    if($request['request_count']==2 && $chance>10){

                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==3 && $chance>11){

                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==4 && $chance>12){

                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==5 && $chance>13){

                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==6 && $chance>14){

                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }
                    else if($request['request_count']==7 && $chance>15){

                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
                    }
//                    else if($request['request_count']==8 && $chance>16){
//                        return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
//                    }
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
//                        $enemy_user['total_match_count']=rand(100,300);
//                        $enemy_user['win_match_count']=rand(1,100);

                        $user_matchs->questions=Question::get_10_Random_question($user_matchs->questions,rand(1,Category::Category_Count));

                        ////for test in user11
//                        $myUser=DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first();
//                        if($myUser != null && $myUser->user_name=='user11'){
//                            $user_matchs->questions='2815,1243,79,1014,2876,4512,4541,4513,4539,295,2696';
//                        }
                        //////end

                        DB::table('user_match')
                            ->where('user_match_id', $user_matchs->user_match_id)
                            ->update(['user_id_2' => $rand_user[0]->user_id *-1,
                                'user_2_spent_time'=>-1,
                                'user_1_spent_time'=>-1,
                                'questions'=>$user_matchs->questions
                            ]);

                        $questions=DB::table('question')
                            ->select('question_id','question_guid','description','category_id','answer','penalty')
                            ->whereIn('question_id', explode(",",$user_matchs->questions))
                            ->orderByRaw("FIELD(question_id , ".$user_matchs->questions.") ASC")
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
                        $decreaseMount=UserMatch::translate_bet($request['bet'])*-1;
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
                        return json_encode(['user_match_id'=>$user_matchs->user_match_id,'user_match_guid'=>$user_matchs->user_match_guid,'questions'=>$questions,'opponent'=>$opponent,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);


                    }



                }
            }
        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_REQUEST]);
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
        if (!$request->has('tag')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_INDEX]);
        }
        ////////////////////////////////////////////

        try
        {
            //////////////get all of the game that current user play on it! in 2 days ago
            $matches= DB::table('user_match')
                ->select('*')
                ->where([
                    ['user_id_1', '=', $user->user_id],
                    ['user_id_2', '<>', null],
                    ['user_1_spent_time', '<>', null],
                    ['deleted_at', '=', null],
//                    ['created_at', '>', Carbon::now()->subDays(20)],
                ])
                ->orWhere([
                    ['user_id_2', '=', $user->user_id],
                    ['user_id_1', '<>', null],
                    ['user_2_spent_time', '<>', null],
                    ['deleted_at', '=', null],
//                    ['created_at', '>', Carbon::now()->subDays(20)],
                ])
                ->orderBy('user_match_id', 'DESC')
                ->take(UserMatch::Match_count_that_show_in_index_page)
                ->get();


            //////////////////set bot game status in index time outed
            //////////////////set bot game status in index time outed
            //////////////////set bot game status in index time outed
            //////////////////set bot game status in index time outed
            for($index=0;$index<count($matches);$index++){
                if($matches[$index]->is_ended==0 && ($matches[$index]->user_id_1 || $matches[$index]->user_id_2 )){
                    $re=new Request();
                    $re['tag']=RequestResponseAPI::TAG_MATCH_STATUS;
                    $re['match_id']=$matches[$index]->user_match_id;
                    $re['match_guid']=$matches[$index]->user_match_guid;
                    SELF::apiStatus($re);
                }
            }
            $matches= DB::table('user_match')
                ->select('*')
                ->where([
                    ['user_id_1', '=', $user->user_id],
                    ['user_id_2', '<>', null],
                    ['user_1_spent_time', '<>', null],
                    ['deleted_at', '=', null],
//                    ['created_at', '>', Carbon::now()->subDays(20)],
                ])
                ->orWhere([
                    ['user_id_2', '=', $user->user_id],
                    ['user_id_1', '<>', null],
                    ['user_2_spent_time', '<>', null],
                    ['deleted_at', '=', null],
//                    ['created_at', '>', Carbon::now()->subDays(20)],
                ])
                ->orderBy('user_match_id', 'DESC')
                ->take(UserMatch::Match_count_that_show_in_index_page)
                ->get();




            /////////////extract opponent user_id in above query for optimize below query
            $user_ides='';

            for ($index=0;$index<count($matches);$index++){
                if($matches[$index]->user_id_1 != $user->user_id){
                    $user_ides=$user_ides.$matches[$index]->user_id_1.',';

//                    if( $matches[$index]->user_2_spent_time  == -1 ){
//                        $matches[$index]->user_2_spent_time==UserMatch::Time_OF_Match;
//                        $matches[$index]->save();
//                    }
                }
                if($matches[$index]->user_id_2 != $user->user_id){
                    $user_ides=$user_ides.$matches[$index]->user_id_2.',';

//                    if( $matches[$index]->user_1_spent_time  == -1 ){
//                        $matches[$index]->user_1_spent_time==UserMatch::Time_OF_Match;
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
                ->select('user_id','user_name','visitor_user_name','image','luck')
                ->whereIn('user_id', $exploded_item)
                ->get();

            ////////////update data of required(meysam ceo) for every matches
            for ($index=0;$index<count($matches);$index++){
                if(abs($matches[$index]->user_id_1) != $user->user_id){
                    for ($index2=0;$index2<count($user_image_lucks);$index2++){
                        if($user_image_lucks[$index2]->user_id ==abs($matches[$index]->user_id_1))
                        {
                            $matches[$index]->opponent_correct_count=$matches[$index]->user_1_correct_count;
                            $matches[$index]->self_correct_count=$matches[$index]->user_2_correct_count;
                            $matches[$index]->opponent_time=$matches[$index]->user_1_spent_time;
                            if($matches[$index]->user_2_spent_time==-1)
                                $matches[$index]->self_time=UserMatch::Time_OF_Match;
                            else
                                $matches[$index]->self_time=$matches[$index]->user_2_spent_time;

                            $matches[$index]->opponent_image=$user_image_lucks[$index2]->image;
                            $matches[$index]->opponent_luck=$user_image_lucks[$index2]->luck;
//                            $matches[$index]->opponent_user_name=$user_image_lucks[$index2]->user_name;
                            if($user_image_lucks[$index2]->user_name!=null)
                                $matches[$index]->opponent_user_name=$user_image_lucks[$index2]->user_name;
                            else
                                $matches[$index]->opponent_user_name=$user_image_lucks[$index2]->visitor_user_name;
                        }
                    }
                }
                if(abs($matches[$index]->user_id_2) != $user->user_id){
                    for ($index3=0;$index3<count($user_image_lucks);$index3++){
                        if($user_image_lucks[$index3]->user_id == abs($matches[$index]->user_id_2))
                        {

                            $matches[$index]->opponent_correct_count=$matches[$index]->user_2_correct_count;
                            $matches[$index]->self_correct_count=$matches[$index]->user_1_correct_count;
                            $matches[$index]->opponent_time=$matches[$index]->user_2_spent_time;
                            if($matches[$index]->user_1_spent_time==-1)
                                $matches[$index]->self_time=UserMatch::Time_OF_Match;
                            else
                                $matches[$index]->self_time=$matches[$index]->user_1_spent_time;

                            $matches[$index]->opponent_image=$user_image_lucks[$index3]->image;
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
                }
                else
                {
                    if($matches[$index3]->winner_id==$user->user_id){
                        $matches[$index3]->winner=UserMatch::win;
                    }else
                        $matches[$index3]->winner=UserMatch::lost;

                    if($matches[$index3]->winner_id === 0){
                        $matches[$index3]->winner=UserMatch::equal;
                    }

                    unset($matches[$index3]->winner_id);
                }
                unset($matches[$index3]->user_1_spent_time);
                unset($matches[$index3]->user_2_spent_time);
                unset($matches[$index3]->user_id_1);
                unset($matches[$index3]->user_id_2);
                unset($matches[$index3]->questions);
                unset($matches[$index3]->user_2_correct_count);
                unset($matches[$index3]->user_1_correct_count);
                unset($matches[$index3]->created_at);
                unset($matches[$index3]->updated_at);
                unset($matches[$index3]->deleted_at);

                if($matches[$index3]->is_ended=='1'){
                    unset($matches[$index3]->user_match_id);
                    unset($matches[$index3]->user_match_guid);
                }else{
                    unset($matches[$index3]->opponent_correct_count);
                    unset($matches[$index3]->opponent_time);
                }
            }
            $bets=[
                ['bet_id'=>UserMatch::l_key,'bet_amount'=>UserMatch::l,'bet_time'=>UserMatch::Time_OF_Match,'bet_interest'=>5],
                ['bet_id'=>UserMatch::m_key,'bet_amount'=>UserMatch::m,'bet_time'=>UserMatch::Time_OF_Match,'bet_interest'=>5],

//                ['bet_id'=>UserMatch::m_key,'bet_amount'=>700,'bet_time'=>UserMatch::Time_OF_Match,'bet_interest'=>5],

                ['bet_id'=>UserMatch::h_key,'bet_amount'=>UserMatch::h,'bet_time'=>UserMatch::Time_OF_Match,'bet_interest'=>5],
                ['bet_id'=>UserMatch::luck_key,'bet_amount'=>UserMatch::u,'bet_time'=>UserMatch::Time_OF_Match,'bet_interest'=>5,'bet_is_on_luck'=>1],
                ['bet_id'=>UserMatch::nitro_key,'bet_amount'=>UserMatch::n,'bet_time'=>UserMatch::Time_OF_NITRO_MATCH,'bet_interest'=>5,'bet_reward_time'=>15],

            ];
            return json_encode(['bets'=>$bets,'matches'=>$matches,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_INDEX]);


        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_INDEX]);

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
        if (!$request->has('tag') || !$request->has('match_id') || !$request->has('match_guid')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
        }
        ////////////////////////////////////////////
        try{


            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
            if(Carbon::now()->subSeconds(1000)>$user_match->created_at && $user_match->is_ended == 0){
                UserMatch::set_user_match_after_15min($request['match_id'],$request['match_guid']);
                $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
            }
            if($user_match!=false)
            {

                if($user_match->user_id_1==$user->user_id){
                    if($user_match->bet != 'u' && $user_match->user_id_2 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_match) < $user_match->created_at){
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                    }
                    if($user_match->bet == 'u' && $user_match->user_id_2 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_nitro_match) < $user_match->created_at){
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                    }
//                    $winner_id = UserMatch::get_winner_id($request['match_id'],false);
//                    if($user_match->user_1_spent_time==null || $user_match->user_1_spent_time==-1 ){
//                        UserMatch::update_time_match($request['match_id'],$request['match_guid'],1);
//                    }
                    if($user_match->user_2_spent_time != -1  || $user_match->user_id_2 < 0){
                        ////is ended game
                        $winner_id=UserMatch::get_winner_id($request['match_id'],true);
                        $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                        if($winner_id==1){
                            ////user_1  player  is winner
                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',$user->user_id)->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
                            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                            $winner['correct_count']=$user_match->user_2_correct_count;
                            $winner['time']=$user_match->user_2_spent_time;
                            $is_ended=1;
                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }elseif ($winner_id==2){
                            ////user_2  player  is winner
                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id', abs($user_match->user_id_2))->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
                            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                            $winner['correct_count']=$user_match->user_2_correct_count;
                            $winner['time']=$user_match->user_2_spent_time;
                            $is_ended=1;
                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }else{
                            //// players is equal score
                            $opponent=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_id_2))->first();
                            if($temp->user_name!=null)
                                $opponent['user_name'] = $temp->user_name;
                            else
                                $opponent['user_name'] = $temp->visitor_user_name;
                            $opponent['correct_count']=$user_match->user_2_correct_count;
                            $opponent['time']=$user_match->user_2_spent_time;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }
                    }
                    else{
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                    }

                }
                elseif ($user_match->user_id_2==$user->user_id){
                    if($user_match->user_id_1 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_match) < $user_match->created_at){
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                    }
                    if($user_match->bet == 'u' && $user_match->user_id_2 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_nitro_match) < $user_match->created_at){
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                    }
//                    $winner_id = UserMatch::get_winner_id($request['match_id'],false);
//                    if($user_match->user_2_spent_time==null || $user_match->user_2_spent_time==-1){
//                        UserMatch::update_time_match($request['match_id'],$request['match_guid'],2);
//                    }
                    if($user_match->user_1_spent_time != -1 ||  $user_match->user_id_1 < 0){
                        ////is ended game
                        $winner_id=UserMatch::get_winner_id($request['match_id'],false);
                        $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                        if($winner_id==1){
                            ////user_1  player  is winner
                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_id_1))->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
                            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                            $winner['correct_count']=$user_match->user_1_correct_count;
                            $winner['time']=$user_match->user_1_spent_time;
                            $is_ended=1;


                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }elseif ($winner_id==2){
                            ////user_2  player  is winner

                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',$user->user_id)->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
                            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                            $winner['correct_count']=$user_match->user_1_correct_count;
                            $winner['time']=$user_match->user_1_spent_time;;
                            $is_ended=1;

                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }else{
                            //// players is equal score
                            $opponent=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_id_1))->first();
                            if($temp->user_name!=null)
                                $opponent['user_name'] = $temp->user_name;
                            else
                                $opponent['user_name'] = $temp->visitor_user_name;
                            $opponent['correct_count']=$user_match->user_1_correct_count;
                            $opponent['time']=$user_match->user_1_spent_time;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }
                    }
                    else{
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                    }
                }
                else
                    return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
            }
            else
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);


        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);

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
        if (!$request->has('tag') || !$request->has('time') || !$request->has('questions') || !$request->has('match_id') || !$request->has('match_guid')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
        }
        ////////////////////////////////////////////
        try{

            $questions=[];
            $questions=json_decode($request['questions']);


            //////update user_category for questions of this game for
            for($index=0;$index<count($questions);$index++){
                $user_category = UserCategory::findBy_UserId_and_CategoryId($user->user_id,$questions[$index]->category_id);
                if($questions[$index]->is_correct==0){
                    //                                $user_category->newQuery()
                    DB::table('user_category')
                        ->where(['user_id'=> $user->user_id,'category_id'=>$questions[$index]->category_id])
                        ->update([
                            'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                            'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                            'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                        ]);
                }else{
//                                $user_category->newQuery()
                    DB::table('user_category')
                        ->where(['user_id'=> $user->user_id,'category_id'=>$questions[$index]->category_id])->update([
                            'total_answered_count' => \DB::raw( 'total_answered_count + 1' ),
                            'daily_total_answered_count' => \DB::raw( 'daily_total_answered_count + 1' ),
                            'weekly_total_answered_count' => \DB::raw( 'weekly_total_answered_count + 1' ),
                            'correct_answered_count' => \DB::raw( 'correct_answered_count + 1' ),
                            'daily_correct_answered_count' => \DB::raw( 'daily_correct_answered_count + 1' ),
                            'weekly_correct_answered_count' => \DB::raw( 'weekly_correct_answered_count + 1' ),
                        ]);
                }

            }

            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);

            if($user_match->bet =='n'){
                if($request['time']>UserMatch::Time_OF_NITRO_MATCH){
                    $request['time']=UserMatch::Time_OF_NITRO_MATCH;
                }
            }else{
                if($request['time']>UserMatch::Time_OF_Match){
                    $request['time']=UserMatch::Time_OF_Match;
                }
            }
            if($user_match!=false)
            {
                /////updte hazel and luck in hint time used user
                /////updte hazel and luck in hint time used user
                /////updte hazel and luck in hint time used user
                User::DecreaseHazelLuck($user->user_id,$request['used_hazel'],$request['used_luck']);



                if($user_match->user_id_1==$user->user_id)
                {
                    $corect_answered_count=0;
                    for($index=0;$index<count($questions);$index++){
                        if($questions[$index]->is_correct==1)
                            $corect_answered_count++;
                    }
                    ///initialize for update match
                    $request['user_match_id']=$request['match_id'];
                    $request['user_match_guid']=$request['match_guid'];
                    $request['user_1_correct_count']=$corect_answered_count;
                    $request['user_1_spent_time']=$request['time'];
                    $this->userMatch->intialize();
                    $this->userMatch->intializeByRequest($request);
                    $this->userMatch->edit($request);
                    unset($request['user_1_correct_count']);
                    unset($request['user_1_spent_time']);

                    if($user_match->user_2_spent_time==-1 && $user_match->user_id_2>0){
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                    }
                    if($user_match->user_2_spent_time > 0  || $user_match->user_id_2 < 0){
                        ////is ended game
                        if($user_match->user_id_2 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }
                        if($user_match->bet == 'u' && $user_match->user_id_2 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_nitro_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }
                        $winner_id = UserMatch::get_winner_id_for_result($request['match_id'],true,$request['time']);
                        $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);


                        if($winner_id==1){
                            ////user_1  player  is winner
                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',$user->user_id)->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
//                            $winner['user_name'] = DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first()->user_name;
                            $winner['correct_count']=$user_match->user_2_correct_count;
                            $winner['time']=$user_match->user_2_spent_time;
                            $is_ended=1;
                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }elseif ($winner_id==2){
                            ////user_2  player  is winner
                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_id_2))->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
//                            $winner['user_name'] = DB::table('user')->select('user_name')->where('user_id',abs($user_match->user_id_2))->first()->user_name;
                            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                            $winner['correct_count']=$user_match->user_2_correct_count;
                            $winner['time']=$user_match->user_2_spent_time;
                            $is_ended=1;
                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }else{
                            //// players is equal score
                            $opponent=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',$user_match->user_id_2)->first();
                            if($temp->user_name!=null)
                                $opponent['user_name'] = $temp->user_name;
                            else
                                $opponent['user_name'] = $temp->visitor_user_name;
//                            $opponent['user_name'] = DB::table('user')->select('user_name')->where('user_id',$user_match->user_id_2)->first()->user_name;
                            $opponent['correct_count']=$user_match->user_2_correct_count;
                            $opponent['time']=$user_match->user_2_spent_time;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }
                    }
                    else{
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                    }

                }
                elseif ($user_match->user_id_2==$user->user_id){

                    $corect_answered_count=0;
                    for($index=0;$index<count($questions);$index++){
                        if($questions[$index]->is_correct==1)
                            $corect_answered_count++;
                    }
                    $request['user_match_id']=$request['match_id'];
                    $request['user_match_guid']=$request['match_guid'];
                    $request['user_2_correct_count']=$corect_answered_count;
                    $request['user_2_spent_time']=$request['time'];
                    $this->userMatch->intialize();
                    $this->userMatch->intializeByRequest($request);
                    $this->userMatch->edit($request);
                    unset($request['user_2_correct_count']);
                    unset($request['user_2_spent_time']);


                    if($user_match->user_1_spent_time==-1 && $user_match->user_id_1>0){
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                    }
                    if($user_match->user_1_spent_time > 0 || $user_match->user_id_1 < 0){
                        ////is ended game
                        if($user_match->user_id_1 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }
                        if($user_match->bet == 'u' && $user_match->user_id_2 < 0 && Carbon::now()->subSecond(UserMatch::min_robat_after_start_nitro_match) < $user_match->created_at){
                            ////this game not end
                            $is_ended=0;
                            return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_STATUS]);
                        }



                        $winner_id=UserMatch::get_winner_id_for_result($request['match_id'],true,$request['time']);
                        $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                        if($winner_id==1){
                            ////user_1  player  is winner
                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',abs($user_match->user_id_1))->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
//                            $winner['user_name'] = DB::table('user')->select('user_name')->where('user_id',abs($user_match->user_id_1))->first()->user_name;
                            $user_match=UserMatch::findByIdAndGuid($request['match_id'],$request['match_guid']);
                            $winner['correct_count']=$user_match->user_1_correct_count;
                            $winner['time']=$user_match->user_1_spent_time;
                            $is_ended=1;


                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }elseif ($winner_id==2){
                            ////user_2  player  is winner

                            $winner=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',$user->user_id)->first();
                            if($temp->user_name!=null)
                                $winner['user_name'] = $temp->user_name;
                            else
                                $winner['user_name'] = $temp->visitor_user_name;
//                            $winner['user_name'] = DB::table('user')->select('user_name')->where('user_id',$user->user_id)->first()->user_name;
                            $winner['correct_count']=$user_match->user_1_correct_count;
                            $winner['time']=$user_match->user_1_spent_time;
                            $is_ended=1;

                            return json_encode(['winner'=>$winner,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }else{
                            //// players is equal score
                            $opponent=[];
                            $temp=DB::table('user')->select('user_name','visitor_user_name')->where('user_id',$user_match->user_id_1)->first();
                            if($temp->user_name!=null)
                                $opponent['user_name'] = $temp->user_name;
                            else
                                $opponent['user_name'] = $temp->visitor_user_name;
//                            $opponent['user_name'] = DB::table('user')->select('user_name')->where('user_id',$user_match->user_id_1)->first()->user_name;
                            $opponent['correct_count']=$user_match->user_1_correct_count;
                            $opponent['time']=$user_match->user_1_spent_time;
                            $is_ended=1;
                            return json_encode(['opponent'=>$opponent,'is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                        }
                    }
                    else{
                        ////this game not end
                        $is_ended=0;
                        return json_encode(['is_ended'=>$is_ended,'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                    }
                }
                else
                {
                    return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);
                }
            }else
            {
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);

            }



        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MATCH_RESULT]);

        }

    }
}