package ir.fardan7eghlim.luckylord.views.settings;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.fardan7eghlim.luckylord.views.user.UserProfileActivity;
import ir.fardan7eghlim.luckylord.views.user.UserRegisterActivity;

public class SettingIndexActivity extends BaseActivity implements Observer {
    private DialogModel DM;
//    private SessionModel session;
    private ImageView iv_sound;
    private LinearLayout fl_sound;

    private LinearLayout ll_notificationSound;
    private LinearLayout ll_allowChat;
    private ImageView iv_notificationSoundImage;
    private ImageView iv_allowChatImage;

    private  UserController uc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_index);

//        session = new SessionModel(getApplicationContext());

        DM=new DialogModel(SettingIndexActivity.this);

        uc = new UserController(SettingIndexActivity.this);
        uc.addObserver(SettingIndexActivity.this);

        LuckyButton lb_register_user = (LuckyButton) findViewById(R.id.lb_register_user);
        lb_register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        LuckyButton lb_user_profile = (LuckyButton) findViewById(R.id.lb_user_profile);
        lb_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });

        LuckyButton lb_instagram = (LuckyButton) findViewById(R.id.lb_instagram);
        lb_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagram();
            }
        });

        LuckyButton lb_other_products = (LuckyButton) findViewById(R.id.lb_other_products);
        lb_other_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherProducts();
            }
        });

        if(session.getCurrentUser().getUserName() == null){
            lb_user_profile.setVisibility(View.GONE);
        }else{
            lb_register_user.setVisibility(View.GONE);
        }

        LuckyButton lb_contact_us = (LuckyButton) findViewById(R.id.lb_contact_us);
        lb_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToContactUs();
            }
        });

        LuckyButton lb_invite_friend = (LuckyButton) findViewById(R.id.lb_invite_friend);
        lb_invite_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInviteFriend();
            }
        });

        LuckyButton lb_about_us = (LuckyButton) findViewById(R.id.lb_about_us);
        lb_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutUs();
            }
        });

        LuckyButton lb_help_user = (LuckyButton) findViewById(R.id.lb_help_user);
        lb_help_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHelp();
            }
        });

        LuckyButton lb_share = (LuckyButton) findViewById(R.id.lb_share);
        lb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });


        fl_sound =  findViewById(R.id.fl_sound);
        iv_sound = (ImageView) findViewById(R.id.iv_sound);
        if(session.getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
            iv_sound.setImageResource(R.drawable.icon_sound_unmute);
        else
            iv_sound.setImageResource(R.drawable.icon_sound_mute);

        fl_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SoundModel.isPlaying())
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.STOPPED);
                    SoundModel.stopMusic();

                    iv_sound.setImageResource(R.drawable.icon_sound_mute);
                }
                else
                {
                    session.saveItem(SessionModel.KEY_MUSIC_PLAY,SoundModel.IS_PLAYING);
                    new SoundModel(getApplicationContext()).playCountinuseRandomMusic();

                    iv_sound.setImageResource(R.drawable.icon_sound_unmute);
                }
            }
        });

        ll_notificationSound =  findViewById(R.id.ll_notification_sound);
        ll_allowChat = findViewById(R.id.ll_chat_allow);
        iv_allowChatImage = findViewById(R.id.iv_chat_allow);

        if(session.getStringItem(SessionModel.KEY_ALLOW_CHAT).equals("1"))
        {
            iv_allowChatImage.setImageResource(R.drawable.icon_like);
        }
        else
        {
            iv_allowChatImage.setImageResource(R.drawable.icon_hate);
        }

        iv_notificationSoundImage = (ImageView) findViewById(R.id.iv_chat_notification);
        if(session.getBoolianItem(SessionModel.KEY_NOTIFICATION_SOUND,true))
            iv_notificationSoundImage.setImageResource(R.drawable.icon_like);
        else
            iv_notificationSoundImage.setImageResource(R.drawable.icon_hate);


        ll_notificationSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getBoolianItem(SessionModel.KEY_NOTIFICATION_SOUND,true))
                {
                    session.saveItem(SessionModel.KEY_NOTIFICATION_SOUND,false);

                    iv_notificationSoundImage.setImageResource(R.drawable.icon_hate);
                }
                else
                {
                    session.saveItem(SessionModel.KEY_NOTIFICATION_SOUND,true);

                    iv_notificationSoundImage.setImageResource(R.drawable.icon_like);
                }
            }
        });

            ll_allowChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(session.getCurrentUser().getUserName() != null )
                    {
                        DM.show();
                        if(session.getStringItem(SessionModel.KEY_ALLOW_CHAT).equals("1"))
                        {
                            uc.updateAllowChat("0");
                        }
                        else
                        {
                            uc.updateAllowChat("1");
                        }
                    }
                    else
                    {
                        DialogPopUpModel.show(SettingIndexActivity.this,getString(R.string.msg_MustRegister),getString(R.string.btn_OK),null,false,true);
                    }


                }
            });


    }

    //goToRegister
    public void goToRegister(){
        Intent i = new Intent(SettingIndexActivity.this,UserRegisterActivity.class);
        SettingIndexActivity.this.startActivity(i);
    }
    //goToProfile
    public void goToProfile(){
        Intent i = new Intent(SettingIndexActivity.this,UserProfileActivity.class);
        SettingIndexActivity.this.startActivity(i);
    }
    //goToContactUs
    public void goToContactUs(){
        Intent i = new Intent(SettingIndexActivity.this,SettingContactUsActivity.class);
        SettingIndexActivity.this.startActivity(i);
    }
    //goToInviteFriend
    public void goToInviteFriend(){
        Intent i = new Intent(SettingIndexActivity.this,SettingInviteFriendActivity.class);
        SettingIndexActivity.this.startActivity(i);
    }
    //goToAboutUs
    public void goToAboutUs(){
        Intent i = new Intent(SettingIndexActivity.this,SettingAboutUsActivity.class);
        SettingIndexActivity.this.startActivity(i);
    }
    //goToHelp
    public void goToHelp(){
        Intent i = new Intent(SettingIndexActivity.this,SettingHelpActivity.class);
        SettingIndexActivity.this.startActivity(i);
    }
    //share app - show share dialog
    public void showShareDialog()
    {
        final String[] link = new String[1];

        DialogPopUpFourVerticalModel.show(SettingIndexActivity.this, "لینک مستقیم یا کافه بازار؟","مستقیم", "کافه بازار",null,null,true,true);
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
                        if(DialogPopUpFourVerticalModel.dialog_result==1)
                        {
                            //meysam - direct link
                            //meysam - direct link
                            link[0] = "http://www.luckylord.ir/getAPK";
                            shareLink(link[0]);

                        }
                        else if(DialogPopUpFourVerticalModel.dialog_result==2)
                        {
                            //meysam - cafebazzar link
                            //meysam - cafebazar link
                            link[0] = "https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa";
                            shareLink(link[0]);
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

    public void shareLink(String link)
    {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "لاکی لرد");
            String sAux = "\nمن بازی لاکی لرد رو پیشنهاد میدم\n\n";
            sAux = sAux + link + " \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "یکی رو انتخاب کن"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    //goTo Instagram
    public void instagram(){
        Uri uri = Uri.parse("http://instagram.com/_u/luckylord_game");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/luckylord_game")));
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg)
    {
        DM.hide();

        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Utility.finishActivity(SettingIndexActivity.this);
                }
            }
            else if (arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UPDATE_ALLOW_CHAT_USER))
                {
                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        if(session.getStringItem(SessionModel.KEY_ALLOW_CHAT).equals("1"))
                        {
                            session.saveItem(SessionModel.KEY_ALLOW_CHAT,"0");

                            iv_allowChatImage.setImageResource(R.drawable.icon_hate);
                        }
                        else
                        {
                            session.saveItem(SessionModel.KEY_ALLOW_CHAT,"1");

                            iv_allowChatImage.setImageResource(R.drawable.icon_like);
                        }
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

    //meysam - 13960625
//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(SettingIndexActivity.this,MainActivity.class);
//
//        SettingIndexActivity.this.startActivity(i);
//        super.onBackPressed();
//    }

    @Override
    public void onDestroy() {
        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObservers();
            uc = null;
        }
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        super.onDestroy();
    }
}
