package ir.fardan7eghlim.luckylord.utils;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import io.supercharge.shimmerlayout.ShimmerLayout;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.views.home.MainActivity;


/**
 * Created by Meysam on 13960428.
 */

public class DialogModel_level {
    private Dialog pdL;
    private Context context;
    private CircleProgressView pdl_mCircleView;

    public DialogModel_level(Context c) {
        context=c;
        pdL= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pdL.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pdL.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdL.setContentView(R.layout.added_level);
    }

    public void show(final int beforeLevel, final int currentLevel){
        ImageView sun_effect= (ImageView) pdL.findViewById(R.id.added_level_img_sunShine);
        sun_effect.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.rotate_and_scale_indefinitely));

        final TextView added_hazel_amount= (TextView) pdL.findViewById(R.id.added_level_amount);
        added_hazel_amount.setText(Utility.enToFa(beforeLevel+""));

        pdl_mCircleView = (CircleProgressView) pdL.findViewById(R.id.levelDialog_circleView);
        pdl_mCircleView.setTextSize(1);
        pdl_mCircleView.setUnitVisible(false);

        pdL.show();
        pdl_mCircleView.setValueAnimated(0,100,1200);

        final ShimmerLayout shimmer_text_pdL = (ShimmerLayout) pdL.findViewById(R.id.shimmer_text_pdL);
        shimmer_text_pdL.setShimmerColor(Color.parseColor("#9ad2d6"));

        pdl_mCircleView.setOnAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                                shimmer_text_pdL.startShimmerAnimation();
                                added_hazel_amount.setText(Utility.enToFa(currentLevel+""));
                                break;
                            case ANIMATING:
                                added_hazel_amount.setText(Utility.enToFa(beforeLevel+""));
                                break;
                            case START_ANIMATING_AFTER_SPINNING:
                                break;
                            case SPINNING:
                                break;
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;
                        }
                    }
                }
        );

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pdL != null)
                 if(pdL.isShowing())
                    pdL.dismiss();
            }
        }, 5000);
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
