package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class QuestionModel implements ContactUsInterface {

    public static final int  Correct= 1;
    public static final int  InCorrect= 0;

    ///static attributes
    ///
    public static final int LIKED = 1;
    public static final int HATED = 0;


    public static final int REPORT_OTHER_REASONS = 0;
    public static final int REPORT_NO_PICTURE = 1;
    public static final int REPORT_WRONG_PICTURE = 2;
    public static final int REPORT_WRONG_ANSWER = 3;
    public static final int REPORT_WRONG_WRITING = 4;
    public static final int REPORT_WRONG_SPELLING = 5;
    public static final int REPORT_VERY_HARD_QUESTION = 6;
    public static final int REPORT_INVALID_QUESTION= 7;
    public static final int REPORT_VAGUE_QUESTION = 8;
    public static final int REPORT_WRONG_CATEGORY = 9;
    public static final int REPORT_REPEATED_CHOICE = 10;
    public static final int REPORT_MULTIPLE_ANSWER = 11;
    public static final int REPORT_MORAL_PROBLEM = 12;
    public static final int REPORT_CREED_PROBLEM = 13;
    public static final int REPORT_TRIBAL_PROBLEM = 14;
    public static final int REPORT_POLITICAL_PROBLEM = 15;
    public static final int REPORT_AGAINST_LOW = 16;

    /// ////////////////

    //meysam - 13960727
    public static String TABLE_QUESTIONS_DARHAM_TAG = "table_questions_darham_tag";
    public static String TABLE_QUESTIONS_CROSS_TAG = "table_questions_cross_tag";
    ////////////////////


    public static final int  CategoryColorful= 0;

    //meysam - add other cat id's
    public static final int HISTORY = 1;
    public static final int POEM_AND_LITERATURE = 2;
    public static final int ENGLISH = 3;
    public static final int CULTURE_AND_ART = 4;
    public static final int RELIGIOUS = 5;
    public static final int GEOGRAPHY = 6;
    public static final int TECHNOLOGY = 7;
    public static final int MEDICAL = 8;
    public static final int SPORTS = 9;
    public static final int MATHEMATICAL_AND_ITELLIGENCE = 10;
    public static final int MUSIC = 11;
    public static final int PUBLIC_INFORMATION=12;
    public static final int PUZZLE=13;
    ///////////////////////////////

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public Integer getMaxHazelReward() {
        return MaxHazelReward;
    }

    public void setMaxHazelReward(Integer maxHazelReward) {
        MaxHazelReward = maxHazelReward;
    }

    public Integer getMinHazelReward() {
        return MinHazelReward;
    }

    public void setMinHazelReward(Integer minHazelReward) {
        MinHazelReward = minHazelReward;
    }

    public Integer getMaxLuckReward() {
        return MaxLuckReward;
    }

    public void setMaxLuckReward(Integer maxLuckReward) {
        MaxLuckReward = maxLuckReward;
    }

    public Integer getMinLuckReward() {
        return MinLuckReward;
    }

    public void setMinLuckReward(Integer minLuckReward) {
        MinLuckReward = minLuckReward;
    }

    public Integer getPenalty() {
        return Penalty;
    }

    public void setPenalty(Integer penalty) {
        Penalty = penalty;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public Integer getFinalHazelReward() {
        return FinalHazelReward;
    }

    public void setFinalHazelReward(Integer finalHazelReward) {
        FinalHazelReward = finalHazelReward;
    }

    public Integer getFinalLuckReward() {
        return FinalLuckReward;
    }

    public void setFinalLuckReward(Integer finalLuckReward) {
        FinalLuckReward = finalLuckReward;
    }

    public Boolean getCorrect() {
        return IsCorrect;
    }

    public void setCorrect(Boolean correct) {
        IsCorrect = correct;
    }

    public Boolean getAnswered() {
        return IsAnswered;
    }

    public void setAnswered(Boolean answered) {
        IsAnswered = answered;
    }

    public ArrayList<String> getAnsweredLetters() {
        return AnsweredLetters;
    }

    public void setAnsweredLetters(ArrayList<String> answeredLetters) {
        AnsweredLetters = answeredLetters;
    }

    public ArrayList<String> getAnsweredCells() {
        return AnsweredCells;
    }

    public void setAnsweredCells(ArrayList<String> answeredCells) {
        AnsweredCells = answeredCells;
    }

    public ArrayList<String> getAnswerCells() {
        return AnswerCells;
    }

    public void setAnswerCells(ArrayList<String> answerCells) {
        AnswerCells = answerCells;
    }

    public String getPositionCode() {
        return PositionCode;
    }

    public void setPositionCode(String positionCode) {
        PositionCode = positionCode;
    }

    public String getQuestionPosition() {
        return QuestionPosition;
    }

    public void setQuestionPosition(String questionPosition) {
        QuestionPosition = questionPosition;
    }

    private BigInteger Id;
    private String Guid;
    private String Description;
    private String Answer;
    private Integer MaxHazelReward;
    private Integer MinHazelReward;
    private Integer MaxLuckReward;
    private Integer MinLuckReward;
    private Integer FinalHazelReward;
    private Integer FinalLuckReward;
    private Integer Penalty;
    private ArrayList<String> AnsweredLetters;
    private ArrayList<String> AnsweredCells;
    private ArrayList<String> AnswerCells;
    private Bitmap Image;
    private Integer CategoryId;
    private String PositionCode;
    private String QuestionPosition;
    private String CreatedAt;
    private String UpdatedAt;
    private Boolean IsCorrect;
    private Boolean IsAnswered;

    public QuestionModel()
    {
        Id = null;
        Guid = null;
        Description = null;
        Answer = null;
        MaxHazelReward = null;
        MinHazelReward = null;
        MaxLuckReward = null;
        MinHazelReward = null;
        Penalty = null;
        Image = null;
        CategoryId = null;
        CreatedAt = null;
        UpdatedAt = null;
        IsCorrect = false;
        IsAnswered = false;
        QuestionPosition = null;
        PositionCode = null;

        AnswerCells = new ArrayList<>();
        AnsweredLetters = new ArrayList<>();
        AnsweredCells = new ArrayList<>();

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

   // meysam - 13960727
    public void addAnswerCell(String position)
    {
//        if(AnswerCells != null)
//        {
//            AnswerCells += ","+position;
//        }
//        else
//        {
//            AnswerCells = ""+position;
//        }
        if(AnswerCells == null)
        {
            AnswerCells = new ArrayList<String>();
        }
        AnswerCells.add(position);
    }

    //meysam - 13960727
    public void addAnsweredCell(String position)
    {
//        if(AnsweredCells != null)
//        {
//            AnsweredCells += ","+position;
//        }
//        else
//        {
//            AnsweredCells = ""+position;
//        }

        if(AnsweredCells == null)
        {
            AnsweredCells = new ArrayList<String>();
        }
        AnsweredCells.add(position);
    }

    //meysam - 13960727
    public void addAnsweredLetter(String letter)
    {
//        if(AnsweredLetters != null)
//        {
//            AnsweredLetters += letter;
//        }
//        else
//        {
//            AnsweredLetters = ""+letter;
//        }

        if(AnsweredLetters == null)
        {
            AnsweredLetters = new ArrayList<String>();
        }

        AnsweredLetters.add(letter);
    }
    //meysam - 13960727
    public String getAnsweredLettersAsString()
    {
        StringBuilder sb = new StringBuilder();
        if(this.getAnsweredLetters()!=null && this.getAnsweredLetters().size() > 0)
        {
            char[] letters = getAnswer().toCharArray();
            int j = 0;
            int i = 0;
            while(i < getAnsweredCells().size())
            {
                if(letters[j] == ' ')
                {
                    j++;
                }
                else
                {
                    sb.append(letters[j]) ;
                    j++;
                    i++;
                }
            }
        }
        return sb.toString();
    }

    //meysam - 13960727
    public String getAnsweredLettersAsStringWithSpace()
    {
        StringBuilder sb = new StringBuilder();
        if(this.getAnsweredLetters()!=null && this.getAnsweredLetters().size() > 0)
        {
            char[] letters = getAnswer().toCharArray();
            int j = 0;
            int i = 0;
            while(i < getAnsweredCells().size())
            {
                sb.append(letters[j]) ;
                j++;
                if(letters[j] != ' ')
                {
                    i++;
                }
            }
        }
        return sb.toString();
    }

    public static boolean isHatedOrLiked(BigInteger questionId, Context context)
    {
        SessionModel session = new SessionModel(context);
        ArrayList<String> questionIds = session.getStringList(SessionModel.KEY_QUESTION_HATE_LIKE_LIST);
        if(questionIds.contains(String.valueOf(questionId)))
        {
            return true;
        }
        return false;

    }
    public static boolean isHatedOrLikedTable(BigInteger questionId, Context context)
    {
        SessionModel session = new SessionModel(context);
        ArrayList<String> questionIds = session.getStringList(SessionModel.KEY_PUZZLE_TABLE_QUESTION_HATE_LIKE_LIST);
        if(questionIds.contains(String.valueOf(questionId)))
        {
            return true;
        }
        return false;

    }

    public static HashMap<Integer,String> generateReportHashMap(Context cntx)
    {
        HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();

        spinnerMap.put(QuestionModel.REPORT_AGAINST_LOW,cntx.getString(R.string.report_AgainstLaw));
        spinnerMap.put(QuestionModel.REPORT_CREED_PROBLEM,cntx.getString(R.string.report_CreedProblem));
        spinnerMap.put(QuestionModel.REPORT_INVALID_QUESTION,cntx.getString(R.string.report_InvalidQuestion));
        spinnerMap.put(QuestionModel.REPORT_MORAL_PROBLEM,cntx.getString(R.string.report_MoralProblem));
        spinnerMap.put(QuestionModel.REPORT_MULTIPLE_ANSWER,cntx.getString(R.string.report_MultipleAnswer));
        spinnerMap.put(QuestionModel.REPORT_NO_PICTURE,cntx.getString(R.string.report_NoPicture));
        spinnerMap.put(QuestionModel.REPORT_POLITICAL_PROBLEM,cntx.getString(R.string.report_PoliticalProblem));
        spinnerMap.put(QuestionModel.REPORT_REPEATED_CHOICE,cntx.getString(R.string.report_RepeatedChoice));
        spinnerMap.put(QuestionModel.REPORT_VERY_HARD_QUESTION,cntx.getString(R.string.report_VeryHardQuestion));
        spinnerMap.put(QuestionModel.REPORT_TRIBAL_PROBLEM,cntx.getString(R.string.report_TribalProblem));
        spinnerMap.put(QuestionModel.REPORT_VAGUE_QUESTION,cntx.getString(R.string.report_VagueQuestion));
        spinnerMap.put(QuestionModel.REPORT_WRONG_ANSWER,cntx.getString(R.string.report_WrongAnswer));
        spinnerMap.put(QuestionModel.REPORT_WRONG_CATEGORY,cntx.getString(R.string.report_WrongCategory));
        spinnerMap.put(QuestionModel.REPORT_WRONG_PICTURE,cntx.getString(R.string.report_WrongPicture));
        spinnerMap.put(QuestionModel.REPORT_WRONG_SPELLING,cntx.getString(R.string.report_WrongSpelling));
        spinnerMap.put(QuestionModel.REPORT_WRONG_WRITING,cntx.getString(R.string.report_WrongWriting));
        spinnerMap.put(QuestionModel.REPORT_OTHER_REASONS,cntx.getString(R.string.report_OtherReasons));


        return spinnerMap;
    }

    public static ArrayList<String> generateReportTitleList(Context cntx)
    {
        ArrayList<String> results = new ArrayList<>();

        results.add(cntx.getString(R.string.report_WrongAnswer));
        results.add(cntx.getString(R.string.report_WrongCategory));
        results.add(cntx.getString(R.string.report_WrongPicture));
        results.add(cntx.getString(R.string.report_WrongSpelling));
        results.add(cntx.getString(R.string.report_WrongWriting));
        results.add(cntx.getString(R.string.report_RepeatedChoice));
        results.add(cntx.getString(R.string.report_VeryHardQuestion));
        results.add(cntx.getString(R.string.report_VagueQuestion));
        results.add(cntx.getString(R.string.report_InvalidQuestion));
        results.add(cntx.getString(R.string.report_MoralProblem));
        results.add(cntx.getString(R.string.report_MultipleAnswer));
        results.add(cntx.getString(R.string.report_CreedProblem));
        results.add(cntx.getString(R.string.report_PoliticalProblem));
        results.add(cntx.getString(R.string.report_TribalProblem));
        results.add(cntx.getString(R.string.report_AgainstLaw));
        results.add(cntx.getString(R.string.report_OtherReasons));


        return results;
    }

    public static ArrayList<QuestionModel> sortQuestionById(ArrayList<QuestionModel> questions)
    {
        Collections.sort(questions, new Comparator<QuestionModel>() {
            @Override
            public int compare(QuestionModel o1, QuestionModel o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        return  questions;
    }

}