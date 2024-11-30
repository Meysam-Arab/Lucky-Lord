<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:39 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Log;
use File;
use DB;
use App\Question;
use Illuminate\Http\Request;


class Question_Puzzle extends Model
{
    use SoftDeletes;
    //
    protected $table = 'question_puzzle';

    protected $primaryKey = 'question_puzzle_id';

    protected $dates = ['deleted_at'];

    public $timestamps = true;

    protected $fillable = ['description','answer'];


    public static function likesOrhatesQuestion($question_puzzle_id, $rate)
    {
        $Question = Question_Puzzle::findById($question_puzzle_id);
        if($rate==Question::LIKED)
            $Question->likes ++;
        else
            $Question->hates ++;
        $Question->save();
    }

    public static function findById($id)
    {
        $Question = new Question_Puzzle();
        $query = $Question->newQuery();
        $query->where('question_puzzle_id', '=', $id);
        $Questions = $query->get();

        return $Questions[0];
    }
}