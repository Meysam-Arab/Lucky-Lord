package ir.fardan7eghlim.luckylord.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.controllers.UserFriendController;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyEditText;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_user;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;

public class UserRankingListActivity extends BaseActivity implements Observer {

    private CustomAdapterList_user CALU;
    private DialogModel DM;
    private int type;
    private ArrayList<UserModel> users;
    private Spinner spinner_kind_of_rank_ur;
    private int fisrt_rank=0;
    private Avatar avatar;
    UserController uc;
    private boolean rankPage=false;
    private boolean isInCreate;
    private DatabaseHandler db;
//    private SessionModel session;
    private String username;
//    private Integer pageState;
//    private static Integer RANKING = 0;
//    private static Integer FRIENDSHIP = 0;

    private LuckyEditText let_search_box;
    private LuckyButton btn_friend_list;
    private LuckyButton btn_friend_list_out_requests;
    private LuckyButton btn_blocked_list;
    private LuckyButton btn_recommended_list;
    private ImageView btn_search;
    private ListView list_recieved_friend_requests;
    private UserFriendController ufc = null;
    private LinearLayout box_friendRequest_rl;

    private Integer requestedFriendshipStatus;

    TextView text_username;
    TextView text_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ranking_list);
        isInCreate = true;

//        type = UserModel.USER_RANK_TYPE_LEVEL;

        db = new DatabaseHandler(getApplicationContext());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btn_friend_list = (LuckyButton) findViewById(R.id.btn_friend_list);
        btn_friend_list_out_requests = (LuckyButton) findViewById(R.id.btn_friend_list_out_requests);
        btn_blocked_list = (LuckyButton) findViewById(R.id.btn_friend_list_blocked);
        btn_recommended_list = (LuckyButton) findViewById(R.id.btn_friend_list_recommend);
        btn_search = (ImageView) findViewById(R.id.btn_search_rl);
        box_friendRequest_rl = (LinearLayout) findViewById(R.id.box_friendRequest_rl);
        text_username = (TextView) findViewById(R.id.text_username);
        text_score = (TextView) findViewById(R.id.text_score);

        let_search_box = (LuckyEditText) findViewById(R.id.let_search_box);
//        int mInputType = let_search_box.getInputType();
//        let_search_box.setInputType(mInputType | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        requestedFriendshipStatus = null;
        list_recieved_friend_requests = (ListView) findViewById(R.id.list_friendRequest_ur);
//        session = new SessionModel(getApplicationContext());
//        session.saveItem(SessionModel.KEY_AD_REWARD,AdvertismentModel.AD_REWARD);
        ufc = new UserFriendController(this);
        ufc.addObserver(this);

        uc = new UserController(this);
        uc.addObserver(this);

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
        if(session.getCurrentUser().getUserName() != null)
            username=session.getCurrentUser().getUserName();
        else
            username=session.getCurrentUser().getVisitorUserName();


        spinner_kind_of_rank_ur = (Spinner) findViewById(R.id.spinner_kind_of_rank_ur);
//        addItemsOnSpinner();

        DM=new DialogModel(UserRankingListActivity.this);
        final ScrollView friendList_layout= (ScrollView) findViewById(R.id.friendList_layout);
        final LinearLayout ranking_layout= (LinearLayout) findViewById(R.id.ranking_layout);

        if(getIntent().getExtras()==null || getIntent().getExtras().getString("friend")==null){
            rankPage=true;
            friendList_layout.setVisibility(View.GONE);
            ranking_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            rankPage=false;
            friendList_layout.setVisibility(View.VISIBLE);
            ranking_layout.setVisibility(View.GONE);
        }
        intialize();

        Button first_btn_li= (Button) findViewById(R.id.first_btn_li);
        first_btn_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rankPage)
                {
                    rankPage=true;
                    intialize();
                    friendList_layout.setVisibility(View.GONE);
                    ranking_layout.setVisibility(View.VISIBLE);
                }

            }
        });
        Button second_btn_li= (Button) findViewById(R.id.second_btn_li);
        second_btn_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rankPage)
                {
                    rankPage=false;
                    intialize();
                    friendList_layout.setVisibility(View.VISIBLE);
                    ranking_layout.setVisibility(View.GONE);
                }

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(let_search_box.getText() != null)
                {
                    String searchText = let_search_box.getText().toString();
                    Intent intent = new Intent(UserRankingListActivity.this, FriendListActivity.class);
                    intent.putExtra("list_type", "search_users");
                    intent.putExtra("user_name", searchText);
                    startActivity(intent);
                }

            }
        });

        btn_friend_list_out_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                {
                    Intent intent = new Intent(UserRankingListActivity.this, FriendListActivity.class);
                    intent.putExtra("list_type", UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED);
                    startActivity(intent);
                }
                else
                {
                    DialogPopUpModel.show(UserRankingListActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                }

            }
        });

        btn_friend_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                {
                    Intent intent = new Intent(UserRankingListActivity.this, FriendListActivity.class);
                    intent.putExtra("list_type", UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);
                    startActivity(intent);
                }
                else
                {
                    DialogPopUpModel.show(UserRankingListActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                }

            }
        });

        btn_blocked_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                {
                    Intent intent = new Intent(UserRankingListActivity.this, FriendListActivity.class);
                    intent.putExtra("list_type", UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED);
                    startActivity(intent);
                }
                else
                {
                    DialogPopUpModel.show(UserRankingListActivity.this,getString(R.string.msg_ComeLater),getString(R.string.btn_OK),null, false,false);
                }
            }
        });

        btn_recommended_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRankingListActivity.this, FriendListActivity.class);
                intent.putExtra("list_type", "recommended_users");
                startActivity(intent);
            }
        });

    }

    private void intialize() {
        if(rankPage){
//            DM.show();
            addItemsOnSpinner();

        }else{
            DM.show();
            requestedFriendshipStatus = UserFriendModel.USER_FRIEND_STATUS_NORMAL;
            ufc.list(UserFriendModel.USER_FRIEND_STATUS_NORMAL);
        }
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        List<String> list = new ArrayList<String>();

        list.add("رتبه بر اساس سطح");
        list.add("رتبه بر اساس شانس");

//        list.add("مشاعیر ترین شاهزاده ها");
//        list.add("خارجی ترین شاهزاده ها");
//        list.add("هنرمند ترین شاهزاده ها");
//        list.add("مذهبی ترین شاهزاده ها");
//        list.add("جغرافیادان ترین شاهزاده ها");
//        list.add("به روز ترین شاهزاده ها");
//        list.add("گیم باز ترین شاهزاده ها");
//        list.add("ورزشکار ترین شاهزاده ها");
//        list.add("باهوش ترین شاهزاده ها");
//        list.add("موسیقی دان ترین شاهزاده ها");
//        list.add("با اطلاع ترین شاهزاده ها");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, list);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.travelreasons, R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kind_of_rank_ur.setAdapter(dataAdapter);

        spinner_kind_of_rank_ur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
//                Toast.makeText(UserRankingListActivity.this,"یه چیزی انتخاب شد",Toast.LENGTH_SHORT);
                if(position == 0)
                    type = UserModel.USER_RANK_TYPE_LEVEL;
                if(position == 1)
                    type = UserModel.USER_RANK_TYPE_LUCK;

                DM.show();
                uc.rank(type, UserModel.RANGE_WHOLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
//                Toast.makeText(UserRankingListActivity.this,"هیچی انتخاب نشد",Toast.LENGTH_SHORT);

            }

        });
    }

    @Override
    public void update(Observable o, Object arg) {
        DM.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext(), getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(UserRankingListActivity.this, MainActivity.class);
                    UserRankingListActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if (arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_RANK_USER))
                {
                    users = (ArrayList<UserModel>) ((ArrayList) arg).get(1);

                    if(users.size()>0) {
                        UserModel current_user = users.get(0);
                        if (users.size() < 21) {
                            for (UserModel u : users) {
                                if (u.getUserName() != null) {
                                    if (u.getUserName().equals(username)) {
                                        current_user = u;
                                        break;
                                    }
                                } else {
                                    if (u.getVisitorUserName().equals(username)) {
                                        current_user = u;
                                        break;
                                    }
                                }

                            }
                        } else {
                            current_user = users.get(users.size() - 1);
                            users.remove(users.size() - 1);
                        }

                        text_username.setText(current_user.getUserName() == null ? current_user.getVisitorUserNameShow() : current_user.getUserName());

                        String s = "";


                        if(type == UserModel.USER_RANK_TYPE_LEVEL)
                        {
                            s = "سطح:" + Utility.calculateLevel(current_user.getLevelScore()) + "\n" + "رتبه:" + current_user.getRank();
                        }
                        else
                        {
                            if (current_user.getLuck() < 0) {
                                s = "شانس:" + (current_user.getLuck() * -1) + "-" + "\n" + "رتبه:" + current_user.getRank();
                            } else {
                                s = "شانس:" + current_user.getLuck() + "\n" + "رتبه:" + current_user.getRank();
                            }
                        }

                        text_score.setText(Utility.enToFa(s));


                        if (users.get(0).getRank() != null)
                            fisrt_rank = users.get(0).getRank();
                        else
                            fisrt_rank = 1;
                        fillList(users);
                    }
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_USER_FRIEND_LIST))
                {
                    users = (ArrayList<UserModel>) ((ArrayList) arg).get(1);
                    // meysam - sync user friend list in db and fill it ...
                    if(users.size()>0)
                    {
                        if(requestedFriendshipStatus != null)
                        {
                            if(requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_REQUESTED)
                            {
                                // meysam - update requested friendship user list in db...
                                UserModel.setMassFriendshipStatus(users,UserFriendModel.USER_FRIEND_STATUS_REQUESTED);
                                UserModel.saveListFriendWRTStatus(users,UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED,true,UserRankingListActivity.this);
                            }
                            if(requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_NORMAL)
                            {
                                // meysam - update recieved request friendship user list in db...

                                box_friendRequest_rl.setVisibility(View.VISIBLE);
                                UserModel.setMassFriendshipStatus(users,UserFriendModel.USER_FRIEND_STATUS_NORMAL);
//                                db.deleteUsersById(users);
//                                UserModel.saveListFriendWRTStatus(users,UserFriendModel.TAG_USER_FRIEND_STATUS_NORMAL,true,UserRankingListActivity.this);

                                session.saveItem(SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT,String.valueOf(users.size()));
                                fillReceivedRequestList(users);

                            }
                            if(requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_ACCEPTED)
                            {
                                // meysam - update friends list in db...
                                UserModel.setMassFriendshipStatus(users,UserFriendModel.USER_FRIEND_STATUS_ACCEPTED);
                                UserModel.saveListFriendWRTStatus(users,UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED,true,UserRankingListActivity.this);

                            }
                            if(requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_BLOCKED)
                            {
                                // meysam - update blocked users list in db...
                                UserModel.setMassFriendshipStatus(users,UserFriendModel.USER_FRIEND_STATUS_BLOCKED);
                                UserModel.saveListFriendWRTStatus(users,UserFriendModel.TAG_USER_FRIEND_STATUS_BLOCKED,true,UserRankingListActivity.this);

                            }
                        }
                    }
                    else
                    {

                        if(requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_NORMAL)
                        {
                            box_friendRequest_rl.setVisibility(View.GONE);
                        }

                        list_recieved_friend_requests.setAdapter(null);
                        if(requestedFriendshipStatus == UserFriendModel.USER_FRIEND_STATUS_NORMAL)
                        {
                            session.saveItem(SessionModel.KEY_UNREAD_FRIENDSHIP_REQUEST_COUNT,String.valueOf(users.size()));
                        }
                    }
                }

            }
        }
    }

    //fill list of users
    private void fillList(ArrayList<UserModel> users) {
        //make list
        ListView lv = (ListView) findViewById(R.id.list_ur);
        if(type == UserModel.USER_RANK_TYPE_LEVEL)
        {
            CALU = new CustomAdapterList_user(this, new ArrayList<Object>(users), UserModel.TAG_USER_RANK_TYPE_LEVEL,fisrt_rank);
        }
        else
        {
            CALU = new CustomAdapterList_user(this, new ArrayList<Object>(users), UserModel.TAG_USER_RANK_TYPE_LUCK,fisrt_rank);
        }
        lv.setAdapter(CALU);

        lv.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
                new Avatar().clear(view);
            }
        });

        lv.invalidateViews();
    }

   //fill list of recieved requests
    private void fillReceivedRequestList(ArrayList<UserModel> users) {
        //make list

        CALU = new CustomAdapterList_user(this, new ArrayList<Object>(users), RequestRespondModel.TAG_USER_FRIEND_LIST);
        list_recieved_friend_requests.setAdapter(CALU);
        Utility.setListViewHeightBasedOnChildren(list_recieved_friend_requests);
        list_recieved_friend_requests.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
            }
        });

        list_recieved_friend_requests.invalidateViews();
    }


    @Override
    public void onResume() {

        let_search_box.setText("");
        if(uc == null)
        {
            uc = new UserController(getApplicationContext());
            uc.addObserver(this);
        }
        //meysam - commented in 13970201 - didn't needed it..
//        if(!isInCreate)
//        {
//            DM.show();
//            intialize();
//        }
        isInCreate = false;
        super.onResume();
    }

    @Override
    public void onPause() {

//        CustomAdapterList_user.setInflater(null);
//        uc.setCntx(null);
//        uc.deleteObserver(this);
//        uc = null;
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
        if(CALU != null)
            CALU.setInflater(null);
        if(uc != null)
        {
            uc.setCntx(null);
//            uc.deleteObserver(this);
            uc.deleteObservers();
            uc = null;
        }

        super.onDestroy();
    }

    //meysam - 13960625
//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(UserRankingListActivity.this,MainActivity.class);
//
//        UserRankingListActivity.this.startActivity(i);
//        super.onBackPressed();
//    }

}
