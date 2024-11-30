package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ir.fardan7eghlim.luckylord.R;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogModel_counter {
    private Dialog pdL;
    private Context context;

    public DialogModel_counter(Context c) {
        context=c;
        pdL= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pdL.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pdL.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdL.setContentView(R.layout.added_counter);
    }

    public void show(int amount){
        TextView added_luck_amount= (TextView) pdL.findViewById(R.id.number_counter_amount);
        added_luck_amount.setText(Utility.enToFa(amount+""));
        Animation anim=AnimationUtils.loadAnimation(context, R.anim.increase);
        added_luck_amount.startAnimation(anim);
        pdL.show();
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                // Pass the Intent to switch to other Activity
                pdL.dismiss();
            }
        });
    }
}
