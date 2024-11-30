package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.math.BigInteger;

import ir.fardan7eghlim.luckylord.interfaces.BodyPartInterface;
import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class BodyPartModel implements BodyPartInterface {


    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getCost() {
        return Cost;
    }

    public void setCost(Integer cost) {
        Cost = cost;
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
    private String Name;
    private Integer Cost;
    private String CreatedAt;
    private String UpdatedAt;


    private Context cntx;


    public BodyPartModel()
    {
        Id = null;
        Name = null;
        Cost = null;
        CreatedAt = null;
        UpdatedAt = null;

        this.cntx = null;
    }

    public BodyPartModel(Context cntx)
    {
        Id = null;
        Name = null;
        Cost = null;
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