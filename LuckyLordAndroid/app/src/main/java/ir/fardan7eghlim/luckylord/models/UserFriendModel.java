package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

import ir.fardan7eghlim.luckylord.controllers.ChatController;
import ir.fardan7eghlim.luckylord.interfaces.MessageInterface;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.services.ChatService;

/**
 * Created by Meysam on 12/20/2016.
 */

public class UserFriendModel {


    public static Integer USER_FRIEND_STATUS_NORMAL=0;
    public static Integer USER_FRIEND_STATUS_REQUESTED=1;
    public static Integer USER_FRIEND_STATUS_ACCEPTED=2;
    public static Integer USER_FRIEND_STATUS_BLOCKED=3;
    public static Integer USER_FRIEND_STATUS_DECLINED=4;
    public static Integer USER_FRIEND_STATUS_DELETED=5;

    public static String TAG_USER_FRIEND_STATUS_NORMAL="user_friend_status_normal";
    public static String TAG_USER_FRIEND_STATUS_REQUESTED="user_friend_status_requested";
    public static String TAG_USER_FRIEND_STATUS_ACCEPTED="user_friend_status_accepted";
    public static String TAG_USER_FRIEND_STATUS_BLOCKED="user_friend_status_blocked";
    public static String TAG_USER_FRIEND_STATUS_DECLINED="user_friend_status_declined";
    public static String TAG_USER_FRIEND_STATUS_DELETED="user_friend_status_deleted";

    public BigInteger getUserFriendId() {
        return UserFriendId;
    }

    public void setUserFriendId(BigInteger userFriendId) {
        UserFriendId = userFriendId;
    }

    public String getUserFriendGuid() {
        return UserFriendGuid;
    }

    public void setUserFriendGuid(String userFriendGuid) {
        UserFriendGuid = userFriendGuid;
    }

    public BigInteger getUserId() {
        return UserId;
    }

    public void setUserId(BigInteger userId) {
        UserId = userId;
    }

    public BigInteger getFriendId() {
        return FriendId;
    }

    public void setFriendId(BigInteger friendId) {
        FriendId = friendId;
    }

    public Integer getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(Integer userStatus) {
        UserStatus = userStatus;
    }

    public Integer getFriendStatus() {
        return FriendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        FriendStatus = friendStatus;
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

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    private BigInteger UserFriendId;
    private String UserFriendGuid;
    private BigInteger UserId;
    private BigInteger FriendId;
    private Integer UserStatus;
    private Integer FriendStatus;
    private String CreatedAt;
    private String UpdatedAt;
    private Context cntx;


    public UserFriendModel()
    {
        UserFriendId = null;
        UserFriendGuid = null;
        UserId = null;
        FriendId = null;
        UserStatus = null;
        FriendStatus = null;
        CreatedAt = null;
        UpdatedAt = null;

        this.cntx = null;
    }

    public UserFriendModel(Context cntx)
    {

        UserFriendId = null;
        UserFriendGuid = null;
        UserId = null;
        FriendId = null;
        UserStatus = null;
        FriendStatus = null;
        CreatedAt = null;
        UpdatedAt = null;

        this.cntx = cntx;

    }

    public static void syncFriendsStatusInDb(ArrayList<UserFriendModel> changedUserFriends, Context cntx)
    {
        DatabaseHandler db = new DatabaseHandler(cntx);

//        ArrayList<String> ids = new ArrayList<>();
//        ArrayList<UserModel> users = db.getUsersByIds(ids);
        for(int i = 0; i < changedUserFriends.size(); i++)
        {
            UserFriendModel ufm = changedUserFriends.get(i);
            UserModel user = db.getUserById(ufm.getFriendId());
            db.deleteUserById(user.getId());
//            if(ufm.getFriendStatus() == UserFriendModel.USER_FRIEND_STATUS_BLOCKED)
//            {
//                //اینجا فعلا کاری نکنیم و بگوییم دوست هست فعلا
//            }
            if(ufm.getFriendStatus().equals(UserFriendModel.USER_FRIEND_STATUS_ACCEPTED))
            {
                user.setFriendshipStatus(ufm.getUserStatus());
                db.addUser(user, UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);
            }
//            if(ufm.getFriendStatus() == UserFriendModel.USER_FRIEND_STATUS_DECLINED)
//            {
//
//            }
//            users.add(user);

//            ids.add(changedUserFriends.get(i).getUserFriendId().toString());
        }

//        db.deleteUsersById(users);
//        db.saveUsers(users);
//        for(int i = 0; i < users.size(); i++)
//        {
//            users.get(i).set
//        }
    }

    public static ArrayList<UserModel> sortListByOnline(ArrayList<UserModel> friends)
    {

        if(ChatService.cc == null)
            return friends;

        ArrayList<UserModel> online = new ArrayList<>();
        ArrayList<UserModel> offline = new ArrayList<>();

        for(int i=0;i<friends.size();i++)
        {
            if (ChatService.cc.isUserOnline(friends.get(i).getUserName()))
            {
                online.add(friends.get(i));
            }
            else
            {
                offline.add(friends.get(i));
            }
        }

        online.addAll(offline);
        return  online;
    }

}