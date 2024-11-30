package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.interfaces.MatchInterface;
import ir.fardan7eghlim.luckylord.utils.Utility;

/**
 * Created by Meysam on 12/20/2016.
 */

public class UniversalMatchModel {

    public static Integer USER_UNIVERSAL_MATCH_TYPE_QUESTION=0;
    public static Integer USER_UNIVERSAL_MATCH_TYPE_DARHAMTABLE=1;
    public static Integer USER_UNIVERSAL_MATCH_TYPE_CROSSTABLE=2;
    public static Integer USER_UNIVERSAL_MATCH_TYPE_GUESSWORD=3;
    public static Integer USER_UNIVERSAL_MATCH_TYPE_FINDWORD=4;

    public static Integer USER_UNIVERSAL_MATCH_OPPONENT_TYPE_RANDOM=1;
    public static Integer USER_UNIVERSAL_MATCH_OPPONENT_TYPE_NONRANDOM=2;
    public static Integer USER_UNIVERSAL_MATCH_OPPONENT_TYPE_FRIEND=3;
    public static Integer USER_UNIVERSAL_MATCH_OPPONENT_TYPE_ALL=-1;

    public static final int STATUS_IN_PROGRESS = 0;
    public static final int STATUS_WIN = 1;
    public static final int STATUS_LOST = 2;
    public static final int STATUS_EQUAL = 3;
    public static final int STATUS_REQUEST_SENT = 4;
    public static final int STATUS_REQUEST_RECEIVED = 5;
    public static final int STATUS_YOUR_TURN = 6;
    public static final int STATUS_OPPONENT_TURN = 7;
    public static final int STATUS_DISAPPROVED_BY_OPPONENT = 8;
    public static final int STATUS_DISAPPROVED_BY_YOU = 9;
    public static final int STATUS_REQUEST_EXPIRED = 10;


    public static final Integer USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED = 1;
    public static final Integer USER_UNIVERSAL_MATCH_ENDED_STATUS_IN_PROGRESS = 0;
    public static final Integer USER_UNIVERSAL_MATCH_ENDED_STATUS_DECLINED = -1;
    public static final Integer USER_UNIVERSAL_MATCH_ENDED_STATUS_EXPIRED = -2;

//    public static String BET_500 = "l";
//    public static String BET_700 = "m";
//    public static String BET_1000 = "h";
//    public static String BET_0 = "n";
//    public static String BET_LUCK_10 = "u";
//    public static String BET_NITRO = "n";


    public static ArrayList<BetModel> bets;

    public static String DEFAULT_TOTAL_TIME = "300";

    // meysam: must be 9
    public static int MATCH_DURATION_MINUTES = 4;//must be 4...meysam

    // meysam: must be 59
    public static int MATCH_DURATION_SECONDS = 59;//must be 59...meysam

    // meysam: must be 5
    public static int TIME_IN_SECONDS_LIMIT = 5;//must be 5...meysam

    // meysam: must be 10
    public static int TIME_WAIT_BEFORE_END = 5;//must be 10...meysam


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

    public Integer getEnded() {
        return IsEnded;
    }

    public void setEnded(Integer ended) {
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


    public ArrayList<WordModel> getWord() {
        return Word;
    }

    public void setWord(ArrayList<WordModel> word) {
        Word = word;
    }

    public ArrayList<String> getCells() {
        return Cells;
    }

    public void setCells(ArrayList<String> cells) {
        Cells = cells;
    }

    public ArrayList<String> getAnswerCells() {
        return AnswerCells;
    }

    public void setAnswerCells(ArrayList<String> answerCells) {
        AnswerCells = answerCells;
    }

    public Integer getOpponentType() {
        return OpponentType;
    }

    public void setOpponentType(Integer opponentType) {
        OpponentType = opponentType;
    }

    public Integer getMatchType() {
        return MatchType;
    }

    public void setMatchType(Integer matchType) {
        MatchType = matchType;
    }

    public Integer getMatchStatus() {
        return MatchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        MatchStatus = matchStatus;
    }

    public Context getCntx() {
        return Cntx;
    }

    public void setCntx(Context cntx) {
        Cntx = cntx;
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
    private Integer IsEnded;
    private Boolean IsNitro;
    private Boolean IsRewardLuck;
    private ArrayList<WordModel> Word;
    private ArrayList<String> Cells;
    private ArrayList<String> AnswerCells;
    private BetModel MatchBet;
    private Integer Winner;
    private Integer Result;
    private Integer MatchType;
    private Integer OpponentType;
    private Integer MatchStatus;
    private Context Cntx;
    private CrossTableModel CrossTable;



    public UniversalMatchModel()
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
        Cells = null;
        AnswerCells = null;
        Word = null;
        MatchType = null;
        OpponentType = null;
        MatchStatus = null;
        Cntx = null;
        CrossTable = null;
    }

    public UniversalMatchModel(JSONObject item, Context cntx)
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
        Cells = null;
        AnswerCells = null;
        Word = null;
        MatchType = null;
        OpponentType = null;
        MatchStatus = null;
        Cntx = cntx;
        CrossTable = null;

        try
        {
            SessionModel session = new SessionModel(cntx.getApplicationContext());
            UserModel opponent = new UserModel();

            setBet(item.getString("bet"));

            if(!item.getString("is_ended").equals("null"))
                setEnded(new Integer(item.getString("is_ended")));
            opponent.setProfilePicture(item.getString("opponent_avatar"));

            String tmo_opponent_user_name = item.getString("opponent_user_name");
            if(tmo_opponent_user_name.length()< 8)
            {
                opponent.setUserName(item.getString("opponent_user_name"));
            }
            else if(tmo_opponent_user_name.substring(0,8).equals("Visitor_"))
            {
                opponent.setUserName( UserModel.getVisitorHashedName(item.getString("opponent_user_name")));
            }
            else
            {
                opponent.setUserName(item.getString("opponent_user_name"));
            }

            opponent.setAllowChat(item.getString("allowChat"));

            BigInteger user1ID = new BigInteger(item.getString("user_1_id"));
            BigInteger user2ID = new BigInteger(item.getString("user_2_id"));

            Integer user2SpentTime = null;
            Integer user2CorrectCount = null;
            Integer user1SpentTime = null;
            Integer user1CorrectCount = null;
            if(item.has("user_2_spent_time"))
                if(!item.getString("user_2_spent_time").equals("null") && Integer.valueOf(item.getString("user_2_spent_time"))> 0)
                    user2SpentTime = new Integer(item.getString("user_2_spent_time"));
            if(item.has("user_2_correct_count"))
                if(!item.getString("user_2_correct_count").equals("null"))
                    user2CorrectCount = new Integer(item.getString("user_2_correct_count"));
            if(item.has("user_1_spent_time"))
                if(!item.getString("user_1_spent_time").equals("null") && Integer.valueOf(item.getString("user_1_spent_time"))> 0)
                    user1SpentTime = new Integer(item.getString("user_1_spent_time"));
            if(item.has("user_1_correct_count"))
                if(!item.getString("user_1_correct_count").equals("null"))
                    user1CorrectCount = new Integer(item.getString("user_1_correct_count"));

            if(session.getCurrentUser().getId().equals(user1ID))
            {
                setSelfSpentTime(String.valueOf(user1SpentTime));
                setSelfCorrectCount(String.valueOf(user1CorrectCount));

                setOpponentSpentTime(String.valueOf(user2SpentTime));
                setOpponentCorrectCount(String.valueOf(user2CorrectCount));

                opponent.setId(user2ID);
            }
            else
            {
                setSelfSpentTime(String.valueOf(user2SpentTime));
                setSelfCorrectCount(String.valueOf(user2CorrectCount));

                setOpponentSpentTime(String.valueOf(user1SpentTime));
                setOpponentCorrectCount(String.valueOf(user1CorrectCount));

                opponent.setId(user1ID);
            }

            if(getEnded() != null && getEnded().equals(USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED) )
            {
                //meysam - match ended or declined - no match id exist
                if(getEnded().equals(USER_UNIVERSAL_MATCH_ENDED_STATUS_ENDED))
                    setWinner(new Integer(item.getString("winner_code")));
//                setBet(item.getString("bet"));
                if(item.has("user_universal_match_id"))//meysam - is match not ended
                    setId(new BigInteger(item.getString("user_universal_match_id")));
            }
            else
            {
                if(item.has("user_universal_match_id"))//meysam - is match not ended
                    setId(new BigInteger(item.getString("user_universal_match_id")));

            }

            setOpponent(opponent);
            setMatchStatus(calculateMatchStatus(getEnded(),user1ID,user1SpentTime,getWinner(),getCntx()));

        }
        catch (Exception ex)
        {
            Utility.generateLogError(ex);
        }



    }

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
    public Integer calculateTrueCellCount()  {
        Integer result = 0;
        JSONObject j_question;
        for(int i = 0; i < this.getQuestions().size(); i++)
        {
//            result += this.getQuestions().get(i).getAnsweredCells().size();

            if(this.getQuestions().get(i).getAnsweredCells().size() == this.getQuestions().get(i).getAnswerCells().size())
                result += 1;
        }

        return result;
    }

    public static Integer getRewardByID(String betId, Integer status)
    {
        Integer result = 0;

        for (int i = 0; i < UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(betId))
            {
                if(status.equals(UniversalMatchModel.STATUS_EQUAL))
                {
                    result = UniversalMatchModel.bets.get(i).getAmount() - ((UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);

                }
                if(status.equals(UniversalMatchModel.STATUS_WIN))
                {
                    result = (2* UniversalMatchModel.bets.get(i).getAmount()) - ((2* UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);
                }
            }
        }
        return result;
    }

    public static void setMinSec(String betId)
    {
        Integer result = 0;

        for (int i = 0; i < UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(betId))
            {
                int remainder = UniversalMatchModel.bets.get(i).getTime();
                UniversalMatchModel.MATCH_DURATION_MINUTES = remainder / 60;
                remainder = remainder - UniversalMatchModel.MATCH_DURATION_MINUTES * 60;
                UniversalMatchModel.MATCH_DURATION_SECONDS = remainder;
            }
        }
    }

    public static void addMoreTimeToMatch(Integer seconds)
    {

        Integer addedMins = seconds / 60;
        Integer addedsecs = seconds - addedMins * 60;
        UniversalMatchModel.MATCH_DURATION_MINUTES += addedMins;
        UniversalMatchModel.MATCH_DURATION_SECONDS +=addedsecs;

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
        for(int i = 0; i< UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(bet_id)) {
                result = UniversalMatchModel.bets.get(i).getAmount();
            }

        }
        return result;
    }

    public static boolean isNitro(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        String bet_id = session.getStringItem(SessionModel.KEY_BET_ID);
        for(int i = 0; i< UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(bet_id)) {
                if(UniversalMatchModel.bets.get(i).getRewardTime()>0)
                    return  true;
            }

        }
        return false;
    }

    public  void initializeBet(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        String bet_id = session.getStringItem(SessionModel.KEY_BET_ID);
        for(int i = 0; i< UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(bet_id)) {
                this.setMatchBet(UniversalMatchModel.bets.get(i));
                break;

            }
        }
    }

    public static String getBetAmountTextById(String betId)
    {
        String result = null;
        for(int i = 0; i< UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(betId)) {
                if(UniversalMatchModel.bets.get(i).getAmount() == 0)
                    result = " مسابقه دوستانه ";
                else
                {
                    result = " مسابقه سر "+UniversalMatchModel.bets.get(i).getAmount();
                    if(UniversalMatchModel.bets.get(i).getBetLuck())
                    {
                        result += " شانس ";
                    }
                    else
                    {
                        result += " فندق ";
                    }
                    Integer temp = (2*UniversalMatchModel.bets.get(i).getAmount()) - ((2*UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);
                    result += " و جایزه نهایی " + temp.toString();
                    if(UniversalMatchModel.bets.get(i).getBetLuck())
                    {
                        result += " شانس ";
                    }
                    else
                    {
                        result += " فندق ";
                    }

                }

                break;
            }
        }
        return result;
    }

    public static UniversalMatchModel findMatchById(ArrayList<UniversalMatchModel> matches, BigInteger matchId)
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

    public static String getStatusStringByCode(Integer status, Context cntx)
    {
        String result = "";
        switch (status)
        {
            case STATUS_IN_PROGRESS:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_in_progress);
                break;
            case STATUS_EQUAL:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_equal);
                break;
            case STATUS_LOST:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_lost);
                break;
            case STATUS_WIN:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_win);
                break;
            case STATUS_DISAPPROVED_BY_OPPONENT:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_disapproved_by_opponent);
                break;
            case STATUS_DISAPPROVED_BY_YOU:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_disapproved_by_you);
                break;
            case STATUS_REQUEST_RECEIVED:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_request_received);
                break;
            case STATUS_REQUEST_SENT:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_request_sent);
                break;
            case STATUS_OPPONENT_TURN:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_opponent_turn);
                break;
            case STATUS_YOUR_TURN:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_your_turn);
                break;
            case STATUS_REQUEST_EXPIRED:
                result = cntx.getResources().getString(R.string.txt_universal_match_status_request_expired);
                break;
            default:
                result = cntx.getResources().getSystem().getString(R.string.txt_universal_match_status_unidentified);
                break;
        }

        return result;

    }

    public static Integer calculateMatchStatus(Integer isEnded, BigInteger user1Id, Integer user1SpentTime, Integer winnerCode, Context cntx)
    {
        SessionModel session = new SessionModel(cntx.getApplicationContext());
        if(isEnded == null)
        {
            if(user1Id.equals(session.getCurrentUser().getId()))
            {
                return STATUS_REQUEST_SENT;
            }
            else
            {
                return STATUS_REQUEST_RECEIVED;
            }
        }
        else if(isEnded == -2)
        {
            return STATUS_REQUEST_EXPIRED;
        }
        else if(isEnded == 0)
        {
            if(user1Id.equals(session.getCurrentUser().getId()))
            {
                if(user1SpentTime == null)
                {
                    return STATUS_YOUR_TURN;
                }
                else
                {
                    return STATUS_OPPONENT_TURN;
                }
            }
            else
            {
                if(user1SpentTime == null)
                {
                    return STATUS_OPPONENT_TURN;
                }
                else
                {
                    return STATUS_YOUR_TURN;
                }
            }
        }
        else if(isEnded == 1)
        {
            if(winnerCode == 1)
            {
                return STATUS_WIN;
            }
            if(winnerCode == 2)
            {
                return STATUS_LOST;
            }
            if(winnerCode == 3)
            {
                return STATUS_EQUAL;
            }
            else
            {
                //meysam - somthings wrong
                String error_message = "استاتوس نال شده. مقدار is_ended:"+isEnded.toString();
                error_message += " مقدار winnerCode: "+winnerCode.toString();
                error_message += " مقدار user1SpentTime: "+user1SpentTime.toString();
                error_message += " مقدار user1Id: "+user1Id.toString();

                Utility.generateLogError(new Exception(error_message));
                return null;
            }
        }
        else if(isEnded == -1)
        {
            if(user1Id.equals(session.getCurrentUser().getId()))
            {
                return STATUS_DISAPPROVED_BY_OPPONENT;
            }
            else
            {
                return STATUS_DISAPPROVED_BY_YOU;
            }
        }
        else
        {
            //meysam - somthings wrong
            String error_message = "استاتوس نال شده. مقدار is_ended:"+isEnded.toString();
            error_message += " مقدار winnerCode: "+winnerCode.toString();
            error_message += " مقدار user1SpentTime: "+user1SpentTime.toString();
            error_message += " مقدار user1Id: "+user1Id.toString();

            Utility.generateLogError(new Exception(error_message));
            return null;
        }

    }

    public static Integer getRewardByIDAndStatus(String betId, Integer status)
    {
        Integer result = 0;

        for (int i = 0; i < UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(betId))
            {
                if(status.equals(UniversalMatchModel.STATUS_EQUAL))
                {
                    result = UniversalMatchModel.bets.get(i).getAmount() - ((UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);

                }
                if(status.equals(UniversalMatchModel.STATUS_WIN))
                {
                    result = (2*UniversalMatchModel.bets.get(i).getAmount()) - ((2*UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);
                }
                if(status.equals(UniversalMatchModel.STATUS_DISAPPROVED_BY_OPPONENT))
                {
                    result = UniversalMatchModel.bets.get(i).getAmount() - ((UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);
                }
                if(status.equals(UniversalMatchModel.STATUS_REQUEST_EXPIRED))
                {
                    result = UniversalMatchModel.bets.get(i).getAmount() - ((UniversalMatchModel.bets.get(i).getAmount()* UniversalMatchModel.bets.get(i).getInterest())/100);
                }
            }
        }
        return result;
    }

    public static BetModel getBetByID(String betId)
    {

        for (int i = 0; i < UniversalMatchModel.bets.size(); i++)
        {
            if(UniversalMatchModel.bets.get(i).getBetId().equals(betId))
            {
               return UniversalMatchModel.bets.get(i);
            }
        }
        return null;
    }

    public void fillNullAttributes(UniversalMatchModel returnedMatch)
    {
        if(getBet() == null && returnedMatch.getBet() != null)
        {
            setBet(returnedMatch.getBet());
        }
        if(getMatchStatus() == null && returnedMatch.getMatchStatus() != null)
        {
            setMatchStatus(returnedMatch.getMatchStatus());
        }
        if(getMatchBet() == null && returnedMatch.getMatchBet() != null)
        {
            setMatchBet(returnedMatch.getMatchBet());
        }
        if(getMatchType() == null && returnedMatch.getMatchType() != null)
        {
            setMatchType(returnedMatch.getMatchType());
        }
        if(getOpponentType() == null && returnedMatch.getOpponentType() != null)
        {
            setOpponentType(returnedMatch.getOpponentType());
        }
        if(getOpponent() == null && returnedMatch.getOpponent() != null)
        {
            setOpponent(returnedMatch.getOpponent());
        }
        if(getEnded() == null && returnedMatch.getEnded() != null)
        {
            setEnded(returnedMatch.getEnded());
        }
        if(getQuestions() == null && returnedMatch.getQuestions() != null)
        {
            setQuestions(returnedMatch.getQuestions());
        }
        if(getGuid() == null && returnedMatch.getGuid() != null)
        {
            setGuid(returnedMatch.getGuid());
        }
        if(getId() == null && returnedMatch.getId() != null)
        {
            setId(returnedMatch.getId());
        }
        if(getNitro() == null && returnedMatch.getNitro() != null)
        {
            setNitro(returnedMatch.getNitro());
        }
        if(getOpponentCorrectCount() == null && returnedMatch.getOpponentCorrectCount() != null)
        {
            setOpponentCorrectCount(returnedMatch.getOpponentCorrectCount());
        }
        if(getOpponentSpentTime() == null && returnedMatch.getOpponentSpentTime() != null)
        {
            setOpponentSpentTime(returnedMatch.getOpponentSpentTime());
        }
        if(getSelfCorrectCount() == null && returnedMatch.getSelfCorrectCount() != null)
        {
            setSelfCorrectCount(returnedMatch.getSelfCorrectCount());
        }
        if(getSelfSpentTime() == null && returnedMatch.getSelfSpentTime() != null)
        {
            setSelfSpentTime(returnedMatch.getSelfSpentTime());
        }
        if(getWinner() == null && returnedMatch.getWinner() != null)
        {
            setWinner(returnedMatch.getWinner());
        }
        if(getCrossTable() == null && returnedMatch.getCrossTable() != null)
        {
            setCrossTable(returnedMatch.getCrossTable());
        }
    }

    public static ArrayList<UniversalMatchModel> fillMatchsType(ArrayList<UniversalMatchModel> matchs, Integer matchType)
    {
        for(int i = 0; i < matchs.size(); i++)
        {
            matchs.get(i).setMatchType(matchType);
        }

        return matchs;
    }

    public static ArrayList<UniversalMatchModel> sortListByTurn(ArrayList<UniversalMatchModel> matchs)
    {
        ArrayList<UniversalMatchModel> yourturn = new ArrayList<>();
        ArrayList<UniversalMatchModel> requestedForYou = new ArrayList<>();
        ArrayList<UniversalMatchModel> normal = new ArrayList<>();

        for(int i=0;i<matchs.size();i++)
        {
            if (matchs.get(i).getMatchStatus().equals(UniversalMatchModel.STATUS_YOUR_TURN))
            {
                yourturn.add(matchs.get(i));
            }
            else if(matchs.get(i).getMatchStatus().equals(UniversalMatchModel.STATUS_REQUEST_RECEIVED))
            {
                requestedForYou.add(matchs.get(i));
            }
            else
            {
                normal.add(matchs.get(i));
            }
        }

        yourturn.addAll(requestedForYou);
        yourturn.addAll(normal);
        return  yourturn;
    }

    public static String getRewardText(Integer status, String betId)
    {
        String result = "";
        BetModel bet = getBetByID(betId);

        if(status == STATUS_WIN)
        {
            result += "مقدار";
            if(bet.getBetLuck())
            {
                result += UniversalMatchModel.getRewardByIDAndStatus(betId,status);
                result+=" به فندقات اضافه شد ";
            }
            else
            {
                result += UniversalMatchModel.getRewardByIDAndStatus(betId,status);
                result+=" به شانست اضافه شد ";
            }
        }
        else if(status == STATUS_REQUEST_EXPIRED ||
                status == STATUS_DISAPPROVED_BY_YOU ||
                status == STATUS_DISAPPROVED_BY_OPPONENT)
        {
            result += "مقدار";

            if(!bet.getBetLuck())
            {
                result += UniversalMatchModel.getRewardByIDAndStatus(betId,status);
                result += " فندق بهت برگشت داده شد ";
            }
            else
            {
                result += UniversalMatchModel.getRewardByIDAndStatus(betId,status);
                result += " شانس بهت برگشت داده شد ";
            }
        }


        return result;

    }
}