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
    <div>
        <label>استان سری اول</label>
        <select class="province">
        </select>
        <label>شهر سری اول</label>
        <select class="city">
        </select>
    </div>

    <div>
        <label>استان سری دوم</label>
        <select class="province">
        </select>
        <label>شهر سری دوم</label>
        <select class="city">
        </select>
    </div>
</div>
    <?php
        $IranCities=[
            "آذربایجان‌شرقی"=>["آذرشهر","اسکو","اهر","بستان‌آباد","بناب","تبریز","جلفا","چاراویماق","سراب","شبستر","عجب‌شیر","کلیبر","مراغه","مرند","ملکان","میانه","ورزقان","هریس","هشترود"],
            "آذربایجان‌غربی"=>["ارومیه","اشنویه","بوکان","پیرانشهر","تکاب","چالدران","خوی","سردشت","سلماس","شاهین‌دژ","ماکو","مهاباد","میاندوآب","نقده"],
            "اردبیل"=>["اردبیل","بیله‌سوار","پارس‌آباد","خلخال","کوثر","گرمی","مشگین شهر","نمین","نیر"],
            "اصفهان"=>["آران و بیدگل","اردستان","اصفهان","برخوردار و میمه","تیران و کرون","چادگان","خمینی‌شهر","خوانسار","سمیرم","شهرضا","سمیرم سفلی","فریدن","فریدون‌شهر","فلاورجان","کاشان","گلپایگان","لنجان","مبارکه","نائین","نجف‌آباد","نطنز"],
            "البرز"=>["ساوجبلاغ","طالقان","کرج","نظرآباد"],
            "ایلام"=>["آبدانان","ایلام","ایوان","دره‌شهر","دهلران","شیروان و چرداول","مهران"],
            "بوشهر"=>["بوشهر","تنگستان","جم","دشتستان","دشتی","دیر","دیلم","کنگان","گناوه"],
            "تهران"=>["ورامین","فیروزکوه","شهریار","شمیرانات","ری","رباط‌کریم","دماوند","تهران","پاکدشت","اسلام‌شهر"],
            "چهارمحال‌و‌بختیاری"=>["اردل","بروجن","شهرکرد","فارسان","کوهرنگ","لردگان"],
            "خراسان‌جنوبی"=>["بیرجند","درمیان","سرایان","سربیشه","فردوس","قائنات","نهبندان"],
            "خراسان‌رضوی"=>["بردسکن","تایباد","تربت جام","تربت حیدریه","چناران","خلیل‌آباد","خواف","درگز","رشتخوار","سبزوار","سرخس","فریمان","قوچان","کاشمر","کلات","گناباد","مشهد","مه ولات","نیشابور"],
            "خراسان‌شمالی"=>["اسفراین","بجنورد","جاجرم","شیروان","فاروج","مانه و سملقان"],
            "خوزستان"=>["آبادان","امیدیه","اندیمشک","اهواز","ایذه","باغ‌ملک","بندر ماهشهر","بهبهان","خرمشهر","دزفول","دشت آزادگان","رامشیر","رامهرمز","شادگان","شوش","شوشتر","گتوند","لالی","مسجد سلیمان","هندیجان"],
            "زنجان"=>["ابهر","ایجرود","خدابنده","خرمدره","زنجان","طارم","ماه‌نشان"],
            "سمنان"=>["دامغان","سمنان","شاهرود","گرمسار","مهدی‌شهر"],
            "سیستان‌و‌بلوچستان"=>["ایرانشهر","چابهار","خاش","دلگان","زابل","زاهدان","زهک","سراوان","سرباز","کنارک","نیک‌شهر"],
            "فارس"=>["آباده","ارسنجان","استهبان","اقلید","بوانات","پاسارگاد","جهرم","خرم‌بید","خنج","داراب","زرین‌دشت","سپیدان","شیراز","فراشبند","فسا","فیروزآباد","قیر و کارزین","کازرون","لارستان","لامرد","مرودشت","ممسنی","مهر","نی‌ریز"],
            "قزوین"=>["آبیک","البرز","بوئین‌زهرا","تاکستان","قزوین"],
            "قم"=>["قم"],
            "کردستان"=>["بانه","بیجار","دیواندره","سروآباد","سقز","سنندج","قروه","کامیاران","مریوان"],
            "کرمان"=>["بافت","بردسیر","بم","جیرفت","راور","رفسنجان","رودبار جنوب","زرند","سیرجان","شهر بابک","عنبرآباد","قلعه گنج","کرمان","کوهبنان","کهنوج","منوجان"],
            "کرمانشاه"=>["اسلام‌آباد غرب","پاوه","ثلاث باباجانی","جوانرود","دالاهو","روانسر","سرپل ذهاب","سنقر","صحنه","قصر شیرین","کرمانشاه","کنگاور","گیلان غرب","هرسین"],
            "کهکیلویه‌و‌بویراحمد"=>["بویراحمد","بهمئی","دنا","کهگیلویه","گچساران"],
            "گلستان"=>["آزادشهر","آق‌قلا","بندر گز","ترکمن","رامیان","علی‌آباد","کردکوی","کلاله","گرگان","گنبد کاووس","مراوه‌تپه","مینودشت"],
            "گیلان"=>["آستارا","آستانه","اشرفیه","املش","بندر انزلی","رشت","رضوانشهر","رودبار","رودسر","سیاهکل","شفت","صومعه‌سرا","طوالش","فومن","لاهیجان","لنگرود","ماسال"],
            "لرستان"=>["ازنا","الیگودرز","بروجرد","پل‌دختر","خرم‌آباد","دورود","دلفان","سلسله","کوهدشت"],
            "مازندران"=>["آمل","بابل","بابلسر","بهشهر","تنکابن","جویبار","چالوس","رامسر","ساری","سوادکوه","قائم‌شهر","گلوگاه","محمودآباد","نکا","نور","نوشهر"],
            "مرکزی"=>["آشتیان","اراک","تفرش","خمین","دلیجان","زرندیه","ساوه","شازند","کمیجان","محلات"],
            "هرمزگان"=>["ابوموسی","بستک","بندر عباس","بندر لنگه","جاسک","حاجی‌آباد","خمیر","رودان","قشم","گاوبندی","میناب"],
            "همدان"=>["اسدآباد","بهار","تویسرکان","رزن","کبودرآهنگ","ملایر","نهاوند","همدان"],
            "یزد"=>["ابرکوه","اردکان","بافق","تفت","خاتم","صدوق","طبس","مهریز","میبد","یزد"],
        ];



    //    echo $IranCities['اردبیل'][3];
    ?>

<script>
    $(document).ready(function(){
        loadprovince();
        $(".province").change(function(){
            $(this).closest('div').find('.city').addClass( "temp09120217432class" );
            loadCity($(this).val());
        });
    });

    function loadprovince() {
        selectValues = {"":"","آذربایجان‌شرقی":"آذربایجان شرقی","آذربایجان‌غربی":"آذربایجان غربی","اردبیل":"اردبیل","اصفهان":"اصفهان","البرز":"البرز","ایلام":"ایلام",
            "بوشهر":"بوشهر","تهران":"تهران","چهارمحال‌و‌بختیاری":"چهارمحال و بختیاری","خراسان‌جنوبی":"خراسان جنوبی","خراسان‌رضوی":"خراسان رضوی","خراسان‌شمالی":"خراسان شمالی","خوزستان":"خوزستان",
            "زنجان":"زنجان","سمنان":"سمنان","سیستان‌و‌بلوچستان":"سیستان و بلوچستان","فارس":"فارس","قزوین":"قزوین","قم":"قم","کردستان":"کردستان",
            "کرمان":"کرمان","کرمانشاه":"کرمانشاه","کهکیلویه‌و‌بویراحمد":"کهگیلویه و بویراحمد","گلستان":"گلستان","گیلان":"گیلان","لرستان":"لرستان","مازندران":"مازندران",
            "مرکزی":"مرکزی","هرمزگان":"هرمزگان","همدان":"همدان","یزد":"یزد"};

        $.each(selectValues, function (key, value) {
            $('.province')
                .append($("<option></option>")
                    .attr("value", key)
                    .text(value));
        });
    }

    //Load city for selete
    function loadCity(province){
        $(".temp09120217432class").find('option').remove();

        switch (province) {
            case "آذربایجان‌شرقی":
                var selectValues = {"آذرشهر":"آذرشهر","اسکو":"اسکو","اهر":"اهر","بستان‌آباد":"بستان‌آباد","بناب":"بناب","تبریز":"تبریز","جلفا":"جلفا","چاراویماق":"چاراویماق","سراب":"سراب","شبستر":"شبستر","عجب‌شیر":"عجب‌شیر","کلیبر":"کلیبر","مراغه":"مراغه","مرند":"مرند","ملکان":"ملکان","میانه":"میانه","ورزقان":"ورزقان","هریس":"هریس","هشترود":"هشترود"};
                break;
            case "آذربایجان‌غربی":
                var selectValues = {"ارومیه":"ارومیه","اشنویه":"اشنویه","بوکان":"بوکان","پیرانشهر":"پیرانشهر","تکاب":"تکاب","چالدران":"چالدران","خوی":"خوی","سردشت":"سردشت","سلماس":"سلماس","شاهین‌دژ":"شاهین‌دژ","ماکو":"ماکو","مهاباد":"مهاباد","میاندوآب":"میاندوآب","نقده":"نقده"};
                break;
            case "اردبیل":
                var selectValues = {"اردبیل":"اردبیل","بیله‌سوار":"بیله‌سوار","پارس‌آباد":"پارس‌آباد","خلخال":"خلخال","کوثر":"کوثر","گرمی":"گرمی","مشگین شهر":"مشگین شهر","نمین":"نمین","نیر":"نیر"};
                break;
            case "اصفهان":
                var selectValues = {"آران و بیدگل":"آران و بیدگل","اردستان":"اردستان","اصفهان":"اصفهان","برخوردار و میمه":"برخوردار و میمه","تیران و کرون":"تیران و کرون","چادگان":"چادگان","خمینی‌شهر":"خمینی‌شهر","خوانسار":"خوانسار","سمیرم":"سمیرم","شهرضا":"شهرضا","سمیرم سفلی":"سمیرم سفلی","فریدن":"فریدن","فریدون‌شهر":"فریدون‌شهر","فلاورجان":"فلاورجان","کاشان":"کاشان","گلپایگان":"گلپایگان","لنجان":"لنجان","مبارکه":"مبارکه","نائین":"نائین","نجف‌آباد":"نجف‌آباد","نطنز":"نطنز"};
                break;
            case "البرز":
                var selectValues = {"ساوجبلاغ":"ساوجبلاغ","طالقان":"طالقان","کرج":"کرج","نظرآباد":"نظرآباد"};
                break;
            case "ایلام":
                var selectValues = {"آبدانان":"آبدانان","ایلام":"ایلام","ایوان":"ایوان","دره‌شهر":"دره‌شهر","دهلران":"دهلران","شیروان و چرداول":"شیروان و چرداول","مهران":"مهران"};
                break;
            case "بوشهر":
                var selectValues = {"بوشهر":"بوشهر","تنگستان":"تنگستان","جم":"جم","دشتستان":"دشتستان","دشتی":"دشتی","دیر":"دیر","دیلم":"دیلم","کنگان":"کنگان","گناوه":"گناوه"};
                break;
            case "تهران":
                var selectValues = {"ورامین":"ورامین","فیروزکوه":"فیروزکوه","شهریار":"شهریار","شمیرانات":"شمیرانات","ری":"ری","رباط‌کریم":"رباط‌کریم","دماوند":"دماوند","تهران":"تهران","پاکدشت":"پاکدشت","اسلام‌شهر":"اسلام‌شهر"};
                break;
            case "چهارمحال‌و‌بختیاری":
                var selectValues = {"اردل":"اردل","بروجن":"بروجن","شهرکرد":"شهرکرد","فارسان":"فارسان","کوهرنگ":"کوهرنگ","لردگان":"لردگان"};
                break;
            case "خراسان‌جنوبی":
                var selectValues = {"بیرجند":"بیرجند","درمیان":"درمیان","سرایان":"سرایان","سربیشه":"سربیشه","فردوس":"فردوس","قائنات":"قائنات","نهبندان":"نهبندان"};
                break;
            case "خراسان‌رضوی":
                var selectValues = {"بردسکن":"بردسکن","تایباد":"تایباد","تربت جام":"تربت جام","تربت حیدریه":"تربت حیدریه","چناران":"چناران","خلیل‌آباد":"خلیل‌آباد","خواف":"خواف","درگز":"درگز","رشتخوار":"رشتخوار","سبزوار":"سبزوار","سرخس":"سرخس","فریمان":"فریمان","قوچان":"قوچان","کاشمر":"کاشمر","کلات":"کلات","گناباد":"گناباد","مشهد":"مشهد","مه ولات":"مه ولات","نیشابور":"نیشابور"};
                break;
            case "خراسان‌شمالی":
                var selectValues = {"اسفراین":"اسفراین","بجنورد":"بجنورد","جاجرم":"جاجرم","شیروان":"شیروان","فاروج":"فاروج","مانه و سملقان":"مانه و سملقان"};
                break;
            case "خوزستان":
                var selectValues = {"آبادان":"آبادان","امیدیه":"امیدیه","اندیمشک":"اندیمشک","اهواز":"اهواز","ایذه":"ایذه","باغ‌ملک":"باغ‌ملک","بندر ماهشهر":"بندر ماهشهر","بهبهان":"بهبهان","خرمشهر":"خرمشهر","دزفول":"دزفول","دشت آزادگان":"دشت آزادگان","رامشیر":"رامشیر","رامهرمز":"رامهرمز","شادگان":"شادگان","شوش":"شوش","شوشتر":"شوشتر","گتوند":"گتوند","لالی":"لالی","مسجد سلیمان":"مسجد سلیمان","هندیجان":"هندیجان"};
                break;
            case "زنجان":
                var selectValues = {"ابهر":"ابهر","ایجرود":"ایجرود","خدابنده":"خدابنده","خرمدره":"خرمدره","زنجان":"زنجان","طارم":"طارم","ماه‌نشان":"ماه‌نشان"};
                break;
            case "سمنان":
                var selectValues = {"دامغان":"دامغان","سمنان":"سمنان","شاهرود":"شاهرود","گرمسار":"گرمسار","مهدی‌شهر":"مهدی‌شهر"};
                break;
            case "سیستان‌و‌بلوچستان":
                var selectValues = {"ایرانشهر":"ایرانشهر","چابهار":"چابهار","خاش":"خاش","دلگان":"دلگان","زابل":"زابل","زاهدان":"زاهدان","زهک":"زهک","سراوان":"سراوان","سرباز":"سرباز","کنارک":"کنارک","نیک‌شهر":"نیک‌شهر"};
                break;
            case "فارس":
                var selectValues = {"آباده":"آباده","ارسنجان":"ارسنجان","استهبان":"استهبان","اقلید":"اقلید","بوانات":"بوانات","پاسارگاد":"پاسارگاد","جهرم":"جهرم","خرم‌بید":"خرم‌بید","خنج":"خنج","داراب":"داراب","زرین‌دشت":"زرین‌دشت","سپیدان":"سپیدان","شیراز":"شیراز","فراشبند":"فراشبند","فسا":"فسا","فیروزآباد":"فیروزآباد","قیر و کارزین":"قیر و کارزین","کازرون":"کازرون","لارستان":"لارستان","لامرد":"لامرد","مرودشت":"مرودشت","ممسنی":"ممسنی","مهر":"مهر","نی‌ریز":"نی‌ریز"};
                break;
            case "قزوین":
                var selectValues = {"آبیک":"آبیک","البرز":"البرز","بوئین‌زهرا":"بوئین‌زهرا","تاکستان":"تاکستان","قزوین":"قزوین"};
                break;
            case "قم":
                var selectValues = {"قم":"قم"};
                break;
            case "کردستان":
                var selectValues = {"بانه":"بانه","بیجار":"بیجار","دیواندره":"دیواندره","سروآباد":"سروآباد","سقز":"سقز","سنندج":"سنندج","قروه":"قروه","کامیاران":"کامیاران","مریوان":"مریوان"};
                break;
            case "کرمان":
                var selectValues = {"بافت":"بافت","بردسیر":"بردسیر","بم":"بم","جیرفت":"جیرفت","راور":"راور","رفسنجان":"رفسنجان","رودبار جنوب":"رودبار جنوب","زرند":"زرند","سیرجان":"سیرجان","شهر بابک":"شهر بابک","عنبرآباد":"عنبرآباد","قلعه گنج":"قلعه گنج","کرمان":"کرمان","کوهبنان":"کوهبنان","کهنوج":"کهنوج","منوجان":"منوجان"};
                break;
            case "کرمانشاه":
                var selectValues = {"اسلام‌آباد غرب":"اسلام‌آباد غرب","پاوه":"پاوه","ثلاث باباجانی":"ثلاث باباجانی","جوانرود":"جوانرود","دالاهو":"دالاهو","روانسر":"روانسر","سرپل ذهاب":"سرپل ذهاب","سنقر":"سنقر","صحنه":"صحنه","قصر شیرین":"قصر شیرین","کرمانشاه":"کرمانشاه","کنگاور":"کنگاور","گیلان غرب":"گیلان غرب","هرسین":"هرسین"};
                break;
            case "کهکیلویه‌و‌بویراحمد":
                var selectValues = {"بویراحمد":"بویراحمد","بهمئی":"بهمئی","دنا":"دنا","کهگیلویه":"کهگیلویه","گچساران":"گچساران"};
                break;
            case "گلستان":
                var selectValues = {"آزادشهر":"آزادشهر","آق‌قلا":"آق‌قلا","بندر گز":"بندر گز","ترکمن":"ترکمن","رامیان":"رامیان","علی‌آباد":"علی‌آباد","کردکوی":"کردکوی","کلاله":"کلاله","گرگان":"گرگان","گنبد کاووس":"گنبد کاووس","مراوه‌تپه":"مراوه‌تپه","مینودشت":"مینودشت"};
                break;
            case "گیلان":
                var selectValues = {"آستارا":"آستارا","آستانه":"آستانه","اشرفیه":"اشرفیه","املش":"املش","بندر انزلی":"بندر انزلی","رشت":"رشت","رضوانشهر":"رضوانشهر","رودبار":"رودبار","رودسر":"رودسر","سیاهکل":"سیاهکل","شفت":"شفت","صومعه‌سرا":"صومعه‌سرا","طوالش":"طوالش","فومن":"فومن","لاهیجان":"لاهیجان","لنگرود":"لنگرود","ماسال":"ماسال"};
                break;
            case "لرستان":
                var selectValues = {"ازنا":"ازنا","الیگودرز":"الیگودرز","بروجرد":"بروجرد","پل‌دختر":"پل‌دختر","خرم‌آباد":"خرم‌آباد","دورود":"دورود","دلفان":"دلفان","سلسله":"سلسله","کوهدشت":"کوهدشت"};
                break;
            case "مازندران":
                var selectValues = {"آمل":"آمل","بابل":"بابل","بابلسر":"بابلسر","بهشهر":"بهشهر","تنکابن":"تنکابن","جویبار":"جویبار","چالوس":"چالوس","رامسر":"رامسر","ساری":"ساری","سوادکوه":"سوادکوه","قائم‌شهر":"قائم‌شهر","گلوگاه":"گلوگاه","محمودآباد":"محمودآباد","نکا":"نکا","نور":"نور","نوشهر":"نوشهر"};
                break;
            case "مرکزی":
                var selectValues = {"آشتیان":"آشتیان","اراک":"اراک","تفرش":"تفرش","خمین":"خمین","دلیجان":"دلیجان","زرندیه":"زرندیه","ساوه":"ساوه","شازند":"شازند","کمیجان":"کمیجان","محلات":"محلات"};
                break;
            case "هرمزگان":
                var selectValues = {"ابوموسی":"ابوموسی","بستک":"بستک","بندر عباس":"بندر عباس","بندر لنگه":"بندر لنگه","جاسک":"جاسک","حاجی‌آباد":"حاجی‌آباد","خمیر":"خمیر","رودان":"رودان","قشم":"قشم","گاوبندی":"گاوبندی","میناب":"میناب"};
                break;
            case "همدان":
                var selectValues = {"اسدآباد":"اسدآباد","بهار":"بهار","تویسرکان":"تویسرکان","رزن":"رزن","کبودرآهنگ":"کبودرآهنگ","ملایر":"ملایر","نهاوند":"نهاوند","همدان":"همدان"};
                break;
            case "یزد":
                var selectValues = {"ابرکوه":"ابرکوه","اردکان":"اردکان","بافق":"بافق","تفت":"تفت","خاتم":"خاتم","صدوق":"صدوق","طبس":"طبس","مهریز":"مهریز","میبد":"میبد","یزد":"یزد"};
                break;
            case "":
                var selectValues = {"":""}

        }


        $.each( selectValues , function (key, value) {
            $(".temp09120217432class")
                .append($("<option></option>")
                    .attr("value", key)
                    .text(value));
        });
        $(".temp09120217432class").removeClass("temp09120217432class");
    }
</script>

</body>
</html>