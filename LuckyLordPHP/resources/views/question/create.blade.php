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
</head>
<body style="direction: rtl;">

<div class="container">
    <div class="col-md-12">


        <div class="alert-success alert">افزودن سوال</div>
        <br>
        <span  class="alert-warning alert">شماره شناسایی شما : </span>
        <input  class="alert-warning alert" type="text" class="form-control" id="identify" name="identify" value="{{session('identifyCode') }}">
    </div>


    <div>
        @foreach ($errors->all() as $error)
            <div class="col-md-12 alert-danger alert">{{ $error }}</div>
        @endforeach
            @if(session('message'))
                @if (session('message')=="عملیات با موفقیت انجام شد")
                        <div class="col-md-12 alert alert-success alert-dismissable fade in faa-bounce animated">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <ul>
                                <li>{{ session('message')}}{{ session()->forget('message')}}</li>
                            </ul>
                        </div>
                @elseif(session('message')=="کد شناسایی غلطه")
                        <div class="col-md-12 alert alert-danger fade in faa-float animated">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <ul>
                        <li>{{ session('message')}}{{ session()->forget('message')}}</li>
                        </ul>
                        </div>

                @endif
            @endif
    </div>

    <div class="container">

        <ul class="nav nav-tabs navbar-right">
            <li  <?php if(session('tabmenu')=='1') echo 'class="active"';else echo'';?> ><a data-toggle="tab" href="#menu4" <?php if(session('tabmenu')=='1') echo 'aria-expanded="true"';else echo'aria-expanded="false"';?>>افزودن سوال عکسی تستی </a></li>
            <li <?php if(session('tabmenu')=='2') echo 'class="active"';else echo'';?>><a data-toggle="tab" href="#menu2" <?php if(session('tabmenu')=='2') echo 'aria-expanded="true"';else echo'aria-expanded="false"';?>>افزودن سوال عکسی تشریحی</a></li>
            <li <?php if(session('tabmenu')=='3') echo 'class="active"';else echo'';?>><a data-toggle="tab" href="#menu3" <?php if(session('tabmenu')=='3') echo 'aria-expanded="true"';else echo'aria-expanded="false"';?>>افزوذن سوال تشریحی</a></li>
            <li <?php if(session('tabmenu')=='4') echo 'class="active"';else echo'';?> ><a data-toggle="tab" href="#menu1" <?php if(session('tabmenu')=='4') echo 'aria-expanded="true"';else echo'aria-expanded="false"';?>>افزودن سوال تستی</a></li>
        </ul>

        <div class="tab-content">

            {{--////////////////////////  insert image test question--}}
            {{--////////////////////////  insert image test question--}}
            {{--////////////////////////  insert image test question--}}
            {{--////////////////////////  insert image test question--}}
            <div id="menu4" <?php if(session('tabmenu')=='1') echo 'class="col-md-12 alert-info tab-pane fade in active"';else echo'class="col-md-12 alert-info tab-pane fade"';?>>
                <h1 class="alert-danger alert">افزودن سوال عکسی تستی</h1>
                <form action="{{ url('questionImage/store') }}" method="post" enctype="multipart/form-data"
                      class="form-horizontal style-form add_hidden">
                    <div class="form-group">
                        <div class="row">
                            <div class="md-md-6">
                                <label for="category">سختی سوال</label>
                                <select class="form-control easyHardselect3"  name="easyHard" onchange="myFunction3()">
                                    <option value="1" <?php if(session('easyHard')=='1') echo 'selected="selected"';else echo'';?>>آسان
                                    <option value="2" <?php if(session('easyHard')=='2') echo 'selected="selected"';else echo'';?>>متوسط
                                    <option value="3" <?php if(session('easyHard')=='3') echo 'selected="selected"';else echo'';?>>سخت
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="_token" value="{{ csrf_token() }}">
                    <div class="col-md-12 form-group">
                        <label for="email">صورت سوال</label>
                        <select class="form-control"  name="description" onchange="myFunction()">
                            <option value="سوال تصویری" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>سوال تصویری
                            <option value="بجای علامت سوال چی باشه ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>بجای علامت سوال چی باشه ؟
                            <option value="بجای علامت سوال چی اضافه بشه ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>بجای علامت سوال چی اضافه بشه ؟

                            <option value=" مفهوم تابلوی زیر چیست ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>> مفهوم تابلوی زیر چیست ؟

                            <option value="نام لوگوی این پرچم ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام لوگوی این پرچم ؟
                            <option value="نام این سریال ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این سریال ؟
                            <option value="نام این فیلم ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این فیلم ؟
                            <option value="نام این گیاه زینتی ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این گیاه زینتی ؟
                            <option value="?what is it" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>?what is it
                            <option value="نام این لوگو ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این لوگو ؟
                            <option value="نام این بازیکن؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این بازیکن؟
                            <option value="نام این تیم؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این تیم؟
                            <option value="نام این باشگاه؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این باشگاه؟
                            <option value="نام این مربی؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این مربی؟
                            <option value="نام این کشتی گیر؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این کشتی گیر؟
                            <option value="نام بازیکن سمت راست؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیکن سمت راست؟
                            <option value="نام بازیکن سمت چپ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیکن سمت چپ؟
                            <option value="لقب این بازیکن؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>لقب این بازیکن؟
                            <option value="نام این ورزشگاه؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این ورزشگاه؟
                            <option value="ملیت این بازیکن ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>ملیت این بازیکن ؟

                            <option value="واحد پول این کشور؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>واحد پول این کشور؟
                            <option value="نام کشور این پرچم ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام کشور این پرچم ؟

                            <option value="نام این بازیگر؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این بازیگر؟
                            <option value="نام بازیگر سمت چپ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیگر سمت چپ؟
                            <option value="نام بازیگر سمت راست؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیگر سمت راست؟
                            <option value="نام این چهره؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این چهره؟

                            <option value="اینجا کجاست؟" <?php if(session('description')=='1') echo 'selected="selected"';else echo'';?>>اینجا کجاست؟

                        </select>
                    </div>
                    <div class="col-md-12 form-group">
                        <label class="control-label">تصویر سوال</label>
                        <div>
                            {{--<input type="file" name="fileLogo"/>--}}
                            <span class="btn btn-default btn-file">
                        <input id="input-img" type="file" multiple class="file-loading" name="fileLogo">
                    </span>
                        </div>
                    </div>

                    <div id="hiddenMessageImageRes"> </div>


                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="1"></label>
                        <label for="email">گزینه اول</label>
                        <input type="text" class="form-control" id="g1" name="answer1" value="{{ old('answer1') }}">
                    </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="2"></label>
                        <label for="pwd">گزینه دوم</label>
                        <input type="text" class="form-control" id="g2" name="answer2" value="{{ old('answer2') }}">
                    </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="3"></label>
                        <label for="email">گزینه سوم</label>
                        <input type="text" class="form-control" id="g3" name="answer3" value="{{ old('answer3') }}">
                    </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="4"></label>
                        <label for="pwd">گزینه چهارم</label>
                        <input type="text" class="form-control" id="g4" name="answer4" value="{{ old('answer4') }}">
                    </div>







                    <button type="submit" class="btn btn-primary col-md-12 luckbutton" onclick="disable()">ثبت سوال</button>
                    <div class="divider"></div>


                    <div class="col-md-3 form-group">
                        <label for="email">از فندق</label>
                        <input type="text" class="form-control" name="min_hazel_reward" id=""  value="{!!session('min_hazel_reward') !!}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا فندق</label>
                        <input type="text" class="form-control" name="max_hazel_reward" id=""  value="{{session('max_hazel_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">از شانس</label>
                        <input type="text" class="form-control" name="min_luck_reward" id=""  value="{{session('min_luck_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا شانس</label>
                        <input type="text" class="form-control" name="max_luck_reward" id="max_luck_reward" value="{{session('max_luck_reward') }}" readonly>
                    </div>
                    <div class="row"></div>
                    <div class="form-group">
                        <label for="category">دسته بندی سوال</label>
                        <select class="form-control" id="category" name="category">
                            <option value="0"<?php if(session('category')=='0') echo 'selected="selected"' ?>>یک دسته بندی انتخاب کن</option>
                            <option value="1"<?php if(session('category')=='1') echo 'selected="selected"' ?>>تاریخی</option>
                            <option value="2"<?php if(session('category')=='2') echo 'selected="selected"';else echo'';?>>شعر و ادب</option>
                            <option value="3"<?php if(session('category')=='3') echo 'selected="selected"';else echo'';?>>زبان انگلیسی</option>
                            <option value="4"<?php if(session('category')=='4') echo 'selected="selected"';else echo'';?>>فرهنگ و هنر</option>
                            <option value="5"<?php if(session('category')=='5') echo 'selected="selected"';else echo'';?>>مذهبی</option>
                            <option value="6"<?php if(session('category')=='6') echo 'selected="selected"';else echo'';?>>جغرافیا</option>
                            <option value="7"<?php if(session('category')=='7') echo 'selected="selected"';else echo'';?>>تکنولوژی</option>
                            <option value="8" <?php if(session('category')=='8') echo 'selected="selected"';else echo'';?>>پزشکی</option>
                            <option value="9" <?php if(session('category')=='9') echo 'selected="selected"';else echo'';?>>ورزشی</option>
                            <option value="10" <?php if(session('category')=='10') echo 'selected="selected"';else echo'';?>>ریاضی و هوش</option>
                            <option value="11" <?php if(session('category')=='11') echo 'selected="selected"';else echo'';?>>موزیک</option>
                            <option value="12" <?php if(session('category')=='12') echo 'selected="selected"';else echo'';?>>اطلاعات عمومی</option>
                            <option value="13" <?php if(session('category')=='13') echo 'selected="selected"';else echo'';?>>چیستان</option>

                        </select>
                    </div>
                    <div class="row"></div>
                    <div class="col-md-12 form-group">
                        <label for="email">خطا</label>
                        <input type="text" class="form-control" name="penalty" id=""  value="{{session('penalty')}}" readonly>
                    </div>

                </form>
            </div>


            {{--////////////////////////  insert image public question--}}
            {{--////////////////////////  insert image public question--}}
            {{--////////////////////////  insert image public question--}}
            {{--////////////////////////  insert image public question--}}
            <div id="menu2" <?php if(session('tabmenu')=='2') echo 'class="col-md-12 alert-info tab-pane fade in active"';else echo'class="col-md-12 alert-info tab-pane fade"';?>>
                <h1 class="alert-warning alert"> افزودن سوال عکسی تشریحی  </h1>
                <form action="{{ url('questionImagepublic/store') }}" method="post" enctype="multipart/form-data"
                      class="form-horizontal style-form add_hidden">
                    <div class="form-group">
                        <div class="row">
                            <div class="md-md-6">
                                <label for="category">سختی سوال</label>
                                <select class="form-control easyHardselect2"  name="easyHard" onchange="myFunction2()">
                                    <option value="1" <?php if(session('easyHard')=='1') echo 'selected="selected"';else echo'';?>>آسان
                                    <option value="2" <?php if(session('easyHard')=='2') echo 'selected="selected"';else echo'';?>>متوسط
                                    <option value="3" <?php if(session('easyHard')=='3') echo 'selected="selected"';else echo'';?>>سخت
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="_token" value="{{ csrf_token() }}">
                    <div class="col-md-12 form-group">
                        <label for="email">صورت سوال</label>
                        <select class="form-control"  name="description" onchange="myFunction()">
                            <option value="سوال تصویری" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>سوال تصویری
                            <option value=" مفهوم تابلوی زیر چیست ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>> مفهوم تابلوی زیر چیست ؟

                            <option value="نام لوگوی این پرچم ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام لوگوی این پرچم ؟
                            <option value="نام این سریال ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این سریال ؟
                            <option value="نام این فیلم ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این فیلم ؟
                            <option value="نام این گیاه زینتی ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این گیاه زینتی ؟
                            <option value="?what is it" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>?what is it
                            <option value="نام این لوگو ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این لوگو ؟

                            <option value="نام این بازیکن؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این بازیکن؟
                            <option value="نام این تیم؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این تیم؟
                            <option value="نام این باشگاه؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این باشگاه؟
                            <option value="نام این مربی؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این مربی؟
                            <option value="نام این کشتی گیر؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این کشتی گیر؟
                            <option value="نام بازیکن سمت راست؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیکن سمت راست؟
                            <option value="نام بازیکن سمت چپ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیکن سمت چپ؟
                            <option value="لقب این بازیکن؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>لقب این بازیکن؟
                            <option value="نام این ورزشگاه؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این ورزشگاه؟
                            <option value="ملیت این بازیکن ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>ملیت این بازیکن ؟

                            <option value="واحد پول این کشور؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>واحد پول این کشور؟
                            <option value="نام کشور این پرچم ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام کشور این پرچم ؟

                            <option value="نام این بازیگر؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این بازیگر؟
                            <option value="نام بازیگر سمت چپ؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیگر سمت چپ؟
                            <option value="نام بازیگر سمت راست؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام بازیگر سمت راست؟
                            <option value="نام این چهره؟" <?php if(session('description')=='2') echo 'selected="selected"';else echo'';?>>نام این چهره؟

                            <option value="اینجا کجاست؟" <?php if(session('description')=='1') echo 'selected="selected"';else echo'';?>>اینجا کجاست؟

                        </select>
                    </div>
                    <div class="col-md-12 form-group">
                        <label class="control-label">تصویر سوال</label>
                        <div>
                            {{--<input type="file" name="fileLogo"/>--}}
                            <span class="btn btn-default btn-file">
                        <input id="input-img" type="file" multiple class="file-loading" name="fileLogo">
                    </span>
                        </div>
                    </div>

                    <div id="hiddenMessageImageRes"> </div>


                    <div class="col-md-12 form-group" style="margin-left: 20px;">
                        <label for="email">جواب سوال عکسی رو اینجا بنویس</label>
                        <input type="text" class="form-control" id="g1" name="answer1" value="{{ old('answer1') }}">
                    </div>








                    <button type="submit" class="btn btn-primary col-md-12 luckbutton" onclick="disable()">ثبت سوال</button>
                    <div class="divider"></div>


                    <div class="col-md-3 form-group">
                        <label for="email">از فندق</label>
                        <input type="text" class="form-control" name="min_hazel_reward" id=""  value="{!!session('min_hazel_reward') !!}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا فندق</label>
                        <input type="text" class="form-control" name="max_hazel_reward" id=""  value="{{session('max_hazel_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">از شانس</label>
                        <input type="text" class="form-control" name="min_luck_reward" id=""  value="{{session('min_luck_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا شانس</label>
                        <input type="text" class="form-control" name="max_luck_reward" id="max_luck_reward" value="{{session('max_luck_reward') }}" readonly>
                    </div>
                    <div class="row"></div>
                    <div class="form-group">
                        <label for="category">دسته بندی سوال</label>
                        <select class="form-control" id="category" name="category">
                            <option value="0"<?php if(session('category')=='0') echo 'selected="selected"' ?>>یک دسته بندی انتخاب کن</option>
                            <option value="1"<?php if(session('category')=='1') echo 'selected="selected"' ?>>تاریخی</option>
                            <option value="2"<?php if(session('category')=='2') echo 'selected="selected"';else echo'';?>>شعر و ادب</option>
                            <option value="3"<?php if(session('category')=='3') echo 'selected="selected"';else echo'';?>>زبان انگلیسی</option>
                            <option value="4"<?php if(session('category')=='4') echo 'selected="selected"';else echo'';?>>فرهنگ و هنر</option>
                            <option value="5"<?php if(session('category')=='5') echo 'selected="selected"';else echo'';?>>مذهبی</option>
                            <option value="6"<?php if(session('category')=='6') echo 'selected="selected"';else echo'';?>>جغرافیا</option>
                            <option value="7"<?php if(session('category')=='7') echo 'selected="selected"';else echo'';?>>تکنولوژی</option>
                            <option value="8" <?php if(session('category')=='8') echo 'selected="selected"';else echo'';?>>پزشکی</option>
                            <option value="9" <?php if(session('category')=='9') echo 'selected="selected"';else echo'';?>>ورزشی</option>
                            <option value="10" <?php if(session('category')=='10') echo 'selected="selected"';else echo'';?>>ریاضی و هوش</option>
                            <option value="11" <?php if(session('category')=='11') echo 'selected="selected"';else echo'';?>>موزیک</option>
                            <option value="12" <?php if(session('category')=='12') echo 'selected="selected"';else echo'';?>>اطلاعات عمومی</option>
                            <option value="13" <?php if(session('category')=='13') echo 'selected="selected"';else echo'';?>>چیستان</option>
                        </select>
                    </div>
                    <div class="row"></div>
                    <div class="col-md-12 form-group">
                        <label for="email">خطا</label>
                        <input type="text" class="form-control" name="penalty" id=""  value="{{session('penalty')}}" readonly>
                    </div>

                </form>
            </div>



            {{--////////////////////////  insert public question--}}
            {{--////////////////////////  insert public question--}}
            {{--////////////////////////  insert public question--}}
            {{--////////////////////////  insert public question--}}
            <div id="menu3" <?php if(session('tabmenu')=='3') echo 'class="col-md-12 alert-info tab-pane fade in active"';else echo'class="col-md-12 alert-info tab-pane fade"';?>>

                <h1 class="alert-info alert">افزودن سوال تشریحی</h1>

                <form action="{{ url('question/store') }}" method="post" class="add_hidden">
                    <div class="form-group">
                        <div class="row">
                            <div class="md-md-6">
                                <label for="category">سختی سوال</label>
                                <select class="form-control easyHardselect4"  name="easyHard" onchange="myFunction4()">
                                    <option value="1" <?php if(session('easyHard')=='1') echo 'selected="selected"';else echo'';?>>آسان
                                    <option value="2" <?php if(session('easyHard')=='2') echo 'selected="selected"';else echo'';?>>متوسط
                                    <option value="3" <?php if(session('easyHard')=='3') echo 'selected="selected"';else echo'';?>>سخت
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="_token" value="{{ csrf_token() }}">
                    <div class="col-md-12 form-group">
                        <label for="email">صورت سوال</label>
                        <input type="text" class="form-control"  name="description" id="publicquestion" value=" ؟ ">
                    </div>
                    <div id="hiddenMessageFullTextForPublic"> </div>
                    <div class="divider"></div>
                    <div class="col-md-12 form-group">
                        <label for="email">جواب سوال</label>
                        <input type="text" class="form-control" name="answer" id="">
                    </div>
                    <div class="divider"></div>
                    <button type="submit" class="btn btn-primary col-md-12 luckbutton" onclick="disable()">ثبت سوال</button>
                    <div class="col-md-3 form-group">
                        <label for="email">از فندق</label>
                        <input type="text" class="form-control" name="min_hazel_reward" id=""  value="{!!session('min_hazel_reward') !!}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا فندق</label>
                        <input type="text" class="form-control" name="max_hazel_reward" id=""  value="{{session('max_hazel_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">از شانس</label>
                        <input type="text" class="form-control" name="min_luck_reward" id=""  value="{{session('min_luck_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا شانس</label>
                        <input type="text" class="form-control" name="max_luck_reward" id="max_luck_reward" value="{{session('max_luck_reward') }}" readonly>
                    </div>
                    <div class="divider"></div>
                    <div class="form-group">
                        <label for="category">دسته بندی سوال</label>
                        <select class="form-control Publicscategory" id="category" name="category">
                            <option value="0"<?php if(session('category')=='0') echo 'selected="selected"' ?>>یک دسته بندی انتخاب کن</option>
                            <option value="1"<?php if(session('category')=='1') echo 'selected="selected"' ?>>تاریخی</option>
                            <option value="2"<?php if(session('category')=='2') echo 'selected="selected"';else echo'';?>>شعر و ادب</option>
                            <option value="3"<?php if(session('category')=='3') echo 'selected="selected"';else echo'';?>>زبان انگلیسی</option>
                            <option value="4"<?php if(session('category')=='4') echo 'selected="selected"';else echo'';?>>فرهنگ و هنر</option>
                            <option value="5"<?php if(session('category')=='5') echo 'selected="selected"';else echo'';?>>مذهبی</option>
                            <option value="6"<?php if(session('category')=='6') echo 'selected="selected"';else echo'';?>>جغرافیا</option>
                            <option value="7"<?php if(session('category')=='7') echo 'selected="selected"';else echo'';?>>تکنولوژی</option>
                            <option value="8" <?php if(session('category')=='8') echo 'selected="selected"';else echo'';?>>پزشکی</option>
                            <option value="9" <?php if(session('category')=='9') echo 'selected="selected"';else echo'';?>>ورزشی</option>
                            <option value="10" <?php if(session('category')=='10') echo 'selected="selected"';else echo'';?>>ریاضی و هوش</option>
                            <option value="11" <?php if(session('category')=='11') echo 'selected="selected"';else echo'';?>>موزیک</option>
                            <option value="12" <?php if(session('category')=='12') echo 'selected="selected"';else echo'';?>>اطلاعات عمومی</option>
                            <option value="13" <?php if(session('category')=='13') echo 'selected="selected"';else echo'';?>>چیستان</option>
                            <option value="14" <?php if(session('category')=='14') echo 'selected="selected"';else echo'';?>>جدول</option>
                        </select>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="email">خطا</label>
                        <input type="text" class="form-control" name="penalty" id=""  value="{{session('penalty')}}" readonly>
                    </div>

                </form>
            </div>

            {{--////////////////////////  insert test question--}}
            {{--////////////////////////  insert test question--}}
            {{--////////////////////////  insert test question--}}
            {{--////////////////////////  insert test question--}}
            <div id="menu1" <?php if(session('tabmenu')=='4') echo 'class="col-md-12 alert-info tab-pane fade in active"';else echo'class="col-md-12 alert-info tab-pane fade"';?>  st>
                <h1 class="alert-success alert">افزودن سوال تستی</h1>
                <form action="{{ url('questionTest/store') }}" method="post" class="add_hidden" >
                    <div class="form-group">
                        <div class="row">
                            <div class="md-md-6">
                                <label for="category">سختی سوال</label>
                                <select class="form-control easyHardselect1" id="mySelect" name="easyHard" onchange="myFunction1()">
                                    <option value="1" <?php if(session('easyHard')=='1') echo 'selected="selected"';else echo'';?>>آسان
                                    <option value="2" <?php if(session('easyHard')=='2') echo 'selected="selected"';else echo'';?>>متوسط
                                    <option value="3" <?php if(session('easyHard')=='3') echo 'selected="selected"';else echo'';?>>سخت
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="_token" value="{{ csrf_token() }}">
                    <div class="col-md-12 form-group">
                        <label for="email">صورت سوال</label>
                        <input type="text" class="form-control" id="testquestion" name="description" value="{{ old('description') }}">
                        {{--<input type="text" class="form-control" id="testquestion" name="description" value="  معنی کلمه ' ' چیست ؟">--}}
                    </div>
                    <div id="hiddenMessageFullText"> </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="1"></label>
                        <label for="email">گزینه اول</label>
                        <input type="text" class="form-control" id="g1" name="answer1" value="{{ old('answer1') }}">
                    </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="2"></label>
                        <label for="pwd">گزینه دوم</label>
                        <input type="text" class="form-control" id="g2" name="answer2" value="{{ old('answer2') }}">
                    </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="3"></label>
                        <label for="email">گزینه سوم</label>
                        <input type="text" class="form-control" id="g3" name="answer3" value="{{ old('answer3') }}">
                    </div>
                    <div class="col-md-5 form-group" style="margin-left: 20px;">
                        <label><input type="radio" name="result" value="4"></label>
                        <label for="pwd">گزینه چهارم</label>
                        <input type="text" class="form-control" id="g4" name="answer4" value="{{ old('answer4') }}">
                    </div>

                    <button type="submit" class="btn btn-primary col-xs-6 luckbutton" onclick="disable()">ثبت سوال</button>
                    <button  onclick="testreverse()" class="btn btn-warning" dir="rtl" type="button">برعکس</button>


                    <div class="row">
                        <div class="md-md-6">
                            <label for="category">دسته بندی سوال</label>
                            <select class="form-control" id="category" name="category">
                                <option value="0"<?php if(session('category')=='0') echo 'selected="selected"' ?>>یک دسته بندی انتخاب کن</option>
                                <option value="1"<?php if(session('category')=='1') echo 'selected="selected"' ?>>تاریخی</option>
                                <option value="2"<?php if(session('category')=='2') echo 'selected="selected"';else echo'';?>>شعر و ادب</option>
                                <option value="3"<?php if(session('category')=='3') echo 'selected="selected"';else echo'';?>>زبان انگلیسی</option>
                                <option value="4"<?php if(session('category')=='4') echo 'selected="selected"';else echo'';?>>فرهنگ و هنر</option>
                                <option value="5"<?php if(session('category')=='5') echo 'selected="selected"';else echo'';?>>مذهبی</option>
                                <option value="6"<?php if(session('category')=='6') echo 'selected="selected"';else echo'';?>>جغرافیا</option>
                                <option value="7"<?php if(session('category')=='7') echo 'selected="selected"';else echo'';?>>تکنولوژی</option>
                                <option value="8" <?php if(session('category')=='8') echo 'selected="selected"';else echo'';?>>پزشکی</option>
                                <option value="9" <?php if(session('category')=='9') echo 'selected="selected"';else echo'';?>>ورزشی</option>
                                <option value="10" <?php if(session('category')=='10') echo 'selected="selected"';else echo'';?>>ریاضی و هوش</option>
                                <option value="11" <?php if(session('category')=='11') echo 'selected="selected"';else echo'';?>>موزیک</option>
                                <option value="12" <?php if(session('category')=='12') echo 'selected="selected"';else echo'';?>>اطلاعات عمومی</option>
                                <option value="13" <?php if(session('category')=='13') echo 'selected="selected"';else echo'';?>>چیستان</option>


                            </select>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="col-md-3 form-group">
                        <label for="email">از فندق</label>
                        <input type="text" class="form-control" name="min_hazel_reward" id=""  value="{!!session('min_hazel_reward') !!}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا فندق</label>
                        <input type="text" class="form-control" name="max_hazel_reward" id=""  value="{{session('max_hazel_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">از شانس</label>
                        <input type="text" class="form-control" name="min_luck_reward" id=""  value="{{session('min_luck_reward') }}" readonly>
                    </div>
                    <div class="col-md-3 form-group">
                        <label for="email">تا شانس</label>
                        <input type="text" class="form-control" name="max_luck_reward" id="max_luck_reward" value="{{session('max_luck_reward') }}" readonly>
                    </div>
                    <div class="col-md-12 form-group">
                        <label for="email">خطا</label>
                        <input type="text" class="form-control" name="penalty" id=""  value="{{session('penalty')}}" readonly>
                    </div>

                </form>
            </div>


        </div>
    </div>


















</div>

<form>

</form>
<script>
//    function disable(){
//        $(".luckbutton").prop("disabled",true);
//
//        $(':input[type="submit"]').prop('disabled', true);
//    }
//    $('.luckbutton').on('click',function() {
//        $(':input[type="submit"]').prop('disabled', true);
//    });

$('.luckbutton').on('click', function() {
    var $this = $(this);
    $this.button('loading');
    setTimeout(function() {
        $this.button('reset');
    }, 8000);
});
</script>
<script>
    $( document ).ready(function() {

        var code= $("#identify").val();
        $(".deleteidentifyCode").remove();
        $('.add_hidden').append("<input type='hidden' class='deleteidentifyCode' name='identifyCode' value='"+code+"'>");


        var ttt='{{session('min_hazel_reward')}}';
//        alert('dsff'+ttt);
        if(ttt!=5 && ttt!=7 && ttt!=14){
            $("input[name='penalty']").val(200);
            $("input[name='min_hazel_reward']").val(5);
            $("input[name='max_hazel_reward']").val(10);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
    });


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
                    if(data.count>20){
                        count=20;
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

    $('input[type=radio][name=result]').change(function() {
        var data = {};
        data.description='';
        if (this.value == '1') {
            data.description=  $('#g1').val();
        }
        else if (this.value == '2') {
            data.description=  $('#g2').val();
        }
        else if (this.value == '3') {
            data.description=  $('#g3').val();
        }
        else if (this.value == '4') {
            data.description=  $('#g4').val();
        }
        data._token = $("meta[name='csrf_token']").attr('content');
        $.ajax({
            url:'{{URL::to('question/searchFullTextinTestImage')}}',
            method: 'POST',
            data : data,
            success: function(data) {
                if (data.success == true) {
                    $( "#hiddenMessageImageRes" ).empty();
                    var errorMessageTag = '';
                    var count=data.count;
                    if(data.count==0){
                        errorMessageTag += 'مورد مشابهی پیدا نشد';
                    }
                    if(data.count>20){
                        count=20;
                    }
                    for(var index=0;index<count;index++){
                        <?php
                            $temp='5956b07f0fb2a5.63922510';
                         ?>
                        errorMessageTag += data.searchResult[index].image;
                        errorMessageTag += data.searchResult[index].question_id+'***';
                        errorMessageTag += data.searchResult[index].description+'***';
                        errorMessageTag += data.searchResult[index].answer;
                    }
                    $("#hiddenMessageImageRes").append(errorMessageTag);

                } else if (data.success == false) {
                    $( "#hiddenMessageImageRes" ).empty();
                    var maData;
                    for(maData in data.message){
                        var errorMessageTag = '<div class="alert alert-danger">'+data.message[maData]+' </div>';
                        $("#hiddenMessageImageRes").append(errorMessageTag);
                    }

                }

            },
            error : function(data) {
                $( "#hiddenMessageImageRes" ).empty();
                var errorMessageTag = '<div class="alert alert-danger">'+
                    '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
                    "متاسفانه ارتباط با سرور برقرار نشد لطفا بعدا به طور مجدد تلاش کنید."+' </div>';
                $("#hiddenMessageImageRes").append(errorMessageTag);
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


        data.category = "<?php  echo session('category') ?>";
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
                    if(data.count>20){
                        count=20;
                    }
                    for(var index=0;index<count;index++){
                        @if(session('category')==14)
                            errorMessageTag += data.searchResult[index].question_puzzle_id+'***';
                        @else
                            errorMessageTag += data.searchResult[index].question_id+'***';
                        @endif
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

    $( "#identify" ).change(function() {
        var code= $("#identify").val();
        $(".deleteidentifyCode").remove();
        $('.add_hidden').append("<input type='hidden' class='deleteidentifyCode' name='identifyCode' value='"+code+"'>");
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

    function myFunction1() {
//        var x = document.getElementsByClassName("easyHardselect").value;
        var x =  $(".easyHardselect1").val();

        $(".easyHardselect4").val(x);
        $(".easyHardselect2").val(x);
        $(".easyHardselect3").val(x);
        //        if guestion is easy
        if(x==1){
            $("input[name='penalty']").val(200);
            $("input[name='min_hazel_reward']").val(5);
            $("input[name='max_hazel_reward']").val(10);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }x
        //        if guestion is middle
        if(x==2){
            $("input[name='penalty']").val(250);
            $("input[name='min_hazel_reward']").val(7);
            $("input[name='max_hazel_reward']").val(14);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
//        if guestion is hard
        if(x==3){
            $("input[name='penalty']").val(300);
            $("input[name='min_hazel_reward']").val(14);
            $("input[name='max_hazel_reward']").val(20);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
    }
    function myFunction2() {
//        var x = document.getElementsByClassName("easyHardselect").value;
        var x =  $(".easyHardselect2").val();

        $(".easyHardselect1").val(x);
        $(".easyHardselect4").val(x);
        $(".easyHardselect3").val(x);
        //        if guestion is easy
        if(x==1){
            $("input[name='penalty']").val(200);
            $("input[name='min_hazel_reward']").val(5);
            $("input[name='max_hazel_reward']").val(10);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }x
        //        if guestion is middle
        if(x==2){
            $("input[name='penalty']").val(250);
            $("input[name='min_hazel_reward']").val(7);
            $("input[name='max_hazel_reward']").val(14);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
//        if guestion is hard
        if(x==3){
            $("input[name='penalty']").val(300);
            $("input[name='min_hazel_reward']").val(14);
            $("input[name='max_hazel_reward']").val(20);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
    }
    function myFunction3() {
//        var x = document.getElementsByClassName("easyHardselect").value;
        var x =  $(".easyHardselect3").val();

        $(".easyHardselect1").val(x);
        $(".easyHardselect2").val(x);
        $(".easyHardselect4").val(x);
        //        if guestion is easy
        if(x==1){
            $("input[name='penalty']").val(200);
            $("input[name='min_hazel_reward']").val(5);
            $("input[name='max_hazel_reward']").val(10);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }x
        //        if guestion is middle
        if(x==2){
            $("input[name='penalty']").val(250);
            $("input[name='min_hazel_reward']").val(7);
            $("input[name='max_hazel_reward']").val(14);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
//        if guestion is hard
        if(x==3){
            $("input[name='penalty']").val(300);
            $("input[name='min_hazel_reward']").val(14);
            $("input[name='max_hazel_reward']").val(20);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
    }
    function myFunction4() {
//        var x = document.getElementsByClassName("easyHardselect").value;
        var x =  $(".easyHardselect4").val();

        $(".easyHardselect1").val(x);
        $(".easyHardselect2").val(x);
        $(".easyHardselect3").val(x);
        //        if guestion is easy
        if(x==1){
            $("input[name='penalty']").val(200);
            $("input[name='min_hazel_reward']").val(5);
            $("input[name='max_hazel_reward']").val(10);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }x
        //        if guestion is middle
        if(x==2){
            $("input[name='penalty']").val(250);
            $("input[name='min_hazel_reward']").val(7);
            $("input[name='max_hazel_reward']").val(14);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
//        if guestion is hard
        if(x==3){
            $("input[name='penalty']").val(300);
            $("input[name='min_hazel_reward']").val(14);
            $("input[name='max_hazel_reward']").val(20);
            $("input[name='min_luck_reward']").val(1);
            $("input[name='max_luck_reward']").val(1);
        }
    }

</SCRIPT>

</body>
</html>