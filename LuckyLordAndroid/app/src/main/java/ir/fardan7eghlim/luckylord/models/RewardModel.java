package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;

import java.math.BigInteger;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;
import ir.fardan7eghlim.luckylord.interfaces.RewardInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class RewardModel implements RewardInterface {


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

    public BigInteger getDrawId() {
        return DrawId;
    }

    public void setDrawId(BigInteger drawId) {
        DrawId = drawId;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    private BigInteger Id;
    private String Guid;
    private BigInteger DrawId;
    private String Cost;
    private String Description;

    private Integer Count;
    private String Unit;
    private String CreatedAt;
    private String UpdatedAt;


//    private Context cntx;


    public RewardModel()
    {
        Id = null;
        Guid = null;
        DrawId = null;
        Cost = null;
        Description = null;
        Count = null;
        Unit = null;
        CreatedAt = null;
        UpdatedAt = null;

//        this.cntx = null;
    }

//    public RewardModel(Context cntx)
//    {
//        Id = null;
//        Guid = null;
//        DrawId = null;
//        Cost = null;
//        Description = null;
//        Count = null;
//        Unit = null;
//        CreatedAt = null;
//        UpdatedAt = null;
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