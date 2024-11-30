package ir.fardan7eghlim.luckylord.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MatchController;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.BetModel;
import ir.fardan7eghlim.luckylord.models.ChatModel;
import ir.fardan7eghlim.luckylord.models.MatchModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.WordModel;
import ir.fardan7eghlim.luckylord.services.ChatService;
import ir.fardan7eghlim.luckylord.views.hazels.HazelMatchIndexActivity;
import ir.fardan7eghlim.luckylord.views.match.UniversalMatchResultActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;
import ir.fardan7eghlim.luckylord.views.user.UserProfileActivity;

public class CustomAdapterList_user extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private LayoutInflater inflater=null;
    private CustomAdapterList_user CAL=this;
    private Object foregn_key_obj;
    private DialogModel DM;
    private DatabaseHandler db;
    private Integer matchType;
    private Integer opponentType;
    private RecyclerView recyclerView;//meysam - added in 13960918
    private LuckyLordRecyclerViewAdapter mAdapter;//meysam - added in 13960918
    private String intentName;
    private SessionModel session;
    private MatchController mc;
    private UniversalMatchModel matchToChange;
    private View viewForChange;
    private Holder holderForChange;
    private Boolean revokeCalled;
//    private Integer ItemToRemovePosition;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        inflater = inflater;
    }

    public CustomAdapterList_user(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List=list;
        Tag=tag.toLowerCase();
        context=c;
        foregn_key_obj=null;
        inflater = (LayoutInflater)context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DM = new DialogModel(c);
        db = new DatabaseHandler(context);

        matchType = null;
        opponentType = null;
        session = new SessionModel(context.getApplicationContext());
        mc = new MatchController(context);
        mc.addObserver(this);
        revokeCalled = false;
//        ItemToRemovePosition = null;
    }
    public CustomAdapterList_user(Context c, java.util.List<Object> list, String tag, Integer inputMatchType, Integer inputOpponentType) {

        List=list;
        Tag=tag.toLowerCase();
        context=c;
        foregn_key_obj=null;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DM = new DialogModel(c);
        db = new DatabaseHandler(context);

        matchType = inputMatchType;
        opponentType = inputOpponentType;
        session = new SessionModel(context.getApplicationContext());
        mc = new MatchController(context);
        mc.addObserver(this);
        revokeCalled = false;
//        ItemToRemovePosition = null;
    }
    public CustomAdapterList_user(Context c, java.util.List<Object> list, String tag, Object obj) {
        // TODO Auto-generated constructor stub
        List=list;
        Tag=tag.toLowerCase();
        context=c;
        foregn_key_obj=obj;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DM = new DialogModel(c);
        db = new DatabaseHandler(context);

        matchType = null;
        opponentType = null;
        session = new SessionModel(context.getApplicationContext());
        mc = new MatchController(context);
        mc.addObserver(this);
        revokeCalled = false;
//        ItemToRemovePosition = null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void updateAdapter(java.util.List<Object> list) {
        this.List= list;
        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
        hideLoading(intentName);
        if(arg != null)
        {

            if (arg instanceof Boolean)
            {

                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    showToast(context.getString(R.string.error_weak_connection),intentName);
                }
                else{
                    showToast(context.getString(R.string.msg_OperationSuccess),intentName);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_UNIVERSAL_MATCH_CHANGE_STATUS))
                {


                    if(((ArrayList) arg).get(2) != null)
                    {
                        DialogPopUpModel.show(context,new RequestRespondModel(context).getErrorCodeMessage(new Integer(((ArrayList) arg).get(2).toString())),context.getString(R.string.btn_OK),null,false,true);

                        String changedStatus = ((ArrayList) arg).get(2).toString();
                        if(new Integer(RequestRespondModel.ERROR_OPPONENT_ACCEPTED_CODE).equals(new Integer(changedStatus)))
                        {
                            matchToChange.setMatchStatus(UniversalMatchModel.STATUS_YOUR_TURN);
                            matchToChange.setEnded(0);
                            initializeMatch(matchToChange,viewForChange,holderForChange);
                            notifyDataSetInvalidated();

                        }
                        if(new Integer(RequestRespondModel.ERROR_OPPONENT_CANCELED_CODE).equals(new Integer(changedStatus)))
                        {
                            List.remove(matchToChange);
                            db.deleteMatch(matchToChange.getId());
                            notifyDataSetChanged();
                        }
                        revokeCalled = false;

                    }
                    else
                    {

                        DialogPopUpModel.show(context,context.getString(R.string.msg_OperationSuccess),context.getString(R.string.btn_OK),null,false,true);

                        if(revokeCalled)
                        {
                            // meysam - return spented hazel or luck to user based on bet and show add hazel or luck animation...
                            BetModel betToCancel = BetModel.getBetById(matchToChange.getBet());
                            if(betToCancel.getBetLuck())
                            {
                                if(betToCancel.getAmount() != 0)
                                {
                                    session.increaseLuck( betToCancel.getAmount().toString());

                                    Intent tintent = new Intent("categories_activity_broadcast");
                                    // You can also include some extra data.
                                    tintent.putExtra("changeLuck", betToCancel.getAmount().toString());
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(tintent);

                                }
                            }
                            else
                            {
                                session.increaseHazel( betToCancel.getAmount().toString());

                                Intent tintent = new Intent("categories_activity_broadcast");
                                // You can also include some extra data.
                                tintent.putExtra("changeHazel", betToCancel.getAmount().toString());
                                LocalBroadcastManager.getInstance(context).sendBroadcast(tintent);
                            }
                            ///////////////////////////////////////

                            /////////////////////////////////
                            revokeCalled = false;
                            List.remove(matchToChange);
                            db.deleteMatch(matchToChange.getId());
                            notifyDataSetChanged();
                        }
                        else
                        {
                            matchToChange.setMatchStatus(UniversalMatchModel.STATUS_DISAPPROVED_BY_YOU);
                            matchToChange.setEnded(-1);
                            db.editMatch(matchToChange);
                            initializeMatch(matchToChange,viewForChange,holderForChange);
                            notifyDataSetInvalidated();
                        }
                    }

                    matchToChange = null;


//                    showToast(context.getString(R.string.msg_OperationSuccess),intentName);
                }
            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    showToast(context.getApplicationContext().getString(R.string.error_auth_fail),intentName);
                    session.logoutUser();

                    Intent intents = new Intent(context, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intents);
                }else {
//                    if(new Integer(RequestRespondModel.ERROR_OPPONENT_ACCEPTED_CODE).equals(new Integer(arg.toString())) ||
//                            new Integer(RequestRespondModel.ERROR_OPPONENT_CANCELED_CODE).equals(new Integer(arg.toString())) )
//                    {
//                        DialogPopUpModel.show(context,new RequestRespondModel(context).getErrorCodeMessage(new Integer(arg.toString())),context.getString(R.string.btn_OK),null,false,true);
//                    }
//                    else
//                    {
                        showToast(new RequestRespondModel(context).getErrorCodeMessage(new Integer(arg.toString())),intentName);

//                    }
                }
            }
            else
            {
                showToast(context.getString(R.string.error_weak_connection),intentName);

            }

        }
        else
        {
            showToast(context.getString(R.string.error_weak_connection),intentName);
        }
    }

    public class Holder
    {
        FrameLayout result_avt;
        TextView text_username;
        TextView text_score;
        Avatar avatar;
        ImageView online;

        public Holder(FrameLayout result_avt, TextView text_first, TextView text_date_from,ImageView online) {
            this.result_avt = result_avt;
            this.text_username = text_first;
            this.text_score = text_date_from;
            this.online=online;
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder=new Holder(new FrameLayout(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new ImageView(context.getApplicationContext()));
        final View rowView= inflater.inflate(R.layout.row_list_user2, null);
        holder.text_username= (TextView) rowView.findViewById(R.id.text_username);
        holder.text_score= (TextView) rowView.findViewById(R.id.text_score);
        holder.result_avt= (FrameLayout) rowView.findViewById(R.id.result_avt);
        holder.online=rowView.findViewById(R.id.result_online_avt);

        switch (Tag){
            //if we have list of users
            case UserModel.TAG_USER_RANK_TYPE_LEVEL:
            case UserModel.TAG_USER_RANK_TYPE_LUCK:
                View row_bg_userList=rowView.findViewById(R.id.row_bg_userList);
                row_bg_userList.setBackgroundResource(R.drawable.bg_02);
                final UserModel user=(UserModel) List.get(position);
                int p=user.getRank();
                if(user.getProfilePicture() != null)
                {
                    holder.avatar=new Avatar(user.getProfilePicture());
                    holder.avatar.setAvatar((Activity) context,rowView);
                }

                holder.text_score.setTextColor(Color.parseColor("#875107"));
                if(p==1){
                    ImageView result_hat= (ImageView) rowView.findViewById(R.id.result_hat_avt);
                    result_hat.setImageResource(R.drawable.mf_king_hat_a);
                    result_hat.setVisibility(View.VISIBLE);
                }else if(p==2){
                    ImageView result_hat= (ImageView) rowView.findViewById(R.id.result_hat_avt);
                    result_hat.setImageResource(R.drawable.mf_king_hat_b);
                    result_hat.setVisibility(View.VISIBLE);
                }else if(p==3){
                    ImageView result_hat= (ImageView) rowView.findViewById(R.id.result_hat_avt);
                    result_hat.setImageResource(R.drawable.mf_king_hat_c);
                    result_hat.setVisibility(View.VISIBLE);
                }


                String temp_username = user.getUserName()!= null?user.getUserName():UserModel.getVisitorHashedName(user.getVisitorUserName());
                if(!temp_username.contains("Visitor_"))
                {
                    if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username)) {
                        holder.online.setVisibility(View.VISIBLE);
                        holder.online.setImageResource(R.drawable.st_online);
                    }
                }

                holder.text_username.setText(temp_username);
                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));

                String s="";
                if(Tag.equals(UserModel.TAG_USER_RANK_TYPE_LEVEL))
                {

                        s="سطح:"+(Utility.enToFa(Utility.calculateLevel(user.getLevelScore()).toString()))+"\n"+"رتبه:"+p;

                }
                else
                {
                    if(user.getLuck()<0){
                        s="شانس:"+(Utility.enToFa(String.valueOf(user.getLuck()*-1)))+"-"+"\n"+"رتبه:"+p+"";
                    }else{
                        s="شانس:"+(Utility.enToFa(user.getLuck().toString()))+"\n"+"رتبه:"+p;
                    }
                }


                holder.text_score.setText(Utility.enToFa(s));

                holder.result_avt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(user.getUserName() != null) {
                            DM.show();
                            //////////////////////////////////////////////////////////////
                            SessionModel session = new SessionModel(context);
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                                UserModel tmpUser = db.getUserByUserName(user.getUserName());
                                if(tmpUser.getUserName() != null)
                                {
                                    tmpUser.setProfilePicture(user.getProfilePicture());
                                    tmpUser.setUpdateDateTime(null);
                                    db.editUser(tmpUser,null);
                                }
                            }


                            ////////////////////////////////////////////////////////////////////
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra("EXTRA_User_Name", user.getUserName());
                            intent.putExtra("EXTRA_Avatar", user.getProfilePicture());
                            intent.putExtra("EXTRA_AllowChat", user.getAllowChat());

                            context.startActivity(intent);
                            DM.hide();
                        }else{
                            Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                        }
                    }
                });


                row_bg_userList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(user.getUserName() != null) {
                            DM.show();
                            //////////////////////////////////////////////////////////////
                            SessionModel session = new SessionModel(context);
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                                UserModel tmpUser = db.getUserByUserName(user.getUserName());
                                if(tmpUser.getUserName() != null)
                                {
                                    tmpUser.setProfilePicture(user.getProfilePicture());
                                    tmpUser.setUpdateDateTime(null);
                                    db.editUser(tmpUser,null);
                                }

                            }

                            ////////////////////////////////////////////////////////////////////
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra("EXTRA_User_Name", user.getUserName());
                            intent.putExtra("EXTRA_Avatar", user.getProfilePicture());
                            intent.putExtra("EXTRA_AllowChat", user.getAllowChat());

                            context.startActivity(intent);
                            DM.hide();
                        }else{
                            Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                        }

                    }
                });
                break;
            case RequestRespondModel.TAG_WINNERS_DRAW://if we have list of winners
                row_bg_userList=rowView.findViewById(R.id.row_bg_userList);
                row_bg_userList.setBackgroundResource(R.drawable.btn_empty_05);

                final UserModel winner=(UserModel) List.get(position);

                if(winner.getProfilePicture() != null)
                {
                    holder.avatar=new Avatar(winner.getProfilePicture());
                    holder.avatar.setAvatar((Activity) context,rowView);
                }

                temp_username = UserModel.getVisitorHashedName(winner.getUserName());
                if(!temp_username.contains("Visitor_"))
                {
                    if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username)){
                        holder.online.setVisibility(View.VISIBLE);
                        holder.online.setImageResource(R.drawable.st_online);
                    }
                }

                holder.text_username.setText(temp_username);
                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));
                holder.text_score.setText(winner.getReward());

                holder.result_avt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //////////////////////////////////////////////////////////////
                        SessionModel session = new SessionModel(context);
                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                            UserModel tmpUser = db.getUserByUserName(winner.getUserName());
                            if(tmpUser.getUserName() != null)
                            {

                                DM.show();
                                tmpUser.setProfilePicture(winner.getProfilePicture());
                                tmpUser.setUpdateDateTime(null);
                                db.editUser(tmpUser,null);

                                Intent intent = new Intent(context, UserProfileActivity.class);
                                intent.putExtra("EXTRA_User_Name", winner.getUserName());
                                intent.putExtra("EXTRA_Avatar", winner.getProfilePicture());
                                intent.putExtra("EXTRA_AllowChat", winner.getAllowChat());

                                context.startActivity(intent);
                                DM.hide();

                            }
                            else
                            {
                                Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                            }
                            ////////////////////////////////////////////////////////////////////
                        }

                    }
                });

                break;
//            case RequestRespondModel.TAG_MATCH_INDEX://if we have list of recent match
//                View row_bg_matchList=rowView.findViewById(R.id.row_bg_userList);
//                row_bg_matchList.setBackgroundResource(R.drawable.btn_empty_05);
//
//                final MatchModel match=(MatchModel) List.get(position);
//
//                if(match.getOpponent().getProfilePicture() != null)
//                {
//                    holder.avatar=new Avatar(match.getOpponent().getProfilePicture());
//                    holder.avatar.setAvatar((Activity) context,rowView);
//                }
//
//
//                if(match.getOpponent().getUserName()!=null) {
//                    if(match.getOpponent().getUserName().contains("Visitor_"))
//                    {
//                        holder.text_username.setText(UserModel.getVisitorHashedName(match.getOpponent().getUserName()));
//                    }
//                    else
//                    {
//                        temp_username = match.getOpponent().getUserName();
//                        if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username))
//                        {
//                            holder.online.setVisibility(View.VISIBLE);
//                            holder.online.setImageResource(R.drawable.st_online);
//                        }
//                        holder.text_username.setText(temp_username);
//                    }
//                }else{
//                    holder.text_username.setText("مهمان");
//                }
//                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));
//                if(match.getEnded()){
//                    if(MatchModel.WIN.toString().equals(match.getWinner().toString())){
//                        holder.text_score.setText("بردی");
//                        holder.text_score.setTextColor(Color.parseColor("#02b055"));
//                    }
//                    else if(MatchModel.LOST.toString().equals(match.getWinner().toString()))
//                    {
//                        holder.text_score.setText("باختی");
//                        holder.text_score.setTextColor(Color.parseColor("#9A2617"));
//                    }
//                    else{
//                        holder.text_score.setText("مساوی");
//                    }
//
//                    row_bg_matchList.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, UniversalMatchResultActivity.class);
//                            intent.putExtra("MatchId", "000");
//                            intent.putExtra("MatchOCC", match.getOpponentCorrectCount().toString());
//                            intent.putExtra("MatchOUN", match.getOpponent().getUserName());
//                            intent.putExtra("MatchOA", match.getOpponent().getProfilePicture());
//                            intent.putExtra("MatchOST", match.getOpponentSpentTime().toString());
//                            intent.putExtra("MatchSCC", match.getSelfCorrectCount().toString());
//                            intent.putExtra("MatchSCT", match.getSelfSpentTime().toString());
//                            intent.putExtra("MatchWinner", match.getWinner().toString());
//                            intent.putExtra("MatchAllowChat", match.getOpponent().getAllowChat());
//                            intent.putExtra("from", "status");
//                            context.startActivity(intent);
//                        }
//                    });
//                }else{
//                    holder.text_score.setText(" برای نمایش نتیجه کلیک کن");
//                    row_bg_matchList.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, UniversalMatchResultActivity.class);
//                            intent.putExtra("MatchId", match.getId().toString());
//                            intent.putExtra("MatchGuid", match.getGuid());
//                            intent.putExtra("MatchOUN", match.getOpponent().getUserName());
//                            intent.putExtra("MatchOA", match.getOpponent().getProfilePicture());
//                            intent.putExtra("MatchSCC", match.getSelfCorrectCount().toString());
//                            intent.putExtra("MatchAllowChat", match.getOpponent().getAllowChat());
//                            intent.putExtra("from", "status");
//
//                            if(match.getSelfSpentTime().toString().equals("null"))
//                                intent.putExtra("MatchSCT", MatchModel.DEFAULT_TOTAL_TIME);
//                            else
//                                intent.putExtra("MatchSCT", match.getSelfSpentTime().toString());
//                            context.startActivity(intent);
//                        }
//                    });
//                }
//                holder.text_score.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//
//                holder.result_avt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        if(match.getOpponent().getUserName() != null) {
//                            if(match.getOpponent().getUserName().contains("Visitor_"))
//                            {
//                                Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
//                            }
//                            else
//                            {
//                                DM.show();
//                                //////////////////////////////////////////////////////////////
//                                SessionModel session = new SessionModel(context);
//                                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
//                                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
//                                    UserModel tmpUser = db.getUserByUserName(match.getOpponent().getUserName());
//                                    if(tmpUser.getUserName() != null)
//                                    {
//                                        tmpUser.setProfilePicture(match.getOpponent().getProfilePicture());
//                                        db.editUser(tmpUser,null);
//                                    }
//
//
//                                }
//
//                                ////////////////////////////////////////////////////////////////////
//                                Intent intent = new Intent(context, UserProfileActivity.class);
//                                intent.putExtra("EXTRA_User_Name", match.getOpponent().getUserName());
//                                intent.putExtra("EXTRA_Avatar", match.getOpponent().getProfilePicture());
//                                context.startActivity(intent);
//                                DM.hide();
//                            }
//
//                        }else{
//                            Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
//                        }
//                    }
//                });
//
//                break;
            case RequestRespondModel.TAG_USER_FRIEND_LIST://if we have list of friends
                row_bg_userList=rowView.findViewById(R.id.row_bg_userList);
                row_bg_userList.setBackgroundResource(R.drawable.btn_empty_05);

                final UserModel userRequested=(UserModel) List.get(position);

                if(userRequested.getProfilePicture() != null)
                {
                    holder.avatar=new Avatar(userRequested.getProfilePicture());
                    holder.avatar.setAvatar((Activity) context,rowView);
                }


                temp_username = UserModel.getVisitorHashedName(userRequested.getUserName());
                if(!temp_username.contains("Visitor_"))
                {
                    if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username))
                    {
                        holder.online.setVisibility(View.VISIBLE);
                        holder.online.setImageResource(R.drawable.st_online);
                    }
                }

                holder.text_username.setText(temp_username);
                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));
                if(userRequested.getLuck()<0){
                    s="شانس:"+(Utility.enToFa(String.valueOf(userRequested.getLuck()*-1)))+"-"+"\n"+"سطح:"+Utility.enToFa(Utility.calculateLevel(userRequested.getLevelScore()).toString());
                }else{
                    s="شانس:"+(Utility.enToFa(userRequested.getLuck().toString()))+"\n"+"سطح:"+Utility.enToFa(Utility.calculateLevel(userRequested.getLevelScore()).toString());
                }

                holder.text_score.setText(Utility.enToFa(s));

                holder.result_avt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!userRequested.getUserName().contains("Visitor_"))
                        {
                            DM.show();
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra("EXTRA_User_Name", userRequested.getUserName());
                            intent.putExtra("EXTRA_Avatar", userRequested.getProfilePicture());
                            if(userRequested.getFriendshipStatus() != null)
                                intent.putExtra("EXTRA_Friendship_Status", userRequested.getFriendshipStatus());
//                            else
//                                intent.putExtra("EXTRA_Friendship_Status", -3);
                            intent.putExtra("EXTRA_Luck", userRequested.getLuck());
                            intent.putExtra("EXTRA_Level", userRequested.getLevelScore());
                            intent.putExtra("EXTRA_Cups", userRequested.getCups());
                            intent.putExtra("EXTRA_Hazel", userRequested.getHazel());
                            intent.putExtra("EXTRA_User_Id", userRequested.getId());
                            intent.putExtra("EXTRA_Friendship_Count", userRequested.getFriendsCount());
                            intent.putExtra("EXTRA_AllowChat", userRequested.getAllowChat());

                            context.startActivity(intent);
                            DM.hide();
                        }
                        else
                        {
                            Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                        }

                    }
                });
                row_bg_userList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchType == null)
                        {
                            if(!userRequested.getUserName().contains("Visitor_"))
                            {
                                DM.show();
                                Intent intent = new Intent(context, UserProfileActivity.class);
                                intent.putExtra("EXTRA_User_Name", userRequested.getUserName());
                                intent.putExtra("EXTRA_Avatar", userRequested.getProfilePicture());
                                if(userRequested.getFriendshipStatus() != null)
                                    intent.putExtra("EXTRA_Friendship_Status", userRequested.getFriendshipStatus());
//                                else
//                                    intent.putExtra("EXTRA_Friendship_Status", -3);
                                intent.putExtra("EXTRA_Luck", userRequested.getLuck());
                                intent.putExtra("EXTRA_Level", userRequested.getLevelScore());
                                intent.putExtra("EXTRA_Cups", userRequested.getCups());
                                intent.putExtra("EXTRA_Hazel", userRequested.getHazel());
                                intent.putExtra("EXTRA_User_Id", userRequested.getId());
                                intent.putExtra("EXTRA_Friendship_Count", userRequested.getFriendsCount());
                                intent.putExtra("EXTRA_AllowChat", userRequested.getAllowChat());

                                context.startActivity(intent);
                                DM.hide();
                            }
                            else
                            {
                                Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                            }
                        }
                        else
                        {
                            // meysam - show chose bet and chose category dialog for question match or send match request wrt type...
                            //is request was successfully sent then close this list and it's respective activity...

                            Intent intent = new Intent("categories_activity_broadcast");
                            // You can also include some extra data.
                            intent.putExtra("opponent_id", userRequested.getId().toString());
                            intent.putExtra("opponent_image", userRequested.getProfilePicture());
                            intent.putExtra("opponent_user_name", userRequested.getUserName() == null ? userRequested.getVisitorUserNameShow():userRequested.getUserName());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            ((Activity) context).finish();
                            /////////////////////////////////////////////////////////////////////////////////////////////////
                        }


                    }
                });

                break;
            case RequestRespondModel.TAG_SEARCH_USER://if we have list of resault of search
                row_bg_userList=rowView.findViewById(R.id.row_bg_userList);
                row_bg_userList.setBackgroundResource(R.drawable.btn_empty_05);

                final UserModel userSearched=(UserModel) List.get(position);

                if(userSearched.getProfilePicture() != null)
                {
                    holder.avatar=new Avatar(userSearched.getProfilePicture());
                    holder.avatar.setAvatar((Activity) context,rowView);
                }


                temp_username = UserModel.getVisitorHashedName(userSearched.getUserName());
                if(!temp_username.contains("Visitor_"))
                {
                    if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username))
                    {
                        holder.online.setVisibility(View.VISIBLE);
                        holder.online.setImageResource(R.drawable.st_online);
                    }
                }

                holder.text_username.setText(temp_username);
                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));
                if(userSearched.getLuck()<0){
                    s="شانس:"+(Utility.enToFa(String.valueOf(userSearched.getLuck()*-1)))+"-"+"\n"+"سطح:"+Utility.enToFa(Utility.calculateLevel(userSearched.getLevelScore()).toString());
                }else{
                    s="شانس:"+(Utility.enToFa(userSearched.getLuck().toString()))+"\n"+"سطح:"+Utility.enToFa(Utility.calculateLevel(userSearched.getLevelScore()).toString());
                }

                holder.text_score.setText(Utility.enToFa(s));

                holder.result_avt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //////////////////////////////////////////////////////////////


                        if(!userSearched.getUserName().contains("Visitor_"))
                        {
                            DM.show();

                            SessionModel session = new SessionModel(context);
                            if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                    session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                                UserModel tmpUser = db.getUserByUserName(userSearched.getUserName());
                                if(tmpUser.getUserName() != null)
                                {
                                    tmpUser.setProfilePicture(userSearched.getProfilePicture());
                                    tmpUser.setHazel(userSearched.getHazel());
                                    tmpUser.setLuck(userSearched.getLuck());
                                    tmpUser.setId(userSearched.getId());
                                    tmpUser.setFriendshipStatus(userSearched.getFriendshipStatus());
                                    tmpUser.setUpdateDateTime(null);
                                    db.editUser(tmpUser,null);
                                }

                            }

                            ////////////////////////////////////////////////////////////////////
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra("EXTRA_User_Name", userSearched.getUserName());
                            intent.putExtra("EXTRA_Avatar", userSearched.getProfilePicture());
                            if(userSearched.getFriendshipStatus() != null)
                                intent.putExtra("EXTRA_Friendship_Status", userSearched.getFriendshipStatus());
                            intent.putExtra("EXTRA_Luck", userSearched.getLuck());
                            intent.putExtra("EXTRA_Level", userSearched.getLevelScore());
                            intent.putExtra("EXTRA_Cups", userSearched.getCups());
                            intent.putExtra("EXTRA_Hazel", userSearched.getHazel());
                            intent.putExtra("EXTRA_User_Id", userSearched.getId());
                            intent.putExtra("EXTRA_Friendship_Count", userSearched.getFriendsCount());
                            intent.putExtra("EXTRA_AllowChat", userSearched.getAllowChat());

                            context.startActivity(intent);
                            DM.hide();
                        }
                        else
                        {
                            Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                        }
                    }
                });
                row_bg_userList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchType == null)
                        {

                            if(!userSearched.getUserName().contains("Visitor_"))
                            {
                                DM.show();
                                SessionModel session = new SessionModel(context);
                                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                                    UserModel tmpUser = db.getUserByUserName(userSearched.getUserName());
                                    if(tmpUser.getUserName() != null)
                                    {
                                        tmpUser.setProfilePicture(userSearched.getProfilePicture());
                                        tmpUser.setHazel(userSearched.getHazel());
                                        tmpUser.setLuck(userSearched.getLuck());
                                        tmpUser.setId(userSearched.getId());
                                        tmpUser.setFriendshipStatus(userSearched.getFriendshipStatus());
                                        tmpUser.setUpdateDateTime(null);
                                        db.editUser(tmpUser,null);
                                    }

                                }

                                ////////////////////////////////////////////////////////////////////
                                Intent intent = new Intent(context, UserProfileActivity.class);
                                intent.putExtra("EXTRA_User_Name", userSearched.getUserName());
                                intent.putExtra("EXTRA_Avatar", userSearched.getProfilePicture());
                                if(userSearched.getFriendshipStatus() != null)
                                    intent.putExtra("EXTRA_Friendship_Status", userSearched.getFriendshipStatus());
                                intent.putExtra("EXTRA_Luck", userSearched.getLuck());
                                intent.putExtra("EXTRA_Level", userSearched.getLevelScore());
                                intent.putExtra("EXTRA_Cups", userSearched.getCups());
                                intent.putExtra("EXTRA_Hazel", userSearched.getHazel());
                                intent.putExtra("EXTRA_User_Id", userSearched.getId());
                                intent.putExtra("EXTRA_Friendship_Count", userSearched.getFriendsCount());
                                intent.putExtra("EXTRA_AllowChat", userSearched.getAllowChat());

                                context.startActivity(intent);
                                DM.hide();
                            }
                        else
                            {
                                Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                            }
                        }
                        else
                        {
                            // meysam - send broadcast to category activity with opponent_id for showing dialog model
                            //is request was successfully sent then close this list and it's respective activity...



                            Intent intent = new Intent("categories_activity_broadcast");
                            // You can also include some extra data.
                            intent.putExtra("opponent_id", userSearched.getId().toString());
                            intent.putExtra("opponent_image", userSearched.getProfilePicture());
                            intent.putExtra("opponent_user_name", userSearched.getUserName() == null ? userSearched.getVisitorUserNameShow():userSearched.getUserName());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            ((Activity) context).finish();
                            /////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                    }
                });

                break;
            case RequestRespondModel.TAG_UNIVERSAL_MATCH_INDEX://if we have list of recent match
                intentName = "categories_activity_broadcast";
                final UniversalMatchModel uMatch=(UniversalMatchModel) List.get(position);

                initializeMatch(uMatch,rowView,holder);

                break;
            case WordModel.TAG_SEARCH_WORDS://if we have list of winners
                row_bg_userList=rowView.findViewById(R.id.row_bg_userList);
                row_bg_userList.setBackgroundResource(R.drawable.bg_05);

                final WordModel wordRequested=(WordModel) List.get(position);

                holder.text_username.setText(wordRequested.getWord());
                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));

                holder.text_score.setText("");

                holder.result_avt.setVisibility(View.GONE);


                break;

            case "chats"://if we have list of users
                final ChatModel chat=(ChatModel) List.get(position);

                row_bg_userList=rowView.findViewById(R.id.row_bg_userList);
                row_bg_userList.setBackgroundResource(R.drawable.btn_empty_05);

                //avatar

                UserModel friend = new DatabaseHandler( context.getApplicationContext()).getUserByUserName(chat.getUserName());
                if(friend != null)
                {
                    if(friend.getProfilePicture() != null)
                    {
                        holder.avatar=new Avatar(friend.getProfilePicture());
                        holder.avatar.setAvatar((Activity) context,rowView);
                    }

                }
                holder.text_username.setText(chat.getUserName());
                holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));

                String message = chat.getText().substring(1);
                if(message.equals("##0s1A##")){
                    message = "لایک";
                }else if(message.equals("#0e1v##")){
                    message = "قلب";
                }else if(message.equals("#fs1n##")){
                    message = "بوس";
                }else if(message.equals("#pQ1m##")){
                    message = "خوشم نیومد";
                }else{
                    //meysam - do nothing
                }
                holder.text_score.setText(message);

                row_bg_userList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent resultIntent = new Intent(context, UserProfileActivity.class);
                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        resultIntent.putExtra("EXTRA_User_Name",chat.getUserName());
                        Intent[] intents = new Intent[1];
                        intents[0] = resultIntent;
                        context.startActivities(intents);
                    }
                });
                holder.result_avt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent resultIntent = new Intent(context, UserProfileActivity.class);
                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        resultIntent.putExtra("EXTRA_User_Name",chat.getUserName());
                        Intent[] intents = new Intent[1];
                        intents[0] = resultIntent;
                        context.startActivities(intents);
                    }
                });
        }

        return rowView;
    }

    private void initializeMatch(final UniversalMatchModel uMatch, final View rowView, final Holder holder)
    {
        View row_bg_matchList=rowView.findViewById(R.id.row_bg_userList);
        row_bg_matchList.setBackgroundResource(R.drawable.btn_empty_05);

        if(uMatch.getOpponent().getProfilePicture() != null)
        {
            holder.avatar=new Avatar(uMatch.getOpponent().getProfilePicture());
            holder.avatar.setAvatar((Activity) context,rowView);
        }


        if(uMatch.getOpponent().getUserName()!=null) {
//            if(uMatch.getOpponent().getUserName().contains("Visitor_"))
//            {
//                holder.text_username.setText(UserModel.getVisitorHashedName(uMatch.getOpponent().getUserName()));
//            }
//            else
//            {
            String temp_username = uMatch.getOpponent().getUserName();
            if(!temp_username.contains("Visitor_"))
            {
                if(ChatService.cc != null && ChatService.cc.isUserOnline(temp_username)){
                    holder.online.setVisibility(View.VISIBLE);
                    holder.online.setImageResource(R.drawable.st_online);
                }
            }

                holder.text_username.setText(temp_username);
//            }
        }else{
            holder.text_username.setText("مهمان");
        }
        holder.text_username.setTextColor(Color.parseColor("#FF4F2F05"));

        if(uMatch.getEnded() != null && (uMatch.getEnded().equals(1) || uMatch.getEnded().equals(-1)|| uMatch.getEnded().equals(-2))){
            if(UniversalMatchModel.STATUS_WIN == uMatch.getMatchStatus()){
                holder.text_score.setText("بردی");
                holder.text_score.setTextColor(Color.parseColor("#02b055"));
            }
            else if(UniversalMatchModel.STATUS_LOST == uMatch.getMatchStatus())
            {
                holder.text_score.setText("باختی");
                holder.text_score.setTextColor(Color.parseColor("#9A2617"));
            }
            else if(UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT == uMatch.getMatchStatus())
            {
                holder.text_score.setText("حریف درخواست را رد کرد");
//                holder.text_score.setTextColor(Color.RED);
                holder.text_score.setTextColor(Color.parseColor("#ea781c"));
//                holder.text_score.setTextColor(context.getResources().getColor(R.color.creamDark));
//                holder.text_score.setTypeface(null, Typeface.BOLD);
            }
            else if(UniversalMatchModel.STATUS_DISAPPROVED_BY_YOU == uMatch.getMatchStatus())
            {
                holder.text_score.setText("شما درخواست را رد کردید");
//                holder.text_score.setTextColor(Color.RED);
                holder.text_score.setTextColor(Color.parseColor("#ea781c"));
//                holder.text_score.setTextColor(context.getResources().getColor(R.color.creamDark));
//                holder.text_score.setTypeface(null, Typeface.BOLD);
            }
            else if(UniversalMatchModel.STATUS_REQUEST_EXPIRED == uMatch.getMatchStatus())
            {
                holder.text_score.setText("درخواست منقضی شد");
//                holder.text_score.setTextColor(Color.RED);
                holder.text_score.setTextColor(Color.parseColor("#ea781c"));
//                holder.text_score.setTextColor(context.getResources().getColor(R.color.creamDark));
//                holder.text_score.setTypeface(null, Typeface.BOLD);
            }
            else{
                holder.text_score.setText("مساوی");
                holder.text_score.setTextColor(Color.BLACK);
//                holder.text_score.setTypeface(null, Typeface.BOLD);
            }

            row_bg_matchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // meysam - must change for universal...

                    Intent intent = new Intent(context, UniversalMatchResultActivity.class);
                    intent.putExtra("match_type", uMatch.getMatchType());
                    intent.putExtra("match_status", uMatch.getMatchStatus());
                    intent.putExtra("opponent_type", uMatch.getOpponentType());
                    intent.putExtra("bet_type", uMatch.getBet());
                    if(!uMatch.getSelfSpentTime().equals("null"))
                        intent.putExtra("match_self_spent_time", uMatch.getSelfSpentTime().toString());
                    if(!uMatch.getSelfCorrectCount().equals("null"))
                        intent.putExtra("match_self_correct_count", uMatch.getSelfCorrectCount().toString());
                    if(!uMatch.getOpponentSpentTime().equals("null"))
                        intent.putExtra("match_opponent_spent_time", uMatch.getOpponentSpentTime());
                    if(!uMatch.getOpponentCorrectCount().equals("null"))
                        intent.putExtra("match_opponent_correct_count", uMatch.getOpponentCorrectCount());
                    intent.putExtra("match_opponent_avatar", uMatch.getOpponent().getProfilePicture());
                    intent.putExtra("match_opponent_user_name", uMatch.getOpponent().getUserName());
                    intent.putExtra("match_opponent_allow_chat", uMatch.getOpponent().getAllowChat());


                    context.startActivity(intent);
                }
            });
        }else{
            if(uMatch.getMatchStatus().equals(UniversalMatchModel.STATUS_IN_PROGRESS))
            {
                holder.text_score.setText("نوبت حریف - برای نمایش نتیجه کلیک کن");

            }
            else if(uMatch.getMatchStatus().equals(UniversalMatchModel.STATUS_YOUR_TURN))
            {
                holder.text_score.setText("نوبت شما - برای ادامه کلیک کن");

            }
            else if(uMatch.getMatchStatus().equals(UniversalMatchModel.STATUS_OPPONENT_TURN))
            {
                holder.text_score.setText("نوبت حریف - برای نمایش نتیجه کلیک کن");

            }
            else if(uMatch.getMatchStatus().equals(UniversalMatchModel.STATUS_REQUEST_RECEIVED))
            {
                holder.text_score.setText("مسابقه درخواستی برای شما - برای قبول یا رد کلیک کن");

            }
            else if(uMatch.getMatchStatus().equals(UniversalMatchModel.STATUS_REQUEST_SENT))
            {
                holder.text_score.setText("مسابقه درخواست شده توسط شما - حریف رد یا قبول نکرده");

            }
            row_bg_matchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // meysam - call status from server
                    showUniversalMatchResponseDialog(uMatch,rowView,holder);
                }
            });

        }

        // meysam - it must exist for second user to see bet amount...
        holder.text_score.setText(holder.text_score.getText()+"\n"+ UniversalMatchModel.getBetAmountTextById(uMatch.getBet()));

        holder.text_score.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

        holder.result_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(uMatch.getOpponent().getUserName() != null) {
                    if(uMatch.getOpponent().getUserName().contains("Visitor_"))
                    {
                        Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        DM.show();
                        //////////////////////////////////////////////////////////////
                        SessionModel session = new SessionModel(context);
                        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  ) {
                            UserModel tmpUser = db.getUserByUserName(uMatch.getOpponent().getUserName());
                            if(tmpUser.getUserName() != null)
                            {
                                tmpUser.setProfilePicture(uMatch.getOpponent().getProfilePicture());
                                db.editUser(tmpUser,null);
                            }

                        }


                        ////////////////////////////////////////////////////////////////////
                        Intent intent = new Intent(context, UserProfileActivity.class);
                        intent.putExtra("EXTRA_User_Name", uMatch.getOpponent().getUserName());
                        intent.putExtra("EXTRA_Avatar", uMatch.getOpponent().getProfilePicture());
                        intent.putExtra("EXTRA_AllowChat", uMatch.getOpponent().getAllowChat());
                        context.startActivity(intent);
                        DM.hide();
                    }

                }else{
                    Utility.displayToast(context,"مهمان پروفایل نداره", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void showToast(String toastMessage,String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("show_toast", toastMessage);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private void showLoading(String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private void hideLoading(String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("hide_loading", "true");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void showUniversalMatchResponseDialog(final UniversalMatchModel match, final View rowView, final Holder holder)
    {
        boolean showDialog = false;
        if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_IN_PROGRESS))
        {
            //meysam - just call status for result .... it is for random ...

        }
        else if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_YOUR_TURN))
        {
//            holder.text_score.setText("نوبت شما - برای ادامه کلیک کن");
            //meysam - call status for recieving questions and go to match...

        }
        else if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_OPPONENT_TURN))
        {
//            holder.text_score.setText("نوبت حریف - برای نمایش نتیجه کلیک کن");
            //meysam - just call status for result...


        }
        else if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_REQUEST_RECEIVED))
        {
//            holder.text_score.setText("مسابقه درخواستی برای شما - برای قبول یا رد کلیک کن");
            //meysam - if accept then call status for recieving questions and go to match... if decline then call change status...
            showDialog = true;
//            ItemToRemovePosition = position;
            DialogPopUpFourVerticalModel.show(context," تصمیمت چیه؟ ",context.getString(R.string.btn_Accept), context.getString(R.string.btn_Decline),context.getString(R.string.btn_No),null,true, false);

        }
        else if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_REQUEST_SENT))
        {
//            holder.text_score.setText("مسابقه درخواست شده توسط شما - حریف رد یا قبول نکرده");
            //meysam - call status for recieving questions and go to match...
            showDialog = true;
            DialogPopUpFourVerticalModel.show(context,"درخواست حذف بشه؟",null, context.getString(R.string.btn_Yes),context.getString(R.string.btn_No),null,true, false);

        }

        if(showDialog)
        {
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
                                //accept match

                                Intent intent = new Intent("categories_activity_broadcast");
                                // You can also include some extra data.
                                intent.putExtra("universal_match_id", match.getId().toString());
                                intent.putExtra("universal_match_bet", match.getBet());
                                intent.putExtra("opponent_id", match.getOpponent().getId().toString());
                                intent.putExtra("opponent_image", match.getOpponent().getProfilePicture());
                                intent.putExtra("opponent_user_name", match.getOpponent().getUserName());

                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                                ((Activity) context).finish();
//
//                                showLoading(intentName);
//                                mc.universalStatus(match, null);

                            }
                            else if(DialogPopUpFourVerticalModel.dialog_result==2)
                            {
                                //decline match
                                showLoading(intentName);
                                matchToChange = match;
                                viewForChange = rowView;
                                holderForChange = holder;
                                if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_REQUEST_SENT))
                                {
                                    revokeCalled = true;
//                                    ItemToRemovePosition = po;
                                    mc.universalChangeStatus(match,-3);
                                }
                                else
                                {
                                    mc.universalChangeStatus(match,-1);
                                }
                            }
                            else if(DialogPopUpFourVerticalModel.dialog_result==3)
                            {
                                //meysam - do nothing

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
        else
        {
            // meysam - call status from category activity if self turn...
//            if(match.getMatchStatus().equals(UniversalMatchModel.STATUS_YOUR_TURN))
//            {
                Intent intent = new Intent("categories_activity_broadcast");
                // You can also include some extra data.
                intent.putExtra("universal_match_status", match.getMatchStatus().toString());
                intent.putExtra("universal_match_id", match.getId().toString());
                intent.putExtra("universal_match_bet", match.getBet());
                intent.putExtra("opponent_id", match.getOpponent().getId().toString());
                intent.putExtra("opponent_image", match.getOpponent().getProfilePicture());
                intent.putExtra("opponent_user_name", match.getOpponent().getUserName());
                intent.putExtra("self_correct_count",match.getSelfCorrectCount().toString());
                intent.putExtra("self_spent_time",match.getSelfSpentTime().toString());

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//            }
//            else
//            {
//                matchToChange = match;
//                viewForChange = rowView;
//                holderForChange = holder;
//                showLoading(intentName);
//                mc.universalStatus(match, null);
//            }

        }

    }

}