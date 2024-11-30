package ir.fardan7eghlim.luckylord.views.match;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.BetModel;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_cup;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_luck;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.DialogSelectBetModel;
import ir.fardan7eghlim.luckylord.utils.DialogSelectCategoryModel;
import ir.fardan7eghlim.luckylord.utils.LuckyLordRecyclerViewAdapter;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesActivity;
import ir.fardan7eghlim.luckylord.views.hazels.HazelMatchIndexActivity;
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

public class UniversalMatchResultActivity extends BaseActivity implements Observer {

    private Avatar avatar;
    private Avatar avatar_oponnent;

    private DialogModel DM;

    private MatchController mc;
    private UserController uc;

    private String MatchOpponentCorrectCount;
    private String MatchOpponentUserName;
    private String MatchOpponentAvatar;
    private String MatchOpponentSpentTime;
    private String MatchSelfCorrectCount;
    private String MatchSelfSpentTime;
    private String MatchOpponentAllowChat;

    private Integer MatchStatus;
    private Integer MatchType;
    private Integer OppoenetType;
    private String BetType;

    private Boolean IsRewardLuck;
    private Integer RewardAmount;

    TextView tv_SelfUserName;
    TextView tv_OpponentUserName;
    TextView tv_SelfCorrectCount;
    TextView tv_OpponentCorrectCount;
    TextView tv_SelfSpentTime;
    TextView tv_OpponentSpentTime;

    private LuckyTextView ltv_AdditionalInfo;
    private ImageView iv_MatchStatus;
    private FrameLayout opponentAvatar;

    private LinearLayout buttons;
    private LinearLayout timer;
    private int seconds;
    private LuckyTextView ltv_timer_to_close;
    private Handler customHandler = new Handler();

    private Bundle extras;

    private Runnable resultTimerThread = new Runnable() {

        public void run() {

                if(seconds<10)
                {
                    ltv_timer_to_close.setText(Utility.enToFa("00:0"+String.valueOf(seconds)));
                }
                else
                {
                    ltv_timer_to_close.setText(Utility.enToFa("00:"+String.valueOf(seconds)));
                }

                if(seconds == 0)
                {

                    ltv_timer_to_close.setText(Utility.enToFa("00:00"));

                }

                seconds -= 1;
                if( seconds < 0)
                {
                    customHandler.removeCallbacks(this);
                    customHandler.removeCallbacksAndMessages(null);
                    customHandler = null;
                    //Meysam: end activity...meysam
                    UniversalMatchResultActivity.this.finish();
                }
                else
                {
                    customHandler.postDelayed(this, 1000);
                }


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);

        if(UniversalMatchQuestionActivity.MQA != null)
        {
            if(UniversalMatchQuestionActivity.MQA.customHandler != null)
            {
                UniversalMatchQuestionActivity.MQA.customHandler.removeCallbacksAndMessages(null);
            }
            UniversalMatchQuestionActivity.MQA = null;
        }

        mc = new MatchController(getApplicationContext());
        mc.addObserver(this);

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("universal_match_result_activity_broadcast"));

        opponentAvatar = findViewById(R.id.result_op_avt);
        DM=new DialogModel(this);

        buttons = (LinearLayout) findViewById(R.id.buttons_mr);
        timer = (LinearLayout) findViewById(R.id.timer_mr);
        ltv_timer_to_close = (LuckyTextView) findViewById(R.id.txt_timer_to_close);
        ltv_timer_to_close.setText(Utility.enToFa("00:10"));
        seconds = 10;

        Button btn_back = (Button) findViewById(R.id.btn_back_resultMatch);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_shair = (Button) findViewById(R.id.btn_shair_resultMatch);
        btn_shair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final LinearLayout ll= (LinearLayout) findViewById(R.id.main_body_amr);
//                ll.setBackgroundResource(R.drawable.t_bg_c);
//                ll.setDrawingCacheEnabled(true);
//                Bitmap b = ll.getDrawingCache();
//                ll.setBackgroundResource(0);
//                Utility.store(b,session.getCurrentUser().getInviteCode());
//                shareImage(new File(Utility.dirPath, session.getCurrentUser().getInviteCode()));
                showNextActionDialog(true,true,true,true);

            }
        });

        //fileds
        tv_SelfUserName= (TextView) findViewById(R.id.UserName_mr);
        tv_OpponentUserName= (TextView) findViewById(R.id.UserName_op_mr);
        tv_SelfCorrectCount= (TextView) findViewById(R.id.UserCorrect_mr);
        tv_OpponentCorrectCount= (TextView) findViewById(R.id.UserCorrect_op_mr);
        tv_OpponentSpentTime= (TextView) findViewById(R.id.UserTime_op_mr);
        tv_SelfSpentTime= (TextView) findViewById(R.id.UserTime_mr);
        ltv_AdditionalInfo = (LuckyTextView) findViewById(R.id.lt_status_result);
        ltv_AdditionalInfo.setText("" );
        iv_MatchStatus = (ImageView) findViewById(R.id.result_resultMatch);

        extras = getIntent().getExtras();


        if(extras != null)
        {
            //meysam - show cup animation if gained...
            if(extras.containsKey("is_cup_gained"))
            {
                new DialogModel_cup(UniversalMatchResultActivity.this).show(Utility.getCupTitleByCode(getApplicationContext(),extras.getInt("is_cup_gained")),extras.getInt("is_cup_gained"));
            }
            ////////////////////////////////////////

            MatchType = extras.getInt("match_type");
            MatchStatus = extras.getInt("match_status");
            OppoenetType = extras.getInt("opponent_type");
            BetType = extras.getString("bet_type");

            if(extras.containsKey("match_self_spent_time"))
                MatchSelfSpentTime = extras.getString("match_self_spent_time");
            if(extras.containsKey("match_self_correct_count"))
                MatchSelfCorrectCount = extras.getString("match_self_correct_count");

            if(extras.containsKey("match_opponent_spent_time"))
                MatchOpponentSpentTime = extras.getString("match_opponent_spent_time");
            if(extras.containsKey("match_opponent_correct_count"))
                MatchOpponentCorrectCount = extras.getString("match_opponent_correct_count");
            MatchOpponentAvatar = extras.getString("match_opponent_avatar");
            MatchOpponentUserName = extras.getString("match_opponent_user_name");

            MatchOpponentAllowChat = extras.getString("match_opponent_allow_chat");

            RewardAmount = UniversalMatchModel.getRewardByIDAndStatus(BetType,MatchStatus);
//            meysam - if exist then we must show reward amount dialog
            if(extras.containsKey("is_reward_luck"))
            {
                IsRewardLuck = extras.getBoolean("is_reward_luck");
//                if(IsRewardLuck)
//                {
//                    session.increaseLuck(RewardAmount.toString());
//                }


            }
//            if(extras.containsKey("reward_amount"))
//            {
////                RewardAmount = extras.getInt("reward_amount");
//                RewardAmount = UniversalMatchModel.getRewardByID(Bet)
//            }

        }
        else
        {
            UniversalMatchResultActivity.this.finish();
            return;
        }

        initialize();
    }

    private void initialize() {

        //meysam - set avatars and usernames and spent times and

//        tv_OpponentUserName.setText(MatchOpponentUserName);
        setUsername(tv_OpponentUserName,MatchOpponentUserName);
        avatar_oponnent = new Avatar(MatchOpponentAvatar);
        avatar_oponnent.setAvatar_opt(this);

        if(session.getCurrentUser().getUserName() != null)
        {
//            tv_SelfUserName.setText(session.getCurrentUser().getUserName());
            setUsername(tv_SelfUserName,session.getCurrentUser().getUserName());
        }
        else
        {
//            tv_SelfUserName.setText(session.getCurrentUser().getVisitorUserNameShow());
            setUsername(tv_SelfUserName,session.getCurrentUser().getVisitorUserNameShow());
        }


        if (MatchOpponentSpentTime != null)
        {
            tv_OpponentSpentTime.setText(Utility.enToFa(Utility.convertSec2Min(MatchOpponentSpentTime)));
            tv_OpponentCorrectCount.setText(Utility.enToFa(MatchOpponentCorrectCount));
        }
        else
        {
            tv_OpponentSpentTime.setText(Utility.enToFa("??"));
            tv_OpponentCorrectCount.setText(Utility.enToFa("??"));
        }
//        if(MatchOpponentCorrectCount != null)
//            tv_OpponentCorrectCount.setText(Utility.enToFa(MatchOpponentCorrectCount));
//        else
//            tv_OpponentCorrectCount.setText(Utility.enToFa("??"));

        if (MatchSelfSpentTime != null)
        {
            tv_SelfSpentTime.setText(Utility.enToFa(Utility.convertSec2Min(MatchSelfSpentTime)));
            tv_SelfCorrectCount.setText(Utility.enToFa(MatchSelfCorrectCount));
        }
        else
        {
            if(MatchStatus.equals(UniversalMatchModel.STATUS_WIN) || MatchStatus.equals( UniversalMatchModel.STATUS_EQUAL)|| MatchStatus.equals( UniversalMatchModel.STATUS_LOST))
            {
                BetModel choosenBet = UniversalMatchModel.getBetByID(BetType);

                tv_SelfSpentTime.setText(Utility.enToFa(Utility.convertSec2Min( choosenBet.getTime().toString())));
                tv_SelfCorrectCount.setText(Utility.enToFa("0"));
            }
            else
            {
                tv_SelfSpentTime.setText(Utility.enToFa("??"));
                tv_SelfCorrectCount.setText(Utility.enToFa("??"));
            }

        }

//        tv_SelfSpentTime.setText(Utility.enToFa(Utility.convertSec2Min(MatchSelfSpentTime)));
//        tv_SelfCorrectCount.setText(Utility.enToFa(MatchSelfCorrectCount));

        if (session.getCurrentUser().getProfilePicture() == null) {
            if(session.getCurrentUser().getGender() != null && session.getCurrentUser().getGender().equals("1"))
            {
                //female
                avatar = new Avatar("f#1#1#1#1#1#0#0#0#0#0#0#0");
            }
            else
            {
                //male
                avatar = new Avatar("m#1#1#1#1#1#0#0#0#0#0#0#0");
            }
        } else {
            avatar = new Avatar(session.getCurrentUser().getProfilePicture());
        }
        avatar.setAvatar(this);

        if(RewardAmount != null && (MatchStatus.equals(UniversalMatchModel.STATUS_WIN) || MatchStatus.equals( UniversalMatchModel.STATUS_EQUAL)))
        {
            //meysam - show additional information textview
            ltv_AdditionalInfo.setVisibility(View.VISIBLE);
            if(IsRewardLuck != null && IsRewardLuck == true)
            {
                session.increaseLuck(RewardAmount.toString());
                ltv_AdditionalInfo.setText(Utility.enToFa(RewardAmount + " شانس به حسابت برگشت داده شد " ));
            }
            else
            {
                session.increaseHazel(RewardAmount.toString());
                ltv_AdditionalInfo.setText(Utility.enToFa( RewardAmount + " فندق به حسابت برگشت داده شد "));
            }
        }
        else
        {
            //meysam - hide additional information text view
//            ltv_AdditionalInfo.setVisibility(View.GONE);
            ltv_AdditionalInfo.setText(Utility.enToFa( UniversalMatchModel.getBetAmountTextById(BetType)));

        }

        if(MatchStatus.equals( UniversalMatchModel.STATUS_DISAPPROVED_BY_YOU) ||
                MatchStatus.equals( UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT) ||
                MatchStatus.equals( UniversalMatchModel.STATUS_REQUEST_EXPIRED) ||
                MatchStatus.equals( UniversalMatchModel.STATUS_EQUAL) ||
                MatchStatus.equals( UniversalMatchModel.STATUS_LOST )||
                MatchStatus.equals( UniversalMatchModel.STATUS_WIN) )
        {
            ltv_timer_to_close.setVisibility(View.GONE);

            if(MatchStatus.equals( UniversalMatchModel.STATUS_EQUAL))
                iv_MatchStatus.setImageResource(R.drawable.t_text_even);
            else if(MatchStatus.equals( UniversalMatchModel.STATUS_WIN))
            {
                //meysam - play win match sound
                SoundModel.playSpecificSound(R.raw.victory_match,getApplicationContext(),false,null,null, null);
                /////////////////////////////
                iv_MatchStatus.setImageResource(R.drawable.t_text_win);
            }
            else if(MatchStatus.equals(UniversalMatchModel.STATUS_LOST))
            {
                //meysam - play lost match sound
                SoundModel.playSpecificSound(R.raw.lost_match,getApplicationContext(),false,null,null, null);
                /////////////////////////////
                iv_MatchStatus.setImageResource(R.drawable.t_text_lose);

            }
            else if(MatchStatus.equals( UniversalMatchModel.STATUS_DISAPPROVED_BY_YOU))
            {
                iv_MatchStatus.setImageResource(R.drawable.t_text_disapproved_by_you);
            }
            else if(MatchStatus.equals( UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT))
            {
                iv_MatchStatus.setImageResource(R.drawable.t_text_disapproved_by_opponent);
            }
            else if(MatchStatus.equals( UniversalMatchModel.STATUS_REQUEST_EXPIRED))
            {
                iv_MatchStatus.setImageResource(R.drawable.t_text_expired);
            }
            else
                iv_MatchStatus.setImageResource(R.drawable.t_text_unfinish);

            //، meysam - add avatar click and rematch and chat and profile
            opponentAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNextActionDialog(true,true,false,false);
                }
            });
            ///////////////////////////////////////////////////////////////////

        }
        else if(MatchStatus.equals( UniversalMatchModel.STATUS_IN_PROGRESS )||
                MatchStatus.equals( UniversalMatchModel.STATUS_OPPONENT_TURN))
        {
            ltv_timer_to_close.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.GONE);
            timer.setVisibility(View.VISIBLE);
            iv_MatchStatus.setImageResource(R.drawable.t_text_unfinish);
            ltv_AdditionalInfo.setText(getApplicationContext().getString(R.string.msg_WaitForOpponent) );

            //meysam - it's after we send our turn then we show timer
            customHandler.postDelayed(resultTimerThread, 1000);
        }
        else
        {
            UniversalMatchResultActivity.this.finish();
            return;
        }
    }


    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "اشتراک گذاری..."));
        } catch (ActivityNotFoundException e) {
            Utility.displayToast(getApplicationContext(), "برنامه ای برای اشتراک وجود ندارد", Toast.LENGTH_SHORT);
        }
    }

    private void showNextActionDialog(Boolean haveChat, Boolean haveProfile, Boolean haveShare, Boolean haveRematch)
    {
        String btn_Chat = "چت";
        String btn_Profile = "پروفایل";
        String btn_Share = "اشتراک گذاری";
        String btn_Rematch = "دوباره";

        if(!haveChat)
            btn_Chat = null;
        if(!haveProfile)
            btn_Profile = null;
        if(!haveShare)
            btn_Share = null;
        if(!haveRematch)
            btn_Rematch = null;

        DialogPopUpFourVerticalModel.show(UniversalMatchResultActivity.this,null, btn_Profile,btn_Chat,btn_Rematch,btn_Share,true,true);
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
                        if(DialogPopUpFourVerticalModel.dialog_result==1)
                        {
                            //meysam - go to profile

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goToProfile();
                                }
                            });

                        }
                        else if(DialogPopUpFourVerticalModel.dialog_result==2)
                        {
                            //meysam - go to chat
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goToChat();
                                }
                            });
                        }
                        else if(DialogPopUpFourVerticalModel.dialog_result==3)
                        {
                            //meysam - send rematch request
                            //meysam - show sure dialog
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(!MatchOpponentUserName.contains("Visitor_"))
                                    {
                                        showHorizontalBetDialog();
                                    }
                                    else
                                    {
//                                        Utility.displayToast(UniversalMatchResultActivity.this,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                                        DialogPopUpModel.show(UniversalMatchResultActivity.this,"به مهمان نمیشه درخواست داد",getString(R.string.btn_OK),null,false,true);
                                    }
//                                    if(MatchType == UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION)
//                                        showSelectCategoryDialog();

//                            Utility.displayToast(UniversalMatchResultActivity.this,getApplicationContext().getString(R.string.msg_InNewVersion),Toast.LENGTH_SHORT);

                                }
                            });
                        }
                        else if(DialogPopUpFourVerticalModel.dialog_result==3)
                        {
                            //meysam - share chat
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final LinearLayout ll= (LinearLayout) findViewById(R.id.main_body_amr);
                                    ll.setBackgroundResource(R.drawable.t_bg_c);
                                    ll.setDrawingCacheEnabled(true);
                                    Bitmap b = ll.getDrawingCache();
                                    ll.setBackgroundResource(0);
                                    Utility.store(b,session.getCurrentUser().getInviteCode());
                                    shareImage(new File(Utility.dirPath, session.getCurrentUser().getInviteCode()));
                                }
                            });

                        }
                        else
                        {
                            //meysam - do nothing...
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

    private void goToProfile()
    {
        //meysam - if visitor then no profile...
        if(MatchOpponentUserName != null && !MatchOpponentUserName.contains("Visitor_")) {
            DM.show();
            //////////////////////////////////////////////////////////////////
            Intent intent = new Intent(UniversalMatchResultActivity.this, UserProfileActivity.class);
            intent.putExtra("EXTRA_User_Name", MatchOpponentUserName);
            intent.putExtra("EXTRA_Avatar", MatchOpponentAvatar);
            UniversalMatchResultActivity.this.startActivity(intent);

            DM.hide();
        }else{
            Utility.displayToast(UniversalMatchResultActivity.this,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
        }
    }

    private void goToChat()
    {
        if(session.getCurrentUser().getUserName() != null )
        {
            //meysam - if visitor then no profile...
            if(MatchOpponentUserName != null && !MatchOpponentUserName.contains("Visitor_")) {

                //////////////////////////////////////////////////////////////////

                if(session.getCurrentUser().getAllowChat().equals("1"))
                {
                    if(MatchOpponentAllowChat.equals("1"))
                    {
                        DM.show();
                        //meysam - chat is allowed
                        Intent intent = new Intent(UniversalMatchResultActivity.this, ChatActivity.class);
                        intent.putExtra("opponent_user_name", MatchOpponentUserName);
                        intent.putExtra("opponent_avatar", MatchOpponentAvatar);
                        DM.hide();
                        UniversalMatchResultActivity.this.startActivity(intent);
                    }
                    else
                    {
                        DialogPopUpModel.show(UniversalMatchResultActivity.this,"حریف چت رو غیر فعال کرده",getString(R.string.btn_OK),null,false,true);
                    }
                }
                else
                {
                    DialogPopUpModel.show(UniversalMatchResultActivity.this,"چت در تنظیمات غیر فعال شده",getString(R.string.btn_OK),null,false,true);

                }


            }else{
                Utility.displayToast(UniversalMatchResultActivity.this,"مهمان چت نداره", Toast.LENGTH_SHORT);
            }
        }
        else
        {
            DialogPopUpModel.show(UniversalMatchResultActivity.this,getString(R.string.msg_MustRegister),getString(R.string.btn_OK),null,false,true);
        }

    }

    private void showSelectCategoryDialog()
    {
        int[] tmp;
        if(session.hasRandomCategoryIds())
            tmp = session.getRandomCategoryIds();
        else
        {
            tmp =  CategoryModel.getThreeRandomCategory();
            session.setRandomCategoryIds(tmp);
        }
        DialogSelectCategoryModel.show(UniversalMatchResultActivity.this, String.valueOf(tmp[0]), String.valueOf(tmp[1]),String.valueOf(tmp[2]), false, true,"universal_match_result_activity_broadcast");
    }

    //show bet dialog
    private void showHorizontalBetDialog(){

        DialogSelectBetModel.show(UniversalMatchResultActivity.this, false, true,"universal_match_result_activity_broadcast");
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

                    Utility.displayToast(UniversalMatchResultActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);

                }
            }
            if(intent.getStringExtra("show_not_enough_hazel_dialog") != null)//meysam - added in 13960608
            {
                DialogPopUpModel.show(UniversalMatchResultActivity.this,"برای بازی باید حداقل " + LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString() + " فندق داشته باشی!","باشه",null, false, false);

            }

            if(intent.getStringExtra("show_not_enough_luck_dialog") != null)//meysam - added in 13960608
            {
                DialogPopUpModel.show(UniversalMatchResultActivity.this,"برای بازی باید حداقل " + LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString() + " شانس داشته باشی!","باشه",null, false, false);

            }
            if(intent.getStringExtra("show_reward_chooser_dialog") != null)//meysam - added in 13960608
            {
                showSelectCategoryDialog();
            }
            if(intent.getStringExtra("category_id") != null)
            {
                String categoryId = intent.getStringExtra("category_id");

                DM.show();
                mc.universalRematchRequest(LuckyLordRecyclerViewAdapter.chosenBet.getBetId().toString(),categoryId,MatchOpponentUserName,MatchType,UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM);

                /////////////////////////////////////////////////////
            }
            if(intent.getStringExtra("call_category_dialog") != null)//meysam - added in 13960608
            {
                if (LuckyLordRecyclerViewAdapter.chosenBet.getAmount().equals(0)) {

                    //meysam - check if 1 day has passed
                    if (Utility.isMinuteLimitReached(session.getStringItem(SessionModel.KEY_FREE_MATCH_TIME), String.valueOf(Utility.ALLOWED_FREE_MATCH_INTERVAL_TIME))) {
                        //meysam - reset date and count for free match...
                        session.saveItem(SessionModel.KEY_FREE_MATCH_TIME, new Date().getTime() + "");
                        session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT, 0);
                    }

                    Integer freeCountTillNow = session.getIntegerItem(SessionModel.KEY_FREE_MATCH_COUNT);

                    if (freeCountTillNow != -1 && freeCountTillNow >= Utility.ALLOWED_FREE_MATCH_COUNT) {
                        //meysam - must see advertisment before free match...
                        //show dialog to choose to watch ad or cancel...

                        DialogPopUpFourVerticalModel.show(UniversalMatchResultActivity.this, " سهم امروزت تموم شده. باید یه تبلیغ نگاه کنی تا بتونی مسابقه بدی ", getString(R.string.btn_SeeAd), Utility.enToFa(String.valueOf(Utility.ALLOWED_FREE_MATCH_COST)) + " تا فندق میدم ", null, getString(R.string.btn_No), true, false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (DialogPopUpFourVerticalModel.isUp()) {
                                        Thread.sleep(500);
                                    }
                                    if (!DialogPopUpFourVerticalModel.isUp()) {
                                        Thread.currentThread().interrupt();
                                        if (DialogPopUpFourVerticalModel.dialog_result == 1) {
                                            //show ad
                                            showLoading();
                                            WatchAd();

                                        } else if (DialogPopUpFourVerticalModel.dialog_result == 2) {
                                            //recieve hazel from user if have it
                                            if (Utility.hasEnoughCoin(UniversalMatchResultActivity.this, Utility.ALLOWED_FREE_MATCH_COST)) {
                                                showLoading();
                                                Utility.changeCoins(getApplicationContext(), -1 * Utility.ALLOWED_FREE_MATCH_COST);
                                                session.changeFinalHazel(-1 * Utility.ALLOWED_FREE_MATCH_COST);
                                                uc.change(-1 * Utility.ALLOWED_FREE_MATCH_COST, session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));

                                            } else {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showNotEnoughHazelMessage();
                                                    }
                                                });
                                            }
                                        } else if (DialogPopUpFourVerticalModel.dialog_result == 4) {
                                            //meysam - do nothing
                                        } else {
                                            //meysam - do nothing
                                        }

                                        DialogPopUpFourVerticalModel.hide();

                                    }
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        //meysam - no need to see ad - cont as usual...
                        freeCountTillNow++;
                        session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT, freeCountTillNow);
                        afterBetDialogOperation();
                    }

                } else {
                    afterBetDialogOperation();
                }
            }
        }

    };

    private void showLoading()
    {
        Intent intent = new Intent("universal_match_result_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(UniversalMatchResultActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughHazelMessage()
    {
        Intent intent = new Intent("universal_match_result_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_hazel", "true");
        LocalBroadcastManager.getInstance(UniversalMatchResultActivity.this).sendBroadcast(intent);
    }

    private void afterBetDialogOperation() {

        if(MatchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
        {
            showSelectCategoryDialog();
        }
        else
        {
            // meysam - call loading activity
            Intent intent = new Intent("universal_match_result_activity_broadcast");
            intent.putExtra("category_id", "null");
            LocalBroadcastManager.getInstance(UniversalMatchResultActivity.this).sendBroadcast(intent);

        }
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();


        String adZoneId;

        adZoneId = AdvertismentModel.MatchResultPageZoneId;


        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);
        Tapsell.requestAd(UniversalMatchResultActivity.this,adZoneId , options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(UniversalMatchResultActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(UniversalMatchResultActivity.this).playCountinuseRandomMusic();

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) UniversalMatchResultActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(UniversalMatchResultActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(UniversalMatchResultActivity.this).playCountinuseRandomMusic();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        afterBetDialogOperation();
                    }
                });

            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(UniversalMatchResultActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(UniversalMatchResultActivity.this).playCountinuseRandomMusic();

            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(UniversalMatchResultActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(UniversalMatchResultActivity.this).playCountinuseRandomMusic();

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

                new SoundModel(UniversalMatchResultActivity.this).playCountinuseRandomMusic();

                if(completed)
                {
                    //stuff that updates ui
                    showSelectCategoryDialog();
                }
                else
                {
                    Utility.displayToast(UniversalMatchResultActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }


            }
        });
    }

    @Override
    public void onDestroy() {

        if(DM != null)
        {
            DM.hide();
            DM.dismiss();
            DM = null;
        }
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
        if(customHandler != null)
        {
            customHandler.removeCallbacks(resultTimerThread);
            customHandler.removeCallbacksAndMessages(null);
            customHandler = null;
        }

        Tapsell.setRewardListener(null);

        // Unregister since the activity is about to be closed.
        LuckyLordRecyclerViewAdapter.chosenBet = null;//meysam - added in 13960608
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        super.onDestroy();
    }

    private void setUsername(TextView main_name_a,String name) {
        Typeface face;
        if (!Utility.isProbablyArabic(name)) {
            face=Typeface.createFromAsset(getAssets(), "fonts/RAVIE.TTF");
        }else{
            face=Typeface.createFromAsset(getAssets(), "fonts/Khandevane.ttf");
        }
        main_name_a.setTypeface(face);
        main_name_a.setText(name);
//        if(name.length()>16){
//            main_name_a.setTextSize(R.dimen.s4_font_size);
//        }
    }

    ////meysam - 13960625
    @Override
    public void onBackPressed() {
        if(customHandler != null)
        {
            customHandler.removeCallbacks(resultTimerThread);
            customHandler.removeCallbacksAndMessages(null);
            customHandler = null;
        }

//        Intent i = new Intent(UniversalMatchResultActivity.this,HazelCategoriesActivity.class);
//
//        UniversalMatchResultActivity.this.startActivity(i);
        super.onBackPressed();
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
                    Intent i = new Intent(UniversalMatchResultActivity.this,MainActivity.class);
                    UniversalMatchResultActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                 if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new DialogModel_hazel(UniversalMatchResultActivity.this).show(false, false, -1*new Integer(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL)));
                                session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                                session.removeItem(SessionModel.KEY_FINAL_LUCK);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 3s = 3000ms
                                        afterBetDialogOperation();
                                    }
                                }, 2000);


                            }
                        });

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST))
                {
                    //meysam - remove randome categories from session
                    session.removeRandomCategoryIds();

                    // meysam - add new Match to Match List...
                    UniversalMatchModel universalMatch = (UniversalMatchModel) ((ArrayList) arg).get(1);

                    UserModel opponent = new UserModel();
                    opponent.setUserName(MatchOpponentUserName);
                    opponent.setProfilePicture(MatchOpponentAvatar);
                    opponent.setAllowChat(MatchOpponentAllowChat);

                    universalMatch.setOpponent(opponent);
                    universalMatch.setMatchStatus(UniversalMatchModel.STATUS_REQUEST_SENT);
                    universalMatch.setBet(LuckyLordRecyclerViewAdapter.chosenBet.getBetId());
                    HazelMatchIndexActivity.uMatches.add(0,universalMatch);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(UniversalMatchResultActivity.this, UniversalMatchResultActivity.this.getApplicationContext().getString(R.string.msg_RequestSent), UniversalMatchResultActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }
                    });

                    //meysam
                    if(LuckyLordRecyclerViewAdapter.chosenBet.getBetLuck())
                    {
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getAmount() != 0)
                        {
                            session.decreaseLuck( LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogModel_luck(UniversalMatchResultActivity.this).show(true, false, LuckyLordRecyclerViewAdapter.chosenBet.getAmount());
                                }
                            });
                        }

                    }
                    else
                    {
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getAmount() != 0)
                        {
                            session.decreaseHazel(LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogModel_hazel(UniversalMatchResultActivity.this).show(true, false, LuckyLordRecyclerViewAdapter.chosenBet.getAmount());
                                }
                            });
                        }

                    }
                    ///////////////////////////////////////

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
}
