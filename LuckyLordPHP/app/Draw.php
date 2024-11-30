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
use File;

class Draw extends Model
{
    use Notifiable;
    use SoftDeletes;

    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'draw';
    protected $primaryKey = 'draw_id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['draw_date_time', 'cost','sponser'];

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
        $this-> draw_id = null;
        $this-> draw_guid = null;
        $this-> draw_date_time = null;
        $this-> cost = null;
        $this-> sponser = null;
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
        if($this->draw_id != null){
            $query->where('	draw.'.'draw_id', '=', $this->draw_id);
        }
        if($this->draw_guid != null){
            $query->where('	draw.'.'draw_guid', '=', $this->draw_guid);
        }
        if($this->draw_date_time != null){
            $query->where('	draw.'.'draw_date_time', 'like', $this->draw_date_time);
        }
        if($this->cost != null){
            $query->where('	draw.'.'cost', 'like', $this->cost);
        }
        if($this->sponser != null){
            $query->where('	draw.'.'sponser', '=', $this->sponser);
        }

        return $query;
    }

    public function getSponserImage($draw_guid){
        //retern previous name of companylogo
        $destinationPath = storage_path().'/app/draw';
        $files1 = scandir($destinationPath);
        $nameOfFile="";
        $search =$draw_guid;
        $search_length = strlen($search);
        foreach ($files1 as $key => $value) {
            if (substr($value, 0, $search_length) == $search) {
                $nameOfFile=$value;
                break;
            }
        }
//        return Image::make(storage_path('question/' . $nameOfFile))->response();
//        delete previou logo
        if($nameOfFile!=null)
            return base64_encode(File::get(storage_path('/app/draw/'.$nameOfFile)));
//            return File::get(storage_path().'question/'.$nameOfFile);
        else
            return null;
    }
}