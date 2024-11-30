package ir.fardan7eghlim.luckylord.views.user;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.services.ChatService;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.views.home.FirstPageActivity;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyEditText;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import spencerstudios.com.bungeelib.Bungee;

public class UserLoginActivity extends BaseActivity implements Observer {


    private LuckyEditText mUserNameView;
    private LuckyEditText mPasswordView;
    private DialogModel DM;
    private View mProgressView;
    private View mLoginFormView;
//    private boolean textFlag;

    private Context cntx;
//    private SessionModel session;

    private UserController mUC = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

//        textFlag = false;

        DM=new DialogModel(UserLoginActivity.this);

        new SoundModel(this).stopMusic();
        // Set up the login form.
        mUserNameView = (LuckyEditText) findViewById(R.id.et_username_l);
//        populateAutoComplete();

        mPasswordView = (LuckyEditText) findViewById(R.id.et_password_l);

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("login_activity_broadcast"));

//        mPasswordView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();
//                String localeString = ims.getLocale();
//                Locale locale = new Locale(localeString);
//                String currentLanguage = locale.getDisplayLanguage();
//                if(!currentLanguage.startsWith("en"))
//                {
//                    Utility.displayToast(getApplicationContext(),"برا رمز صفحه کلید رو انگلیسی کن!!",Toast.LENGTH_SHORT);
//                }
//                return false;
//            }
//        });
//        mPasswordView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!textFlag)
//                {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();
//                    String localeString = ims.getLocale();
//                    Locale locale = new Locale(localeString);
//                    String currentLanguage = locale.getDisplayLanguage();
//                    if(!currentLanguage.startsWith("en"))
//                    {
//                        Utility.displayToast(getApplicationContext(),"برا رمز صفحه کلید رو انگلیسی کن!!",Toast.LENGTH_SHORT);
//                        textFlag = true;
//                        mPasswordView.setText("");
//
//                    }
//                }
//                else
//                {
//                    textFlag = false;
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mPasswordView.setOnEditorActionListener(this);

        cntx = this;

        LuckyButton lb_login = (LuckyButton) findViewById(R.id.lb_Login);
        lb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
//                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                    attemptLogin();
//                }
//                else
//                {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogPopUpModel.show(UserLoginActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
//
//                        }
//                    });
//
//                }


            }
        });

        TextView btn_forgetPassword_ul= (TextView) findViewById(R.id.btn_forgetPassword_ul);
        btn_forgetPassword_ul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this,UserForgetPasswordActivity.class);
                UserLoginActivity.this.startActivity(i);
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin()
    {
//        if (mAuthTask != null)
//        {
//            return;
//        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !Utility.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            UserModel user = new UserModel();
            user.setUserName(userName);
            user.setPassword(password);
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            DM.show();

            UserController uc = new UserController(cntx);
            mUC=uc;
            uc.addObserver(this);

            uc.login(user,cntx);
//            mAuthTask = new UserLoginTask(userName, password);
//            mAuthTask.execute((Void) null);
        }
    }

//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (v.getId() == R.id.et_password_l || v.getId() == EditorInfo.IME_NULL) {
//
//            attemptLogin();
//        }
//        return true;
//    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {

        DM.hide();

        if(arg != null) {
            if (arg instanceof Boolean) {
                if(arg.equals(true))
                {
                    if(session.isLoggedIn())
                    {
                        Intent i = new Intent(UserLoginActivity.this, MainActivity.class);
                        UserLoginActivity.this.startActivity(i);
                        if(FirstPageActivity.FirstPageActivity != null)
                        {
                            FirstPageActivity.FirstPageActivity.finish();
                            FirstPageActivity.FirstPageActivity = null;
                        }

                        if(ChatService.cc == null && session.getCurrentUser().getUserName() != null && session.getCurrentUser().getAllowChat().equals("1"))
                        {
                            Intent msgIntent = new Intent(this, ChatService.class);
                            startService(msgIntent);
                        }

                        Utility.finishActivity(this);
                    }
                    else
                    {
                        Utility.displayToast(getApplicationContext(),getString(R.string.dlg_OperationFail), Toast.LENGTH_SHORT);
                    }
                }
                else
                {
                    Utility.displayToast(getApplicationContext(),getString(R.string.dlg_OperationFail), Toast.LENGTH_SHORT);
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
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.slideDown(this); //fire the slide left animation
    }

    public void onDestroy() {

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }


        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        super.onDestroy();

    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if(intent.getStringExtra("show_forget_password_success_dialog") != null)
            {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(UserLoginActivity.this,"لطفا به آدرس ایمیل که ثبت کردی برو و روی لیک ارسالی کلیک کن تا رمز جدیدت ثبت بشه، پوشه اسپم هم چک کن ",getString(R.string.btn_OK),null,false,true);
                        }
                    });
                }


        }
    };


}
