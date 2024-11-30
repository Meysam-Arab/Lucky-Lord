<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App\LogEvent;
use App\ContactUs;
use App\User;
use App\RequestResponseAPI;
use App\Utility;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;


class API_ContactUsController extends Controller
{

    protected $contactUs;

    public function __construct(ContactUs $contactUs)
    {
        $this->contactUs = $contactUs;
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
             !$request->has('description')) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_STORE_CONTACT_US]);

        }
        ////////////////////////////////////////////
        try {

            $message = $request->input('description');

            try
            {
                Utility::sendInformMail($message,'fardan7eghlim@gmail.com');

            }
            catch (\Exception $ex)
            {
                $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
                $logEvent->store();
            }

            $request['user_id'] = $user->user_id;
            $this->contactUs->intializeByRequest($request);
            $this->contactUs->store();

            return json_encode([ 'token' => $token, 'error' => 0, 'tag' => RequestResponseAPI::TAG_STORE_CONTACT_US]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent($user-> user_id, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_STORE_CONTACT_US]);

        }
    }
}