package ir.fardan7eghlim.luckylord.views.user;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.controllers.UserFriendController;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_user;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;

public class FriendListActivity extends BaseActivity implements Observer {

    private ListView list_users;
    private CustomAdapterList_user CALU;
    private DialogModel DM;
    private String request_type;
    private UserFriendController ufc;
    private UserController uc;
    private int skip;
    private View footerView;
    private LuckyTextView ltv_friends_title;
    //    private String lastTaskType;
    private String userNameForSearch;
    //    private Integer lastRequestedListStatusCode;
    private DatabaseHandler db;
    ArrayList<UserModel> users;
    private boolean isInCreate;
    private Bundle extras;

    private Integer matchType;
    private Integer opponentType;

    private Handler customHandler = new Handler();
    Integer delayCount = 0;
    private int timeWaitBeforeRefresh = 10000;
    private Runnable refreshTimerThread = new Runnable() {

        public void run() {

            delayCount += 1;

            if(delayCount >= 6)
            {
//                timeWaitBeforeRefresh = 10000;
                delayCount = 1;
            }
//            else
//            {
//                timeWaitBeforeRefresh = timeWaitBeforeRefresh * 2;
//
//            }
            //meysam refresh match list .. call match index
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler = null;

            //meysam - commented in 13970119
//            users.clear();
//            Toast.makeText(FriendListActivity.this,"ttt",Toast.LENGTH_SHORT).show();
            initialize(false);

        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        footerView = ((LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
//        lastTaskType = null;
//        lastRequestedListStatusCode = null;

        matchType = null;
        opponentType = null;

        db = new DatabaseHandler(this);
        isInCreate = true;

        users = new ArrayList<>();

        ufc = new UserFriendController(this);
        ufc.addObserver(this);

        uc = new UserController(FriendListActivity.this);
        uc.addObserver(this);

        list_users = (ListView) findViewById(R.id.lv_firendList);
        ltv_friends_title = (LuckyTextView) findViewById(R.id.ltv_friends_title);
        DM=new DialogModel(FriendListActivity.this);
        extras = getIntent().getExtras();
        request_type = extras.getString("list_type");

        if(extras.containsKey("match_type"))
        {
            matchType = extras.getInt("match_type");
            opponentType = extras.getInt("opponent_type");
        }

        initialize(true);

        if(!request_type.equals("search_users") && !request_type.equals("recommended_users"))
        {
            if(customHandler == null)
                customHandler = new Handler();
            customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
        }


    }

    private void initialize(Boolean showLoading)
    {
//        Toast.makeText(FriendListActivity.this,"ttt",Toast.LENGTH_SHORT).show();

        skip = 0;
        //meysam - commented in 13970119
//        users = new ArrayList<>();
//        list_users.setAdapter(null);
        //////////////////////////////////
        if(showLoading)
            DM.show();
        if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED))
        {

//            if(showLoading)
//                DM.show();
            ltv_friends_title.setText("لیست افراد بلاک شده");

//            lastTaskType = "list";
//            lastRequestedListStatusCode = UserFriendModel.USER_FRIEND_STATUS_BLOCKED;
//            ufc.list(UserFriendModel.USER_FRIEND_STATUS_BLOCKED);

            users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED,skip);
            if(users.size() > 0)
            {

                //meysam - added in 13970119
                users = new ArrayList<>();
                list_users.setAdapter(null);
                //////////////////////////////
                fillList(users);
            }
            else
            {
//                Utility.displayToast(this,getString(R.string.msg_EmptyList), Toast.LENGTH_SHORT);
                DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);

//                finish();
            }
            if(showLoading)
                DM.hide();
        }
        if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED))
        {

            ltv_friends_title.setText("لیست دوستان شما");

            ufc.sync();

//            users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED,skip);
//            if(users.size() > 0)
//                fillList(users);
//            else
//            {
//                DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
//            }
//            DM.hide();
        }
        if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED))
        {
//            DM.show();
            ltv_friends_title.setText("لیست درخواست های دوستی شما");

            ufc.sync();

//            users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED,skip);
//            if(users.size() > 0)
//                fillList(users);
//            else
//            {
//                DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
//            }
//            DM.hide();
        }
        if(request_type.equals("search_users"))
        {
            userNameForSearch = extras.getString("user_name");
            UserModel user = new UserModel();
            user.setUserName(userNameForSearch);


            DM.show();
            ltv_friends_title.setText("لیست افراد جستجو شده");

//            lastTaskType = "search";
//            lastRequestedListStatusCode = UserFriendModel.USER_FRIEND_STATUS_REQUESTED;
            uc.search(user,skip);

        }
        if(request_type.equals("recommended_users"))
        {
//            DM.show();
            ltv_friends_title.setText("لیست پیشنهاد ها برای شما");

//            lastTaskType = "search";
//            lastRequestedListStatusCode = UserFriendModel.USER_FRIEND_STATUS_REQUESTED;
            ufc.recommend();
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        DM.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(FriendListActivity.this, MainActivity.class);
                    FriendListActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if (arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_SEARCH_USER))
                {
                    addFooterViewSearch();
                    ArrayList<UserModel> usersTmp = (ArrayList<UserModel>) ((ArrayList) arg).get(1);

                    if(usersTmp.size()>0)
                    {
                        //meysam - added in 13970119
                        users = new ArrayList<>();
                        list_users.setAdapter(null);
                        //////////////////////////////

                        users.addAll(usersTmp);
                        if(usersTmp.size()<10)
                            list_users.removeFooterView(footerView);

                        fillSearchList(users);

                    }
                    else
                    {
//                        Utility.displayToast(this,getString(R.string.msg_EmptyList), Toast.LENGTH_SHORT);
                        DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptySearchList), getString(R.string.btn_OK), null, false, true);
//                        FriendListActivity.this.finish();

                    }
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_LIST))
                {
//                    addFooterViewList();
                    ArrayList<UserModel> usersTmp = (ArrayList<UserModel>) ((ArrayList) arg).get(1);
                    if(usersTmp.size()>0)
                    {

//                        users.addAll(usersTmp);
//                        if(usersTmp.size()<10)
//                            list_users.removeFooterView(footerView);
                        //meysam - added in 13970119
                        users = new ArrayList<>();
                        list_users.setAdapter(null);
                        //////////////////////////////
                        fillList(usersTmp);

                    }
                    else
                    {
//                        Utility.displayToast(this,getString(R.string.msg_EmptyList), Toast.LENGTH_SHORT);
                        DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);

//                        FriendListActivity.this.finish();
                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_RECOMMEND))
                {
//                    addFooterViewList();
                    list_users.setAdapter(null);
                    ArrayList<UserModel> usersTmp = (ArrayList<UserModel>) ((ArrayList) arg).get(1);
                    if(usersTmp.size()>0)
                    {

//                        users.addAll(usersTmp);
//                        if(usersTmp.size()<10)
//                            list_users.removeFooterView(footerView);
                        //meysam - added in 13970119
                        users = new ArrayList<>();
                        list_users.setAdapter(null);
                        //////////////////////////////
                        fillList(usersTmp);

                    }
                    else
                    {
//                        Utility.displayToast(this,getString(R.string.msg_EmptyList), Toast.LENGTH_SHORT);
                        DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyRecommendedList), getString(R.string.btn_OK), null, false, true);

//                        FriendListActivity.this.finish();
                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST))
                {
                    Boolean result = (Boolean) ((ArrayList) arg).get(1);
                    if(result)
                    {
                        DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_RequestSent), getString(R.string.btn_OK), null, false, true);
                        FriendListActivity.this.finish();

                    }
                    else
                    {
                        DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_RequestNotSent), getString(R.string.btn_OK), null, false, true);
                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_SYNC))
                {

                    //meysam - change status of recieced user friend records in sqlite...
                    ArrayList<UserFriendModel> userFriends = (ArrayList<UserFriendModel>) ((ArrayList) arg).get(1);
                    if(userFriends.size() > 0)
                    {
                        UserFriendModel.syncFriendsStatusInDb(userFriends,FriendListActivity.this.getApplicationContext());

                        if(db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED).size() == 0)
                            if(session.hasItem(SessionModel.KEY_FRIENDSHIP_SYNC))
                                session.removeItem(SessionModel.KEY_FRIENDSHIP_SYNC);

                        //if Status changed successfully send validate sync request to server
                        ufc.validateSync(userFriends);
                    }
                    if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED))
                    {
                        //meysam - load main list from sqlite
                        ArrayList<UserModel> usersTmp = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED,skip);
                        if(usersTmp.size() > 0)
                        {
                            //meysam - added in 13970119
                            users = new ArrayList<>();
//                            list_users.setAdapter(null);
                            //////////////////////////////

//                            fillList(usersTmp);
                            if(CALU == null)
                            {
                                list_users.setAdapter(null);
                                fillList(usersTmp);
                            }
                            else
                                reFillList(usersTmp);


                        }
                        else
                        {
                            DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
                        }
//                    DM.hide();
                        /////////////////////////////////////////
                    }
                    if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED))
                    {
                        ArrayList<UserModel> usersTmp = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED,skip);
                        if(usersTmp.size() > 0)
                        {
                            //meysam - added in 13970119
                            users = new ArrayList<>();
                            list_users.setAdapter(null);
                            //////////////////////////////

                            fillList(usersTmp);
                        }
                        else
                        {
                            DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
                        }
//                        DM.hide();
                    }

                }
            }
        }
    }

    private void addFooterViewSearch() {
        list_users.addFooterView(footerView);
        LinearLayout footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        footer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utility().isNetworkAvailable(getApplicationContext())) {
                    skip += 10;
                    mainTaskSearch();
                } else {
                    DialogPopUpModel.show(getApplicationContext(),getString(R.string.error_no_connection),getString(R.string.lbl_retry),getString(R.string.lbl_exit), false, false);

                }
            }
        });
    }
    private void addFooterViewList() {
        list_users.addFooterView(footerView);
        LinearLayout footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        footer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utility().isNetworkAvailable(getApplicationContext())) {
                    skip += 10;
                    mainTaskList();
                } else {
                    DialogPopUpModel.show(getApplicationContext(),getString(R.string.error_no_connection),getString(R.string.lbl_retry),getString(R.string.lbl_exit), false, false);

                }
            }
        });
    }

    private void mainTaskSearch() {
        list_users.removeFooterView(footerView);
        DM=new DialogModel(FriendListActivity.this);
        DM.show();

//        if(lastTaskType.equals("list"))
//        {
//            ufc.list(lastRequestedListStatusCode);
//        }
//        if(lastTaskType.equals("search"))
//        {
        UserModel user = new UserModel();
        user.setUserName(userNameForSearch);
        uc.search(user,skip);
//        }
    }
    private void mainTaskList() {
//        list_users.removeFooterView(footerView);



        if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED))
        {
            DM.show();

            users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED,skip);
            if(users.size() > 0)
            {
//                users.addAll(usersTmp);
//                if(usersTmp.size()<10)
//                    list_users.removeFooterView(footerView);
                fillList(users);
            }
            else
            {
                DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
            }
            DM.hide();
        }
        if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED))
        {
            DM.show();
            users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED,skip);
            if(users.size() > 0)
            {
//                users.addAll(usersTmp);
//                if(usersTmp.size()<10)
//                    list_users.removeFooterView(footerView);
                fillList(users);

            }
            else
            {
                DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
            }
            DM.hide();
        }
        if(request_type.equals(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED))
        {
            DM.show();
            users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED,skip);
            if(users.size() > 0)
            {
//                users.addAll(usersTmp);
//                if(usersTmp.size()<10)
//                    list_users.removeFooterView(footerView);
                fillList(users);

            }
            else
            {
                DialogPopUpModel.show(FriendListActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
            }
            DM.hide();
        }

        if(request_type.equals("recommended_users"))
        {
            DM.show();
            ufc.recommend();
        }

    }


    private void fillList(ArrayList<UserModel> usersTmp) {

        usersTmp = UserFriendModel.sortListByOnline(usersTmp);

        //make list
        addFooterViewList();

        users.addAll(usersTmp);
        if(usersTmp.size()<10)
            list_users.removeFooterView(footerView);

        CALU = new CustomAdapterList_user(this, new ArrayList<Object>(users), RequestRespondModel.TAG_USER_FRIEND_LIST, matchType, opponentType);
        list_users.setAdapter(CALU);

        list_users.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
            }
        });

        list_users.invalidateViews();
        if(skip>0)
            list_users.setSelection(skip);
    }

    private void reFillList(ArrayList<UserModel> usersTmp) {

        usersTmp = UserFriendModel.sortListByOnline(usersTmp);

        //make list
        addFooterViewList();



        users.addAll(usersTmp);

        if(users.size()<10)
            list_users.removeFooterView(footerView);

        CALU.updateAdapter(new ArrayList<Object>(users));
        synchronized(list_users.getAdapter()){
            list_users.getAdapter().notify();
        }
        CALU.notifyDataSetChanged();
    }


    private void fillSearchList(ArrayList<UserModel> users) {
        //make list

        CALU = new CustomAdapterList_user(this, new ArrayList<Object>(users), RequestRespondModel.TAG_SEARCH_USER, matchType, opponentType);
        list_users.setAdapter(CALU);

        list_users.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
            }
        });

        list_users.invalidateViews();
        if(skip>0)
            list_users.setSelection(skip);
    }

    @Override
    public void onResume() {

        if(uc == null)
        {
            uc = new UserController(getApplicationContext());
            uc.addObserver(this);
        }
        if(ufc == null)
        {
            ufc = new UserFriendController(getApplicationContext());
            ufc.addObserver(this);
        }
        if(!isInCreate)
        {
            if(!request_type.equals("recommended_users"))
            {
                if(request_type.equals("search_users"))
                {
                    if(users.size()<10)
                        list_users.removeFooterView(footerView);
                }
                else
                {
                    users.clear();
                    //meysam - refill list
                    initialize(true);
                    if(customHandler == null)
                        customHandler = new Handler();
                    customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
                }

            }




        }
        isInCreate = false;
        super.onResume();
    }

    @Override
    public void onPause() {

        if(customHandler != null)
        {
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler = null;
            timeWaitBeforeRefresh = 10000;
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        if(ufc != null)
        {
            ufc.setCntx(null);
            ufc.deleteObservers();
            ufc = null;
        }
        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObserver(this);
            uc = null;
        }
        if(CALU != null)
            CALU.setInflater(null);
        list_users.setAdapter(null);
        users.clear();

        super.onDestroy();
    }

}
