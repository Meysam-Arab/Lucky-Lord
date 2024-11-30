package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ir.fardan7eghlim.luckylord.R;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogPopUpFourVerticalModel {
    public static Dialog dialog;
    public static int dialog_result;

    public static void show(Context context,String message,String btn_1_text,String btn_2_text, String btn_3_text, String btn_4_text, final Boolean externalAccess, boolean cancelOnOutsideTouch){
        dialog_result=0;

        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.four_vertical_button_dialog);

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

        if(message != null)
        {
            TextView txt= (TextView) dialog.findViewById(R.id.ltv_four_vertical_title);
            txt.setText(message);
        }
        Button btn= (Button) dialog.findViewById(R.id.btn_four_vertical_04);
        if(btn_4_text != null)
        {
            btn.setText(btn_4_text);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_result=4;
                    if(dialog != null)
                        dialog.dismiss();
                    if(!externalAccess)
                    {
                        hide();
                    }

                }
            });
        }
        else
        {
            btn.setVisibility(View.GONE);
        }
        btn= (Button) dialog.findViewById(R.id.btn_four_vertical_02);
        if(btn_2_text!=null){
            btn.setText(btn_2_text);
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_result=2;
                    if(dialog != null)
                        dialog.dismiss();
                    if(!externalAccess)
                    {
                        hide();
                    }

                }
            });
        }
        else
        {
            btn.setVisibility(View.GONE);
        }
        btn= (Button) dialog.findViewById(R.id.btn_four_vertical_03);

        if(btn_3_text!=null){
            btn.setText(btn_3_text);
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_result=3;
                    if(dialog != null)
                        dialog.dismiss();
                    if(!externalAccess)
                    {
                        hide();
                    }

                }
            });
        }
        else
        {
            btn.setVisibility(View.GONE);
        }
        btn= (Button) dialog.findViewById(R.id.btn_four_vertical_01);

        if(btn_1_text!=null){
            btn.setText(btn_1_text);
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_result=1;
                    if(dialog != null)
                        dialog.dismiss();
                    if(!externalAccess)
                    {
                        hide();
                    }

                }
            });
        }
        else
        {
            btn.setVisibility(View.GONE);
        }

        dialog.show();
    }
    public static void hide(){
        if(dialog != null)
        {
            dialog.hide();
            dialog.dismiss();
            dialog=null;
        }
        dialog_result = 0;

    }
    public static boolean isUp(){
        if(dialog == null)
            return false;
        return dialog.isShowing();
    }
}
