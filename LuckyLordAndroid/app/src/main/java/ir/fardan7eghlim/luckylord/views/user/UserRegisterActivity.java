package ir.fardan7eghlim.luckylord.views.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.views.home.FirstPageActivity;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.PushNotificationModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyEditText;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import spencerstudios.com.bungeelib.Bungee;

public class UserRegisterActivity extends BaseActivity implements Observer {

    private Spinner gender;
    private LuckyEditText let_userName;
    private LuckyEditText let_password;
    private LuckyEditText et_password_repeat;
    private LuckyEditText let_email;
    private LuckyEditText let_inviteCode;
    private DialogModel DM;
    private Context cntx;
    LuckyButton lb_register;
//    private boolean textFlag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        arangeGraphic_start();

        new SoundModel(this).stopMusic();

        DM = new DialogModel(this);
        cntx = this;
//        textFlag = false;
        //owl
        ImageView owl= (ImageView) findViewById(R.id.c_owl_r);
        owl.setImageResource(R.drawable.c_owl_anim_a);
        AnimationDrawable anim = (AnimationDrawable) owl.getDrawable();
        anim.start();

        //spinner
        addItemsOnSpinner_gender(null);

        LuckyTextView ltv_login = (LuckyTextView) findViewById(R.id.ltv_login);
        ltv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getCurrentUser() != null)
                {
                    if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                            session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1)
                    {
                        goToLogin();
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(UserRegisterActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);

                        }
                    });
                    }
                }
                else
                {
                    goToLogin();
                }


            }
        });

        lb_register = (LuckyButton) findViewById(R.id.lb_register);
        lb_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();

            }
        });
        lb_register.setEnabled(true);

//        LuckyButton lb_play = (LuckyButton) findViewById(R.id.lb_play);
//        lb_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attemptPlay();
//            }
//        });

        let_userName = (LuckyEditText) findViewById(R.id.et_username_r);
        let_password = (LuckyEditText) findViewById(R.id.et_password_r);
        let_email = (LuckyEditText) findViewById(R.id.et_email_r);
        let_inviteCode = (LuckyEditText) findViewById(R.id.et_inviteCode_r);
        et_password_repeat = (LuckyEditText) findViewById(R.id.et_password_repeat);


//        let_password.setOnTouchListener(new View.OnTouchListener() {
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
//        let_password.addTextChangedListener(new TextWatcher() {
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
//                        let_password.setText("");
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
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        et_password_repeat.setOnTouchListener(new View.OnTouchListener() {
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
//        et_password_repeat.addTextChangedListener(new TextWatcher() {
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
//                        et_password_repeat.setText("");
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
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        final Pattern pAlpha = Pattern.compile("[0-9a-zA-Z,._]+");

//        let_userName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String str = s.toString();
//                String newStr = "";
//                if (str.isEmpty()) {
//                    let_userName.append(newStr);
//                    newStr = "";
//                } else if (!str.equals(newStr)) {
//                    // Replace the regex as per requirement
//                    newStr = str.replaceAll("\'\";=+-", "");
//                    let_userName.setText(newStr);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                if (pAlpha.matcher(let_userName.getText()).matches()) {
////                    // you allowed it
////                } else {
////                    // you don't allow it
////                    String text = let_userName.getText().toString();
////                    let_userName.setText(text.substring(0, text.length() - 1));
////
////                    Utility.displayToast(UserRegisterActivity.this,"حرف غیر مجاز",Toast.LENGTH_SHORT);
////
////                }
//
//            }
//        });

    }

//    private void attemptPlay() {
////        DM=new DialogModel(UserRegisterActivity.this);
//        DM.show();
//
//        UserController uc = new UserController(getApplicationContext());
//        uc.addObserver(this);
//        uc.registerVisitor();
//    }

    private void arangeGraphic_start() {
        int height = getWindowManager().getDefaultDisplay().getHeight();
        final int p20_height= (int) (height*0.2);

        ImageView c_owl= (ImageView) findViewById(R.id.c_owl_r);

        android.view.ViewGroup.LayoutParams layoutParams = c_owl.getLayoutParams();
        layoutParams.height = p20_height;
        c_owl.setLayoutParams(layoutParams);
//        float scale = getResources().getDisplayMetrics().density;
//        int dpAsPixels_right = (int) (20*scale + 0.5f);
//        int dpAsPixels_up = (int) (p20_height*scale + 0.5f);
//        c_owl.setPadding(0,0,50,0);
    }

    //login activity
    public void goToLogin(){
        Intent i = new Intent(UserRegisterActivity.this,UserLoginActivity.class);
        UserRegisterActivity.this.startActivity(i);
    }

    //register activity
    public void attemptRegister() {


        boolean proceed = true;

        if (let_userName.getText().toString().contains("Visitor_") ||
                let_userName.getText().toString().length() > 20 ||
                let_userName.getText().toString().length() < 3) {
            Utility.displayToast(getApplicationContext(), "نام کاربری باید حداقل سه حرف و حداکثر 20 حرف باشه", Toast.LENGTH_SHORT);
//            DialogPopUpModel.show(UserRegisterActivity.this,"نام کاربری نامعتبر","باشه",null);

            proceed = false;
        }

        if (let_password.getText().toString().length() < 5) {
//            DialogPopUpModel.show(UserRegisterActivity.this,"رمز عبور باید بیش از 4 حرف باشه","باشه",null);
            Utility.displayToast(getApplicationContext(), "رمز عبور باید بیش از 4 حرف باشه", Toast.LENGTH_SHORT);
            proceed = false;

        }

        if (!let_password.getText().toString().equals(et_password_repeat.getText().toString())) {
//            DialogPopUpModel.show(UserRegisterActivity.this,"رمزا یکی نیستن!","باشه",null);
            Utility.displayToast(getApplicationContext(), "رمزا یکی نیستن!", Toast.LENGTH_SHORT);


            proceed = false;

        }


        UserModel user = new UserModel();
        user.setUserName(let_userName.getText().toString());
        user.setPassword(let_password.getText().toString());
//        if(let_email.getText().toString().equals("") || !Utility.isValidEmail(let_email.getText().toString()))
//        {
//            Utility.displayToast(getApplicationContext(),"ایمیلت مشکل داره",Toast.LENGTH_SHORT);
//
//            proceed = false;
//        }
        if (!let_email.getText().toString().equals("") && Utility.isValidEmail(let_email.getText().toString()))
        {
            user.setEmail(let_email.getText().toString());

        }

        if( gender.getSelectedItem().toString().equals("پسر"))
        {
            user.setGender( UserModel.Male);

        }
        else
        {
            user.setGender( UserModel.Female);
        }
//        if(!let_inviteCode.getText().toString().equals(""))
//        {
////            if(Integer.parseInt(let_inviteCode.getText().toString()) >0)
        user.setInviteCode(let_inviteCode.getText().toString());

//        }
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
//        DM=new DialogModel(UserRegisterActivity.this);
        if(proceed)
        {
            DM.show();

            UserController uc = new UserController(getApplicationContext());
            uc.addObserver(this);

            lb_register.setEnabled(false);
            uc.register(user);
        }

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner_gender(String item) {
        gender = (Spinner) findViewById(R.id.gender_spnr_r);
        List<String> list = new ArrayList<String>();
        list.add("پسر");
        list.add("دختر");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_01,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(dataAdapter);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
        DM.hide();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(  !lb_register.isEnabled())
                    lb_register.setEnabled(true);
            }
        });

        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
                else{
                    UserModel user = new UserModel();
                    user.setUserName(let_userName.getText().toString());
                    user.setPassword(let_password.getText().toString());
                    user.setEmail(let_email.getText().toString());
                    if( gender.getSelectedItem().toString().equals("پسر"))
                    {
                        user.setGender( UserModel.Male);
                        user.setProfilePicture("m#1#1#1#1#1#0#0#0#0#0#0#0");

                    }
                    else
                    {
                        user.setGender( UserModel.Female);
                        user.setProfilePicture("f#1#1#1#1#1#0#0#0#0#0#0#0");

                    }
                    user.setAllowChat("1");

                    session.updateUserSession(user);

                    String[] temp = Utility.tokenDecode(session.getStoredToken());
                    try {
                        JSONObject jobj = new JSONObject(temp[1]);
                        OneSignal.sendTag(PushNotificationModel.USER_ID_KEY,jobj.getString("user_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //meysam - reset refresh time
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE, -30);
                    Date dateBefore30Days = cal.getTime();
                    session.saveItem(SessionModel.KEY_LAST_CHECK_SERVER_TIME,dateBefore30Days.getTime()+"");

                    Utility.displayToast(getApplicationContext(),getString(R.string.msg_RegisterSuccess), Toast.LENGTH_SHORT);
                    Intent intent = new Intent(UserRegisterActivity.this, MainActivity.class);
                    if(FirstPageActivity.FirstPageActivity != null)
                    {
                        FirstPageActivity.FirstPageActivity.finish();
                        FirstPageActivity.FirstPageActivity = null;
                    }

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Utility.finishActivity(this);
                }
            }else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0)== RequestRespondModel.TAG_VISITOR_REGISTER_USER){

                    UserModel user = new UserModel();
                    user.setProfilePicture("m#1#1#1#1#1#0#0#0#0#0#0#0");
                    session.updateUserSession(user);

                    String[] temp = Utility.tokenDecode(session.getStoredToken());
                    try {
                        JSONObject jobj = new JSONObject(temp[1]);
                        OneSignal.sendTag(PushNotificationModel.USER_ID_KEY,jobj.getString("user_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(UserRegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Utility.finishActivity(this);
                }
//                if(((ArrayList) arg).get(0) instanceof LanguageModel){
//                    languages= (ArrayList<LanguageModel>) arg;
//                    addItemsOnSpinner_language();
//                }

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

    public void onDestroy() {

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

//        new SoundModel(this).stopMusic();
//        DM.dismiss();
        super.onDestroy();

    }

    public void onPause() {

//        new SoundModel(this).stopMusic();
        DM.dismiss();
        super.onPause();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.slideUp(this); //fire the slide left animation
    }


}
