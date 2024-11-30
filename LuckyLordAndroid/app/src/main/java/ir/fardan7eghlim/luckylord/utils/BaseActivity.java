package ir.fardan7eghlim.luckylord.utils;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesActivity;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;


/**
 * Created by Meysam on 3/8/2017.
 */

public class BaseActivity extends AppCompatActivity  {

    public static SoundModel bgSound;
    boolean flag = false;
    protected SessionModel session;
    private Thread llErrorThread;
    private LuckyLordThreadUncaughtExceptionHandler mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = true;
        session = new SessionModel(getApplicationContext());



        mThread = new LuckyLordThreadUncaughtExceptionHandler(this);
        llErrorThread = Thread.currentThread();
        llErrorThread.setDefaultUncaughtExceptionHandler(mThread);



        //meysam - for preventing device to go to sleep
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


//        DMBase = new DialogModel(BaseActivity.this);



        if (!session.getBoolianItem(SessionModel.KEY_FIRST_RUN,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            session.saveItem(SessionModel.KEY_FIRST_RUN,true);
            session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
        }
        if(session.isUserNameOrVisitorUserNameExist())
        {
            if(bgSound == null)
            {
                bgSound = new SoundModel(getApplicationContext());
//            bgSound.playCountinuseRandomMusic();

            }
            bgSound.playCountinuseRandomMusic();
        }

//        bgSound.playCountinuseRandomMusic();
//        bgSound.playRandomMusic();

//        DMBase.show();
        if(!Utility.isNetworkAvailable(getApplicationContext())){
//            DMBase.hide();
            DialogPopUpModel.show(this,getString(R.string.error_no_connection),getString(R.string.lbl_retry),getString(R.string.lbl_exit), true, false);
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
//                            DialogPopUpModel.dialog.setCanceledOnTouchOutside(true);
                            Thread.currentThread().interrupt();//meysam 13960525
                            if(DialogPopUpModel.dialog_result==1){
                                //again
                                if (Build.VERSION.SDK_INT >= 11) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recreate();
                                        }
                                    });

                                } else {
                                    Intent intent = getIntent();
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    finish();
//                                    overridePendingTransition(0, 0);

                                    startActivity(intent);
//                                    overridePendingTransition(0, 0);
                                }
                            }else{
                                //exit
                                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("Exit me", true);
                                startActivity(intent);
//                                BaseActivity.this.finish();
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
//        else
//        {
//            DMBase.hide();
//        }


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        throw new RuntimeException("error meydam");
    }

    @Override
    public void onResume() {
        super.onResume();

        mThread = new LuckyLordThreadUncaughtExceptionHandler(this);
        llErrorThread = Thread.currentThread();
        llErrorThread.setDefaultUncaughtExceptionHandler(mThread);

        if(flag == true){
                  // it has came from onCreate()
            flag = false;

        }
        else{
                 // it has directly came to onResume()
            if(!Utility.isNetworkAvailable(getApplicationContext())){
                DialogPopUpModel.show(this,getString(R.string.error_no_connection),getString(R.string.lbl_retry),getString(R.string.lbl_exit), true,false);
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
//                                DialogPopUpModel.dialog.setCanceledOnTouchOutside(true);
                                Thread.currentThread().interrupt();
                                if(DialogPopUpModel.dialog_result==1){
                                    //again
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    //exit
                                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("Exit me", true);
                                    startActivity(intent);
//                                    BaseActivity.this.finish();
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

            if(new SessionModel(getApplicationContext()).isUserNameOrVisitorUserNameExist())
            {
                if(bgSound == null)
                {
                    bgSound = new SoundModel(getApplicationContext());
//            bgSound.playCountinuseRandomMusic();

                }
                bgSound.playCountinuseRandomMusic();
            }
        }
    }

    @Override
    public void onBackPressed() {

        System.gc();
        super.onBackPressed();
    }

    public void show_profile() {
//        Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }


    public void aboutUs() {
        try {

//            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);


        } catch (ActivityNotFoundException ex) {

//            //on catch, show the download dialog
//            Utility.displayToast(this, "error", Toast.LENGTH_SHORT;
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: " + ex.getMessage() + " CallStack: " + ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(BigInteger.valueOf(Long.parseLong(new SessionModel(getApplicationContext()).getUserDetails().get(ir.fardan7eghlim.attentra.models.SessionModel.KEY_ID).toString())));
//            log.insert();

//            Utility.generateLogError(ex);
        }
    }

//    public void share() {
//        try {
//
//            Intent sharingIntent = new Intent();
//            sharingIntent.setAction(Intent.ACTION_SEND);
//            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
//            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Here is the share content body");
//            sharingIntent.setType("text/plain");
//            startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//
//        } catch (ActivityNotFoundException ex) {
//
//            //on catch, show the download dialog
//            Utility.displayToast(this, "error", Toast.LENGTH_SHORT);
////            LogModel log = new LogModel(getApplicationContext());
////            log.setErrorMessage("message: " + ex.getMessage() + " CallStack: " + ex.getStackTrace());
////            log.setContollerName(this.getClass().getName());
////            log.setUserId(new SessionModel(getApplicationContext()).getCurrentUser().getId());
////            log.insert();
//
//            Utility.generateLogError(ex);
//        }
//    }

    public void rate() {
        try {

            String PACKAGE_NAME = getApplicationContext().getPackageName();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(Uri.parse("bazaar://details?id=" + PACKAGE_NAME));
            intent.setPackage("com.farsitel.bazaar");
            startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            //on catch, show the download dialog
            Utility.displayToast(this, "error", Toast.LENGTH_SHORT);
//            LogModel log = new LogModel(getApplicationContext());
//            log.setErrorMessage("message: " + ex.getMessage() + " CallStack: " + ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(getApplicationContext()).getCurrentUser().getId());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }

    public void otherProducts() {
        try {

            // meysam - set developer id
            String DEVELOPER_ID = "693725359875";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=" + DEVELOPER_ID));
            intent.setPackage("com.farsitel.bazaar");
            startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            //on catch, show the download dialog
            Utility.displayToast(this, getString(R.string.msg_OperationError), Toast.LENGTH_SHORT);
//            LogModel log = new LogModel(getApplicationContext());
//            log.setErrorMessage("message: " + ex.getMessage() + " CallStack: " + ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(getApplicationContext()).getCurrentUser().getId());
//            log.insert();

            Utility.generateLogError(ex);
        }
    }

    public void onDestroy() {
//        bgSound.stopMusic();
//        Utility.displayToast(this,"destroessssst",Toast.LENGTH_SHORT);
        super.onDestroy();
    }

    @Override
    public void onPause() {
//        bgSound.stopMusic();
//        Utility.displayToast(this,"pauuuuuuse",Toast.LENGTH_SHORT);
        mThread.clear();
        mThread = null;
        llErrorThread.interrupt();
        super.onPause();
    }

    @Override
    protected void onUserLeaveHint()
    {
//        SoundModel.stopMusic();
//        Utility.displayToast(this,"tessssst",Toast.LENGTH_SHORT);
        super.onUserLeaveHint();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utility.runningActivities == 0) {
            // app enters foreground
            if(new SessionModel(getApplicationContext()).isUserNameOrVisitorUserNameExist())
            {
                if(bgSound == null)
                {
                    bgSound = new SoundModel(getApplicationContext());
//            bgSound.playCountinuseRandomMusic();

                }
                bgSound.playCountinuseRandomMusic();
            }
        }
        Utility.runningActivities++;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Utility.runningActivities--;
        if (Utility.runningActivities == 0) {
            // app goes to background
            bgSound.stopMusic();
        }
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
////        Utility.displayToast(this,"onWindowFocusChanged",Toast.LENGTH_SHORT);
//
//        }

}
