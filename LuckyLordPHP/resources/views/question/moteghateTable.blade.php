<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">

    <meta name="csrf_token" content="{{csrf_token()}}"/>
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="{{URL::to('css/font.css')}}">
    <link rel="stylesheet" type="text/css" href="{{URL::to('css/test.css')}}">
    <link rel="stylesheet" href="{{URL::to('dice/css/dice3d.css')}}">
    <style>
        .circle-badge {
            height: 100px;
            width: 100px;
            display:block;
            float:left;


            line-height:40px;
            text-align: center;
            border-radius: 10px;
            margin-left:auto;
            margin-right:auto;
            color: #3c763d;
            font-weight: bold;
            background-color: white;
            border-color: #d6e9c6;
        }
        .divider {
            height: 1px;
            width:100%;
            display:block; /* for use on default inline elements like span */
            margin: 9px 0;
            overflow: hidden;
            background-color: #e5e5e5;
        }
        .space11{
            position: absolute;top:15px;left: -10px;z-index: 10000;
        }
        .space32{
            position: absolute;top:96px;left: 40px;z-index: 10000;
        }
        .space21{
            position: absolute;top:45px;left: -10px;z-index: 10000;
        }
        .space12{
            position: absolute;top:-10px;left: 40px;z-index: 10000;
        }
        .space31{
            position: absolute;top:70px;left: -10px;z-index: 10000;
        }
        .space13{
            position: absolute;top:20px;left: 100px;z-index: 10000;
        }
    </style>
</head>
<body style="direction: rtl;background-color:#dff0d8 ">
<div class="container">
    <div class="col-md-12">
        <div class="alert-success alert">بازی سوال و جواب</div>
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
    <div class="col-xs-2" id="board">


    </div>
    <div class="col-xs-8" id="board">
        @for($i=0;$i<$height;$i++)
            <div class="row">
                @for($j=0;$j<$width;$j++)
                    @if(is_array($table[$i*$width+$j]))
                        @if(array_key_exists('secondQ', $table[$i*$width+$j]))
                            <div class="col-xs-1 circle-badge" style="margin:2px 0 0 2px;">
                                <div class="col-xs-12" style="margin:2px 0 0 2px;font-size: 9px;padding: 0">
                                    `                            {{$table[$i*$width+$j]['firstQ']['question']}}
                                </div>
                                <hr class="divider" />
                                <div class="col-xs-12 " style="margin:2px 0 0 2px;font-size: 9px;padding: 0">
                                    {{$table[$i*$width+$j]['secondQ']['question']}}
                                </div>

                                <span class="fa {{$table[$i*$width+$j]['firstQ']['direction']}} fa-1x space{{$table[$i*$width+$j]['firstQ']['directionVer']}}{{$table[$i*$width+$j]['firstQ']['directionHor']}}"></span>
                                <span class="fa {{$table[$i*$width+$j]['secondQ']['direction']}} fa-1x space{{$table[$i*$width+$j]['secondQ']['directionVer']}}{{$table[$i*$width+$j]['secondQ']['directionHor']}}"></span>
                            </div>
                        @else
                            <div class="col-xs-1 circle-badge" style="margin:2px 0 0 2px;">
                                <div class="col-xs-12" style="margin:2px 0 0 2px;font-size: 9px;padding: 0; line-height:90px;">
                                    `                            {{$table[$i*$width+$j]['firstQ']['question']}}
                                </div>
                                <span class="fa {{$table[$i*$width+$j]['firstQ']['direction']}} fa-1x space{{$table[$i*$width+$j]['firstQ']['directionVer']}}{{$table[$i*$width+$j]['firstQ']['directionHor']}}"></span>
                            </div>
                        @endif
                    @else
                        <div class="col-xs-1 circle-badge" style="margin:2px 0 0 2px;padding: 0;line-height:100px;font-size: 30px;color: black;">{{$table[$i*$width+$j]}}</div>
                    @endif
                @endfor
            </div>
        @endfor
    </div>
    <div class="col-xs-2" id="board">
        @for($i=0;$i<5;$i++)
            <div class="row">
                x
            </div>
        @endfor
    </div>




</div>

<form>

</form>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>



</SCRIPT>

</body>
</html>