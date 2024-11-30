package ir.fardan7eghlim.luckylord.views.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.PushNotificationModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.user.MakeAvatarActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class SettingEditProfileActivity extends BaseActivity implements Observer {

    Context context=this;
    private DialogModel DM;
    private int PICK_IMAGE_REQUEST = 1;
    private PersianDatePickerDialog picker;
    private String birthday_date=null;
    private String phone=null;
    private LinearLayout box_birthday_sep;
    private Button birthday_date_ma;
    private Avatar avatar;
//    private SessionModel session;
    private UserController uc;
    private Spinner gender;
    private String genderForChange;
    private String emailForChange;
//    private boolean textFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_edit_profile);

//        textFlag = false;

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("edit_activity_broadcast"));

        //spinner
        addItemsOnSpinner_gender();

        genderForChange = null;
        emailForChange = null;

        // perform the user login attempt.
        DM=new DialogModel(SettingEditProfileActivity.this);

//        session = new SessionModel(getApplicationContext());

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

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        birthday_date_ma= (Button) findViewById(R.id.btn_birthday_sep);
        birthday_date_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(v);
            }
        });

        LuckyButton lb_change_password= (LuckyButton) findViewById(R.id.lb_change_password);
        lb_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });

        LuckyButton makeAvatar_btn_sep= (LuckyButton) findViewById(R.id.makeAvatar_btn_sep);
        makeAvatar_btn_sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingEditProfileActivity.this,MakeAvatarActivity.class);
                SettingEditProfileActivity.this.startActivity(i);
                System.gc();
            }
        });

        //tel
        LinearLayout box_tel_sep= (LinearLayout) findViewById(R.id.box_tel_sep);
        if(session.getCurrentUser().getTel()==null || (session.getCurrentUser().getTel()!=null && session.getCurrentUser().getTel().equals("null"))){
            box_tel_sep.setVisibility(View.VISIBLE);
            Button btn_submit_tel= (Button) findViewById(R.id.btn_submit_tel_sep);
            btn_submit_tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitPhone();
                }
            });
        }else{
            box_tel_sep.setVisibility(View.GONE);
        }

        //birthday
        box_birthday_sep= (LinearLayout) findViewById(R.id.box_birthday_sep);
        if(session.getCurrentUser().getBirthDate() != null && !session.getCurrentUser().getBirthDate().equals("null")){ //#meysam - set true if user has already selected his/her birthday
            box_birthday_sep.setVisibility(View.GONE);
        }else{
            box_birthday_sep.setVisibility(View.VISIBLE);
        }

        //phone
        EditText tel= (EditText) findViewById(R.id.tel_sep);
        if(session.getCurrentUser().getTel() != null && !session.getCurrentUser().getTel().equals("null")){ //#meysam - set true if user has already selected his/her tek
            tel.setText(Utility.enToFa(session.getCurrentUser().getTel()));
        }


        //email
        TextView emial= (TextView) findViewById(R.id.email_sep);
        if(session.getCurrentUser().getEmail()!=null && !session.getCurrentUser().getEmail().equals("null")){
            emial.setText(session.getCurrentUser().getEmail());
        }
        Button btn_edit_email= (Button) findViewById(R.id.btn_edit_email_sep);
        btn_edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView email= (TextView) findViewById(R.id.email_sep);

                if(!session.getCurrentUser().getEmail().equals(email.getText().toString()))
                {
                    // meysam - for editing email address
                    if(Utility.isValidEmail(email.getText().toString()))
                    {
                        DM.show();
                        emailForChange = email.getText().toString();
                        uc.updateEmail(email.getText().toString());

                    }
                }

            }
        });

        Button btn_edit_gender= (Button) findViewById(R.id.btn_edit_gender);
        btn_edit_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( gender.getSelectedItem().toString().equals("پسر"))
                {
                    genderForChange = UserModel.Male;

                }
                else
                {
                    genderForChange = UserModel.Female;
                }
                String current_gender = session.getCurrentUser().getGender().equals("1")?UserModel.Female:UserModel.Male;
                // meysam - check if gender is different from session
                if(!current_gender.equals(genderForChange))
                {
                    DialogPopUpModel.show(SettingEditProfileActivity.this,"اطمینان داری؟ ",getString(R.string.btn_Yes),getString(R.string.btn_No), true, false);
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while(DialogPopUpModel.isUp()){
                                    Thread.sleep(1000);
                                }
                                if(!DialogPopUpModel.isUp()){
                                    Thread.currentThread().interrupt();//meysam 13960525
                                    if(DialogPopUpModel.dialog_result==1){
                                        // meysam - submit new gender
                                        ChangeGender();
                                    }
                                }

                                DialogPopUpModel.hide();
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
                else
                {
                    DialogPopUpModel.show(SettingEditProfileActivity.this,"این که هستی!",getString(R.string.btn_OK),null, false, false);
                }
                ///////////////////////////////////////////////////////////


            }
        });

    }
    //persian date picker
    public void showCalendar(View v) {
        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("انتخاب کن")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMaxYear(1420)
                .setMinYear(1301)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {

                        birthday_date=persianCalendar.getPersianYear() + "/" + zeroAdder(persianCalendar.getPersianMonth()) + "/" + zeroAdder(persianCalendar.getPersianDay());
                        final Button birthday_date_ma= (Button) findViewById(R.id.btn_birthday_sep);
                        birthday_date_ma.setText(birthday_date);
                        DialogPopUpModel.show(SettingEditProfileActivity.this,"آیا از ثبت تاریخ "+birthday_date+" اطمینان دارید؟","بله","خیر",true, false);
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    while(DialogPopUpModel.isUp()){
                                        Thread.sleep(1000);
                                    }
                                    if(!DialogPopUpModel.isUp()){
                                        Thread.currentThread().interrupt();//meysam 13960525
                                        if(DialogPopUpModel.dialog_result==1){
                                            // meysam - submit birthday
//                                            DM.show();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DM.show();
                                                }
                                            });
                                            uc.updateBirthDate(Utility.convertDatePersian2Gorgeian(birthday_date));

                                        }
                                        else
                                        {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    birthday_date_ma.setText("");
                                                }
                                            });
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

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }
    private String zeroAdder(int t){
        String temp=t+"";
        if(t<10)
            temp="0"+temp;
        return temp;
    }
    //submitPhone
    public void submitPhone(){
        final EditText tel= (EditText) findViewById(R.id.tel_sep);
        if(tel.getText().toString().length()==11){
            // meysam - sumbit tel
            phone = Utility.faToEn(tel.getText().toString());
            DM.show();
            uc.updatePhone(tel.getText().toString());
        }else{
            DialogPopUpModel.show(context,"شماره تلفن وارد شده بایستی 11رقم باشه","متوجه شدم",null, false, false);
        }
    }
    //ChangePassword
    public void ChangePassword(){
        final EditText old_password= (EditText) findViewById(R.id.old_password_sep);
        final EditText new_password= (EditText) findViewById(R.id.new_password_sep);
        final EditText new2_password= (EditText) findViewById(R.id.new2_password_sep);
//        ///////////////////////////////////////meysam - check be english/////////////////////////
//        old_password.setOnTouchListener(new View.OnTouchListener() {
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
//        old_password.addTextChangedListener(new TextWatcher() {
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
//                        old_password.setText("");
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
//        ///////////////
//        new_password.setOnTouchListener(new View.OnTouchListener() {
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
//        new_password.addTextChangedListener(new TextWatcher() {
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
//                        new_password.setText("");
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
//        //////////////
//        new2_password.setOnTouchListener(new View.OnTouchListener() {
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
//        new2_password.addTextChangedListener(new TextWatcher() {
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
//                        new2_password.setText("");
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
//
//        //////////////////////////////////////////////////////////////////////////////////




        if(!old_password.getText().toString().isEmpty() && !new_password.getText().toString().isEmpty() && !new2_password.getText().toString().isEmpty()){
            if(new_password.getText().toString().equals(new2_password.getText().toString())){
                //change password
                // meysam - sumbit tel
                DM.show();
                uc.updatePassword(old_password.getText().toString(),new_password.getText().toString());
            }else{
                DialogPopUpModel.show(context,"رمزهای جدید شبیه به هم نیستن","متوجه شدم",null, false, false);
            }
        }else{
            DialogPopUpModel.show(context,"تمامی جاها رو باید پر کنید","متوجه شدم",null, false, false);
        }
    }

    //ChangePassword
    public void ChangeGender(){

        showLoading();
        uc.updateGender(genderForChange);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner_gender() {
        gender = (Spinner) findViewById(R.id.spn_gender);
        List<String> list = new ArrayList<String>();
        list.add("پسر");
        list.add("دختر");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_01,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(dataAdapter);

        String current_gender = session.getCurrentUser().getGender().equals("1") ?UserModel.Female:UserModel.Male;
        int spinnerPosition = dataAdapter.getPosition(current_gender.equals(UserModel.Female)?"دختر":"پسر");

        //set the default according to value
        gender.setSelection(spinnerPosition);
    }

    private void showLoading()
    {
        Intent intent = new Intent("edit_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(SettingEditProfileActivity.this).sendBroadcast(intent);
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String isLoading = intent.getStringExtra("loading");
            if(isLoading.equals("true"))
            {
                DM.show();
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
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
    }
    public void onDestroy() {
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);
        // Unregister since the activity is about to be closed.
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysam‌BaseBroadcastReceiver);
        super.onDestroy();

    }
    @Override
    public void update(Observable o, Object arg) {
        DM.hide();

        if(arg != null) {
            if (arg instanceof Boolean) {
                if(arg.equals(true))
                {
                    DialogPopUpModel.show(SettingEditProfileActivity.this,getString(R.string.dlg_OperationSuccess),getString(R.string.btn_OK),null, false, false);

                }
                else
                {
                    DialogPopUpModel.show(SettingEditProfileActivity.this,getString(R.string.dlg_OperationFail),getString(R.string.btn_OK),null, false, false);

                }
            } else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UPDATE_EMAIL_USER))
                {
                    UserModel user = new UserModel();
                    user.setEmail(emailForChange);
                    session.updateUserSession(user);
                    emailForChange = null;

                    DialogPopUpModel.show(context,context.getString(R.string.msg_OperationSuccess),context.getString(R.string.btn_OK),null, false, false);

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UPDATE_BIRTH_DATE_USER))
                {
                    DialogPopUpModel.show(context,context.getString(R.string.msg_OperationSuccess),context.getString(R.string.btn_OK),null, false, false);
                    box_birthday_sep.setVisibility(View.GONE);

                    session.saveItem(SessionModel.KEY_BIRTH_DATE, birthday_date);
                    OneSignal.sendTag(PushNotificationModel.BIRTHDAY_KEY,birthday_date.replaceAll("[//]",""));


                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UPDATE_PASSWORD_USER))
                {

                  DialogPopUpModel.show(context,context.getString(R.string.msg_OperationSuccess),context.getString(R.string.btn_OK),null, false, false);

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UPDATE_PHONE_USER))
                {

                    session.saveItem(SessionModel.KEY_TEL, phone);
                    phone = null;
                    DialogPopUpModel.show(context,context.getString(R.string.msg_OperationSuccess),context.getString(R.string.btn_OK),null, false, false);

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UPDATE_GENDER_USER))
                {
                    //meysam - change gender and profile image in session
                    UserModel user = new UserModel();
                    if(genderForChange.equals(UserModel.Male))
                        user.setProfilePicture("m#1#1#1#1#1#0#0#0#0#0#0#0");
                    else
                        user.setProfilePicture("f#1#1#1#1#1#0#0#0#0#0#0#0");
                    user.setGender(genderForChange == UserModel.Female?"1":"0");
                    session.updateUserSession(user);

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

                    genderForChange = null;
                    DialogPopUpModel.show(context,context.getString(R.string.msg_OperationSuccess),context.getString(R.string.btn_OK),null, false, false);

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
                    DialogPopUpModel.show(SettingEditProfileActivity.this,new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())),getString(R.string.btn_OK),null, false, false);
//                    Utility.displayToast(getApplicationContext(),new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            }
            else
            {
                DialogPopUpModel.show(SettingEditProfileActivity.this,getString(R.string.error_weak_connection),getString(R.string.btn_OK),null, false, false);
//                Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        }
        else
        {
            DialogPopUpModel.show(SettingEditProfileActivity.this,getString(R.string.error_weak_connection),getString(R.string.btn_OK),null, false, false);
//            Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

}
