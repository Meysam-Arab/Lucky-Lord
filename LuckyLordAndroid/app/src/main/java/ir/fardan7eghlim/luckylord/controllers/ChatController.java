package ir.fardan7eghlim.luckylord.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.telecom.Connection;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.AppConfig;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.hazels.HazelMatchIndexActivity;

/**
 * Created by Meysam on 12/21/2016.
 */

public class ChatController extends Observable {

    public Context cntx = null;
    private Integer TIMEOUT = 10000;

    private String senderCode;

    private DatabaseHandler db;
    private String opponentUserName;
    private SessionModel session;

    private ArrayList<String> onlineFriends;
//    private ArrayList<String> onlineUsers;

    private WebSocketClient mWebSocketClient;
    public ChatController(Context context, String targetOpponentUserName)
    {
        this.cntx = context;
        this.senderCode = null;

        db = new DatabaseHandler(cntx);
        onlineFriends = null;
        opponentUserName = targetOpponentUserName;

        session = new SessionModel(cntx);

    }

    public ChatController(Context context)
    {
        this.cntx = context;
        this.senderCode = null;

        db = new DatabaseHandler(cntx);
        onlineFriends = null;
        opponentUserName = null;

        session = new SessionModel(cntx);

    }

    ///////////////////////////////////////////////////////meysam - websocket lib//////////////////////////



    public void connectWebSocket(final String userName) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        URI uri;
        try {
            uri = new URI(AppConfig.Chat_Host_IP+":"+AppConfig.Chat_Host_Port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }

            @Override
            public void onMessage(String s) {
                // meysam - send received information to chat activity by broadcast...

                JSONObject jsonMessage = null;
                try {
                    jsonMessage = new JSONObject(s);

                if(jsonMessage != null)
                {
                    if(jsonMessage.has("cmd"))
                    {

                        String tmp = jsonMessage.getString("cmd");

                        if(tmp != null && tmp.equals("im user"))
                        {

                            JSONObject tmpMessage = new JSONObject(jsonMessage.getString("data"));
                            String receivedMessage = tmpMessage.getString("data");
                            String senderUserName = Utility.unicodeToUft8(tmpMessage.getString("sender"));

                            //meysam - added in 13970119
                            //if user wasn't shown online ... add it to online users list...
                            if(!onlineFriends.contains(senderUserName))
                                onlineFriends.add(senderUserName);
                            //////////////////////////////

                            Intent tintent;
                            if(opponentUserName != null)
                                tintent = new Intent("chat_activity_broadcast");
                            else
                                tintent = new Intent("chat_service_broadcast");
                            tintent.putExtra("receivedMessage", receivedMessage);
                            tintent.putExtra("senderUserName", senderUserName);

                            LocalBroadcastManager.getInstance(cntx).sendBroadcast(tintent);

                        }
                        if(tmp != null && tmp.equals("connect"))
                        {
                            String receivedMessage = jsonMessage.getString("data");
                            senderCode = receivedMessage;

                            JSONObject messageToSend = new JSONObject();

                            try {
                                messageToSend.put("cmd", "add user");
                                messageToSend.put("data", userName);
                                messageToSend.put("sender", senderCode);
                                messageToSend.put("broadcast", false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mWebSocketClient.send(messageToSend.toString());

                        }
                        if(tmp != null && tmp.equals("user list"))
                        {
                            if(onlineFriends == null)
                            {
                                String tmpOnlineUsers = new JSONObject(jsonMessage.getString("data")).getString("users");
                                ArrayList<String> onlineUsers = extractOnlineUserNamesFromString(tmpOnlineUsers);
                                onlineFriends = new ArrayList<>();
                                ArrayList<UserModel> allFriends = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);

                                onlineUsers.removeAll(Arrays.asList(null,""));

                                if(session.getCurrentUser().getUserName() != null )
                                {

                                for(UserModel item : allFriends){
                                    for(String stringItem : onlineUsers){
                                        if(item.getUserName().equals(stringItem))
                                        {
                                            onlineFriends.add(stringItem);
                                        }
                                    }

                                }


                                }

                                if(opponentUserName != null && onlineFriends.contains(opponentUserName))
                                {
                                    onlineFriends.remove(opponentUserName);
                                    //meysam - opponent is online
                                    Intent intent = new Intent("chat_activity_broadcast");
                                    intent.putExtra("status", true);
                                    LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
                                }


                                if(onlineFriends.size() > 0)
                                {
                                    Intent tintent;
                                    if(opponentUserName != null)
                                        tintent = new Intent("chat_activity_broadcast");
                                    else
                                        tintent = new Intent("chat_service_broadcast");

                                    tintent.putExtra("change", true);
                                    tintent.putExtra("onlineFriends", onlineFriends);
                                    LocalBroadcastManager.getInstance(cntx).sendBroadcast(tintent);
                                }

                            }
                            else
                            {
                                //meysam - check if any user left...
                            }

                        }
                        if(tmp != null && tmp.equals("user joined"))
                        {

                            JSONObject tmpMessage = new JSONObject(jsonMessage.getString("data"));
                            String joinedUser = Utility.unicodeToUft8(tmpMessage.getString("username"));

                            Boolean isFriend = false;
                            ArrayList<UserModel> allFriends = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);
                            for(UserModel item : allFriends){
                                    if(item.getUserName().equals(joinedUser))
                                    {
//                                        onlineFriends.add(joinedUser);
                                        isFriend = true;
                                    }
                            }

                            if(!onlineFriends.contains(joinedUser))
                            {
                                onlineFriends.add(joinedUser);
                            }


                            if(opponentUserName != null && joinedUser.equals(opponentUserName))
                            {
                                //meysam - opponent is online
                                Intent intent = new Intent("chat_activity_broadcast");
                                intent.putExtra("status", true);
                                LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
                            }

//                            if(onlineFriends.contains(joinedUser))
                            if(isFriend)
                            {
                                Intent tintent;
                                if(opponentUserName != null)
                                    tintent = new Intent("chat_activity_broadcast");
                                else
                                    tintent = new Intent("chat_service_broadcast");

                                tintent.putExtra("change", true);
                                tintent.putExtra("friendJoined", joinedUser);
                                LocalBroadcastManager.getInstance(cntx).sendBroadcast(tintent);
                            }

                        }
                        if(tmp != null && tmp.equals("user left"))
                        {

                            JSONObject tmpMessage = new JSONObject(jsonMessage.getString("data"));
                            String leavedUser = Utility.unicodeToUft8(tmpMessage.getString("username"));

                            Boolean isFriend = false;
                            ArrayList<UserModel> allFriends = db.getUsersByTag(UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);
                            for(UserModel item : allFriends){
                                if(item.getUserName().equals(leavedUser))
                                {
//                                        onlineFriends.add(joinedUser);
                                    isFriend = true;
                                }
                            }

                            if(opponentUserName != null && leavedUser.equals(opponentUserName))
                            {
                                //meysam - opponent is online
                                Intent intent = new Intent("chat_activity_broadcast");
                                intent.putExtra("status", false);
                                LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
                            }

//                            if(onlineFriends.contains(leavedUser))
                            if(isFriend)
                            {
                                //meysam - a friend became offline...
                                Intent tintent;
                                if(opponentUserName != null)
                                    tintent = new Intent("chat_activity_broadcast");
                                else
                                    tintent = new Intent("chat_service_broadcast");

                                tintent.putExtra("change", true);
                                tintent.putExtra("friendLeaved", leavedUser);
                                LocalBroadcastManager.getInstance(cntx).sendBroadcast(tintent);
                            }

                            if(onlineFriends.contains(leavedUser))
                                onlineFriends.remove(leavedUser);

                        }
                    }
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onMessage(ByteBuffer bytes) {
                final ByteBuffer message = bytes;
            }

            @Override
            public void onClose(int i, String s, boolean b) {

                Intent tintent = new Intent("chat_activity_broadcast");

                tintent.putExtra("closed", true);
                LocalBroadcastManager.getInstance(cntx).sendBroadcast(tintent);
            }

            @Override
            public void onError(Exception e) {
                Intent tintent = new Intent("chat_activity_broadcast");

                tintent.putExtra("error", true);
                LocalBroadcastManager.getInstance(cntx).sendBroadcast(tintent);
            }
        };
        mWebSocketClient.setConnectionLostTimeout(TIMEOUT);
//        for (int retries = 0; retries < 3; retries++) {
            try
            {
                mWebSocketClient.connect();
            }
            catch (WebsocketNotConnectedException ex)
            {
                //meysam - can not connect
                Utility.generateLogError(ex);

            }
//        }

    }

    public void sendMessage(String opponentUserName,String message)
    {
        SessionModel session = new SessionModel(cntx.getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        JSONObject messageToSend = new JSONObject();
        JSONObject dataObject = new JSONObject();

        try {

            dataObject.put("data", message);
            dataObject.put("to", opponentUserName);
            if(senderCode == null)
            {
                messageToSend.put("sender", session.getCurrentUser().getUserName());
                dataObject.put("sender",  session.getCurrentUser().getUserName());
            }
            else
            {
                messageToSend.put("sender", senderCode);
                dataObject.put("sender",  senderCode);
            }
            messageToSend.put("data", dataObject);
            messageToSend.put("cmd", "im user");//im user, chat message, new message
            messageToSend.put("broadcast", false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mWebSocketClient.send(messageToSend.toString());
    }

    public void closeWebSocket()
    {
        if(mWebSocketClient != null)
        {
            if(mWebSocketClient.isOpen())
            {
                mWebSocketClient.close();
            }
        }
    }


    private ArrayList<String> extractOnlineUserNamesFromString(String onlineUserNames)
    {
        StringBuilder sb = new StringBuilder(onlineUserNames);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);

        onlineUserNames = sb.toString();

        ArrayList<String> result = new ArrayList<>();
        ArrayList<String>  temp = new ArrayList<String>(Arrays.asList(onlineUserNames.split("\\s*,\\s*")));
        for(String item : temp)
        {
            sb = new StringBuilder(item);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length()-1);

            result.add(Utility.unicodeToUft8(sb.toString()));
        }

        return result;
    }

    public void setContext(Context newContext)
    {
        cntx = newContext;
    }
    public void setOpponentUserName(String inputOpponentUserName)
    {
        opponentUserName = inputOpponentUserName;
    }

    public Boolean isUserOnline(String userName)
    {

            if(onlineFriends != null && onlineFriends.contains(userName))
                return true;
            else
                return false;

    }

    public boolean isConnected()
    {
        if(mWebSocketClient == null)
            return false;
        if(mWebSocketClient.isClosed())
            return false;
        return true;
    }
}
