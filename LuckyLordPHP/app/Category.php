<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 5/31/2017
 * Time: 7:30 PM
 */

namespace App;

use Illuminate\Database\Eloquent\Model;

class Category extends Model
{
    //public
    const HISTORY = 1;
    const POEM_AND_LITERATURE = 2;
    const ENGLISH = 3;
    const CULTURE_AND_ART = 4;
    const RELIGIOUS = 5;
    const GEOGRAPHY = 6;
    const TECHNOLOGY = 7;
    const MEDICAL = 8;
    const SPORTS = 9;
    const MATHEMATICAL_AND_ITELLIGENCE = 10;
    const MUSIC = 11;
    const PUBLIC_INFORMATION=12;
    const PUZZLE=13;

    const table=14;

    const Category_Count=13;

}