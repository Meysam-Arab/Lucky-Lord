package ir.fardan7eghlim.luckylord.views.user;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.BodyPartController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.Avatar;
import ir.fardan7eghlim.luckylord.models.BodyPartModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.home.StoreActivity;

public class MakeAvatarActivity extends BaseActivity implements Observer {
    private DialogModel DM;
    private Avatar avatar;
    private Avatar master_avatar;

    private String sexAge;

    private ImageView result_hair_back;
    private ImageView result_face;
    private ImageView result_glasses;
    private ImageView result_nose;
    private ImageView result_lip;
    private ImageView result_eye;
    private ImageView result_eyebrow;
    private ImageView result_dress;
    private ImageView result_neckless;
    private ImageView result_beard;
    private ImageView result_earing;
    private ImageView result_hair_front;
    private ImageView result_hat;
    private TextView hazzel_make_avatar_show;

    private String[] item_name_eng={"face","nose","lip","eye","eyebrow","hair","dress","glasses","beard","hat","earing","neckless"};
    private String[] item_name_fa={"صورت","بینی","دهان","چشم","ابرو","مو","لباس","عینک","چهره","کلاه","گوشواره","گردن بند"};

    private TextView current_item_text_avt;
    private ImageView prev_item;
    private ImageView next_item;
    private FrameLayout prev_item_avt;
    private FrameLayout next_item_avt;
    private TextView prev_item_text_avt;
    private TextView next_item_text_avt;
    private int k=0;
    private boolean must_buy=false;

    private ArrayList<BodyPartModel> bodyPartsShopList;
    private ArrayList<String> bodyPartsShopList_names;
    private BodyPartModel item_name_for_purch;
    private BodyPartController bpc;
    private UserController uc;

    private String reqTag;

//    private SessionModel session;

    Animator anm_market;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_avatar);

        reqTag = null;

        DM=new DialogModel(MakeAvatarActivity.this);
        DM.show();

//        session = new SessionModel(getApplicationContext());

        result_hair_back= (ImageView) findViewById(R.id.result_hair_back_avt);
        result_face= (ImageView) findViewById(R.id.result_face_avt);
        result_nose= (ImageView) findViewById(R.id.result_nose_avt);
        result_lip= (ImageView) findViewById(R.id.result_lip_avt);
        result_eye= (ImageView) findViewById(R.id.result_eye_avt);
        result_eyebrow= (ImageView) findViewById(R.id.result_eyebrow_avt);
        result_dress= (ImageView) findViewById(R.id.result_dress_avt);
        result_neckless= (ImageView) findViewById(R.id.result_neckless_avt);
        result_beard= (ImageView) findViewById(R.id.result_beard_avt);
        result_earing= (ImageView) findViewById(R.id.result_earing_avt);
        result_glasses= (ImageView) findViewById(R.id.result_glasses_avt);
        result_hair_front= (ImageView) findViewById(R.id.result_hair_front_avt);
        result_hat= (ImageView) findViewById(R.id.result_hat_avt);

        current_item_text_avt=(TextView) findViewById(R.id.current_item_text_avt);
        prev_item_avt= (FrameLayout) findViewById(R.id.prev_item_avt);
        next_item_avt= (FrameLayout) findViewById(R.id.next_item_avt);
        prev_item_text_avt= (TextView) findViewById(R.id.prev_item_text_avt);
        next_item_text_avt= (TextView) findViewById(R.id.next_item_text_avt);

        //sexAge
//        final SessionModel session = new SessionModel(getApplicationContext());
        avatar = new Avatar(session.getCurrentUser().getProfilePicture());
        master_avatar=new Avatar(session.getCurrentUser().getProfilePicture());
        avatar.setAvatar(this);
        sexAge=avatar.getSexAge();

        bodyPartsShopList=new ArrayList<>();
        bodyPartsShopList_names=new ArrayList<>();

        //items
        prev_item= (ImageView) findViewById(R.id.prev_item);
        next_item= (ImageView) findViewById(R.id.next_item);

        //K
        current_item_text_avt.setText(item_name_fa[k]);
        prev_item_text_avt.setText("...");
        prev_item_avt.setEnabled(false);
        next_item_text_avt.setText(item_name_fa[k+1]);
        next_item_avt.setEnabled(true);

        prev_item_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_item_avt.setEnabled(false);
                next_item_avt.setEnabled(false);

                if(must_buy){
                    reset_item(item_name_eng[k],current_item_code_master(item_name_eng[k]));
                    luckStore(false,0,"");
                    must_buy=false;
                }

                k--;
                current_item_text_avt.setText(item_name_fa[k]);
                if(k>0){
                    prev_item_text_avt.setText(item_name_fa[k-1]);
                    prev_item_avt.setEnabled(true);
                    next_item_text_avt.setText(item_name_fa[k+1]);
                    next_item_avt.setEnabled(true);
                }else{
                    prev_item_text_avt.setText("...");
                    prev_item_avt.setEnabled(false);
                    next_item_text_avt.setText(item_name_fa[k+1]);
                    next_item_avt.setEnabled(true);
                }
                arrangeItems(item_name_eng[k],current_item_code(item_name_eng[k]));
                next_item_text_avt.setTextColor(Color.parseColor("#875107"));
                prev_item_text_avt.setTextColor(Color.parseColor("#875107"));
            }
        });
        next_item_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_item_avt.setEnabled(false);
                next_item_avt.setEnabled(false);

                if(must_buy){
                    reset_item(item_name_eng[k],current_item_code_master(item_name_eng[k]));
                    luckStore(false,0,"");
                    must_buy=false;
                }

                k++;
                current_item_text_avt.setText(item_name_fa[k]);
                if(k<item_name_fa.length-1){
                    next_item_text_avt.setText(item_name_fa[k+1]);
                    next_item_avt.setEnabled(true);
                    prev_item_text_avt.setText(item_name_fa[k-1]);
                    prev_item_avt.setEnabled(true);
                }else{
                    next_item_text_avt.setText("...");
                    next_item_avt.setEnabled(false);
                    prev_item_text_avt.setText(item_name_fa[k-1]);
                    prev_item_avt.setEnabled(true);
                }
                arrangeItems(item_name_eng[k],current_item_code(item_name_eng[k]));
                next_item_text_avt.setTextColor(Color.parseColor("#875107"));
                prev_item_text_avt.setTextColor(Color.parseColor("#875107"));
            }
        });

        //items
        arrangeItems(item_name_eng[k],current_item_code(item_name_eng[k]));

        //buttons
        Button reset_avt= (Button) findViewById(R.id.reset_avt);
        reset_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final SessionModel session = new SessionModel(getApplicationContext());
                avatar = new Avatar(session.getCurrentUser().getProfilePicture());
                master_avatar=new Avatar(session.getCurrentUser().getProfilePicture());
                avatar.setAvatar(MakeAvatarActivity.this);
                k=0;
                must_buy=false;
                luckStore(false,0,"");
                current_item_text_avt.setText(item_name_fa[k]);
                prev_item_text_avt.setText("...");
                prev_item_avt.setEnabled(false);
                next_item_text_avt.setText(item_name_fa[k+1]);
                next_item_avt.setEnabled(true);
                //items
                arrangeItems(item_name_eng[k],current_item_code(item_name_eng[k]));
            }
        });
        Button cancel_avt= (Button) findViewById(R.id.cancel_avt);
        cancel_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView store= (ImageView) findViewById(R.id.store_make_avatar_btn);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeAvatarActivity.this, StoreActivity.class);
                MakeAvatarActivity.this.startActivity(i);
                finish();
            }
        });
        anm_market = Flubber.with().listener(flubberMarketListener)
                .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                .repeatCount(0)                              // Repeat once
                .duration(1200)  // Last for 1000 milliseconds(1 second)
                .delay(5000)
//                .force((float) 0.5)
                .createFor(store);
        anm_market.start();// Apply it to the view
        //////////////////////////////////////////


        hazzel_make_avatar_show= (TextView) findViewById(R.id.hazzel_make_avatar_show);
        hazzel_make_avatar_show.setText(session.getCurrentUser().getHazel().toString());

        //shop
        bpc=new BodyPartController(MakeAvatarActivity.this);
        bpc.addObserver(this);
        bpc.index();
        reqTag = RequestRespondModel.TAG_BODY_PART_INDEX;

        uc=new UserController(MakeAvatarActivity.this);
        uc.addObserver(MakeAvatarActivity.this);

        //save as image
        Button save_avt= (Button) findViewById(R.id.save_avt);
        save_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(MakeAvatarActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                d.setContentView(R.layout.save_avatar);

                avatar.setAvatar(MakeAvatarActivity.this,d);
                final FrameLayout ll= (FrameLayout) d.findViewById(R.id.result_avt);

                Button btn_saveAsImage= (Button) d.findViewById(R.id.saveAsImage_avt);
                btn_saveAsImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll.setDrawingCacheEnabled(true);
                        Bitmap bitmap = ll.getDrawingCache();

                        String root = Environment.getExternalStorageDirectory().toString();
                        File newDir = new File(root + "/LuckyLord");
                        newDir.mkdirs();
                        String fotoname = session.getCurrentUser().getUserName() + ".jpg";
                        File file = new File(newDir, fotoname);

                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                            Utility.displayToast(getApplicationContext()," با موفقیت ذخیره شد.", Toast.LENGTH_SHORT);
                        } catch (Exception e) {
                            Utility.displayToast(getApplicationContext(),"یه مشکلی پیش اومد!", Toast.LENGTH_SHORT);
                        }

                        d.hide();
                    }
                });

                Button btn_shareAsImage= (Button) d.findViewById(R.id.shareAsImage_avt);
                btn_shareAsImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll.setDrawingCacheEnabled(true);
                        Bitmap b = ll.getDrawingCache();

                        Utility.store(b,session.getCurrentUser().getInviteCode());
                        shareImage(new File(Utility.dirPath, session.getCurrentUser().getInviteCode()));

                        d.hide();
                    }
                });
                d.show();
            }
        });
    }

    private void arrangeItems(final String item,final int first){

        //reset
        prev_item.setImageResource(R.drawable.b_next_alpha);
        next_item.setImageResource(R.drawable.b_next_alpha);
        prev_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        next_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        int i=1;
        if(k>4) i=0;

        if(first!=i){
            prev_item.setImageResource(R.drawable.b_next_btn);
            prev_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test_item(first-1,item);
                    arrangeItems(item,first-1);
                }
            });
        }else{
            prev_item.setImageResource(R.drawable.b_next_alpha);
        }


        String file_name=sexAge+"_"+item+"_"+(first+1);
        int file_id = getResources().getIdentifier(file_name, "drawable", getPackageName());
        if(file_id!=0){
            next_item.setImageResource(R.drawable.b_next_btn);
            next_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test_item(first+1,item);
                    arrangeItems(item,first+1);
                }
            });
        }else {
            next_item.setImageResource(R.drawable.b_next_alpha);
        }

//        enableButtonItems();
    }

    private void test_item(int finalI, String item) {
        String file_name=sexAge+"_"+item+"_"+finalI;
        int file_id = getResources().getIdentifier(file_name, "drawable", getPackageName());
        if(finalI==0){
            file_id = getResources().getIdentifier("nothing", "drawable", getPackageName());
        }
        boolean is_free=true;
        if(file_id!=0){
            //for shop
            if(bodyPartsShopList_names.contains(file_name)){
                is_free=false;
            }
        }

        Drawable masterBitmap=getResources().getDrawable(file_id);
        switch (item) {
            case "face":
                result_face.setImageDrawable(masterBitmap);
                avatar.setFace(finalI);
                break;
            case "nose":
                result_nose.setImageDrawable(masterBitmap);
                avatar.setNose(finalI);
                break;
            case "lip":
                result_lip.setImageDrawable(masterBitmap);
                avatar.setLip(finalI);
                break;
            case "eye":
                result_eye.setImageDrawable(masterBitmap);
                avatar.setEye(finalI);
                break;
            case "eyebrow":
                result_eyebrow.setImageDrawable(masterBitmap);
                avatar.setEyebrow(finalI);
                break;
            case "glasses":
                if (finalI > 0) {
                    result_glasses.setImageDrawable(masterBitmap);
                    result_glasses.setVisibility(View.VISIBLE);
                } else {
                    result_glasses.setVisibility(View.GONE);
                }
                avatar.setGlasses(finalI);
                break;
            case "hair":
                result_hair_back.setVisibility(View.GONE);
                if (finalI > 0) {
                    result_hair_front.setImageDrawable(masterBitmap);
                    result_hair_front.setVisibility(View.VISIBLE);

                    int file_id_02 = getResources().getIdentifier(sexAge + "_" + item + "_" + finalI + "_back", "drawable", getPackageName());
                    if (file_id_02 != 0) {
                        result_hair_back.setImageResource(file_id_02);
                        result_hair_back.setVisibility(View.VISIBLE);
                    }
                } else {
                    result_hair_front.setVisibility(View.GONE);
                }
                avatar.setHair(finalI);
                break;
            case "hat":
                if (finalI > 0) {
                    result_hat.setImageDrawable(masterBitmap);
                    result_hat.setVisibility(View.VISIBLE);
                } else {
                    result_hat.setVisibility(View.GONE);
                }
                avatar.setHat(finalI);
                break;
            case "neckless":
                if (finalI > 0) {
                    result_neckless.setImageDrawable(masterBitmap);
                    result_neckless.setVisibility(View.VISIBLE);
                } else {
                    result_neckless.setVisibility(View.GONE);
                }
                avatar.setNeckless(finalI);
                break;
            case "earing":
                if (finalI > 0) {
                    result_earing.setImageDrawable(masterBitmap);
                    result_earing.setVisibility(View.VISIBLE);
                } else {
                    result_earing.setVisibility(View.GONE);
                }
                avatar.setEaring(finalI);
                break;
            case "dress":
                if (finalI > 0) {
                    result_dress.setImageDrawable(masterBitmap);
                    result_dress.setVisibility(View.VISIBLE);
                } else {
                    result_dress.setVisibility(View.GONE);
                }
                avatar.setDress(finalI);
                break;
            case "beard":
                if (finalI > 0) {
                    result_beard.setImageDrawable(masterBitmap);
                    result_beard.setVisibility(View.VISIBLE);
                } else {
                    result_beard.setVisibility(View.GONE);
                }
                avatar.setBeard(finalI);
                break;
        }
        //for shop
        if(!is_free){
            luckStore(true,getPrice(file_name),file_name);
            must_buy=true;
        }else{
            luckStore(false,0,"");
            must_buy=false;
        }
    }

    private void reset_item(String item,int finalI) {
        String file_name=sexAge+"_"+item+"_"+finalI;
        int file_id = getResources().getIdentifier(file_name, "drawable", getPackageName());
        if(finalI==0){
            file_id = getResources().getIdentifier("nothing", "drawable", getPackageName());
        }
        Drawable masterBitmap=getResources().getDrawable(file_id);
        switch (item) {
            case "face":
                result_face.setImageDrawable(masterBitmap);
                avatar.setFace(finalI);
                break;
            case "nose":
                result_nose.setImageDrawable(masterBitmap);
                avatar.setNose(finalI);
                break;
            case "lip":
                result_lip.setImageDrawable(masterBitmap);
                avatar.setLip(finalI);
                break;
            case "eye":
                result_eye.setImageDrawable(masterBitmap);
                avatar.setEye(finalI);
                break;
            case "eyebrow":
                result_eyebrow.setImageDrawable(masterBitmap);
                avatar.setEyebrow(finalI);
                break;
            case "glasses":
                if (finalI > 0) {
                    result_glasses.setImageDrawable(masterBitmap);
                    result_glasses.setVisibility(View.VISIBLE);
                } else {
                    result_glasses.setVisibility(View.GONE);
                }
                avatar.setGlasses(finalI);
                break;
            case "hair":
                result_hair_back.setVisibility(View.GONE);
                if (finalI > 0) {
                    result_hair_front.setImageDrawable(masterBitmap);
                    result_hair_front.setVisibility(View.VISIBLE);

                    int file_id_02 = getResources().getIdentifier(sexAge + "_" + item + "_" + finalI + "_back", "drawable", getPackageName());
                    if (file_id_02 != 0) {
                        result_hair_back.setImageResource(file_id_02);
                        result_hair_back.setVisibility(View.VISIBLE);
                    }
                } else {
                    result_hair_front.setVisibility(View.GONE);
                }
                avatar.setHair(finalI);
                break;
            case "hat":
                if (finalI > 0) {
                    result_hat.setImageDrawable(masterBitmap);
                    result_hat.setVisibility(View.VISIBLE);
                } else {
                    result_hat.setVisibility(View.GONE);
                }
                avatar.setHat(finalI);
                break;
            case "neckless":
                if (finalI > 0) {
                    result_neckless.setImageDrawable(masterBitmap);
                    result_neckless.setVisibility(View.VISIBLE);
                } else {
                    result_neckless.setVisibility(View.GONE);
                }
                avatar.setNeckless(finalI);
                break;
            case "earing":
                if (finalI > 0) {
                    result_earing.setImageDrawable(masterBitmap);
                    result_earing.setVisibility(View.VISIBLE);
                } else {
                    result_earing.setVisibility(View.GONE);
                }
                avatar.setEaring(finalI);
                break;
            case "dress":
                if (finalI > 0) {
                    result_dress.setImageDrawable(masterBitmap);
                    result_dress.setVisibility(View.VISIBLE);
                } else {
                    result_dress.setVisibility(View.GONE);
                }
                avatar.setDress(finalI);
                break;
            case "beard":
                if (finalI > 0) {
                    result_beard.setImageDrawable(masterBitmap);
                    result_beard.setVisibility(View.VISIBLE);
                } else {
                    result_beard.setVisibility(View.GONE);
                }
                avatar.setBeard(finalI);
                break;
        }
    }

    private void enableButtonItems(){
        if(k>0) {
            prev_item_avt.setEnabled(true);
        }
        if(k<item_name_fa.length-1){
            next_item_avt.setEnabled(true);
        }
        ImageView next_item_avt_p1= (ImageView) findViewById(R.id.next_item_avt_p1);
        next_item_avt_p1.setImageResource(R.drawable.bg_05);
        ImageView next_item_avt_p2= (ImageView) findViewById(R.id.next_item_avt_p2);
        next_item_avt_p2.setImageResource(R.drawable.b_next_all);
        ImageView prev_item_avt_p1= (ImageView) findViewById(R.id.prev_item_avt_p1);
        prev_item_avt_p1.setImageResource(R.drawable.bg_05);
        ImageView prev_item_avt_p2= (ImageView) findViewById(R.id.prev_item_avt_p2);
        prev_item_avt_p2.setImageResource(R.drawable.b_next_all);
    }

    private Bitmap TrimBitmap(Bitmap bmp) {
        int imgHeight = bmp.getHeight();
        int imgWidth  = bmp.getWidth();

        //TRIM WIDTH - LEFT
        int startWidth = 0;
        for(int x = 0; x < imgWidth; x++) {
            if (startWidth == 0) {
                for (int y = 0; y < imgHeight; y++) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        startWidth = x;
                        break;
                    }
                }
            } else break;
        }

        //TRIM WIDTH - RIGHT
        int endWidth  = 0;
        for(int x = imgWidth - 1; x >= 0; x--) {
            if (endWidth == 0) {
                for (int y = 0; y < imgHeight; y++) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        endWidth = x;
                        break;
                    }
                }
            } else break;
        }

        //TRIM HEIGHT - TOP
        int startHeight = 0;
        for(int y = 0; y < imgHeight; y++) {
            if (startHeight == 0) {
                for (int x = 0; x < imgWidth; x++) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        startHeight = y;
                        break;
                    }
                }
            } else break;
        }

        //TRIM HEIGHT - BOTTOM
        int endHeight = 0;
        for(int y = imgHeight - 1; y >= 0; y--) {
            if (endHeight == 0 ) {
                for (int x = 0; x < imgWidth; x++) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        endHeight = y;
                        break;
                    }
                }
            } else break;
        }

        return Bitmap.createBitmap(
                bmp,
                startWidth,
                startHeight,
                endWidth - startWidth,
                endHeight - startHeight
        );
    }

    private void luckStore(boolean flag,final int price,final String item_name){
        Button setAvatar_avt= (Button) findViewById(R.id.setAvatar_avt);
        FrameLayout price_banner= (FrameLayout) findViewById(R.id.price_banner_ma);
        if(flag){
//            next_item_avt.setEnabled(false);
//            ImageView next_item_avt_p1= (ImageView) findViewById(R.id.next_item_avt_p1);
//            next_item_avt_p1.setImageResource(R.drawable.bg_05_alpha);
//            ImageView next_item_avt_p2= (ImageView) findViewById(R.id.next_item_avt_p2);
//            next_item_avt_p2.setImageResource(R.drawable.b_next_all_alpha);
//            next_item_text_avt.setTextColor(Color.parseColor("#782f1f0a"));
//            prev_item_avt.setEnabled(false);
//            ImageView prev_item_avt_p1= (ImageView) findViewById(R.id.prev_item_avt_p1);
//            prev_item_avt_p1.setImageResource(R.drawable.bg_05_alpha);
//            ImageView prev_item_avt_p2= (ImageView) findViewById(R.id.prev_item_avt_p2);
//            prev_item_avt_p2.setImageResource(R.drawable.b_next_all_alpha);
//            prev_item_text_avt.setTextColor(Color.parseColor("#782f1f0a"));
            setAvatar_avt.setText("خرید ("+price+" فندق)");
            price_banner.setVisibility(View.VISIBLE);
            TextView price_text_ma= (TextView) findViewById(R.id.price_text_ma);
            price_text_ma.setText(price+"");
            setAvatar_avt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d= new Dialog(MakeAvatarActivity.this);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    d.setContentView(R.layout.message_dialog);

                    TextView message_box_dialog= (TextView) d.findViewById(R.id.message_box_dialog);
                    Button btn_dialog_01= (Button) d.findViewById(R.id.btn_mess_01);
                    Button btn_dialog_02= (Button) d.findViewById(R.id.btn_mess_02);

                    message_box_dialog.setText("مقدار "+price+" فندق برای خرید این آیتم لازم است");
                    btn_dialog_01.setText("از حسابم کم کن");
                    btn_dialog_02.setText("نه نمی خوام");
                    btn_dialog_02.setVisibility(View.VISIBLE);

                    btn_dialog_01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if( Utility.hasEnoughCoin(MakeAvatarActivity.this,price))
                            {
                                //purchasing
                                DM.show();
//                                BodyPartController mc=new BodyPartController(MakeAvatarActivity.this);
//                                mc.addObserver(MakeAvatarActivity.this);
                                item_name_for_purch=getBodyPart(item_name);
                                bpc.purchase(item_name_for_purch);
                                d.hide();
                            }
                            else
                            {
                                DialogPopUpModel.show(MakeAvatarActivity.this,"برای خرید باید حداقل " + price + " فندق داشته باشی!","باشه",null, false, false);

                            }

                        }
                    });

                    btn_dialog_02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();
                        }
                    });

                    d.show();
                }
            });
        }else{
            enableButtonItems();
            next_item_text_avt.setTextColor(Color.parseColor("#875107"));
            prev_item_text_avt.setTextColor(Color.parseColor("#875107"));
            price_banner.setVisibility(View.GONE);
            setAvatar_avt.setText("خوبه");
            setAvatar_avt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set avatar
                    DM.show();

                    String pic=avatar.makeString();
                    uc.updateProfilePicture(pic);

//                    SessionModel session=new SessionModel(MakeAvatarActivity.this);
                    session.saveItem(SessionModel.KEY_PROFILE_IMAGE,pic);
                }
            });
        }
    }

    private int getPrice(String item_name){
        for(BodyPartModel bp:bodyPartsShopList){
            if(bp.getName().equals(item_name)){
                return bp.getCost();
            }
        }
        return 0;
    }
    private BodyPartModel getBodyPart(String item_name){
        for(BodyPartModel bp:bodyPartsShopList){
            if(bp.getName().equals(item_name)){
                return bp;
            }
        }
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
        DM.hide();
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);

                    if(reqTag != null)
                    {
                        if(reqTag.equals(RequestRespondModel.TAG_BODY_PART_INDEX))
                        {
                            reqTag = null;
                            Utility.finishActivity(MakeAvatarActivity.this);
                        }
                    }

                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0)== RequestRespondModel.TAG_BODY_PART_INDEX) {
                    //index
                    reqTag = null;
                    bodyPartsShopList = (ArrayList<BodyPartModel>) ((ArrayList) arg).get(1);
                    for (BodyPartModel bp : bodyPartsShopList) {
                        bodyPartsShopList_names.add(bp.getName());
                    }
                }else if(((ArrayList) arg).get(0)== RequestRespondModel.TAG_UPDATE_PROFILE_USER) {
                    //set avatar successed
                    Utility.finishActivity(this);
                }else {
                    //purchasing
                    bodyPartsShopList.remove(item_name_for_purch);
                    bodyPartsShopList_names.remove(item_name_for_purch.getName());
                    String temp[]=item_name_for_purch.getName().split("_");
                    arrangeItems(temp[1],new Integer(temp[2]));
                    session.decreaseHazel(item_name_for_purch.getCost().toString());
                    luckStore(false,0,"");
                    must_buy=false;
                    hazzel_make_avatar_show.setText(session.getCurrentUser().getHazel().toString());
                }
            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
//                    SessionModel session = new SessionModel(getApplicationContext());
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    Utility.finishActivity(this);
                }else {
                    Utility.displayToast(getApplicationContext(),new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            }
            else
            {
                Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    //share avatar
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
    private int current_item_code(String item) {
        int out=0;
        switch (item) {
            case "face":
                out=avatar.getFace();
                break;
            case "nose":
                out=avatar.getNose();
                break;
            case "lip":
                out=avatar.getLip();
                break;
            case "eye":
                out=avatar.getEye();
                break;
            case "eyebrow":
                out=avatar.getEyebrow();
                break;
            case "glasses":
                out=avatar.getGlasses();
                break;
            case "hair":
                out=avatar.getHair();
                break;
            case "hat":
                out=avatar.getHat();
                break;
            case "neckless":
                out=avatar.getNeckless();
                break;
            case "earing":
                out=avatar.getEaring();
                break;
            case "dress":
                out=avatar.getDress();
                break;
            case "beard":
                out=avatar.getBeard();
                break;
        }

        return out;
    }
    private int current_item_code_master(String item) {
        int out=0;
        switch (item) {
            case "face":
                out=master_avatar.getFace();
                break;
            case "nose":
                out=master_avatar.getNose();
                break;
            case "lip":
                out=master_avatar.getLip();
                break;
            case "eye":
                out=master_avatar.getEye();
                break;
            case "eyebrow":
                out=master_avatar.getEyebrow();
                break;
            case "glasses":
                out=master_avatar.getGlasses();
                break;
            case "hair":
                out=master_avatar.getHair();
                break;
            case "hat":
                out=master_avatar.getHat();
                break;
            case "neckless":
                out=master_avatar.getNeckless();
                break;
            case "earing":
                out=master_avatar.getEaring();
                break;
            case "dress":
                out=master_avatar.getDress();
                break;
            case "beard":
                out=master_avatar.getBeard();
                break;
        }
        return out;
    }

    @Override
    public void onPause() {
        super.onPause();
        bpc.setCntx(null);
        bpc.deleteObservers();
        bpc = null;

        uc.setCntx(null);
        uc.deleteObservers();
        uc = null;
    }

    @Override
    public void onResume() {

        bpc = new BodyPartController(MakeAvatarActivity.this);
        bpc.addObserver(this);

        uc = new UserController(MakeAvatarActivity.this);
        uc.addObserver(this);

        super.onResume();
    }

    @Override
    public void onBackPressed() {

        final String pic=avatar.makeString();
        if(!session.getCurrentUser().getProfilePicture().equals(pic))
        {

            if( !must_buy)
            {
                DialogPopUpModel.show(MakeAvatarActivity.this,getString(R.string.msg_SaveChanges),getString(R.string.btn_Yes),getString(R.string.btn_No),true,false);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            while(DialogPopUpModel.isUp()){
                                Thread.sleep(500);
                            }
                            if(!DialogPopUpModel.isUp()){
                                Thread.currentThread().interrupt();//meysam 13960525
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(DialogPopUpModel.dialog_result==1){
                                            //yes was pressed - save avatar
                                            DM.show();

                                            uc.updateProfilePicture(pic);
                                            session.saveItem(SessionModel.KEY_PROFILE_IMAGE,pic);
                                        }
                                        else
                                        {
                                            finishActivity();
                                        }
                                    }
                                });
                                DialogPopUpModel.hide();
                            }
                        }
                        catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                //////////////////////////////////////////
            }
            else
            {
                DialogPopUpModel.show(MakeAvatarActivity.this,getString(R.string.msg_ChangesDosntSavedAreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No),true,false);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            while(DialogPopUpModel.isUp()){
                                Thread.sleep(500);
                            }
                            if(!DialogPopUpModel.isUp()){
                                Thread.currentThread().interrupt();//meysam 13960525
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(DialogPopUpModel.dialog_result==1){
                                            //yes was pressed - finish activity
                                            finishActivity();
                                        }
                                        else
                                        {
                                            //meysam - do nothing
                                        }
                                    }
                                });
                                DialogPopUpModel.hide();
                            }
                        }
                        catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                //////////////////////////////////////////
            }
        }
        else
        {
            finishActivity();
        }


    }

    private void finishActivity()
    {
        finish();
        avatar=null;
        result_hair_back=null;
        result_face=null;
        result_glasses=null;
        result_nose=null;
        result_lip=null;
        result_eye=null;
        result_eyebrow=null;
        result_dress=null;
        result_neckless=null;
        result_beard=null;
        result_earing=null;
        result_hair_front=null;
        result_hat=null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    public void onDestroy() {
        anm_market.end();
        anm_market.cancel();
        anm_market.removeAllListeners();

        flubberMarketListener = null;

        super.onDestroy();
    }

    public Animator.AnimatorListener flubberMarketListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ImageView store= (ImageView) findViewById(R.id.store_make_avatar_btn);
            Random rand = new Random();
            int min = 5;
            int max = 25;
            int delay = rand.nextInt((max - min) + 1) + min;
            anm_market.removeAllListeners();
            anm_market = Flubber.with().listener(flubberMarketListener)
                    .animation(Flubber.AnimationPreset.SQUEEZE) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1200)  // Last for 1000 milliseconds(1 second)
                    .delay(delay*1000)
//                    .force((float) 0.5)
                    .createFor(store);
            anm_market.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    } ;

}
