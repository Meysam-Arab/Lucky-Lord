<?php
namespace App\Http\Controllers;
use App\HazelCost;
use App\Question;
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
class API_MarketController extends Controller
{
    public function apiIndex(Request $request)
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
            !$request->has('name')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_INDEX]);

        }
        ////////////////////////////////////////////
        try
        {
            $costs=[
//                HazelCost::HAZEL_1500,
//                HazelCost::HAZEL_3300,
//                HazelCost::HAZEL_8700,
//                HazelCost::HAZEL_18000,
//                HazelCost::HAZEL_42000,
//                HazelCost::HAZEL_75000,
                ['product_id'=>HazelCost::hz_lc_200,'product_cost'=>HazelCost::HAZEL_TO_LUCK_200_COST,'product_title'=>HazelCost::HAZEL_TO_LUCK_200,'product_description'=>HazelCost::HAZEL_TO_LUCK_200_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_TO_LUCK_200_AMOUNT],
                ['product_id'=>HazelCost::hz_lc_2000,'product_cost'=>HazelCost::HAZEL_TO_LUCK_2000_COST,'product_title'=>HazelCost::HAZEL_TO_LUCK_2000,'product_description'=>HazelCost::HAZEL_TO_LUCK_2000_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_TO_LUCK_2000_AMOUNT],
                ['product_id'=>HazelCost::hz_1500,'product_cost'=>HazelCost::HAZEL_1500_COST,'product_title'=>HazelCost::HAZEL_1500,'product_description'=>HazelCost::HAZEL_1500_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_1500_AMOUNT],
                ['product_id'=>HazelCost::hz_3300,'product_cost'=>HazelCost::HAZEL_3300_COST,'product_title'=>HazelCost::HAZEL_3300,'product_description'=>HazelCost::HAZEL_3300_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_3300_AMOUNT],
                ['product_id'=>HazelCost::hz_8700,'product_cost'=>HazelCost::HAZEL_8700_COST,'product_title'=>HazelCost::HAZEL_8700,'product_description'=>HazelCost::HAZEL_8700_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_8700_AMOUNT],
                ['product_id'=>HazelCost::hz_18000,'product_cost'=>HazelCost::HAZEL_18000_COST,'product_title'=>HazelCost::HAZEL_18000,'product_description'=>HazelCost::HAZEL_18000_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_18000_AMOUNT],
                ['product_id'=>HazelCost::hz_42000,'product_cost'=>HazelCost::HAZEL_42000_COST,'product_title'=>HazelCost::HAZEL_42000,'product_description'=>HazelCost::HAZEL_42000_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_42000_AMOUNT],
                ['product_id'=>HazelCost::hz_75000,'product_cost'=>HazelCost::HAZEL_75000_COST,'product_title'=>HazelCost::HAZEL_75000,'product_description'=>HazelCost::HAZEL_75000_DESCRIPTION,'product_amount'=>HazelCost::HAZEL_75000_AMOUNT],
            ];
            $publicKey = RequestResponseAPI::getPublicKeyByName($request->input('name'));
            if($publicKey == "")
            {
                return json_encode(['error' => RequestResponseAPI::ERROR_INVALID_PUBLIC_KEY_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_INDEX]);
            }
            return json_encode([ 'token' => $token,'costs' => $costs, 'public_key' => $publicKey, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MARKET_INDEX]);

        }
        catch (\Exception $ex)
        {
//            DB::rollback();
            $logEvent = new LogEvent(-1, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_INDEX]);

        }

    }

    public function apiPurchase(Request $request){
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
            !$request->has('token')||
            !$request->has('code')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_PURCHASE]);

        }
        ////////////////////////////////////////////

        try
        {
            DB::beginTransaction();
            /////////////////////////////////////////////Insert user_purchase$
            /////////////////////////////////////////////Insert user_purchase$
            /////////////////////////////////////////////Insert user_purchase$
            if (DB::table('user_purchase')->where('token', '=', $request['token'])->where('user_id', '=', $user->user_id)->count() == 0) {
                $oldHazel=DB::table('user')->where('user_id', $user->user_id)->pluck('hazel');
                DB::table('user_purchase')->insert([
                    ['purchase_guid' => uniqid('',true),
                        'user_id' => $user->user_id,
                        'token' => $request['token'],
                        'code' => $request['code'],
                        'old_hazel' =>  $oldHazel[0],
                        'created_at' => Carbon::now(),
                        'updated_at' => Carbon::now(),
                    ]
                ]);


                //////////////////////////update hazel of this current user
                if($request['code']==HazelCost::hz_1500){
                    User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_1500_AMOUNT,0);
                }
                if($request['code']==HazelCost::hz_3300){
                    User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_3300_AMOUNT,0);
                }
                if($request['code']==HazelCost::hz_8700){
                    User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_8700_AMOUNT,0);
                }
                if($request['code']==HazelCost::hz_18000){
                    User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_18000_AMOUNT,0);
                }
                if($request['code']==HazelCost::hz_42000){
                    User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_42000_AMOUNT,0);
                }
                if($request['code']==HazelCost::hz_75000){
                    User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_75000_AMOUNT,0);
                }
            }


            DB::commit();

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MARKET_PURCHASE]);

        }
        catch (\Exception $ex)
        {

            DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_PURCHASE]);

        }

    }

    public function apiConvert(Request $request){
        /////////////////////check token
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        //////////////////////////////
        //validation
        if (!$request->has('tag') || !$request->has('key')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_CONVERT]);

        }
        ////////////////////////////////////////////

        try
        {
            if(($request['key']==HazelCost::hz_lc_200 && $user->hazel<200) || ($request['key']==HazelCost::hz_lc_2000 && $user->hazel<2000) ){
                return json_encode(['token' => $token, 'error' => RequestResponseAPI::ERROR_NOT_ENOUGH_BALANCE_USER_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_CONVERT]);
            }
            //////////////////////////update hazel of this current user
            if($request['key']==HazelCost::hz_lc_200){
                User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_TO_LUCK_200_hazel_decrease,HazelCost::HAZEL_TO_LUCK_200_luck_increase);
            }
            if($request['key']==HazelCost::hz_lc_2000){
                User::IncreaseDecreaseHazelLuck($user->user_id,HazelCost::HAZEL_TO_LUCK_2000_hazel_decrease,HazelCost::HAZEL_TO_LUCK_2000_luck_increase);
            }

            return json_encode(['token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MARKET_CONVERT]);

        }
        catch (\Exception $ex)
        {
//          DB::rollback();
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();
            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_CONVERT]);

        }

    }

    public function apiKey(Request $request)
    {
        ///////////////////check token validation/////////////
        $token = null;
        if (session('tokenRefreshed'))
            $token = session('token');
        else
            $token = JWTAuth::parseToken()->getToken()->get();
        $user = JWTAuth::parseToken()->authenticate($token);
        ////////////////////////////////////////////////////////

        /////validation
        if (!$request->has('tag')||
            !$request->has('name')
        ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_KEY]);

        }

        try {

            $publicKey = RequestResponseAPI::getPublicKeyByName($request->input('name'));
            if($publicKey == "")
            {
                return json_encode(['error' => RequestResponseAPI::ERROR_INVALID_PUBLIC_KEY_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_KEY]);
            }

            return json_encode(['token' => $token, 'public_key' => $publicKey, 'error' => 0, 'tag' => RequestResponseAPI::TAG_MARKET_KEY]);
        } catch (\Exception $ex) {
            $logEvent = new LogEventRepository($user->user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_MARKET_KEY]);
        }
    }
}