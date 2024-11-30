package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.StateListDrawable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.interfaces.CategoryInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class CategoryModel implements CategoryInterface {

    //public
    public static int HISTORY = 1;
    public static int POEM_AND_LITERATURE = 2;
    public static int ENGLISH = 3;
    public static int CULTURE_AND_ART = 4;
    public static int RELIGIOUS = 5;
    public static int GEOGRAPHY = 6;
    public static int TECHNOLOGY = 7;
    public static int MEDICAL_AND_HEALTH = 8;
    public static int SPORTS = 9;
    public static int MATHEMATICAL_AND_ITELLIGENCE = 10;
    public static int MUSIC = 11;
    public static int PUBLIC_INFORMATION=12;
    public static int PUZZLE=13;
//    public static int  SINAMA=13;

    public static int RANDOM=-1;
    public static int SHAMBLES = 0;

    public static int Category_Count=13;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTotalAnsweredCount() {
        return TotalAnsweredCount;
    }

    public void setTotalAnsweredCount(Integer totalAnsweredCount) {
        TotalAnsweredCount = totalAnsweredCount;
    }

    public Integer getCorrectAnsweredCount() {
        return CorrectAnsweredCount;
    }

    public void setCorrectAnsweredCount(Integer correctAnsweredCount) {
        CorrectAnsweredCount = correctAnsweredCount;
    }

    public Integer getDailyTotalAnsweredCount() {
        return DailyTotalAnsweredCount;
    }

    public void setDailyTotalAnsweredCount(Integer dailyTotalAnsweredCount) {
        DailyTotalAnsweredCount = dailyTotalAnsweredCount;
    }

    public Integer getDailyCorrectAnsweredCount() {
        return DailyCorrectAnsweredCount;
    }

    public void setDailyCorrectAnsweredCount(Integer dailyCorrectAnsweredCount) {
        DailyCorrectAnsweredCount = dailyCorrectAnsweredCount;
    }

    public Integer getWeeklyTotalAnsweredCount() {
        return WeeklyTotalAnsweredCount;
    }

    public void setWeeklyTotalAnsweredCount(Integer weeklyTotalAnsweredCount) {
        WeeklyTotalAnsweredCount = weeklyTotalAnsweredCount;
    }

    public Integer getWeeklyCorrectAnsweredCount() {
        return WeeklyCorrectAnsweredCount;
    }

    public void setWeeklyCorrectAnsweredCount(Integer weeklyCorrectAnsweredCount) {
        WeeklyCorrectAnsweredCount = weeklyCorrectAnsweredCount;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    private Integer Id;
    private Integer TotalAnsweredCount;
    private Integer CorrectAnsweredCount;
    private Integer DailyTotalAnsweredCount;
    private Integer DailyCorrectAnsweredCount;
    private Integer WeeklyTotalAnsweredCount;
    private Integer WeeklyCorrectAnsweredCount;
    private String CreatedAt;
    private String UpdatedAt;


    private Context cntx;


    public CategoryModel()
    {
        Id = null;
        TotalAnsweredCount = null;
        CorrectAnsweredCount = null;
        DailyTotalAnsweredCount = null;
        DailyCorrectAnsweredCount = null;
        WeeklyTotalAnsweredCount = null;
        WeeklyCorrectAnsweredCount = null;
        CreatedAt = null;
        UpdatedAt = null;

        this.cntx = null;
    }

    public CategoryModel(Context cntx)
    {
        Id = null;
        TotalAnsweredCount = null;
        CorrectAnsweredCount = null;
        DailyTotalAnsweredCount = null;
        DailyCorrectAnsweredCount = null;
        WeeklyTotalAnsweredCount = null;
        WeeklyCorrectAnsweredCount = null;
        CreatedAt = null;
        UpdatedAt = null;

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

    public static String getCategoryTitleById(Integer categoryId, Context cntx)
    {
        if(categoryId.intValue() == CategoryModel.MEDICAL_AND_HEALTH)
            return cntx.getString(R.string.tlt_category_medical_and_health);
        if(categoryId.intValue() == CategoryModel.CULTURE_AND_ART)
            return cntx.getString(R.string.tlt_category_culture_and_art);
        if(categoryId.intValue() == CategoryModel.SPORTS)
            return cntx.getString(R.string.tlt_category_sports);
        if(categoryId.intValue() == CategoryModel.GEOGRAPHY)
            return cntx.getString(R.string.tlt_category_geography);
        if(categoryId.intValue() == CategoryModel.HISTORY)
            return cntx.getString(R.string.tlt_category_history);
        if(categoryId.intValue() == CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)
            return cntx.getString(R.string.tlt_category_mathematical_and_intelligence);
        if(categoryId.intValue() == CategoryModel.MUSIC)
            return cntx.getString(R.string.tlt_category_music);
        if(categoryId.intValue() == CategoryModel.POEM_AND_LITERATURE)
            return cntx.getString(R.string.tlt_category_poem_and_literature);
        if(categoryId.intValue() == CategoryModel.PUBLIC_INFORMATION)
            return cntx.getString(R.string.tlt_category_public_information);
        if(categoryId.intValue() == CategoryModel.TECHNOLOGY)
            return cntx.getString(R.string.tlt_category_technology);
        if(categoryId.intValue() == CategoryModel.ENGLISH)
            return cntx.getString(R.string.tlt_category_english);
        if(categoryId.intValue() == CategoryModel.RELIGIOUS)
            return cntx.getString(R.string.tlt_category_religious);
        if(categoryId.intValue() == CategoryModel.PUZZLE)
            return cntx.getString(R.string.tlt_category_puzzle);
        return null;

    }

    public static Bitmap getImageBitmapByCategoryId(String categoryIdstr, Context cntx) {


        Integer categoryId = Integer.valueOf(categoryIdstr);

        if(categoryId.intValue() == CategoryModel.MEDICAL_AND_HEALTH)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_medicate);
        if(categoryId.intValue() == CategoryModel.CULTURE_AND_ART)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_art);
        if(categoryId.intValue() == CategoryModel.TECHNOLOGY)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_tech);
        if(categoryId.intValue() == CategoryModel.GEOGRAPHY)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_geography);
        if(categoryId.intValue() == CategoryModel.HISTORY)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_history);
        if(categoryId.intValue() == CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_math);
        if(categoryId.intValue() == CategoryModel.MUSIC)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_music);
        if(categoryId.intValue() == CategoryModel.POEM_AND_LITERATURE)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_poem);
        if(categoryId.intValue() == CategoryModel.PUBLIC_INFORMATION)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_information);
        if(categoryId.intValue() == CategoryModel.SPORTS)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_sport);
        if(categoryId.intValue() == CategoryModel.ENGLISH)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_english);
        if(categoryId.intValue() == CategoryModel.RELIGIOUS)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_religious);
        if(categoryId.intValue() == CategoryModel.SHAMBLES)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_all);
        if(categoryId.intValue() == CategoryModel.PUZZLE)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_puzzle);
        return null;
    }

    public static Bitmap getLockedImageBitmapByCategoryId(String categoryIdstr, Context cntx) {


        Integer categoryId = Integer.valueOf(categoryIdstr);

        if(categoryId.intValue() == CategoryModel.MEDICAL_AND_HEALTH)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_medicate_gray);
        if(categoryId.intValue() == CategoryModel.CULTURE_AND_ART)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_art_gray);
        if(categoryId.intValue() == CategoryModel.TECHNOLOGY)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_tech_gray);
        if(categoryId.intValue() == CategoryModel.GEOGRAPHY)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_geography_gray);
        if(categoryId.intValue() == CategoryModel.HISTORY)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_history_gray);
        if(categoryId.intValue() == CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_math_gray);
        if(categoryId.intValue() == CategoryModel.MUSIC)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_music_gray);
        if(categoryId.intValue() == CategoryModel.POEM_AND_LITERATURE)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_poem_gray);
        if(categoryId.intValue() == CategoryModel.PUBLIC_INFORMATION)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_information_gray);
        if(categoryId.intValue() == CategoryModel.SPORTS)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_sport_gray);
        if(categoryId.intValue() == CategoryModel.ENGLISH)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_english_gray);
        if(categoryId.intValue() == CategoryModel.RELIGIOUS)
            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_religious_gray);
//        if(categoryId.intValue() == CategoryModel.SHAMBLES)
//            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_all_gray);
//        if(categoryId.intValue() == CategoryModel.PUZZLE)
//            return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.cat_puzzle_gray);
        return null;
    }

    public static Integer getRandomCategory()
    {
        return new Random().nextInt(Category_Count-1)+1;
    }

    public static Integer getCategoryCost(Integer categoryId)
    {
        if(categoryId.intValue() == CategoryModel.MEDICAL_AND_HEALTH)
            return 80;
        if(categoryId.intValue() == CategoryModel.CULTURE_AND_ART)
            return 40;
        if(categoryId.intValue() == CategoryModel.TECHNOLOGY)
            return 100;
        if(categoryId.intValue() == CategoryModel.GEOGRAPHY)
            return 110;
        if(categoryId.intValue() == CategoryModel.HISTORY)
            return 70;
        if(categoryId.intValue() == CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)
            return 20;
        if(categoryId.intValue() == CategoryModel.MUSIC)
            return 90;
        if(categoryId.intValue() == CategoryModel.POEM_AND_LITERATURE)
            return 30;
        if(categoryId.intValue() == CategoryModel.PUBLIC_INFORMATION)
            return 130;
        if(categoryId.intValue() == CategoryModel.SPORTS)
            return 50;
        if(categoryId.intValue() == CategoryModel.ENGLISH)
            return 120;
        if(categoryId.intValue() == CategoryModel.RELIGIOUS)
            return 60;

        return -1;
    }

    public static int[] getThreeRandomCategory()
    {
        final Random random = new Random();
        final Set<Integer> intSet = new HashSet<>();
        while (intSet.size() < 3) {
            intSet.add(random.nextInt(Category_Count-1) + 1);
        }
        final int[] ints = new int[intSet.size()];
        final Iterator<Integer> iter = intSet.iterator();
        for (int i = 0; iter.hasNext(); ++i) {
            ints[i] = iter.next();
        }

        ints[1] = SHAMBLES;

        return ints;
    }
}