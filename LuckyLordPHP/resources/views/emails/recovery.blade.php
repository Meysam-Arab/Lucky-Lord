
{{--/**--}}
 {{--* Created by PhpStorm.--}}
 {{--* User: Meysam--}}
 {{--* Date: 04/11/2017--}}
 {{--* Time: 02:03 PM--}}
 {{--*/--}}

<html>
<head></head>
{{--<body style="background: black; color: white">--}}
<body>
{{--<h1>{{$title}}</h1>--}}
{{--<p>{{$content}}</p>--}}
<div style="font-family:IRANSans,\'B Yekan\',\'2 Yekan\',Yekan,Tahoma,\'Helvetica Neue\',Arial,sans-serif;background-color: #f3f3f3;display: block;height: 1000px;width: 966px;margin:10px auto;">
    <div style="display: block;height: 380px;width: 650px;margin:0px auto;">
        <div >
            <p style="display: block;height: 50px; background-color: #683014;margin:0 auto;font-size: 30px;text-align:center;padding-top: 15px;color:wheat">
                شما درخواست تغییر رمزتان را داده اید
            </p>
        </div >
        <div style="background-color: white;padding-top: 20px;height: 450px;">
            <div style="background-color: wheat;height: 120px; width: 650px;margin:0 auto;font-size: 20px;text-align:right;padding-top: 0px;">

                <div style="float:right;display: block;width: 650px;">
                    <p  style="margin:0;display: block;font-size: 20px;text-align:right;padding: 0px 5px 0 0; ">کاربر گرامی</p>

                    <p style="display: block;font-family:IRANSans,\'B Yekan\',\'2 Yekan\',Yekan,Tahoma,\'Helvetica Neue\',Arial,sans-serif;font-size:16px;text-align:right;padding: 0px 5px 0 0; ">
                        لطفا روی لینک زیر کلیک کنید فشردن این لینک به معنی تایید شما برای تغییر رمز عبورتان در بازی لاکی لرد خواهد بود و پس از فشردن این لینک شما میتوانید به صفحه لاگین در بازی رفته و با رمز جدید لاگین شوید
                    </p >
                </div>
            </div>
            <div style="background-color: #683014;height: 65px;width: 650px;font-size: 20px;text-align:right;">
                <div style="background-color: #683014;color:wheat;height: 35px;width: 650px;font-size: 20px;text-align:right;margin-top: 20px;">
                    لینک زیر را بفشارید
                </div>
                <div style="background-color: wheat;height: 30px;width: 650px;font-size: 20px;text-align:right;">

                    <form action="http://luckylord.ir/api/RecoverPassword" method="post">
                        <input type="hidden" name="messageText" value="{{$messageText}}">
                        <input type="submit" value="برای تغییر رمزتان اینجا را کلیک کنید">
                    </form>
                </div>
            </div>

            <div style="background-color: #683014;height: 65px;width: 650px;font-size: 20px;text-align:right;">
                <div style="background-color: #683014;color:wheat;height: 45px;width: 640px;font-size: 20px;text-align:right;padding-right: 10px">
                    رمز جدید شما
                </div>
                <div style="background-color: wheat;height: 30px;width: 640px;font-size: 20px;text-align:right;padding-right: 10px">{{$pass}}</div>
            </div>

        </div>
    </div>
    <div style="display: block;height: 230px;width: 650px;margin:0px auto;">
        <div style="display:block;height: 30px;background-color: #683014;color:white;padding-top: 10px;">
            <div style="float: right;width: 215px;    border-collapse: collapse!important;
                color: white;
                font-weight: 400;
                line-height: 1.3;
                margin: 0;
                padding: 0;
                text-align: center;
                vertical-align: top;
                word-wrap: break-word;">

            </div>

            <div style="float: right;width: 215px;    border-collapse: collapse!important;
                color: white;
                font-weight: 400;
                line-height: 1.3;
                margin-right: 215px;
                padding: 0;
                text-align: center;
                vertical-align: top;
                word-wrap: break-word;">
                تماس با ما و درج آگهی
            </div>
        </div>
        <div style="display:block;height: 100px;background-color: wheat;color:white;padding-top: 10px;">
            <div style="float: right;width: 215px;    border-collapse: collapse!important;
                color: white;
                font-weight: 400;
                line-height: 1.3;
                margin: 0;
                padding: 0;
                text-align: center;
                vertical-align: top;
                word-wrap: break-word;">
            </div>
            <div style="float: right;width: 215px;    border-collapse: collapse!important;
                color: white;
                font-weight: 400;
                line-height: 1.3;
                margin-right: 215px;
                padding: 0;
                text-align: center;
                vertical-align: top;
                word-wrap: break-word;">
                <div>شماره تماس واحد پشتیبانی</div>
                <div style="color:#0b93d5">071-32221402</div>
                <div>ایمیل واحد پشتیبانی</div>
                <div style="color:#0b93d5">info@luckylord.ir</div>
            </div>
        </div>
        <div style="display:block;height: 30px;background-color: #683014;color:white;padding-top: 10px;">
            <div style="float: right;width: 215px;    border-collapse: collapse!important;
                color: white;
                font-weight: 400;
                line-height: 1.3;
                margin: 0;
                padding: 0;
                text-align: center;
                vertical-align: top;
                word-wrap: break-word;">

            </div>

            <div style="float: right;width: 215px;    border-collapse: collapse!important;
                color: white;
                font-weight: 400;
                line-height: 1.3;
                margin-right: 215px;
                padding: 0;
                text-align: center;
                vertical-align: top;
                word-wrap: break-word;">
                لاکی لرد
            </div>
        </div>
    </div>
</div>
</body>
</html>

