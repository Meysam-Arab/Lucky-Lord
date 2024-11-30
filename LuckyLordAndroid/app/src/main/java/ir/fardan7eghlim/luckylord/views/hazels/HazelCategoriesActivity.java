package ir.fardan7eghlim.luckylord.views.hazels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.controllers.UserFriendController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.LuckyLordRecyclerViewAdapter;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchQuestionActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;
import spencerstudios.com.bungeelib.Bungee;

public class HazelCategoriesActivity extends BaseActivity implements Observer{
    private DialogModel DM;

    private boolean inCreating;

    private Double reward_time_cost;//meysam - added in 13960608

    private UserController uc;
    private MatchController mc;
    private UserFriendController ufc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazel_categories);

        inCreating = true;

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        ufc = new UserFriendController(getApplicationContext());
        ufc.addObserver(this);

        mc = new MatchController(getApplicationContext());
        mc.addObserver(this);

        if (!session.getBoolianItem(SessionModel.KEY_CATEGORIES_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

            DialogPopUpModel.show(HazelCategoriesActivity.this, getString(R.string.dlg_TutorialCategories), getString(R.string.btn_OK), null,false, false);
            session.saveItem(SessionModel.KEY_CATEGORIES_IS_FIRST_TIME, true);

        }

        setCategoriesClickListeners();

        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        DM = new DialogModel(HazelCategoriesActivity.this);

        //contest with friend
        final FrameLayout cat_1= (FrameLayout) findViewById(R.id.cat_1_hc);
        cat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: meysam - remove after version 2.0.0
                PackageInfo pInfo = null;
                Float version = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if(pInfo != null)
                {
                    String[] tmpVersion = pInfo.versionName.split("[.]");
                    version = new Float(tmpVersion[0]+"."+tmpVersion[1]+tmpVersion[2]);
                }
                Float current_ver = version;
                if(current_ver < 1.12)
                {
                    Utility.displayToast(HazelCategoriesActivity.this,"باید برنامه رو بروزرسانی کنی!!", Toast.LENGTH_SHORT);
                    return;
                }
                //////////////////////////////////////////////////

                Intent i = new Intent(HazelCategoriesActivity.this,HazelMatchIndexActivity.class);
                i.putExtra("match_type",UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION.toString());
                HazelCategoriesActivity.this.startActivity(i);

            }
        });

        ImageView iv_cross_table_match = findViewById(R.id.iv_cat_4_hc);
        iv_cross_table_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: meysam - remove after version 2.0.0
                PackageInfo pInfo = null;
                Float version = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if(pInfo != null)
                {
                    String[] tmpVersion = pInfo.versionName.split("[.]");
                    version = new Float(tmpVersion[0]+"."+tmpVersion[1]+tmpVersion[2]);
                }
                Float current_ver = version;
                if(current_ver < 1.12)
                {
                    Utility.displayToast(HazelCategoriesActivity.this,"باید برنامه رو بروزرسانی کنی!!", Toast.LENGTH_SHORT);
                    return;
                }
                //////////////////////////////////////////////////

                Intent i = new Intent(HazelCategoriesActivity.this,HazelMatchIndexActivity.class);
                i.putExtra("match_type",UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE.toString());
                HazelCategoriesActivity.this.startActivity(i);
            }
        });


        if(session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
            ufc.sync();
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
                    Intent i = new Intent(HazelCategoriesActivity.this,MainActivity.class);
                    HazelCategoriesActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER))
                {

                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()) == true)
                    {

                        //meysam - added in 13960608
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getBetLuck())
                        {
                            session.decreaseLuck( Integer.valueOf(reward_time_cost.intValue()).toString());
                        }
                        else
                        {
                            session.decreaseHazel(Integer.valueOf(reward_time_cost.intValue()).toString());
                        }

                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_SYNC))
                {

                    //meysam - change status of recieced user friend records in sqlite...
                    ArrayList<UserFriendModel> userFriends = (ArrayList<UserFriendModel>) ((ArrayList) arg).get(1);
                    if(userFriends.size() > 0)
                    {
                        UserFriendModel.syncFriendsStatusInDb(userFriends,HazelCategoriesActivity.this.getApplicationContext());

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


    private void WatchAd()
    {
        new SoundModel(this).stopMusic();


        String adZoneId;


        adZoneId = AdvertismentModel.CategoriesPageZoneId;


        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(HazelCategoriesActivity.this,adZoneId , options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(HazelCategoriesActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesActivity.this).playCountinuseRandomMusic();

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) HazelCategoriesActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(HazelCategoriesActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesActivity.this).playCountinuseRandomMusic();

            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(HazelCategoriesActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesActivity.this).playCountinuseRandomMusic();

            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(HazelCategoriesActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(HazelCategoriesActivity.this).playCountinuseRandomMusic();

            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {

                new SoundModel(HazelCategoriesActivity.this).playCountinuseRandomMusic();

                if(completed)
                {

                }
                else
                {
                    Utility.displayToast(HazelCategoriesActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }

            }
        });
    }

    private void setCategoriesClickListeners()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                FrameLayout fl_local_match = (FrameLayout) findViewById(R.id.cat_12_hc);
                FrameLayout fl_ask_who = (FrameLayout) findViewById(R.id.cat_3_hc);



//                ImageView iv_local_match = (ImageView) findViewById(R.id.iv_cat_12_hc);
//                ImageView iv_ask_who = (ImageView) findViewById(R.id.iv_cat_3_hc);


                fl_local_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utility.displayToast(HazelCategoriesActivity.this, getString(R.string.msg_InNewVersion), Toast.LENGTH_SHORT);
                    }
                });


                fl_ask_who.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utility.displayToast(HazelCategoriesActivity.this, getString(R.string.msg_InNewVersion), Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }


    @Override
    public void onPause() {

        super.onPause();
    }



    public void onDestroy() {

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

        Tapsell.setRewardListener(null);

        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();


        if(UniversalMatchQuestionActivity.MQA != null)
        {
            if(UniversalMatchQuestionActivity.MQA.customHandler != null)
            {
                UniversalMatchQuestionActivity.MQA.customHandler.removeCallbacksAndMessages(null);
            }
            UniversalMatchQuestionActivity.MQA = null;
        }

        if(!inCreating)
        {


            mc = new MatchController(getApplicationContext());
            mc.addObserver(this);

            uc = new UserController(getApplicationContext());
            uc.addObserver(this);

            ufc = new UserFriendController(getApplicationContext());
            ufc.addObserver(this);
        }

        if(inCreating)
        {
            inCreating = false;
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.slideLeft(this); //fire the slide left animation
    }
}
