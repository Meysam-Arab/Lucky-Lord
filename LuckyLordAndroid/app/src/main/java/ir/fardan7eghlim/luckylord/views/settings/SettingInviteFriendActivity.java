package ir.fardan7eghlim.luckylord.views.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.Utility;

import static android.R.attr.y;

public class SettingInviteFriendActivity extends BaseActivity {

    private AsyncTask<String, Integer, Boolean> asyncTask;
    private DialogModel DM;
    private int width;
    private int height;
    private LuckyTextView inviteCode_sif;
    private  LuckyTextView text01_sif;
    private LinearLayout land_inviteFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_invite_friend);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

//        final FrameLayout F_rabbitBoard_sif= (FrameLayout) findViewById(R.id.F_rabbitBoard_sif);
//        F_rabbitBoard_sif.post(new Runnable() {
//            @Override
//            public void run() {
//                int y2 = F_rabbitBoard_sif.getHeight();
//                land_inviteFriend= (LinearLayout) findViewById(R.id.land_inviteFriend);
//                Anims.move(land_inviteFriend,0,0,0,(y2/2+height/2),1,0);
//            }
//        });


        text01_sif= (LuckyTextView) findViewById(R.id.text01_sif);
        // meysam - matn ha ro dorost farmaid (matne samimitar o behtar)
        text01_sif.setText("اگه دوستان شما زمان ثبت نام کد زیر رو بزنن،به ازای هر نفر "+Utility.enToFa("1500")+" فندق دریافت می کنی. ");

        inviteCode_sif= (LuckyTextView) findViewById(R.id.inviteCode_sif);

        //rabbitBoard_s
        ImageView fox= (ImageView) findViewById(R.id.rabbitBoard_sif);
        fox.setImageResource(R.drawable.c_rabbit_board_anim_a);
        AnimationDrawable anim = (AnimationDrawable) fox.getDrawable();
        anim.start();

        cloudMotion_a();

        inviteCode_sif.setText(Utility.enToFa(session.getCurrentUser().getInviteCode().toString()));

        Button btn_invite_sif= (Button) findViewById(R.id.btn_invite_sif);
        btn_invite_sif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View ll=findViewById(R.id.F_rabbitBoard_sif);
                ImageView t_title_b_sif= (ImageView) findViewById(R.id.t_title_b_sif);
                t_title_b_sif.setVisibility(View.VISIBLE);
                ll.setBackgroundResource(R.drawable.t_bg_a);
                text01_sif.setText("موقع ثبت نام در بازی LuckyLord این کد دعوت رو "+" بزن تا 500 فندق جایزه بگیری");
                ll.setDrawingCacheEnabled(true);
                ll.buildDrawingCache();
                Bitmap b = ll.getDrawingCache();
                t_title_b_sif.setVisibility(View.INVISIBLE);
                ll.setBackgroundDrawable(null);
                text01_sif.setText("اگه دوستان شما زمان ثبت نام کد زیر رو بزنن،به ازای هر نفر شما 1500 فندق دریافت می کنید.");
                Utility.store(b,session.getCurrentUser().getInviteCode());
                shareImage(new File(Utility.dirPath, session.getCurrentUser().getInviteCode()));

                //meysam - learmemory
                b.recycle();
                b = null;
            }
        });
    }
    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "اشتراک گذاری..."));
        } catch (ActivityNotFoundException e) {
            Utility.displayToast(getApplicationContext(), "برنامه ای برای اشتراک وجود ندارد", Toast.LENGTH_SHORT);
        }
    }


    private void cloudMotion_a(){
        asyncTask=new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                //anim one
                int i1 = new Random().nextInt(5 - 1) + 3;
                ImageView c_cloud_a_scu = (ImageView) findViewById(R.id.c_cloud_a_sif);
                Anims.cloudMotion(c_cloud_a_scu,width+3.0f, -1*(width+30.0f),i1*10000);
                //anim two
                int i2 = new Random().nextInt(4 - 1) + 2;
                ImageView c_cloud_b_scu = (ImageView) findViewById(R.id.c_cloud_b_sif);
                Anims.cloudMotion(c_cloud_b_scu,width+5.0f, -1*(width+30.0f),i2*10000);
                //anim three
                int i3 = new Random().nextInt(4 - 1) + 2;
                ImageView c_cloud_c_scu = (ImageView) findViewById(R.id.c_cloud_c_sif);
                Anims.cloudMotion(c_cloud_c_scu, -1*(width+5.0f),width+30.0f,i3*10000);
                //anim four
                int i4 = new Random().nextInt(5 - 1) + 3;
                ImageView c_cloud_d_scu = (ImageView) findViewById(R.id.c_cloud_d_sif);
                Anims.cloudMotion(c_cloud_d_scu,width+2.0f, -1*(width+30.0f),i4*10000);

                return null;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }
        };
        asyncTask.execute();
    }
}
