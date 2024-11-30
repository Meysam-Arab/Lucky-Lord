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
 * Created by Meysam on 13960428.
 */

public class DialogModel_hazel {
    private Dialog pdL;
    private Context context;

    public DialogModel_hazel(Context c) {
        context=c;
        pdL= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pdL.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pdL.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdL.setContentView(R.layout.added_hazel);
    }

    public void show(boolean isLuck,boolean isMore,int amount){
        TextView added_hazel_amount= (TextView) pdL.findViewById(R.id.added_hazel_amount);
        if(isMore){
            added_hazel_amount.setTextColor(Color.parseColor("#163803"));
        }else{
            added_hazel_amount.setTextColor(Color.parseColor("#be3f3f"));
        }
        added_hazel_amount.setText(Utility.enToFa((isMore?"+":"-")+amount+""));
        added_hazel_amount.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.increase) );
        ImageView img= (ImageView) pdL.findViewById(R.id.added_hazel_img);
        if(isLuck){
            img.setImageResource(R.drawable.c_hazzel_a);
        }else{
            img.setImageResource(R.drawable.c_coin_a);
        }
        Animation anim=AnimationUtils.loadAnimation(context, R.anim.increase);
        img.startAnimation(anim);
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

    public void showV2(boolean isLuck,int amount){
        TextView added_hazel_amount= (TextView) pdL.findViewById(R.id.added_hazel_amount);
        boolean isMore = false;
        if(amount < 0)
        {
            isMore = false;
        }
        else
        {
            isMore = true;
        }
        if(isMore){
            added_hazel_amount.setTextColor(Color.parseColor("#163803"));
        }else{
            added_hazel_amount.setTextColor(Color.parseColor("#be3f3f"));
        }
        added_hazel_amount.setText(Utility.enToFa((isMore?"+":"-")+amount+""));
        added_hazel_amount.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.increase) );
        ImageView img= (ImageView) pdL.findViewById(R.id.added_hazel_img);
        if(isLuck){
            img.setImageResource(R.drawable.c_hazzel_a);
        }else{
            img.setImageResource(R.drawable.c_coin_a);
        }
        Animation anim=AnimationUtils.loadAnimation(context, R.anim.increase);
        img.startAnimation(anim);
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
