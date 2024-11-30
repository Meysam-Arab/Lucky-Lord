package ir.fardan7eghlim.luckylord.views.match;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.HomeController;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.BetModel;
import ir.fardan7eghlim.luckylord.models.MixedTableModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.WordModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextViewAutoSize;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_cup;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_level;
import ir.fardan7eghlim.luckylord.utils.DialogModel_luck;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.ReportDialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.SoundFXModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.CrossTableActivity;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesActivity;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.message.ChatActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.fardan7eghlim.luckylord.views.user.UserProfileActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;
import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

public class UniversalMatchCrossTableActivity extends BaseActivity implements Observer {


    public static UniversalMatchModel match;
    public Handler customHandler = new Handler();
    private int seconds;
    private int minutes;
    private TextView tv_timer;
    public static UniversalMatchCrossTableActivity MCTA;
    public static int used_hazel_amount = 0;
    public static int used_luck_amount = 0;
    private static boolean returned_from_ad = false;
    private Boolean WaitingToSendCups;
    private boolean adShowing = false;
    private LuckyTextView ltv_TrueCount=null;

    private DialogModel_level dml;
    private DialogModel_cup dmc;


    private int fixed_size_cells=100;
    private int num_row=0;
    private int num_column=0;
    private LinearLayout crossTable;
    private ArrayList<QuestionModel> questions;
    private ArrayList<String> table_shuffled;
    private AutofitTextView older=null;
    private Hashtable<String, String> hash_javab;
    private boolean[] isRightPlace;

    final String DOUBLE_BYTE_SPACE = "\u3000";
    Typeface face;

    //meysam
    private Boolean isInCreate;
    LinearLayout ll_main_dynamic;
    private Boolean isFirstCell;
    private Integer oldIndex;
    private Integer currentIndex;
    private DialogModel DM;
    private SoundFXModel sfxm = null;
    private int height;
    private LuckyTextViewAutoSize tv_hazel_amount;
    private LuckyTextViewAutoSize tv_luck_amount;
    private ImageView btn_sound;
    private ImageView btn_hints;
    private LinearLayout hint_btn_report_cross_table;
    private int cost_show_one_letter;
    private boolean back_requested;
    private boolean home_requested;
    private boolean showOneLetter;
    private LinearLayout windowDown_dt;
    private ScrollView windowDown_SV_dt;
    private LinearLayout hints_dt;
    private LuckyButton windownClose_dt;
    private LinearLayout gameover_win;
    private LinearLayout hint_btn_dh_show_one_letter;
    private LinearLayout hint_btn_dh_ad_show_one_letter;

    private boolean is_gameOver=false;
    private boolean windowDown_isShowing=false;
    private boolean windowDown2_isShowing=false;
    private int final_hazel_amount;
    private int final_luck_amount;
    private ArrayList<String> currentCells;
    private ArrayList<AutofitTextView> ltvCells;
    ///////////////////////////////////////////////
    private ImageView btn_zoom_out;
    private ImageView btn_zoom_in;

    private UserController uc;
    private QuestionController qc;
    private MatchController mc;
    private HomeController hc;

    private Observer cntx;

    private ArrayList<String> queueTags;

    private FrameLayout opponentAvatar;

    private Integer wrongAnswerPenalty = 5;//meysam - in hazel

    Animator anm_hint_ad;
    Animator anm_hazel;
    Animator anm_luck;

    private Runnable requestCrossTableTimerThread = new Runnable() {

        public void run() {
            if(UniversalMatchCrossTableActivity.MCTA == null)
            {
                customHandler.removeCallbacks(this);
                customHandler.removeCallbacksAndMessages(null);
                customHandler = null;
            }
            else
            {
                if(seconds<10 && minutes < 1)
                    tv_timer.setTextColor(Color.RED);
                if(seconds<10)
                {
                    if(minutes < 10)
                    {
                        tv_timer.setText(Utility.enToFa("0"+String.valueOf(minutes)+":0"+String.valueOf(seconds)));

                    }
                    else
                    {
                        tv_timer.setText(Utility.enToFa(String.valueOf(minutes)+":0"+String.valueOf(seconds)));

                    }

                }
                else
                {
                    if(minutes < 10)
                    {
                        tv_timer.setText(Utility.enToFa("0"+String.valueOf(minutes)+":"+String.valueOf(seconds)));

                    }
                    else
                    {
                        tv_timer.setText(Utility.enToFa(String.valueOf(minutes)+":"+String.valueOf(seconds)));

                    }

                }

                if(seconds == 0)
                {
                    if(minutes < 10)
                        tv_timer.setText(Utility.enToFa("0"+String.valueOf(minutes)+":00"));
                    else
                        tv_timer.setText(Utility.enToFa(String.valueOf(minutes)+":00"));


                    seconds=60;
                    minutes=minutes-1;

                }
                if(minutes < 0 || seconds < 0)
                {
                    tv_timer.setText(Utility.enToFa("00"+":00"));

                }
                seconds -= 1;
                if(minutes <= 0 && seconds <= 0)
                {
                    customHandler.removeCallbacks(this);
                    customHandler.removeCallbacksAndMessages(null);
                    customHandler = null;
                    //Meysam: show dialog to user that his/her time ended...meysam
                    timesUp();
                }
                else if(minutes < 0 || seconds < 0)
                {
                    //meysam - something is wrong - end the match!!!
                    customHandler.removeCallbacks(this);
                    customHandler.removeCallbacksAndMessages(null);
                    customHandler = null;
                    //Meysam: show dialog to user that his/her time ended...meysam
                    timesUp();
                }
                else
                {
                    customHandler.postDelayed(this, 1000);
                }
            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_table_match);

        isInCreate = true;

        face = Typeface.createFromAsset(getAssets(),
                "fonts/Khandevane.ttf");

        MCTA = this;
        cntx = this;

        WaitingToSendCups = false;

        hc = new HomeController(getApplicationContext());
        hc.addObserver(this);

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        mc =  new MatchController(getApplicationContext());
        mc.addObserver(this);

        qc = new QuestionController(getApplicationContext());
        qc.addObserver(this);

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("universal_match_cross_table_activity_broadcast"));

        queueTags = new ArrayList<String>();

        opponentAvatar = findViewById(R.id.result_op_avt);
        opponentAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });

        ///////////////////////////////////////////mutual part with individual play - start/////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //meysam
        ltvCells = new ArrayList<>();
        oldIndex = null;
        currentIndex = null;
        currentCells = new ArrayList<>();
        showOneLetter = false;
//        showFirstLetters = false;
//        goToNextCrossTable = false;
        uc=new UserController(this);
        uc.addObserver(this);
        DM=new DialogModel(this);
        btn_sound= (ImageView) findViewById(R.id.btn_sound);
        btn_hints = (ImageView) findViewById(R.id.btn_hints);
        windowDown_dt= (LinearLayout) findViewById(R.id.windowDown_dt);
        windowDown_SV_dt= (ScrollView) findViewById(R.id.windowDown_SV_dt);
        hints_dt= (LinearLayout) findViewById(R.id.hints_dt);
        windownClose_dt= (LuckyButton) findViewById(R.id.windownClose_dt);
        gameover_win= (LinearLayout) findViewById(R.id.gameover_win);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        if (!session.getBoolianItem(SessionModel.KEY_MATCH_CROSS_TABLE_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

            DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, getString(R.string.dlg_TutorialCrossTable), getString(R.string.btn_OK), null,false, false);
            session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_IS_FIRST_TIME, true);

        }
        sfxm = new SoundFXModel(getApplicationContext());

        tv_hazel_amount= (LuckyTextViewAutoSize) findViewById(R.id.tv_hazel_amount);
        tv_luck_amount= (LuckyTextViewAutoSize) findViewById(R.id.tv_luck_amount);

        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

        ImageView iv_luck_amount = findViewById(R.id.iv_luck_amount);
        ImageView iv_hazel_amount = findViewById(R.id.iv_hazel_amount);

        anm_luck =  Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .repeatCount(1000000)                              // Repeat once
                .duration(8000)  // Last for 1000 milliseconds(1 second)
                .delay(0)
//                .force((float) 0.3)
                .createFor(iv_luck_amount);
        anm_luck.start();

        anm_hazel = Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .repeatCount(1000000)                              // Repeat once
                .duration(8000)  // Last for 1000 milliseconds(1 second)
                .delay(2500)
//                .force((float) 0.3)
                .createFor(iv_hazel_amount);
        anm_hazel.start();

        ltv_TrueCount = findViewById(R.id.category_mq);
        ltv_TrueCount.setText(Utility.enToFa("جواب داده: 0"));

//        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
//        endWin_luckAmount = (LuckyTextView) findViewById(R.id.endWin_luckAmount);

//        endWin_luckAmount.setText("0");
//        endWin_hazelAmount.setText("0");

//        ltv_questions = (LuckyTextView) findViewById(R.id.list_questions);

        if(session.getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
            btn_sound.setImageResource(R.drawable.b_sound_on);
        else
            btn_sound.setImageResource(R.drawable.b_sound_mute);
        btn_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam
                if(SoundModel.isPlaying())
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                    SoundModel.stopMusic();
                    btn_sound.setImageResource(R.drawable.b_sound_mute);
                }
                else
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
//                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                    SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                    btn_sound.setImageResource(R.drawable.b_sound_on);
                }

            }
        });

        hint_btn_report_cross_table= (LinearLayout) findViewById(R.id.hint_btn_report_cross_table);
//        hint_btn_dh_ad_next_cross_table= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_next_cross_table);
        hint_btn_dh_show_one_letter= (LinearLayout) findViewById(R.id.hint_btn_dh_show_one_letter);
        hint_btn_dh_ad_show_one_letter= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_show_one_letter);
//        hint_btn_dh_show_first_letters= (LinearLayout) findViewById(R.id.hint_btn_dh_show_first_letters);
//        endWin_doubleHazel = (LinearLayout) findViewById(R.id.endWin_doubleHazel);
//        ll_reportCrossTable = (LinearLayout) findViewById(R.id.ll_reportCrossTable);

        clearAll();

        btn_hints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam
                if(is_gameOver) return;
                if(windowDown_isShowing){
                    windowDown_01(false);
                }else{
                    windowDown_01(true);
                }
            }
        });
        anm_hint_ad = Flubber.with().listener(flubberHintListener)
                .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1200)  // Last for 1000 milliseconds(1 second)
                .delay(5000)
//                .force((float) 0.5)
                .createFor(btn_hints);
        anm_hint_ad.start();// Apply it to the view
        //////////////////////////////////////////

        windownClose_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(windowDown_isShowing){
                    windowDown_01(false);
                }else if(windowDown2_isShowing){
                    windowDown_02(false);
                }
            }
        });

//
//        endWin_doubleHazel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // meysam -movie ad
//
//                if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
//                {
//                    if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
//                    {
//
//                        DM.show();
//                        WatchAd();
//
//                        doubleReward = true;
//                    }
//                    else
//                    {
//                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
//                    }
//                }
//            }
//        });
        hint_btn_dh_show_one_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(UniversalMatchCrossTableActivity.this, cost_show_one_letter))
                {
                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while(DialogPopUpModel.isUp()){
                                    Thread.sleep(500);
                                }
                                if(!DialogPopUpModel.isUp()){
                                    Thread.currentThread().interrupt();
                                    if(DialogPopUpModel.dialog_result==1){
                                        //yes

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // meysam - put show one first letter function
                                                if( hintPutShowOneLetter())
                                                {
                                                    final_hazel_amount -= cost_show_one_letter;
                                                    used_hazel_amount += cost_show_one_letter;

                                                    session.changeFinalHazel(-1*cost_show_one_letter);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(UniversalMatchCrossTableActivity.this).show(false, false, new Integer(cost_show_one_letter));
                                                        }
                                                    });

                                                    Utility.changeCoins(UniversalMatchCrossTableActivity.this,-1*cost_show_one_letter);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
                                                }
                                                else
                                                {
                                                    Utility.displayToast(UniversalMatchCrossTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);//meysam commented in 13960727
                                                    //meysam - 13960727
//                                                    gameOver();
                                                    ///////////////////
                                                }
                                            }
                                        });


                                    }else{
                                        //no
                                        //do nothing
                                    }
                                    DialogPopUpModel.hide();
                                }
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, Utility.enToFa("باید حداقل " + cost_show_one_letter + " فندق داشته باشی!"), "باشه", null, false, false);

                }

            }
        });
        hint_btn_dh_ad_show_one_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad
                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                {
                    DM.show();
                    WatchAd();

                    showOneLetter = true;

                    closeWindows();
                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });
//        hint_btn_dh_next_cross_table.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utility.hasEnoughCoin(UniversalMatchCrossTableActivity.this, cost_next_cross_table))
//                {
//
//                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false );
//                    new Thread(new Runnable()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            try
//                            {
//                                while(DialogPopUpModel.isUp()){
//                                    Thread.sleep(500);
//                                }
//                                if(!DialogPopUpModel.isUp()){
//                                    Thread.currentThread().interrupt();
//                                    if(DialogPopUpModel.dialog_result==1){
//                                        //yes
//
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
////                                                final_hazel_amount -= cost_next_guess_word;
//
//                                                session.changeFinalHazel(-1*cost_next_cross_table);
//
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        new DialogModel_hazel(UniversalMatchCrossTableActivity.this).show(false, false, new Integer(cost_next_cross_table));
//                                                    }
//                                                });
//
//
//                                                Utility.changeCoins(UniversalMatchCrossTableActivity.this,-1*cost_next_cross_table);
//                                                tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
//                                                tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
//
//                                                //meysam
//                                                finishCrossTable(false);
//                                                ////////////////////
//                                            }
//                                        });
//
//
//                                    }else{
//                                        //no
//                                        //do nothing
//                                    }
//                                    DialogPopUpModel.hide();
//                                }
//                            }
//                            catch (InterruptedException e)
//                            {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//
//                }
//                else
//                {
//                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, Utility.enToFa("باید حداقل " + cost_next_cross_table + " فندق داشته باشی!"), "باشه", null, false, false);
//
//                }
//            }
//        });

//        hint_btn_dh_ad_next_cross_table.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //meysam -movie ad
//
//                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
//                {
//                    DM.show();
//                    WatchAd();
//
//                    closeWindows();
//                }
//                else
//                {
//                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
//                }
//            }
//        });
        hint_btn_report_cross_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - report word

                ReportDialogPopUpModel.show(UniversalMatchCrossTableActivity.this);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            while(ReportDialogPopUpModel.isUp()){
                                Thread.sleep(500);
                            }
                            if(!ReportDialogPopUpModel.isUp()){
                                Thread.currentThread().interrupt();//meysam 13960525
                                if(ReportDialogPopUpModel.dialog_result==1){
                                    //send report to server - send btn clicked
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DM.show();
                                        }
                                    });

                                    QuestionController qc = new QuestionController(getApplicationContext());
                                    qc.addObserver(UniversalMatchCrossTableActivity.this);
                                    qc.universalReport(new BigInteger(String.valueOf( session.getIntegerItem(SessionModel.KEY_MATCH_CROSS_TABLE_SINGLE_TABLE_ID))),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description, ReportDialogPopUpModel.CROSS_TABLE_TYPE);

                                }else{
                                    //do nothing
                                }

                                ReportDialogPopUpModel.hide();
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
        });

        //zoomers
        btn_zoom_out= (ImageView) findViewById(R.id.btn_04_dt);
        btn_zoom_in= (ImageView) findViewById(R.id.btn_05_dt);
        btn_zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!windowDown2_isShowing){
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int dpWidth = size.x;
                    if(num_column==0 || (dpWidth/num_column)>(fixed_size_cells)) return;
                    fixed_size_cells-=50;
                    crossTable.removeAllViews();
                    reload(false);
                }

            }
        });
        btn_zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!windowDown2_isShowing) {
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int dpWidth = size.x;
                    if(dpWidth<(fixed_size_cells+50)) return;
                    fixed_size_cells+=50;
                    crossTable.removeAllViews();
                    reload(false);
                }

            }
        });


        crossTable= (LinearLayout) findViewById(R.id.crossTable);

        DM.show();
        initializeTable();

        //meysam - fill cross table

        fillCrossTable();
        DM.hide();


        isInCreate = false;
        init_bidimential();
        ///////////////////////////////////////////mutual part with individual play - end/////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        tv_timer = (TextView) findViewById(R.id.time_counter_mq);

        seconds = getIntent().getIntExtra("seconds",10);
        minutes = getIntent().getIntExtra("minutes",0);

        seconds--;
        if(seconds <= 0)
        {
            seconds=59;
            minutes=minutes-1;

        }

        String tmp_sec = seconds+"";
        String tmp_min = minutes+"";
        if(seconds < 10)
        {

            tmp_sec = "0"+tmp_sec;
        }
        if(minutes < 10)
        {
            tmp_min = "0"+tmp_min;
        }
        if(minutes < 0 || seconds < 0)
        {
            tmp_min = "0";
            tmp_sec = "0";

        }

        tv_timer.setText(Utility.enToFa(tmp_min + ":" + tmp_sec));

        if(minutes < 0 || seconds < 0)
        {

            timesUp();
        }

        //set avatars

        Avatar self_avatar = new Avatar(session.getCurrentUser().getProfilePicture());
        if(match == null || match.getOpponent() == null)
            finish();
        else {
            Avatar opponent_avatar = new Avatar(match.getOpponent().getProfilePicture());

//        View opponent_row_view = findViewById(R.id.result_op_avt);
            opponent_avatar.setAvatar_opt(this);


//        View self_row_view = findViewById(R.id.result_avt);
            self_avatar.setAvatar(this);
            //////////////////////////////
        }

        customHandler.postDelayed(requestCrossTableTimerThread, 1000);

    }

    @Override
    public void update(Observable o, Object arg)
    {
        DM.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {

                    if(queueTags.contains(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT))
                    {
                        //meysam - show repeat dialog
                        DialogPopUpModel.show((Context) cntx,getString(R.string.dlg_WarningTimeRestrictionForMatch),getString(R.string.lbl_retry),getString(R.string.lbl_exit), true, false);
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    while(DialogPopUpModel.isUp()){
                                        Thread.sleep(500);
                                    }
                                    if(!DialogPopUpModel.isUp()){
                                        Thread.currentThread().interrupt();//meysam 13960525
                                        if(customHandler != null)
                                        {
                                            customHandler.removeCallbacks(requestCrossTableTimerThread);
                                            customHandler.removeCallbacksAndMessages(null);
                                        }
                                        if(DialogPopUpModel.dialog_result==1){
                                            //again

                                            showLoading();

                                            mc.universalResult(match,used_luck_amount,used_hazel_amount);

                                        }else{
                                            //exit

                                            //meysam - remove whatever is used hazels
                                            session.removeItem(SessionModel.KEY_QUESTION_USED_HAZEL);
                                            session.removeItem(SessionModel.KEY_QUESTION_USED_LUCK);


                                            if(customHandler != null)
                                            {
                                                customHandler.removeCallbacks(requestCrossTableTimerThread);
                                                customHandler.removeCallbacksAndMessages(null);
                                            }
                                            customHandler = null;

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent i = new Intent(UniversalMatchCrossTableActivity.this,HazelCategoriesActivity.class);
                                                    UniversalMatchCrossTableActivity.this.startActivity(i);
                                                    Utility.finishActivity((Activity) cntx);
                                                }
                                            });

                                        }
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
                    else
                    {
                        Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                        if(customHandler != null)
                        {
                            customHandler.removeCallbacks(requestCrossTableTimerThread);
                            customHandler.removeCallbacksAndMessages(null);
                        }
                        Intent i = new Intent(UniversalMatchCrossTableActivity.this,HazelCategoriesActivity.class);
                        UniversalMatchCrossTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                }
            } else if (arg instanceof ArrayList) {

                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT)) {

                    if (customHandler != null) {
                        customHandler.removeCallbacks(requestCrossTableTimerThread);
                        customHandler.removeCallbacksAndMessages(null);
                    }
                    customHandler = null;

                    if (queueTags.contains(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT))
                        queueTags.remove(queueTags.indexOf(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT));

                    UniversalMatchModel uMatch = (UniversalMatchModel) ((ArrayList) arg).get(1);


                    Intent intent = new Intent(UniversalMatchCrossTableActivity.this, UniversalMatchResultActivity.class);
                    intent.putExtra("match_type", uMatch.getMatchType());
                    intent.putExtra("match_status", uMatch.getMatchStatus());
                    intent.putExtra("opponent_type", uMatch.getOpponentType());
                    intent.putExtra("bet_type", uMatch.getBet());
                    intent.putExtra("match_opponent_allow_chat", match.getOpponent().getAllowChat());

                    if (!match.getSelfSpentTime().equals("null"))
                        intent.putExtra("match_self_spent_time", match.getSelfSpentTime().toString());
                    if (!match.getSelfCorrectCount().equals("null"))
                        intent.putExtra("match_self_correct_count", match.getSelfCorrectCount().toString());
                    if (uMatch.getOpponentSpentTime() != null && !uMatch.getOpponentSpentTime().equals("null"))
                        intent.putExtra("match_opponent_spent_time", uMatch.getOpponentSpentTime());
                    if (uMatch.getOpponentCorrectCount() != null && !uMatch.getOpponentCorrectCount().equals("null"))
                        intent.putExtra("match_opponent_correct_count", uMatch.getOpponentCorrectCount());
                    intent.putExtra("match_opponent_avatar", match.getOpponent().getProfilePicture());
                    intent.putExtra("match_opponent_user_name", match.getOpponent().getUserName());

                    if (uMatch.getEnded().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED)) {
                        if (match.getRewardLuck() != null)
                            intent.putExtra("is_reward_luck", match.getRewardLuck());
                        else
                            intent.putExtra("is_reward_luck", BetModel.isMatchRewardLuck(uMatch.getBet()));
                    }

                    //meysam - check if gained all answers true cup...
                    if (match.getSelfCorrectCount().equals(10))
                    {
                        if (!Utility.isGainedSpecificCups(getApplicationContext(), Utility.CUP_INDEX_MATCH_PERFECT_ANSWERS_ONCE))
                        {
                            // meysam - show earned cup animation...
                            intent.putExtra("is_cup_gained", Utility.CUP_INDEX_MATCH_PERFECT_ANSWERS_ONCE);
                            //////////////////////////////////////
                            ArrayList<Object> tmp = Utility.addCupsScore(getApplicationContext(), SessionModel.KEY_MATCH_ENTIRELY_TRUE_COUNT);
                            if ((Boolean) tmp.get(0)) {
                                //meysam - a cup has been earned...
                                // meysam - show earned cup animation...
                                String cupsToAdd = session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE);
                                //meysam - no need to store session variable for show...
                                final ArrayList<Integer> changedCupsIndexes = Utility.getChangedCups(getApplicationContext(), cupsToAdd);
                                if (changedCupsIndexes.size() > 0) {
                                    // meysam - show earned cup animation...
                                    for (int i = 0; i < changedCupsIndexes.size(); i++) {
                                        final int finalI = i;
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Do something after 5s = 5000ms
                                                dmc = new DialogModel_cup(UniversalMatchCrossTableActivity.this);
                                                dmc.show(Utility.getCupTitleByCode(getApplicationContext(), changedCupsIndexes.get(finalI)), changedCupsIndexes.get(finalI));

                                            }
                                        }, Utility.CUP_DIALOG_TIME);
                                    }
                                    //////////////////////////////////////////////
                                }
                                session.addNewCups(changedCupsIndexes);
                                //////////////////////////////////////
                                /////////////////////////////////////////////
                                //meysam - send new cup to server ...
                                WaitingToSendCups = true;
                                uc.updateCups(session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE));
                                ///////////////////////////////////////
                            }
                        }
                    }

                    /////////////////////////////////////////////////

                    UniversalMatchCrossTableActivity.this.startActivity(intent);

                    //meysam - remove whatever is used hazels - we sent it to server
                    session.removeItem(SessionModel.KEY_QUESTION_USED_HAZEL);
                    session.removeItem(SessionModel.KEY_QUESTION_USED_LUCK);

                    if (!WaitingToSendCups) {
                        Utility.finishActivity(this);
                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER))
                {
                    session.decreaseHazel(String.valueOf(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_HAZEL)));
                    session.decreaseLuck(String.valueOf(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_LUCK)));

                    session.removeItem(SessionModel.KEY_QUESTION_USED_HAZEL);
                    session.removeItem(SessionModel.KEY_QUESTION_USED_LUCK);

                    if(back_requested)
                    {
                        back_requested = false;
                        Utility.finishActivity(this);
                    }
                    if(home_requested)
                    {
                        home_requested = false;
                        Intent i = new Intent(UniversalMatchCrossTableActivity.this, MainActivity.class);
                        UniversalMatchCrossTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {

                    session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                    session.removeItem(SessionModel.KEY_FINAL_LUCK);

                    if(back_requested)
                    {
                        back_requested = false;
                        Utility.finishActivity(this);
                    }
                    if(home_requested)
                    {
                        home_requested = false;
                        Intent i = new Intent(UniversalMatchCrossTableActivity.this, MainActivity.class);
                        UniversalMatchCrossTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_UNIVERSAL))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_LEVEL_USER))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        //meysam - reset KEY_LEVEL_COUNT_FOR_SERVER_UPDATE key in session if successful...
                        //meysam - update user level in session....
//                        Integer levelsToAdd = session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE);
//                        Integer currentLevel = Integer.valueOf(session.getCurrentUser().getLevelScore());
//                        session.saveItem(SessionModel.KEY_LEVEL_SCORE,String.valueOf(levelsToAdd+currentLevel));
                        session.saveItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE,new Integer(0));
                    }
                    else
                    {
                        //meysam - error in updating level in server ... do nothing for now...
                    }

                }
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CUPS_USER)) {
                    if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {
                        //meysam - reset KEY_CUPS_FOR_SERVER_UPDATE key in session if successful...
                        //meysam - update user cups in session....

                        session.saveItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE, "");
                    } else {
                        //meysam - error in updating cups in server ... do nothing for now...
                    }

                    if (WaitingToSendCups) {
                        WaitingToSendCups = false;
                        Utility.finishActivity(this);
                    }

                }

                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_RATE_QUESTION)) {
                    if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {

                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, getApplicationContext().getString(R.string.msg_RateRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                    } else {

                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, getApplicationContext().getString(R.string.msg_RateNotRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }

                }
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_ADVERTISMENT_HOME)) {
                    returned_from_ad = true;
                    hintPutShowOneLetter();

                }

            }
            else if (arg instanceof Integer) {
                if (Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    Utility.finishActivity(this);
                } else {
                    Utility.displayToast(getApplicationContext(), new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
//                    if(back_requested)
//                        finish();
//                    if(home_requested)
//                    {
//                        home_requested = false;
//                        Intent i = new Intent(CrossTableActivity.this, MainActivity.class);
//                        CrossTableActivity.this.startActivity(i);
//                        Utility.finishActivity(this);
//                    }
//                    if(market_requested)
//                    {
//                        market_requested = false;
//                        Intent i = new Intent(CrossTableActivity.this, StoreActivity.class);
//                        CrossTableActivity.this.startActivity(i);
//                        Utility.finishActivity(this);
//                    }
//                    DialogPopUpModel.show(context,"خطا در دریافت سوال!!!","دوباره","خروج", true, false);
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while(DialogPopUpModel.isUp()){
                                    Thread.sleep(500);
                                }
                                if(!DialogPopUpModel.isUp()){
                                    Thread.currentThread().interrupt();//meysam 13960525
                                    if(DialogPopUpModel.dialog_result==1){
                                        //again
//                                        finish();
//                                        startActivity(getIntent());

//                                        showLoading();
//                                        isQuestionRequested = true;
//                                        qc.next(0, 0, 0, categoryId, question);
                                    }else{
                                        //exit
//                                        finish();

//                                        Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
//                                        HazelLevelQuestionActivity.this.startActivity(i);
                                        finish();
                                    }

                                    DialogPopUpModel.hide();
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
            } else {
                Utility.displayToast(getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
//                if(back_requested)
//                    finish();
//                if(home_requested)
//                {
//                    home_requested = false;
//                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
//                    HazelLevelQuestionActivity.this.startActivity(i);
//                    Utility.finishActivity(this);
//                }
//                if(market_requested)
//                {
//                    market_requested = false;
//                    Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
//                    HazelLevelQuestionActivity.this.startActivity(i);
//                    Utility.finishActivity(this);
//                }
//                DialogPopUpModel.show(context,"خطا در دریافت سوال!!!","دوباره","خروج",true, false);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            while(DialogPopUpModel.isUp()){
                                Thread.sleep(500);
                            }
                            if(!DialogPopUpModel.isUp()){
                                Thread.currentThread().interrupt();//meysam 13960525
                                if(DialogPopUpModel.dialog_result==1){
                                    //again
//                                        finish();
//                                        startActivity(getIntent());

                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    //exit
//                                        finish();

                                    Intent i = new Intent(UniversalMatchCrossTableActivity.this, MainActivity.class);
                                    UniversalMatchCrossTableActivity.this.startActivity(i);
                                    finish();
                                }
                                DialogPopUpModel.hide();
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
        } else {
            Utility.displayToast(getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
//            if(back_requested)
//                finish();
//            if(home_requested)
//            {
//                home_requested = false;
//                Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
//                HazelLevelQuestionActivity.this.startActivity(i);
//                Utility.finishActivity(this);
//            }
//            if(market_requested)
//            {
//                market_requested = false;
//                Intent i = new Intent(CrossTableActivity.this, StoreActivity.class);
//                CrossTableActivity.this.startActivity(i);
//                Utility.finishActivity(this);
//            }
//            DialogPopUpModel.show(cntx,"خطا در دریافت سوال!!!","دوباره","خروج", true, false);
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        while(DialogPopUpModel.isUp()){
                            Thread.sleep(500);
                        }
                        if(!DialogPopUpModel.isUp()){
                            Thread.currentThread().interrupt();//meysam 13960525
                            if(DialogPopUpModel.dialog_result==1){
                                //again
                                finish();
                                startActivity(getIntent());
                            }else{
                                //exit
                                Intent i = new Intent(UniversalMatchCrossTableActivity.this, MainActivity.class);
                                UniversalMatchCrossTableActivity.this.startActivity(i);
                                finish();
                            }

                            DialogPopUpModel.hide();
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

    private void reload(boolean is_init) {

        isInCreate = true;
        //meysam
        ltvCells = new ArrayList<>();

        if(!is_init) {
            //meysam - get last letter and positions from session
            currentCells = Utility.convertStringToArrayStrig(session.getStringItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS));
        }

        //hash table for true answer for right place
        hash_javab = new Hashtable<String, String>();
        //hash for question cells
        Map<String, List<String>> hash_questioons = new HashMap<String, List<String>>();
        //hash for arrows
        Hashtable<String, Integer> hash_arrows = new Hashtable<String, Integer>();

        //search in all questions to fill 3 hash tables
        for(QuestionModel question:questions){
            for(int k=0;k<question.getAnswerCells().size();k++){
                String temp_i=question.getAnswerCells().get(k);
//                hash_javab.put(temp_i,question.getAnswer().substring(k,k+1));
                if(k != (question.getAnswerCells().size()-1))
                    hash_javab.put(temp_i,question.getAnswer().substring(k,k+1));
                else
                    hash_javab.put(temp_i,question.getAnswer().substring(question.getAnswer().length() - 1));
            }
            String temp_str=question.getQuestionPosition();
            String temp_str2="";
            if(temp_str.contains(",")){
                temp_str2=temp_str.substring(temp_str.indexOf(",")+1);
                temp_str=temp_str.substring(0,temp_str.indexOf(","));
            }
            List<String> temp_list=hash_questioons.get(temp_str);
            if(temp_list!=null){
                temp_list.add(question.getDescription()+(temp_str2!=""?"#"+temp_str2:"")+"#"+question.getPositionCode());}
            else {
                temp_list=new ArrayList<>();
                temp_list.add(question.getDescription()+(temp_str2!=""?"#"+temp_str2:"")+"#"+question.getPositionCode());
            }
            hash_questioons.put(temp_str,temp_list);

            if(question.getPositionCode().equals("d,3,2")){
                int temp_pos=Integer.valueOf(temp_str)+num_column;
                hash_arrows.put(temp_pos+"",1);
            }else if(question.getPositionCode().equals("d,1,1")){
                int temp_pos=Integer.valueOf(temp_str)-1;
                hash_arrows.put(temp_pos+"",2);
            }else if(question.getPositionCode().equals("l,3,2")){
                int temp_pos=Integer.valueOf(temp_str)+num_column;
                hash_arrows.put(temp_pos+"",3);
            }else if(question.getPositionCode().equals("l,2,1")){
                int temp_pos=Integer.valueOf(temp_str)-1;
                hash_arrows.put(temp_pos+"",4);
            }else if(question.getPositionCode().equals("d,2,1")){
                int temp_pos=Integer.valueOf(temp_str)-1;
                hash_arrows.put(temp_pos+"",5);
            }else if(question.getPositionCode().equals("d,3,1")){
                int temp_pos=Integer.valueOf(temp_str)+num_column;
                hash_arrows.put(temp_pos+"",6);
            }else if(question.getPositionCode().equals("l,1,2")){
                int temp_pos=Integer.valueOf(temp_str)-num_column;
                hash_arrows.put(temp_pos+"",7);
            }else if(question.getPositionCode().equals("l,1,1")){
                int temp_pos=Integer.valueOf(temp_str)-1;
                hash_arrows.put(temp_pos+"",8);
            }else if(question.getPositionCode().equals("l,3,1")){
                int temp_pos=Integer.valueOf(temp_str)-1;
                hash_arrows.put(temp_pos+"",9);
            }else if(question.getPositionCode().equals("d,3,3")){
                int temp_pos=Integer.valueOf(temp_str)+1;
                hash_arrows.put(temp_pos+"",10);
            }else if(question.getPositionCode().equals("d,1,3")){
                int temp_pos=Integer.valueOf(temp_str)+1;
                hash_arrows.put(temp_pos+"",11);
            }
        }

        //shuffle table
        table_shuffled=new ArrayList<>();

        if(is_init) {
            Enumeration keys = hash_javab.keys();
            String key;
            while (keys.hasMoreElements()) {
                key = (String) keys.nextElement();
                table_shuffled.add(hash_javab.get(key));
            }
            fixed_size_cells=Zoom(true);
        }

        //define an array for set if a cell is right place or not
        isRightPlace=new boolean[num_column*num_row];

        int count_row=200;
        int count_column=500;
        for(int i=0;i<num_row;i++){
            LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, fixed_size_cells);
            ll_main_dynamic=new LinearLayout(this);
            ll_main_dynamic.setLayoutParams(lparams);
            ll_main_dynamic.setOrientation(LinearLayout.HORIZONTAL);
            ll_main_dynamic.setTag(count_row++);
            crossTable.addView(ll_main_dynamic);
            lparams = new LinearLayoutCompat.LayoutParams(
                    fixed_size_cells,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
            for(int j=0;j<num_column;j++){
                String temp_int=(count_column-500)+"";
                //is question cell?
                List<String> value=hash_questioons.get(temp_int);
                if(value!=null){
                    //is question cell.
                    if(value.size()>1)
                    {
                        //is question cell with 2 questions
                        LinearLayout fl = new LinearLayout(this);
                        fl.setLayoutParams(lparams);
                        fl.setOrientation(LinearLayout.VERTICAL);
                        ll_main_dynamic.addView(fl);

                        LinearLayoutCompat.LayoutParams lparams2 = new LinearLayoutCompat.LayoutParams(
                                fixed_size_cells,fixed_size_cells/2);

                        String temp="";
                        if(is_init){
                            temp=value.get(0).substring(0,value.get(0).indexOf("#"))+"#"+value.get(1).substring(0,value.get(1).indexOf("#"));
                            currentCells.add(temp);
                            session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
                        }else{
                            temp=currentCells.get(Integer.valueOf(temp_int));
                        }
                        //meysam
                        AutofitTextView tv3=new AutofitTextView(this);
                        tv3.setText(temp);
                        ltvCells.add(tv3);

                        isRightPlace[count_column-500]=true;

                        final AutofitTextView tv=new AutofitTextView(this);
                        tv.setLayoutParams(lparams2);
                        tv.setTag(count_column++);
                        tv.setText(temp.substring(0,temp.indexOf("#")));
                        fixString(tv);
                        tv.setTypeface(face);
                        tv.setMaxLines(1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,250);
                        tv.setTextColor(Color.parseColor("#4e2e03"));
                        tv.setBackgroundResource(R.drawable.diamon_clkr_wooden);
                        tv.setClickable(false);
                        tv.setGravity(Gravity.CENTER);
                        AutofitHelper.create(tv);
                        fl.addView(tv);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,tv.getText().toString(),getString(R.string.btn_OK),null,false, true);
                            }
                        });

                        final AutofitTextView tv2=new AutofitTextView(this);
                        tv2.setLayoutParams(lparams2);
                        tv2.setTag(count_column);
                        tv2.setText(temp.substring(temp.indexOf("#")+1));
                        fixString(tv2);
                        tv2.setTypeface(face);
                        tv2.setMaxLines(1);
                        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP,250);
                        tv2.setTextColor(Color.parseColor("#4e2e03"));
                        tv2.setBackgroundResource(R.drawable.diamon_clkr_wooden);
                        tv2.setClickable(false);
                        tv2.setGravity(Gravity.CENTER);
                        AutofitHelper.create(tv2);
                        fl.addView(tv2);

                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,tv2.getText().toString(),getString(R.string.btn_OK),null,false, true);
                            }
                        });
                    }else{
                        //is question cell with 1 question
                        final AutofitTextView tv=new AutofitTextView(this);
                        tv.setLayoutParams(lparams);
                        tv.setTag(count_column++);

                        if(is_init){
                            currentCells.add(Integer.valueOf(temp_int),value.get(0).substring(0,value.get(0).indexOf("#")));
                            session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
                            ////////////////////////////////////////////////////////////////////////////
                            tv.setText(value.get(0).substring(0,value.get(0).indexOf("#")));
                        }else{
                            tv.setText(currentCells.get(Integer.valueOf(temp_int)));
                        }

                        fixString(tv);
                        tv.setTypeface(face);
                        tv.setMaxLines(1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,250);
                        tv.setTextColor(Color.parseColor("#4e2e03"));
                        tv.setBackgroundResource(R.drawable.diamon_clkr_wooden);
                        tv.setClickable(false);
                        isRightPlace[count_column-501]=true;
                        tv.setGravity(Gravity.CENTER);
                        AutofitHelper.create(tv);
                        //meysam
                        ltvCells.add(tv);
                        //////////////////////
                        ll_main_dynamic.addView(tv);
                        ////////
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,tv.getText().toString(),getString(R.string.btn_OK),null,false, true);
                            }
                        });
                    }
                }else{
                    //is harf cell
                    Integer value2=hash_arrows.get(temp_int);
                    final AutofitTextView tv=new AutofitTextView(this);
                    if(value2==null) {
                        //is harf cell without arrow
                        tv.setLayoutParams(lparams);
                        tv.setTag(count_column++);

                        if(is_init){
                            String mstmp = getHarfInShuffle();
                            currentCells.add(Integer.valueOf(temp_int),mstmp);
                            session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
                            ////////////////////////////////////////////////////////////////////////////
                            tv.setText(mstmp);
                        }else {
                            tv.setText(currentCells.get(Integer.valueOf(temp_int)));
                        }

                        fixString(tv);
                        tv.setTypeface(face);
                        tv.setMaxLines(1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,250);
                        tv.setGravity(Gravity.CENTER);
                        AutofitHelper.create(tv);
                        tv.setGravity(Gravity.CENTER);
                        //meysam
                        ltvCells.add(tv);
                        //////////////////////
                        checkCellIsInRightPlace(tv,false,false);
                        ll_main_dynamic.addView(tv);
                    }else {
                        //is harf cell with arrow
                        FrameLayout fl = new FrameLayout(this);
                        fl.setLayoutParams(lparams);
                        ll_main_dynamic.addView(fl);

                        tv.setLayoutParams(lparams);
                        tv.setTag(count_column++);

                        if(is_init){
                            String mstmp = getHarfInShuffle();
                            currentCells.add(Integer.valueOf(temp_int),mstmp);
                            session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
                            ////////////////////////////////////////////////////////////////////////////
                            tv.setText(mstmp);
                        }else {
                            tv.setText(currentCells.get(Integer.valueOf(temp_int)));
                        }

                        fixString(tv);
                        tv.setTypeface(face);
                        tv.setMaxLines(1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,250);
                        tv.setGravity(Gravity.CENTER);
                        AutofitHelper.create(tv);
                        tv.setGravity(Gravity.CENTER);
                        //meysam
                        ltvCells.add(tv);
                        //////////////////////
                        checkCellIsInRightPlace(tv,false,false);
                        fl.addView(tv);

                        ImageView imgv=new ImageView(this);
                        if(value2==4){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            imgv.setPadding(temp_int2,0,0,0);
                            imgv.setImageResource(R.drawable.arrow_a);
                        }else if(value2==1){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            imgv.setPadding(0,0,0,temp_int2);
                            imgv.setImageResource(R.drawable.arrow_a2);
                        }else if(value2==3){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            imgv.setPadding(0,0,0,temp_int2);
                            imgv.setImageResource(R.drawable.arrow_b);
                        }else if(value2==2){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            int temp_int3=fixed_size_cells-(fixed_size_cells/1);
                            imgv.setPadding(temp_int2,temp_int3,0,temp_int2);
                            imgv.setImageResource(R.drawable.arrow_b2);
                        }else if(value2==6){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            int temp_int3=fixed_size_cells-(fixed_size_cells/1);
                            imgv.setPadding(temp_int2,temp_int2,0,temp_int3);
                            imgv.setImageResource(R.drawable.arrow_b2);
                        }else if(value2==5){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            imgv.setPadding(temp_int2,0,0,0);
                            imgv.setImageResource(R.drawable.arrow_b2);
                        }else if(value2==7){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            imgv.setPadding(0,temp_int2,0,0);
                            imgv.setImageResource(R.drawable.arrow_b3);
                        }else if(value2==8){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            int temp_int3=fixed_size_cells-(fixed_size_cells/1);
                            imgv.setPadding(temp_int2,temp_int3,0,temp_int2);
                            imgv.setImageResource(R.drawable.arrow_a);
                        }else if(value2==9){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            int temp_int3=fixed_size_cells-(fixed_size_cells/1);
                            imgv.setPadding(temp_int2,temp_int2,0,temp_int3);
                            imgv.setImageResource(R.drawable.arrow_a);
                        }else if(value2==10){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            int temp_int3=fixed_size_cells-(fixed_size_cells/1);
                            imgv.setPadding(0,temp_int2,temp_int2,temp_int3);
                            imgv.setImageResource(R.drawable.arrow_b4);
                        }else if(value2==11){
                            int temp_int2=fixed_size_cells-(fixed_size_cells/4);
                            int temp_int3=fixed_size_cells-(fixed_size_cells/1);
                            imgv.setPadding(0,temp_int3,temp_int2,temp_int2);
                            imgv.setImageResource(R.drawable.arrow_b4);
                        }

                        imgv.setLayoutParams(lparams);
                        fl.addView(imgv);
                    }

                    //set Button set
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(older==null){
                                //meysam
                                int index = ltvCells.indexOf(tv);
                                oldIndex = index;
                                ///////////////////////////////////
                                older=tv;
                                tv.setBackgroundResource(R.drawable.diamond_gray);


                            }else if(older==tv) {
                                //meysam
                                oldIndex = null;
                                ///////////////////////////////////
                                older.setBackgroundResource(R.drawable.diamond_orange);
                                older=null;
                            }else{
                                //meysam
                                int index = ltvCells.indexOf(tv);

                                currentIndex = index;
                                ///////////////////////////////////
                                String temp=older.getText().toString();
                                older.setText(tv.getText().toString());
                                AutofitHelper.create(older);
                                older.setGravity(Gravity.CENTER);
                                older.setBackgroundResource(R.drawable.diamond_orange);
                                tv.setText(temp);
                                AutofitHelper.create(tv);
                                tv.setGravity(Gravity.CENTER);
                                checkCellIsInRightPlace(older,false,true);
                                checkCellIsInRightPlace(tv,true,false);
                                // meysam - change and save index array in session
                                Collections.swap(currentCells, oldIndex, currentIndex);

                                session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
                                oldIndex = null;
                                currentIndex = null;
                                ////////////////////////////////////////////////////////
                                older=null;
                                if(isWin()){
                                    // meysam - show next table dialog.... no need
                                    if(!is_gameOver)
//                                        gameOver(true);
                                        finishCrossTable(true);
                                }
                            }
                        }
                    });
                }
            }
        }
        isInCreate = false;

    }

    //fix string
    private void fixString(AutofitTextView textView){
        String fixString = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1
                && android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 || true) {
            fixString = DOUBLE_BYTE_SPACE;
        }
        textView.setText(fixString + textView.getText().toString() + fixString);
    }

    private void checkCellIsInRightPlace(AutofitTextView tv,boolean inGaming, Boolean setIsFirst) {
        int temp_int=new Integer(tv.getTag().toString())-500;
        if(tv.getText().toString().replace(DOUBLE_BYTE_SPACE,"").equals(hash_javab.get(temp_int+""))){
//            if(inGaming)
            if(isInCreate != null)
                if(!isInCreate)
                    sfxm.play(SoundFXModel.CORRECT, getApplicationContext());
            if(setIsFirst)
                isFirstCell = true;




            //meysam - it is in right place and true
            tv.setBackgroundResource(R.drawable.diamond_green);
            isRightPlace[temp_int]=true;
            // meysam - add currect cell to respected question and store in db....
            ltvCells.get(temp_int).setBackgroundResource(R.drawable.diamond_green);
            ltvCells.get(temp_int).setEnabled(false);

            // meysam - check if all questions are answered then end game.....
            MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions,temp_int,true);

            //meysam - count number of answered questions
            Integer answeredQuestionCount = MixedTableModel.countAnsweredQuestions(questions);
            ltv_TrueCount.setText(Utility.enToFa("جواب داده: "+answeredQuestionCount));

            if(MixedTableModel.isAllQuestionsAnswered(questions))
//                gameOver(true);
                finishCrossTable(true);
            ///////////////////////////////////////////////////////////
        }else{

            if(setIsFirst)
                isFirstCell = false;

            //meysam - it is not in right place and false
            ltvCells.get(temp_int).setBackgroundResource(R.drawable.diamond_orange);
            ///////////////////////////////////////////////
            tv.setBackgroundResource(R.drawable.diamond_orange);
            isRightPlace[temp_int]=false;
            if(inGaming)
            {
                if(isFirstCell != null)
                {
                    if(!isFirstCell)
                        reduceHazzelResult();
                    isFirstCell = null;
                }
                else
                {
                    reduceHazzelResult();
                }
            }
        }
    }

    private void reduceHazzelResult(){
        // meysam - subtract one hazel as penalty for wrong answer
        sfxm.play(SoundFXModel.WRONG, getApplicationContext());
        final_hazel_amount-=wrongAnswerPenalty;
        used_hazel_amount +=wrongAnswerPenalty;

        session.changeFinalHazel(-1 * wrongAnswerPenalty);
        Utility.changeCoins(getApplicationContext(),-1 * wrongAnswerPenalty);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DialogModel_hazel(UniversalMatchCrossTableActivity.this).show(false, false, new Integer(wrongAnswerPenalty));
                tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
            }
        });


        ///////////////////////////////////////////////////////////////

    }

    private boolean isWin(){
        for(int i=0;i<isRightPlace.length;i++){
            if(isRightPlace[i]==false)
                return false;
        }
        return true;
    }

    //meysam - get a random letter from table_shuffled...
    private String getHarfInShuffle() {
        int rnd=new Random().nextInt(table_shuffled.size());
        String temp=table_shuffled.get(rnd);
        table_shuffled.remove(rnd);
        return temp;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void init_bidimential() {
        final HorizontalScrollView hScroll = (HorizontalScrollView) findViewById(R.id.scrollHorizontal);
        final ScrollView vScroll = (ScrollView) findViewById(R.id.scrollVertical);
        vScroll.setOnTouchListener(new View.OnTouchListener() { //inner scroll listener
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        hScroll.setOnTouchListener(new View.OnTouchListener() { //outer scroll listener
            private float mx, my, curX, curY;
            private boolean started = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                curX = event.getX();
                curY = event.getY();
                int dx = (int) (mx - curX);
                int dy = (int) (my - curY);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (started) {
                            vScroll.scrollBy(0, dy);
                            hScroll.scrollBy(dx, 0);
                        } else {
                            started = true;
                        }
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        vScroll.scrollBy(0, dy);
                        hScroll.scrollBy(dx, 0);
                        started = false;
                        break;
                }
                return true;
            }
        });
    }

    //meysam
    private void initializeTable()
    {

        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

        // meysam - reset all cells to their default values...
//        correctCells = new ArrayList<>();
        currentCells = new ArrayList<>();
        ltvCells = new ArrayList<>();

        if(ll_main_dynamic != null)
        {
            if(((LinearLayout) ll_main_dynamic).getChildCount() > 0)
                ((LinearLayout) ll_main_dynamic).removeAllViews();
//            ll_main_dynamic = null;
        }
        if(crossTable != null)
        {
            if(((LinearLayout) crossTable).getChildCount() > 0)
                ((LinearLayout) crossTable).removeAllViews();
        }
        ////////////////////////////////////////////////////////////


    }

    //meysam
//    private void setQuestionsFinalLuckAndHazels() {
//
//        reward_hazel_amount = questions.size();
//        reward_luck_amount = 1;
//        endWin_hazelAmount.setText(Utility.enToFa(reward_hazel_amount+""));
//        endWin_luckAmount.setText(Utility.enToFa(reward_luck_amount+""));
//    }

    //meysam
    private void closeWindows()
    {
        if(windowDown_isShowing){
            windowDown_01(false);
        }else if(windowDown2_isShowing){
            windowDown_02(false);
        }
    }

    //meysam
    private void windowDown_02(boolean show) {
        if(show){
            if(windowDown_isShowing){
                windowDown_isShowing=false;
                Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
                windowDown_dt.setVisibility(View.GONE);
                windowDown_SV_dt.setVisibility(View.GONE);
                hints_dt.setVisibility(View.GONE);
//                list_questions.setVisibility(View.GONE);
                windownClose_dt.setVisibility(View.GONE);
            }
            windowDown2_isShowing=true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
//            list_questions.setVisibility(View.VISIBLE);
            windownClose_dt.setVisibility(View.VISIBLE);
            if(is_gameOver){
                hints_dt.setVisibility(View.GONE);
                gameover_win.setVisibility(View.VISIBLE);
                windownClose_dt.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        windowDown_SV_dt.fullScroll(ScrollView.FOCUS_UP);
                        gameover_win.requestFocus();
                        gameover_win.clearFocus();
                    }
                });
            }
            else
            {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        list_questions.smoothScrollToPosition(0);
//                        list_questions.setSelection(0);
//                    }
//                });
            }
            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);

        }else{
            windowDown2_isShowing=false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
            gameover_win.setVisibility(View.GONE);
        }
    }
    //meysam
    private void windowDown_01(boolean show) {
        if(show){
            if(windowDown2_isShowing){
                windowDown2_isShowing=false;
                Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
                windowDown_dt.setVisibility(View.GONE);
                windowDown_SV_dt.setVisibility(View.GONE);
                hints_dt.setVisibility(View.GONE);
                windownClose_dt.setVisibility(View.GONE);
            }
            windowDown_isShowing=true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            hints_dt.setVisibility(View.VISIBLE);
            windownClose_dt.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hints_dt.requestFocus();
                    hints_dt.clearFocus();
                }
            });
            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);

        }else{
            windowDown_isShowing=false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
        }
    }

    private int Zoom(boolean default_value){
        if(default_value){

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int dpWidth = size.x;
            return num_column==0? 100 : (int) (dpWidth / num_column);
        }
        return 0;
    }

    //meysam
    private void WatchAd()
    {
        new SoundModel(this).stopMusic();


        customHandler.removeCallbacks(requestCrossTableTimerThread);
        customHandler.removeCallbacksAndMessages(null);

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(UniversalMatchCrossTableActivity.this, AdvertismentModel.CrossTablePageZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(UniversalMatchCrossTableActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

//                new SoundModel(UniversalMatchCrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                //meysam - resume timer
                customHandler.postDelayed(requestCrossTableTimerThread, 1000);
                /////////////////////////

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {
                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) UniversalMatchCrossTableActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(UniversalMatchCrossTableActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);
//                new SoundModel(UniversalMatchCrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                //meysam - resume timer
                customHandler.postDelayed(requestCrossTableTimerThread, 1000);
                /////////////////////////
            }

            @Override
            public void onNoNetwork ()
            {
                DM.hide();
                Utility.displayToast(UniversalMatchCrossTableActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

//                new SoundModel(UniversalMatchCrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                //meysam - resume timer
                customHandler.postDelayed(requestCrossTableTimerThread, 1000);
                /////////////////////////
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(UniversalMatchCrossTableActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

//                new SoundModel(UniversalMatchCrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                //meysam - resume timer
                customHandler.postDelayed(requestCrossTableTimerThread, 1000);
                /////////////////////////
            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

//                new SoundModel(UniversalMatchCrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                //meysam - resume timer
                customHandler.postDelayed(requestCrossTableTimerThread, 1000);
                /////////////////////////
                if(completed)
                {

                    AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                    if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()) +" "+getString(R.string.msg_AdCountRemained)),getString(R.string.btn_OK),null, false, false);
                    else
                        DialogPopUpModel.show(UniversalMatchCrossTableActivity.this, getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null, false, false);


//                    if(doubleReward)
//                    {
//                        endWin_doubleHazel.setEnabled(false);
//                        // meysam - double reward hazel
//                        reward_hazel_amount *= 2;
//
//                        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
//                        endWin_hazelAmount.setText(Utility.enToFa(reward_hazel_amount+""));
//
//                        doubleReward = false;
//
//                    }
//                    else
                    if(showOneLetter)
                    {
                        showOneLetter = false;

                        //meysam
                        // meysam - show one true letter
                        if(! hintPutShowOneLetter())
                            Utility.displayToast(UniversalMatchCrossTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
                        ///////////////////

                    }
                    else
                    {
                        //meysam
                        finishCrossTable(false);
                        ////////////////////
                    }

                }
                else
                {
                    Utility.displayToast(UniversalMatchCrossTableActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

            }
        });
    }

    //meysam
    private void finishCrossTable(Boolean playVictorySound){


        is_gameOver = true;
        //meysam - add score...
        if(Utility.addLevelScore(getApplicationContext(), UserModel.REWARD_AMOUNT_LEVEL_SCORE_CROSS_TABLE))
        {
            //meysam - an increase in level occured...
            // meysam - show earned level animation...
            //meysam - no need to store session variable for show...
            int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()),Integer.valueOf(session.getCurrentUser().getLevelScore())+ session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
            dml = new DialogModel_level(UniversalMatchCrossTableActivity.this);
            dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())),Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()))+levelDiffrence);
            /////////////////////////////////////////////
            //meysam - send new level to server ...
            uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
            ///////////////////////////////////////

        }

//        match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(match.getQuestions()).toString());
        match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(questions).toString());
        match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds,UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

        if(customHandler != null)
        {
            customHandler.removeCallbacks(requestCrossTableTimerThread);
            customHandler.removeCallbacksAndMessages(null);
        }


        mc.universalResult(match,used_luck_amount,used_hazel_amount);//meysam - added in 13961001

        queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

        DM.show();
//        endWin_doubleHazel.setEnabled(false);
//
//        endWin_luckAmount.setText("0");
//        endWin_hazelAmount.setText("0");

//        gameOver(playVictorySound);

//        windowDown_01(false);
    }

    //meysam
    private void setHintAmounts() {

//        TextView hint_dh_next_table= (TextView) findViewById(R.id.hint_dh_next_cross_table);
//        cost_next_cross_table = 100;
//        hint_dh_next_table.setText(Utility.enToFa("-"+(cost_next_cross_table)));


        TextView hint_dh_show_one_letter= (TextView) findViewById(R.id.hint_dh_show_one_letter);
        cost_show_one_letter = 30;
        hint_dh_show_one_letter.setText(Utility.enToFa("-"+(cost_show_one_letter)));
    }

    private boolean hintPutShowOneLetter() {
        windowDown_01(false);
        if(is_gameOver) return false;

        int nextCellForHintIndex = getNextCellForHint();
        if(nextCellForHintIndex == -1) return false;

        AutofitTextView ltv_nextCellForHint = ltvCells.get(nextCellForHintIndex);

        int nextCellForHintSwapIndex = getNextCellForHintSwap(hash_javab.get(String.valueOf(nextCellForHintIndex)), nextCellForHintIndex);
        if(nextCellForHintSwapIndex == -1) return false;
        AutofitTextView ltv_nextCellForHintSwap = ltvCells.get(nextCellForHintSwapIndex);

        String temp=ltv_nextCellForHint.getText().toString();
        ltv_nextCellForHint.setText(ltv_nextCellForHintSwap.getText().toString());
        ltv_nextCellForHint.setBackgroundResource(R.drawable.bg_05_clker);
        ltv_nextCellForHintSwap.setText(temp);

        Collections.swap(currentCells, nextCellForHintIndex, nextCellForHintSwapIndex);
//        MixedTableModel.swapAnswerCells(questions,nextCellForHintIndex,nextCellForHintSwapIndex);
        session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));

        checkCellIsInRightPlace(ltv_nextCellForHint,false,true);
        checkCellIsInRightPlace(ltv_nextCellForHintSwap,true,false);

        return true;
    }

    //meysam
    private int getNextCellForHint( )
    {
        int maxCellPos = 0;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().size() > maxCellPos)
                maxCellPos = questions.get(i).getAnswerCells().size();
        }

        int cellPos = 0;
        int result = -1;
        outerloop:
        while (cellPos < maxCellPos)
        {

            for(int i = 0; i < questions.size(); i++)
            {
                List<String> answeredCells;
                if(questions.get(i).getAnsweredCells() != null && questions.get(i).getAnsweredCells().size() > 0)
                {
                    answeredCells = questions.get(i).getAnsweredCells();
                }
                else
                {
                    answeredCells = new ArrayList<String>();
                }

                if(questions.get(i).getAnswerCells().size() != answeredCells.size())
                {
                    List<String> temp = questions.get(i).getAnswerCells();
                    for(int j = 0; j <= cellPos; j++)
                    {
                        if(temp.size() > j)
                        {
                            if(!answeredCells.contains(temp.get(j)))
                            {
                                result = new Integer(temp.get(j));
                                break outerloop;

                            }
                        }

                    }
                }
            }
            cellPos++;
        }

        return result;
    }

    //meysam
    private int getNextCellForHintSwap(String targetValue, Integer indexOfHintCell )
    {
        int result = -1;
        for(int i = 0; i < ltvCells.size(); i++)
        {

            if(!MixedTableModel.isCellInAnsweredCells(questions,i)&& ltvCells.get(i).getText().toString().replace(DOUBLE_BYTE_SPACE,"").equals(targetValue) && i != indexOfHintCell)
            {
                //meysam - find somthing that could be answer...
                result =  i;
                break;
            }
        }

        //TODO: meysam - check and find a cell that with swapping dosnt become an answer itself!!...future versions
        ////////////////////////////////////////////////////////////////////////////////////////////////

        return result;
    }

    private void fillCrossTable()
    {

        int id = Integer.valueOf(match.getCrossTable().getId().toString());
        num_column = match.getCrossTable().getWidth();
        num_row = match.getCrossTable().getHeight();
        questions=match.getQuestions();

        session.removeItem(SessionModel.KEY_MATCH_CROSS_TABLE_CURRENT_CELLS);

        //meysam - save row and column counts as well as table id to session(using in reporting problem questions)
        session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_SINGLE_ROW_COUNT,num_row);
        session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_SINGLE_COLUMN_COUNT,num_column);
        session.saveItem(SessionModel.KEY_MATCH_CROSS_TABLE_SINGLE_TABLE_ID,id);

        session.removeItem(SessionModel.KEY_FINAL_HAZEL);
        session.removeItem(SessionModel.KEY_FINAL_LUCK);

        if(questions.size()<1){
            Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            Intent i = new Intent(UniversalMatchCrossTableActivity.this,MainActivity.class);
            UniversalMatchCrossTableActivity.this.startActivity(i);
            Utility.finishActivity(this);
        }

        //meysam - calculate hint amounts
        setHintAmounts();
        //////////////////////////

        reload(true);
    }

    private void endMatch()
    {

        if(customHandler != null)
        {
            customHandler.removeCallbacks(requestCrossTableTimerThread);
            customHandler.removeCallbacksAndMessages(null);

        }


        DM.show();
//        match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
        match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(questions).toString());
        match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

        mc.universalResult(match,used_luck_amount,used_hazel_amount);

        queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);
    }

    private void timesUp()
    {
        final Dialog d = new Dialog(UniversalMatchCrossTableActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.message_dialog);

        TextView message_box_dialog = (TextView) d.findViewById(R.id.message_box_dialog);
        Button btn_dialog_01 = (Button) d.findViewById(R.id.btn_mess_01);
        Button btn_dialog_02 = (Button) d.findViewById(R.id.btn_mess_02);

        tv_timer.setText("00:00");

        message_box_dialog.setText(getApplicationContext().getString(R.string.msg_TimesUp));
        btn_dialog_01.setText(getApplicationContext().getString(R.string.btn_OK));
        btn_dialog_02.setVisibility(View.GONE);

        btn_dialog_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
//                match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(questions).toString());
                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestCrossTableTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }

                mc.universalResult(match,used_luck_amount,used_hazel_amount);

                queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

                DM.show();
            }
        });
        d.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                d.hide();
//                match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(questions).toString());

                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestCrossTableTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }

                mc.universalResult(match,used_luck_amount,used_hazel_amount);


                queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

                DM.show();
            }
        });
        d.show();
    }

    private void showLoading()
    {
        Intent intent = new Intent("universal_match_cross_table_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(UniversalMatchCrossTableActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughHazelMessage()
    {
        Intent intent = new Intent("universal_match_cross_table_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_hazel", "true");
        LocalBroadcastManager.getInstance(UniversalMatchCrossTableActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughLuckMessage()
    {
        Intent intent = new Intent("universal_match_cross_table_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_luck", "true");
        LocalBroadcastManager.getInstance(UniversalMatchCrossTableActivity.this).sendBroadcast(intent);
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
            if(intent.getStringExtra("not_enough_hazel") != null)
            {
                if(intent.getStringExtra("not_enough_hazel").equals("true")) {

                    Utility.displayToast(UniversalMatchCrossTableActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);
                    endMatch();

                }
            }
            if(intent.getStringExtra("not_enough_luck") != null)
            {
                if(intent.getStringExtra("not_enough_luck").equals("true")) {

                    Utility.displayToast(UniversalMatchCrossTableActivity.this,getString(R.string.msg_NotEnoughLuck),Toast.LENGTH_SHORT);
                    endMatch();

                }
            }



        }
    };

    private void goToProfile()
    {
        //meysam - if visitor then no profile...
        if(match.getOpponent().getUserName() != null && !match.getOpponent().getUserName().contains("Visitor_")) {
            DM.show();
       //////////////////////////////////////////////////////////////////
//            Intent intent = new Intent(UniversalMatchCrossTableActivity.this, UserProfileActivity.class);
//            intent.putExtra("EXTRA_User_Name", match.getOpponent().getUserName());
//            intent.putExtra("EXTRA_Avatar", match.getOpponent().getProfilePicture());

            if(session.getCurrentUser().getAllowChat().equals("1"))
            {
                if(match.getOpponent().getAllowChat().equals("1"))
                {
                    //meysam - chat is allowed
                    //meysam - chat is allowed
                    Intent intent = new Intent(UniversalMatchCrossTableActivity.this, ChatActivity.class);
                    intent.putExtra("opponent_user_name", match.getOpponent().getUserName());
                    intent.putExtra("opponent_avatar", match.getOpponent().getProfilePicture());

                    UniversalMatchCrossTableActivity.this.startActivity(intent);
                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,"حریف چت رو غیر فعال کرده",getString(R.string.btn_OK),null,false,true);
                }


            }
            else
            {
                DialogPopUpModel.show(UniversalMatchCrossTableActivity.this,"چت در تنظیمات غیر فعال شده",getString(R.string.btn_OK),null,false,true);

            }

            DM.hide();
        }else{
            Utility.displayToast(UniversalMatchCrossTableActivity.this,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
        }
    }

    //meysam
    private void clearAll()
    {
        is_gameOver = false;

//        endWin_doubleHazel.setEnabled(false);

        back_requested = false;
        home_requested = false;
//        doubleReward = false;

//        cost_next_cross_table = 0;

        final_hazel_amount = 0;
        final_luck_amount = 0;

//        reward_luck_amount = 0;
//        reward_hazel_amount = 0;

        used_hazel_amount = 0;
        used_luck_amount = 0;

        showOneLetter = false;
//        showFirstLetters = false;
//        goToNextCrossTable = false;
        back_requested = false;

//        cost_show_first_letters = 0;
        cost_show_one_letter = 0;
//        cost_next_cross_table = 0;

        if(ll_main_dynamic != null)
        {
            if(((LinearLayout) ll_main_dynamic).getChildCount() > 0)
                ((LinearLayout) ll_main_dynamic).removeAllViews();
        }
        if(crossTable != null)
        {
            if(((LinearLayout) crossTable).getChildCount() > 0)
                ((LinearLayout) crossTable).removeAllViews();
        }
    }

    private void dialogForExit(final boolean backRq, final boolean homeReq) {

        final Dialog d = new Dialog(UniversalMatchCrossTableActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.message_dialog);

        TextView message_box_dialog = (TextView) d.findViewById(R.id.message_box_dialog);
        Button btn_dialog_01 = (Button) d.findViewById(R.id.btn_mess_01);
        Button btn_dialog_02 = (Button) d.findViewById(R.id.btn_mess_02);

        message_box_dialog.setText("اطمینان داری؟ ممکنه ببازی!!");
        btn_dialog_01.setText("بله");
        btn_dialog_02.setText("خیر");
        btn_dialog_02.setVisibility(View.VISIBLE);

        btn_dialog_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(questions).toString());
                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());
                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestCrossTableTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }


                if(backRq)
                {
                    back_requested = true;

                    mc.universalResult(match,used_luck_amount,used_hazel_amount);//meysam - added in 13961001
                }
                if(homeReq)
                {
                    home_requested = true;
                    mc.universalResult(match,used_luck_amount,used_hazel_amount);//meysam - added in 13961001


                }
                queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

                session.saveUsedHazel(used_hazel_amount);
                session.saveUsedLuck(used_luck_amount);


                DM.show();
                d.hide();

            }
        });
        btn_dialog_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });

        d.show();
    }

    public void onDestroy() {

        if(DM != null)
        {
            DM.hide();
            DM.dismiss();
            DM = null;
        }


        sfxm.releaseSoundPool();
        sfxm = null;

        if(customHandler != null)
        {
            customHandler.removeCallbacks(requestCrossTableTimerThread);
            customHandler.removeCallbacksAndMessages(null);
        }

        if(mc != null)
        {
            mc.setCntx(null);
            mc.deleteObservers();
            mc = null;
        }
        if(qc != null)
        {
            qc.setCntx(null);
            qc.deleteObservers();
            qc = null;
        }
        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObservers();
            uc = null;
        }
        if(hc != null)
        {
            hc.setCntx(null);
            hc.deleteObservers();
            hc = null;
        }

        Tapsell.setRewardListener(null);

        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        isInCreate = false;

        if(dml != null)
        {
            dml.dismiss();
            dml = null;
        }
        if(dmc != null)
        {
            dmc.dismiss();
            dmc = null;
        }

        if(anm_hint_ad != null)
        {
            anm_hint_ad.end();
            anm_hint_ad.cancel();
            anm_hint_ad.removeAllListeners();
        }


        flubberHintListener = null;

        if(anm_hazel != null)
        {
            anm_hazel.end();
            anm_hazel.cancel();
            anm_hazel.removeAllListeners();
        }


        if(anm_luck != null)
        {
            anm_luck.end();
            anm_luck.cancel();
            anm_luck.removeAllListeners();
        }


        MCTA = null;

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if(!windowDown_isShowing && !windowDown2_isShowing)
        {
            if(minutes <= 0 && seconds <= 0)
            {
//                match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(match.getQuestions()).toString());
                match.setSelfCorrectCount(MixedTableModel.countAnsweredQuestions(questions).toString());
                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds,UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestCrossTableTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }


                mc.universalResult(match,used_luck_amount,used_hazel_amount);//meysam - added in 13961001

                queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

                DM.show();
            }
            else
            {

                dialogForExit(true,false);

            }

        }
        else
        {
            if(!is_gameOver)
            {
                //meysam - added in 13970118
                windowDown_01(false);
                windowDown_02(false);
            }

        }

    }

    public void onResume() {

        sfxm = new SoundFXModel(getApplicationContext());

        if(!isInCreate)
        {

            if(adShowing)
            {
                adShowing = false;
                //meysam - somthing went wrong in showing ad in standard senario
            }

        }
        else
            isInCreate = false;

        super.onResume();
        SoundModel.stopSpecificSound();
        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

    }

    @Override
    public void onPause() {
        SoundModel.stopSpecificSound();
        super.onPause();
    }

    public Animator.AnimatorListener flubberHintListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
//            ImageView btn_hints = (ImageView) findViewById(R.id.btn_hints);
            Random rand = new Random();
            int min = 5;
            int max = 25;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_hint_ad.removeAllListeners();
            anm_hint_ad = Flubber.with().listener(flubberHintListener)
                    .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1200)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
//                    .force((float) 0.5)
                    .createFor(btn_hints);
            anm_hint_ad.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;


}
