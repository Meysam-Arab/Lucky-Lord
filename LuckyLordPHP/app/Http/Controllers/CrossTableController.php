<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 12/11/2017
 * Time: 11:16 AM
 */

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
use App\LogEvent;
class CrossTableController extends Controller
{
    protected $question;

    public function __construct(Question_Puzzle $q)
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

    public function make_Unlimited_Cross_table(){
        ini_set('max_execution_time', 100000);

//        $Questions1=DB::select("Select `answer`,`description` from question_puzzle where CHAR_LENGTH(answer)=5 and `answer` like 'ا_س_ن' and CHAR_LENGTH(description)<14 GROUP BY(answer)");
    while (true){
        //*********start get rand cross_table
        $random_category_table=rand(7,16);
            $randTable = DB::table('table_base')
            ->join('cell_info_table', 'cell_info_table.table_base_id', '=', 'table_base.table_base_id')
            ->select('*')
            ->where('table_base.table_base_id',$random_category_table)
            ->get();
        //*********end get rand cross_table






        //********start ini array of table
        $arrayCrossTable[]='';
        $arrayIndexTable[]='';
        unset($arrayCrossTable); // $foo is gone
        $arrayCrossTable = array(); // $foo is here again
        unset($arrayIndexTable); // $foo is gone
        $arrayIndexTable = array(); // $foo is here again
        for($index=0;$index<$randTable[0]->width_size*$randTable[0]->height_size;$index++){
            $arrayCrossTable[$index]='_';
        }
        for($index=0;$index<count($randTable);$index++){
            $arrayCrossTable[$randTable[$index]->cell_index]='#';

            if($randTable[$index]->question_count==1){
                $array['index_cells']=$randTable[$index]->first_answer_index_cell;
                $array['index']=$randTable[$index]->cell_index;
                $array['is_filled']=-1;
                array_push($arrayIndexTable,$array);
            }elseif ($randTable[$index]->question_count==2){
                $array['index_cells']=$randTable[$index]->first_answer_index_cell;
                $array['index']=$randTable[$index]->cell_index;
                $array['is_filled']=-1;
                array_push($arrayIndexTable,$array);

                $array['index_cells']=$randTable[$index]->second_answer_index_cell;
                $array['index']=$randTable[$index]->cell_index;
                $array['is_filled']=-1;
                array_push($arrayIndexTable,$array);
            }
        }
        //********end ini array of table




        //********start ini variable
        $endMakeCrossTable=false;
        //********end ini variable

        $tatalCount=0;
        do{
            ///*********start calculate percent question
            $maxId=1;
            $max=1;
            if($tatalCount==0){
                $maxId=rand(0,count($arrayIndexTable)-1);
            }
            else{
                for($index=0;$index<count($arrayIndexTable);$index++){
                    $counter=0;
                    if($arrayIndexTable[$index]['is_filled']<=100){
                        $ids= explode(",",$arrayIndexTable[$index]['index_cells']);

                        for($j=0;$j<count($ids);$j++){

                            if($arrayCrossTable[$ids[$j]] !='#' and $arrayCrossTable[$ids[$j]] !='_' )
                                $counter++;
                        }
                        if(count($ids)>=6){
                            $arrayIndexTable[$index]['is_filled']=99;
                        }else{
                            $arrayIndexTable[$index]['is_filled']=$counter/count($ids)*100;
                        }

                        if($arrayIndexTable[$index]['is_filled']>$max){
                            $max=$arrayIndexTable[$index]['is_filled'];
                            $maxId=$index;
                        }
                    }

                }
                ///*********end calculate percent question
            }


            $idss= explode(",",$arrayIndexTable[$maxId]['index_cells']);
            $likeText='';
            for($index=0;$index<count($idss);$index++){
                $likeText.=$arrayCrossTable[$idss[$index]];
            }

            $Questions=DB::select("Select `answer` from question_puzzle where CHAR_LENGTH(answer)= :id and `answer` like :likeText and CHAR_LENGTH(description)<14 GROUP BY(answer)",['id' =>count($idss),'likeText'=>$likeText]);

            if(count($Questions)>0){
                $id=rand(0,count($Questions)-1);

                for($index=0;$index<count($idss);$index++){
                    $arrayCrossTable[$idss[$index]]=mb_substr($Questions[$id]->answer,$index,1);
                }
                $arrayIndexTable[$maxId]['is_filled']='110';
            }
            else{
                ////clear data
                for($i=0;$i<count($idss);$i++){
                    for($j=0;$j<count($arrayIndexTable);$j++){
                        $TempIds= explode(",",$arrayIndexTable[$j]['index_cells']);
                        if (in_array($idss[$i], $TempIds))
                        {
                            for($k=0;$k<count($TempIds);$k++){
                                $arrayCrossTable[$TempIds[$k]]='_';
                            }
                        }

                    }
                }
                for($j=0;$j<count($arrayIndexTable);$j++){
                    $arrayIndexTable[$j]['is_filled']=-1;
                }
            }


            $flag=true;
            for($index=0;$index<count($arrayCrossTable);$index++){
                if($arrayCrossTable[$index] =='_' )
                {
                    $flag=false;
                    break;
                }
            }
//            for($index=0;$index<count($arrayIndexTable);$index++){
//                if($arrayIndexTable[$index]["is_filled"] !=110 )
//                {
//                    $flag=false;
//                    break;
//                }
//            }

            if($flag)$endMakeCrossTable=true;
            $tatalCount++;
            if($tatalCount>5*$randTable[0]->width_size*$randTable[0]->height_size )
                break;
        }while(!$endMakeCrossTable);


        if($endMakeCrossTable){
            $updatedAnswers='';
            $Questions=DB::select("Select `answer` from question_puzzle where CHAR_LENGTH(answer)= :id and `answer` like :likeText and CHAR_LENGTH(description)<14 GROUP BY(answer)",['id' =>count($idss),'likeText'=>$likeText]);

            $correctFlag=true;
            for($index=0;$index<count($arrayIndexTable);$index++){
                $likeText='';
                $idss= explode(",",$arrayIndexTable[$index]['index_cells']);
                for($index2=0;$index2<count($idss);$index2++){
                    $likeText.=$arrayCrossTable[$idss[$index2]];
                }

                $desc=DB::select("Select `description` from question_puzzle where CHAR_LENGTH(answer)= :id and `answer` like :likeText and CHAR_LENGTH(description)<14 ",['id' =>count($idss),'likeText'=>$likeText]);
                if(count($desc)==0){
                    $correctFlag=false;
                    break;
                }
                $updatedAnswers.=$likeText.'*'.$desc[rand(0,count($desc)-1)]->description.',';
            }
            $status_index=$tatalCount;
            if($correctFlag){
                $checkrepeated=DB::select("SELECT * FROM `moteghate_table` WHERE `questions` LIKE :questions ",['questions' =>$updatedAnswers]);

                if(count($checkrepeated)==0){
                    DB::table('moteghate_Table')->insert(
                        ['questions' => $updatedAnswers, 'table_base_id' => $random_category_table,'status_index' => $status_index,]
                    );
                }
            }
        }

    }


        return view('question/moteghateTable',['table' => $arrayCrossTable,'width' => $randTable[0]->width_size,'height' => $randTable[0]->height_size]);

    }
}      