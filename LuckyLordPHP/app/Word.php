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
use DB;

class Word extends Model
{
    const CATEGORY_NAME_BOY = 1;
    const CATEGORY_NAME_GIRL = 2;
    const CATEGORY_NAME_TRANS = 3;
    const CATEGORY_FRUIT = 4;
    const CATEGORY_COLOR = 5;
    const CATEGORY_VEGETABLE = 6;
    const CATEGORY_FLOWER = 7;
    const CATEGORY_LEGUME = 8;//meysam - حبوبات
    const CATEGORY_GRAIN = 9;//meysam - غلات

    protected $table = 'words';
    protected $primaryKey = 'word_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;


    protected $fillable = ['value,category'];


    public function intialize()
    {
        $this -> word_id = null;
        $this -> word_guid = null;
        $this -> word_value = null;
        $this -> word_category = null;
        $this -> all_letters = null;
        $this -> answer_letters_positions = null;

    }

    public function intializeByRequest(Request $request)
    {
        $this -> word_id =  $request ->input('word_id');
        $this -> word_guid =  $request ->input('word_guid');
        $this -> word_value =  $request ->input('word_value');
        $this -> word_category =  $request ->input('word_category');
    }
}