<?php

namespace App\Http\Controllers;
use App\Verse;
use App\Poem;
use Carbon\Carbon;
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




class PoemController extends Controller
{
    protected $poem;



    public function __construct(Poem $poem)
    {
        $this->poem = $poem;
    }

    public function save_poems(){
        try{
            ini_set('max_execution_time', 9200);
            $poems=[];

            for($index=0;$index<count($poems);$index++){
                $id = DB::table('poem')->insertGetId(
                    [
                        'poem_guid' => uniqid('',true),
                        'title' => $poems[$index][1],
                        'comment_count' => $poems[$index][2],
                        'poet_name' => $poems[$index][0],
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                );
                for($index_of_verse=0;$index_of_verse<count($poems[$index][3]);$index_of_verse+=2){
                    DB::table('verse')->insertGetId(
                        [
                            'verse_guid' => uniqid('',true),
                            'poem_id' => $id,
                            'first_hemistich' => $poems[$index][3][$index_of_verse],
                            'second_hemistich' => $poems[$index][3][$index_of_verse+1],
                            'verse_number' => $index_of_verse/2+1,
                            'created_at' => Carbon::now(),
                            'updated_at' => Carbon::now(),
                        ]
                    );
                }
            }





            return "ok";



        }catch (\Exception $e){

            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => "ذخیره فایل"]);
        }
    }
}
