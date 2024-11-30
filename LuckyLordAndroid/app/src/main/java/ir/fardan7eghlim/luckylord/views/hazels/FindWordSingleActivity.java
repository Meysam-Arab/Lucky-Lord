package ir.fardan7eghlim.luckylord.views.hazels;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.help.DialogModel_help;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.WordModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextViewAutoSize;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_puzzle_question;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_level;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.ReportDialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.SoundFXModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.user.SearchUserActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class FindWordSingleActivity extends BaseActivity  implements Observer {

    private DatabaseHandler db;

    private final int DEFAULT_WIN_COUNT = 5;

    private SoundFXModel sfxm = null;
    private DialogModel DM;

    private DialogModel_level dml;

    private int sizeOfItemInList;

    private int numberWord=70;
    private View lastWord=null;
    private TextView text_dt;
    private String horoofs[] = {"آ", "ب", "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی", "ا"};

    private CustomAdapterList_puzzle_question CALPQ;
    private int height;

    private LuckyTextView word_01;
    private LuckyTextView word_02;
    private LuckyTextView word_03;
    private LuckyTextView word_04;
    private LuckyTextView word_05;
    private LuckyTextView word_06;
    private LuckyTextView word_07;
    private LuckyTextView word_08;
    private LuckyTextView word_09;
    private LuckyTextView word_10;
    private LuckyTextView word_11;
    private LuckyTextView word_12;
    private LuckyTextView word_13;
    private LuckyTextView word_14;
    private LuckyTextView word_15;
    private LuckyTextView word_16;
    private LuckyTextView word_17;
    private LuckyTextView word_18;
    private LuckyTextView word_19;
    private LuckyTextView word_20;
    private LuckyTextView word_21;
    private LuckyTextView word_22;
    private LuckyTextView word_23;
    private LuckyTextView word_24;
    private LuckyTextView word_25;
    private LuckyTextView word_26;
    private LuckyTextView word_27;
    private LuckyTextView word_28;
    private LuckyTextView word_29;
    private LuckyTextView word_30;
    private LuckyTextView word_31;
    private LuckyTextView word_32;
    private LuckyTextView word_33;
    private LuckyTextView word_34;
    private LuckyTextView word_35;
    private LuckyTextView word_36;
    private LuckyTextView word_37;
    private LuckyTextView word_38;
    private LuckyTextView word_39;
    private LuckyTextView word_40;
    private LuckyTextView word_41;
    private LuckyTextView word_42;
    private LuckyTextView word_43;
    private LuckyTextView word_44;
    private LuckyTextView word_45;
    private LuckyTextView word_46;
    private LuckyTextView word_47;
    private LuckyTextView word_48;
    private LuckyTextView word_49;
    private LuckyTextView word_50;
    private LuckyTextView word_51;
    private LuckyTextView word_52;
    private LuckyTextView word_53;
    private LuckyTextView word_54;
    private LuckyTextView word_55;
    private LuckyTextView word_56;
    private LuckyTextView word_57;
    private LuckyTextView word_58;
    private LuckyTextView word_59;
    private LuckyTextView word_60;
    private LuckyTextView word_61;
    private LuckyTextView word_62;
    private LuckyTextView word_63;
    private LuckyTextView word_64;
    private LuckyTextView word_65;
    private LuckyTextView word_66;
    private LuckyTextView word_67;
    private LuckyTextView word_68;
    private LuckyTextView word_69;
    private LuckyTextView word_70;

    private LuckyTextView found_count;

    private LuckyTextViewAutoSize tv_hazel_amount;
    private LuckyTextViewAutoSize tv_luck_amount;

    private LuckyTextView endWin_luckAmount;
    private LuckyTextView endWin_hazelAmount;

    private ImageView btn_sound;
    private ImageView btn_hints;

    private WordModel answer;
    private UserController uc;

    private int reward_hazel_amount;
    private int reward_luck_amount;
    private int win_count;

    LinearLayout hint_btn_dh_next_find_words;
    LinearLayout hint_btn_dh_ad_next_find_words;
    LinearLayout hint_btn_dh_reduce_required_word_count;
    LinearLayout hint_btn_dh_ad_reduce_required_word_count;

    private int cost_next_find_words;
    private int cost_reduce_word_count;



    private boolean doubleReward;
    private boolean back_requested;
    private boolean home_requested;
    private boolean goToNextTable;
    private boolean reduceWordCount;


    private LinearLayout windowDown_dt;
    private ScrollView windowDown_SV_dt;
    private LinearLayout hints_dt;
    private ListView list_words;
    private LuckyButton windownClose_dt;
    private LuckyButton lb_words_list;
    private LuckyButton lb_recommend_word;



    private LinearLayout gameover_win;

    LinearLayout endWin_doubleHazel;
    FrameLayout fl_doubleHazel;

    private boolean is_gameOver=false;
    private boolean windowDown_isShowing=false;
    private boolean windowDown2_isShowing=false;

    private Animation in_game_wavy;
    private Animation in_game_shake;

    Animator anm_hint_ad;
    Animator anm_double_reward;
    Animator anm_hazel;
    Animator anm_luck;

    private boolean is_onCreate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_word_single);

//        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

        db=new DatabaseHandler(getApplicationContext());

        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("find_words_activity_broadcast"));

        uc=new UserController(this);
        uc.addObserver(this);

        DM=new DialogModel(this);

        in_game_wavy = AnimationUtils.loadAnimation(FindWordSingleActivity.this, R.anim.in_game_wavy);
        in_game_shake = AnimationUtils.loadAnimation(FindWordSingleActivity.this, R.anim.in_game_shake);

        btn_sound= (ImageView) findViewById(R.id.btn_sound);
        btn_hints = (ImageView) findViewById(R.id.btn_hints);

        word_01= (LuckyTextView) findViewById(R.id.word_01);
        word_02= (LuckyTextView) findViewById(R.id.word_02);
        word_03= (LuckyTextView) findViewById(R.id.word_03);
        word_04= (LuckyTextView) findViewById(R.id.word_04);
        word_05= (LuckyTextView) findViewById(R.id.word_05);
        word_06= (LuckyTextView) findViewById(R.id.word_06);
        word_07= (LuckyTextView) findViewById(R.id.word_07);
        word_08= (LuckyTextView) findViewById(R.id.word_08);
        word_09= (LuckyTextView) findViewById(R.id.word_09);
        word_10= (LuckyTextView) findViewById(R.id.word_10);
        word_11= (LuckyTextView) findViewById(R.id.word_11);
        word_12= (LuckyTextView) findViewById(R.id.word_12);
        word_13= (LuckyTextView) findViewById(R.id.word_13);
        word_14= (LuckyTextView) findViewById(R.id.word_14);
        word_15= (LuckyTextView) findViewById(R.id.word_15);
        word_16= (LuckyTextView) findViewById(R.id.word_16);
        word_17= (LuckyTextView) findViewById(R.id.word_17);
        word_18= (LuckyTextView) findViewById(R.id.word_18);
        word_19= (LuckyTextView) findViewById(R.id.word_19);
        word_20= (LuckyTextView) findViewById(R.id.word_20);
        word_21= (LuckyTextView) findViewById(R.id.word_21);
        word_22= (LuckyTextView) findViewById(R.id.word_22);
        word_23= (LuckyTextView) findViewById(R.id.word_23);
        word_24= (LuckyTextView) findViewById(R.id.word_24);
        word_25= (LuckyTextView) findViewById(R.id.word_25);
        word_26= (LuckyTextView) findViewById(R.id.word_26);
        word_27= (LuckyTextView) findViewById(R.id.word_27);
        word_28= (LuckyTextView) findViewById(R.id.word_28);
        word_29= (LuckyTextView) findViewById(R.id.word_29);
        word_30= (LuckyTextView) findViewById(R.id.word_30);
        word_31= (LuckyTextView) findViewById(R.id.word_31);
        word_32= (LuckyTextView) findViewById(R.id.word_32);
        word_33= (LuckyTextView) findViewById(R.id.word_33);
        word_34= (LuckyTextView) findViewById(R.id.word_34);
        word_35= (LuckyTextView) findViewById(R.id.word_35);
        word_36= (LuckyTextView) findViewById(R.id.word_36);
        word_37= (LuckyTextView) findViewById(R.id.word_37);
        word_38= (LuckyTextView) findViewById(R.id.word_38);
        word_39= (LuckyTextView) findViewById(R.id.word_39);
        word_40= (LuckyTextView) findViewById(R.id.word_40);
        word_41= (LuckyTextView) findViewById(R.id.word_41);
        word_42= (LuckyTextView) findViewById(R.id.word_42);
        word_43= (LuckyTextView) findViewById(R.id.word_43);
        word_44= (LuckyTextView) findViewById(R.id.word_44);
        word_45= (LuckyTextView) findViewById(R.id.word_45);
        word_46= (LuckyTextView) findViewById(R.id.word_46);
        word_47= (LuckyTextView) findViewById(R.id.word_47);
        word_48= (LuckyTextView) findViewById(R.id.word_48);
        word_49= (LuckyTextView) findViewById(R.id.word_49);
        word_50= (LuckyTextView) findViewById(R.id.word_50);
        word_51= (LuckyTextView) findViewById(R.id.word_51);
        word_52= (LuckyTextView) findViewById(R.id.word_52);
        word_53= (LuckyTextView) findViewById(R.id.word_53);
        word_54= (LuckyTextView) findViewById(R.id.word_54);
        word_55= (LuckyTextView) findViewById(R.id.word_55);
        word_56= (LuckyTextView) findViewById(R.id.word_56);
        word_57= (LuckyTextView) findViewById(R.id.word_57);
        word_58= (LuckyTextView) findViewById(R.id.word_58);
        word_59= (LuckyTextView) findViewById(R.id.word_59);
        word_60= (LuckyTextView) findViewById(R.id.word_60);
        word_61= (LuckyTextView) findViewById(R.id.word_61);
        word_62= (LuckyTextView) findViewById(R.id.word_62);
        word_63= (LuckyTextView) findViewById(R.id.word_63);
        word_64= (LuckyTextView) findViewById(R.id.word_64);
        word_65= (LuckyTextView) findViewById(R.id.word_65);
        word_66= (LuckyTextView) findViewById(R.id.word_66);
        word_67= (LuckyTextView) findViewById(R.id.word_67);
        word_68= (LuckyTextView) findViewById(R.id.word_68);
        word_69= (LuckyTextView) findViewById(R.id.word_69);
        word_70= (LuckyTextView) findViewById(R.id.word_70);


        windowDown_dt= (LinearLayout) findViewById(R.id.windowDown_dt);
        windowDown_SV_dt= (ScrollView) findViewById(R.id.windowDown_SV_dt);
        hints_dt= (LinearLayout) findViewById(R.id.hints_dt);
        list_words=(ListView) findViewById(R.id.list_words);
        windownClose_dt= (LuckyButton) findViewById(R.id.windownClose_dt);
        gameover_win= (LinearLayout) findViewById(R.id.gameover_win);
        lb_words_list = (LuckyButton) findViewById(R.id.lb_words_list);
        lb_recommend_word = (LuckyButton) findViewById(R.id.lb_recommend_word);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;


        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////
        if (!session.getBoolianItem(SessionModel.KEY_FIND_WORD_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

//            DialogPopUpModel.show(FindWordSingleActivity.this, getString(R.string.dlg_TutorialFindWords), getString(R.string.btn_OK), null,false, false);
            DialogModel_help dm_help=new DialogModel_help(FindWordSingleActivity.this);
            dm_help.show(1);
            session.saveItem(SessionModel.KEY_FIND_WORD_IS_FIRST_TIME, true);

        }
        sfxm = new SoundFXModel(getApplicationContext());

        found_count= (LuckyTextView) findViewById(R.id.ltv_foundCount);

        tv_hazel_amount= (LuckyTextViewAutoSize) findViewById(R.id.tv_hazel_amount);
        tv_luck_amount= (LuckyTextViewAutoSize) findViewById(R.id.tv_luck_amount);

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

        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
        endWin_luckAmount = (LuckyTextView) findViewById(R.id.endWin_luckAmount);

        endWin_luckAmount.setText("0");
        endWin_hazelAmount.setText("0");

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
                    new SoundModel(getApplicationContext()).playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);
                    btn_sound.setImageResource(R.drawable.b_sound_on);
                }

            }
        });

        hint_btn_dh_next_find_words= (LinearLayout) findViewById(R.id.hint_btn_dh_next_find_words);
        hint_btn_dh_ad_next_find_words= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_next_find_words);

        hint_btn_dh_reduce_required_word_count= (LinearLayout) findViewById(R.id.hint_btn_dh_reduce_required_word_count);
        hint_btn_dh_ad_reduce_required_word_count= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_reduce_required_word_count);

        endWin_doubleHazel = (LinearLayout) findViewById(R.id.endWin_doubleHazel);
        fl_doubleHazel = findViewById(R.id.fl_doubleHazel);


        is_onCreate=true;
        clearAll();
        setHintAmounts();


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

        lb_words_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindWordSingleActivity.this, SearchUserActivity.class);
                intent.putExtra("list_type", "search_words");
                FindWordSingleActivity.this.startActivity(intent);
            }
        });

        lb_recommend_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDialogPopUpModel.show(FindWordSingleActivity.this,"کلمه پیشنهادی شما:","کلمه رو وارد کن...",false);
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
                                Thread.currentThread().interrupt();
                                if(ReportDialogPopUpModel.dialog_result==1){
                                    //send
                                    if(!ReportDialogPopUpModel.description.equals(""))
                                    {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DM.show();
                                            }
                                        });

                                        QuestionController qc = new QuestionController(FindWordSingleActivity.this);
                                        qc.addObserver(FindWordSingleActivity.this);
                                        qc.universalReport(new BigInteger("0"),ReportDialogPopUpModel.code," کلمه پیشنهادی از طرف کاربر: "+ReportDialogPopUpModel.description, ReportDialogPopUpModel.FIND_WORD_TYPE);

                                    }

                                }else{
                                    //cancel
                                    //do nothing
                                }
                                ReportDialogPopUpModel.hide();
                            }
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });



            endWin_doubleHazel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // meysam -movie ad

                    if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
                    {
                        if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                        {

                            DM.show();
                            doubleReward = true;
                            WatchAd();
                        }
                        else
                        {
                            DialogPopUpModel.show(FindWordSingleActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                        }
                    }
                }
            });






        hint_btn_dh_next_find_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(FindWordSingleActivity.this, cost_next_find_words))
                {

                    DialogPopUpModel.show(FindWordSingleActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false );
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
//                                                final_hazel_amount -= cost_next_guess_word;

                                                session.changeFinalHazel(-1*cost_next_find_words);

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new DialogModel_hazel(FindWordSingleActivity.this).show(false, false, new Integer(cost_next_find_words));
                                                    }
                                                });


                                                Utility.changeCoins(FindWordSingleActivity.this,-1*cost_next_find_words);
                                                tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                //meysam
                                                finishFindWords();
                                                ////////////////////
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
                    DialogPopUpModel.show(FindWordSingleActivity.this, Utility.enToFa("باید حداقل " + cost_next_find_words + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });

        hint_btn_dh_ad_next_find_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad

                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                {
                    DM.show();
                    WatchAd();

                    goToNextTable = true;

                    closeWindows();
                }
                else
                {
                    DialogPopUpModel.show(FindWordSingleActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });

        hint_btn_dh_reduce_required_word_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(FindWordSingleActivity.this, cost_reduce_word_count))
                {
                    if(win_count <= 1)
                    {
                        DialogPopUpModel.show(FindWordSingleActivity.this, Utility.enToFa(  " کمتر از این نمیشه!"), "باشه", null, false, false);

                    }
                    else
                    {
                        DialogPopUpModel.show(FindWordSingleActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false );
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
//                                                final_hazel_amount -= cost_next_guess_word;

                                                    session.changeFinalHazel(-1*cost_reduce_word_count);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(FindWordSingleActivity.this).show(false, false, new Integer(cost_reduce_word_count));
                                                        }
                                                    });


                                                    Utility.changeCoins(FindWordSingleActivity.this,-1*cost_reduce_word_count);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                    //meysam
                                                    // meysam - hint for reduce word count...
                                                    hintReduceWordCount();
                                                    closeWindows();
                                                    if(answer.getWords().size() >= win_count)
                                                    {
                                                        gameOver(true);
                                                    }
                                                    ////////////////////
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

                }
                else
                {
                    DialogPopUpModel.show(FindWordSingleActivity.this, Utility.enToFa("باید حداقل " + cost_reduce_word_count + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });

        hint_btn_dh_ad_reduce_required_word_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad

                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                {
                    DM.show();
                    WatchAd();

                    reduceWordCount = true;

                    closeWindows();
                }
                else
                {
                    DialogPopUpModel.show(FindWordSingleActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });



/////////////////////////////////////////////////////////////////////////////////
        text_dt= (TextView) findViewById(R.id.text_dt);

//        win_count = DEFAULT_WIN_COUNT;

        reward_luck_amount = 1;



        //meysam - check if word exist from before
        //meysam = 13960830
        answer = db.getWordByTag(WordModel.TABLE_WORDS_FIND_WORD_TAG);
        /////////////////////////////////////////////
        reward_hazel_amount = calculateRewardHazel();
        if(answer == null)
        {
            initialize();
        }
        else
        {
            win_count = session.getIntegerItem(SessionModel.KEY_FIND_WORD_WORD_COUNT);
            for(int i=0;i<numberWord;i++) {
                String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                TextView tempV = (TextView) findViewById(id);
                tempV.setBackgroundResource(R.drawable.diamon_clkr_orange);
                tempV.setClickable(true);
                tempV.setVisibility(View.VISIBLE);
                tempV.setEnabled(true);
                tempV.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(!is_gameOver) {

                            int x = (int) event.getRawX();
                            int y = (int) event.getRawY();
                            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                                TextView onView= (TextView) whichOne(x,y);
                                if(!onView.isPressed())
                                    sfxm.play(SoundFXModel.CLICKED,getApplicationContext());
                                addWord(x, y);
                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                v.setBackgroundResource(R.drawable.diamon_clkr_orange);

                                //meysam
                                checkAnswer();
                                //////////////////
                                lastWord=null;
                            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                sfxm.play(SoundFXModel.CLICKED,getApplicationContext());
                                addWord(x, y);
                            }
                        }
                        return true;
                    }
                });
                tempV.setText(answer.getAllLetters().get(i));
            }
            fillList(answer.getWords());
        }
    }

    private int calculateRewardHazel()
    {
        int result = 0;
        if(answer != null)
        {
            for(int i = 0; i< answer.getWords().size();i++)
            {
                if(answer.getWords().get(i).length() > 2)
                    result += answer.getWords().get(i).replaceAll("\\s+","").length();
            }

        }

        return result;
    }

    private int calculateRewardHazel(String word)
    {
        int result = word.replaceAll("\\s+","").length();
        return result;
    }

    private void makeTable() {
        for(int i=0;i<numberWord;i++) {

            String harf = null;

            //meysam - for testing specific words
//            int limitDown = 64;
//            int limitUp = 67;
//            if(i >= limitDown && i <= limitUp)
//            {
//                String testWord = "بوکس";
//                harf = String.valueOf(testWord.toCharArray()[i-limitDown]);
//            }
//            else
//            {

                int rnd=new Random().nextInt(horoofs.length);
                harf = horoofs[rnd];

//            }
            ///////////////////////////////////////
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView tempV = (TextView) findViewById(id);
//            int rnd=new Random().nextInt(horoofs.length);
//            tempV.setText(horoofs[rnd]);
//            answer.getAllLetters().add(i,horoofs[rnd]);
            answer.getAllLetters().add(i,harf);
            tempV.setText(harf);
            answer.getAllLettersPositions().add(String.valueOf(i));



        }
    }

    private void checkAnswer() {

        String currentAnswer=text_dt.getText().toString();
        int foundWord=0;//0:wrong - 1: true - 2:alredy found



//        WordModel word=db.getFarsiWord(currentAnswer);
            WordModel word=db.getFarsiWordNoSpace(currentAnswer);

            // meysam - remove true from condition...
            if(word!=null && !answer.getWords().contains(currentAnswer) && currentAnswer.length() > 2){
//                Utility.displayToast(getApplicationContext(), "درسته!", Toast.LENGTH_SHORT);


                tv_hazel_amount.setText(Utility.enToFa(String.valueOf(Integer.valueOf(tv_hazel_amount.getText().toString()) + calculateRewardHazel(currentAnswer))));
                final String tmpCurrentAnswer = currentAnswer;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new DialogModel_hazel(FindWordSingleActivity.this).show(false, true, calculateRewardHazel(tmpCurrentAnswer));
                    }
                });


                foundWord=1;
                answer.getWords().add(currentAnswer);
                db.deleteWordsByTag(WordModel.TABLE_WORDS_FIND_WORD_TAG);
                db.addWord(answer,WordModel.TABLE_WORDS_FIND_WORD_TAG);
                fillList(answer.getWords());
                if(answer.getWords().size() >= win_count)
                {
                    gameOver(true);
                }
                else
                {
                    sfxm.play(SoundFXModel.CORRECT, getApplicationContext());

                }
//            Utility.displayToast(getApplicationContext(), "درسته", Toast.LENGTH_SHORT);
            }else{
                if(currentAnswer.length() <= 2)
                {
                    Utility.displayToast(getApplicationContext(), "باید بیشتر از دو حرف باشه!!", Toast.LENGTH_SHORT);
                    foundWord=0;
                }
                if(answer.getWords().contains(currentAnswer))
                {
                    Utility.displayToast(getApplicationContext(), "قبلا پیداش کردی!!", Toast.LENGTH_SHORT);
                    foundWord=2;

                }
                sfxm.play(SoundFXModel.WRONG, getApplicationContext());
//            Utility.displayToast(getApplicationContext(), "غلط", Toast.LENGTH_SHORT);
            }

            text_dt.setText("");
            for(int i=0;i<numberWord;i++){
                String word_name="word_"+(i>8?"":"0")+(i+1);
                final int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());

                if(findViewById(id).isPressed()){
                    if(foundWord == 1){
                        findViewById(id).setBackgroundResource(R.drawable.diamond_green);
                        findViewById(id).setAnimation(in_game_wavy);
                    }else if(foundWord == 2){
                        findViewById(id).setBackgroundResource(R.drawable.diamond_blue);
                    }
                    else{
                        findViewById(id).setBackgroundResource(R.drawable.diamond_red);
                        findViewById(id).setAnimation(in_game_shake);
                    }
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(id).setBackgroundResource(R.drawable.diamon_clkr_orange);
                        }
                    }, 1200);
                    findViewById(id).setPressed(false);
                    findViewById(id).setClickable(true);
//                v.setEnabled(true);
                }
            }
    }

    private void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = list_words.getAdapter();
        if (listAdapter == null) {

            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, list_words);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list_words.getLayoutParams();
        params.height = totalHeight
                + (list_words.getDividerHeight() * (listAdapter.getCount() - 1));
        list_words.setLayoutParams(params);
        list_words.requestLayout();
    }


    private boolean isNextValidePosition(TextView onView, View lastWord){

        int lw_index= Integer.parseInt(String.valueOf(lastWord.getTag()));
        int ov_index= Integer.parseInt(String.valueOf(onView.getTag()));

        if(lw_index==1) if(ov_index==2 || ov_index==8) return true;
        else if(lw_index==7) if(ov_index==6 || ov_index==14) return true;
        else if(lw_index==64) if(ov_index==57 || ov_index==65) return true;
        else if(lw_index==70) if(ov_index==69 || ov_index==63) return true;
        else if(lw_index==2 || lw_index==3 || lw_index==4 || lw_index==5 || lw_index==6){
            if(lw_index+1==ov_index || lw_index-1==ov_index || lw_index+7==ov_index){
                return true;
            }
        }
        else if(lw_index==65 || lw_index==66 || lw_index==67 || lw_index==68 || lw_index==69){
            if(lw_index+1==ov_index || lw_index-1==ov_index || lw_index-7==ov_index){
                return true;
            }
        }
        else if(lw_index==8 || lw_index==15 || lw_index==22 || lw_index==29 || lw_index==36 || lw_index==43 || lw_index==50 || lw_index==57){
            if(lw_index+1==ov_index || lw_index+7==ov_index || lw_index-7==ov_index){
                return true;
            }
        }
        else if(lw_index==14 || lw_index==21 || lw_index==28 || lw_index==35 || lw_index==42 || lw_index==49 || lw_index==56 || lw_index==63){
            if(lw_index-1==ov_index || lw_index+7==ov_index || lw_index-7==ov_index){
                return true;
            }
        }
        if(lw_index+1==ov_index || lw_index-1==ov_index || lw_index+7==ov_index || lw_index-7==ov_index){
            return true;
        }
        return false;
    }


    private void addWord(int x, int y) {
        TextView onView= (TextView) whichOne(x,y);
        if(onView==null || !onView.isClickable())return;
        onView.setPressed(true);
        if(onView!=null && onView!=lastWord){
            if(lastWord!=null) if(!isNextValidePosition(onView,lastWord)) return;
            lastWord=onView;
            onView.setClickable(false);
            if(!onView.getText().toString().equals(""))
                text_dt.setText(text_dt.getText().toString()+onView.getText().toString());
            else {
                text_dt.setText("");
                //meysam
                checkAnswer();
                ///////////////
            }
        }
    }

    public View whichOne(int x,int y){
        for(int i=0;i<numberWord;i++){
            String word_name="word_"+(i>8?"":"0")+(i+1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp=findViewById(id);
            if(isViewInBounds(temp, x, y)){
                return temp;
            }
        }
        return null;
    }


    Rect outRect = new Rect();
    int[] location = new int[2];
    private boolean isViewInBounds(View view, int x, int y){
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    private void resetButtonColors()
    {
        for(int i=0;i<numberWord;i++){
            String word_name = "word_" + (i > 8 ? "" : "0") + (i+1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            LuckyTextView temp = (LuckyTextView) findViewById(id);
            temp.setBackgroundResource(R.drawable.diamon_clkr_orange);
            temp.setEnabled(true);
            temp.setClickable(true);
        }
    }

    private void finishFindWords(){

        if(anm_double_reward != null && anm_double_reward.isRunning())
        {
            anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }
        endWin_doubleHazel.setEnabled(false);
        endWin_doubleHazel.setVisibility(View.GONE);
        fl_doubleHazel.setVisibility(View.GONE);
        fl_doubleHazel.invalidate();
        endWin_doubleHazel.invalidate();

        endWin_luckAmount.setText("0");
        endWin_hazelAmount.setText("0");

        fillList(answer.getWords());
        gameOver(false);

        windowDown_01(false);
    }

    private void gameOver(final boolean isWin){

        if(isWin)
        {
            Random r = new Random();
            int randomInt = r.nextInt(100) + 1;

            if(randomInt < 20)//meysam - must be lower than 20 %...
            {
                endWin_doubleHazel.setEnabled(true);
                endWin_doubleHazel.setVisibility(View.VISIBLE);
                fl_doubleHazel.setVisibility(View.VISIBLE);
                fl_doubleHazel.invalidate();
                endWin_doubleHazel.invalidate();
//                fl_doubleHazel.removeAllViews();
//                fl_doubleHazel.refreshDrawableState();
                anm_double_reward = Flubber.with().listener(flubberDoubleRewardListener)
                        .animation(Flubber.AnimationPreset.POP) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(1000)  // Last for 1000 milliseconds(1 second)
                        .delay(0)
                        .force((float) 0.3)
                        .createFor(fl_doubleHazel);
                anm_double_reward.start();// Apply it to the view
                //////////////////////////////////////////
            }
            else
            {
                if(anm_double_reward != null)
                {
                    if(anm_double_reward.isRunning())
                        anm_double_reward.end();
                    anm_double_reward.cancel();
                    anm_double_reward.removeAllListeners();
                }
                endWin_doubleHazel.setEnabled(false);
                endWin_doubleHazel.setVisibility(View.GONE);
                fl_doubleHazel.setVisibility(View.GONE);
                fl_doubleHazel.invalidate();
                endWin_doubleHazel.invalidate();
            }




            //meysam - play win table sound
            SoundModel.playSpecificSound(R.raw.victory_match,getApplicationContext(),false,null,null, null);

            //meysam - add score...
            if(Utility.addLevelScore(getApplicationContext(), UserModel.REWARD_AMOUNT_LEVEL_SCORE_FIND_WORD_TABLE))
            {
                //meysam - an increase in level occured...
                // meysam - show earned level animation...
                //meysam - no need to store session variable for show...
                int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()),Integer.valueOf(session.getCurrentUser().getLevelScore())+ session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                dml = new DialogModel_level(FindWordSingleActivity.this);
                dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())),Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()))+levelDiffrence);
                /////////////////////////////////////////////
                //meysam - send new level to server ...
                uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                ///////////////////////////////////////

            }
            /////////////////////////////
            reward_hazel_amount = calculateRewardHazel();
            endWin_hazelAmount.setText(String.valueOf(reward_hazel_amount));
            endWin_luckAmount.setText(String.valueOf(reward_luck_amount));


        }
        else
        {
            if(anm_double_reward != null)
            {
                if(anm_double_reward.isRunning())
                    anm_double_reward.end();
                anm_double_reward.cancel();
                anm_double_reward.removeAllListeners();
            }
            endWin_doubleHazel.setEnabled(false);
            endWin_doubleHazel.setVisibility(View.GONE);
            fl_doubleHazel.setVisibility(View.GONE);
            fl_doubleHazel.invalidate();
            endWin_doubleHazel.invalidate();
            //meysam - play lose table sound
//            SoundModel.playSpecificSound(R.raw.lost_match,getApplicationContext(),false);
            /////////////////////////////
        }


        //meysam
        db.deleteWordsByTag(WordModel.TABLE_WORDS_FIND_WORD_TAG);
        session.removeItem(SessionModel.KEY_FIND_WORD_WORD_COUNT);
        ///////////////////

        is_gameOver=true;

        //game over
//        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

        //game is over
        for(int i=0;i<numberWord;i++){
            String word_name="word_"+(i>8?"":"0")+(i+1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp=findViewById(id);
            Random r = new Random();
            int r1_duration = r.nextInt(1000) + 2000;
            int r2_delay = r.nextInt(100) + 1000;
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT_IN)
                    .repeatCount(1)
                    .repeatMode(ValueAnimator.RESTART)
                    .duration(r1_duration)
                    .delay(r2_delay)
                    .createFor(temp)
                    .start();
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                windowDown_02(true);
            }
        }, 3000);

        //buttons of end win
        LinearLayout endWin_pause= (LinearLayout) findViewById(R.id.endWin_pause);
        endWin_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam

                if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
                {
                    if(isWin)
                    {
                        reward_hazel_amount = calculateRewardHazel();
                        session.changeFinalHazel(reward_hazel_amount);
                        session.changeFinalLuck( reward_luck_amount);

                        //meysam - add reward to session
                        Utility.changeCoins(getApplicationContext(),reward_hazel_amount);
                        Utility.changeLucks(getApplicationContext(),reward_luck_amount);
                        ///////////////////////////////
                    }
                    else
                    {
                        session.changeFinalLuck( -1);
                        Utility.changeLucks(getApplicationContext(),-1);

                    }


                }


                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {
                    DM.show();
                    home_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {
                    Intent i = new Intent(FindWordSingleActivity.this, MainActivity.class);
                    FindWordSingleActivity.this.startActivity(i);
                    finish();
                }

                db.deleteWordsByTag(WordModel.TABLE_WORDS_FIND_WORD_TAG);
            }
        });
        LinearLayout endWin_next= (LinearLayout) findViewById(R.id.endWin_next);
        endWin_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
                {

                    session.changeFinalHazel(reward_hazel_amount);
                    session.changeFinalLuck( reward_luck_amount);

                    //meysam - add reward to session
                    Utility.changeCoins(getApplicationContext(),reward_hazel_amount);
                    Utility.changeLucks(getApplicationContext(),reward_luck_amount);
                    ///////////////////////////////
                }

                // meysam - reload next table
                closeWindows();

                DM.show();
                uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                clearAll();
                initialize();

            }
        });
    }

    private void initialize() {

        new SoundModel(getApplicationContext()).playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


        win_count = DEFAULT_WIN_COUNT;
        session.saveItem(SessionModel.KEY_FIND_WORD_WORD_COUNT,win_count);

        resetButtonColors();
        setHintAmounts();

        answer = null;
        list_words.setAdapter(null);
        is_gameOver = false;

        db.deleteWordsByTag(WordModel.TABLE_WORDS_FIND_WORD_TAG);

        answer=new WordModel();

        for(int i=0;i<numberWord;i++){
            String word_name="word_"+(i>8?"":"0")+(i+1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View v=findViewById(id);
            v.setBackgroundResource(R.drawable.diamon_clkr_orange);
            v.setClickable(true);
            v.setVisibility(View.VISIBLE);
            v.setEnabled(true);
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if(!is_gameOver) {
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            TextView onView= (TextView) whichOne(x,y);
                            if(!onView.isPressed())
                                sfxm.play(SoundFXModel.CLICKED,getApplicationContext());
                            addWord(x, y);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            v.setBackgroundResource(R.drawable.diamon_clkr_orange);

                            //meysam
                            checkAnswer();
                            //////////////////
                            lastWord=null;
                        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            sfxm.play(SoundFXModel.CLICKED,getApplicationContext());
                            addWord(x, y);
                        }
                    }
                    return true;
                }
            });
        }

        makeTable();

    }

    private void closeWindows()
    {
        if(windowDown_isShowing){
            windowDown_01(false);
        }else if(windowDown2_isShowing){
            windowDown_02(false);
        }
    }
    //////////////////////////////////
    private void windowDown_02(boolean show) {
        if(show){
            if(windowDown_isShowing){
                windowDown_isShowing=false;
                Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
                windowDown_dt.setVisibility(View.GONE);
                windowDown_SV_dt.setVisibility(View.GONE);
                hints_dt.setVisibility(View.GONE);
                list_words.setVisibility(View.GONE);
                windownClose_dt.setVisibility(View.GONE);
                lb_words_list.setVisibility(View.GONE);
                lb_recommend_word.setVisibility(View.GONE);
            }
            windowDown2_isShowing=true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            list_words.setVisibility(View.VISIBLE);
            lb_words_list.setVisibility(View.VISIBLE);
            lb_recommend_word.setVisibility(View.VISIBLE);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        list_words.smoothScrollToPosition(0);
                        list_words.setSelection(0);
                    }
                });
            }
            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);

        }else{
            windowDown2_isShowing=false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            list_words.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
            gameover_win.setVisibility(View.GONE);
            lb_words_list.setVisibility(View.GONE);
            lb_recommend_word.setVisibility(View.GONE);
        }
    }
    private void windowDown_01(boolean show) {
        if(show){
            if(windowDown2_isShowing){
                windowDown2_isShowing=false;
                Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
                windowDown_dt.setVisibility(View.GONE);
                windowDown_SV_dt.setVisibility(View.GONE);
                hints_dt.setVisibility(View.GONE);
                list_words.setVisibility(View.GONE);
                windownClose_dt.setVisibility(View.GONE);
                lb_words_list.setVisibility(View.GONE);
                lb_recommend_word.setVisibility(View.GONE);
            }
            windowDown_isShowing=true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            hints_dt.setVisibility(View.VISIBLE);
            lb_words_list.setVisibility(View.VISIBLE);
            lb_recommend_word.setVisibility(View.VISIBLE);
            windownClose_dt.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    list_words.setVisibility(View.VISIBLE);
//                    list_words.smoothScrollToPosition(0);
//                    list_words.setSelection(0);

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
            list_words.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
            lb_words_list.setVisibility(View.GONE);
            lb_recommend_word.setVisibility(View.GONE);
        }
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(FindWordSingleActivity.this, AdvertismentModel.FindWordPageZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(FindWordSingleActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

//                new SoundModel(FindWordSingleActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {
                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) FindWordSingleActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(FindWordSingleActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);
//                new SoundModel(FindWordSingleActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }

            @Override
            public void onNoNetwork ()
            {
                DM.hide();
                Utility.displayToast(FindWordSingleActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

//                new SoundModel(FindWordSingleActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(FindWordSingleActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

//                new SoundModel(FindWordSingleActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

//                new SoundModel(FindWordSingleActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


                if(completed)
                {

                    AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                    if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(FindWordSingleActivity.this, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()) +" "+getString(R.string.msg_AdCountRemained)),getString(R.string.btn_OK),null, false, false);
                    else
                        DialogPopUpModel.show(FindWordSingleActivity.this, getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null, false, false);


                    if(doubleReward)
                    {
                        if(anm_double_reward != null)
                        {
                            if(anm_double_reward.isRunning())
                                anm_double_reward.end();
                            anm_double_reward.cancel();
                            anm_double_reward.removeAllListeners();
                        }
                        endWin_doubleHazel.setEnabled(false);
                        endWin_doubleHazel.setVisibility(View.GONE);
                        fl_doubleHazel.setVisibility(View.GONE);
                        fl_doubleHazel.invalidate();
                        endWin_doubleHazel.invalidate();
                        // meysam - double reward hazel
                        reward_hazel_amount *= 2;

                        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
                        endWin_hazelAmount.setText(Utility.enToFa(reward_hazel_amount+""));

                        doubleReward = false;

                    }
                    else if(goToNextTable)
                    {
                        goToNextTable = false;

                        //meysam
                        finishFindWords();
                        ////////////////////
                    }
                    else if(reduceWordCount)
                    {
                        reduceWordCount = false;

                        // meysam - call reduce word count hint function..
                        hintReduceWordCount();
                        if(answer.getWords().size() >= win_count)
                        {
                            gameOver(true);
                        }
                        ////////////////////////////////////////////////////////
                    }
                    else
                    {
                        //meysam - do nothing...
                    }

                }
                else
                {
                    Utility.displayToast(FindWordSingleActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

            }
        });
    }


    private void setHintAmounts() {

        TextView hint_dh_next_table= (TextView) findViewById(R.id.hint_dh_next_find_words);
        cost_next_find_words = 100;
        hint_dh_next_table.setText(Utility.enToFa("-"+(cost_next_find_words)));

        cost_reduce_word_count = 30;
        found_count.setText("کلمات پیدا شده" +Utility.enToFa(" (0/"+win_count+") "));
        TextView hint_dh_reduce_required_word_count= (TextView) findViewById(R.id.hint_dh_reduce_required_word_count);
        hint_dh_reduce_required_word_count.setText(Utility.enToFa("-"+(cost_reduce_word_count)));

    }

    private void hintReduceWordCount() {
        // meysam - complete this...

        win_count--;
        session.saveItem(SessionModel.KEY_FIND_WORD_WORD_COUNT,win_count);

        found_count.setText("کلمات پیدا شده" +Utility.enToFa(" ("+answer.getWords().size()+"/"+win_count+") "));
        TextView hint_dh_reduce_required_word_count= (TextView) findViewById(R.id.hint_dh_reduce_required_word_count);
        hint_dh_reduce_required_word_count.setText(Utility.enToFa("-"+(cost_reduce_word_count)));

    }

    //fill list of questions
    private void fillList(ArrayList<String> words) {
        found_count.setText("کلمات پیدا شده" +Utility.enToFa(" ("+words.size()+"/"+win_count+") "));

        CALPQ = new CustomAdapterList_puzzle_question(this, new ArrayList<Object>(words), RequestRespondModel.TAG_WORD_FIND_WORDS);
        list_words.setAdapter(CALPQ);
        setListViewHeightBasedOnChildren();
        list_words.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
            }
        });

        list_words.invalidateViews();
    }

//    private void timesUp()
//    {
//        final Dialog d = new Dialog(FindWordSingleActivity.this);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        d.setContentView(R.layout.message_dialog);
//
//        TextView message_box_dialog = (TextView) d.findViewById(R.id.message_box_dialog);
//        Button btn_dialog_01 = (Button) d.findViewById(R.id.btn_mess_01);
//        Button btn_dialog_02 = (Button) d.findViewById(R.id.btn_mess_02);
//
////        tv_timer.setText("00:00");
//
//        message_box_dialog.setText(getApplicationContext().getString(R.string.msg_TimesUp));
//        btn_dialog_01.setText(getApplicationContext().getString(R.string.btn_OK));
//        btn_dialog_02.setVisibility(View.GONE);
//
//        btn_dialog_01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                d.hide();
//                // meysam - what to do??
//
//                /////////////////////////////
//
//                DM.show();
//            }
//        });
//        d.setOnCancelListener(new DialogInterface.OnCancelListener()
//        {
//            @Override
//            public void onCancel(DialogInterface dialog)
//            {
//                d.hide();
//                // meysam - what to do??
//
//                /////////////////////////////
//                DM.show();
//            }
//        });
//        d.show();
//    }


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
            if(intent.getStringExtra("report_result_dialog") != null)
            {
                if(intent.getStringExtra("report_result_dialog").equals("true"))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(FindWordSingleActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(FindWordSingleActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });
                }
            }
            if(intent.getStringExtra("rate_result_dialog") != null)
            {

                if(intent.getStringExtra("rate_result_dialog").equals("true"))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(FindWordSingleActivity.this, getApplicationContext().getString(R.string.msg_RateRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(FindWordSingleActivity.this, getApplicationContext().getString(R.string.msg_RateNotRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });
                }
            }
            if(intent.getStringExtra("show_toast") != null)
            {
                final String temp = intent.getStringExtra("show_toast");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.displayToast(FindWordSingleActivity.this,temp, Toast.LENGTH_SHORT);
                    }
                });
            }
            if(intent.getStringExtra("show_dialog") != null)
            {

                final String temp = intent.getStringExtra("show_dialog");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(FindWordSingleActivity.this, temp, getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                });

            }

        }
    };


    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg)
    {
        DM.hide();
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(FindWordSingleActivity.this,MainActivity.class);
                    FindWordSingleActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER))
                {

                    session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                    session.removeItem(SessionModel.KEY_FINAL_LUCK);

                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                    if(back_requested)
                    {
                        back_requested = false;
                        Utility.finishActivity(this);
                    }
                    if(home_requested)
                    {
                        home_requested = false;
                        Intent i = new Intent(FindWordSingleActivity.this, MainActivity.class);
                        FindWordSingleActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {

                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

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
                        Intent i = new Intent(FindWordSingleActivity.this, MainActivity.class);
                        FindWordSingleActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_UNIVERSAL))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        DialogPopUpModel.show(FindWordSingleActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        DialogPopUpModel.show(FindWordSingleActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

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
            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
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

    public void onDestroy() {

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        sfxm.releaseSoundPool();
        sfxm = null;

        uc.deleteObservers();
        uc = null;

        Tapsell.setRewardListener(null);

        CustomAdapterList_puzzle_question.setInflater(null);

        if(dml != null)
        {
            dml.dismiss();
            dml = null;
        }

        if(anm_hint_ad != null)
        {
            anm_hint_ad.end();
            anm_hint_ad.cancel();
            anm_hint_ad.removeAllListeners();
        }


        if(anm_double_reward != null)
        {
            if(anm_double_reward.isRunning())
                anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }

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


        flubberHintListener = null;
        flubberDoubleRewardListener = null;


        super.onDestroy();

    }

    @Override
    public void onBackPressed() {

        if(!windowDown_isShowing && !windowDown2_isShowing)
        {

            //meysam - set three arrayes

            //meysam - 13960727
            db.deleteWordsByTag(WordModel.TABLE_WORDS_FIND_WORD_TAG);
            db.addWord(answer, WordModel.TABLE_WORDS_FIND_WORD_TAG);
            ////////////////////

            if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 ||
                    session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0  )
            {
                DM.show();
                back_requested = true;
                uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
            }
            else
            {
                super.onBackPressed();
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

    @Override
    public void onPause() {
        SoundModel.stopSpecificSound();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        SoundModel.stopSpecificSound();
        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

    }

    private void clearAll()
    {
        found_count.setText("کلمات پیدا شده" +Utility.enToFa(" (0/5) "));

        if(anm_double_reward != null)
        {
            if(anm_double_reward.isRunning())
                anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }
        endWin_doubleHazel.setEnabled(false);
        endWin_doubleHazel.setVisibility(View.GONE);
        fl_doubleHazel.setVisibility(View.GONE);
        fl_doubleHazel.invalidate();
        endWin_doubleHazel.invalidate();

        back_requested = false;
        home_requested = false;
        doubleReward = false;
        reduceWordCount = false;
        goToNextTable = false;

        list_words.setAdapter(null);

//        win_count = DEFAULT_WIN_COUNT;
//        session.saveItem(SessionModel.KEY_FIND_WORD_WORD_COUNT,win_count);

        cost_next_find_words = 0;
        for(int i=0;i<numberWord;i++){
            String word_name="word_"+(i>8?"":"0")+(i+1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp=findViewById(id);
            int temp_i=1;
            if(is_onCreate==true) {
                temp_i = 1000;
            }
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN_UP)
                    .repeatCount(1)
                    .duration(temp_i)
                    .delay(1)
                    .createFor(temp)
                    .start();
        }
        is_onCreate=false;
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

    public Animator.AnimatorListener flubberDoubleRewardListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
//            ImageView btn_hints = (ImageView) findViewById(R.id.btn_hints);
            Random rand = new Random();
            int min = 1;
            int max = 2;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_double_reward.removeAllListeners();
            fl_doubleHazel.setVisibility(View.VISIBLE);
            fl_doubleHazel.invalidate();
            endWin_doubleHazel.invalidate();
//            fl_doubleHazel.removeAllViews();
//            fl_doubleHazel.refreshDrawableState();
            anm_double_reward = Flubber.with().listener(flubberDoubleRewardListener)
                    .animation(Flubber.AnimationPreset.POP) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1000)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
                    .force((float) 0.3)
                    .createFor(fl_doubleHazel);
            anm_double_reward.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;
}
