package ir.fardan7eghlim.luckylord.views.hazels;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.help.DialogModel_help;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.MixedTableModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
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
import ir.fardan7eghlim.luckylord.utils.SoundFXModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

import static ir.fardan7eghlim.luckylord.models.SessionModel.KEY_TANGLED_TABLE_AVAILABLE_CELLS;
import static ir.fardan7eghlim.luckylord.models.SessionModel.KEY_TANGLED_TABLE_REMOVED_CELLS;

public class DarhamTableActivity extends BaseActivity implements Observer {

    private TextView text_dt;
    private DialogModel DM;
    //meysam - commented in 13960727
//    private FrameLayout table_dt;
//    private ArrayList<String> ListOfAnswers;
//    private String[] ListOfAnswers4show={"","","","",""};
    /////////////////////////////////////
    private ArrayList<Integer> availablePlace;
    private boolean is_gameOver = false;
    private LinearLayout gameover_win;
    private String horoofs[] = {"آ", "ب", "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی", "ئ", "ا"};


    private CustomAdapterList_puzzle_question CALPQ;

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


    private View lastWord = null;
    private int numberWord = 70;

    private ImageView btn_01_dt;
    private ImageView btn_02_dt;
    private ImageView btn_03_dt;
    private LinearLayout windowDown_dt;
    private int height;
    private boolean windowDown_isShowing = false;
    private boolean windowDown2_isShowing = false;
    private ScrollView windowDown_SV_dt;
    private LinearLayout hints_dt;
    //meysam - commented at 13960902
//    private LuckyTextView questions_dt;
    /////////////////////////////////
    //meysam - 13960902
    private ListView list_questions;
    /////////////////////////////////
    private LuckyButton windownClose_dt;
    private LuckyTextViewAutoSize tv_hazel_amount;
    private LuckyTextViewAutoSize tv_luck_amount;

    private ArrayList<QuestionModel> questions;

    private ArrayList<Integer> removedCells;
    //meysam - commented in 13960727
//    private ArrayList<Integer> answeredCells;
//    private ArrayList<Integer> hintedCells;
//    private ArrayList<Integer> fisrtPlaceOfAnswers;
//    private ArrayList<String> userAnswered;
    //////////////////////////////////

    private boolean removeAdditionalCells;
    private boolean showFirstLetter;
    private boolean showFirstLetters;
    private boolean doubleReward;
    private boolean removeAdditionalCell;
    private boolean goToNextTable;
    private boolean back_requested;
    private boolean home_requested;

    private int cost_remove_additional_cell;
    private int cost_show_first_letter;
    private int cost_next_table;
    private int cost_remove_additional_cells;
    private int cost_show_first_letters;

    private int final_hazel_amount;
    private int final_luck_amount;

    private int used_hazel_amount;
    private int used_luck_amount;

    private int reward_hazel_amount;
    private int reward_luck_amount;

    QuestionController qc;
    UserController uc;

    LinearLayout hint_btn_dh_remove_additional_cell;
    LinearLayout hint_btn_dh_ad_remove_additional_cell;
    LinearLayout hint_btn_dh_show_first_letter;
    LinearLayout hint_btn_dh_ad_show_first_letter;
    LinearLayout hint_btn_dh_next_table;
    LinearLayout hint_btn_dh_ad_next_table;
    LinearLayout hint_btn_dh_remove_additional_cells;
    LinearLayout hint_btn_dh_show_first_letters;

    LinearLayout endWin_doubleHazel;
    //    FrameLayout fl_doubleHazel;
    LinearLayout ll_doubleHazel;

    private LuckyTextView endWin_luckAmount;
    private LuckyTextView endWin_hazelAmount;

    private SoundFXModel sfxm = null;

    private DatabaseHandler db;

    private DialogModel_level dml;

    private Animation in_game_wavy;
    private Animation in_game_wavy2;
    private Animation in_game_scale_up;

    Animator anm_hint_ad;
    Animator anm_double_reward;
    Animator anm_hazel;
    Animator anm_luck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darham_table);
        //meysam - commented in 13960727
//        table_dt= (FrameLayout) findViewById(R.id.table_dt);
        ///////////////////////////////
        text_dt = (TextView) findViewById(R.id.text_dt);

        //meysam - 13960727
        db = new DatabaseHandler(this);
        ////////////////////
        in_game_wavy = AnimationUtils.loadAnimation(DarhamTableActivity.this, R.anim.in_game_wavy);
        in_game_wavy2 = AnimationUtils.loadAnimation(DarhamTableActivity.this, R.anim.in_game_wavy2);
        in_game_scale_up = AnimationUtils.loadAnimation(DarhamTableActivity.this, R.anim.in_game_scale_up);

        removeAdditionalCells = false;
        showFirstLetter = false;
        showFirstLetters = false;
        removeAdditionalCell = false;
        goToNextTable = false;
        back_requested = false;
        doubleReward = false;

        reward_hazel_amount = 0;
        reward_luck_amount = 0;

        qc = new QuestionController(this);
        qc.addObserver(this);

        uc = new UserController(this);
        uc.addObserver(this);

        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        if (!session.getBoolianItem(SessionModel.KEY_TANGLED_TABLE_IS_FIRST_TIME, false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
//            session.saveItem(SessionModel.KEY_TANGLED_TABLE_IS_FIRST_TIME,true);

//            DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.dlg_TutorialDarhamTable), getString(R.string.btn_OK), null,false, false);
            DialogModel_help dm_help = new DialogModel_help(DarhamTableActivity.this);
            dm_help.show(4);
        }

        sfxm = new SoundFXModel(getApplicationContext());

        DM = new DialogModel(this);

        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
        endWin_luckAmount = (LuckyTextView) findViewById(R.id.endWin_luckAmount);

        word_01 = (LuckyTextView) findViewById(R.id.word_01);
        word_02 = (LuckyTextView) findViewById(R.id.word_02);
        word_03 = (LuckyTextView) findViewById(R.id.word_03);
        word_04 = (LuckyTextView) findViewById(R.id.word_04);
        word_05 = (LuckyTextView) findViewById(R.id.word_05);
        word_06 = (LuckyTextView) findViewById(R.id.word_06);
        word_07 = (LuckyTextView) findViewById(R.id.word_07);
        word_08 = (LuckyTextView) findViewById(R.id.word_08);
        word_09 = (LuckyTextView) findViewById(R.id.word_09);
        word_10 = (LuckyTextView) findViewById(R.id.word_10);
        word_11 = (LuckyTextView) findViewById(R.id.word_11);
        word_12 = (LuckyTextView) findViewById(R.id.word_12);
        word_13 = (LuckyTextView) findViewById(R.id.word_13);
        word_14 = (LuckyTextView) findViewById(R.id.word_14);
        word_15 = (LuckyTextView) findViewById(R.id.word_15);
        word_16 = (LuckyTextView) findViewById(R.id.word_16);
        word_17 = (LuckyTextView) findViewById(R.id.word_17);
        word_18 = (LuckyTextView) findViewById(R.id.word_18);
        word_19 = (LuckyTextView) findViewById(R.id.word_19);
        word_20 = (LuckyTextView) findViewById(R.id.word_20);
        word_21 = (LuckyTextView) findViewById(R.id.word_21);
        word_22 = (LuckyTextView) findViewById(R.id.word_22);
        word_23 = (LuckyTextView) findViewById(R.id.word_23);
        word_24 = (LuckyTextView) findViewById(R.id.word_24);
        word_25 = (LuckyTextView) findViewById(R.id.word_25);
        word_26 = (LuckyTextView) findViewById(R.id.word_26);
        word_27 = (LuckyTextView) findViewById(R.id.word_27);
        word_28 = (LuckyTextView) findViewById(R.id.word_28);
        word_29 = (LuckyTextView) findViewById(R.id.word_29);
        word_30 = (LuckyTextView) findViewById(R.id.word_30);
        word_31 = (LuckyTextView) findViewById(R.id.word_31);
        word_32 = (LuckyTextView) findViewById(R.id.word_32);
        word_33 = (LuckyTextView) findViewById(R.id.word_33);
        word_34 = (LuckyTextView) findViewById(R.id.word_34);
        word_35 = (LuckyTextView) findViewById(R.id.word_35);
        word_36 = (LuckyTextView) findViewById(R.id.word_36);
        word_37 = (LuckyTextView) findViewById(R.id.word_37);
        word_38 = (LuckyTextView) findViewById(R.id.word_38);
        word_39 = (LuckyTextView) findViewById(R.id.word_39);
        word_40 = (LuckyTextView) findViewById(R.id.word_40);
        word_41 = (LuckyTextView) findViewById(R.id.word_41);
        word_42 = (LuckyTextView) findViewById(R.id.word_42);
        word_43 = (LuckyTextView) findViewById(R.id.word_43);
        word_44 = (LuckyTextView) findViewById(R.id.word_44);
        word_45 = (LuckyTextView) findViewById(R.id.word_45);
        word_46 = (LuckyTextView) findViewById(R.id.word_46);
        word_47 = (LuckyTextView) findViewById(R.id.word_47);
        word_48 = (LuckyTextView) findViewById(R.id.word_48);
        word_49 = (LuckyTextView) findViewById(R.id.word_49);
        word_50 = (LuckyTextView) findViewById(R.id.word_50);
        word_51 = (LuckyTextView) findViewById(R.id.word_51);
        word_52 = (LuckyTextView) findViewById(R.id.word_52);
        word_53 = (LuckyTextView) findViewById(R.id.word_53);
        word_54 = (LuckyTextView) findViewById(R.id.word_54);
        word_55 = (LuckyTextView) findViewById(R.id.word_55);
        word_56 = (LuckyTextView) findViewById(R.id.word_56);
        word_57 = (LuckyTextView) findViewById(R.id.word_57);
        word_58 = (LuckyTextView) findViewById(R.id.word_58);
        word_59 = (LuckyTextView) findViewById(R.id.word_59);
        word_60 = (LuckyTextView) findViewById(R.id.word_60);
        word_61 = (LuckyTextView) findViewById(R.id.word_61);
        word_62 = (LuckyTextView) findViewById(R.id.word_62);
        word_63 = (LuckyTextView) findViewById(R.id.word_63);
        word_64 = (LuckyTextView) findViewById(R.id.word_64);
        word_65 = (LuckyTextView) findViewById(R.id.word_65);
        word_66 = (LuckyTextView) findViewById(R.id.word_66);
        word_67 = (LuckyTextView) findViewById(R.id.word_67);
        word_68 = (LuckyTextView) findViewById(R.id.word_68);
        word_69 = (LuckyTextView) findViewById(R.id.word_69);
        word_70 = (LuckyTextView) findViewById(R.id.word_70);

        btn_01_dt = (ImageView) findViewById(R.id.btn_01_dt);
        btn_02_dt = (ImageView) findViewById(R.id.btn_02_dt);
        btn_03_dt = (ImageView) findViewById(R.id.btn_03_dt);
        windowDown_dt = (LinearLayout) findViewById(R.id.windowDown_dt);
        windowDown_SV_dt = (ScrollView) findViewById(R.id.windowDown_SV_dt);
        hints_dt = (LinearLayout) findViewById(R.id.hints_dt);

        //meysam - commented in 13960902
//        questions_dt= (LuckyTextView) findViewById(R.id.questions_dt);
        ////////////////////////////////
        //meysam - 13960902
        list_questions = (ListView) findViewById(R.id.list_questions);
        //////////////////////

        windownClose_dt = (LuckyButton) findViewById(R.id.windownClose_dt);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        gameover_win = (LinearLayout) findViewById(R.id.gameover_win);

        availablePlace = new ArrayList<>();


        removedCells = new ArrayList<>();

        //meysam - commented in 13960727
//        answeredCells=new ArrayList<>();
//        hintedCells=new ArrayList<>();
//        userAnswered=new ArrayList<>();
//        ListOfAnswers=new ArrayList<>();
//        fisrtPlaceOfAnswers=new ArrayList<>();
        //////////////////////////////////

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("darham_table_activity_broadcast"));


        tv_hazel_amount = (LuckyTextViewAutoSize) findViewById(R.id.tv_hazel_amount);
        tv_luck_amount = (LuckyTextViewAutoSize) findViewById(R.id.tv_luck_amount);

//        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
//        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));


        ImageView iv_luck_amount = findViewById(R.id.iv_luck_amount);
        ImageView iv_hazel_amount = findViewById(R.id.iv_hazel_amount);

        anm_luck = Flubber.with()
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


        if (session.getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
            btn_03_dt.setImageResource(R.drawable.b_sound_on);
        else
            btn_03_dt.setImageResource(R.drawable.b_sound_mute);
        btn_03_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam
                if (SoundModel.isPlaying()) {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY, SoundModel.STOPPED);
                    SoundModel.stopMusic();

                    btn_03_dt.setImageResource(R.drawable.b_sound_mute);
                } else {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY, SoundModel.IS_PLAYING);
//                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                    SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


                    btn_03_dt.setImageResource(R.drawable.b_sound_on);
                }

            }
        });
        btn_01_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_gameOver) return;
                if (windowDown_isShowing) {
                    windowDown_01(false);
                } else {
                    windowDown_01(true);
                }
            }
        });
        btn_02_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_gameOver) return;
                if (windowDown2_isShowing) {
                    windowDown_02(false);
                } else {
                    windowDown_02(true);

                    //  //meysam - commented in 13960727
//                    makeListOfQuestionsDemons();
                    /////////////////////////////
                    //meysam - 13960902 //meysam - commented in 13960727
//                    makeListOfQuestionsDemonsV2();
                    //////////////////////////
                    fillList(questions);
                }
            }
        });
        anm_hint_ad = Flubber.with().listener(flubberHintListener)
                .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1200)  // Last for 1000 milliseconds(1 second)
                .delay(5000)
//                .force((float) 0.5)
                .createFor(btn_01_dt);
        anm_hint_ad.start();// Apply it to the view
        //////////////////////////////////////////

        windownClose_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (windowDown_isShowing) {
                    windowDown_01(false);
                } else if (windowDown2_isShowing) {
                    windowDown_02(false);
                }
            }
        });

//        for(int i=0;i<numberWord;i++){
//            String word_name="word_"+(i>8?"":"0")+(i+1);
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            View v=findViewById(id);
//            v.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if(!is_gameOver) {
//                        int x = (int) event.getRawX();
//                        int y = (int) event.getRawY();
//                        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                            addWord(x, y);
//                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                            v.setBackgroundResource(R.drawable.bg_05_clker);
//                            checkAnswer();
//                            lastWord=null;
//                        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                            v.setBackgroundResource(R.drawable.bg_05_clker2);
//                            addWord(x, y);
//                        }
//                    }
//                    return true;
//                }
//            });
//
//        }


        hint_btn_dh_remove_additional_cell = (LinearLayout) findViewById(R.id.hint_btn_dh_remove_additional_cell);
        hint_btn_dh_ad_remove_additional_cell = (LinearLayout) findViewById(R.id.hint_btn_dh_ad_remove_additional_cell);
        hint_btn_dh_show_first_letter = (LinearLayout) findViewById(R.id.hint_btn_dh_show_first_letter);
        hint_btn_dh_ad_show_first_letter = (LinearLayout) findViewById(R.id.hint_btn_dh_ad_show_first_letter);
        hint_btn_dh_next_table = (LinearLayout) findViewById(R.id.hint_btn_dh_next_table);
        hint_btn_dh_ad_next_table = (LinearLayout) findViewById(R.id.hint_btn_dh_ad_next_table);
        hint_btn_dh_remove_additional_cells = (LinearLayout) findViewById(R.id.hint_btn_dh_remove_additional_cells);
        hint_btn_dh_show_first_letters = (LinearLayout) findViewById(R.id.hint_btn_dh_show_first_letters);
        endWin_doubleHazel = (LinearLayout) findViewById(R.id.endWin_doubleHazel);
//        fl_doubleHazel = findViewById(R.id.fl_doubleHazel);
        ll_doubleHazel = findViewById(R.id.ll_doubleHazel);

        endWin_doubleHazel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam -movie ad

                if (AdvertismentModel.haveMainAdRemained(getApplicationContext())) {

                    DM.show();
                    doubleReward = true;
                    WatchAd();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }


            }
        });
        hint_btn_dh_remove_additional_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(DarhamTableActivity.this, cost_remove_additional_cell)) {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (DialogPopUpModel.isUp()) {
                                    Thread.sleep(500);
                                }
                                if (!DialogPopUpModel.isUp()) {
                                    Thread.currentThread().interrupt();
                                    if (DialogPopUpModel.dialog_result == 1) {
                                        //yes

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (hint_remove5ExtraPlace()) {
                                                    final_hazel_amount -= cost_remove_additional_cell;
                                                    used_hazel_amount += cost_remove_additional_cell;

                                                    session.changeFinalHazel(-1 * cost_remove_additional_cell);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(DarhamTableActivity.this).show(false, false, new Integer(cost_remove_additional_cell));
                                                        }
                                                    });

                                                    Utility.changeCoins(DarhamTableActivity.this, -1 * cost_remove_additional_cell);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                } else {
                                                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
                                                }
                                            }
                                        });


                                    } else {
                                        //no
                                        //do nothing
                                    }
                                    DialogPopUpModel.hide();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, Utility.enToFa("باید حداقل " + cost_remove_additional_cell + " فندق داشته باشی!"), "باشه", null, false, false);
                }
            }
        });
        hint_btn_dh_ad_remove_additional_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam -movie ad

                if (AdvertismentModel.haveMainAdRemained(getApplicationContext())) {
                    DM.show();
                    WatchAd();

                    removeAdditionalCell = true;

                    closeWindows();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }


            }
        });
        hint_btn_dh_show_first_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(DarhamTableActivity.this, cost_show_first_letter)) {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (DialogPopUpModel.isUp()) {
                                    Thread.sleep(500);
                                }
                                if (!DialogPopUpModel.isUp()) {
                                    Thread.currentThread().interrupt();
                                    if (DialogPopUpModel.dialog_result == 1) {
                                        //yes

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // amir - put show one first letter function
//                                                if( hint_putShowOneFirstLetter())//meysam commented in 13960727
                                                if (hintPutShowOneFirstLetterV2()) {
                                                    final_hazel_amount -= cost_show_first_letter;
                                                    used_hazel_amount += cost_show_first_letter;

                                                    session.changeFinalHazel(-1 * cost_show_first_letter);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(DarhamTableActivity.this).show(false, false, new Integer(cost_show_first_letter));
                                                        }
                                                    });

                                                    Utility.changeCoins(DarhamTableActivity.this, -1 * cost_show_first_letter);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
                                                } else {
//                                                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);//meysam commented in 13960727
                                                    //meysam - 13960727
                                                    gameOver();
                                                    ///////////////////
                                                }
                                            }
                                        });


                                    } else {
                                        //no
                                        //do nothing
                                    }
                                    DialogPopUpModel.hide();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, Utility.enToFa("باید حداقل " + cost_show_first_letter + " فندق داشته باشی!"), "باشه", null, false, false);

                }

            }
        });
        hint_btn_dh_ad_show_first_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad
                if (AdvertismentModel.haveMainAdRemained(getApplicationContext())) {
                    DM.show();
                    WatchAd();

                    showFirstLetter = true;

                    closeWindows();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });
        hint_btn_dh_next_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(DarhamTableActivity.this, cost_next_table)) {

                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (DialogPopUpModel.isUp()) {
                                    Thread.sleep(500);
                                }
                                if (!DialogPopUpModel.isUp()) {
                                    Thread.currentThread().interrupt();
                                    if (DialogPopUpModel.dialog_result == 1) {
                                        //yes

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final_hazel_amount -= cost_next_table;
                                                used_hazel_amount += cost_next_table;

                                                session.changeFinalHazel(-1 * cost_next_table);

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new DialogModel_hazel(DarhamTableActivity.this).show(false, false, new Integer(cost_next_table));
                                                    }
                                                });


                                                Utility.changeCoins(DarhamTableActivity.this, -1 * cost_next_table);
                                                tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                //meysam - commented in 13960727
//                                                finishTable();
                                                ////////////////////

                                                //meysam - 13960727
                                                finishTableV2();
                                                ////////////////////
                                            }
                                        });


                                    } else {
                                        //no
                                        //do nothing
                                    }
                                    DialogPopUpModel.hide();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, Utility.enToFa("باید حداقل " + cost_next_table + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });

        hint_btn_dh_ad_next_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad

                if (AdvertismentModel.haveMainAdRemained(getApplicationContext())) {
                    DM.show();
                    WatchAd();

                    goToNextTable = true;

                    closeWindows();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });

        hint_btn_dh_show_first_letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(DarhamTableActivity.this, cost_show_first_letters)) {

                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (DialogPopUpModel.isUp()) {
                                    Thread.sleep(500);
                                }
                                if (!DialogPopUpModel.isUp()) {
                                    Thread.currentThread().interrupt();
                                    if (DialogPopUpModel.dialog_result == 1) {
                                        //yes

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // amir - put show ajj first letters function
//                                                if( hint_putShowAllFirstLetter())//meysam commented in 13960727
                                                //meysam - 13960727
                                                if (hintPutShowAllFirstLetterV2())
                                                ////////////////
                                                {
                                                    final_hazel_amount -= cost_show_first_letters;
                                                    used_hazel_amount += cost_show_first_letters;

                                                    session.changeFinalHazel(-1 * cost_show_first_letters);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(DarhamTableActivity.this).show(false, false, new Integer(cost_show_first_letters));
                                                        }
                                                    });

                                                    Utility.changeCoins(DarhamTableActivity.this, -1 * cost_show_first_letters);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
                                                } else {
                                                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
                                                }
                                            }
                                        });


                                    } else {
                                        //no
                                        //do nothing
                                    }
                                    DialogPopUpModel.hide();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, Utility.enToFa("باید حداقل " + cost_show_first_letters + " فندق داشته باشی!"), "باشه", null, false, false);
                }
            }
        });
        hint_btn_dh_remove_additional_cells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(DarhamTableActivity.this, cost_remove_additional_cells)) {
                    DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (DialogPopUpModel.isUp()) {
                                    Thread.sleep(500);
                                }
                                if (!DialogPopUpModel.isUp()) {
                                    Thread.currentThread().interrupt();
                                    if (DialogPopUpModel.dialog_result == 1) {
                                        //yes

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (hint_removeAllExtraPlace()) {
                                                    final_hazel_amount -= cost_remove_additional_cells;
                                                    used_hazel_amount += cost_remove_additional_cells;

                                                    session.changeFinalHazel(-1 * cost_remove_additional_cells);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(DarhamTableActivity.this).show(false, false, new Integer(cost_remove_additional_cells));
                                                        }
                                                    });
                                                    Utility.changeCoins(DarhamTableActivity.this, -1 * cost_remove_additional_cells);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                } else {
                                                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
                                                }

                                            }
                                        });


                                    } else {
                                        //no
                                        //do nothing
                                    }
                                    DialogPopUpModel.hide();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    DialogPopUpModel.show(DarhamTableActivity.this, Utility.enToFa("باید حداقل " + cost_remove_additional_cells + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });


        DM.show();
        initializeTable();

        //meysam = 13960727
        questions = db.getQuestionsByTag(QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
        ////////////////////

//        questions = session.getTangledTable();//meysam - commented in 13960727
        if (questions.size() == 0) {

            qc.wordTable(0, 0, 5);
        } else {


            //amir - create table based on session
//            DM.show();
            //load anserList from session
            //meysam - commented in 13960727
//            int hint_amount_01=0;
//            for(int i=0;i<questions.size();i++){
//                QuestionModel q=questions.get(i);
//                ListOfAnswers.add(q.getAnswer().replaceAll("\\s+",""));
//                ListOfAnswers4show[i]=q.getAnswer();
//                hint_amount_01+=q.getPenalty();
//                if(q.getAnswered()){
//                    userAnswered.add(q.getAnswer().replaceAll("\\s+",""));
//                }
//            }
            /////////////////////////////////////

            //  //meysam - commented in 13960727
//                    makeListOfQuestionsDemons();
            /////////////////////////////
            //meysam - 13960902//meysam - commented in 13960727
//            makeListOfQuestionsDemonsV2();
            //////////////////////////
            fillList(questions);


            //  //meysam - commented in 13960727
//                    load_cells_inSession();
            /////////////////////////////
            //meysam - 13960727
            load_cells_inSessionV2();
            int hint_amount_01 = MixedTableModel.getAggrigatedHintAmount(questions);
            //////////////////////////


            setHintAmount(hint_amount_01);

            setQuestionsFinalLuckAndHazels();

            DM.hide();
        }
    }

    //meysam - commented in 13960727
//    private boolean hint_putShowOneFirstLetter() {
//        windowDown_01(false);
//        if(is_gameOver) return false;
//        if(hintedCells.size()==fisrtPlaceOfAnswers.size()) return false;
//        ArrayList<Integer> temp_array=new ArrayList<>(fisrtPlaceOfAnswers);
//        ArrayList<Integer> t_temp_array=new ArrayList<>(fisrtPlaceOfAnswers);
//        for(int i=0;i<t_temp_array.size();i++){
//            if(answeredCells.contains(Integer.valueOf(t_temp_array.get(i)))|| hintedCells.contains(Integer.valueOf(t_temp_array.get(i))))
//                temp_array.remove(Integer.valueOf(t_temp_array.get(i)));
//        }
//        if(temp_array.size()<1) return false;
//
//        int pos=temp_array.get(new Random().nextInt(temp_array.size()));
//        String word_name="word_"+(pos>9?"":"0")+pos;
//        int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//        TextView temp= (TextView) findViewById(id);
//        temp.setBackgroundResource(R.drawable.bg_05_clker3);
//        hintedCells.add(pos);
//        session.saveItem(SessionModel.KEY_TANGLED_TABLE_HINTED_CELLS,convertArrayIntToStrig(hintedCells));
//        return true;
//    }

    //meysam - 13960727
    private boolean hintPutShowOneFirstLetterV2() {
        windowDown_01(false);
        if (is_gameOver) return false;
        int nextCellIndex = getNextCellForHint();
        if (nextCellIndex == -1) return false;

        int pos = nextCellIndex;
        String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
        int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
        TextView temp = (TextView) findViewById(id);
        // meysam - check if is last cell -- if it is then turn all cell questions to blue
        if (MixedTableModel.isCellLastCellInAnswer(questions, nextCellIndex)) {
            //meysam - turn all cells of question blue
            QuestionModel cellQuestion = MixedTableModel.getRelatedCellQuestion(questions, nextCellIndex);
            changeCellColors(cellQuestion.getAnswerCells(), "green", null);
        } else {
            temp.setBackgroundResource(R.drawable.diamond_blue);//become blue
        }
//        MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCells(questions, pos);
        db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
        if (MixedTableModel.isAllQuestionsAnswered(questions))
            gameOver();
        return true;
    }

    //meysam - 13960727
    private int getNextCellForHint() {
        int maxCellPos = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getAnswerCells().size() > maxCellPos)
                maxCellPos = questions.get(i).getAnswerCells().size();
        }

        int cellPos = 0;
        while (cellPos < maxCellPos) {

            for (int i = 0; i < questions.size(); i++) {
                List<String> answeredCells;
                if (questions.get(i).getAnsweredCells() != null) {
                    answeredCells = questions.get(i).getAnsweredCells();
                } else {
                    answeredCells = new ArrayList<String>();
                }

                if (questions.get(i).getAnswerCells().size() != answeredCells.size()) {
                    List<String> temp = questions.get(i).getAnswerCells();
                    for (int j = 0; j <= cellPos; j++) {
                        if (temp.size() > j) {
                            if (!answeredCells.contains(temp.get(j))) {
                                MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions, new Integer(temp.get(j)), false);
//                                questions.get(i).addAnsweredCell(temp.get(j));
//                                questions.get(i).addAnsweredLetter(questions.get(i).getAnswer().substring(j,j+1));
                                return new Integer(temp.get(j));
                            }
                        }

                    }
                }
            }
            cellPos++;
        }


        return -1;
    }

    //meysam - 13960727
    private ArrayList<Integer> getFirstCellsForHint() {

        ArrayList<Integer> results = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            List<String> answeredCells;
            if (questions.get(i).getAnsweredCells() != null) {
                answeredCells = questions.get(i).getAnsweredCells();
            } else {
                answeredCells = new ArrayList<String>();
            }

            if (answeredCells.size() == 0) {
                results.add(new Integer(questions.get(i).getAnswerCells().get(0)));

            }
        }

        return results;
    }

    //meysam - commented in 13960727
//    private boolean hint_putShowAllFirstLetter(){
//        windowDown_01(false);
//        if(is_gameOver) return false;
//        if(hintedCells.size()==fisrtPlaceOfAnswers.size()) return false;
//        for(int i=0;i<fisrtPlaceOfAnswers.size();i++){
//            int pos=fisrtPlaceOfAnswers.get(i);
//            String word_name="word_"+(pos>9?"":"0")+pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            TextView temp= (TextView) findViewById(id);
//            temp.setBackgroundResource(R.drawable.bg_05_clker3);
//            if(!hintedCells.contains(Integer.valueOf(pos)))
//                hintedCells.add(pos);
//        }
//        session.saveItem(SessionModel.KEY_TANGLED_TABLE_HINTED_CELLS,convertArrayIntToStrig(hintedCells));
//        return true;
//
//    }

    //meysam - 13960727
    private boolean hintPutShowAllFirstLetterV2() {
        windowDown_01(false);
        if (is_gameOver) return false;
        ArrayList<Integer> firstCellsIndex = getFirstCellsForHint();
        if (firstCellsIndex.size() == 0) return false;

        for (int i = 0; i < firstCellsIndex.size(); i++) {
            int pos = firstCellsIndex.get(i);
            String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setBackgroundResource(R.drawable.diamond_blue);//become blue
            MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions, pos, false);
        }
//        session.saveItem(SessionModel.KEY_TANGLED_TABLE_HINTED_CELLS,convertArrayIntToStrig(hintedCells));
        db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
        return true;
    }

    private boolean hint_removeAllExtraPlace() {
        windowDown_01(false);
        if (is_gameOver) return false;
        if (availablePlace.size() < 1) return false;
        ArrayList<Integer> temp_array = new ArrayList<>(availablePlace);
        for (int k = 0; k < availablePlace.size(); k++) {
            int pos = availablePlace.get(k);
            String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setText("");
            temp.setEnabled(false);
            temp.setVisibility(View.INVISIBLE);
            removedCells.add(pos);
            temp_array.remove(Integer.valueOf(pos));
        }
        availablePlace = temp_array;
        session.saveItem(SessionModel.KEY_TANGLED_TABLE_REMOVED_CELLS, Utility.convertArrayIntToStrig(removedCells));
        session.saveItem(SessionModel.KEY_TANGLED_TABLE_AVAILABLE_CELLS, Utility.convertArrayIntToStrig(availablePlace));
        return true;
    }

    private boolean hint_remove5ExtraPlace() {
        windowDown_01(false);
        if (is_gameOver) return false;
        if (availablePlace.size() < 1) return false;
        ArrayList<Integer> temp_array = new ArrayList<>(availablePlace);
        for (int i = 0; i < removedCells.size(); i++) {
            int value = Integer.valueOf(removedCells.get(i));
            if (availablePlace.contains(value)) {
                temp_array.remove(value);
            }
        }
        Collections.shuffle(temp_array);
        temp_array.subList(temp_array.size() < 10 ? temp_array.size() : 10, temp_array.size()).clear();
        for (int k = 0; k < temp_array.size(); k++) {
            int pos = temp_array.get(k);
            String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setEnabled(false);
            temp.setVisibility(View.INVISIBLE);
            removedCells.add(pos);
            availablePlace.remove(Integer.valueOf(pos));
        }
        session.saveItem(SessionModel.KEY_TANGLED_TABLE_REMOVED_CELLS, Utility.convertArrayIntToStrig(removedCells));
        session.saveItem(SessionModel.KEY_TANGLED_TABLE_AVAILABLE_CELLS, Utility.convertArrayIntToStrig(availablePlace));
        return true;
    }

    //meysam - commenetd in 13960727
//    private void checkAnswer() {
//        String currentAnswer=text_dt.getText().toString();
//        boolean foundWord=false;
//        if(currentAnswer!="" && ListOfAnswers.contains(currentAnswer)) {
//            if(!userAnswered.contains(currentAnswer)) {
////                Utility.displayToast(getApplicationContext(), "درسته", Toast.LENGTH_SHORT);
//                sfxm.play(SoundFXModel.CORRECT, getApplicationContext());
//                for(QuestionModel q:questions)
//                {
//                    if(q.getAnswer().replaceAll("\\s+","").equals(currentAnswer))
//                        q.setAnswered(true);
//                }
//
//                userAnswered.add(currentAnswer);
//                setIsAnswered(currentAnswer);
//            }
//            foundWord = true;
//        }
//        else
//        {
//            sfxm.play(SoundFXModel.WRONG , getApplicationContext());
//        }
//        text_dt.setText("");
//        for(int i=0;i<numberWord;i++){
//            String word_name="word_"+(i>8?"":"0")+(i+1);
//            final int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            findViewById(id).setClickable(true);
//            if(findViewById(id).isPressed() && findViewById(id).isEnabled()){
//                if(foundWord){
////                    findViewById(id).setEnabled(false);
//                    findViewById(id).setBackgroundResource(R.drawable.bg_05_clker3);
//                    answeredCells.add(Integer.valueOf(i+1));
//                }else if(!answeredCells.contains(Integer.valueOf(i+1)) && !hintedCells.contains(Integer.valueOf(i+1))){
//                    findViewById(id).setBackgroundResource(R.drawable.bg_05_clker4);
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            findViewById(id).setBackgroundResource(R.drawable.bg_05_clker);
//                            findViewById(id).setPressed(false);
//                        }
//                    }, 900);
//                }else if(answeredCells.contains(Integer.valueOf(i+1)) || hintedCells.contains(Integer.valueOf(i+1))){
//                    findViewById(id).setBackgroundResource(R.drawable.bg_05_clker3);
//                }
//            }
//        }
//        session.saveItem(SessionModel.KEY_TANGLED_TABLE_ANSWERED_CELLS,convertArrayIntToStrig(answeredCells));
//        if(userAnswered.size()==ListOfAnswers.size()){
//            gameOver();
//        }
//    }

    private void checkAnswerV2() {
        String currentAnswer = text_dt.getText().toString();
        boolean foundWord = false;
        if (currentAnswer != "") {
//                Utility.displayToast(getApplicationContext(), "درسته", Toast.LENGTH_SHORT);

            for (QuestionModel q : questions) {
                if (q.getAnswered() == false) {
                    if (q.getAnswer().replaceAll("\\s+", "").equals(currentAnswer)) {
                        q.setAnswered(true);
                        foundWord = true;

                        q.setAnsweredCells(q.getAnswerCells());
                        q.setAnsweredLetters(new ArrayList(Arrays.asList(q.getAnswer().replaceAll("\\s+", "").split("(?!^)"))));

                    }
                }

            }
        }
        if (foundWord) {
            sfxm.play(SoundFXModel.CORRECT, getApplicationContext());
        } else {
            sfxm.play(SoundFXModel.WRONG, getApplicationContext());
        }
        text_dt.setText("");
        // meysam - we should check if answer string is true but itis not default answer cells we should do something...
        QuestionModel question = MixedTableModel.getRelatedAnswerQuestion(questions, currentAnswer);

        ///////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < numberWord; i++) {
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            final int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            findViewById(id).setClickable(true);
            if (findViewById(id).isPressed() && findViewById(id).isEnabled()) {
                if (foundWord) {
//                    findViewById(id).setBackgroundResource(R.drawable.bg_tetragon_blue);
//                    MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions,Integer.valueOf(i+1));
                    findViewById(id).setBackgroundResource(R.drawable.diamond_orange);
                    findViewById(id).setPressed(false);

                } else if (!MixedTableModel.isCellInAnsweredCells(questions, Integer.valueOf(i + 1))) {
                    Animation in_game_shake = AnimationUtils.loadAnimation(DarhamTableActivity.this, R.anim.in_game_shake);
                    findViewById(id).setBackgroundResource(R.drawable.diamond_red);
                    if (findViewById(id).getAnimation() == null)
                        findViewById(id).startAnimation(in_game_shake);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(id).setBackgroundResource(R.drawable.diamond_orange);
                            findViewById(id).setPressed(false);
                            findViewById(id).clearAnimation();
                        }
                    }, 1100);
                }
//                else if(MixedTableModel.isCellInAnswer(questions,Integer.valueOf(i+1)))
//                {
//
//                    QuestionModel tquestion = MixedTableModel.getRelatedCellQuestion(questions,Integer.valueOf(i+1));
//                    if(tquestion.getAnswered())
//                        findViewById(id).setBackgroundResource(R.drawable.bg_05_clker3);//become green
//                    else
//                        findViewById(id).setBackgroundResource(R.drawable.bg_tetragon_blue);//become blue
//                }
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////////
//        if(foundWord)
//        {
//            //meysam - turn all cells of question green
//            changeCellColors(question.getAnswerCells(),"green");
//        }

        for (QuestionModel tq : questions) {
            if (tq.getAnswered() == true) {
                //meysam - turn all cells of question green
                changeCellColors(tq.getAnswerCells(), "green", null);
            } else {
                if (tq.getAnsweredCells().size() > 0) {
                    //meysam - turn all cells of question blue
                    changeCellColors(tq.getAnsweredCells(), "blue", foundWord);
                }
            }

        }

        /////////////////////////////////////////////////////////////////////////////////////////

        db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
        if (MixedTableModel.isAllQuestionsAnswered(questions)) {
            gameOver();
        }
    }

    //meysam - commenetd in 13960727
//    private void finishTable(){
//
//        endWin_doubleHazel.setEnabled(false);
//
//        endWin_luckAmount.setText("0");
//        endWin_hazelAmount.setText("0");
//
//        for(int pos=1;pos<=numberWord;pos++){
//            String word_name="word_"+(pos>9?"":"0")+pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            TextView temp= (TextView) findViewById(id);
//            if(!availablePlace.contains(Integer.valueOf(pos)))
//                temp.setBackgroundResource(R.drawable.bg_05_clker3);
//        }
//        for(String s:ListOfAnswers){
//            if(!userAnswered.contains(s)){
//                userAnswered.add(s);
//            }
//        }
//        if(userAnswered.size()==ListOfAnswers.size()){
//            gameOver();
//        }
//        windowDown_01(false);
//    }

    private void finishTableV2() {

        if(anm_double_reward != null)
        {
            if(anm_double_reward.isRunning())
                anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }
        endWin_doubleHazel.setEnabled(false);
        endWin_doubleHazel.setVisibility(View.GONE);
        ll_doubleHazel.setVisibility(View.GONE);
        ll_doubleHazel.invalidate();
        endWin_doubleHazel.invalidate();

        endWin_luckAmount.setText("0");
        endWin_hazelAmount.setText("0");

        for (int i = 0; i < questions.size(); i++) {
            if (!questions.get(i).getAnswered()) {
                for (int j = 0; j < questions.get(i).getAnswerCells().size(); j++) {
                    final int pos = new Integer(questions.get(i).getAnswerCells().get(j));
                    if (!MixedTableModel.isCellInAnsweredCells(questions, pos)) {
                        final String word_name = "word_" + (pos > 9 ? "" : "0") + pos;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                                TextView temp = (TextView) findViewById(id);
                                temp.setBackgroundResource(R.drawable.diamond_blue);//become blue
                            }
                        });

                        MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions, j, false);

                    }
                }
                questions.get(i).setAnswered(true);
            }
        }
        if (MixedTableModel.isAllQuestionsAnswered(questions)) {
            fillList(questions);
            gameOver();
        }
        windowDown_01(false);
    }

    private void gameOver() {


        if (MixedTableModel.calculateTrueCellCount(questions) == questions.size()) {
            //meysam - play win table sound
            SoundModel.playSpecificSound(R.raw.victory_match, getApplicationContext(), false,null,null,null);

            //meysam - add score...
            if (Utility.addLevelScore(getApplicationContext(), UserModel.REWARD_AMOUNT_LEVEL_SCORE_DARHAM_TABLE)) {
                //meysam - an increase in level occured...
                // meysam - show earned level animation...
                //meysam - no need to store session variable for show...
                int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()), Integer.valueOf(session.getCurrentUser().getLevelScore()) + session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                dml = new DialogModel_level(DarhamTableActivity.this);
                dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())), Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())) + levelDiffrence);
                /////////////////////////////////////////////
                //meysam - send new level to server ...
                uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                ///////////////////////////////////////

            }

            Random r = new Random();
            int randomInt = r.nextInt(100) + 1;

            if (randomInt < 20)//meysam - must be lower than 20 %...
            {
                endWin_doubleHazel.setEnabled(true);
                endWin_doubleHazel.setVisibility(View.VISIBLE);
                ll_doubleHazel.setVisibility(View.VISIBLE);
                ll_doubleHazel.invalidate();
                endWin_doubleHazel.invalidate();

                anm_double_reward = Flubber.with().listener(flubberDoubleRewardListener)
                        .animation(Flubber.AnimationPreset.POP) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(1000)  // Last for 1000 milliseconds(1 second)
                        .delay(0)
                        .force((float) 0.3)
                        .createFor(ll_doubleHazel);
                anm_double_reward.start();// Apply it to the view
                //////////////////////////////////////////
            } else {
                if (anm_double_reward != null) {
                    if(anm_double_reward.isRunning())
                        anm_double_reward.end();
                    anm_double_reward.cancel();
                    anm_double_reward.removeAllListeners();
                }
                endWin_doubleHazel.setEnabled(false);
                endWin_doubleHazel.setVisibility(View.GONE);
                ll_doubleHazel.setVisibility(View.GONE);
                ll_doubleHazel.invalidate();
                endWin_doubleHazel.invalidate();

            }
        } else {
            if (anm_double_reward != null) {
                if(anm_double_reward.isRunning())
                    anm_double_reward.end();
                anm_double_reward.cancel();
                anm_double_reward.removeAllListeners();
            }
            endWin_doubleHazel.setEnabled(false);
            endWin_doubleHazel.setVisibility(View.GONE);
            ll_doubleHazel.setVisibility(View.GONE);
            ll_doubleHazel.invalidate();
            endWin_doubleHazel.invalidate();
        }

        /////////////////////////////
        session.removeTangledTable();
        //meysam - 13960727
        db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
        ///////////////////

        is_gameOver = true;
        //game over
        for (int i = 0; i < numberWord; i++) {
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp = findViewById(id);
            Random r = new Random();
            int r1_duration = r.nextInt(1000) + 2000;
            int r2_delay = r.nextInt(100) + 1000;
            Flubber.with()
                    .animation(Flubber.AnimationPreset.POP)
                    .interpolator(Flubber.Curve.BZR_EASE_OUT)
                    .repeatCount(1)
                    .repeatMode(ValueAnimator.RESTART)
                    .duration(r1_duration)
                    .delay(r2_delay)
                    .createFor(temp)
                    .start();
        }
        //        Utility.displayToast(getApplicationContext(), "finish", Toast.LENGTH_SHORT);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                windowDown_02(true);

                //meysam - commented in 13960727
//                makeListOfQuestionsDemons();
                ////////////////////////////////

                //meysam - 13960902//meysam - commented in 13960727
//                makeListOfQuestionsDemonsV2();
                /////////////////////////
                fillList(questions);
            }
        }, 4000);
        //buttons of end win
        LinearLayout endWin_pause = (LinearLayout) findViewById(R.id.endWin_pause);
        endWin_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam
                //button for going to home activity
//                if(used_hazel_amount > 0 || used_luck_amount > 0)//  ch:meysam
//                {
                // problem exist
//                    session.saveUsedHazel(used_hazel_amount);//  ch:meysam
//                    session.saveUsedLuck(used_luck_amount);//  ch:meysam
//                    DM.show();
//                    home_requested = true;
//                    uc.decrease(used_hazel_amount,used_luck_amount);
//                }
                if (!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0")) {
                    final_hazel_amount += reward_hazel_amount;
                    final_luck_amount += reward_luck_amount;

                    session.changeFinalHazel(reward_hazel_amount);
                    session.changeFinalLuck(reward_luck_amount);

                    //meysam - add reward to session
                    Utility.changeCoins(getApplicationContext(), reward_hazel_amount);
                    Utility.changeLucks(getApplicationContext(), reward_luck_amount);
                    ///////////////////////////////
                }


                if (session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0) {
//                    session.saveFinalHazel(final_hazel_amount);//  ch:meysam
//                    session.saveFinalLuck(final_luck_amount);//  ch:meysam
                    DM.show();
                    home_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL), session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                } else {
                    Intent i = new Intent(DarhamTableActivity.this, MainActivity.class);
                    DarhamTableActivity.this.startActivity(i);
                    finish();
                }

            }
        });
        LinearLayout endWin_next = (LinearLayout) findViewById(R.id.endWin_next);
        endWin_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0")) {
                    final_hazel_amount += reward_hazel_amount;
                    final_luck_amount += reward_luck_amount;

                    session.changeFinalHazel(reward_hazel_amount);
                    session.changeFinalLuck(reward_luck_amount);

                    //meysam - add reward to session
                    Utility.changeCoins(getApplicationContext(), reward_hazel_amount);
                    Utility.changeLucks(getApplicationContext(), reward_luck_amount);
                    ///////////////////////////////
                }

                // meysam - reload next table
                closeWindows();

                DM.show();
                qc.wordTable(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL), session.getIntegerItem(SessionModel.KEY_FINAL_LUCK), 5);
                clearAll();
                initializeTable();
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
            }
        });
    }

    private boolean isNextValidePosition(TextView onView, View lastWord) {

        int lw_index = Integer.parseInt(String.valueOf(lastWord.getTag()));
        int ov_index = Integer.parseInt(String.valueOf(onView.getTag()));

        if (lw_index == 1) if (ov_index == 2 || ov_index == 8) return true;
        else if (lw_index == 7) if (ov_index == 6 || ov_index == 14) return true;
        else if (lw_index == 64) if (ov_index == 57 || ov_index == 65) return true;
        else if (lw_index == 70) if (ov_index == 69 || ov_index == 63) return true;
        else if (lw_index == 2 || lw_index == 3 || lw_index == 4 || lw_index == 5 || lw_index == 6) {
            if (lw_index + 1 == ov_index || lw_index - 1 == ov_index || lw_index + 7 == ov_index) {
                return true;
            }
        } else if (lw_index == 65 || lw_index == 66 || lw_index == 67 || lw_index == 68 || lw_index == 69) {
            if (lw_index + 1 == ov_index || lw_index - 1 == ov_index || lw_index - 7 == ov_index) {
                return true;
            }
        } else if (lw_index == 8 || lw_index == 15 || lw_index == 22 || lw_index == 29 || lw_index == 36 || lw_index == 43 || lw_index == 50 || lw_index == 57) {
            if (lw_index + 1 == ov_index || lw_index + 7 == ov_index || lw_index - 7 == ov_index) {
                return true;
            }
        } else if (lw_index == 14 || lw_index == 21 || lw_index == 28 || lw_index == 35 || lw_index == 42 || lw_index == 49 || lw_index == 56 || lw_index == 63) {
            if (lw_index - 1 == ov_index || lw_index + 7 == ov_index || lw_index - 7 == ov_index) {
                return true;
            }
        }
        if (lw_index + 1 == ov_index || lw_index - 1 == ov_index || lw_index + 7 == ov_index || lw_index - 7 == ov_index) {
            return true;
        }
        return false;
    }

    private void addWord(int x, int y) {
        TextView onView = (TextView) whichOne(x, y);
        if (onView == null || !onView.isClickable()) return;
        onView.setBackgroundResource(R.drawable.diamond_gray);
        onView.setPressed(true);
        if (onView != null && onView != lastWord) {
            if (lastWord != null) if (!isNextValidePosition(onView, lastWord)) return;
            lastWord = onView;
            onView.setClickable(false);
            if (!onView.getText().toString().equals(""))
                text_dt.setText(text_dt.getText().toString() + onView.getText().toString());
            else {
                text_dt.setText("");

                //meysam - commeneted in 13960727
//                checkAnswer();
                ///////////////

                //meysam - 13960727
                checkAnswerV2();
                /////////////////////
            }
        }
    }

    public View whichOne(int x, int y) {
        for (int i = 0; i < numberWord; i++) {
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp = findViewById(id);
            if (isViewInBounds(temp, x, y)) {
                return temp;
            }
        }
        return null;
    }


    Rect outRect = new Rect();
    int[] location = new int[2];

    private boolean isViewInBounds(View view, int x, int y) {
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    //meysam - commented in 13960727
//    private void makeTable() {
//
//        //add all place as available place
//        for(int i=0;i<numberWord;i++){
//            availablePlace.add(i+1);
//        }
//
//        //for all Answers do
//        for(String answer:ListOfAnswers){
//            int count_try=0;
//            while(count_try<1000){
//                boolean is_word_set=false;
//                ArrayList<Integer> used_availablePlace=new ArrayList<>();
//                //break word
//                ArrayList<String> words=new ArrayList<>();
//                for(int j=0;j<answer.length();j++){
//                    words.add(answer.substring(j,j+1));
//                }
//                //get randomly fisrt place to start
//                int pos = availablePlace.get(new Random().nextInt(availablePlace.size()));
//                int temp_pos=pos;
//                //is available?
//                if(!is_place_available(pos)) continue;
//                //set letter in place
//                for(int j=0;j<answer.length();j++) {
//                    String word_name="word_"+(pos>9?"":"0")+pos;
//                    int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//                    TextView temp= (TextView) findViewById(id);
//                    temp.setText(words.get(j));
//                    //remove place from available
//                    availablePlace.remove(Integer.valueOf(pos));
//                    used_availablePlace.add(Integer.valueOf(pos));
//                    //dont need to find next pos after last letter
//                    if(j==answer.length()-1) {is_word_set=true; break;}
//                    //1=up 2=left 3=down 4=right
//                    int  n = new Random().nextInt(4) + 1;
//                    boolean is_next_pos_ok=false;
//                    for(int k=0;k<4;k++){
//                        if(n==1){
//                            if(is_up_available(pos))
//                            {
//                                pos=pos-7;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=2;
//                                continue;
//                            }
//                        }else if(n==2){
//                            if(is_left_available(pos))
//                            {
//                                pos=pos-1;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=3;
//                                continue;
//                            }
//                        }else if(n==3){
//                            if(is_down_available(pos))
//                            {
//                                pos=pos+7;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=4;
//                                continue;
//                            }
//                        }else{
//                            if(is_right_available(pos))
//                            {
//                                pos=pos+1;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=1;
//                                continue;
//                            }
//                        }
//                    }
//                    if(!is_next_pos_ok) break;
//
//                }
//                if(!is_word_set){
//                    availablePlace.addAll(used_availablePlace);
//                    resetWords(used_availablePlace);
//                    continue;
//                }else{
//                    fisrtPlaceOfAnswers.add(temp_pos);
//                    break;
//                }
//            }
//            if(count_try==1000){
//                //unsucessful to make a table
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
//            }
//        }
//
//        //fill all extra place with random letters
//        for(int k=0;k<availablePlace.size();k++){
//            int pos=availablePlace.get(k);
//            String word_name="word_"+(pos>9?"":"0")+pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            TextView temp= (TextView) findViewById(id);
//            temp.setText(horoofs[new Random().nextInt(32)]);
//        }
//
//
//
//        //amir: save cells to session
//
//        save_cells_inSession();
//    }


    private void save_cells_inSession() {
        String cells = "";
        for (int pos = 1; pos <= numberWord; pos++) {
            String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            cells += temp.getText().toString();
        }
        //amir - set three arrays
//        session.saveTangledTable(questions,cells,convertArrayIntToStrig(removedCells),
//                convertArrayIntToStrig(answeredCells),convertArrayIntToStrig(hintedCells),
//                convertArrayIntToStrig(fisrtPlaceOfAnswers), convertArrayIntToStrig(availablePlace));//meysam - commented in 13960727

        //meysam - 13960727
        session.saveTangledTableV2(cells, Utility.convertArrayIntToStrig(removedCells), Utility.convertArrayIntToStrig(availablePlace));
        db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
        ////////////////////
    }

    //meysam - commented in 13960727
//    private void load_cells_inSession() {
//        String cells = session.getStringItem(SessionModel.KEY_TANGLED_TABLE_CELLS);
//        String[] temp_removedCells= session.getStringItem(KEY_TANGLED_TABLE_REMOVED_CELLS).split("_");
//        String[] temp_answeredCells= session.getStringItem(KEY_TANGLED_TABLE_ANSWERED_CELLS).split("_");
//        String[] temp_hintedCells= session.getStringItem(KEY_TANGLED_TABLE_HINTED_CELLS).split("_");
//        String[] temp_firstLetters= session.getStringItem(SessionModel.KEY_TANGLED_TABLE_FIRST_LETTER_CELLS).split("_");
//        String[] temp_availableCells= session.getStringItem(KEY_TANGLED_TABLE_AVAILABLE_CELLS).split("_");
//        for(int pos=0;pos<numberWord;pos++){
////            if(cells.length() > pos)
////            {
//                String word_name="word_"+(pos>8?"":"0")+(pos+1);
//                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//                TextView temp= (TextView) findViewById(id);
//                temp.setText(cells.substring(pos,pos+1));
////            }
//
//        }
//        int temp_pos=0;
//        if(!temp_removedCells[0].equals(""))
//        for(int i=0;i<temp_removedCells.length;i++){
//            temp_pos=Integer.valueOf(temp_removedCells[i]);
//            removedCells.add(temp_pos);
//
//            String word_name = "word_" + (temp_pos > 9 ? "" : "0") + temp_pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            TextView temp = (TextView) findViewById(id);
//            temp.setText("");
//            temp.setEnabled(false);
//            temp.setVisibility(View.INVISIBLE);
//        }
//        if(!temp_answeredCells[0].equals(""))
//        for(int i=0;i<temp_answeredCells.length;i++){
//            temp_pos=Integer.valueOf(temp_answeredCells[i]);
//            answeredCells.add(temp_pos);
//
//            String word_name = "word_" + (temp_pos > 9 ? "" : "0") + temp_pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            findViewById(id).setBackgroundResource(R.drawable.bg_05_clker3);
//        }
//        if(!temp_hintedCells[0].equals(""))
//        for(int i=0;i<temp_hintedCells.length;i++){
//            temp_pos=Integer.valueOf(temp_hintedCells[i]);
//            hintedCells.add(temp_pos);
//
//            String word_name = "word_" + (temp_pos > 9 ? "" : "0") + temp_pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            findViewById(id).setBackgroundResource(R.drawable.bg_05_clker3);
//        }
//        if(!temp_firstLetters[0].equals(""))
//        for(int i=0;i<temp_firstLetters.length;i++){
//            fisrtPlaceOfAnswers.add(Integer.valueOf(temp_firstLetters[i]));
//        }
//        if(!temp_availableCells[0].equals(""))
//        for(int i=0;i<temp_availableCells.length;i++){
//            availablePlace.add(Integer.valueOf(temp_availableCells[i]));
//        }
//        DM.hide();
//    }

    //meysam - 13960727
    private void load_cells_inSessionV2() {
        String cells = session.getStringItem(SessionModel.KEY_TANGLED_TABLE_CELLS);
        String[] temp_removedCells = session.getStringItem(KEY_TANGLED_TABLE_REMOVED_CELLS).split("_");

        String[] temp_availableCells = session.getStringItem(KEY_TANGLED_TABLE_AVAILABLE_CELLS).split("_");
        for (int pos = 0; pos < numberWord; pos++) {

            String word_name = "word_" + (pos > 8 ? "" : "0") + (pos + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setText(cells.substring(pos, pos + 1));

        }
        int temp_pos = 0;
        if (!temp_removedCells[0].equals(""))
            for (int i = 0; i < temp_removedCells.length; i++) {
                temp_pos = Integer.valueOf(temp_removedCells[i]);
                removedCells.add(temp_pos);

                String word_name = "word_" + (temp_pos > 9 ? "" : "0") + temp_pos;
                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                TextView temp = (TextView) findViewById(id);
                temp.setText("");
                temp.setEnabled(false);
                temp.setVisibility(View.INVISIBLE);
            }
        for (int i = 0; i < questions.size(); i++) {
            for (int j = 0; j < questions.get(i).getAnsweredCells().size(); j++) {
                temp_pos = Integer.valueOf(questions.get(i).getAnsweredCells().get(j));

                String word_name = "word_" + (temp_pos > 9 ? "" : "0") + temp_pos;
                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                if (questions.get(i).getAnswered()) {
                    findViewById(id).setBackgroundResource(R.drawable.diamond_green);//become green
                    findViewById(id).setAnimation(in_game_wavy2);
                } else {
                    findViewById(id).setBackgroundResource(R.drawable.diamond_blue);//become blue
                    findViewById(id).setAnimation(in_game_scale_up);
                }
            }
        }

        if (!temp_availableCells[0].equals(""))
            for (int i = 0; i < temp_availableCells.length; i++) {
                availablePlace.add(Integer.valueOf(temp_availableCells[i]));
            }
        DM.hide();
        for(int i=0;i<numberWord;i++){
            String word_name="word_"+(i>8?"":"0")+(i+1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp=findViewById(id);
            Random r = new Random();
            int r1_duration = r.nextInt(1000) + 20;
            int r2_delay = r.nextInt(100) + 10;
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN)
                    .interpolator(Flubber.Curve.BZR_EASE_OUT)
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
    }


    private void setQuestionsFinalLuckAndHazels() {


        for (int i = 0; i < questions.size(); i++) {
            // meysam - set final rewards for each question from max and min values

            if (questions.get(i).getMaxHazelReward() != null) {
                if ((questions.get(i).getMaxHazelReward() - questions.get(i).getMinHazelReward()) <= 0)
                    questions.get(i).setFinalHazelReward(questions.get(i).getMaxHazelReward());
                else
                    questions.get(i).setFinalHazelReward(new Random().nextInt(questions.get(i).getMaxHazelReward() - questions.get(i).getMinHazelReward()) + questions.get(i).getMinHazelReward());
                if ((questions.get(i).getMaxLuckReward() - questions.get(i).getMinLuckReward()) <= 0)
                    questions.get(i).setFinalLuckReward(questions.get(i).getMaxLuckReward());
                else
                    questions.get(i).setFinalLuckReward(new Random().nextInt(questions.get(i).getMaxLuckReward() - questions.get(i).getMinLuckReward()) + questions.get(i).getMinLuckReward());

            }
            //////////////////////////////////////////////////
            reward_hazel_amount += questions.get(i).getFinalHazelReward();
            reward_luck_amount += questions.get(i).getFinalLuckReward();
        }

        endWin_hazelAmount.setText(Utility.enToFa(reward_hazel_amount + ""));
        endWin_luckAmount.setText(Utility.enToFa(reward_luck_amount + ""));
    }

    //  //meysam - commented in 13960727
//    private void makeListOfQuestionsDemons(){
//        String temp="";
//        int count=0;
//        for(QuestionModel q:questions){
//            temp=temp+"\n "+Utility.enToFa((++count)+"")+"-"+q.getDescription()+" ("+q.getAnswer().replaceAll("\\s+","").length()+" حرفی)";
//            if(userAnswered.contains(q.getAnswer().replaceAll("\\s+",""))){
//                temp=temp+"\n جواب:"+ListOfAnswers4show[count-1]+"\n";
//            }else{
//                temp=temp+"\n جواب:"+"..."+"\n";
//            }
//        }
//        questions_dt.setText(temp);
//    }

    //meysam - 13960902
//    private void makeListOfQuestionsDemonsV2(){
//        String temp="";
//        int count=0;
//        for(QuestionModel q:questions){
//            temp=temp+"\n "+Utility.enToFa((++count)+"")+"-"+q.getDescription()+" ("+q.getAnswer().replaceAll("\\s+","").length()+" حرفی)";
//            if(q.getAnswered() || is_gameOver)
//            {
//                temp=temp+"\n جواب:"+q.getAnswer()+"\n";
//            }
////            else if( is_gameOver)
////            {
////                temp=temp+"\n جواب:"+q.getAnswer()+"\n";
////            }
//            else
//            {
//                if(q.getAnsweredCells().size() > 0)
//                {
//                    temp=temp+"\n جواب:"+q.getAnsweredLettersAsString()+"..."+"\n";
//                }
//                else
//                {
//                    temp=temp+"\n جواب:"+"..."+"\n";
//                }
//            }
//        }
//        questions_dt.setText(temp);
//    }

    private void resetWords(ArrayList<Integer> used_availablePlace) {
        for (int pos : used_availablePlace) {
            String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setText("");
        }
    }

    //meysam - commented in 13960727
//    private boolean is_place_available(int i){
//        if(availablePlace.contains(Integer.valueOf(i)))
//            return true;
//        return false;
//    }
//    private boolean is_up_available(int i){
//        int up=i-7;
//        if(up>0 && availablePlace.contains(Integer.valueOf(up)))
//            return true;
//        return false;
//    }
//    private boolean is_down_available(int i){
//        int down=i+7;
//        if(down<71 && availablePlace.contains(Integer.valueOf(down)))
//            return true;
//        return false;
//    }
//    private boolean is_left_available(int i){
//        if(i==1 || i==8 || i==15 || i==22 || i==29 || i==36 || i==43 || i==50 || i==57 || i==64)
//            return false;
//        int left=i-1;
//        if(availablePlace.contains(Integer.valueOf(left)))
//            return true;
//        return false;
//
//    }
//    private boolean is_right_available(int i){
//        if(i==7 || i==14 || i==21 || i==28 || i==35 || i==42 || i==49 || i==56 || i==63 || i==70)
//            return false;
//        int right=i+1;
//        if(availablePlace.contains(Integer.valueOf(right)))
//            return true;
//        return false;
//    }
    //////////////////////////////////
    private void windowDown_02(boolean show) {
        if (show) {
            if (windowDown_isShowing) {
                windowDown_isShowing = false;
                Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
                windowDown_dt.setVisibility(View.GONE);
                windowDown_SV_dt.setVisibility(View.GONE);
                hints_dt.setVisibility(View.GONE);
                //meysam - commented at 13960902
//                questions_dt.setVisibility(View.GONE);
                //////////////////////////////////
                //meysam - 13960902
                list_questions.setVisibility(View.GONE);
                //////////////////////
                windownClose_dt.setVisibility(View.GONE);
            }
            windowDown2_isShowing = true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            //meysam - commented at 13960902
//            questions_dt.setVisibility(View.VISIBLE);
            ////////////////////////////////
            //meysam - 13960902

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    list_questions.setVisibility(View.VISIBLE);
                    list_questions.smoothScrollToPosition(0);
                    list_questions.setSelection(0);
                }
            });

            //////////////////////
            windownClose_dt.setVisibility(View.VISIBLE);

            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);
            if (is_gameOver) {
                gameover_win.setVisibility(View.VISIBLE);
                windownClose_dt.setVisibility(View.GONE);
                windowDown_SV_dt.post(new Runnable() {
                    @Override
                    public void run() {
                        windowDown_SV_dt.fullScroll(View.FOCUS_UP);
                    }
                });
            }
        } else {
            windowDown2_isShowing = false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            //meysam - commented at 13960902
//            questions_dt.setVisibility(View.GONE);
            ///////////////////////////////////
            //meysam - 13960902
            list_questions.setVisibility(View.GONE);
            //////////////////////
            windownClose_dt.setVisibility(View.GONE);
            gameover_win.setVisibility(View.GONE);
        }
    }

    private void windowDown_01(boolean show) {
        if (show) {
            if (windowDown2_isShowing) {
                windowDown2_isShowing = false;
                Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
                windowDown_dt.setVisibility(View.GONE);
                windowDown_SV_dt.setVisibility(View.GONE);
                hints_dt.setVisibility(View.GONE);
                //meysam - commented at 13960902
//                questions_dt.setVisibility(View.GONE);
                //////////////////////////////////
                //meysam - 13960902
                list_questions.setVisibility(View.GONE);
                //////////////////////
                windownClose_dt.setVisibility(View.GONE);
            }
            windowDown_isShowing = true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            hints_dt.setVisibility(View.VISIBLE);
            windownClose_dt.setVisibility(View.VISIBLE);
            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);
        } else {
            windowDown_isShowing = false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            //meysam - commented at 13960902
//            questions_dt.setVisibility(View.GONE);
            ///////////////////////////////
            //meysam - 13960902
            list_questions.setVisibility(View.GONE);
            //////////////////////
            windownClose_dt.setVisibility(View.GONE);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
        DM.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(DarhamTableActivity.this, MainActivity.class);
                    DarhamTableActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            } else if (arg instanceof ArrayList) {
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_WORD_TABLE_QUESTION)) {
//                    showLoading();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DM.show();
                        }
                    });
                    if (!session.getBoolianItem(SessionModel.KEY_TANGLED_TABLE_IS_FIRST_TIME, false)) {
                        // Do first run stuff here then set 'firstrun' as false
                        // using the following line to edit/commit prefs
                        session.saveItem(SessionModel.KEY_TANGLED_TABLE_IS_FIRST_TIME, true);
                    }
//                    resetAll();
                    session.removeTangledTable();

                    //meysam - 13960727
                    db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
                    ///////////////////

                    session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                    session.removeItem(SessionModel.KEY_FINAL_LUCK);

                    questions = (ArrayList<QuestionModel>) ((ArrayList) arg).get(1);
                    if (questions.size() < 1) {
                        Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                        Intent i = new Intent(DarhamTableActivity.this, MainActivity.class);
                        DarhamTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                    setQuestionsFinalLuckAndHazels();

                    int hint_amount_01 = 0;
                    for (int m = 0; m < questions.size(); m++) {
                        QuestionModel q = questions.get(m);
                        //meysam - commented in 13960727
//                        ListOfAnswers.add(q.getAnswer().replaceAll("\\s+",""));
//                        ListOfAnswers4show[m]=q.getAnswer();
                        ///////////////////////////////////

                        //find amount of hints
                        hint_amount_01 += q.getPenalty();
                    }

                    setHintAmount(hint_amount_01);
                    fillList(questions);
                    //////////////////////////////////////////

                    //make tabel
//                    makeTable();//commented by meysam in 13960727


                    //meysam - 13960727
                    makeTableV2();
                    ///////////////////


                }
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER)) {
//                    session.decreaseHazel(session.getStringItem(SessionModel.KEY_TANGLED_TABLE_USED_HAZEL));
//                    session.decreaseLuck(session.getStringItem(SessionModel.KEY_TANGLED_TABLE_USED_LUCK));

                    session.removeItem(SessionModel.KEY_TANGLED_TABLE_USED_HAZEL);
                    session.removeItem(SessionModel.KEY_TANGLED_TABLE_USED_LUCK);

                    if (back_requested) {
                        back_requested = false;
                        Utility.finishActivity(this);
                    }
                    if (home_requested) {
                        home_requested = false;
                        Intent i = new Intent(DarhamTableActivity.this, MainActivity.class);
                        DarhamTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                }
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER)) {

                    session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                    session.removeItem(SessionModel.KEY_FINAL_LUCK);

                    if (back_requested) {
                        back_requested = false;
                        Utility.finishActivity(this);
                    }
                    if (home_requested) {
                        home_requested = false;
                        Intent i = new Intent(DarhamTableActivity.this, MainActivity.class);
                        DarhamTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                }
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_LEVEL_USER)) {
                    if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {
                        //meysam - reset KEY_LEVEL_COUNT_FOR_SERVER_UPDATE key in session if successful...
                        //meysam - update user level in session....
//                        Integer levelsToAdd = session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE);
//                        Integer currentLevel = Integer.valueOf(session.getCurrentUser().getLevelScore());
//                        session.saveItem(SessionModel.KEY_LEVEL_SCORE,String.valueOf(levelsToAdd+currentLevel));
                        session.saveItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE, new Integer(0));
                    } else {
                        //meysam - error in updating level in server ... do nothing for now...
                    }

                }
//                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_ADVERTISMENT_HOME))
//                {
//
//                }
            } else if (arg instanceof Integer) {
                if (Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
//                    SessionModel session = new SessionModel(getApplicationContext());
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    Utility.finishActivity(this);
                } else {
                    Utility.displayToast(getApplicationContext(), new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            } else {
                Utility.displayToast(getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        } else {
            Utility.displayToast(getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    private void setHintAmount(int hint_amount_01) {

        TextView hint_dh_remove_additional_cell = (TextView) findViewById(R.id.hint_dh_remove_additional_cell);
        cost_remove_additional_cell = (int) (hint_amount_01 / 14);
        hint_dh_remove_additional_cell.setText(Utility.enToFa("-" + (cost_remove_additional_cell)));

        TextView hint_dh_show_first_letter = (TextView) findViewById(R.id.hint_dh_show_first_letter);
        cost_show_first_letter = (int) (hint_amount_01 / 8);
        hint_dh_show_first_letter.setText(Utility.enToFa("-" + (cost_show_first_letter)));

        TextView hint_dh_next_table = (TextView) findViewById(R.id.hint_dh_next_table);
        cost_next_table = (int) (hint_amount_01 / 9);
        hint_dh_next_table.setText(Utility.enToFa("-" + (cost_next_table)));

        //amir - set costs
        TextView hint_dh_remove_additional_cells = (TextView) findViewById(R.id.hint_dh_remove_additional_cells);
        cost_remove_additional_cells = (int) (hint_amount_01 / 4);
        hint_dh_remove_additional_cells.setText(Utility.enToFa("-" + cost_remove_additional_cells));

        TextView hint_dh_show_first_letters = (TextView) findViewById(R.id.hint_dh_show_first_letters);
        cost_show_first_letters = (int) (hint_amount_01 / 5);
        hint_dh_show_first_letters.setText(Utility.enToFa("-" + cost_show_first_letters));
    }

    private void WatchAd() {
        new SoundModel(this).stopMusic();

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(DarhamTableActivity.this, AdvertismentModel.TangledTablePageZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError(String error) {
                DM.hide();
                Utility.displayToast(DarhamTableActivity.this, getApplicationContext().getString(R.string.error_show_advertisment), Toast.LENGTH_SHORT);

//                new SoundModel(DarhamTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


            }

            @Override
            public void onAdAvailable(TapsellAd ad) {
                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) DarhamTableActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable() {
                DM.hide();
                Utility.displayToast(DarhamTableActivity.this, getApplicationContext().getString(R.string.error_no_advertisment), Toast.LENGTH_SHORT);
//                new SoundModel(DarhamTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }

            @Override
            public void onNoNetwork() {
                DM.hide();
                Utility.displayToast(DarhamTableActivity.this, getApplicationContext().getString(R.string.error_no_connection), Toast.LENGTH_SHORT);

//                new SoundModel(DarhamTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }

            @Override
            public void onExpiring(TapsellAd ad) {
                DM.hide();
                Utility.displayToast(DarhamTableActivity.this, getApplicationContext().getString(R.string.error_advertisment_expired), Toast.LENGTH_SHORT);

//                new SoundModel(DarhamTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

//                new SoundModel(DarhamTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                if (completed) {

                    AdvertismentModel.increaseTodaySpentAd(getApplicationContext(), 1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                    if (AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(DarhamTableActivity.this, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()) + " " + getString(R.string.msg_AdCountRemained)), getString(R.string.btn_OK), null, false, false);
                    else
                        DialogPopUpModel.show(DarhamTableActivity.this, getString(R.string.msg_AdLastWatched), getString(R.string.btn_OK), null, false, false);

                    // store user reward if ad.isRewardedAd() and completed is true
                    // Meysam: send Reward To server and store it in session .... meysam
//                    Utility.displayToast(context,getApplicationContext().getString(R.string.msg_AdvertismentShowCompleted),Toast.LENGTH_SHORT);

                    if (removeAdditionalCells) {
                        removeAdditionalCells = false;
                        if (!hint_removeAllExtraPlace())
                            Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);


                    } else if (removeAdditionalCell) {
                        removeAdditionalCell = false;
                        // meysam - remove some cells
                        if (!hint_remove5ExtraPlace())
                            Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);

                    } else if (showFirstLetters) {
                        showFirstLetters = false;
//                                if(!hint_putShowAllFirstLetter())//meysam commented in 13960727
//                                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);//meysam commented in 13960727

                        //meysam - 13960727
                        if (!hintPutShowAllFirstLetterV2())
                            Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
                        ///////////////////

                    } else if (showFirstLetter) {
                        showFirstLetter = false;
                        // meysam - show one first letter
//                                if(!hint_putShowOneFirstLetter())//meysam commented on 13960727
//                                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);//meysam commented on 13960727

                        //meysam - 13960727
                        if (!hintPutShowOneFirstLetterV2())
                            gameOver();
                        ///////////////////

                    } else if (doubleReward) {

                        if (anm_double_reward != null) {
                            if(anm_double_reward.isRunning())
                                anm_double_reward.end();
                            anm_double_reward.cancel();
                            anm_double_reward.removeAllListeners();
                        }
                        endWin_doubleHazel.setEnabled(false);
                        endWin_doubleHazel.setVisibility(View.GONE);
                        ll_doubleHazel.setVisibility(View.GONE);
                        ll_doubleHazel.invalidate();
                        endWin_doubleHazel.invalidate();

                        // meysam - double reward hazel
                        reward_hazel_amount *= 2;

                        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
                        endWin_hazelAmount.setText(Utility.enToFa(reward_hazel_amount + ""));

                        doubleReward = false;

                    } else {
                        //meysam - go to next table
                        goToNextTable = false;

                        //meysam - commented in 13960727
//                                finishTable();
                        ///////////////////////

                        //meysam - 13960727
                        finishTableV2();
                        ////////////////////
                    }
                } else {
                    Utility.displayToast(DarhamTableActivity.this, getApplicationContext().getString(R.string.error_advertisment_faulty), Toast.LENGTH_SHORT);
                }

            }
        });
    }

    public void onDestroy() {

        clearAll();

        CustomAdapterList_puzzle_question.setInflater(null);


        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        if (DM != null) {
            DM.dismiss();
            DM = null;
        }


        sfxm.releaseSoundPool();
        sfxm = null;

        qc.deleteObservers();
        qc = null;

        uc.deleteObservers();
        uc = null;

        Tapsell.setRewardListener(null);

        if (dml != null) {
            dml.dismiss();
            dml = null;
        }

        if (anm_hint_ad != null) {
            anm_hint_ad.end();
            anm_hint_ad.cancel();
            anm_hint_ad.removeAllListeners();
        }


        if (anm_double_reward != null) {
            if (anm_double_reward.isRunning())
                anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }

        if (anm_hazel != null) {
            anm_hazel.end();
            anm_hazel.cancel();
            anm_hazel.removeAllListeners();
        }

        if (anm_luck != null) {
            anm_luck.end();
            anm_luck.cancel();
            anm_luck.removeAllListeners();
        }


        flubberHintListener = null;
        flubberDoubleRewardListener = null;

        super.onDestroy();

    }

    private void closeWindows() {
        if (windowDown_isShowing) {
            windowDown_01(false);
        } else if (windowDown2_isShowing) {
            windowDown_02(false);
        }
    }

//    private void resetAll()
//    {
//        final_luck_amount = 0;
//        final_hazel_amount = 0;
//        used_hazel_amount = 0;
//        used_luck_amount = 0;
//
//        cost_next_table = 0;
//        cost_remove_additional_cell = 0;
//        cost_show_first_letter = 0;
//    }

    @Override
    public void onBackPressed() {

        if (!windowDown_isShowing && !windowDown2_isShowing) {

            //meysam - set three arrayes
//            session.saveTangledTable(questions,null,convertArrayIntToStrig(removedCells),
//                    convertArrayIntToStrig(answeredCells),convertArrayIntToStrig(hintedCells),
//                    convertArrayIntToStrig(fisrtPlaceOfAnswers), convertArrayIntToStrig(availablePlace));//meysam - commented in 13960727

            //meysam - 13960727
            session.saveTangledTableV2(null, Utility.convertArrayIntToStrig(removedCells), Utility.convertArrayIntToStrig(availablePlace));
            db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
            ////////////////////


//            if(used_hazel_amount > 0 ||
//                    used_luck_amount > 0  )
//            {
//                session.saveItem(SessionModel.KEY_TANGLED_TABLE_USED_HAZEL,used_hazel_amount);
//                session.saveItem(SessionModel.KEY_TANGLED_TABLE_USED_LUCK, used_luck_amount);
//
//                DM.show();
//                back_requested = true;
//                uc.decrease(used_hazel_amount,used_luck_amount);
//            }
            if (session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 ||
                    session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0) {
//                session.saveFinalHazel(final_hazel_amount);
//                session.saveFinalLuck( final_luck_amount);

                DM.show();
                back_requested = true;
                uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL), session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
            } else {
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

    public void setIsAnswered(String currentAnswer) {
        for (QuestionModel q : questions) {
            if (q.getAnswer().replaceAll("\\s+", "").equals(currentAnswer)) {
                q.setAnswered(true);
                break;
            }
        }
    }


    private void clearAll() {
        for (int i = 0; i < numberWord; i++) {
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            findViewById(id).clearAnimation();
        }

        if (anm_double_reward != null )
        {
            if(anm_double_reward.isRunning())
                anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }
        endWin_doubleHazel.setEnabled(false);
        endWin_doubleHazel.setVisibility(View.GONE);
        ll_doubleHazel.setVisibility(View.GONE);
        ll_doubleHazel.invalidate();
        endWin_doubleHazel.invalidate();

        final_hazel_amount = 0;
        final_luck_amount = 0;

        reward_luck_amount = 0;
        reward_hazel_amount = 0;

        used_hazel_amount = 0;
        used_luck_amount = 0;

        removeAdditionalCells = false;
        showFirstLetter = false;
        showFirstLetters = false;
        removeAdditionalCell = false;
        goToNextTable = false;
        back_requested = false;
        doubleReward = false;

        cost_show_first_letters = 0;
        cost_show_first_letter = 0;
        cost_remove_additional_cell = 0;
        cost_remove_additional_cells = 0;
        cost_next_table = 0;


        removedCells.clear();

        availablePlace.clear();


        //meysam - commenetd in 13960727
//        ListOfAnswers.clear();
//        userAnswered.clear();
//        answeredCells.clear();
//        hintedCells.clear();
//        fisrtPlaceOfAnswers.clear();
        ///////////////////////////////

        is_gameOver = false;
        windowDown_02(false);
    }

    private void initializeTable() {
        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));


        for (int i = 0; i < numberWord; i++) {
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View v = findViewById(id);
            v.setBackgroundResource(R.drawable.diamond_orange);
            v.clearAnimation();
            v.setClickable(true);
            v.setVisibility(View.VISIBLE);
            v.setEnabled(true);
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (!is_gameOver) {
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        if (event.getAction() == MotionEvent.ACTION_MOVE) {

                            TextView onView= (TextView) whichOne(x,y);
                            if(!onView.isPressed())
                                sfxm.play(SoundFXModel.CLICKED,getApplicationContext());

                            addWord(x, y);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            v.setBackgroundResource(R.drawable.diamond_orange);

                            //meysam - commented in 13960727
//                            checkAnswer();
                            //////////////////

                            //meysam - 13960727
                            checkAnswerV2();
                            /////////////////////


                            lastWord = null;
                        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            sfxm.play(SoundFXModel.CLICKED,getApplicationContext());
                            v.setBackgroundResource(R.drawable.diamond_gray);
                            addWord(x, y);
                        }
                    }
                    return true;
                }
            });

        }
    }

    //    ///meysam - 13960727
//    private void makeTableV2() {
//
//        //add all place as available place
//        for(int i=0;i<numberWord;i++){
//            availablePlace.add(i+1);
//        }
//
//        for(int i = 0; i < questions.size(); i++)
//        {
//            int count_try=0;
//            while(count_try<1000){
//                boolean is_word_set=false;
//                ArrayList<Integer> used_availablePlace=new ArrayList<>();
//                //break word
//                ArrayList<String> letters=new ArrayList<>();
//                for(int j=0;j<questions.get(i).getAnswer().replaceAll("\\s+","").length();j++){
//                    letters.add(questions.get(i).getAnswer().replaceAll("\\s+","").substring(j,j+1));
//                }
//
//                //get randomly fisrt place to start
//                int pos = availablePlace.get(new Random().nextInt(availablePlace.size()));
//                //is occupied?
//                if(isPositionOccupied(pos)) continue;
//                // meysam - there is problem here
//                questions.get(i).addAnswerCell(String.valueOf(pos));
//
//                availablePlace.remove(Integer.valueOf(pos));
//                used_availablePlace.add(Integer.valueOf(pos));
//                //set letter in place
//                for(int j=0;j<questions.get(i).getAnswer().replaceAll("\\s+","").length();j++) {
//                    String word_name="word_"+(pos>9?"":"0")+pos;
//                    int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//                    TextView temp= (TextView) findViewById(id);
//                    temp.setText(letters.get(j));
//
//
//                    //dont need to find next pos after last letter
//                    if(j==questions.get(i).getAnswer().replaceAll("\\s+","").length()-1) {is_word_set=true; break;}
//                    //1=up 2=left 3=down 4=right
//                    int  n = new Random().nextInt(4) + 1;
//                    boolean is_next_pos_ok=false;
//                    for(int k=0;k<4;k++){
//                        if(n==1){
//                            if(isUpAvailable(pos))
//                            {
//                                pos=pos-7;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=2;
//                                continue;
//                            }
//                        }else if(n==2){
//                            if(isLeftAvailable(pos))
//                            {
//                                pos=pos-1;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=3;
//                                continue;
//                            }
//                        }else if(n==3){
//                            if(isDownAvailable(pos))
//                            {
//                                pos=pos+7;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=4;
//                                continue;
//                            }
//                        }else{
//                            if(isRightAvailable(pos))
//                            {
//                                pos=pos+1;
//                                is_next_pos_ok=true;
//                                break;
//                            }else{
//                                n=1;
//                                continue;
//                            }
//                        }
//                    }
//                    if(!is_next_pos_ok)
//                    {
//                        break;
//                    }
//                    //remove place from available
//                    availablePlace.remove(Integer.valueOf(pos));
//                    used_availablePlace.add(Integer.valueOf(pos));
//                    questions.get(i).addAnswerCell(String.valueOf(pos));
//                }
//                if(!is_word_set){
//                    availablePlace.addAll(used_availablePlace);
//                    resetWords(used_availablePlace);
//                    continue;
//                }else{
////                    fisrtPlaceOfAnswers.add(temp_pos);
//                    break;
//                }
//            }
//            if(count_try==1000){
//                //unsuccessful to make a table
//                session.removeTangledTable();
//
//                //meysam - 13960727
//                db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
//                ///////////////////
//
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
//            }
//        }
//
//
//        //fill all extra place with random letters
//        for(int k=0;k<availablePlace.size();k++){
//            int pos=availablePlace.get(k);
//            String word_name="word_"+(pos>9?"":"0")+pos;
//            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
//            TextView temp= (TextView) findViewById(id);
//            temp.setText(horoofs[new Random().nextInt(32)]);
//        }
//
//        //meysam: save cells to session
//        save_cells_inSession();
//    }
    ///meysam - 13960727
    private void makeTableV2() {

        //add all place as available place
        for (int i = 0; i < numberWord; i++) {
            availablePlace.add(i + 1);
        }

        for (int i = 0; i < questions.size(); i++) {
            int count_try = 0;
            while (count_try < 1000) {

                if (count_try == 1000) {
                    //unsuccessful to make a table
                    session.removeTangledTable();

                    //meysam - 13960727
                    db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);
                    ///////////////////

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

                boolean is_word_set = false;
                ArrayList<Integer> used_availablePlace = new ArrayList<>();


                //break word
                ArrayList<String> letters = new ArrayList<>();
                for (int j = 0; j < questions.get(i).getAnswer().replaceAll("\\s+", "").length(); j++) {
                    letters.add(questions.get(i).getAnswer().replaceAll("\\s+", "").substring(j, j + 1));
                }

                if (letters.size() == 0) {
                    //meysam - somthing is not right...
                    Utility.displayToast(DarhamTableActivity.this, getString(R.string.error_operation_fail), Toast.LENGTH_SHORT);
                    DarhamTableActivity.this.finish();
                }

                //get randomly fisrt place to start
                int pos = availablePlace.get(new Random().nextInt(availablePlace.size()));
                //is occupied?
                if (isPositionOccupied(pos))
                    continue;

                questions.get(i).addAnswerCell(String.valueOf(pos));
                availablePlace.remove(Integer.valueOf(pos));
                used_availablePlace.add(Integer.valueOf(pos));


                int j = 0;
                do {


                    String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
                    int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                    TextView temp = (TextView) findViewById(id);
                    temp.setText(letters.get(j));


                    //1=up 2=left 3=down 4=right
                    int n = new Random().nextInt(4) + 1;
                    boolean is_next_pos_ok = false;
                    for (int k = 0; k < 4; k++) {
                        if (n == 1) {
                            if (isUpAvailable(pos)) {
                                pos = pos - 7;
                                is_next_pos_ok = true;
                                break;
                            } else {
                                n = 2;
                                continue;
                            }
                        } else if (n == 2) {
                            if (isLeftAvailable(pos)) {
                                pos = pos - 1;
                                is_next_pos_ok = true;
                                break;
                            } else {
                                n = 3;
                                continue;
                            }
                        } else if (n == 3) {
                            if (isDownAvailable(pos)) {
                                pos = pos + 7;
                                is_next_pos_ok = true;
                                break;
                            } else {
                                n = 4;
                                continue;
                            }
                        } else {
                            if (isRightAvailable(pos)) {
                                pos = pos + 1;
                                is_next_pos_ok = true;
                                break;
                            } else {
                                n = 1;
                                continue;
                            }
                        }
                    }
                    if (!is_next_pos_ok) {

                        for (Integer item : used_availablePlace) {
                            availablePlace.add(item);
                        }
                        used_availablePlace.clear();
                        questions.get(i).getAnswerCells().clear();
                        break;
                    }
                    //remove place from available
                    availablePlace.remove(Integer.valueOf(pos));
                    used_availablePlace.add(Integer.valueOf(pos));
                    questions.get(i).addAnswerCell(String.valueOf(pos));


                    j++;
                    //dont need to find next pos after last letter
                    if (j == questions.get(i).getAnswer().replaceAll("\\s+", "").length() - 1) {
                        word_name = "word_" + (pos > 9 ? "" : "0") + pos;
                        id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                        temp = (TextView) findViewById(id);
                        temp.setText(letters.get(j));
                        is_word_set = true;
                    }
                } while (j < questions.get(i).getAnswer().replaceAll("\\s+", "").length() - 1);


                if (!is_word_set) {
                    availablePlace.addAll(used_availablePlace);
                    questions.get(i).getAnswerCells().clear();
                    resetWords(used_availablePlace);
                    used_availablePlace.clear();
                    continue;
                } else {
//                    fisrtPlaceOfAnswers.add(temp_pos);
                    break;
                }
            }

        }


        //fill all extra place with random letters
        for (int k = 0; k < availablePlace.size(); k++) {
            int pos = availablePlace.get(k);
            String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setText(horoofs[new Random().nextInt(32)]);
        }

        //meysam: save cells to session
        save_cells_inSession();

        DM.hide();
        for (int i = 0; i < numberWord; i++) {
            String word_name = "word_" + (i > 8 ? "" : "0") + (i + 1);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            View temp = findViewById(id);
            Random r = new Random();
            int r1_duration = r.nextInt(1000) + 20;
            int r2_delay = r.nextInt(100) + 10;
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN)
                    .interpolator(Flubber.Curve.BZR_EASE_OUT)
                    .repeatCount(1)
                    .repeatMode(ValueAnimator.RESTART)
                    .duration(r1_duration)
                    .delay(r2_delay)
                    .createFor(temp)
                    .start();
        }
    }

    //meysam - 13960727
    //check if cell index for letter has been occupied by pervious questions
    private boolean isPositionOccupied(Integer positionIndex) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getAnswerCells() != null) {
                if (questions.get(i).getAnswerCells().contains(positionIndex.toString())) {
                    return true;
                }
            }

        }

        return false;
    }

    //meysam - 13960727
    private boolean isUpAvailable(int position) {
        int upPosition = position - 7;
        if (upPosition > 0 && !isPositionOccupied(upPosition))
            return true;
        return false;
    }

    //meysam - 13960727
    private boolean isDownAvailable(int position) {
        int downPosition = position + 7;
        if (downPosition < 71 && !isPositionOccupied(downPosition))
            return true;
        return false;
    }

    //meysam - 13960727
    private boolean isLeftAvailable(int position) {
        if (position == 1 || position == 8 || position == 15 ||
                position == 22 || position == 29 || position == 36 ||
                position == 43 || position == 50 || position == 57 || position == 64)
            return false;
        int leftPosition = position - 1;
        if (!isPositionOccupied(leftPosition))
            return true;
        return false;

    }

    //meysam - 13960727
    private boolean isRightAvailable(int position) {
        if (position == 7 || position == 14 || position == 21 || position == 28 ||
                position == 35 || position == 42 || position == 49 || position == 56 ||
                position == 63 || position == 70)
            return false;
        int rightPosition = position + 1;
        if (!isPositionOccupied(rightPosition))
            return true;
        return false;
    }

    //fill list of questions
    private void fillList(ArrayList<QuestionModel> questions) {
        //make list
        ViewGroup.LayoutParams params = list_questions.getLayoutParams();
        params.height = (int) (height * 0.8);
        list_questions.requestLayout();

        CALPQ = new CustomAdapterList_puzzle_question(this, new ArrayList<Object>(questions), RequestRespondModel.TAG_WORD_TABLE_QUESTION);
        list_questions.setAdapter(CALPQ);

        list_questions.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
            }
        });

        list_questions.invalidateViews();
    }


    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if (intent.getStringExtra("loading") != null) {
                if (intent.getStringExtra("loading").equals("true")) {
                    DM.show();
                }
            }
            if (intent.getStringExtra("hide_loading") != null) {
                if (intent.getStringExtra("hide_loading").equals("true")) {
                    DM.hide();
                }
            }
            if (intent.getStringExtra("report_result_dialog") != null) {
                if (intent.getStringExtra("report_result_dialog").equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(DarhamTableActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(DarhamTableActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });
                }
            }
            if (intent.getStringExtra("rate_result_dialog") != null) {

                if (intent.getStringExtra("rate_result_dialog").equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(DarhamTableActivity.this, getApplicationContext().getString(R.string.msg_RateRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(DarhamTableActivity.this, getApplicationContext().getString(R.string.msg_RateNotRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });
                }
            }
            if (intent.getStringExtra("show_toast") != null) {
                final String temp = intent.getStringExtra("show_toast");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.displayToast(DarhamTableActivity.this, temp, Toast.LENGTH_SHORT);
                    }
                });
            }
            if (intent.getStringExtra("show_dialog") != null) {

                final String temp = intent.getStringExtra("show_dialog");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(DarhamTableActivity.this, temp, getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                });

            }

        }
    };

    private void showLoading() {
        Intent intent = new Intent("darham_table_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(DarhamTableActivity.this).sendBroadcast(intent);
    }

    private void changeCellColors(ArrayList<String> cells, String color, Boolean foundword) {
        for (int i = 0; i < cells.size(); i++) {
            int temp_pos = new Integer(cells.get(i));

            String word_name = "word_" + (temp_pos > 9 ? "" : "0") + temp_pos;
            final int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            if (color.equals("blue")) {
                findViewById(id).setBackgroundResource(R.drawable.diamond_blue);
                if (findViewById(id).getAnimation() == null)
                    if (!foundword)
                        findViewById(id).setAnimation(in_game_scale_up);
            } else if (color.equals("green")) {
                findViewById(id).setBackgroundResource(R.drawable.diamond_green);
                if (findViewById(id).getAnimation() == null) {
                    findViewById(id).setAnimation(in_game_wavy);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(id).setAnimation(in_game_wavy2);
                        }
                    }, 900);
                }
            } else {
                findViewById(id).setBackgroundResource(R.drawable.diamond_red);
            }

        }
    }

    @Override
    public void onResume() {
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
                    .delay(delay * 1000)
//                    .force((float) 0.5)
                    .createFor(btn_01_dt);
            anm_hint_ad.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

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
            ll_doubleHazel.setVisibility(View.VISIBLE);
            ll_doubleHazel.invalidate();
            endWin_doubleHazel.invalidate();
//            ll_doubleHazel.removeAllViews();
//            ll_doubleHazel.refreshDrawableState();
            anm_double_reward = Flubber.with().listener(flubberDoubleRewardListener)
                    .animation(Flubber.AnimationPreset.POP) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1000)  // Last for 1000 milliseconds(1 second)
                    .delay(delay * 1000)
                    .force((float) 0.3)
                    .createFor(ll_doubleHazel);
            anm_double_reward.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

}
