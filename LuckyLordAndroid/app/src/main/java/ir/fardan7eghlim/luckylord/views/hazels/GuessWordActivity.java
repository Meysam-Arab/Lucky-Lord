package ir.fardan7eghlim.luckylord.views.hazels;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_level;
import ir.fardan7eghlim.luckylord.utils.DialogModel_luck;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.ReportDialogPopUpModel;
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

public class GuessWordActivity extends BaseActivity implements Observer {

    private DatabaseHandler db;
    private SoundFXModel sfxm = null;
    private DialogModel DM;

    private String horoofs[] = {"آ", "ب", "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی", "ئ", "ا"};

    private int height;


    private LuckyTextView word_00;
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

    private LuckyTextView ans_00;
    private LuckyTextView ans_01;
    private LuckyTextView ans_02;
    private LuckyTextView ans_03;
    private LuckyTextView ans_04;
    private LuckyTextView ans_05;
    private LuckyTextView ans_06;

    private LinearLayout windowDown_dt;
    private ScrollView windowDown_SV_dt;
    private LinearLayout hints_dt;
    private LuckyTextView word_dt;
    private LuckyButton windownClose_dt;
    private LinearLayout gameover_win;

    private String answer_in_go;
    private int number_wrong=0;
    private int number_ansered=0;

    private boolean doubleReward;
    private boolean back_requested;
    private boolean home_requested;

    private Boolean isInCreating;


    private ImageView squirrel;
    private ImageView cage;

    private LuckyTextViewAutoSize tv_hazel_amount;
    private LuckyTextViewAutoSize tv_luck_amount;


    private LuckyTextView endWin_luckAmount;
    private LuckyTextView endWin_hazelAmount;

    private LuckyTextView txt_category;

    private ImageView btn_sound;
    private ImageView btn_hints;


    private WordModel answer;
    private UserController uc;

    private int reward_hazel_amount;
    private int reward_luck_amount;

    LinearLayout hint_btn_dh_remove_additional_cell;
    LinearLayout hint_btn_dh_ad_remove_additional_cells;
    LinearLayout hint_btn_dh_show_one_letter;
    LinearLayout hint_btn_dh_ad_show_one_letter;
    LinearLayout hint_btn_dh_next_guess_word;
    LinearLayout hint_btn_dh_ad_next_guess_word;
    LinearLayout hint_btn_dh_remove_additional_cells;

    private int cost_remove_additional_cell;
    private int cost_show_one_letter;
    private int cost_next_guess_word;
    private int cost_remove_additional_cells;

    private boolean removeAdditionalCell;
    private boolean removeAdditionalCells;
    private boolean showOneLetter;
    private boolean goToNextGuessWord;


    LinearLayout endWin_doubleHazel;
    FrameLayout fl_doubleHazel;
    LinearLayout ll_reportWord;

    private boolean is_gameOver=false;
    private boolean windowDown_isShowing=false;
    private boolean windowDown2_isShowing=false;
//    private int final_hazel_amount;
//    private int final_luck_amount;

    private boolean cage_move=false;
    private DialogModel_level dml;

    private Animation in_game_wavy;
    private Animation in_game_wavy2;
    private Animation in_game_shake;
    private Animation in_game_scale_up;

    Animator anm_hint_ad;
    Animator anm_double_reward;
    Animator anm_hazel;
    Animator anm_luck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_word);

        isInCreating = true;

        answer = null;

        uc=new UserController(this);
        uc.addObserver(this);

        in_game_wavy = AnimationUtils.loadAnimation(GuessWordActivity.this, R.anim.in_game_wavy);
        in_game_wavy2 = AnimationUtils.loadAnimation(GuessWordActivity.this, R.anim.in_game_wavy2);
        in_game_shake = AnimationUtils.loadAnimation(GuessWordActivity.this, R.anim.in_game_shake);
        in_game_scale_up = AnimationUtils.loadAnimation(GuessWordActivity.this, R.anim.in_game_scale_up);

        DM=new DialogModel(this);

        btn_sound= (ImageView) findViewById(R.id.btn_sound);
        btn_hints = (ImageView) findViewById(R.id.btn_hints);

        //keyboards
        word_00= (LuckyTextView) findViewById(R.id.word_00);
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

        ans_00= (LuckyTextView) findViewById(R.id.ans_00);
        ans_01= (LuckyTextView) findViewById(R.id.ans_01);
        ans_02= (LuckyTextView) findViewById(R.id.ans_02);
        ans_03= (LuckyTextView) findViewById(R.id.ans_03);
        ans_04= (LuckyTextView) findViewById(R.id.ans_04);
        ans_05= (LuckyTextView) findViewById(R.id.ans_05);
        ans_06= (LuckyTextView) findViewById(R.id.ans_06);

        txt_category = (LuckyTextView) findViewById(R.id.txt_category);

        windowDown_dt= (LinearLayout) findViewById(R.id.windowDown_dt);
        windowDown_SV_dt= (ScrollView) findViewById(R.id.windowDown_SV_dt);
        hints_dt= (LinearLayout) findViewById(R.id.hints_dt);
        word_dt= (LuckyTextView) findViewById(R.id.word_dt);
        windownClose_dt= (LuckyButton) findViewById(R.id.windownClose_dt);
        gameover_win= (LinearLayout) findViewById(R.id.gameover_win);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;

//        list_of_place_for_answer=new ArrayList<>();
        answer_in_go="";
        squirrel= (ImageView) findViewById(R.id.c_squirrel_gw);
        cage= (ImageView) findViewById(R.id.cage_gw);


        //get Word from db
        db=new DatabaseHandler(getApplicationContext());

        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        if (!session.getBoolianItem(SessionModel.KEY_GUESS_WORD_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

//            DialogPopUpModel.show(GuessWordActivity.this, getString(R.string.dlg_TutorialGuessWord), getString(R.string.btn_OK), null,false, false);
            DialogModel_help dm_help=new DialogModel_help(GuessWordActivity.this);
            dm_help.show(2);
            session.saveItem(SessionModel.KEY_GUESS_WORD_IS_FIRST_TIME, true);

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
//                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                    SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);
                    btn_sound.setImageResource(R.drawable.b_sound_on);
                }

            }
        });

        hint_btn_dh_remove_additional_cell= (LinearLayout) findViewById(R.id.hint_btn_dh_remove_additional_cell);
        hint_btn_dh_ad_remove_additional_cells= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_remove_additional_cells);
        hint_btn_dh_show_one_letter= (LinearLayout) findViewById(R.id.hint_btn_dh_show_one_letter);
        hint_btn_dh_ad_show_one_letter= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_show_one_letter);
        hint_btn_dh_next_guess_word= (LinearLayout) findViewById(R.id.hint_btn_dh_next_guess_word);
        hint_btn_dh_ad_next_guess_word= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_next_guess_word);
        hint_btn_dh_remove_additional_cells= (LinearLayout) findViewById(R.id.hint_btn_dh_remove_additional_cells);
        endWin_doubleHazel = (LinearLayout) findViewById(R.id.endWin_doubleHazel);
        ll_reportWord = (LinearLayout) findViewById(R.id.ll_reportWord);
        fl_doubleHazel = findViewById(R.id.fl_doubleHazel);



        clearAll();
        setHintAmounts();

        //meysam - check if word exist from before
        //meysam = 13960830
//        db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);//meysam - must be deleted...
        answer = db.getWordByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
        /////////////////////////////////////////////

        if(answer == null)
        {
            initialize();
        }
        else
        {
            // meysam - set all true and right answered cells ....
            //meysam - مشخص کردن تعداد جای خالی برای جواب
            answer_in_go=answer.getWord().replaceAll("\\s+","");
            txt_category.setText(WordModel.getCategoryById(answer.getCategory(), GuessWordActivity.this)+" ? ");

            //meysam - مشخص کردن تعداد جای خالی برای جواب
            char[] tmpLetters=answer.getWord().replaceAll("\\s+","").toCharArray();
            for(int i=0;i<tmpLetters.length;i++){
                String ans_name = "ans_0" +i;
                int id = getResources().getIdentifier(ans_name, "id", getApplicationContext().getPackageName());
                TextView temp = (TextView) findViewById(id);

                if(temp == null)
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_undefined), Toast.LENGTH_SHORT);
                    Intent ii = new Intent(GuessWordActivity.this,MainActivity.class);
                    GuessWordActivity.this.startActivity(ii);
                    Utility.finishActivity(this);
                    return;
                }
                if(answer.getAnsweredLetters().contains(String.valueOf(tmpLetters[i])))
                    temp.setText(String.valueOf(tmpLetters[i]));
                else
                    temp.setText("");
                temp.setVisibility(View.VISIBLE);
            }

            int hintedWrongLetterCount = session.getIntegerItem(SessionModel.KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT);
//            int hintedCorrectLetterCount = session.getIntegerItem(SessionModel.KEY_GUESS_WORD_HINTED_CORRECT_LETTER_COUNT);
            reward_hazel_amount = answer.getWord().replaceAll("\\s+","").length();
            reward_luck_amount = 1;
            word_dt.setText("جواب:"+answer.getWord());

            for(int i=0;i<21;i++){

                String word_name = "word_" + (i > 9 ? "" : "0") + i;
                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                TextView temp = (TextView) findViewById(id);
                int letterIndex = answer.getAllLettersPositions().indexOf(String.valueOf(i));
                temp.setText(answer.getAllLetters().get(letterIndex));

                //meysam - اگر کاربر جواب داده و درست بوده قبلا
                if(answer.getAnsweredLettersPositions().contains(String.valueOf(i)))
                {
                    correctLetter(i,false);
                }
                //meysam - اگر کاربر جواب داده و غلط بوده قبلا
                if(answer.getWrongAnsweredLettersPositions().contains(String.valueOf(i)))
                {
                    if(hintedWrongLetterCount > 0)
                    {
                        wrongLetter(i,true,false);
                        hintedWrongLetterCount--;
                    }
                    else
                    {
                        wrongLetter(i,false,false);
                    }

                }
            }

            CageStatus();

        }


        //meysam - ست کردن رویداد کلیک کیبورد
        for(int i=0;i<21;i++){
            String word_name="word_"+(i>9?"":"0")+(i);
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            LuckyTextView current=(LuckyTextView) findViewById(id);
            if(!answer.getAnsweredLetters().contains(current.getText()) &&
                    !answer.getWrongAnsweredLetters().contains(current.getText()))
            {
                current.setBackgroundResource(R.drawable.diamond_orange);
            }
            current.setClickable(true);
            current.setVisibility(View.VISIBLE);
            current.setEnabled(true);
            final LuckyTextView currentTemp=current;
            final int finalI = i;
            current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (!v.isPressed())
//                        sfxm.play(SoundFXModel.CLICKED, getApplicationContext());

                    if(!answer.getAnsweredLettersPositions().contains(String.valueOf(finalI)) &&
                            !answer.getWrongAnsweredLettersPositions().contains(String.valueOf(finalI)))
                    {
                        if(answer.getAnswerLettersPositions().contains(String.valueOf(finalI))){
                            correctLetter(finalI,true);
                        }else{
                            wrongLetter(finalI, false,true);
                        }
                    }
                }
            });
        }


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
                        DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                    }
                }
            }
        });
        ll_reportWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - report word

                ReportDialogPopUpModel.show(GuessWordActivity.this);
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
                                    qc.addObserver(GuessWordActivity.this);
                                    qc.universalReport(new BigInteger(answer.getId().toString()),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description, ReportDialogPopUpModel.GUESS_WORD_TYPE);

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
        hint_btn_dh_remove_additional_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(GuessWordActivity.this, cost_remove_additional_cell))
                {
                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                                                if(  hint_removeOneExtraPlace())
                                                {
//                                                    final_hazel_amount -= cost_remove_additional_cell;

                                                    session.changeFinalHazel(-1*cost_remove_additional_cell);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(GuessWordActivity.this).show(false, false, new Integer(cost_remove_additional_cell));
                                                        }
                                                    });

                                                    Utility.changeCoins(GuessWordActivity.this,-1*cost_remove_additional_cell);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                }
                                                else
                                                {
                                                    Utility.displayToast(GuessWordActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
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
                    DialogPopUpModel.show(GuessWordActivity.this, Utility.enToFa("باید حداقل " + cost_remove_additional_cell + " فندق داشته باشی!"), "باشه", null, false, false);
                }
            }
        });
        hint_btn_dh_ad_remove_additional_cells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam -movie ad

                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                {
                    DM.show();
                    WatchAd();

                    removeAdditionalCells = true;

                    closeWindows();
                }
                else
                {
                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }


            }
        });
        hint_btn_dh_show_one_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(GuessWordActivity.this, cost_show_one_letter))
                {
                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                                                // amir - put show one first letter function
//                                                if( hint_putShowOneFirstLetter())//meysam commented in 13960727
                                                if( hintPutShowOneLetter())
                                                {
//                                                    final_hazel_amount -= cost_show_one_letter;

                                                    session.changeFinalHazel(-1*cost_show_one_letter);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(GuessWordActivity.this).show(false, false, new Integer(cost_show_one_letter));
                                                        }
                                                    });

                                                    Utility.changeCoins(GuessWordActivity.this,-1*cost_show_one_letter);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
                                                }
                                                else
                                                {
                                                   Utility.displayToast(GuessWordActivity.this,getString(R.string.error_no_more),Toast.LENGTH_SHORT);
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
                    DialogPopUpModel.show(GuessWordActivity.this, Utility.enToFa("باید حداقل " + cost_show_one_letter + " فندق داشته باشی!"), "باشه", null, false, false);

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
                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });
        hint_btn_dh_next_guess_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(GuessWordActivity.this, cost_next_guess_word))
                {

                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false );
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

                                                session.changeFinalHazel(-1*cost_next_guess_word);

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new DialogModel_hazel(GuessWordActivity.this).show(false, false, new Integer(cost_next_guess_word));
                                                    }
                                                });


                                                Utility.changeCoins(GuessWordActivity.this,-1*cost_next_guess_word);
                                                tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                //meysam
                                                finishGuessWord();
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
                    DialogPopUpModel.show(GuessWordActivity.this, Utility.enToFa("باید حداقل " + cost_next_guess_word + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });

        hint_btn_dh_ad_next_guess_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad

                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                {
                    DM.show();
                    WatchAd();

                    goToNextGuessWord = true;

                    closeWindows();
                }
                else
                {
                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });


        hint_btn_dh_remove_additional_cells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(GuessWordActivity.this, cost_remove_additional_cells))
                {
                    DialogPopUpModel.show(GuessWordActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false );
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
                                                if( hint_removeExtraPlaces())
                                                {
//                                                    final_hazel_amount -= cost_remove_additional_cells;

                                                    session.changeFinalHazel(-1*cost_remove_additional_cells);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            new DialogModel_hazel(GuessWordActivity.this).show(false, false, new Integer(cost_remove_additional_cells));
                                                        }
                                                    });


                                                    Utility.changeCoins(GuessWordActivity.this,-1*cost_remove_additional_cells);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                }
                                                else
                                                {
                                                    Utility.displayToast(GuessWordActivity.this, getString(R.string.error_no_more), Toast.LENGTH_SHORT);
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
                    DialogPopUpModel.show(GuessWordActivity.this, Utility.enToFa("باید حداقل " + cost_remove_additional_cells + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });

        //animation
        squirrel.setImageResource(R.drawable.c_squirrel_anim_a);
        AnimationDrawable anim = (AnimationDrawable) squirrel.getDrawable();
        anim.start();

        isInCreating = false;

    }

    private void initialize() {

        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


        resetButtonColors();

        setHintAmounts();

//        session.saveItem(SessionModel.KEY_GUESS_WORD_HINTED_CORRECT_LETTER_COUNT, 0);
        session.saveItem(SessionModel.KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT, 0);


        //animation
        squirrel.setImageResource(R.drawable.c_squirrel_anim_a);
        AnimationDrawable anim = (AnimationDrawable) squirrel.getDrawable();
        anim.start();

        answer = null;
        number_wrong = 0;
        number_ansered = 0;
        is_gameOver = false;

        CageStatus();

//        db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);

        answer=getOneWord();
        if(answer!=null){

            // meysam - delete for development...
//            Utility.displayToast(GuessWordActivity.this, answer.getWord(), Toast.LENGTH_SHORT);
            /////////////////////////////////////////////


            txt_category.setText(WordModel.getCategoryById(answer.getCategory(), GuessWordActivity.this)+" ? ");
            //meysam - مشخص کردن تعداد جای خالی برای جواب
            answer_in_go=answer.getWord().replaceAll("\\s+","");
            for(int i=0;i<answer_in_go.length();i++){
                String ans_name = "ans_0" +i;
                int id = getResources().getIdentifier(ans_name, "id", getApplicationContext().getPackageName());
                TextView temp = (TextView) findViewById(id);
                if(temp == null)
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_undefined), Toast.LENGTH_SHORT);
                    Intent ii = new Intent(GuessWordActivity.this,MainActivity.class);
                    GuessWordActivity.this.startActivity(ii);
                    Utility.finishActivity(this);
                    return;
                }
                else
                {
                    temp.setText("");
                    temp.setVisibility(View.VISIBLE);
                }

            }

            //meysam - ساخت کیبورد به هم ریخته
            ArrayList<Integer> temp_array=new ArrayList<>();
            for(int i=0;i<21;i++){
                temp_array.add(i);
            }
            Collections.shuffle(temp_array);

            ArrayList<String> temp_array2=new ArrayList<>();
            for(int i=0;i<answer_in_go.length();i++){
                String harf=answer_in_go.substring(i,i+1);
                if(!temp_array2.contains(harf)){
                    temp_array2.add(harf);
                    int pos=temp_array.get(0);//شماره خانه این حرف از جواب
                    temp_array.remove(0);
//                        list_of_place_for_answer.add(pos);

                    String word_name = "word_" + (pos > 9 ? "" : "0") + pos;
                    int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                    TextView temp = (TextView) findViewById(id);
                    temp.setText(harf);

                    answer.getAnswerLettersPositions().add(String.valueOf(pos));
                    answer.getAllLettersPositions().add(String.valueOf(pos));
                    answer.getAllLetters().add(harf);
                }
            }
            //تمامی حرف هایی که در جواب نیستند را از آرایه حروف استخراج می کند و به هم می ریزد در آخر
            temp_array2.clear();
            for(int i=0;i<horoofs.length;i++){
                String harf=horoofs[i];
                if(!answer_in_go.contains(harf)){
                    temp_array2.add(harf);
                }
            }
            Collections.shuffle(temp_array2);
            //خانه های غیر از خانه جواب را با حرف های تصادفی که جواب نیستند پر می کند
            for(int i=0;i<21;i++){
                if(answer.getAnswerLettersPositions().contains(String.valueOf(i))){
                    continue;
                }
                else
                {
                    String word_name = "word_" + (i > 9 ? "" : "0") + i;
                    int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                    TextView temp = (TextView) findViewById(id);
                    temp.setText(temp_array2.get(i));

                    answer.getAllLettersPositions().add(String.valueOf(i));
                    answer.getAllLetters().add(temp_array2.get(i));
                }
            }

            db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
            db.addWord(answer,WordModel.TABLE_WORDS_GUESS_WORD_TAG);

            reward_hazel_amount = answer.getWord().replaceAll("\\s+","").length();
            reward_luck_amount = 1;
            word_dt.setText("جواب:"+answer.getWord());

        }else{
            //couldnt find a word as answer
        }

    }

    private void wrongLetter(int i, Boolean isHint, Boolean updateDb) {
        if(!isHint){
            number_wrong++;
            CageStatus();
        }
        String word_name="word_"+(i>9?"":"0")+(i);
        int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
        LuckyTextView current=(LuckyTextView) findViewById(id);
        current.setBackgroundResource(R.drawable.diamond_red);
        current.setAnimation(in_game_shake);
        current.setClickable(false);
        current.setEnabled(false);

        if(updateDb)
        {
            int letterIndex = answer.getAllLettersPositions().indexOf(String.valueOf(i));
            answer.getWrongAnsweredLettersPositions().add(String.valueOf(i));
            answer.getWrongAnsweredLetters().add(answer.getAllLetters().get(letterIndex));
            db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
            db.addWord(answer,WordModel.TABLE_WORDS_GUESS_WORD_TAG);
        }


        if(number_wrong==5 && isHint == false){
            endGame(false);
        }
        else
        {
            if(isInCreating != null)
                if(!isInCreating)
                    sfxm.play(SoundFXModel.WRONG, getApplicationContext());
        }
    }

    private void correctLetter(int i, Boolean updateDb) {
        if(isInCreating != null)
            if(!isInCreating)
                sfxm.play(SoundFXModel.CORRECT, getApplicationContext());
        String word_name="word_"+(i>9?"":"0")+(i);
        final int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
        final LuckyTextView current=(LuckyTextView) findViewById(id);
        current.setBackgroundResource(R.drawable.diamond_green);
        current.setAnimation(in_game_wavy);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                current.setAnimation(in_game_wavy2);
            }
        }, 900);
        current.setClickable(false);
        current.setEnabled(false);
        for(int j=0;j<answer_in_go.length();j++){
            if(answer_in_go.substring(j,j+1).equals(current.getText().toString())){
                String ans_name = "ans_0" +(j);
                int id2 = getResources().getIdentifier(ans_name, "id", getApplicationContext().getPackageName());
                TextView temp2 = (TextView) findViewById(id2);
                temp2.setText(current.getText().toString());
                temp2.setAnimation(in_game_scale_up);
                number_ansered++;
            }
        }

        if(updateDb)
        {
            int letterIndex = answer.getAllLettersPositions().indexOf(String.valueOf(i));
            answer.getAnsweredLettersPositions().add(String.valueOf(i));
            answer.getAnsweredLetters().add(answer.getAllLetters().get(letterIndex));
            db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
            db.addWord(answer,WordModel.TABLE_WORDS_GUESS_WORD_TAG);
        }


        if(number_ansered==answer_in_go.length()){
            endGame(true);
        }
    }

    private void resetButtonColors()
    {
        for(int i=0;i<21;i++){
            String word_name = "word_" + (i > 9 ? "" : "0") + i;
            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
            LuckyTextView temp = (LuckyTextView) findViewById(id);
            temp.setBackgroundResource(R.drawable.diamond_orange);
            temp.setEnabled(true);
            temp.setClickable(true);
        }

        for(int i=0;i<7;i++){
            String ans_name = "ans_0" +i;
            int id = getResources().getIdentifier(ans_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            if(temp == null)
            {
                Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_undefined), Toast.LENGTH_SHORT);
                Intent ii = new Intent(GuessWordActivity.this,MainActivity.class);
                GuessWordActivity.this.startActivity(ii);
                Utility.finishActivity(this);
                return;
            }
            temp.setText("");
            temp.setVisibility(View.GONE);
        }
    }

    private void endGame(boolean isWin){
        if(isWin){
            endWin_hazelAmount.setText(String.valueOf(reward_hazel_amount));
            endWin_luckAmount.setText(String.valueOf(reward_luck_amount));
//            Utility.displayToast(GuessWordActivity.this, "win", Toast.LENGTH_SHORT);
            gameOver(isWin);
        }else{

            endWin_hazelAmount.setText(String.valueOf(0));
            endWin_luckAmount.setText(String.valueOf(-1));

//            final_luck_amount -= 1;
            session.changeFinalLuck( -1);
            //meysam - add reward to session
            Utility.changeLucks(getApplicationContext(),-1);
            ///////////////////////////////

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new DialogModel_luck(GuessWordActivity.this).show(true, false, new Integer(1));
                }
            });

//            Utility.displayToast(GuessWordActivity.this, "lose", Toast.LENGTH_SHORT);
            squirrel.setImageResource(R.drawable.c_squirrel_m);
            gameOver(isWin);
        }
    }

    private void finishGuessWord(){

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

        endWin_luckAmount.setText("0");
        endWin_hazelAmount.setText("0");

        //meysam - show true answer
        showTrueAnswer();

        gameOver(false);

        windowDown_01(false);
    }
    private void gameOver(final boolean isWin){

        if(isWin)
        {
//            Anims.move(cage,0.0f,0.0f,0.0f,-500.0f,500,0);
//            cage_move=true;
//            endWin_doubleHazel.setEnabled(true);
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
            /////////////////////////////

            //meysam - add score...
            if(Utility.addLevelScore(getApplicationContext(), UserModel.REWARD_AMOUNT_LEVEL_SCORE_GUESS_WORD))
            {
                //meysam - an increase in level occured...
                //meysam - show earned level animation...
                //meysam - no need to store session variable for show...
                int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()),Integer.valueOf(session.getCurrentUser().getLevelScore())+ session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                dml = new DialogModel_level(GuessWordActivity.this);
                dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())),Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()))+levelDiffrence);
                /////////////////////////////////////////////
                //meysam - send new level to server ...
                uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                ///////////////////////////////////////

            }

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
            //meysam - play win table sound
            SoundModel.playSpecificSound(R.raw.cage_fall,getApplicationContext(),false,null,null, null);
//            SoundModel.playSpecificSound(R.raw.lost_match,getApplicationContext(),false);
            /////////////////////////////
        }


        //meysam
        db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
        ///////////////////

        is_gameOver=true;
        //game over

        tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                windowDown_02(true);

                //meysam
                showTrueAnswer();
                if(!isWin)
                {
                    SoundModel.playSpecificSound(R.raw.lost_match,getApplicationContext(),false,null,null, null);
                }

            }
        }, 2000);

        //buttons of end win
        LinearLayout endWin_pause= (LinearLayout) findViewById(R.id.endWin_pause);
        endWin_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam

                if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
                {
//                    final_hazel_amount += reward_hazel_amount;
//                    final_luck_amount += reward_luck_amount;
                    if(isWin)
                    {
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
                    Intent i = new Intent(GuessWordActivity.this, MainActivity.class);
                    GuessWordActivity.this.startActivity(i);
                    finish();
                }

                db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
            }
        });
        LinearLayout endWin_next= (LinearLayout) findViewById(R.id.endWin_next);
        endWin_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
                {
//                    final_hazel_amount += reward_hazel_amount;
//                    final_luck_amount += reward_luck_amount;

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

    private void showTrueAnswer() {

        //meysam - مشخص کردن تعداد جای خالی برای جواب
        char[] tmpLetters=answer.getWord().replaceAll("\\s+","").toCharArray();
        for(int i=0;i<tmpLetters.length;i++){
            String ans_name = "ans_0" +i;
            int id = getResources().getIdentifier(ans_name, "id", getApplicationContext().getPackageName());
            TextView temp = (TextView) findViewById(id);
            temp.setText(String.valueOf(tmpLetters[i]));
        }
    }

    private void CageStatus(){
        if(number_wrong==0){
//            if(cage_move){
//                Anims.move(cage,0.0f,0.0f,-500.0f,0.0f,500,0);
//                cage.setVisibility(View.INVISIBLE);
//                cage_move=false;
//            }
            cage.setVisibility(View.INVISIBLE);
        }else if(number_wrong==1){
            cage.setVisibility(View.VISIBLE);
            cage.setImageResource(R.drawable.c_cage_anim_1);
            AnimationDrawable anim = (AnimationDrawable) cage.getDrawable();
            anim.start();

//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(number_wrong==1) {
//                        cage.setImageResource(R.drawable.c_cage_anim_b);
//                        AnimationDrawable anim = (AnimationDrawable) cage.getDrawable();
//                        anim.start();
//                    }
//                }
//            }, 250);

        }else if(number_wrong==2){
            cage.setVisibility(View.VISIBLE);
            cage.setImageResource(R.drawable.c_cage_anim_2);
            AnimationDrawable anim = (AnimationDrawable) cage.getDrawable();
            anim.start();
        }else if(number_wrong==3){
            cage.setVisibility(View.VISIBLE);
            cage.setImageResource(R.drawable.c_cage_anim_3);
            AnimationDrawable anim = (AnimationDrawable) cage.getDrawable();
            anim.start();
        }else if(number_wrong==4){
            cage.setVisibility(View.VISIBLE);
            cage.setImageResource(R.drawable.c_cage_anim_4);
            AnimationDrawable anim = (AnimationDrawable) cage.getDrawable();
            anim.start();
        }
        else{
            cage.setVisibility(View.VISIBLE);
            cage.setImageResource(R.drawable.c_cage_loose);
        }
    }

    private WordModel getOneWord(){
//        int count_try=0;
//        while (count_try<10000){
//            int rnd = new Random().nextInt(WordModel.numberOfFarsiWords - 1) + 1;
//            WordModel word=db.getFarsiWord(new BigInteger(String.valueOf(rnd)));
//            if(word.getLength()>2 && word.getLength()<7){
//                return word;
//            }
//            count_try++;
//        }
//        return null;

        WordModel word=db.getFarsiWord(true,3,7);
        return word;
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
                word_dt.setVisibility(View.GONE);
                windownClose_dt.setVisibility(View.GONE);
            }
            windowDown2_isShowing=true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            word_dt.setVisibility(View.VISIBLE);
            windownClose_dt.setVisibility(View.VISIBLE);
            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);
            if(is_gameOver){
                gameover_win.setVisibility(View.VISIBLE);
                windownClose_dt.setVisibility(View.GONE);
                windowDown_SV_dt.fullScroll(ScrollView.FOCUS_UP);
            }
        }else{
            windowDown2_isShowing=false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            word_dt.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
            gameover_win.setVisibility(View.GONE);
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
                word_dt.setVisibility(View.GONE);
                windownClose_dt.setVisibility(View.GONE);
            }
            windowDown_isShowing=true;
            windowDown_dt.setVisibility(View.VISIBLE);
            windowDown_SV_dt.setVisibility(View.VISIBLE);
            hints_dt.setVisibility(View.VISIBLE);
            windownClose_dt.setVisibility(View.VISIBLE);
            Anims.move(windowDown_dt, 0.0f, 0.0f, height + 30.0f, 0.0f, 700, 0);
        }else{
            windowDown_isShowing=false;
            Anims.move(windowDown_dt, 0.0f, 0.0f, 0.0f, height + 30.0f, 700, 0);
            windowDown_dt.setVisibility(View.GONE);
            windowDown_SV_dt.setVisibility(View.GONE);
            hints_dt.setVisibility(View.GONE);
            word_dt.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
        }
    }

    //meysam - mark 3 random false letters
    private boolean hint_removeExtraPlaces(){
        windowDown_01(false);
        if(is_gameOver) return false;

        if(answer.getAllLettersPositions().size() - (answer.getAnswerLettersPositions().size()+answer.getWrongAnsweredLettersPositions().size()) <3)
            return false;

        ArrayList<String> temp = new ArrayList<>(answer.getAllLettersPositions()) ;
        temp.removeAll(answer.getWrongAnsweredLettersPositions());
        temp.removeAll(answer.getAnswerLettersPositions());


        if(temp.size() < 3)
            return false;

        Collections.shuffle(temp);

        wrongLetter(Integer.valueOf(temp.get(0)) ,true,true);
        wrongLetter(Integer.valueOf(temp.get(1)),true,true);
        wrongLetter(Integer.valueOf(temp.get(2)),true,true);

        int hintedWrongLetter = session.getIntegerItem(SessionModel.KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT);
        hintedWrongLetter+=3;
        session.saveItem(SessionModel.KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT, hintedWrongLetter);

//        db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
//        db.addWord(answer,WordModel.TABLE_WORDS_GUESS_WORD_TAG);

        return true;
    }

    //meysam - mark 1 random false letters
    private boolean hint_removeOneExtraPlace(){
        windowDown_01(false);
        if(is_gameOver) return false;

        if(answer.getAllLettersPositions().size() - (answer.getAnswerLettersPositions().size()+answer.getWrongAnsweredLettersPositions().size()) <1)
            return false;

        ArrayList<String> temp = new ArrayList<>(answer.getAllLettersPositions()) ;
        temp.removeAll(answer.getWrongAnsweredLettersPositions());
        temp.removeAll(answer.getAnswerLettersPositions());

        if(temp.size() == 0)
            return false;

        Collections.shuffle(temp);

        wrongLetter(Integer.valueOf(temp.get(0)), true,true);

        int hintedWrongLetter = session.getIntegerItem(SessionModel.KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT);
        hintedWrongLetter+=1;
        session.saveItem(SessionModel.KEY_GUESS_WORD_HINTED_WRONG_LETTER_COUNT, hintedWrongLetter);

//        db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
//        db.addWord(answer,WordModel.TABLE_WORDS_GUESS_WORD_TAG);

        return true;
    }

    //meysam
    private boolean hintPutShowOneLetter() {
        windowDown_01(false);
        if(is_gameOver) return false;

        ArrayList<String> temp = new ArrayList<>(answer.getAnswerLettersPositions());
        temp.removeAll(answer.getAnsweredLettersPositions());


        if(temp.size() == 0)
            return false;

        Collections.shuffle(temp);

        correctLetter(Integer.valueOf(temp.get(0)),true);

//        db.deleteWordsByTag(WordModel.TABLE_WORDS_GUESS_WORD_TAG);
//        db.addWord(answer,WordModel.TABLE_WORDS_GUESS_WORD_TAG);

        return true;
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(GuessWordActivity.this, AdvertismentModel.GuessWordPageZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(GuessWordActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

//                new SoundModel(GuessWordActivity.this).playCountinuseRandomMusic();
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
                ad.show((Activity) GuessWordActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(GuessWordActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);
//                new SoundModel(GuessWordActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }

            @Override
            public void onNoNetwork ()
            {
                DM.hide();
                Utility.displayToast(GuessWordActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

//                new SoundModel(GuessWordActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(GuessWordActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

//                new SoundModel(GuessWordActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

//                new SoundModel(GuessWordActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                if(completed)
                {

                    AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                    if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(GuessWordActivity.this, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()) +" "+getString(R.string.msg_AdCountRemained)),getString(R.string.btn_OK),null, false, false);
                    else
                        DialogPopUpModel.show(GuessWordActivity.this, getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null, false, false);

                    // store user reward if ad.isRewardedAd() and completed is true
                    // Meysam: send Reward To server and store it in session .... meysam
//                    Utility.displayToast(context,getApplicationContext().getString(R.string.msg_AdvertismentShowCompleted),Toast.LENGTH_SHORT);


                    if( removeAdditionalCells )
                    {
                        removeAdditionalCells = false;
                        if(!hint_removeExtraPlaces())
                            Utility.displayToast(GuessWordActivity.this, getString(R.string.error_no_more), Toast.LENGTH_SHORT);


                    }
//                    else if(removeAdditionalCell)
//                    {
//                        removeAdditionalCell = false;
//                        // meysam - remove some cells
//                        if(!hint_removeExtraPlaces())
//                            Utility.displayToast(GuessWordActivity.this, getString(R.string.error_no_more), Toast.LENGTH_SHORT);
//
//                    }
                    else if(showOneLetter)
                    {
                        showOneLetter = false;
                        //meysam
                        if(! hintPutShowOneLetter())
                            Utility.displayToast(GuessWordActivity.this,getString(R.string.error_no_more),Toast.LENGTH_SHORT);
                        ///////////////////

                    }
                    else if(doubleReward)
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
                    else
                    {
                        //meysam - go to next word
                        goToNextGuessWord= false;

                        //meysam
                        finishGuessWord();
                        ////////////////////
                    }

                }
                else
                {
                    Utility.displayToast(GuessWordActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

            }
        });
    }

    private void setHintAmounts() {

        TextView hint_dh_remove_additional_cell= (TextView) findViewById(R.id.hint_dh_remove_additional_cell);
        cost_remove_additional_cell = 3;
        hint_dh_remove_additional_cell.setText(Utility.enToFa("-"+(cost_remove_additional_cell)));

        TextView hint_dh_show_first_letter= (TextView) findViewById(R.id.hint_dh_show_one_letter);
        cost_show_one_letter = 15;
        hint_dh_show_first_letter.setText(Utility.enToFa("-"+(cost_show_one_letter)));

        TextView hint_dh_next_table= (TextView) findViewById(R.id.hint_dh_next_guess_word);
        cost_next_guess_word = 50;
        hint_dh_next_table.setText(Utility.enToFa("-"+(cost_next_guess_word)));

        //meysam - set costs for removing 3 cells
        TextView hint_dh_remove_additional_cells= (TextView) findViewById(R.id.hint_dh_remove_additional_cells);
        cost_remove_additional_cells = 8;
        hint_dh_remove_additional_cells.setText(Utility.enToFa("-"+cost_remove_additional_cells));

    }

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
                    Intent i = new Intent(GuessWordActivity.this,MainActivity.class);
                    GuessWordActivity.this.startActivity(i);
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
                        Intent i = new Intent(GuessWordActivity.this, MainActivity.class);
                        GuessWordActivity.this.startActivity(i);
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
                        Intent i = new Intent(GuessWordActivity.this, MainActivity.class);
                        GuessWordActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_UNIVERSAL))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        DialogPopUpModel.show(GuessWordActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        DialogPopUpModel.show(GuessWordActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

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

        sfxm.releaseSoundPool();
        sfxm = null;

        uc.deleteObservers();
        uc = null;

        Tapsell.setRewardListener(null);
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

    @Override
    public void onBackPressed() {

        if(!windowDown_isShowing && !windowDown2_isShowing)
        {

            //meysam - set three arrayes

            //meysam - 13960727
            db.addWord(answer, WordModel.TABLE_WORDS_GUESS_WORD_TAG);
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

    private void clearAll()
    {
        number_wrong = 0;
        CageStatus();
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


//        final_hazel_amount = 0;
//        final_luck_amount = 0;

        removeAdditionalCells = false;
        showOneLetter = false;
        removeAdditionalCell = false;
        goToNextGuessWord = false;
        back_requested = false;
        home_requested = false;
        doubleReward = false;

//        cost_show_one_letter = 0;
//        cost_remove_additional_cell = 0;
//        cost_remove_additional_cells = 0;
        cost_next_guess_word = 0;

        number_ansered = 0;
        for(int i=0;i<21;i++){
                String word_name = "word_" + (i > 9 ? "" : "0") + i;
                int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
                findViewById(id).clearAnimation();
        }
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
