package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.interfaces.MatchInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class MatchModel implements MatchInterface {

    public static Integer IN_PROGRESS = 0;
    public static Integer WIN = 1;
    public static Integer LOST = 2;
    public static Integer EQUAL = 3;

//    public static String BET_500 = "l";
//    public static String BET_700 = "m";
//    public static String BET_1000 = "h";
//    public static String BET_LUCK_10 = "u";
//    public static String BET_NITRO = "n";


    public static ArrayList<BetModel> bets;

    public static String DEFAULT_TOTAL_TIME = "300";

    // meysam: must be 9
    public static int MATCH_DURATION_MINUTES = 9;//must be 9...meysam

    // meysam: must be 59
    public static int MATCH_DURATION_SECONDS = 59;//must be 59...meysam

    // meysam: must be 5
    public static int TIME_IN_SECONDS_LIMIT = 5;//must be 5...meysam

    // meysam: must be 10
    public static int TIME_WAIT_BEFORE_END = 5;//must be 5...meysam


    protected MatchModel(Parcel in) {
        Guid = in.readString();
        SelfCorrectCount = in.readString();
        OpponentCorrectCount = in.readString();
        SelfSpentTime = in.readString();
        OpponentSpentTime = in.readString();
        Bet = in.readString();
        Questions =  in.readArrayList(QuestionModel.class.getClassLoader());
        Opponent =  (UserModel) in.readValue(UserModel.class.getClassLoader());
        IsEnded = (Boolean) in.readValue(Boolean.class.getClassLoader());
        Winner = Integer.valueOf((int)in.readLong());
        Result = Integer.valueOf((int)in.readLong());
        Id = BigInteger.valueOf((int)in.readLong());


    }

    public UserModel getOpponent() {
        return Opponent;
    }

    public void setOpponent(UserModel opponent) {
        Opponent = opponent;
    }

    public ArrayList<QuestionModel> getQuestions() {
        return Questions;
    }

    public void setQuestions(ArrayList<QuestionModel> questions) {
        Questions = questions;
    }

    public String getSelfCorrectCount() {
        return SelfCorrectCount;
    }

    public void setSelfCorrectCount(String selfCorrectCount) {
        SelfCorrectCount = selfCorrectCount;
    }

    public String getOpponentCorrectCount() {
        return OpponentCorrectCount;
    }

    public void setOpponentCorrectCount(String opponentCorrectCount) {
        OpponentCorrectCount = opponentCorrectCount;
    }


    public String getSelfSpentTime() {
        return SelfSpentTime;
    }

    public void setSelfSpentTime(String selfSpentTime) {
        SelfSpentTime = selfSpentTime;
    }

    public String getOpponentSpentTime() {
        return OpponentSpentTime;
    }

    public void setOpponentSpentTime(String opponentSpentTime) {
        OpponentSpentTime = opponentSpentTime;
    }

    public String getBet() {
        return Bet;
    }

    public void setBet(String bet) {
        Bet = bet;
    }

    public Boolean getEnded() {
        return IsEnded;
    }

    public void setEnded(Boolean ended) {
        IsEnded = ended;
    }

    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }
    public Integer getWinner() {
        return Winner;
    }

    public void setWinner(Integer winner) {
        Winner = winner;
    }

    public Integer getResult() {
        return Result;
    }

    public void setResult(Integer result) {
        Result = result;
    }

    public Boolean getNitro() {
        return IsNitro;
    }

    public void setNitro(Boolean nitro) {
        IsNitro = nitro;
    }

    public Boolean getRewardLuck() {
        return IsRewardLuck;
    }

    public void setRewardLuck(Boolean rewardLuck) {
        IsRewardLuck = rewardLuck;
    }

    public BetModel getMatchBet() {
        return MatchBet;
    }

    public void setMatchBet(BetModel matchBet) {
        MatchBet = matchBet;
    }

    public CrossTableModel getCrossTable() {
        return CrossTable;
    }

    public void setCrossTable(CrossTableModel crossTable) {
        CrossTable = crossTable;
    }



    private BigInteger Id;
    private String Guid;
    private UserModel Opponent;
    private ArrayList<QuestionModel> Questions;
    private String SelfCorrectCount;
    private String OpponentCorrectCount;
    private String SelfSpentTime;
    private String OpponentSpentTime;
    private String Bet;
    private Boolean IsEnded;
    private Boolean IsNitro;
    private Boolean IsRewardLuck;

    private CrossTableModel CrossTable;

    private BetModel MatchBet;

    private Integer Winner;
    private Integer Result;


    public MatchModel()
    {

        Id = null;
        Guid = null;
        Opponent = null;
        Questions = null;
        SelfCorrectCount = null;
        SelfSpentTime = null;
        OpponentCorrectCount = null;
        OpponentSpentTime = null;
        Winner = null;
        Result = null;
        IsNitro = null;
        IsRewardLuck = null;
        MatchBet = null;
        CrossTable = null;

    }

    public MatchModel(Context cntx)
    {
        Id = null;
        Guid = null;
        Opponent = null;
        Questions = null;
        SelfCorrectCount = null;
        SelfSpentTime = null;
        OpponentCorrectCount = null;
        OpponentSpentTime = null;
        Winner = null;
        Result = null;
        IsNitro = null;
        IsRewardLuck = null;
        MatchBet = null;
        CrossTable = null;

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

//    public static String getBetChar(String amount)
//    {
//        if(amount.equals("500"))
//            return BET_500;
//
//        if(amount.equals("700"))
//            return BET_700;
//
//        if(amount.equals("1000"))
//            return BET_1000;
//
//        return BET_500;
//    }

    public static Integer countCorrectAnswers(ArrayList<QuestionModel> questions)
    {
        Integer result = 0;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getCorrect() == true)
                result++;
        }

        return result;
    }

    public static Integer calculateSpentTime(Integer minute, Integer seconds, Integer totalTime)
    {
        Integer result = totalTime;
        result = result - (minute * 60);
        result = result - seconds;
        return result;
    }

    public JSONArray generateJsonArrayResult() throws JSONException {
        JSONArray j_questions = new JSONArray();
        JSONObject j_question;
        for(int i = 0; i < this.getQuestions().size(); i++)
        {
            j_question = new JSONObject();
            j_question.put("is_correct", this.getQuestions().get(i).getCorrect() == true?1:0);
            j_question.put("category_id", this.getQuestions().get(i).getCategoryId());

            j_questions.put(j_question);
        }

        return j_questions;
    }

    public static Integer getRewardByID(String betId, Integer status)
    {
        Integer result = 0;

        for (int i = 0; i < MatchModel.bets.size(); i++)
        {
            if(MatchModel.bets.get(i).getBetId().equals(betId))
            {
                if(status.equals(MatchModel.EQUAL))
                {
                    result = MatchModel.bets.get(i).getAmount() - ((MatchModel.bets.get(i).getAmount()* MatchModel.bets.get(i).getInterest())/100);

                }
                if(status.equals(MatchModel.WIN))
                {
                    result = (2*MatchModel.bets.get(i).getAmount()) - ((2*MatchModel.bets.get(i).getAmount()* MatchModel.bets.get(i).getInterest())/100);
                }
            }
        }
        return result;
    }



    public static void setMinSec(String betId)
    {

        //
        for (int i = 0; i < MatchModel.bets.size(); i++)
        {
            if(MatchModel.bets.get(i).getBetId().equals(betId))
            {
                int remainder = MatchModel.bets.get(i).getTime();
                MatchModel.MATCH_DURATION_MINUTES = remainder / 60;
                remainder = remainder - MatchModel.MATCH_DURATION_MINUTES * 60;
                MatchModel.MATCH_DURATION_SECONDS = remainder;
            }
        }
        ///////////////////////////////////////


    }

    public static void addMoreTimeToMatch(Integer seconds)
    {

        Integer addedMins = seconds / 60;
        Integer addedsecs = seconds - addedMins * 60;
        MatchModel.MATCH_DURATION_MINUTES += addedMins;
        MatchModel.MATCH_DURATION_SECONDS +=addedsecs;

    }

    public static Boolean isSpentTimeWithinPersent(Integer minute, Integer seconds, Integer persent, Integer totalTime)
    {
        Integer result = totalTime;
        result = result - (minute * 60);
        result = result - seconds;
        if(result <= (totalTime*(float)persent/100))
            return true;
        return false;
    }

//    public static Integer getMatchTime(Context cntx)
//    {
//        Integer result = 0;
//        SessionModel session = new SessionModel(cntx);
//        String betId =  session.getStringItem(SessionModel.KEY_BET_ID);
//        for(int i = 0; i < MatchModel.bets.size(); i++)
//        {
//            if(MatchModel.bets.get(i).getBetId().equals(betId))
//            {
//                result = MatchModel.bets.get(i).getTime();
//                break;
//            }
//        }
//        return result;
//    }

    public static Integer getCurrentMatchTime(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        return session.getIntegerItem(SessionModel.KEY_BET_TIME);
    }
    public static void setCurrentMatchTime(Context cntx, Integer matchTime)
    {
        SessionModel session = new SessionModel(cntx);
        session.saveItem(SessionModel.KEY_BET_TIME, matchTime);
    }
    public static Integer getCurrentMatchBet(Context cntx)
    {
        Integer result = 0;
        SessionModel session = new SessionModel(cntx);
        String bet_id = session.getStringItem(SessionModel.KEY_BET_ID);
        for(int i=0; i< MatchModel.bets.size();i++)
        {
            if(MatchModel.bets.get(i).getBetId().equals(bet_id)) {
                result = MatchModel.bets.get(i).getAmount();
            }

        }
        return result;
    }

    public static boolean isNitro(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        String bet_id = session.getStringItem(SessionModel.KEY_BET_ID);
        for(int i=0; i< MatchModel.bets.size();i++)
        {
            if(MatchModel.bets.get(i).getBetId().equals(bet_id)) {
                if(MatchModel.bets.get(i).getRewardTime()>0)
                    return  true;
            }

        }
        return false;
    }

    public  void initializeBet(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        String bet_id = session.getStringItem(SessionModel.KEY_BET_ID);
        for(int i=0; i< MatchModel.bets.size();i++)
        {
            if(MatchModel.bets.get(i).getBetId().equals(bet_id)) {
                this.setMatchBet(MatchModel.bets.get(i));
                break;

            }
        }
    }

    public static MatchModel findMatchById(ArrayList<MatchModel> matches, BigInteger matchId)
    {
        for(int i=0; i<matches.size();i++)
        {
            if(matches.get(i).getId() != null)
                if(matches.get(i).getId().equals(matchId))
                {
                    return matches.get(i);
                }
        }
        return null;
    }
}