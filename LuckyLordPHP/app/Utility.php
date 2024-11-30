<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 12/4/2017
 * Time: 10:13 AM
 */
namespace App;

use App\Jobs\SendEmail;
use Carbon\Carbon;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Mail;
use Log;

class Utility extends Authenticatable
{
    //function for filtering query dynemically
    public static function fillQueryFilter($query,$params)
    {
        foreach ($params as $param) {
            switch ($param[0]) {
                case 'orderBy':
                    $query->orderBy($param[1], $param[2]);
                    break;
                case 'take':
                    $query->take($param[1]);
                    break;
                case 'skip':
                    $query->skip($param[1]);
                    break;
                case 'where':
                    $query->where($param[1], $param[2], $param[3]);
                    break;
                case 'groupBy':
                    $query->groupBy($param[1]);
                    break;
                case 'having':
                    $query->having($param[1], $param[2], $param[3]);
                    break;
                case 'orWhere':
                    $query->orWhere($param[1], $param[2]);
                    break;
                case 'whereRaw':
                    $query->whereRaw($param[1]);
                    break;
                case 'orWhereRaw':
                    $query->orWhereRaw($param[1]);
                    break;
                case 'between':
                    $query->whereBetween($param[1],[$param[2], $param[3]]);
                    break;
                case 'orbetween':
                    $query->orWhereBetween($param[1],[$param[2], $param[3]]);
                    break;
                case 'whereIn':
                    $query->whereIn($param[1], $param[2]);
                    break;
            }
        }
        return $query;
    }
    //function for filtering query dynemically
    public static function fillQueryJoin($query,$params)
    {
        foreach ($params as $param) {
            switch ($param[0]) {
                case 'join':
                    $query->join($param[1],$param[2][0],$param[2][1],$param[2][2]);
                    break;
                case 'leftjoin':
                    $query->leftjoin($param[1],$param[2][0],$param[2][1],$param[2][2]);
                    break;
                case 'crossjoin':
                    $query->crossjoin($param[1],$param[2][0],$param[2][1],$param[2][2]);
                    break;
            }
        }
        return $query;
    }
    //function for aliasing query dynemically
    public static function fillQueryAlias($query,$params)
    {
        $temp=array();
        foreach ($params as $param) {
            switch ($param[0]) {
                case 'se':
                    array_push($temp,$param[1].'.'.$param[2]);
                    break;
                case 'st':
                    array_push($temp,$param[1].'.*');
                    break;
                case 'as':
                    array_push($temp,$param[1].'.'.$param[2].' as '.$param[3]);
                    break;
            }
        }
        $query-> select($temp);
        return $query;
    }

//    public  static  function sendRecoveryMail($email, $message,$pass){
//        $messageText = $message;
//        Mail::raw($messageText, function($message) use ( $messageText,$email,$pass)
//        {
//            $message->from('info@luckylord.ir', 'No Reply');
//
//            $message->to($email);
//
//            $message->setBody('
//<div style="font-family:IRANSans,\'B Yekan\',\'2 Yekan\',Yekan,Tahoma,\'Helvetica Neue\',Arial,sans-serif;background-color: #f3f3f3;display: block;height: 1000px;width: 966px;margin:10px auto;">
//    <div style="display: block;height: 380px;width: 650px;margin:0px auto;">
//        <div >
//            <p style="display: block;height: 50px; background-color: #683014;margin:0 auto;font-size: 30px;text-align:center;padding-top: 15px;color:wheat">
//               شما درخواست تغییر رمزتان را داده اید
//            </p>
//        </div >
//        <div style="background-color: white;padding-top: 20px;height: 450px;">
//            <div style="background-color: wheat;height: 120px; width: 650px;margin:0 auto;font-size: 20px;text-align:right;padding-top: 0px;">
//
//                <div style="float:right;display: block;width: 650px;">
//                    <p  style="margin:0;display: block;font-size: 20px;text-align:right;padding: 0px 5px 0 0; ">کاربر گرامی</p>
//
//                    <p style="display: block;font-family:IRANSans,\'B Yekan\',\'2 Yekan\',Yekan,Tahoma,\'Helvetica Neue\',Arial,sans-serif;font-size:16px;text-align:right;padding: 0px 5px 0 0; ">
//                       کاربر گرامی لطفا روی لینک زیر کلیک کنید فشردن این لینک به معنی تایید شما برای تغییر رمز عبورتان در بازی لاکی لرد خواهد بود و پس از فشردن این لینک شما میتوانید به صفحه لاگین در بازی رفته و با رمز جدید لاگین شوید
//                    </p >
//                </div>
//            </div>
//            <div style="background-color: #683014;height: 65px;width: 650px;font-size: 20px;text-align:right;">
//                <div style="background-color: #683014;color:wheat;height: 35px;width: 650px;font-size: 20px;text-align:right;margin-top: 20px;">
//                    لینک زیر را بفشارید
//                </div>
//                <div style="background-color: #D7CCC8;height: 30px;width: 650px;font-size: 20px;text-align:right;">
//
//                    <form action="http://luckylord.ir/api/RecoverPassword" method="post">
//                         <input type="hidden" name="messageText" value="'.$messageText.'">
//                         <input type="submit" value="برای تغییر رمزتان اینجا را کلیک کنید">
//                    </form>
//                </div>
//            </div>
//
//            <div style="background-color: #683014;height: 65px;width: 650px;font-size: 20px;text-align:right;">
//                <div style="background-color: #683014;color:wheat;height: 45px;width: 650px;font-size: 20px;text-align:right;">
//                  رمز جدید شما
//                </div>
//                <div style="background-color: #D7CCC8;height: 30px;width: 650px;font-size: 20px;text-align:right;">'.$pass.'</div>
//            </div>
//
//        </div>
//    </div>
//    <div style="display: block;height: 230px;width: 650px;margin:0px auto;">
//        <div style="display:block;height: 30px;background-color: #0a0a0a;color:white;padding-top: 10px;">
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin: 0;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//
//            </div>
//
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin-right: 215px;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//               تماس با ما و درج آگهی
//            </div>
//        </div>
//        <div style="display:block;height: 100px;background-color: #0d1827;color:white;padding-top: 10px;">
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin: 0;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//            </div>
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin-right: 215px;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//				<div>شماره تماس واحد پشتیبانی</div>
//                <div style="color:#0b93d5">071-32221402</div>
//                <div>ایمیل واحد پشتیبانی</div>
//                <div style="color:#0b93d5">info@luckylord.ir</div>
//            </div>
//        </div>
//        <div style="display:block;height: 40px;background-color: #0a0a0a;color:white;padding-top: 10px;">
//            <div style="float: right;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin: 0;
//                padding-right: 15px;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;
//                margin-right: 215px">
//               هوش مصنوعی فردان هفت اقلیم
//            </div>
//        </div>
//    </div>
//</div>
//', 'text/html'); // for HTML rich messages
//        });
//    }

//    public  static  function sendMail( $message, $email, $pass,$user_name){
//        $messageText = $message;
//        Mail::raw($messageText, function($message) use ($messageText, $email, $user_name ,$pass)
//        {
//            $message->from('info@luckylord.ir', 'No Reply');
//
//            $message->to($email);
//
//            $message->setBody('
//	<div style="font-family:IRANSans,\'B Yekan\',\'2 Yekan\',Yekan,Tahoma,\'Helvetica Neue\',Arial,sans-serif;font-size:16px;text-align:right;padding: 0px 5px 0 0; ">
//	<div>
//		<div style="background-color: #683014;display: block;width: 650px;margin:0px auto;">
//			<div style="height: 65px;width: 650px;font-size: 20px;text-align:right;">
//                <div style="color:wheat;background-color: #683014;height: 35px;width: 650px;font-size: 20px;text-align:right;">
//					اطلاعات ثبت نام
//                </div>
//            </div>
//
//
//            <div style="background-color: #976e6e;height: 65px;width: 650px;font-size: 20px;text-align:right;">
//                <div style="background-color: wheat;height: 35px;width: 650px;font-size: 20px;text-align:right;">
//            نام کاربری
//                </div>
//                <div style="background-color: wheat;height: 30px;width: 650px;font-size: 20px;text-align:right;">' .$user_name.'</div>
//            </div>
//            <div style="background-color: #795548;height: 65px;width: 650px;font-size: 20px;text-align:right;">
//                <div style="background-color: wheat;height: 35px;width: 650px;font-size: 20px;text-align:right;">
//                  رمز عبور
//                </div>
//                <div style="background-color: wheat;height: 30px;width: 650px;font-size: 20px;text-align:right;">'.$pass.'</div>
//            </div>
//
//        </div>
//    </div>
//    <div style="display: block;height: 230px;width: 650px;margin:0px auto;">
//        <div style="display:block;height: 30px;background-color: #0a0a0a;color:white;padding-top: 10px;">
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin: 0;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//
//            </div>
//
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin-right: 215px;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//               تماس با ما و درج آگهی
//            </div>
//        </div>
//        <div style="display:block;height: 100px;background-color: #0d1827;color:white;padding-top: 10px;">
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin: 0;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//            </div>
//            <div style="float: right;width: 215px;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin-right: 215px;
//                padding: 0;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;">
//				<div>شماره تماس واحد پشتیبانی</div>
//                <div style="color:#0b93d5">071-32221402</div>
//                <div>ایمیل واحد پشتیبانی</div>
//                <div style="color:#0b93d5">info@luckylord.ir</div>
//            </div>
//        </div>
//        <div style="display:block;height: 40px;background-color: #0a0a0a;color:white;padding-top: 10px;">
//            <div style="float: right;    border-collapse: collapse!important;
//                color: white;
//                font-weight: 400;
//                line-height: 1.3;
//                margin: 0;
//                padding-right: 15px;
//                text-align: center;
//                vertical-align: top;
//                word-wrap: break-word;
//                margin-right: 215px">
//               هوش مصنوعی فردان هفت اقلیم
//            </div>
//        </div>
//    </div>
//</div>
//
//', 'text/html'); // for HTML rich messages
//        });
//    }


    public static function sendRecoveryMailV2($mmessage ,$email ,$pass)
    {

        Mail::send('emails.recovery', ['pass' => $pass, 'messageText' => $mmessage], function ($message) use ( $email)
        {

            $message->from('info@luckylord.ir', 'Do Not Reply');
            $message->to($email);
            $message->subject('درخواست تغییر رمز عبور');

        });

    }

    public static function sendRegisterMailV2($mmessage, $email, $pass,$user_name)
    {

        //dispatch((new SendEmail())->delay(60 * 5));

        Mail::send('emails.register', ['pass' => $pass, 'message' => $mmessage, 'user_name' => $user_name], function ($message) use ( $email)
        {

            $message->from('info@luckylord.ir', 'Do Not Reply');
            $message->to($email);
            $message->subject('ثبت نام در لاکی لرد');

        });

    }


    public static function sendInformMail($description, $email)
    {

        //dispatch((new SendEmail())->delay(60 * 5));

        Mail::send('emails.inform', [ 'description' => $description], function ($message) use ( $email)
        {

            $message->from('info@luckylord.ir', 'Do Not Reply');
            $message->to($email);
            $message->subject('پیام جدید در قسمت ارتباط با ما');

        });

    }

}

?>

