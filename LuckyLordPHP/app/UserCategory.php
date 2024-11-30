<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 5/31/2017
 * Time: 7:40 PM
 */

namespace App;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Http\Request;

class UserCategory extends Model
{
    use Notifiable;
    use SoftDeletes;

    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'user_category';
    protected $primaryKey = 'user_category_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['total_answered_count', 'correct_answered_count','daily_total_answered_count'
        ,'daily_correct_answered_count', 'weekly_total_answered_count','weekly_correct_answered_count'];

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
        $this-> user_category_id = null;
        $this-> user_category_guid = null;
        $this-> user_id = null;
        $this-> category_id = null;

        $this-> total_answered_count = null;
        $this->correct_answered_count=null;

        $this->daily_total_answered_count = null;
        $this-> daily_correct_answered_count = null;

        $this->weekly_total_answered_count=null;
        $this->weekly_correct_answered_count = null;
    }


    public function intializeByRequest(Request $request)
    {
        $this -> user_category_id = $request ->input('user_category_id');
        $this -> user_category_guid = $request ->input('user_category_guid');
        $this -> user_id = $request ->input('user_id');
        $this -> category_id = $request ->input('category_id');

        $this -> total_answered_count = $request ->input('total_answered_count');
        $this -> correct_answered_count = $request ->input('correct_answered_count');

        $this -> daily_total_answered_count = $request ->input('daily_total_answered_count');
        $this -> daily_correct_answered_count = $request ->input('daily_correct_answered_count');

        $this -> weekly_total_answered_count = $request ->input('weekly_total_answered_count');
        $this -> weekly_correct_answered_count = $request ->input('weekly_correct_answered_count');
    }

    public function exist_user_id($user_id){
        $query = $this->newQuery();
        $query->where('user_id', '=', $user_id);
        $users = $query->get();
        if (count($users) == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public static function findBy_UserId_and_CategoryId($user_id,$category_id)
    {
        $userCat = new UserCategory();
        $query = $userCat->newQuery();
        $query->where('user_id', '=', $user_id);
        $query->where('category_id', '=', $category_id);
        $users = $query->get();

        return $users[0];
    }

    public function store()
    {
        $this->user_category_guid = uniqid('',true);
        $this->save();
    }
}