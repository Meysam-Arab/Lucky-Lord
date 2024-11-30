package ir.fardan7eghlim.luckylord.views.hazels;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
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

import com.appolica.flubber.AnimationBody;
import com.appolica.flubber.Flubber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.HomeController;
import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.help.DialogModel_help;
import ir.fardan7eghlim.luckylord.help.DialogModel_help_hand;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
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
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.home.StoreActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;


public class HazelLevelQuestionActivity extends BaseActivity implements Observer {

    private int width;
    private int height;
    private TextView questionTitle_hlq;
    private QuestionModel question;
    private QuestionController qc;
    private int selected_answer_id = 0;
    private EditText answer_et;
    private boolean keyboardIsShowing = false;
    private ArrayList<Integer> remind_selected;
    private int reward_hazel_amount;
    private int reward_luck_amount;
    private DatabaseHandler db;
    private boolean isQuestionRequested;

    private DialogModel_level dml;
    private DialogModel_cup dmc;


    private Integer categoryId;

    private int final_hazel_amount;
    private int final_luck_amount;

    private int used_hazel_amount;
    private int used_luck_amount;

    private Integer finalIs_Correct;

//    private SessionModel session;

    private boolean back_requested = false;
    private boolean home_requested = false;
    private boolean market_requested = false;

    UserController uc;

//    private Integer category_id;
    private DialogModel DM;
    private DialogModel_help_hand DMHH;
    private LinearLayout layout_question_section;
    private String horoofs[] = {"آ", "ب", "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی"};
    private String Letters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    int hint_penalty;

    private static boolean returned_from_ad = false;
    private boolean adForNextQuestion;

    private Context context;
    private SoundFXModel sfxm = null;

    TextView reward_luck_hql ;
    TextView reward_hazel_hql;

    ImageView iv_home_text;
    ImageView iv_home_test;

    ImageView iv_sound_text;
    ImageView iv_sound_test;

    ImageView iv_market_text;
    ImageView iv_market_test;

    Animator anm_hint_ad;
    Animator anm_market_ad;
    Animator anm_hazel;
    Animator anm_luck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazel_level_question);

        adForNextQuestion = false;
        isQuestionRequested = false;

        used_hazel_amount = 0;
        used_luck_amount = 0;

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        qc = new QuestionController(getApplicationContext());
        qc.addObserver(this);

        db = new DatabaseHandler(this);

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("hazel_level_question_activity_broadcast"));

//        session = new SessionModel(getApplicationContext());

        iv_home_test = (ImageView) findViewById(R.id.lb_home_test_hlq);
        iv_home_text = (ImageView) findViewById(R.id.lb_home_text_hlq);

        iv_sound_test = (ImageView) findViewById(R.id.lb_sound_test_hlq);
        iv_sound_text = (ImageView) findViewById(R.id.lb_sound_text_hlq);

        iv_market_test = (ImageView) findViewById(R.id.lb_store_test_hlq);
        iv_market_text = (ImageView) findViewById(R.id.lb_store_text_hlq);

        setAllButtonsListeners();



        reward_luck_hql = (TextView) findViewById(R.id.reward_luck_hql);
        reward_hazel_hql = (TextView) findViewById(R.id.reward_hazel_hql);


        reward_luck_hql.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
        if(session.getCurrentUser().getLuck() < 0)
            reward_luck_hql.setTextColor(Color.RED);
        else
            reward_luck_hql.setTextColor(this.getResources().getColor (R.color.creamDark));
        reward_hazel_hql.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));

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

        context = this;
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("Category_Id", 0);

        questionTitle_hlq = (TextView) findViewById(R.id.questionTitle_hlq);

        DM = new DialogModel(HazelLevelQuestionActivity.this);

        if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL)!=0 ||
                session.getIntegerItem(SessionModel.KEY_FINAL_LUCK)!=0)
            uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));

        //for Tapsell ...
        Tapsell.initialize(context, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        sfxm = new SoundFXModel(getApplicationContext());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        layout_question_section = (LinearLayout) findViewById(R.id.layout_question_section);

        //owl
        ImageView owl = (ImageView) findViewById(R.id.c_owl_hlq);
        owl.setImageResource(R.drawable.c_owl_anim_a);
        AnimationDrawable anim = (AnimationDrawable) owl.getDrawable();
        anim.start();

        //keyboard
        final FrameLayout keyboard = (FrameLayout) findViewById(R.id.keyboard_hlq);
        //answer
        EditText answer = (EditText) findViewById(R.id.answer_hlq);
        answer.setOnTouchListener(otl);

        //fetch question from server
//        QuestionController uc = new QuestionController(getApplicationContext());
        qc.addObserver(this);

        final QuestionModel question = db.getQuestionByTag(categoryId.toString());
        if (question.getId() == null) {
            DM.show();
            isQuestionRequested = true;
            qc.next(0, 0, 0, categoryId, question);
        } else {
            this.question = question;
            setQuestion();
        }

//        throw new RuntimeException();

        if (!session.getBoolianItem(SessionModel.KEY_QUESTION_IS_FIRST_TIME,false))
        {
            //help hand
            final TextView question_text = (TextView) findViewById(R.id.questionText_hlq);
            question_text.post(new Runnable() {
                @Override
                public void run() {
                    if(true){
                        DMHH = new DialogModel_help_hand(HazelLevelQuestionActivity.this);
                        int centreX = (int) (question_text.getX() + question_text.getWidth() / 2);
                        int centreY= (int) (question_text.getY() + question_text.getHeight() / 2);
                        DMHH.show(true,"برای بزرگ نمایی برروی صورت سوال کلیک کنید",centreX,centreY,8000);
                    }
                }
            });

            session.saveItem(SessionModel.KEY_QUESTION_IS_FIRST_TIME, true);

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

    public void hideKeyboard(View v) {
        if (keyboardIsShowing)
            keyboard_move(false);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
        DM.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    if(isQuestionRequested)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isQuestionRequested = false;
                                DialogPopUpModel.show(context,"خطا در دریافت سوال!!!","دوباره","خروج",true, false);

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
                                                    showLoading();
                                                    isQuestionRequested = true;
                                                    qc.next(0, 0, 0, categoryId, question);

                                                }else{
                                                    //exit

                                                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                                                    HazelLevelQuestionActivity.this.startActivity(i);
                                                    Utility.finishActivity(HazelLevelQuestionActivity.this);

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
                        });
                    }

                }
            } else if (arg instanceof ArrayList) {

                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_ADVERTISMENT_HOME))
                {
                    returned_from_ad = true;

                    if( adForNextQuestion )
                    {
                        //meysam - go to next question
                        adForNextQuestion = false;
                        giveCorrectAnswer();
                        endGameWindow_show(null, reward_hazel_amount + "", reward_luck_amount + "");

                    }
                    else
                    {
                        giveHint();
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
                        Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                        HazelLevelQuestionActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                    if(market_requested)
                    {
                        market_requested = false;
                        Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                        HazelLevelQuestionActivity.this.startActivity(i);
//                        Utility.finishActivity(this);
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
                        Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                        HazelLevelQuestionActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                    if(market_requested)
                    {
                        market_requested = false;
                        Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                        HazelLevelQuestionActivity.this.startActivity(i);
                    }
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_RATE_QUESTION))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {

                        DialogPopUpModel.show(HazelLevelQuestionActivity.this, getApplicationContext().getString(R.string.msg_RateRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        session.addToList(SessionModel.KEY_QUESTION_HATE_LIKE_LIST,question.getId().toString());
                    }
                    else
                    {

                        DialogPopUpModel.show(HazelLevelQuestionActivity.this, getApplicationContext().getString(R.string.msg_RateNotRegistered), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_QUESTION))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this, getApplicationContext().getString(R.string.msg_ReportSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this, getApplicationContext().getString(R.string.msg_ReportNotSent), getApplicationContext().getString(R.string.btn_OK), null, false, false);

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
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CUPS_USER))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        //meysam - reset KEY_CUPS_FOR_SERVER_UPDATE key in session if successful...
                        //meysam - update user cups in session....

                        session.saveItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE,"");
                    }
                    else
                    {
                        //meysam - error in updating level in server ... do nothing for now...
                    }

                }

            } else if (arg instanceof QuestionModel) {
                /////////////////

                isQuestionRequested = false;

                session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                session.removeItem(SessionModel.KEY_FINAL_LUCK);

                resetAll();
                question = (QuestionModel) arg;
//                session.setQuestion(question);
                db.addQuestion(question,categoryId.toString());
//                category_id = Integer.valueOf(question.getCategoryId().toString());
                setQuestion();

            } else if (arg instanceof Integer) {
                if (Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    Utility.finishActivity(this);
                } else {
                    Utility.displayToast(getApplicationContext(), new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                    if(back_requested)
                        finish();
                    if(home_requested)
                    {
                        home_requested = false;
                        Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                        HazelLevelQuestionActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                    if(market_requested)
                    {
                        market_requested = false;
                        Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                        HazelLevelQuestionActivity.this.startActivity(i);
                        Utility.finishActivity(this);
                    }
                    DialogPopUpModel.show(context,"خطا در دریافت سوال!!!","دوباره","خروج", true, false);
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

                                        showLoading();
                                        isQuestionRequested = true;
                                        qc.next(0, 0, 0, categoryId, question);
                                    }else{
                                        //exit
//                                        finish();

                                        Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                                        HazelLevelQuestionActivity.this.startActivity(i);
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
                if(back_requested)
                    finish();
                if(home_requested)
                {
                    home_requested = false;
                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
                if(market_requested)
                {
                    market_requested = false;
                    Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
                DialogPopUpModel.show(context,"خطا در دریافت سوال!!!","دوباره","خروج",true, false);
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

                                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                                    HazelLevelQuestionActivity.this.startActivity(i);
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
            if(back_requested)
                finish();
            if(home_requested)
            {
                home_requested = false;
                Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                HazelLevelQuestionActivity.this.startActivity(i);
                Utility.finishActivity(this);
            }
            if(market_requested)
            {
                market_requested = false;
                Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                HazelLevelQuestionActivity.this.startActivity(i);
                Utility.finishActivity(this);
            }
            DialogPopUpModel.show(context,"خطا در دریافت سوال!!!","دوباره","خروج", true, false);
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

                                Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                                HazelLevelQuestionActivity.this.startActivity(i);
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

    private void setQuestion() {

        reward_luck_hql.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
        if(session.getCurrentUser().getLuck() < 0)
            reward_luck_hql.setTextColor(Color.RED);
        else
            reward_luck_hql.setTextColor(this.getResources().getColor (R.color.creamDark));
        reward_hazel_hql.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));

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


        if (db.getQuestionByTag(categoryId.toString()).getId() == null) {

            reward_luck_amount = new Random().nextInt(question.getMaxLuckReward() - question.getMinLuckReward()) + question.getMinLuckReward();
            reward_hazel_amount = new Random().nextInt(question.getMaxHazelReward() - question.getMinHazelReward()) + question.getMinHazelReward();
        } else {
            reward_luck_amount = db.getQuestionByTag(categoryId.toString()).getFinalLuckReward();
            reward_hazel_amount = db.getQuestionByTag(categoryId.toString()).getFinalHazelReward();
        }


//        reward_luck_hql.setText("+" + reward_luck_amount);
//        reward_hazel_hql.setText("+" + reward_hazel_amount);

        TextView question_text = (TextView) findViewById(R.id.questionText_hlq);
        ImageView question_pic = (ImageView) findViewById(R.id.questionPic_hlq);

        //set question
//      if(question.getDescription()==null || (question.getDescription()!=null && question.getDescription().equals("null"))){
        if (question.getImage() != null) {//if we have Picture question
            question_text.setVisibility(View.GONE);
            question_pic.setVisibility(View.VISIBLE);
            question_pic.setImageBitmap(question.getImage());

            layout_question_section.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zoom(true);
                }
            });
        } else {
            //if we have Text question
            question_text.setVisibility(View.VISIBLE);
            question_pic.setVisibility(View.GONE);
            question_text.setText(" "+question.getDescription());

            layout_question_section.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zoom(false);
                }
            });
        }


        FrameLayout part_text_hlq = (FrameLayout) findViewById(R.id.part_text_hlq);
        FrameLayout part_test_hlq = (FrameLayout) findViewById(R.id.part_test_hlq);

//        Utility.displayToast(getApplicationContext(),question.getAnswer(),Toast.LENGTH_SHORT);
//        Utility.displayToast(getApplicationContext(),question.getId().toString(),Toast.LENGTH_SHORT);

        if (question.getAnswer().contains("#")) {
            //test question
            part_text_hlq.setVisibility(View.GONE);
            part_test_hlq.setVisibility(View.VISIBLE);

            if (question.getImage() != null) {
                questionTitle_hlq.setText(question.getDescription());

            } else {
//                questionTitle_hlq.setText("سوال تستی");
                questionTitle_hlq.setText(" سوال "+ CategoryModel.getCategoryTitleById(question.getCategoryId(),getApplicationContext()));

            }

            selected_answer_id = 0;

            hint_penalty = (int) question.getPenalty() / 3;
            TextView hint_test_hql = (TextView) findViewById(R.id.hint_test_hql);
            hint_test_hql.setText(Utility.enToFa("-" + hint_penalty));

            LinearLayout ll_next_question_test = (LinearLayout) findViewById(R.id.ll_next_question_test);
            ll_next_question_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utility.hasEnoughCoin(HazelLevelQuestionActivity.this, question.getPenalty())) {

                        DialogPopUpFourVerticalModel.show(HazelLevelQuestionActivity.this,null,Utility.enToFa(question.getPenalty().toString())+" فندق میدم ","یه فیلم تبلیغاتی می بینم",getString(R.string.btn_No),null, true, false);
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
                                        Thread.currentThread().interrupt();//meysam 13960525
                                        if(DialogPopUpFourVerticalModel.dialog_result==1){
                                            //yes
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    //stuff that updates ui
                                                    giveCorrectAnswer();

                                                    final_hazel_amount -= question.getPenalty();
                                                    used_hazel_amount += question.getPenalty();

                                                    ////meysam - 13960717
                                                    session.changeFinalHazel(-1*question.getPenalty());
                                                    ////////////////////////////



                                                    Utility.changeCoins(getApplicationContext(),-1* question.getPenalty());
                                                    new DialogModel_hazel(HazelLevelQuestionActivity.this).show(true, false, question.getPenalty());//subtract hazel animation...meysam
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //Do something after 1 second
                                                            endGameWindow_show(null, reward_hazel_amount + "", reward_luck_amount + "");//  ch:meysam

                                                        }
                                                    }, 1000);

                                                    reward_hazel_hql.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
                                                    reward_luck_hql.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

                                                }
                                            });


                                        }else if(DialogPopUpFourVerticalModel.dialog_result==2)
                                        {
                                            adForNextQuestion = true;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DM.show();
                                                    WatchAd();
                                                }
                                            });
                                        }
                                        else{
                                            //no
                                            //do nothing

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
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this, Utility.enToFa("باید حداقل " + question.getPenalty() + " فندق داشته باشی!"), "باشه", null, false, false);

                    }

                }
            });

//            TextView nextQuestion_test_hql = (TextView) findViewById(R.id.nextQuestion_test_hql);
//            nextQuestion_test_hql.setText(Utility.enToFa("-" + question.getPenalty()));


            //hint - hazel
            LinearLayout removeOneSelected_hql = (LinearLayout) findViewById(R.id.removeOneSelected_hql);
            removeOneSelected_hql.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (remind_selected.size() > 0) {

                        if (Utility.hasEnoughCoin(HazelLevelQuestionActivity.this, hint_penalty)) {
                            DialogPopUpModel.show(HazelLevelQuestionActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                                    DialogPopUpModel.show(HazelLevelQuestionActivity.this, Utility.enToFa("باید حداقل " + hint_penalty + " فندق داشته باشی! بجاش تبلیغ ببین"), "باشه", null, false, false);
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
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
                    }
                }
            });
            ImageView ivMovie = (ImageView) findViewById(R.id.iv_movie);
           anm_hint_ad = Flubber.with().listener(flubberAdListener)
                    .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)  // Last for 1000 milliseconds(1 second)
                    .delay(5000)
                    .force((float) 0.7)
                    .createFor( ivMovie);
           anm_hint_ad.start();// Apply it to the view
            //////////////////////////////////////////


            String[] selected = question.getAnswer().trim().split("#");
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
            //text question
            part_test_hlq.setVisibility(View.GONE);
            part_text_hlq.setVisibility(View.VISIBLE);
            if (question.getImage() != null) {
                questionTitle_hlq.setText(question.getDescription());

            } else {
//                questionTitle_hlq.setText("سوال حدس کلمه");
                questionTitle_hlq.setText(" سوال "+ CategoryModel.getCategoryTitleById(question.getCategoryId(),getApplicationContext()));

            }

            answer_et = (EditText) findViewById(R.id.answer_hlq);
            answer_et.setText("");
            String[] temp = question.getAnswer().trim().split(" ");
            answer_et.setHint("جواب " + question.getAnswer().replaceAll(" ", "").length() + " حرفی است و " + temp.length + " کلمه ای");

            hint_penalty  = (int) question.getPenalty() / 3;
            TextView hint_text_hql = (TextView) findViewById(R.id.hint_text_hql);
            hint_text_hql.setText(Utility.enToFa("-" + hint_penalty));


            LinearLayout ll_next_question_text = (LinearLayout) findViewById(R.id.ll_next_question_text);
            ll_next_question_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Utility.hasEnoughCoin(HazelLevelQuestionActivity.this, question.getPenalty()))
                    {

                        DialogPopUpFourVerticalModel.show(HazelLevelQuestionActivity.this,null,Utility.enToFa(question.getPenalty().toString())+" فندق میدم ", "یه فیلم تبلیغاتی می بینم",getString(R.string.btn_No),null, true, false);
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
                                        Thread.currentThread().interrupt();//meysam 13960525
                                        if(DialogPopUpFourVerticalModel.dialog_result==1){
                                            //yes
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    //stuff that updates ui
                                                    giveCorrectAnswer();

                                                    final_hazel_amount -= question.getPenalty();
                                                    used_hazel_amount += question.getPenalty();

                                                    ////meysam - 13960717
                                                    session.changeFinalHazel(-1*question.getPenalty());
                                                    ////////////////////////////

                                                    Utility.changeCoins(getApplicationContext(),-1* question.getPenalty());
                                                    new DialogModel_hazel(HazelLevelQuestionActivity.this).show(true, false, question.getPenalty());//subtract hazel animation...meysam
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //Do something after 1 second
                                                            endGameWindow_show(null, reward_hazel_amount + "", reward_luck_amount + "");//  ch:meysam

                                                        }
                                                    }, 1000);


                                                }
                                            });

                                        }else if(DialogPopUpFourVerticalModel.dialog_result==2)
                                        {
                                            adForNextQuestion = true;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DM.show();
                                                    WatchAd();
                                                }
                                            });

                                        }
                                        else{
                                            //no
                                            //do nothing
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
                        // meysam - show not enouph hazel dialog
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this, Utility.enToFa("باید حداقل " + question.getPenalty() + " فندق داشته باشی!"), "باشه", null, false, false);

                    }


                }
            });

            //hint hazel reduce
            LinearLayout hintShow_text_hql = (LinearLayout) findViewById(R.id.hintShow_text_hql);
            hintShow_text_hql.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (Utility.hasEnoughCoin(HazelLevelQuestionActivity.this, hint_penalty)) {
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true , false);
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
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this,Utility.enToFa( "باید حداقل " + hint_penalty + " فندق داشته باشی! بجاش تبلیغ ببین"), "باشه", null, false, false);
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
                        DialogPopUpModel.show(HazelLevelQuestionActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, false);
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

            int answer_length = question.getAnswer().trim().length();
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
    private void giveCorrectAnswer()
    {
        if (question.getAnswer().contains("#"))
        {
            chooseCorrectChoice();
        }
        else
        {
            answer_et = (EditText) findViewById(R.id.answer_hlq);
            String true_answer = question.getAnswer().toString().trim();
            answer_et.setText(true_answer);
        }

    }

    private void chooseCorrectChoice() {
        int resID = getResources().getIdentifier("hql_selected_" + (selected_answer_id+1), "id", getPackageName());
        Button btn = ((Button) findViewById(resID));
        btn.setBackgroundResource(R.drawable.t_wood_d);
    }

    private void zoom(boolean isImage) {
        final Dialog d = new Dialog(HazelLevelQuestionActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setContentView(R.layout.zoom_question);
        d.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        TextView zoom_text= (TextView) d.findViewById(R.id.zoom_text);
        ImageView zoom_img= (ImageView) d.findViewById(R.id.zoom_img);
        Button btn_zoom_close= (Button) d.findViewById(R.id.btn_zoom_close);

        if(isImage){
            zoom_text.setVisibility(View.GONE);
            zoom_img.setVisibility(View.VISIBLE);
            zoom_img.setImageBitmap(question.getImage());
        }else{
            zoom_img.setVisibility(View.GONE);
            zoom_text.setVisibility(View.VISIBLE);
            zoom_text.setText(question.getDescription());
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
        int answer_length = question.getAnswer().replaceAll(" ", "").length();
        String true_answer = question.getAnswer().replaceAll(" ", "").toLowerCase();
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
        if (user_answer.equals(question.getAnswer().toString().trim())) {

            sfxm.play(SoundFXModel.CORRECT, getApplicationContext());

            //win
            final_hazel_amount+=reward_hazel_amount;
            final_luck_amount+=reward_luck_amount;

            ////meysam - 13960717
            session.changeFinalHazel(reward_hazel_amount);
            session.changeFinalLuck( reward_luck_amount);
            ////////////////////////////

            endGameWindow_show(true, reward_hazel_amount + "", reward_luck_amount + "");//  ch:meysam

            Utility.changeCoins(HazelLevelQuestionActivity.this, reward_hazel_amount);//  ch:meysam
            Utility.changeLucks(HazelLevelQuestionActivity.this, reward_luck_amount);//  ch:meysam

        }
        else
        {
            if(user_answer.length() == question.getAnswer().toString().length())
                sfxm.play(SoundFXModel.WRONG , getApplicationContext());
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

    private void user_selected_choice(int i) {
        int resID = getResources().getIdentifier("hql_selected_" + (i + 1), "id", getPackageName());
        Button btn = ((Button) findViewById(resID));
        if (i == selected_answer_id) {

            sfxm.play(SoundFXModel.CORRECT, getApplicationContext());


            btn.setBackgroundResource(R.drawable.t_wood_d);
            btn.setEnabled(false);

            Utility.changeCoins(HazelLevelQuestionActivity.this, reward_hazel_amount);
            Utility.changeLucks(HazelLevelQuestionActivity.this, reward_luck_amount);
            final_hazel_amount+=reward_hazel_amount;
            final_luck_amount+=reward_luck_amount;

            ////meysam - 13960717
            session.changeFinalHazel(reward_hazel_amount);
            session.changeFinalLuck( reward_luck_amount);
            ////////////////////////////


            //win
            endGameWindow_show(true, reward_hazel_amount + "", reward_luck_amount + "");// ch:meysam

        } else {
            //user choice is incorrect....meysam

            sfxm.play(SoundFXModel.WRONG, getApplicationContext());

            btn.setBackgroundResource(R.drawable.t_wood_c);
            btn.setEnabled(false);
            if(i >= 0)
            {
                remind_selected.remove(remind_selected.indexOf(i));
                new DialogModel_luck(HazelLevelQuestionActivity.this).show(true, false, 1);//subtract luck animation...meysam
            }

            changeLuckAndReward(-1,0);
            final_luck_amount-=1;
            used_luck_amount +=1;

            ////meysam - 13960717
            session.changeFinalLuck(-1);
            ////////////////////////////

            Utility.changeLucks(getApplicationContext(),-1);
            //lose
            if (remind_selected.size() == 0) {//meysam - commented in 13960909
//            reward_luck_amount = -1;//meysam - added in 13960909
                endGameWindow_show(false, reward_hazel_amount + "", reward_luck_amount + "");// ch:meysam

            }
        }
    }

    private void hint_selected_choice(int i) {
        int resID = getResources().getIdentifier("hql_selected_" + (i + 1), "id", getPackageName());
        Button btn = ((Button) findViewById(resID));

            //hint choice must is incorrect....meysam

            btn.setBackgroundResource(R.drawable.t_wood_c);
            btn.setEnabled(false);
            remind_selected.remove(remind_selected.indexOf(i));

            //win
            if (remind_selected.size() == 0) {

                Utility.changeCoins(HazelLevelQuestionActivity.this, reward_hazel_amount);
                Utility.changeLucks(HazelLevelQuestionActivity.this, reward_luck_amount);

                final_hazel_amount+=reward_hazel_amount;
                final_luck_amount+=reward_luck_amount;
                ////meysam - 13960717
                session.changeFinalHazel(reward_hazel_amount);
                session.changeFinalLuck( reward_luck_amount);
                ////////////////////////////

                endGameWindow_show(true, reward_hazel_amount + "", reward_luck_amount + "");// ch:meysam

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

    private void endGameWindow_show(Boolean win, final String hazel, final String luck) {

//        session.removeQuestion();// ch:meysam
        db.deleteQuestionsByTag(categoryId.toString());

        final Dialog d = new Dialog(HazelLevelQuestionActivity.this,R.style.PauseDialog);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setContentView(R.layout.end_game_window);
        d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(false);
        d.show();

        Integer is_Correct = 0;

        ImageView endWin_status = (ImageView) d.findViewById(R.id.endWin_status);

        ///meysam = for like and hate(dislike)
        LinearLayout iv_like = (LinearLayout) d.findViewById(R.id.iv_like);
        LinearLayout iv_hate = (LinearLayout) d.findViewById(R.id.iv_hate);
        LinearLayout iv_report = (LinearLayout) d.findViewById(R.id.iv_report);

        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!QuestionModel.isHatedOrLiked(question.getId(),getApplicationContext()))
                {
                    //meysam - send like to server
                    DM.show();
                    qc.rate(question.getId(),QuestionModel.LIKED);
                }
                else
                {
                    DialogPopUpModel.show(HazelLevelQuestionActivity.this, getApplicationContext().getString(R.string.msg_AlredyRated), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                }

            }
        });
        iv_hate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!QuestionModel.isHatedOrLiked(question.getId(),getApplicationContext()))
                {
                    //meysam - send dislike to server
                    DM.show();
                    qc.rate(question.getId(),QuestionModel.HATED);
                }
                else
                {
                    DialogPopUpModel.show(HazelLevelQuestionActivity.this, getApplicationContext().getString(R.string.msg_AlredyRated), getApplicationContext().getString(R.string.btn_OK), null, false, false);

                }

            }
        });

        iv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // meyasm - show report dialog

                ReportDialogPopUpModel.show(context);
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
                                    qc.report(question.getId(),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description);

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
        LuckyTextView endWin_luckAmount = (LuckyTextView) d.findViewById(R.id.endWin_luckAmount);
        LuckyTextView endWin_hazelAmount = (LuckyTextView) d.findViewById(R.id.endWin_hazelAmount);

        ImageView iv_luck = (ImageView) d.findViewById(R.id.iv_luck_end_window_hlq);
        endWin_luckAmount.setVisibility(View.VISIBLE);
        iv_luck.setVisibility(View.VISIBLE);

        if(win == null)
        {
            //meysam - user clicked go to next
            endWin_status.setImageResource(android.R.color.transparent);

//            if(reward_luck_amount < 0)//meysam - added in 13960909
//            {
//                endWin_luckAmount.setText(Utility.enToFa(String.valueOf(reward_luck_amount)));//meysam - added in 13960909
//            }
//            else//meysam - added in 13960909
//            {
//                endWin_luckAmount.setText(Utility.enToFa("0"));//meysam - added in 13960909
//            }
            endWin_luckAmount.setText(Utility.enToFa("0"));//meysam - commented in 13960909
            endWin_hazelAmount.setText(Utility.enToFa("0"));
        }
        else
        {
            if (win) {

                ///////////////////////////////////////////////////////////
                // meysam - add to scores for cups and level...
                //meysam - change level and cups in server if user earned it....

                ArrayList<Object> tmp = Utility.addCupsScore(getApplicationContext(),SessionModel.KEY_QUESTION_TRUE_COUNT);
                if((Boolean) tmp.get(0))
                {
                    //meysam - a cup has been earned...
                    // meysam - show earned cup animation...
//                    (String) tmp.get(1)//meysam - string for cup title from here
                    String cupsToAdd = session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE);
                    //meysam - no need to store session variable for show...
                    final ArrayList<Integer> changedCupsIndexes = Utility.getChangedCups(getApplicationContext(),cupsToAdd);
                    if(changedCupsIndexes.size() > 0)
                    {
                        // meysam - show earned cup animation...
                        for (int i = 0; i < changedCupsIndexes.size();i++)
                        {
                            final int finalI = i;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    dmc = new DialogModel_cup(HazelLevelQuestionActivity.this);
                                    dmc.show(Utility.getCupTitleByCode(getApplicationContext(),changedCupsIndexes.get(finalI)),changedCupsIndexes.get(finalI));

                                }
                            }, Utility.CUP_DIALOG_TIME);
                        }
                        //////////////////////////////////////////////
                    }
                    session.addNewCups(changedCupsIndexes);
                    //////////////////////////////////////
                    /////////////////////////////////////////////
                    //meysam - send new cup to server ...
                    uc.updateCups(session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE));
                    ///////////////////////////////////////
                }
                if(Utility.addLevelScore(getApplicationContext(), UserModel.REWARD_AMOUNT_LEVEL_SCORE_NORMAL_QUESTION))
                {
                    //meysam - an increase in level occured...
                    // meysam - show earned level animation...
                    //meysam - no need to store session variable for show...
                    dml = new DialogModel_level(HazelLevelQuestionActivity.this);
                    dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())),Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()))+1);
                    /////////////////////////////////////////////
                    //meysam - send new level to server ...
                    uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                    ///////////////////////////////////////

                }

                ////////////////////////////////////////////////////


                if(luck.equals("0"))
                {
                    endWin_luckAmount.setVisibility(View.INVISIBLE);
                    iv_luck.setVisibility(View.INVISIBLE);
                }

                endWin_status.setImageResource(R.drawable.t_text_win);
                is_Correct = 1;

                endWin_luckAmount.setText(Utility.enToFa(String.valueOf(luck)));
                endWin_hazelAmount.setText(Utility.enToFa(String.valueOf(hazel)));


            } else {
                endWin_status.setImageResource(R.drawable.t_text_lose);

//                if(reward_luck_amount < 0)//meysam - added in 13960909
//                {
//                    endWin_luckAmount.setText(Utility.enToFa(String.valueOf(reward_luck_amount)));//meysam - added in 13960909
//                }
//                else//meysam - added in 13960909
//                {
//                    endWin_luckAmount.setText(Utility.enToFa("0"));//meysam - added in 13960909
//                }
                endWin_luckAmount.setText(Utility.enToFa("0"));//meysam - commented in 13960909
                endWin_hazelAmount.setText(Utility.enToFa("0"));
            }
        }

//        reward_luck_amount = 0;//meysam - added in 13960909

        LinearLayout btn1 = (LinearLayout) d.findViewById(R.id.endWin_pause);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.hide();
                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {
                    DM.show();
                    home_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {

                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                    finish();
                }

            }
        });

        LinearLayout btn2 = (LinearLayout) d.findViewById(R.id.endWin_next);
        finalIs_Correct = is_Correct;
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(String.valueOf(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL)), String.valueOf(session.getIntegerItem(SessionModel.KEY_FINAL_LUCK)), finalIs_Correct);
                d.hide();
            }
        });
    }

    //next question
    private void next(String hazel, String luck, Integer is_correct) {

//        session.removeQuestion();
        db.deleteQuestionsByTag(categoryId.toString());
        //fetch question from server
//        QuestionController qc = new QuestionController(getApplicationContext());
        qc.addObserver(this);
        DM.show();
        isQuestionRequested = true;
        qc.next(new Integer(luck), new Integer(hazel), is_correct, categoryId, question);
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);
        Tapsell.requestAd(context, AdvertismentModel.QuestionPageZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(context,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(context).playCountinuseRandomMusic();

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {
                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) context, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(context,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(context).playCountinuseRandomMusic();
            }

            @Override
            public void onNoNetwork ()
            {
                DM.hide();
                Utility.displayToast(context,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(context).playCountinuseRandomMusic();
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(context,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(context).playCountinuseRandomMusic();
            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

                new SoundModel(context).playCountinuseRandomMusic();

                if(completed)
                {

                    AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);
                    if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(context, Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()) +" "+getString(R.string.msg_AdCountRemained)),getString(R.string.btn_OK),null, false, false);
                    else
                        DialogPopUpModel.show(context, getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null, false, false);

                    // store user reward if ad.isRewardedAd() and completed is true
                    // Meysam: send Reward To server and store it in session .... meysam
//                    Utility.displayToast(context,getApplicationContext().getString(R.string.msg_AdvertismentShowCompleted),Toast.LENGTH_SHORT);

                    HomeController hc = new HomeController(getApplicationContext());
                    hc.addObserver((Observer) context);
                    hc.ad();
                }
                else
                {
                    Utility.displayToast(context,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }


            }
        });
    }

    private void giveHint()
    {
        if (question.getAnswer().contains("#"))
        {

            if(!returned_from_ad)
            {
                new DialogModel_hazel(HazelLevelQuestionActivity.this).show(false, false, hint_penalty);
                Utility.changeCoins(HazelLevelQuestionActivity.this, -1*hint_penalty);
//                changeRewardLuckText( -1*hint_penalty);
                changeLuckAndReward(0,-1*hint_penalty);
                final_hazel_amount -= hint_penalty;
                used_hazel_amount += hint_penalty;

                ////meysam - 13960717
                session.changeFinalHazel(-1*hint_penalty);
                ////////////////////////////

            }
            returned_from_ad = false;
            hint_selected_choice(remind_selected.get(0));

        }
        else
        {
            answer_et = (EditText) findViewById(R.id.answer_hlq);


            String user_answer = answer_et.getText().toString();
            String true_answer = question.getAnswer().toString().trim();
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
                new DialogModel_luck(HazelLevelQuestionActivity.this).show(false, false, hint_penalty);
                Utility.changeCoins(HazelLevelQuestionActivity.this, -1*hint_penalty);
                final_hazel_amount -= hint_penalty;
                used_hazel_amount += hint_penalty;

                ////meysam - 13960717
                session.changeFinalHazel(-1*hint_penalty);
                ////////////////////////////
            }
            returned_from_ad = false;
            checkUserAnser(answer_et.getText().toString());
        }

        reward_hazel_hql.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
        reward_luck_hql.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

    }

    @Override
    public void onBackPressed() {

        if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
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

    private void resetAll()
    {
        final_luck_amount = 0;
        final_hazel_amount = 0;
        used_hazel_amount = 0;
        used_luck_amount = 0;
    }

    private void setAllButtonsListeners()
    {
        setHomeButtonListener();
        setMarketButtonListener();
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
                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();

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
                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                    iv_sound_test.setImageResource(R.drawable.b_sound_on);

                }


            }
        });
    }

    private void setMarketButtonListener() {

        iv_market_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {

                    DM.show();
                    market_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {
                    Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                }
            }
        });
        //animations
        anm_market_ad = Flubber.with().listener(flubberMarketTestListener)
                .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1500)  // Last for 1000 milliseconds(1 second)
                .delay(2000)
                .createFor(iv_market_test);// Apply it to the view
        anm_market_ad.start();

        iv_market_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {

                    DM.show();
                    market_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {
                    Intent i = new Intent(HazelLevelQuestionActivity.this, StoreActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                }
            }
        });
        //animations
        Flubber.with().listener(flubberMarketTextListener)
                .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1500)  // Last for 1000 milliseconds(1 second)
                .delay(2000)
                .createFor(iv_market_text)
                .start();// Apply it to the view
    }

    private void setHomeButtonListener() {

        iv_home_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {

                    DM.show();
                    home_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {
                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                    finish();
                }
            }
        });

        iv_home_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
                {

                    DM.show();
                    home_requested = true;
                    uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                }
                else
                {
                    Intent i = new Intent(HazelLevelQuestionActivity.this, MainActivity.class);
                    HazelLevelQuestionActivity.this.startActivity(i);
                    finish();
                }
            }
        });


    }
    private void changeLuckAndReward(Integer luck, Integer hazel)
    {
        int current_amount = Integer.valueOf(reward_luck_hql.getText().toString());
        int new_amount = current_amount + luck;
        reward_luck_hql.setText( Utility.enToFa(String.valueOf(new_amount)));
        if(new_amount < 0)
            reward_luck_hql.setTextColor(Color.RED);
        else
            reward_luck_hql.setTextColor(this.getResources().getColor (R.color.creamDark));
        //for hazel
        current_amount = Integer.valueOf(reward_hazel_hql.getText().toString());
        new_amount = current_amount + hazel;
        reward_hazel_hql.setText(Utility.enToFa( String.valueOf(new_amount)));
    }

    private void showLoading()
    {
        Intent intent = new Intent("hazel_level_question_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(HazelLevelQuestionActivity.this).sendBroadcast(intent);
    }

    public void onDestroy() {

        sfxm.releaseSoundPool();
        sfxm = null;

        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        Tapsell.setRewardListener(null);


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

        if(anm_market_ad != null)
        {
            anm_market_ad.end();
            anm_market_ad.cancel();
            anm_market_ad.removeAllListeners();
        }


        if(anm_hint_ad != null)
        {
            anm_hint_ad.end();
            anm_hint_ad.cancel();
            anm_hint_ad.removeAllListeners();
        }


        flubberAdListener = null;
        flubberMarketTestListener = null;
        flubberMarketTextListener = null;

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


        super.onDestroy();

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

        }
    };

    public void onResume() {

        qc = new QuestionController(getApplicationContext());
        qc.addObserver(this);

        sfxm = new SoundFXModel(getApplicationContext());

        super.onResume();
    }
    @Override
    public void onPause() {

        qc.setCntx(null);
        qc.deleteObserver(this);
        qc = null;
        super.onPause();
    }
    public Animator.AnimatorListener flubberAdListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ImageView ivMovie = (ImageView) findViewById(R.id.iv_movie);
            Random rand = new Random();
            int min = 10;
            int max = 25;
            int delay = rand.nextInt((max - min) + 1) + min;

            anm_hint_ad.removeAllListeners();

            anm_hint_ad = Flubber.with().listener(flubberAdListener)
                    .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
                    .force((float) 0.7)
                    .createFor(ivMovie);

            anm_hint_ad.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;

    public Animator.AnimatorListener flubberMarketTestListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Random rand = new Random();
            int min = 10;
            int max = 30;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_market_ad.removeAllListeners();
            anm_market_ad = Flubber.with().listener(flubberMarketTestListener)
                    .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
                    .createFor(iv_market_test);
            anm_market_ad.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;

    public Animator.AnimatorListener flubberMarketTextListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Random rand = new Random();
            int min = 10;
            int max = 30;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_market_ad.removeAllListeners();
            anm_market_ad =Flubber.with().listener(flubberMarketTextListener)
                    .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
                    .repeatCount(0)                              // Repeat once
            .duration(1500)  // Last for 1000 milliseconds(1 second)
             .delay(delay*1000)
            .createFor(iv_market_text);
            anm_market_ad.start();// Apply it to the view

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;
}
