package ir.fardan7eghlim.luckylord.views.hazels;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.help.DialogModel_help;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.CrossTableModel;
import ir.fardan7eghlim.luckylord.models.MixedTableModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
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
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
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
import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

public class CrossTableActivity extends BaseActivity implements Observer {

    private Integer HINT_SHOW_ONE_ANSWER_COST = 50;

    private int fixed_size_cells=100;
    private int num_row=0;
    private int num_column=0;
    private LinearLayout crossTable;
    private ArrayList<QuestionModel> questions;
    private QuestionController qc;
    private ArrayList<String> table_shuffled;
    private AutofitTextView older=null;
    private LuckyTextView later=null;
    private Hashtable<String, String> hash_javab;
    private boolean[] isRightPlace;

    private DialogModel_level dml;

    final String DOUBLE_BYTE_SPACE = "\u3000";
    Typeface face;

    //meysam
    private Boolean isInCreate;
    LinearLayout ll_main_dynamic;
    private Boolean isFirstCell;
    private Integer oldIndex;
    private Integer currentIndex;
    private DialogModel DM;
    private DatabaseHandler db;
    private SoundFXModel sfxm = null;
    private int height;
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
    private LinearLayout hint_btn_dh_next_cross_table;
    private LinearLayout hint_btn_dh_ad_next_cross_table;
    private int cost_next_cross_table;
    private int cost_show_one_letter;
    private int cost_show_first_letters;
    private boolean doubleReward;
    private boolean back_requested;
    private boolean home_requested;
    private boolean showOneLetter;
    private boolean showOneAnswer;
    private boolean showFirstLetters;
    private boolean goToNextCrossTable;
    private BigInteger question_id_to_show_answer;
    private LinearLayout windowDown_dt;
    private ScrollView windowDown_SV_dt;
    private LinearLayout hints_dt;
//    private ListView list_questions;
    private LuckyButton windownClose_dt;
    private LinearLayout gameover_win;
    private LinearLayout endWin_doubleHazel;
    private FrameLayout fl_doubleHazel;
    private LinearLayout ll_reportCrossTable;
    private LinearLayout hint_btn_dh_show_one_letter;
    private LinearLayout hint_btn_dh_ad_show_one_letter;

    private LuckyTextView ltv_questions;
//    private LinearLayout hint_btn_dh_show_first_letters;
    private boolean is_gameOver=false;
    private boolean windowDown_isShowing=false;
    private boolean windowDown2_isShowing=false;
    private int final_hazel_amount;
    private int final_luck_amount;
    private int used_hazel_amount;
    private int used_luck_amount;
    private ArrayList<String> currentCells;
//    private ArrayList<String> correctCells;
    private ArrayList<AutofitTextView> ltvCells;
//    private CustomAdapterList_puzzle_question CALPQ;
    ///////////////////////////////////////////////
    private ImageView btn_zoom_out;
    private ImageView btn_zoom_in;

    private Boolean WaitingToSendCups;

    private Integer wrongAnswerPenalty = 5;//meysam - in hazel

    Animator anm_hint_ad;
    Animator anm_double_reward;
    Animator anm_hazel;
    Animator anm_luck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_table);

//        ll_main_dynamic=new LinearLayout(this);

        isInCreate = true;

        WaitingToSendCups  = false;

        face = Typeface.createFromAsset(getAssets(),
                "fonts/Khandevane.ttf");

        //meysam
        ltvCells = new ArrayList<>();
        oldIndex = null;
        currentIndex = null;
        currentCells = new ArrayList<>();
//        correctCells = new ArrayList<>();
        showOneLetter = false;
        showFirstLetters = false;
        showOneAnswer = false;
        question_id_to_show_answer = null;
        goToNextCrossTable = false;
        db=new DatabaseHandler(getApplicationContext());
        uc=new UserController(this);
        uc.addObserver(this);
        DM=new DialogModel(this);
        btn_sound= (ImageView) findViewById(R.id.btn_sound);
        btn_hints = (ImageView) findViewById(R.id.btn_hints);
        windowDown_dt= (LinearLayout) findViewById(R.id.windowDown_dt);
        windowDown_SV_dt= (ScrollView) findViewById(R.id.windowDown_SV_dt);
        hints_dt= (LinearLayout) findViewById(R.id.hints_dt);
//        list_questions=(ListView) findViewById(R.id.list_questions);
        windownClose_dt= (LuckyButton) findViewById(R.id.windownClose_dt);
        gameover_win= (LinearLayout) findViewById(R.id.gameover_win);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        if (!session.getBoolianItem(SessionModel.KEY_CROSS_TABLE_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

//            DialogPopUpModel.show(CrossTableActivity.this, getString(R.string.dlg_TutorialCrossTable), getString(R.string.btn_OK), null,false, false);
            DialogModel_help dm_help=new DialogModel_help(CrossTableActivity.this);
            dm_help.show(3);
            session.saveItem(SessionModel.KEY_CROSS_TABLE_IS_FIRST_TIME, true);

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
//                .force((float) 0.1)
                .createFor(iv_luck_amount);
        anm_luck.start();

        anm_hazel = Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .repeatCount(1000000)                              // Repeat once
                .duration(8000)  // Last for 1000 milliseconds(1 second)
                .delay(2500)
//                .force((float) 0.1)
                .createFor(iv_hazel_amount);
        anm_hazel.start();

        endWin_hazelAmount = (LuckyTextView) findViewById(R.id.endWin_hazelAmount);
        endWin_luckAmount = (LuckyTextView) findViewById(R.id.endWin_luckAmount);

        endWin_luckAmount.setText("0");
        endWin_hazelAmount.setText("0");

        ltv_questions = (LuckyTextView) findViewById(R.id.list_questions);

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

        hint_btn_dh_next_cross_table= (LinearLayout) findViewById(R.id.hint_btn_dh_next_cross_table);
        hint_btn_dh_ad_next_cross_table= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_next_cross_table);
        hint_btn_dh_show_one_letter= (LinearLayout) findViewById(R.id.hint_btn_dh_show_one_letter);
        hint_btn_dh_ad_show_one_letter= (LinearLayout) findViewById(R.id.hint_btn_dh_ad_show_one_letter);
//        hint_btn_dh_show_first_letters= (LinearLayout) findViewById(R.id.hint_btn_dh_show_first_letters);
        endWin_doubleHazel = (LinearLayout) findViewById(R.id.endWin_doubleHazel);
        ll_reportCrossTable = (LinearLayout) findViewById(R.id.ll_reportCrossTable);
        fl_doubleHazel = findViewById(R.id.fl_doubleHazel);

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
                        DialogPopUpModel.show(CrossTableActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                    }
                }
            }
        });
        hint_btn_dh_show_one_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(CrossTableActivity.this, cost_show_one_letter))
                {
                    DialogPopUpModel.show(CrossTableActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                                                            new DialogModel_hazel(CrossTableActivity.this).show(false, false, new Integer(cost_show_one_letter));
                                                        }
                                                    });

                                                    Utility.changeCoins(CrossTableActivity.this,-1*cost_show_one_letter);
                                                    tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
                                                }
                                                else
                                                {
                                                    Utility.displayToast(CrossTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);//meysam commented in 13960727
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
                    DialogPopUpModel.show(CrossTableActivity.this, Utility.enToFa("باید حداقل " + cost_show_one_letter + " فندق داشته باشی!"), "باشه", null, false, false);

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
                    DialogPopUpModel.show(CrossTableActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });
        hint_btn_dh_next_cross_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.hasEnoughCoin(CrossTableActivity.this, cost_next_cross_table))
                {

                    DialogPopUpModel.show(CrossTableActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false );
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

                                                session.changeFinalHazel(-1*cost_next_cross_table);

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new DialogModel_hazel(CrossTableActivity.this).show(false, false, new Integer(cost_next_cross_table));
                                                    }
                                                });


                                                Utility.changeCoins(CrossTableActivity.this,-1*cost_next_cross_table);
                                                tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                //meysam
                                                finishCrossTable(false);
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
                    DialogPopUpModel.show(CrossTableActivity.this, Utility.enToFa("باید حداقل " + cost_next_cross_table + " فندق داشته باشی!"), "باشه", null, false, false);

                }
            }
        });

        hint_btn_dh_ad_next_cross_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam -movie ad

                if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                {
                    DM.show();
                    WatchAd();

                    closeWindows();
                }
                else
                {
                    DialogPopUpModel.show(CrossTableActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                }
            }
        });
        ll_reportCrossTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - report word

                ReportDialogPopUpModel.show(CrossTableActivity.this);
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
                                    qc.addObserver(CrossTableActivity.this);
                                    qc.universalReport(new BigInteger(String.valueOf( session.getIntegerItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID))),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description, ReportDialogPopUpModel.CROSS_TABLE_TYPE);

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

                //meysam - check if end window is up or not...
                if(!windowDown2_isShowing){
                    ////////////////////////////////////////////////////

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

                //meysam - check if end window is up or not...
                if(!windowDown2_isShowing){

                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int dpWidth = size.x;
                    if(dpWidth<(fixed_size_cells+50)) return;
                    fixed_size_cells+=50;
                    crossTable.removeAllViews();
                    reload(false);
                }
                ////////////////////////////////////////////////////

            }
        });


        crossTable= (LinearLayout) findViewById(R.id.crossTable);
        qc = new QuestionController(this);
        qc.addObserver(this);

        DM.show();
        initializeTable();

        //meysam = 13960727
//        db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);//meysam - must be deleted...
        questions = db.getQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
        if(questions.size() == 0)
        {
            qc.crossTable(0,0);
        }
        else
        {
            num_column = session.getIntegerItem(SessionModel.KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT);
            num_row = session.getIntegerItem(SessionModel.KEY_CROSS_TABLE_SINGLE_ROW_COUNT);

            setHintAmounts();
//            fillList(questions);
            //////////////////////////

            setQuestionsFinalLuckAndHazels();

            // meysam - reload table set based on last resoults from db...
            fixed_size_cells=Zoom(true);
            reload(false);
            ////////////////////////////////////////////////////////////////
            DM.hide();
        }
        //////////////////////////////////////////////////////

        isInCreate = false;
        init_bidimential();
    }
    //meysam - reload from session...
    private void reload(boolean is_init) {

        try
        {
            isInCreate = true;
            //meysam
            ltvCells = new ArrayList<>();
//        correctCells = MixedTableModel.generateCorrectCells(questions);
//        correctCells = new ArrayList<>();

            if(!is_init) {
                //meysam - get last letter and positions from session
                currentCells = Utility.convertStringToArrayStrig(session.getStringItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS));
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
//                currentCells.add(null);
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
                                session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
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

                            tv.setOnClickListener(questionOnClickListener);

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

                            tv2.setOnClickListener(questionOnClickListener);
                        }else{
                            //is question cell with 1 question
                            final AutofitTextView tv=new AutofitTextView(this);
                            tv.setLayoutParams(lparams);
                            tv.setTag(count_column++);

                            if(is_init){
                                currentCells.add(Integer.valueOf(temp_int),value.get(0).substring(0,value.get(0).indexOf("#")));
                                session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
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
//                        tv.setText(currentCells.get(Integer.valueOf(temp_int)));
//                        tv.setGravity(Gravity.CENTER);
                            //meysam
                            ltvCells.add(tv);
                            //////////////////////
                            ll_main_dynamic.addView(tv);
                            ////////
                            tv.setOnClickListener(questionOnClickListener);
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
                                session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
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
                                session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
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
//                                ltvCells.get(index).setBackgroundResource(R.drawable.bg_05_clker2);
                                    oldIndex = index;
                                    ///////////////////////////////////
                                    older=tv;
                                    tv.setBackgroundResource(R.drawable.diamond_gray);


                                }else if(older==tv) {
                                    //meysam
//                                int index = ltvCells.indexOf(older);
//                                ltvCells.get(index).setBackgroundResource(R.drawable.bg_05_clker);
                                    oldIndex = null;
                                    ///////////////////////////////////
                                    older.setBackgroundResource(R.drawable.diamond_orange);
                                    older=null;
                                }else{
                                    //meysam
                                    int index = ltvCells.indexOf(tv);
//                                ltvCells.get(oldIndex).setText(tv.getText().toString());
//                                ltvCells.get(oldIndex).setBackgroundResource(R.drawable.bg_05_clker);
                                    currentIndex = index;
//                                ltvCells.get(currentIndex).setText(older.getText().toString());
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

//                                MixedTableModel.swapAnswerIndexesQuestion(questions,oldIndex, currentIndex);
//                                db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
//                                db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_CROSS_TAG);

//                                Collections.swap(ltvCells, oldIndex, currentIndex);
                                    session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
                                    oldIndex = null;
                                    currentIndex = null;
                                    ////////////////////////////////////////////////////////
                                    older=null;
                                    if(isWin()){
//                                    Utility.displayToast(getApplicationContext(), "Win", Toast.LENGTH_SHORT);
                                        // meysam - show next table dialog.... no need
                                        if(!is_gameOver)
                                            gameOver(true);
                                    }
                                }
                            }
                        });
                    }
                }
            }
            isInCreate = false;
        }
        catch (Exception ex)
        {
            db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
            session.removeItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS);
            session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_ROW_COUNT);
            session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT);
            session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID);
            Utility.generateLogError(ex);
            Utility.displayToast(CrossTableActivity.this,getString(R.string.msg_ErrorProblemOccured),Toast.LENGTH_SHORT);
            CrossTableActivity.this.finish();
        }


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
//            MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions,temp_int);
//            db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
//            db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
            // meysam - check if all questions are answered then end game.....
            MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions,temp_int,true);
//            if(MixedTableModel.isCellLastAnswered(questions,temp_int))
//            {
////                //meysam - turn all cells of question blue
////                QuestionModel cellQuestion = MixedTableModel.getRelatedCellQuestion(questions,temp_int);
////                int index = questions.indexOf(cellQuestion);
////                questions.get(index).setAnswered(true);
//
//            }

            db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
            db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
            if(MixedTableModel.isAllQuestionsAnswered(questions))
                gameOver(true);
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

        session.changeFinalHazel(-1*wrongAnswerPenalty);
        Utility.changeCoins(getApplicationContext(),-1*wrongAnswerPenalty);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DialogModel_hazel(CrossTableActivity.this).show(false, false, new Integer(wrongAnswerPenalty));
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

    @Override
    public void update(Observable o, Object arg)
    {
        DM.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(CrossTableActivity.this,MainActivity.class);
                    CrossTableActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            } else if (arg instanceof ArrayList) {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CROSS_TABLE_QUESTION))
                {
                    db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
                    session.removeItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS);
                    session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_ROW_COUNT);
                    session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT);
                    session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID);


                    ltv_questions.setVisibility(View.GONE);
                    ltv_questions.setText("");

                    int id = Integer.valueOf(((ArrayList) arg).get(1).toString());
                    num_column = Integer.valueOf(((ArrayList) arg).get(2).toString());
                    num_row = Integer.valueOf(((ArrayList) arg).get(3).toString());
                    questions=(ArrayList<QuestionModel>) ((ArrayList) arg).get(4);

                    if(questions.size()<1){
                        Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                        Intent i = new Intent(CrossTableActivity.this,MainActivity.class);
                        CrossTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }

                    //meysam - save row and column counts as well as table id to session(using in reporting problem questions)
                    session.saveItem(SessionModel.KEY_CROSS_TABLE_SINGLE_ROW_COUNT,num_row);
                    session.saveItem(SessionModel.KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT,num_column);
                    session.saveItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID,id);
                    //meysam - 13960727
                    db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
                    ///////////////////

                    session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                    session.removeItem(SessionModel.KEY_FINAL_LUCK);


                    setQuestionsFinalLuckAndHazels();

                    //meysam - calculate hint amounts

                    setHintAmounts();
//                    fillList(questions);
                    //////////////////////////

                    reload(true);
                    if(num_column>4)
                        forceZoom();
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
                        Intent i = new Intent(CrossTableActivity.this, MainActivity.class);
                        CrossTableActivity.this.startActivity(i);
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
                        Intent i = new Intent(CrossTableActivity.this, MainActivity.class);
                        CrossTableActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_UNIVERSAL))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        DialogPopUpModel.show(CrossTableActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        DialogPopUpModel.show(CrossTableActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

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

                                    Intent i = new Intent(CrossTableActivity.this, MainActivity.class);
                                    CrossTableActivity.this.startActivity(i);
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

                                Intent i = new Intent(CrossTableActivity.this, MainActivity.class);
                                CrossTableActivity.this.startActivity(i);
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

    //meysam
    private void initializeTable()
    {
        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

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
    private void setQuestionsFinalLuckAndHazels() {

        reward_hazel_amount = questions.size();
        reward_luck_amount = 1;
        endWin_hazelAmount.setText(Utility.enToFa(reward_hazel_amount+""));
        endWin_luckAmount.setText(Utility.enToFa(reward_luck_amount+""));
    }

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
//            list_questions.setVisibility(View.GONE);
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
//                list_questions.setVisibility(View.GONE);
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
//                    list_questions.setVisibility(View.VISIBLE);
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
//            list_questions.setVisibility(View.GONE);
            windownClose_dt.setVisibility(View.GONE);
        }
    }

    private int Zoom(boolean default_value){
        if(default_value){
//            Display display = getWindowManager().getDefaultDisplay();
//            DisplayMetrics outMetrics = new DisplayMetrics ();
//            display.getMetrics(outMetrics);
//
//            float density  = getResources().getDisplayMetrics().density;
//            float dpHeight = outMetrics.heightPixels / density;
//            float dpWidth  = outMetrics.widthPixels / density;
//            Configuration configuration = getApplicationContext().getResources().getConfiguration();
//            int dpWidth = configuration.screenWidthDp;
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

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(CrossTableActivity.this, AdvertismentModel.CrossTablePageZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(CrossTableActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

//                new SoundModel(CrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                showFirstLetters = false;
                showOneAnswer = false;
                showOneLetter = false;
                question_id_to_show_answer = null;
                doubleReward = false;

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {
                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) CrossTableActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(CrossTableActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);
//                new SoundModel(CrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                showFirstLetters = false;
                showOneAnswer = false;
                showOneLetter = false;
                question_id_to_show_answer = null;
                doubleReward = false;
            }

            @Override
            public void onNoNetwork ()
            {
                DM.hide();
                Utility.displayToast(CrossTableActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

//                new SoundModel(CrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                showFirstLetters = false;
                showOneAnswer = false;
                showOneLetter = false;
                question_id_to_show_answer = null;
                doubleReward = false;
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(CrossTableActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

//                new SoundModel(CrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                showFirstLetters = false;
                showOneAnswer = false;
                showOneLetter = false;
                question_id_to_show_answer = null;
                doubleReward = false;
            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

//                new SoundModel(CrossTableActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


                if(completed)
                {

                    AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                    if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(CrossTableActivity.this, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()) +" "+getString(R.string.msg_AdCountRemained)),getString(R.string.btn_OK),null, false, false);
                    else
                        DialogPopUpModel.show(CrossTableActivity.this, getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null, false, false);


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
                    else if(showOneLetter)
                    {
                        showOneLetter = false;

                        //meysam
                        // meysam - show one true letter
                        if(! hintPutShowOneLetter())
                            Utility.displayToast(CrossTableActivity.this, getString(R.string.error_alredy_given), Toast.LENGTH_SHORT);
                        ///////////////////

                    }
                    else if(showOneAnswer)
                    {
                        showOneAnswer = false;
                        //meysam - show choosen question answer...
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpFourVerticalModel.show(CrossTableActivity.this,MixedTableModel.getQuestionById(questions,question_id_to_show_answer).getAnswer(),CrossTableActivity.this.getString(R.string.btn_OK),null,null,null,false,true);
                            }
                        });

                        //////////////////////////////////////////////////
                    }
                    else
                    {
                        //meysam
                        finishCrossTable(false);
                        ////////////////////
                    }

                    showFirstLetters = false;
                    showOneAnswer = false;
                    showOneLetter = false;
                    question_id_to_show_answer = null;
                    doubleReward = false;

                }
                else
                {
                    Utility.displayToast(CrossTableActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

            }
        });
    }

    //meysam
    private void finishCrossTable(Boolean playVictorySound){

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

//        fillList(answer.getWords());
        gameOver(playVictorySound);

        windowDown_01(false);
    }

//    //fill list of questions
//    private void fillList(ArrayList<QuestionModel> questions) {
//        //make list
//        ViewGroup.LayoutParams params = list_questions.getLayoutParams();
//        params.height = (int) (height*0.8);
//        list_questions.requestLayout();
//
//        CALPQ = new CustomAdapterList_puzzle_question(this, new ArrayList<Object>(questions), RequestRespondModel.TAG_CROSS_TABLE_QUESTION);
//        list_questions.setAdapter(CALPQ);
//
//        list_questions.setRecyclerListener(new AbsListView.RecyclerListener() {
//            @Override
//            public void onMovedToScrapHeap(View view) {
//                // Release strong reference when a view is recycled
//            }
//        });
//
//        list_questions.invalidateViews();
//    }

    //meysam
    private void setHintAmounts() {

        TextView hint_dh_next_table= (TextView) findViewById(R.id.hint_dh_next_cross_table);
        cost_next_cross_table = 100;
        hint_dh_next_table.setText(Utility.enToFa("-"+(cost_next_cross_table)));


        TextView hint_dh_show_one_letter= (TextView) findViewById(R.id.hint_dh_show_one_letter);
        cost_show_one_letter = 10;
        hint_dh_show_one_letter.setText(Utility.enToFa("-"+(cost_show_one_letter)));


//        TextView hint_dh_show_first_letters= (TextView) findViewById(R.id.hint_dh_show_first_letters);
//        cost_show_first_letters = 100;
//        hint_dh_show_first_letters.setText(Utility.enToFa("-"+cost_show_first_letters));
    }

//    private boolean hintPutShowAllFirstLetter(){
//        windowDown_01(false);
//        if(is_gameOver) return false;
//        // meysam - complete this...
////        ArrayList<Integer> firstCellsIndex = getFirstCellsForHint();
////        if(firstCellsIndex.size() == 0) return false;
////
////        for(int i=0;i<firstCellsIndex.size();i++){
////            int pos=firstCellsIndex.get(i);
////            String word_name="word_"+(pos>9?"":"0")+pos;
////            int id = getResources().getIdentifier(word_name, "id", getApplicationContext().getPackageName());
////            TextView temp= (TextView) findViewById(id);
////            temp.setBackgroundResource(R.drawable.bg_tetragon_blue);//become blue
////            MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions,pos);
////        }
////        db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
//        return true;
//    }

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
        session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));

//        // meysam - check if is last cell -- if it is then turn all cell questions to blue
//        if(MixedTableModel.isCellLastCellInAnswer(questions,nextCellForHintIndex))
//        {
//            //meysam - turn all cells of question blue
//            QuestionModel cellQuestion = MixedTableModel.getRelatedCellQuestion(questions,nextCellForHintIndex);
//            int index = questions.indexOf(cellQuestion);
//            questions.get(index).setAnswered(true);
//
//
//        }
//
//        MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions,nextCellForHintIndex);
//        db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_DARHAM_TAG);

        checkCellIsInRightPlace(ltv_nextCellForHint,false,true);
        checkCellIsInRightPlace(ltv_nextCellForHintSwap,true,false);

//        if(MixedTableModel.isAllQuestionsAnswered(questions))
//            gameOver();
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
//                                MixedTableModel.addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(questions, new Integer(temp.get(j)));

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

        //TODO: meysam - check and find a cell that with swapping dosnt become an answer itself!!...

        return result;
    }

    private void gameOver(Boolean playVictorySound){

        //meysam - play win table sound
        if(playVictorySound)
        {
            SoundModel.playSpecificSound(R.raw.victory_match,getApplicationContext(),false,null,null,null);

            //meysam - add score...
            if(Utility.addLevelScore(getApplicationContext(), UserModel.REWARD_AMOUNT_LEVEL_SCORE_CROSS_TABLE))
            {
                //meysam - an increase in level occured...
                // meysam - show earned level animation...
                //meysam - no need to store session variable for show...
                int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()),Integer.valueOf(session.getCurrentUser().getLevelScore())+ session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                dml = new DialogModel_level(CrossTableActivity.this);
                dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())),Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()))+levelDiffrence);
                /////////////////////////////////////////////
                //meysam - send new level to server ...
                uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                ///////////////////////////////////////

            }


            Random r = new Random();
            int randomInt = r.nextInt(100) + 1;

            if(randomInt < 20)//meysam - must be lower than 20 %...
            {
                endWin_doubleHazel.setEnabled(true);
                endWin_doubleHazel.setVisibility(View.VISIBLE);
                fl_doubleHazel.setVisibility(View.VISIBLE);
                fl_doubleHazel.invalidate();
                endWin_doubleHazel.invalidate();
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
        }

        /////////////////////////////
        //meysam - 13960727
        db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
        session.removeItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS);
        session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_ROW_COUNT);
        session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT);
//        session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID);

        // meysam - fill questions and answer list in end window...
        ltv_questions.setVisibility(View.VISIBLE);
        fillQuestionList();

        is_gameOver=true;
        //game over
        for(int index=0; index<crossTable.getChildCount(); ++index) {
            View nextChild = crossTable.getChildAt(index);
            Random rnd = new Random();
            int r1_duration = rnd.nextInt(1000) + 1000;
            int r2_delay = rnd.nextInt(100) + 1000;
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FALL)
                    .interpolator(Flubber.Curve.BZR_EASE_IN)
                    .repeatCount(1)
                    .repeatMode(ValueAnimator.RESTART)
                    .duration(r1_duration)
                    .delay(r2_delay)
                    .createFor(nextChild)
                    .start();
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                windowDown_02(true);

//                fillList(questions);
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
                    final_hazel_amount += reward_hazel_amount;
                    final_luck_amount += reward_luck_amount;

                    session.changeFinalHazel(reward_hazel_amount);
                    session.changeFinalLuck( reward_luck_amount);

                    //meysam - add reward to session
                    Utility.changeCoins(getApplicationContext(),reward_hazel_amount);
                    Utility.changeLucks(getApplicationContext(),reward_luck_amount);
                    ///////////////////////////////
                }


                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {
                    DM.show();
                    home_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {
                    Intent i = new Intent(CrossTableActivity.this, MainActivity.class);
                    CrossTableActivity.this.startActivity(i);
                    finish();
                }

                db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
                session.removeItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS);
                session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_ROW_COUNT);
                session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_COLUMN_COUNT);
                session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID);
            }
        });
        LinearLayout endWin_next= (LinearLayout) findViewById(R.id.endWin_next);
        endWin_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!endWin_hazelAmount.getText().equals("0") && !endWin_luckAmount.getText().equals("0"))
                {
                    final_hazel_amount += reward_hazel_amount;
                    final_luck_amount += reward_luck_amount;

                    session.changeFinalHazel(reward_hazel_amount);
                    session.changeFinalLuck( reward_luck_amount);

                    //meysam - add reward to session
                    Utility.changeCoins(getApplicationContext(),reward_hazel_amount);
                    Utility.changeLucks(getApplicationContext(),reward_luck_amount);
                    ///////////////////////////////
                }

                session.removeItem(SessionModel.KEY_CROSS_TABLE_SINGLE_TABLE_ID);

                // meysam - reload next table
                closeWindows();

                DM.show();
                clearAll();
                initializeTable();
                qc.crossTable(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));

            }
        });
    }

    private void fillQuestionList()
    {
        StringBuilder sb_temp = new StringBuilder();
        for(int i = 0; i < questions.size(); i++)
        {
            sb_temp.append(" سوال: " + questions.get(i).getDescription());
            sb_temp.append("\n");
            sb_temp.append(" جواب: " +questions.get(i).getAnswer());
            sb_temp.append("\n");
            sb_temp.append("\n");
        }

        ltv_questions.setText(sb_temp.toString());
    }

    //meysam
    private void clearAll()
    {
        is_gameOver = false;

        endWin_doubleHazel.setEnabled(false);
        endWin_doubleHazel.setVisibility(View.GONE);
        fl_doubleHazel.setVisibility(View.GONE);
        if(anm_double_reward != null)
        {
            if(anm_double_reward.isRunning())
                anm_double_reward.end();
            anm_double_reward.cancel();
            anm_double_reward.removeAllListeners();
        }

        back_requested = false;
        home_requested = false;
        doubleReward = false;

        cost_next_cross_table = 0;

        final_hazel_amount = 0;
        final_luck_amount = 0;

        reward_luck_amount = 0;
        reward_hazel_amount = 0;

        used_hazel_amount = 0;
        used_luck_amount = 0;

        showOneLetter = false;
        showOneAnswer = false;
        question_id_to_show_answer = null;
        showFirstLetters = false;
        goToNextCrossTable = false;
        back_requested = false;
        doubleReward = false;

        cost_show_first_letters = 0;
        cost_show_one_letter = 0;
        cost_next_cross_table = 0;

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


    }

    public void onDestroy() {

        if(dml != null)
        {
            dml.dismiss();
            dml = null;
        }

        if(DM != null)
        {
            DM.hide();
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
    public void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        if(num_column>4)
            forceZoom();
    }

    private void forceZoom() {
        if(!windowDown2_isShowing){
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

    @Override
    public void onBackPressed() {

        if(!windowDown_isShowing && !windowDown2_isShowing)
        {
            //meysam
            session.saveItem(SessionModel.KEY_CROSS_TABLE_CURRENT_CELLS,Utility.convertArrayStringToStrig(currentCells));
            db.deleteQuestionsByTag(QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
            db.saveTableQuestions(questions, QuestionModel.TABLE_QUESTIONS_CROSS_TAG);
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

//    private class CheckCellsInRightPlaceAsyncTask extends AsyncTask<Void, Void, Void>
//    {
//        @Override
//        protected Void doInBackground(Void... params) {
//            checkCellIsInRightPlace(ltv_nextCellForHint,false,true);
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            checkCellIsInRightPlace(ltv_nextCellForHintSwap,true,false);
//        }
//    }

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

    private View.OnClickListener questionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
//            DialogPopUpModel.show(CrossTableActivity.this,v.toString(),getString(R.string.btn_OK),null,false, true);
            final  AutofitTextView tv =(AutofitTextView)v;
            final Integer cost = MixedTableModel.getQuestionByTitle(questions,tv.getText().toString()).getAnswerCells().size()*8;
            DialogPopUpFourVerticalModel.show(CrossTableActivity.this, tv.getText().toString()," یه تبلیغ ببین و جواب رو ببین ", Utility.enToFa(String.valueOf(cost))+" تا فندق بده و جواب رو ببین ",null,"بستن",true, true);
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
                                showOneAnswer = true;
                                question_id_to_show_answer = MixedTableModel.getQuestionByTitle(questions,tv.getText().toString()).getId();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DM.show();
                                    }
                                });
                                WatchAd();

                            }
                            else if(DialogPopUpFourVerticalModel.dialog_result==2)
                            {
                                //recieve hazel from user if have it
                                if(Utility.hasEnoughCoin(CrossTableActivity.this,cost))
                                {
                                    showOneAnswer = true;
                                    question_id_to_show_answer = MixedTableModel.getQuestionByTitle(questions,tv.getText().toString()).getId();

                                    final_hazel_amount -= cost;
                                    used_hazel_amount += cost;

                                    session.changeFinalHazel(-1*cost);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new DialogModel_hazel(CrossTableActivity.this).show(false, false, cost);

                                            Utility.changeCoins(CrossTableActivity.this,-1*cost);
                                            tv_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                            tv_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //Do something after 3000ms
                                                    DialogPopUpModel.show(CrossTableActivity.this,MixedTableModel.getQuestionById(questions,question_id_to_show_answer).getAnswer(),CrossTableActivity.this.getString(R.string.btn_OK),null,false,true);
                                                    showOneAnswer = false;
                                                    question_id_to_show_answer = null;
                                                }
                                            }, 1500);

                                        }
                                    });

                                }
                                else
                                {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utility.displayToast(CrossTableActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);
                                        }
                                    });
                                }
                            }
                            else if(DialogPopUpFourVerticalModel.dialog_result==4)
                            {
                                //meysam - do nothing
                                showOneAnswer = false;
                                question_id_to_show_answer = null;
                            }
                            else
                            {
                                //meysam - do nothing
                                showOneAnswer = false;
                                question_id_to_show_answer = null;
                            }

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
    };

}
