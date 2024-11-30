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

class UserDraw extends Model
{
    use SoftDeletes;
    //
    protected $table = 'user_draw';
    protected $primaryKey = 'user_draw_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;

    protected $fillable = ['is_readed'];

    public function intialize()
    {
        $this -> user_draw_id = null;
        $this -> user_draw_guid = null;
        $this -> user_id = null;
        $this -> draw_id = null;
        $this -> reward_id = null;
    }

    public function intializeByRequest(Request $request)
    {
        $this -> user_draw_id = $request ->input('user_draw_id');
        $this -> user_draw_guid = $request ->input('user_draw_guid');
        $this -> user_id = $request ->input('user_id');
        $this -> draw_id = $request ->input('draw_id');
        $this -> reward_id = $request ->input('reward_id');

    }

    public function store(){

        $this->user_draw_guid = uniqid('',true);

        $this->save();
    }
}