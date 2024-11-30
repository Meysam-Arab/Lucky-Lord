package ir.fardan7eghlim.luckylord.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;

public class UserForgetPasswordActivity extends BaseActivity implements Observer {
    private DialogModel DM;
    private UserController uc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forget_password);

        final EditText et_email_r= (EditText) findViewById(R.id.et_email_r);
        final EditText et_password_r= (EditText) findViewById(R.id.et_password_r);
        final EditText et_password2_r= (EditText) findViewById(R.id.et_password2_r);

        new SoundModel(this).stopMusic();

        DM=new DialogModel(UserForgetPasswordActivity.this);

        uc = new UserController(this);
        uc.addObserver(this);


        Button lb_forgetPass= (Button) findViewById(R.id.lb_forgetPass);
        lb_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean proceed = true;

                if(et_email_r.getText().toString().equals("") || !Utility.isValidEmail(et_email_r.getText().toString()))
                {
                    Utility.displayToast(getApplicationContext(),"ایمیلت مشکل داره",Toast.LENGTH_SHORT);

                    proceed = false;
                }

                if(et_password_r.getText().toString().length() < 5 )
                {
                    Utility.displayToast(getApplicationContext(),"رمز عبور باید بیش از 4 حرف باشه",Toast.LENGTH_SHORT);
                    proceed = false;

                }

                if(!et_password_r.getText().toString().equals(et_password2_r.getText().toString()))
                {
                    Utility.displayToast(getApplicationContext(),"رمزا یکی نیستن!",Toast.LENGTH_SHORT);


                    proceed = false;

                }


                if(proceed)
                {
                    // #meysam - function forgeting password
                    DM.show();
                    uc.forgetPassword(et_email_r.getText().toString(),et_password_r.getText().toString());
                }

            }
        });
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
                    Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }

            }else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0)== RequestRespondModel.TAG_FORGET_PASSWORD_USER){
//                    Utility.displayToast(getApplicationContext(),getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);

                    Intent intent = new Intent("login_activity_broadcast");
                    // You can also include some extra data.
                    intent.putExtra("show_forget_password_success_dialog", "true");
                    LocalBroadcastManager.getInstance(UserForgetPasswordActivity.this).sendBroadcast(intent);

                    // TIP: We should finish this activity for prevent spamming
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
