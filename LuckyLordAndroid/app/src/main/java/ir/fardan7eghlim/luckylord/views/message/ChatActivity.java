package ir.fardan7eghlim.luckylord.views.message;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;

import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.ChatController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.ChatModel;
import ir.fardan7eghlim.luckylord.models.MessageModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextViewAutoSize;
import ir.fardan7eghlim.luckylord.services.ChatService;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_chat;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_chatDetail;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_message;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.home.StoreActivity;
import ir.fardan7eghlim.luckylord.views.user.UserProfileActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class ChatActivity extends BaseActivity implements Observer {


//    private ChatController cc;
    private String opponentUserName;
    private Avatar avatar;
    private String opponentAvatar;
    private String myUserName;
    private chatUser me;
    private chatUser opponent;
    private CustomAdapterList_chat CAC;
    private CustomAdapterList_chatDetail CACD;
    private int height;
    private ImageView result_anim;

    private FrameLayout key_1;
    private FrameLayout key_2;
    private FrameLayout key_3;
    private FrameLayout key_4;
    private FrameLayout key_5;
    private FrameLayout key_6;
    private FrameLayout key_7;
    private FrameLayout key_8;
    private FrameLayout key_9;
    private FrameLayout key_11;
    private FrameLayout key_12;
    private FrameLayout key_13;
    private FrameLayout key_14;
    private FrameLayout key_15;
    private FrameLayout key_16;
    private FrameLayout key_17;
    private FrameLayout key_18;
    private FrameLayout key_19;
    private FrameLayout key_21;
    private FrameLayout key_22;
    private FrameLayout key_23;
    private FrameLayout key_24;
    private FrameLayout key_25;
    private FrameLayout key_26;
    private FrameLayout key_backspace;
    private FrameLayout key_space;
    private FrameLayout key_laugh;
    private FrameLayout key_confused;
    private FrameLayout key_angry;
    private FrameLayout key_sad;
    private FrameLayout key_pockerFace;

    private EditText txt_typing;
    private ListView lvl_recent_chat;
    private ListView lvl_recentNews_chat;

    private ArrayList<MessageModel> recentList;
    private LinearLayout ll_sendBTn_chat;
    private boolean isKeyboardShow;
    private LinearLayout ll_option_box_chat;
    private LinearLayout ll_keyboard_chat;

    private FrameLayout key_types;
    private LuckyTextViewAutoSize key_b_types;
    private int keyboard_current_type;
    private FrameLayout key_detail;
    private boolean isDetailKeyboard;

    private ImageView emj_current_stutus;
    private int emj_current_stutus_int;

    private LuckyTextViewAutoSize ltvas_status;
    private LinearLayout ll_freeChat;
    private LuckyTextViewAutoSize ltvas_last_message;
    private ImageView iv_status_icon;

    private LuckyTextViewAutoSize ltvas_hazelAmount;

    private Boolean adWatched;
    private DialogModel DM;
    private Integer spentedHazel;
    private ImageView imv_hazel_amount;

    private ImageView iv_home;
    private ImageView iv_market;
    private ImageView iv_soundMusic;
    private ImageView iv_soundNonMusic;

    private ArrayList<String> outlinedMessages;

    private ImageView icon_like;
    private ImageView icon_heart;
    private ImageView icon_kiss;
    private ImageView icon_hate;

    private boolean isMaleOpp;
    private int defult_eyebrow=1;
    private int defult_mouth=1;
    private boolean isAlowAvatarAnim;
    private Animation wavy;
    private Animation showUp;
    private TextView UserName_mr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        DM=new DialogModel(ChatActivity.this);

        ll_option_box_chat=findViewById(R.id.ll_option_box_chat);
        ll_keyboard_chat=findViewById(R.id.ll_keyboard_chat);
        lvl_recent_chat=findViewById(R.id.lvl_recent_chat);
        lvl_recentNews_chat = findViewById(R.id.lvl_recentNews_chat);
        result_anim=findViewById(R.id.result_anim);
        recentList=new ArrayList<>();
        ll_sendBTn_chat=findViewById(R.id.ll_sendBTn_chat);
        isKeyboardShow=false;
        opponentUserName = getIntent().getExtras().getString("opponent_user_name");
        UserName_mr=findViewById(R.id.UserName_mr);
        setUsername(UserName_mr,opponentUserName);
        opponentAvatar = getIntent().getExtras().getString("opponent_avatar");
        myUserName = session.getCurrentUser().getUserName() != null ? session.getCurrentUser().getUserName() : session.getCurrentUser().getVisitorUserNameShow();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        imv_hazel_amount=findViewById(R.id.imv_hazel_amount);
        //emoji
        emj_current_stutus=findViewById(R.id.emj_current_stutus);
        emj_current_stutus_int=1;
        isAlowAvatarAnim=true;
        wavy = AnimationUtils.loadAnimation(ChatActivity.this, R.anim.wavy);
        showUp = AnimationUtils.loadAnimation(ChatActivity.this, R.anim.increase);
        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        if (!session.getBoolianItem(SessionModel.KEY_CHAT_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

            DialogPopUpModel.show(ChatActivity.this, getString(R.string.dlg_TutorialChat), getString(R.string.btn_OK), null,false, false);
            session.saveItem(SessionModel.KEY_CHAT_IS_FIRST_TIME, true);

        }

        //amir - set opponent avatar...
        if(opponentAvatar == null)
        {
            //meysam - something is not right...
            Utility.displayToast(ChatActivity.this,getString(R.string.error_operation_fail),Toast.LENGTH_SHORT);
            ChatActivity.this.finish();
        }
        else
        {
            avatar = new Avatar(opponentAvatar);
            avatar.setAvatar_opt(this);
            isMaleOpp=false;
            if(avatar.getSexAge().equals("m")){
                isMaleOpp=true;
            }
            defult_eyebrow=avatar.getEyebrow();
            defult_mouth=avatar.getLip();
            //////////////////////////////////////

            ltvas_status = findViewById(R.id.onlineStatus);
            ll_freeChat = findViewById(R.id.ll_freeChat);
            ltvas_last_message = findViewById(R.id.txt_news_current);
            iv_status_icon = findViewById(R.id.onlineStatus_icon);
            ltvas_hazelAmount = findViewById(R.id.tv_hazel_amount);

            ltvas_hazelAmount.setText(session.getCurrentUser().getHazel().toString());

            adWatched = false;
            spentedHazel = 0;
            outlinedMessages = new ArrayList<>();

            iv_home = findViewById(R.id.iv_home);
            iv_market = findViewById(R.id.iv_market);
            iv_soundMusic = findViewById(R.id.iv_sound_music);
            iv_soundNonMusic = findViewById(R.id.iv_sound_non_music);

            if(session.getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
            {
                iv_soundNonMusic.setImageResource(R.drawable.b_sound_on);
                iv_soundMusic.setImageResource(R.drawable.b_sound_on);


            }
            else
            {
                iv_soundMusic.setImageResource(R.drawable.b_sound_mute);
                iv_soundNonMusic.setImageResource(R.drawable.b_sound_mute);
            }

            iv_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ChatActivity.this, MainActivity.class);
                    ChatActivity.this.startActivity(i);
                    finish();

                }
            });

            iv_market.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ChatActivity.this, StoreActivity.class);
                    ChatActivity.this.startActivity(i);
                }
            });

            iv_soundMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // meysam
                    if(SoundModel.isPlaying())
                    {
                        session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                        SoundModel.stopMusic();

                        iv_soundMusic.setImageResource(R.drawable.b_sound_mute);
                    }
                    else
                    {
                        session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
                        new SoundModel(getApplicationContext()).playCountinuseRandomMusic();

                        iv_soundMusic.setImageResource(R.drawable.b_sound_on);
                    }

                }
            });

            iv_soundNonMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: amir - controll nonmusic sound...
                }
            });


            ll_freeChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //meysam - show ad ...
//                if(!adWatched)
//                {

                    DialogPopUpFourVerticalModel.show(ChatActivity.this," یه تبلیغ ببین تا این چت رایگان بشه ",getString(R.string.btn_SeeAd), getString(R.string.btn_No),null,null,true, false);
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
                                        WatchAd();

                                    }
                                    else
                                    {
                                        //meysam - do nothing

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
//                        DM.show();
//                        WatchAd();
//                }
//                else
//                {
//                    Utility.displayToast(ChatActivity.this,getString(R.string.msg_AdLastWatched),Toast.LENGTH_SHORT);
//                }


                }
            });

            //
            txt_typing=findViewById(R.id.txt_typing);
            disableSoftInputFromAppearing(txt_typing);
            txt_typing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isKeyboardShow){
                        keyboardHideOrShow();
                    }
                }
            });
            //keyboard keys
            key_1=findViewById(R.id.key_1);
            key_2=findViewById(R.id.key_2);
            key_3=findViewById(R.id.key_3);
            key_4=findViewById(R.id.key_4);
            key_5=findViewById(R.id.key_5);
            key_6=findViewById(R.id.key_6);
            key_7=findViewById(R.id.key_7);
            key_8=findViewById(R.id.key_8);
            key_9=findViewById(R.id.key_9);
            key_11=findViewById(R.id.key_11);
            key_12=findViewById(R.id.key_12);
            key_13=findViewById(R.id.key_13);
            key_14=findViewById(R.id.key_14);
            key_15=findViewById(R.id.key_15);
            key_16=findViewById(R.id.key_16);
            key_17=findViewById(R.id.key_17);
            key_18=findViewById(R.id.key_18);
            key_19=findViewById(R.id.key_19);
            key_21=findViewById(R.id.key_21);
            key_22=findViewById(R.id.key_22);
            key_23=findViewById(R.id.key_23);
            key_24=findViewById(R.id.key_24);
            key_25=findViewById(R.id.key_25);
            key_26=findViewById(R.id.key_26);
            key_backspace=findViewById(R.id.key_backspace);
            key_space=findViewById(R.id.key_space);
            key_laugh=findViewById(R.id.key_laugh);
            key_confused=findViewById(R.id.key_confused);
            key_angry=findViewById(R.id.key_angry);
            key_sad=findViewById(R.id.key_sad);
            key_pockerFace=findViewById(R.id.key_pockerFace);

            //type and detail
            keyboard_current_type=1;
            key_types=findViewById(R.id.key_types);
            key_b_types=findViewById(R.id.key_b_types);
            key_detail=findViewById(R.id.key_detail);
            isDetailKeyboard=false;
            key_types.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    keyboard_current_type++;
                    if(keyboard_current_type==4) keyboard_current_type=1;
                    arrangeKeyboard(false);
                }
            });
            key_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrangeKeyboard(!isDetailKeyboard);
                }
            });

            for(int i=1;i<=26;i++){
                if(i==10 || i==20) continue;
                int resID = getResources().getIdentifier("key_" + i, "id", getPackageName());
                final FrameLayout btn =findViewById(resID);
                final int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int resID = getResources().getIdentifier("key_b_" + finalI, "id", getPackageName());
                        LuckyTextViewAutoSize btn =findViewById(resID);
                        String txt=txt_typing.getText().toString();
                        int cursorPosition = txt_typing.getSelectionStart();
                        txt_typing.setText(txt.substring(0,cursorPosition)+btn.getText().toString()+txt.substring(cursorPosition));
                        txt_typing.setSelection(cursorPosition+1);
                    }
                });
                btn.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        int resID = getResources().getIdentifier("key_s_" + finalI, "id", getPackageName());
                        if(resID==0) return false;
                        LuckyTextViewAutoSize btn =findViewById(resID);
                        if(btn.getText().toString().equals("")) return false;
                        String txt=txt_typing.getText().toString();
                        int cursorPosition = txt_typing.getSelectionStart();
                        txt_typing.setText(txt.substring(0,cursorPosition)+btn.getText().toString()+txt.substring(cursorPosition));
                        txt_typing.setSelection(cursorPosition+1);
                        return true;
                    }
                });
            }
            key_backspace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txt=txt_typing.getText().toString();
                    int cursorPosition = txt_typing.getSelectionStart();
                    if(cursorPosition>0 && !txt.equals("")) {
                        txt_typing.setText(txt.substring(0, cursorPosition - 1) + txt.substring(cursorPosition));
                        txt_typing.setSelection(cursorPosition-1);
                    }
                }
            });
            key_space.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txt=txt_typing.getText().toString();
                    int cursorPosition = txt_typing.getSelectionStart();
                    txt_typing.setText(txt.substring(0,cursorPosition)+" "+txt.substring(cursorPosition));
                    txt_typing.setSelection(cursorPosition+1);
                }
            });


            ll_sendBTn_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message=txt_typing.getText().toString();
                    if(!message.equals("")){
                        message=catEmotionToMessage(message);
                        send(message);
                    }
                }
            });

            key_laugh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emj_current_stutus_int=2;
                    set_emoji();
                }
            });
            key_confused.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emj_current_stutus_int=3;
                    set_emoji();
                }
            });
            key_angry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emj_current_stutus_int=4;
                    set_emoji();
                }
            });
            key_sad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emj_current_stutus_int=5;
                    set_emoji();
                }
            });
            key_pockerFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emj_current_stutus_int=1;
                    set_emoji();
                }
            });

            //non-text keys
            icon_like=findViewById(R.id.icon_like);
            icon_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAlowAvatarAnim) {
                        isAlowAvatarAnim = false;
                        send("##0s1A##");
                        unLockAvatarAnim();
                    }
                }
            });
            icon_heart=findViewById(R.id.icon_heart);
            icon_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAlowAvatarAnim) {
                        isAlowAvatarAnim = false;
                        send("##0e1v##");
                        unLockAvatarAnim();
                    }
                }
            });
            icon_kiss=findViewById(R.id.icon_kiss);
            icon_kiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAlowAvatarAnim) {
                        isAlowAvatarAnim = false;
                        send("##fs1n##");
                        unLockAvatarAnim();
                    }
                }
            });
            icon_hate=findViewById(R.id.icon_hate);
            icon_hate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAlowAvatarAnim) {
                        isAlowAvatarAnim = false;
                        send("##pQ1m##");
                        unLockAvatarAnim();
                    }
                }
            });
//        //////////////////////////////////////////////////////////////////////////////////////////////////
            //meysam - call chat controller and connect to server chat port...
//        cc = new ChatController(getApplicationContext(), opponentUserName);
//        cc.addObserver(this);
//        cc.connectWebSocket(myUserName);
//        cc = ChatService.cc;
            if(ChatService.cc == null)
            {
                //meysam - service is not running show approperiate message and exit...
                Utility.displayToast(ChatActivity.this,"سرور چت غیر فعاله",Toast.LENGTH_SHORT);
                finish();
                return;
//            Utility.finishActivity(ChatActivity.this);
            }
            ChatService.cc.setOpponentUserName(opponentUserName);
            ChatService.cc.setContext(this);
            if(!ChatService.cc.isConnected())
            {
                if(session.getCurrentUser().getUserName() != null && session.getCurrentUser().getAllowChat().equals("1"))
                {
                    Intent msgIntent = new Intent(this, ChatService.class);
                    startService(msgIntent);
                }
            }
            if(ChatService.cc.isUserOnline(opponentUserName))
            {
                ltvas_status.setText("آنلاین");
                iv_status_icon.setImageResource(R.drawable.st_online);

            }
            else
            {
                ltvas_status.setText("آفلاین");
                iv_status_icon.setImageResource(R.drawable.st_offline);
            }

            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            ArrayList<ChatModel> chats = db.getChatsByUserName(opponentUserName);
            for(int i = 0; i < chats.size(); i++)
            {
                ReceiveChatModel(chats.get(i));
//            db.deleteChatById(chats.get(i).getId());
            }
            db.deleteChatsByUserName(opponentUserName);
//        ////////////////////////////////////////////////////////////////////////

            LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                    new IntentFilter("chat_activity_broadcast"));

        }

    }

    private String catEmotionToMessage(String message) {
        if(emj_current_stutus_int==2){
            return "2"+message;
        }
        if(emj_current_stutus_int==3){
            return "3"+message;
        }
        if(emj_current_stutus_int==4){
            return "4"+message;
        }
        if(emj_current_stutus_int==5){
            return "5"+message;
        }
        return "1"+message;
    }
    private void setAvatarDependReceivedMessage(int stut){
        //TODO:: Amir = change avatar of opp depend on received message
        int current_lip=avatar.getLip();
        int current_eyebrow=avatar.getEyebrow();
        if(isMaleOpp){
            if(stut==2){//laugh
                if(current_eyebrow==1 || current_eyebrow==5 || current_eyebrow==9 || current_eyebrow==13 || current_eyebrow==17 || current_eyebrow==21){
                    avatar.setEyebrow(9);
                }else if(current_eyebrow==2 || current_eyebrow==6 || current_eyebrow==10 || current_eyebrow==14 || current_eyebrow==18 || current_eyebrow==22) {
                    avatar.setEyebrow(10);
                }else if(current_eyebrow==3 || current_eyebrow==7 || current_eyebrow==11 || current_eyebrow==15 || current_eyebrow==19 || current_eyebrow==23) {
                    avatar.setEyebrow(11);
                }else if(current_eyebrow==4 || current_eyebrow==8 || current_eyebrow==12 || current_eyebrow==16 || current_eyebrow==20 || current_eyebrow==24) {
                    avatar.setEyebrow(12);
                }
                avatar.setLip(11);
            }else if(stut==3){//confused
                if(current_eyebrow==1 || current_eyebrow==5 || current_eyebrow==9 || current_eyebrow==13 || current_eyebrow==17 || current_eyebrow==21){
                    avatar.setEyebrow(5);
                }else if(current_eyebrow==2 || current_eyebrow==6 || current_eyebrow==10 || current_eyebrow==14 || current_eyebrow==18 || current_eyebrow==22) {
                    avatar.setEyebrow(6);
                }else if(current_eyebrow==3 || current_eyebrow==7 || current_eyebrow==11 || current_eyebrow==15 || current_eyebrow==19 || current_eyebrow==23) {
                    avatar.setEyebrow(7);
                }else if(current_eyebrow==4 || current_eyebrow==8 || current_eyebrow==12 || current_eyebrow==16 || current_eyebrow==20 || current_eyebrow==24) {
                    avatar.setEyebrow(8);
                }
                avatar.setLip(10);
            }else if(stut==4){//angry
                if(current_eyebrow==1 || current_eyebrow==5 || current_eyebrow==9 || current_eyebrow==13 || current_eyebrow==17 || current_eyebrow==21){
                    avatar.setEyebrow(21);
                }else if(current_eyebrow==2 || current_eyebrow==6 || current_eyebrow==10 || current_eyebrow==14 || current_eyebrow==18 || current_eyebrow==22) {
                    avatar.setEyebrow(22);
                }else if(current_eyebrow==3 || current_eyebrow==7 || current_eyebrow==11 || current_eyebrow==15 || current_eyebrow==19 || current_eyebrow==23) {
                    avatar.setEyebrow(23);
                }else if(current_eyebrow==4 || current_eyebrow==8 || current_eyebrow==12 || current_eyebrow==16 || current_eyebrow==20 || current_eyebrow==24) {
                    avatar.setEyebrow(24);
                }
                avatar.setLip(2);
            }else if(stut==5){//sad
                if(current_eyebrow==1 || current_eyebrow==5 || current_eyebrow==9 || current_eyebrow==13 || current_eyebrow==17 || current_eyebrow==21){
                    avatar.setEyebrow(9);
                }else if(current_eyebrow==2 || current_eyebrow==6 || current_eyebrow==10 || current_eyebrow==14 || current_eyebrow==18 || current_eyebrow==22) {
                    avatar.setEyebrow(10);
                }else if(current_eyebrow==3 || current_eyebrow==7 || current_eyebrow==11 || current_eyebrow==15 || current_eyebrow==19 || current_eyebrow==23) {
                    avatar.setEyebrow(11);
                }else if(current_eyebrow==4 || current_eyebrow==8 || current_eyebrow==12 || current_eyebrow==16 || current_eyebrow==20 || current_eyebrow==24) {
                    avatar.setEyebrow(12);
                }
                avatar.setLip(3);
            }else{
                avatar.setEyebrow(defult_eyebrow);
                avatar.setLip(defult_mouth);
            }
        }else{
            if(stut==2){//laugh
                avatar.setEyebrow(defult_eyebrow);
                avatar.setLip(6);
            }else if(stut==3){//confused
                if(current_eyebrow==1 || current_eyebrow==7 || current_eyebrow==13){
                    avatar.setEyebrow(7);
                }else if(current_eyebrow==2 || current_eyebrow==8 || current_eyebrow==14) {
                    avatar.setEyebrow(8);
                }else if(current_eyebrow==3 || current_eyebrow==9 || current_eyebrow==15) {
                    avatar.setEyebrow(9);
                }else if(current_eyebrow==4 || current_eyebrow==10 || current_eyebrow==16) {
                    avatar.setEyebrow(10);
                }else if(current_eyebrow==5 || current_eyebrow==11 || current_eyebrow==17) {
                    avatar.setEyebrow(11);
                }else if(current_eyebrow==6 || current_eyebrow==12 || current_eyebrow==18) {
                    avatar.setEyebrow(12);
                }
                avatar.setLip(14);
            }else if(stut==4){//angry
                if(current_eyebrow==1 || current_eyebrow==7 || current_eyebrow==13){
                    avatar.setEyebrow(7);
                }else if(current_eyebrow==2 || current_eyebrow==8 || current_eyebrow==14) {
                    avatar.setEyebrow(8);
                }else if(current_eyebrow==3 || current_eyebrow==9 || current_eyebrow==15) {
                    avatar.setEyebrow(9);
                }else if(current_eyebrow==4 || current_eyebrow==10 || current_eyebrow==16) {
                    avatar.setEyebrow(10);
                }else if(current_eyebrow==5 || current_eyebrow==11 || current_eyebrow==17) {
                    avatar.setEyebrow(11);
                }else if(current_eyebrow==6 || current_eyebrow==12 || current_eyebrow==18) {
                    avatar.setEyebrow(12);
                }
                avatar.setLip(16);
            }else if(stut==5){//sad
                if(current_eyebrow==1 || current_eyebrow==7 || current_eyebrow==13){
                    avatar.setEyebrow(7);
                }else if(current_eyebrow==2 || current_eyebrow==8 || current_eyebrow==14) {
                    avatar.setEyebrow(8);
                }else if(current_eyebrow==3 || current_eyebrow==9 || current_eyebrow==15) {
                    avatar.setEyebrow(9);
                }else if(current_eyebrow==4 || current_eyebrow==10 || current_eyebrow==16) {
                    avatar.setEyebrow(10);
                }else if(current_eyebrow==5 || current_eyebrow==11 || current_eyebrow==17) {
                    avatar.setEyebrow(11);
                }else if(current_eyebrow==6 || current_eyebrow==12 || current_eyebrow==18) {
                    avatar.setEyebrow(12);
                }
                avatar.setLip(15);
            }else{
                avatar.setEyebrow(defult_eyebrow);
                avatar.setLip(defult_mouth);
            }
        }
        avatar.setAvatar_opt(this);
    }

    //function to hide avatar anim
    private void hideAvatarAnim(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                result_anim.clearAnimation();
                result_anim.setVisibility(View.GONE);
            }
        }, 2000);
    }
    private void unLockAvatarAnim(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isAlowAvatarAnim=true;
            }
        }, 2000);
    }
    //emoji
    private void set_emoji(){
        switch (emj_current_stutus_int){
            case 2:
                emj_current_stutus.setImageResource(R.drawable.emj_laugh);
                break;
            case 3:
                emj_current_stutus.setImageResource(R.drawable.emj_confused);
                break;
            case 4:
                emj_current_stutus.setImageResource(R.drawable.emj_angry);
                break;
            case 5:
                emj_current_stutus.setImageResource(R.drawable.emj_sad);
                break;
            default:
                emj_current_stutus.setImageResource(R.drawable.emj_pocker);
                break;
        }
    }

    private void changeAvatarOfOpp(int stuts){

    }

    //keyboard functions
    private void keyboardHideOrShow(){
        if(isKeyboardShow){
            isKeyboardShow=false;
            ll_keyboard_chat.setVisibility(View.GONE);
            ll_option_box_chat.setVisibility(View.VISIBLE);
            lvl_recent_chat.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height*0.92)));
        }else{
            isKeyboardShow=true;
            ll_keyboard_chat.setVisibility(View.VISIBLE);
            ll_option_box_chat.setVisibility(View.GONE);
            lvl_recent_chat.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height*0.52)));
        }
        scrollMyListViewToBottom();
    }
    private void send(String message){

        // meysam - check if other user is online and if current user have hazel to spent...
        //meysam - check if char count is below 300 and get 1 hazel from user...
        if(message.length() > 300)
        {
            Utility.displayToast(ChatActivity.this,"تعداد حروف بیش از حد مجاز!",Toast.LENGTH_SHORT);
            return;
        }
        if(ltvas_status.getText().equals("آفلاین"))
        {
            Utility.displayToast(ChatActivity.this,"کاربر دیگر آفلاین هست!",Toast.LENGTH_SHORT);
            return;
        }
        if(!adWatched)
        {
            if(session.getCurrentUser().getHazel() - spentedHazel < 0)
            {
                Utility.displayToast(ChatActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);
                return;
            }
            else
            {
                spentedHazel +=Utility.CHAT_MESSAGE_COST;
                session.changeFinalHazel(-1 * Utility.CHAT_MESSAGE_COST);
                // meysam - change hazel text...
                ltvas_hazelAmount.setText(new Integer(session.getCurrentUser().getHazel() - spentedHazel).toString());
                //animation for decrease hazel
                imv_hazel_amount.startAnimation(showUp);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imv_hazel_amount.clearAnimation();
                    }
                }, 500);
                //////////////////////////////////
            }
        }
        //////////////////////////////////////////////////
        txt_typing.setText("");
        MessageModel mm=new MessageModel(getApplicationContext());
        mm.setSend(true);
        if(message.equals("##pQ1m##") || message.equals("##fs1n##") ||message.equals("##0e1v##") ||message.equals("##0s1A##")){
            mm.setTitle(message);
        }else{
            mm.setTitle(message.substring(1));
        }
        mm.setDescription("");
        //add message to list
        recentList.add(mm);
        fillList(recentList,true);
        //

        //meysam - send message for server...
        ChatService.cc.sendMessage(opponentUserName, message);

        scrollMyListViewToBottom();
    }
    private void Receive(String message){
        MessageModel mm=new MessageModel(getApplicationContext());
        mm.setSend(false);
        mm.setDescription("");
        int recievedMessageStutus=0;
        //depend on contaion of message
        if(message.equals("##0s1A##")){
            result_anim.setImageResource(R.drawable.icon_like);
            result_anim.startAnimation(wavy);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(message);
            hideAvatarAnim();
        }else if(message.equals("##0e1v##")){
            result_anim.setImageResource(R.drawable.icon_heart);
            result_anim.startAnimation(showUp);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(message);
            hideAvatarAnim();
        }else if(message.equals("##fs1n##")){
            result_anim.setImageResource(R.drawable.icon_kiss);
            result_anim.startAnimation(showUp);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(message);
            hideAvatarAnim();
        }else if(message.equals("##pQ1m##")){
            result_anim.setImageResource(R.drawable.icon_hate);
            result_anim.startAnimation(wavy);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(message);
            hideAvatarAnim();
        }else{
            setAvatarDependReceivedMessage(Integer.parseInt(message.substring(0,1)));
            mm.setTitle(message.substring(1));
        }

        //add message to list
        recentList.add(mm);
        fillList(recentList,false);
        //
        scrollMyListViewToBottom();
    }
    private String convertActiveEmoji(String message){
        //depend on contaion of message
        if(message.equals("##0s1A##")){
            return "لایک";
        }else if(message.equals("##0e1v##")){
            return "قلب";
        }else if(message.equals("##fs1n##")){
            return "بوس";
        }else if(message.equals("##pQ1m##")){
            return "خوشم نیومد";
        }else{
            return message;
        }
    }

    private void ReceiveChatModel(ChatModel chat){
        MessageModel mm=new MessageModel(getApplicationContext());
        mm.setSend(false);
        mm.setDescription("");
        int recievedMessageStutus=0;
        //depend on contaion of message
        if(chat.getText().equals("##0s1A##")){
            result_anim.setImageResource(R.drawable.icon_like);
            result_anim.startAnimation(wavy);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(chat.getText());
            hideAvatarAnim();
        }else if(chat.getText().equals("##0e1v##")){
            result_anim.setImageResource(R.drawable.icon_heart);
            result_anim.startAnimation(showUp);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(chat.getText());
            hideAvatarAnim();
        }else if(chat.getText().equals("##fs1n##")){
            result_anim.setImageResource(R.drawable.icon_kiss);
            result_anim.startAnimation(showUp);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(chat.getText());
            hideAvatarAnim();
        }else if(chat.getText().equals("##pQ1m##")){
            result_anim.setImageResource(R.drawable.icon_hate);
            result_anim.startAnimation(wavy);
            result_anim.setVisibility(View.VISIBLE);
            mm.setTitle(chat.getText());
            hideAvatarAnim();
        }else{
            setAvatarDependReceivedMessage(Integer.parseInt(chat.getText().substring(0,1)));
            mm.setTitle(chat.getText().substring(1));
        }

        //add message to list
        recentList.add(mm);
        fillList(recentList,false);
        //
        scrollMyListViewToBottom();
    }

    private void fillList(ArrayList<MessageModel> messages,boolean isSend) {
        //make list
        CAC=new CustomAdapterList_chat(this, new ArrayList<Object>(messages), (isSend?RequestRespondModel.TAG_INDEX_CHAT_SEND:RequestRespondModel.TAG_INDEX_CHAT_RECEIVE));
        lvl_recent_chat.setAdapter(CAC);
        lvl_recent_chat.invalidateViews();
    }

    private void fillOutlineList(ArrayList<String> messages) {
        //make list
        CACD=new CustomAdapterList_chatDetail(this, new ArrayList<Object>(messages), null);
        lvl_recentNews_chat.setAdapter(CACD);
        lvl_recentNews_chat.invalidateViews();
    }
    //go down to listview
    private void scrollMyListViewToBottom() {
        lvl_recent_chat.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lvl_recent_chat.setSelection(lvl_recent_chat.getCount() - 1);
            }
        });
    }
    @Override
    public void update(Observable observable, Object o) {

    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
            if(intent.getStringExtra("loading") != null)
            {
                if(intent.getStringExtra("loading").equals("true"))
                {
                    DM.show();
                }
            }
            if (intent.getStringExtra("receivedMessage") != null) {

                // meysam - set online or offline status...
                if(ltvas_status.getText().equals("آفلاین"))
                {
                    ltvas_status.setText("آنلاین");
                    iv_status_icon.setImageResource(R.drawable.st_online);

                }

                if(intent.getStringExtra("senderUserName").equals(opponentUserName))
                {
                    Receive(intent.getStringExtra("receivedMessage"));

                }
                else
                {
                    ltvas_last_message.setText(intent.getStringExtra("senderUserName") + " : "+convertActiveEmoji(intent.getStringExtra("receivedMessage").substring(1)));
                    outlinedMessages.add(0,"2"+intent.getStringExtra("senderUserName") + " : "+convertActiveEmoji(intent.getStringExtra("receivedMessage").substring(1)));
                    fillOutlineList(outlinedMessages);
                }

            }
            if (intent.getBooleanExtra("error",false)) {
                Utility.displayToast(ChatActivity.this,"خطای نامشخص رخ داد", Toast.LENGTH_LONG);
                ChatActivity.this.finish();

            }
            if (intent.getBooleanExtra("closed",false)) {
                Utility.displayToast(ChatActivity.this,"کانکشن بسته شد", Toast.LENGTH_LONG);
                ChatActivity.this.finish();

            }
            if (intent.hasExtra("status")) {
                // meysam - set online or offline status...
                if(intent.getBooleanExtra("status",false))
                {
                    ltvas_status.setText("آنلاین");
                    iv_status_icon.setImageResource(R.drawable.st_online);

                }
                else
                {
                    ltvas_status.setText("آفلاین");
                    iv_status_icon.setImageResource(R.drawable.st_offline);
//                    Utility.displayToast(ChatActivity.this,"طرف مقابل از چت خارج شد", Toast.LENGTH_LONG);

                }

            }
            if (intent.getBooleanExtra("change",false)) {
                // meysam - add outline notification to respected place...
                if(intent.hasExtra("onlineFriends"))
                {
                    // meysam - show list of Online friends...
                    ArrayList<String> onlineFriends = intent.getStringArrayListExtra("onlineFriends");
//                    StringBuilder sb = new StringBuilder();
                    for(int i = 0;i<onlineFriends.size();i++)
                    {
//                        sb.append(onlineFriends.get(i));
//                        sb.append(" آنلاین می باشد ");
//                        sb.append("\n");

                        if(i == (onlineFriends.size() -1))
                        {
//                            ltvas_last_message.setText(sb.toString());
                            ltvas_last_message.setText(onlineFriends.get(i) + " آنلاین می باشد ");
                        }

                        outlinedMessages.add(0,"0"+onlineFriends.get(i) + " آنلاین می باشد ");
                        fillOutlineList(outlinedMessages);
                    }
//                    ll_outline_messages..setText(sb.toString());
                }
                if(intent.hasExtra("friendJoined"))
                {
                    // meysam - show joined friend...
                    String joinedFriend = intent.getStringExtra("friendJoined");
                    StringBuilder sb = new StringBuilder();

                    sb.append(joinedFriend);
                    sb.append(" آنلاین می باشد ");
                    sb.append("\n");

                    ltvas_last_message.setText(sb.toString());
                    outlinedMessages.add(0,"0"+sb.toString());
                    fillOutlineList(outlinedMessages);
                }
                if(intent.hasExtra("friendLeaved"))
                {
                    // meysam - show leaved friend...
                    String joinedFriend = intent.getStringExtra("friendLeaved");
                    StringBuilder sb = new StringBuilder();

                    sb.append(joinedFriend);
                    sb.append(" آفلاین شد ");
                    sb.append("\n");

                    ltvas_last_message.setText(sb.toString());
                    outlinedMessages.add(0,"1"+sb.toString());
                    fillOutlineList(outlinedMessages);

                }

            }
        }

    };

    private void arrangeKeyboard(boolean isDetail){
        String[] keys_s = new String[24];
        String[] keys_b = new String[24];
        if(isDetail){
            isDetailKeyboard=true;
            keys_s=new String[]{"","","","","","","","","","","","","","","","","؟","","","","","","",""};
            keys_b=new String[]{"،","1","2","3","+","-","*","%","&","#","4","5","6","\\",":","!","?","$","7","8","9","0",".","/"};
        }else{
            if(keyboard_current_type==1){
                keys_s=new String[]{"ض","@","،","*","غ","؛","/","\\","چ","ش","ئ","(",")","آ",":","!","؟","گ","ظ","ژ","ء","ذ",".","ؤ"};
                keys_b=new String[]{"ص","ث","ق","ف","ع","ه","خ","ح","ج","س","ی","ب","ل","ا","ت","ن","م","ک","ط","ز","ر","د","پ","و"};
                key_b_types.setText("en");
            }else if(keyboard_current_type==2){
                keys_s=new String[]{"q","1","2","3","+","-","*","%","&","#","4","5","6","\\",":","!","?","v","7","8","9","0",".","/"};
                keys_b=new String[]{"w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","b","n","m"};
                key_b_types.setText("EN");
            }else if(keyboard_current_type==3){
                keys_s=new String[]{"Q","1","2","3","+","-","*","%","&","#","4","5","6","\\",":","!","?","V","7","8","9","0",".","/"};
                keys_b=new String[]{"W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","B","N","M"};
                key_b_types.setText("فا");
            }
            isDetailKeyboard=false;
        }
        int count=0;
        for(int i=1;i<=26;i++){
            if(i==10 || i==20) continue;
            int resID = getResources().getIdentifier("key_b_" + i, "id", getPackageName());
            LuckyTextViewAutoSize btn =findViewById(resID);
            btn.setText(keys_b[count]);
            int resID2 = getResources().getIdentifier("key_s_" + i, "id", getPackageName());
            LuckyTextViewAutoSize btn2 =findViewById(resID2);
            btn2.setText(keys_s[count]);
            count++;
        }

    }
    private class chatUser {

        Integer id;
        String name;
        Bitmap icon;

        public chatUser(int id, String name, Bitmap icon) {
            this.id = id;
            this.name = name;
            this.icon = icon;
        }
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    private void showLoading()
    {
        Intent intent = new Intent("chat_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(ChatActivity.this).sendBroadcast(intent);
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();


        String adZoneId = AdvertismentModel.ChatPageZoneId;

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(ChatActivity.this,adZoneId , options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(ChatActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(ChatActivity.this).playCountinuseRandomMusic();
            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) ChatActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(ChatActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(ChatActivity.this).playCountinuseRandomMusic();
            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(ChatActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(ChatActivity.this).playCountinuseRandomMusic();
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(ChatActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(ChatActivity.this).playCountinuseRandomMusic();

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

                new SoundModel(ChatActivity.this).playCountinuseRandomMusic();

                if(completed)
                {

                    adWatched = true;

                }
                else
                {
                    Utility.displayToast(ChatActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
//
        //meysam - update server hazel will be done in home page...
//        if(spentedHazel != 0)
//        {
//
//        }
//        else
//        {
        if(isKeyboardShow)
        {
            keyboardHideOrShow();
        }
        else
        {
            super.onBackPressed();
        }
//        }
    }

    @Override
    public void onDestroy() {
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        if(ChatService.cc != null)
        {
            ChatService.cc.setOpponentUserName(null);
            ChatService.cc.setContext(ChatService.SCS);
        }

        super.onDestroy();
    }

    private void setUsername(TextView main_name_a, String name) {
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
}

