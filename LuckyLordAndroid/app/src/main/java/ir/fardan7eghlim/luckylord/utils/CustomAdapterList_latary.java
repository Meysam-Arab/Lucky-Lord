package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.IntentCompat;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.DrawController;
import ir.fardan7eghlim.luckylord.models.DrawModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.RewardModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.views.lattery.LatteryIndexActivity;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class CustomAdapterList_latary extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater=null;
    private CustomAdapterList_latary CAL=this;
    private boolean is_ended;
    private UserModel User;
    private DialogModel DM;
    private Dialog winners_dialog;
    private ProgressBar pb;
    private ListView w_lv;

    public static Boolean participated =null;
    public static DrawModel currentDraw = null;



    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_latary.inflater = inflater;
    }

    public CustomAdapterList_latary(Context c, DialogModel dm, java.util.List<Object> list, String tag, boolean isEnded, UserModel user, Dialog winners_dialog, ProgressBar pb, ListView w_lv) {
        // TODO Auto-generated constructor stub

        participated =null;
        currentDraw = null;

        List=list;
        Tag=tag.toLowerCase();
        context=c;
        is_ended=isEnded;
        User=user;
        DM=dm;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.winners_dialog=winners_dialog;
        this.pb=pb;
        this.w_lv=w_lv;
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
                    Utility.displayToast(context,context.getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);

                }
            }
//            else if(arg instanceof ArrayList)
//            {
//
//            }
//            else if(arg instanceof Integer)
//            {
//                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
//                {
//                   // meysam - what to do??
//
//                }else {
//                    Utility.displayToast(context,new RequestRespondModel(context).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
//                }
//            }
//            else
//            {
//                Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
//            }
        }
        else
        {
            Utility.displayToast(context,context.getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    public class Holder
    {
        LinearLayout row_bg;
        TextView text_first;
        TextView text_second;
        TextView text_second2;
        TextView text_cost;
        Button button_01;
        Button button_02;
        ImageView avatar;
        FrameLayout row_bg_avatar;




        public Holder(LinearLayout row_bg, TextView text_first, TextView text_second, TextView text_second2, TextView text_cost, Button button_01, Button button_02, ImageView avatar, FrameLayout row_bg_avatar) {
            this.row_bg = row_bg;
            this.text_first = text_first;
            this.text_second = text_second;
            this.text_second2 = text_second2;
            this.text_cost = text_cost;
            this.button_01 = button_01;
            this.button_02 = button_02;
            this.avatar=avatar;
            this.row_bg_avatar=row_bg_avatar;
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder=new Holder(new LinearLayout(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new Button(context.getApplicationContext()), new Button(context.getApplicationContext()), new ImageView(context.getApplicationContext()),new FrameLayout(context.getApplicationContext()));
        final View rowView= inflater.inflate(R.layout.row_list_latary, null);
        holder.text_first= (TextView) rowView.findViewById(R.id.text_first);
        holder.text_second= (TextView) rowView.findViewById(R.id.text_second);
        holder.text_second2= (TextView) rowView.findViewById(R.id.text_second2);
        holder.text_cost= (TextView) rowView.findViewById(R.id.text_cost);
        holder.button_01= (Button) rowView.findViewById(R.id.row_first_btn);
        holder.button_02= (Button) rowView.findViewById(R.id.row_second_btn);
//        holder.show_option_detail_btn= (ImageView) rowView.findViewById(R.id.show_option_detail_btn);
        holder.row_bg= (LinearLayout) rowView.findViewById(R.id.row_bg);
        holder.avatar= (ImageView) rowView.findViewById(R.id.avatar_in_row);
        holder.row_bg_avatar= (FrameLayout) rowView.findViewById(R.id.row_bg_avatar);

//        holder.base_row_content= (LinearLayout) rowView.findViewById(R.id.base_row_content);
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
            case RequestRespondModel.TAG_INDEX_DRAW://if we have list of draws
                final DrawModel draw=(DrawModel) List.get(position);

                //avatar
                if(draw.getImage()==null){
                    holder.avatar.setVisibility(View.GONE);
                }else {
                    holder.avatar.setVisibility(View.VISIBLE);
                    holder.avatar.setImageBitmap(draw.getImage());
                }

                holder.text_first.setText(Utility.enToFa(dateBetter(Utility.convertDateGorgeian2Persian(draw.getDrawDateTime()))) );

                boolean isFirst=true;
                for(RewardModel reward:draw.getRewards()){
                    holder.text_second.setText(holder.text_second.getText()+(isFirst?"":"\n")+reward.getDescription() +  " برای ");


                    String temp01=reward.getCount().toString();
                    String temp02=reward.getUnit();
                    if(temp02.equals("non")){
                        temp01=" ";
                        temp02=" ";
                    }
                    holder.text_second2.setText(holder.text_second2.getText()+(isFirst?"":"\n")+Utility.enToFa(temp01)+" "+temp02);
                    isFirst=false;
                }


                holder.text_cost.setText(Utility.enToFa(draw.getCost()+" فندق"));

                if(is_ended){
                    holder.button_02.setText("لیست برندگان");
                    holder.button_02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pb.setVisibility(View.VISIBLE);
                            w_lv.setVisibility(View.INVISIBLE);
                            winners_dialog.show();

                            DrawController dc = new DrawController(context);
                            dc.addObserver((Observer) context);
                            dc.winners(draw);
                        }
                    });
                }else {
                    if(draw.getParticipated()) {
                        holder.button_02.setText("پشیمون شدم");
                    }else{
                        holder.button_02.setText("می خوام شرکت کنم");
                    }
                    holder.button_02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(User.getUserName() != null){
                                if(User.getLuck()<=0)
                                {
                                    DialogPopUpModel.show(context,context.getString(R.string.msg_NotEnoughLuck),context.getString(R.string.btn_OK),null, false, false);
                                }
                                else
                                {
                                    if(User.getHazel() < new Integer(draw.getCost()) )
                                    {
                                        DialogPopUpModel.show(context,context.getString(R.string.msg_NotEnoughHazel),context.getString(R.string.btn_OK),null, false, false);
                                    }
                                    else
                                    {
                                        showMessage(draw.getParticipated(),new Integer(draw.getCost()),draw);
                                    }

                                }
                            }else{
                                DialogPopUpModel.show(context,context.getString(R.string.msg_MustRegister),context.getString(R.string.btn_OK),null, false, false);
                            }

                        }
                    });
                }


                if(draw.getParticipated())
                {
                   // meysam - distinct participated draws for user
                    holder.row_bg.setBackgroundResource(R.drawable.t_wood_f);
                }


                final Dialog d=new Dialog(context);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                d.setContentView(R.layout.sponcer_description);

                holder.button_01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.show();
                    }
                });

                ImageView descript_avatar= (ImageView) d.findViewById(R.id.descript_avatar);
                if(draw.getImage()==null){
                    descript_avatar.setVisibility(View.GONE);
                }else {
                    descript_avatar.setVisibility(View.VISIBLE);
                    descript_avatar.setImageBitmap(draw.getImage());
                }
                TextView tv_title= (TextView) d.findViewById(R.id.tv_title);
                tv_title.setText(draw.getSponser());
                TextView tv_description= (TextView) d.findViewById(R.id.tv_description);
                tv_description.setText(draw.getDescription());

                Button btnOpenLink= (Button) d.findViewById(R.id.btnOpenLink);
                if(draw.getLink() == null)
                    btnOpenLink.setVisibility(View.GONE);
                btnOpenLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        d.hide();

                        if(draw.getLink() != null && !draw.getLink().equals(""))
                        {
                            String url = draw.getLink();
                            if (!url.startsWith("http://") && !url.startsWith("https://"))
                                url = "http://" + url;
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            context.startActivity(browserIntent);
                        }

                    }
                });
                Button btnClose= (Button) d.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
//                btn_dialog_03.setText(context.getString(R.string.Tracking));
//                btn_dialog_03.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(context, TrackIndexActivity.class);
//                        i.putExtra("user_id", user.getId().toString());
//                        i.putExtra("user_guid", user.getGuid());
//                        d.hide();
//                        context.startActivity(i);
//                    }
//                });
//                btn_dialog_04.setText(context.getString(R.string.Delete_phoneCode));
//                btn_dialog_04.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                        alertDialogBuilder.setPositiveButton(R.string.dlg_Yes, dialogClickListenerPhoneCode);
//                        alertDialogBuilder.setNegativeButton(R.string.dlg_No, dialogClickListenerPhoneCode);
//                        alertDialogBuilder.setMessage(R.string.dlg_msg_Exit);
//                        alertDialogBuilder.show();
//                        foregn_key_obj=user;
//                        d.hide();
//                    }
//                });
//                holder.row_bg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        d.show();
//                    }
//                });
//                btn_dialog_yDlt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        UserController uc = new UserController(context);
//                        uc.addObserver((Observer) context);
//                        uc.delete(user);
//                        d.hide();
//                        new DialogModel(getApplicationContext()).show(context);
//                    }
//                });
////                btn_dialog_edt.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Utility.displayToast(context,context.getResources().getString(R.string.msg_MessageHeadToWebSite),Toast.LENGTH_SHORT);
////                    }
////                });
//                btn_dialog_edt.setVisibility(View.GONE);
                break;

        }
        return rowView;
    }

    private String dateBetter(String date) {
        String[] datePart=date.split("/");

        String temp=datePart[2]+" ";

        switch (datePart[1]){
            case "1":
                temp+="فروردین";
                break;
            case "2":
                temp+="اردیبهشت";
                break;
            case "3":
                temp+="خرداد";
                break;
            case "4":
                temp+="تیر";
                break;
            case "5":
                temp+="مرداد";
                break;
            case "6":
                temp+="شهریور";
                break;
            case "7":
                temp+="مهر";
                break;
            case "8":
                temp+="آبان";
                break;
            case "9":
                temp+="آذر";
                break;
            case "10":
                temp+="دی";
                break;
            case "11":
                temp+="بهمن";
                break;
            case "12":
                temp+="اسفند";
                break;
        }

        return temp+" "+datePart[0];
    }

    //message for participante or not
    private void showMessage(boolean isCancel, int penalty, final DrawModel draw){

        final Dialog dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.message_dialog);
        TextView txt= (TextView) dialog.findViewById(R.id.message_box_dialog);
        String message="";
        Button btn2= (Button) dialog.findViewById(R.id.btn_mess_02);
        btn2.setText("آره");
        btn2.setVisibility(View.VISIBLE);
        Button btn= (Button) dialog.findViewById(R.id.btn_mess_01);
        btn.setText("نه");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if(!isCancel){
            message="آیا مطمئنی که میخوای شرکت کنی؟"+"\n"+"تعداد "+penalty+" فندق هزینه دارد";
            txt.setText(message);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    DM.show();
                    participated = true;
                    DrawController dc = new DrawController(context);
                    dc.addObserver((Observer) context);
                    dc.participate(draw);
                    currentDraw = draw;
                }
            });
            dialog.show();
        }else{
            message="آیا مطمئنی که میخوای انصراف بدی؟"+"\n"+"فقط تعداد "+(penalty/2)+" فندق بهت اضافه خواهد شد";
            txt.setText(message);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    participated = false;
                    DM.show();
                    DrawController dc = new DrawController(context);
                    dc.addObserver((Observer) context);
                    dc.participate(draw);
                    currentDraw = draw;
                }
            });
            dialog.show();
        }
    }

}