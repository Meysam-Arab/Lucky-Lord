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
    <style>
        .circle-badge {
            height: 40px;
            width: 40px;
            line-height:40px;
            text-align: center;
            border-radius: 50px;
            background: #84a42b;
            color:white;
            margin-left:auto;
            margin-right:auto;
        }
    </style>
</head>
<body style="direction: rtl;">
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
        @for($i=0;$i<10;$i++)
            <div class="row">
            @for($j=0;$j<7;$j++)
                    <div class="col-xs-1 circle-badge ">{{$table[$i][$j]}}</div>
            @endfor
            </div>
        @endfor
    </div>
    <div class="col-xs-2" id="board">
        @for($i=0;$i<5;$i++)
            <div class="row">
                {{$questions[$i]->description}} ## {{$questions[$i]->answer}} ## {{json_encode($answer_cells[$i])}}
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