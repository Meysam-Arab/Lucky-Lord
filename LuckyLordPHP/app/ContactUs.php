<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:41 PM
 */

namespace App;

use Carbon\Carbon;
use Illuminate\Http\Request;
use DB;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Database\Eloquent\Model;

class ContactUs extends Model
{
    use SoftDeletes;
    //
    protected $table = 'contact_us';
    protected $primaryKey = 'contact_us_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;


    protected $fillable = ['title','description','email','tel'];

    public function intialize()
    {
            $this -> contact_us_id = null;
            $this -> contact_us_guid = null;
            $this -> user_id = null;
            $this -> title = null;
            $this -> description = null;
            $this -> email = null;
            $this -> tel = null;

    }

    public function intializeByRequest(Request $request)
    {

            $this -> contact_us_id = $request ->input('contact_us_id');
            $this -> contact_us_guid = $request ->input('contact_us_guid');
            $this -> user_id = $request ->input('user_id');;
            $this -> title = $request ->input('title');
            $this -> description = $request ->input('description');
            $this -> email = $request ->input('email');
            $this -> tel = $request ->input('tel');

    }

    public function store()
    {
        $this->contact_us_guid = uniqid('',true);
        $this->save();

    }
}