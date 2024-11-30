package ir.fardan7eghlim.luckylord.views.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import dyanamitechetan.vusikview.VusikView;
import io.supercharge.shimmerlayout.ShimmerLayout;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.HomeController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.PushNotificationModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_first;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.DialogSecsSelection;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.fardan7eghlim.luckylord.views.user.UserRegisterActivity;
import spencerstudios.com.bungeelib.Bungee;

public class FirstPageActivity extends BaseActivity implements Observer {
    private Button lb_login;
    private Button lb_register;
    private Button lb_play;
    private ImageView iv_sound;
    private ImageView thunderstrom;

    private String gender;

    public static Activity FirstPageActivity;

    private boolean doubleBackToExitPressedOnce = false;

    private DialogModel DM;
    private DialogModel_first f_DM;

    private boolean isSoundOn;

    private HomeController hc;

    VusikView vusikView;
    ShimmerLayout shimmerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

//        SoundModel.stopMusic();


        FirstPageActivity = this;
        gender = null;

        isSoundOn = true;

        DM = new DialogModel(this);
        f_DM=new DialogModel_first(FirstPageActivity.this);

        vusikView = (VusikView) findViewById(R.id.vusik);
//        vusikView = new VusikView(getApplicationContext());
        int[]  myImageList = new int[]{R.drawable.alphabet_a,R.drawable.alphabet_b,R.drawable.alphabet_d,R.drawable.alphabet_eyn,R.drawable.alphabet_g,R.drawable.alphabet_gh,R.drawable.alphabet_h,R.drawable.alphabet_l,R.drawable.alphabet_m,R.drawable.alphabet_n,R.drawable.alphabet_p,R.drawable.alphabet_r,R.drawable.alphabet_s,R.drawable.alphabet_t,R.drawable.alphabet_v,R.drawable.alphabet_y,R.drawable.alphabet_z};
        vusikView.setImages(myImageList)
                 .start();

        shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_text);
        shimmerText.startShimmerAnimation();

        iv_sound = findViewById(R.id.iv_sound);
        setSoundButtonListener();


//        arangeGraphic_start();

        //owl
//        ImageView owl= (ImageView) findViewById(R.id.c_owl_r);
//        owl.setImageResource(R.drawable.c_owl_anim_a);
//        AnimationDrawable anim = (AnimationDrawable) owl.getDrawable();
//        anim.start();

        lb_register= (Button) findViewById(R.id.lb_register);
        lb_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(FirstPageActivity.this,UserRegisterActivity.class);
//                FirstPageActivity.this.startActivity(i);
                startActivity(new Intent(FirstPageActivity.this,UserRegisterActivity.class));
                Bungee.slideDown(FirstPageActivity.this);
            }
        });
        lb_login= (Button) findViewById(R.id.lb_login);
        lb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(FirstPageActivity.this,UserLoginActivity.class);
//                FirstPageActivity.this.startActivity(i);
                startActivity(new Intent(FirstPageActivity.this,UserLoginActivity.class));
                Bungee.slideUp(FirstPageActivity.this);
            }
        });
        lb_play= (Button) findViewById(R.id.lb_play);
        lb_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// 8/16/2017 Meysam
                DialogSecsSelection.show(FirstPageActivity.this,true, true);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            while(DialogSecsSelection.isUp()){
                                Thread.sleep(500);
                            }
                            if(!DialogSecsSelection.isUp()){
                                Thread.currentThread().interrupt();
                                if(DialogSecsSelection.dialog_result==1){
                                    //meysam - girl
                                    gender = UserModel.Female;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            attemptPlay(gender);
                                        }
                                    });
                                }
                                else if(DialogSecsSelection.dialog_result==2)
                                {
                                    //meysam - boy
                                    gender = UserModel.Male;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            attemptPlay(gender);
                                        }
                                    });
                                }
                                else
                                {
                                    //meysam - do nothing
                                }


                                DialogSecsSelection.hide();
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


        TextView tv_version = findViewById(R.id.tv_version);
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(pInfo != null)
        {
            tv_version.setText("Version " +pInfo.versionName);
        }



//        LuckyButton lb_play = (LuckyButton) findViewById(R.id.lb_play);
//        lb_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attemptPlay();
//            }
//        });

        //thunderstrom
        thunderstrom=findViewById(R.id.thunderstrom);
        thunderstrom.setImageResource(R.drawable.thunderstrom_anim);
        AnimationDrawable anim = (AnimationDrawable) thunderstrom.getDrawable();
        anim.start();

        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch( audio.getRingerMode() ){
            case AudioManager.RINGER_MODE_NORMAL:

                if(isSoundOn)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isSoundOn)
                            {
                                SoundModel.playSpecificSound(R.raw.thunder,getApplicationContext(),false, null,null,0.5f);
                            }
                        }
                    }, 2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isSoundOn)
                            {
                                session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
                                new SoundModel(getApplicationContext()).playCountinuseRandomMusic();


                            }
                        }
                    }, 10000);
                }

                break;
            case AudioManager.RINGER_MODE_SILENT:
                //meysam - make music mute
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //meysam - make music mute
                break;
            default:
                break;
        }

        hc = new HomeController(getApplicationContext());
        hc.addObserver(this);


    }
//    private void arangeGraphic_start() {
//        int height = getWindowManager().getDefaultDisplay().getHeight();
//        final int p20_height= (int) (height*0.2);
//
//        ImageView c_owl= (ImageView) findViewById(R.id.c_owl_r);
//
//        android.view.ViewGroup.LayoutParams layoutParams = c_owl.getLayoutParams();
//        layoutParams.height = p20_height;
//        c_owl.setLayoutParams(layoutParams);
//    }

    private void attemptPlay(String gender) {
        DM.show();

        UserController uc = new UserController(getApplicationContext());
        uc.addObserver(this);
        uc.registerVisitor(gender);
    }

    @Override
    public void update(Observable o, final Object arg) {
        DM.hide();
//        f_DM.hide();
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }

            }else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0)== RequestRespondModel.TAG_VISITOR_REGISTER_USER)
                {

                    UserModel user = new UserModel();
                    if(gender.equals(UserModel.Male))
                        user.setProfilePicture("m#1#1#1#1#1#0#0#0#0#0#0#0");
                    else
                        user.setProfilePicture("f#1#1#1#1#1#0#0#0#0#0#0#0");
                    session.updateUserSession(user);

                    String[] temp = Utility.tokenDecode(session.getStoredToken());
                    try {
                        JSONObject jobj = new JSONObject(temp[1]);
                        OneSignal.sendTag(PushNotificationModel.USER_ID_KEY,jobj.getString("user_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(FirstPageActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Utility.finishActivity(this);
                }

            }else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
//                    SessionModel session = new SessionModel(getApplicationContext());
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        if(hc != null)
        {
            hc.setCntx(null);
            hc.deleteObservers();
            hc = null;
        }


        if(shimmerText != null)
        {
            shimmerText.stopShimmerAnimation();
            shimmerText.clearAnimation();
            shimmerText.removeAllViews();
            shimmerText = null;
        }

        if(vusikView != null)
        {
            vusikView.stopNotesFall();
            vusikView.setEnabled(false);
            vusikView.setAnimation(null);
            vusikView.destroyDrawingCache();
            vusikView.cancelLongPress();
            vusikView.clearAnimation();
            vusikView.setImages(null);
            vusikView.invalidate();
            vusikView.postInvalidate();
            vusikView = null;
        }

        super.onDestroy();

    }

    @Override
    public void onPause() {

//        new SoundModel(this).stopMusic();

          super.onPause();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
//            new SoundModel(this).stopMusic();

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

    @Override
    public void onResume() {

//        new SoundModel(this).playCountinuseRandomMusic();
        super.onResume();


    }

    private void setSoundButtonListener() {

        if(session.hasItem(SessionModel.KEY_MUSIC_PLAY))
        {
            if(isSoundOn && session.getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
            {
                iv_sound.setImageResource(R.drawable.b_sound_on);
                isSoundOn = true;
            }
            else
            {
                iv_sound.setImageResource(R.drawable.b_sound_mute);
                isSoundOn = false;

            }
        }
        else
        {
            iv_sound.setImageResource(R.drawable.b_sound_on);
            isSoundOn = true;
        }


        iv_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSoundOn && SoundModel.isPlaying())
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                    SoundModel.stopMusic();
                    isSoundOn = false;

                    iv_sound.setImageResource(R.drawable.b_sound_mute);
                }
                else
                {
                    isSoundOn = true;
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();

                    iv_sound.setImageResource(R.drawable.b_sound_on);
                }

            }
        });

    }


}
