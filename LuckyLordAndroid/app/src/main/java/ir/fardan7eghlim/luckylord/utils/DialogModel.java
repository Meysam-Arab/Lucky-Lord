package ir.fardan7eghlim.luckylord.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogModel {
    private Dialog pd;
    private Context context;

    public DialogModel(Context context) {
        this.context = context;
        pd= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.loading);
    }

    public void show(){
        ImageView img= (ImageView) pd.findViewById(R.id.hazzel_loading);
        img.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.rotate_indefinitely) );

        ImageView bg_d_left= (ImageView) pd.findViewById(R.id.bg_d_left);
        bg_d_left.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.left_come_bg) );

        ImageView bg_d_right= (ImageView) pd.findViewById(R.id.bg_d_right);
        bg_d_right.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.right_come_bg) );


        LuckyTextView hint_box_dialog=pd.findViewById(R.id.hint_box_dialog);
        hint_box_dialog.setText(Utility.getHintFromFile(context));

        if(pd!=null && !pd.isShowing()) pd.show();
    }
    public void hide(){
        ImageView bg_d_left= (ImageView) pd.findViewById(R.id.bg_d_left);
        bg_d_left.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.left_out_bg) );

        ImageView bg_d_right= (ImageView) pd.findViewById(R.id.bg_d_right);
        bg_d_right.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.right_out_bg) );

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pd!=null && pd.isShowing()) pd.dismiss();
            }
        }, 600);
    }

    public void dismiss()
    {
        if(pd.isShowing())
            pd.hide();
        pd.dismiss();
    }
}
