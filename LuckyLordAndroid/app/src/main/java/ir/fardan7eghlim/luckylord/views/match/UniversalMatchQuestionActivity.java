package ir.fardan7eghlim.luckylord.views.match;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_cup;
import ir.fardan7eghlim.luckylord.utils.DialogModel_luck;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.ReportDialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.SoundFXModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
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

public class UniversalMatchQuestionActivity extends BaseActivity implements Observer {

    public static UniversalMatchModel match;
    public static Integer questionNumber;
    private TextView tv_timer;
    private TextView tv_question_description;
    private TextView tv_question_title;
    private TextView tv_question_count;
    private LuckyTextView ltv_category;
    private ImageView iv_question_image;
    public Handler customHandler = new Handler();
    private int seconds;
    private int minutes;
    private boolean keyboardIsShowing = false;
    private int selected_answer_id = 0;
    private EditText answer_et;
    private LinearLayout layout_question_section;
    private String horoofs[] = {"آ", "ب", "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی"};
    private String Letters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int height;
    private DialogModel DM;
    private Observer cntx;
    private boolean IsAdForRewardQuestion;
    public static boolean ShowRewardQuestion = false;
    private static boolean returned_from_ad = false;
    private boolean inCreate = false;
    private boolean adShowing = false;

    private ArrayList<String> queueTags;
    private Handler showAnswerDelayHandler;
    private Runnable showAnswerDelayRunnable;

    private boolean forHint = false;

    private boolean back_requested = false;
    private boolean home_requested = false;

    private SoundFXModel sfxm = null;

//    private SessionModel session;

    public static int used_hazel_amount = 0;
    public static int used_luck_amount = 0;

    public static UniversalMatchQuestionActivity MQA;

    private UserController uc;
    private QuestionController qc;
    private MatchController mc;


    private TextView tv_total_hazel_amount;
    private TextView tv_total_luck_amount;
    private ImageView iv_like;
    private ImageView iv_hate;
    private ImageView iv_report;


    ImageView iv_home_text;
    ImageView iv_home_test;

    ImageView iv_sound_text;
    ImageView iv_sound_test;

    HomeController hc;

    private Boolean WaitingToSendCups;

    Animator anm_hint_ad;
    Animator anm_hazel;
    Animator anm_luck;

    private Runnable requestQuestionTimerThread = new Runnable() {

        public void run() {
            if(UniversalMatchQuestionActivity.MQA == null)
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

    private ArrayList<Integer> remind_selected;

    private FrameLayout opponentAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_question);

        WaitingToSendCups = false;

        IsAdForRewardQuestion = false;
        inCreate = true;

        showAnswerDelayHandler = null;
        showAnswerDelayRunnable = null;

        hc = new HomeController(getApplicationContext());
        hc.addObserver(this);

        MQA = this;

        queueTags = new ArrayList<String>();

//        session = new SessionModel(getApplicationContext());


        iv_home_test = (ImageView) findViewById(R.id.lb_home_test_hlq);
        iv_home_text = (ImageView) findViewById(R.id.lb_home_text_hlq);

        iv_sound_test = (ImageView) findViewById(R.id.lb_sound_test_hlq);
        iv_sound_text = (ImageView) findViewById(R.id.lb_sound_text_hlq);

        ltv_category = (LuckyTextView) findViewById(R.id.category_mq);

        setAllButtonsListeners();

        opponentAvatar = findViewById(R.id.result_op_avt);
        opponentAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });

        tv_total_hazel_amount = (TextView) findViewById(R.id.tv_hazel_amount);
        tv_total_luck_amount = (TextView) findViewById(R.id.tv_luck_amount);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        iv_hate = (ImageView) findViewById(R.id.iv_hate);
        iv_report = (ImageView) findViewById(R.id.iv_report);

        setReportLikeHateListeners();

        tv_total_hazel_amount.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        tv_total_luck_amount.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

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

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("universal_match_question_activity_broadcast"));

        cntx = this;
        DM=new DialogModel(UniversalMatchQuestionActivity.this);

        //for Tapsell ...
        Tapsell.initialize((Context) cntx, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////
        sfxm = new SoundFXModel(getApplicationContext());

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        mc =  new MatchController(getApplicationContext());
        mc.addObserver(this);

        qc = new QuestionController(getApplicationContext());
        qc.addObserver(this);




        tv_timer = (TextView) findViewById(R.id.time_counter_mq);
        tv_question_description = (TextView) findViewById(R.id.questionText_hlq);
        tv_question_title = (TextView) findViewById(R.id.tv_question_title_mqa);
        iv_question_image = (ImageView) findViewById(R.id.questionPic_hlq);
        tv_question_count = findViewById(R.id.tv_question_count);

        Integer countCorrectAnsweredQuestions = UniversalMatchModel.countCorrectAnswers(match.getQuestions());
        if(countCorrectAnsweredQuestions == 0)
            tv_question_count.setText("صحیح: 0");
        else
            tv_question_count.setText(Utility.enToFa("صحیح: "+countCorrectAnsweredQuestions));

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

        layout_question_section = (LinearLayout) findViewById(R.id.layout_question_section);

        //set avatars

        Avatar self_avatar = new Avatar(session.getCurrentUser().getProfilePicture());
        if(match == null || match.getOpponent() == null)
            finish();
        else
        {
            Avatar opponent_avatar = new Avatar(match.getOpponent().getProfilePicture());

//        View opponent_row_view = findViewById(R.id.result_op_avt);
            opponent_avatar.setAvatar_opt(this);


//        View self_row_view = findViewById(R.id.result_avt);
            self_avatar.setAvatar(this);
            //////////////////////////////
            //check if it is 11's question??!!??
            if(questionNumber == (match.getQuestions().size()-1))
            {
                if(match.getNitro())
                {
//                Utility.displayToast(getApplicationContext(),"این آخرین سوال بود!!",Toast.LENGTH_SHORT);
                    endMatch();
                }
                else
                {
                    Random rnd = new Random(new Date().getTime());
                    // meyam - uncomment for production
                    int x = rnd.nextInt(100);
                    // meyam - comment for production
//            int x = 20;
                    boolean temp = UniversalMatchModel.isSpentTimeWithinPersent(minutes,seconds,70,UniversalMatchModel.getCurrentMatchTime(getApplicationContext()));

                    if( x < 70 && temp)
                    {
                        Double time_cost_temp = UniversalMatchModel.getCurrentMatchBet(getApplicationContext())*0.2;
                        if(time_cost_temp.equals(0.0))
                        {
                            time_cost_temp = 30.0;
                        }
                        final Double reward_time_cost = time_cost_temp;


                        // meysam - show reward question dialog
                        String vahed = "فندق";
                        if(match.getRewardLuck())
                            vahed = "شانس";
                        DialogPopUpFourVerticalModel.show(UniversalMatchQuestionActivity.this,getString(R.string.msg_RewardQuestionAvailable),getString(R.string.btn_SeeAd),reward_time_cost.intValue()+ " تا "+ vahed + " میدم ",null,getString(R.string.btn_No),true, false);
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
                                            IsAdForRewardQuestion = true;
                                            showLoading();
                                            WatchAd();

                                        }
                                        else if(DialogPopUpFourVerticalModel.dialog_result==2)
                                        {
                                            if(match.getRewardLuck())
                                            {
                                                //recieve hazel from user if have it
                                                if(Utility.hasEnoughLuck(UniversalMatchQuestionActivity.this,reward_time_cost.intValue()))
                                                {
                                                    session.decreaseLuck(String.valueOf(reward_time_cost.intValue()));
                                                    used_luck_amount += reward_time_cost.intValue();
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            //stuff that updates ui
                                                            changeLuckAndReward(-1*reward_time_cost.intValue(),0);

                                                        }
                                                    });

                                                    showRewardQuestion();
                                                }
                                                else
                                                {
                                                    showNotEnoughLuckMessage();

//                                            showLoading();
//                                            match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
//                                            match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());
//                                            MatchController mc = new MatchController(getApplicationContext());
//                                            mc.addObserver(MatchQuestionActivity.this);
//                                            mc.result(match,used_luck_amount,used_hazel_amount);
//                                            Utility.displayToast(MatchQuestionActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);
//                                            DialogPopUpModel.show(MatchQuestionActivity.this,getString(R.string.msg_NotEnoughHazel),getString(R.string.btn_OK),null);
                                                }
                                            }
                                            else
                                            {
                                                //recieve hazel from user if have it
                                                if(Utility.hasEnoughCoin(UniversalMatchQuestionActivity.this,reward_time_cost.intValue()))
                                                {
                                                    session.decreaseHazel(String.valueOf(reward_time_cost.intValue()));
                                                    used_hazel_amount += reward_time_cost.intValue();
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            //stuff that updates ui
                                                            changeLuckAndReward(0,-1*reward_time_cost.intValue());

                                                        }
                                                    });

                                                    showRewardQuestion();
                                                }
                                                else
                                                {
                                                    showNotEnoughHazelMessage();

//
                                                }
                                            }

                                        }
                                        else
                                        {
                                            //end match

                                            showLoading();
                                            match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                                            match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

                                            if(customHandler != null)
                                            {
                                                customHandler.removeCallbacks(requestQuestionTimerThread);
                                                customHandler.removeCallbacksAndMessages(null);

                                            }

                                            mc.universalResult(match,used_luck_amount,used_hazel_amount);

                                            queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);
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
                    else
                    {
//                    Utility.displayToast(getApplicationContext(),"این آخرین سوال بود!!",Toast.LENGTH_SHORT);
                        endMatch();
                    }
                }


            }
            else if(questionNumber == match.getQuestions().size())
            {
                if(ShowRewardQuestion)
                {
                    ShowRewardQuestion = false;
                    //set question and show answer scenario wrt type
                    questionNumber--;
                    setQuestion();
                    customHandler.postDelayed(requestQuestionTimerThread, 1000);
                }
                else
                {

                    endMatch();
                }
            }
            else
            {
                //set question and show answer scenario wrt type
                setQuestion();
                customHandler.postDelayed(requestQuestionTimerThread, 1000);
            }
        }



        ////////////////////////////////////////////////////////


//        Utility.displayToast(getApplicationContext(),match.getQuestions().get(questionNumber).getId().toString(),Toast.LENGTH_SHORT);
        // meysam remove for development...
//        Utility.displayToast(getApplicationContext(),match.getQuestions().get(questionNumber).getAnswer(),Toast.LENGTH_SHORT);

    }

    private void setAllButtonsListeners() {

        setHomeButtonListener();
        setSoundButtonListener();
    }

    private void setSoundButtonListener() {

        if(session.getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
        {
            iv_sound_text.setImageResource(R.drawable.b_sound_on);
            iv_sound_test.setImageResource(R.drawable.b_sound_on);
        }
        else
        {
            iv_sound_text.setImageResource(R.drawable.b_sound_mute);
            iv_sound_test.setImageResource(R.drawable.b_sound_mute);

        }


        iv_sound_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SoundModel.isPlaying())
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                    SoundModel.stopMusic();


                    iv_sound_text.setImageResource(R.drawable.b_sound_mute);
                }
                else
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
//                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                    SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                    iv_sound_text.setImageResource(R.drawable.b_sound_on);
                }

            }
        });

        iv_sound_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SoundModel.isPlaying())
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                    SoundModel.stopMusic();


                    iv_sound_test.setImageResource(R.drawable.b_sound_mute);
                }
                else
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
//                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                    SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                    iv_sound_test.setImageResource(R.drawable.b_sound_on);

                }


            }
        });
    }

    private void setHomeButtonListener() {

        iv_home_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForExit(false,true);
            }
        });

        iv_home_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForExit(false,true);
            }
        });
    }

    private void setQuestion() {
        //remove remnant of last question
        for (int i = 0; i < 5; i++) {
            int resID = getResources().getIdentifier("hql_selected_" + (i + 1), "id", getPackageName());
            Button btn = ((Button) findViewById(resID));
            btn.setBackgroundResource(R.drawable.t_wood_a);
            btn.setVisibility(View.GONE);
            btn.setEnabled(true);
            btn.setText("...");
        }
        keyboardIsShowing = false;
        keyboard_move(false);
        remind_selected = null;


        //set question

        //if we have Picture question
        if (match.getQuestions().get(questionNumber).getImage() != null) {
            tv_question_description.setVisibility(View.GONE);
//            tv_question_title.setVisibility(View.VISIBLE);
            iv_question_image.setVisibility(View.VISIBLE);
            iv_question_image.setImageBitmap(match.getQuestions().get(questionNumber).getImage());
            if(match.getQuestions().get(questionNumber).getDescription().charAt(0) == '#')
            {
                match.getQuestions().get(questionNumber).setDescription(match.getQuestions().get(questionNumber).getDescription().substring(1));

            }
            tv_question_title.setText(Utility.enToFa(String.valueOf(questionNumber+1))+"-"+" "+match.getQuestions().get(questionNumber).getDescription());
            ltv_category.setText(CategoryModel.getCategoryTitleById(match.getQuestions().get(questionNumber).getCategoryId(),getApplicationContext()));
            layout_question_section.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zoom(true);
                }
            });

        } else {
            //if we have Text question
            tv_question_description.setVisibility(View.VISIBLE);
            iv_question_image.setVisibility(View.GONE);
//            tv_question_title.setVisibility(View.GONE);
            tv_question_title.setText("");
            ltv_category.setText(CategoryModel.getCategoryTitleById(match.getQuestions().get(questionNumber).getCategoryId(),getApplicationContext()));

            tv_question_description.setText(Utility.enToFa(String.valueOf(questionNumber+1))+"-"+" "+match.getQuestions().get(questionNumber).getDescription());

            layout_question_section.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zoom(false);
                }
            });

        }



        FrameLayout part_text_hlq = (FrameLayout) findViewById(R.id.part_text_hlq);
        FrameLayout part_test_hlq = (FrameLayout) findViewById(R.id.part_test_hlq);

        if (match.getQuestions().get(questionNumber).getAnswer().contains("#")) {
            //test question

            final TextView nextQuestion_test_hql = (TextView) findViewById(R.id.nextQuestion_test_hql);
            nextQuestion_test_hql.setText(Utility.enToFa("0"));

            final LinearLayout ll_next_question_test = (LinearLayout) findViewById(R.id.ll_next_question_test);
            ll_next_question_test.setVisibility(View.VISIBLE);
            ll_next_question_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    // meysam: go to next question
                    ll_next_question_test.setVisibility(View.INVISIBLE);
                    goToNextQuestion(false);
                }
            });


            part_text_hlq.setVisibility(View.GONE);
            part_test_hlq.setVisibility(View.VISIBLE);


            selected_answer_id = 0;

            final int hint_penalty = (int) match.getQuestions().get(questionNumber).getPenalty() / 3;
            TextView hint_test_hql = (TextView) findViewById(R.id.hint_test_hql);
            hint_test_hql.setText(Utility.enToFa("-" + hint_penalty));

            //hint
            LinearLayout removeOneSelected_hql = (LinearLayout) findViewById(R.id.removeOneSelected_hql);
            removeOneSelected_hql.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (remind_selected.size() > 0) {
                        if (Utility.hasEnoughCoin(UniversalMatchQuestionActivity.this, hint_penalty)) {
                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                                                        giveHint();
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

                        } else {
                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this,Utility.enToFa( "باید حداقل " + hint_penalty )+ " فندق داشته باشی! بجاش تبلیغ ببین", "باشه", null, false, false);
                        }

                    }
                }
            });

            //hint - hazel
            LinearLayout removeOneSelected_movie_hql = (LinearLayout) findViewById(R.id.removeOneSelected_movie_hql);
            removeOneSelected_movie_hql.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //meysam
                    //must add hint operation after movie is over

                    if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                    {
                        DM.show();
                        WatchAd();
                    }
                    else
                    {
                        DialogPopUpModel.show(UniversalMatchQuestionActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                    }

                }
            });


            String[] selected = match.getQuestions().get(questionNumber).getAnswer().trim().split("#");
            //shuffle selecteds
            List<String> strList = Arrays.asList(selected);
            Collections.shuffle(strList);
            selected = strList.toArray(new String[strList.size()]);

            remind_selected = new ArrayList<>();
            for (int i = 0; i < selected.length; i++) {
                selected[i] = selected[i].trim();
                if (selected[i].contains("*")) {
                    selected_answer_id = i;
                    selected[i] = selected[i].replace("*", "");
                } else {
                    remind_selected.add(i);
                }
                int resID = getResources().getIdentifier("hql_selected_" + (i + 1), "id", getPackageName());
                Button btn = ((Button) findViewById(resID));
                btn.setVisibility(View.VISIBLE);
                btn.setText(selected[i]);
                final int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_selected_choice(finalI);
                    }
                });
            }
            Collections.shuffle(remind_selected);

        } else {
            //text answer

            part_text_hlq.setVisibility(View.VISIBLE);
            part_test_hlq.setVisibility(View.GONE);
/////////////////////////////////////////////////////////////

            answer_et = (EditText) findViewById(R.id.answer_hlq);
            answer_et.setOnTouchListener(otl);
            answer_et.setText("");
            String[] temp = match.getQuestions().get(questionNumber).getAnswer().trim().split(" ");
            answer_et.setHint(Utility.enToFa("جواب " + match.getQuestions().get(questionNumber).getAnswer().replaceAll(" ", "").length() + " حرفی است و " + temp.length + " کلمه ای"));

            final int hint_penalty = (int) match.getQuestions().get(questionNumber).getPenalty() / 3;
            TextView hint_text_hql = (TextView) findViewById(R.id.hint_text_hql);
            hint_text_hql.setText(Utility.enToFa("-" + hint_penalty));

            final TextView nextQuestion_text_hql = (TextView) findViewById(R.id.nextQuestion_text_hql);
            nextQuestion_text_hql.setText(Utility.enToFa("0"));

            final LinearLayout ll_next_question_text = (LinearLayout) findViewById(R.id.ll_next_question_text);
            ll_next_question_text.setVisibility(View.VISIBLE);
            ll_next_question_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    DialogPopUpModel.show(MatchQuestionActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No));
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
//                                    Thread.currentThread().interrupt();//meysam 13960525
//                                    if(DialogPopUpModel.dialog_result==1){
//                                        //yes
//
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                //stuff that updates ui
//                                                // meysam: go to next question
//                                                ll_next_question_text.setVisibility(View.INVISIBLE);
//                                                goToNextQuestion(false);
//
//                                            }
//                                        });
//
//
//                                    }else{
//                                        //no
//                                        //do nothing
//                                    }
//                                }
//                            }
//                            catch (InterruptedException e)
//                            {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();


//                    // meysam: go to next question
                    ll_next_question_text.setVisibility(View.INVISIBLE);
                    goToNextQuestion(false);

                }
            });
            //hint
            LinearLayout hintShow_text_hql = (LinearLayout) findViewById(R.id.hintShow_text_hql);
            hintShow_text_hql.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utility.hasEnoughCoin(UniversalMatchQuestionActivity.this, hint_penalty)) {
                        DialogPopUpModel.show(UniversalMatchQuestionActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                                                    giveHint();
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
                    } else {
                        DialogPopUpModel.show(UniversalMatchQuestionActivity.this,Utility.enToFa( "باید حداقل " + hint_penalty) + " فندق داشته باشی! بجاش تبلیغ ببین", "باشه", null, false, false);
                    }

                }
            });
            LinearLayout hintShow_text_movie_hql = (LinearLayout) findViewById(R.id.hintShow_movie_text_hql);
            hintShow_text_movie_hql.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //meysam
                    //must add hint operation after movie is over

                    if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                    {
                        DM.show();
                        WatchAd();
                    }
                    else
                    {
                        DialogPopUpModel.show(UniversalMatchQuestionActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                    }
                }
            });


            Button hql_key_space = (Button) findViewById(R.id.hql_key_space);
            hql_key_space.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spaceOrdelete(true);
                }
            });
            Button hql_key_delete = (Button) findViewById(R.id.hql_key_delete);
            hql_key_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spaceOrdelete(false);
                }
            });

            int answer_length = match.getQuestions().get(questionNumber).getAnswer().trim().length();
            if (answer_length < 7) {
                LinearLayout hql_keyRow_1 = (LinearLayout) findViewById(R.id.hql_keyRow_1);
                hql_keyRow_1.setVisibility(View.VISIBLE);
                LinearLayout hql_keyRow_2 = (LinearLayout) findViewById(R.id.hql_keyRow_2);
                hql_keyRow_2.setVisibility(View.GONE);
                LinearLayout hql_keyRow_3 = (LinearLayout) findViewById(R.id.hql_keyRow_3);
                hql_keyRow_3.setVisibility(View.GONE);

                arrangeKeyboard(6);

            } else if (answer_length < 13) {
                LinearLayout hql_keyRow_1 = (LinearLayout) findViewById(R.id.hql_keyRow_1);
                hql_keyRow_1.setVisibility(View.VISIBLE);
                LinearLayout hql_keyRow_2 = (LinearLayout) findViewById(R.id.hql_keyRow_2);
                hql_keyRow_2.setVisibility(View.VISIBLE);
                LinearLayout hql_keyRow_3 = (LinearLayout) findViewById(R.id.hql_keyRow_3);
                hql_keyRow_3.setVisibility(View.GONE);

                arrangeKeyboard(12);

            } else {
                LinearLayout hql_keyRow_1 = (LinearLayout) findViewById(R.id.hql_keyRow_1);
                hql_keyRow_1.setVisibility(View.VISIBLE);
                LinearLayout hql_keyRow_2 = (LinearLayout) findViewById(R.id.hql_keyRow_2);
                hql_keyRow_2.setVisibility(View.VISIBLE);
                LinearLayout hql_keyRow_3 = (LinearLayout) findViewById(R.id.hql_keyRow_3);
                hql_keyRow_3.setVisibility(View.VISIBLE);

                arrangeKeyboard(18);
            }
        }
    }
    public void hideKeyboard(View v) {
        if (keyboardIsShowing)
            keyboard_move(false);
    }

    private void keyboard_move(boolean flag) {
        final FrameLayout keyboard = (FrameLayout) findViewById(R.id.keyboard_hlq);
        if (flag) {
            keyboardIsShowing = true;
            keyboard.setVisibility(View.VISIBLE);
            Anims.move(keyboard, 0.0f, 0.0f, height + 30.0f, 0.0f, 500, 0);
        } else {
            keyboardIsShowing = false;
            Anims.move(keyboard, 0.0f, 0.0f, 0.0f, height + 30.0f, 500, 0);
            keyboard.setVisibility(View.GONE);
        }
    }

    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (!keyboardIsShowing) {
                keyboard_move(true);
            }
            return true; // the listener has consumed the event
        }
    };
    private void zoom(boolean isImage) {
        final Dialog d = new Dialog(UniversalMatchQuestionActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.zoom_question);
        d.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        TextView zoom_text= (TextView) d.findViewById(R.id.zoom_text);
        ImageView zoom_img= (ImageView) d.findViewById(R.id.zoom_img);
        Button btn_zoom_close= (Button) d.findViewById(R.id.btn_zoom_close);

        if(isImage){
            zoom_text.setVisibility(View.GONE);
            zoom_img.setVisibility(View.VISIBLE);
            zoom_img.setImageBitmap(match.getQuestions().get(questionNumber).getImage());
        }else{
            zoom_img.setVisibility(View.GONE);
            zoom_text.setVisibility(View.VISIBLE);
            zoom_text.setText(match.getQuestions().get(questionNumber).getDescription());
        }

        btn_zoom_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
        d.show();
    }

    private void arrangeKeyboard(int count) {
        int answer_length = match.getQuestions().get(questionNumber).getAnswer().replaceAll(" ", "").length();
        String true_answer = match.getQuestions().get(questionNumber).getAnswer().replaceAll(" ", "").toLowerCase();
        ArrayList<String> temp_horoof = new ArrayList<>();
        int number_hooroof_answer=0;
        for (int i = 0; i < answer_length; i++) {
            String t = true_answer.substring(i, i + 1);
            if (!t.equals(" ") && !temp_horoof.contains(t)) {
                temp_horoof.add(t);
                number_hooroof_answer++;
            }
        }
        int c = 0;
        int diff = count - number_hooroof_answer;
        while (c < diff) {
            String s = isEnglish(true_answer.substring(0, 1).toUpperCase()) ? getRandomlyLetter() : getRandomlyHarf();
            if (!temp_horoof.contains(s)) {
                temp_horoof.add(s);
                c++;
            }
        }
        Collections.shuffle(temp_horoof);
        for (int i = 0; i < count; i++) {
            int resID = getResources().getIdentifier("hql_key_" + (i + 1), "id", getPackageName());
            Button btn = ((Button) findViewById(resID));
            btn.setText(temp_horoof.get(i));
            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user_keyboard_choice(finalI);
                }
            });
        }
    }

    private void user_keyboard_choice(int i) {
        int resID = getResources().getIdentifier("hql_key_" + (i + 1), "id", getPackageName());
        Button btn = ((Button) findViewById(resID));
        answer_et.setText(answer_et.getText().toString() + (btn.getText().toString().toLowerCase()));
        checkUserAnser(answer_et.getText().toString());
    }

    private void checkUserAnser(String user_answer) {
        if (user_answer.equals(match.getQuestions().get(questionNumber).getAnswer().toString().trim())) {
            //win
            sfxm.play(SoundFXModel.CORRECT, getApplicationContext());
            // Meysam: go to next question and set user answer...meysam
//            Utility.displayToast(getApplicationContext(),"win - des",Toast.LENGTH_SHORT);
            goToNextQuestion(true);

        }
        else
        {

            if(user_answer.length() == match.getQuestions().get(questionNumber).getAnswer().toString().length())
                sfxm.play(SoundFXModel.WRONG , getApplicationContext());

//            Utility.displayToast(getApplicationContext(),"wrong - des",Toast.LENGTH_SHORT);

        }
    }

    private void spaceOrdelete(boolean flag) {
        String temp = answer_et.getText().toString();
        int l = temp.length();
        if (l > 0) {
            if (flag) {
                //space
                answer_et.setText(temp + " ");
            } else {
                answer_et.setText(temp.substring(0, l - 1));
            }
        }
    }

    private String getRandomlyHarf() {
        int i = new Random().nextInt(32);
        return horoofs[i];
    }

    private String getRandomlyLetter() {
        int i = new Random().nextInt(26);
        return Letters[i];
    }

    private boolean isEnglish(String firstLetter) {
        if (Arrays.asList(Letters).contains(firstLetter))
            return true;
        return false;
    }

    private void user_selected_choice(int i) {
        int resID = getResources().getIdentifier("hql_selected_" + (i + 1), "id", getPackageName());
        Button btn = ((Button) findViewById(resID));
        if (i == selected_answer_id) {
            btn.setBackgroundResource(R.drawable.t_wood_d);

            sfxm.play(SoundFXModel.CORRECT, getApplicationContext());

            //win
            // Meysam: go to next question and set user answer...meysam
//            Utility.displayToast(getApplicationContext(),"win",Toast.LENGTH_SHORT);
            goToNextQuestion(true);

        } else {

            sfxm.play(SoundFXModel.WRONG, getApplicationContext());

            btn.setBackgroundResource(R.drawable.t_wood_c);
            btn.setEnabled(false);
            if(i >= 0)
                remind_selected.remove(remind_selected.indexOf(i));
//            new DialogModel_luck(MatchQuestionActivity.this).show(true, false, 2);

            //lose
            if (remind_selected.size() == 0) {
                // Meysam: go to next question and set user answer...meysam
//                Utility.displayToast(getApplicationContext(),"win",Toast.LENGTH_SHORT);
                goToNextQuestion(true);
            }
            else
            {
                // Meysam: go to next question and set user answer...meysam
//                Utility.displayToast(getApplicationContext(),"wrong",Toast.LENGTH_SHORT);
                if(!forHint)
                    goToNextQuestion(false);
                else
                    forHint = false;
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(minutes <= 0 && seconds <= 0)
        {
//            super.onBackPressed();
//            finish();
            match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
            match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds,UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());


            if(customHandler != null)
            {
                customHandler.removeCallbacks(requestQuestionTimerThread);
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

    private void dialogForExit(final boolean backRq, final boolean homeReq) {

        final Dialog d = new Dialog(UniversalMatchQuestionActivity.this);
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


                match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());
                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestQuestionTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }


                if(backRq)
                {
                    back_requested = true;

//                    mc.result(match,used_luck_amount,used_hazel_amount);//meysam - commented in 13961001
                    mc.universalResult(match,used_luck_amount,used_hazel_amount);//meysam - added in 13961001


                }
                if(homeReq)
                {
                    home_requested = true;
//                    uc.decrease(used_hazel_amount,used_luck_amount);

//                    mc.result(match,used_luck_amount,used_hazel_amount);//meysam - commented in 13961001
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

    private void goToNextQuestion(final Boolean isCorrect)
    {
        giveCorrectAnswer();
        DM.show();
        showAnswerDelayRunnable = new Runnable() {
            @Override
            public void run() {
                DM.hide();
                //Do Something
                match.getQuestions().get(questionNumber).setCorrect(isCorrect);

//        showRateReportDialog();

                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestQuestionTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }
                showAnswerDelayHandler.removeCallbacks(showAnswerDelayRunnable);

                customHandler = null;
                Intent inte = new Intent(UniversalMatchQuestionActivity.this,UniversalMatchQuestionActivity.class);
                questionNumber++;
                inte.putExtra("seconds",seconds);
                inte.putExtra("minutes",minutes);

                UniversalMatchQuestionActivity.this.finish();//meysam - 13960625
                UniversalMatchQuestionActivity.this.startActivity(inte);
            }
        };
        showAnswerDelayHandler = new Handler();
        showAnswerDelayHandler.postDelayed(showAnswerDelayRunnable,1000);
    }


    private void setReportLikeHateListeners() {
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!QuestionModel.isHatedOrLiked(match.getQuestions().get(questionNumber).getId(),getApplicationContext()))
                {
                    //meysam - send like to server
                    DM.show();
                    qc.rate(match.getQuestions().get(questionNumber).getId(),QuestionModel.LIKED);
                    session.addToList(SessionModel.KEY_QUESTION_HATE_LIKE_LIST,match.getQuestions().get(questionNumber).getId().toString());

                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getApplicationContext().getString(R.string.msg_AlredyRated), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                }

            }
        });
        iv_hate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!QuestionModel.isHatedOrLiked(match.getQuestions().get(questionNumber).getId(),getApplicationContext()))
                {
                    //meysam - send dislike to server
                    DM.show();
                    qc.rate(match.getQuestions().get(questionNumber).getId(),QuestionModel.HATED);
                    session.addToList(SessionModel.KEY_QUESTION_HATE_LIKE_LIST,match.getQuestions().get(questionNumber).getId().toString());

                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getApplicationContext().getString(R.string.msg_AlredyRated), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                }

            }
        });

        iv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // meyasm - show report dialog

                ReportDialogPopUpModel.show(UniversalMatchQuestionActivity.this);
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
                                    showLoading();
                                    qc.report(match.getQuestions().get(questionNumber).getId(),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description);
                                    queueTags.add(RequestRespondModel.TAG_REPORT_QUESTION);

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
        //////////////////////////////////////

    }
    private void timesUp()
    {
        final Dialog d = new Dialog(UniversalMatchQuestionActivity.this);
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
                match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());


                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestQuestionTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }

                mc.universalResult(match,used_luck_amount,used_hazel_amount);


                queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

                DM.show();

//                finish();
//                return;
            }
        });
        d.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                d.hide();
                match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
                match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestQuestionTimerThread);
                    customHandler.removeCallbacksAndMessages(null);
                }

                mc.universalResult(match,used_luck_amount,used_hazel_amount);


                queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);

                DM.show();

//                MatchQuestionActivity.this.finish();
            }
        });
        d.show();
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
                                            customHandler.removeCallbacks(requestQuestionTimerThread);
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
                                                customHandler.removeCallbacks(requestQuestionTimerThread);
                                                customHandler.removeCallbacksAndMessages(null);
                                            }
                                            customHandler = null;


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent i = new Intent(UniversalMatchQuestionActivity.this,HazelCategoriesActivity.class);
                                                    UniversalMatchQuestionActivity.this.startActivity(i);
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
                    else if(queueTags.contains(RequestRespondModel.TAG_REPORT_QUESTION))
                    {
                        //meysam - sending report failed
                        Utility.displayToast(getApplicationContext(), getString(R.string.msg_ReportNotSent), Toast.LENGTH_SHORT);
                        queueTags.remove(queueTags.indexOf(RequestRespondModel.TAG_REPORT_QUESTION));
                    }
                    else
                    {
                        Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                        if(customHandler != null)
                        {
                            customHandler.removeCallbacks(requestQuestionTimerThread);
                            customHandler.removeCallbacksAndMessages(null);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(UniversalMatchQuestionActivity.this,HazelCategoriesActivity.class);
                                UniversalMatchQuestionActivity.this.startActivity(i);
                                Utility.finishActivity(UniversalMatchQuestionActivity.this);
                            }
                        });

                    }
                }
            }
            else if(arg instanceof ArrayList) {
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT)) {

                    if (customHandler != null) {
                        customHandler.removeCallbacks(requestQuestionTimerThread);
                        customHandler.removeCallbacksAndMessages(null);
                    }
                    customHandler = null;

                    if (queueTags.contains(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT))
                        queueTags.remove(queueTags.indexOf(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT));

                    UniversalMatchModel uMatch = (UniversalMatchModel) ((ArrayList) arg).get(1);

                    Intent intent = new Intent(UniversalMatchQuestionActivity.this, UniversalMatchResultActivity.class);
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
                                                    new DialogModel_cup(UniversalMatchQuestionActivity.this).show(Utility.getCupTitleByCode(getApplicationContext(), changedCupsIndexes.get(finalI)), changedCupsIndexes.get(finalI));

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

                        UniversalMatchQuestionActivity.this.startActivity(intent);

                        //meysam - remove whatever is used hazels - we sent it to server
                        session.removeItem(SessionModel.KEY_QUESTION_USED_HAZEL);
                        session.removeItem(SessionModel.KEY_QUESTION_USED_LUCK);


//                    Utility.displayToast(getApplicationContext(),"نتیجه مسابقه آمد اینجا",Toast.LENGTH_SHORT);
//                    finish();
//                    DM.dismiss();

                        if (!WaitingToSendCups) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utility.finishActivity(UniversalMatchQuestionActivity.this);
                                }
                            });
                        }

                    }

                    if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER)) {
                        session.removeItem(SessionModel.KEY_QUESTION_USED_HAZEL);
                        session.removeItem(SessionModel.KEY_QUESTION_USED_LUCK);
                        if (back_requested) {
                            if (customHandler != null) {
                                customHandler.removeCallbacks(requestQuestionTimerThread);
                                customHandler.removeCallbacksAndMessages(null);
                            }

                            back_requested = false;
//                        DM.dismiss();
//                        Utility.finishActivity(this);
                        }
                        if (home_requested) {
                            if (customHandler != null) {
                                customHandler.removeCallbacks(requestQuestionTimerThread);
                                customHandler.removeCallbacksAndMessages(null);
                            }
                            MQA = null;
                            home_requested = false;
                            Intent i = new Intent(UniversalMatchQuestionActivity.this, MainActivity.class);
                            UniversalMatchQuestionActivity.this.startActivity(i);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utility.finishActivity(UniversalMatchQuestionActivity.this);
                                }
                            });
                        }

                    }
                    if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_RATE_QUESTION)) {
                        if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {

                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getApplicationContext().getString(R.string.msg_RateRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        } else {

                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getApplicationContext().getString(R.string.msg_RateNotRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }

                    }
                    if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_QUESTION)) {
                        if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {
                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        } else {
                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }

                    }
                    if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_ADVERTISMENT_HOME)) {
                        returned_from_ad = true;
                        giveHint();

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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utility.finishActivity(UniversalMatchQuestionActivity.this);
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
//                    DM.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utility.finishActivity(UniversalMatchQuestionActivity.this);

                        }
                    });
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

    private void giveHint()
    {
        forHint = true;
        if(!returned_from_ad)
        {
            used_hazel_amount+= match.getQuestions().get(questionNumber).getPenalty()/ 3;
            changeLuckAndReward(0,-1*match.getQuestions().get(questionNumber).getPenalty()/ 3);
        }


        if (match.getQuestions().get(questionNumber).getAnswer().contains("#"))
        {
            if(!returned_from_ad)
            {
//                user_selected_choice(remind_selected.get(0));
                new DialogModel_luck(UniversalMatchQuestionActivity.this).show(false, false,match.getQuestions().get(questionNumber).getPenalty()/ 3);
                Utility.changeCoins(UniversalMatchQuestionActivity.this, -1*(match.getQuestions().get(questionNumber).getPenalty()/ 3));

            }
            returned_from_ad = false;
            user_selected_choice(remind_selected.get(0));
        }
        else
        {
            String user_answer = answer_et.getText().toString();
            String true_answer = match.getQuestions().get(questionNumber).getAnswer().toString().trim();
            if (user_answer.equals(true_answer)) {
                return;
            }
            int k = 0;
            for (int i = 0; i < user_answer.length(); i++) {
                if (!user_answer.substring(i, i + 1).equals(true_answer.substring(i, i + 1))) {
                    break;
                }
                k++;
            }
            int j = 1;
            String next = true_answer.substring(k, k + 1);
            if (next.equals(" ")) {
                j++;
            }
            answer_et.setText((k != 0 ? user_answer.substring(0, k) : "") + true_answer.substring(k, k + j));

            if(!returned_from_ad)
            {
                new DialogModel_luck(UniversalMatchQuestionActivity.this).show(false, false, match.getQuestions().get(questionNumber).getPenalty()/ 3);
                Utility.changeCoins(UniversalMatchQuestionActivity.this,-1* match.getQuestions().get(questionNumber).getPenalty()/ 3);
            }
            returned_from_ad = false;
            checkUserAnser(answer_et.getText().toString());
        }
    }

    private void giveCorrectAnswer()
    {
        if (match.getQuestions().get(questionNumber).getAnswer().contains("#"))
        {
            chooseCorrectChoice();
        }
        else
        {
            answer_et = (EditText) findViewById(R.id.answer_hlq);
            String true_answer = match.getQuestions().get(questionNumber).getAnswer().toString().trim();
            answer_et.setText(true_answer);
        }

    }

    private void chooseCorrectChoice() {
        int resID = getResources().getIdentifier("hql_selected_" + (selected_answer_id+1), "id", getPackageName());
        Button btn = ((Button) findViewById(resID));
        btn.setBackgroundResource(R.drawable.t_wood_d);
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();
        //meysam - pause timer
//        customHandler.postDelayed(requestQuestionTimerThread,1);
        customHandler.removeCallbacks(requestQuestionTimerThread);
        customHandler.removeCallbacksAndMessages(null);

        /////////////////////////

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);
        Tapsell.requestAd(UniversalMatchQuestionActivity.this, AdvertismentModel.RandomMatchPageZoneId, options, new TapsellAdRequestListener() {



            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(UniversalMatchQuestionActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);


//                new SoundModel(UniversalMatchQuestionActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                if(IsAdForRewardQuestion)
                {
                    IsAdForRewardQuestion = false;
                    endMatch();
                }
                else
                {
                    //meysam - resume timer
                    customHandler.postDelayed(requestQuestionTimerThread, 1000);
                    /////////////////////////
                }

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

//                //meysam - pause timer
//                customHandler.removeCallbacks(requestQuestionTimerThread);
//                /////////////////////////

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) UniversalMatchQuestionActivity.this, showOptions);
//                //meysam - resume timer
//                customHandler.postDelayed(requestQuestionTimerThread, 1000);
//                /////////////////////////
                adShowing = true;

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(UniversalMatchQuestionActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

//                new SoundModel(UniversalMatchQuestionActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                if(IsAdForRewardQuestion)
                {
                    IsAdForRewardQuestion = false;
                    endMatch();
                }
                else
                {
                    //meysam - resume timer
                    customHandler.postDelayed(requestQuestionTimerThread, 1000);
                    /////////////////////////
                }
            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(UniversalMatchQuestionActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

//                new SoundModel(UniversalMatchQuestionActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                if(IsAdForRewardQuestion)
                {
                    IsAdForRewardQuestion = false;
                    endMatch();
                }
                else
                {
                    //meysam - resume timer
                    customHandler.postDelayed(requestQuestionTimerThread, 1000);
                    /////////////////////////
                }
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(UniversalMatchQuestionActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

//                new SoundModel(UniversalMatchQuestionActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

                if(IsAdForRewardQuestion)
                {
                    IsAdForRewardQuestion = false;
                    endMatch();
                }
                else
                {
                    //meysam - resume timer
                    customHandler.postDelayed(requestQuestionTimerThread, 1000);
                    /////////////////////////
                }

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

//                new SoundModel(UniversalMatchQuestionActivity.this).playCountinuseRandomMusic();
                SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);


                //meysam - resume timer
                customHandler.postDelayed(requestQuestionTimerThread, 1000);
                /////////////////////////
                adShowing = false;
                if(completed)
                {
                    if(!IsAdForRewardQuestion)
                    {
                        AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                        if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()).toString()) +" "+ getString(R.string.msg_AdCountRemained),getString(R.string.btn_OK),null, false, false);
                        else
                            DialogPopUpModel.show(UniversalMatchQuestionActivity.this, getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null, false, false);

                        // store user reward if ad.isRewardedAd() and completed is true
                        // Meysam: send Reward To server and store it in session .... meysam
//                        Utility.displayToast(MatchQuestionActivity.this,getApplicationContext().getString(R.string.msg_AdvertismentShowCompleted),Toast.LENGTH_SHORT);

                        hc.ad();

                    }
                    else
                    {
                        showRewardQuestion();
                    }

                }
                else
                {
                    Utility.displayToast(UniversalMatchQuestionActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                    if(IsAdForRewardQuestion)
                    {
                        IsAdForRewardQuestion = false;
                        endMatch();
                    }

                }


            }
        });
    }

    private void showLoading()
    {
        Intent intent = new Intent("universal_match_question_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(UniversalMatchQuestionActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughHazelMessage()
    {
        Intent intent = new Intent("universal_match_question_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_hazel", "true");
        LocalBroadcastManager.getInstance(UniversalMatchQuestionActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughLuckMessage()
    {
        Intent intent = new Intent("universal_match_question_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_luck", "true");
        LocalBroadcastManager.getInstance(UniversalMatchQuestionActivity.this).sendBroadcast(intent);
    }

    private void showRewardQuestion()
    {
        // meysam - show reward question
        IsAdForRewardQuestion = false;

        Intent inte = new Intent(UniversalMatchQuestionActivity.this,UniversalMatchQuestionActivity.class);
        questionNumber++;
        ShowRewardQuestion = true;
        inte.putExtra("seconds",seconds);
        inte.putExtra("minutes",minutes);
        finish();//meysam - uncommented in 13960628
        UniversalMatchQuestionActivity.this.startActivity(inte);
    }

    private void goToProfile()
    {
        //meysam - if visitor then no profile...
        if(match.getOpponent().getUserName() != null && !match.getOpponent().getUserName().contains("Visitor_")) {
            DM.show();
            //////////////////////////////////////////////////////////////////
//            Intent intent = new Intent(UniversalMatchQuestionActivity.this, UserProfileActivity.class);
//            intent.putExtra("EXTRA_User_Name", match.getOpponent().getUserName());
//            intent.putExtra("EXTRA_Avatar", match.getOpponent().getProfilePicture());

            if(session.getCurrentUser().getAllowChat().equals("1"))
            {
                if(match.getOpponent().getAllowChat().equals("1"))
                {
                    //meysam - chat is allowed
                    Intent intent = new Intent(UniversalMatchQuestionActivity.this, ChatActivity.class);
                    intent.putExtra("opponent_user_name", match.getOpponent().getUserName());
                    intent.putExtra("opponent_avatar", match.getOpponent().getProfilePicture());
                    UniversalMatchQuestionActivity.this.startActivity(intent);
                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchQuestionActivity.this,"حریف چت رو غیر فعال کرده",getString(R.string.btn_OK),null,false,true);
                }

            }
            else
            {
                DialogPopUpModel.show(UniversalMatchQuestionActivity.this,"چت در تنظیمات غیر فعال شده",getString(R.string.btn_OK),null,false,true);

            }



            DM.hide();
        }else{
            Utility.displayToast(UniversalMatchQuestionActivity.this,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
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
            if(intent.getStringExtra("not_enough_hazel") != null)
            {
                if(intent.getStringExtra("not_enough_hazel").equals("true")) {

                    Utility.displayToast(UniversalMatchQuestionActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);
                    endMatch();

                }
            }
            if(intent.getStringExtra("not_enough_luck") != null)
            {
                if(intent.getStringExtra("not_enough_luck").equals("true")) {

                    Utility.displayToast(UniversalMatchQuestionActivity.this,getString(R.string.msg_NotEnoughLuck),Toast.LENGTH_SHORT);
                    endMatch();

                }
            }



        }
    };

    public void onDestroy() {

        sfxm.releaseSoundPool();
        sfxm = null;
        if(customHandler != null)
        {
            customHandler.removeCallbacks(requestQuestionTimerThread);
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


        if(showAnswerDelayHandler != null)
            showAnswerDelayHandler.removeCallbacks(showAnswerDelayRunnable);
        showAnswerDelayHandler = null;
        showAnswerDelayRunnable = null;



        Tapsell.setRewardListener(null);
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);
        inCreate = false;

        if(anm_hint_ad != null)
        {
            anm_hint_ad.end();
            anm_hint_ad.cancel();
            anm_hint_ad.removeAllListeners();
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


        flubberAdListener = null;

        super.onDestroy();

    }

    private void endMatch()
    {

        if(customHandler != null)
        {
            customHandler.removeCallbacks(requestQuestionTimerThread);
            customHandler.removeCallbacksAndMessages(null);

        }


        DM.show();
        match.setSelfCorrectCount(UniversalMatchModel.countCorrectAnswers(match.getQuestions()).toString());
        match.setSelfSpentTime(UniversalMatchModel.calculateSpentTime(minutes, seconds, UniversalMatchModel.getCurrentMatchTime(getApplicationContext())).toString());

        mc.universalResult(match,used_luck_amount,used_hazel_amount);

        queueTags.add(RequestRespondModel.TAG_UNIVERSAL_MATCH_RESULT);
    }

    private void changeLuckAndReward(Integer luck, Integer hazel)
    {
        int current_amount = Integer.valueOf(tv_total_luck_amount.getText().toString());
        int new_amount = current_amount + luck;
        tv_total_luck_amount.setText( Utility.enToFa(String.valueOf(new_amount)));
        //for hazel
        current_amount = Integer.valueOf(tv_total_hazel_amount.getText().toString());
        new_amount = current_amount + hazel;
        tv_total_hazel_amount.setText(Utility.enToFa( String.valueOf(new_amount)));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onPause() {
//        if(customHandler != null)
//        {
//            customHandler.removeCallbacks(requestQuestionTimerThread);
//            customHandler.removeCallbacksAndMessages(null);
//        }
        SoundModel.stopSpecificSound();
        super.onPause();

    }


    public void onResume() {


        sfxm = new SoundFXModel(getApplicationContext());

        if(!inCreate)
        {
            if(mc == null)
            {
                mc = new MatchController(getApplicationContext());
                mc.addObserver(this);
            }
            if(qc == null)
            {
                qc = new QuestionController(getApplicationContext());
                qc.addObserver(this);
            }


            if(adShowing)
            {
                adShowing = false;
                //meysam - somthing went wrong in showing ad in standard senario
                if(IsAdForRewardQuestion)
                {
                    IsAdForRewardQuestion = false;
                    endMatch();
                }
            }

//            if(customHandler != null)
//                customHandler.postDelayed(requestQuestionTimerThread,1000);
        }
        else
            inCreate = false;

        ////////////////////////////////
        ImageView iv_adHint = findViewById(R.id.iv_adHint);
        Random rand = new Random();
        int min = 3;
        int max = 15;
        int delay = rand.nextInt((max - min) + 1) + min;
        anm_hint_ad = Flubber.with().listener(flubberAdListener)
                .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1500)  // Last for 1000 milliseconds(1 second)
                .delay(delay*1000)
                    .force((float) 0.7)
                .createFor(iv_adHint);
        anm_hint_ad.start();// Apply it to the view
        ////////////////////////////////////////////

        super.onResume();
        SoundModel.stopSpecificSound();
        SoundModel.playSpecificSound(R.raw.soft_background,getApplicationContext(),true,null,null, 0.4f);

    }

    public Animator.AnimatorListener flubberAdListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ImageView iv_adHint = findViewById(R.id.iv_adHint);
            Random rand = new Random();
            int min = 5;
            int max = 20;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_hint_ad.removeAllListeners();
            anm_hint_ad = Flubber.with().listener(flubberAdListener)
                    .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
                    .force((float) 0.7)
                    .createFor(iv_adHint);
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
