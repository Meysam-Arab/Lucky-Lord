package ir.fardan7eghlim.luckylord.views.hazels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.AdvertismentModel;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.BetModel;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.MatchModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.CustomAdapterList_user;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_hazel;
import ir.fardan7eghlim.luckylord.utils.DialogModel_luck;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFourVerticalModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpFree;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.DialogSelectBetModel;
import ir.fardan7eghlim.luckylord.utils.DialogSelectCategoryModel;
import ir.fardan7eghlim.luckylord.utils.LuckyLordRecyclerViewAdapter;
import ir.fardan7eghlim.luckylord.utils.SoundFXModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchCrossTableActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchLoadingActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchQuestionActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchResultActivity;
import ir.fardan7eghlim.luckylord.views.user.FriendListActivity;
import ir.fardan7eghlim.luckylord.views.user.SearchUserActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class HazelMatchIndexActivity extends BaseActivity implements Observer{
    private CustomAdapterList_user HCAC;
    private DialogModel DM;
    public static ArrayList<MatchModel> matches;
    public static ArrayList<UniversalMatchModel> uMatches;
    private ProgressBar pb_recent_games;

    private ListView lv ;

    private DatabaseHandler db;

    private boolean loading_matches = false;
    private boolean inCreating;
    private boolean returningFromMatch;
    private boolean waitingForAd;
    private boolean isMatchCrossTable;
    private boolean isMatchQuestionAnswer;
    public static String categoryId;
    private Integer finalAddedTime;//meysam - added in 13960608
    private Boolean forTurbo;
    private UserModel opponent;
    private UniversalMatchModel uMatch;
    private SoundFXModel sfxm = null;

    private Double reward_time_cost;//meysam - added in 13960608

    private UserController uc;
    private MatchController mc;

    Dialog betDialog;
    private Integer matchType;

    private boolean forFreeMatch;

    private Handler customHandler = new Handler();
    Integer delayCount = 0;
    private int timeWaitBeforeRefresh = 10000;
    private Runnable refreshTimerThread = new Runnable() {

        public void run() {

            if(!waitingForAd)
            {
                delayCount += 1;

                if(delayCount >= 6)
                {
                    timeWaitBeforeRefresh = 10000;
                    delayCount = 1;
                }
                else
                {
                    timeWaitBeforeRefresh = timeWaitBeforeRefresh * 2;
                }
                //meysam refresh match list .. call match index
                customHandler.removeCallbacks(refreshTimerThread);
                customHandler = null;
                if(mc == null)
                    HazelMatchIndexActivity.this.finish();
                mc.universalIndex(matchType, UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_ALL);//meysam - added in 13961001
                loading_matches = true;// meysam - comment when match offline - Offline Match
            }
            else
            {
                customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazel_match_index);

        matchType = null;
        opponent = null;
        uMatch = null;

        forFreeMatch = false;
        waitingForAd = false;

        inCreating = true;
        returningFromMatch = false;
        finalAddedTime = 0;//meysam - added in 13960608

        forTurbo = false;

        categoryId = String.valueOf(CategoryModel.RANDOM);

        uc = new UserController(getApplicationContext());
        uc.addObserver(this);

        mc = new MatchController(getApplicationContext());
        mc.addObserver(this);

        db=new DatabaseHandler(getApplicationContext());

        lv = (ListView) findViewById(R.id.list_recent_games);
        sfxm = new SoundFXModel(getApplicationContext());

        matchType = Integer.valueOf(getIntent().getExtras().getString("match_type"));

        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
        {
            isMatchCrossTable = true;
            isMatchQuestionAnswer = false;
        }
        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
        {
            isMatchCrossTable = false;
            isMatchQuestionAnswer = true;
        }

        setCategoriesClickListeners();

        if (!session.getBoolianItem(SessionModel.KEY_MATCH_INDEX_IS_FIRST_TIME,false)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            //meysam - show tutorial

            DialogPopUpModel.show(HazelMatchIndexActivity.this, getString(R.string.dlg_TutorialMatchIndex), getString(R.string.btn_OK), null,false, false);
            session.saveItem(SessionModel.KEY_MATCH_INDEX_IS_FIRST_TIME, true);

        }


        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("categories_activity_broadcast"));

        //for Tapsell ...
        Tapsell.initialize(this, AdvertismentModel.tapsellAppKey);
        /////////////////////////////////////////

        pb_recent_games=(ProgressBar) findViewById(R.id.pb_recent_games);

        DM = new DialogModel(HazelMatchIndexActivity.this);
        betDialog= new Dialog(HazelMatchIndexActivity.this);

        //buttons
        Button btn_chance_context= (Button) findViewById(R.id.btn_chance_context);
        btn_chance_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //////////
                //meysam - added in 13961001
                uMatch = null;
                opponent = null;
                if(UniversalMatchModel.bets != null)
                {
                    if(UniversalMatchModel.bets.size() > 0)
                    {
                        showHorizontalBetDialog();

                    } else
                    {
                        Utility.displayToast(getApplicationContext(),"لطفا صبر کنید...",Toast.LENGTH_SHORT);
                    }
                }
                else
                {
                    Utility.displayToast(getApplicationContext(),"لطفا صبر کنید...",Toast.LENGTH_SHORT);
                }
                ///////////////////////////////

            }
        });

        if(mc == null)
            HazelMatchIndexActivity.this.finish();
        mc.universalIndex(matchType, UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_ALL);//meysam - added in 13961001

        loading_matches = true;// meysam - comment when match offline - Offline Match

        DM.show();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {

        DM.hide();
        //hide recent list progressbar
        pb_recent_games.setVisibility(View.GONE);

        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Intent i = new Intent(HazelMatchIndexActivity.this,MainActivity.class);
                    HazelMatchIndexActivity.this.startActivity(i);
                    Utility.finishActivity(this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DECREASE_HAZEL_LUCK_USER))
                {

                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()) == true)
                    {

                        //meysam - added in 13960608
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getBetLuck())
                        {
                            session.decreaseLuck( Integer.valueOf(reward_time_cost.intValue()).toString());
                        }
                        else
                        {
                            session.decreaseHazel(Integer.valueOf(reward_time_cost.intValue()).toString());
                        }
                        ///////////////////////////////////////

                        if(forTurbo)
                        {
                            finalAddedTime = LuckyLordRecyclerViewAdapter.chosenBet.getRewardTime();//meysam - added in 13960608
                            //meysam - added in 13961001
                            if(uMatch != null)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //stuff that updates ui
                                        new DialogModel_hazel(HazelMatchIndexActivity.this).show(true, false, new Integer(reward_time_cost.intValue()));
                                    }
                                });

                                afterBetDialogOperation();
//                                if(uMatch == null || uMatch.getMatchStatus() == null)
//                                    showSelectCategoryDialog();
//                                else
//                                    goToUniversalMatch();
                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //stuff that updates ui
                                        afterBetDialogOperation();

//                                        if(uMatch == null || uMatch.getMatchStatus() == null)
//                                            showSelectCategoryDialog();
//                                        else
//                                            goToUniversalMatch();
                                        new DialogModel_hazel(HazelMatchIndexActivity.this).show(true, false, new Integer(reward_time_cost.intValue()));
                                    }
                                });
                            }

                        }
                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {
                    if(forFreeMatch)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new DialogModel_hazel(HazelMatchIndexActivity.this).show(false, false, -1*new Integer(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL)));
                                session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                                session.removeItem(SessionModel.KEY_FINAL_LUCK);

                                forFreeMatch = false;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 3s = 3000ms
                                        afterBetDialogOperation();
                                    }
                                }, 2000);


                            }
                        });
                    }


                }
                //meysam - added in 13961001
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX))
                {
                    //meysam - bets are initializing in MatchController - Offline Match
                    if(customHandler == null)
                        customHandler = new Handler();
                    customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
                    if(UniversalMatchModel.bets != null)
                    {
                        if(UniversalMatchModel.bets.size() == 0)
                        {
                            Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_please_wait), Toast.LENGTH_SHORT);
                            Utility.finishActivity(HazelMatchIndexActivity.this);
                        }
                    }
                    else
                    {
                        Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_please_wait), Toast.LENGTH_SHORT);
                        Utility.finishActivity(HazelMatchIndexActivity.this);
                    }

                    //////////////////////// meysam - comment - called just for bets - Offline Match
                    loading_matches = false;
                    uMatches = (ArrayList<UniversalMatchModel>) ((ArrayList) arg).get(1);
                    uMatches = UniversalMatchModel.fillMatchsType(uMatches,matchType);

                    if(uMatches.size()>0)
                    {
                        if(HCAC == null)
                            fillUniversalMatchList();
                        else
                            reFillUniversalMatchList();

                        //meysam - check matches if changed matches are older that 5 matches then show info dialog....
//                        String dialogText = "";


                        ArrayList<UniversalMatchModel> oldUmatches = db.getMatches();
                        for(int i = 0; i < oldUmatches.size(); i++)
                        {
                            UniversalMatchModel newMatch = UniversalMatchModel.findMatchById(uMatches,oldUmatches.get(i).getId());
//                            sfxm.play(SoundFXModel.NOTIFY, getApplicationContext());
//                            new DialogPopUpFree().show(HazelMatchIndexActivity.this,newMatch.getMatchStatus(),newMatch.getOpponent().getUserName(),newMatch.getOpponent().getProfilePicture(),UniversalMatchModel.getRewardText(newMatch.getMatchStatus(),newMatch.getBet()),true);

                            if(newMatch != null && newMatch.getEnded() != null)
                            {
                                if(oldUmatches.get(i).getEnded() == null || !oldUmatches.get(i).getEnded().equals(newMatch.getEnded()))
                                {
                                    sfxm.play(SoundFXModel.NOTIFY, getApplicationContext());
                                    new DialogPopUpFree().show(HazelMatchIndexActivity.this,newMatch.getMatchStatus(),newMatch.getOpponent().getUserName(),newMatch.getOpponent().getProfilePicture(),UniversalMatchModel.getRewardText(newMatch.getMatchStatus(),newMatch.getBet()),true);
                                }
                            }

                        }
                        //meysam - refresh database matches...
                        db.saveMatches(uMatches,true);
                    }

                    //////////////////////////////////////////
                }
                //meysam - added in 13961001
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_REQUEST))
                {
                    //meysam - remove randome categories from session
                    session.removeRandomCategoryIds();

                    // meysam - add new Match to Match List...
                    UniversalMatchModel universalMatch = (UniversalMatchModel) ((ArrayList) arg).get(1);
                    universalMatch.setOpponent(opponent);
                    universalMatch.setMatchStatus(UniversalMatchModel.STATUS_REQUEST_SENT);
                    universalMatch.setBet(LuckyLordRecyclerViewAdapter.chosenBet.getBetId());
                    uMatches.add(0,universalMatch);
                    db.addMatch(universalMatch);
                    if(uMatches.size()>0)
                        fillUniversalMatchList();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogPopUpModel.show(HazelMatchIndexActivity.this, HazelMatchIndexActivity.this.getApplicationContext().getString(R.string.msg_RequestSent), HazelMatchIndexActivity.this.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }
                    });

                    //meysam
                    if(LuckyLordRecyclerViewAdapter.chosenBet.getBetLuck())
                    {
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getAmount() != 0)
                        {
                            session.decreaseLuck( LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogModel_luck(HazelMatchIndexActivity.this).show(true, false, LuckyLordRecyclerViewAdapter.chosenBet.getAmount());
                                }
                            });
                        }

                    }
                    else
                    {
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getAmount() != 0)
                        {
                            session.decreaseHazel(LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogModel_hazel(HazelMatchIndexActivity.this).show(true, false, LuckyLordRecyclerViewAdapter.chosenBet.getAmount());
                                }
                            });
                        }
                        else
                        {
                            if (LuckyLordRecyclerViewAdapter.chosenBet.getAmount().equals(0))
                            {
                                //meysam - no need to see ad - cont as usual...
                                Integer freeCountTillNow = session.getIntegerItem(SessionModel.KEY_FREE_MATCH_COUNT);
                                freeCountTillNow ++;
                                session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT,freeCountTillNow);
                            }
                        }

                    }
                    ///////////////////////////////////////

                }
                //meysam - added in 13961001
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_STATUS))
                {

                    int matchIndexInList = -1;
                    for(int i = 0; i < uMatches.size(); i++) {
                        if(uMatches.get(i).getId() != null)
                        {
                            if(uMatches.get(i).getId().equals(uMatch.getId())) {
                                //found it!
                                matchIndexInList = i;
                                break;
                            }
                        }

                    }

                    if(matchIndexInList != -1)
                        uMatches.remove(matchIndexInList);

                    // meysam - recieve accepted match info and go to match activity...
                    UniversalMatchModel universalMatch = (UniversalMatchModel) ((ArrayList) arg).get(1);
                    uMatch.setQuestions(universalMatch.getQuestions());
                    uMatch.setEnded(universalMatch.getEnded());
                    uMatch.setMatchType(universalMatch.getMatchType());
                    uMatch.setOpponentType(universalMatch.getOpponentType());
                    uMatch.setMatchStatus(universalMatch.getMatchStatus());
                    uMatch.setBet(universalMatch.getBet());

                    uMatch.fillNullAttributes(universalMatch);
                    //meysam - added in 13970129
                    db.editMatch(uMatch);
                    ///////////////////////////
                    if(uMatch.getMatchStatus().equals(UniversalMatchModel.STATUS_YOUR_TURN))
                    {

//                    LuckyLordRecyclerViewAdapter.chosenBet = BetModel.getBetById(uMatch.getBet());
                        if(LuckyLordRecyclerViewAdapter.chosenBet.getNitro())
                            uMatch.setNitro(true);
                        else
                            uMatch.setNitro(false);
                        HCAC.notifyDataSetInvalidated();

                        if(BetModel.isMatchRewardLuck(uMatch.getBet()))
                            Utility.changeLucks(getApplicationContext(),-1*LuckyLordRecyclerViewAdapter.chosenBet.getAmount());
                        else
                            Utility.changeCoins(getApplicationContext(),-1*LuckyLordRecyclerViewAdapter.chosenBet.getAmount());



                        BetModel oppBet = BetModel.getBetById(uMatch.getBet());
                        Random rnd = new Random(new Date().getTime());
                        int x = rnd.nextInt(100);
                        if(oppBet.getRewardTime() != 0 && x < 30)//meysam - must be 30
                        {

                            Intent tintent = new Intent("categories_activity_broadcast");
                            // You can also include some extra data.
                            tintent.putExtra("show_reward_chooser_dialog", "true");
                            LocalBroadcastManager.getInstance(HazelMatchIndexActivity.this).sendBroadcast(tintent);

                        }
                        else
                        {
                            goToUniversalMatch();
                        }
                    }
                    else
                    {
                        // meysam - call result activity
                        Intent intent = new Intent(HazelMatchIndexActivity.this, UniversalMatchResultActivity.class);
                        intent.putExtra("match_type", uMatch.getMatchType());
                        intent.putExtra("match_status", uMatch.getMatchStatus());
                        intent.putExtra("opponent_type", uMatch.getOpponentType());
                        intent.putExtra("bet_type", uMatch.getBet());
                        if(uMatch.getSelfSpentTime() != null)
                        {
                            if(!uMatch.getSelfSpentTime().equals("null"))
                                intent.putExtra("match_self_spent_time", uMatch.getSelfSpentTime().toString());
                        }
//                        else
//                        {
//                            if(!uMatch.getSelfSpentTime().equals("null"))
//                                intent.putExtra("match_self_spent_time", uMatch.getSelfSpentTime().toString());
//                        }
                        if(uMatch.getSelfCorrectCount() != null)
                        {
                            if( !uMatch.getSelfCorrectCount().equals("null"))
                                intent.putExtra("match_self_correct_count", uMatch.getSelfCorrectCount().toString());
                        }
//                        else
//                        {
//                            if(!uMatch.getSelfCorrectCount().equals("null"))
//                                intent.putExtra("match_self_correct_count", uMatch.getSelfCorrectCount().toString());
//                        }
                        if(uMatch.getOpponentSpentTime() != null && !uMatch.getOpponentSpentTime().equals("null"))
                            intent.putExtra("match_opponent_spent_time", uMatch.getOpponentSpentTime());
                        if(uMatch.getOpponentCorrectCount() != null && !uMatch.getOpponentCorrectCount().equals("null"))
                            intent.putExtra("match_opponent_correct_count", uMatch.getOpponentCorrectCount());

                        if(uMatch.getOpponent() != null &&
                                uMatch.getOpponent().getProfilePicture() != null &&
                                uMatch.getOpponent().getUserName() != null)
                        {
                            intent.putExtra("match_opponent_avatar", uMatch.getOpponent().getProfilePicture());
                            intent.putExtra("match_opponent_user_name", uMatch.getOpponent().getUserName());
                        }
                        else
                        {
                            intent.putExtra("match_opponent_avatar", uMatch.getOpponent().getProfilePicture());
                            intent.putExtra("match_opponent_user_name", uMatch.getOpponent().getUserName());
                        }

                        if(uMatch.getEnded().equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED))
                        {
                            if(uMatch.getRewardLuck() != null)
                                intent.putExtra("is_reward_luck", uMatch.getRewardLuck());
                            else
                                intent.putExtra("is_reward_luck", BetModel.isMatchRewardLuck(uMatch.getBet()));
                        }

                        HazelMatchIndexActivity.this.startActivity(intent);


                    }

                    if(matchIndexInList != -1)
                        uMatches.add(matchIndexInList,uMatch);
                    loading_matches = false;
//                    uMatch = null;

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

    //meysam - added in 13961001
    private void goToUniversalMatch() {

        //meysam - remove randome categories from session
        session.removeRandomCategoryIds();

        Intent i = null;
        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
        {
            i = new Intent(HazelMatchIndexActivity.this,UniversalMatchQuestionActivity.class);
        }
        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
        {
            i = new Intent(HazelMatchIndexActivity.this, UniversalMatchCrossTableActivity.class);
        }
        uMatch.setNitro(LuckyLordRecyclerViewAdapter.chosenBet.getNitro());
        uMatch.setRewardLuck(LuckyLordRecyclerViewAdapter.chosenBet.getBetLuck());
//                uMatch.setEnded(false);
        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
        {
            UniversalMatchQuestionActivity.match = uMatch;

            UniversalMatchQuestionActivity.ShowRewardQuestion = false;
            UniversalMatchQuestionActivity.questionNumber = 0;
            UniversalMatchQuestionActivity.used_hazel_amount = 0;
            UniversalMatchQuestionActivity.used_luck_amount = 0;
        }
        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE))
        {
            UniversalMatchCrossTableActivity.match = uMatch;

            UniversalMatchCrossTableActivity.used_hazel_amount = 0;
            UniversalMatchCrossTableActivity.used_luck_amount = 0;
        }

        //meysam - halfOffline Match - add new match to list
        HazelMatchIndexActivity.uMatches.add(0,uMatch);



        session.saveItem(SessionModel.KEY_BET_ID,LuckyLordRecyclerViewAdapter.chosenBet.getBetId());



        UniversalMatchModel.setCurrentMatchTime(getApplicationContext(),LuckyLordRecyclerViewAdapter.chosenBet.getTime()+finalAddedTime);
        UniversalMatchModel.setMinSec(LuckyLordRecyclerViewAdapter.chosenBet.getBetId());



        if(finalAddedTime > 0)
        {
            UniversalMatchModel.addMoreTimeToMatch(finalAddedTime);
            finalAddedTime= 0;
        }

        int matchDurationSeconds = UniversalMatchModel.MATCH_DURATION_SECONDS;
        int matchDurationMinutes = UniversalMatchModel.MATCH_DURATION_MINUTES;

        i.putExtra("seconds",matchDurationSeconds);
        i.putExtra("minutes",matchDurationMinutes);
//        HazelCategoriesActivity.this.finish();
        returningFromMatch = true;
        HazelMatchIndexActivity.this.startActivity(i);
    }

    //meysam - added in 13961001
    private void fillUniversalMatchList() {
//        ListView lv = (ListView) findViewById(R.id.list_recent_games);
        lv.setVisibility(View.VISIBLE);
        HCAC = new CustomAdapterList_user(this, new ArrayList<Object>(uMatches), RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX);
        lv.setAdapter(HCAC);

        lv.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
                new Avatar().clear(view);
            }
        });

        lv.invalidateViews();
    }

    //meysam - added in 13961001
    private void reFillUniversalMatchList() {
        lv.setVisibility(View.VISIBLE);

        HCAC.updateAdapter(new ArrayList<Object>(uMatches));
        synchronized(lv.getAdapter()){
            lv.getAdapter().notify();
        }
        HCAC.notifyDataSetChanged();
    }

    //meysam - added in 13960608
    //show bet dialog
    private void showHorizontalBetDialog(){

        DialogSelectBetModel.show(HazelMatchIndexActivity.this, false, true,"categories_activity_broadcast");
    }

    private void WatchAd()
    {
        waitingForAd = true;
        new SoundModel(this).stopMusic();

        String adZoneId;
        if(forTurbo)
        {
            adZoneId = AdvertismentModel.CategoriesPageZoneId;
        }
        else
        {
            adZoneId = AdvertismentModel.CategoriesPageZoneId;
        }

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_CACHED);
        Tapsell.requestAd(HazelMatchIndexActivity.this,adZoneId , options, new TapsellAdRequestListener() {


            @Override
            public void onError (String error)
            {
                DM.hide();
                Utility.displayToast(HazelMatchIndexActivity.this,getApplicationContext().getString(R.string.error_show_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(HazelMatchIndexActivity.this).playCountinuseRandomMusic();

                forTurbo = false;

                //meysam - added in 13961001
                if(uMatch != null)
                {
                    goToUniversalMatch();
                }
                //////////////////////////////

                forFreeMatch = false;
                waitingForAd = false;

            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {

                DM.hide();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
                showOptions.setShowDialog(true);
                ad.show((Activity) HazelMatchIndexActivity.this, showOptions);

            }

            @Override
            public void onNoAdAvailable ()
            {
                DM.hide();
                Utility.displayToast(HazelMatchIndexActivity.this,getApplicationContext().getString(R.string.error_no_advertisment),Toast.LENGTH_SHORT);

                new SoundModel(HazelMatchIndexActivity.this).playCountinuseRandomMusic();

                forTurbo = false;

                //meysam - added in 13961001
                if(uMatch != null)
                {
                    goToUniversalMatch();
                }
                //////////////////////////////

                if(forFreeMatch)
                {
                    forFreeMatch = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            afterBetDialogOperation();
                        }
                    });

                }
                waitingForAd = false;

            }

            @Override
            public void onNoNetwork ()
            {

                DM.hide();
                Utility.displayToast(HazelMatchIndexActivity.this,getApplicationContext().getString(R.string.error_no_connection),Toast.LENGTH_SHORT);

                new SoundModel(HazelMatchIndexActivity.this).playCountinuseRandomMusic();

                forTurbo = false;

                //meysam - added in 13961001
                if(uMatch != null)
                {
                    goToUniversalMatch();
                }
                //////////////////////////////

                forFreeMatch = false;
                waitingForAd = false;
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                DM.hide();
                Utility.displayToast(HazelMatchIndexActivity.this,getApplicationContext().getString(R.string.error_advertisment_expired),Toast.LENGTH_SHORT);

                new SoundModel(HazelMatchIndexActivity.this).playCountinuseRandomMusic();

                forTurbo = false;

                //meysam - added in 13961001
                if(uMatch != null)
                {
                    goToUniversalMatch();
                }
                //////////////////////////////

                forFreeMatch = false;
                waitingForAd = false;
            }
        });
        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {
                DM.hide();
                new SoundModel(HazelMatchIndexActivity.this).playCountinuseRandomMusic();

                if(completed)
                {

                    if(forTurbo)
                    {
                        finalAddedTime = LuckyLordRecyclerViewAdapter.chosenBet.getRewardTime();//meysam - added in 13960608

                        //meysam - added in 13961001
                        if(uMatch != null)
                        {
                            goToUniversalMatch();
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //stuff that updates ui
//                                    if(uMatch == null || uMatch.getMatchStatus() == null)
                                    if(isMatchQuestionAnswer)
                                        showSelectCategoryDialog();
                                    else if(isMatchCrossTable)
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                afterBetDialogOperation();
                                            }
                                        });
                                    else
                                    {
                                        //meysam - nothing for now...
                                    }
                                }
                            });
                        }

                    }
                    if(forFreeMatch)
                    {
                        forFreeMatch = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                afterBetDialogOperation();
                            }
                        });
                    }
                }
                else
                {
                    Utility.displayToast(HazelMatchIndexActivity.this,getApplicationContext().getString(R.string.error_advertisment_faulty),Toast.LENGTH_SHORT);
                }
                forTurbo = false;
                forFreeMatch = false;
                waitingForAd = false;
            }
        });
    }

    private void showLoading()
    {
        Intent intent = new Intent("categories_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(HazelMatchIndexActivity.this).sendBroadcast(intent);
    }

    private void showNotEnoughHazelMessage()
    {
        Intent intent = new Intent("categories_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("not_enough_hazel", "true");
        LocalBroadcastManager.getInstance(HazelMatchIndexActivity.this).sendBroadcast(intent);
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if(intent.getStringExtra("loading") != null)
            {
                if(intent.getStringExtra("loading").equals("true"))
                {
                    DM.show();
                }
            }
            if(intent.getStringExtra("hide_loading") != null)
            {
                if(intent.getStringExtra("hide_loading").equals("true"))
                {
                    DM.hide();
                }
            }
            if(intent.getStringExtra("changeHazel") != null)
            {
                final Integer amount = Integer.valueOf(intent.getStringExtra("changeHazel"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(amount < 0)
                        {
                            new DialogModel_hazel(HazelMatchIndexActivity.this).show(false, false, amount);

                        }
                        else
                        {
                            new DialogModel_hazel(HazelMatchIndexActivity.this).show(false, true, amount);

                        }
                    }
                });
            }
            if(intent.getStringExtra("changeLuck") != null)
            {
                final Integer amount = Integer.valueOf(intent.getStringExtra("changeLuck"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(amount < 0)
                        {
                            new DialogModel_luck(HazelMatchIndexActivity.this).show(true, false, amount);

                        }
                        else
                        {
                            new DialogModel_luck(HazelMatchIndexActivity.this).show(true, true, amount);

                        }
                    }
                });
            }
            if(intent.getStringExtra("not_enough_hazel") != null)
            {
                if(intent.getStringExtra("not_enough_hazel").equals("true")) {

                    Utility.displayToast(HazelMatchIndexActivity.this,getString(R.string.msg_NotEnoughHazel),Toast.LENGTH_SHORT);

                }
            }

            if(intent.getStringExtra("category_id") != null)
            {
                categoryId = intent.getStringExtra("category_id");

                if(categoryId.equals("null"))
                    categoryId = null;
                // meysam - change to universal and check opponenttype and do respected work...
                //meysam - added in 13961001
                if(matchType != null)
                {
                    if(uMatch != null)
                    {
                        // meysam - user accepted a challenge so send status with chosen category id to server and recieve match and values...
                        DM.show();
                        mc.universalStatus(uMatch,categoryId);
                    }
                    else
                    {
                        if(opponent != null)
                        {
                            DM.show();
//                            if (LuckyLordRecyclerViewAdapter.chosenBet.getAmount().equals(0))
//                            {
//                                //meysam - no need to see ad - cont as usual...
//                                Integer freeCountTillNow = session.getIntegerItem(SessionModel.KEY_FREE_MATCH_COUNT);
//                                freeCountTillNow ++;
//                                session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT,freeCountTillNow);
//                            }
                            mc.universalRequest(null,LuckyLordRecyclerViewAdapter.chosenBet.getBetId().toString(),categoryId,opponent.getId(),matchType,UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM);
                        }
                        else
                        {
                            //meysam - random match was requested...
                            startLoadingActivity(LuckyLordRecyclerViewAdapter.chosenBet,finalAddedTime, matchType);
                        }
                    }

                }
                /////////////////////////////////////////////////////

            }
            if(intent.getStringExtra("call_category_dialog") != null)//meysam - added in 13960608
            {
                if(LuckyLordRecyclerViewAdapter.chosenBet.getAmount().equals(0))
                {

                    //meysam - check if 1 day has passed
                    if(Utility.isMinuteLimitReached(session.getStringItem(SessionModel.KEY_FREE_MATCH_TIME),String.valueOf(Utility.ALLOWED_FREE_MATCH_INTERVAL_TIME)))
                    {
                        //meysam - reset date and count for free match...
//                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                        Date date = new Date();
                        session.saveItem(SessionModel.KEY_FREE_MATCH_TIME,new Date().getTime()+"");
                        session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT,0);
                    }

                    Integer freeCountTillNow = session.getIntegerItem(SessionModel.KEY_FREE_MATCH_COUNT);

                    if(freeCountTillNow != -1 && freeCountTillNow >= Utility.ALLOWED_FREE_MATCH_COUNT)
                    {
                        //meysam - must see advertisment before free match...
                        //show dialog to choose to watch ad or cancel...

                        DialogPopUpFourVerticalModel.show(HazelMatchIndexActivity.this, " سهم امروزت تموم شده. باید یه تبلیغ نگاه کنی تا بتونی مسابقه بدی ",getString(R.string.btn_SeeAd), Utility.enToFa(String.valueOf(Utility.ALLOWED_FREE_MATCH_COST))+" تا فندق میدم ",null,getString(R.string.btn_No),true, false);
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    while(DialogPopUpFourVerticalModel.isUp()){
                                        Thread.sleep(500);
                                    }
                                    if(!DialogPopUpFourVerticalModel.isUp()){
                                        Thread.currentThread().interrupt();
                                        if(DialogPopUpFourVerticalModel.dialog_result==1){
                                            //show ad
//                                            showLoading();
                                            forFreeMatch = true;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DM.show();
                                                }
                                            });
                                            WatchAd();

                                        }
                                        else if(DialogPopUpFourVerticalModel.dialog_result==2)
                                        {
                                            //recieve hazel from user if have it
                                            if(Utility.hasEnoughCoin(HazelMatchIndexActivity.this,Utility.ALLOWED_FREE_MATCH_COST))
                                            {
                                                forFreeMatch = true;
                                                showLoading();
                                                Utility.changeCoins(getApplicationContext(),-1*Utility.ALLOWED_FREE_MATCH_COST);
                                                session.changeFinalHazel(-1*Utility.ALLOWED_FREE_MATCH_COST);
                                                uc.change(-1*Utility.ALLOWED_FREE_MATCH_COST,session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));

                                            }
                                            else
                                            {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showNotEnoughHazelMessage();
                                                    }
                                                });
                                            }
                                        }
                                        else if(DialogPopUpFourVerticalModel.dialog_result==4)
                                        {
                                            //meysam - do nothing
                                            forFreeMatch = false;
                                        }
                                        else
                                        {
                                            //meysam - do nothing
                                            forFreeMatch = false;
                                        }

                                        DialogPopUpFourVerticalModel.hide();

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
                    else
                    {
//                        //meysam - no need to see ad - cont as usual...
//                        freeCountTillNow ++;
//                        session.saveItem(SessionModel.KEY_FREE_MATCH_COUNT,freeCountTillNow);
                        afterBetDialogOperation();
                    }

                }
                else
                {
                    afterBetDialogOperation();
                }



            }

            if(intent.getStringExtra("show_not_enough_hazel_dialog") != null)//meysam - added in 13960608
            {
                DialogPopUpModel.show(HazelMatchIndexActivity.this,"برای بازی باید حداقل " + LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString() + " فندق داشته باشی!","باشه",null, false, false);

            }

            if(intent.getStringExtra("show_not_enough_luck_dialog") != null)//meysam - added in 13960608
            {
                DialogPopUpModel.show(HazelMatchIndexActivity.this,"برای بازی باید حداقل " + LuckyLordRecyclerViewAdapter.chosenBet.getAmount().toString() + " شانس داشته باشی!","باشه",null, false, false);

            }
            if(intent.getStringExtra("show_reward_chooser_dialog") != null)//meysam - added in 13960608
            {
                if(uMatch == null && opponent != null && isMatchQuestionAnswer)
                {
                    showSelectCategoryDialog();
                }
                else
                {
                    reward_time_cost =  LuckyLordRecyclerViewAdapter.chosenBet.getAmount()*0.2;
                    String currencyStr = " تا فندق میدم";
                    if(LuckyLordRecyclerViewAdapter.chosenBet.getBetLuck())
                    {
                        currencyStr = " تا شانس میدم";
                    }

                    DialogPopUpFourVerticalModel.show(HazelMatchIndexActivity.this,LuckyLordRecyclerViewAdapter.chosenBet.getRewardTime()+" ثانیه "+getString(R.string.msg_NitroQuestion),getString(R.string.btn_SeeAd),reward_time_cost.intValue() +currencyStr,null,getString(R.string.btn_No),true, false);
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while(DialogPopUpFourVerticalModel.isUp()){
                                    Thread.sleep(500);
                                }
                                if(!DialogPopUpFourVerticalModel.isUp()){
                                    Thread.currentThread().interrupt();
                                    if(DialogPopUpFourVerticalModel.dialog_result==1){
                                        //show ad
                                        forTurbo = true;
//                                        showLoading();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DM.show();
                                            }
                                        });
                                        WatchAd();

                                    }
                                    else if(DialogPopUpFourVerticalModel.dialog_result==2)
                                    {
                                        //recieve hazel from user if have it
                                        if(Utility.hasEnoughCoin(HazelMatchIndexActivity.this,reward_time_cost.intValue()))
                                        {
                                            forTurbo = true;
                                            showLoading();
                                            uc.decrease(reward_time_cost.intValue(),0);
                                        }
                                        else
                                        {
                                            showNotEnoughHazelMessage();
                                        }
                                    }
                                    else if(DialogPopUpFourVerticalModel.dialog_result==4)
                                    {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                //stuff that updates ui
//                                                if(uMatch == null || uMatch.getMatchStatus() == null)
//                                                    showSelectCategoryDialog();
//                                                else
//                                                    goToUniversalMatch();
                                                if(isMatchQuestionAnswer)
                                                    showSelectCategoryDialog();
                                                else if(isMatchCrossTable)
                                                    goToUniversalMatch();
                                                else
                                                {
                                                    //meysam - nothing for now...
                                                }
                                            }
                                        });

                                    }
                                    else
                                    {
                                        //meysam - do nothing
                                    }
                                    DialogPopUpFourVerticalModel.hide();
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
            }
            if(intent.getStringExtra("show_toast") != null)
            {
                final String temp = intent.getStringExtra("show_toast");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.displayToast(HazelMatchIndexActivity.this,temp, Toast.LENGTH_SHORT);
                    }
                });
            }
            if(intent.hasExtra("universal_match_id"))//meysam - added in 13961001
            {
                // meysam - show select category dialog and set universal_match_id to sent to server...
                opponent = new UserModel();
                opponent.setId(new BigInteger(intent.getStringExtra("opponent_id")));
                opponent.setProfilePicture(intent.getStringExtra("opponent_image"));
                opponent.setUserName(intent.getStringExtra("opponent_user_name"));

                uMatch = new UniversalMatchModel();
                uMatch.setId(new BigInteger(intent.getStringExtra("universal_match_id")));
                uMatch.setBet(intent.getStringExtra("universal_match_bet"));
                uMatch.setSelfSpentTime(intent.getStringExtra("self_spent_time"));
                uMatch.setSelfCorrectCount(intent.getStringExtra("self_correct_count"));
                uMatch.setOpponent(opponent);

                BetModel oppBet = BetModel.getBetById(uMatch.getBet());
                LuckyLordRecyclerViewAdapter.chosenBet = oppBet;

                if (intent.hasExtra("universal_match_status"))
                {
                    uMatch.setMatchStatus(new Integer(intent.getStringExtra("universal_match_status")));


                    DM.show();
                    mc.universalStatus(uMatch,null);
                }
                else
                {
                    if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
                        showSelectCategoryDialog();
                    else
                    {
                        DM.show();
                        mc.universalStatus(uMatch,categoryId);
                    }
                }


            }
            else if(intent.hasExtra("opponent_id"))//meysam - added in 13961001
            {
                // meysam - show bet dialog and set opponent_id to sent to server...

                opponent = new UserModel();
                opponent.setId(new BigInteger(intent.getStringExtra("opponent_id")));
                opponent.setProfilePicture(intent.getStringExtra("opponent_image"));
                opponent.setUserName(intent.getStringExtra("opponent_user_name"));

                showHorizontalBetDialog();

            }
        }
    };

    private void afterBetDialogOperation() {

        if(matchType.equals(UniversalMatchModel.USER_UNIVERSAL_MATCH_TYPE_QUESTION))
        {
            showSelectCategoryDialog();
        }
        else
        {
             // meysam - call loading activity
            Intent intent = new Intent("categories_activity_broadcast");
            intent.putExtra("category_id", "null");
            LocalBroadcastManager.getInstance(HazelMatchIndexActivity.this).sendBroadcast(intent);

        }
    }

    private void showSelectCategoryDialog()
    {
        int[] tmp;
        if(session.hasRandomCategoryIds())
            tmp = session.getRandomCategoryIds();
        else
        {
            tmp =  CategoryModel.getThreeRandomCategory();
            session.setRandomCategoryIds(tmp);
        }
        DialogSelectCategoryModel.show(HazelMatchIndexActivity.this, String.valueOf(tmp[0]), String.valueOf(tmp[1]),String.valueOf(tmp[2]), false, true,"categories_activity_broadcast");
    }

    //meysam - added in 13960608
    private void startLoadingActivity(BetModel bet, Integer addedTime, Integer matchType)
    {
        session.saveItem(SessionModel.KEY_BET_ID,bet.getBetId());

        //meysam - added in 13961001
        UniversalMatchModel.setCurrentMatchTime(getApplicationContext(),bet.getTime()+addedTime);
        UniversalMatchModel.setMinSec(bet.getBetId());
        Intent i = new Intent(HazelMatchIndexActivity.this, UniversalMatchLoadingActivity.class);
        i.putExtra("betAmount", bet.getAmount().toString());
        i.putExtra("betId", bet.getBetId());
        i.putExtra("isNitro", bet.getNitro());
        i.putExtra("rewardTime",addedTime);
        i.putExtra("categoryId", categoryId);
        i.putExtra("isRewardLuck", bet.getBetLuck());
        i.putExtra("matchType", matchType);
        LuckyLordRecyclerViewAdapter.chosenBet = null;
        HazelMatchIndexActivity.this.startActivity(i);

        returningFromMatch = true;
        ////////////////////////////////

    }

    private void setCategoriesClickListeners()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                LuckyButton lb_friends = (LuckyButton) findViewById(R.id.btn_with_firend_context);
                LuckyButton lb_search_opponent = (LuckyButton) findViewById(R.id.btn_search_opponent_context);
                LuckyButton lb_team = (LuckyButton) findViewById(R.id.btn_team_context);


                lb_friends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // meysam - show list of friends - added in 13961001
                        Intent intent = new Intent(HazelMatchIndexActivity.this, FriendListActivity.class);
                        intent.putExtra("list_type", UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);
                        intent.putExtra("match_type", matchType);
                        intent.putExtra("opponent_type", UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND);
                        startActivity(intent);
                        //////////////////////////////////
                    }
                });
                lb_search_opponent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // meysam - show search user activity - added in 13961001
                        Intent intent = new Intent(HazelMatchIndexActivity.this, SearchUserActivity.class);
                        intent.putExtra("list_type", "search_users");
                        intent.putExtra("match_type", matchType);
                        intent.putExtra("opponent_type", UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM);
                        startActivity(intent);
                        //////////////////////////////////
                    }
                });
                lb_team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utility.displayToast(HazelMatchIndexActivity.this, getString(R.string.msg_InNewVersion), Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }


    @Override
    public void onPause() {

        if(customHandler != null)
        {
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler = null;
            timeWaitBeforeRefresh = 10000;
        }

        if(HCAC != null)
        {
            HCAC.setInflater(null);
            HCAC = null;
        }


        super.onPause();
    }



    public void onDestroy() {

        if(customHandler != null)
        {
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler = null;
            timeWaitBeforeRefresh = 10000;
        }

        if(mc != null)
        {
            mc.setCntx(null);
            mc.deleteObservers();
            mc = null;
        }

        if(uc != null)
        {
            uc.setCntx(null);
            uc.deleteObservers();
            uc = null;
        }

        if(HCAC != null)
            HCAC.setInflater(null);

        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        Tapsell.setRewardListener(null);

        // Unregister since the activity is about to be closed.
        LuckyLordRecyclerViewAdapter.chosenBet = null;//meysam - added in 13960608
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        db = null;
        sfxm.releaseSoundPool();
        sfxm = null;

        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(UniversalMatchQuestionActivity.MQA != null)
        {
            if(UniversalMatchQuestionActivity.MQA.customHandler != null)
            {
                UniversalMatchQuestionActivity.MQA.customHandler.removeCallbacksAndMessages(null);
            }
            UniversalMatchQuestionActivity.MQA = null;
        }

        if(betDialog != null)
            if(betDialog.isShowing())
                betDialog.hide();

        if(!inCreating)
        {


//            mc = new MatchController(getApplicationContext());
//            mc.addObserver(this);
//
//            uc = new UserController(getApplicationContext());
//            uc.addObserver(this);

            if(!loading_matches)
            {
                if(customHandler == null)
                    customHandler = new Handler();
                customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);

                if(uMatches != null)
                {

                    if(HCAC != null)
                        HCAC.setInflater(null);
                    ListView lv = (ListView) findViewById(R.id.list_recent_games);
                    lv.setAdapter(null);

                    if(returningFromMatch)
                    {

                        if(mc == null)
                            HazelMatchIndexActivity.this.finish();

                        returningFromMatch = false;
                        DM.show();
                        mc.universalIndex(matchType,UniversalMatchModel.USER_UNIVERSAL_MATCH_OPPONENT_TYPE_ALL);
                    }
                    else
                    {
                        fillUniversalMatchList();
                    }

                }
                ///////////////////////////////////
            }



        }
        else
        {
            inCreating = false;
        }
    }
}
