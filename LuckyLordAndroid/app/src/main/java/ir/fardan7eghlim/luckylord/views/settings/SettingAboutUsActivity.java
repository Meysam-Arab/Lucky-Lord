package ir.fardan7eghlim.luckylord.views.settings;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;

public class SettingAboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about_us);

//        //Amir
//        ImageView amir= (ImageView) findViewById(R.id.p_amir);
//        amir.setImageResource(R.drawable.p_amir_anim_a);
//        AnimationDrawable anim_amir = (AnimationDrawable) amir.getDrawable();
//        anim_amir.start();
//
//        //meysam
//        ImageView meysam= (ImageView) findViewById(R.id.p_meysam);
//        meysam.setImageResource(R.drawable.p_meysam_anim_a);
//        AnimationDrawable anim_meysam = (AnimationDrawable) meysam.getDrawable();
//        anim_meysam.start();
//
//        //hooman
//        ImageView hooman= (ImageView) findViewById(R.id.p_hooman);
//        hooman.setImageResource(R.drawable.p_hooman_anim_a);
//        AnimationDrawable anim_hooman = (AnimationDrawable) hooman.getDrawable();
//        anim_hooman.start();

        LuckyTextView tv_version = (LuckyTextView) findViewById(R.id.tv_vrsion);
        PackageInfo pInfo = null;
        String version = getString(R.string.error_undefined);
//        int verCode = pInfo.versionCode;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = getString(R.string.lbl_version)+" : " + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_version.setText(version);
    }
}
