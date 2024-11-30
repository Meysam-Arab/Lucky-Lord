package ir.fardan7eghlim.luckylord.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.IntentCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MessageController;
import ir.fardan7eghlim.luckylord.models.MessageModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class CustomAdapterList_message extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater=null;
    private CustomAdapterList_message CAL=this;
    private Object foregn_key_obj;
    private MessageController mc;

    public CustomAdapterList_message(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List=list;
        Tag=tag.toLowerCase();
        context=c;
        inflater = (LayoutInflater)context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mc = new MessageController(context);
    }
    public CustomAdapterList_message(Context c, java.util.List<Object> list, String tag, Object obj) {
        // TODO Auto-generated constructor stub
        List=list;
        Tag=tag.toLowerCase();
        context=c;
        foregn_key_obj=obj;
        inflater = (LayoutInflater)context.
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
        this.List= list;
        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg != null)
        {
            if (arg instanceof ArrayList)
            if (((ArrayList) arg).get(0) instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
                else{
//                    Utility.displayToast(context,context.getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);

                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_INDEX_MESSAGE))
                {

                 //meysam - nothing to do
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_READ_MESSAGE))
                {

                    if((Boolean) ((ArrayList) arg).get(1))
                    {
                        SessionModel session = new SessionModel(context);
                        Integer unread_count = Integer.valueOf(session.getStringItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT));
                        unread_count--;
                        session.saveItem(SessionModel.KEY_UNREAD_MESSAGE_COUNT,unread_count.toString());
                    }
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_DELETE_USER_MESSAGE))
                {

                    if((Boolean) ((ArrayList) arg).get(1))
                    {
                      // meysam - do nothing for now ...
                    }
                }

            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(context,context.getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
                    SessionModel session = new SessionModel(context);
                    session.logoutUser();

                    Intent intents = new Intent(context, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intents);

                }else {
                    Utility.displayToast(context,new RequestRespondModel(context).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            }
            else
            {
                Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    public class Holder
    {
        View row_bg;
        TextView text_title;
        TextView text_message;
//        TextView text_date_to;
//        ImageView show_option_detail_btn;
        ImageView avatar;
//        FrameLayout row_bg_avatar;
//        LinearLayout base_row_content;

        public Holder(View row_bg, TextView text_first, TextView text_date_from, TextView text_date_to, ImageView show_option_detail_btn, ImageView avatar, FrameLayout row_bg_avatar, LinearLayout base_row_content) {
            this.row_bg = row_bg;
            this.text_title = text_first;
            this.text_message = text_date_from;
//            this.text_date_to = text_date_to;
//            this.show_option_detail_btn = show_option_detail_btn;
            this.avatar=avatar;
//            this.row_bg_avatar=row_bg_avatar;
//            this.base_row_content=base_row_content;
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder=new Holder(new View(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new ImageView(context.getApplicationContext()), new ImageView(context.getApplicationContext()),new FrameLayout(context.getApplicationContext()),new LinearLayout(context.getApplicationContext()));
        final View rowView= inflater.inflate(R.layout.row_list_message, null);
        holder.text_title= (TextView) rowView.findViewById(R.id.text_message_title);
        holder.text_message= (TextView) rowView.findViewById(R.id.text_message);
//        holder.text_date_to= (TextView) rowView.findViewById(R.id.text_date_to);
//        holder.show_option_detail_btn= (ImageView) rowView.findViewById(R.id.show_option_detail_btn);
        holder.row_bg= (View) rowView.findViewById(R.id.row_bg);
        holder.avatar= (ImageView) rowView.findViewById(R.id.avatar_in_row);
//        holder.row_bg_avatar= (FrameLayout) rowView.findViewById(R.id.row_bg_avatar);
//        holder.base_row_content= (LinearLayout) rowView.findViewById(R.id.base_row_content);
//
//        final Dialog d=new Dialog(context);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        d.setContentView(R.layout.option_dialog);
//
//        holder.progressBar= (FrameLayout) rowView.findViewById(R.id.row_progress_01);
//        holder.progressBar.setVisibility(View.GONE);
//
//        final Button btn_dialog_01= (Button) d.findViewById(R.id.btn_dialog_01);
//        final Button btn_dialog_02= (Button) d.findViewById(R.id.btn_dialog_02);
//        final Button btn_dialog_03= (Button) d.findViewById(R.id.btn_dialog_03);
//        final Button btn_dialog_04= (Button) d.findViewById(R.id.btn_dialog_04);
//        final Button btn_dialog_dlt= (Button) d.findViewById(R.id.btn_dialog_dlt);
//        final TextView messForDelete= (TextView) d.findViewById(R.id.messForDelete);
//        final Button btn_dialog_edt= (Button) d.findViewById(R.id.btn_dialog_edt);
//        final Button btn_dialog_yDlt= (Button) d.findViewById(R.id.btn_dialog_yDlt);
//        final Button btn_dialog_nDlt= (Button) d.findViewById(R.id.btn_dialog_nDlt);
//        final LinearLayout delete_link_message= (LinearLayout) d.findViewById(R.id.delete_link_message);
//        delete_link_message.setVisibility(View.GONE);
//
//        holder.show_option_detail_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //for option code here
//                d.show();
//            }
//        });
//        btn_dialog_dlt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //for option code here
//                delete_link_message.setVisibility(View.VISIBLE);
//                messForDelete.setText(context.getString(R.string.dlg_DoYouReallyWantToDelete));
//            }
//        });
//        btn_dialog_nDlt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //for option code here
//                delete_link_message.setVisibility(View.GONE);
//            }
//        });

        switch (Tag){
            case RequestRespondModel.TAG_INDEX_MESSAGE://if we have list of users
                final MessageModel message=(MessageModel) List.get(position);
                //avatar
                if(message.getReaded()){
                    holder.avatar.setImageResource(R.drawable.b_message_gray);
                    holder.row_bg.setBackgroundResource(R.drawable.bg_07);
                }else {
                    holder.avatar.setImageResource(R.drawable.b_message);
                    holder.row_bg.setBackgroundResource(R.drawable.bg_03);
                }

                holder.text_title.setText(message.getTitle());
                String temp=message.getDescription();
                if(temp.length()>30){
                    temp=temp.substring(0,27)+"...";
                }
                holder.text_message.setText(temp);

                holder.row_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDescriptionDialog(message, position);

                        if(!message.getReaded()) {
                            sendSetReadREquest(message);
                            message.setReaded(true);
                            holder.avatar.setImageResource(R.drawable.b_message_gray);
                            holder.row_bg.setBackgroundResource(R.drawable.bg_07);
                        }
                    }
                });

        }

        return rowView;
    }

    private void sendSetReadREquest(MessageModel message) {
        mc.addObserver(this);
        mc.read(message);
    }

    private void showDescriptionDialog(final MessageModel message, final int position)
    {
        final Dialog d=new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setContentView(R.layout.message_description);

        TextView tv_title= (TextView) d.findViewById(R.id.tv_message_title);
        tv_title.setText(message.getTitle());
        TextView tv_description= (TextView) d.findViewById(R.id.tv_message_description);
        tv_description.setMovementMethod(LinkMovementMethod.getInstance());
        tv_description.setText(Html.fromHtml(message.getDescription()));
//        tv_description.setText(message.getDescription());

        Button btn_close= (Button) d.findViewById(R.id.btn_message_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });

        Button btn_delete_message= (Button) d.findViewById(R.id.btn_delete_message);
        btn_delete_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //meysam - we dont show any loading dialog and do thing behind the scene...
                //meysam - no need for an observer...
                mc.delete(message);
                List.remove(position);
                CustomAdapterList_message.this.notifyDataSetChanged(); //Updates adapter to new changes
                d.hide();
            }
        });

        d.show();
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_message.inflater = inflater;
    }
}