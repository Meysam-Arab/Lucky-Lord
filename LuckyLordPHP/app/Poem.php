<?php

namespace App;


use Carbon\Carbon;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;
use Log;
use DB;
use File;
use Hash;

class Poem extends Authenticatable
{
    use Notifiable;
    use SoftDeletes;

    protected $dates = ['deleted_at'];
    protected $table = 'poem';
    protected $primaryKey = 'poem_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'title', 'comment_count', 'poet_name'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    //these will not be in seassion...
    protected $hidden = [
    ];

    /**
     * Get all of the tasks for the user.
     */
    public function verse()
    {
        return $this->hasMany(Verse::class);
    }

    protected static function boot() {
        parent::boot();

        static::deleting(function($poem) {
            $poem->verse()->delete();
        });
    }


    public function initialize()
    {

        $this-> poem_id = null;
        $this-> poem_guid = null;
        $this-> title = null;
        $this-> comment_count = null;
        $this-> poet_name = null;
        $this->create_at = null;
        $this->update_at = null;
        $this->deleted_at = null;

    }

    public function initializeByRequest(Request $request)
    {
        $this-> poem_id = $request ->input('poem_id');
        $this-> poem_guid = $request ->input('poem_guid');
        $this-> title = $request ->input('title');
        $this-> comment_count = $request ->input('comment_count');
        $this-> poet_name = $request ->input('poet_name');
        $this->create_at = $request ->input('create_at');
        $this->update_at = $request ->input('update_at');
        $this->deleted_at = $request ->input('deleted_at');


    }

    public function store(){

        $this->poem_guid = uniqid('',true);
        $this->save();
        $poem_id = DB::table('poem')->where('poem_guid', $this->poem_guid)->value('poem_id');


        return $poem_id;
    }

}