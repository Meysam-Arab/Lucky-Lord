<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 6/11/2017
 * Time: 6:35 PM
 */

namespace App;


class HazelCost
{
    //public
    const HAZEL_1500 = "1500 فندق ";
    const HAZEL_3300 = "3300 فندق ";
    const HAZEL_8700 = "8700 فندق ";
    const HAZEL_18000 = "18000 فندق ";
    const HAZEL_42000 = "42000 فندق ";
    const HAZEL_75000 = "75000 فندق ";

    const HAZEL_TO_LUCK_200="هر 1 شانس "."\n"." 200 تا فندق ";
    const HAZEL_TO_LUCK_2000="هر 12 شانس "."\n"." 2000 تا فندق ";
//    const HAZEL_75000 = "75000 فندق "."\n"." 30000 تومان";

    const HAZEL_1500_COST = 1000;
    const HAZEL_3300_COST = 2000;
    const HAZEL_8700_COST = 5000;
    const HAZEL_18000_COST = 10000;
    const HAZEL_42000_COST = 20000;
    const HAZEL_75000_COST = 30000;

    const HAZEL_TO_LUCK_200_COST=200;
    const HAZEL_TO_LUCK_2000_COST=2000;

    const HAZEL_1500_DESCRIPTION = " هزار تومن بده و هزار و پونصد تا فندق بگیر ";
    const HAZEL_3300_DESCRIPTION = " دو هزار تومن بده و سه هزار و سیصد تا فندق بگیر ";
    const HAZEL_8700_DESCRIPTION = " پنج هزار تومن بده و هشت هزار و هفتصد تا فندق بگیر ";
    const HAZEL_18000_DESCRIPTION = " ده هزار تومن بده و هجده هزار تا فندق بگیر ";
    const HAZEL_42000_DESCRIPTION = " بیست هزار تومن بده و چهل و دو هزار تا فندق بگیر ";
    const HAZEL_75000_DESCRIPTION = " سی هزار تومن بده و هفتاد و پنج هزار تا فندق بگیر ";

    const HAZEL_TO_LUCK_200_DESCRIPTION = " دویست تا فندق بده و یه شانس بگیر ";
    const HAZEL_TO_LUCK_2000_DESCRIPTION = " دو هزار تا فندق بده و 12 تا شانس بگیر ";

    const HAZEL_1500_AMOUNT = 1500;
    const HAZEL_3300_AMOUNT = 3300;
    const HAZEL_8700_AMOUNT = 8700;
    const HAZEL_18000_AMOUNT = 18000;
    const HAZEL_42000_AMOUNT = 42000;
    const HAZEL_75000_AMOUNT = 75000;

    const HAZEL_TO_LUCK_200_AMOUNT=1;
    const HAZEL_TO_LUCK_2000_AMOUNT=12;

    const hz_1500 = "HAZEL_1500";
    const hz_3300 = "HAZEL_3300";
    const hz_8700 = "HAZEL_8700";
    const hz_18000 = "HAZEL_18000";
    const hz_42000 = "HAZEL_42000";
    const hz_75000 = "HAZEL_75000";

    const hz_lc_200 = "hz_lc_200";
    const hz_lc_2000 = "hz_lc_2000";

    const HAZEL_TO_LUCK_200_hazel_decrease=-200;
    const HAZEL_TO_LUCK_200_luck_increase=1;

    const HAZEL_TO_LUCK_2000_hazel_decrease=-2000;
    const HAZEL_TO_LUCK_2000_luck_increase=12;

    public static function getAmount($productCode)
    {
        switch ($productCode)
        {
            case self::hz_1500:
                return "1000";
                break;
            case self::hz_3300:
                return "2000";
                break;
            case self::hz_8700:
                return "5000";
                break;
            case self::hz_18000:
                return "10000";
                break;
            case self::hz_42000:
                return "20000";
                break;
            case self::hz_75000:
                return "30000";
                break;
                break;

            default:
                return  "0";
                break;
        }
    }

}