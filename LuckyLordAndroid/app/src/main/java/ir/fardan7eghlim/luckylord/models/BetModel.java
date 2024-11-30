package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.math.BigInteger;

import ir.fardan7eghlim.luckylord.interfaces.BetInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class BetModel implements BetInterface {


    public String getBetId() {
        return BetId;
    }

    public void setBetId(String betId) {
        BetId = betId;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getInterest() {
        return Interest;
    }

    public void setInterest(Integer interest) {
        Interest = interest;
    }

    public Integer getTime() {
        return Time;
    }

    public void setTime(Integer time) {
        Time = time;
    }

    public Integer getRewardTime() {
        return RewardTime;
    }

    public void setRewardTime(Integer rewardTime) {
        RewardTime = rewardTime;
    }

    public Boolean getBetLuck() {
        return IsBetLuck;
    }

    public void setBetLuck(Boolean betLuck) {
        IsBetLuck = betLuck;
    }

    public Boolean getNitro() {
        return IsNitro;
    }

    public void setNitro(Boolean nitro) {
        IsNitro = nitro;
    }

    private String BetId;
    private Integer Amount;
    private Integer Interest;
    private Integer Time;

    private Boolean IsBetLuck;

    private Boolean IsNitro;

    private Integer RewardTime;

    private Context cntx;


    public BetModel()
    {
        BetId = null;
        Amount = null;
        Interest = null;
        Time = null;
        RewardTime = 0;
        IsNitro = false;
        IsBetLuck = false;

        this.cntx = null;
    }

    public BetModel(Context cntx)
    {

        BetId = null;
        Amount = null;
        Interest = null;
        Time = null;
        RewardTime = 0;
        IsNitro = false;
        IsBetLuck = false;

        this.cntx = cntx;

    }

    public void insert()
    {
        try
        {

        }
        catch (Exception ex)
        {

        }
    }
    public void update()
    {

    }
    public boolean delete(){

        return false;

    }

    public Integer getCalculatedReward()
    {
        return (2*getAmount()) - ((2*getAmount()*getInterest())/100);

    }

    public static Boolean isMatchRewardLuck(String betId)
    {
        Boolean result = false;
        for(int i = 0; i < UniversalMatchModel.bets.size(); i++)
        {
            if(betId.equals(UniversalMatchModel.bets.get(i).getBetId()))
            {
                if(UniversalMatchModel.bets.get(i).getBetLuck())
                {
                    result = true;
                    break;
                }

            }
        }

        return result;
    }

    public static BetModel getBetById(String betId)
    {

        BetModel result = null;
        for(int i = 0; i < UniversalMatchModel.bets.size(); i++)
        {
            if(betId.equals(UniversalMatchModel.bets.get(i).getBetId()))
            {
                result = UniversalMatchModel.bets.get(i);
                break;
            }
        }

        return result;
    }
}