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

public class DialogModel_cup {
    private Dialog pdL;
    private Context context;

    public DialogModel_cup(Context c) {
        context=c;
        pdL= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pdL.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pdL.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdL.setContentView(R.layout.added_cup);
    }

    public void show(String text,int numCup){
        TextView added_hazel_amount= (TextView) pdL.findViewById(R.id.added_cup_amount);
        added_hazel_amount.setText(text);
        added_hazel_amount.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.increase_slow) );
        ImageView img= (ImageView) pdL.findViewById(R.id.added_cup_img);
        if(numCup==1){
            img.setImageResource(R.drawable.cup_a);
        }else if(numCup==2){
            img.setImageResource(R.drawable.cup_b);
        }else if(numCup==3){
            img.setImageResource(R.drawable.cup_c);
        }else if(numCup==4){
            img.setImageResource(R.drawable.cup_d);
        }else if(numCup==5){
            img.setImageResource(R.drawable.cup_e);
        }else if(numCup==6){
            img.setImageResource(R.drawable.cup_f);
        }else if(numCup==8){
            img.setImageResource(R.drawable.cup_h);
        }else if(numCup==9){
            img.setImageResource(R.drawable.cup_i);
        }else{ //for seven
            img.setImageResource(R.drawable.cup_g);
        }
        Animation anim=AnimationUtils.loadAnimation(context, R.anim.increase_slow);
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
                if(pdL != null)
                    if(pdL.isShowing())
                        pdL.dismiss();
            }
        });
    }
    public void dismiss()
    {
        if(pdL != null)
        {
            if(pdL.isShowing())
                pdL.hide();
            pdL.dismiss();
        }
    }
}
