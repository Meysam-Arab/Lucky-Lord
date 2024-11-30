package ir.fardan7eghlim.luckylord.views.home;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import at.grabner.circleprogress.CircleProgressView;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.HomeController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.controllers.UserFriendController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextViewAutoSize;
import ir.fardan7eghlim.luckylord.services.ChatService;
import ir.fardan7eghlim.luckylord.services.DbService;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_cup;
import ir.fardan7eghlim.luckylord.utils.DialogModel_first;
import ir.fardan7eghlim.luckylord.utils.DialogModel_guide;
import ir.fardan7eghlim.luckylord.utils.DialogModel_level;
import ir.fardan7eghlim.luckylord.utils.DialogModel_luck;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
//import ir.fardan7eghlim.luckylord.utils.InternetModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesActivity;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesIndividualActivity;
import ir.fardan7eghlim.luckylord.views.lattery.LatteryIndexActivity;
import ir.fardan7eghlim.luckylord.views.message.MessageIndexActivity;
import ir.fardan7eghlim.luckylord.views.settings.SettingContactUsActivity;
import ir.fardan7eghlim.luckylord.views.settings.SettingIndexActivity;
import ir.fardan7eghlim.luckylord.views.user.MakeAvatarActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.fardan7eghlim.luckylord.views.user.UserProfileActivity;
import ir.fardan7eghlim.luckylord.views.user.UserRankingListActivity;
import ir.fardan7eghlim.luckylord.views.user.UserRegisterActivity;
import ir.tapsell.sdk.*;
import spencerstudios.com.bungeelib.Bungee;

public class MainActivity extends BaseActivity implements Observer {

    private boolean doubleBackToExitPressedOnce = false;

    private Context context;
    private DialogModel DM;
    private DialogModel_first f_DM;
    private AsyncTask<String, Integer, Boolean> asyncTask;
    private int width;
    private int height;
    private Integer UnreadMessageCount;
    private Integer UnreadFriendshipRequestCount;
    private Integer Egg;
    private Avatar avatar;
    private boolean isNoInternetDialogShowing = false;
//    private SessionModel session;
    private boolean adForEgg;
    private boolean adForRandomReward;
    private boolean adWatched;
    private int eggReward;
    private HomeController hc;

    private boolean isRandomRewardShowed;

//    private Thread wt;

    private static String oneSignalPlayerId;

    private boolean inCreating;
    private boolean returnedFromAd;
    private boolean callingAd;

    private Integer randomRewardAmount;
    private Boolean isRewardDialogUp;

    private boolean isFirstTimeConnecting;
    private Integer retryFirstTimeConnectingCount = 0;
    private ArrayList<String> requestedType = new ArrayList<>();

    private int isLoadingShowing;

    LuckyTextViewAutoSize luckNumber_m;
    LuckyTextViewAutoSize hazzelNumber_m;

    AdvertismentModel mTopAdvertisment;
    AdvertismentModel mBottomAdvertisment;

    private LuckyTextViewAutoSize ltvasLevel;


    UserController uc;

    private Handler exitHandler;

    private static Handler animationHandler;

    private CircleProgressView mCircleView;

    private Boolean newUser;
    private Boolean updatedUser;

    private UserFriendController ufc;

    private DialogModel_level dml;
    private DialogModel_cup dmc;

    private Boolean isEmergencyMessageShowed;

    Animator anm_market;
    Animator anm_ad;
    Animator anm_hazel;
    Animator anm_luck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isRandomRewardShowed = false;
        isEmergencyMessageShowed = false;

        newUser = false;
        updatedUser = false;

//        wt =  new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // DO your work here
//                // get the data
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Utility.displayToast(getApplicationContext(),"ذخیره پایگاه شروع شد",Toast.LENGTH_SHORT);
//                    }
//                });
//
//                DatabaseHandler db=new DatabaseHandler(getApplicationContext());
//                db.fillTable();
//            }
//        });



        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch( audio.getRingerMode() ){
            case AudioManager.RINGER_MODE_NORMAL:
//                session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
                break;
            case AudioManager.RINGER_MODE_SILENT:
                //meysam - make music mute
                session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //meysam - make music mute
                session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                break;
            default:
//                session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
                break;
        }

        callingAd = false;

        mTopAdvertisment = null;
        mBottomAdvertisment = null;

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        ufc = new UserFriendController(getApplicationContext());
        ufc.addObserver(this);


        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            System.exit(0);
        }

        inCreating = true;

//        if(!callingAd)
//        {
            adForEgg = false;
        adForRandomReward = false;
            adWatched = false;
            eggReward = 0;
            returnedFromAd = false;
//        }

        randomRewardAmount = 0;
        isRewardDialogUp = false;

        context = this;
        DM=new DialogModel(MainActivity.this);
        f_DM=new DialogModel_first(MainActivity.this);
        isLoadingShowing = 0;

//        f_DM.show();
//        isLoadingShowing++;


//        AdvertismentModel.adMainCountAndDateInitialization(getApplicationContext(), AdvertismentModel.DefaultAdMainAllowedCount);


//        int x = "Visitor_fb7c5f59fbbc0520#G6".hashCode();

        hc = new HomeController(getApplicationContext());
        hc.addObserver(this);


        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("main_activity_broadcast"));

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysam‌BaseBroadcastReceiver,
//                new IntentFilter("check_internet_broadcast"));
//
//        new InternetModel(this).execute();

//        session = new SessionModel(getApplicationContext());

//        session.saveItem(SessionModel.KEY_AD_REWARD,AdvertismentModel.AD_REWARD);


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

        if(session.getStringItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT) != null)
        {
            UnreadMessageCount = new Integer(session.getStringItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT));
            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
            {
                UnreadMessageCount += new DatabaseHandler(getApplicationContext()).getAllChats().size();
            }
            setCountUNread(UnreadMessageCount.toString());
        }
        else
            UnreadMessageCount = -1;

        if(session.getStringItem( SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT) != null)
        {
            UnreadFriendshipRequestCount = new Integer(session.getStringItem( SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT));
            setFriendshipRequestCountUnread(UnreadFriendshipRequestCount.toString());
        }
        else
            UnreadFriendshipRequestCount = -1;


        if(!session.isLoggedIn())
        {
//            DM.dismiss();
//            f_DM.dismiss();
            f_DM.show();
//            isLoadingShowing++;
            //go to register

            hc.getVersion();
//            Intent i = new Intent(MainActivity.this,FirstPageActivity.class);
//            MainActivity.this.startActivity(i);
//            finish();
        }
        else
        {
            isFirstTimeConnecting = true;

            if(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_HAZEL) != 0 ||
                    session.getIntegerItem(SessionModel.KEY_QUESTION_USED_LUCK) != 0)
            {

                if(isLoadingShowing <= 0)
                {

                    DM.show();
                }
                isLoadingShowing+=1;
                uc.decrease(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_HAZEL),session.getIntegerItem(SessionModel.KEY_QUESTION_USED_LUCK));
                requestedType.add("decrease");
            }



            if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 ||
                    session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
            {

                if(isLoadingShowing <= 0)
                {

                    DM.show();
                }
                isLoadingShowing+=1;
                uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                requestedType.add("change");
            }

            if(!session.hasItem(SessionModel.KEY_ONE_SIGNAL_PLAYER_ID))
            {

                OneSignal.addSubscriptionObserver(new OSSubscriptionObserver() {
                    @Override
                    public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
                        if (!stateChanges.getFrom().getSubscribed() &&
                                stateChanges.getTo().getSubscribed()) {

                            // get player ID
                            String userId = stateChanges.getTo().getUserId();

                            if (userId != null)
                            {
                                // meysam - store id to server
                                oneSignalPlayerId = userId;
                                if(isLoadingShowing <= 0)
                                {
                                    DM.show();
                                }
                                isLoadingShowing+=1;
                                hc.oneSiganlPlayerId(userId);
                                requestedType.add("oneSiganlPlayerId");

                            }
                        }

                    }
                });
            }


            Egg = 1;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            if(session.getStringItem( SessionModel.KEY_EGG) != null)
            {
                if(session.getStringItem( SessionModel.KEY_EGG).equals(dateFormat.format(date)))
                    Egg = 0;
            }
            else
            {
                // meysam - uncomment
                session.saveItem(SessionModel.KEY_EGG, dateFormat.format(date));

                String email = session.getCurrentUser().getEmail();
                if(email != null)
                    OneSignal.syncHashedEmail(email);
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;

            arangeGraphic_start();

            //buttons
            ImageView shop= (ImageView) findViewById(R.id.b_store);
            shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent i = new Intent(MainActivity.this,StoreActivity.class);
//                    MainActivity.this.startActivity(i);
                    startActivity(new Intent(MainActivity.this,StoreActivity.class));
                    Bungee.slideUp(MainActivity.this);
                }
            });
//            //animations
//            anm_market = Flubber.with().listener(flubberMarketListener)
//                    .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
//                    .repeatCount(0)                              // Repeat once
//                    .duration(1500)  // Last for 1000 milliseconds(1 second)
//                    .delay(2000)
//                    .createFor(shop);
//            anm_market.start();// Apply it to the view


            //squirrel
            ImageView squirrel= (ImageView) findViewById(R.id.squirrel_m);
            squirrel.setImageResource(R.drawable.c_squirrel_anim_a);
            AnimationDrawable anim = (AnimationDrawable) squirrel.getDrawable();

            android.view.ViewGroup.LayoutParams layoutParams = squirrel.getLayoutParams();
            layoutParams.height = (int) (height*0.15);
            squirrel.setLayoutParams(layoutParams);

            anim.start();

            squirrel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    new DialogModel_cup(MainActivity.this).show("کاپ شماره یک",1);
//                    new DialogModel_level(MainActivity.this).show(10,11);
                }
            });


            //Tearsure box
            ImageView Tearsure= (ImageView) findViewById(R.id.Tearsure_m);
            Tearsure.setImageResource(R.drawable.c_treasure_anim_a);
            AnimationDrawable anim4 = (AnimationDrawable) Tearsure.getDrawable();

            android.view.ViewGroup.LayoutParams  layoutParams2 = Tearsure.getLayoutParams();
            layoutParams2.height = (int) (height*0.03);
            Tearsure.setLayoutParams(layoutParams2);

            anim4.start();

            //luckyHazzel
            ImageView luckyHazzel= (ImageView) findViewById(R.id.LuckyHazzel_m);
            luckyHazzel.setImageResource(R.drawable.c_hazzel_anim_a);
            AnimationDrawable anim2 = (AnimationDrawable) luckyHazzel.getDrawable();

            android.view.ViewGroup.LayoutParams  layoutParams3 = luckyHazzel.getLayoutParams();
            layoutParams3.height = (int) (height*0.06);
            luckyHazzel.setLayoutParams(layoutParams3);

            anim2.start();
            checkIfAnimationDone(luckyHazzel,anim2);

            //mainBoard
            setUsername();

            luckNumber_m= (LuckyTextViewAutoSize) findViewById(R.id.luckNumberMain_m);
            luckNumber_m.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
            hazzelNumber_m= (LuckyTextViewAutoSize) findViewById(R.id.hazzelNumberMain_m);
            hazzelNumber_m.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));

//            ImageView iv_luck_amount = findViewById(R.id.luckPicMain_m);
//            ImageView iv_hazel_amount = findViewById(R.id.hazzelPicMain_m);
//
//            anm_luck =  Flubber.with()
//                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
//                    .repeatCount(1000000)                              // Repeat once
//                    .duration(8000)  // Last for 1000 milliseconds(1 second)
//                    .delay(0)
////                .force((float) 0.3)
//                    .createFor(iv_luck_amount);
//            anm_luck.start();
//
//            anm_hazel = Flubber.with()
//                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
//                    .repeatCount(1000000)                              // Repeat once
//                    .duration(8000)  // Last for 1000 milliseconds(1 second)
//                    .delay(2500)
////                .force((float) 0.3)
//                    .createFor(iv_hazel_amount);
//            anm_hazel.start();

            setLevel();

            if(isLoadingShowing <= 0)
            {

                f_DM.show();
            }
            isLoadingShowing+=1;
            hc.index(Egg);
            requestedType.add("index");

            // meysam - uncomment
            Egg = 0;
            FrameLayout main_board=findViewById(R.id.main_board);
            ImageView main_board_detail=findViewById(R.id.main_board_detail);
            main_board_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(session.getCurrentUser().getUserName() == null){
                        DialogPopUpModel.show(context,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                            //go to register
                                            Intent i = new Intent(MainActivity.this,UserRegisterActivity.class);
                                            MainActivity.this.startActivity(i);
//                                            finish();
                                        }else{
                                            //do nothing
//                                finish();
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
                    else {

                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                            DM.show();
                            Intent i = new Intent(MainActivity.this,UserProfileActivity.class);
                            MainActivity.this.startActivity(i);
                            if(DM != null)
                                DM.hide();
                        }
                        else
                        {
                            DialogPopUpModel.show(MainActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                        }



                    }

                }
            });



            //watch advertisment movies....meysam
            ImageView iv_watchAd = (ImageView) findViewById(R.id.b_movie);
            iv_watchAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AdvertismentModel.haveMainAdRemained(getApplicationContext()))
                    {
                        if(isLoadingShowing <= 0)
                        {
                            DM.show();
                        }
                        isLoadingShowing += 1;
                        WatchAd();
                    }
                    else
                    {
                        DialogPopUpModel.show(MainActivity.this,getString(R.string.msg_NoAdRemained), getString(R.string.btn_OK), null, false, true);
                    }

                }
            });

//            anm_ad = Flubber.with().listener(flubberAdListener)
//                    .animation(Flubber.AnimationPreset.SWING) // Slide up animation
//                    .repeatCount(0)                              // Repeat once
//                    .duration(1500)  // Last for 1000 milliseconds(1 second)
//                    .delay(4000)
//                    .force((float) 0.5)
//                    .createFor(iv_watchAd);
//            anm_ad.start();// Apply it to the view
//            //////////////////////////////////////////


            //for Tapsell ...
            Tapsell.initialize(context, AdvertismentModel.tapsellAppKey);
            /////////////////////////////////////////


        }

        FrameLayout result_avt= (FrameLayout) findViewById(R.id.result_avt);
        result_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getCurrentUser().getUserName() == null){
                    DialogPopUpModel.show(context,"باید ثبت نام کنی!!","باشه","بیخیال", true, true);
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
                                        //go to register
                                        Intent i = new Intent(MainActivity.this,UserRegisterActivity.class);
                                        MainActivity.this.startActivity(i);
//                                            finish();
                                    }else{
                                        //do nothing
//                                finish();
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
                else {

                    DM.show();
//                    Intent i = new Intent(MainActivity.this,MakeAvatarActivity.class);
//                    MainActivity.this.startActivity(i);
                    startActivity(new Intent(MainActivity.this,MakeAvatarActivity.class));
                    Bungee.slideDown(MainActivity.this);
                    if(DM != null)
                        DM.hide();

                }
            }
        });




//        Utility.displayToast(this, "نسخه آزمایشی", Toast.LENGTH_LONG) ;

    }

    private void setUsername() {
        String name=session.getCurrentUser().getUserName() == null?session.getCurrentUser().getVisitorUserNameShow():session.getCurrentUser().getUserName();
        if(name.contains(" ")){
            int i=name.indexOf(" ");
            name=name.replaceAll(" ","");
            name=name.substring(0,i)+"\n"+name.substring(i);
        }else if(name.contains("_")) {
            int i = name.indexOf("_");
            name = name.substring(0, i) + "\n" + name.substring(i);
        }else if(name.contains("-")) {
            int i = name.indexOf("-");
            name = name.substring(0, i) + "\n" + name.substring(i);
        }else if(name.length()>8){
            double i=((double) name.length())/((double) 2);
            name=name.substring(0,(int) i)+"\n"+name.substring(((int) i));
        }
        Typeface face;
        if (!Utility.isProbablyArabic(name)) {
            face=Typeface.createFromAsset(getAssets(), "fonts/RAVIE.TTF");
        }else{
            face=Typeface.createFromAsset(getAssets(), "fonts/Khandevane.ttf");
        }
        TextView main_name_a= (TextView) findViewById(R.id.main_name_a);
        main_name_a.setTypeface(face);
        main_name_a.setText(name);
        TextView main_name_b= (TextView) findViewById(R.id.main_name_b);
        main_name_b.setTypeface(face);
        main_name_b.setText(name);
//        if(name.length()>16){
//            main_name_a.setTextSize(R.dimen.s4_font_size);
//            main_name_b.setTextSize(R.dimen.s4_font_size);
//        }
    }

    private void setLevel() {
        ltvasLevel = findViewById(R.id.ltvas_level);
        int lvl = Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()));
//        if(lvl == 0)
//            ltvasLevel.setText(String.valueOf(lvl));
//        else
            ltvasLevel.setText(Utility.enToFa(String.valueOf(lvl)));
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        double countToNextLevel=Utility.calculateScorePercent(MainActivity.this);
        mCircleView.setUnitVisible(false);
//        mCircleView.setValueAnimated((int) countToNextLevel);
        mCircleView.setValueAnimated(0,(int) countToNextLevel,1500);
//        mCircleView.setAutoTextSize(true);
//        mCircleView.setTextMode(TextMode.TEXT);
//        mCircleView.setText(Utility.enToFa(Utility.calculateLevel(session.getCurrentUser().getLevelScore()).toString()));
        mCircleView.setTextSize(1);
        FrameLayout fl_gotoProfile=findViewById(R.id.fl_gotoProfile);
        fl_gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(session.getCurrentUser().getUserName() == null){
                    DialogPopUpModel.show(context,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                        //go to register
                                        Intent i = new Intent(MainActivity.this,UserRegisterActivity.class);
                                        MainActivity.this.startActivity(i);
//                                            finish();
                                    }else{
                                        //do nothing
//                                finish();
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
                else {

                    if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                            session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {

                        DM.show();
                        Intent i = new Intent(MainActivity.this,UserProfileActivity.class);
                        MainActivity.this.startActivity(i);
                        if(DM != null)
                            DM.hide();
                    }
                    else
                    {
                        DialogPopUpModel.show(MainActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                    }


                }
            }
        });
//        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.b_game);
//        mCircleView.setClippingBitmap(icon);

    }

    @Override
    public void onPause() {
        super.onPause();
        if(!callingAd)
        {
            hc.setCntx(null);
            hc.deleteObserver(this);
            hc = null;

            newUser = false;
            updatedUser = false;
        }

        arangeGraphic_exit();
    }
    @Override
    public void onResume() {

        System.gc();


        if(returnedFromAd)
        {
            hc = new HomeController(getApplicationContext());
            hc.addObserver(this);
            if(isLoadingShowing <= 0)
            {

                DM.show();
            }
            isLoadingShowing += 1;
            hc.egg(adWatched);
            returnedFromAd = false;
        }
        else
        {
            if(DM != null)
            {
                if(isLoadingShowing <= 0)
                {
                    isLoadingShowing = 0;
                    DM.hide();
                    if(f_DM != null)
                    {
                        if(session.isLoggedIn())
                            f_DM.hide();
                    }

                }
            }




//            if(callingAd)
//            {
//                callingAd = false;
//                adForEgg = false;
//                adWatched = false;
//            }
//            else
//            {
                adForEgg = false;
            adForRandomReward = false;
                adWatched = false;
                eggReward = 0;
//            randomRewardAmount = 0;
            isRewardDialogUp = false;
                // meysam - hide egg if exist
                closeEgg();
//            }

            ///////////////////////////////////////
        }



        if(!inCreating)
        {
            hc = new HomeController(getApplicationContext());
            hc.addObserver(this);

            uc = new UserController(getApplicationContext());
            uc.addObserver(this);

            ufc = new UserFriendController(getApplicationContext());
            ufc.addObserver(this);

            if(DM != null)
                DM.hide();

            if(f_DM != null)
                f_DM.hide();

            isLoadingShowing = 0;

            DM=new DialogModel(MainActivity.this);

//            new InternetModel(this).execute();
            if (!session.getBoolianItem(SessionModel.KEY_TOTURIAL,false)) {


                // Do first run stuff here then set 'firstrun' as false
                // using the following line to edit/commit prefs
                session.saveItem(SessionModel.KEY_TOTURIAL,true);
//                tutorial();
                first_aid();



            }

            if(session.getStringItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT) != null)
            {
                UnreadMessageCount = new Integer(session.getStringItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT));
                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                {
                    UnreadMessageCount += new DatabaseHandler(getApplicationContext()).getAllChats().size();
                }
                setCountUNread(UnreadMessageCount.toString());
            }
            else
                UnreadMessageCount = -1;

            if(session.getStringItem( SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT) != null)
            {
                UnreadFriendshipRequestCount = new Integer(session.getStringItem( SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT));
                setFriendshipRequestCountUnread(UnreadFriendshipRequestCount.toString());
            }
            else
                UnreadFriendshipRequestCount = -1;

            Egg = 1;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            if(session.getStringItem( SessionModel.KEY_EGG) != null)
            {
                if(session.getStringItem( SessionModel.KEY_EGG).equals(dateFormat.format(date)))
                    Egg = 0;
            }
            else
            {
                // meysam - uncomment
                session.saveItem(SessionModel.KEY_EGG, dateFormat.format(date));
            }

            arangeGraphic_start();

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


            if(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_HAZEL) != 0 ||
                    session.getIntegerItem(SessionModel.KEY_QUESTION_USED_LUCK) != 0)
            {

                if(isLoadingShowing <= 0)
                {
                    DM.show();
                }
                isLoadingShowing += 1;
                uc.decrease(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_HAZEL),session.getIntegerItem(SessionModel.KEY_QUESTION_USED_LUCK));

            }
            if((session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 ||
                    session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0) && !requestedType.contains("change"))
            {

                if(isLoadingShowing <= 0)
                {
                    DM.show();
                }
                isLoadingShowing += 1;
                uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                requestedType.add("change");
            }

            setUsername();

            luckNumber_m= (LuckyTextViewAutoSize) findViewById(R.id.luckNumberMain_m);
            luckNumber_m.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
            hazzelNumber_m= (LuckyTextViewAutoSize) findViewById(R.id.hazzelNumberMain_m);
            hazzelNumber_m.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));

            //meysam - set user level for show....
            setLevel();
            ////////////////////////////////////////////

            // meysam - every x minutes
            int checkServerInterval = session.getIntegerItem(SessionModel.KEY_SERVER_CHECK_INTERVAL);//meysam - minutes
            if(Utility.isTimeSpent(session.getStringItem(SessionModel.KEY_LAST_CHECK_SERVER_TIME),checkServerInterval))
            {
                if(isLoadingShowing <= 0)
                {
                    DM.show();
                }
                isLoadingShowing += 2;
                hc.index(Egg);
                hc.unread();

            }
//            else
//            {
//
//            }

//            if(!isRandomRewardShowed)
//                showRandomRewardDialog();
//            else
//                isRandomRewardShowed = false;

        }
        else
        {
            inCreating = false;
            returnedFromAd = false;

            if(!session.hasItem(SessionModel.KEY_LAST_UPDATED_VERSION))
            {
                //meysam - it's first time program is running...
                newUser = true;
                session.saveItem(SessionModel.KEY_LAST_UPDATED_VERSION,"0");

                session.saveItem(SessionModel.KEY_LEVEL_SCORE,"0");
                session.saveItem(SessionModel.KEY_CUPS,"000000000");
//                session.saveItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW,0);
                session.saveItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE,"");
                session.saveItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE,new Integer(0));

                //meysam - added in 13961203
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                Date date = new Date();
                session.saveItem(SessionModel.KEY_FREE_MATCH_TIME,new Date().getTime()+"");
                session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT,0);

            }
            //meysam - check if it's an update or normal run...
            if(!session.getStringItem(SessionModel.KEY_LAST_UPDATED_VERSION).equals(Utility.getVersionString(getApplicationContext())))
            {
                //meysam - it's update run ...
                updatedUser = true;
                session.saveItem(SessionModel.KEY_LAST_UPDATED_VERSION,Utility.getVersionString(getApplicationContext()));
//                if(Utility.getVersion(getApplicationContext()) <= 3.00)
//                {
                    DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                    db.deleteFarsiWords();
                    session.saveItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS,0);
//                }

                String oldVersion = session.getStringItem(SessionModel.KEY_LAST_UPDATED_VERSION);
                if(oldVersion.equals("2") || oldVersion.equals("2.0.0") || oldVersion.equals("2.0.1"))
                {
                    session.saveItem(SessionModel.KEY_LEVEL_SCORE,"0");
                    session.saveItem(SessionModel.KEY_CUPS,"000000000");
//                    session.saveItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW,0);
                    session.saveItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE,"");
                    session.saveItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE,new Integer(0));
                    session.saveItem(SessionModel.KEY_ALLOW_CHAT,"1");
                }
            }
            else
            {
                //meysam - it's normal run ... do nothing
                newUser = false;
                updatedUser = false;
            }

            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) == 0 ||
                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) == 1)
            {
                session.saveItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS,0);
                Intent msgIntent = new Intent(this, DbService.class);
                startService(msgIntent);
            }

//            //meysam - added in 13961115
//            //run if service is not running...
//
//                //chat service will start if user have been registered...
//                if(session.getCurrentUser().getUserName() != null)
//                {
//                    Intent msgIntent = new Intent(this, ChatService.class);
//                    startService(msgIntent);
//                }
        }
        //meysam - added in 13961115
        //run if service is not running...

        //chat service will start if user have been registered...
        if(session.getCurrentUser().getUserName() != null && session.getCurrentUser().getAllowChat().equals("1"))
        {
            Intent msgIntent = new Intent(this, ChatService.class);
            startService(msgIntent);
        }

        if(session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
            ufc.sync();

//        if(!isRandomRewardShowed)
//            showRandomRewardDialog();
//        else
//            isRandomRewardShowed = false;

        //Tearsure box
        ImageView Tearsure= (ImageView) findViewById(R.id.Tearsure_m);
        Tearsure.setImageResource(R.drawable.c_treasure_anim_a);
        AnimationDrawable anim4 = (AnimationDrawable) Tearsure.getDrawable();

        android.view.ViewGroup.LayoutParams layoutParams = Tearsure.getLayoutParams();
        int p20_height= (int) (height*0.1);
        layoutParams.height = p20_height;
        Tearsure.setLayoutParams(layoutParams);

        anim4.start();

        super.onResume();
    }

    private void first_aid() {
        final DialogModel_guide dm_guide=new DialogModel_guide(context,true,0);
        dm_guide.setText("سلام.به بازی لاکی لرد خوش آمدید");
        dm_guide.setText2("آموزش چند ثانیه ای بازی");
        dm_guide.setBTN01("باشه",0,this);
        dm_guide.show();

    }

    private void arangeGraphic_start() {
        //main ad footer
        FrameLayout main_ad_footer= (FrameLayout) findViewById(R.id.main_ad_footer);
        Anims.move(main_ad_footer,0.0f,0.0f,height+100.0f,0.0f,1500,0);
        //main board
//        LinearLayout right_btns_m= (LinearLayout) findViewById(R.id.right_btns_m);
//        Anims.move(right_btns_m,width+50.0f,0.0f,0.0f,0.0f,1500,0);
//        LinearLayout left_btns_m= (LinearLayout) findViewById(R.id.left_btns_m);
//        Anims.move(left_btns_m,-100.0f,0.0f,0.0f,0.0f,1500,0);

//        new DialogModel_luck(this).show(true,false,85);
    }
    private void arangeGraphic_exit() {
        //main ad footer
        FrameLayout main_ad_footer= (FrameLayout) findViewById(R.id.main_ad_footer);
        Anims.move(main_ad_footer,0.0f,0.0f,0.0f,height+100.0f,1500,0);
        //main board
//        LinearLayout right_btns_m= (LinearLayout) findViewById(R.id.right_btns_m);
//        Anims.move(right_btns_m,0.0f,width+50.0f,0.0f,0.0f,2500,0);
//        LinearLayout left_btns_m= (LinearLayout) findViewById(R.id.left_btns_m);
//        Anims.move(left_btns_m,0.0f,-100.0f,0.0f,0.0f,2500,0);
    }
    //fetchUserRanking
    public void fetchUserRanking(View v){
        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
//            Intent i = new Intent(MainActivity.this,UserRankingListActivity.class);
//            MainActivity.this.startActivity(i);
            startActivity(new Intent(MainActivity.this,UserRankingListActivity.class));
            Bungee.slideLeft(MainActivity.this);
        }
        else
        {
            DialogPopUpModel.show(MainActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
        }

//        finish();//meysam - 13960625
    }
    public void fetchUserFriend(View v){
        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
            Intent i = new Intent(MainActivity.this,UserRankingListActivity.class);
            i.putExtra("friend", "1");
            MainActivity.this.startActivity(i);
        }
        else
        {
            DialogPopUpModel.show(MainActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
        }
    }
    //goToSettingIndex
    public void goToSettingIndex(View v){
//        Intent i = new Intent(MainActivity.this,SettingIndexActivity.class);
//        MainActivity.this.startActivity(i);
//        finish();//meysam - 13960625
        startActivity(new Intent(MainActivity.this,SettingIndexActivity.class));
        Bungee.slideUp(MainActivity.this);
    }
    //goToHazelIndex
    public void goToHazelIndexOne(View v){
//        Intent i = new Intent(MainActivity.this,HazelCategoriesActivity.class);
//        MainActivity.this.startActivity(i);
        startActivity(new Intent(MainActivity.this, HazelCategoriesIndividualActivity.class));
        Bungee.slideLeft(MainActivity.this);
//        finish();//meysam - 13960625
    }
    public void goToHazelIndex(View v){
//        Intent i = new Intent(MainActivity.this,HazelCategoriesOneActivity.class);
//        MainActivity.this.startActivity(i);
        startActivity(new Intent(MainActivity.this,HazelCategoriesActivity.class));
        Bungee.slideRight(MainActivity.this);
//        finish();//meysam - 13960625
    }
    //goToMessageIndex
    public void goToMessageIndex(View v){

        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
           Intent i = new Intent(MainActivity.this,MessageIndexActivity.class);
            MainActivity.this.startActivity(i);
        }
        else
        {
            DialogPopUpModel.show(MainActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
        }


//        finish();//meysam - 13960625
    }
    //goToLatteryIndex
    public void goToLatteryIndex(View v){
//        Intent i = new Intent(MainActivity.this,LatteryIndexActivity.class);
//        MainActivity.this.startActivity(i);
//        finish();//meysam - 13960625
        startActivity(new Intent(MainActivity.this,LatteryIndexActivity.class));
        Bungee.slideUp(MainActivity.this);
    }

    //check for finishing anim2
    public static void checkIfAnimationDone(final ImageView luckyHazzel, AnimationDrawable anim){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        animationHandler = new Handler();
        animationHandler.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(luckyHazzel,a);
                } else{
                    luckyHazzel.setImageResource(R.drawable.c_hazzel_anim_b);
                    AnimationDrawable anim3 = (AnimationDrawable) luckyHazzel.getDrawable();
                    anim3.start();
                }
            }
        }, timeBetweenChecks);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, final Object arg) {
//        if(f_DM != null)
//            f_DM.hide();
        if(!isFirstTimeConnecting)
        {
            if(isLoadingShowing > 0)
                isLoadingShowing -= 1;
            if(isLoadingShowing <= 0)
            {
                isLoadingShowing = 0;
                if(DM != null)
                    DM.hide();
                if(f_DM != null)
                    f_DM.hide();
            }
        }
        if((isFirstTimeConnecting &&
                arg != null &&
                arg instanceof Boolean == false &&
                arg instanceof Integer == false) || retryFirstTimeConnectingCount > 3)
        {
            isFirstTimeConnecting = false;
            retryFirstTimeConnectingCount = 0;
            requestedType.clear();
            if(isLoadingShowing > 0)
                isLoadingShowing -= 1;
            if(isLoadingShowing <= 0)
            {
                isLoadingShowing = 0;
                if(DM != null)
                    DM.hide();
                if(f_DM != null)
                    f_DM.hide();
            }
        }
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
//                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);

                    if(isFirstTimeConnecting)
                    {
                        retryFirstTimeConnectingCount ++;
                        if(requestedType.contains("index"))
                        {
                            hc.index(Egg);
                        }
                        if(requestedType.contains("decrease"))
                        {
                            uc.decrease(session.getIntegerItem(SessionModel.KEY_QUESTION_USED_HAZEL),session.getIntegerItem(SessionModel.KEY_QUESTION_USED_LUCK));

                        }
                        if(requestedType.contains("change"))
                        {
                            uc.change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));

                        }
                        if(requestedType.contains("oneSiganlPlayerId"))
                        {
                            hc.oneSiganlPlayerId(oneSignalPlayerId);
                        }
                    }
                    if(!isNoInternetDialogShowing && !isFirstTimeConnecting)
                    {
                        isNoInternetDialogShowing = true;

                        DialogPopUpModel.show(context,getString(R.string.error_no_connection),getString(R.string.lbl_retry),getString(R.string.lbl_exit),true,false);

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
//                                        DialogPopUpModel.dialog.setCanceledOnTouchOutside(true);

                                        Thread.currentThread().interrupt();//meysam 13960525
                                        isNoInternetDialogShowing = false;
                                        if(DialogPopUpModel.dialog_result==1){
                                            //again

                                            finish();
                                            startActivity(getIntent());

                                        }else if(DialogPopUpModel.dialog_result==2){
                                            //exit
                                            finish();
                                            System.exit(2);
                                        }
                                        else
                                        {
                                            //meysam - do nothing

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
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0)== RequestRespondModel.TAG_GET_VERSION_HOME)
                {

                    PackageInfo pInfo = null;
                    Float version = null;
                    try {
                        pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (pInfo != null) {
                        String[] tmpVersion = pInfo.versionName.split("[.]");
                        version = new Float(tmpVersion[0] + "." + tmpVersion[1] + tmpVersion[2]);
                    }
                    Float current_ver = version;

                    version = new Float(((ArrayList) arg).get(1).toString());
                    Float new_ver = version;

                    Float tmp_new_version = new Float(Utility.faToEn(new_ver.toString().substring(0,new_ver.toString().indexOf(".")+2)));
                    Float tmp_current_ver = new Float(Utility.faToEn(current_ver.toString().substring(0,current_ver.toString().indexOf(".")+2)));
                    if (tmp_new_version > tmp_current_ver) {
                        Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                        i.putExtra("link", ((ArrayList) arg).get(2).toString());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                    else if (new_ver > current_ver)
                    {
                        if (current_ver < 1) {
                            Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                            i.putExtra("link", ((ArrayList) arg).get(2).toString());
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                        else
                        {

                            String link = ((ArrayList) arg).get(2).toString();
                            if (link.equals("")) {
                                Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                                i.putExtra("link", ((ArrayList) arg).get(2).toString());
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                //meysam
                                Utility.displayToast(getApplicationContext(), getResources().getString(R.string.msg_OldVersion), Toast.LENGTH_SHORT);
                                Intent i = new Intent(MainActivity.this,FirstPageActivity.class);
                                MainActivity.this.startActivity(i);
                                Utility.finishActivity(MainActivity.this);
                            }
                        }

                    }
                    else
                    {
                        Intent i = new Intent(MainActivity.this,FirstPageActivity.class);
                        MainActivity.this.startActivity(i);
                        Utility.finishActivity(MainActivity.this);
                    }

                    final String message = ((ArrayList) arg).get(3).toString();
                    if (!message.equals("null")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(MainActivity.this,message,getString(R.string.btn_OK),null,false,true);
                            }
                        });
                    }
            }
            else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_INDEX_HOME)) {


                    if (!isRandomRewardShowed)
                        showRandomRewardDialog();
                    else
                        isRandomRewardShowed = false;


                    //meysam - uncomment
                    Egg = 0;
                    PackageInfo pInfo = null;
                    Float version = null;
                    try {
                        pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (pInfo != null) {
                        String[] tmpVersion = pInfo.versionName.split("[.]");
                        version = new Float(tmpVersion[0] + "." + tmpVersion[1] + tmpVersion[2]);
                    }
                    Float current_ver = version;

                    version = new Float(((ArrayList) arg).get(1).toString());
                    Float new_ver = version;

                    Float tmp_new_version = new Float(Utility.faToEn(new_ver.toString().substring(0,new_ver.toString().indexOf(".")+2)));
                    Float tmp_current_ver = new Float(Utility.faToEn(current_ver.toString().substring(0,current_ver.toString().indexOf(".")+2)));

                    if (tmp_new_version > tmp_current_ver) {
//                    if(Math.floor(new_ver) > Math.floor(current_ver))
//                    {

                        Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                        i.putExtra("link", ((ArrayList) arg).get(2).toString());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MainActivity.this.startActivity(i);
                        finish();
                    } else if (new_ver > current_ver) {
                        if (current_ver < 1) {
                            Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                            i.putExtra("link", ((ArrayList) arg).get(2).toString());
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            MainActivity.this.startActivity(i);
                            finish();
                        } else {

                            String link = ((ArrayList) arg).get(2).toString();
                            if (link.equals("")) {
                                Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                                i.putExtra("link", ((ArrayList) arg).get(2).toString());
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                MainActivity.this.startActivity(i);
                                finish();
                            } else {
                                Utility.displayToast(getApplicationContext(), getResources().getString(R.string.msg_OldVersion), Toast.LENGTH_SHORT);
                                ImageView tree=findViewById(R.id.tree_right_side);
                                tree.setImageResource(R.drawable.newbg_03_new_version_ready);
                                tree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://luckylord.ir/getAPK"));
                                        startActivity(browserIntent);
                                    }
                                });
                            }
                        }

                    } else {
                        //nothing
                    }
                    if (!((ArrayList) arg).get(3).toString().equals("null")) {
                        // there is some egg rewards !!! show to user in dancer hazel #amir
                        //we will not add it to session. just add to session that today user requested his/her lucky egg chance!!
                        final String temp = (String) ((ArrayList) arg).get(3);
                        eggReward = Integer.valueOf(temp);
                        final ImageView LuckyHazzel_m = (ImageView) findViewById(R.id.LuckyHazzel_m);
                        LuckyHazzel_m.setVisibility(View.VISIBLE);
                        LuckyHazzel_m.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //meysam - show watch ad dialog

                                if (!DialogPopUpModel.isUp()) {
                                    DialogPopUpModel.show(context, getString(R.string.title_ad_for_egg_dialog), getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                while (DialogPopUpModel.isUp()) {
                                                    Thread.sleep(500);
                                                }
                                                if (!DialogPopUpModel.isUp()) {
                                                    Thread.currentThread().interrupt();//meysam 13960525
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //yes was pressed - show ad
                                                                adForEgg = true;
                                                                adForRandomReward = false;
                                                                randomRewardAmount = 0;
                                                                isRewardDialogUp = false;
                                                                DM.show();
                                                                isLoadingShowing += 1;
                                                                WatchAd();
                                                            } else {
                                                                //no was pressed
                                                                DM.show();
                                                                isLoadingShowing += 1;
                                                                hc.egg(adWatched);
                                                            }
                                                        }
                                                    });


                                                    DialogPopUpModel.hide();
                                                }
                                            } catch (InterruptedException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    //////////////////////////////////////////
                                }


                            }
                        });
                    } else {
                        closeEgg();
                    }

                    session.saveItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT, ((ArrayList) arg).get(4).toString());

                    UnreadMessageCount = new Integer(session.getStringItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT));
                    if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                            session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                    {
                        UnreadMessageCount += new DatabaseHandler(getApplicationContext()).getAllChats().size();
                    }
                    setCountUNread(UnreadMessageCount.toString());


//                    setCountUNread(((ArrayList) arg).get(4).toString());

                    ImageView tearsure_m = (ImageView) findViewById(R.id.Tearsure_m);
                    if (!((ArrayList) arg).get(5).toString().equals("null")) {
                        //main_off_hazzel

                        tearsure_m.setVisibility(View.VISIBLE);
                        //meysam - top advertisment
                        mTopAdvertisment = (AdvertismentModel) ((ArrayList) arg).get(5);
//                        final ArrayList<String> temp= (ArrayList<String>) ((ArrayList) arg).get(4);
                        tearsure_m.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog d = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                d.getWindow().setBackgroundDrawableResource(R.color.glassblack);
                                d.setContentView(R.layout.main_off_dialog);

                                ImageView sun_effect = (ImageView) d.findViewById(R.id.sun_effect_mod);
                                sun_effect.startAnimation(
                                        AnimationUtils.loadAnimation(context, R.anim.rotate_and_scale_indefinitely));

                                d.show();

                                TextView main_off_description = (TextView) d.findViewById(R.id.main_off_description);
                                main_off_description.setText(mTopAdvertisment.getDescription());
                                TextView main_off_title = (TextView) d.findViewById(R.id.main_off_title);
                                main_off_title.setText(mTopAdvertisment.getTitle());

                                Button row_first_btn = (Button) d.findViewById(R.id.row_first_btn);
                                row_first_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        d.hide();

                                    }
                                });
                                Button row_second_btn = (Button) d.findViewById(R.id.row_second_btn);
                                row_second_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //top_advertisment.getLink() is LINK ????
                                        // meysam
                                        //kharid az off
                                        Intent intents = new Intent(context, StoreActivity.class);
//                                        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                                | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);

                                        d.hide();
                                        startActivity(intents);
//                                        finish();
                                    }
                                });

                            }
                        });
                    } else {
                        tearsure_m.setVisibility(View.GONE);
                    }
                    if (!((ArrayList) arg).get(6).toString().equals("null")) {
                        //meysam - buttom advertisment
                        mBottomAdvertisment = (AdvertismentModel) ((ArrayList) arg).get(6);
                        // show advertisment in respected location
                        if (!mBottomAdvertisment.getTitle().equals("null")) {
                            LinearLayout bottom_ad_main_text = (LinearLayout) findViewById(R.id.bottom_ad_main_text);
                            LinearLayout bottom_ad_main_pic = (LinearLayout) findViewById(R.id.bottom_ad_main_pic);
                            if (mBottomAdvertisment.getTitle().equals("0")) {
                                //image ad
                                bottom_ad_main_text.setVisibility(View.GONE);
                                bottom_ad_main_pic.setVisibility(View.VISIBLE);
                                if (!mBottomAdvertisment.getDescription().equals("null")) {
                                    ImageView bottom_ad_main_picture = (ImageView) findViewById(R.id.bottom_ad_main_picture);
                                    Bitmap mad = Utility.getBitmapImage(mBottomAdvertisment.getDescription());
                                    bottom_ad_main_picture.setImageBitmap(mad);

                                    //meysam - clear memory - cant...error in vew animation
//                                    mad.recycle();
//                                    mad = null;
                                }
                            } else {
                                //text ad
                                bottom_ad_main_text.setVisibility(View.VISIBLE);
                                bottom_ad_main_pic.setVisibility(View.GONE);

                                TextView bottom_ad_main_title = (TextView) findViewById(R.id.bottom_ad_main_title);
                                bottom_ad_main_title.setText(mBottomAdvertisment.getTitle());
                                if (!mBottomAdvertisment.getDescription().equals("null")) {
                                    TextView bottom_ad_main_description = (TextView) findViewById(R.id.bottom_ad_main_description);
                                    bottom_ad_main_description.setText(mBottomAdvertisment.getDescription());
                                }
                            }
                            if (!mBottomAdvertisment.getLink().equals("null")) {
                                FrameLayout bottom_ad_main = (FrameLayout) findViewById(R.id.main_ad_footer);
                                bottom_ad_main.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Utility.goToUrl(MainActivity.this, mBottomAdvertisment.getLink());
                                    }
                                });
                            }
                        }
                        session.saveItem(SessionModel.KEY_HAZEL, ((ArrayList) arg).get(7).toString());
                        session.saveItem(SessionModel.KEY_LUCK, ((ArrayList) arg).get(8).toString());

                        if (!AdvertismentModel.isTodayAdDateCorrect(getApplicationContext(), SessionModel.KEY_AD_FILM_MAIN_SPENT_TIME))
                            AdvertismentModel.adMainCountAndDateInitialization(getApplicationContext(), Integer.parseInt(((ArrayList) arg).get(9).toString()));
                        else if (session.getAllowedFilmCount(SessionModel.KEY_AD_FILM_MAIN_ALLOWED_COUNT) != Integer.parseInt(((ArrayList) arg).get(9).toString())) {
                            AdvertismentModel.setAllowedAdCount(getApplicationContext(), Integer.parseInt(((ArrayList) arg).get(9).toString()), SessionModel.KEY_AD_FILM_MAIN_ALLOWED_COUNT);
                        } else {
                            //meysam - do nothing
                        }

                        //update hazel and luck in ui
                        luckNumber_m.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));
//                        luckNumber_m.resize();
                        hazzelNumber_m.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));

                        setLevel();

                    }

                    session.saveItem(SessionModel.KEY_SERVER_CHECK_INTERVAL, Integer.parseInt(((ArrayList) arg).get(10).toString()));
                    session.saveItem(SessionModel.KEY_LAST_CHECK_SERVER_TIME, new Date().getTime() + "");

                    session.saveItem(SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT, ((ArrayList) arg).get(11).toString());
                    setFriendshipRequestCountUnread(((ArrayList) arg).get(11).toString());


                    if (!session.getBoolianItem(SessionModel.KEY_TOTURIAL, false)) {

//                        //fill database of Farsi words
//                        session.saveItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS,0);
//                        if(wt.getState() == Thread.State.NEW)
//                            wt.start();

                        // Do first run stuff here then set 'firstrun' as false
                        // using the following line to edit/commit prefs
                        session.saveItem(SessionModel.KEY_TOTURIAL, true);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpFourVerticalModel.show(MainActivity.this, getString(R.string.msg_ShowTutorial), getString(R.string.btn_Yes), getString(R.string.btn_No), null, null, true, false);
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
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            first_aid();
                                                        }
                                                    });
                                                } else {
                                                    //meysam - do nothing
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            startFlubberAnimations();
                                                        }
                                                    });
                                                }
                                                DialogPopUpFourVerticalModel.hide();
                                            }
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        });

                    }
                    else
                    {
                        startFlubberAnimations();
                    }

                    //meysam - check level and cups ....
                    String tempScore = ((ArrayList) arg).get(12).toString();

                    if (Utility.calculateLevel(Integer.valueOf(tempScore)) > Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore()))) {
                        if (!newUser && !updatedUser) {
                            //meysam - we have increase in level ....
//                        session.saveItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW,session.getIntegerItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW)+1);
                            int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()), Integer.valueOf(tempScore));
                            dml = new DialogModel_level(MainActivity.this);
                            dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())), Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())) + levelDiffrence);

                        }


                    }
                    session.setUserLevelScore(tempScore); //level
                    if (Utility.shouldLevelChange(getApplicationContext())) {
                        if (!newUser && !updatedUser) {
                            //meysam - increase level for show...
//                        session.saveItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW,session.getIntegerItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW)+1);
                            //meysam - level should change...
                            // meysam - show earned level animation...
                            int levelDiffrence = Utility.getLevelDiffrence(Integer.valueOf(session.getCurrentUser().getLevelScore()), Integer.valueOf(session.getCurrentUser().getLevelScore()) + session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                            dml = new DialogModel_level(MainActivity.this);
                            dml.show(Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())), Utility.calculateLevel(Integer.valueOf(session.getCurrentUser().getLevelScore())) + levelDiffrence);

                        }
                        ///////////////////////////////////////////////
                        //meysam - send new level to server ...
                        uc.changeLevel(session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE));
                        ///////////////////////////////////////
                    }

                    String newCups = ((ArrayList) arg).get(13).toString();//cups
                    final ArrayList<Integer> changedCupsIndexes = Utility.getChangedCups(getApplicationContext(), newCups);
                    if (changedCupsIndexes.size() > 0) {
                        if (!newUser && !updatedUser) {
//                            Boolean showedCupAnimation = false;
                            // meysam - show earned cup animation...
                            for (int i = 0; i < changedCupsIndexes.size(); i++) {
                                final int finalI = i;
//                                if(showedCupAnimation)
//                                {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 5s = 5000ms
                                        dmc = new DialogModel_cup(MainActivity.this);
                                        dmc.show(Utility.getCupTitleByCode(getApplicationContext(), changedCupsIndexes.get(finalI)), changedCupsIndexes.get(finalI));

                                    }
                                }, Utility.CUP_DIALOG_TIME * i);
//                                }
//                                else
//                                {
//                                    showedCupAnimation = true;
//                                    new DialogModel_cup(MainActivity.this).show(Utility.getCupTitleByCode(getApplicationContext(),changedCupsIndexes.get(finalI)),changedCupsIndexes.get(finalI));
//                                }

                            }
                            //////////////////////////////////////////////
                        }

                    }
                    session.addNewCups(changedCupsIndexes);
                    //////////////////////////////////////
                    if (session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE) != null && !session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE).equals("")) {
                        // meysam - send sercer update cups to server...
                        String cupsToAdd = session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE);
                        uc.updateCups(cupsToAdd);
                        //////////////////////////////////////////////
                    }
//                    if(session.getIntegerItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW) != 0)
//                    {
//                        // meysam - show earned level animation...
//
//                        ///////////////////////////////////////////////
//                    }

                    setLevel();
                    if(!isEmergencyMessageShowed){
                        final String message = ((ArrayList) arg).get(14).toString();
                        if (!message.equals("null")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogPopUpModel.show(MainActivity.this,message,getString(R.string.btn_OK),null,false,true);
                                }
                            });
                        }
                        isEmergencyMessageShowed = true;
                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNREAD_HOME))
                {
//                    SessionModel session = new SessionModel(getApplicationContext());
                    session.saveItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT, ((ArrayList) arg).get(1).toString());
                    UnreadMessageCount = new Integer(session.getStringItem( SessionModel.KEY_UNREAD_MESSAGE_COUNT));
                    if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                            session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                    {
                        UnreadMessageCount += new DatabaseHandler(getApplicationContext()).getAllChats().size();
                    }
                    setCountUNread(UnreadMessageCount.toString());
//                    setCountUNread(((ArrayList) arg).get(1).toString());

                    session.saveItem( SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT, ((ArrayList) arg).get(2).toString());
                    setFriendshipRequestCountUnread(((ArrayList) arg).get(2).toString());

                    session.saveItem(SessionModel.KEY_LAST_CHECK_SERVER_TIME,new Date().getTime()+"");
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_EGG_HOME))
                {
                    if(((ArrayList) arg).get(1).toString().equals("true"))
                    {
                        int finalReward = new Integer(eggReward);
                        if(adWatched)
                            finalReward = finalReward*2;
//                        SessionModel session = new SessionModel(getApplicationContext());
//                        session.increaseHazel(String.valueOf(finalReward));
                        Utility.changeCoins(getApplicationContext(),finalReward);
                        final int finalReward1 = finalReward;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reloadHazelAmount();
                                setLevel();
                                disableEgg();
                                openEgg(String.valueOf(finalReward1));
                            }
                        });

                    }
                    else
                    {
                        DialogPopUpModel.show(context,context.getString(R.string.error_weak_connection),context.getString(R.string.btn_OK),null, false,false);
                    }

                }

                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_ADVERTISMENT_HOME))
                {

                    session.increaseHazel(((ArrayList) arg).get(1).toString());
                    if(AdvertismentModel.getRemainedMainAd(getApplicationContext()) != 0)
                        DialogPopUpModel.show(context,((ArrayList) arg).get(1).toString()+" " + getString(R.string.msg_HazelAddedSuccessfully) + " و "+ Utility.enToFa(AdvertismentModel.getRemainedMainAd(getApplicationContext()).toString()) +" "+ getString(R.string.msg_AdCountRemained),getString(R.string.btn_OK),null, false, false);
                    else
                        DialogPopUpModel.show(context,((ArrayList) arg).get(1).toString()+" " + getString(R.string.msg_HazelAddedSuccessfully) + " و "+ getString(R.string.msg_AdLastWatched),getString(R.string.btn_OK),null , false, false);

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER))
                {
                    session.removeItem(SessionModel.KEY_QUESTION_USED_HAZEL);
                    session.removeItem(SessionModel.KEY_QUESTION_USED_LUCK);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reloadHazelAmount();
                            setLevel();
                        }
                    });

                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            new DialogModel_hazel(MainActivity.this).show(false, true, session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL));
                            session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                            session.removeItem(SessionModel.KEY_FINAL_LUCK);
                            reloadHazelAmount();
                            setLevel();
                        }
                    });



                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_PUSH_NOTIFICATION_PLAYER_ID_HOME))
                {
                    session.saveItem( SessionModel.KEY_ONE_SIGNAL_PLAYER_ID, ((ArrayList) arg).get(1).toString());
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
//                        session.saveItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW,0);

                        setLevel();

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
                        String cupsToAdd = session.getStringItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE);
                        String currentCups = session.getCurrentUser().getCups();
                        currentCups  = Utility.updateCupStringByCommaSeparatedIndexes(currentCups,cupsToAdd);
                        session.saveItem(SessionModel.KEY_CUPS,currentCups);
                        session.saveItem(SessionModel.KEY_CUPS_FOR_SERVER_UPDATE,"");
//                        session.saveItem(SessionModel.KEY_LEVEL_FOR_LOCAL_SHOW,"");

                    }
                    else
                    {
                        //meysam - error in updating level in server ... do nothing for now...
                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_SYNC))
                {

                    //meysam - change status of recieced user friend records in sqlite...
                    ArrayList<UserFriendModel> userFriends = (ArrayList<UserFriendModel>) ((ArrayList) arg).get(1);
                    if(userFriends.size() > 0)
                    {
                        UserFriendModel.syncFriendsStatusInDb(userFriends,MainActivity.this.getApplicationContext());

                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        if(db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED).size() == 0)
                            if(session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
                                session.removeItem(SessionModel.KEY_FRIENDSHIP_SYNC);

                        //if Status changed successfully send validate sync request to server
                        ufc.validateSync(userFriends);
                    }

                }


            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE ||
                        Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_TOKEN_INVALID)
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    finish();
                }else {
                    Utility.displayToast(getApplicationContext(),new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            }
            else
            {
                Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection),Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection),Toast.LENGTH_SHORT);
        }
    }

    private void setCountUNread(String s) {
        TextView message_unread_count_main= (TextView) findViewById(R.id.message_unread_count_main);
        if(s.equals("0") || s.contains("-")){
            message_unread_count_main.setVisibility(View.GONE);
        }else{
            message_unread_count_main.setText(Utility.enToFa(s));
            message_unread_count_main.setVisibility(View.VISIBLE);
        }
    }

    private void setFriendshipRequestCountUnread(String s) {
        TextView message_unread_count_main= (TextView) findViewById(R.id.friend_request_count_main);
        if(s.equals("0") || s.contains("-")){
            message_unread_count_main.setVisibility(View.GONE);
        }else{
            message_unread_count_main.setText(Utility.enToFa(s));
            message_unread_count_main.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public void onBackPressed() {

        if(!session.hasItem(SessionModel.KEY_FIRST_TIME_CLOSING))
        {
            // meysam - show dialog to feedback...
            DialogPopUpModel.show(this,"بازی در حال پیشرفته، لطفا پیشنهاد، انتقاد و نظرت رو برا بهتر کردن بازی برامون بفرست. ممنون",getString(R.string.btn_OK),getString(R.string.btn_No),false,false);
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(DialogPopUpModel.dialog_result==1){
                                        //yes was pressed - go to feedback
                                        Intent i = new Intent(MainActivity.this,SettingContactUsActivity.class);
                                        MainActivity.this.startActivity(i);
                                    }
                                }
                            });
                            DialogPopUpModel.hide();
                            session.saveItem(SessionModel.KEY_FIRST_TIME_CLOSING,true);
                            session.saveItem(SessionModel.KEY_NUMBER_TIME_CLOSING,1);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
            ///////////////////////////////////////////////

        }
        else if(session.getIntegerItem(SessionModel.KEY_NUMBER_TIME_CLOSING) == 5)
        {
            if(Utility.isPackageInstalled(getApplicationContext(),"com.farsitel.bazaar"))
            {
                // meysam - show dialog to feedback...
                DialogPopUpModel.show(this,"از بازی خوشت اومد؟ با امتیاز پنج ستاره ما رو خوشحال کن.",getString(R.string.btn_OK),getString(R.string.btn_No),false,false);
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(DialogPopUpModel.dialog_result==1){
                                            //yes was pressed - go to bazar rate
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    rate();
                                                }
                                            });
                                        }
                                    }
                                });
                                DialogPopUpModel.hide();
                                session.saveItem(SessionModel.KEY_NUMBER_TIME_CLOSING,session.getIntegerItem(SessionModel.KEY_NUMBER_TIME_CLOSING)+1);
                            }
                        }
                        catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                ///////////////////////////////////////////////
            }
            else
            {
                session.saveItem(SessionModel.KEY_NUMBER_TIME_CLOSING,session.getIntegerItem(SessionModel.KEY_NUMBER_TIME_CLOSING)+1);
            }
        }
        else
        {
            if (doubleBackToExitPressedOnce) {
                if(exitHandler != null)
                {
                    exitHandler.removeCallbacksAndMessages(null);
                    exitHandler = null;
                }
                session.saveItem(SessionModel.KEY_NUMBER_TIME_CLOSING,session.getIntegerItem(SessionModel.KEY_NUMBER_TIME_CLOSING)+1);
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Utility.displayToast(getApplicationContext(),getString(R.string.msg_PressBackAgain),Toast.LENGTH_SHORT);
            //////////////////////////////////////////////////////////

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }


    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();

        callingAd = true;


        String adZoneId;
        if(adForEgg)
        {

            adZoneId = AdvertismentModel.EggZoneId;
        }
        else if(adForRandomReward)
        {
            adZoneId = AdvertismentModel.RandomRewardZoneId;
        }
        else
        {
            adZoneId = AdvertismentModel.HomePageZoneId;
        }

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(context, adZoneId, options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                isLoadingShowing -= 1;
                if(DM != null)
                    DM.hide();
//                Utility.displayToast(context,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);
                DialogPopUpModel.show(MainActivity.this,context.getString(R.string.error_show_advertisment),context.getString(R.string.btn_OK),null, false, false);

                new SoundModel(context).playCountinuseRandomMusic();
            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {
                isLoadingShowing -= 1;
                if(DM != null)
                    DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
//                if(adForEgg)
//                    showOptions.setBackDisabled(true);
//                else
                    showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) context, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                isLoadingShowing -= 1;
                if(DM != null)
                    DM.hide();
                Utility.displayToast(context,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(context).playCountinuseRandomMusic();
            }

            @Override
            public void onNoNetwork ()
            {
                isLoadingShowing -= 1;
                if(DM != null)
                    DM.hide();
                Utility.displayToast(context,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(context).playCountinuseRandomMusic();
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                isLoadingShowing -= 1;
                if(DM != null)
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
                    // store user reward if ad.isRewardedAd() and completed is true
                    // Meysam: send Reward To server and store it in session .... meysam
//                    Utility.displayToast(context,getApplicationContext().getString(R.string.msg_AdvertismentShowCompleted),Toast.LENGTH_SHORT);

                    //for now dosnt matter
//                session.saveItem(SessionModel.KEY_SEND_AD_REWARD_REQUEST,"true");
                    callingAd = false;

                    if(adForEgg)
                    {

                        returnedFromAd = true;
                        adForEgg = false;
                        adWatched = true;
                        adForRandomReward = false;
                        randomRewardAmount = 0;
                        isRewardDialogUp = false;
                    }
                    else if(adForRandomReward)
                    {
                        returnedFromAd = false;
                        adForEgg = false;
                        adWatched = true;
                        adForRandomReward = false;
                        isRewardDialogUp = false;
                        // meysam - increase hazel and send change to server
                        Utility.changeCoins(getApplicationContext(),randomRewardAmount);
                        session.changeFinalHazel(randomRewardAmount);

                        if(!requestedType.contains("change"))
                        {
                            if(isLoadingShowing <= 0)
                            {

                                DM.show();
                            }
                            isLoadingShowing+=1;
                            uc.change(randomRewardAmount,session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
                            requestedType.add("change");
                        }

                        randomRewardAmount = 0;
                        isRandomRewardShowed = true;

                    }
                    else
                    {

                        //meysam - moved to onResume
//                    openEgg(String.valueOf(eggReward));
                        AdvertismentModel.increaseTodaySpentAd(getApplicationContext(),1, SessionModel.KEY_AD_FILM_MAIN_SPENT_COUNT);

                        if(isLoadingShowing <= 0)
                        {

                            DM.show();
                        }
                        isLoadingShowing+=1;
                        hc.ad();
                    }
                }
                else
                {
                    Utility.displayToast(MainActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                    if(adForEgg)
                    {
                        returnedFromAd = false;
                        adForEgg = false;
                        adWatched = false;
                        callingAd = false;

                        hc.deleteObservers();;
                        hc.egg(adWatched);
                    }
                }


            }
        });
    }


    public void onDestroy() {

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        if(f_DM != null)
        {
            f_DM.dismiss();
            f_DM = null;
        }

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

        new SoundModel(this).stopMusic();
        if(bgSound != null)
        {
            bgSound.setCntx(null);
            bgSound = null;
        }

        if(animationHandler != null)
        {
            animationHandler.removeCallbacksAndMessages(null);
            animationHandler = null;
        }
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);


        Intent msgIntent = new Intent(this, ChatService.class);
        stopService(msgIntent);

        //meysam - added in 13961115
        if(ChatService.cc != null && ChatService.cc.isConnected())
        {
            ChatService.cc.closeWebSocket();
        }

        ChatService.cc = null;
        ChatService.SCS = null;


        if(anm_market != null)
        {
            anm_market.end();
            anm_market.cancel();
            anm_market.removeAllListeners();
        }
        if(anm_ad != null)
        {
            anm_ad.end();
            anm_ad.cancel();
            anm_ad.removeAllListeners();
        }
        if(flubberAdListener != null)
            flubberAdListener = null;
        if(flubberMarketListener != null)
            flubberMarketListener = null;

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

    private void openEgg(final String rewardAmount)
    {
        ImageView LuckyHazzel_m= (ImageView) findViewById(R.id.LuckyHazzel_m);
        LuckyHazzel_m.setImageResource(R.drawable.c_hazzel_anim_c);
        AnimationDrawable anim = (AnimationDrawable) LuckyHazzel_m.getDrawable();
        anim.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int finalReward = new Integer(rewardAmount);

                new DialogModel_luck(MainActivity.this).show(false,true,finalReward);
//                disableEgg();
            }
        }, 1000);
    }
    private void closeEgg()
    {
        final ImageView LuckyHazzel_m= (ImageView) findViewById(R.id.LuckyHazzel_m);
        LuckyHazzel_m.setVisibility(View.GONE);
    }

    private void disableEgg()
    {
        final ImageView LuckyHazzel_m= (ImageView) findViewById(R.id.LuckyHazzel_m);
        LuckyHazzel_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam - do nothing when click on egg - deable to pervent multiple gifts.
            }
        });
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if(intent.hasExtra("loading"))
            {
                String isLoading = intent.getStringExtra("loading");
                if(isLoading.equals("true"))
                {
                    if(isLoadingShowing <= 0)
                    {
                        DM.show();
                    }
                }
            }

            if(intent.hasExtra("play_flubber_animations"))
            {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       startFlubberAnimations();
                   }
               });

            }

            if(intent.hasExtra("logout"))
            {
                DM.dismiss();
                f_DM.dismiss();

                session.logoutUser();
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteAllTablerecords();

                //go to register
                Intent i = new Intent(MainActivity.this,FirstPageActivity.class);
                MainActivity.this.startActivity(i);
                MainActivity.this.finish();
            }

        }
    };

    private void showLoading()
    {
        Intent intent = new Intent("main_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
    }

    private void reloadHazelAmount()
    {
        hazzelNumber_m= (LuckyTextViewAutoSize) findViewById(R.id.hazzelNumberMain_m);
        hazzelNumber_m.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
    }

    private void showRandomRewardDialog()
    {

        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;

        randomRewardAmount = 50 + r.nextInt(200 - 50 + 1);

        if(randomInt < 2 && !isRewardDialogUp)//meysam - must be lower than 2...
        {

            isRewardDialogUp = true;
            DialogPopUpModel.show(context," یه تبلیغ ببین و " + randomRewardAmount + " فندق جایزه بگیر ",getString(R.string.btn_Yes),getString(R.string.btn_No),true,false);
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(DialogPopUpModel.dialog_result==1){
                                        //yes was pressed - show ad
                                        adForEgg = false;
                                        adForRandomReward = true;
                                        DM.show();
                                        isLoadingShowing += 1;
                                        WatchAd();
                                    }
                                }
                            });
                            DialogPopUpModel.hide();
                            isRewardDialogUp = false;
                        }
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
            //////////////////////////////////////////
        }
    }

    public void startFlubberAnimations()
    {
        //animations
        ImageView shop= (ImageView) findViewById(R.id.b_store);
        anm_market = Flubber.with().listener(flubberMarketListener)
                .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1500)  // Last for 1000 milliseconds(1 second)
                .delay(2000)
                .createFor(shop);
        anm_market.start();// Apply it to the view

        ImageView iv_luck_amount = findViewById(R.id.luckPicMain_m);
        ImageView iv_hazel_amount = findViewById(R.id.hazzelPicMain_m);

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

        //watch advertisment movies....meysam
        ImageView iv_watchAd = (ImageView) findViewById(R.id.b_movie);
        anm_ad = Flubber.with().listener(flubberAdListener)
                .animation(Flubber.AnimationPreset.SWING) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1500)  // Last for 1000 milliseconds(1 second)
                .delay(4000)
                .force((float) 0.5)
                .createFor(iv_watchAd);
        anm_ad.start();// Apply it to the view
        //////////////////////////////////////////
    }

    public Animator.AnimatorListener flubberMarketListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ImageView shop= (ImageView) findViewById(R.id.b_store);
            Random rand = new Random();
            int min = 5;
            int max = 15;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_market.removeAllListeners();
            anm_market = Flubber.with().listener(flubberMarketListener)
                    .animation(Flubber.AnimationPreset.MORPH) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
                    .createFor(shop);
            anm_market.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;

    public Animator.AnimatorListener flubberAdListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ImageView movieAd= (ImageView) findViewById(R.id.b_movie);
            Random rand = new Random();
            int min = 3;
            int max = 13;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_ad.removeAllListeners();
            anm_ad = Flubber.with().listener(flubberAdListener)
                    .animation(Flubber.AnimationPreset.SWING) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
                    .force((float) 0.5)
                    .createFor(movieAd);
                    anm_ad.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;
}
