package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.math.BigInteger;

import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;
import ir.fardan7eghlim.luckylord.interfaces.MessageInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class MessageModel implements MessageInterface {


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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getReaded() {
        return IsReaded;
    }

    public void setReaded(Boolean readed) {
        IsReaded = readed;
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

    public BigInteger getUserMessageId() {
        return UserMessageId;
    }

    public void setUserMessageId(BigInteger userMessageId) {
        UserMessageId = userMessageId;
    }
    public Boolean getSend() {
        return IsSend;
    }

    public void setSend(Boolean send) {
        IsSend = send;
    }


    private BigInteger Id;

    private BigInteger UserMessageId;
    private String Guid;
    private BigInteger UserId;
    private String Title;
    private String Description;
    private Boolean IsReaded;
    private Boolean IsSend;
    private String CreatedAt;
    private String UpdatedAt;
    private Context cntx;


    public MessageModel()
    {
        Id = null;
        Guid = null;
        Title = null;
        UserId = null;
        Description = null;
        IsReaded = null;
        CreatedAt = null;
        UpdatedAt = null;
        IsSend=false;
        this.cntx = null;
    }

    public MessageModel(Context cntx)
    {

        Id = null;
        Guid = null;
        Title = null;
        UserId = null;
        Description = null;
        IsReaded = null;
        CreatedAt = null;
        UpdatedAt = null;
        IsSend=false;
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