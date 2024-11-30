<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 5/31/2017
 * Time: 7:38 PM
 */


namespace App\Http\Controllers;

use App\LogEvent;
use App\Question;
use App\UserCategory;
use App\UserQuestion;
use App\User;
use App\RequestResponseAPI;
use Illuminate\Http\Request;

use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Carbon\Carbon;

class API_UserCategoryController extends Controller
{
    protected $user_category;

    public function __construct(UserCategory $question)
    {
        $this->question = $question;
    }

    public function firstOrnewFor_uesr_category(){
        ini_set('max_execution_time', 9200);
        $users = DB::table('user')->select('user_id')->get();


        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',1)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=1;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }

        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',2)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=2;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',3)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=3;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',4)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=4;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',5)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=5;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',6)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=6;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',7)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=7;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',8)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=8;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',9)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=9;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',10)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=10;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',11)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=11;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }
        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',12)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=12;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }

        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',13)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
            if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=13;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }

        for($index=0;$index<count($users);$index++){
            $user_categories = DB::table('user_category')->select('user_category_id','user_id', 'category_id')->where('category_id',14)->where('user_id',$users[$index]->user_id)->get();
            if(count($user_categories)>1){
                for($index2=1;$index2<count($user_categories);$index2++)
                    DB::table('user_category')->where('user_category_id', '=', $user_categories[$index2]->user_category_id)->delete();
            }
             if(count($user_categories)==0){
                $r=new Request();
                $r['user_id']=$users[$index]->user_id;
                $r['category_id']=14;

                $r['total_answered_count']=0;
                $r['correct_answered_count']=0;
                $r['daily_total_answered_count']=0;
                $r['daily_correct_answered_count']=0;
                $r['weekly_total_answered_count']=0;
                $r['weekly_correct_answered_count']=0;
                $UserCategory=new UserCategory();
                $UserCategory->intializeByRequest($r);
                $UserCategory->store();
            }
        }


        return "OK";
    }

    public function set_random_percent_for_bot(){
        ini_set('max_execution_time', 9200);
        $bots = DB::table('user')->select('user_id')->where('password','=','zxcvbnm')->get();


        for($index=0;$index<count($bots);$index++){
            for($index2=1;$index2<=14;$index2++)
                UserCategory::where('category_id', $index2)
                    ->where('user_id', $bots[$index]->user_id)
                    ->update([
                        'total_answered_count' => 100,
                        'correct_answered_count' => rand(40,90),
                        'daily_total_answered_count' => 100,
                        'daily_correct_answered_count' => rand(40,90),
                        'weekly_total_answered_count' => 100,
                        'weekly_correct_answered_count' => rand(40,90),
                    ]);

        }

        return "lets go OK";
    }
}