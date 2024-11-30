package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyButton;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogModel_guide {
    private Dialog pd;
    private Context context;

    public DialogModel_guide(Context context,boolean flag,int pos) {
        this.context = context;

        pd=new Dialog(context,R.style.BounceDialog);
        if(!flag)
            pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.message_dialog_guide);
        switch (pos){
            case -1:
                pd.getWindow().getAttributes().gravity = Gravity.BOTTOM;
                break;
            case 1:
                pd.getWindow().getAttributes().gravity = Gravity.TOP;
                break;
            default:
                pd.getWindow().getAttributes().gravity = Gravity.CENTER;

        }
    }

    public void setText(String text){
        LuckyTextView t= (LuckyTextView) pd.findViewById(R.id.message_box_dialog);
        if(text.equals("")){
            t.setVisibility(View.GONE);
        }else {
            t.setVisibility(View.VISIBLE);
            t.setText(text);
        }
    }
    public void setText2(String text){
        LuckyTextView t = (LuckyTextView) pd.findViewById(R.id.message02_box_dialog);
        if(text.equals("")){
            t.setVisibility(View.GONE);
        }else {
            t.setVisibility(View.VISIBLE);
            t.setText(text);
        }
    }
    public void setBTN01(String text, final int counter, final MainActivity mainActivity){
        LuckyButton b= (LuckyButton) pd.findViewById(R.id.btn_mess_01);
        b.setText(text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.dismiss();
                texts(counter,mainActivity);
            }
        });

    }

    private void texts(int counter, MainActivity mainActivity){
        Animation shake = AnimationUtils.loadAnimation(mainActivity, R.anim.shake);
        Animation shake_fast = AnimationUtils.loadAnimation(mainActivity, R.anim.shake_fast);
        Animation shake_fast2 = AnimationUtils.loadAnimation(mainActivity, R.anim.shake_fast2);
        Animation wavy = AnimationUtils.loadAnimation(mainActivity, R.anim.wavy);
        FrameLayout main_board= (FrameLayout) mainActivity.findViewById(R.id.main_board);
        FrameLayout result_avt= (FrameLayout) mainActivity.findViewById(R.id.result_avt);
        LinearLayout ll_result_avt=mainActivity.findViewById(R.id.ll_result_avt);
        ImageView main_board_detail= mainActivity.findViewById(R.id.main_board_detail);
        TextView hazzelNumberMain_m= (TextView) mainActivity.findViewById(R.id.hazzelNumberMain_m);
        ImageView b_store= (ImageView) mainActivity.findViewById(R.id.b_store);
        ImageView b_dice= (ImageView) mainActivity.findViewById(R.id.b_dice);
        ImageView b_nut= (ImageView) mainActivity.findViewById(R.id.b_nut);
        ImageView b_nut2= (ImageView) mainActivity.findViewById(R.id.b_nut2);
        ImageView b_setting= (ImageView) mainActivity.findViewById(R.id.b_setting);
        ImageView b_friend= (ImageView) mainActivity.findViewById(R.id.b_friend);
//        FrameLayout b_ranking_m= (FrameLayout) mainActivity.findViewById(R.id.b_ranking_m);
        switch (counter){
            case 0:
                DialogModel_guide dm_guide=new DialogModel_guide(context,true,-1);
                dm_guide.setText("هدف بازی اینه که شما با جواب دادن به سوالا و شرکت در مسابقات و به دست آوردن شانس علاوه بر افزایش اطلاعات عمومیتون، احتمال برنده شدن در قرعه کشی ها رو برای خودتون زیاد کنید");
                dm_guide.setText2("");
                dm_guide.setBTN01("بعدی",1, mainActivity);
                dm_guide.show();
                break;
            case 1:
                dm_guide=new DialogModel_guide(context,false,1);
                dm_guide.setText("");
                dm_guide.setText2("سطح رو میتونی اینجا ببینی");
                dm_guide.setBTN01("بعدی",2, mainActivity);
                main_board.startAnimation(shake);
//                main_board_detail.startAnimation(shake);
                dm_guide.show();
                break;
            case 2:
                main_board.clearAnimation();
//                main_board_detail.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,-1);
                dm_guide.setText("ما یه مقدار فندق برای شروع بهت جایزه دادیم");
                dm_guide.setText2("اگه ثبت نام کنی بازم فندق جایزه میگیری");
                dm_guide.setBTN01("بعدی",3, mainActivity);
                hazzelNumberMain_m.startAnimation(shake_fast);
                dm_guide.show();
                break;
            case 3:
                hazzelNumberMain_m.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,-1);
                dm_guide.setText("اینجا آواتار شما هست");
                dm_guide.setText2("بعد از ثبت نام روش کلیک کن و برو آواتار خودتو بساز");
                dm_guide.setBTN01("بعدی",4, mainActivity);
                ll_result_avt.startAnimation(wavy);
                dm_guide.show();
                break;
            case 4:
                ll_result_avt.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,1);
                dm_guide.setText("اینجا لیست قرعه کشی ها قرار داره");
                dm_guide.setText2("از این قسمت میتونی در قرعه کشی ها شرکت کنی و جوایز مختلفی برنده بشی");
                dm_guide.setBTN01("بعدی",5, mainActivity);
                b_dice.startAnimation(shake_fast2);
                dm_guide.show();
                break;
            case 5:
                b_dice.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,1);
                dm_guide.setText("بازی ها هم داخل این قسمت هستن");
                dm_guide.setText2("از این قسمت می تونی در مسابقه شرکت کنی یا سوالات رو به صورت آزاد جواب بدی و فندق و شانس به دست بیاری");
                dm_guide.setBTN01("بعدی",6, mainActivity);
                b_nut.startAnimation(shake_fast2);
                b_nut2.startAnimation(shake_fast2);
                dm_guide.show();
                break;
            case 6:
                b_nut.clearAnimation();
                b_nut2.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,1);
                dm_guide.setText("در اینجا میتونی دنبال دوستات بگردی");
                dm_guide.setText2("");
                dm_guide.setBTN01("بعدی",8, mainActivity);
                b_friend.startAnimation(shake_fast2);
                dm_guide.show();
                break;
            case 7:
                b_setting.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,1);
                dm_guide.setText("تنظیمات بازی");
                dm_guide.setText2("از این قسمت به انواع تنظیمات در بازی می تونی دسترسی داشته باشی");
                dm_guide.setBTN01("بعدی",7, mainActivity);
                b_setting.startAnimation(shake_fast2);
                dm_guide.show();
                break;
            case 8:
                b_friend.clearAnimation();
                dm_guide=new DialogModel_guide(context,false,1);
                dm_guide.setText("و در آخر ما اینجا یه فروشگاه داریم");
                dm_guide.setText2("حتما بهش سر بزن");
                dm_guide.setBTN01("بریم بازی",9, mainActivity);
                b_store.startAnimation(shake_fast2);
                dm_guide.show();
                break;
            default:
            {
                b_store.clearAnimation();

                Intent intent = new Intent("main_activity_broadcast");
                // You can also include some extra data.
                intent.putExtra("play_flubber_animations", "true");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

        }
    }

    public void show(){
        if(pd!=null && !pd.isShowing()) pd.show();
    }
    public void hide(){
//        ImageView bg_d_left= (ImageView) pd.findViewById(R.id.bg_d_left);
//        bg_d_left.startAnimation(
//                AnimationUtils.loadAnimation(context, R.anim.left_out_bg) );
//
//        ImageView bg_d_right= (ImageView) pd.findViewById(R.id.bg_d_right);
//        bg_d_right.startAnimation(
//                AnimationUtils.loadAnimation(context, R.anim.right_out_bg) );
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(pd!=null && pd.isShowing()) pd.dismiss();
//            }
//        }, 600);
    }

    public void dismiss()
    {
        if(pd != null)
        {
            if(pd.isShowing())
                pd.hide();
            pd.dismiss();
        }
    }
}
