package ir.fardan7eghlim.luckylord.models;

import android.content.Context;

import java.math.BigInteger;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.R;

/**
 * Created by Amir on 11/16/2017.
 */

public class WordModel {

    public static final String TAG_SEARCH_WORDS = "search_words";


    public static final int CATEGORY_NAME_BOY = 1;
    public static final int CATEGORY_NAME_GIRL = 2;
    public static final int CATEGORY_NAME_TRANS = 3;
    public static final int CATEGORY_FRUIT = 4;
    public static final int CATEGORY_COLOR = 5;
    public static final int CATEGORY_VEGETABLE = 6;
    public static final int CATEGORY_FLOWER = 7;
    public static final int CATEGORY_LEGUME = 8;//meysam - حبوبات
    public static final int CATEGORY_GRAIN = 9;//meysam - غلات



    public static final int  numberOfFarsiWords= 80;

    //getters and setters
    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public Integer getLength() {
        return Length;
    }

    public void setLength(Integer length) {
        Length = length;
    }

    public Integer getLengthWithoutSpace() {
        return LengthWithoutSpace;
    }

    public void setLengthWithoutSpace(Integer lengthWithoutSpace) {
        LengthWithoutSpace = lengthWithoutSpace;
    }
    public ArrayList<String> getAnsweredLetters() {
        return AnsweredLetters;
    }

    public void setAnsweredLetters(ArrayList<String> answeredLetters) {
        AnsweredLetters = answeredLetters;
    }

    public ArrayList<String> getAnsweredLettersPositions() {
        return AnsweredLettersPositions;
    }

    public void setAnsweredLettersPositions(ArrayList<String> answeredLettersPositions) {
        AnsweredLettersPositions = answeredLettersPositions;
    }

    public ArrayList<String> getWrongAnsweredLetters() {
        return WrongAnsweredLetters;
    }

    public void setWrongAnsweredLetters(ArrayList<String> wrongAnsweredLetters) {
        WrongAnsweredLetters = wrongAnsweredLetters;
    }

    public ArrayList<String> getWrongAnsweredLettersPositions() {
        return WrongAnsweredLettersPositions;
    }

    public void setWrongAnsweredLettersPositions(ArrayList<String> wrongAnsweredLettersPositions) {
        WrongAnsweredLettersPositions = wrongAnsweredLettersPositions;
    }

    public ArrayList<String> getAnswerLettersPositions() {
        return AnswerLettersPositions;
    }

    public void setAnswerLettersPositions(ArrayList<String> answerLettersPositions) {
        AnswerLettersPositions = answerLettersPositions;
    }


    public ArrayList<String> getAllLettersPositions() {
        return AllLettersPositions;
    }

    public void setAllLettersPositions(ArrayList<String> allLettersPositions) {
        AllLettersPositions = allLettersPositions;
    }

    public ArrayList<String> getAllLetters() {
        return AllLetters;
    }

    public void setAllLetters(ArrayList<String> allLetters) {
        AllLetters = allLetters;
    }

    public Integer getCategory() {
        return Category;
    }

    public void setCategory(Integer category) {
        Category = category;
    }

    public ArrayList<String> getWords() {
        return Words;
    }

    public void setWords(ArrayList<String> words) {
        Words = words;
    }
//
    public String getWordNoSpace() {
        return WordNoSpace;
    }

    public void setWordNoSpace(String wordNoSpace) {
        WordNoSpace = wordNoSpace;
    }


    //definations
    private BigInteger Id;
    private String Word;
    private String WordNoSpace;
    private Integer Length;
    private Integer LengthWithoutSpace;
    private Integer Category;

    private ArrayList<String> AnsweredLetters;
    private ArrayList<String> AnsweredLettersPositions;
    private ArrayList<String> WrongAnsweredLetters;
    private ArrayList<String> WrongAnsweredLettersPositions;
    private ArrayList<String> AnswerLettersPositions;
    private ArrayList<String> AllLettersPositions;
    private ArrayList<String> AllLetters;
    private ArrayList<String> Words;

    public static String TABLE_WORDS_GUESS_WORD_TAG = "table_words_guess_word_tag";
    public static String TABLE_WORDS_FIND_WORD_TAG = "table_words_find_word_tag";

    //constructor
    public WordModel() {
        Id = null;
        Word = null;
        WordNoSpace = null;
        Length = 0;
        LengthWithoutSpace = 0;
        Category = null;

        setAnsweredLetters(new ArrayList<String>());
        setWrongAnsweredLettersPositions(new ArrayList<String>());
        setAnsweredLettersPositions(new ArrayList<String>());
        setWrongAnsweredLetters(new ArrayList<String>());
        setAnswerLettersPositions(new ArrayList<String>());
        setAllLettersPositions(new ArrayList<String>());
        setAllLetters(new ArrayList<String>());
        setWords(new ArrayList<String>());
    }
    public WordModel(String word, Integer category) {
        Id = null;
        Word = word.trim();
        WordNoSpace = word.replaceAll("\\s+","");
//        if(WordNoSpace.length() < Word.length())
//        {
//            int tt = 0;
//            tt = -1;
//        }

        Length = word.length();
        LengthWithoutSpace = word.replaceAll("\\s+","").length();
        Category = category;

        setAnsweredLetters(new ArrayList<String>());
        setWrongAnsweredLettersPositions(new ArrayList<String>());
        setAnsweredLettersPositions(new ArrayList<String>());
        setWrongAnsweredLetters(new ArrayList<String>());
        setAnswerLettersPositions(new ArrayList<String>());
        setAllLettersPositions(new ArrayList<String>());
        setAllLetters(new ArrayList<String>());
        setWords(new ArrayList<String>());

    }
    public WordModel(BigInteger id, String word, Integer length, Integer lengthWithoutSpace, Integer category) {
        Id = id;
        Word = word;
        WordNoSpace = word.replaceAll("\\s+","");
        Length = length;
        LengthWithoutSpace = lengthWithoutSpace;
        Category = category;

        setAnsweredLetters(new ArrayList<String>());
        setWrongAnsweredLettersPositions(new ArrayList<String>());
        setAnsweredLettersPositions(new ArrayList<String>());
        setWrongAnsweredLetters(new ArrayList<String>());
        setAnswerLettersPositions(new ArrayList<String>());
        setAllLettersPositions(new ArrayList<String>());
        setAllLetters(new ArrayList<String>());
        setWords(new ArrayList<String>());

    }

    public static String getCategoryById(Integer category, Context cntx)
    {
        if(category == CATEGORY_COLOR)
            return cntx.getString(R.string.tlt_category_guess_color);
        if(category == CATEGORY_FLOWER)
            return cntx.getString(R.string.tlt_category_guess_flower);
        if(category == CATEGORY_FRUIT)
            return cntx.getString(R.string.tlt_category_guess_fruit);
        if(category == CATEGORY_GRAIN)
            return cntx.getString(R.string.tlt_category_guess_grain);
        if(category == CATEGORY_LEGUME)
            return cntx.getString(R.string.tlt_category_guess_legume);
        if(category == CATEGORY_NAME_BOY)
            return cntx.getString(R.string.tlt_category_guess_name_boy);
        if(category == CATEGORY_NAME_GIRL)
            return cntx.getString(R.string.tlt_category_guess_name_girl);
        if(category == CATEGORY_NAME_TRANS)
            return cntx.getString(R.string.tlt_category_guess_name_trans);
        if(category == CATEGORY_VEGETABLE)
            return cntx.getString(R.string.tlt_category_guess_vegetable);

        return null;
    }

}
