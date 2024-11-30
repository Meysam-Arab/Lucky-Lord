package ir.fardan7eghlim.luckylord.views.lattery;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.DrawController;
import ir.fardan7eghlim.luckylord.controllers.MessageController;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.DrawModel;
import ir.fardan7eghlim.luckylord.models.MessageModel;
import ir.fardan7eghlim.luckylord.models.PushNotificationModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_latary;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_message;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_user;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.HazelLevelQuestionActivity;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.message.MessageIndexActivity;
import ir.fardan7eghlim.luckylord.views.settings.SettingContactUsActivity;
import ir.fardan7eghlim.luckylord.views.settings.SettingIndexActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import spencerstudios.com.bungeelib.Bungee;

public class LatteryIndexActivity extends BaseActivity implements Observer {

    private CustomAdapterList_latary CALL;
    private CustomAdapterList_user CALU;
    private int skip=0;
    private ArrayList<DrawModel> lataries;
    private boolean is_ended=false;
    private DialogModel DM;
    private Dialog winners_dialog;
    private ListView lv;
    private View footerView;
    private static String LotteryCost = "0";
    DrawController dc;
    private boolean inCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lattery_index);
        dc = new DrawController(LatteryIndexActivity.this);
        footerView = ((LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        DM=new DialogModel(LatteryIndexActivity.this);
        lataries=new ArrayList<DrawModel>();
        lv = (ListView) findViewById(R.id.draws_li);

        mainTask();

        inCreate = true;

        Button fb= (Button) findViewById(R.id.first_btn_li);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!is_ended){
                    lataries.clear();
                    lv.setAdapter(null);
                    is_ended=true;
                    DM.show();
//                    DrawController dc = new DrawController(LatteryIndexActivity.this);
                    dc.addObserver(LatteryIndexActivity.this);
                    skip=0;
                    dc.index(skip,1);
                }
            }
        });
        Button sb= (Button) findViewById(R.id.second_btn_li);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_ended){
                    lataries.clear();
                    lv.removeFooterView(footerView);
                    lv.setAdapter(null);
                    is_ended=false;
                    DM.show();
//                    DrawController dc = new DrawController(LatteryIndexActivity.this);
                    dc.addObserver(LatteryIndexActivity.this);
                    skip=0;
                    dc.index(skip,0);
                }
            }
        });
        Button tb= (Button) findViewById(R.id.third_btn_li);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LatteryIndexActivity.this,SettingContactUsActivity.class);
                LatteryIndexActivity.this.startActivity(i);
            }
        });
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
                    Intent i = new Intent(LatteryIndexActivity.this,MainActivity.class);
                    LatteryIndexActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_INDEX_DRAW))
                {
                    if(is_ended) {
                        addFooterView();
                        List<DrawModel> temp= (ArrayList<DrawModel>) ((ArrayList) arg).get(1);
                        if(temp.size()<10){
                            lv.removeFooterView(footerView);
                        }
                        lataries.addAll(temp);
                    }else{
                        lataries = (ArrayList<DrawModel>) ((ArrayList) arg).get(1);
                    }
                    fillList(lataries);
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_WINNERS_DRAW))
                {
                    ProgressBar pb= (ProgressBar) winners_dialog.findViewById(R.id.progressBar_wd);
                    pb.setVisibility(View.GONE);
                    ListView w_lv= (ListView) winners_dialog.findViewById(R.id.winner_list_wd);
                    ArrayList<UserModel> users= (ArrayList<UserModel>) ((ArrayList) arg).get(1);
                    if(users.size()>0){
                        CALU=new CustomAdapterList_user(this, new ArrayList<Object>(users), RequestRespondModel.TAG_WINNERS_DRAW);
                        w_lv.setVisibility(View.VISIBLE);
                        w_lv.setAdapter(CALU);

                        w_lv.setRecyclerListener(new AbsListView.RecyclerListener() {
                            @Override
                            public void onMovedToScrapHeap(View view) {
                                // Release strong reference when a view is recycled
                                new Avatar().clear(view);
                            }
                        });

                        w_lv.invalidateViews();
                    }else{
                        winners_dialog.hide();
                        Utility.displayToast(getApplicationContext(),"این قرعه کشی برنده نداشته ", Toast.LENGTH_SHORT);
                    }
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_PARTICIPATE_DRAW))
                {
                    if(CustomAdapterList_latary.participated != null)
                    {
//                        SessionModel session = new SessionModel(getApplicationContext());
                        if(CustomAdapterList_latary.participated)
                        {
                            //meysam - user participated in lottery
                            CustomAdapterList_latary.participated = null;
                            session.decreaseHazel(CustomAdapterList_latary.currentDraw.getCost());
                            DialogPopUpModel.show(LatteryIndexActivity.this,CustomAdapterList_latary.currentDraw.getCost().toString()+" " + LatteryIndexActivity.this.getString(R.string.msg_HazelSubbedSuccessfully) ,LatteryIndexActivity.this.getString(R.string.btn_OK),null, false, false);
                            OneSignal.sendTag(PushNotificationModel.PRE_DRAW_KEY+CustomAdapterList_latary.currentDraw.getId(),"1");


                        }
                        else
                        {
                            //meysam - user leaved lottery
                            session.increaseHazel(new Integer(new Integer(CustomAdapterList_latary.currentDraw.getCost())/2).toString());
                            DialogPopUpModel.show(LatteryIndexActivity.this,new Integer(new Integer(CustomAdapterList_latary.currentDraw.getCost())/2).toString()+" " + LatteryIndexActivity.this.getString(R.string.msg_HazelAddedSuccessfully) ,LatteryIndexActivity.this.getString(R.string.btn_OK),null, false, false);
                            OneSignal.sendTag(PushNotificationModel.PRE_DRAW_KEY+CustomAdapterList_latary.currentDraw.getId(),"");
                        }
                        CustomAdapterList_latary.currentDraw = null;
                        mainTask();
                    }
                    else
                    {
                        DialogPopUpModel.show(LatteryIndexActivity.this,LatteryIndexActivity.this.getString(R.string.msg_OperationSuccess),LatteryIndexActivity.this.getString(R.string.btn_OK),null, false, false);
                    }

                }
                else
                {
                    Utility.displayToast(getApplicationContext(),getString(R.string.msg_MessageNotSpecified), Toast.LENGTH_SHORT);
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

    //fill list of draws
    private void fillList(ArrayList<DrawModel> lataries) {
        //make list
//        SessionModel sm=new SessionModel(LatteryIndexActivity.this);
        UserModel user=session.getCurrentUser();

        winners_dialog=new Dialog(LatteryIndexActivity.this);
        winners_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        winners_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        winners_dialog.setContentView(R.layout.winners_dialog);
        ProgressBar pb= (ProgressBar) winners_dialog.findViewById(R.id.progressBar_wd);
        ListView w_lv= (ListView) winners_dialog.findViewById(R.id.winner_list_wd);
        Button btn_close_wd= (Button) winners_dialog.findViewById(R.id.btn_close_wd);
        btn_close_wd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winners_dialog.hide();
            }
        });

        CALL=new CustomAdapterList_latary(this,DM, new ArrayList<Object>(lataries), RequestRespondModel.TAG_INDEX_DRAW,is_ended,user,winners_dialog,pb,w_lv);
        lv.setAdapter(CALL);

        lv.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
                final ImageView avatar_in_row = (ImageView) view.findViewById(R.id.avatar_in_row);
                avatar_in_row.setImageBitmap(null);
            }
        });

        lv.invalidateViews();
    }

    private void addFooterView(){
        lv.addFooterView(footerView);
        LinearLayout footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        footer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utility().isNetworkAvailable(getApplicationContext())) {
                    skip += 10;
                    mainTask();
                } else {
                    Utility.displayToast(getApplicationContext(), getString(R.string.error_no_connection), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void mainTask() {
        lv.removeFooterView(footerView);

        DM.show();
//        DrawController dc = new DrawController(getApplicationContext());
        dc.addObserver(this);
        dc.index(skip,is_ended?1:0);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Bungee.slideDown(this); //fire the slide left animation
    }
    public void onResume() {

        if(!inCreate)
        {
            dc = new DrawController(getApplicationContext());
            dc.addObserver(this);
        }
        else
            inCreate = false;

        super.onResume();
    }
    @Override
    public void onPause() {

        inCreate = false;


//        CustomAdapterList_latary.setInflater(null);
//        dc.setCntx(null);
////        dc.deleteObserver(this);
//        dc.deleteObservers();
//        dc = null;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        CustomAdapterList_latary.setInflater(null);
        dc.setCntx(null);
//        dc.deleteObserver(this);
        dc.deleteObservers();
        dc = null;
        super.onDestroy();
    }

    //meysam - 13960625
//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(LatteryIndexActivity.this,MainActivity.class);
//
//        LatteryIndexActivity.this.startActivity(i);
//        super.onBackPressed();
//    }
}
