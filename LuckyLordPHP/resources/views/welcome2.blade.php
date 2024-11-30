<?php
/**
 * Created by PhpStorm.
 * User: Amir
 * Date: 2/27/2018
 * Time: 1:16 PM
 */
?>
<html>
<header>
    <title>لاکی لرد</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body{
            min-width:250px; /* suppose you want minimun width of 1000px */
            /* As IE ignores !important it will set width as 1000px; */
            margin: 0px;
            background: linear-gradient(10deg, #1c9134, #0a4330);
        }
        *{
            font-family: Tahoma;
        }
        .set_bg_board{
            display:block;
            background-image: url('//imgs/t_board_c.png');
            background-size: 100% 100%;
            background-repeat:no-repeat;
            width: 60%;
            height: 60%;
            z-index: 20;
        }
        .modal-header-success {
            color:#fff;
            padding:9px 15px;
            border-bottom:1px solid #eee;
            background-color: #5cb85c;
            -webkit-border-top-left-radius: 5px;
            -webkit-border-top-right-radius: 5px;
            -moz-border-radius-topleft: 5px;
            -moz-border-radius-topright: 5px;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
        }
        .modal-header-danger {
            color:#fff;
            padding:9px 15px;
            border-bottom:1px solid #eee;
            background-color: #d9534f;
            -webkit-border-top-left-radius: 5px;
            -webkit-border-top-right-radius: 5px;
            -moz-border-radius-topleft: 5px;
            -moz-border-radius-topright: 5px;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
        }

        @media screen and (max-width: 767px) and  (orientation: portrait) {
            .hide_in{
                display: none;
            }

            .font_style{color: black;}
        }
        @media only screen and (max-width: 480px) {
            #setmargin{
                margin-top: 200px;
            }
            .zoombuttom{
                display: block;
                width: 100%;
                height: 200px;
                font-size: 30px;
                padding-left: 250px;
                padding-top: 85px;
            }
        }


        @media(min-width: 481px){
            .hide_in{
                display: block;
            }
            .zoombuttom{
                 /*width: 300px;*/
                 height: 120px;
                 padding-left: 100px;
                 padding-top: 45px;
                font-size: 23px;
             }
            .zoombuttom2{
                padding-left: 130px;
            }

            .font_style{display: none;}

        }
    </style>
</header>
<body>
@if( ! empty($message))
    <div class="modal fade" id="msgmodal" role="dialog" style="direction: rtl">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header-success" >
                    <h4 class="modal-title">پیام</h4>
                </div>
                <div class="modal-body">
                    <p>{{$message}}</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">بستن</button>
                </div>
            </div>
        </div>
    </div>
@endif
@if(!( Browser::isFirefox() || Browser::isOpera() || Browser::isChrome() || Browser::isIE() || Browser::isSafari()))
    <div class="modal fade in" id="detectmodal" role="dialog" style="direction: rtl">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header-success" >
                    <h4 class="modal-title">پیام</h4>
                </div>
                <div class="modal-body">
                    <p>کاربر گرامی مرورگر شما استاندارد نیست و احتمال دارد این سایت روی دستگاه شما به خوبی کار نکند</p>
                    <div class="row">
                        <a href="https://www.mozilla.org/en-US/firefox/new/" class="btn btn-primary"> دانلود فایرفاکس</a>
                        <a href="https://www.google.com/chrome/" class="btn btn-primary"> دانلود کروم</a>

                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">بستن</button>
                </div>
            </div>
        </div>
    </div>
@endif


@if( ! empty($error))
    <div class="modal fade" id="errModal" role="dialog" style="direction: rtl">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header-danger" >
                    <h4 class="modal-title">پیام</h4>
                </div>
                <div class="modal-body">
                    <p>{{$error}}</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">بستن</button>
                </div>
            </div>
        </div>
    </div>
@endif
<div style="">
    <img src="{{url('imgs/newbg_02.png')}}" height="100%" style="position: fixed;z-index: -10"/>
    <img src="{{url('imgs/newbg_03.png')}}" height="100%" style="position: fixed;z-index: -10;right: 0"/>
    <img src="{{url('imgs/t_land_a.png')}}" width="100%" height="7%" style="position: fixed;z-index: 0;right: 0;bottom: 0"/>
    <div>
        <img src="{{url('imgs/t_title.png')}}" width="30%" style="display:block;margin-left: auto;margin-right: auto;z-index: 1;"/>
        {{--<!--        <img src="resources/t_board_c.png" width="50%" height="60%" style="position:absolute;z-index: 20;margin-left: 10%"/>-->--}}
    </div>
    <div class="container" id="setmargin">
        <div style="display:block;background-size: 100% 100%;background-repeat:no-repeat;width: 80%;height: 60%;z-index: 20;"
             class="col-lg-pull-1 col-lg-7 col-xs-pull-1 col-xs-11">
            <div style="height: 30%;z-index: 21" class="container-fluid" id="boxbutton">
        {{--<div class="col-md-3"></div>--}}
                <div class="col-md-6 col-xs-push-2 col-xs-10" style="flex-direction: column;justify-content:space-between;align-self: center;">
                    <h1
                        style="display:block;
                                {{--background-image: url({{url('imgs/b_spring_a.png')}});--}}
                                background-size: 100% 100%;
                                background-repeat:no-repeat;
                                height: 55px;
                                z-index: 21;
                                text-decoration: none;
                                color:#E5BB5C;
                                font-weight: bold;
                                text-align: center;
                                font-size: 23px;">

                   اولین مجموعه بازی کلمات</h1>
                    {{--<a href="https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa" target="_blank"--}}
                    <a href="#" class="zoombuttom" target="_blank" id="cafebazar"  data-toggle="modal" data-target="#myModal2"
                       style="display:flex;
                               background-image: url({{url('imgs/btn_long_e2.png')}});
                               background-size: 100% 100%;
                               background-repeat:no-repeat;

                               z-index: 21;
                               text-decoration: none;
                               color:#321e0c;

                               font-weight: bold;
                               text-align: center;">
                        دانلود از کافه بازار
                    </a>



                    <div>
                        <a class="zoombuttom" href="https://www.instagram.com/luckylord_game" target="_blank"
                           style="display:flex;
                                   background-image: url({{url('imgs/btn_long_e2.png')}});
                                   background-size: 100% 100%;
                                   background-repeat:no-repeat;

                                   z-index: 21;
                                   text-decoration: none;
                                   color:#321e0c;

                                   font-weight: bold;
                                   text-align: center;
                               ">
                            صفحه اینستاگرام
                        </a>

                    </div>

                    <div>
                        <a class="zoombuttom zoombuttom2" href="#" target="_blank" data-toggle="modal" data-target="#myModal3"
                           style="display:flex;
                                   background-image: url({{url('imgs/btn_long_e2.png')}});
                                   background-size: 100% 100%;
                                   background-repeat:no-repeat;

                                   z-index: 21;
                                   text-decoration: none;
                                   color:#321e0c;
                                   font-weight: bold;
                                   text-align: center;
                                   margin: 0 auto;
                               ">
                            دانلود مستقیم
                        </a>

                    </div>
                </div>
                <div class="col-md-3"></div>

            </div>
        </div>
    </div>
    <div style="width: 100%;direction: rtl;bottom: 0;position: fixed;right: 20%">
        <img src="{{url('imgs/c_squirrel_a.png')}}" width="20%" style="z-index: 200;"/>
    </div>
</div>
<script type="text/javascript">
    $(window).on('load',function(){
        $('#detectmodal').modal('show');
    });
</script>
<!-- Modal for cafebazar -->
<div class="modal fade" id="myModal2" role="dialog " >
    <div class="modal-dialog" style="z-index: 10000">

        <!-- Modal content-->
        <div class="modal-content">

            <div class="modal-body">
                {{--<div class="row" style="display: flex;flex-direction: column">--}}
                    {{--<span style="direction: rtl">صفحه کافه بازار</span>--}}
                {{--</div>--}}
                <div class="row" style="display: flex;flex-direction:column;justify-content:space-between;align-self: center;">
                    <img src="{{url('imgs/bazzarLuckylord.png')}}" width="400px" height="400px" style="margin: 50px auto;" />
                    <a href="https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa" target="_blank"
                       style="display:inline-flex;
                               position:relative;
                               background-image: url({{url('imgs/btn_long_e2.png')}});
                               background-size: 100% 100%;
                               background-repeat:no-repeat;
                               width: 400px;
                               height: 145px;
                               z-index: 21;
                               text-decoration: none;
                               color:#321e0c;
                               padding-left: 70px;
                               padding-top: 40px;
                               font-size: 33px;
                               font-weight: bold;
                               text-align: center;
                               margin:50px auto;">
                        دانلود از کافه بازار
                    </a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 100%;height: 100px;font-size: 30px">بستن</button>
            </div>
        </div>

    </div>
</div>
<!-- Modal for direct link -->
<div class="modal fade" id="myModal3" role="dialog " >
    <div class="modal-dialog" style="z-index: 10000">

        <!-- Modal content-->
        <div class="modal-content">

            <div class="modal-body">
                {{--<div class="row" style="display: flex;flex-direction: column">--}}
                    {{--<span style="direction: rtl">دانلود مستقیم</span>--}}
                {{--</div>--}}
                <div class="row" style="display: flex;flex-direction:column;justify-content:space-between;align-self: center;">
                    <img src="{{url('imgs/directLinkOfLuckyLord.png')}}" width="400px" height="400px" style="margin:50px auto;"/>
                    <a href="{{URL::to('/getAPK')}}" target="_blank"
                       style="display:inline-flex;
                               position:relative;
                               background-image: url({{url('imgs/btn_long_e2.png')}});
                               background-size: 100% 100%;
                               background-repeat:no-repeat;
                               width: 400px;
                               height: 145px;
                               z-index: 21;
                               text-decoration: none;
                               color:#321e0c;
                               padding-left: 100px;
                               padding-top: 40px;
                               font-size: 33px;
                               font-weight: bold;
                               text-align: center;
                               margin:50px auto;">
                        دانلود مستقیم
                    </a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 100%;height: 100px;font-size: 30px">بستن</button>
            </div>
        </div>

    </div>
</div>
</body>
</html>
</body>




