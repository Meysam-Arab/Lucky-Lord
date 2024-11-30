<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <meta name="csrf_token" content="{{csrf_token()}}"/>
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="{{URL::to('css/font.css')}}">
    <link rel="stylesheet" type="text/css" href="{{URL::to('css/test.css')}}">
    <link rel="stylesheet" href="{{URL::to('dice/css/dice3d.css')}}">
</head>
<body style="direction: rtl;">
<audio id="dice3d-sound" src="{{URL::to('dice/sounds/nc93322.mp3')}}"></audio>
<input id="number" type="number" value=2>
<button id="button-roll">roll</button>
<div class="container">
    <div class="col-md-12">
        <div class="alert-success alert">بازی تخته نرد</div>
    </div>
    @if(session('message'))
        @if (in_array(session('message')->Code, \App\OperationMessage::RedMessages))
            <div class="alert alert-danger alert-dismissable fade in faa-bounce animated">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <ul>
                    <li>{{ session('message')->Text}}{{ session()->forget('message')}}</li>
                </ul>
            </div>
        @else
            <div class="alert alert-success fade in faa-float animated">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <ul>
                    <li>{{ session('message')->Text}}{{ session()->forget('message')}}</li>
                </ul>
            </div>
        @endif
    @endif


    {{--////////////////////////  insert test question--}}
    {{--////////////////////////  insert test question--}}
    {{--////////////////////////  insert test question--}}
    {{--////////////////////////  insert test question--}}


    <div class="col-xs-12 " id="board">
        <div class="col-xs-5 alert-info" id="page2">
            <div class="col-xs-2 alert-info" id="column12">
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
            </div>
            <div class="col-xs-2 alert-info" id="column11"></div>
            <div class="col-xs-2 alert-info" id="column10"></div>
            <div class="col-xs-2 alert-info" id="column9"></div>
            <div class="col-xs-2 alert-info" id="column8">
                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
            </div>
            <div class="col-xs-2 alert-info" id="column7">
            </div>
        </div>
        <div class="col-xs-1 middlecolumn" id=""></div>

        <div class="col-xs-5 alert-info" id="page1">
            <div class="col-xs-2 alert-info" id="column6">

                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
            </div>
            <div class="col-xs-2 alert-info" id="column5"></div>
            <div class="col-xs-2 alert-info" id="column4"></div>
            <div class="col-xs-2 alert-info" id="column3"></div>
            <div class="col-xs-2 alert-info" id="column2"></div>
            <div class="col-xs-2 alert-info" id="column1">
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
            </div>
        </div>
        <div class="col-xs-1 nearcolumn" id=""></div>
        <div class="throw-dice" style="position: absolute;top: 160px;left: 200px;height: 60px;width: 100px;z-index: 1100">
            <script src="{{URL::to('dice/js/dice3d.js')}}"></script>
            <script>
                var button = document.getElementById('button-roll');
                var input = document.getElementById('button-roll');

                button.addEventListener('click', function(e) {
                    e.preventDefault();
                    var n = +document.getElementById('number').value;
                    var log = [];
                    for (var i = 0; i < n; ++i) {
                        var r = 4;
                        log.push(4);
                        dice3d(6, r); // Animate 6 faces dice
                    }
                    console.log(log);
                });
            </script>
        </div>

        <div class="col-xs-5 alert-info" id="page3">
            <div class="col-xs-2 alert-info" id="column13">
                <section class="freeSpaceBounce"></section>
                <section class="UpBounce"></section>
                <section class="UpBounce"></section>
                <section class="UpBounce"></section>
                <section class="UpBounce"></section>
                <section class="UpBounce"></section>
            </div>
            <div class="col-xs-2 alert-info" id="column14"></div>
            <div class="col-xs-2 alert-info" id="column15"></div>
            <div class="col-xs-2 alert-info" id="column16"></div>
            <div class="col-xs-2 alert-info" id="column17">
                <section class="freeSpaceBounce"></section>
                <section class="freeSpaceBounce"></section>
                <section class="freeSpaceBounce"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
            </div>
            <div class="col-xs-2 alert-info" id="column18">

            </div>
        </div>
        <div class="col-xs-1 middlecolumn" id="">fr     </div>

        <div class="col-xs-5 alert-info" id="page4">
            <div class="col-xs-2 alert-info" id="column19">
                <section class="freeSpaceBounce"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
                <section class="bottomBounce" style="z-index: 10000"></section>
            </div>
            <div class="col-xs-2 alert-info" id="column20"></div>
            <div class="col-xs-2 alert-info" id="column21"></div>
            <div class="col-xs-2 alert-info" id="column22">

            </div>
            <div class="col-xs-2 alert-info" id="column23"></div>
            <div class="col-xs-2 alert-info" id="column24">
                <section class="freeSpaceBounce"></section>
                <section class="freeSpaceBounce"></section>
                <section class="freeSpaceBounce"></section>
                <section class="freeSpaceBounce"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
                <section class="UpBounce" style="z-index: 10000"></section>
            </div>
        </div>
        <div class="col-xs-1 nearcolumn" ></div>

    </div>



</div>

<form>

</form>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>

    $( function() {
        $( ".bottomBounce" ).draggable({
            opacity: 0.35,
            revert: true,
            start: function( event, ui ) {
                var col_id=$( this ).parent().attr("id");
                var bottomBouncecount = $('#'+col_id).children('section.bottomBounce').length;
//                alert(bottomBouncecount);
                if(bottomBouncecount>5){
                    $('#'+col_id).children('span.badge').text(bottomBouncecount);
                }
            }
        });
        $( "#column12,#column11,#column10,#column9,#column8,#column7,#column6,#column5,#column4,#column3,#column2,#column1" ).droppable(
            {
                drop: function( event, ui ) {
                    // do something with the draggable item

                    var col_id=$( ui.draggable ).parent().attr("id");
                    var test=$('#'+col_id).children('span.badge').text();
                    if(test>6){
                        $('#'+col_id).children('span.badge').text(--test);
                    }
                    else if(test==6){
                        $('#'+col_id).children('span.badge').remove();
                    }
                    else
                        $(ui.draggable).remove();
                    var bottomBouncecount=0;
                    if ($(this).children('span.badge').length > 0){
                        // do something here
                        bottomBouncecount =  $(this).children('span.badge').text();
                    }else
                        bottomBouncecount = $(this).children('section.bottomBounce').length;
                    if(bottomBouncecount==0){
                        var t=$(this).html("<section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:10000'></section>");

                        t.children().draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==1)
                    {
                        var t=$(this).html("<section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:10000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:10000'></section>");

                        t.children().draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==2)
                    {
                        var t=$(this).html("<section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children().draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==3)
                    {
                        var t=$(this).html("<section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children().draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==4)
                    {
                        var t=$(this).html("<section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children().draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount>4)
                    {
                        bottomBouncecount++;
                        var t=$(this).html("<section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><span class='badge' style='height:42px;position: relative;z-index:1000'>"+bottomBouncecount+"</span>");
                        t.children('section.UpBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }


                }
            }
        );

        $( "#column13,#column14,#column15,#column16,#column17,#column18,#column19,#column20,#column21,#column22,#column23,#column24" ).droppable(
            {
                drop: function( event, ui ) {
                    // do something with the draggable item

                    var col_id=$( ui.draggable ).parent().attr("id");
                    var col=col_id.slice(6, 8);
                    if(col>12){
                        var test=$('#'+col_id).children('span.badge').text();

                        if(test>6){
                            $('#'+col_id).children('span.badge').text(--test);
                        }
                        else if(test==6){
                            $('#'+col_id).children('span.badge').remove();

                            var t=$(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                            t.children('section.bottomBounce').draggable({
                                opacity: 0.35,
                                revert: true
                            });
                        }
                        else{
                            var countOfbottomBounce=$('#'+col_id).children('section.bottomBounce').length;
//                        $(ui.draggable).removeClass('bottomBounce');
                            if(countOfbottomBounce-1==5){
                                var t=$(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                                t.children('section.bottomBounce').draggable({
                                    opacity: 0.35,
                                    revert: true
                                });
                            }
                            if(countOfbottomBounce-1==4){
                                var t=$(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                                t.children('section.bottomBounce').draggable({
                                    opacity: 0.35,
                                    revert: true
                                });
                            }
                            if(countOfbottomBounce-1==3){
                                var t=$(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                                t.children('section.bottomBounce').draggable({
                                    opacity: 0.35,
                                    revert: true
                                });
                            }
                            if(countOfbottomBounce-1==2){
                                var t=$(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                                t.children('section.bottomBounce').draggable({
                                    opacity: 0.35,
                                    revert: true
                                });
                            }
                            if(countOfbottomBounce-1==1){
                                var t=$(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                                t.children('section.bottomBounce').draggable({
                                    opacity: 0.35,
                                    revert: true
                                });
                            }
                            if(countOfbottomBounce-1==0){
                                $(ui.draggable).remove();
                            }

//                        $(ui.draggable).parent().html("<section class='freeSpaceBounce' style='height:42px;'></section>");
//                        $(ui.draggable).remove();
                        }
                    }

//                        $(ui.draggable).remove();
                    var bottomBouncecount=0;
                    if ($(this).children('span.badge').length > 0){
                        // do something here
                        bottomBouncecount =  $(this).children('span.badge').text();
                    }else
                        bottomBouncecount = $(this).children('section.bottomBounce').length;
                    if(bottomBouncecount==0){
                        var t=$(this).html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:10000'></section>");

                        t.children('section.bottomBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==1)
                    {
                        var t=$(this).html("<section class='freeSpaceBounce' style='height:42px'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:10000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:10000'></section>");

                        t.children('section.bottomBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==2)
                    {
                        var t=$(this).html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children('section.bottomBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==3)
                    {
                        var t=$(this).html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children('section.bottomBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount==4)
                    {
                        var t=$(this).html("<section class='freeSpaceBounce' style='height:42px;'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children('section.bottomBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }
                    else if(bottomBouncecount>4)
                    {
                        bottomBouncecount++;
                        var t=$(this).html("<span class='badge' style='height:42px;position: relative;z-index:1000'>"+bottomBouncecount+"</span><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section><section class='bottomBounce ui-draggable ui-draggable-handle' style='height:42px;position: relative;z-index:1000'></section>");
                        t.children('section.bottomBounce').draggable({
                            opacity: 0.35,
                            revert: true
                        });
                    }


                }
            }
        );

    } );
    window.onload = window.onresize = function (event) {
        $("div#page1 div").height($("div#column5").width()*6);
        $("div#page2 div").height($("div#column5").width()*6);
        $("div#page3 div").height($("div#column5").width()*6);
        $("div#page4 div").height($("div#column5").width()*6);

        $(".middlecolumn").height($("div#column5").width()*6);
        $(".bottomBounce").height($("div#column5").width());
        $(".UpBounce").height($("div#column5").width());
        $(".freeSpaceBounce").height($("div#column5").width());
    };
    function gettoken() {
        var token = '{{ csrf_field() }}';
        token = $(token).val();
        return token;
    }

    $( "#testquestion" ).change(function() {
        var data = {};
        data.description=  $('#testquestion').val();
        data._token = $("meta[name='csrf_token']").attr('content');
        $.ajax({
            url:'{{URL::to('question/searchinFullText')}}',
            method: 'POST',
            data : data,
            success: function(data) {
                if (data.success == true) {
                    $( "#hiddenMessageFullText" ).empty();
                    var errorMessageTag = '<div class="alert alert-success">' +
                        '<br/><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><br/>' ;
                    var count=data.count;
                    if(data.count==0){
                        errorMessageTag += 'مورد مشابهی پیدا نشد';
                    }
                    if(data.count>10){
                        count=10;
                    }
                    for(var index=0;index<count;index++){
                        errorMessageTag += data.searchResult[index].question_id+'***';
                        errorMessageTag += data.searchResult[index].description+'<br/>';
                        errorMessageTag += data.searchResult[index].answer+'<br/><br/>';
                    }
                    errorMessageTag += '</div>';
                    $("#hiddenMessageFullText").append(errorMessageTag);

                } else if (data.success == false) {
                    $( "#hiddenMessageFullText" ).empty();
                    var maData;
                    for(maData in data.message){
                        var errorMessageTag = '<div class="alert alert-danger">'+data.message[maData]+' </div>';
                        $("#hiddenMessageFullText").append(errorMessageTag);
                    }

                }

            },
            error : function(data) {
                $( "#hiddenMessageFullText" ).empty();
                var errorMessageTag = '<div class="alert alert-danger">'+
                    '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
                    "متاسفانه ارتباط با سرور برقرار نشد لطفا بعدا به طور مجدد تلاش کنید."+' </div>';
                $("#hiddenMessageFullText").append(errorMessageTag);
            },
            complete: function(){
                $('#imgLoaderForFeedback').hide();
                $(':input[type="submit"]').prop('disabled', false);
            }
        });
    });

    $( "#publicquestion" ).change(function() {
        var data = {};
        data.description=  $('#publicquestion').val();
        data._token = $("meta[name='csrf_token']").attr('content');
        $.ajax({
            url:'{{URL::to('question/searchinFullText')}}',
            method: 'POST',
            data : data,
            success: function(data) {
                if (data.success == true) {
                    $( "#hiddenMessageFullTextForPublic" ).empty();
                    var errorMessageTag = '<div class="alert alert-success">' +
                        '<br/><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><br/>' ;
                    var count=data.count;
                    if(data.count==0){
                        errorMessageTag += 'مورد مشابهی پیدا نشد';
                    }
                    if(data.count>10){
                        count=10;
                    }
                    for(var index=0;index<count;index++){
                        errorMessageTag += data.searchResult[index].question_id+'***';
                        errorMessageTag += data.searchResult[index].description+'<br/>';
                        errorMessageTag += data.searchResult[index].answer+'<br/><br/>';
                    }
                    errorMessageTag += '</div>';
                    $("#hiddenMessageFullTextForPublic").append(errorMessageTag);

                } else if (data.success == false) {
                    $( "#hiddenMessageFullTextForPublic" ).empty();
                    var maData;
                    for(maData in data.message){
                        var errorMessageTag = '<div class="alert alert-danger">'+data.message[maData]+' </div>';
                        $("#hiddenMessageFullTextForPublic").append(errorMessageTag);
                    }

                }

            },
            error : function(data) {
                $( "#hiddenMessageFullText" ).empty();
                var errorMessageTag = '<div class="alert alert-danger">'+
                    '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
                    "متاسفانه ارتباط با سرور برقرار نشد لطفا بعدا به طور مجدد تلاش کنید."+' </div>';
                $("#hiddenMessageFullText").append(errorMessageTag);
            },
            complete: function(){
                $('#imgLoaderForFeedback').hide();
                $(':input[type="submit"]').prop('disabled', false);
            }
        });
    });

    function reverse() {
        var myvalue = $('#inverse1').val();

        for (var mytext = '',i=myvalue.length-1;i>-1;i=i-1)
            mytext += myvalue.charAt(i);
        $('#inverse1').val(mytext);
    }

    function testreverse() {
        var myvalue1 = $('#g1').val();
        var myvalue2 = $('#g2').val();
        var myvalue3 = $('#g3').val();
        var myvalue4 = $('#g4').val();
        var myvalue5 = $('#testquestion').val();

        for (var mytext1 = '',i=myvalue1.length-1;i>-1;i=i-1)
            mytext1 += myvalue1.charAt(i);
        for (var mytext2 = '',i=myvalue2.length-1;i>-1;i=i-1)
            mytext2 += myvalue2.charAt(i);
        for (var mytext3 = '',i=myvalue3.length-1;i>-1;i=i-1)
            mytext3 += myvalue3.charAt(i);
        for (var mytext4 = '',i=myvalue4.length-1;i>-1;i=i-1)
            mytext4 += myvalue4.charAt(i);
        for (var mytext5 = '',i=myvalue5.length-1;i>-1;i=i-1)
            mytext5 += myvalue5.charAt(i);
        $('#g1').val(mytext1);
        $('#g2').val(mytext2);
        $('#g3').val(mytext3);
        $('#g4').val(mytext4);
        $('#testquestion').val(mytext5);


    }

    function myFunction() {
        var x = document.getElementById("mySelect").value;
        //        if guestion is easy
        if(x==1){
            $("input[name='penalty']").val(200);
            $("input[name='min_hazel_reward']").val(5);
            $("input[name='max_hazel_reward']").val(10);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(3);
        }x
        //        if guestion is middle
        if(x==2){
            $("input[name='penalty']").val(250);
            $("input[name='min_hazel_reward']").val(7);
            $("input[name='max_hazel_reward']").val(14);
            $("input[name='min_luck_reward']").val(2);
            $("input[name='max_luck_reward']").val(4);
        }
//        if guestion is hard
        if(x==3){
            $("input[name='penalty']").val(300);
            $("input[name='min_hazel_reward']").val(14);
            $("input[name='max_hazel_reward']").val(20);
            $("input[name='min_luck_reward']").val(3);
            $("input[name='max_luck_reward']").val(5);
        }
    }
</SCRIPT>

</body>
</html>