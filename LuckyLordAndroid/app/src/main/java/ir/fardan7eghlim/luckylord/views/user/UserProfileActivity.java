package ir.fardan7eghlim.luckylord.views.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.controllers.UserFriendController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextViewAutoSize;
import ir.fardan7eghlim.luckylord.models.widgets.PieChart;
import ir.fardan7eghlim.luckylord.services.ChatService;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.LuckyLordRecyclerViewAdapter;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesIndividualActivity;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.message.ChatActivity;
import ir.fardan7eghlim.luckylord.views.message.MessageIndexActivity;
import ir.fardan7eghlim.luckylord.views.settings.SettingEditProfileActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

import static android.graphics.Paint.Style.FILL;

public class UserProfileActivity extends BaseActivity implements Observer {
    private DialogModel DM;
    private Avatar avatar;
    private ProgressBar pb_cat_record;
    private LinearLayout user_record_layout_up;
    private FrameLayout user_record_match_chart;
    private UserController uc = null;
    private boolean isHimself=true;
    private UserModel userForShow;
    private LuckyTextViewAutoSize username_up;
    private LuckyButton lb_user_edit;
    private UserFriendController ufc = null;
    private DatabaseHandler db;
    private Integer requestedFriendshipStatus;
    private Bundle extras;
    private LuckyButton closet_open;
    private int cat_i;
    private Boolean friendshipRequested;
//    private Boolean infoCalledOnce;
//    private boolean inCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        inCreate = true;

        username_up = (LuckyTextViewAutoSize) findViewById(R.id.username_up);
        lb_user_edit = (LuckyButton) findViewById(R.id.lb_user_edit);
        db = new DatabaseHandler(UserProfileActivity.this);
        userForShow = new UserModel();
        requestedFriendshipStatus = null;
        friendshipRequested = false;
//        infoCalledOnce = false;

        DM=new DialogModel(UserProfileActivity.this);
        DM.show();
        uc = new UserController(this);
        uc.addObserver(this);

        ufc = new UserFriendController(this);
        ufc.addObserver(this);

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("user_profile_activity_broadcast"));

        extras = getIntent().getExtras();
        isHimself=false;
        if(extras != null)
        {

            userForShow.setUserName(extras.getString("EXTRA_User_Name"));
            if(extras.getString("EXTRA_Avatar") == null)
            {
                // meysam - came from chat notification...
//                if(!infoCalledOnce)
//                {
//                    infoCalledOnce = true;
                    DM.show();
                    uc.info(extras.getString("EXTRA_User_Name"));
//                }

            }
            else
            {
                userForShow.setProfilePicture(extras.getString("EXTRA_Avatar"));

                if(session.getCurrentUser().getUserName()!=null && !session.getCurrentUser().getUserName().equals("null")){
                    if(session.getCurrentUser().getUserName().equals(extras.getString("EXTRA_User_Name"))){
                        isHimself=true;
                    }
                }else{
                    if(session.getCurrentUser().getVisitorUserName().equals(extras.getString("EXTRA_User_Name"))){
                        isHimself=true;
                    }
                }
            }

        }
        else
            isHimself = true;

//       fillAll();

        DM.hide();
        //cat record
        pb_cat_record= (ProgressBar) findViewById(R.id.pb_cat_record);
        user_record_layout_up= (LinearLayout) findViewById(R.id.user_record_layout_up);
        user_record_match_chart=findViewById(R.id.user_record_match_chart);
        final FrameLayout btn_ca_record=findViewById(R.id.btn_ca_record);
        closet_open=findViewById(R.id.closet_open);
        closet_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosetOpen();
                btn_ca_record.setEnabled(false);
                pb_cat_record.setVisibility(View.VISIBLE);
                UserController uc = new UserController(getApplicationContext());
                uc.addObserver(UserProfileActivity.this);
                uc.profile(isHimself?null:extras.getString("EXTRA_User_Name"));
            }
        });
    }
    private void fillAll()
    {
        if(!isHimself)
        {

            //for Tapsell ...
            Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
            /////////////////////////////////////////

            userForShow.setUserName(extras.getString("EXTRA_User_Name"));
            userForShow.setProfilePicture(extras.getString("EXTRA_Avatar"));
            if(!extras.containsKey("EXTRA_Friendship_Status"))
            {
                String usr_nm=extras.getString("EXTRA_User_Name");
//                if(!infoCalledOnce) {
//                    infoCalledOnce = true;
                    DM.show();
                    uc.info(usr_nm);
//                }


                String avtr=extras.getString("EXTRA_Avatar");

                if (avtr == null) {
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
                    avatar = new Avatar(avtr);
                }
                avatar.setAvatar(this);


                if(usr_nm!=null) {
                    if(usr_nm.contains("Visitor_"))
                    {
                        username_up.setText(UserModel.getVisitorHashedName(usr_nm));
                    }
                    else
                    {
                        String temp_username = usr_nm;
                        if(session.getCurrentUser().getUserName() != null && !session.getCurrentUser().getUserName().equals(usr_nm) && !temp_username.contains("Visitor_"))
                        {
                            if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username))
                                temp_username += " (آنلاین) ";
                            else
                                temp_username += " (آفلاین) ";
                        }

                        username_up.setText(temp_username);
                    }
                }else{
                    username_up.setText("مهمان");
                }
            }
            else
            {
//                userForShow.setUserName(extras.getString("EXTRA_User_Name"));//set above
                userForShow.setFriendshipStatus(extras.getInt("EXTRA_Friendship_Status"));
//                if(userForShow.getFriendshipStatus() == -3)
//                    userForShow.setFriendshipStatus(null);
//                userForShow.setProfilePicture(extras.getString("EXTRA_Avatar"));
                userForShow.setId(new BigInteger(String.valueOf(extras.get("EXTRA_User_Id"))));
                userForShow.setLuck(extras.getInt("EXTRA_Luck"));
                userForShow.setHazel(extras.getInt("EXTRA_Hazel"));
                userForShow.setLevelScore(extras.getInt("EXTRA_Level"));
                userForShow.setCups(extras.getString("EXTRA_Cups"));
                userForShow.setFriendsCount(extras.getInt("EXTRA_Friendship_Count"));
                userForShow.setAllowChat(extras.getString("EXTRA_AllowChat"));
                //////////////////////////////////////////////////////////////
                UserModel tmpUser = db.getUserByUserName(userForShow.getUserName());
                if(tmpUser.getUserName() != null)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    Date lastUpdatedDate = null;
                    Date currentDate = new Date();
                    try {
                        lastUpdatedDate = sdf.parse(tmpUser.getUpdateDateTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diff = currentDate.getTime() - lastUpdatedDate.getTime();
//                    long diffSeconds = diff / 1000;
//                    long diffMinutes = diff / (60 * 1000);
                    long diffHours = diff / (60 * 60 * 1000);
                    if(diffHours > 12)
                    {
//                        if(!infoCalledOnce) {
//                            infoCalledOnce = true;
                            DM.show();
                            uc.info(userForShow.getUserName());
//                        }
                    }
                    else
                    {
                        initialize();
                    }
                }
                else
                {
                    initialize();
                }

                ////////////////////////////////////////////////////////////////////


            }

            setChatClickListener(userForShow.getUserName(),userForShow.getProfilePicture(), userForShow.getAllowChat());
        }
        else {
            //arange display
            lb_user_edit.setText("ویرایش اطلاعات");
            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(session.getCurrentUser().getUserName() == null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                                    Intent i = new Intent(UserProfileActivity.this,UserRegisterActivity.class);
                                                    UserProfileActivity.this.startActivity(i);
                                                }else{
                                                    //do nothing
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
                    else
                    {
                        goToEditProfile();
                    }
                }
            });

            // meysam - show and set cups ...
            reloadCupsImages(session.getCurrentUser().getCups());
//            setCupsOnClickListeners();
            ///////////////////////////////////////
            // meysam - show and set level and friend count ...
            setLevel(session.getCurrentUser().getLevelScore());
            setFriendCount(db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED).size());
            ///////////////////////////////////////


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

//            LuckyTextView username_up = (LuckyTextView) findViewById(R.id.username_up);
            String temp_username = session.getCurrentUser().getUserName();
            if(session.getCurrentUser().getUserName() != null && !session.getCurrentUser().getUserName().equals(temp_username) && !temp_username.contains("Visitor_"))
            {
                if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username))
                    temp_username += " (آنلاین) ";
                else
                    temp_username += " (آفلاین) ";
            }
            username_up.setText(temp_username);

            LuckyTextViewAutoSize hazzelNumberMain_up = (LuckyTextViewAutoSize) findViewById(R.id.hazzelNumberMain_up);
            hazzelNumberMain_up.setText(Utility.enToFa(session.getCurrentUser().getHazel().toString()));
            LuckyTextViewAutoSize luckNumberMain_up = (LuckyTextViewAutoSize) findViewById(R.id.luckNumberMain_up);
            luckNumberMain_up.setText(Utility.enToFa(session.getCurrentUser().getLuck().toString()));

            setChatClickListener(session.getCurrentUser().getUserName(),session.getCurrentUser().getProfilePicture(),session.getCurrentUser().getAllowChat());

        }

        setCupsOnClickListeners();
    }

    private void setLevel(Integer levelScore) {
        int level= Utility.calculateLevel(levelScore);
        LuckyTextViewAutoSize levelNumberMain_up=findViewById(R.id.levelNumberMain_up);
        if(level==1){
            levelNumberMain_up.setText("بازیکن جدید");
        }else {
            levelNumberMain_up.setText(level+"");
        }
    }

    private void initialize()
    {
        if (userForShow.getProfilePicture() == null) {
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
            avatar = new Avatar(userForShow.getProfilePicture());
        }
        avatar.setAvatar(this);

        // meysam - show and set cups and level ...
        reloadCupsImages(userForShow.getCups());
        setLevel(userForShow.getLevelScore());
        setFriendCount(userForShow.getFriendsCount());
        ///////////////////////////////////////////////////


        if(userForShow.getUserName()!=null) {
            if(userForShow.getUserName().contains("Visitor_"))
            {
                username_up.setText(UserModel.getVisitorHashedName(userForShow.getUserName()));
            }
            else
            {
                String temp_username = userForShow.getUserName();
                if(session.getCurrentUser().getUserName() != null && !session.getCurrentUser().getUserName().equals(temp_username) && !temp_username.contains("Visitor_"))
                {
                    if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username))
                        temp_username += " (آنلاین) ";
                    else
                        temp_username += " (آفلاین) ";
                }

                username_up.setText(temp_username);
            }
        }else{
            username_up.setText("مهمان");
        }

        // meysam - set text to button wrt friendship status...
        if(userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_ACCEPTED)
        {
            //meysam - user is a friend of current user...
            lb_user_edit.setText(getString(R.string.btn_Block_Friendship));
            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.getCurrentUser().getUserName() == null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                                    Intent i = new Intent(UserProfileActivity.this,UserRegisterActivity.class);
                                                    UserProfileActivity.this.startActivity(i);
                                                }else{
                                                    //do nothing
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
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No),true,true);

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
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            DM.show();
                                                        }
                                                    });
                                                    requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_BLOCKED;
                                                    ufc.changeStatus(userForShow,UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                                                }else{
                                                    //do nothing
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
            });
        }
        else if(userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_BLOCKED)
        {
            //meysam - user is blocked by current user...
            lb_user_edit.setText(getString(R.string.btn_Unblock_Friendship));
            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.getCurrentUser().getUserName() == null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                                    Intent i = new Intent(UserProfileActivity.this,UserRegisterActivity.class);
                                                    UserProfileActivity.this.startActivity(i);
                                                }else{
                                                    //do nothing
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
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No),true,true);

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
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            DM.show();
                                                        }
                                                    });
                                                    requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_DELETED;
                                                    ufc.changeStatus(userForShow, UserFriendModel.USER_FRIEND_STATUS_DELETED);
                                                }else{
                                                    //do nothing
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
            });
        }
        else if(userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_REQUESTED)
        {
            lb_user_edit.setText(getString(R.string.btn_Delete_Request_Friendship));
            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.getCurrentUser().getUserName() == null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                                    Intent i = new Intent(UserProfileActivity.this,UserRegisterActivity.class);
                                                    UserProfileActivity.this.startActivity(i);
                                                }else{
                                                    //do nothing
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
                    else
                    {

                        showRevokeDialog(userForShow);
                    }


                }
            });
        }
        else if(userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_NORMAL)
        {
            lb_user_edit.setText(getString(R.string.btn_Answer_Friendship));
            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.getCurrentUser().getUserName() == null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                                    Intent i = new Intent(UserProfileActivity.this,UserRegisterActivity.class);
                                                    UserProfileActivity.this.startActivity(i);
                                                }else{
                                                    //do nothing
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
                    else
                    {
                        // meysam - show three button dialog for accept or decline or block...
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                    DM.show();
                                showResponseDialog(userForShow);
                            }
                        });
                    }
                }
            });
        }
        else
        {
            lb_user_edit.setText(getString(R.string.btn_Request_Friendship));
            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(session.getCurrentUser().getUserName() == null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this,"باید ثبت نام کنی!!","باشه","بیخیال",true,true);
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
                                                    Intent i = new Intent(UserProfileActivity.this,UserRegisterActivity.class);
                                                    UserProfileActivity.this.startActivity(i);
                                                }else{
                                                    //do nothing
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
                    else
                    {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                DM.show();
//                            }
//                        });
//                        ufc.request(userForShow);
                        showRequestDialog(userForShow);

                    }


                }
            });
        }

        LuckyTextViewAutoSize hazzelNumberMain_up = (LuckyTextViewAutoSize) findViewById(R.id.hazzelNumberMain_up);
        hazzelNumberMain_up.setText(Utility.enToFa(userForShow.getHazel().toString()));
        LuckyTextViewAutoSize luckNumberMain_up = (LuckyTextViewAutoSize) findViewById(R.id.luckNumberMain_up);
        luckNumberMain_up.setText(Utility.enToFa(userForShow.getLuck().toString()));
    }

    private void showRevokeDialog(final UserModel userForShow) {



                DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.msg_yes),getString(R.string.msg_no),true,true);
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
                                    //meysam - show revoke dialog....
                                    runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              DM.show();
                                          }
                                    });

                                    ufc.revoke(userForShow);
                                }else{
                                    //do nothing
                                }
                                DialogPopUpModel.hide();
                            }
                        }
                        catch (InterruptedException e)
                        {
                            // Auto-generated catch block
                            //TODO: meysam - show approperiate message...
                            e.printStackTrace();
                        }
                    }
                }).start();



    }
    private void showRequestDialog(final UserModel userForShow) {


//        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.msg_yes),getString(R.string.msg_no),true,true);
        DialogPopUpFourVerticalModel.show(UserProfileActivity.this,"یا تبلیغ ببین یا "+Utility.FRIENDSHIP_REQUEST_COST+" تا فندق بده ",getString(R.string.btn_SeeAd), Utility.FRIENDSHIP_REQUEST_COST+" تا فندق میدم ",null,getString(R.string.btn_No),true, false);
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
                                    //meysam - show revoke dialog....
                                    showLoading();

                                    friendshipRequested = true;
                                    WatchAd();
                                }
                                else if(DialogPopUpFourVerticalModel.dialog_result==2){
                                    //meysam
                                    //recieve hazel from user if have it
                                    if(Utility.hasEnoughCoin(UserProfileActivity.this,Utility.FRIENDSHIP_REQUEST_COST))
                                    {
                                        friendshipRequested = true;
                                        showLoading();
                                        Utility.changeCoins(getApplicationContext(),-1*Utility.FRIENDSHIP_REQUEST_COST);
                                        uc.change(-1*Utility.FRIENDSHIP_REQUEST_COST,0);

                                    }
                                    else
                                    {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Utility.displayToast(UserProfileActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);
                                            }
                                        });
                                    }

                                }
                                else{
                                    //do nothing
                                }
                                DialogPopUpFourVerticalModel.hide();
                            }
                        }
                        catch (InterruptedException e)
                        {
                            // Auto-generated catch block
                            //TODO: meysam - show approperiate message...
                            e.printStackTrace();
                        }
                    }
                }).start();

    }
    //goToEditProfile
    public void goToEditProfile(){
        Intent i = new Intent(UserProfileActivity.this,SettingEditProfileActivity.class);
        UserProfileActivity.this.startActivity(i);
//        finish();
//        System.gc();
    }
    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
        DM.hide();

        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Utility.finishActivity(UserProfileActivity.this);
                }
            }
            else if (arg instanceof ArrayList)
            {
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_INFO_USER)) {
                    //meysam - 13960628
                    UserModel opponent = (UserModel) ((ArrayList) arg).get(1);
                    //arange display
                    LuckyButton lb_user_edit = (LuckyButton) findViewById(R.id.lb_user_edit);
                    // meysam - ست کردن متن روی دکمه و عملیات روی دکمه با توجه به وضعیت دوستی که در دیتا بیس اگر باشد
                    userForShow.setHazel(opponent.getHazel());
                    userForShow.setLuck(opponent.getLuck());
                    userForShow.setLevelScore(opponent.getLevelScore());
                    userForShow.setCups(opponent.getCups());
                    userForShow.setId(opponent.getId());
                    userForShow.setFriendshipStatus(UserModel.setUserFriendshipStatusWRTDb(userForShow, UserProfileActivity.this));
                    userForShow.setFriendsCount(opponent.getFriendsCount());
                    userForShow.setProfilePicture(opponent.getProfilePicture());
                    userForShow.setAllowChat(opponent.getAllowChat());
                    //////////////////////////////////////////////////////////////
                    if (session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                            session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1) {
                        UserModel tmpUser = db.getUserByUserName(userForShow.getUserName());
                        if (tmpUser.getUserName() != null) {
                            tmpUser.setProfilePicture(userForShow.getProfilePicture());
                            db.editUser(tmpUser, null);
                        }
                    }


                    ////////////////////////////////////////////////////////////////////
                    if (userForShow.getFriendshipStatus() == null) {
                        lb_user_edit.setText(getString(R.string.btn_Request_Friendship));
                        lb_user_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (session.getCurrentUser().getUserName() == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                UserProfileActivity.this.startActivity(i);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
                                                        }
                                                    } catch (InterruptedException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    });

                                } else {

//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            DM.show();
//                                        }
//                                    });
//                                    ufc.request(userForShow);
                                    showRequestDialog(userForShow);
                                }

                            }
                        });
                    }
                    if (userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_ACCEPTED) {
                        lb_user_edit.setText(getString(R.string.btn_Block_Friendship));
                        lb_user_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (session.getCurrentUser().getUserName() == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                UserProfileActivity.this.startActivity(i);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
                                                        }
                                                    } catch (InterruptedException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    });

                                } else {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        DM.show();
                                                                    }
                                                                });
                                                                requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_BLOCKED;
                                                                ufc.changeStatus(userForShow, UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
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


                            }
                        });
                    }
                    if (userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_BLOCKED) {
                        lb_user_edit.setText(getString(R.string.btn_Unblock_Friendship));
                        lb_user_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (session.getCurrentUser().getUserName() == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                UserProfileActivity.this.startActivity(i);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
                                                        }
                                                    } catch (InterruptedException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    });

                                } else {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        DM.show();
                                                                    }
                                                                });
                                                                requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_BLOCKED;
                                                                ufc.changeStatus(userForShow, UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
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


                            }
                        });
                    }
                    if (userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_REQUESTED) {
                        lb_user_edit.setText(getString(R.string.btn_Delete_Request_Friendship));
                        lb_user_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (session.getCurrentUser().getUserName() == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                UserProfileActivity.this.startActivity(i);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
                                                        }
                                                    } catch (InterruptedException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    });

                                } else {

//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            DM.show();
//                                        }
//                                    });
//                                    ufc.revoke(userForShow);
                                    showRevokeDialog(userForShow);
                                }


                            }
                        });
                    }
                    if (userForShow.getFriendshipStatus() == UserFriendModel.USER_FRIEND_STATUS_NORMAL) {
                        lb_user_edit.setText(getString(R.string.btn_Answer_Friendship));
                        lb_user_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (session.getCurrentUser().getUserName() == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        while (DialogPopUpModel.isUp()) {
                                                            Thread.sleep(500);
                                                        }
                                                        if (!DialogPopUpModel.isUp()) {
                                                            Thread.currentThread().interrupt();//meysam 13960525
                                                            if (DialogPopUpModel.dialog_result == 1) {
                                                                //go to register
                                                                Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                UserProfileActivity.this.startActivity(i);
                                                            } else {
                                                                //do nothing
                                                            }
                                                            DialogPopUpModel.hide();
                                                        }
                                                    } catch (InterruptedException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    });

                                } else {

                                    // meysam - show three button dialog for accept or decline or block...
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                        DM.show();
                                            showResponseDialog(userForShow);
                                        }
                                    });
                                }

                            }
                        });
                    }

                    ////////////////////////////////////////////////////////////////////////////////////////

                    setChatClickListener(userForShow.getUserName(),userForShow.getProfilePicture(), userForShow.getAllowChat());


                    LuckyTextViewAutoSize hazzelNumberMain_up = (LuckyTextViewAutoSize) findViewById(R.id.hazzelNumberMain_up);
                    hazzelNumberMain_up.setText(Utility.enToFa(opponent.getHazel().toString()));
                    LuckyTextViewAutoSize luckNumberMain_up = (LuckyTextViewAutoSize) findViewById(R.id.luckNumberMain_up);
                    luckNumberMain_up.setText(Utility.enToFa(opponent.getLuck().toString()));

                    initialize();
                }
                if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_PROFILE_USER)) {
                    final ArrayList<CategoryModel> temp = (ArrayList<CategoryModel>) ((ArrayList) arg).get(1);

                    if (temp.size() < 1) {
                        user_record_layout_up.setVisibility(View.GONE);
                    } else {
                        user_record_layout_up.setVisibility(View.VISIBLE);
                        cat_i = 1;
                        setStaticsOfCats(temp, cat_i);

                        ImageView statis_left = findViewById(R.id.statis_left);
                        statis_left.setVisibility(View.VISIBLE);
                        statis_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cat_i--;
                                if (cat_i == 0) cat_i = 14;
                                setStaticsOfCats(temp, cat_i);
                            }
                        });
                        ImageView statis_right = findViewById(R.id.statis_right);
                        statis_right.setVisibility(View.VISIBLE);
                        statis_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cat_i++;
                                if (cat_i == 15) cat_i = 1;
                                setStaticsOfCats(temp, cat_i);
                            }
                        });

                    }

                    //pie chart for matches
                    ArrayList<String> mmtemp= (ArrayList<String>) ((ArrayList) arg).get(2);
                    PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
                    float[] datas = new float[3];
                    datas[0] = new Float(mmtemp.get(0));
                    datas[1] = new Float(mmtemp.get(1));
                    datas[2] = new Float(mmtemp.get(2));

                    if(datas[0]+datas[1]+datas[2]!=0) {
                        pieChart.setData(datas);

                        String[] labels = new String[3];
                        labels[0] = "برد";
                        labels[1] = "باخت";
                        labels[2] = "مساوی";

                        pieChart.setLabels(labels);
                    }else{
                        datas[0] = 100;
                        pieChart.setData(datas);

                        String[] labels = new String[3];
                        labels[0] = "هیچ";
                        labels[1] = "";
                        labels[2] = "";

                        pieChart.setLabels(labels);
                    }
                    //hide progress bar for loading
                    pb_cat_record.setVisibility(View.GONE);
                }
                else if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_REQUEST))
                {

                    if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {
                        // meysam - add user to requested list in db...
                        userForShow.setFriendshipStatus(UserFriendModel.USER_FRIEND_STATUS_REQUESTED);
                        db.addUser(userForShow, UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED);
                        if(!session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
                            session.saveItem(SessionModel.KEY_FRIENDSHIP_SYNC,true);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this, UserProfileActivity.this.getApplicationContext().getString(R.string.msg_RequestSent), UserProfileActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);
                                lb_user_edit.setText(getString(R.string.btn_Delete_Request_Friendship));
                                lb_user_edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (session.getCurrentUser().getUserName() == null) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                while (DialogPopUpModel.isUp()) {
                                                                    Thread.sleep(500);
                                                                }
                                                                if (!DialogPopUpModel.isUp()) {
                                                                    Thread.currentThread().interrupt();//meysam 13960525
                                                                    if (DialogPopUpModel.dialog_result == 1) {
                                                                        //go to register
                                                                        Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                        UserProfileActivity.this.startActivity(i);
                                                                    } else {
                                                                        //do nothing
                                                                    }
                                                                    DialogPopUpModel.hide();
                                                                }
                                                            } catch (InterruptedException e) {
                                                                // TODO Auto-generated catch block
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }).start();
                                                }
                                            });

                                        } else {
                                            // meysam - call change status for deleting request...
//                                            DM.show();
//                                            ufc.revoke(userForShow);
                                            showRevokeDialog(userForShow);
                                        }

                                    }
                                });
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this, UserProfileActivity.this.getApplicationContext().getString(R.string.msg_RequestNotSent), UserProfileActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                            }
                        });

                    }
                }
                else if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_CHANGE_STATUS))
                {

                    if (Boolean.valueOf(((ArrayList) arg).get(1).toString())) {
                        // meysam - add user to respected list in db...
                        if (requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_ACCEPTED) {
                            userForShow.setFriendshipStatus(UserFriendModel.USER_FRIEND_STATUS_ACCEPTED);
                            db.deleteUserById(userForShow.getId());
                            db.addUser(userForShow, UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);

                            lb_user_edit.setText(getString(R.string.btn_Block_Friendship));
                            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (session.getCurrentUser().getUserName() == null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            while (DialogPopUpModel.isUp()) {
                                                                Thread.sleep(500);
                                                            }
                                                            if (!DialogPopUpModel.isUp()) {
                                                                Thread.currentThread().interrupt();//meysam 13960525
                                                                if (DialogPopUpModel.dialog_result == 1) {
                                                                    //go to register
                                                                    Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                    UserProfileActivity.this.startActivity(i);
                                                                } else {
                                                                    //do nothing
                                                                }
                                                                DialogPopUpModel.hide();
                                                            }
                                                        } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();
                                            }
                                        });

                                    } else {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogPopUpModel.show(UserProfileActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, true);

                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            while (DialogPopUpModel.isUp()) {
                                                                Thread.sleep(500);
                                                            }
                                                            if (!DialogPopUpModel.isUp()) {
                                                                Thread.currentThread().interrupt();//meysam 13960525
                                                                if (DialogPopUpModel.dialog_result == 1) {
                                                                    //go to register
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            DM.show();
                                                                        }
                                                                    });
                                                                    requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_BLOCKED;
                                                                    ufc.changeStatus(userForShow, UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                                                                } else {
                                                                    //do nothing
                                                                }
                                                                DialogPopUpModel.hide();
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


                                }
                            });
                        }
                        if (requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_DECLINED) {
                            userForShow.setFriendshipStatus(UserFriendModel.USER_FRIEND_STATUS_DECLINED);
                            db.deleteUserById(userForShow.getId());
                            db.addUser(userForShow, UserFriendModel.TAG_USER_FRIEND_STATUS_DECLINED);

                            lb_user_edit.setText(getString(R.string.btn_Request_Friendship));
                            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (session.getCurrentUser().getUserName() == null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            while (DialogPopUpModel.isUp()) {
                                                                Thread.sleep(500);
                                                            }
                                                            if (!DialogPopUpModel.isUp()) {
                                                                Thread.currentThread().interrupt();//meysam 13960525
                                                                if (DialogPopUpModel.dialog_result == 1) {
                                                                    //go to register
                                                                    Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                    UserProfileActivity.this.startActivity(i);
                                                                } else {
                                                                    //do nothing
                                                                }
                                                                DialogPopUpModel.hide();
                                                            }
                                                        } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();
                                            }
                                        });

                                    } else {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                DM.show();
//                                            }
//                                        });
//                                        ufc.request(userForShow);
                                        showRequestDialog(userForShow);

                                    }

                                }
                            });
                        }
                        if (requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_BLOCKED) {
                            userForShow.setFriendshipStatus(UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                            db.deleteUserById(userForShow.getId());
                            db.addUser(userForShow, UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED);

                            lb_user_edit.setText(getString(R.string.btn_Unblock_Friendship));
                            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    if (session.getCurrentUser().getUserName() == null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            while (DialogPopUpModel.isUp()) {
                                                                Thread.sleep(500);
                                                            }
                                                            if (!DialogPopUpModel.isUp()) {
                                                                Thread.currentThread().interrupt();//meysam 13960525
                                                                if (DialogPopUpModel.dialog_result == 1) {
                                                                    //go to register
                                                                    Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                    UserProfileActivity.this.startActivity(i);
                                                                } else {
                                                                    //do nothing
                                                                }
                                                                DialogPopUpModel.hide();
                                                            }
                                                        } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();
                                            }
                                        });

                                    } else {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogPopUpModel.show(UserProfileActivity.this, getString(R.string.msg_AreYouSure), getString(R.string.btn_Yes), getString(R.string.btn_No), true, true);

                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            while (DialogPopUpModel.isUp()) {
                                                                Thread.sleep(500);
                                                            }
                                                            if (!DialogPopUpModel.isUp()) {
                                                                Thread.currentThread().interrupt();//meysam 13960525
                                                                if (DialogPopUpModel.dialog_result == 1) {
                                                                    //go to register
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            DM.show();
                                                                        }
                                                                    });
                                                                    requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_DELETED;
                                                                    ufc.changeStatus(userForShow, UserFriendModel.USER_FRIEND_STATUS_DELETED);
                                                                } else {
                                                                    //do nothing
                                                                }
                                                                DialogPopUpModel.hide();
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
                                }
                            });
                        }
                        if (requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_DELETED) {
                            userForShow.setFriendshipStatus(UserFriendModel.USER_FRIEND_STATUS_DELETED);
                            db.deleteUserById(userForShow.getId());
//                            db.addUser(userForShow,UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED);

                            if(db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED).size() == 0)
                                if(session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
                                    session.removeItem(SessionModel.KEY_FRIENDSHIP_SYNC);

                            lb_user_edit.setText(getString(R.string.btn_Request_Friendship));
                            lb_user_edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (session.getCurrentUser().getUserName() == null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            while (DialogPopUpModel.isUp()) {
                                                                Thread.sleep(500);
                                                            }
                                                            if (!DialogPopUpModel.isUp()) {
                                                                Thread.currentThread().interrupt();//meysam 13960525
                                                                if (DialogPopUpModel.dialog_result == 1) {
                                                                    //go to register
                                                                    Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                                    UserProfileActivity.this.startActivity(i);
                                                                } else {
                                                                    //do nothing
                                                                }
                                                                DialogPopUpModel.hide();
                                                            }
                                                        } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();
                                            }
                                        });

                                    } else {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                DM.show();
//                                            }
//                                        });
                                        requestedFriendshipStatus = null;
//                                        ufc.request(userForShow);
                                        showRequestDialog(userForShow);

                                    }


                                }
                            });
                        }
                        requestedFriendshipStatus = null;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this, UserProfileActivity.this.getApplicationContext().getString(R.string.msg_OperationSuccess), UserProfileActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogPopUpModel.show(UserProfileActivity.this, UserProfileActivity.this.getApplicationContext().getString(R.string.msg_OperationFail), UserProfileActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                            }
                        });

                    }
                }
                else if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_REVOKE)) {
                    userForShow.setFriendshipStatus(UserFriendModel.USER_FRIEND_STATUS_DELETED);
                    db.deleteUserById(userForShow.getId());

                    if(db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED).size() == 0)
                        if(session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
                            session.removeItem(SessionModel.KEY_FRIENDSHIP_SYNC);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(UserProfileActivity.this, UserProfileActivity.this.getApplicationContext().getString(R.string.msg_OperationSuccess), UserProfileActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        }
                    });

                    lb_user_edit.setText(getString(R.string.btn_Request_Friendship));
                    lb_user_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (session.getCurrentUser().getUserName() == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DialogPopUpModel.show(UserProfileActivity.this, "باید ثبت نام کنی!!", "باشه", "بیخیال", true, true);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    while (DialogPopUpModel.isUp()) {
                                                        Thread.sleep(500);
                                                    }
                                                    if (!DialogPopUpModel.isUp()) {
                                                        Thread.currentThread().interrupt();//meysam 13960525
                                                        if (DialogPopUpModel.dialog_result == 1) {
                                                            //go to register
                                                            Intent i = new Intent(UserProfileActivity.this, UserRegisterActivity.class);
                                                            UserProfileActivity.this.startActivity(i);
                                                        } else {
                                                            //do nothing
                                                        }
                                                        DialogPopUpModel.hide();
                                                    }
                                                } catch (InterruptedException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }
                                });

                            } else {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        DM.show();
//                                    }
//                                });
                                requestedFriendshipStatus = null;
//                                ufc.request(userForShow);
                                showRequestDialog(userForShow);

                            }


                        }
                    });
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {
                    if(friendshipRequested)
                    {
                        friendshipRequested = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new DialogModel_hazel(UserProfileActivity.this).show(true, false, new Integer(Utility.FRIENDSHIP_REQUEST_COST));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DM.show();
                                    }
                                });

                                ufc.request(userForShow);

                            }
                        });
                    }


                }
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

    private void setStaticsOfCats(ArrayList<CategoryModel> temp, int i) {
        LuckyTextView cat_title_pb=findViewById(R.id.cat_title_pb);
        ProgressBar cat_a_pb=findViewById(R.id.cat_a_pb);
        LuckyTextView cat_a_percent=findViewById(R.id.cat_a_percent);
        ProgressBar cat_b_pb=findViewById(R.id.cat_b_pb);
        LuckyTextView cat_b_percent=findViewById(R.id.cat_b_percent);
        ProgressBar cat_c_pb=findViewById(R.id.cat_c_pb);
        LuckyTextView cat_c_percent=findViewById(R.id.cat_c_percent);

        if(i==14){
            user_record_match_chart.setVisibility(View.VISIBLE);
            user_record_layout_up.setVisibility(View.GONE);
        }else {
            user_record_match_chart.setVisibility(View.GONE);
            user_record_layout_up.setVisibility(View.VISIBLE);

            String[] cat_titles = {"تاریخ", "شعر و ادب", "زبان انگلیسی", "فرهنگ و هنر", "مذهبی", "جغرافیا", "تکنولوژی", "پزشکی و سلامت", "ورزشی", "هوش و ریاضی", "موسیقی", "اطلاعات عمومی", "چیستان", "آمار مسابقات"};
            cat_title_pb.setText(cat_titles[i - 1]);

            double p = (double) temp.get(i - 1).getDailyCorrectAnsweredCount() / (double) temp.get(i - 1).getDailyTotalAnsweredCount() * 100;
            int p2 = (int) Math.round(p);
            cat_a_pb.setProgress(p2);
            cat_a_percent.setText(Utility.enToFa(p2 + "%"));

            p = (double) temp.get(i - 1).getWeeklyCorrectAnsweredCount() / (double) temp.get(i - 1).getWeeklyTotalAnsweredCount() * 100;
            p2 = (int) Math.round(p);
            cat_b_pb.setProgress(p2);
            cat_b_percent.setText(Utility.enToFa(p2 + "%"));

            p = (double) temp.get(i - 1).getCorrectAnsweredCount() / (double) temp.get(i - 1).getTotalAnsweredCount() * 100;
            p2 = (int) Math.round(p);
            cat_c_pb.setProgress(p2);
            cat_c_percent.setText(Utility.enToFa(p2 + "%"));
        }
    }


    private void showResponseDialog(final UserModel user)
    {
        DialogPopUpFourVerticalModel.show(UserProfileActivity.this," تصمیمت چیه؟ ",getString(R.string.btn_Accept_Friendship), getString(R.string.btn_Decline_Friendship),getString(R.string.btn_Block_Friendship),getString(R.string.btn_No),true, false);
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
                            //accept friendship
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DM.show();
                                    requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_ACCEPTED;
                                    ufc.changeStatus(user,UserFriendModel.USER_FRIEND_STATUS_ACCEPTED);
                                }
                            });


                        }
                        else if(DialogPopUpFourVerticalModel.dialog_result==2)
                        {
                            //decline friendship
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DM.show();
                                        requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_DECLINED;
                                        ufc.changeStatus(user,UserFriendModel.USER_FRIEND_STATUS_DECLINED);
                                    }
                                });

                        }
                        else if(DialogPopUpFourVerticalModel.dialog_result==3)
                        {
                            //block user
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DM.show();
                                    requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_BLOCKED;
                                    ufc.changeStatus(user,UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                                }
                            });

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
    }


    @Override
    public void onResume() {
//        if(inCreate)
//        {
//            inCreate = false;
            fillAll();
//        }
//        else
//        {
//            infoCalledOnce = false;
//        }
       super.onResume();
    }

    public void onDestroy() {

        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObservers();
            uc = null;
        }
        if(ufc != null)
        {
            ufc.setCntx(null);
            ufc.deleteObservers();
            ufc = null;
        }

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        super.onDestroy();

    }
    private void ClosetOpen(){
        closet_open.setVisibility(View.GONE);
        ImageView closet_door_left=findViewById(R.id.closet_door_left);
        ImageView closet_door_right=findViewById(R.id.closet_door_right);
        int w=closet_door_left.getWidth();
        Anims.move(closet_door_right,0.0f,w+50.0f,0.0f,0.0f,1500,0);
        Anims.move(closet_door_left,0.0f,-1*(w+50.0f),0.0f,0.0f,1500,0);
    }

    private void setCupsOnClickListeners()
    {
        ImageView cupImage;

        cupImage = findViewById(R.id.cup_a_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_1000_question_answered_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_b_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_draw_winner_once_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_c_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_match_winner_100_time_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_d_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_match_perfect_answers_once_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_e_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_invite_one_person_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });


        cupImage = findViewById(R.id.cup_f_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_match_winner_1000_time_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_g_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_10000_question_answered_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_h_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_leage_1_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

        cupImage = findViewById(R.id.cup_i_up);
        cupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.tlt_cup_leage_2_description),getString(R.string.btn_OK),null,false,true);
                    }
                });
            }
        });

    }
    private void reloadCupsImages(String cups)
    {
        ImageView cupImage;
        String[] oldCups = cups.replaceAll("\\s+","").split("(?!^)");
        if(oldCups[0].equals("1"))
        {
            cupImage = findViewById(R.id.cup_a_up);
            cupImage.setImageResource(R.drawable.cup_a);
        }
        if(oldCups[1].equals("1"))
        {
            cupImage = findViewById(R.id.cup_b_up);
            cupImage.setImageResource(R.drawable.cup_b);
        }
        if(oldCups[2].equals("1"))
        {
            cupImage = findViewById(R.id.cup_c_up);
            cupImage.setImageResource(R.drawable.cup_c);
        }
        if(oldCups[3].equals("1"))
        {
            cupImage = findViewById(R.id.cup_d_up);
            cupImage.setImageResource(R.drawable.cup_d);
        }
        if(oldCups[4].equals("1"))
        {
            cupImage = findViewById(R.id.cup_e_up);
            cupImage.setImageResource(R.drawable.cup_e);
        }
        if(oldCups[5].equals("1"))
        {
            cupImage = findViewById(R.id.cup_f_up);
            cupImage.setImageResource(R.drawable.cup_f);
        }
        if(oldCups[6].equals("1"))
        {
            cupImage = findViewById(R.id.cup_g_up);
            cupImage.setImageResource(R.drawable.cup_g);
        }
        if(oldCups[8].equals("1"))
        {
            cupImage = findViewById(R.id.cup_h_up);
            cupImage.setImageResource(R.drawable.cup_h);
        }

    }

    private void setChatClickListener(final String userName, final String avatar, final String allowChat)
    {
        ImageView lb_chat = findViewById(R.id.lb_chat);
        lb_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getCurrentUser().getUserName() != null )
                {
                    if(userName != null)
                    {

                        if(userName.equals(session.getCurrentUser().getUserName()))
                        {
                            //meysam - go to chat list.... فرد در پروفایل خودش هست
                            Intent i = new Intent(UserProfileActivity.this, MessageIndexActivity.class);
                            i.putExtra("isChat",true);
                            UserProfileActivity.this.startActivity(i);
                        }
                        else
                        {
                            //meysam - پروفایل خودش نیست
                            if(session.getCurrentUser().getAllowChat().equals("1"))
                            {

                                //meysam - اگر پروفایل برای خود کاربر نبود
                                if(!userName.contains("Visitor_"))
                                {
                                    if(allowChat.equals("1"))
                                    {
                                        //meysam - chat is allowed - خود فرد اجازه چت در تنظیمات خود دارد
                                        //meysam - added in 13961002
                                        Intent i = new Intent(UserProfileActivity.this, ChatActivity.class);
                                        i.putExtra("opponent_user_name", userName);
                                        i.putExtra("opponent_avatar", avatar);
                                        UserProfileActivity.this.startActivity(i);
                                        //////////////////////////////
                                    }
                                    else
                                    {
                                        //meysam - حریف چت رو غیر فعال کرده
                                        DialogPopUpModel.show(UserProfileActivity.this,"حریف چت رو غیر فعال کرده",getString(R.string.btn_OK),null,false,true);
                                    }
                                }
                                else
                                {
                                    //meysam - other person is visitor and can not chat...
                                    Utility.displayToast(UserProfileActivity.this,"مهمان چت نداره", Toast.LENGTH_SHORT);
                                }


                            }
                            else
                            {
                                //meysam - فرد چت رو در تنظیمات خودش غیر فعال کرده
                                DialogPopUpModel.show(UserProfileActivity.this,"چت در تنظیمات غیر فعال شده",getString(R.string.btn_OK),null,false,true);

                            }


                        }

                    }
                }
                else
                {
                    DialogPopUpModel.show(UserProfileActivity.this,getString(R.string.msg_MustRegister),getString(R.string.btn_OK),null,false,true);
                }
            }
        });
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void setFriendCount(Integer friendCount)
    {
        LuckyTextViewAutoSize ltvaFriendCount = findViewById(R.id.friendNumberMain_up);
        if(friendCount == null)
        {
            ltvaFriendCount.setText("??");
        }
        else
        {
            ltvaFriendCount.setText(friendCount.toString());
        }
    }

    private void WatchAd()
    {
        new SoundModel(this).stopMusic();


        String adZoneId = AdvertismentModel.ProfilePageZoneId;

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);
        Tapsell.requestAd(UserProfileActivity.this,adZoneId , options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(UserProfileActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(UserProfileActivity.this).playCountinuseRandomMusic();

                friendshipRequested = false;

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) UserProfileActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(UserProfileActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(UserProfileActivity.this).playCountinuseRandomMusic();

                friendshipRequested = false;

            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(UserProfileActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(UserProfileActivity.this).playCountinuseRandomMusic();

                friendshipRequested = false;

            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(UserProfileActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(UserProfileActivity.this).playCountinuseRandomMusic();

                friendshipRequested = false;

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

                new SoundModel(UserProfileActivity.this).playCountinuseRandomMusic();

                if(completed)
                {
                    if(friendshipRequested)
                    {
                        // meysam - go to friendship...
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DM.show();
                            }
                        });

                        ufc.request(userForShow);
                    }

                }
                else
                {
                    Utility.displayToast(UserProfileActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

                friendshipRequested = false;
            }
        });
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

                    Utility.displayToast(UserProfileActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);

                }
            }

        }
    };

    private void showLoading()
    {
        Intent intent = new Intent("user_profile_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(UserProfileActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughHazelMessage()
    {
        Intent intent = new Intent("user_profile_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_hazel", "true");
        LocalBroadcastManager.getInstance(UserProfileActivity.this).sendBroadcast(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);

        extras = getIntent().getExtras();
        isHimself=false;
        if(extras != null)
        {

            userForShow.setUserName(extras.getString("EXTRA_User_Name"));
            if(extras.getString("EXTRA_Avatar") == null)
            {
                // meysam - came from chat notification...
                DM.show();
                uc.info(extras.getString("EXTRA_User_Name"));
            }
            else
            {
                userForShow.setProfilePicture(extras.getString("EXTRA_Avatar"));

                if(session.getCurrentUser().getUserName()!=null && !session.getCurrentUser().getUserName().equals("null")){
                    if(session.getCurrentUser().getUserName().equals(extras.getString("EXTRA_User_Name"))){
                        isHimself=true;
                    }
                }else{
                    if(session.getCurrentUser().getVisitorUserName().equals(extras.getString("EXTRA_User_Name"))){
                        isHimself=true;
                    }
                }
            }

        }
        fillAll();

    }
}
