<?php
namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;
use Log;
use DB;
class UserUniversalMatchStatistics extends Model
{
    use SoftDeletes;

    protected $table = 'user_universal_match_statistics';
    protected $primaryKey = 'user_universal_match_statistics_id';
//    protected $dates = ['deleted_at'];
    public $timestamps = true;



    public function intialize()
    {
        $this -> user_universal_match_statistic_id = null;
        $this -> user_universal_match_statistic_guid = null;
        $this -> user_id = null;
        $this -> universal_match_type = null;
        $this -> win_count = null;
        $this -> lost_count = null;
        $this -> equal_count = null;
    }

    public function intializeByRequest(Request $request)
    {

        $this -> user_universal_match_statistic_id = $request ->input('user_universal_match_statistic_id');
        $this -> user_universal_match_statistic_guid = $request ->input('user_universal_match_statistic_guid');
        $this -> user_id = $request ->input('user_id');
        $this -> universal_match_type = $request ->input('universal_match_type');
        $this -> win_count = $request ->input('win_count');
        $this -> lost_count = $request ->input('lost_count');
        $this -> equal_count = $request ->input('equal_count');

    }


    public function User()
    {
        return $this->belongsTo(User::class);
    }

    public function store()
    {
        $this->user_universal_match_statistic_guid = uniqid('',true);
        $this->save();

    }
}