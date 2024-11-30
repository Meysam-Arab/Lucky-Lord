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

public class ContactUsModel implements ContactUsInterface {



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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
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


    private BigInteger Id;
    private String Guid;
    private String Title;
    private String Description;
    private String Tel;
    private String Email;
    private String CreatedAt;
    private String UpdatedAt;


    private Context cntx;


    public ContactUsModel()
    {
        Id = null;
        Guid = null;
        Title = null;
        Email = null;
        Description = null;
        CreatedAt = null;
        UpdatedAt = null;
        Tel = null;

        this.cntx = null;
    }

    public ContactUsModel(Context cntx)
    {
        Id = null;
        Guid = null;
        Title = null;
        Email = null;
        Description = null;
        CreatedAt = null;
        UpdatedAt = null;
        Tel = null;

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