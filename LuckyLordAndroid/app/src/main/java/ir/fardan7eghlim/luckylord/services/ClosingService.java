package ir.fardan7eghlim.luckylord.services;

/**
 * Created by Meysam on 5/2/2017.
 */


import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;
import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.ChatController;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.utils.SoundModel;
import ir.fardan7eghlim.luckylord.utils.Utility;

public class ClosingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        SessionModel session = new SessionModel(getApplicationContext());
        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) == 1)
        {
            DatabaseHandler db=new DatabaseHandler(getApplicationContext());
            db.deleteFarsiWords();
            session.saveItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS,0);
//            Utility.displayToast(getApplicationContext(),"ذخیره پایگاه ناقص بود",Toast.LENGTH_SHORT);

        }
        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) == 2)
        {
//            Utility.displayToast(getApplicationContext(),"ذخیره پایگاه تمام شده",Toast.LENGTH_SHORT);
        }

        // Handle application closing
//        Utility.displayToast(getApplicationContext(),getApplicationContext().getText(R.string.msg_AppExited).toString(),Toast.LENGTH_SHORT);
        // Destroy the service
        SoundModel.stopMusic();


        //meysam - added in 13961115
        if(ChatService.cc != null && ChatService.cc.isConnected())
        {
            ChatService.cc.closeWebSocket();
        }

        ChatService.cc = null;
        ChatService.SCS = null;

        stopSelf();
    }
}