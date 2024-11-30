package ir.fardan7eghlim.luckylord.views.user;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.WordModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyEditText;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_user;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.FirstPageActivity;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;

public class SearchUserActivity extends BaseActivity implements Observer {

    private DatabaseHandler db;
    private View footerView;
    private DialogModel DM;
    private String request_type;
    private ListView list_items;
    ArrayList<UserModel> users;
    ArrayList<WordModel> words;
    private boolean isInCreate;
    private Bundle extras;
    private LuckyEditText let_search_box;
    private ImageView iv_search_button;
    private int skip;
    private CustomAdapterList_user CALU;
    private String searched_text;
    private UserController uc;
    private Integer matchType;
    private Integer opponentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);


        footerView = ((LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);

        db = new DatabaseHandler(this);
        isInCreate = true;
        matchType = null;
        opponentType = null;

        users = new ArrayList<>();
        words = new ArrayList<>();

        searched_text = "";

        list_items = (ListView) findViewById(R.id.lv_search_result);
        let_search_box = (LuckyEditText) findViewById(R.id.let_search_box);
        iv_search_button = (ImageView) findViewById(R.id.btn_search_rl);

        DM=new DialogModel(SearchUserActivity.this);
        extras = getIntent().getExtras();
        request_type = extras.getString("list_type");

        if(extras.containsKey("match_type"))
        {
            matchType = extras.getInt("match_type");
            opponentType = extras.getInt("opponent_type");
        }


        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        initialize();

        iv_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initialize();
                if(searched_text.toLowerCase().contains("visitor_"))
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.msg_NoVisitorInSearchResult), Toast.LENGTH_SHORT);

                }
                else
                {
                    searched_text = let_search_box.getText().toString();
                    if(!searched_text.equals(""))
                    {
                        mainTaskSearch();

                    }

                }

            }
        });
    }

    private void initialize()
    {
        skip = 0;
        words = new ArrayList<>();
        users = new ArrayList<>();
        list_items.setAdapter(null);
        searched_text = "";
//        matchType = null;
//        opponentType = null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg)
    {
        DM.hide();

        if(arg != null) {
            if (arg instanceof Boolean) {
                if(arg.equals(true))
                {
                    if(session.isLoggedIn())
                    {
                        Intent i = new Intent(SearchUserActivity.this, MainActivity.class);
                        SearchUserActivity.this.startActivity(i);
                        if(FirstPageActivity.FirstPageActivity != null)
                        {
                            FirstPageActivity.FirstPageActivity.finish();
                            FirstPageActivity.FirstPageActivity = null;
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
            }
            else if (arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_SEARCH_USER))
                {
                                ArrayList<UserModel> usertemps = (ArrayList<UserModel>) ((ArrayList) arg).get(1);
                                if(usertemps.size() > 0)
                                {
                    //                users.addAll(usersTmp);
                    //                if(usersTmp.size()<10)
                    //                    list_users.removeFooterView(footerView);
                                    fillList(usertemps,null);

                                }
                                else
                                {
                                    DialogPopUpModel.show(SearchUserActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
                                }
                }
            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
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

    private void addFooterViewSearch() {
        list_items.addFooterView(footerView);
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

    private void mainTaskSearch() {

        if(request_type.equals("search_words"))
        {
            DM.show();

            ArrayList<WordModel> twords = db.searchFarsiWord(searched_text,skip);
            if(twords.size() > 0)
            {
                fillList(null,twords);
            }
            else
            {
                DialogPopUpModel.show(SearchUserActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
            }
            DM.hide();
        }
        if(request_type.equals("search_users"))
        {
            DM.show();

//            searched_text = let_search_box.getText().toString();
            if(searched_text == null || searched_text.length() == 0)
            {
               DialogPopUpModel.show(SearchUserActivity.this,getString(R.string.msg_EmptyList), getString(R.string.btn_OK), null, false, true);
            }
            else
            {
                if(searched_text.toLowerCase().contains("visitor_"))
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.msg_NoVisitorInSearchResult), Toast.LENGTH_SHORT);

                }
                else
                {
                    UserModel user = new UserModel();
                    user.setUserName(searched_text);
                    uc.search(user,skip);
                }

            }
        }
    }
    private void fillList(ArrayList<UserModel> usersTmp, ArrayList<WordModel> wordsTmp) {
        //make list
        addFooterViewList();

        if(request_type.equals("search_users"))
        {
            users.addAll(usersTmp);
            if(usersTmp.size()<10)
                list_items.removeFooterView(footerView);

            CALU = new CustomAdapterList_user(this, new ArrayList<Object>(users), RequestRespondModel.TAG_SEARCH_USER, matchType, opponentType);
        }
        if(request_type.equals("search_words"))
        {
            words.addAll(wordsTmp);
            if(wordsTmp.size()<10)
                list_items.removeFooterView(footerView);

            CALU = new CustomAdapterList_user(this, new ArrayList<Object>(words), WordModel.TAG_SEARCH_WORDS, null, null);

        }

        list_items.setAdapter(CALU);

        list_items.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
            }
        });

        list_items.invalidateViews();
        if(skip>0)
            list_items.setSelection(skip);
    }
    private void addFooterViewList() {
        list_items.addFooterView(footerView);
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


    @Override
    public void onResume() {

        if(uc == null)
        {
            uc = new UserController(getApplicationContext());
            uc.addObserver(this);
        }
//        if(ufc == null)
//        {
//            ufc = new UserFriendController(getApplicationContext());
//            ufc.addObserver(this);
//        }
//        if(!isInCreate)
//        {
//            users.clear();
//            //meysam - refill list
//            initialize();
//        }
//        if(users.size() < 10)
//            list_items.removeFooterView(footerView);

        isInCreate = false;
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }
        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObserver(this);
            uc = null;
        }
        if(CALU != null)
            CALU.setInflater(null);
        list_items.setAdapter(null);
        users.clear();
        searched_text = "";

        super.onDestroy();
    }
}
