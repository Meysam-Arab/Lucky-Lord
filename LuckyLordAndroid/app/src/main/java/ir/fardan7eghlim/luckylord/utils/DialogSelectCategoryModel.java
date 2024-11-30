package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.views.hazels.HazelCategoriesActivity;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogSelectCategoryModel {
    public static Dialog dialog;
    private static ImageView iv_1;
    private static ImageView iv_2;
    private static ImageView iv_3;


    public static void show(final Context context, final String miv_1, final String miv_2, final String miv_3, final Boolean externalAccess, boolean cancelOnOutsideTouch, String action){


        final String categoryId = String.valueOf(CategoryModel.RANDOM);
        final Intent intent = new Intent(action);
        // You can also include some extra data.


        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.select_category);

        dialog.setCanceledOnTouchOutside(cancelOnOutsideTouch);
        dialog.setCancelable(cancelOnOutsideTouch);

        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //When you touch outside of dialog bounds,
                        //the dialog gets canceled and this method executes.

                        hide();
                    }
                }
        );

        iv_1 = (ImageView) dialog.findViewById(R.id.select_cat_01);
        iv_2 = (ImageView) dialog.findViewById(R.id.select_cat_02);
        iv_3 = (ImageView) dialog.findViewById(R.id.select_cat_03);


        iv_1.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(miv_1, context));
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("category_id", miv_1);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                dialog.hide();
                dialog.dismiss();

                if(!externalAccess)
                {
                    hide();
                }

//                HazelCategoriesActivity.categoryId = miv_1;
//                dialog.dismiss();

            }
        });

        iv_2.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(miv_2, context));
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("category_id", miv_2);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                dialog.hide();
                dialog.dismiss();

                if(!externalAccess)
                {
                    hide();
                }

//                HazelCategoriesActivity.categoryId = miv_2;
//                dialog.dismiss();

            }
        });

        iv_3.setImageBitmap(CategoryModel.getImageBitmapByCategoryId(miv_3, context));
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("category_id", miv_3);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                dialog.hide();
                dialog.dismiss();

                if(!externalAccess)
                {
                    hide();

                }

//                HazelCategoriesActivity.categoryId = miv_3;
//                dialog.dismiss();

            }
        });


        dialog.show();
    }
    public static void hide(){
        if(dialog != null)
        {
            dialog.dismiss();
            dialog=null;
        }

        clearResources();
    }

    public static void clearResources()
    {
        iv_1 = null;
        iv_2 = null;
        iv_3 = null;
    }

}
