package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;

import java.math.BigInteger;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;
import ir.fardan7eghlim.luckylord.interfaces.DrawInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class DrawModel implements DrawInterface {


    public static final int  IsEnded= 1;
    public static final int  IsNotEnded= 0;


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

    public String getDrawDateTime() {
        return DrawDateTime;
    }

    public void setDrawDateTime(String drawDateTime) {
        DrawDateTime = drawDateTime;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getSponser() {
        return Sponser;
    }

    public void setSponser(String sponser) {
        Sponser = sponser;
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

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }
    public ArrayList<RewardModel> getRewards() {
        return Rewards;
    }

    public void setRewards(ArrayList<RewardModel> rewards) {
        Rewards = rewards;
    }

    public Boolean getParticipated() {
        return Participated;
    }

    public void setParticipated(Boolean participated) {
        Participated = participated;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    private BigInteger Id;
    private String Guid;
    private String DrawDateTime;
    private String Cost;
    private String Sponser;
    private Bitmap Image;
    private Boolean Participated;
    private String Description;



    private String Link;


    private String CreatedAt;
    private String UpdatedAt;


    private ArrayList<RewardModel> Rewards;


//    private Context cntx;


    public DrawModel()
    {
        Id = null;
        Guid = null;
        DrawDateTime = null;
        Cost = null;
        Sponser = null;
        Image = null;
        Participated = null;
        Description = null;
        CreatedAt = null;
        UpdatedAt = null;
        Link = null;

        Rewards = new ArrayList<>();

//        this.cntx = null;
    }

//    public DrawModel(Context cntx)
//    {
//        Id = null;
//        Guid = null;
//        DrawDateTime = null;
//        Cost = null;
//        Sponser = null;
//        Image = null;
//        Participated = null;
//        Description = null;
//        CreatedAt = null;
//        UpdatedAt = null;
//        Link = null;
//
//        Rewards = new ArrayList<>();
//
//
//        this.cntx = cntx;
//
//    }

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