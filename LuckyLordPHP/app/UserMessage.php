<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:36 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;
use Log;

class UserMessage extends Model
{
    use SoftDeletes;
    //
    protected $table = 'user_message';
    protected $primaryKey = 'user_message_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;
    protected $softDelete = true;


    protected $fillable = ['is_readed'];

    public function intialize()
    {
        $this -> user_message_id = null;
        $this -> user_message_guid = null;
        $this -> user_id = null;
        $this -> message_id = null;
        $this -> is_readed = null;
    }

    public function intializeByRequest(Request $request)
    {
        $this -> user_message_id = $request ->input('user_message_id');
        $this -> user_message_guid = $request ->input('user_message_guid');
        $this -> user_id = $request ->input('user_id');
        $this -> message_id = $request ->input('message_id');
        $this -> is_readed = $request ->input('is_readed');

    }

    public function destroy_user_message(){
        self::findByMessageId($this->message_id,$this->user_id)->delete();
    }

    public function remove(){
        $user_message = UserMessage::find($this->user_message_id);
        $user_message->delete();
    }

    public function findByMessageId($message_id,$user_id)
    {
        $query = $this->newQuery();
        $query->where('message_id', '=', $message_id);
        $query->where('user_id', '=', $user_id);
        $UserMessages = $query->get();
        return $UserMessages[0];
    }

    public function store()
    {
        $this->user_message_guid = uniqid('',true);
        $this->save();

    }

    public function Message()
    {
        return $this->belongsTo(Message::class);
    }

    public function User()
    {
        return $this->belongsTo(User::class);
    }
}