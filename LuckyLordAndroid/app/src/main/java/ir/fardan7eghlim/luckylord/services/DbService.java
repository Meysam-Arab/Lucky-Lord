package ir.fardan7eghlim.luckylord.services;

import android.app.IntentService;
import android.content.Intent;

import java.util.Scanner;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.WordModel;

public class DbService extends IntentService {
//    public static final String PARAM_IN_MSG = "imsg";
//    public static final String PARAM_OUT_MSG = "omsg";

    public DbService() {
        super("DbService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
        db.fillTable();

    }

}
