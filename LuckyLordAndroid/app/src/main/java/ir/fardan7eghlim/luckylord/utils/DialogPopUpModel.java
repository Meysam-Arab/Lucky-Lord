package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
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

public class DialogPopUpModel {
    public static Dialog dialog;
    public static int dialog_result;

    public static void show(Context context, String message, String b1, String b2, final Boolean externalAccess, boolean cancelOnOutsideTouch){
        dialog_result=0;

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

                dialog_result=1;
                if(dialog != null)
                {
                    dialog.hide();
                    dialog.dismiss();
                }

                if(!externalAccess)
                {
                    hide();
                }
            }
        });
        if(b2!=null){
            Button btn2= (Button) dialog.findViewById(R.id.btn_mess_02);
            btn2.setText(b2);
            btn2.setVisibility(View.VISIBLE);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog_result=2;
                    if(dialog != null) {
                        dialog.hide();
                        dialog.dismiss();
                    }
                    if(!externalAccess)
                    {
                        hide();
                    }
                }
            });
        }

        dialog.show();
    }
    public static void hide(){
        if(dialog != null)
        {
            DialogPopUpModel.dialog.setCanceledOnTouchOutside(true);

            dialog.dismiss();
            dialog=null;
        }

    }
    public static boolean isUp(){
        if(dialog == null)
            return false;
        return dialog.isShowing();
    }
}
