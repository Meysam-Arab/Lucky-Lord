package ir.fardan7eghlim.luckylord.help;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.Utility;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogModel_help {
    private Dialog pd;
    private Context context;

    public DialogModel_help(Context context) {
        this.context = context;
        pd= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.help_in_game);
    }

    public void show(int type){
        ImageView help_dialog_img= pd.findViewById(R.id.help_dialog_img);
        LuckyTextView help_dialog_text = pd.findViewById(R.id.help_dialog_text);

        if(type==1){//findword
            help_dialog_img.setImageResource(R.drawable.help_findword);
            help_dialog_text.setText("در این بازی باید در هر جدول 5تا کلمه ی معنی دار بالای دو حرف پیدا کنی.");
        }else if(type==2){ //guessword
            help_dialog_img.setImageResource(R.drawable.help_guessword);
            help_dialog_text.setText("حروف درست کلمه ی مورد نظر رو بایستی حدس بزنی.");
        }else if(type==3){ //crossTable
            help_dialog_img.setImageResource(R.drawable.help_darham);
            help_dialog_text.setText("با جابه جایی حروف جدول را سبز کن.");
        }else if(type==4){ //darhamTable
            help_dialog_img.setImageResource(R.drawable.help_findword);
            help_dialog_text.setText("بایستی جواب سوال ها رو داخل جدول پیدا کنی.");
        }
        AnimationDrawable anim = (AnimationDrawable) help_dialog_img.getDrawable();
        anim.start();

        LuckyButton help_dialog_close=pd.findViewById(R.id.help_dialog_close);
        help_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

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
