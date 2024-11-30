package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.math.BigInteger;

import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class UserQuestionModel implements ContactUsInterface {


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

    public BigInteger getUserId() {
        return UserId;
    }

    public void setUserId(BigInteger userId) {
        UserId = userId;
    }

    public BigInteger getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(BigInteger questionId) {
        QuestionId = questionId;
    }

    public Boolean getCorrect() {
        return IsCorrect;
    }

    public void setCorrect(Boolean correct) {
        IsCorrect = correct;
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

    private BigInteger Id;
    private String Guid;
    private BigInteger UserId;
    private BigInteger QuestionId;
    private Boolean IsCorrect;
    private String CreatedAt;
    private String UpdatedAt;


    private Context cntx;


    public UserQuestionModel()
    {
        Id = null;
        Guid = null;
        UserId = null;
        QuestionId = null;
        IsCorrect = null;
        CreatedAt = null;
        UpdatedAt = null;


        this.cntx = null;
    }

    public UserQuestionModel(Context cntx)
    {
        Id = null;
        Guid = null;
        UserId = null;
        QuestionId = null;
        IsCorrect = null;
        CreatedAt = null;
        UpdatedAt = null;

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

}