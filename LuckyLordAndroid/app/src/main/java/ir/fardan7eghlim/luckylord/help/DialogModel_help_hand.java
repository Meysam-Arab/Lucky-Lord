package ir.fardan7eghlim.luckylord.help;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appolica.flubber.Flubber;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogModel_help_hand {
    private Dialog pd;
    private Context context;

    public DialogModel_help_hand(Context context) {
        this.context = context;
        pd= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(true);
        pd.setCancelable(true);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.help_hand);
    }

    public void show(boolean is_hand_show,String message,int x,int y,int timeOut){
        LinearLayout layout_hand_help=pd.findViewById(R.id.layout_hand_help);
        layout_hand_help.setY(y);
//        layout_hand_help.setX(x);

        LuckyTextView message_help_hand=pd.findViewById(R.id.message_help_hand);
        message_help_hand.setText(message);

        ImageView help_hand=pd.findViewById(R.id.help_hand);
        if(is_hand_show){
            help_hand.setVisibility(View.VISIBLE);
            help_hand.setImageResource(R.drawable.help_hand_click);
            AnimationDrawable anim = (AnimationDrawable) help_hand.getDrawable();
            anim.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(pd!=null && pd.isShowing()) pd.dismiss();
                }
            }, timeOut);
        }else{
            help_hand.setVisibility(View.INVISIBLE);
        }
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
