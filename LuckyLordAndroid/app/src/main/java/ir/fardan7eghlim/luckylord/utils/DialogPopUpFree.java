package ir.fardan7eghlim.luckylord.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;

import static ir.fardan7eghlim.luckylord.models.UniversalMatchModel.STATUS_EQUAL;
import static ir.fardan7eghlim.luckylord.models.UniversalMatchModel.STATUS_LOST;

/**
 * Created by Meysam on 4/17/2018.
 */

public class DialogPopUpFree {

    private Dialog dialog;
    private Context context;


    public void show(Context context, int matchResultCode,String opp_name,String opp_avatar,String tozih, boolean cancelOnOutsideTouch){

        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.message_dialog_pop_up_free);
        dialog.setCanceledOnTouchOutside(cancelOnOutsideTouch);
        dialog.setCancelable(cancelOnOutsideTouch);

        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //When you touch outside of dialog bounds,
                        //the dialog gets canceled and this method executes.

                        hide();
                    }
                }
        );

        ImageView matchResult_df= dialog.findViewById(R.id.matchResult_df);
        LuckyTextView tozihat_df= dialog.findViewById(R.id.tozihat_df);
        TextView UserName_op_df= dialog.findViewById(R.id.UserName_op_df);
        LuckyButton btn_mess_df= dialog.findViewById(R.id.btn_mess_df);

        switch (matchResultCode)
        {
            case UniversalMatchModel.STATUS_IN_PROGRESS:
                matchResult_df.setImageResource(R.drawable.t_text_unfinish);
                break;
            case UniversalMatchModel.STATUS_EQUAL:
                matchResult_df.setImageResource(R.drawable.t_text_even);
                break;
            case UniversalMatchModel.STATUS_LOST:
                matchResult_df.setImageResource(R.drawable.t_text_lose);
                break;
            case UniversalMatchModel.STATUS_WIN:
                matchResult_df.setImageResource(R.drawable.t_text_win);
                break;
            case UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT:
                matchResult_df.setImageResource(R.drawable.t_text_disapproved_by_opponent);
                break;
            case UniversalMatchModel.STATUS_DISAPPROVED_BY_YOU:
                matchResult_df.setImageResource(R.drawable.t_text_disapproved_by_you);
                break;
            case UniversalMatchModel.STATUS_REQUEST_EXPIRED:
                matchResult_df.setImageResource(R.drawable.t_text_expired);
                break;
            default:
                matchResult_df.setImageResource(R.drawable.t_text_unfinish);
                break;
        }

        UserName_op_df.setText(opp_name);
//        if(opp_name.length()>10){
//            UserName_op_df.setTextSize(context.getResources().getDimension(R.dimen.s5_font_size));
//        }
        tozihat_df.setText(tozih);
        Avatar avatar_oponnent = new Avatar(opp_avatar);
        avatar_oponnent.setAvatar_opt((Activity) context,dialog);
        btn_mess_df.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog != null)
                {
                    dialog.hide();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    public void show(Context context, String message, String b1, boolean cancelOnOutsideTouch){

        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.message_dialog);

        dialog.setCanceledOnTouchOutside(cancelOnOutsideTouch);
        dialog.setCancelable(cancelOnOutsideTouch);

        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //When you touch outside of dialog bounds,
                        //the dialog gets canceled and this method executes.

                        hide();
                    }
                }
        );

        TextView txt= (TextView) dialog.findViewById(R.id.message_box_dialog);
        txt.setText(message);
        Button btn= (Button) dialog.findViewById(R.id.btn_mess_01);
        btn.setText(b1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dialog != null)
                {
                    dialog.hide();
                    dialog.dismiss();
                }


            }
        });


        dialog.show();
    }



    public void hide(){
        if(dialog != null)
        {
            dialog.hide();
            dialog.dismiss();
            dialog=null;
        }

    }
}
