package ir.fardan7eghlim.luckylord.views.settings;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.ContactUsController;
import ir.fardan7eghlim.luckylord.models.ContactUsModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyEditText;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.fardan7eghlim.luckylord.views.user.UserRegisterActivity;

public class SettingContactUsActivity extends BaseActivity implements Observer {

    private AsyncTask<String, Integer, Boolean> asyncTask;
    private DialogModel DM;
    private int width;
    private int height;

    LuckyEditText let_contactus_email;
    LuckyEditText let_contactus_tel;
    LuckyEditText let_contactus_title;
    LuckyEditText let_contactus_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_contact_us);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        arangeGraphic_start();
        cloudMotion_a();

        //fox
        ImageView fox= (ImageView) findViewById(R.id.c_fox_scu);
        fox.setImageResource(R.drawable.c_fox_anim_a);
        AnimationDrawable anim = (AnimationDrawable) fox.getDrawable();
        anim.start();


        let_contactus_email = (LuckyEditText) findViewById(R.id.et_email_scu);
        if(session.getCurrentUser().getEmail() != null && !session.getCurrentUser().getEmail().equals("null"))
        {
            let_contactus_email.setText(session.getCurrentUser().getEmail());
        }
        let_contactus_tel=(LuckyEditText) findViewById(R.id.et_tel_scu);
        let_contactus_title=(LuckyEditText) findViewById(R.id.et_title_scu);
        let_contactus_description=(LuckyEditText) findViewById(R.id.et_description_scu);

        LuckyButton lb_contactus_send = (LuckyButton) findViewById(R.id.lb_contactus_send);
        lb_contactus_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendContactUs();
            }
        });

    }

    private void cloudMotion_a(){
        asyncTask=new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //anim one
                        int i1 = new Random().nextInt(5 - 1) + 3;
                        ImageView c_cloud_a_scu = (ImageView) findViewById(R.id.c_cloud_a_scu);
                        Anims.cloudMotion(c_cloud_a_scu,width+30.0f, -1*(width+30.0f),i1*10000);
                        //anim one
                        int i2 = new Random().nextInt(4 - 1) + 2;
                        ImageView c_cloud_b_scu = (ImageView) findViewById(R.id.c_cloud_b_scu);
                        Anims.cloudMotion(c_cloud_b_scu,width+30.0f, -1*(width+30.0f),i2*10000);
                        //anim one
                        int i3 = new Random().nextInt(4 - 1) + 2;
                        ImageView c_cloud_c_scu = (ImageView) findViewById(R.id.c_cloud_c_scu);
                        Anims.cloudMotion(c_cloud_c_scu, -1*(width+30.0f),width+30.0f,i3*10000);
                    }
                });


                return null;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }
        };
        asyncTask.execute();
    }
    private ImageView clouds(int i){
        switch (i){
            case 1:
                return (ImageView) findViewById(R.id.c_cloud_a_scu);
            case 2:
                return (ImageView) findViewById(R.id.c_cloud_b_scu);
            case 3:
                return (ImageView) findViewById(R.id.c_cloud_c_scu);
            default:
                return null;
//                return (ImageView) findViewById(R.id.c_cloud_d_scu);
        }
    }

    private void arangeGraphic_start() {
        final int p30_height= (int) (height*0.3);

        ImageView c_owl= (ImageView) findViewById(R.id.c_fox_scu);

        android.view.ViewGroup.LayoutParams layoutParams = c_owl.getLayoutParams();
        layoutParams.height = p30_height;
        c_owl.setLayoutParams(layoutParams);
    }

    private void sendContactUs()
    {

        ContactUsModel contactUs = new ContactUsModel(getApplicationContext());
//        if(!Utility.isValidEmail(let_contactus_email.getText().toString()))
//        {
//
//            Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_defective_information), Toast.LENGTH_SHORT);
//            return;
//
//        }
        if(Utility.isValidEmail(let_contactus_email.getText().toString()))
        {
            contactUs.setEmail(let_contactus_email.getText().toString());

        }

        if(let_contactus_description.getText().toString() == null || let_contactus_description.getText().toString().trim().length() == 0 )
        {

            Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_defective_information), Toast.LENGTH_SHORT);
            return;
        }
        contactUs.setDescription(let_contactus_description.getText().toString());

        if(let_contactus_title.getText().toString() == null || let_contactus_title.getText().toString().trim().length() == 0 )
        {

//            Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_defective_information), Toast.LENGTH_SHORT);
//            return;
        }
        else
        {
            contactUs.setTitle(let_contactus_title.getText().toString());

        }

        if(let_contactus_tel.getText().toString() != null && let_contactus_tel.getText().toString().trim().length() > 0 )
        {
            if(let_contactus_tel.getText().toString().matches("[0-9]+") && let_contactus_tel.getText().toString().length() > 5)
            {
                contactUs.setTel(let_contactus_tel.getText().toString());

            }
            else
            {
                Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_defective_information), Toast.LENGTH_SHORT);
                return;
            }
        }

        ContactUsController cuc = new ContactUsController(getApplicationContext());
        cuc.addObserver(this);
        cuc.store(contactUs);

        DM=new DialogModel(SettingContactUsActivity.this);
        DM.show();

    }

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
                }
                else
                {
//                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                    DialogPopUpModel.show(SettingContactUsActivity.this,"پیامت با موفقیت ارسال شد","باشه",null, false, false);
//                    DM.dismiss();
//                    Intent i = new Intent(SettingContactUsActivity.this,SettingIndexActivity.class);
//                    SettingContactUsActivity.this.startActivity(i);
//                    Utility.finishActivity(this);
                    clearAllFields();

                }
            }
            else if(arg instanceof ArrayList)
            {

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

    private void clearAllFields()
    {
        let_contactus_tel.setText("");
        let_contactus_title.setText("");
        let_contactus_description.setText("");

    }
}
