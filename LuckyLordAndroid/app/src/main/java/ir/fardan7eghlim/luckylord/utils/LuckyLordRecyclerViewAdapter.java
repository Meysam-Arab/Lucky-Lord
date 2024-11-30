package ir.fardan7eghlim.luckylord.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Date;
import java.util.List;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.BetModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;

/**
 * Created by Meysam on 30/08/2017.
 */

public class LuckyLordRecyclerViewAdapter extends RecyclerView.Adapter<LuckyLordRecyclerViewAdapter.MyViewHolder> {

    private List<BetModel> betList;
    private String action;

    public static BetModel chosenBet;

    private Context cntx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LuckyTextView cost, reward, time;
        public ImageView image;
        public LinearLayout betRow;
        public ImageView iv_is_nitro;

        public MyViewHolder(View view) {
            super(view);
            cost = (LuckyTextView) view.findViewById(R.id.ltv_bet_cost);
            reward = (LuckyTextView) view.findViewById(R.id.ltv_bet_reward);
            time = (LuckyTextView) view.findViewById(R.id.ltv_bet_time);
            image = (ImageView) view.findViewById(R.id.iv_bet_image);
            betRow = (LinearLayout) view.findViewById(R.id.ll_bet_row);
            iv_is_nitro= (ImageView) view.findViewById(R.id.iv_is_nitro);

            chosenBet = null;

        }

    }


    public LuckyLordRecyclerViewAdapter(List<BetModel> betList, Context context, String action) {
        this.betList = betList;
        this.action = action;
        this.cntx = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bet_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BetModel bet = betList.get(position);

        if(position % 2 == 1){
            holder.betRow.setBackgroundResource(R.drawable.t_board_c2);
        }

        if(bet.getBetLuck())
        {
            holder.cost.setText(Utility.enToFa(bet.getAmount() +" "+ cntx.getString(R.string.lbl_luck)));
            holder.reward.setText(Utility.enToFa(bet.getCalculatedReward().toString())+" "+ cntx.getString(R.string.lbl_luck)+" "+ cntx.getString(R.string.lbl_reward));

        }
        else
        {
            if(bet.getAmount() > 0)
            {
                holder.cost.setText(Utility.enToFa(bet.getAmount() +" "+ cntx.getString(R.string.lbl_hazel)));
                holder.reward.setText(Utility.enToFa(bet.getCalculatedReward().toString())+" "+ cntx.getString(R.string.lbl_hazel)+" "+ cntx.getString(R.string.lbl_reward));
            }
            else
            {
                holder.cost.setText( cntx.getString(R.string.lbl_free));
                holder.reward.setText( cntx.getString(R.string.lbl_hadnnot));

            }


        }
        holder.cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - on click bet - go to loading

                onBetClickProcess(bet);
            }
        });
        holder.reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - on click bet - go to loading

                onBetClickProcess(bet);
            }
        });
        holder.time.setText(Utility.enToFa(bet.getTime().toString())+" "+ cntx.getString(R.string.lbl_time_second));
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - on click bet - go to loading

                onBetClickProcess(bet);
            }
        });
        holder.betRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - on click bet - go to loading

                onBetClickProcess(bet);
            }
        });

        // meysam - set image for nitro
        if(bet.getNitro())
        {
            holder.iv_is_nitro.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.iv_is_nitro.setVisibility(View.INVISIBLE);
        }
        //meysam - set image for luck & hazel
        if(bet.getBetLuck())
        {
            // meysam - set image for luck
            holder.image.setImageResource(R.drawable.c_luck_a);
        }
        else
        {
            holder.image.setImageResource(R.drawable.c_coin_a);
        }



    }

    @Override
    public int getItemCount() {
        return betList.size();
    }

    public static void clear(RecyclerView.ViewHolder rowView){

        ImageView image = (ImageView) rowView.itemView.findViewById(R.id.iv_bet_image);
        image.setImageBitmap(null);

    }

    private void onBetClickProcess(BetModel bet)
    {
        chosenBet = bet;
        DialogSelectBetModel.hide();
        boolean hazHL = false;
        if(bet.getBetLuck())
        {
            if(Utility.hasEnoughLuck(cntx,bet.getAmount()))
            {
                hazHL = true;
            }
            else
            {
                Intent intent = new Intent(action);
                // You can also include some extra data.
                intent.putExtra("show_not_enough_luck_dialog", "true");
                LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
            }
        }
        else
        {
            if(Utility.hasEnoughCoin(cntx,bet.getAmount()))
            {
                hazHL = true;
            }
            else
            {
                Intent intent = new Intent(action);
                // You can also include some extra data.
                intent.putExtra("show_not_enough_hazel_dialog", "true");
                LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
            }
        }

        if(hazHL){

            Random rnd = new Random(new Date().getTime());
            int x = rnd.nextInt(100);
            // meysam - for nitro bet - dialog for extended time
            if(bet.getRewardTime() != 0 && x < 100)//meysam - must be 100 - always show
            {

                Intent intent = new Intent(action);
                // You can also include some extra data.
                intent.putExtra("show_reward_chooser_dialog", "true");
                LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);

            }
            else
            {
                Intent intent = new Intent(action);
                // You can also include some extra data.
                intent.putExtra("call_category_dialog", "true");
                LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
            }
        }
    }


}
