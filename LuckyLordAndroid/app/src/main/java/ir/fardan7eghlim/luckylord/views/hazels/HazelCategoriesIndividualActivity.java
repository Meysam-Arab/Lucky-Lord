package ir.fardan7eghlim.luckylord.views.hazels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.LuckyLordRecyclerViewAdapter;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchQuestionActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;
import spencerstudios.com.bungeelib.Bungee;

public class HazelCategoriesIndividualActivity extends BaseActivity implements Observer{
    private DialogModel DM;

    private Context cntx;

    private boolean inCreating;
    private boolean returningFromMatch;
    private Boolean isRandomCategoryDialogUp;
    public static String categoryId;
    private Integer randomCategoryId;
    private Boolean forRandomCategory;

    private UserController uc;
    private MatchController mc;
    private ImageView btn_come_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazel_categories_individual);

        inCreating = true;
        returningFromMatch = false;

        forRandomCategory = false;
        isRandomCategoryDialogUp = false;
        randomCategoryId = -1;
        cntx = this;

        categoryId = String.valueOf(CategoryModel.RANDOM);

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        mc = new MatchController(getApplicationContext());
        mc.addObserver(this);

        btn_come_down=findViewById(R.id.btn_come_down);
        Animation come_up_and_down= AnimationUtils.loadAnimation(HazelCategoriesIndividualActivity.this, R.anim.come_up_and_down);
        btn_come_down.setAnimation(come_up_and_down);
        btn_come_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView slist_of_cats_hc=findViewById(R.id.slist_of_cats_hc);
                slist_of_cats_hc.fullScroll(View.FOCUS_DOWN);
            }
        });

        if (!session.getBoolianItem(SessionModel.KEY_CATEGORIES_INDIVIDUAL_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

            DialogPopUpModel.show(HazelCategoriesIndividualActivity.this, getString(R.string.dlg_TutorialCategoriesIndividual), getString(R.string.btn_OK), null,false, false);
            session.saveItem(SessionModel.KEY_CATEGORIES_INDIVIDUAL_IS_FIRST_TIME, true);

        }
        else
        {
            randomCategoryId = -1;
            showRandomCategoryDialog();
        }

        setCategoriesClickListeners();


        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("categories_individual_activity_broadcast"));

        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        DM = new DialogModel(HazelCategoriesIndividualActivity.this);

        //ghati-pati
        final FrameLayout cat_2= (FrameLayout) findViewById(R.id.cat_2_hc);
        cat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DM.show();
                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                {
                    Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                    i.putExtra("Category_Id", QuestionModel.CategoryColorful);
                    HazelCategoriesIndividualActivity.this.startActivity(i);
                }
                else
                {
                    DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                }
                DM.hide();
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {

        DM.hide();

        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(HazelCategoriesIndividualActivity.this,MainActivity.class);
                    HazelCategoriesIndividualActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER))
                {

                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()) == true)
                    {
                        if(forRandomCategory)
                        {
                            // meysam - enable specific category...
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setCategoriesClickListeners();
                                }
                            });
                            forRandomCategory = false;
                        }
                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {
                    if(forRandomCategory)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new DialogModel_hazel(HazelCategoriesIndividualActivity.this).show(true, false, new Integer(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL)));
                                session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                                session.removeItem(SessionModel.KEY_FINAL_LUCK);

                                setCategoriesClickListeners();

                                forRandomCategory = false;

                            }
                        });
                    }


                }
            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
//                    SessionModel session = new SessionModel(getApplicationContext());
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    Utility.finishActivity(this);
                }else {
                    Utility.displayToast(getApplicationContext(),new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            }
            else
            {
                Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();


        String adZoneId;

        if(forRandomCategory)
        {
            adZoneId = AdvertismentModel.RandomCategoryZoneId;
        }
        else
        {
            adZoneId = AdvertismentModel.CategoriesPageZoneId;
        }

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(HazelCategoriesIndividualActivity.this,adZoneId , options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(HazelCategoriesIndividualActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesIndividualActivity.this).playCountinuseRandomMusic();

                forRandomCategory = false;
                randomCategoryId = -1;

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) HazelCategoriesIndividualActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(HazelCategoriesIndividualActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesIndividualActivity.this).playCountinuseRandomMusic();

                forRandomCategory = false;
                randomCategoryId = -1;

            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(HazelCategoriesIndividualActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesIndividualActivity.this).playCountinuseRandomMusic();

                forRandomCategory = false;
                randomCategoryId = -1;

            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(HazelCategoriesIndividualActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesIndividualActivity.this).playCountinuseRandomMusic();

                forRandomCategory = false;

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

                new SoundModel(HazelCategoriesIndividualActivity.this).playCountinuseRandomMusic();

                if(completed)
                {
                    if(forRandomCategory)
                    {
                        // meysam - enable random category for user to choose
                        setCategoriesClickListeners();
                    }

                }
                else
                {
                    Utility.displayToast(HazelCategoriesIndividualActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

                forRandomCategory = false;
            }
        });
    }

    private void showLoading()
    {
        Intent intent = new Intent("categories_individual_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(HazelCategoriesIndividualActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughHazelMessage()
    {
        Intent intent = new Intent("categories_individual_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_hazel", "true");
        LocalBroadcastManager.getInstance(HazelCategoriesIndividualActivity.this).sendBroadcast(intent);
    }

    private void setCategoriesClickListeners()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout fl_history = (FrameLayout) findViewById(R.id.cat_b_hc);
                FrameLayout fl_poem_and_literature = (FrameLayout) findViewById(R.id.cat_g_hc);
                FrameLayout fl_english = (FrameLayout) findViewById(R.id.cat_j_hc);
                FrameLayout fl_culture_and_art = (FrameLayout) findViewById(R.id.cat_k_hc);
                FrameLayout fl_religious = (FrameLayout) findViewById(R.id.cat_d_hc);
                FrameLayout fl_geography = (FrameLayout) findViewById(R.id.cat_e_hc);
                FrameLayout fl_technology = (FrameLayout) findViewById(R.id.cat_i_hc);
                FrameLayout fl_medical_and_health = (FrameLayout) findViewById(R.id.cat_l_hc);
                FrameLayout fl_sports = (FrameLayout) findViewById(R.id.cat_h_hc);
                FrameLayout fl_mathematical_and_intelligence = (FrameLayout) findViewById(R.id.cat_f_hc);
                FrameLayout fl_music = (FrameLayout) findViewById(R.id.cat_c_hc);
                FrameLayout fl_public_information = (FrameLayout) findViewById(R.id.cat_a_hc);
                FrameLayout fl_puzzle = (FrameLayout) findViewById(R.id.cat_z_hc);
                FrameLayout fl_table_shambles = (FrameLayout) findViewById(R.id.cat_x_hc);
                FrameLayout fl_table_cross = (FrameLayout) findViewById(R.id.cat_y_hc);
                FrameLayout fl_guess_word = (FrameLayout) findViewById(R.id.cat_x1_hc);
                FrameLayout fl_word_finder_single= (FrameLayout) findViewById(R.id.cat_y1_hc);

                final ImageView iv_history = (ImageView) findViewById(R.id.iv_cat_b_hc);
                final ImageView iv_poem_and_literature = (ImageView) findViewById(R.id.iv_cat_g_hc);
                ImageView iv_english = (ImageView) findViewById(R.id.iv_cat_j_hc);
                final ImageView iv_culture_and_art = (ImageView) findViewById(R.id.iv_cat_k_hc);
                final ImageView iv_religious = (ImageView) findViewById(R.id.iv_cat_d_hc);
                final ImageView iv_geography = (ImageView) findViewById(R.id.iv_cat_e_hc);
                final ImageView iv_technology = (ImageView) findViewById(R.id.iv_cat_i_hc);
                final ImageView iv_medical_and_health = (ImageView) findViewById(R.id.iv_cat_l_hc);
                final ImageView iv_sports = (ImageView) findViewById(R.id.iv_cat_h_hc);
                final ImageView iv_mathematical_and_intelligence = (ImageView) findViewById(R.id.iv_cat_f_hc);
                final ImageView iv_music = (ImageView) findViewById(R.id.iv_cat_c_hc);
                ImageView iv_public_information = (ImageView) findViewById(R.id.iv_cat_a_hc);



                // meysam - change cat pictures based on luck amount
                //here
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.MATHEMATICAL_AND_ITELLIGENCE) || randomCategoryId == CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)
                {
                    iv_mathematical_and_intelligence.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.MATHEMATICAL_AND_ITELLIGENCE),getApplicationContext()));
                }
                else
                {
                    iv_mathematical_and_intelligence.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.MATHEMATICAL_AND_ITELLIGENCE),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.POEM_AND_LITERATURE) || randomCategoryId == CategoryModel.POEM_AND_LITERATURE)
                {
                    iv_poem_and_literature.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.POEM_AND_LITERATURE),getApplicationContext()));

                }
                else
                {
                    iv_poem_and_literature.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.POEM_AND_LITERATURE),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.CULTURE_AND_ART) || randomCategoryId == CategoryModel.CULTURE_AND_ART)
                {
                    iv_culture_and_art.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.CULTURE_AND_ART),getApplicationContext()));

                }
                else
                {
                    iv_culture_and_art.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.CULTURE_AND_ART),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.SPORTS) || randomCategoryId == CategoryModel.SPORTS)
                {
                    iv_sports.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.SPORTS),getApplicationContext()));

                }
                else
                {
                    iv_sports.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.SPORTS),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.RELIGIOUS) || randomCategoryId == CategoryModel.RELIGIOUS)
                {
                    iv_religious.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.RELIGIOUS),getApplicationContext()));

                }
                else
                {
                    iv_religious.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.RELIGIOUS),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.HISTORY) || randomCategoryId == CategoryModel.HISTORY)
                {
                    iv_history.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.HISTORY),getApplicationContext()));

                }
                else
                {
                    iv_history.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.HISTORY),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.MEDICAL_AND_HEALTH) || randomCategoryId == CategoryModel.MEDICAL_AND_HEALTH)
                {
                    iv_medical_and_health.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.MEDICAL_AND_HEALTH),getApplicationContext()));

                }
                else
                {
                    iv_medical_and_health.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.MEDICAL_AND_HEALTH),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.MUSIC) || randomCategoryId == CategoryModel.MUSIC)
                {
                    iv_music.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.MUSIC),getApplicationContext()));

                }
                else
                {
                    iv_music.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.MUSIC),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.TECHNOLOGY) || randomCategoryId == CategoryModel.TECHNOLOGY)
                {
                    iv_technology.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.TECHNOLOGY),getApplicationContext()));

                }
                else
                {
                    iv_technology.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.TECHNOLOGY),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.GEOGRAPHY) || randomCategoryId == CategoryModel.GEOGRAPHY)
                {
                    iv_geography.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.GEOGRAPHY),getApplicationContext()));

                }
                else
                {
                    iv_geography.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.GEOGRAPHY),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.ENGLISH) || randomCategoryId == CategoryModel.ENGLISH)
                {
                    iv_english.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.ENGLISH),getApplicationContext()));

                }
                else
                {
                    iv_english.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.ENGLISH),getApplicationContext()));
                }
                if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.PUBLIC_INFORMATION) || randomCategoryId == CategoryModel.PUBLIC_INFORMATION)
                {
                    iv_public_information.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(String.valueOf(CategoryModel.PUBLIC_INFORMATION),getApplicationContext()));

                }
                else
                {
                    iv_public_information.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.PUBLIC_INFORMATION),getApplicationContext()));
                }
                ////////////////////////////////////////////////////////////

                fl_table_shambles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DM.show();
                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                        {
                            Intent i = new Intent(HazelCategoriesIndividualActivity.this,DarhamTableActivity.class);
                            HazelCategoriesIndividualActivity.this.startActivity(i);
                        }
                        else
                        {
                            DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                        }
                        DM.hide();

                    }
                });
                fl_table_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DM.show();
                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                        {
                            Intent i = new Intent(HazelCategoriesIndividualActivity.this,CrossTableActivity.class);
                            HazelCategoriesIndividualActivity.this.startActivity(i);
                        }
                        else
                        {
                            DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                        }
                        DM.hide();
                    }
                });

                fl_history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.HISTORY) || randomCategoryId == CategoryModel.HISTORY)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.HISTORY);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_history.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.HISTORY),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.HISTORY).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_poem_and_literature.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.POEM_AND_LITERATURE) || randomCategoryId == CategoryModel.POEM_AND_LITERATURE)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.POEM_AND_LITERATURE);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_poem_and_literature.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.POEM_AND_LITERATURE),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.POEM_AND_LITERATURE).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.ENGLISH) || randomCategoryId == CategoryModel.ENGLISH)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.ENGLISH);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.ENGLISH).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_culture_and_art.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.CULTURE_AND_ART) || randomCategoryId == CategoryModel.CULTURE_AND_ART)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.CULTURE_AND_ART);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_culture_and_art.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.CULTURE_AND_ART),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.CULTURE_AND_ART).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }                 }
                });
                fl_religious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.RELIGIOUS)  || randomCategoryId == CategoryModel.RELIGIOUS)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.RELIGIOUS);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_religious.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.RELIGIOUS),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.RELIGIOUS).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_geography.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.GEOGRAPHY) || randomCategoryId == CategoryModel.GEOGRAPHY)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.GEOGRAPHY);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_geography.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.GEOGRAPHY),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.GEOGRAPHY).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_technology.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.TECHNOLOGY)  || randomCategoryId == CategoryModel.TECHNOLOGY)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.TECHNOLOGY);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_technology.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.TECHNOLOGY),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.TECHNOLOGY).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_medical_and_health.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.MEDICAL_AND_HEALTH) || randomCategoryId == CategoryModel.MEDICAL_AND_HEALTH)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.MEDICAL);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_medical_and_health.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.MEDICAL_AND_HEALTH),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.MEDICAL_AND_HEALTH).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_sports.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.SPORTS) || randomCategoryId == CategoryModel.SPORTS)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.SPORTS);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_sports.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.SPORTS),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.SPORTS).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_mathematical_and_intelligence.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)|| randomCategoryId == CategoryModel.MATHEMATICAL_AND_ITELLIGENCE)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.MATHEMATICAL_AND_ITELLIGENCE);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_mathematical_and_intelligence.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.MATHEMATICAL_AND_ITELLIGENCE),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.MATHEMATICAL_AND_ITELLIGENCE).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_music.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.MUSIC) || randomCategoryId == CategoryModel.MUSIC)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.MUSIC);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();
                        }
                        else
                        {
                            iv_music.setImageBitmap(CategoryModel.getLockedImageBitmapByCategoryId(String.valueOf(CategoryModel.MUSIC),getApplicationContext()));
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.MUSIC).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }
                    }
                });
                fl_public_information.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Utility.calculateLevel(session.getCurrentUser().getLevelScore()) >= CategoryModel.getCategoryCost(CategoryModel.PUBLIC_INFORMATION) || randomCategoryId == CategoryModel.PUBLIC_INFORMATION)
                        {
                            DM.show();
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                            {
                                randomCategoryId = -1;
                                Intent i = new Intent(HazelCategoriesIndividualActivity.this,HazelLevelQuestionActivity.class);
                                i.putExtra("Category_Id", QuestionModel.PUBLIC_INFORMATION);
                                HazelCategoriesIndividualActivity.this.startActivity(i);
                            }
                            else
                            {
                                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                            }
                            DM.hide();

                        }
                        else
                        {
                            Utility.displayToast(HazelCategoriesIndividualActivity.this, Utility.enToFa(" سطح "+CategoryModel.getCategoryCost(CategoryModel.PUBLIC_INFORMATION).toString()+" لازمه "), Toast.LENGTH_SHORT);
                        }

                    }
                });
                fl_puzzle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utility.displayToast(HazelCategoriesIndividualActivity.this, getString(R.string.msg_InNewVersion), Toast.LENGTH_SHORT);
                    }
                });

                fl_word_finder_single.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DM.show();
                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                        {
                            Intent i = new Intent(HazelCategoriesIndividualActivity.this,FindWordSingleActivity.class);
                            HazelCategoriesIndividualActivity.this.startActivity(i);
                        }
                        else
                        {
                            DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                        }
                        DM.hide();

                    }
                });
                fl_guess_word.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DM.show();
                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                        {
                            Intent i = new Intent(HazelCategoriesIndividualActivity.this,GuessWordActivity.class);
                            HazelCategoriesIndividualActivity.this.startActivity(i);
                        }
                        else
                        {
                            DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                        }
                        DM.hide();

                    }
                });
            }
        });
    }


    @Override
    public void onPause() {

        super.onPause();
    }



    public void onDestroy() {

        if(mc != null)
        {
            mc.setCntx(null);
            mc.deleteObservers();
            mc = null;
        }

        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObservers();
            uc = null;
        }

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        Tapsell.setRewardListener(null);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();

        //check progressbar
        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS)<2){
            ProgressBar progressBar_01= (ProgressBar) findViewById(R.id.progressBar_01);
            ProgressBar progressBar_02= (ProgressBar) findViewById(R.id.progressBar_02);
            ProgressBar progressBar_03= (ProgressBar) findViewById(R.id.progressBar_03);
            ProgressBar progressBar_04= (ProgressBar) findViewById(R.id.progressBar_04);
//            ProgressBar progressBar_05= (ProgressBar) findViewById(R.id.progressBar_05);
            ProgressBar progressBar_06= (ProgressBar) findViewById(R.id.progressBar_06);
            ImageView iv_cat_x1_hc= (ImageView) findViewById(R.id.iv_cat_x1_hc);
            iv_cat_x1_hc.setImageResource(R.drawable.cat_guess_word_gray);
            ImageView iv_cat_y1_hc= (ImageView) findViewById(R.id.iv_cat_y1_hc);
            iv_cat_y1_hc.setImageResource(R.drawable.cat_word_finder_gray);
            ImageView iv_cat_x_hc= (ImageView) findViewById(R.id.iv_cat_x_hc);
            iv_cat_x_hc.setImageResource(R.drawable.cat_puzzle_table_01_gray);
            ImageView iv_cat_y_hc= (ImageView) findViewById(R.id.iv_cat_y_hc);
            iv_cat_y_hc.setImageResource(R.drawable.cat_puzzle_table_02_gray_02);
            ImageView iv_cat_2_hc= (ImageView) findViewById(R.id.iv_cat_2_hc);
            iv_cat_2_hc.setImageResource(R.drawable.cat_all_02_gray);

            progressBar_01.setVisibility(View.VISIBLE);
            progressBar_02.setVisibility(View.VISIBLE);
            progressBar_03.setVisibility(View.VISIBLE);
            progressBar_04.setVisibility(View.VISIBLE);
//            progressBar_05.setVisibility(View.VISIBLE);
            progressBar_06.setVisibility(View.VISIBLE);

            new DBTask().execute();

        }


        if(UniversalMatchQuestionActivity.MQA != null)
        {
            if(UniversalMatchQuestionActivity.MQA.customHandler != null)
            {
                UniversalMatchQuestionActivity.MQA.customHandler.removeCallbacksAndMessages(null);
            }
            UniversalMatchQuestionActivity.MQA = null;
        }

        if(!inCreating)
        {


            mc = new MatchController(getApplicationContext());
            mc.addObserver(this);

            uc = new UserController(getApplicationContext());
            uc.addObserver(this);
        }
        else
        {
            inCreating = false;
        }
//
//        if(inCreating)
//        {
//            randomCategoryId = -1;
//            inCreating = false;
//            showRandomCategoryDialog();
//        }
    }

    private void showRandomCategoryDialog()
    {

        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;

        if(randomInt < 10 && !isRandomCategoryDialogUp && session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1)
        {

            randomCategoryId = CategoryModel.getRandomCategory();

            if(CategoryModel.getCategoryCost(randomCategoryId) <= Utility.calculateLevel(session.getCurrentUser().getLevelScore()))
            {
                randomCategoryId = -1;
                return;
            }
            //////////////////////////////////////////
            isRandomCategoryDialogUp = true;

            DialogPopUpFourVerticalModel.show(HazelCategoriesIndividualActivity.this," میتونی دسته "+CategoryModel.getCategoryTitleById(randomCategoryId, cntx)+" رو یکبار باز کنی ",getString(R.string.btn_SeeAd), CategoryModel.getCategoryCost(randomCategoryId)+" تا فندق میدم ",null,getString(R.string.btn_No),true, false);
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        while(DialogPopUpFourVerticalModel.isUp()){
                            Thread.sleep(500);
                        }
                        if(!DialogPopUpFourVerticalModel.isUp()){
                            Thread.currentThread().interrupt();
                            if(DialogPopUpFourVerticalModel.dialog_result==1){
                                //show ad
                                showLoading();
                                forRandomCategory = true;
                                WatchAd();

                            }
                            else if(DialogPopUpFourVerticalModel.dialog_result==2)
                            {
                                //recieve hazel from user if have it
                                if(Utility.hasEnoughCoin(HazelCategoriesIndividualActivity.this,CategoryModel.getCategoryCost(randomCategoryId).intValue()))
                                {
                                    forRandomCategory = true;
                                    showLoading();
                                    Utility.changeCoins(getApplicationContext(),-1*CategoryModel.getCategoryCost(randomCategoryId).intValue());
                                    session.changeFinalHazel(-1*CategoryModel.getCategoryCost(randomCategoryId).intValue());
                                    uc.change(-1*CategoryModel.getCategoryCost(randomCategoryId).intValue(),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));

                                }
                                else
                                {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showNotEnoughHazelMessage();
                                        }
                                    });
                                }
                            }
                            else if(DialogPopUpFourVerticalModel.dialog_result==4)
                            {
                                //meysam - do nothing
                                randomCategoryId = -1;
                            }
                            else
                            {
                                //meysam - do nothing
                                randomCategoryId = -1;
                            }
                            isRandomCategoryDialogUp = false;
                            DialogPopUpFourVerticalModel.hide();

                        }
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if(intent.getStringExtra("loading") != null)
            {
                if(intent.getStringExtra("loading").equals("true"))
                {
                    DM.show();
                }
            }
            if(intent.getStringExtra("hide_loading") != null)
            {
                if(intent.getStringExtra("hide_loading").equals("true"))
                {
                    DM.hide();
                }
            }
            if(intent.getStringExtra("not_enough_hazel") != null)
            {
                if(intent.getStringExtra("not_enough_hazel").equals("true")) {

                    Utility.displayToast(HazelCategoriesIndividualActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);

                }
            }
            if(intent.getStringExtra("show_not_enough_hazel_dialog") != null)//meysam - added in 13960608
            {
                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,"برای بازی باید حداقل " + LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString() + " فندق داشته باشی!","باشه",null, false, false);

            }
            if(intent.getStringExtra("show_not_enough_luck_dialog") != null)//meysam - added in 13960608
            {
                DialogPopUpModel.show(HazelCategoriesIndividualActivity.this,"برای بازی باید حداقل " + LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString() + " شانس داشته باشی!","باشه",null, false, false);

            }

        }
    };


    class DBTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            while(true) {
                try {
                    Thread.sleep(5000);
                    if (session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) == 2) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ProgressBar progressBar_01 = (ProgressBar) findViewById(R.id.progressBar_01);
                                ProgressBar progressBar_02 = (ProgressBar) findViewById(R.id.progressBar_02);
                                ProgressBar progressBar_03 = (ProgressBar) findViewById(R.id.progressBar_03);
                                ProgressBar progressBar_04 = (ProgressBar) findViewById(R.id.progressBar_04);
//                                ProgressBar progressBar_05 = (ProgressBar) findViewById(R.id.progressBar_05);
                                ProgressBar progressBar_06 = (ProgressBar) findViewById(R.id.progressBar_06);
                                progressBar_01.setVisibility(View.GONE);
                                progressBar_02.setVisibility(View.GONE);
                                progressBar_03.setVisibility(View.GONE);
                                progressBar_04.setVisibility(View.GONE);
//                                progressBar_05.setVisibility(View.GONE);
                                progressBar_06.setVisibility(View.GONE);
                                ImageView iv_cat_x1_hc= (ImageView) findViewById(R.id.iv_cat_x1_hc);
                                iv_cat_x1_hc.setImageResource(R.drawable.cat_guess_word);
                                ImageView iv_cat_y1_hc= (ImageView) findViewById(R.id.iv_cat_y1_hc);
                                iv_cat_y1_hc.setImageResource(R.drawable.cat_word_finder);
                                ImageView iv_cat_x_hc= (ImageView) findViewById(R.id.iv_cat_x_hc);
                                iv_cat_x_hc.setImageResource(R.drawable.cat_puzzle_table_01);
                                ImageView iv_cat_y_hc= (ImageView) findViewById(R.id.iv_cat_y_hc);
                                iv_cat_y_hc.setImageResource(R.drawable.cat_puzzle_table_02);
                                ImageView iv_cat_2_hc= (ImageView) findViewById(R.id.iv_cat_2_hc);
                                iv_cat_2_hc.setImageResource(R.drawable.cat_all_02);
                            }
                        });
                        break;
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
                //Do Your stuff here..
                return null;
            }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.slideRight(this); //fire the slide left animation
    }
}
