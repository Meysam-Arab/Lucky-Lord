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

class BodyPart extends Model
{
    use Notifiable;
    use SoftDeletes;

    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'body_part_cost';
    protected $primaryKey = 'body_part_cost_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['title', 'cost'];

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
        $this-> body_part_cost_id = null;
        $this-> body_part_cost_guid = null;
        $this-> title = null;
        $this-> cost = null;
    }

    public function initializeByRequest(Request $request)
    {
        $this-> body_part_cost_id = $request ->input('body_part_cost_id');
        $this-> body_part_cost_guid = $request ->input('body_part_cost_guid');
        $this-> title = $request ->input('title');
        $this-> cost = $request ->input('cost');

    }
    public function store()
    {
        $this->body_part_cost_guid = uniqid('', true);
        $this-> cost = 500;
        $this->save();
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

        $bodyPart = $query->get();
        return $bodyPart;
    }

    public function makeWhere($query){
        if($this->body_part_cost_id != null){
            $query->where('	draw.'.'body_part_cost_id', '=', $this->body_part_cost_id);
        }
        if($this->body_part_cost_guid != null){
            $query->where('	draw.'.'body_part_cost_guid', '=', $this->body_part_cost_guid);
        }
        if($this->title != null){
            $query->where('	draw.'.'title', 'like', $this->title);
        }
        if($this->cost != null){
            $query->where('	draw.'.'cost', 'like', $this->cost);
        }

        return $query;
    }
}