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

class Verse extends Authenticatable
{
    use Notifiable;
    use SoftDeletes;

    protected $dates = ['deleted_at'];
    protected $table = 'verse';
    protected $primaryKey = 'verse_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'poem_id', 'first_hemistich', 'second_hemistich','hemistich_number'
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
    public function Poem()
    {
        return $this->belongsTo(Poem::class);
    }


    public function initialize()
    {

        $this-> verse_id = null;
        $this-> verse_guid = null;
        $this-> poem_id = null;
        $this-> first_hemistich = null;
        $this-> second_hemistich = null;
        $this-> verse_number = null;
        $this->create_at = null;
        $this->update_at = null;
        $this->deleted_at = null;

    }

    public function initializeByRequest(Request $request)
    {
        $this-> verse_id = $request ->input('verse_id');
        $this-> verse_guid = $request ->input('verse_guid');
        $this-> poem_id = $request ->input('poem_id');
        $this-> first_hemistich = $request ->input('first_hemistich');
        $this-> second_hemistich = $request ->input('second_hemistich');
        $this-> verse_number = $request ->input('verse_number');
        $this->create_at = $request ->input('create_at');
        $this->update_at = $request ->input('update_at');
        $this->deleted_at = $request ->input('deleted_at');


    }

    public function store(){

        $this->verse_guid = uniqid('',true);
        $this->save();
        $verse_id = DB::table('verse')->where('verse_guid', $this->verse_guid)->value('verse_id');


        return $verse_id;
    }

}