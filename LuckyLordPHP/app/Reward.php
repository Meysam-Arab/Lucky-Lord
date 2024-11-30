<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 3:40 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;

class Reward extends Model
{
    protected $dates = ['deleted_at'];
    protected $table = 'reward';
    protected $primaryKey = 'reward_id';
}