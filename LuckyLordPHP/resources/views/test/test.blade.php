<html>

<head>
    <meta name="csrf_token" content="{{ csrf_token() }}"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
    <link href="{{URL::to('dist/css/roboto.min.css')}}" rel="stylesheet">
    <link href="{{URL::to('dist/css/material.min.css')}}" rel="stylesheet">
    <link href="{{URL::to('dist/css/ripples.min.css')}}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="{{URL::to('css/font.css')}}">
</head>

<body >

<!-- Your site -->


    <div class="col-lg-3"></div>

    <div class="col-lg-6" style="direction: rtl;margin-top: 20px;">
        <div class="well bs-component">
            <form id="loginForm" class="form-horizontal">
                {{ csrf_field() }}
                <fieldset>
                    <legend>ثبت نام</legend>
                    <div class="form-group col-lg-12">
                        <div class="form-control-wrapper ">
                            <input type="text" class="form-control empty" id="name" name="name" >
                            <div class="floating-label" style="right: 0">نام</div>
                            <small for="name"></small>
                        </div>

                    </div>
                    <br>

                    <div class="form-group bmd-form-group col-lg-12">
                        <div class="form-control-wrapper ">
                            <input type="text" class="form-control form-control-warning empty mdl-textfield__input"  id="family" name="family">
                            <div class="floating-label" style="right: 0">نام خانوادگی</div>
                            <i class="form-control-feedback fa fa-" data-fv-icon-for="PersonGroup" style=""></i>
                             <small for="family"></small>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-control-wrapper col-lg-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"><span class="checkbox-material"></span> چک شدن
                                </label>
                            </div>
                            <br>
                            <div class="togglebutton">
                                <label>
                                    <input type="checkbox" checked=""> نمایش ایمیل
                                </label>
                            </div>
                        </div>
                        <label for="inputFile" class="col-lg-2 control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="form-control-wrapper col-lg-10">
                            <input class="form-control empty" id="focusedInput" type="text">
                            <div class="floating-label" style="right: 0">یه چیزی بنویس . . .</div>
                        </div>
                        <label for="inputFile" class="col-lg-2 control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="form-control-wrapper col-lg-10">
                            <div class="form-control-wrapper fileinput"><input type="text" readonly="" class="form-control empty">
                                <input type="file" id="inputFile" multiple="">
                                <div class="floating-label" style="right: 0">انتخاب فایل . . .</div>
                                <span class="material-input"></span>
                            </div>

                        </div>
                        <label for="inputFile" class="col-lg-2 control-label">فایل</label>

                    </div>
                    <div class="form-group">
                        <div class="col-lg-10">
                            <textarea class="form-control" rows="3" id="textArea"></textarea>
                            <span class="help-block">یک بلاک بزرگ برای یادداشت توضیحات بیشتر</span>
                        </div>
                        <label for="textArea" class="col-lg-2 control-label">توضیحات</label>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-10">
                            <div class="radio radio-primary">
                                <label>
                                    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked=""><span class="circle"></span><span class="check"></span>
                                    گزینه یک
                                </label>
                            </div>
                            <div class="radio radio-primary">
                                <label>
                                    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2"><span class="circle"></span><span class="check"></span>
                                    گزینه دو
                                </label>
                            </div>
                        </div>
                        <label class="col-lg-2 control-label">یک حالت</label>

                    </div>
                    <div class="form-group">
                        <div class="col-lg-10">
                            <select class="form-control" id="select">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                            </select>
                            <br>
                            <select multiple="" class="form-control">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                            </select>
                        </div>
                        <label for="select" class="col-lg-2 control-label">شهرها</label>

                    </div>
                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">
                            <button class="btn btn-default">انصراف</button>
                            <button type="submit" class="btn btn-primary">ثبت نام</button>
                        </div>
                    </div>
                </fieldset>
            </form>

            <div id="source-button" class="btn btn-primary btn-xs" style="display: none;">&lt; &gt;</div></div>
    </div>




    </div>

    <div class="col-lg-3"></div>


<!-- Your site ends -->


<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="{{URL::to('dist/js/ripples.min.js')}}"></script>
<script src="{{URL::to('dist/js/material.min.js')}}"></script>
<script>
    $(document).ready(function() {
        // This command is used to initialize some elements and make them work properly
        $.material.init();
    });
</script>

<script>
    $(document).ready(function(){
        $("input").blur(function(){
//            $this.removeAttr('title');
            var ids=$(this).attr('id');
            var name=$(this).attr('name');

//            var text = $("small[for='"+ids+"']").css("display", "block");
//            var text = $("small[for='"+ids+"']").css("color", "red");

            var data = {};
            data.text=$(this).val();
            data.name=name;
            data._token = $("meta[name='csrf_token']").attr('content');
            var elem=$(this);

            $.ajax({
                url:'{{URL::to('/checkValidation')}}',
                method: 'POST',
                data : data,
                success: function(data) {
                    if (data.success == true) {

                        $("small[for='"+ids+"']").text("");
//                        $("#hiddenMessageFeedback").append(errorMessageTag);
                        $("small[for='"+ids+"']").parent().parent().removeClass( "has-error" );
                        $("input[id='"+ids+"']").css( "color","#73b82e" );

                    } else if (data.success == false) {
                        $("small[for='"+ids+"']").text("'"+data.message+"'");
                        $("small[for='"+ids+"']").css("color", "#ff5151");
//                        $(this).parent().addClass('has-danger');
                        var d=$(this).parent("div");
                        $("small[for='"+ids+"']").parent().parent().addClass( "has-error" );
                        $("input[id='"+ids+"']").css( "color","#ff5151" );
                    }

                },
                error : function(data) {
                    var errorMessageTag = '<div class="alert alert-danger">'+"متاسفانه ارتباط با سرور برقرار نشد لطفا بعدا به طور مجدد تلاش کنید."+' </div>';
//                    $("#hiddenMessageFeedback").append(errorMessageTag);
                    $(this).append(errorMessageTag);
                },
                complete: function(){
                    $('#imgLoaderForFeedback').hide();
                    $(':input[type="submit"]').prop('disabled', false);
                }
            });

        });
    });
</script>

</body>

</html>
