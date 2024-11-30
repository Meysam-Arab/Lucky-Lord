package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import ir.fardan7eghlim.luckylord.R;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogSecsSelection {
    public static Dialog dialog;
    public static int dialog_result;

    public static void show(Context context, final Boolean externalAccess, boolean cancelOnOutsideTouch){
        dialog_result=0;

        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.secs_selection);

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

        FrameLayout male_secs_choise= (FrameLayout) dialog.findViewById(R.id.male_secs_choise);
        male_secs_choise.setOnClickListener(new View.OnClickListener() {
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
        FrameLayout female_secs_choise= (FrameLayout) dialog.findViewById(R.id.female_secs_choise);
        female_secs_choise.setOnClickListener(new View.OnClickListener() {
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
