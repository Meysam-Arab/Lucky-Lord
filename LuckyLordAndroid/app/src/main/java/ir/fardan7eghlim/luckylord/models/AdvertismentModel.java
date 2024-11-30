package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ir.fardan7eghlim.luckylord.interfaces.AdvertismentInterface;
import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class AdvertismentModel implements AdvertismentInterface {

    public static final int  Top= 1;
    public static final int  Botton= 0;

    public static String tapsellAppKey = "stbmmrqimsklktihskpjaioedsinbenhftjqqbfhmdhgloamipjgjjdtgfrjfrnaalgqpq";

    public static String EggZoneId = "5960d1744684654da381291d";
    public static String HomePageZoneId = "5960d2564684654da381675d";
    public static String CategoriesPageZoneId = "598ffecc4684654242903b3d";
    public static String QuestionPageZoneId = "59674504468465521bd1ce64";
    public static String RandomMatchPageZoneId = "598ac67c4684650e1b034c05";
    public static String TangledTablePageZoneId = "59ca004f4684653f2580ca1f";
    public static String RandomRewardZoneId = "5a119f85dc93ee000150d93c";
    public static String RandomCategoryZoneId = "5a127b4edc93ee0001603f5e";
    public static String GuessWordPageZoneId = "5a13ef7259196f000165bd3f";
    public static String FindWordPageZoneId = "5a13efb059196f000165c23a";
    public static String CrossTablePageZoneId = "5a2ce98c39086d0001b49861";
    public static String ProfilePageZoneId = "5a6d9f7d73916d00016cb09f";
    public static String ChatPageZoneId = "5a6da86073916d0001e304b8";
    public static String MatchResultPageZoneId = "5ac49fd13daef00001673cf4";




//    public static String AD_REWARD = "20";

    public static Integer DefaultAdMainAllowedCount = 5;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    private String Title;
    private String Description;
    private String Link;


    private Context cntx;


    public AdvertismentModel()
    {
        Title = null;
        Description = null;
        Link = null;

        this.cntx = null;
    }

    public AdvertismentModel(Context cntx)
    {
        Title = null;
        Description = null;
        Link = null;

        this.cntx = cntx;

    }

    public void insert()
    {
        try
        {

        }
        catch (Exception ex)
        {

        }
    }
    public void update()
    {

    }
    public boolean delete(){

        return false;
    }

    /////// meysam - for ad film management

    public static void setAllowedAdCount( Context cntx, Integer count, String allowedCountKey)
    {
        SessionModel session = new SessionModel(cntx);
        session.setAllowedFilmCount(count,allowedCountKey);
    }

    public static Integer getAllowedAdCount( Context cntx, String spentCountKey)
    {
        SessionModel session = new SessionModel(cntx);
        return session.getAllowedFilmCount(spentCountKey);
    }

    public static Integer getTodaySpentAd(Context cntx, String spentCountKey)
    {
        SessionModel session = new SessionModel(cntx);
        return session.getIntegerItem(spentCountKey);
    }

    public static void increaseTodaySpentAd(Context cntx, Integer amount, String spentCountKey)
    {
        SessionModel session = new SessionModel(cntx);
        session.saveSpentedAd(amount, spentCountKey);
    }

    public static void resetTodaySpentAd(Context cntx, String spentCountKey)
    {
        SessionModel session = new SessionModel(cntx);
        session.removeItem(spentCountKey);
        session.saveSpentedAd(0,spentCountKey);
    }

    public static void setTodayAdDate(Context cntx, String dateKey)
    {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        SessionModel session = new SessionModel(cntx);
        session.removeItem(dateKey);
        session.saveItem(dateKey, dateFormat.format(date));
    }

    public static String getTodayAdDate(Context cntx, String dateKey)
    {
        SessionModel session = new SessionModel(cntx);
        return session.getStringItem(dateKey);
    }

    public static boolean isTodayAdDateCorrect(Context cntx, String dateKey)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String today = dateFormat.format(date);
//        SessionModel session = new SessionModel(cntx);
        if(today.equals(getTodayAdDate(cntx, dateKey)))
            return true;
        else
            return false;
    }

    //main ad management
    public static boolean haveMainAdRemained(Context cntx)
    {
        if(isTodayAdDateCorrect(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME))
        {
            if(getTodaySpentAd(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT) >= getAllowedAdCount(cntx, SessionModel.KEY_AD_FILM_MAIN_ALLOWED_COUNT))
                return false;
            else
                return true;
        }
        else
        {
            setTodayAdDate(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME);
            resetTodaySpentAd(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
            return true;
        }
    }

    public static void adMainCountAndDateInitialization(Context cntx, Integer newCount)
    {
//        if(!isTodayAdDateCorrect(cntx,SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME))
//        {
            resetTodaySpentAd(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
            setAllowedAdCount(cntx, newCount, SessionModel.KEY_AD_FILM_MAIN_ALLOWED_COUNT);
            setTodayAdDate(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME);

//        }
    }


    public static Integer getRemainedMainAd(Context cntx)
    {
        if(!isTodayAdDateCorrect(cntx,SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME))
        {
            resetTodaySpentAd(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
            setTodayAdDate(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME);

            return getAllowedAdCount(cntx, SessionModel.KEY_AD_FILM_MAIN_ALLOWED_COUNT);

        }
        else
        {
            return (getAllowedAdCount(cntx, SessionModel.KEY_AD_FILM_MAIN_ALLOWED_COUNT) - getTodaySpentAd(cntx, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT));
        }

    }
    ////////////////////////////////////////////////////////////////////////////////////

}