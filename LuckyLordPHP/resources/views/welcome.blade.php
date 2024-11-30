<!DOCTYPE html>
<html lang="en">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title>لاکی لُرد</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body{
            min-width:250px; /* suppose you want minimun width of 1000px */
                         /* As IE ignores !important it will set width as 1000px; */
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
        @media (max-width: 767px) {

            .hide_in{
                display: none;

            }
            .font_style{color: black;}

        }
        @media (min-width: 768px) {

            .hide_in{
                display: block;
            }
            .font_style{display: none;}

        }
    </style>

</head>


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

<!-- Modal -->

<div class="container" id="slide" style="padding: 0;margin: 0;width: 100%;float: right">
    <div id="myCarousel" class="carousel slide " data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">

            <div class="item active">
                <img src="{{url('style/imgs/1.jpg')}}" alt="Los Angeles" style="width:100%;">
                <div class="carousel-caption">
                    <div class="row hide_in" >
                        <a href="https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa" target="_blank" class="btn btn-primary btn-lg">دانلود از کافه بازار</a>
                    </div>
                    <h2><span class="label label-warning hide_in">لاکی لرد یک بازی هیجان انگیز</span></h2>
                    <p style="font-size: 20px"><span class="label label-warning text-center hide_in">به سوالای مختلف تستی و تشریحی و تصویری و جدول جواب بده و شانس و فندق هدیه بگیر، هم اطلاعات عمومیت رو بالا ببر هم در قرعه کشی شرکت کن</span></p>
                    <p style="font-size:20px"><span class="label label-warning text-center hide_in">شانس به دست بیار و در رتبه بندی رتبه خودت رو بالا ببر و در قرعه کشی ها شرکت کن، هرچی شانست بیشتر باشه احتمال برنده شدنت هم بیشتره</span></p>
                </div>
            </div>

            <div class="item">
                <img src="{{url('style/imgs/2.jpg')}}" alt="Chicago" style="width:100%;">
                <div class="carousel-caption">
                    <div class="row hide_in">
                        <a href="https://www.instagram.com/luckylord_game" target="_blank"  class="btn btn-primary btn-lg">صفحه اینستاگرام لاکی لرد</a>
                    </div>
                    <h3><span class="label label-warning text-center hide_in">آواتارهای مختلف و قشنگ بساز </span></h3>
                    <h3><span class="label label-warning text-center hide_in">برا آواتارت زیورآلات بخر </span></h3>
                    <h3><span class="label label-warning text-center hide_in">عکسش رو با بقیه به اشتراک بگذار </span></h3>
                </div>
            </div>

            <div class="item">
                <img src="{{url('style/imgs/3.jpg')}}" alt="New York" style="width:100%;">
                <div class="carousel-caption">
                    <div class="row hide_in">
                        <a href="{{URL::to('/getAPK')}}" target="_blank"  class="btn btn-primary btn-lg">دانلود مستقیم بازی</a>
                    </div>
                    <h3><span class="label label-warning text-center hide_in">بیش از 100 هزار سوال که در حال افزایشه</span></h3>

                    <h3><span class="label label-warning text-center hide_in">رقابت و مسابقه آنلاین با بقیه </span></h3>
                    <h3><span class="label label-warning text-center hide_in">انواع جدول و بازی های متنوع و جالب</span></h3>
                </div>
            </div>

        </div>

        <!-- Left and right controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

</div>
<div id="textdiv" >
    <div class="row font_style">
        <a style="position: absolute;
margin-left: auto;
margin-right: auto;
top:5px;
left: 0;
right: 0;" href="https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa" target="_blank"  class="btn btn-primary btn-lg">دانلود از کافه بازار</a>
    </div>
    <div class="row font_style">
        <a style="position: absolute;
margin-left: auto;
margin-right: auto;
top:65px;
left: 0;
right: 0;" href="https://www.instagram.com/luckylord_game" target="_blank"  class="btn btn-primary btn-lg">صفحه اینستاگرام لاکی لرد</a>
    </div>
    <div class="row font_style">
        <a style="position: absolute;
margin-left: auto;
margin-right: auto;
top:125px;
left: 0;
right: 0;" href="{{URL::to('/getAPK')}}" target="_blank"  class="btn btn-primary btn-lg">دانلود مستقیم بازی</a>
    </div>
</div>
<script type="text/javascript">
    $(window).on('load',function(){
        $('#msgmodal').modal('show');
        $('#errmodal').modal('show');

        var clientHeight = document.getElementById('slide').clientHeight;
        document.getElementById("textdiv").style.position = 'relative';
        document.getElementById("textdiv").style.top = clientHeight+'px';
    });


    window.addEventListener("resize", myFunction);
    function myFunction() {
//        alert('dwd');
        var clientHeight = document.getElementById('slide').clientHeight;
        document.getElementById("textdiv").style.position = 'relative';
        document.getElementById("textdiv").style.top=clientHeight+'px';
    }
</script>
</body>
</html>
