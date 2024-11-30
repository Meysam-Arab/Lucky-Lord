<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:40 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Log;
use Illuminate\Http\Request;

class UserBodyPart extends Model
{
    use Notifiable;
    use SoftDeletes;

    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'user_body_part';
    protected $primaryKey = 'user_body_part_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['body_part_id', 'user_id'];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    //these will not be in seassion...
    protected $hidden = [
        'created_at', 'updated_at', 'deleted_at',
    ];

    public function initialize()
    {
        $this-> user_body_part_id = null;
        $this-> user_body_part_guid = null;
        $this-> body_part_id = null;
        $this-> user_id = null;
    }
    public function intializeByRequest(Request $request)
    {
        $this -> user_body_part_id = $request ->input('user_body_part_id');
        $this -> user_body_part_guid = $request ->input('user_body_part_guid');
        $this -> body_part_id = $request ->input('body_part_id');
        $this -> user_id = $request ->input('user_id');

    }

    public function store(){

        $this->user_body_part_guid = uniqid('',true);

        $this->save();
    }

}