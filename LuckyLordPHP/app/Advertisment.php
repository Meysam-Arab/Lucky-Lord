<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:41 PM
 */

namespace App;
use File;

use Illuminate\Database\Eloquent\Model;

class Advertisment extends Model
{
//    protected $title='شرکت فردان';
//    protected $description='یک شرکت برنامه نویسی است';
//    protected $link="http://www.fardan7eghlim.ir";

    const AD_REWARD = 20;

    const AD_MAIN_COUNT = 15;
    const AD_QUESTION_LEVEL_COUNT=5;

    public static function getImage($filename)
    {
            $file = File::get(storage_path('app/mad/' . $filename.'.png'));
            return $file;
    }
}