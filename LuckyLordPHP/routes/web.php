<?php

Route::get('/image/rename', [ 'uses' => 'QuestionController@renameImage']);
Route::get('/question/{filename}/{path}', ['as'   => 'question.image', 'uses' => 'QuestionController@getImage']);
Route::get('/getAPK', [ 'uses' => 'QuestionController@getAPK']);
Route::post('/question/searchinFullText', 'QuestionController@FullText');

Route::post('/question/searchFullTextinTestImage', 'QuestionController@FullTextinImageTest');
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome2');
})->name('home');
//Route::get('/','QuestionController@detect')->name('home');

Route::get('/addQuestion', function() {
    return view('question/create');
});
Route::post('/questionTest/store', 'QuestionController@storeTestQuestion');
Route::post('/questionImage/store', 'QuestionController@storeImageQuestion');
Route::post('/questionImagepublic/store', 'QuestionController@storequestionImagepublic');
Route::post('/question/store', 'QuestionController@storeQuestion');

Route::post('/questionTest/update', 'QuestionController@updateTestQuestion');
Route::post('/questionImage/update', 'QuestionController@updateImageQuestion');
Route::post('/questionImagepublic/update', 'QuestionController@updatequestionImagepublic');
Route::post('/question/update', 'QuestionController@updateQuestion');

Route::get('/edit_temp_question', ['as'=>'question.edit','uses' => 'QuestionController@edit_temp_question'] );
Route::get('/avatars/{filename}/{path}', ['as'   => 'question.image', 'uses' => 'QuestionController@getAvatar']);

Route::get('/edit_question/{qi}', ['uses' => 'QuestionController@edit_question'] );
//
//Route::get('/test123qwe', 'QuestionController@add_table_question');
//Route::get('/add123qwe', 'QuestionController@add_question_images');
//Route::get('/addimage123qwe', 'QuestionController@get_english_image');
//
//Route::get('/merge','QuestionController@merge_txt_file');

//Route::get('/storejogh','QuestionController@storecity');
//
//Route::get('/print','QuestionController@print_file_for_question_image_english');
//
//Route::get('/save','QuestionController@save_image_english_question_in_question');
//
//Route::get('/repeat123qwe','QuestionController@delete_question_puzzle_repeated');


//Route::get('/firstOrcreate','API_UserCategoryController@firstOrnewFor_uesr_category');

//Route::get('/percentbot','API_UserCategoryController@set_random_percent_for_bot');

//Route::get('/medicalQ','QuestionController@save_medical_question');


//Route::get('/save_poem_Qu','QuestionController@save_poem_Qu');



//Route::get('/math_and_inteligenceQ','QuestionController@save_math_and_inteligence_question');
//Route::get('/poemquestionzxc123','PoemController@save_poems');
//
Route::get('/test', function () {
    return view('test/test');
});
//Route::get('/detect','QuestionController@detect');

Route::get('/login', function () {
    return view('userAdmin/login');
});
//
Route::post('/checkValidation', 'API_UserController@ValidateUserForm');

Route::post('/user/login', 'API_UserController@loginlToMessageManager');


Route::post('/saveMessageData', 'API_UserMessageController@saveUserMessage');
Route::post('/deleteMessageData', 'API_UserMessageController@deleteUserMessage');

//Route::post('/getErrors', 'API_LogController@getErrors');

//Route::get('/qu', function () {
//    return view('test/qu');
//});
//Route::get('/qu', 'QuestionController@getRandomQu');

//Route::get('/moteghateTable', 'QuestionController@moteghateTable');
Route::get('/moteghateView', 'QuestionController@moteghateTableView');
//Route::get('/goInfinite', 'CrossTableController@make_Unlimited_Cross_table');
//Route::get('/statistics', 'API_UserController@add_statistics_user');
//Route::get('/save_words', 'QuestionController@save_words');
//delete_question_repeated

