package ir.fardan7eghlim.luckylord.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.CategoryModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;


/**
 * Created by Amir on 5/7/2017.
 */

public class DialogSelectBetModel {
    public static Dialog dialog;
    private static RecyclerView recyclerView;//meysam - added in 13960608
    private static LuckyLordRecyclerViewAdapter mAdapter;//meysam - added in 13960608


    public static void show(final Context context, final Boolean externalAccess, boolean cancelOnOutsideTouch, String action){

        dialog= new Dialog(context);


        dialog.setCanceledOnTouchOutside(cancelOnOutsideTouch);
        dialog.setCancelable(cancelOnOutsideTouch);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_horizontal_list);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //meysam - create recycler list
        recyclerView = (RecyclerView) dialog.findViewById(R.id.rv_list);

        //meysam - commented in 13961001
//        mAdapter = new LuckyLordRecyclerViewAdapter(MatchModel.bets, getApplicationContext());

        //meysam - added in 13961001
        mAdapter = new LuckyLordRecyclerViewAdapter(UniversalMatchModel.bets, context, action);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL));
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                LuckyLordRecyclerViewAdapter.clear(holder);
            }

        });
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

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

        dialog.show();
    }
    public static void hide(){
        if(dialog != null)
        {
            dialog.dismiss();
            dialog=null;

        }
        if(mAdapter != null)
        {
            mAdapter = null;
        }
        if(recyclerView != null)
        {
            recyclerView.setAdapter(null);
            recyclerView = null;
        }
    }

}
