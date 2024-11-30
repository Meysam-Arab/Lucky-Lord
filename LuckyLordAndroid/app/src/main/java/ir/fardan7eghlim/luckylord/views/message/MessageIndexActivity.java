package ir.fardan7eghlim.luckylord.views.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MessageController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.ChatModel;
import ir.fardan7eghlim.luckylord.models.MessageModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_message;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_user;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class MessageIndexActivity extends BaseActivity implements Observer {

    private CustomAdapterList_message CALM;
    private CustomAdapterList_user CALU;
    private DialogModel DM;
    private int skip=0;
    private ArrayList<MessageModel> messages;
    private ListView lv;
    private View footerView;
    private MessageController mc;
    private LuckyButton chats_btn_li;
    private LuckyButton messages_btn_li;
    private boolean isMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_index);
        chats_btn_li=findViewById(R.id.chats_btn_li);
        messages_btn_li=findViewById(R.id.messages_btn_li);

        DM=new DialogModel(MessageIndexActivity.this);
        DM.show();

        mc = new MessageController(getApplicationContext());
        mc.addObserver(this);

        messages=new ArrayList<MessageModel>();
        lv = (ListView) findViewById(R.id.list_mi);
        footerView = ((LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);

        messages_btn_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isMessage)
                {
                    isMessage=true;
                    //for showing list of messages
                    DM.show();

                    mc.index(skip);
                }


                setChatCount();

            }
        });
       chats_btn_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isMessage)
                {
                    isMessage=false;
                    // for showing list of chats
                    DM.show();
                    messages.clear();
                    lv.setAdapter(null);
                    ArrayList<ChatModel> chats = new DatabaseHandler(getApplicationContext()).getAllChats();
                    if(chats.size() > 0)
                    {
                        fillChatList(chats);
                    }
                    else
                    {
                        DialogPopUpModel.show(MessageIndexActivity.this,getString(R.string.msg_ChatBoxEmpty), getString(R.string.btn_OK), null, false, true);
                        DM.hide();
                    }
                }
                setChatCount();

            }
        });

        setChatCount();

        if(getIntent().hasExtra("isChat"))
        {
            isMessage = false;
            // for showing list of chats
            DM.show();

            ArrayList<ChatModel> chats = new DatabaseHandler(getApplicationContext()).getAllChats();
            if(chats.size() > 0)
            {
                fillChatList(chats);
            }
            else
            {
                DialogPopUpModel.show(MessageIndexActivity.this,getString(R.string.msg_ChatBoxEmpty), getString(R.string.btn_OK), null, false, true);
                DM.hide();
            }
        }
        else
        {
            isMessage = true;
            //for defult showing messages
            mc.index(skip);
        }


    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                new DialogModel(getApplicationContext()).hide(MessageIndexActivity.this);
//            }
//        }, 3000);
        DM.hide();
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(MessageIndexActivity.this,MainActivity.class);
                    MessageIndexActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_INDEX_MESSAGE))
                {

                    addFooterView();
                    List<MessageModel> temp= (List<MessageModel>) ((ArrayList) arg).get(1);
                    if(temp.size()<10){
                        lv.removeFooterView(footerView);
                    }
                    messages.clear();
                    messages.addAll(temp);
                    if(messages.size()<1){
//                        Utility.displayToast(getApplicationContext(),getString(R.string.msg_MesageBoxEmpty), Toast.LENGTH_SHORT);
                        DialogPopUpModel.show(MessageIndexActivity.this,getString(R.string.msg_MesageBoxEmpty), getString(R.string.btn_OK), null, false, true);

                    }
                    fillList(messages);
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_READ_MESSAGE))
                {

                    if((Boolean) ((ArrayList) arg).get(1))
                    {
//                        SessionModel session = new SessionModel(getApplicationContext());
                        Integer unread_count = Integer.valueOf(session.getStringItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT));
                        unread_count--;
                        session.saveItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT,unread_count.toString());
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

    private void addFooterView() {
        lv.addFooterView(footerView);
        LinearLayout footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        footer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utility().isNetworkAvailable(getApplicationContext())) {
                    skip += 10;
                    mainTask();
                } else {
                    DialogPopUpModel.show(getApplicationContext(),getString(R.string.error_no_connection),getString(R.string.lbl_retry),getString(R.string.lbl_exit), false, false);

                }
            }
        });
    }

    //fill list of messages
    private void fillList(ArrayList<MessageModel> messages) {
        //make list
        lv.setAdapter(null);
        CALM=new CustomAdapterList_message(this, new ArrayList<Object>(messages), RequestRespondModel.TAG_INDEX_MESSAGE);
        lv.setAdapter(CALM);
        lv.invalidateViews();
    }

    //fill list of chats
    private void fillChatList(ArrayList<ChatModel> chats) {
        //make list

        lv.setAdapter(null);
        CALU=new CustomAdapterList_user(this, new ArrayList<Object>(chats), "chats");
        lv.setAdapter(CALU);
        lv.invalidateViews();
        DM.hide();
    }

    private void mainTask() {
        lv.removeFooterView(footerView);
        DM=new DialogModel(MessageIndexActivity.this);
        DM.show();

        mc.index(skip);
    }

    private void setChatCount()
    {
        chats_btn_li.setText(" چت "+"("+new DatabaseHandler(getApplicationContext()).getAllChats().size()+")");
    }

    @Override
    public void onDestroy() {

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        CustomAdapterList_message.setInflater(null);
        if(CALU != null)
            CALU.setInflater(null);
        if(CALM != null)
            CALM.setInflater(null);


        mc.setCntx(null);
        mc.deleteObservers();
        mc = null;
        super.onDestroy();
    }

//    //meysam - 13960625
//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(MessageIndexActivity.this,MainActivity.class);
//
//        MessageIndexActivity.this.startActivity(i);
//        super.onBackPressed();
//    }

}
