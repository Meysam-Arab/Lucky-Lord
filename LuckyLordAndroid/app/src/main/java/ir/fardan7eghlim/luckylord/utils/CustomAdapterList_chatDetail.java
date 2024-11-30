package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MessageController;
import ir.fardan7eghlim.luckylord.models.MessageModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class CustomAdapterList_chatDetail extends BaseAdapter implements Observer {

    private Context context;
//    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater = null;
    private CustomAdapterList_chatDetail CAL = this;
    private Object foregn_key_obj;
    private MessageController mc;

    public CustomAdapterList_chatDetail(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List = list;
//        Tag = tag.toLowerCase();
        context = c;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mc = new MessageController(context);
    }

    public CustomAdapterList_chatDetail(Context c, java.util.List<Object> list, String tag, Object obj) {
        // TODO Auto-generated constructor stub
        List = list;
//        Tag = tag.toLowerCase();
        context = c;
        foregn_key_obj = obj;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mc = new MessageController(context);
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
        this.List = list;
        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof ArrayList)
                if (((ArrayList) arg).get(0) instanceof Boolean) {
                    if (Boolean.parseBoolean(arg.toString()) == false) {
                        Utility.displayToast(context, context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    } else {
//                    Utility.displayToast(context,context.getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);

                    }
                } else if (arg instanceof ArrayList) {
                    if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_INDEX_MESSAGE)) {

                        //meysam - nothing to do
                    } else if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_READ_MESSAGE)) {

                        if ((Boolean) ((ArrayList) arg).get(1)) {
                            SessionModel session = new SessionModel(context);
                            Integer unread_count = Integer.valueOf(session.getStringItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT));
                            unread_count--;
                            session.saveItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT, unread_count.toString());
                        }
                    } else if (((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DELETE_USER_MESSAGE)) {

                        if ((Boolean) ((ArrayList) arg).get(1)) {
                            // meysam - do nothing for now ...
                        }
                    }

                } else if (arg instanceof Integer) {
                    if (Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE) {
                        Utility.displayToast(context, context.getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
                        SessionModel session = new SessionModel(context);
                        session.logoutUser();

                        Intent intents = new Intent(context, UserLoginActivity.class);
                        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intents);

                    } else {
                        Utility.displayToast(context, new RequestRespondModel(context).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                    }
                } else {
                    Utility.displayToast(context, context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
        } else {
            Utility.displayToast(context, context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    public class Holder {
        LinearLayout ll_mainbox_chat_dtl;
        LuckyTextView text_box_chat_dtl;
        ImageView img_helper_chat_dtl;

        public Holder(LinearLayout ll_mainbox_chat_dtl, LuckyTextView text_box_chat_dtl,ImageView img_helper_chat_dtl) {
            this.ll_mainbox_chat_dtl = ll_mainbox_chat_dtl;
            this.text_box_chat_dtl = text_box_chat_dtl;
            this.img_helper_chat_dtl = img_helper_chat_dtl;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder(new LinearLayout(context.getApplicationContext()), new LuckyTextView(context.getApplicationContext()),  new ImageView(context.getApplicationContext()));
        final View rowView = inflater.inflate(R.layout.row_list_chat_detail, null);
        holder.ll_mainbox_chat_dtl = rowView.findViewById(R.id.ll_mainbox_chat_dtl);
        holder.text_box_chat_dtl = rowView.findViewById(R.id.text_box_chat_dtl);

        String current_news = (String) List.get(position);
        int type= Integer.parseInt(current_news.substring(0,1));
        String text=current_news.substring(1);
        holder.text_box_chat_dtl.setText(text);
        if(type==0){
            //sb became online
            holder.ll_mainbox_chat_dtl.setBackgroundResource(R.drawable.bg_01);
        }else if(type==1){
            //sb became offline
            holder.ll_mainbox_chat_dtl.setBackgroundResource(R.drawable.bg_08);
        }else{
            //sb sent message
            holder.ll_mainbox_chat_dtl.setBackgroundResource(R.drawable.bg_05);
        }
        //return
        return rowView;
    }

    private void sendSetReadREquest(MessageModel message) {
        mc.addObserver(this);
        mc.read(message);
    }

    private void showDescriptionDialog(final MessageModel message, final int position) {
        final Dialog d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setContentView(R.layout.message_description);

        TextView tv_title = (TextView) d.findViewById(R.id.tv_message_title);
        tv_title.setText(message.getTitle());
        TextView tv_description = (TextView) d.findViewById(R.id.tv_message_description);
        tv_description.setMovementMethod(LinkMovementMethod.getInstance());
        tv_description.setText(Html.fromHtml(message.getDescription()));
//        tv_description.setText(message.getDescription());

        Button btn_close = (Button) d.findViewById(R.id.btn_message_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });

        Button btn_delete_message = (Button) d.findViewById(R.id.btn_delete_message);
        btn_delete_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam - we dont show any loading dialog and do thing behind the scene...
                //meysam - no need for an observer...
                mc.delete(message);
                List.remove(position);
                CustomAdapterList_chatDetail.this.notifyDataSetChanged(); //Updates adapter to new changes
                d.hide();
            }
        });

        d.show();
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_chatDetail.inflater = inflater;
    }
}