<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::post('/user/apiLogin', 'Auth\API_LoginController@apiLogIn');
Route::post('/user/apiRegister', 'API_UserController@apiRegister');
Route::post('/user/apiVisitorRegister', 'API_UserController@apiVisitorRegister');
Route::post('/user/apiIndex', 'API_UserController@apiIndex')->middleware('jwt.auth');
Route::post('/user/apiRank', 'API_UserController@apiRank')->middleware('jwt.auth');
Route::post('/user/apiProfile', 'API_UserController@apiProfile')->middleware('jwt.auth');
Route::post('/user/apiUpdateProfile', 'API_UserController@apiUpdateProfile')->middleware('jwt.auth');
Route::post('/user/apiUpdatePhone', 'API_UserController@apiUpdatePhone')->middleware('jwt.auth');
Route::post('/user/apiUpdateEmail', 'API_UserController@apiUpdateEmail')->middleware('jwt.auth');
Route::post('/user/apiUpdateBirthDate', 'API_UserController@apiUpdateBirthDate')->middleware('jwt.auth');
Route::post('/user/apiUpdatePassword', 'API_UserController@apiUpdatePassword')->middleware('jwt.auth');
Route::post('/user/apiDecreaseHazelLuck', 'API_UserController@apiDecreaseHazelLuck')->middleware('jwt.auth');
Route::post('/user/apiUpdateGender', 'API_UserController@apiUpdateGender')->middleware('jwt.auth');
Route::post('/user/apiInfo', 'API_UserController@apiInfo')->middleware('jwt.auth');
Route::post('/user/apiChangeHazelLuck', 'API_UserController@apiChangeHazelLuck')->middleware('jwt.auth');
Route::post('/user/apiChangeLevel', 'API_UserController@apiChangeLevel')->middleware('jwt.auth');
Route::post('/user/apiForgetPassword', 'API_UserController@apiForgetPassword');
Route::post('/RecoverPassword', 'API_UserController@apiRecoverPassword');
Route::post('/user/apiSearch', 'API_UserController@apiSearch')->middleware('jwt.auth');
Route::post('/user/apiCups', 'API_UserController@apiCups')->middleware('jwt.auth');
Route::post('/user/apiUpdateAllowChat', 'API_UserController@apiUpdateAllowChat')->middleware('jwt.auth');


Route::post('/contact_us/apiStore', 'API_ContactUsController@apiStore')->middleware('jwt.auth');

Route::post('/question/apiNext', 'API_QuestionController@apiNext')->middleware('jwt.auth');
Route::post('/question/apiReport', 'API_QuestionController@apiReport')->middleware('jwt.auth');
Route::post('/question/apiRate', 'API_QuestionController@apiRate')->middleware('jwt.auth');
Route::post('/question/apiWordTable', 'API_QuestionController@apiWordTable')->middleware('jwt.auth');
Route::post('/question/apiTableReport', 'API_QuestionController@apiTableReport')->middleware('jwt.auth');
Route::post('/question/apiTableRate', 'API_QuestionController@apiTableRate')->middleware('jwt.auth');
Route::post('/question/apiUniversalReport', 'API_QuestionController@apiUniversalReport')->middleware('jwt.auth');
Route::post('/question/apiCrossTable', 'API_QuestionController@apiCrossTable')->middleware('jwt.auth');


Route::post('/message/apiIndex', 'API_MessageController@apiIndex')->middleware('jwt.auth');
Route::post('/message/apiRead', 'API_MessageController@apiRead')->middleware('jwt.auth');

Route::post('/draw/apiIndex', 'API_DrawController@apiIndex')->middleware('jwt.auth');
Route::post('/draw/apiWinners', 'API_DrawController@apiWinners')->middleware('jwt.auth');
Route::post('/draw/apiParticipate', 'API_DrawController@apiParticipate')->middleware('jwt.auth');

Route::post('/home/apiIndex', 'API_HomeController@apiIndex')->middleware('jwt.auth');
Route::post('/home/apiEgg', 'API_HomeController@apiEgg')->middleware('jwt.auth');
Route::post('/home/apiUnread', 'API_HomeController@apiUnread')->middleware('jwt.auth');
Route::post('/home/apiAdHazel', 'API_HomeController@apiAdHazel')->middleware('jwt.auth');
Route::post('/home/apiPlayerId', 'API_HomeController@apiPlayerId')->middleware('jwt.auth');
Route::post('/home/apiGetVersion', 'API_HomeController@apiGetVersion');
Route::post('/home/test', 'API_HomeController@test');

Route::post('/user_message/apiDelete', 'API_UserMessageController@apiDelete')->middleware('jwt.auth');

Route::post('/market/apiIndex', 'API_MarketController@apiIndex')->middleware('jwt.auth');;
Route::post('/market/apiPurchase', 'API_MarketController@apiPurchase')->middleware('jwt.auth');
Route::post('/market/apiConvert', 'API_MarketController@apiConvert')->middleware('jwt.auth');

Route::post('/body_part/apiIndex', 'API_BodyPartController@apiIndex')->middleware('jwt.auth');
Route::post('/body_part/apiPurchase', 'API_BodyPartController@apiPurchase')->middleware('jwt.auth');

Route::post('/match/apiRequest', 'API_MatchController@apiRequest')->middleware('jwt.auth');
Route::post('/match/apiStatus', 'API_MatchController@apiStatus')->middleware('jwt.auth');
Route::post('/match/apiResult', 'API_MatchController@apiResult')->middleware('jwt.auth');
Route::post('/match/apiIndex', 'API_MatchController@apiIndex')->middleware('jwt.auth');


Route::post('/user_friend/apiRequest', 'API_UserFriendController@apiRequest')->middleware('jwt.auth');
Route::post('/user_friend/apiStatus', 'API_UserFriendController@apiStatus')->middleware('jwt.auth');
Route::post('/user_friend/apiChangeStatus', 'API_UserFriendController@apiChangeStatus')->middleware('jwt.auth');
Route::post('/user_friend/apiList', 'API_UserFriendController@apiList')->middleware('jwt.auth');
Route::post('/user_friend/apiRevoke', 'API_UserFriendController@apiRevoke')->middleware('jwt.auth');
Route::post('/user_friend/apiRecommend', 'API_UserFriendController@apiRecommend')->middleware('jwt.auth');
Route::post('/user_friend/apiSync', 'API_UserFriendController@apiSync')->middleware('jwt.auth');
Route::post('/user_friend/apiValidateSync', 'API_UserFriendController@apiValidateSync')->middleware('jwt.auth');



Route::post('/log/apiStore', 'API_LogController@apiStore')->middleware('jwt.auth');

Route::post('/universal_match/apiRequest', 'API_UserUniversalMatchController@apiRequest')->middleware('jwt.auth');
Route::post('/universal_match/apiStatus', 'API_UserUniversalMatchController@apiStatus')->middleware('jwt.auth');
Route::post('/universal_match/apiResult', 'API_UserUniversalMatchController@apiResult')->middleware('jwt.auth');
Route::post('/universal_match/apiIndex', 'API_UserUniversalMatchController@apiIndex')->middleware('jwt.auth');
Route::post('/universal_match/apiChangeStatus', 'API_UserUniversalMatchController@apiChangeStatus')->middleware('jwt.auth');
Route::post('/universal_match/apiRematchRequest', 'API_UserUniversalMatchController@apiRematchRequest')->middleware('jwt.auth');
