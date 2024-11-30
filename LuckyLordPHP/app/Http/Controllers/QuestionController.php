<?php

namespace App\Http\Controllers;
use App\BodyPart;
use App\Question;
use App\Question_Puzzle;
use App\Question_Temp;
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
use Image;
use App\RequestResponseAPI;
use Storage;
use Exception;
use Route;
use Browser;
use App\LogEvent;




class QuestionController extends Controller
{
    protected $question;

    public function __construct(Question $q)
    {
        try
        {
            $this->question = $q;

        }
        catch (\Exception $e)
        {
//            $logEvent = new LogEventRepository((Auth::check() == true?Auth::user()->user_id:-1),Route::getCurrentRoute()->getActionName(),"Main Message: ".$e->getMessage()." Stack Trace: ".$e->getTraceAsString());
//            $logEvent->store();
//
//            $message = new OperationMessage();
//            $message->initializeByCode(OperationMessage::OperationErrorCode);
//            return redirect()->back()->with('message', $message);


//            DB::rollback();
//            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
//            $logEvent->store();
//            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }

    }

    public function storeQuestion(Request $request){
        $this->validate($request, [
            'description' => 'required|max:15',
            'answer' => 'required|max:15',
            'identifyCode' => 'required',
            'category'=>'required|integer|min:1|digits_between:1,13',
        ]);
        try
        {

            session(['identifyCode' => $request ->input('identifyCode')]);

            $identifyFlag=false;
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            if($identifyFlag==false){
                $message = "کد شناسایی غلطه";
                return redirect()->back()->with('message', $message);
            }
            //for own user
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $this->question->intializeByRequest($request);
                    $this->question->store();
                    break;
                }
            }

            //for other user
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $otherUser=new Question_Temp();
                    $request['user_temp_id']=$request ->input('identifyCode');
                    $request['is_approved']=0;
                    $otherUser->intializeByRequest($request);
                    $otherUser->store();
                    break;
                }
            }


            $message = "عملیات خوب بود";
            // Store a piece of data in the session...
            session(['penalty' => $request ->input('penalty')]);
            session(['min_luck_reward' => $request ->input('min_luck_reward')]);
            session(['max_luck_reward' => $request ->input('max_luck_reward')]);
            session(['min_hazel_reward' => $request ->input('min_hazel_reward')]);
            session(['max_hazel_reward' => $request ->input('max_hazel_reward')]);
            session(['category' => $request ->input('category')]);
            session(['easyHard' => $request ->input('easyHard')]);
            session(['identifyCode' => $request ->input('identifyCode')]);
            session(['tabmenu' => 1]);

//            session(['myStatus' => 1]);

            $message = "عملیات با موفقیت انجام شد";
            return redirect()->back()->with('message', $message);
            // Retrieve a piece of data from the session...
            $value = session('key');



        }
        catch (\Exception $ex)
        {
//            session(['myStatus' => 0]);
            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function storeTestQuestion(Request $request){
        // Store a piece of data in the session...
        session(['penalty' => $request ->input('penalty')]);
        session(['min_luck_reward' => $request ->input('min_luck_reward')]);
        session(['max_luck_reward' => $request ->input('max_luck_reward')]);
        session(['min_hazel_reward' => $request ->input('min_hazel_reward')]);
        session(['max_hazel_reward' => $request ->input('max_hazel_reward')]);
        session(['category' => $request ->input('category')]);
        session(['easyHard' => $request ->input('easyHard')]);
        session(['identifyCode' => $request ->input('identifyCode')]);
        session(['tabmenu' => 4]);


        $this->validate($request, [
            'description' => 'required',
            'answer1' => 'required',
            'answer2' => 'required',
            'answer3' => 'required',
            'answer4' => 'required',
            'result' => 'required',
            'identifyCode' => 'required',
            'category'=>'required|integer|min:1|digits_between:1,13'
        ]);
        try
        {
            $identifyFlag=false;
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            if($identifyFlag==false){
                $message = "کد شناسایی غلطه";
                return redirect()->back()->with('message', $message);
            }
            $answer='';
            if($request ->input('result')=='1'){
                $answer=$request ->input('answer1').'*'.'#'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.$request ->input('answer4');
            }
            if($request ->input('result')=='2'){
                $answer=$request ->input('answer1').'#'.'*'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.$request ->input('answer4');
            }
            if($request ->input('result')=='3'){
                $answer=$request ->input('answer1').'#'.$request ->input('answer2').'#'.'*'.$request ->input('answer3').'#'.$request ->input('answer4');
            }
            if($request ->input('result')=='4'){
                $answer=$request ->input('answer1').'#'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.'*'.$request ->input('answer4');
            }


            //for own user
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $this->question->intializeByRequest($request);
                    $this->question->storeByAnswer($answer);
                    break;
                }
            }

            //for other user
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $otherUser=new Question_Temp();
                    $request['user_temp_id']=$request ->input('identifyCode');
                    $request['is_approved']=0;
                    $otherUser->intializeByRequest($request);
                    $otherUser->storeByAnswer($answer);
                    break;
                }
            }
            $message = "عملیات خوب بود";


            $message = "عملیات با موفقیت انجام شد";
            return redirect()->back()->with('message', $message);



        }
        catch (\Exception $ex)
        {

            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function updateImageQuestion(Request $request){
        // Store a piece of data in the session...
        session(['penalty' => $request ->input('penalty')]);
        session(['min_luck_reward' => $request ->input('min_luck_reward')]);
        session(['max_luck_reward' => $request ->input('max_luck_reward')]);
        session(['min_hazel_reward' => $request ->input('min_hazel_reward')]);
        session(['max_hazel_reward' => $request ->input('max_hazel_reward')]);
        session(['category' => $request ->input('category')]);
        session(['easyHard' => $request ->input('easyHard')]);
        session(['identifyCode' => $request ->input('identifyCode')]);
        session(['tabmenu' => 1]);

        $this->validate($request, [
            'description' => 'required',
            'answer1' => 'required',
            'answer2' => 'required',
            'answer3' => 'required',
            'answer4' => 'required',
            'result' => 'required',
            'identifyCode' => 'required',
            'category'=>'required|integer|min:1|digits_between:1,13',
            'question_temp_id'=>'required',

        ]);
        try
        {
            $identifyFlag=false;
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }

            if($identifyFlag==false){
                $message = "کد شناسایی غلطه";
                return redirect()->back()->with('message', $message);
            }


            $question_id=0;
            $flag=false;

            if($request ->input('answer2') != '' && $request ->input('answer3') != '' && $request ->input('answer4') != ''){
                $request['description']='#'.$request ->input('description');
                $answer='';
                if($request ->input('result')=='1'){
                    $answer=$request ->input('answer1').'*'.'#'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.$request ->input('answer4');
                }
                if($request ->input('result')=='2'){
                    $answer=$request ->input('answer1').'#'.'*'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.$request ->input('answer4');
                }
                if($request ->input('result')=='3'){
                    $answer=$request ->input('answer1').'#'.$request ->input('answer2').'#'.'*'.$request ->input('answer3').'#'.$request ->input('answer4');
                }
                if($request ->input('result')=='4'){
                    $answer=$request ->input('answer1').'#'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.'*'.$request ->input('answer4');
                }


            }
            else if($request ->input('answer1') != '' && $request ->input('answer2') == '' && $request ->input('answer3') == '' && $request ->input('answer4') == '')
            {
                $answer=$request ->input('answer1');
                $request['description']='#'.$request ->input('description');
            }

            //for own user
            if($request['status']==1){
                for($index=0;$index<count(Question::identifycode);$index++){
                    if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                        $request['question_id']= $request['question_temp_id'];
                        $this->question->intializeByRequest($request);
                        $question_id=$this->question->storeByAnswer($answer);
                        DB::table('question_temp')->where('question_temp_id', '=', $request['question_temp_id'])->delete();
                        $flag=true;
                        if (!$request->hasFile('fileLogo') && $flag) {

//                        $file =Input::file('fileLogo');
//                        $request->file('fileLogo')->storeAs('question',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
                            Storage::move('question_temp/'.$request['question_temp_id'].'.jpg','question/'.$request['question_temp_id'].'.jpg');


                        }
                        break;
                    }
                }
            }else if($request['status']==2){
                for($index=0;$index<count(Question::identifycode);$index++){
                    if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                        $request['question_id']= $request['question_temp_id'];
                        $this->question->intializeByRequest($request);
                        $question_id=$this->question->storeByAnswer($answer);
                        $flag=true;
                        if (!$request->hasFile('fileLogo') && $flag) {

//                        $file =Input::file('fileLogo');
//                        $request->file('fileLogo')->storeAs('question',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
                            Storage::copy('question_temp/'.$request['question_temp_id'].'.jpg','question/'.$request['question_temp_id'].'.jpg');


                        }
                        break;
                    }
                }
            }else if($request['status']==3){

                    DB::table('question_temp')->where('question_temp_id', '=', $request['question_temp_id'])->delete();
                    Storage::move('question_temp/'.$request['question_temp_id'].'.jpg','RecycleBin/'.$request['question_temp_id'].'.jpg');

            }

            $message = "عملیات خوب بود";

            $clubs=[ 'آرسنال','آتالانتا', 'منچستر یونایتد','چلسی', 'منچسترسیتی','آث میلان', 'بایرن مونیخ','اینترمیلان', 'رئال مادرید','بارسلونا', 'پاری سن ژرمن',
                'لیورپول', 'دورتموند', 'اتلتیکو مادرید','یوونتوس', 'وستهم یونایتد','کریستال پالاس', 'شالکه','دارمشتات', 'لیون','نیس', 'موناکو','جنوا', 'کالیاری','کیوو', 'والنسیا'];
            session(['description' =>9]);
            $question=DB::table('question_temp')->first();
            for($index=0;$index<count($clubs);$index++)
                if($clubs[$index]==$question->answer)
                    session(['description' =>8]);
            $message = "عملیات با موفقیت انجام شد";
            $question->answer=trim( $question->answer);
            return view('question.edit', ['question' =>$question])->with('message', $message);


        }
        catch (\Exception $ex)
        {

            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function storeImageQuestion(Request $request){
        // Store a piece of data in the session...

        session(['penalty' => $request ->input('penalty')]);
        session(['min_luck_reward' => $request ->input('min_luck_reward')]);
        session(['max_luck_reward' => $request ->input('max_luck_reward')]);
        session(['min_hazel_reward' => $request ->input('min_hazel_reward')]);
        session(['max_hazel_reward' => $request ->input('max_hazel_reward')]);
        session(['category' => $request ->input('category')]);
        session(['easyHard' => $request ->input('easyHard')]);
        session(['identifyCode' => $request ->input('identifyCode')]);
        session(['tabmenu' => 1]);


        $this->validate($request, [
            'description' => 'required',
            'fileLogo' => 'required|image|mimes:jpeg,png,jpg|max:50',
            'answer1' => 'required',
            'answer2' => 'required',
            'answer3' => 'required',
            'answer4' => 'required',
            'result' => 'required',
            'identifyCode' => 'required',
            'category'=>'required|integer|min:1|digits_between:1,13'
        ]);
        try
        {
            $identifyFlag=false;
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            if($identifyFlag==false){
                $message = "کد شناسایی غلطه";
                return redirect()->back()->with('message', $message);
            }


            $question_id=0;
            $flag=false;

            if($request ->input('answer2') != '' && $request ->input('answer3') != '' && $request ->input('answer4') != ''){
                $request['description']='#'.$request ->input('description');
                $answer='';
                if($request ->input('result')=='1'){
                    $answer=$request ->input('answer1').'*'.'#'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.$request ->input('answer4');
                }
                if($request ->input('result')=='2'){
                    $answer=$request ->input('answer1').'#'.'*'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.$request ->input('answer4');
                }
                if($request ->input('result')=='3'){
                    $answer=$request ->input('answer1').'#'.$request ->input('answer2').'#'.'*'.$request ->input('answer3').'#'.$request ->input('answer4');
                }
                if($request ->input('result')=='4'){
                    $answer=$request ->input('answer1').'#'.$request ->input('answer2').'#'.$request ->input('answer3').'#'.'*'.$request ->input('answer4');
                }


            }
            else if($request ->input('answer1') != '' && $request ->input('answer2') == '' && $request ->input('answer3') == '' && $request ->input('answer4') == '')
            {
                $answer=$request ->input('answer1');
                $request['description']='#'.$request ->input('description');
            }

            //for own user
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $this->question->intializeByRequest($request);
                    $question_id=$this->question->storeByAnswer($answer);
                    $flag=true;
                    if ($request->hasFile('fileLogo') && $flag) {
//                        $file =Input::file('fileLogo');
                        $request->file('fileLogo')->storeAs('question',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
//                        $file = $request->file('fileLogo');
//                        $fileName = $question_id . '.' . $file->guessClientExtension();
//                        $destinationPath = storage_path() . '\app\question';
//                        $file->move($destinationPath, $fileName);

//                        $img = Image::make($destinationPath. '\\' .$fileName);
//                        $img->resize(400, 300);

//                $img->text('؟ ناکم نیا مان', 120, 100, function($font) {
//                    $font->file(public_path('fonts/Yekan.ttf'));
//                    $font->size(35);
//                    $font->color('#0ff');
////                    $font->align('center');
////                    $font->valign('bottom');
//                });
//                        $img->save($destinationPath . '\\' . $fileName);


                    }
                    break;
                }
            }

            //for other user
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $otherUser=new Question_Temp();
                    $request['user_temp_id']=$request ->input('identifyCode');
                    $request['is_approved']=0;
                    $otherUser->intializeByRequest($request);
                    $question_id=$otherUser->storeByAnswer($answer);
                    $flag=true;
                    if ($request->hasFile('fileLogo') && $flag) {
//                        $file =Input::file('fileLogo');
                        $request->file('fileLogo')->storeAs('question_temp',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
//                        $file = $request->file('fileLogo');
//                        $fileName = $question_id . '.' . $file->guessClientExtension();
//                        $destinationPath = storage_path() . '\app\question_temp';
//                        $file->move($destinationPath, $fileName);

//                        $img = Image::make($destinationPath. '\\' .$fileName);
//                        $img->resize(400, 300);

//                $img->text('؟ ناکم نیا مان', 120, 100, function($font) {
//                    $font->file(public_path('fonts/Yekan.ttf'));
//                    $font->size(35);
//                    $font->color('#0ff');
////                    $font->align('center');
////                    $font->valign('bottom');
//                });
//                        $img->save($destinationPath . '\\' . $fileName);


                    }
                    break;
                }
            }







            $message = "عملیات خوب بود";


                $message = "عملیات با موفقیت انجام شد";
            return redirect()->back()->with('message', $message);



        }
        catch (\Exception $ex)
        {

            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function updatequestionImagepublic(Request $request){
        // Store a piece of data in the session...
        session(['penalty' => $request ->input('penalty')]);
        session(['min_luck_reward' => $request ->input('min_luck_reward')]);
        session(['max_luck_reward' => $request ->input('max_luck_reward')]);
        session(['min_hazel_reward' => $request ->input('min_hazel_reward')]);
        session(['max_hazel_reward' => $request ->input('max_hazel_reward')]);
        session(['category' => $request ->input('category')]);
        session(['easyHard' => $request ->input('easyHard')]);
        session(['identifyCode' => $request ->input('identifyCode')]);
        session(['tabmenu' => 2]);

        $this->validate($request, [
            'description' => 'required',
            'answer1' => 'required',
            'identifyCode' => 'required',
            'category'=>'required|integer|min:1|digits_between:1,13',
            'question_temp_id'=>'required',
        ]);
        try
        {

            $identifyFlag=false;
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            if($identifyFlag==false){
                $message = "کد شناسایی غلطه";
                return redirect()->back()->with('message', $message);
            }


            $question_id=0;
            $flag=false;


            $answer=$request ->input('answer1');
            $request['description']='#'.$request ->input('description');

            //for own user
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $request['question_id']= $request['question_temp_id'];
                    $this->question->intializeByRequest($request);
                    $question_id=$this->question->storeByAnswer($answer);
                    DB::table('question_temp')->where('question_temp_id', '=', $request['question_temp_id'])->delete();
                    $flag=true;
                    if (!$request->hasFile('fileLogo') && $flag) {
//                        $file =Input::file('fileLogo');
                        Storage::move('question_temp/'.$request['question_temp_id'].'.jpg','question/'.$request['question_temp_id'].'.jpg');
//                        $request->file('fileLogo')->storeAs('question',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
//                        $file = $request->file('fileLogo');
//                        $fileName = $question_id . '.' . $file->guessClientExtension();
//                        $destinationPath = storage_path() . '\app\question';
//                        $file->move($destinationPath, $fileName);

//                        $img = Image::make($destinationPath. '\\' .$fileName);
//                        $img->resize(400, 300);

//                $img->text('؟ ناکم نیا مان', 120, 100, function($font) {
//                    $font->file(public_path('fonts/Yekan.ttf'));
//                    $font->size(35);
//                    $font->color('#0ff');
////                    $font->align('center');
////                    $font->valign('bottom');
//                });
//                        $img->save($destinationPath . '\\' . $fileName);


                    }
                    break;
                }
            }

            //for other user
//            for($index=0;$index<count(Question::identifycodeForOther);$index++){
//                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
//                    $otherUser=new Question_Temp();
//                    $request['user_temp_id']=$request ->input('identifyCode');
//                    $request['is_approved']=0;
//                    $otherUser->intializeByRequest($request);
//                    $question_id=$otherUser->storeByAnswer($answer);
//                    $flag=true;
//                    if ($request->hasFile('fileLogo') && $flag) {
////                        $file =Input::file('fileLogo');
//                        $request->file('fileLogo')->storeAs('question_temp',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
////                        $file = $request->file('fileLogo');
////                        $fileName = $question_id . '.' . $file->guessClientExtension();
////                        $destinationPath = storage_path() . '\app\question_temp';
////                        $file->move($destinationPath, $fileName);
//
////                        $img = Image::make($destinationPath. '\\' .$fileName);
////                        $img->resize(400, 300);
//
////                $img->text('؟ ناکم نیا مان', 120, 100, function($font) {
////                    $font->file(public_path('fonts/Yekan.ttf'));
////                    $font->size(35);
////                    $font->color('#0ff');
//////                    $font->align('center');
//////                    $font->valign('bottom');
////                });
////                        $img->save($destinationPath . '\\' . $fileName);
//
//
//                    }
//                    break;
//                }
//            }







            $message = "عملیات خوب بود";
            session(['tabmenu' => 1]);
            $clubs=[ 'آرسنال','آتالانتا', 'منچستر یونایتد','چلسی', 'منچسترسیتی','آث میلان', 'بایرن مونیخ','اینترمیلان', 'رئال مادرید','بارسلونا', 'پاری سن ژرمن',
                'لیورپول', 'دورتموند', 'اتلتیکو مادرید','یوونتوس', 'وستهم یونایتد','کریستال پالاس', 'شالکه','دارمشتات', 'لیون','نیس', 'موناکو','جنوا', 'کالیاری','کیوو', 'والنسیا'];
            session(['description' =>9]);
            $question=DB::table('question_temp')->first();
            for($index=0;$index<count($clubs);$index++)
                if($clubs[$index]==$question->answer)
                    session(['description' =>8]);
            $message = "عملیات با موفقیت انجام شد";
            $question->answer=trim( $question->answer);
            return view('question.edit', ['question' => $question])->with('message', $message);




        }
        catch (\Exception $ex)
        {

            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function storequestionImagepublic(Request $request){
        // Store a piece of data in the session..
        session(['penalty' => $request ->input('penalty')]);
        session(['min_luck_reward' => $request ->input('min_luck_reward')]);
        session(['max_luck_reward' => $request ->input('max_luck_reward')]);
        session(['min_hazel_reward' => $request ->input('min_hazel_reward')]);
        session(['max_hazel_reward' => $request ->input('max_hazel_reward')]);
        session(['category' => $request ->input('category')]);
        session(['easyHard' => $request ->input('easyHard')]);
        session(['identifyCode' => $request ->input('identifyCode')]);
        session(['tabmenu' => 2]);

        $this->validate($request, [
            'description' => 'required',
            'fileLogo' => 'required|image|mimes:jpeg,png,jpg,gif,svg|max:50',
            'answer1' => 'required',
            'identifyCode' => 'required',
            'category'=>'required|integer|min:1|digits_between:1,13'
        ]);
        try
        {
            $identifyFlag=false;
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $identifyFlag=true;
                }
            }
            if($identifyFlag==false){
                $message = "کد شناسایی غلطه";
                return redirect()->back()->with('message', $message);
            }


            $question_id=0;
            $flag=false;


                $answer=$request ->input('answer1');
                $request['description']='#'.$request ->input('description');

            //for own user
            for($index=0;$index<count(Question::identifycode);$index++){
                if(Question::identifycode[$index][0]==$request ->input('identifyCode')){
                    $this->question->intializeByRequest($request);
                    $question_id=$this->question->storeByAnswer($answer);
                    $flag=true;
                    if ($request->hasFile('fileLogo') && $flag) {
//                        $file =Input::file('fileLogo');
                        $request->file('fileLogo')->storeAs('question',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
//                        $file = $request->file('fileLogo');
//                        $fileName = $question_id . '.' . $file->guessClientExtension();
//                        $destinationPath = storage_path() . '\app\question';
//                        $file->move($destinationPath, $fileName);
//                        $img = Image::make($destinationPath. '\\' .$fileName);
//                        $img->resize(400, 300);

//                $img->text('؟ ناکم نیا مان', 120, 100, function($font) {
//                    $font->file(public_path('fonts/Yekan.ttf'));
//                    $font->size(35);
//                    $font->color('#0ff');
////                    $font->align('center');
////                    $font->valign('bottom');
//                });
//                        $img->save($destinationPath . '\\' . $fileName);


                    }
                    break;
                }
            }

            //for other user
            for($index=0;$index<count(Question::identifycodeForOther);$index++){
                if(Question::identifycodeForOther[$index][0]==$request ->input('identifyCode')){
                    $otherUser=new Question_Temp();
                    $request['user_temp_id']=$request ->input('identifyCode');
                    $request['is_approved']=0;
                    $otherUser->intializeByRequest($request);
                    $question_id=$otherUser->storeByAnswer($answer);
                    $flag=true;
                    if ($request->hasFile('fileLogo') && $flag) {
//                        $file =Input::file('fileLogo');
                        $request->file('fileLogo')->storeAs('question_temp',$question_id.'.'.$request->file('fileLogo')->guessClientExtension());
//                        $file = $request->file('fileLogo');
//                        $fileName = $question_id . '.' . $file->guessClientExtension();
//                        $destinationPath = storage_path() . '\app\question_temp';
//                        $file->move($destinationPath, $fileName);

//                        $img = Image::make($destinationPath. '\\' .$fileName);
//                        $img->resize(400, 300);

//                $img->text('؟ ناکم نیا مان', 120, 100, function($font) {
//                    $font->file(public_path('fonts/Yekan.ttf'));
//                    $font->size(35);
//                    $font->color('#0ff');
////                    $font->align('center');
////                    $font->valign('bottom');
//                });
//                        $img->save($destinationPath . '\\' . $fileName);


                    }
                    break;
                }
            }







            $message = "عملیات خوب بود";


            $message = "عملیات با موفقیت انجام شد";
            return redirect()->back()->with('message', $message);



        }
        catch (\Exception $ex)
        {

            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function FullText(Request $request){
        $validation = Validator::make($request->all(), [
            'description' => 'required',
        ]);
        if($validation->passes()){
            $query_for_get_Serach_result="";
            if($request ->input('category') == 14)
                $query_for_get_Serach_result="SELECT description,answer,question_puzzle_id FROM question_puzzle WHERE MATCH(description) AGAINST(:desc)";
            else
                $query_for_get_Serach_result="SELECT description,answer,question_id FROM question WHERE MATCH(description) AGAINST(:desc)";
            $result = DB::select($query_for_get_Serach_result, ['desc' =>$request ->input('description')]);

            return response()->json([
                'success' => true,
                'searchResult'=>$result,
                'count'=>count($result),
            ]);
        }else{
            $errors = $validation->errors()->all();
            return response()->json([
                'success' => false,
                'message' => $errors
            ]);
        }
    }

    public function FullTextinImageTest(Request $request){
        $validation = Validator::make($request->all(), [
            'description' => 'required',
        ]);
        if($validation->passes()){


            $query_for_get_Serach_result="SELECT  question_guid,description,answer,question_id FROM question WHERE SUBSTRING(description , 1 ,1)='#' AND answer LIKE '%".$request ->input('description')."%'";
            $result = DB::select($query_for_get_Serach_result);



            $count=0;
            for($index=0;$index<count($result);$index++){

                $result[$index]->image='<img class="img-circle" style="display: block;margin: 10px auto;width: 170px;height: 170px;" src="'.route('question.image', ['filename' =>$result[$index]->question_id,'path'=>'question']).'">';
//                route('question.image', ['filename' =>$result[$index]->question_guid]);
//                $temp=substr($result[$index]->description,0,1);
//
//                if ($temp == '#') {
//                    $count++;
//                }
//                $result[$index]->image=self::getImage('5956b01e1c64c6.57836526');

            }
            return response()->json([
                'success' => true,
                'searchResult'=>$result,
                'count'=>count($result),
            ]);
        }else{
            $errors = $validation->errors()->all();
            return response()->json([
                'success' => false,
                'message' => $errors
            ]);
        }
    }

    public function storecity(){
        $IranCities=[
            ["آذرشهر","اسکو","اهر","بستان‌آباد","بناب","تبریز","جلفا","چاراویماق","سراب","شبستر","عجب‌شیر","کلیبر","مراغه","مرند","ملکان","میانه","ورزقان","هریس","هشترود"],
            ["ارومیه","اشنویه","بوکان","پیرانشهر","تکاب","چالدران","خوی","سردشت","سلماس","شاهین‌دژ","ماکو","مهاباد","میاندوآب","نقده"],
            ["اردبیل","بیله‌سوار","پارس‌آباد","خلخال","کوثر","گرمی","مشگین شهر","نمین","نیر"],
            ["آران و بیدگل","اردستان","اصفهان","برخوردار و میمه","تیران و کرون","چادگان","خمینی‌شهر","خوانسار","سمیرم","شهرضا","سمیرم سفلی","فریدن","فریدون‌شهر","فلاورجان","کاشان","گلپایگان","لنجان","مبارکه","نائین","نجف‌آباد","نطنز"],
            ["ساوجبلاغ","طالقان","کرج","نظرآباد"],
            ["آبدانان","ایلام","ایوان","دره‌شهر","دهلران","شیروان و چرداول","مهران"],
            ["بوشهر","تنگستان","جم","دشتستان","دشتی","دیر","دیلم","کنگان","گناوه"],
            ["ورامین","فیروزکوه","شهریار","شمیرانات","ری","رباط‌کریم","دماوند","تهران","پاکدشت","اسلام‌شهر"],
            ["اردل","بروجن","شهرکرد","فارسان","کوهرنگ","لردگان"],
            ["بیرجند","درمیان","سرایان","سربیشه","فردوس","قائنات","نهبندان"],
            ["بردسکن","تایباد","تربت جام","تربت حیدریه","چناران","خلیل‌آباد","خواف","درگز","رشتخوار","سبزوار","سرخس","فریمان","قوچان","کاشمر","کلات","گناباد","مشهد","مه ولات","نیشابور"],
            ["اسفراین","بجنورد","جاجرم","شیروان","فاروج","مانه و سملقان"],
            ["آبادان","امیدیه","اندیمشک","اهواز","ایذه","باغ‌ملک","بندر ماهشهر","بهبهان","خرمشهر","دزفول","دشت آزادگان","رامشیر","رامهرمز","شادگان","شوش","شوشتر","گتوند","لالی","مسجد سلیمان","هندیجان"],
            ["ابهر","ایجرود","خدابنده","خرمدره","زنجان","طارم","ماه‌نشان"],
            ["دامغان","سمنان","شاهرود","گرمسار","مهدی‌شهر"],
            ["ایرانشهر","چابهار","خاش","دلگان","زابل","زاهدان","زهک","سراوان","سرباز","کنارک","نیک‌شهر"],
            ["آباده","ارسنجان","استهبان","اقلید","بوانات","پاسارگاد","جهرم","خرم‌بید","خنج","داراب","زرین‌دشت","سپیدان","شیراز","فراشبند","فسا","فیروزآباد","قیر و کارزین","کازرون","لارستان","لامرد","مرودشت","ممسنی","مهر","نی‌ریز"],
            ["آبیک","البرز","بوئین‌زهرا","تاکستان","قزوین"],
            ["قم"],
            ["بانه","بیجار","دیواندره","سروآباد","سقز","سنندج","قروه","کامیاران","مریوان"],
            ["بافت","بردسیر","بم","جیرفت","راور","رفسنجان","رودبار جنوب","زرند","سیرجان","شهر بابک","عنبرآباد","قلعه گنج","کرمان","کوهبنان","کهنوج","منوجان"],
            ["اسلام‌آباد غرب","پاوه","ثلاث باباجانی","جوانرود","دالاهو","روانسر","سرپل ذهاب","سنقر","صحنه","قصر شیرین","کرمانشاه","کنگاور","گیلان غرب","هرسین"],
            ["بویراحمد","بهمئی","دنا","کهگیلویه","گچساران"],
            ["آزادشهر","آق‌قلا","بندر گز","ترکمن","رامیان","علی‌آباد","کردکوی","کلاله","گرگان","گنبد کاووس","مراوه‌تپه","مینودشت"],
            ["آستارا","آستانه","اشرفیه","املش","بندر انزلی","رشت","رضوانشهر","رودبار","رودسر","سیاهکل","شفت","صومعه‌سرا","طوالش","فومن","لاهیجان","لنگرود","ماسال"],
            ["ازنا","الیگودرز","بروجرد","پل‌دختر","خرم‌آباد","دورود","دلفان","سلسله","کوهدشت"],
            ["آمل","بابل","بابلسر","بهشهر","تنکابن","جویبار","چالوس","رامسر","ساری","سوادکوه","قائم‌شهر","گلوگاه","محمودآباد","نکا","نور","نوشهر"],
            ["آشتیان","اراک","تفرش","خمین","دلیجان","زرندیه","ساوه","شازند","کمیجان","محلات"],
            ["ابوموسی","بستک","بندر عباس","بندر لنگه","جاسک","حاجی‌آباد","خمیر","رودان","قشم","گاوبندی","میناب"],
            ["اسدآباد","بهار","تویسرکان","رزن","کبودرآهنگ","ملایر","نهاوند","همدان"],
            ["ابرکوه","اردکان","بافق","تفت","خاتم","صدوق","طبس","مهریز","میبد","یزد"],
        ];
        $stateOfIran=["آذربایجان‌شرقی","آذربایجان‌غربی","اردبیل","اصفهان",
                        "البرز","ایلام","بوشهر","تهران",
                        "چهارمحال‌و‌بختیاری","خراسان‌جنوبی","خراسان‌رضوی","خراسان‌شمالی",
                        "خوزستان","زنجان","سمنان","سیستان‌و‌بلوچستان",
                         "فارس","قزوین","قم","کردستان",
                        "کرمان","کرمانشاه","کهکیلویه‌و‌بویراحمد","گلستان",
                        "گیلان","لرستان","مازندران","مرکزی",
                        "هرمزگان","همدان","یزد"];

//            $q=[0,0,0,0,0,0,0,0,0,0,0,0,0,0];
//            for($i=0;$i<14000;$i++){
//                $q[rand(0,13)]++;
//            }
//


        $statesCount=0;

        $count=0;
        for ($indexj=0;$indexj<count($stateOfIran);$indexj++) {
            for($index=0;$index<count($IranCities[$indexj]);$index++){
                if($stateOfIran[$indexj]==$IranCities[$indexj][$index])continue;
                for($index2=0;$index2<6;$index2++){
                    $count++;
                    $req=new Request();
                    $req['category']=6;
                    $req['penalty']=250;
                    $req['min_luck_reward']=2;
                    $req['max_luck_reward']=4;
                    $req['min_hazel_reward']=7;
                    $req['max_hazel_reward']=14;
                    $req['description']='کدام شهرستان جزء استان " '.$stateOfIran[$indexj].' " است؟';

                    $answer="";
                    $answer1=$IranCities[$indexj][$index];
                    $answer2="";
                    $answer3="";
                    $answer4="";
                    do {
                        $i=rand(0,count($stateOfIran)-1);
                        $answer2=$IranCities[$i][rand(0,count($IranCities[$i])-1)];
                    } while ($i==$indexj);

                    do {
                        $i=rand(0,count($stateOfIran)-1);
                        $answer3=$IranCities[$i][rand(0,count($IranCities[$i])-1)];
                    } while ($i==$indexj || strcmp($answer3,$answer2)==0);

                    do {
                        $i=rand(0,count($stateOfIran)-1);
                        $answer4=$IranCities[$i][rand(0,count($IranCities[$i])-1)];
                    } while ($i==$indexj || strcmp($answer4,$answer2)==0 || strcmp($answer4,$answer3)==0 );
                    $req['answer']=$answer1.'*'.'#'.$answer2.'#'.$answer3.'#'.$answer4;



                    $id = DB::table('question')->insertGetId(
                        ['question_guid' => uniqid('',true),
                            'category_id' => 6,
                            'description' => $req['description'],
                            'answer' => $req['answer'],
                            'penalty' => 250,
                            'min_luck_reward' => 1,
                            'max_luck_reward' => 1,
                            'min_hazel_reward' => 7,
                            'max_hazel_reward' => 14,
                            'created_at' => Carbon::now(),
                            'updated_at' => Carbon::now(),
                        ]
                    );

                }
            }
        }






//        foreach ($IranCities as $states) {
//
//            foreach ($states as $city) {
//                if($stateOfIran[$statesCount] != $city){
//                    $req=new Request();
//                    $req['category']=6;
//                    $req['penalty']=250;
//                    $req['min_luck_reward']=2;
//                    $req['max_luck_reward']=4;
//                    $req['min_hazel_reward']=7;
//                    $req['max_hazel_reward']=14;
//                    $req['description']='شهرستان " '.$city.' " در کدام استان قرار دارد؟';
//                    if($statesCount==0 || $statesCount==1){
//                        if($statesCount==0){
//                            $req['answer']=$stateOfIran[0].'*'.'#'.$stateOfIran[1].'#'.$stateOfIran[2].'#'.$stateOfIran[3].'#'.$stateOfIran[4];
//                        }
//                        if($statesCount==1){
//                            $req['answer']=$stateOfIran[1].'*'.'#'.$stateOfIran[0].'#'.$stateOfIran[2].'#'.$stateOfIran[3].'#'.$stateOfIran[4];
//                        }
//                    }elseif ($statesCount==29 || $statesCount==30){
//                        if($statesCount==29){
//                            $req['answer']=$stateOfIran[29].'*'.'#'.$stateOfIran[30].'#'.$stateOfIran[28].'#'.$stateOfIran[27].'#'.$stateOfIran[26];
//                        }
//                        if($statesCount==30){
//                            $req['answer']=$stateOfIran[30].'*'.'#'.$stateOfIran[29].'#'.$stateOfIran[28].'#'.$stateOfIran[27].'#'.$stateOfIran[26];
//                        }
//                    }else{
//                        $req['answer']=$stateOfIran[$statesCount].'*'.'#'.$stateOfIran[$statesCount+1].'#'.$stateOfIran[$statesCount+2].'#'.$stateOfIran[$statesCount-1].'#'.$stateOfIran[$statesCount-2];
//                    }
////                    $this->question->intializeByRequest($req);
//                    $qu_id=$this->question->storeByAnswerGetId($req['answer'],$qu_id);
//                    $qu_id++;
//                    $id = DB::table('question')->insertGetId(
//                        ['question_guid' => uniqid('',true),
//                            'category_id' => 6,
//                            'description' => $req['description'],
//                            'answer' => $req['answer'],
//                            'penalty' => 250,
//                            'min_luck_reward' => 2,
//                            'max_luck_reward' => 4,
//                            'min_hazel_reward' => 7,
//                            'max_hazel_reward' => 14,
//                            'created_at' => Carbon::now(),
//                            'updated_at' => Carbon::now(),
//                        ]
//                    );
//                }
//            }
//            $statesCount++;
//        }
    }

    public function SetUserName(){





        $user_name_meysam_mens=[
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
        ];
        $user_name_meysam_girls=[
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
        ];





        $user_name_amir_mens=[
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
        ];
        $user_name_amir_girls=[
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
        ];







        $user_name_hooman_girls=[
            "mGolshan","pari39","TATU1374","azera","pariii","saraqsp","hamiy","anitaMHZ","hss400","hiphop","persona","parandak","zahra","zahrabh","yazahra","yahraOmid","zahra96","misszahra",
            "JAVAzahra","zahraB6","zahra70","zahraJoon","fatima28","fatifati","fatij","fatimasafari","fation","fatimatbs","fatiw","fatix",
            "fatibabazade","Zeus","shadi7071","shadiii","sooori","sorayya","sooosan","soolmaz","sogand","simin","shparak","shahdokht","shabnam","shokuufe","shahrbano",
            "shahrnoosh","shohre","shahla","shahin","sheyda","shide","shima","shirin","shina","leila","ghonche","fattane","faranak","tarzane",
            "fereshte","farnaz","tarangis","farnoosh","forough","fariba","faridokht","farinaz","firooze","katayoon","marjan","kereshme",
            "kiyana","diyana","gelare","golaviz","golbad","golbar","golbanoo","golpari","golchin","golrokh","golshan","golshid","golnar","golnaz",
            "golnoosh","golikhanum","gisoo","lale0065","lumana","maral","mariya","mandana","mahrokh","mojde21","moji77","mastane","lanizhe","mahtabKeramati",
            "mehrnaz","mahasti","bitabato","hayde","mahshad","mahnoosh","mahvash","mahin","minimiyu","nazinazi","nazanin069","nedaKSP","narges","narges1376",
            "nastaran","nasrin","nasrinT","nasrin22","negar","negin","navaTarane","noosha","nooshin","niloofar","soozan","Arooosha","Artemiss","AssssA","Atanaz",
            "Atena","Atuusa","Ayda1201","Azarnoush","Azita","elika","elmira","Anahita","behnoush","tara66","tinaBSG","Dorsa","DornaKL","Roksana","zhale","sharare",
            "lidaMobarrez","mahshidSadeghi","melisaBagheri","MItraBabak","NaZnush","nuuusha","niiika","nahid","HomaAirLine","ZahraTavajohi0021",
            "FatemeAbedi","ZeynabZeynabAst","Maryam","Roghayye","MinaFArham","shiva0079","simaTirAndaz","RoyaRoya","somayye","mahsaJavid","AzadeSamadi",
            "vishkaAsayesh","mahnazAfshar","Ahuuuu","Afsane","IranDokht","BanoyeSharghi","Banafshe","bahare","baharak","behnaz","bitabihamta","parastuTak",
            "Parniya","parvane","parvin","ForoughFarokhZad","pariDarya","parichehr","paridokht","parisa","parnaz","parivash","pariiya","poopak",
            "PouranDokht","pune123","tAbAn","Tarane","Darya","HarBarInDaro...","chehrZad","delaram","Delband","BanuDelKash","MalakeDelroba","Diba73",
            "Ramesh","Rokhsar","Roshanak","ZariZari","ZibaBoruufe","ZHina","SaraSarhadi","SareBayat","Saghar","Sanaz","Sayna","SayeTanha","Sepide","Setare",
            "Samira","Soodabe","Soheyla","Eshrat","RuuudAbe","HenGame","FariBa071","ستاره","سمیرا","سودابه","سهیلا","عشرت",
            "سوری","ثریا","سوسن","سولماز","سوگند","سیمین","شاپرک","شادی","شاهدخت","شبنم","شکوفه","شهربانو","شهرنوش",
            "شهره","شهلا","شهین","شیدا","شیده","شیما","شیرین","شینا","لیلا","غنچه","فتانه","فرانک","فرزانه","فرشته","فرناز","فرنگیس","فرنوش","فروغ",
            "فریبا","فریدخت","فریناز","فیروزه","کتایون","مرجان","کرشمه","کیانا","دیانا","گلاره","گلاویز","گلباد","گلبار","گلبانو","گلپری","گلچین","گلرخ",
            "گلشن","گلشید","گلنار","گلناز","گلنوش","گلی","گیسو","لاله","لومانا","مارال","ماریا","ماندانا","ماهرخ","مژده","مژگان","مستانه","منیژه",
            "مهتاب","مهرناز","مهستی","هایده","مهشاد","مهنوش","مهوش","مهین","مینو","نازی","نازنین","ندا","نرگس","نسترن","نسرین","نگار","نگین","نوا",
            "نوشین","نیلوفر","سوزان","آروشا","آرتمیس","آسا","آتاناز","آتنا","آتوسا","آیدا","آذرنوش","آزیتا","الیکا","المیرا","آناهیتا","بهنوش","تارا","تینا","درسا","درنا",
            "رکسانا","ژاله","شراره","لیدا","مهشید","ملیسا","میترا","نازنوش","نوشا","نیکا","ناهید","هما","زهرا","فاطمه","زینب","مریم","رقیه","مینا",
            "شیوا","سیما","رویا","سمیه","مهسا","آزاده","مهناز","آهو","افسانه","ایراندخت","بانو","بنفشه","بهاره","بهارک","بهناز","بیتا","پرستو","پرنیا",
            "پروانه","پروین","پری","پریچهر","پریدخت","پریسا","پرناز","پریوش","پریا","پوپک","پوراندخت","پونه","تابان","ترانه","دریا","چهرزاد","دلارام",
            "دلبند","دلربا","دلناز","دیبا","رامش","رخسار","روشنک","زری","زیبا","ژینا","سارا","ساره","ساغر","ساناز","ساینا","سایه","سپیده",

        ];
        $user_name_hooman_mens=[
            "reza0","EXORCIST","ROOCKET","fasele","KINGSAJED","po0oria","HolorKing","hesampoorazimi","sashu","alireza2376","shervin686",
            "mohammadashkani","masoud12","sullivan","Majid57139","RAIK","yoons","joem","javad72","carrol","mojtaba25","MohhammadMahdikhani",
            "weed","darBdar","hadi7418","mehranfb","yaemamzaman","nimaab74","WOLF","afzal","sorena1374","farzzad","ali68xXx4","poool2",
            "BigPanZer","kiansolo","javadbest","niima68","mMehdiN1","esiano","saman65","AliKarami74","ArashSeraji","DoctorM","foaaadd","AlirezaFrz",
            "Enzo","samiKing","samer75","pproffesor","Morieta","behzadbhri","sharon","mohhammad73","bahmanmzf","hadiMr","arman1993",
            "hosseinAhmadi","mehdi1001","hamedfarhangrad","omrankto","saad3x","rezaHaghNazari","hamedsafir","omid","amir8","sadegh22","hajmoshtagh",
            "Rahnama","KillerOfQuiz","davoodparcham","rezabahmiyari","adelche","rezaaa","shabshekar","saeed8","alirezazsokhandan","ho3einRz",
            "AliKHodadadi","abbas67","arashKing","eddie6501","armindarabi","ONLY","marmar","HDT68","eza4421","yonsm","mojtabaxxx","hesamheidari",
            "sibzamini","MasoudmehranBan","alipouria","lahimchiEMS","mohammad242","Ali68xXx","khalilniknam","mohsen19sadeghi","ho3einktm","mmehdiii75",
            "8persian8","khchess","alitaheri","mahdivafa","miladnavabi","zaxscdvf","f14TOMCET","hojjat1372","LORDLOSS","crazy","morientez","james",
            "rezamirzazade","seyed2","elyasss","yashsinAzarbayjan","sadeghche","PesarEhsasi","28asghar","aghamorteza","hamed1372Amiri","mehran1azimi",
            "HAMED5169","jalalyazd","emperturr","mohammadrezalove76","moslem62","milad021","mamreza98","Masoud62","yoones66","shahbaz","farhadyY","reza212",
            "esmaeil00R","naviiid","bakhte100darsad","parsaww","petiboor","esteghlal","omidhhhh","Alipvd","majiid","ANGUS","mehdiaraz","shahinMaghsoodi","mohsen0206",
            "kia2017","GOToFight","Helboys","armini","milo","akamo","farzintahriri","QameOverr","elyassss","khalmalki","emadW0W","esteghlal2",
            "mohammadb95","jahan","amiin","khasmeShab","matinKhorjenkar","farsha","farshadkhan","darrbdarr","javid","KingOfIran","ardashir","00Majid00",
            "ALEXA","sajad58","hero2017","RaminB","eh3anKQZ","alirezaz","aj13744","Behriuzina","Ajzalzahi","Ali27","RaminBik","ahmad666","asefm",
            "babirich","Mhdavati","Alir3za","Safizade","kermoo","javad93","Gangster","mevil65","shahinblack","kian24","ehsan1375","siina46","llifar",
            "benyamin70","amirsa12","samiyar","saied67","amiri9170","mohammad64salehi","pirbaba","mhadad","topplayer","Azazel","garcia","mehrdadtork",
            "miladShadow","ho3ienMonsef","alireza1983","salmanbabapour","xxerfanxx","erfan75","panda","mehran123","toraj0073","arianp75","soheil021",
            "borhan619","bileh1suvar","misagh","miladmht","saeed75","saeedcris","topsalan","shekarchii","amirbear","Melkan1","mehrdadKing","payman",
            "xxkingxx","ghafour","saberi","hadad","morteza4344","masoud7171","melkan3","abbas22","mehdigoli","rezak60","saeedjoon63","shahram065",
            "masoud11111","imover","instagramsaad3x","peymankhalili7727","farhadam","shajarian","saeid22teh","mamadoalaki","esi1370","Ali68xXx3","behyyy",
            "meysam3D","fcbarcelonaa","masoudcnc","Alifor","xxsahandxx","farzzzzad","ho3ieno","Mamada","Amirarsalan3132","jamer","sasan1988","HUNTER",
            "MiladRohani","javadK","ebraaaa","amin1424","OverLord","amirking20","germy","saeed0P","omidkm","AmirA72","Tohid2199","avash01","vito","majak",
            "abdola","Amir3js","sosha","Alireza8071","esi1372","sajad1373t","TANHA007","hamed960","AirWOLF","mahanBlack","elyaaaaas","meysam75","esi1369",
            "dadgar1989","abol7Roll","majidAhmadi","behnam7","tabriz","arashshakor","esi1371","67oghab","saber2017","behesht","mhd3","rasolranjbar",
            "behzadKing","deadmanakbar","masoodmoradi","meysam3080","darkboy495","powerfull","GodOfWaar","arash80","Canan","bahramraper","mehrdad64",
            "saberkhan2","saeed1355","amifbi","hossienbibak","badel0166","hama","saeed63joon","mortezayam","hamze24","pedro","reza68","sasanSD","hoseinbibak1","omidkkzz",
            "saminsamin","almata","virangar1","SaSa","CR7mf","milad21","google","siyamakkhan","hoc061","mrchavez","MasterBoy","majidgh","hojjatCR7","Amir100",
            "kian1ta7","Mahdi83","soltan76","hadinervy","patoshik","abbaspir","Hektor","mtn1396","LODERKING","AMIR08","DadaMasoud","Mehran2090",
            "asghar7596","mohammadxxx","farhad5870","aliazin","amiiiiin","monashe","MehdiSi","PINOKIO","uooni","hamedhunter","hossein12100","masoud41",
            "sobihp","MortezaMD","hasanoudi","Naderafshar","ankaboot","esmaeil778","AliIr19","fered4","kapras","HamedAmiri993","Me6ki","vahidmsv","mobinrz95",
            "Helboy121","mpaki","SpiderMan","mehdiezadi","danialMehrdad","mehsan","instaalikra2","hamyid","mohammad2d","shahinshakeri","amirj1374",
            "malekixxx","meysamabi","amir09","AliMahdavi","Etivand","mehdiem","webnet","arashm24","babak0077","googooli",
            "پارسا","پدرام","رادمهر","رادین","رها","ساسان","سامان","سام",
            "سپهر","سیروس","شاهین","شایان","شایگان","شهرام","فرامرز","فردین","ایوب","ابی","اسی","کامبیز","کامران","کامی","آرش","افشین","آریا","آبتین","اشکان","ارشام","اردشیر","اردوان","بابک","بردیا","بهداد","جمشید","جهانگیر","خشایار","خسرو","داریوش",
            "رامین","روزبه","سروش","سهراب","سینا","شروین","فرهود","فرزین","فرهاد","کیان","مازیار","مهران","نیما","پیمان","پویا","فرخ","فرزاد",
            "فرشاد","کوشا","محمد","امیر","محمدعلی","امیرمحمد","امیرعلی","علی","حسن","حسین","سجاد","باقر","کاظم","صادق","رضا","هادی",
            "مهدی","کیانوش","کیوان","مهرداد","نوید","کسری","احسان","میثم","هومن","حمید","حامد","فرشید","جواد","شهاب","شاهرخ","شادمهر",
            "سیاوش","آرمین","آرمان","امید","بهروز","بهزاد","بهنام","بهمن","بیژن",

        ];
        $d=[

        ];

        for($i=0;$i<count($user_name_hooman_girls);$i++){
//            for($j=67;$j<=80;$j++){
                $tempUser=new User();
                $tempUser->user_name=$user_name_hooman_girls[$i];
                $tempUser->visitor_user_name=null;
                $tempUser->password='zxcvbnm';
                $tempUser->gender='female';
                $tempUser->luck=rand(1,123);
                $faceAndNose=rand(1,7);
                //old string image
//                $tempUser->image='f#'.$faceAndNose.'#'.$faceAndNose.'#'.rand(1,9).'#'.rand(1,9).'#'.rand(1,9).'#'.rand(0,13).'#'.rand(0,13).'#'.rand(0,3).'#0#0#'.rand(0,10).'#'.rand(0,12);

                $tempUser->image='f#'.$faceAndNose.'#'.$faceAndNose.'#'.rand(1,9).'#'.rand(1,9).'#'.rand(1,9).'#'.rand(0,13).'#'.rand(0,13).'#'.rand(0,12).'#0#'.rand(0,10).'#'.rand(0,3).'#0';
                //f,7,7,9,9,9,13,13,12,0,10,3,0
                //m,9,9,8,8,9,13,9,2,11,2,4,0
                $tempUser->store();
//            }
        }
//
        for($i=0;$i<count($user_name_hooman_mens);$i++){
//            for($j=67;$j<=80;$j++){
                $tempUser=new User();
                $tempUser->user_name=$user_name_hooman_mens[$i];
                $tempUser->visitor_user_name=null;
                $tempUser->password='zxcvbnm';
                $tempUser->gender='male';
                $faceAndNose=rand(1,9);
                $tempUser->luck=rand(1,123);
                //old string image
//                $tempUser->image='m#'.$faceAndNose.'#'.$faceAndNose.'#'.rand(1,8).'#'.rand(1,8).'#'.rand(1,9).'#'.rand(0,13).'#'.rand(0,9).'#'.rand(0,4).'#'.rand(0,11).'#0#'.rand(0,2).'#'.rand(0,2);

                $tempUser->image='m#'.$faceAndNose.'#'.$faceAndNose.'#'.rand(1,8).'#'.rand(1,8).'#'.rand(1,9).'#'.rand(0,13).'#'.rand(0,9).'#'.rand(0,2).'#'.rand(0,11).'#'.rand(0,2).'#'.rand(0,4).'#0';

                $tempUser->store();
//            }
        }






    }

    public function SetVisitorUserName(){
        for($i=0;$i<400;$i++){
            $tempUser=new User();
            $tempUser->user_name=null;
            $tempUser->visitor_user_name="Visitor_".rand(10000,99999).rand(10000,99999).rand(10000,99999)."#Samsung GT-I9192";
            $tempUser->password='zxcvbnm';
            $tempUser->gender='male';
            $tempUser->luck=rand(1,123);
            $tempUser->image="m#1#1#1#1#1#0#0#0#0#0#0#0";
            $tempUser->phone_code="09171234567";

            $tempUser->store();
        }
    }

    public function getImage($filename)
    {
//        $filename='5957a03bea5449.79935983';
        try {
            $file = File::get(storage_path('app/question/'.$filename.'.jpeg'));
            return $file;

        } catch (Exception $e) {

        }
    }

    public  function getAPK()
    {
//        $filename='5957a03bea5449.79935983';
        try {
//            return Redirect::to(url('dist/luckylord.apk')) ;
//            return response()->file(url('style/imgs/2.jpg'));
            $path = storage_path('app/luckylord.apk');

            return response()->file($path ,[
                'Content-Type'=>'application/vnd.android.package-archive',
                'Content-Disposition'=> 'attachment; filename="android.apk"',
            ]) ;
//            return response()->download($path);
            return response()->download(url('style/imgs/2.jpg'));

        } catch (Exception $e) {

        }
    }

    public function renameImage(){
        $query_for_get_Serach_result="SELECT  question_guid,question_id FROM question WHERE SUBSTRING(description , 1 ,1)='#' ";
        $result = DB::select($query_for_get_Serach_result);
        for($index=0;$index<count($result);$index++){
            rename(storage_path().'\\app\\question\\'.$result[$index]->question_guid.'.jpeg', storage_path().'\\app\\question\\'.$result[$index]->question_id.'.jpeg');
        }
//        rename(storage_path().'\\app\\avatars\\male.png', storage_path().'\\app\\avatars\\male.png');
    }

    public function body_part_cost(){
//        sexAge#face#nose#lip#eye#eyebrow#hair#dress#neckless#beard#earing#glasses#hat
//    public static int[] max_m_bodyPart={20,22,9,18,24,49,25,4,31,2,14,0};10
//    public static int[] max_f_bodyPart={16,18,13,20,18,55,30,15,5,10,22,0};7
        $body_part_cost=[
            ['16','20','face'],
            ['18','22','nose'],
            ['13','9','lip'],
            ['20','18','eye'],
            ['18','24','eyebrow'],
            ['55','49','hair'],
            ['30','25','dress'],
            ['15','4','neckless'],
            ['5','31','beard'],
            ['10','2','earing'],
            ['22','14','glasses'],
            ['7','10','hat'],
        ];


        for($index=0;$index<count($body_part_cost);$index++){
            for($mensIndex=1;$mensIndex<=$body_part_cost[$index][1];$mensIndex++){
                $r=new Request();
                $r['title']='m_'.$body_part_cost[$index][2].'_'.$mensIndex;
                $newBodyPart=new BodyPart();
                $newBodyPart->initializeByRequest($r);
                $newBodyPart->store();
            }
            for($FemaleIndex=1;$FemaleIndex<=$body_part_cost[$index][0];$FemaleIndex++){
                $r=new Request();
                $r['title']='f_'.$body_part_cost[$index][2].'_'.$FemaleIndex;
                $newBodyPart=new BodyPart();
                $newBodyPart->initializeByRequest($r);
                $newBodyPart->store();
            }
        }

        return 'OK';
    }

    public function add_table_question(){
//        sexAge#face#nose#lip#eye#eyebrow#hair#dress#neckless#beard#earing#glasses#hat
//    public static int[] max_m_bodyPart={20,22,9,18,24,49,25,4,31,2,14,0};10
//    public static int[] max_f_bodyPart={16,18,13,20,18,55,30,15,5,10,22,0};7
        $table_questions=[


        ];

        $req=new Request();
        $req['category']=14;
        $req['penalty']=250;
        $req['min_luck_reward']=1;
        $req['max_luck_reward']=1;
        $req['min_hazel_reward']=7;
        $req['max_hazel_reward']=14;
        for($index=0;$index<count($table_questions);$index++){
            $req['description']=$table_questions[$index][0];
            $req['answer']=$table_questions[$index][1];


            $id = DB::table('question')->insertGetId(
                ['question_guid' => uniqid('',true),
                    'category_id' => 14,
                    'description' => $table_questions[$index][0],
                    'answer' => $table_questions[$index][1],
                    'penalty' => 250,
                    'min_luck_reward' => 1,
                    'max_luck_reward' => 1,
                    'min_hazel_reward' => 7,
                    'max_hazel_reward' => 14,
                    'created_at' => Carbon::now(),
                    'updated_at' => Carbon::now(),
                ]
            );
        }

        return 'OK';
    }

    public function merge_txt_file(){
        try{
            $files = glob("C:\Users\Hooman\Downloads\*.*");
            //Then open an output file handle

            $out = fopen("D://ewfile.txt", "w");
            //Then cycle through the files reading and writing.
            fwrite($out, "\n\n ////***************************************************************301-400******************************************************\n\n");

            foreach($files as $file){

                $in = fopen($file, "r");
                while ($line = fgets($in)){
                    print $file;
                    fwrite($out, $line);
                }
                fclose($in);
            }
            //Then clean up
            fclose($out);
        }catch (\Exception $e){

            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => "ذخیره فایل"]);
        }

    }

    public function delete_question_puzzle_repeated(){
        ini_set('max_execution_time', 9200);
        $Questions = DB::table('question_puzzle')
            ->select('question_puzzle_id', 'description','answer')
            ->get();
        $repeated_questions_forRow=[];
        $repeated_questions_total=[];
        $repeated_questions_count=0;
        $questions_text="";
//        return json_encode($Questions);
        for($index=0;$index<count($Questions);$index++){
            if($Questions[$index]->question_puzzle_id=="")continue;
            for($index2=$index+1;$index2<count($Questions);$index2++){
                if(trim($Questions[$index]->description)==trim($Questions[$index2]->description) && trim($Questions[$index]->answer) ==trim($Questions[$index2]->answer)){
//                    array_push($repeated_questions_forRow,$Questions[$index2]->question_id);
//                    $questions_text+=$Questions[$index2]->question_id.",";
                    DB::table('question_puzzle')
                        ->where('question_puzzle_id', '=',$Questions[$index2]->question_puzzle_id)
                        ->delete();
                    $Questions[$index2]->question_puzzle_id ="";
                    $repeated_questions_count++;
                }
            }
//            if($questions_text!=""){
////                DB::table('question')
////                    ->whereIn('question_id', explode(",",$questions_text))
////                    ->delete();
//
////                $questions_text="";
////                array_push($repeated_questions_forRow,$Questions[$index]->question_id);
////                array_push($repeated_questions_total,$repeated_questions_forRow);
//            }

//            $repeated_questions_forRow = array();
//            if($repeated_questions_count>1000)break;
        }



//        return json_encode($repeated_questions_total);
        return json_encode($repeated_questions_count);
    }

    public function get_english_image(){
        ini_set('max_execution_time', 9200);
        $path="";




            for($index=13000;$index<13000;$index++){
                $path = "http://en.kanoon.ir/Handler/WoWImage.ashx?id=".$index;
                try{
                    Image::make($path)->save(public_path('/'.$index.'.jpg'));
                }catch (\Exception $e){
                    continue;
                }
            }

    }

    public function add_question_images(){
        ini_set('max_execution_time', 9200);
        $table_questions=[

        ];


        for($index=0;$index<count($table_questions);$index++){
            try{
                $id = DB::table('question_temp')->insertGetId(
                    ['question_temp_guid' => uniqid('',true),
                        'category_id' => 9,
                        'description' => "#نام این بازیکن؟",
                        'user_temp_id'=>1412,
                        'is_approved'=>0,
                        'answer' => $table_questions[$index][1],
                        'penalty' => 250,
                        'min_luck_reward' => 1,
                        'max_luck_reward' => 1,
                        'min_hazel_reward' => 7,
                        'max_hazel_reward' => 14,
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                );

                $path = $table_questions[$index][0];
                Image::make($path)->save(public_path('/'.$id.'.jpg'));
            }catch (\Exception $e){
                Question_Temp::destroy($id);
            }




//            $img = Image::make($table_questions[$index][0])->resize(320, 240)->insert('question_temp/1.png');

//            $imgFile='data:image/jpg;base64,'.base64_encode($table_questions[$index][0]);
//            $imgFile->storeAs('question_temp',$id.'.'.$imgFile->guessClientExtension());


//            $filename = basename($path);

//            Image::make($path)->save('question_temp',$id.'.'.'jpeg');
//            Storage::disk('local')->put('question_temp'.'/'.$id.'.jpg',  Image::make($path), 'public');

//            $path = $table_questions[$index][0];
//            $filename = basename($path);
//            $image = Image::make($path);
//            $store = $image->storeAs('question_temp/'.$filename,'public');


        }

        return 'OK';
    }

    public function edit_temp_question(){
        try
        {
            session(['description' =>9]);



            $question=DB::table('question_temp')->first();
            if($question->penalty==200)
                session(['easyHard' => 1]);
            elseif($question->penalty==250)
                session(['easyHard' => 2]);
            elseif($question->penalty==300)
                session(['easyHard' => 3]);
            session(['category' =>$question->category_id]);
            if (strpos($question->answer, '#') !== false) {
                session(['tabmenu' =>4]);
            }else{
                session(['tabmenu' =>3]);
            }
            $question->answer=trim( $question->answer);
            return view('question.edit', ['question' =>$question ]);
        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function updateTestQuestion(Request $request){
        self::storeTestQuestion($request);

    }

    public function edit_question($question_id){
        try
        {
            session(['description' =>9]);
            session(['tabmenu' =>1]);
            session(['easyHard' => 2]);
            session(['category' =>9]);
            $question=DB::table('question')->where('question_id',$question_id)->first();
            $question->answer=trim( $question->answer);

            return view('main_question.edit', ['question' =>$question ]);
        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }
    }

    public function getAvatar($question_id,$path)
    {
        try
        {
            $flag = false;
            $destinationPath = storage_path() . '/app/'.$path;
            $files1 = scandir($destinationPath);
            $nameOfFile = "";
            $search = $question_id;
            $search_length = strlen($search);
            foreach ($files1 as $key => $value) {
                if (substr($value, 0, $search_length) == $search) {
                    $nameOfFile = $value;
                    $flag = true;
                    break;
                }
            }


            $file = File::get(storage_path('app/'.$path.'/' . $nameOfFile));

            return $file;

        }
        catch (\Exception $ex)
        {
            DB::rollback();
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_NEXT_QUESTION]);

        }


    }

    public function print_file_for_question_image_english(){
        try{
            ini_set('max_execution_time', 9200);
            $english_questions=[];



             $keyvaluesQuestions = array();
             for($index=0;$index<count($english_questions);$index++){
                 if (!array_key_exists($english_questions[$index][0], $keyvaluesQuestions)) {
                     $keyvaluesQuestions[$english_questions[$index][0]] = $english_questions[$index][1];
                 }
             }
            ksort($keyvaluesQuestions);

            $out = fopen("D://ewfile.txt", "w");
            foreach ($keyvaluesQuestions as $key => $val) {
                //persian
                if (!preg_match('/^[^\x{600}-\x{6FF}]+$/u', $val)) // '/[^a-z\d]/i' should also work.
                {
                    // string contains only english letters & digits
                    fwrite($out, "['$key' , '$val'],\n");
                }
            }
            foreach ($keyvaluesQuestions as $key => $val) {
                if (preg_match('/^[^\x{600}-\x{6FF}]+$/u', $val)) // '/[^a-z\d]/i' should also work.
                {
                    // string contains only english letters & digits
                    fwrite($out, "['$key' , '$val'],\n");
                }

            }
            fclose($out);

            return "ok";


//
//            $files = glob("C:\Users\Hooman\Downloads\*.*");
//
//            fwrite($out, "\n\n ////***************************************************************301-400******************************************************\n\n");

//            foreach($files as $file){
//                $in = fopen($file, "r");
//                while ($line = fgets($in)){
//                    print $file;
//                    fwrite($out, $line);
//                }
//                fclose($in);
//            }
//            fclose($out);
        }catch (\Exception $e){

            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => "ذخیره فایل"]);
        }

    }

    public function save_image_english_question_in_question(){
        try{
            ini_set('max_execution_time', 9200);
            $english_questions=[];
            $answer="";
            for($index=0;$index<count($english_questions);$index++){
                if($index<2){
                    if($index==0){
                        $answer="*".$english_questions[0][1]."#".$english_questions[1][1]."#".$english_questions[2][1]."#".$english_questions[3][1]."#".$english_questions[4][1];
                    }
                    if($index==1){
                        $answer=$english_questions[0][1]."#*".$english_questions[1][1]."#".$english_questions[2][1]."#".$english_questions[3][1]."#".$english_questions[4][1];
                    }
                }
                else if($index>count($english_questions)-3){
                    if($index==count($english_questions)-1){
                        $answer="*".$english_questions[count($english_questions)-1][1]."#".$english_questions[count($english_questions)-2][1]."#".$english_questions[count($english_questions)-3][1]."#".$english_questions[count($english_questions)-4][1]."#".$english_questions[count($english_questions)-5][1];
                    }
                    if($index==count($english_questions)-2){
                        $answer=$english_questions[count($english_questions)-1][1]."#*".$english_questions[count($english_questions)-2][1]."#".$english_questions[count($english_questions)-3][1]."#".$english_questions[count($english_questions)-4][1]."#".$english_questions[count($english_questions)-5][1];
                    }
                }
                else{
                    $answer=$english_questions[$index-2][1]."#".$english_questions[$index-1][1]."#*".$english_questions[$index][1]."#".$english_questions[$index+1][1]."#".$english_questions[$index+2][1];

                }

                $id = DB::table('question')->insertGetId(
                    ['question_guid' => uniqid('',true),
                        'category_id' => 3,
                        'description' => "#سوال تصویری؟",
//                        'user_temp_id'=>1412,
//                        'is_approved'=>0,
                        'answer' => $answer,
                        'penalty' => 250,
                        'min_luck_reward' => 1,
                        'max_luck_reward' => 1,
                        'min_hazel_reward' => 7,
                        'max_hazel_reward' => 14,
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                );

//                Storage::copy('english_image/'.$english_questions[$index][0].'.jpg','RecycleBin/'.$english_questions[$index][0].'.jpg');
                Storage::copy('english_image/'.$english_questions[$index][0].'.jpg','question/'.$id.'.jpg');
            }

            $english_questions_text=[];


            $answer="";
            for($index=0;$index<count($english_questions_text);$index++){
                if($index<2){
                    if($index==0){
                        $answer="*".$english_questions_text[0][1]."#".$english_questions_text[1][1]."#".$english_questions_text[2][1]."#".$english_questions_text[3][1]."#".$english_questions_text[4][1];
                    }
                    if($index==1){
                        $answer=$english_questions_text[0][1]."#*".$english_questions_text[1][1]."#".$english_questions_text[2][1]."#".$english_questions_text[3][1]."#".$english_questions_text[4][1];
                    }
                }
                else if($index>count($english_questions_text)-3){
                    if($index==count($english_questions_text)-1){
                        $answer="*".$english_questions_text[count($english_questions_text)-1][1]."#".$english_questions_text[count($english_questions_text)-2][1]."#".$english_questions_text[count($english_questions_text)-3][1]."#".$english_questions_text[count($english_questions_text)-4][1]."#".$english_questions_text[count($english_questions_text)-5][1];
                    }
                    if($index==count($english_questions_text)-2){
                        $answer=$english_questions_text[count($english_questions_text)-1][1]."#*".$english_questions_text[count($english_questions_text)-2][1]."#".$english_questions_text[count($english_questions_text)-3][1]."#".$english_questions_text[count($english_questions_text)-4][1]."#".$english_questions_text[count($english_questions_text)-5][1];
                    }
                }
                else{
                    $answer=$english_questions_text[$index-2][1]."#".$english_questions_text[$index-1][1]."#*".$english_questions_text[$index][1]."#".$english_questions_text[$index+1][1]."#".$english_questions_text[$index+2][1];

                }

                $id = DB::table('question')->insertGetId(
                    ['question_guid' => uniqid('',true),
                        'category_id' => 3,
                        'description' => "#معنی کلمه زیر چیست؟",
//                        'user_temp_id'=>1412,
//                        'is_approved'=>0,
                        'answer' => $answer,
                        'penalty' => 250,
                        'min_luck_reward' => 1,
                        'max_luck_reward' => 1,
                        'min_hazel_reward' => 7,
                        'max_hazel_reward' => 14,
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                );

//                Storage::copy('english_image/'.$english_questions_text[$index][0].'.jpg','RecycleBin/'.$english_questions_text[$index][0].'.jpg');
                Storage::copy('english_image/'.$english_questions_text[$index][0].'.jpg','question/'.$id.'.jpg');
            }

            return "ok";



        }catch (\Exception $e){

            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => "ذخیره فایل"]);
        }
    }

    public function save_medical_question(){
        $medical_Q=[];
        $medical_Q_count=count($medical_Q);
        for($index=0;$index<count($medical_Q);$index++){
            $answer=$medical_Q[$index][0];
            $answer1=$medical_Q[$index][1];
            $answer2="";
            $answer3="";
            while(strcmp($answer,$medical_Q[$index][0])==0){
                $id=rand(0,$medical_Q_count-1);
                $answer2=$medical_Q[$id][1];
                $answer=$medical_Q[$id][0];
            }
            $answer=$medical_Q[$index][0];
            while(strcmp($answer,$medical_Q[$index][0])==0 || strcmp($answer,$answer2)==0){
                $id=rand(0,$medical_Q_count-1);
                $answer3=$medical_Q[$id][1];
                $answer=$medical_Q[$id][0];
            }





                $answer="*".$answer1."#".$answer2."#".$answer3;


            $id = DB::table('question')->insertGetId(
                ['question_guid' => uniqid('',true),
                    'category_id' => 8,
                    'description' => 'کدام گزینه جزء دارو های "'.$medical_Q[$index][0].'" می باشد ؟',
//                        'user_temp_id'=>1412,
//                        'is_approved'=>0,
                    'answer' => $answer,
                    'penalty' => 300,
                    'min_luck_reward' => 1,
                    'max_luck_reward' => 1,
                    'min_hazel_reward' => 14,
                    'max_hazel_reward' => 20,
                    'created_at' => Carbon::now(),
                    'updated_at' => Carbon::now(),
                ]
            );

//                Storage::copy('english_image/'.$english_questions[$index][0].'.jpg','RecycleBin/'.$english_questions[$index][0].'.jpg');
//            Storage::copy('english_image/'.$english_questions[$index][0].'.jpg','question/'.$id.'.jpg');
        }
        return $medical_Q[0][1];
    }

    public function save_math_and_inteligence_question(){
        try{
            ini_set('max_execution_time', 9200);
            $english_questions=[];
            $answer="";
            $files = File::allFiles(storage_path('app/alaki_test'));
            foreach ($files as $file)
            {
                $image_names=basename((string)$file);
                $image_name_in_file=explode(".",$image_names);
                $image_name=array();
                $image_name=explode("_",$image_names);
                $answer=str_replace("%","*",$image_name[1]);
                $difficulty=explode(".",$image_name[2]);

                $penalty="";
                $min_hazel_reward="";
                $max_hazel_reward="";
                if($difficulty[0]=='h'){
                    $penalty=300;
                    $min_hazel_reward=14;
                    $max_hazel_reward=21;
                }else if($difficulty[0]=='e'){
                    $penalty=200;
                    $min_hazel_reward=5;
                    $max_hazel_reward=10;
                }else if($difficulty[0]=='m'){
                    $penalty=250;
                    $min_hazel_reward=7;
                    $max_hazel_reward=14;
                }


                $id = DB::table('question')->insertGetId(
                    ['question_guid' => uniqid('',true),
                        'category_id' => 10,
                        'description' => "#به جای علامت سوال چی باشه؟",
//                        'user_temp_id'=>1412,
//                        'is_approved'=>0,
                        'answer' => $answer,
                        'penalty' => $penalty,
                        'min_luck_reward' => 1,
                        'max_luck_reward' => 1,
                        'min_hazel_reward' => $min_hazel_reward,
                        'max_hazel_reward' => $max_hazel_reward,
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                );

//                Storage::copy('english_image/'.$english_questions[$index][0].'.jpg','RecycleBin/'.$english_questions[$index][0].'.jpg');
                Storage::copy('alaki_test/'.$image_name_in_file[0].'.jpg','question/'.$id.'.jpg');
            }




            return "ok";



        }catch (\Exception $e){

            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => "ذخیره فایل"]);
        }
    }

    public function save_poem_Qu(){
        try{
            ini_set('max_execution_time', 9200);
            $english_questions=[];
            $answer="";

            $english_questions_text=[];

            $famous_poet=['سعدی','حافظ','مولوی'];
            $Pub_poet=['ابوسعید ابوالخیر','اقبال لاهوری','خاقانی','خیام','رودکی',
                'شاه نعمت‌الله ولی','شهریار','شیخ بهایی','شیخ محمود شبستری','عبید زاکانی',
                'عطار','فردوسی','فیض کاشانی','محتشم کاشانی','نظامی','هاتف اصفهانی'
                ];


            $query_for_getQuestion="SELECT poem.poet_name,verse.first_hemistich,verse.second_hemistich FROM verse INNER JOIN poem ON verse.poem_id=poem.poem_id where comment_count>=48 and poem.deleted_at is NULL and verse.deleted_at is NULL";
            $questions = DB::select($query_for_getQuestion);

            for($index=0;$index<count($questions);$index++){
                $answer="";
                $answer1=$questions[$index]->poet_name;
                $answer2='';
                $answer3='';
                $answer4='';
                $answer5='';

                $answer2 = $famous_poet[0];
                if($answer2 == $answer1)
                    do{
                        $answer2=$Pub_poet[rand(0,count($Pub_poet)-1)];
                    }while($answer2==$answer1);

                $answer3=$famous_poet[1];
                if($answer3 == $answer2 || $answer3==$answer1)
                    do{
                        $answer3=$Pub_poet[rand(0,count($Pub_poet)-1)];
                    }while($answer3 ==$answer2 || $answer3==$answer1);

                $answer4=$famous_poet[2];
                if($answer4 == $answer2 || $answer4==$answer1 || $answer4==$answer3)
                    do{
                        $answer4=$Pub_poet[rand(0,count($Pub_poet)-1)];
                    }while($answer4 ==$answer2 || $answer4==$answer1 || $answer4==$answer3);

                do{
                    $answer5=$Pub_poet[rand(0,count($Pub_poet)-1)];
                }while($answer5 ==$answer2 || $answer5==$answer1 || $answer5==$answer3 || $answer5==$answer4);

                $answer=$answer1.'*#'.$answer2.'#'.$answer3.'#'.$answer4.'#'.$answer5;

                $id = DB::table('question')->insertGetId(
                    ['question_guid' => uniqid('',true),
                        'category_id' => 2,
                        'description' => 'شاعر این بیت کیست؟ "'.$questions[$index]->first_hemistich.'/'.$questions[$index]->second_hemistich.'"',
//                        'user_temp_id'=>1412,
//                        'is_approved'=>0,
                        'answer' => $answer,
                        'penalty' => 250,
                        'min_luck_reward' => 1,
                        'max_luck_reward' => 1,
                        'min_hazel_reward' => 7,
                        'max_hazel_reward' => 14,
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                );

             }


            return "ok   ".count($questions);



        }catch (\Exception $e){

            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => "ذخیره فایل"]);
        }
    }

    public function save_words(){
        ini_set('max_execution_time', 9200);
        $handle = fopen("D:\words.txt", "r");
        if ($handle) {
            while (($line = fgets($handle)) !== false) {
//                if(trim($line)=='')
//                else{
//                    $id = DB::table('words')->insertGetId(
//                        ['word_guid' => uniqid('',true),
//                            'word' => $line,
//                            'created_at' => Carbon::now(),
//                            'updated_at' => Carbon::now(),
//                        ]
//                    );
//                }

            }

            fclose($handle);
        } else {
            // error opening the file.
        }
        return "OK";
    }

    public function detect(){
        if (Browser::isFirefox() || Browser::isOpera() || Browser::isChrome() || Browser::isIE() || Browser::isSafari()) {
           return "ok";
        }
    }

    public function getRandomQu(){

        $questions=DB::table('question_puzzle')
            ->select('description', 'answer')
            ->orderBy(DB::raw('RAND()'))
            ->take(5)->get();
//        $q=$questions;
//        for($index=0;$index<count($questions);$index++){
//            $questions[$index]->answer=str_replace(" ","",$questions[$index]->answer);
//        }
        $table=[
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
            ['','','','','','',''],
        ];



            do{
                $ii=0;
                $jj=0;
                $answer_cells=[];
            for($index=0;$index<count($questions);$index++){
                $temp_answer_cells=[];
                    $countOfchoose=0;
                    $breakFlag=false;

                do {
                    $ii=rand(0,9);
                    $jj=rand(0,6);
                } while ($table[$ii][$jj]!='');
                $answer=str_replace(" ","",$questions[$index]->answer);

                array_push($temp_answer_cells,$ii*7+$jj);
                $table[$ii][$jj]=mb_substr($answer,0,1);

                $currentI=$ii;
                $currentJ=$jj;
                for($index2=1;$index2<mb_strlen($answer, 'UTF-8');$index2++){
                    $countOfchoose=0;
                    if($currentI-1 >=0 && $table[$currentI-1][$currentJ]=='')$countOfchoose++;
                    if($currentI+1 <=9 && $table[$currentI+1][$currentJ]=='')$countOfchoose++;
                    if($currentJ-1 >=0 && $table[$currentI][$currentJ-1]=='')$countOfchoose++;
                    if($currentJ+1 <=6 && $table[$currentI][$currentJ+1]=='')$countOfchoose++;

                    if($countOfchoose>0){
                        $ranDirection=rand(1,$countOfchoose);

                        $directionIndex=0;
                        if($currentI-1 >=0 && $table[$currentI-1][$currentJ]==''){
                            $directionIndex++;
                            if($directionIndex==$ranDirection){
                                $currentI--;
                                array_push($temp_answer_cells,$currentI*7+$currentJ);
                                $table[$currentI][$currentJ]=mb_substr($answer,$index2,1);
                            }
                        }
                        if($currentI+1 <=9 && $table[$currentI+1][$currentJ]==''){
                            $directionIndex++;
                            if($directionIndex==$ranDirection){
                                $currentI++;
                                array_push($temp_answer_cells,$currentI*7+$currentJ);
                                $table[$currentI][$currentJ]=mb_substr($answer,$index2,1);
                            }
                        }
                        if($currentJ-1 >=0 && $table[$currentI][$currentJ-1]==''){
                            $directionIndex++;
                            if($directionIndex==$ranDirection){
                                $currentJ--;
                                array_push($temp_answer_cells,$currentI*7+$currentJ);
                                $table[$currentI][$currentJ]=mb_substr($answer,$index2,1);
                            }
                        }
                        if($currentJ+1 <=6 && $table[$currentI][$currentJ+1]==''){
                            $directionIndex++;
                            if($directionIndex==$ranDirection){
                                $currentJ++;
                                array_push($temp_answer_cells,$currentI*7+$currentJ);
                                $table[$currentI][$currentJ]=mb_substr($answer,$index2,1);
                            }
                        }
                    }
                    else{

                        $table=[
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                            ['','','','','','',''],
                        ];
                        $breakFlag=true;
                        $countOfchoose=0;
                        break;}



                }
                if($breakFlag)break;
                $answer_cells[$index]=$temp_answer_cells;
            }
//                array_push($answer_cells,$temp_answer_cells);
//                $answer_cells[$index]=$temp_answer_cells;
//                array_merge($answer_cells, $temp_answer_cells);
            }while($countOfchoose<=0);


            $indexCount=0;
            $cells=[];
            for ($index=0;$index<10;$index++)
                for ($index2=0;$index2<7;$index2++){
                    if($table[$index][$index2]==''){
                        $table[$index][$index2]=Question::persianAlphabet[rand(0,count(Question::persianAlphabet)-1)];
                    }
                    array_push($cells,$table[$index][$index2]);
                }



        return view('test/qu',['questions' => $questions, 'table' => $table,'cells'=>$cells,'answer_cells'=>$answer_cells]);
    }

    public function moteghateTable()
    {
        ini_set('max_execution_time', 100000);


        $Questions1=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=3 and CHAR_LENGTH(description)<12 GROUP BY(answer)");
        $Questions2=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=6 and CHAR_LENGTH(description)<12 GROUP BY(answer)");
        $Questions3=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=3 and CHAR_LENGTH(description)<12 GROUP BY(answer)");
        $Questions4=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=7 and CHAR_LENGTH(description)<12 GROUP BY(answer)");
        $temp=$Questions1[45]->answer;
        $charr=mb_substr($temp,1,1);
        $g=0;
        for($index1=69;$index1<count($Questions1);$index1++){
                $count=0;

                for($index2=rand(0,count($Questions2)-11);$index2<count($Questions2);$index2++){

                        for($index4=rand(0,count($Questions4)-11);$index4<count($Questions4);$index4++){

                                $answer5=mb_substr($Questions4[$index4]->answer,0,1).mb_substr($Questions1[$index1]->answer,0,1);
                                $answer6=mb_substr($Questions4[$index4]->answer,1,1).mb_substr($Questions1[$index1]->answer,1,1).mb_substr($Questions2[$index2]->answer,0,1);
                                $answer7=mb_substr($Questions4[$index4]->answer,2,1).mb_substr($Questions1[$index1]->answer,2,1).mb_substr($Questions2[$index2]->answer,1,1);
                                $answer8=mb_substr($Questions4[$index4]->answer,3,1);

                                $Questions_res_5=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=2 and CHAR_LENGTH(description)<12 and answer LIKE '".$answer5."' GROUP BY(answer)");
                                $Questions_res_6=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=4 and CHAR_LENGTH(description)<12 and answer LIKE '%".$answer6."' GROUP BY(answer)");
                                $Questions_res_7=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=3 and CHAR_LENGTH(description)<12 and answer LIKE '".$answer7."' GROUP BY(answer)");
                                $Questions_res_8=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=2 and CHAR_LENGTH(description)<12 and answer LIKE '%".$answer8."' GROUP BY(answer)");


                                if(count($Questions_res_5)>0 && count($Questions_res_6)>0 && count($Questions_res_7)>0 && count($Questions_res_8)>0  ){
                                    for($index3=rand(0,count($Questions3)-1);$index3<count($Questions3);$index3++){
                                        $answer9=mb_substr($Questions4[$index4]->answer,4,1).mb_substr($Questions3[$index3]->answer,0,1).mb_substr($Questions2[$index2]->answer,3,1);
                                        $answer10=mb_substr($Questions4[$index4]->answer,5,1).mb_substr($Questions3[$index3]->answer,1,1).mb_substr($Questions2[$index2]->answer,4,1);
                                        $answer11=mb_substr($Questions4[$index4]->answer,6,1).mb_substr($Questions3[$index3]->answer,2,1).mb_substr($Questions2[$index2]->answer,5,1);

                                        $Questions_res_9=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=3 and CHAR_LENGTH(description)<12 and answer LIKE '".$answer9."' GROUP BY(answer)");
                                        $Questions_res_10=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=4 and CHAR_LENGTH(description)<12 and answer LIKE '%".$answer10."' GROUP BY(answer)");
                                        $Questions_res_11=DB::select("select answer from question_puzzle where CHAR_LENGTH(answer)=3 and CHAR_LENGTH(description)<12 and answer LIKE '".$answer11."' GROUP BY(answer)");
                                        if( count($Questions_res_9)>0 && count($Questions_res_10)>0 && count($Questions_res_11)>0){
                                            $answers=$Questions1[$index1]->answer.','.$Questions2[$index2]->answer.','.$Questions_res_5[0]->answer.','.$Questions4[$index4]->answer.','.$Questions_res_6[0]->answer.','.$Questions_res_7[0]->answer.','.$Questions3[$index3]->answer.','.$Questions_res_8[0]->answer.','.$Questions_res_9[0]->answer.','.$Questions_res_10[0]->answer.','.$Questions_res_11[0]->answer;

                                            $updatedAnswers='';
                                            $answers2= explode(",",$answers);
                                            for($index=0;$index<count($answers2);$index++){
                                                $desc=DB::select("select description from question_puzzle where CHAR_LENGTH(description)<12 and answer LIKE '".$answers2[$index]."'");
                                                $updatedAnswers.=$answers2[$index].'*'.$desc[0]->description.',';
                                            }

                                            $status_index=$index1.",".$index2.",".$index3.",".$index4;
                                            DB::table('moteghate_Table')->insert(
                                                ['questions' => $updatedAnswers, 'table_base_id' => 3,'status_index' => $status_index,]
                                            );
                                            $count++;
                                            if($count>5)
                                                break 3;
                                            else
                                                break 2;
                                        }
                                    }


                                }
//                                elseif(true){
//                                    if(count($Questions_res_5)==0 ){
//                                        $index2++;
//                                        if(count($Questions_res_5)==$index2)
//                                            break 3;
//                                    }
//                                    elseif(count($Questions_res_7)==0){
//                                        $index2++;
//                                        if(count($Questions_res_7)==$index2)
//                                            break 3;
//                                    }
//                                }




//                            return $Questions1[$index1]->answer.' '.$Questions4[$index4]->answer.' '.$Questions5[$index5]->answer.' '.$Questions6[$index6]->answer;



                        }



                }
        }

        return 'ok good job';
    }

    public function moteghateTableView()
    {

        ini_set('max_execution_time', 100000);
//        $table=[
//            [['firstQ'=>['question'=>'عدالت','answer'=>'داد','direction'=>'fa-arrow-down','directionVer'=>3,'directionHor'=>2,'answerIndex'=>[4,8,12]]],['firstQ'=>['question'=>'نشان دولتی','answer'=>'آرم','direction'=>'fa-arrow-down','directionVer'=>3,'directionHor'=>2,'answerIndex'=>[5,9,13]]],'م',['firstQ'=>['question'=>'امانت دهنده','answer'=>'مودع','direction'=>'fa-arrow-down','directionVer'=>1,'directionHor'=>1,'answerIndex'=>[2,6,10,14]],'secondQ'=>['question'=>'تبعید دارد','answer'=>'قواد','direction'=>'fa-arrow-left','directionVer'=>3,'directionHor'=>2,'answerIndex'=>[7,6,5,4]]]],
//            ['د','ا','و','ق'],
//            ['ا','ر','د',['firstQ'=>['question'=>'قاعده فقهی','answer'=>'درا','direction'=>'fa-arrow-left','directionVer'=>2,'directionHor'=>1,'answerIndex'=>[10,9,8]]]],
//            ['د','م','ع',['firstQ'=>['question'=>'عنصر قتل','answer'=>'عمد','direction'=>'fa-arrow-left','directionVer'=>2,'directionHor'=>1,'answerIndex'=>[14,13,12]]]],
//        ];

//        $tableTemp=DB::table('moteghate_table')->first();
        $tableTempRand=DB::table('moteghate_table')
//            ->where('table_base_id', rand(15,15))
            ->where('moteghate_Table_id','=' ,'27428')
            ->orderBy(DB::raw('RAND()'))->limit(1)->first();

        $tableTemp = DB::table('moteghate_table')
            ->join('table_base', 'table_base.table_base_id', '=', 'moteghate_table.table_base_id')
            ->join('cell_info_table', 'cell_info_table.table_base_id', '=', 'table_base.table_base_id')
            ->select('moteghate_table.*', 'cell_info_table.*', 'table_base.*')
//            ->where('table_base.table_base_id', '=', $tableTempRand->table_base_id)
//            ->where('moteghate_table.moteghate_Table_id', '=', $tableTempRand->moteghate_Table_id)
            ->where('moteghate_table.moteghate_Table_id', '=', 47172)
            ->get();


//        $answers=[];
        $updatedAnswers='';
        $questions= explode(",",$tableTemp[0]->questions);
        $table=[];
        $question_index=0;
        for($index1=0;$index1<count($tableTemp);$index1++){
                $desc_ans= explode("*",$questions[$question_index]);
//                if($tableTemp[$index1]->)
            $firstQ=[];
            $secondQ=[];
            $firstQ['question']=$desc_ans[1];
            $firstQ['answer']=$desc_ans[0];
            $dir=explode(",",$tableTemp[$index1]->first_direction);

            $firstQ['direction']='fa-arrow-'.$dir[0];
            $firstQ['directionVer']=$dir[1];
            $firstQ['directionHor']=$dir[2];
            $answerIndex=explode(",",$tableTemp[$index1]->first_answer_index_cell);
            for($index2=0;$index2<count($answerIndex);$index2++){
                $table[$answerIndex[$index2]]=mb_substr($desc_ans[0],$index2,1);
            }
            $firstQ['answerIndex']=$answerIndex;
            $cell=[];
            $cell['firstQ']=$firstQ;
            $question_index++;
            if($tableTemp[$index1]->question_count==2){
                $dir=explode(",",$tableTemp[$index1]->second_direction);
                $answerIndex=explode(",",$tableTemp[$index1]->second_answer_index_cell);
                $desc_ans= explode("*",$questions[$question_index]);
                $secondQ['question']=$desc_ans[1];
                $secondQ['answer']=$desc_ans[0];

                $secondQ['direction']='fa-arrow-'.$dir[0];
                $secondQ['directionVer']=$dir[1];
                $secondQ['directionHor']=$dir[2];

                for($index2=0;$index2<count($answerIndex);$index2++){
                    $table[$answerIndex[$index2]]=mb_substr($desc_ans[0],$index2,1);
                }
                $secondQ['answerIndex']=$answerIndex;
                $cell['secondQ']=$secondQ;
                $table[$tableTemp[$index1]->cell_index]=$cell;
                $question_index++;
            }
            if($tableTemp[$index1]->question_count==1)
                $table[$tableTemp[$index1]->cell_index]=$cell;


        }

//        return json_encode($table);
        return view('question/moteghateTable',['table' => $table,'width' => $tableTemp[0]->width_size,'height' => $tableTemp[0]->height_size]);
    }
}