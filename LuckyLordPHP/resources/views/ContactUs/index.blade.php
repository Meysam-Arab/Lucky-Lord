<html>
<head>
    <meta name="csrf_token" content="{{ csrf_token() }}"/>
    <title>مدیریت لاکی لُرد</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="{{URL::to('css/font.css')}}">

    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css">

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.1.1/css/responsive.dataTables.min.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .important{
            background-color: #edffed;
        }
        #tashilat{
            /*height: auto !important;*/
            padding: 1px 12px !important;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <noscript>
        <style type="text/css">
            .pagecontainer {display:none;
            }
            .noscriptmsg{
                background-color: red;
                float: right;
                font-size: 24px;
                text-align: right;
            }
        </style>
        <div class="col-lg-10" style="direction: rtl;margin-top: 20px;float: right;text-align: right">
            <div class="well bs-component" style="">

                <div class="alert alert-danger" role="alert">
                    <h4 class="alert-heading">توجه!</h4>
                    <p class="">
                        کاربر گرامی شما در تنظمیات مرورگر خود اجرای کدهای جاوااسکریپت را غیر فعال کرده اید در صورتی که می خواهید از این وبسایت استفاده کنید لطفا تنظیمات مرورگر خود را به حالت اولیه برگردانید شما میتوانید در لینک زیر آموزش این کار را در مرورگر های مختلف ببینید
                    </p>
                </div>


            </div>
        </div>

    </noscript>

    {{--<script src="/path/to/bootstrap/js/bootstrap.min.js"></script>--}}
</head>


<body class="important">

<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="showMessages">
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

<div class="row" style="margin-top: 5%">
    {{--{{$rewards}}--}}
    <div class="col-lg-1"></div>
    <div class="col-lg-10" >
        <fieldset>
            <legend style="float: right;direction: rtl">تنظیمات:</legend>
            <div class="row">


                    <div class="col-lg-6">
                        <a class="btn btn-danger" href="{{URL::to('/logout')}}" style="float: left">خروج</a>
                        <a class="btn btn-primary" target="_blank" href="{{URL::to('/addQuestion')}}" style="float: left">افزودن سوال</a>
                        <a class="btn btn-primary" target="_blank" href="{{URL::to('/edit_temp_question')}}" style="float: left">ویرایش سوال های آزمایشی</a>
                    </div>
                    <div class="col-lg-3">
                        <form tag="3">
                            <input type="hidden" name="_token" value="{{ csrf_token() }}">
                            <button type="submit" class="btn btn-danger" style="float: right">نمایش گزارش سوال ها توسط کاربران</button>
                        </form>
                    </div>
                    <div class="col-lg-3">
                        <form tag="4">
                            <input type="hidden" name="_token" value="{{ csrf_token() }}">
                            <button type="submit" class="btn btn-success" style="float: right">نمایش پیغام های کاربران</button>
                        </form>
                    </div>

            </div>

        </fieldset>

    </div>
    <div class="col-lg-1"></div>
</div>
<br/><br/><br/><br/>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog" style="direction: rtl">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">ارسال پیغام</h4>
            </div>
            <div class="modal-body" >


                <form tag="1">
                    <div class="row">
                        {{ csrf_field() }}
                        <input type="hidden" name="user_id" id="user_id" value="">
                        <input type="hidden" name="contact_us_id" id="contact_us_id" value="">
                        <div class="col-md-9">
                            <div class="form-control-wrapper ">
                                <select class="form-control checkValidate" id="message_id_select"  name="message_id" style="direction: rtl">
                                    <option value='0' id="reward-0" selected='selected'>لطفا یک پیغام را انتخاب کنید</option>
                                    @if(! empty($message))
                                        @foreach($message as $mymessage)
                                            <option value='{{$mymessage->message_id}}' decs="{{$mymessage->description}}" id="reward-{{$mymessage->message_id}}" >{{$mymessage->title}}</option>
                                        @endforeach
                                    @endif
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            متن پیام
                        </div>
                    </div>
                    <button class="btn btn-primary"  type="submit">ارسال پیام</button>
                </form>



            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">بستن</button>
            </div>
        </div>

    </div>
</div>
<div class="row">
    <div class="col-lg-1"></div>
    <div class="col-lg-10" >
        <div class="row" style="margin:1px">
            <div class="table-responsive" >
                <table id="example" class="display nowrap full-width"  cellspacing="0" width="100%" style="direction: rtl;margin-top: 20px;float: right;text-align: right">
                    <thead class="noprint">
                    <tr style="direction: ltr">
                        <th style="text-align: right">آی دی<span class="fa fa-id-badge"></span></th>
                        <th style="text-align: right">نام کاربر <span class="fa fa-user"></span></th>
                        <th style="text-align: right">عنوان<span class="fa fa-id-card-o"></span></th>
                        <th style="text-align: right">متن<span class="fa fa-id-card-o"></span></th>
                        <th style="text-align: right">جزئیات<span class="fa fa-cogs"></span></th>
                        <th style="text-align: right">ایمیل<span class="fa fa-envelope-o"></span></th>
                        <th style="text-align: right">تلفن<span class="fa fa-phone"></span></th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr style="direction: ltr">
                        <th style="text-align: right">آی دی<span class="fa fa-id-badge"></span></th>
                        <th style="text-align: right">نام کاربر <span class="fa fa-user"></span></th>
                        <th style="text-align: right">عنوان<span class="fa fa-id-card-o"></span></th>
                        <th style="text-align: right">متن<span class="fa fa-id-card-o"></span></th>
                        <th style="text-align: right">جزئیات<span class="fa fa-cogs"></span></th>
                        <th style="text-align: right">ایمیل<span class="fa fa-envelope-o"></span></th>
                        <th style="text-align: right">تلفن<span class="fa fa-phone"></span></th>
                        {{--<th tabindex="10">&#1588;&#1605;&#1575;&#1585;&#1607;</th>--}}

                    </tr>
                    </tfoot>
                    <tbody>

                    @foreach($contact_us as $contact_us_sample)
                        <tr>
                            <td >{{$contact_us_sample->contact_us_id}}</td>
                            <td >{{$contact_us_sample->user_id}}</td>
                            <td >{{$contact_us_sample->title}}</td>
                            <td >{{$contact_us_sample->description}}</td>
                            <td >
                                <a class="btn btn_success add_message" data-toggle="modal" data-target="#myModal" contact_us_id="{{$contact_us_sample->contact_us_id}}" user_id="{{$contact_us_sample->user_id}}" href="#" ><span class="fa fa-reply fa-2"  title="پاسخ"></span></a>
                                <form style="float: left;" method="post" tag="2">
                                    <input type="hidden" name="_token"  value="{{csrf_token()}}">
                                    <input type="hidden" name="contact_us_id"  value="{{$contact_us_sample->contact_us_id}}">
                                    <input type="hidden" name="contact_us_guid"  value="{{$contact_us_sample->contact_us_guid}}">
                                    <button class="btn btn-danger fa fa-trash fa-2" type="submit"  title="بیخیال"></button>
                                </form>
                                </td>
                            <td >{{$contact_us_sample->email}}</td>
                            <td >{{$contact_us_sample->tel}}</td>
                            {{--<td >{{$contact_us_sample->user_name}}</td>--}}
                            </tr>
                    @endforeach
                    </tbody>

                </table>
            </div>
        </div>
    </div>
    <div class="col-lg-1"></div>
</div>


{{----}}








<script src="//cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js" type="text/javascript"></script>
{{--//code.jquery.com/jquery-1.12.4.js--}}
{{--https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js--}}
<script src="https://cdn.datatables.net/buttons/1.2.4/js/dataTables.buttons.min.js" type="text/javascript"></script>

<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.flash.min.js" type="text/javascript"></script>

<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js" type="text/javascript"></script>

{{--<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js" type="text/javascript"></script>--}}

{{--<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js" type="text/javascript"></script>--}}

<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js" type="text/javascript"></script>

<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.print.min.js" type="text/javascript"></script>

<script src="//cdn.datatables.net/responsive/2.1.1/js/dataTables.responsive.min.js" type="text/javascript"></script>

<script src="{{URL::to('dist/js/bootstrap-waitingfor.min.js')}}"></script>
<script>

    $(document).ready(function() {
        $('#example').DataTable( {

            "order": [[ 1, "desc" ],[ 1, "asc" ]],
            dom: 'Bfrtip',

            buttons: [
                {
                    extend: 'print',

                    exportOptions: {
                        columns: [ 0, 1, 2,3,4 ]
                    },
                    text:"@Lang('messages.lbl_Print')"
                },
                {
                    extend: 'excel',
                    exportOptions: {
                        columns: [ 0, 1, 2,3,4 ]
                    },
                    text:"@lang('messages.lbl_Excel')",
                },
                {
                    extend: 'copy',
                    exportOptions: {
                        columns: [ 0, 1, 2,3,4 ]
                    },
                    text:"@lang('messages.lbl_Copy')",
                }
            ],
            "pageLength": 25,
            "language": {
                "lengthMenu": "@lang('messages.lbl_lengthMenu')",
                "zeroRecords": "@lang('messages.lbl_zeroRecords')",
                "info": "@lang('messages.lbl_info')",
                "infoEmpty": "@lang('messages.lbl_infoEmpty')",
                "infoFiltered": "@lang('messages.lbl_infoFiltered')",
                "paginate": {
                    "first":      "@lang('messages.lbl_first')",
                    "last":       "@lang('messages.lbl_last')",
                    "next":       "@lang('messages.lbl_next')",
                    "previous":   "@lang('messages.lbl_previous')"
                },
                "decimal":        "@lang('messages.lbl_decimal')",
                "emptyTable":     "@lang('messages.lbl_emptyTable')",
                "infoPostFix":    "@lang('messages.lbl_infoPostFix')",
                "thousands":      "@lang('messages.lbl_thousands')",
                "loadingRecords": "@lang('messages.lbl_loadingRecords')",
                "processing":     "@lang('messages.lbl_processing')",
                "search":         "@lang('messages.lbl_search')",

            },
            responsive: {
                details: true
            },


        });

        var table = $('#example').DataTable();
// #myInput is a <input type="text"> element
        $("input[aria-controls='example']").on( 'keyup', function () {
            table.search( this.value ).draw();
        });

        $("form").on('submit', function(e) {
            waitingDialog.show('لطفا منتظر باشید', {dialogSize: 'sm', progressType: 'warning'});
            e.preventDefault();
            var myform;
            tag=$(this).attr('tag');
            var urlTo= "<?php echo URL::to('/'); ?>";
            var formData = new FormData(this);
            var url=urlTo;
            if(tag==1){
                url+='/saveMessageData';
            }else if(tag==2){
                url+='/deleteMessageData';
                myform=$(this);
            }else if(tag==3){
                url+='/getErrors';
                myform=$(this);
            }







            $.ajax({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                },
                url:url,
                dataType: 'json',
                type: 'POST',
                data:formData,
                cache : false,
                contentType: false,
                processData: false,

                success: function(data) {
                    $('#myModal').modal('hide');
                    if (data.success == true) {
                        if(tag==1){
                            showMessages("ارسال پیام","عملیات با موفقیت انجام شد",1,0);
                            $( 'a[contact_us_id="'+data.contact_us_id+'"]').parent().parent().hide();
                        }else if(tag==2){
                            showMessages("حذف پیام","عملیات با موفقیت انجام شد",1,0);
                            myform.parent().parent().hide();

//
//                            table = $('#example').DataTable();
//                            table.clear().draw();
                        }else if(tag==3) {
//                            showMessages("بازگشت اطلاعات", "عملیات با موفقیت انجام شد", 1, 0);

//                            $( "#example").empty();
                            table.rows().remove().draw();
//                            $("#example").append(
//                            '<thead class="noprint">'+
//                                '<tr style="direction: ltr">'+
//                                '<th style="text-align: right">آی دی گزارش<span class="fa fa-id-badge"></span></th>'+
//                                '<th style="text-align: right">آی دی کاربر<span class="fa fa-user"></span></th>'+
//                                '<th style="text-align: right">آی دی سوال<span class="fa fa-id-card-o"></span></th>'+
//                                '<th style="text-align: right">کد<span class="fa fa-id-card-o"></span></th>'+
//                                '<th style="text-align: right">توضیحات<span class="fa fa-cogs"></span></th>'+
//                                '<th style="text-align: right">کد جدول<span class="fa fa-envelope-o"></span></th>'+
//                                '<th style="text-align: right">تاریخ گزارش<span class="fa fa-phone"></span></th>'+
//                                '</tr>'+
//                            '</thead>'+
//                                '<tfoot>'+
//                                '<tr style="direction: ltr">'+
//                                '<th style="text-align: right">آی دی گزارش<span class="fa fa-id-badge"></span></th>'+
//                                '<th style="text-align: right">آی دی کاربر<span class="fa fa-user"></span></th>'+
//                                '<th style="text-align: right">آی دی سوال<span class="fa fa-id-card-o"></span></th>'+
//                                '<th style="text-align: right">کد<span class="fa fa-id-card-o"></span></th>'+
//                                '<th style="text-align: right">توضیحات<span class="fa fa-cogs"></span></th>'+
//                                '<th style="text-align: right">کد جدول<span class="fa fa-envelope-o"></span></th>'+
//                                '<th style="text-align: right">تاریخ گزارش<span class="fa fa-phone"></span></th>'+
//                                '</tr>'+
//                                '</tfoot>'+
//                                '<tbody>'+
//                                '</tbody>'
//                            );

//                            var table = $('#example').DataTable();
//                            table.rows().remove().draw();

//                            table.AcceptChanges();

                            for(var index=0;index<data.msgs.length;index++){
                                table.row.add([
                                    data.msgs[index]['user_question_report_id'],
                                    data.msgs[index]['user_id'],
                                    '<a class="btn btn-primary" target="_blank" href="{{URL::to("/edit_question/")}}/'+data.msgs[index]['question_id']+'">ویرایش سوال</a>',
                                    data.msgs[index]['code'],
                                    data.msgs[index]['description'],
                                    data.msgs[index]['table_code'],
                                    '<a href="#">ewweew</a>'
                                ]).draw( false );
                            }

//                            table.rows.add(  [data.msgs]).draw();
                        }
                    } else{
                        showMessages("خطا",'ارسال پیغام انجام نشد',0,0);
                    }

                },
                error : function(data) {
                    showMessages("خطا",'متاسفانه ارتباط با سرور برقرار نشد لطفا بعدا به طور مجدد تلاش کنید',0,0);
                },
                complete: function(){
                    waitingDialog.hide();
                }
            });

        });

        $(document).on('click', '.add_message', function(){

            $('#user_id').attr('value', $(this).attr('user_id'));
            $('#contact_us_id').attr('value', $(this).attr('contact_us_id'));

//            $('#AddRewardForm').attr('reward_guid', $(this).attr('reward_guid'));

//            $('#AddRewardYesNo').modal('show');
        });



    } );
    function showMessages(header,message,dangerOrSuccess,HasMessages) {
        var msg="";
        if(HasMessages==0){
            msg+="<p style='direction: rtl;text-align: right;margin-right: 15px'>"+message+"</p>";
        }else{
            $.each( message, function( key, value ) {
                msg+="<p style='direction: rtl;text-align: right;margin-right: 15px'>"+value+"</p>";
            });
        }
        if(dangerOrSuccess=="1"){
            $('#errorList').removeClass('alert-danger');
            $('#errorList').addClass('alert-success');
        }else{
            $('#errorList').removeClass('alert-success');
            $('#errorList').addClass('alert-danger');
        }
        $( "#errorList p" ).empty();
        $( "#errorList h4" ).empty();
        $( "#errorList h4" ).append(header);
        $("#errorList").append(msg);
        $('#showMessages').modal('show');
    }

</script>
</body>

</html>




