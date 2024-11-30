package ir.fardan7eghlim.luckylord.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.DrawController;
import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.models.DrawModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.RewardModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.WordModel;
import ir.fardan7eghlim.luckylord.views.hazels.DarhamTableActivity;
import ir.fardan7eghlim.luckylord.views.hazels.HazelLevelQuestionActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class CustomAdapterList_puzzle_question extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater=null;

    private QuestionController qc;
    private SessionModel session;
    private QuestionModel currentQuestion;
    private String currentWord;

    private String intentName;

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_puzzle_question.inflater = inflater;
    }

    public CustomAdapterList_puzzle_question(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List=list;
        Tag=tag.toLowerCase();
        context=c;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        qc = new QuestionController(context.getApplicationContext());
        qc.addObserver(this);

        session = new SessionModel(context);
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
//        showToast(context.getString(R.string.dlg_OperationSuccess));
        hideLoading(intentName);
        if(arg != null)
        {

            if (arg instanceof Boolean)
            {

                if(Boolean.parseBoolean(arg.toString()) == false )
                {
//                    Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    showToast(context.getString(R.string.error_weak_connection),intentName);
                }
                else{
//                    Utility.displayToast(context,context.getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                    showToast(context.getString(R.string.msg_OperationSuccess),intentName);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_RATE_TABLE_QUESTION))
                {

                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        showRateResult(true,intentName);
//                    DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_RateRegistered), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);
                        session.addToList(SessionModel.KEY_PUZZLE_TABLE_QUESTION_HATE_LIKE_LIST,currentQuestion.getId().toString());
                    }
                    else
                    {
                        showRateResult(false,intentName);
//                    DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_RateNotRegistered), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_TABLE_QUESTION))
                {

                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        showReportResult(true,intentName);
//                    DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_ReportSent), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        showReportResult(false,intentName);
//                    DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_ReportNotSent), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_REPORT_UNIVERSAL))
                {

                    if(Boolean.valueOf(((ArrayList) arg).get(1).toString()))
                    {
                        showReportResult(true,intentName);
//                    DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_ReportSent), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
                    else
                    {
                        showReportResult(false,intentName);
//                    DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_ReportNotSent), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                    }
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
                    showToast(new RequestRespondModel(context).getErrorCodeMessage(new Integer(arg.toString())),intentName);
                }
            }
            else
            {
                showToast(context.getString(R.string.error_weak_connection),intentName);

            }

        }
        else
        {
//            Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            showToast(context.getString(R.string.error_weak_connection),intentName);
        }
    }

    public class Holder
    {
        LinearLayout row_bg;
        TextView question_rlqt;
        TextView answer_rlqt;
        ImageView iv_like_rlqt;
        ImageView iv_hate_rlqt;
        ImageView iv_report_rlqt;

        public Holder(LinearLayout row_bg, TextView question_rlqt, TextView answer_rlqt,ImageView iv_like_rlqt,ImageView iv_hate_rlqt,ImageView iv_report_rlqt) {
            this.row_bg = row_bg;
            this.question_rlqt = question_rlqt;
            this.answer_rlqt = answer_rlqt;
            this.iv_like_rlqt = iv_like_rlqt;
            this.iv_hate_rlqt = iv_hate_rlqt;
            this.iv_report_rlqt = iv_report_rlqt;
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder=new Holder(new LinearLayout(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new ImageView(context.getApplicationContext()), new ImageView(context.getApplicationContext()), new ImageView(context.getApplicationContext()));
        final View rowView= inflater.inflate(R.layout.row_list_puzzle_question, null);
        holder.question_rlqt= (TextView) rowView.findViewById(R.id.question_rlqt);
        holder.answer_rlqt= (TextView) rowView.findViewById(R.id.answer_rlqt);
        holder.iv_like_rlqt= (ImageView) rowView.findViewById(R.id.iv_like_rlqt);
        holder.iv_hate_rlqt= (ImageView) rowView.findViewById(R.id.iv_hate_rlqt);
        holder.iv_report_rlqt= (ImageView) rowView.findViewById(R.id.iv_report_rlqt);

        switch (Tag){
            case RequestRespondModel.TAG_WORD_TABLE_QUESTION:
                final QuestionModel question=(QuestionModel) List.get(position);
                holder.question_rlqt.setText(Utility.enToFa((position+1)+"")+"-"+question.getDescription()+" ("+question.getAnswer().replaceAll("\\s+","").length()+" حرفی)");
                if(question.getAnswered())
                {
                    holder.answer_rlqt.setText(" جواب:"+question.getAnswer());
                }
                else
                {
                    if(question.getAnsweredCells().size() > 0)
                    {
                        holder.answer_rlqt.setText(" جواب:"+question.getAnsweredLettersAsStringWithSpace()+"...");
                    }
                    else
                    {
                        holder.answer_rlqt.setText(" جواب:"+"...");
                    }
                }

                holder.iv_like_rlqt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(!QuestionModel.isHatedOrLikedTable(question.getId(),context.getApplicationContext()))
                        {
                            currentQuestion = question;
                            //meysam - send like to server
                            intentName = "darham_table_activity_broadcast";
                            showLoading(intentName);
                            qc.rateTable(question.getId(),QuestionModel.LIKED);
                        }
                        else
                        {
                            DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_AlredyRated), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }

                    }
                });
                holder.iv_hate_rlqt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!QuestionModel.isHatedOrLikedTable(question.getId(),context.getApplicationContext()))
                        {
                            currentQuestion = question;
                            //meysam - send dislike to server
                            intentName = "darham_table_activity_broadcast";
                            showLoading(intentName);
                            qc.rateTable(question.getId(),QuestionModel.HATED);
                        }
                        else
                        {
                            DialogPopUpModel.show(context, context.getApplicationContext().getString(R.string.msg_AlredyRated), context.getApplicationContext().getString(R.string.btn_OK), null, false, false);

                        }

                    }
                });
                holder.iv_report_rlqt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // meyasm - show report dialog
                        currentQuestion = question;
                        intentName = "darham_table_activity_broadcast";
                        ReportDialogPopUpModel.show(context);
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    while(ReportDialogPopUpModel.isUp()){
                                        Thread.sleep(500);
                                    }
                                    if(!ReportDialogPopUpModel.isUp()){
                                        Thread.currentThread().interrupt();//meysam 13960525
                                        if(ReportDialogPopUpModel.dialog_result==1){
                                            //send report to server - send btn clicked
                                            showLoading(intentName);
//                                            qc.reportTable(question.getId(),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description);
                                            qc.universalReport(question.getId(),ReportDialogPopUpModel.code,ReportDialogPopUpModel.description, ReportDialogPopUpModel.DARHAM_TABLE_TYPE);


                                        }else{
                                            //do nothing
                                        }

                                        ReportDialogPopUpModel.hide();
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
                });
                break;
            case RequestRespondModel.TAG_WORD_FIND_WORDS:
                final String word=(String) List.get(position);

                holder.question_rlqt.setText(Utility.enToFa((position+1)+"")+"-"+word +" ("+  (word.replaceAll("\\s+","").length()) + " فندق جایزه )");
                holder.answer_rlqt.setText("");

                holder.iv_like_rlqt.setVisibility(View.GONE);
                holder.iv_hate_rlqt.setVisibility(View.GONE);
                holder.iv_report_rlqt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // meyasm - show report dialog
                        currentWord = word;
                        intentName = "find_words_activity_broadcast";
                        ReportDialogPopUpModel.show(context);
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    while(ReportDialogPopUpModel.isUp()){
                                        Thread.sleep(500);
                                    }
                                    if(!ReportDialogPopUpModel.isUp()){
                                        Thread.currentThread().interrupt();//meysam 13960525
                                        if(ReportDialogPopUpModel.dialog_result==1){
                                            //send report to server - send btn clicked
                                            showLoading(intentName);
                                            qc.universalReport(new BigInteger(String.valueOf(word.hashCode())),ReportDialogPopUpModel.code," کلمه: "+word+" توضیح: "+ReportDialogPopUpModel.description, ReportDialogPopUpModel.FIND_WORD_TYPE);

                                        }else{
                                            //do nothing
                                        }

                                        ReportDialogPopUpModel.hide();
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
                });
                break;
        }
        return rowView;
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
    private void showReportResult(boolean result,String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("report_result_dialog", String.valueOf(result));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private void showRateResult(boolean result,String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("rate_result_dialog", String.valueOf(result));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private void showToast(String toastMessage,String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("show_toast", toastMessage);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private void showDialog(String dialogMessage,String intentName)
    {
        Intent intent = new Intent(intentName);
        // You can also include some extra data.
        intent.putExtra("show_dialog", dialogMessage);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}