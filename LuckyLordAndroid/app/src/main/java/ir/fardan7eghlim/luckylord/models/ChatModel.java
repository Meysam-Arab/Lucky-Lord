package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */


import java.math.BigInteger;

/**
 * Created by Meysam on 12/20/2016.
 */

public class ChatModel {


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    private BigInteger Id;
    private String Text;
    private Integer Type;
    private String UserName;
    private String DateTime;


    public ChatModel()
    {
        Id = null;
        Text = null;
        Type = null;
        UserName = null;
        DateTime = null;
    }

    public ChatModel(String userName, String dateTime, Integer type, String text, BigInteger chatId)
    {


        Id = chatId;
        Text = text;
        Type = type;
        UserName = userName;
        DateTime = dateTime;


    }

}