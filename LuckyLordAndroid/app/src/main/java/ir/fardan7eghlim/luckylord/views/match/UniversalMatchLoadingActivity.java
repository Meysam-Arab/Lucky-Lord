package ir.fardan7eghlim.luckylord.views.match;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.LuckyLordRecyclerViewAdapter;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class UniversalMatchLoadingActivity extends BaseActivity implements Observer {
    private boolean isFoundOpponent=false;
    private boolean sendReq=false;
    private boolean isNitro;
    private boolean isRewardLuck;
    private Avatar avatar;
    private Integer requestCount;
    private int width;
    private int height;
    private Integer betAmount;
    private String betId;
    private String categoryId;
    private ImageView t_bg_d_left;
    private ImageView t_bg_d_right;
    private TextView stutus_matchLoading;
    private MatchController mc;
    private  UniversalMatchModel uMatch;
    private Integer addedTime;
    private Integer matchType;
    private Context cntx;
    private boolean inCreating;

    private LuckyTextView ltv_hint_and_opponent_name;

    // meysam: must be 9
    private int mathDurationMinutes = UniversalMatchModel.MATCH_DURATION_MINUTES;//must be 9...meysam


    private int mathDurationSeconds = UniversalMatchModel.MATCH_DURATION_SECONDS;//must be 59...meysam

    //meysam: must be 10
    private int timeInSecondsLimit = UniversalMatchModel.TIME_IN_SECONDS_LIMIT;//must be 10...meysam

    // meysam: must be 10
    private int timeWaitBeforeEnd = UniversalMatchModel.TIME_WAIT_BEFORE_END;//must be 10...meysam

    private Handler customHandler;
    private Handler randomAvatarHandler;
    Integer timeInSeconds = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_loading);

        inCreating = true;
        requestCount = 1;

        customHandler = new Handler();

        ltv_hint_and_opponent_name = (LuckyTextView) findViewById(R.id.ltv_hint_matchLoading);
        //meysam - read a line from guide file and set it here
        ltv_hint_and_opponent_name.setText(getString(R.string.title_hint)+" : "+Utility.getHintFromFile(getApplicationContext()));
        /////////////////////////////////////////////////////////////

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("universal_match_loading_activity_broadcast"));

        Intent i = this.getIntent();
        betAmount = Integer.valueOf(i.getStringExtra("betAmount"));
        betId = i.getStringExtra("betId");

        categoryId = i.getStringExtra("categoryId");

        isNitro = i.getBooleanExtra("isNitro",false);
        isRewardLuck = i.getBooleanExtra("isRewardLuck",false);
        addedTime = i.getIntExtra("rewardTime",0);
        matchType = i.getIntExtra("matchType",0);

        cntx = this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        t_bg_d_left= (ImageView) findViewById(R.id.t_bg_d_left);
        t_bg_d_right= (ImageView) findViewById(R.id.t_bg_d_right);
        stutus_matchLoading= (TextView) findViewById(R.id.stutus_matchLoading);

        t_bg_d_left.startAnimation(
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_come_bg_2) );

        t_bg_d_right.startAnimation(
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_come_bg_2) );

        avatar = new Avatar("m#1#1#1#1#1#0#0#0#0#0#0#0");
        makeRandomAvatar();

        mc = new MatchController(getApplicationContext());
        mc.addObserver(this);

        mc.universalRequest(requestCount.toString(),betId,categoryId,null,matchType, UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM);

        requestCount++;
        sendReq = false;

        customHandler.postDelayed(requestSendTimerThread, 1000);

    }
    //make random avatar
    private void makeRandomAvatar(){
        avatar.getRandomAvatar(null);
        avatar.setAvatar_opt(UniversalMatchLoadingActivity.this);
        int timeBetweenChecks = 500;
        if(randomAvatarHandler != null)
        {
            randomAvatarHandler.removeCallbacks(randomAvatarTimerThread);
            randomAvatarHandler = null;
        }
        randomAvatarHandler = new Handler();
        randomAvatarHandler.postDelayed(randomAvatarTimerThread, timeBetweenChecks);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {

        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
//                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
//                    if(timeInSeconds >= timeInSecondsLimit*requestCount)
//                    {
//                        sendReq = false;
//                    }
//                    else
//                    {
                    if(requestCount > 8)
                    {
                        customHandler.removeCallbacks(requestWaitTimerThread);
                        customHandler.removeCallbacks(requestSendTimerThread);

                        Utility.displayToast(UniversalMatchLoadingActivity.this,getString(R.string.error_connecting_to_server),Toast.LENGTH_SHORT);

                        /////////////////////meysam - 13960625
                        Intent i = new Intent(UniversalMatchLoadingActivity.this,HazelCategoriesActivity.class);
                        UniversalMatchLoadingActivity.this.startActivity(i);
                        //////////////////////////////////////////////////
                        finish();
                        return;
                    }

                    sendReq = true;
//                    }

                }
            }
            else if(arg instanceof ArrayList)
            {

                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST))
                {
                    if(!isFoundOpponent)
                    {
                        uMatch =  (UniversalMatchModel) ((ArrayList) arg).get(1);
                        if(uMatch.getOpponent() == null)
                        {
                            //no opponent found

                            //meysam - change for second revolution
                            if(sendReq && timeInSeconds >= timeInSecondsLimit*requestCount)
                            {
                                sendReq = false;

                                mc.universalRequest(requestCount.toString(),betId,categoryId,null,matchType, UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM);//meysam - added in 13961001

                                requestCount++;
                            }
                            else
                            {
                                sendReq = true;
                            }

                            /////////////////////////////////////////
                        }
                        else
                        {
                            //an opponent was found
                            //start first question activity and pass match object to it
                            isFoundOpponent = true;
                            customHandler.removeCallbacks(requestSendTimerThread);
                            customHandler.removeCallbacks(requestWaitTimerThread);

                            ltv_hint_and_opponent_name.setText(uMatch.getOpponent().getUserName());
                            ltv_hint_and_opponent_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,getResources().getDimension(R.dimen.s5_font_size));
                            if(isRewardLuck)
                                Utility.changeLucks(getApplicationContext(),-1*betAmount);
                            else
                                Utility.changeCoins(getApplicationContext(),-1*betAmount);

                            //meysam - remove randome categories from session
                            session.removeRandomCategoryIds();

                            if (betAmount == 0)
                            {
                                //meysam - no need to see ad - cont as usual...
                                Integer freeCountTillNow = session.getIntegerItem(SessionModel.KEY_FREE_MATCH_COUNT);
                                freeCountTillNow ++;
                                session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT,freeCountTillNow);
                            }

                        }
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
                    Utility.finishActivity(this);
                    startActivity(intents);
                }else {
                    Utility.displayToast(getApplicationContext(),new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                    if(new Integer(arg.toString()).equals(RequestRespondModel.ERROR_NOT_ENOUGH_BALANCE_USER_CODE))
                    {
                        customHandler.removeCallbacks(requestWaitTimerThread);
                        customHandler.removeCallbacks(requestSendTimerThread);

//                        /////////////////////meysam - 13960625
//                        Intent i = new Intent(UniversalMatchLoadingActivity.this,HazelCategoriesActivity.class);
//                        UniversalMatchLoadingActivity.this.startActivity(i);
//                        //////////////////////////////////////////////////
                        finish();
                        return;
                    }
                    sendReq = true;
                }
            }
            else
            {
                Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                sendReq = true;
            }
        }
        else
        {
            Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            sendReq = true;

        }

    }
    private Runnable randomAvatarTimerThread =new Runnable(){
        public void run(){
//                Utility.displayToast(getApplicationContext(),"",Toast.LENGTH_SHORT);
            if (isFoundOpponent){
                //opponent was found
//                    stutus_matchLoading.setVisibility(View.GONE);
                t_bg_d_left.startAnimation(
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out_bg) );
                t_bg_d_right.startAnimation(
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_out_bg) );

                //set opponent avatar sor displaying in count down....meysam
                avatar = new Avatar(uMatch.getOpponent().getProfilePicture());
                avatar.setAvatar_opt(UniversalMatchLoadingActivity.this);
                ///////////////////////////////////////////////////////////

                //meysam - play pump-up sound
                bgSound.playSpecificSound(R.raw.tick_tock_bomb,getApplicationContext(),true,null,null, null);
                /////////////////////////////

                timeInSeconds = 0;
                stutus_matchLoading.setText(Utility.enToFa(String.valueOf(timeWaitBeforeEnd)));

                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestWaitTimerThread);
                    customHandler.removeCallbacks(requestSendTimerThread);
                    customHandler = null;
                }
                customHandler =  new Handler();
                customHandler.postDelayed(requestWaitTimerThread, 1000);
            }
            else
            {
                makeRandomAvatar();
            }
        }
    };
    private Runnable requestSendTimerThread = new Runnable() {

        public void run() {

            timeInSeconds += 1;
            if(sendReq && timeInSeconds >= timeInSecondsLimit*requestCount)
            {

                if(requestCount > 8)
                {
                    if(customHandler != null)
                    {
                        customHandler.removeCallbacks(requestWaitTimerThread);
                        customHandler.removeCallbacks(requestSendTimerThread);
                        customHandler = null;
                    }

                    Utility.displayToast(UniversalMatchLoadingActivity.this,getString(R.string.msg_NoOpponentFound),Toast.LENGTH_SHORT);

//                    Utility.finishActivity(UniversalMatchLoadingActivity.this);
                    closeActivity();

//                    finish();
                    return;
                }
                else
                {
                    sendReq = false;
                    mc.universalRequest(requestCount.toString(),betId,categoryId,null,matchType, UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM);//meysam - added in 13961001
                    requestCount++;
                    customHandler.postDelayed(this, 1000);
                }

            }
            else if(requestCount > 9)
            {
                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestWaitTimerThread);
                    customHandler.removeCallbacks(requestSendTimerThread);
                    customHandler = null;
                }

                Utility.displayToast(UniversalMatchLoadingActivity.this,getString(R.string.error_connecting_to_server),Toast.LENGTH_SHORT);
//                /////////////////////meysam - 13960625
//                Intent i = new Intent(UniversalMatchLoadingActivity.this,HazelCategoriesActivity.class);
//                UniversalMatchLoadingActivity.this.startActivity(i);
//                //////////////////////////////////////////////////
//                Utility.finishActivity(UniversalMatchLoadingActivity.this);
                closeActivity();
//                finish();
                return;
            }
            else
            {
                customHandler.postDelayed(this, 1000);
            }
//            customHandler.postDelayed(this, 1000);
        }

    };

    private Runnable requestWaitTimerThread = new Runnable() {

        public void run() {

            timeInSeconds += 1;

            if(timeInSeconds > timeWaitBeforeEnd)
            {
                //meysam - stop playing specific sound
                bgSound.stopSpecificSound();
                bgSound.playCountinuseRandomMusic();
//                new SoundModel(getApplicationContext()).playCountinuseRandomMusic();
                ///////////////////////////////////////


                if(customHandler != null)
                {
                    customHandler.removeCallbacks(requestWaitTimerThread);
                    customHandler.removeCallbacks(requestSendTimerThread);
                    customHandler = null;
                }

                Intent i = null;
                if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
                    i = new Intent(UniversalMatchLoadingActivity.this,UniversalMatchQuestionActivity.class);
                if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
                    i = new Intent(UniversalMatchLoadingActivity.this,UniversalMatchCrossTableActivity.class);
                uMatch.setNitro(isNitro);
                uMatch.setRewardLuck(isRewardLuck);
//                uMatch.setEnded(false);
                uMatch.setMatchType(matchType);
                uMatch.setBet(betId);
                uMatch.setEnded(UniversalMatchModel.USER_UNIVERSAL_MATCH_ENDED_STATUS_IN_PROGRESS);
                uMatch.setMatchStatus(UniversalMatchModel.STATUS_IN_PROGRESS);
                if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
                {
                    UniversalMatchQuestionActivity.match = uMatch;

                    UniversalMatchQuestionActivity.ShowRewardQuestion = false;
                    UniversalMatchQuestionActivity.questionNumber = 0;
                    UniversalMatchQuestionActivity.used_hazel_amount = 0;
                    UniversalMatchQuestionActivity.used_luck_amount = 0;
                }
                if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
                {
                    UniversalMatchCrossTableActivity.match = uMatch;

                    UniversalMatchCrossTableActivity.used_hazel_amount = 0;
                    UniversalMatchCrossTableActivity.used_luck_amount = 0;
                }



                if(addedTime > 0)
                {
                    UniversalMatchModel.addMoreTimeToMatch(addedTime);
                    mathDurationSeconds = UniversalMatchModel.MATCH_DURATION_SECONDS;
                    mathDurationMinutes = UniversalMatchModel.MATCH_DURATION_MINUTES;
                    addedTime= 0;

                }
                i.putExtra("seconds",mathDurationSeconds);
                i.putExtra("minutes",mathDurationMinutes);
                finish();
                UniversalMatchLoadingActivity.this.startActivity(i);
            }
            else
            {
                stutus_matchLoading.setText(Utility.enToFa(String.valueOf(timeWaitBeforeEnd - timeInSeconds)));
//                stutus_matchLoading.setTextSize(stutus_matchLoading.getTextSize()*2);
                customHandler.postDelayed(this, 1000);
            }

        }

    };

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if(intent.getStringExtra("close_activity") != null)
            {
                if(intent.getStringExtra("close_activity").equals("true"))
                {
                    Utility.finishActivity(UniversalMatchLoadingActivity.this);
                }
            }
        }

    };

    private void closeActivity()
    {
        Intent intent = new Intent("universal_match_loading_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("close_activity", "true");
        LocalBroadcastManager.getInstance(UniversalMatchLoadingActivity.this).sendBroadcast(intent);
    }

//    @Override
//    public void onBackPressed() {
//
//        final Dialog d = new Dialog(UniversalMatchLoadingActivity.this);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        d.setContentView(R.layout.message_dialog);
//
//        TextView message_box_dialog = (TextView) d.findViewById(R.id.message_box_dialog);
//        Button btn_dialog_01 = (Button) d.findViewById(R.id.btn_mess_01);
//        Button btn_dialog_02 = (Button) d.findViewById(R.id.btn_mess_02);
//
//        message_box_dialog.setText("اطمینان داری؟ ممکنه ببازی!!");
//        btn_dialog_01.setText("بله");
//        btn_dialog_02.setText("خیر");
//        btn_dialog_02.setVisibility(View.VISIBLE);
//
//        btn_dialog_01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                customHandler.removeCallbacks(requestWaitTimerThread);
//                customHandler.removeCallbacks(requestSendTimerThread);
//
//                    UniversalMatchLoadingActivity.this.finish();
//                    return;
//
//
//            }
//        });
//
//        btn_dialog_02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                d.hide();
//            }
//        });
//
//        d.show();
//    }

    @Override
    public void onBackPressed() {

//        super.onBackPressed();
    }

@Override
public void onPause() {
    super.onPause();
}

    @Override
    public void onDestroy() {

        if(mc != null)
        {
            mc.setCntx(null);
            mc.deleteObservers();
            mc = null;
        }

        if(customHandler != null)
        {
            customHandler.removeCallbacks(requestWaitTimerThread);
            customHandler.removeCallbacks(requestSendTimerThread);
            customHandler = null;
        }
        if(randomAvatarHandler != null)
        {
            randomAvatarHandler.removeCallbacks(randomAvatarTimerThread);
            randomAvatarHandler = null;
        }
        if(requestSendTimerThread != null)
        {
            requestSendTimerThread = null;
        }
        if(requestWaitTimerThread != null)
        {
            requestWaitTimerThread = null;
        }
        if(randomAvatarTimerThread != null)
        {
            randomAvatarTimerThread = null;
        }
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onResume() {

        if(!inCreating)
        {
            mc = new MatchController(getApplicationContext());
            mc.addObserver(this);
        }
        inCreating = false;

        super.onResume();
    }
}
