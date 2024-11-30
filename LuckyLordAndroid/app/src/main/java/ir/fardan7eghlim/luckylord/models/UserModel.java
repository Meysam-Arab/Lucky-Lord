package ir.fardan7eghlim.luckylord.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.BoringLayout;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;
import ir.fardan7eghlim.luckylord.interfaces.UserInterface;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;

/**
 * Created by Meysam on 12/20/2016.
 */

public class UserModel implements UserInterface {


    //meysam - 13960727
    public static String TABLE_USERS_FRIENDS_TAG = "UsersFriendsTag";
    ////////////////////

    public static String Female= "female";//1
    public static String  Male= "male";//0

    public static Integer  Public= 1;
    public static Integer  Personal= 0;

    public static Integer USER_RANK_TYPE_LUCK= 0;
    public static Integer USER_RANK_TYPE_LEVEL= 1;

    public static final String TAG_USER_RANK_TYPE_LUCK= "rank_user_luck";
    public static final String TAG_USER_RANK_TYPE_LEVEL= "rank_user_level";



    public static Integer  RANGE_WHOLE=0;
    public static Integer  RANGE_VICINITY=1;
    public static Integer  RANGE_DAILY=2;
    public static Integer  RANGE_WEEKLY=3;



    public static Integer  REWARD_AMOUNT_LEVEL_SCORE_NORMAL_QUESTION=1;
    public static Integer  REWARD_AMOUNT_LEVEL_SCORE_DARHAM_TABLE=10;
    public static Integer  REWARD_AMOUNT_LEVEL_SCORE_CROSS_TABLE=7;
    public static Integer  REWARD_AMOUNT_LEVEL_SCORE_GUESS_WORD=5;
    public static Integer  REWARD_AMOUNT_LEVEL_SCORE_FIND_WORD_TABLE=8;
    public static Integer  REWARD_AMOUNT_LEVEL_SCORE_QUESTION_MATCH=10;

    protected UserModel(Parcel in) {
        Guid = in.readString();
        Token = in.readString();
        Email = in.readString();
        Tel = in.readString();
        UserName = in.readString();
        Password = in.readString();
        InviteCode = in.readString();
        ProfilePicture = in.readString();
        Gender = in.readString();
        VisitorUserName = in.readString();
        BirthDate = in.readString();
        EggDateTime = in.readString();
//        TotalMatchCount = in.readString();
//        WinMatchCount = in.readString();
        Reward = in.readString();
        FriendshipStatus = in.readInt();
    }



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

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getVisitorUserName() {
        return VisitorUserName;
    }
    public String getVisitorUserNameShow() {
        int temp = VisitorUserName.substring(8).hashCode();
        if(temp < 0)
            temp = temp * -1;
        String result = "Visitor_"+temp;

        return result;
    }
    public void setVisitorUserName(String visitorUserName) {
        VisitorUserName = visitorUserName;
    }

    public Integer getLuck() {
        return Luck;
    }

    public void setLuck(Integer luck) {
        Luck = luck;
    }

    public Integer getHazel() {
        return Hazel;
    }

    public void setHazel(Integer hazel) {
        Hazel = hazel;
    }

    public String getEggDateTime() {
        return EggDateTime;
    }

    public void setEggDateTime(String eggDateTime) {
        EggDateTime = eggDateTime;
    }

    public Boolean getEggScore() {
        return EggScore;
    }

    public void setEggScore(Boolean eggScore) {
        EggScore = eggScore;
    }


    public String getReward() {
        return Reward;
    }

    public void setReward(String reward) {
        Reward = reward;
    }

    public Integer getRank() {
        return Rank;
    }

    public void setRank(Integer rank) {
        Rank = rank;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

//    public String getTotalMatchCount() {
//        return TotalMatchCount;
//    }
//
//    public void setTotalMatchCount(String totalMatchCount) {
//        TotalMatchCount = totalMatchCount;
//    }
//
//    public String getWinMatchCount() {
//        return WinMatchCount;
//    }
//
//    public void setWinMatchCount(String winMatchCount) {
//        WinMatchCount = winMatchCount;
//    }

    public Integer getFriendshipStatus() {
        return FriendshipStatus;
    }

    public void setFriendshipStatus(Integer friendshipStatus) {
        FriendshipStatus = friendshipStatus;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        UpdateDateTime = updateDateTime;
    }

    public Integer getLevelScore() {
        return LevelScore;
    }

    public void setLevelScore(Integer levelScore) {
        LevelScore = levelScore;
    }

    public String getCups() {
        return Cups;
    }

    public void setCups(String cups) {
        Cups = cups;
    }

    public Integer getFriendsCount() {
        return FriendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        FriendsCount = friendsCount;
    }

    public String getAllowChat() {
        return AllowChat;
    }

    public void setAllowChat(String allowChat) {
        AllowChat = allowChat;
    }


    private BigInteger Id;
    private String Guid;
    private String Token;
    private String Email;
    private String Tel;
    private String UserName;
    private String Password;
    private String InviteCode;
    private String ProfilePicture;
    private String Gender;
    private String VisitorUserName;
    private Integer Luck;
    private Integer Hazel;
    private String BirthDate;
    private String EggDateTime;
//    private String TotalMatchCount;
//    private String WinMatchCount;
    private Boolean EggScore;
    private Boolean IsActive;
    private Integer FriendshipStatus;
    private String UpdateDateTime;
    private Integer LevelScore;
    private String Cups;
    private Integer FriendsCount;
    private String AllowChat;

    ////external fields that are neccesaary
    private String Reward;
    private Integer Rank;


    public UserModel( BigInteger id, String guid, String token, String email, String userName, String password, String inviteCode, String profilePicture, String gender, Integer luck, Integer hazel, String tel, Boolean eggScore, Boolean isActive, String eggDateTime, String visitorUserName, String birthDate, Integer levelScore, String cups, Integer friendsCount, String allowChat) {

        Id = id;
        Guid = guid;
        Token = token;
        Email = email;
        UserName = userName;
        Password = password;
        InviteCode = inviteCode;
        ProfilePicture = profilePicture;
        Gender = gender;
        Tel = tel;
        VisitorUserName = visitorUserName;
        Hazel = hazel;
        Luck = luck;
        BirthDate = birthDate;
        EggDateTime = eggDateTime;
        EggScore = eggScore;
        IsActive = isActive;
        Reward = null;
        FriendshipStatus = null;
        UpdateDateTime = null;
        LevelScore = levelScore;
        Cups = cups;
        FriendsCount = friendsCount;
        AllowChat = allowChat;

    }

    public UserModel()
    {
        Id = null;
        Guid = null;
        Token = null;
        Email = null;
        UserName = null;
        Password = null;
        InviteCode = null;
        ProfilePicture = null;
        Gender = null;
        Tel = null;
        IsActive = null;
        VisitorUserName = null;
        Hazel = null;
        Luck = null;
        BirthDate = null;
        EggDateTime = null;
        EggScore = null;
        Reward = null;
        FriendshipStatus = null;
        UpdateDateTime = null;
        LevelScore = null;
        Cups = null;
        FriendsCount = null;
        AllowChat = null;

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

    public static String getVisitorHashedName(String rawName)
    {
        if(rawName.length()< 8)
        {
            return rawName;
        }
        else if(rawName.substring(0,8).equals("Visitor_"))
        {
            int temp = rawName.substring(8).hashCode();
            if(temp < 0)
                temp = temp * -1;
            String result = "Visitor_"+temp;
            return result;
        }
        else
            {
            return rawName;
        }
    }

    public static Integer setUserFriendshipStatusWRTDb(UserModel user, Context cntx)
    {
        DatabaseHandler db = new DatabaseHandler(cntx);
        ArrayList<UserModel> users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getId().equals(user.getId()))
                return users.get(i).getFriendshipStatus();
        }
        users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_REQUESTED);
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getId().equals(user.getId()))
                return users.get(i).getFriendshipStatus();
        }
        users = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_NORMAL);
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getId().equals(user.getId()))
                return users.get(i).getFriendshipStatus();
        }
        return null;
    }

    public static ArrayList<UserModel> setMassFriendshipStatus(ArrayList<UserModel> users, Integer friendshipStatus)
    {
        for(int i=0;i<users.size();i++)
        {
            users.get(i).setFriendshipStatus(friendshipStatus);
        }
        return users;
    }

    public static void saveListFriendWRTStatus(ArrayList<UserModel> users, String friendshipStatus, Boolean deleteOld, Context cntx)
    {
        DatabaseHandler db = new DatabaseHandler(cntx);
        db.saveUsers(users,friendshipStatus,deleteOld);
    }

    public String getCommaSeparatedCupIndexes()
    {
        StringBuilder result = new StringBuilder();
        String temp = getCups();
        String[] arrayCups = temp.replaceAll("\\s+","").split("(?!^)");

        for(int i = 0; i < arrayCups.length; i++)
        {
            result.append(i);
           if(i != (arrayCups.length-1))
           {
               result.append(",");
           }
        }

        return result.toString();

    }
}
