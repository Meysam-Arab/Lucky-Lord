package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

public class DialogModel_first {
    private Dialog pd;
    private Context context;

    public DialogModel_first(Context context) {
        this.context = context;
        pd= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.first_loading);
    }

    public void show(){
        ImageView f_loading_img_title= (ImageView) pd.findViewById(R.id.f_loading_img_title);
        f_loading_img_title.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.wavy) );

        ImageView f_loading_img_nuts= (ImageView) pd.findViewById(R.id.f_loading_img_nuts);
        f_loading_img_nuts.setImageResource(R.drawable.g_loading);
        AnimationDrawable anim = (AnimationDrawable) f_loading_img_nuts.getDrawable();
        anim.start();

        ImageView f_loading_img_squr= (ImageView) pd.findViewById(R.id.f_loading_img_squr);
        f_loading_img_squr.setImageResource(R.drawable.f_loading);
        AnimationDrawable anim2 = (AnimationDrawable) f_loading_img_squr.getDrawable();
        anim2.start();

        if(pd!=null && !pd.isShowing()) pd.show();
    }
    public void hide(){


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
