<html>

<head>
    <meta name="csrf_token" content="{{ csrf_token() }}"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
    <link href="{{URL::to('dist/css/roboto.min.css')}}" rel="stylesheet">
    <link href="{{URL::to('dist/css/material.min.css')}}" rel="stylesheet">
    <link href="{{URL::to('dist/css/ripples.min.css')}}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="{{URL::to('css/font.css')}}">

    <script src="{{URL::to('js/jquery-3.2.1.min.js')}}"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src='https://www.google.com/recaptcha/api.js?explicit&hl=fa'></script>
</head>

<body class="bg">
<style>

    .fill {
        min-height: 100%;
        height: 100%;
    }
    .half-fill{
        min-height: 50%;
        height: 50%;
    }
    .half30-fill{
        min-height: 30%;
        height: 30%;
    }
    .half10-fill{
        min-height: 30%;
        height: 30%;
    }
</style>
<!-- Your site -->

<div class="row">
    @if(session('message'))
        <div class="modal fade" id="msgmodal" role="dialog" style="direction: rtl">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header-success" >
                        <h4 class="modal-title">پیام</h4>
                    </div>
                    <div class="modal-body">
                        <p>{{session('message')}}
                            {{session()->forget('message')}}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">بستن</button>
                    </div>
                </div>
            </div>
        </div>
    @endif


    @if(session('error'))
        <div class="modal fade" id="errModal" role="dialog" style="direction: rtl">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header-danger" >
                        <h4 class="modal-title">پیام</h4>
                    </div>
                    <div class="modal-body">
                        <p>{{session('error')}}
                            {{session()->forget('error')}}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">بستن</button>
                    </div>
                </div>
            </div>
        </div>
    @endif

    @if(session('messages'))
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="alert alert-danger" style="text-align: center">
                    {{session('messages')}}
                    {{session()->forget('messages')}}
                </div>
            </div>
        </div>
    @endif
    @if(session('success_message'))
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="alert alert-success" style="text-align: center">
                    {{session('success_message')}}
                    {{session()->forget('success_message')}}
                </div>
            </div>
        </div>
    @endif
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" style="z-index: 10000" aria-hidden="true" id="showMessages">
        <div class="modal-dialog modal-lg">
            <div class="modal-content" >
                {{--grade--}}
                <div class="well bs-component" style="">
                    <div class="alert alert-danger" role="alert" id="errorList">
                        <h4 class="alert-heading" style='direction: rtl;text-align: right;margin-right: 15px'>توجه!</h4>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <div class="col-lg-12 fill" style="margin-top: 30px;">
        <div class="col-lg-1"></div>

        <div class="col-lg-10" style="direction: rtl;">
            <div class="well bs-component">

                <form id="loginForm" name="loginForm" class="form-horizontal" method="POST" action="{{ url('/user/login') }}">
                    {{ csrf_field() }}
                    <fieldset>
                        <legend>ورود</legend>
                        {{--glyphicon glyphicon-asterisk--}}
                        {{--glyphicon glyphicon-ok--}}
                        {{--glyphicon glyphicon-remove--}}
                        <div class="form-group col-lg-12" style="float: right">
                            <div class="form-control-wrapper ">
                                <input type="text" class="form-control empty" id="national_code" name="national_code">
                                <div class="floating-label" style="right: 0">نام کاربری</div>
                                <span class="glyphicon glyphicon-asterisk" check_icon="national_code"
                                      style="    position: absolute;left:0;color: red;"></span>
                                <small for="national_code"></small>
                            </div>

                        </div>
                        <div class="col-lg-1" style="float: right"></div>
                        <div class="form-group bmd-form-group col-lg-12" style="float: right">
                            <div class="form-control-wrapper ">
                                <input type="password" class="form-control form-control-warning empty mdl-textfield__input"
                                       id="password" name="password">
                                <div class="floating-label" style="right: 0">رمز عبور</div>
                                <span class="glyphicon glyphicon-asterisk" check_icon="password"
                                      style="    position: absolute;left:0;color: red;"></span>
                                <small for="password"></small>
                            </div>
                        </div>




                        <br><br><br>
                        <div class="form-group">
                            <div class="col-lg-10 col-lg-offset-2">
                                <button type="submit" class="btn btn-primary">ورود</button>
                                {{--<a class="btn btn-default" href="{{ url('/user/register') }}">ثبت نام</a>--}}
                                <a class="btn btn-danger" data-toggle="modal" data-target="#" style="float: left">رمز عبورم را فراموش کرده ام</a>
                            </div>
                        </div>
                    </fieldset>
                </form>





                {{--<button class="btn btn-danger" data-toggle="modal" data-target="#myforgetPass">رمز عبورم را فراموش کرده ام</button>--}}
                <div class="modal"  role="dialog" aria-hidden="treu" id="myforgetPass">
                    <div class="modal-dialog modal-sd">
                        <div class="modal-content">
                            <div class="container">
                                <div class="col-md-1"></div>
                                <div class="col-md-10">
                                    <fieldset>
                                        <legend>بازیابی رمز عبور</legend>



                                        <form id="forgetpassForm" method="post" name="forgetpassForm" class="form-horizontal">
                                            <div class="row">
                                                <div class="form-group col-lg-6" style="float: right">
                                                    <div class="form-control-wrapper ">
                                                        <input type="text" class="form-control empty" id="national_code_forget" name="national_code">
                                                        <div class="floating-label" style="right: 0">کد ملی</div>
                                                        <span class="glyphicon glyphicon-asterisk" check_icon="national_code_forget"
                                                              style="    position: absolute;left:0;color: red;"></span>
                                                        <small for="national_code_forget"></small>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group bmd-form-group col-lg-6" style="float: right">
                                                    <div class="form-control-wrapper ">
                                                        <input type="password" class="form-control form-control-warning empty mdl-textfield__input"
                                                               id="new_password" name="new_password">
                                                        <div class="floating-label" style="right: 0">رمز عبور جدید</div>
                                                        <span class="glyphicon glyphicon-asterisk" check_icon="new_password"
                                                              style="    position: absolute;left:0;color: red;"></span>
                                                        <small for="new_password"></small>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group bmd-form-group col-lg-6" style="float: right">
                                                    <div class="form-control-wrapper ">
                                                        <input type="password" class="form-control form-control-warning empty mdl-textfield__input"
                                                               id="repeated_new_password" name="repeated_new_password">
                                                        <div class="floating-label" style="right: 0">تکرار رمز عبور جدید</div>
                                                        <span class="glyphicon glyphicon-asterisk" check_icon="repeated_new_password"
                                                              style="    position: absolute;left:0;color: red;"></span>
                                                        <small for="repeated_new_password"></small>
                                                    </div>
                                                </div>
                                            </div>
                                            <button type="submit" id="send_new_pass" class="btn btn-primary">ارسال برای بازنشانی</button>

                                        </form>



                                    </fieldset>
                                </div>
                                <div class="col-md-1"></div>
                            </div>


                        </div>
                    </div>
                </div>




                <div id="source-button" class="btn btn-primary btn-xs" style="display: none;">&lt; &gt;</div>
            </div>
        </div>
        <div class="col-lg-1"></div>

    </div>
</div>




<!-- Your site ends -->




<script src="{{URL::to('dist/js/ripples.min.js')}}"></script>
<script src="{{URL::to('dist/js/material.min.js')}}"></script>
<script src="{{URL::to('dist/js/bootstrap-waitingfor.min.js')}}"></script>



</body>

</html>
