package ir.fardan7eghlim.luckylord.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Amir on 5/27/2017.
 */

public class Anims {
    public static void cloudMotion(ImageView img_animation, float fromX, float toX, int speed){
        TranslateAnimation animation = new TranslateAnimation(fromX, toX,
                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(speed);  // animation duration
        animation.setRepeatCount(999999);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);  // start animation
    }
    public static void move(ImageView img_animation, float fromX, float toX,float fromY, float toY, int speed,int count){
        TranslateAnimation animation = new TranslateAnimation(fromX, toX,
                fromY, toY);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(speed);  // animation duration
        animation.setRepeatCount(count);  // animation repeat count
        animation.setRepeatMode(count==0?0:1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);  // start animation
    }
    public static void move(FrameLayout img_animation, float fromX, float toX, float fromY, float toY, int speed, int count){
        TranslateAnimation animation = new TranslateAnimation(fromX, toX,
                fromY, toY);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(speed);  // animation duration
        animation.setRepeatCount(count);  // animation repeat count
        animation.setRepeatMode(count==0?0:1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);  // start animation
    }
    public static void move(LinearLayout img_animation, float fromX, float toX, float fromY, float toY, int speed, int count){
        TranslateAnimation animation = new TranslateAnimation(fromX, toX,
                fromY, toY);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(speed);  // animation duration
        animation.setRepeatCount(count);  // animation repeat count
        animation.setRepeatMode(count==0?0:1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);  // start animation
    }
    //guide window
    public static void move_guideWindow(LinearLayout img_animation, float fromX, float toX, float fromY, float toY, int speed, int count){
        TranslateAnimation animation = new TranslateAnimation(fromX, toX,
                fromY, toY);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(speed);  // animation duration
        animation.setRepeatCount(count);  // animation repeat count
        animation.setRepeatMode(count==0?0:1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);  // start animation
    }
    public static void scaleView(FrameLayout v, float startXScale, float endXScale, float startYScale, float endYScale) {
        Animation anim = new ScaleAnimation(
                startXScale, endXScale, // Start and end values for the X axis scaling
                startYScale, endYScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        v.startAnimation(anim);
    }
    public static void show_guideWin(FrameLayout v,float endYScale) {
//        scaleView(v,0,1,0, (float) 0.1);
        scaleView(v,0,1,(float) 0.1, endYScale);
    }

    // To animate view slide out from left to right
    public static void slideToRight(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // To animate view slide out from right to left
    public static void slideToLeft(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from bottom to top
    public static void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
}
