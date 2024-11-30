package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.math.BigInteger;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.controllers.QuestionController;
import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class CrossTableModel {


    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public Integer getWidth() {
        return Width;
    }

    public void setWidth(Integer width) {
        Width = width;
    }

    public Integer getHeight() {
        return Height;
    }

    public void setHeight(Integer height) {
        Height = height;
    }

//    public ArrayList<QuestionModel> getQuestions() {
//        return Questions;
//    }
//
//    public void setQuestions(ArrayList<QuestionModel> questions) {
//      Questions = questions;
//    }

    private BigInteger Id;
    private Integer Width;
    private Integer Height;
//    private ArrayList<QuestionModel> Questions;

    public CrossTableModel()
    {
        Id = null;
        Width = null;
        Height = null;
//        Questions = null;
    }
}