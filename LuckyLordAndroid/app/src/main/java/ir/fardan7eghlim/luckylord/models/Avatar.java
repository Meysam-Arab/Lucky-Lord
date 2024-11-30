package ir.fardan7eghlim.luckylord.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import ir.fardan7eghlim.luckylord.R;

/**
 * Created by Amir on 6/9/2017.
 */

public class Avatar {
                        //sexAge#face#nose#lip#eye#eyebrow#hair#dress#neckless#beard#earing#glasses#hat
    public static int[] max_m_bodyPart={20,22,9,18,24,49,25,4,31,2,14,0};
    public static int[] max_f_bodyPart={16,18,13,20,18,55,30,15,5,10,21,0};

    public void clear(View rowView){
        ImageView result_hair_back= (ImageView) rowView.findViewById(R.id.result_hair_back_avt);
        ImageView result_face= (ImageView) rowView.findViewById(R.id.result_face_avt);
        ImageView result_nose= (ImageView) rowView.findViewById(R.id.result_nose_avt);
        ImageView result_lip= (ImageView) rowView.findViewById(R.id.result_lip_avt);
        ImageView result_eye= (ImageView) rowView.findViewById(R.id.result_eye_avt);
        ImageView result_eyebrow= (ImageView) rowView.findViewById(R.id.result_eyebrow_avt);
        ImageView result_dress= (ImageView) rowView.findViewById(R.id.result_dress_avt);
        ImageView result_neckless= (ImageView) rowView.findViewById(R.id.result_neckless_avt);
        ImageView result_beard= (ImageView) rowView.findViewById(R.id.result_beard_avt);
        ImageView result_earing= (ImageView) rowView.findViewById(R.id.result_earing_avt);
        ImageView result_glasses= (ImageView) rowView.findViewById(R.id.result_glasses_avt);
        ImageView result_hair_front= (ImageView) rowView.findViewById(R.id.result_hair_front_avt);
        ImageView result_hat= (ImageView) rowView.findViewById(R.id.result_hat_avt);

        result_hair_back.setImageBitmap(null);
        result_face.setImageBitmap(null);
        result_nose.setImageBitmap(null);
        result_lip.setImageBitmap(null);
        result_eye.setImageBitmap(null);
        result_eyebrow.setImageBitmap(null);
        result_dress.setImageBitmap(null);
        result_neckless.setImageBitmap(null);
        result_beard.setImageBitmap(null);
        result_earing.setImageBitmap(null);
        result_glasses.setImageBitmap(null);
        result_hair_front.setImageBitmap(null);
        result_hat.setImageBitmap(null);
    }

    public void setAvatar(Activity activity,View rowView) {
        ImageView result_hair_back= (ImageView) rowView.findViewById(R.id.result_hair_back_avt);
        ImageView result_face= (ImageView) rowView.findViewById(R.id.result_face_avt);
        ImageView result_nose= (ImageView) rowView.findViewById(R.id.result_nose_avt);
        ImageView result_lip= (ImageView) rowView.findViewById(R.id.result_lip_avt);
        ImageView result_eye= (ImageView) rowView.findViewById(R.id.result_eye_avt);
        ImageView result_eyebrow= (ImageView) rowView.findViewById(R.id.result_eyebrow_avt);
        ImageView result_dress= (ImageView) rowView.findViewById(R.id.result_dress_avt);
        ImageView result_neckless= (ImageView) rowView.findViewById(R.id.result_neckless_avt);
        ImageView result_beard= (ImageView) rowView.findViewById(R.id.result_beard_avt);
        ImageView result_earing= (ImageView) rowView.findViewById(R.id.result_earing_avt);
        ImageView result_glasses= (ImageView) rowView.findViewById(R.id.result_glasses_avt);
        ImageView result_hair_front= (ImageView) rowView.findViewById(R.id.result_hair_front_avt);
        ImageView result_hat= (ImageView) rowView.findViewById(R.id.result_hat_avt);

//        result_hair_back.setImageBitmap(null);
//        result_face.setImageBitmap(null);
//        result_nose.setImageBitmap(null);
//        result_lip.setImageBitmap(null);
//        result_eye.setImageBitmap(null);
//        result_eyebrow.setImageBitmap(null);
//        result_dress.setImageBitmap(null);
//        result_neckless.setImageBitmap(null);
//        result_beard.setImageBitmap(null);
//        result_earing.setImageBitmap(null);
//        result_glasses.setImageBitmap(null);
//        result_hair_front.setImageBitmap(null);
//        result_hat.setImageBitmap(null);

        String file_name=getSexAge()+"_face_"+getFace();
        int file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_face.setImageResource(file_id);

        file_name=getSexAge()+"_nose_"+getNose();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_nose.setImageResource(file_id);

        file_name=getSexAge()+"_lip_"+getLip();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_lip.setImageResource(file_id);

        file_name=getSexAge()+"_eye_"+getEye();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());

        result_eye.setImageResource(file_id);

        file_name=getSexAge()+"_eyebrow_"+getEyebrow();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eyebrow.setImageResource(file_id);

        if(getGlasses()>0){
            file_name=getSexAge()+"_glasses_"+getGlasses();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_glasses.setImageResource(file_id);
            result_glasses.setVisibility(View.VISIBLE);
        }else{
            result_glasses.setVisibility(View.GONE);
        }

        if(getHair()>0){
            //front
            file_name=getSexAge()+"_hair_"+getHair();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hair_front.setImageResource(file_id);
            result_hair_front.setVisibility(View.VISIBLE);

            //back
            result_hair_back.setVisibility(View.GONE);
            file_id = activity.getResources().getIdentifier(sexAge+"_hair_"+getHair()+"_back", "drawable", activity.getPackageName());
            if(file_id!=0){
                result_hair_back.setImageResource(file_id);
                result_hair_back.setVisibility(View.VISIBLE);
            }
        }else{
            result_hair_front.setVisibility(View.GONE);
        }

        if(getNeckless()>0){
            file_name=getSexAge()+"_neckless_"+getNeckless();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_neckless.setImageResource(file_id);
            result_neckless.setVisibility(View.VISIBLE);
        }else{
            result_neckless.setVisibility(View.GONE);
        }

        if(getEaring()>0){
            file_name=getSexAge()+"_earing_"+getEaring();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_earing.setImageResource(file_id);
            result_earing.setVisibility(View.VISIBLE);
        }else{
            result_earing.setVisibility(View.GONE);
        }

        if(getHat()>0){
            file_name=getSexAge()+"_hat_"+getHat();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hat.setImageResource(file_id);
            result_hat.setVisibility(View.VISIBLE);
        }else{
            result_hat.setVisibility(View.GONE);
        }

        if(getDress()>0){
            file_name=getSexAge()+"_dress_"+getDress();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_dress.setImageResource(file_id);
            result_dress.setVisibility(View.VISIBLE);
        }else{
            result_dress.setVisibility(View.GONE);
        }

        if(getBeard()>0){
            file_name=getSexAge()+"_beard_"+getBeard();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_beard.setImageResource(file_id);
            result_beard.setVisibility(View.VISIBLE);
        }else{
            result_beard.setVisibility(View.GONE);
        }
    }

    public void setAvatar(Activity activity,Dialog rowView) {
        ImageView result_hair_back= (ImageView) rowView.findViewById(R.id.result_hair_back_avt);
        ImageView result_face= (ImageView) rowView.findViewById(R.id.result_face_avt);
        ImageView result_nose= (ImageView) rowView.findViewById(R.id.result_nose_avt);
        ImageView result_lip= (ImageView) rowView.findViewById(R.id.result_lip_avt);
        ImageView result_eye= (ImageView) rowView.findViewById(R.id.result_eye_avt);
        ImageView result_eyebrow= (ImageView) rowView.findViewById(R.id.result_eyebrow_avt);
        ImageView result_dress= (ImageView) rowView.findViewById(R.id.result_dress_avt);
        ImageView result_neckless= (ImageView) rowView.findViewById(R.id.result_neckless_avt);
        ImageView result_beard= (ImageView) rowView.findViewById(R.id.result_beard_avt);
        ImageView result_earing= (ImageView) rowView.findViewById(R.id.result_earing_avt);
        ImageView result_glasses= (ImageView) rowView.findViewById(R.id.result_glasses_avt);
        ImageView result_hair_front= (ImageView) rowView.findViewById(R.id.result_hair_front_avt);
        ImageView result_hat= (ImageView) rowView.findViewById(R.id.result_hat_avt);


        String file_name=getSexAge()+"_face_"+getFace();
        int file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_face.setImageResource(file_id);

        file_name=getSexAge()+"_nose_"+getNose();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_nose.setImageResource(file_id);

        file_name=getSexAge()+"_lip_"+getLip();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_lip.setImageResource(file_id);

        file_name=getSexAge()+"_eye_"+getEye();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());

        result_eye.setImageResource(file_id);

        file_name=getSexAge()+"_eyebrow_"+getEyebrow();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eyebrow.setImageResource(file_id);

        if(getGlasses()>0){
            file_name=getSexAge()+"_glasses_"+getGlasses();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_glasses.setImageResource(file_id);
            result_glasses.setVisibility(View.VISIBLE);
        }else{
            result_glasses.setVisibility(View.GONE);
        }

        if(getHair()>0){
            //front
            file_name=getSexAge()+"_hair_"+getHair();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hair_front.setImageResource(file_id);
            result_hair_front.setVisibility(View.VISIBLE);

            //back
            result_hair_back.setVisibility(View.GONE);
            file_id = activity.getResources().getIdentifier(sexAge+"_hair_"+getHair()+"_back", "drawable", activity.getPackageName());
            if(file_id!=0){
                result_hair_back.setImageResource(file_id);
                result_hair_back.setVisibility(View.VISIBLE);
            }
        }else{
            result_hair_front.setVisibility(View.GONE);
        }

        if(getNeckless()>0){
            file_name=getSexAge()+"_neckless_"+getNeckless();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_neckless.setImageResource(file_id);
            result_neckless.setVisibility(View.VISIBLE);
        }else{
            result_neckless.setVisibility(View.GONE);
        }

        if(getEaring()>0){
            file_name=getSexAge()+"_earing_"+getEaring();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_earing.setImageResource(file_id);
            result_earing.setVisibility(View.VISIBLE);
        }else{
            result_earing.setVisibility(View.GONE);
        }

        if(getHat()>0){
            file_name=getSexAge()+"_hat_"+getHat();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hat.setImageResource(file_id);
            result_hat.setVisibility(View.VISIBLE);
        }else{
            result_hat.setVisibility(View.GONE);
        }

        if(getDress()>0){
            file_name=getSexAge()+"_dress_"+getDress();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_dress.setImageResource(file_id);
            result_dress.setVisibility(View.VISIBLE);
        }else{
            result_dress.setVisibility(View.GONE);
        }

        if(getBeard()>0){
            file_name=getSexAge()+"_beard_"+getBeard();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_beard.setImageResource(file_id);
            result_beard.setVisibility(View.VISIBLE);
        }else{
            result_beard.setVisibility(View.GONE);
        }
    }

    public void setAvatar(Activity activity){
        ImageView result_hair_back= (ImageView) activity.findViewById(R.id.result_hair_back_avt);
        ImageView result_face= (ImageView) activity.findViewById(R.id.result_face_avt);
        ImageView result_nose= (ImageView) activity.findViewById(R.id.result_nose_avt);
        ImageView result_lip= (ImageView) activity.findViewById(R.id.result_lip_avt);
        ImageView result_eye= (ImageView) activity.findViewById(R.id.result_eye_avt);
        ImageView result_eyebrow= (ImageView) activity.findViewById(R.id.result_eyebrow_avt);
        ImageView result_dress= (ImageView) activity.findViewById(R.id.result_dress_avt);
        ImageView result_neckless= (ImageView) activity.findViewById(R.id.result_neckless_avt);
        ImageView result_beard= (ImageView) activity.findViewById(R.id.result_beard_avt);
        ImageView result_earing= (ImageView) activity.findViewById(R.id.result_earing_avt);
        ImageView result_glasses= (ImageView) activity.findViewById(R.id.result_glasses_avt);
        ImageView result_hair_front= (ImageView) activity.findViewById(R.id.result_hair_front_avt);
        ImageView result_hat= (ImageView) activity.findViewById(R.id.result_hat_avt);


        String file_name=getSexAge()+"_face_"+getFace();
        int file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_face.setImageResource(file_id);

        file_name=getSexAge()+"_nose_"+getNose();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_nose.setImageResource(file_id);

        file_name=getSexAge()+"_lip_"+getLip();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_lip.setImageResource(file_id);

        file_name=getSexAge()+"_eye_"+getEye();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eye.setImageResource(file_id);

        file_name=getSexAge()+"_eyebrow_"+getEyebrow();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eyebrow.setImageResource(file_id);

        if(getGlasses()>0){
            file_name=getSexAge()+"_glasses_"+getGlasses();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_glasses.setImageResource(file_id);
            result_glasses.setVisibility(View.VISIBLE);
        }else{
            result_glasses.setVisibility(View.GONE);
        }

        if(getHair()>0){
            //front
            file_name=getSexAge()+"_hair_"+getHair();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hair_front.setImageResource(file_id);
            result_hair_front.setVisibility(View.VISIBLE);

            //back
            result_hair_back.setVisibility(View.GONE);
            file_id = activity.getResources().getIdentifier(sexAge+"_hair_"+getHair()+"_back", "drawable", activity.getPackageName());
            if(file_id!=0){
                result_hair_back.setImageResource(file_id);
                result_hair_back.setVisibility(View.VISIBLE);
            }
        }else{
            result_hair_front.setVisibility(View.GONE);
            result_hair_back.setVisibility(View.GONE);
        }

        if(getNeckless()>0){
            file_name=getSexAge()+"_neckless_"+getNeckless();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_neckless.setImageResource(file_id);
            result_neckless.setVisibility(View.VISIBLE);
        }else{
            result_neckless.setVisibility(View.GONE);
        }

        if(getEaring()>0){
            file_name=getSexAge()+"_earing_"+getEaring();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_earing.setImageResource(file_id);
            result_earing.setVisibility(View.VISIBLE);
        }else{
            result_earing.setVisibility(View.GONE);
        }

        if(getHat()>0){
            file_name=getSexAge()+"_hat_"+getHat();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hat.setImageResource(file_id);
            result_hat.setVisibility(View.VISIBLE);
        }else{
            result_hat.setVisibility(View.GONE);
        }

        if(getDress()>0){
            file_name=getSexAge()+"_dress_"+getDress();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_dress.setImageResource(file_id);
            result_dress.setVisibility(View.VISIBLE);
        }else{
            result_dress.setVisibility(View.GONE);
        }

        if(getBeard()>0){
            file_name=getSexAge()+"_beard_"+getBeard();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_beard.setImageResource(file_id);
            result_beard.setVisibility(View.VISIBLE);
        }else{
            result_beard.setVisibility(View.GONE);
        }
    }

    public void setAvatar_opt(Activity activity,Dialog dialog) {
        ImageView result_hair_back= (ImageView) dialog.findViewById(R.id.result_hair_back_op_avt);
        ImageView result_face= (ImageView) dialog.findViewById(R.id.result_face_op_avt);
        ImageView result_nose= (ImageView) dialog.findViewById(R.id.result_nose_op_avt);
        ImageView result_lip= (ImageView) dialog.findViewById(R.id.result_lip_op_avt);
        ImageView result_eye= (ImageView) dialog.findViewById(R.id.result_eye_op_avt);
        ImageView result_eyebrow= (ImageView) dialog.findViewById(R.id.result_eyebrow_op_avt);
        ImageView result_dress= (ImageView) dialog.findViewById(R.id.result_dress_op_avt);
        ImageView result_neckless= (ImageView) dialog.findViewById(R.id.result_neckless_op_avt);
        ImageView result_beard= (ImageView) dialog.findViewById(R.id.result_beard_op_avt);
        ImageView result_earing= (ImageView) dialog.findViewById(R.id.result_earing_op_avt);
        ImageView result_glasses= (ImageView) dialog.findViewById(R.id.result_glasses_op_avt);
        ImageView result_hair_front= (ImageView) dialog.findViewById(R.id.result_hair_front_op_avt);
        ImageView result_hat= (ImageView) dialog.findViewById(R.id.result_hat_op_avt);

        String file_name=getSexAge()+"_face_"+getFace();
        int file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_face.setImageResource(file_id);

        file_name=getSexAge()+"_nose_"+getNose();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_nose.setImageResource(file_id);

        file_name=getSexAge()+"_lip_"+getLip();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_lip.setImageResource(file_id);

        file_name=getSexAge()+"_eye_"+getEye();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eye.setImageResource(file_id);

        file_name=getSexAge()+"_eyebrow_"+getEyebrow();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eyebrow.setImageResource(file_id);

        if(getGlasses()>0){
            file_name=getSexAge()+"_glasses_"+getGlasses();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_glasses.setImageResource(file_id);
            result_glasses.setVisibility(View.VISIBLE);
        }else{
            result_glasses.setVisibility(View.GONE);
        }

        if(getHair()>0){
            //front
            file_name=getSexAge()+"_hair_"+getHair();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hair_front.setImageResource(file_id);
            result_hair_front.setVisibility(View.VISIBLE);

            //back
            result_hair_back.setVisibility(View.GONE);
            file_id = activity.getResources().getIdentifier(sexAge+"_hair_"+getHair()+"_back", "drawable", activity.getPackageName());
            if(file_id!=0){
                result_hair_back.setImageResource(file_id);
                result_hair_back.setVisibility(View.VISIBLE);
            }
        }else{
            result_hair_front.setVisibility(View.GONE);
            result_hair_back.setVisibility(View.GONE);
        }

        if(getNeckless()>0){
            file_name=getSexAge()+"_neckless_"+getNeckless();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_neckless.setImageResource(file_id);
            result_neckless.setVisibility(View.VISIBLE);
        }else{
            result_neckless.setVisibility(View.GONE);
        }

        if(getEaring()>0){
            file_name=getSexAge()+"_earing_"+getEaring();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_earing.setImageResource(file_id);
            result_earing.setVisibility(View.VISIBLE);
        }else{
            result_earing.setVisibility(View.GONE);
        }

        if(getHat()>0){
            file_name=getSexAge()+"_hat_"+getHat();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hat.setImageResource(file_id);
            result_hat.setVisibility(View.VISIBLE);
        }else{
            result_hat.setVisibility(View.GONE);
        }

        if(getDress()>0){
            file_name=getSexAge()+"_dress_"+getDress();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_dress.setImageResource(file_id);
            result_dress.setVisibility(View.VISIBLE);
        }else{
            result_dress.setVisibility(View.GONE);
        }

        if(getBeard()>0){
            file_name=getSexAge()+"_beard_"+getBeard();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_beard.setImageResource(file_id);
            result_beard.setVisibility(View.VISIBLE);
        }else{
            result_beard.setVisibility(View.GONE);
        }
    }
    public void setAvatar_opt(Activity activity){
        ImageView result_hair_back= (ImageView) activity.findViewById(R.id.result_hair_back_op_avt);
        ImageView result_face= (ImageView) activity.findViewById(R.id.result_face_op_avt);
        ImageView result_nose= (ImageView) activity.findViewById(R.id.result_nose_op_avt);
        ImageView result_lip= (ImageView) activity.findViewById(R.id.result_lip_op_avt);
        ImageView result_eye= (ImageView) activity.findViewById(R.id.result_eye_op_avt);
        ImageView result_eyebrow= (ImageView) activity.findViewById(R.id.result_eyebrow_op_avt);
        ImageView result_dress= (ImageView) activity.findViewById(R.id.result_dress_op_avt);
        ImageView result_neckless= (ImageView) activity.findViewById(R.id.result_neckless_op_avt);
        ImageView result_beard= (ImageView) activity.findViewById(R.id.result_beard_op_avt);
        ImageView result_earing= (ImageView) activity.findViewById(R.id.result_earing_op_avt);
        ImageView result_glasses= (ImageView) activity.findViewById(R.id.result_glasses_op_avt);
        ImageView result_hair_front= (ImageView) activity.findViewById(R.id.result_hair_front_op_avt);
        ImageView result_hat= (ImageView) activity.findViewById(R.id.result_hat_op_avt);

        String file_name=getSexAge()+"_face_"+getFace();
        int file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_face.setImageResource(file_id);

        file_name=getSexAge()+"_nose_"+getNose();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_nose.setImageResource(file_id);

        file_name=getSexAge()+"_lip_"+getLip();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_lip.setImageResource(file_id);

        file_name=getSexAge()+"_eye_"+getEye();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eye.setImageResource(file_id);

        file_name=getSexAge()+"_eyebrow_"+getEyebrow();
        file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
        result_eyebrow.setImageResource(file_id);

        if(getGlasses()>0){
            file_name=getSexAge()+"_glasses_"+getGlasses();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_glasses.setImageResource(file_id);
            result_glasses.setVisibility(View.VISIBLE);
        }else{
            result_glasses.setVisibility(View.GONE);
        }

        if(getHair()>0){
            //front
            file_name=getSexAge()+"_hair_"+getHair();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hair_front.setImageResource(file_id);
            result_hair_front.setVisibility(View.VISIBLE);

            //back
            result_hair_back.setVisibility(View.GONE);
            file_id = activity.getResources().getIdentifier(sexAge+"_hair_"+getHair()+"_back", "drawable", activity.getPackageName());
            if(file_id!=0){
                result_hair_back.setImageResource(file_id);
                result_hair_back.setVisibility(View.VISIBLE);
            }
        }else{
            result_hair_front.setVisibility(View.GONE);
            result_hair_back.setVisibility(View.GONE);
        }

        if(getNeckless()>0){
            file_name=getSexAge()+"_neckless_"+getNeckless();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_neckless.setImageResource(file_id);
            result_neckless.setVisibility(View.VISIBLE);
        }else{
            result_neckless.setVisibility(View.GONE);
        }

        if(getEaring()>0){
            file_name=getSexAge()+"_earing_"+getEaring();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_earing.setImageResource(file_id);
            result_earing.setVisibility(View.VISIBLE);
        }else{
            result_earing.setVisibility(View.GONE);
        }

        if(getHat()>0){
            file_name=getSexAge()+"_hat_"+getHat();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_hat.setImageResource(file_id);
            result_hat.setVisibility(View.VISIBLE);
        }else{
            result_hat.setVisibility(View.GONE);
        }

        if(getDress()>0){
            file_name=getSexAge()+"_dress_"+getDress();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_dress.setImageResource(file_id);
            result_dress.setVisibility(View.VISIBLE);
        }else{
            result_dress.setVisibility(View.GONE);
        }

        if(getBeard()>0){
            file_name=getSexAge()+"_beard_"+getBeard();
            file_id = activity.getResources().getIdentifier(file_name, "drawable", activity.getPackageName());
            result_beard.setImageResource(file_id);
            result_beard.setVisibility(View.VISIBLE);
        }else{
            result_beard.setVisibility(View.GONE);
        }
    }

    public String makeString(){
        //sexAge#face#nose#lip#eye#eyebrow#hair#dress#neckless#beard#earing#glasses#hat
        //ex: m#1#1#1#1#1#0#0#0#0#0#0#0
        return this.sexAge+"#"+this.face+"#"+this.nose+"#"+this.lip+"#"+this.eye+"#"+this.eyebrow+"#"+this.hair+"#"+this.dress+"#"+this.neckless+"#"+this.beard+"#"+this.earing+"#"+this.glasses+"#"+this.hat;
    }

    public void getRandomAvatar(String gender){

        if(gender==null){
            int r=new Random().nextInt(2);
            if(r==1)
                this.sexAge="f";
            else
                this.sexAge="m";
        }

        int has_glasses=new Random().nextInt(7);
        int has_bread_m=new Random().nextInt(20);
        int has_bread_f=new Random().nextInt(50);

        if(this.sexAge.equals("m")){
            if(max_m_bodyPart[0]>1){int r=new Random().nextInt((max_m_bodyPart[0]-1))+1; this.setFace(r);  this.setNose(r);}else {this.setFace(1); this.setNose(1);}
//            if(max_m_bodyPart[1]>1)this.setNose(new Random().nextInt((max_m_bodyPart[1]-1))+1);else this.setNose(1);
            if(max_m_bodyPart[2]>1)this.setLip(new Random().nextInt((max_m_bodyPart[2]-1))+1);else this.setLip(1);
            if(max_m_bodyPart[3]>1)this.setEye(new Random().nextInt((max_m_bodyPart[3]-1))+1);else this.setEye(1);
            if(max_m_bodyPart[4]>1)this.setEyebrow(new Random().nextInt((max_m_bodyPart[4]-1))+1);else this.setEyebrow(1);
            if(max_m_bodyPart[5]>0)this.setHair(new Random().nextInt(max_m_bodyPart[5]));else this.setHair(0);
            if(max_m_bodyPart[6]>0)this.setDress(new Random().nextInt(max_m_bodyPart[6]));else this.setDress(0);
            if(max_m_bodyPart[7]>0)this.setNeckless(new Random().nextInt(max_m_bodyPart[7]));else this.setNeckless(0);
            if(max_m_bodyPart[8]>0)this.setBeard(new Random().nextInt(max_m_bodyPart[8]));else this.setBeard(0);
            if(has_bread_m>6) this.setBeard(0);
            if(max_m_bodyPart[9]>0)this.setEaring(new Random().nextInt(max_m_bodyPart[9]));else this.setEaring(9);
            if(max_m_bodyPart[10]>0)this.setGlasses(new Random().nextInt(max_m_bodyPart[10]));else this.setGlasses(0);
            if(has_glasses>2) this.setGlasses(0);
            if(max_m_bodyPart[11]>0)this.setHat(new Random().nextInt(max_m_bodyPart[11]));else this.setHat(0);
        }else{
            if(max_f_bodyPart[0]>1){int r=new Random().nextInt((max_f_bodyPart[0]-1))+1; this.setFace(r);  this.setNose(r);}else {this.setFace(1); this.setNose(1);}
//            if(max_f_bodyPart[1]>1)this.setNose(new Random().nextInt((max_f_bodyPart[1]-1))+1);else this.setNose(1);
            if(max_f_bodyPart[2]>1)this.setLip(new Random().nextInt((max_f_bodyPart[2]-1))+1);else this.setLip(1);
            if(max_f_bodyPart[3]>1)this.setEye(new Random().nextInt((max_f_bodyPart[3]-1))+1);else this.setEye(1);
            if(max_f_bodyPart[4]>1)this.setEyebrow(new Random().nextInt((max_f_bodyPart[4]-1))+1);else this.setEyebrow(1);
            if(max_f_bodyPart[5]>0)this.setHair(new Random().nextInt(max_f_bodyPart[5]));else this.setHair(0);
            if(max_f_bodyPart[6]>0)this.setDress(new Random().nextInt(max_f_bodyPart[6]));else this.setDress(0);
            if(max_f_bodyPart[7]>0)this.setNeckless(new Random().nextInt(max_f_bodyPart[7]));else this.setNeckless(0);
            if(max_f_bodyPart[8]>0)this.setBeard(new Random().nextInt(max_f_bodyPart[8]));else this.setBeard(0);
            if(has_bread_f>6) this.setBeard(0);
            if(max_f_bodyPart[9]>0)this.setEaring(new Random().nextInt(max_f_bodyPart[9]));else this.setEaring(9);
            if(max_f_bodyPart[10]>0)this.setGlasses(new Random().nextInt(max_f_bodyPart[10]));else this.setGlasses(0);
            if(has_glasses>2) this.setGlasses(0);
            if(max_f_bodyPart[11]>0)this.setHat(new Random().nextInt(max_f_bodyPart[11]));else this.setHat(0);
        }
    }

    private String sexAge;
    private int face;
    private int nose;
    private int lip;
    private int eye;
    private int eyebrow;
    private int hair;
    private int dress;
    private int neckless;
    private int beard;
    private int earing;
    private int glasses;
    private int hat;


    public Avatar(String sexAge, int face, int nose, int lip, int eye, int eyebrow, int hair, int dress, int neckless, int beard, int earing, int glasses, int hat) {
        this.sexAge = sexAge;
        this.face = face;
        this.nose = nose;
        this.lip = lip;
        this.eye = eye;
        this.eyebrow = eyebrow;
        this.hair = hair;
        this.dress = dress;
        this.neckless = neckless;
        this.beard = beard;
        this.earing = earing;
        this.glasses = glasses;
        this.hat = hat;
    }

    public Avatar(String serverString) {

        //sexAge#face#nose#lip#eye#eyebrow#hair#dress#neckless#beard#earing#glasses#hat
        //ex: m#1#1#1#1#1#0#0#0#0#0#0#0
        String[] temp=serverString.split("#");

        this.sexAge = temp[0];
        this.face = new Integer(temp[1]);
        this.nose = new Integer(temp[2]);
        this.lip = new Integer(temp[3]);
        this.eye = new Integer(temp[4]);
        this.eyebrow = new Integer(temp[5]);
        this.hair = new Integer(temp[6]);
        this.dress = new Integer(temp[7]);
        this.neckless = new Integer(temp[8]);
        this.beard = new Integer(temp[9]);
        this.earing = new Integer(temp[10]);
        this.glasses = new Integer(temp[11]);
        this.hat = new Integer(temp[12]);
    }
    public Avatar() {
    }

    public String getSexAge() {
        return sexAge;
    }

    public void setSexAge(String sexAge) {
        this.sexAge = sexAge;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public int getNose() {
        return nose;
    }

    public void setNose(int nose) {
        this.nose = nose;
    }

    public int getLip() {
        return lip;
    }

    public void setLip(int lip) {
        this.lip = lip;
    }

    public int getEye() {
        return eye;
    }

    public void setEye(int eye) {
        this.eye = eye;
    }

    public int getEyebrow() {
        return eyebrow;
    }

    public void setEyebrow(int eyebrow) {
        this.eyebrow = eyebrow;
    }

    public int getHair() {
        return hair;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public int getDress() {
        return dress;
    }

    public void setDress(int dress) {
        this.dress = dress;
    }

    public int getNeckless() {
        return neckless;
    }

    public void setNeckless(int neckless) {
        this.neckless = neckless;
    }

    public int getBeard() {
        return beard;
    }

    public void setBeard(int beard) {
        this.beard = beard;
    }

    public int getEaring() {
        return earing;
    }

    public void setEaring(int earing) {
        this.earing = earing;
    }

    public int getGlasses() {
        return glasses;
    }

    public void setGlasses(int glasses) {
        this.glasses = glasses;
    }

    public int getHat() {
        return hat;
    }

    public void setHat(int hat) {
        this.hat = hat;
    }

}
