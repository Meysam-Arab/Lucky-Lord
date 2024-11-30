<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:32 PM
 */
namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Log;

class Message extends Model
{
    use Notifiable;
    use SoftDeletes;

    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'message';
    protected $primaryKey = 'message_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['title', 'description','user_id'];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    //these will not be in seassion...
    protected $hidden = [
        'created_at', 'updated_at', 'deleted_at',
    ];

    ///static attributes
    const MESSSAGE_1=1;
    const MESSSAGE_1_DESC='به مناسبت ارتقا برنامه به ورژن دوم به همه ی بازیکنان 50 فندق جایزه داده می شود. باشد که رستگار شوید';
    const MESSSAGE_1_TITLE='جایزه به مناسبت ورژن دوم برنامه';

    const MESSSAGE_2=2;
    const MESSSAGE_2_DESC='20 درصد تخفیف ویژه فروش فندق به مناسبت دهه فجر. بدو بیا که تموم نشه . فرصت محدود می باشد';
    const MESSSAGE_2_TITLE='تخفیف ویژه فروش فندق';

    const MESSSAGE_3=3;
    const MESSSAGE_3_DESC='تبریک! تعداد 200 فندق بابت تولدتان به شما تعلق گرفت.';
    const MESSSAGE_3_TITLE='جایزه به مناسبت تولد شما';

    const MESSSAGE_4=4;
    const MESSSAGE_4_DESC='تبریک! تعداد 500 فندق بابت ورود شماره موبایلتان به شما تعلق گرفت.';
    const MESSSAGE_4_TITLE='جایزه به مناسبت ورود شماره موبایل';

    const MESSSAGE_5=5;
    const MESSSAGE_5_DESC='کاربر گرامی 1500 فندق بابت دعوت از دوستتان به شما تعلق گرفت .';
    const MESSSAGE_5_TITLE='جایزه بابت دعوت دوستان';

    const MESSSAGE_6=6;
    const MESSSAGE_6_DESC='کاربر گرامی 500 فندق بابت ثبت نام با کد دعت دوستتان به شما تعلق گرفت .';
    const MESSSAGE_6_TITLE='جایزه بابت ثبت نام با کد دعوت';

    const MESSSAGE_7=7;
    const MESSSAGE_7_DESC='کاربر گرامی 1000 فندق برای تولدتان به شما تعلق گرفت';
    const MESSSAGE_7_TITLE='تولدت مبارک';

    const MESSSAGE_8=8;
    const MESSSAGE_8_DESC='سلام، به لاکی لرد خوش اومدی!! سوال جواب بده و مسابقه بده و فندق و شانس ببر و در قرعه کشی ها شرکت کن و جوایز نفیس برنده شو!!!';
    const MESSSAGE_8_TITLE='خوش اومدی';

    const MESSSAGE_9=9;
    const MESSSAGE_9_DESC='کاربر گرامی شما به ما درخواست تغییر رمز دادید و ما لینکی به ایمیل شما ارسال کردیم که با کلیک روی اون رمز شما به رمز جدید تغییر پیدا میکنه. درصورتی که این درخواست از جانب شما نبوده میتوانید این پیام رو نادیده بگیرید.';
    const MESSSAGE_9_TITLE='درخواست رمز عبور جدید';

    const MESSSAGE_10=10;
    const MESSSAGE_10_DESC='دوست عزیز اگه ثبت نام کنی علاوه بر این که فندق بیشتری گیرت میاد اکانتتم ایمن کردی';
    const MESSSAGE_10_TITLE='ثبت نام';

    public function initialize()
    {
        $this-> message_id = null;
        $this-> message_guid = null;
        $this-> user_id = null;
        $this-> title = null;
        $this-> description = null;
        $this->deleted_at = null;
    }
    public function intializeByRequest(Request $request)
    {

        $this -> message_id = $request ->input('message_id');
        $this -> message_guid = $request ->input('message_guid');
        $this -> user_id = $request ->input('user_id');
        $this -> title = $request ->input('title');
        $this -> description = $request ->input('description');
        $this -> deleted_at = $request ->input('deleted_at');

    }

    public function store()
    {
        $this->message_guid = uniqid('',true);
        $this->save();
    }


    public static function exist($Id,$guid)
    {
        $message = new Question();
        $query = $message->newQuery();
        $query->where('message_id', '=', $Id);
        $query->where('message_guid', 'like', $guid);
        $messages = $query->get();
        if (count($messages) == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public function getFullDetail( $params1,$params2,$params3)
    {

        $query = $this->newQuery();

        if($params1!=null) {

            $query=\App\Utility::fillQueryAlias($query,$params1);
        }
        $query =Self::makeWhere($query);

        //
        if($params2!=null) {
            $query=\App\Utility::fillQueryJoin($query,$params2);

        }
        //filtering
        if($params3!=null) {
            $query=\App\Utility::fillQueryFilter($query,$params3);
        }
        $messages = $query->get();
        return $messages;
    }

    public function makeWhere($query){
        if($this->message_id != null){
            $query->where('	message.'.'message_id', '=', $this->message_id);
        }
        if($this->message_guid != null){
            $query->where('	message.'.'message_guid', '=', $this->message_guid);
        }
        if($this->title != null){
            $query->where('	message.'.'title', 'like', $this->title);
        }
        if($this->description != null){
            $query->where('	message.'.'description', '=', $this->description);
        }

        return $query;
    }

    public function User()
    {
        return $this->belongsTo(User::class);
    }



}
