package ir.fardan7eghlim.luckylord.services;

/**
 * Created by Meysam on 5/2/2017.
 */


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.android.gms.phenotype.Flag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.ChatController;
import ir.fardan7eghlim.luckylord.models.ChatModel;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UserFriendModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.message.ChatActivity;
import ir.fardan7eghlim.luckylord.views.settings.SettingAboutUsActivity;
import ir.fardan7eghlim.luckylord.views.user.FriendListActivity;
import ir.fardan7eghlim.luckylord.views.user.UserProfileActivity;

public class ChatService extends Service {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    public static ChatController cc;
    public static ChatService SCS;
    public static Boolean isRunning;
    public DatabaseHandler db;
    NotificationManager mNotificationManager;


    @Override
    public void onCreate() {
        // To avoid cpu-blocking, we create a background handler to run our service
        HandlerThread thread = new HandlerThread("ChatService",
                Process.THREAD_PRIORITY_BACKGROUND);
        // start the new handler thread
        thread.start();

        mServiceLooper = thread.getLooper();
        // start the service using the background handler
        mServiceHandler = new ServiceHandler(mServiceLooper);

        SCS =this;
        isRunning = false;

        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("chat_service_broadcast"));

        db = new DatabaseHandler(getApplicationContext());
        cc = new ChatController(ChatService.this);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "chat start command", Toast.LENGTH_SHORT).show();

        if(isRunning == null || !isRunning)
        {
            isRunning = true;
            // call a new service handler. The service ID can be used to identify the service
            Message message = mServiceHandler.obtainMessage();
            message.arg1 = startId;
            mServiceHandler.sendMessage(message);


        }
        return START_STICKY;

    }

    // Object responsible for
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Well calling mServiceHandler.sendMessage(message); from onStartCommand,
            // this method will be called.

            // Add your cpu-blocking activity here
            if(cc != null)
                cc.connectWebSocket(new SessionModel(getApplicationContext()).getCurrentUser().getUserName());
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(String message, String userName, String[] messages)
    {

//        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

        SessionModel session = new SessionModel(getApplicationContext());
        if(!session.getBoolianItem(SessionModel.KEY_NOTIFICATION_SOUND,true))
        {
            return;
        }
//        //meysam - do not show notification if user is visitor
//        if(session.getCurrentUser().getUserName() == null)
//        {
//            return;
//        }
        ////////////////////////////////
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "chat_channel_1");

        mBuilder.setSmallIcon(R.drawable.b_message);
        mBuilder.setContentTitle("چت");
//        mBuilder.setContentText(userName + " : "+convertActiveEmoji(message));
        mBuilder.setTicker("چت");
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        if(new SessionModel(getApplicationContext()).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.IS_PLAYING))
        {
            mBuilder.setSound(soundUri);
        }
        mBuilder.setAutoCancel(true);
        mBuilder.setLights(Color.GREEN, 100, 3000); // will blink
        mBuilder.setVibrate(new long[]{100, 500});
        mBuilder.setLargeIcon( BitmapFactory.decodeResource(getResources(), R.drawable.b_message));

        if(userName != null)
        {
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));


            mBuilder.setContentText(userName + " : "+convertActiveEmoji(message));

            Intent resultIntent;

            UserModel opponentUser = db.getUserByUserName(userName);
            if(opponentUser.getProfilePicture() == null)
            {
                resultIntent = new Intent(this, UserProfileActivity.class);
                resultIntent.putExtra("EXTRA_User_Name",userName);
            }
            else
            {
                if(session.getCurrentUser().getAllowChat().equals("1"))
                {
                    //meysam - chat is allowed
                    resultIntent = new Intent(this, ChatActivity.class);
                    resultIntent.putExtra("opponent_user_name", userName);
                    resultIntent.putExtra("opponent_avatar", opponentUser.getProfilePicture());
                }
                else
                {
                    //meysam - never will be called....
                    resultIntent = new Intent(this, UserProfileActivity.class);
                    resultIntent.putExtra("EXTRA_User_Name",userName);
                }


            }


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(UserProfileActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, uniqueInt, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
        }
        else
        {
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();

            String[] events = messages;
            // Sets a title for the Inbox in expanded layout
            inboxStyle.setBigContentTitle("چت");
            inboxStyle.setSummaryText(events[0]);

            // Moves events into the expanded layout
            for (int i=0; i < events.length; i++) {
                inboxStyle.addLine(events[i]);
            }
            // Moves the expanded layout object into the notification object.
            mBuilder.setStyle(inboxStyle);

            Intent resultIntent = new Intent(this, FriendListActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.putExtra("list_type",UserFriendModel.TAG_USER_FRIEND_STATUS_ACCEPTED);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(UserProfileActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, uniqueInt, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
        }

        // notificationID allows you to update the notification later on.
        int notificationID = new Random().nextInt(1000);
        mNotificationManager.notify(notificationID, mBuilder.build());
//////////////////////////////////////////////

    }

    @Nullable
    @Override
    public boolean stopService(Intent name) {

//        Toast.makeText(this,"service stopped",Toast.LENGTH_SHORT);

        mNotificationManager.cancelAll();

        if(cc != null)
            cc.closeWebSocket();
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
//        Toast.makeText(this,"service destroyed",Toast.LENGTH_SHORT);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);

        if(cc != null)
        {
            cc.closeWebSocket();
            cc=null;
        }

        mNotificationManager.cancelAll();

        SCS = null;
        isRunning = null;
        super.onDestroy();
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (intent.getStringExtra("receivedMessage") != null) {

                SessionModel session = new SessionModel(context);
                if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
                        session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
                {
                    showNotification(intent.getStringExtra("receivedMessage").substring(1),intent.getStringExtra("senderUserName"),null);

                    String currentDateTime = Calendar.getInstance().getTime().toString();
                    String userName = intent.getStringExtra("senderUserName");
                    int type = 0;
                    String message = intent.getStringExtra("receivedMessage");

                    ChatModel chat = new ChatModel(userName,currentDateTime,type,message,null);
                    db.addChat(chat);
                }

            }
            if (intent.getBooleanExtra("error",false)) {
                Utility.displayToast(ChatService.this,"خطای نامشخص در چت رخ داد - چت غیر فعال شد", Toast.LENGTH_LONG);
                ChatService.this.stopSelf();

            }
            if (intent.getBooleanExtra("closed",false)) {
                Utility.displayToast(ChatService.this,"ارتباط چت قطع شد - چت غیر فعال شد", Toast.LENGTH_LONG);
                ChatService.this.stopSelf();

            }
            if (intent.getBooleanExtra("change",false)) {
                // meysam - add outline notification to respected place...
                if(intent.hasExtra("onlineFriends"))
                {
//                    // meysam - show list of Online friends...
//                    ArrayList<String> onlineFriends = intent.getStringArrayListExtra("onlineFriends");
//                    String[] messages = new String[onlineFriends.size()];
//
//                    if(onlineFriends.size() == 1)
//                    {
//                        showNotification( " آنلاین می باشد ",onlineFriends.get(0),null);
//                    }
//                    else
//                    {
//                        for(int i = 0;i<onlineFriends.size();i++)
//                        {
//                            messages[i] = onlineFriends.get(i) +" آنلاین می باشد " ;
//                        }
//                        showNotification( null,null,messages);
//
//                    }
//
//
////                    if(messages.length == 1)
////                        showNotification( messages[0],onlineFriends.get(0),null);
////                    else

                }
                if(intent.hasExtra("friendJoined"))
                {
//                    // meysam - show joined friend...
//                    String joinedFriend = intent.getStringExtra("friendJoined");
//                    StringBuilder sb = new StringBuilder();
//
////                    sb.append(joinedFriend);
//                    sb.append(" آنلاین می باشد ");
//                    sb.append("\n");
//
//                    showNotification(sb.toString(),joinedFriend,null);

                }
                if(intent.hasExtra("friendLeaved"))
                {
                    //meysam - do nothing...
//                    // meysam - show leaved friend...
//                    String leavedFriend = intent.getStringExtra("friendLeaved");
//                    StringBuilder sb = new StringBuilder();
//
////                    sb.append(leavedFriend);
//                    sb.append(" آفلاین شد ");
//                    sb.append("\n");
//
//                    showNotification(sb.toString(),leavedFriend,null);


                }

            }
        }

    };

    private String convertActiveEmoji(String message){
        //depend on contaion of message
        if(message.equals("#0s1A##")){
            return "لایک";
        }else if(message.equals("#0e1v##")){
            return "قلب";
        }else if(message.equals("#fs1n##")){
            return "بوس";
        }else if(message.equals("#pQ1m##")){
            return "خوشم نیومد";
        }else{
            return message;
        }
    }

}