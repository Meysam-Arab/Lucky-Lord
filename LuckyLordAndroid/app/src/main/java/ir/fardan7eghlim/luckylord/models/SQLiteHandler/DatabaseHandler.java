package ir.fardan7eghlim.luckylord.models.SQLiteHandler;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.ChatModel;
import ir.fardan7eghlim.luckylord.models.QuestionModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.UniversalMatchModel;
import ir.fardan7eghlim.luckylord.models.UserModel;
import ir.fardan7eghlim.luckylord.models.WordModel;

/**
 * Created by Meysam on 21/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context cntx;

    // All Static variables
    // Database Version
    //in version 4 we added allowchat column
    private static final int DATABASE_VERSION = 4;//meysam - last count added for app version 4.0.0

    // Database Name
    private static final String DATABASE_NAME = "luckyLordDB";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cntx=context;
    }



    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            createTables(db);
    }

    public void createTables(SQLiteDatabase db)
    {
        String CREATE_TAG_QUESTION_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_TAG_QUESTIONS + "("
                + KEY_TAG_QUESTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TAG_QUESTIONS_GUID + " TEXT,"
                + KEY_TAG_QUESTIONS_ANSWER + " TEXT,"
                + KEY_TAG_QUESTIONS_ANSWERCELLS + " TEXT,"
                + KEY_TAG_QUESTIONS_ANSWEREDLETTERS + " TEXT,"
                + KEY_TAG_QUESTIONS_ANSWEREDCELLS + " TEXT,"
                + KEY_TAG_QUESTIONS_CATEGORYID + " INTEGER,"
                + KEY_TAG_QUESTIONS_DESCRIPTION + " TEXT,"
                + KEY_TAG_QUESTIONS_FINALHAZELREWARD + " INTEGER,"
                + KEY_TAG_QUESTIONS_FINALLUCKREWARD + " INTEGER,"
                + KEY_TAG_QUESTIONS_MAXHAZEKREWARD + " INTEGER,"
                + KEY_TAG_QUESTIONS_MAXLUCKREWARD + " INTEGER,"
                + KEY_TAG_QUESTIONS_MINHAZELREWARD + " INTEGER,"
                + KEY_TAG_QUESTIONS_MINLUCKREWARD + " INTEGER,"
                + KEY_TAG_QUESTIONS_PENALTY + " INTEGER,"
                + KEY_TAG_QUESTIONS_ISANSWERED + " INTEGER,"
                + KEY_TAG_QUESTIONS_ISCORRECT+ " INTEGER,"
                + KEY_TAG_QUESTIONS_CREATEDAT + " TEXT,"
                + KEY_TAG_QUESTIONS_UPDATEDAT + " TEXT,"
                + KEY_TAG_QUESTIONS_POSITION_CODE + " TEXT,"
                + KEY_TAG_QUESTIONS_QUESTION_POSITION + " TEXT,"
                + KEY_TAG_QUESTIONS_IMAGE + " BLOB,"
                + KEY_TAG_QUESTIONS_TAG + " TEXT" + ")";
        db.execSQL(CREATE_TAG_QUESTION_TABLE);

        String CREATE_FARSI_WORDS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FarsiWords + "("
                + KEY_FARSI_WORD_ID + " INTEGER PRIMARY KEY,"
                + KEY_FARSI_WORD_WORD + " TEXT,"
                + KEY_FARSI_WORD_WORD_NO_SPACE + " TEXT,"
                + KEY_FARSI_WORD_LENGTH + " INTEGER,"
                + KEY_FARSI_WORD_CATEGORY+ " INTEGER,"
                + KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE + " INTEGER" + ")";

        db.execSQL(CREATE_FARSI_WORDS_TABLE);

        String CREATE_MATCH_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_UNIVERSAL_MATCH + "("
                + KEY_UNIVERSAL_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_UNIVERSAL_MATCH_OPPONENT_USER_NAME + " TEXT,"
                + KEY_UNIVERSAL_MATCH_OPPONENT_CORRECT_COUNT + " TEXT,"
                + KEY_UNIVERSAL_MATCH_OPPONENT_AVATAR + " TEXT,"
                + KEY_UNIVERSAL_MATCH_OPPONENT_SPENT_TIME + " TEXT,"
                + KEY_UNIVERSAL_MATCH_SELF_CORRECT_COUNT + " TEXT,"
                + KEY_UNIVERSAL_MATCH_SELF_SPENT_TIME + " TEXT,"
                + KEY_UNIVERSAL_MATCH_WINNER_CODE + " INTEGER,"
                + KEY_UNIVERSAL_MATCH_IS_ENDED + " INTEGER,"
                + KEY_UNIVERSAL_MATCH_IS_NITRO + " INTEGER,"
                + KEY_UNIVERSAL_MATCH_IS_REWARD_LUCK + " INTEGER,"
                + KEY_UNIVERSAL_MATCH_BET + " TEXT,"
                + KEY_UNIVERSAL_MATCH_TYPE + " INTEGER,"
                + KEY_UNIVERSAL_MATCH_OPPONENT_TYPE + " INTEGER" + ")";
        db.execSQL(CREATE_MATCH_TABLE);


        String CREATE_USER_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_TAG_USERS + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_USERNAME + " TEXT,"
                + KEY_USER_LUCK + " INTEGER,"
                + KEY_USER_HAZEL + " INTEGER,"
                + KEY_USER_FRIENDSHIP_STATUS + " INTEGER,"
                + KEY_USER_TAG + " TEXT,"
                + KEY_USER_UPDATE_DATE_TIME + " TEXT,"
                + KEY_USER_AVATAR + " TEXT,"
                + KEY_USER_LEVEL + " INTEGER,"
                + KEY_USER_CUPS + " TEXT,"
                + KEY_USER_FRIENDS_COUNT + " INTEGER,"
                + KEY_USER_ALLOW_CHAT + " TEXT"
                + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_WORD_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_WORDS + "("
                + KEY_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_WORD_VALUE + " TEXT,"
                + KEY_WORD_LENGTH + " INTEGER,"
                + KEY_WORD_LENGTH_WITHOUT_SPACE + " INTEGER,"
                + KEY_WORD_ANSWERED_LETTERS + " TEXT,"
                + KEY_WORD_ANSWERED_LETTERS_POSITIONS + " TEXT,"
                + KEY_WORD_WRONG_ANSWERED_LETTERS + " TEXT,"
                + KEY_WORD_WRONG_ANSWERED_LETTERS_POSITIONS + " TEXT,"
                + KEY_WORD_ANSWER_LETTERS_POSITIONS + " TEXT,"
                + KEY_WORD_ALL_LETTERS_POSITIONS + " TEXT,"
                + KEY_WORD_ALL_LETTERS + " TEXT,"
                + KEY_WORD_CATEGORY+ " INTEGER,"
                + KEY_WORD_WORDS + " TEXT,"
                + KEY_WORD_TAG + " TEXT"
                + ")";
        db.execSQL(CREATE_WORD_TABLE);

        String CREATE_MESSAGE_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_CHATS + "("
                + KEY_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CHAT_USERNAME + " TEXT,"
                + KEY_CHAT_TEXT + " TEXT,"
                + KEY_CHAT_TYPE + " INTEGER,"
                + KEY_CHAT_DATETIME + " TEXT"
                + ")";
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    public void dropTables(SQLiteDatabase db)
    {

            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG_QUESTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FarsiWords);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIVERSAL_MATCH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATS);
            onCreate(db);
    }

    public void deleteAllTablerecords()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_TAG_QUESTIONS);
        db.execSQL("delete from "+ TABLE_WORDS);
        db.execSQL("delete from "+ TABLE_TAG_USERS);
        db.execSQL("delete from "+ TABLE_FarsiWords);
        db.execSQL("delete from "+ TABLE_UNIVERSAL_MATCH);
        db.execSQL("delete from "+ TABLE_CHATS);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
           dropTables(db);
            Intent intent = new Intent("main_activity_broadcast");
            // You can also include some extra data.
            intent.putExtra("logout", "true");
            LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
        }



//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG_QUESTIONS);
//
//        // Create tables again
//        onCreate(db);

//        switch(oldVersion) {
//            case 1:
//                db.execSQL(DATABASE_CREATE_color);
//                // we want both updates, so no break statement here...
//            case 2:
//                db.execSQL(DATABASE_CREATE_someothertable);
//        }

    }

    public void dropAll(Context cntx) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FarsiWords);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIVERSAL_MATCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATS);
        onCreate(db);
    }

    /////////////////////meysam - question table specifications - start/////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    // Contacts table name
    private static final String TABLE_TAG_QUESTIONS = "tagQuestions";

    // Contacts Table Columns names
    private static final String KEY_TAG_QUESTIONS_TAG = "tag";
    private static final String KEY_TAG_QUESTIONS_ID = "question_id";
    private static final String KEY_TAG_QUESTIONS_GUID = "question_guid";
    private static final String KEY_TAG_QUESTIONS_DESCRIPTION = "description";
    private static final String KEY_TAG_QUESTIONS_ANSWER = "answer";
    private static final String KEY_TAG_QUESTIONS_MAXHAZEKREWARD = "max_hazel_reward";
    private static final String KEY_TAG_QUESTIONS_MINHAZELREWARD = "min_hazel_reward";
    private static final String KEY_TAG_QUESTIONS_MAXLUCKREWARD = "max_luck_reward";
    private static final String KEY_TAG_QUESTIONS_MINLUCKREWARD = "min_luck_reward";
    private static final String KEY_TAG_QUESTIONS_FINALHAZELREWARD = "final_hazel_reward";
    private static final String KEY_TAG_QUESTIONS_FINALLUCKREWARD = "final_luck_reward";
    private static final String KEY_TAG_QUESTIONS_PENALTY = "penalty";
    private static final String KEY_TAG_QUESTIONS_ANSWEREDLETTERS = "answered_letters";
    private static final String KEY_TAG_QUESTIONS_ANSWEREDCELLS = "answered_cells";
    private static final String KEY_TAG_QUESTIONS_ANSWERCELLS = "answer_cells";
    private static final String KEY_TAG_QUESTIONS_IMAGE = "image";
    private static final String KEY_TAG_QUESTIONS_POSITION_CODE = "position_code";
    private static final String KEY_TAG_QUESTIONS_QUESTION_POSITION = "question_position";
    private static final String KEY_TAG_QUESTIONS_CATEGORYID = "category_id";
    private static final String KEY_TAG_QUESTIONS_CREATEDAT = "created_at";
    private static final String KEY_TAG_QUESTIONS_UPDATEDAT = "updated_at";
    private static final String KEY_TAG_QUESTIONS_ISCORRECT = "is_correct";
    private static final String KEY_TAG_QUESTIONS_ISANSWERED = "is_answered";

    /**
     * Storing user details in sqlite database
     * */
    public void addQuestion(QuestionModel question, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        byte[] byteArray = null;
        if(question.getImage() != null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            question.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }


        values.put(KEY_TAG_QUESTIONS_TAG, tag);
        if(question.getId() != null)
            values.put(KEY_TAG_QUESTIONS_ID, question.getId().toString());
        values.put(KEY_TAG_QUESTIONS_GUID, question.getGuid());
        values.put(KEY_TAG_QUESTIONS_CATEGORYID, question.getCategoryId());
        values.put(KEY_TAG_QUESTIONS_DESCRIPTION, question.getDescription());
        values.put(KEY_TAG_QUESTIONS_ANSWER, question.getAnswer());
        values.put(KEY_TAG_QUESTIONS_IMAGE, byteArray);
        values.put(KEY_TAG_QUESTIONS_PENALTY, question.getPenalty());
        values.put(KEY_TAG_QUESTIONS_FINALHAZELREWARD, question.getFinalHazelReward());
        values.put(KEY_TAG_QUESTIONS_FINALLUCKREWARD, question.getFinalLuckReward());
        values.put(KEY_TAG_QUESTIONS_MAXHAZEKREWARD, question.getMaxHazelReward());
        values.put(KEY_TAG_QUESTIONS_MAXLUCKREWARD, question.getMaxLuckReward());
        values.put(KEY_TAG_QUESTIONS_MINHAZELREWARD, question.getMinHazelReward());
        values.put(KEY_TAG_QUESTIONS_MINLUCKREWARD, question.getMinLuckReward());
        values.put(KEY_TAG_QUESTIONS_POSITION_CODE, question.getPositionCode());
        values.put(KEY_TAG_QUESTIONS_QUESTION_POSITION, question.getQuestionPosition());



        StringBuilder sb = new StringBuilder();
        if(question.getAnswerCells() != null &&
                question.getAnswerCells().size()>0)
        {
            for(String s: question.getAnswerCells()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWERCELLS, sb.toString());
        }



        if(question.getAnsweredCells() != null &&
                question.getAnsweredCells().size()>0)
        {
            sb = new StringBuilder();
            for(String s: question.getAnsweredCells()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWEREDCELLS, sb.toString());
        }




        if(question.getAnsweredLetters() != null &&
                question.getAnsweredLetters().size()>0)
        {
            sb = new StringBuilder();
            for(String s: question.getAnsweredLetters()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWEREDLETTERS, sb.toString());
        }




        values.put(KEY_TAG_QUESTIONS_CREATEDAT, question.getCreatedAt());
        values.put(KEY_TAG_QUESTIONS_UPDATEDAT, question.getUpdatedAt());
        values.put(KEY_TAG_QUESTIONS_ISANSWERED, question.getAnswered()?"1":"0");
        values.put(KEY_TAG_QUESTIONS_ISCORRECT, question.getCorrect()?"1":"0");

        // Inserting Row
        try
        {
            long id = db.insertOrThrow(TABLE_TAG_QUESTIONS, null, values);

        }
        catch (Exception ex)
        {
            int temp = 0;
        }
        db.close(); // Closing database connection

//        Log.d(TAG, "New match inserted into sqlite: " + id);
    }

    /**
     * Storing user details in sqlite database
     * */
    public void addQuestion(QuestionModel question, String tag, SQLiteDatabase tdb) {


        ContentValues values = new ContentValues();

        byte[] byteArray = null;
        if(question.getImage() != null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            question.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }


        values.put(KEY_TAG_QUESTIONS_TAG, tag);
        if(question.getId() != null)
            values.put(KEY_TAG_QUESTIONS_ID, question.getId().toString());
        values.put(KEY_TAG_QUESTIONS_GUID, question.getGuid());
        values.put(KEY_TAG_QUESTIONS_CATEGORYID, question.getCategoryId());
        values.put(KEY_TAG_QUESTIONS_DESCRIPTION, question.getDescription());
        values.put(KEY_TAG_QUESTIONS_ANSWER, question.getAnswer());
        values.put(KEY_TAG_QUESTIONS_IMAGE, byteArray);
        values.put(KEY_TAG_QUESTIONS_PENALTY, question.getPenalty());
        values.put(KEY_TAG_QUESTIONS_FINALHAZELREWARD, question.getFinalHazelReward());
        values.put(KEY_TAG_QUESTIONS_FINALLUCKREWARD, question.getFinalLuckReward());
        values.put(KEY_TAG_QUESTIONS_MAXHAZEKREWARD, question.getMaxHazelReward());
        values.put(KEY_TAG_QUESTIONS_MAXLUCKREWARD, question.getMaxLuckReward());
        values.put(KEY_TAG_QUESTIONS_MINHAZELREWARD, question.getMinHazelReward());
        values.put(KEY_TAG_QUESTIONS_MINLUCKREWARD, question.getMinLuckReward());
        values.put(KEY_TAG_QUESTIONS_POSITION_CODE, question.getPositionCode());
        values.put(KEY_TAG_QUESTIONS_QUESTION_POSITION, question.getQuestionPosition());



        StringBuilder sb = new StringBuilder();
        if(question.getAnswerCells() != null &&
                question.getAnswerCells().size()>0)
        {
            for(String s: question.getAnswerCells()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWERCELLS, sb.toString());
        }



        if(question.getAnsweredCells() != null &&
                question.getAnsweredCells().size()>0)
        {
            sb = new StringBuilder();
            for(String s: question.getAnsweredCells()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWEREDCELLS, sb.toString());
        }




        if(question.getAnsweredLetters() != null &&
                question.getAnsweredLetters().size()>0)
        {
            sb = new StringBuilder();
            for(String s: question.getAnsweredLetters()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWEREDLETTERS, sb.toString());
        }




        values.put(KEY_TAG_QUESTIONS_CREATEDAT, question.getCreatedAt());
        values.put(KEY_TAG_QUESTIONS_UPDATEDAT, question.getUpdatedAt());
        values.put(KEY_TAG_QUESTIONS_ISANSWERED, question.getAnswered()?"1":"0");
        values.put(KEY_TAG_QUESTIONS_ISCORRECT, question.getCorrect()?"1":"0");

        // Inserting Row
        try
        {
            long id = tdb.insertOrThrow(TABLE_TAG_QUESTIONS, null, values);

        }
        catch (Exception ex)
        {
            int temp = 0;
        }
    }

    public void editQuestion(QuestionModel question, String tag) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        if(question.getId()!=null) values.put(KEY_TAG_QUESTIONS_ID, question.getId().toString());
        if(question.getGuid()!=null) values.put(KEY_TAG_QUESTIONS_GUID, question.getGuid());
        if(question.getDescription()!=null) values.put(KEY_TAG_QUESTIONS_DESCRIPTION, question.getDescription());
        if(question.getAnswer()!=null) values.put(KEY_TAG_QUESTIONS_ANSWER, question.getAnswer());
        if(question.getFinalLuckReward()!=null) values.put(KEY_TAG_QUESTIONS_FINALHAZELREWARD, question.getFinalHazelReward());
        if(question.getFinalLuckReward()!=null) values.put(KEY_TAG_QUESTIONS_FINALLUCKREWARD, question.getFinalLuckReward());
        if(question.getMaxHazelReward()!=null) values.put(KEY_TAG_QUESTIONS_MAXHAZEKREWARD, question.getMaxHazelReward());
        if(question.getMaxLuckReward()!=null) values.put(KEY_TAG_QUESTIONS_MAXLUCKREWARD, question.getMaxLuckReward());
        if(question.getMinHazelReward()!=null) values.put(KEY_TAG_QUESTIONS_MINHAZELREWARD, question.getMinHazelReward());
        if(question.getMinLuckReward()!=null) values.put(KEY_TAG_QUESTIONS_MINLUCKREWARD, question.getMinLuckReward());
        if(question.getPositionCode()!=null) values.put(KEY_TAG_QUESTIONS_POSITION_CODE, question.getPositionCode());
        if(question.getQuestionPosition()!=null) values.put(KEY_TAG_QUESTIONS_QUESTION_POSITION, question.getQuestionPosition());

        if(question.getImage()!=null)
        {
            byte[] byteArray;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            question.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            values.put(KEY_TAG_QUESTIONS_IMAGE, byteArray);
        }
        StringBuilder sb = new StringBuilder();
        if(question.getAnswerCells()!=null)
        {
            for(String s: question.getAnswerCells()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWERCELLS, sb.toString());
        }

        if(question.getAnsweredLetters()!=null)
        {
            sb = new StringBuilder();
            for(String s: question.getAnsweredLetters()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWEREDLETTERS, sb.toString());
        }
        if(question.getAnsweredCells()!=null)
        {
            sb = new StringBuilder();
            for(String s: question.getAnsweredCells()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TAG_QUESTIONS_ANSWEREDCELLS, sb.toString());
        }
        if(question.getAnswered()!=null)  values.put(KEY_TAG_QUESTIONS_ISANSWERED,question.getAnswered()?"1":"0");
        if(question.getCorrect()!=null) values.put(KEY_TAG_QUESTIONS_ISCORRECT,question.getCorrect()?"1":"0");
        if(question.getCategoryId()!=null) values.put(KEY_TAG_QUESTIONS_CATEGORYID, question.getCategoryId());
        if(question.getCreatedAt()!=null) values.put(KEY_TAG_QUESTIONS_CREATEDAT, question.getCreatedAt());
        if(question.getPenalty()!=null) values.put(KEY_TAG_QUESTIONS_PENALTY, question.getPenalty());
        if(question.getUpdatedAt()!=null) values.put(KEY_TAG_QUESTIONS_UPDATEDAT, question.getUpdatedAt());

        // Edit Row
        long id = db.update(TABLE_TAG_QUESTIONS, values,KEY_TAG_QUESTIONS_TAG+"="+tag,null);
        db.close(); // Closing database connection
    }

    // Getting single question by tag
    public QuestionModel getQuestionByTag(String questionTag) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TAG_QUESTIONS, new String[] { KEY_TAG_QUESTIONS_ID,
                        KEY_TAG_QUESTIONS_GUID,
                        KEY_TAG_QUESTIONS_DESCRIPTION,
                        KEY_TAG_QUESTIONS_IMAGE,
                        KEY_TAG_QUESTIONS_FINALHAZELREWARD,
                        KEY_TAG_QUESTIONS_FINALLUCKREWARD,
                        KEY_TAG_QUESTIONS_MAXHAZEKREWARD,
                        KEY_TAG_QUESTIONS_MAXLUCKREWARD,
                        KEY_TAG_QUESTIONS_MINHAZELREWARD,
                        KEY_TAG_QUESTIONS_MINLUCKREWARD,
                        KEY_TAG_QUESTIONS_ANSWER,
                        KEY_TAG_QUESTIONS_ANSWERCELLS,
                        KEY_TAG_QUESTIONS_ANSWEREDCELLS,
                        KEY_TAG_QUESTIONS_ANSWEREDLETTERS,
                        KEY_TAG_QUESTIONS_CATEGORYID,
                        KEY_TAG_QUESTIONS_CREATEDAT,
                        KEY_TAG_QUESTIONS_ISANSWERED,
                        KEY_TAG_QUESTIONS_ISCORRECT,
                        KEY_TAG_QUESTIONS_PENALTY,
                        KEY_TAG_QUESTIONS_UPDATEDAT,
                        KEY_TAG_QUESTIONS_POSITION_CODE,
                        KEY_TAG_QUESTIONS_QUESTION_POSITION}, KEY_TAG_QUESTIONS_TAG + "=?",
                new String[] { questionTag }, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();

        QuestionModel question = new QuestionModel();
        if(cursor.getCount() > 0)
        {
            question.setId(new BigInteger(cursor.getString(0)));
            question.setGuid(cursor.getString(1));
            question.setDescription(cursor.getString(2));
            byte[] byteArray = cursor.getBlob(3);
            if(byteArray != null)
            {
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                question.setImage(bm);
            }
            else
            {
                question.setImage(null);
            }

            if(cursor.getString(4) != null)
                question.setFinalHazelReward(new Integer(cursor.getString(4)));
            if(cursor.getString(5) != null)
                question.setFinalLuckReward(new Integer(cursor.getString(5)));
            if(cursor.getString(6) != null)
                question.setMaxHazelReward(new Integer(cursor.getString(6)));
            if(cursor.getString(7) != null)
                question.setMaxLuckReward(new Integer(cursor.getString(7)));
            if(cursor.getString(8) != null)
                question.setMinHazelReward(new Integer(cursor.getString(8)));
            if(cursor.getString(9) != null)
                question.setMinLuckReward(new Integer(cursor.getString(9)));
            if(cursor.getString(10) != null)
                question.setAnswer(cursor.getString(10));
            if(cursor.getString(11) != null)
                question.setAnswerCells( new ArrayList<String>(Arrays.asList(cursor.getString(11).split("\\s*,\\s*"))));
            if(cursor.getString(12) != null)
                question.setAnsweredCells(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));
            if(cursor.getString(13) != null)
                question.setAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(13).split("\\s*,\\s*"))));
            if(cursor.getString(14) != null)
                question.setCategoryId(new Integer(cursor.getString(14)));
            if(cursor.getString(15) != null)
                question.setCreatedAt(cursor.getString(15));
            if(cursor.getString(16) != null)
                question.setAnswered(cursor.getString(16).equals("1")?true:false);
            if(cursor.getString(17) != null)
                question.setCorrect(cursor.getString(17).equals("1")?true:false);
            if(cursor.getString(18) != null)
                question.setPenalty(new Integer(cursor.getString(18)));
            if(cursor.getString(19) != null)
                question.setUpdatedAt(cursor.getString(19));
            if(cursor.getString(20) != null)
                question.setPositionCode(cursor.getString(20));
            if(cursor.getString(21) != null)
                question.setQuestionPosition(cursor.getString(21));
        }

        // return question
        return question;
    }

    // Getting single question by id
    public QuestionModel getQuestionById(BigInteger questionId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TAG_QUESTIONS, new String[] { KEY_TAG_QUESTIONS_ID,
                        KEY_TAG_QUESTIONS_GUID,
                        KEY_TAG_QUESTIONS_DESCRIPTION,
                        KEY_TAG_QUESTIONS_IMAGE,
                        KEY_TAG_QUESTIONS_FINALHAZELREWARD,
                        KEY_TAG_QUESTIONS_FINALLUCKREWARD,
                        KEY_TAG_QUESTIONS_MAXHAZEKREWARD,
                        KEY_TAG_QUESTIONS_MAXLUCKREWARD,
                        KEY_TAG_QUESTIONS_MINHAZELREWARD,
                        KEY_TAG_QUESTIONS_MINLUCKREWARD,
                        KEY_TAG_QUESTIONS_ANSWER,
                        KEY_TAG_QUESTIONS_ANSWERCELLS,
                        KEY_TAG_QUESTIONS_ANSWEREDCELLS,
                        KEY_TAG_QUESTIONS_ANSWEREDLETTERS,
                        KEY_TAG_QUESTIONS_CATEGORYID,
                        KEY_TAG_QUESTIONS_CREATEDAT,
                        KEY_TAG_QUESTIONS_ISANSWERED,
                        KEY_TAG_QUESTIONS_ISCORRECT,
                        KEY_TAG_QUESTIONS_PENALTY,
                        KEY_TAG_QUESTIONS_UPDATEDAT,
                        KEY_TAG_QUESTIONS_POSITION_CODE,
                        KEY_TAG_QUESTIONS_QUESTION_POSITION}, KEY_TAG_QUESTIONS_ID + "=?",
                new String[] { String.valueOf(questionId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        QuestionModel question = new QuestionModel();

        if(cursor.getString(0) != null)
            question.setId(new BigInteger(cursor.getString(0)));
        if(cursor.getString(1) != null)
            question.setGuid(cursor.getString(1));
        if(cursor.getString(2) != null)
            question.setDescription(cursor.getString(2));
        if(cursor.getBlob(3) != null)
        {
            byte[] byteArray = cursor.getBlob(3);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
            question.setImage(bm);
        }

        if(cursor.getString(4) != null)
            question.setFinalHazelReward(new Integer(cursor.getString(4)));
        if(cursor.getString(5) != null)
            question.setFinalLuckReward(new Integer(cursor.getString(5)));
        if(cursor.getString(6) != null)
            question.setMaxHazelReward(new Integer(cursor.getString(6)));
        if(cursor.getString(7) != null)
            question.setMaxLuckReward(new Integer(cursor.getString(7)));
        if(cursor.getString(8) != null)
            question.setMinHazelReward(new Integer(cursor.getString(8)));
        if(cursor.getString(9) != null)
            question.setMinLuckReward(new Integer(cursor.getString(9)));
        if(cursor.getString(10) != null)
            question.setAnswer(cursor.getString(10));
        if(cursor.getString(11) != null)
            question.setAnswerCells(new ArrayList<String>(Arrays.asList(cursor.getString(11).split("\\s*,\\s*"))));
        if(cursor.getString(12) != null)
            question.setAnsweredCells(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));
        if(cursor.getString(13) != null)
            question.setAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(13).split("\\s*,\\s*"))));
        if(cursor.getString(14) != null)
            question.setCategoryId(new Integer(cursor.getString(14)));
        if(cursor.getString(15) != null)
            question.setCreatedAt(cursor.getString(15));
        if(cursor.getString(16) != null)
            question.setAnswered(cursor.getString(16).equals("1")?true:false);
        if(cursor.getString(17) != null)
            question.setCorrect(cursor.getString(17).equals("1")?true:false);
        if(cursor.getString(18) != null)
            question.setPenalty(new Integer(cursor.getString(18)));
        if(cursor.getString(19) != null)
            question.setUpdatedAt(cursor.getString(19));
        if(cursor.getString(20) != null)
            question.setPositionCode(cursor.getString(20));
        if(cursor.getString(21) != null)
            question.setQuestionPosition(cursor.getString(21));
        // return question
        return question;
    }
    // Deleting single contact
    public void deleteQuestionById(BigInteger question_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAG_QUESTIONS, KEY_TAG_QUESTIONS_ID + " = ?",
                new String[] { String.valueOf(question_id) });
        db.close();
    }

    // Deleting single contact
    public void deleteQuestionsByTag(String question_tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAG_QUESTIONS, KEY_TAG_QUESTIONS_TAG + " = ?",
                new String[] { question_tag });
        db.close();
    }

    // Getting All Questions related to a specific tag
    public ArrayList<QuestionModel> getQuestionsByTag(String questionTag) {
        ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_QUESTIONS + " WHERE "+ KEY_TAG_QUESTIONS_TAG +" = '"+questionTag+"' ORDER BY " +
                KEY_TAG_QUESTIONS_ID+" ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionModel question = new QuestionModel();

                if(cursor.getString(0) != null)
                    question.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    question.setGuid(cursor.getString(1));
                if(cursor.getString(2) != null)
                    question.setAnswer(cursor.getString(2));
                if(cursor.getString(3) != null)
                    question.setAnswerCells(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    question.setAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(4).split("\\s*,\\s*"))));
                if(cursor.getString(5) != null)
                    question.setAnsweredCells(new ArrayList<String>(Arrays.asList(cursor.getString(5).split("\\s*,\\s*"))));
                if(cursor.getString(6) != null)
                    question.setCategoryId(new Integer(cursor.getString(6)));
                if(cursor.getString(7) != null)
                    question.setDescription(cursor.getString(7));
                if(cursor.getString(8) != null)
                    question.setFinalHazelReward(new Integer(cursor.getString(8)));
                if(cursor.getString(9) != null)
                    question.setFinalLuckReward(new Integer(cursor.getString(9)));
                if(cursor.getString(10) != null)
                    question.setMaxHazelReward(new Integer(cursor.getString(10)));
                if(cursor.getString(11) != null)
                    question.setMaxLuckReward(new Integer(cursor.getString(11)));
                if(cursor.getString(12) != null)
                    question.setMinHazelReward(new Integer(cursor.getString(12)));
                if(cursor.getString(13) != null)
                    question.setMinLuckReward(new Integer(cursor.getString(13)));
                if(cursor.getString(14) != null)
                    question.setPenalty(new Integer(cursor.getString(14)));
                if(cursor.getString(15) != null)
                    question.setAnswered(cursor.getString(15).equals("1")?true:false);
                if(cursor.getString(16) != null)
                    question.setCorrect(cursor.getString(16).equals("1")?true:false);
                if(cursor.getString(17) != null)
                    question.setCreatedAt(cursor.getString(17));
                if(cursor.getString(18) != null)
                    question.setUpdatedAt(cursor.getString(18));
                if(cursor.getString(19) != null)
                    question.setPositionCode(cursor.getString(19));
                if(cursor.getString(20) != null)
                    question.setQuestionPosition(cursor.getString(20));

                byte[] byteArray = cursor.getBlob(21);
                if(byteArray != null)
                {
                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                    question.setImage(bm);
                }

                // Adding contact to list
                questions.add(question);
            } while (cursor.moveToNext());
        }

        // return question list
        return questions;
    }

    public void saveTableQuestions(ArrayList<QuestionModel> questions, String tag)
    {
        deleteQuestionsByTag(tag);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try
        {
            for(int i = 0; i < questions.size(); i++)
            {
                addQuestion(questions.get(i), tag, db);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
        }
        finally {

            db.endTransaction();
        }
    }
    /////////////////////meysam - question table specifications - end/////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - farsi words table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_FarsiWords = "farsiWords";

    //  Table Columns names
    private static final String KEY_FARSI_WORD_ID = "farsi_word_id";
    private static final String KEY_FARSI_WORD_WORD = "farsi_word_word";
    private static final String KEY_FARSI_WORD_WORD_NO_SPACE = "farsi_word_word_no_space";
    private static final String KEY_FARSI_WORD_LENGTH = "farsi_word_length";
    private static final String KEY_FARSI_WORD_CATEGORY = "farsi_word_category";
    private static final String KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE = "farsi_word_lengthWithoutSpace";

    public void fillTable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        //fill table
        SessionModel session = new SessionModel(cntx);
        session.saveItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS,1);
        Scanner s = new Scanner(cntx.getResources().openRawResource(R.raw.farsi_words));
        try
        {
            while (s.hasNext())
            {
                String[] lineText = s.nextLine().split(",");
                if(lineText.length > 1)
                {
                    addFarsiWord(new WordModel(lineText[0],Integer.valueOf(lineText[1])),db);
                }
                else
                {
                    addFarsiWord(new WordModel(lineText[0], null),db);
                }


            }
            db.setTransactionSuccessful();
            session.saveItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS,2);
        }
        catch (Exception ex)
        {
        }
        finally {
            s.close();
            db.endTransaction();
        }
    }

    /**
     * Storing user details in database
     * */
    public void addFarsiWord(WordModel word, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
//        values.put(KEY_ID, String.valueOf(word.getId())); // id
        values.put(KEY_FARSI_WORD_WORD,word.getWord()); // word
        values.put(KEY_FARSI_WORD_WORD_NO_SPACE,word.getWordNoSpace()); // word
        values.put(KEY_FARSI_WORD_LENGTH,word.getLength()); // length
        values.put(KEY_FARSI_WORD_CATEGORY,word.getCategory()); // category
        values.put(KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE,word.getLengthWithoutSpace()); // lengthWithoutSpace

        // Inserting Row
        tdb.insert(TABLE_FarsiWords, null, values);
    }

    /**
     * Storing user details in database
     * */
    public void addFarsiWord(WordModel word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_ID, String.valueOf(word.getId())); // id
        values.put(KEY_FARSI_WORD_WORD,word.getWord()); // word
        values.put(KEY_FARSI_WORD_WORD_NO_SPACE,word.getWordNoSpace()); // word
        values.put(KEY_FARSI_WORD_LENGTH,word.getLength()); // length
        values.put(KEY_FARSI_WORD_CATEGORY,word.getCategory()); // category
        values.put(KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE,word.getLengthWithoutSpace()); // lengthWithoutSpace

        // Inserting Row
        long id = db.insert(TABLE_FarsiWords, null, values);
        db.close(); // Closing database connection

//        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public WordModel getFarsiWord(boolean categoryExist, Integer minAnswerLength, Integer maxAnswerLength) {
        WordModel out = null;
        String selectQuery;

        SQLiteDatabase db = this.getReadableDatabase();
        if(categoryExist)
        {
            selectQuery = "SELECT  * FROM " + TABLE_FarsiWords + " WHERE "+KEY_FARSI_WORD_CATEGORY+" IS NOT NULL ";
        }
        else
        {
            selectQuery = "SELECT  * FROM " + TABLE_FarsiWords;
        }

//        if(minAnswerLength != null)
//            selectQuery += " AND LENGTH("+KEY_FARSI_WORD_WORD_NO_SPACE+") >= "+minAnswerLength;
//        if(maxAnswerLength != null)
//            selectQuery += " AND LENGTH("+KEY_FARSI_WORD_WORD_NO_SPACE+") <= "+maxAnswerLength;

        if(minAnswerLength != null)
            selectQuery += " AND "+KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE+" >= "+minAnswerLength;
        if(maxAnswerLength != null)
            selectQuery += " AND "+KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE+" <= "+maxAnswerLength;


        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToPosition(0);
        int count = cursor.getCount();
        int rnd = new Random().nextInt(count - 1) + 1;

//        if(categoryExist)
//        {
//            selectQuery = "SELECT  * FROM " + TABLE_FarsiWords + " WHERE "+KEY_FARSI_WORD_CATEGORY+" IS NOT NULL ";
//        }
//        else
//        {
//            selectQuery = "SELECT  * FROM " + TABLE_FarsiWords;
//        }
//
////        if(minAnswerLength != null)
////            selectQuery += " AND LENGTH("+KEY_FARSI_WORD_WORD_NO_SPACE+") >= "+minAnswerLength;
////        if(maxAnswerLength != null)
////            selectQuery += " AND LENGTH("+KEY_FARSI_WORD_WORD_NO_SPACE+") <= "+maxAnswerLength;
//
//        if(minAnswerLength != null)
//            selectQuery += " AND "+KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE+" >= "+minAnswerLength;
//        if(maxAnswerLength != null)
//            selectQuery += " AND "+KEY_FARSI_WORD_LENGTH_WITHOUT_SPACE+" <= "+maxAnswerLength;
//
//        selectQuery += " AND "+KEY_FARSI_WORD_ID+" = "+rnd;

//        cursor = db.rawQuery(selectQuery, null);
//        if(cursor.getCount() > 0)
//        {
//            Integer count = cursor.getCount();
//            int rnd = new Random().nextInt(count - 1) + 1;
            // Move to first row
            cursor.moveToPosition(rnd);
            out = new WordModel();
            out.setId(new BigInteger(cursor.getString(0)));
            out.setWord(cursor.getString(1));
            out.setWordNoSpace(cursor.getString(2));
            out.setLength(Integer.parseInt(cursor.getString(3)));
            if(cursor.getString(4) != null)
                out.setCategory(Integer.parseInt(cursor.getString(4)));
            out.setLengthWithoutSpace(Integer.parseInt(cursor.getString(5)));
//        }
        cursor.close();
        db.close();

        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return out;
    }

    /**
     * Getting user data from database
     * */
    public WordModel getFarsiWord(String word) {
        WordModel out = null;
        String selectQuery = "SELECT  * FROM " + TABLE_FarsiWords + " WHERE "+KEY_FARSI_WORD_WORD+" LIKE '"+word+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            out = new WordModel();
            out.setId(new BigInteger(cursor.getString(0)));
            out.setWord(cursor.getString(1));
            out.setWordNoSpace(cursor.getString(2));
            out.setLength(Integer.parseInt(cursor.getString(3)));
            if(cursor.getString(4) != null)
                out.setCategory(Integer.parseInt(cursor.getString(4)));
            out.setLengthWithoutSpace(Integer.parseInt(cursor.getString(5)));
        }
        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return out;
    }
    /**
     * Getting user data from database
     * */
    public WordModel getFarsiWordNoSpace(String wordNoSpace) {
        WordModel out = null;
        String selectQuery = "SELECT  * FROM " + TABLE_FarsiWords + " WHERE "+KEY_FARSI_WORD_WORD_NO_SPACE+" LIKE '"+wordNoSpace+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            out = new WordModel();
            out.setId(new BigInteger(cursor.getString(0)));
            out.setWord(cursor.getString(1));
            out.setWordNoSpace(cursor.getString(2));
            out.setLength(Integer.parseInt(cursor.getString(3)));
            if(cursor.getString(4) != null)
                out.setCategory(Integer.parseInt(cursor.getString(4)));
            out.setLengthWithoutSpace(Integer.parseInt(cursor.getString(5)));
        }
        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return out;
    }
    public WordModel getFarsiWord(BigInteger id) {
        WordModel out = null;
        String selectQuery = "SELECT  * FROM " + TABLE_FarsiWords + " WHERE "+KEY_FARSI_WORD_ID+" = "+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            out = new WordModel();
            out.setId(new BigInteger(cursor.getString(0)));
            out.setWord(cursor.getString(1));
            out.setWordNoSpace(cursor.getString(2));
            out.setLength(Integer.parseInt(cursor.getString(3)));
            if(cursor.getString(4) != null)
                out.setCategory(Integer.parseInt(cursor.getString(4)));
            out.setLengthWithoutSpace(Integer.parseInt(cursor.getString(5)));
        }
        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return out;
    }
    /**
     * Getting user data from database
     * */
    public ArrayList<WordModel> searchFarsiWord(String word, Integer skip) {
        ArrayList<WordModel> words = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FarsiWords + " WHERE "+KEY_FARSI_WORD_WORD+" LIKE '%"+word+"%'  LIMIT "+ 10 +" OFFSET " + skip;;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast())
            {
                WordModel out = new WordModel();
                out.setId(new BigInteger(cursor.getString(0)));
                out.setWord(cursor.getString(1));
                out.setWordNoSpace(cursor.getString(2));
                out.setLength(Integer.parseInt(cursor.getString(3)));
                if(cursor.getString(4) != null)
                    out.setCategory(Integer.parseInt(cursor.getString(4)));
                out.setLengthWithoutSpace(Integer.parseInt(cursor.getString(5)));

                words.add(out);
                cursor.moveToNext();
            }

        }
        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return words;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteFarsiWords() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_FarsiWords, null, null);
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }
    /////////////////////meysam - farsi words table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - match table specifications - start///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Contacts table name
    //  table name
    private static final String TABLE_UNIVERSAL_MATCH = "universal_matches";

    //  Table Columns names
    private static final String KEY_UNIVERSAL_MATCH_ID = "match_Id";
    private static final String KEY_UNIVERSAL_MATCH_OPPONENT_USER_NAME = "UniversalMatchOpponentUserName";
    private static final String KEY_UNIVERSAL_MATCH_OPPONENT_CORRECT_COUNT= "UniversalMatchOpponentCorrectCount";
    private static final String KEY_UNIVERSAL_MATCH_OPPONENT_AVATAR = "UniversalMatchOpponentAvatar";
    private static final String KEY_UNIVERSAL_MATCH_OPPONENT_SPENT_TIME = "UniversalMatchOpponentSpentTime";
    private static final String KEY_UNIVERSAL_MATCH_SELF_CORRECT_COUNT = "UniversalMatchSelfCorrectCount";
    private static final String KEY_UNIVERSAL_MATCH_SELF_SPENT_TIME = "UniversalMatchSelfSpentTime";
    private static final String KEY_UNIVERSAL_MATCH_WINNER_CODE = "UniversalMatchWinnerCode";
    private static final String KEY_UNIVERSAL_MATCH_IS_ENDED= "UniversalMatchIsEnded";
    private static final String KEY_UNIVERSAL_MATCH_IS_NITRO = "UniversalMatchIsNitro";
    private static final String KEY_UNIVERSAL_MATCH_IS_REWARD_LUCK = "UniversalMatchIsRewardLuck";
    private static final String KEY_UNIVERSAL_MATCH_BET = "UniversalMatchBet";
    private static final String KEY_UNIVERSAL_MATCH_TYPE = "UniversalMatchType";
    private static final String KEY_UNIVERSAL_MATCH_OPPONENT_TYPE = "UniversalMatchOpponentType";

    /**
     * Storing user details in sqlite database
     * */
    public void addMatch(UniversalMatchModel match) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(match.getId() != null)
            values.put(KEY_UNIVERSAL_MATCH_ID, match.getId().toString());
        if(match.getOpponent() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_USER_NAME, match.getOpponent().getUserName());
        if(match.getOpponentCorrectCount() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_CORRECT_COUNT, match.getOpponentCorrectCount());
        if(match.getOpponent() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_AVATAR, match.getOpponent().getProfilePicture());
        if(match.getOpponentSpentTime() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_SPENT_TIME, match.getOpponentSpentTime());
        if(match.getSelfCorrectCount() != null)
            values.put(KEY_UNIVERSAL_MATCH_SELF_CORRECT_COUNT, match.getSelfCorrectCount());
        if(match.getSelfSpentTime() != null)
            values.put(KEY_UNIVERSAL_MATCH_SELF_SPENT_TIME, match.getSelfSpentTime());
        if(match.getWinner() != null)
            values.put(KEY_UNIVERSAL_MATCH_WINNER_CODE, match.getWinner().toString());
        if(match.getEnded() != null)
            values.put(KEY_UNIVERSAL_MATCH_IS_ENDED, match.getEnded());
        if(match.getNitro() != null)
            values.put(KEY_UNIVERSAL_MATCH_IS_NITRO, match.getNitro()?"1":"0");
        if(match.getRewardLuck() != null)
            values.put(KEY_UNIVERSAL_MATCH_IS_REWARD_LUCK, match.getRewardLuck()?"1":"0");
        if(match.getBet() != null)
            values.put(KEY_UNIVERSAL_MATCH_BET, match.getBet());
        if(match.getMatchType() != null)
            values.put(KEY_UNIVERSAL_MATCH_TYPE, match.getMatchType());
        if(match.getOpponentType() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_TYPE, match.getOpponentType());

        // Inserting Row
        long id = db.insert(TABLE_UNIVERSAL_MATCH, null, values);
        db.close(); // Closing database connection

//        Log.d(TAG, "New match inserted into sqlite: " + id);
    }
//
    public void editMatch(UniversalMatchModel match) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        if(match.getOpponent() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_USER_NAME, match.getOpponent().getUserName());
        if(match.getOpponentCorrectCount() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_CORRECT_COUNT, match.getOpponentCorrectCount());
        if(match.getOpponent() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_AVATAR, match.getOpponent().getProfilePicture());
        if(match.getOpponentSpentTime() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_SPENT_TIME, match.getOpponentSpentTime());
        if(match.getSelfCorrectCount() != null)
            values.put(KEY_UNIVERSAL_MATCH_SELF_CORRECT_COUNT, match.getSelfCorrectCount());
        if(match.getSelfSpentTime() != null)
            values.put(KEY_UNIVERSAL_MATCH_SELF_SPENT_TIME, match.getSelfSpentTime());
        if(match.getWinner() != null)
            values.put(KEY_UNIVERSAL_MATCH_WINNER_CODE, match.getWinner().toString());
        if(match.getEnded() != null)
            values.put(KEY_UNIVERSAL_MATCH_IS_ENDED, match.getEnded());
        if(match.getNitro() != null)
            values.put(KEY_UNIVERSAL_MATCH_IS_NITRO, match.getNitro()?"1":"0");
        if(match.getRewardLuck() != null)
            values.put(KEY_UNIVERSAL_MATCH_IS_REWARD_LUCK, match.getRewardLuck()?"1":"0");
        if(match.getBet() != null)
            values.put(KEY_UNIVERSAL_MATCH_BET, match.getBet());
        if(match.getMatchType() != null)
            values.put(KEY_UNIVERSAL_MATCH_TYPE, match.getMatchType());
        if(match.getOpponentType() != null)
            values.put(KEY_UNIVERSAL_MATCH_OPPONENT_TYPE, match.getOpponentType());

        // Edit Row
        long id = db.update(TABLE_UNIVERSAL_MATCH, values,KEY_UNIVERSAL_MATCH_ID+"="+match.getId().toString(),null);
        db.close(); // Closing database connection
    }

//    /**
//     * Getting match data from database
//     * //meysam - 13960601 - added input parameter
//     * */
//    public MatchModel getMatch(BigInteger matchId) {
//        MatchModel match = new MatchModel();
//        String selectQuery = "SELECT  * FROM " + MATCH_TABLE_NAME + " WHERE "+ KEY_MATCH_ID +" = " + matchId.toString();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            match.setId(new BigInteger(cursor.getString(0)));
//
//            UserModel Opponent=new UserModel();
//            Opponent.setUserName(cursor.getString(1));
//            Opponent.setProfilePicture(cursor.getString(3));
//
//            match.setOpponentCorrectCount(cursor.getString(2));
//            match.setOpponentSpentTime(cursor.getString(4));
//
//            match.setSelfCorrectCount(cursor.getString(5));
//            match.setSelfSpentTime(cursor.getString(6));
//
//            match.setWinner(new Integer(cursor.getString(7)));
//            match.setEnded(cursor.getString(8).equals("1")?true:false);
//            match.setNitro(cursor.getString(9).equals("1")?true:false);
//            match.setRewardLuck(cursor.getString(10).equals("1")?true:false);
//            match.setBet(cursor.getString(11));
//        }
//        cursor.close();
//        db.close();
//        // return user
////        Log.d(TAG, "Fetching specific match from Sqlite: " + match.toString());
//
//        return match;
//    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteMatches() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UNIVERSAL_MATCH, null, null);
        db.close();

    }

    //meysam - 13960601
    public void deleteMatch(BigInteger matchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete specific row
        db.delete(TABLE_UNIVERSAL_MATCH,  KEY_UNIVERSAL_MATCH_ID + "=" + matchId, null);
        db.close();

    }

    //meysam - 13960601
    public ArrayList<UniversalMatchModel> getMatches() {
        ArrayList<UniversalMatchModel> matches = new ArrayList<>();
        UniversalMatchModel match = null;

        String selectQuery = "SELECT  * FROM " + TABLE_UNIVERSAL_MATCH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                match = new UniversalMatchModel();
                if(cursor.getString(0) != null)
                    match.setId(new BigInteger(cursor.getString(0)));

                UserModel Opponent=new UserModel();
                if(cursor.getString(1) != null)
                    Opponent.setUserName(cursor.getString(1));
                if(cursor.getString(3) != null)
                    Opponent.setProfilePicture(cursor.getString(3));
                if(cursor.getString(2) != null)
                    match.setOpponentCorrectCount(cursor.getString(2));
                if(cursor.getString(4) != null)
                    match.setOpponentSpentTime(cursor.getString(4));
                if(cursor.getString(5) != null)
                    match.setSelfCorrectCount(cursor.getString(5));
                if(cursor.getString(6) != null)
                    match.setSelfSpentTime(cursor.getString(6));
                if(cursor.getString(7) != null)
                    match.setWinner(new Integer(cursor.getString(7)));
                if(cursor.getString(8) != null)
                    match.setEnded(Integer.valueOf(cursor.getString(8)));
                if(cursor.getString(9) != null)
                    match.setNitro(cursor.getString(9).equals("1")?true:false);
                if(cursor.getString(10) != null)
                    match.setRewardLuck(cursor.getString(10).equals("1")?true:false);
                if(cursor.getString(11) != null)
                    match.setBet(cursor.getString(11));

                match.setOpponent(Opponent);
                matches.add(match);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return matches;

    }

    public void saveMatches(ArrayList<UniversalMatchModel> matches, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteMatches();
        for(int i = 0; i < matches.size(); i++)
        {
            addMatch(matches.get(i));
        }
    }
    /////////////////////meysam - match table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - user table specifications - start///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // users table name
    private static final String TABLE_TAG_USERS = "tagUsers";
    //  Table Columns names
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_USER_USERNAME = "UserName";
    private static final String KEY_USER_LUCK= "UserLuck";
    private static final String KEY_USER_HAZEL = "UserHazel";
    private static final String KEY_USER_UPDATE_DATE_TIME = "UserUpdateDateTime";
    private static final String KEY_USER_AVATAR = "UserAvatar";
    private static final String KEY_USER_FRIENDSHIP_STATUS = "UserFriendshipStatus";
    private static final String KEY_USER_TAG = "UserTag";
    private static final String KEY_USER_LEVEL= "UserLevel";
    private static final String KEY_USER_CUPS= "UserCups";
    private static final String KEY_USER_FRIENDS_COUNT= "UserFriendsCount";
    private static final String KEY_USER_ALLOW_CHAT= "UserAllowChat";

    /**
     * Storing user details in sqlite database
     * */
    public void addUser(UserModel user, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_USER_TAG, tag);
        if(user.getId() != null)
            values.put(KEY_USER_ID, user.getId().toString());
        values.put(KEY_USER_USERNAME, user.getUserName());
        values.put(KEY_USER_HAZEL, user.getHazel());
        values.put(KEY_USER_LUCK, user.getLuck());
        if(user.getUpdateDateTime() == null)
        {
//            Calendar cal = Calendar.getInstance();
//            LocalDateTime cal = LocalDateTime.now();
            Date cal = new Date();
//            cal.add(Calendar.DATE, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            values.put(KEY_USER_UPDATE_DATE_TIME, format1.format(cal.getTime()));
        }
        else
            values.put(KEY_USER_UPDATE_DATE_TIME, user.getUpdateDateTime());
        values.put(KEY_USER_AVATAR, user.getProfilePicture());
        values.put(KEY_USER_FRIENDSHIP_STATUS, user.getFriendshipStatus());

        if(user.getLevelScore() != null)
            values.put(KEY_USER_LEVEL, user.getLevelScore());
        if(user.getCups() != null)
            values.put(KEY_USER_CUPS, user.getCups());
        if(user.getFriendsCount() != null)
            values.put(KEY_USER_FRIENDS_COUNT, user.getFriendsCount());
        if(user.getAllowChat() != null)
            values.put(KEY_USER_ALLOW_CHAT, user.getAllowChat());
        // Inserting Row
        try
        {
            long id = db.insertOrThrow(TABLE_TAG_USERS, null, values);

        }
        catch (Exception ex)
        {
            int temp = 0;
        }
        db.close(); // Closing database connection

    }

    public void editUser(UserModel user, String tag) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        if(tag!=null) values.put(KEY_USER_TAG, tag);

//        if(user.getId()!=null) values.put(KEY_USER_ID, user.getId().toString());
        if(user.getUserName()!=null) values.put(KEY_USER_USERNAME, user.getUserName());
        if(user.getHazel()!=null) values.put(KEY_USER_HAZEL, user.getHazel());
        if(user.getLuck()!=null) values.put(KEY_USER_LUCK, user.getLuck());
        if(user.getUpdateDateTime() == null)
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            values.put(KEY_USER_UPDATE_DATE_TIME, format1.format(cal.getTime()));
        }
        else
            values.put(KEY_USER_UPDATE_DATE_TIME, user.getUpdateDateTime());
        if(user.getProfilePicture()!=null) values.put(KEY_USER_AVATAR, user.getProfilePicture());
        if(user.getFriendshipStatus()!=null) values.put(KEY_USER_FRIENDSHIP_STATUS, user.getFriendshipStatus());
        if(user.getLevelScore() != null)
            values.put(KEY_USER_LEVEL, user.getLevelScore());
        if(user.getCups() != null)
            values.put(KEY_USER_CUPS, user.getCups());
        if(user.getFriendsCount() != null)
            values.put(KEY_USER_FRIENDS_COUNT, user.getFriendsCount());
        if(user.getAllowChat() != null)
            values.put(KEY_USER_ALLOW_CHAT, user.getAllowChat());
        // Edit Row
        long id = db.update(TABLE_TAG_USERS, values,KEY_USER_ID+"="+user.getId(),null);
        db.close(); // Closing database connection
    }


    // Getting single user by tag
    public UserModel getUserByTag(String userTag) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TAG_USERS, new String[] { KEY_USER_ID,
                        KEY_USER_USERNAME,
                        KEY_USER_HAZEL,
                        KEY_USER_LUCK,
                        KEY_USER_FRIENDSHIP_STATUS,
                        KEY_USER_AVATAR,
                        KEY_USER_UPDATE_DATE_TIME,
                KEY_USER_LEVEL,
                KEY_USER_CUPS,
                KEY_USER_FRIENDS_COUNT,
                        KEY_USER_ALLOW_CHAT}, KEY_USER_TAG + "=?",
                new String[] { userTag }, null, null, null, null);


        UserModel user = new UserModel();
        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            user.setId(new BigInteger(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setHazel(cursor.getInt(2));
            user.setLuck(cursor.getInt(3));
            user.setFriendshipStatus(cursor.getInt(4));
            user.setProfilePicture(cursor.getString(5));
            user.setUpdateDateTime(cursor.getString(6));
            user.setLevelScore(new Integer(cursor.getInt(7)));
            user.setCups(cursor.getString(8));
            user.setFriendsCount(new Integer(cursor.getString(9)));
            user.setAllowChat(cursor.getString(10));
        }

        // return user
        return user;
    }

    // Getting single user by id
    public UserModel getUserById(BigInteger userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TAG_USERS, new String[] { KEY_USER_ID,
                        KEY_USER_USERNAME,
                        KEY_USER_HAZEL,
                        KEY_USER_LUCK,
                        KEY_USER_FRIENDSHIP_STATUS,
                        KEY_USER_AVATAR,
                        KEY_USER_UPDATE_DATE_TIME,
                        KEY_USER_LEVEL,
                        KEY_USER_CUPS,
                        KEY_USER_FRIENDS_COUNT,
                        KEY_USER_ALLOW_CHAT}, KEY_USER_ID + "=?",
                new String[] { String.valueOf(userId) }, null, null, null, null);
        UserModel user = new UserModel();
        if (cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            user.setId(new BigInteger(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setHazel(cursor.getInt(2));
            user.setLuck(cursor.getInt(3));
            user.setFriendshipStatus(cursor.getInt(4));
            user.setProfilePicture(cursor.getString(5));
            user.setUpdateDateTime(cursor.getString(6));
            user.setLevelScore(new Integer(cursor.getInt(7)));
            user.setCups(cursor.getString(8));
            user.setFriendsCount(new Integer(cursor.getString(9)));
            user.setAllowChat(cursor.getString(10));

        }


        // return user
        return user;
    }

    // Getting single user by id
    public UserModel getUserByUserName(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TAG_USERS, new String[] { KEY_USER_ID,
                        KEY_USER_USERNAME,
                        KEY_USER_HAZEL,
                        KEY_USER_LUCK,
                        KEY_USER_FRIENDSHIP_STATUS,
                        KEY_USER_AVATAR,
                        KEY_USER_UPDATE_DATE_TIME,
                        KEY_USER_LEVEL,
                        KEY_USER_CUPS,
                KEY_USER_FRIENDS_COUNT,
                        KEY_USER_ALLOW_CHAT}, KEY_USER_USERNAME + "=?",
                new String[] { userName }, null, null, null, null);

        UserModel user = new UserModel();
        if (cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            user.setId(new BigInteger(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setHazel(cursor.getInt(2));
            user.setLuck(cursor.getInt(3));
            user.setFriendshipStatus(cursor.getInt(4));
            user.setProfilePicture(cursor.getString(5));
            user.setUpdateDateTime(cursor.getString(6));
            user.setLevelScore(new Integer(cursor.getInt(7)));
            user.setCups(cursor.getString(8));
            user.setFriendsCount(new Integer(cursor.getString(9)));
            user.setAllowChat(cursor.getString(10));

        }

        // return user
        return user;
    }
    // Deleting single user
    public void deleteUserById(BigInteger userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAG_USERS, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(userId) });
        db.close();
    }

    // Deleting multiple users
    public void deleteUsersByTag(String userTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAG_USERS, KEY_USER_TAG + " = ?",
                new String[] { userTag });
        db.close();
    }
    // Deleting single user
    public void deleteUsersById(ArrayList<UserModel> users) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i =0; i < users.size(); i++)
        {
            db.delete(TABLE_TAG_USERS, KEY_USER_ID + " = ?",
                    new String[] { String.valueOf(users.get(i).getId()) });
        }

        db.close();
    }

    // Getting All users related to a specific tag
    public ArrayList<UserModel> getUsersByTag(String userTag) {
        ArrayList<UserModel> users = new ArrayList<UserModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_USERS + " WHERE "+ KEY_USER_TAG +" = '"+userTag+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0)
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();
                    user.setId(new BigInteger(cursor.getString(0)));
                    user.setUserName(cursor.getString(1));
                    user.setLuck(cursor.getInt(2));
                    user.setHazel(cursor.getInt(3));

                    user.setFriendshipStatus(cursor.getInt(4));
                    user.setUpdateDateTime(cursor.getString(6));
                    user.setProfilePicture(cursor.getString(7));
                    user.setLevelScore(new Integer(cursor.getInt(8)));
                    user.setCups(cursor.getString(9));
                    user.setFriendsCount(new Integer(cursor.getString(10)));
                    user.setAllowChat(cursor.getString(11));


                    // Adding contact to list
                    users.add(user);
                } while (cursor.moveToNext());
            }
        }


        // return user list
        return users;
    }
    // Getting All users related to a specific tag
    public ArrayList<UserModel> getUsersByTag(String userTag, int skip) {
        ArrayList<UserModel> users = new ArrayList<UserModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_USERS + " WHERE "+ KEY_USER_TAG +" = '"+userTag+"' LIMIT "+ 10 +" OFFSET " + skip;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setId(new BigInteger(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setLuck(cursor.getInt(2));
                user.setHazel(cursor.getInt(3));

                user.setFriendshipStatus(cursor.getInt(4));
                user.setUpdateDateTime(cursor.getString(6));
                user.setProfilePicture(cursor.getString(7));
                user.setLevelScore(new Integer(cursor.getInt(8)));
                user.setCups(cursor.getString(9));
                user.setFriendsCount(new Integer(cursor.getString(10)));
                user.setAllowChat(cursor.getString(11));


                // Adding contact to list
                users.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return users;
    }

    // Getting All users related to a list of ids
    public ArrayList<UserModel> getUsersByIds(ArrayList<String> userIds) {
        ArrayList<UserModel> users = new ArrayList<UserModel>();
        // Select All Query
        StringBuilder ids = new StringBuilder();
        for(int i = 0; i < userIds.size(); i++)
        {
            ids.append("'"+userIds.get(i)+"'");
        }
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_USERS + " WHERE "+ KEY_USER_ID +" IN ( "+ids.toString()+" )";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0)
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();
                    user.setId(new BigInteger(cursor.getString(0)));
                    user.setUserName(cursor.getString(1));
                    user.setLuck(cursor.getInt(2));
                    user.setHazel(cursor.getInt(3));

                    user.setFriendshipStatus(cursor.getInt(4));
                    user.setUpdateDateTime(cursor.getString(6));
                    user.setProfilePicture(cursor.getString(7));
                    user.setLevelScore(new Integer(cursor.getInt(8)));
                    user.setCups(cursor.getString(9));
                    user.setFriendsCount(new Integer(cursor.getString(10)));
                    user.setAllowChat(cursor.getString(11));


                    // Adding contact to list
                    users.add(user);
                } while (cursor.moveToNext());
            }
        }


        // return user list
        return users;
    }

    public void saveUsers(ArrayList<UserModel> users, String tag, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteUsersByTag(tag);
        for(int i = 0; i < users.size(); i++)
        {
            addUser(users.get(i), tag);
        }
    }
    /////////////////////meysam - user table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - word table specifications - start///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // words table name
    private static final String TABLE_WORDS = "Words";
    //  Table Columns names
    private static final String KEY_WORD_ID = "WordId";
    private static final String KEY_WORD_VALUE = "WordValue";
    private static final String KEY_WORD_LENGTH= "WordLength";
    private static final String KEY_WORD_LENGTH_WITHOUT_SPACE = "WordLengthWithoutSpace";
    private static final String KEY_WORD_ANSWERED_LETTERS = "WordAnsweredLetters";
    private static final String KEY_WORD_ANSWERED_LETTERS_POSITIONS = "WordAnsweredLettersPositions";
    private static final String KEY_WORD_WRONG_ANSWERED_LETTERS = "WordWrongAnsweredLetters";
    private static final String KEY_WORD_WRONG_ANSWERED_LETTERS_POSITIONS = "WordWrongAnsweredLettersPositions";
    private static final String KEY_WORD_ANSWER_LETTERS_POSITIONS = "WordAnswerLettersPositions";
    private static final String KEY_WORD_ALL_LETTERS_POSITIONS = "WordAllLettersPositions";
    private static final String KEY_WORD_ALL_LETTERS = "WordAllLetters";
    private static final String KEY_WORD_CATEGORY = "WordCategory";
    private static final String KEY_WORD_WORDS = "WordWords";


    private static final String KEY_WORD_TAG = "WordTag";


    /**
     * Storing word details in sqlite database
     * */
    public void addWord(WordModel word, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_WORD_TAG, tag);
        if(word.getId() != null)
            values.put(KEY_WORD_ID, word.getId().toString());
        values.put(KEY_WORD_LENGTH_WITHOUT_SPACE, word.getLengthWithoutSpace());
        values.put(KEY_WORD_LENGTH, word.getLength());
        values.put(KEY_WORD_VALUE, word.getWord());
        values.put(KEY_WORD_CATEGORY, word.getCategory());

        StringBuilder sb = new StringBuilder();
        if(word.getAnsweredLetters() != null &&
                word.getAnsweredLetters().size()>0)
        {
            for(String s: word.getAnsweredLetters()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ANSWERED_LETTERS, sb.toString());
        }

        if(word.getAnsweredLettersPositions() != null &&
                word.getAnsweredLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAnsweredLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ANSWERED_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getWrongAnsweredLetters() != null &&
                word.getWrongAnsweredLetters().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getWrongAnsweredLetters()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_WRONG_ANSWERED_LETTERS, sb.toString());
        }


        if(word.getWrongAnsweredLettersPositions() != null &&
                word.getWrongAnsweredLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getWrongAnsweredLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_WRONG_ANSWERED_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getAnswerLettersPositions() != null &&
                word.getAnswerLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAnswerLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ANSWER_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getAllLettersPositions() != null &&
                word.getAllLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAllLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ALL_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getAllLetters() != null &&
                word.getAllLetters().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAllLetters()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ALL_LETTERS, sb.toString());
        }

        if(word.getWords() != null &&
                word.getWords().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getWords()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_WORDS, sb.toString());
        }


        // Inserting Row
        try
        {
            long id = db.insertOrThrow(TABLE_WORDS, null, values);

        }
        catch (Exception ex)
        {
            int temp = 0;
        }
        db.close(); // Closing database connection

    }

    public void editWord(WordModel word, String tag) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        if(tag!=null) values.put(KEY_WORD_TAG, tag);

//        if(user.getId()!=null) values.put(KEY_USER_ID, user.getId().toString());
        if(word.getWord()!=null) values.put(KEY_WORD_VALUE, word.getWord());
        if(word.getLength()!=null) values.put(KEY_WORD_LENGTH, word.getLength());
        if(word.getLengthWithoutSpace()!=null) values.put(KEY_WORD_LENGTH_WITHOUT_SPACE, word.getLengthWithoutSpace());
        if(word.getCategory()!=null) values.put(KEY_WORD_CATEGORY, word.getCategory());


        StringBuilder sb = new StringBuilder();
        if(word.getAnsweredLetters() != null &&
                word.getAnsweredLetters().size()>0)
        {
            for(String s: word.getAnsweredLetters()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ANSWERED_LETTERS, sb.toString());
        }

        if(word.getAnsweredLettersPositions() != null &&
                word.getAnsweredLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAnsweredLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ANSWERED_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getWrongAnsweredLetters() != null &&
                word.getWrongAnsweredLetters().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getWrongAnsweredLetters()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_WRONG_ANSWERED_LETTERS, sb.toString());
        }


        if(word.getWrongAnsweredLettersPositions() != null &&
                word.getWrongAnsweredLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getWrongAnsweredLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_WRONG_ANSWERED_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getAnswerLettersPositions() != null &&
                word.getAnswerLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAnswerLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ANSWER_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getAllLettersPositions() != null &&
                word.getAllLettersPositions().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAllLettersPositions()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ALL_LETTERS_POSITIONS, sb.toString());
        }

        if(word.getAllLetters() != null &&
                word.getAllLetters().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getAllLetters()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_ALL_LETTERS, sb.toString());
        }

        if(word.getWords() != null &&
                word.getWords().size()>0)
        {
            sb = new StringBuilder();
            for(String s: word.getWords()) {
                sb.append(s).append(',');
            }
            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_WORD_WORDS, sb.toString());
        }


        // Edit Row
        long id = db.update(TABLE_WORDS, values,KEY_WORD_ID+"="+word.getId(),null);
        db.close(); // Closing database connection
    }

    // Getting single word by tag
    public WordModel getWordByTag(String wordTag) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORDS, new String[] { KEY_WORD_ID,
                        KEY_WORD_VALUE,
                        KEY_WORD_LENGTH,
                        KEY_WORD_LENGTH_WITHOUT_SPACE,
                        KEY_WORD_ANSWERED_LETTERS,
                        KEY_WORD_ANSWERED_LETTERS_POSITIONS,
                        KEY_WORD_WRONG_ANSWERED_LETTERS,
                        KEY_WORD_WRONG_ANSWERED_LETTERS_POSITIONS,
                        KEY_WORD_ANSWER_LETTERS_POSITIONS,
                        KEY_WORD_ALL_LETTERS_POSITIONS,
                        KEY_WORD_ALL_LETTERS,
                        KEY_WORD_CATEGORY,
                        KEY_WORD_WORDS},
                        KEY_WORD_TAG + "=?",
                new String[] { wordTag }, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();

        WordModel word = null;
        if(cursor.getCount() > 0)
        {
            word = new WordModel();
            word.setId(new BigInteger(cursor.getString(0)));
            word.setWord(cursor.getString(1));
            word.setLength(new Integer(cursor.getString(2)));
            word.setLengthWithoutSpace(new Integer(cursor.getString(3)));
            if(cursor.getString(4) != null)
                word.setAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(4).split("\\s*,\\s*"))));
            if(cursor.getString(5) != null)
                word.setAnsweredLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(5).split("\\s*,\\s*"))));
            if(cursor.getString(6) != null)
                word.setWrongAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(6).split("\\s*,\\s*"))));
            if(cursor.getString(7) != null)
                word.setWrongAnsweredLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(7).split("\\s*,\\s*"))));
            if(cursor.getString(8) != null)
                word.setAnswerLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(8).split("\\s*,\\s*"))));
            if(cursor.getString(9) != null)
                word.setAllLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(9).split("\\s*,\\s*"))));
            if(cursor.getString(10) != null)
                word.setAllLetters(new ArrayList<String>(Arrays.asList(cursor.getString(10).split("\\s*,\\s*"))));
            if(cursor.getString(11) != null)
                word.setCategory(new Integer(cursor.getString(11)));
            if(cursor.getString(12) != null)
                word.setWords(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));

        }

        // return user
        return word;
    }

    // Getting single word by id
    public WordModel getWordById(BigInteger wordId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORDS, new String[] { KEY_WORD_ID,
                KEY_WORD_VALUE,
                KEY_WORD_LENGTH,
                KEY_WORD_LENGTH_WITHOUT_SPACE,
                KEY_WORD_ANSWERED_LETTERS,
                KEY_WORD_ANSWERED_LETTERS_POSITIONS,
                KEY_WORD_WRONG_ANSWERED_LETTERS,
                KEY_WORD_WRONG_ANSWERED_LETTERS_POSITIONS,
                        KEY_WORD_ANSWER_LETTERS_POSITIONS,
                        KEY_WORD_ALL_LETTERS_POSITIONS,
                        KEY_WORD_ALL_LETTERS,
                        KEY_WORD_CATEGORY,
                        KEY_WORD_WORDS},
                KEY_WORD_ID + "=?",
                new String[] { String.valueOf(wordId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WordModel word = null;
        if(cursor.getCount() > 0)
        {
            word = new WordModel();
            word.setId(new BigInteger(cursor.getString(0)));
            word.setWord(cursor.getString(1));
            word.setLength(cursor.getInt(2));
            word.setLengthWithoutSpace(cursor.getInt(3));
            if(cursor.getString(4) != null)
                word.setAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(4).split("\\s*,\\s*"))));
            if(cursor.getString(5) != null)
                word.setAnsweredLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(5).split("\\s*,\\s*"))));
            if(cursor.getString(6) != null)
                word.setWrongAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(6).split("\\s*,\\s*"))));
            if(cursor.getString(7) != null)
                word.setWrongAnsweredLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(7).split("\\s*,\\s*"))));
            if(cursor.getString(8) != null)
                word.setAnswerLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(8).split("\\s*,\\s*"))));
            if(cursor.getString(9) != null)
                word.setAllLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(9).split("\\s*,\\s*"))));
            if(cursor.getString(10) != null)
                word.setAllLetters(new ArrayList<String>(Arrays.asList(cursor.getString(10).split("\\s*,\\s*"))));
            if(cursor.getString(11) != null)
                word.setCategory(new Integer(cursor.getString(11)));
            if(cursor.getString(12) != null)
                word.setWords(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));

        }

        // return word
        return word;
    }
    // Deleting single word
    public void deleteWordById(BigInteger wordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, KEY_WORD_ID + " = ?",
                new String[] { String.valueOf(wordId) });
        db.close();
    }

    // Deleting multiple words
    public void deleteWordsByTag(String wordTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, KEY_WORD_TAG + " = ?",
                new String[] { wordTag });
        db.close();
    }

    // Getting All words related to a specific tag
    public ArrayList<WordModel> getWordsByTag(String wordTag) {
        ArrayList<WordModel> words = new ArrayList<WordModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE "+ KEY_WORD_TAG +" = '"+wordTag+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WordModel word = new WordModel();
                word.setId(new BigInteger(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setLength(cursor.getInt(2));
                word.setLengthWithoutSpace(cursor.getInt(3));
                if(cursor.getString(4) != null)
                    word.setAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(4).split("\\s*,\\s*"))));
                if(cursor.getString(5) != null)
                    word.setAnsweredLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(5).split("\\s*,\\s*"))));
                if(cursor.getString(6) != null)
                    word.setWrongAnsweredLetters(new ArrayList<String>(Arrays.asList(cursor.getString(6).split("\\s*,\\s*"))));
                if(cursor.getString(7) != null)
                    word.setWrongAnsweredLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(7).split("\\s*,\\s*"))));
                if(cursor.getString(8) != null)
                    word.setAnswerLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(8).split("\\s*,\\s*"))));
                if(cursor.getString(9) != null)
                    word.setAllLettersPositions(new ArrayList<String>(Arrays.asList(cursor.getString(9).split("\\s*,\\s*"))));
                if(cursor.getString(10) != null)
                    word.setAllLetters(new ArrayList<String>(Arrays.asList(cursor.getString(10).split("\\s*,\\s*"))));

                if(cursor.getString(11) != null)
                    word.setCategory(new Integer(cursor.getString(11)));

                if(cursor.getString(12) != null)
                    word.setWords(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));

                // Adding contact to list
                words.add(word);
            } while (cursor.moveToNext());
        }

        // return user list
        return words;
    }

    public void saveWords(ArrayList<WordModel> words, String tag, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteWordsByTag(tag);
        for(int i = 0; i < words.size(); i++)
        {
            addWord(words.get(i), tag);
        }
    }
    /////////////////////meysam - word table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - chat table specifications - start///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // users table name
    private static final String TABLE_CHATS = "tagChats";
    //  Table Columns names
    private static final String KEY_CHAT_ID = "ChatId";
    private static final String KEY_CHAT_USERNAME = "ChatUserName";
    private static final String KEY_CHAT_TEXT = "ChatText";
    private static final String KEY_CHAT_TYPE= "ChatType";
    private static final String KEY_CHAT_DATETIME = "ChatDateTime";


    /**
     * Storing chat details in sqlite database
     * */
    public void addChat(ChatModel chat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if(chat.getId() != null)
            values.put(KEY_CHAT_ID, chat.getId().toString());
        values.put(KEY_CHAT_USERNAME, chat.getUserName());
        values.put(KEY_CHAT_TEXT, chat.getText());
        values.put(KEY_CHAT_TYPE, chat.getType());
        values.put(KEY_CHAT_DATETIME, chat.getDateTime());

        // Inserting Row
        try
        {
            long id = db.insertOrThrow(TABLE_CHATS, null, values);

        }
        catch (Exception ex)
        {
            int temp = 0;
        }
        db.close(); // Closing database connection

    }

    // Getting single chat by id
    public ChatModel getChatById(BigInteger chatId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHATS, new String[] { KEY_CHAT_ID,
                        KEY_CHAT_USERNAME,
                        KEY_CHAT_TEXT,
                        KEY_CHAT_TYPE,
                        KEY_CHAT_DATETIME}, KEY_CHAT_ID + "=?",
                new String[] { String.valueOf(chatId) }, null, null, null, null);
        ChatModel chat = new ChatModel();
        if (cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            chat.setId(new BigInteger(cursor.getString(0)));
            chat.setUserName(cursor.getString(1));
            chat.setText(cursor.getString(2));
            chat.setType(cursor.getInt(3));
            chat.setDateTime(cursor.getString(4));

        }


        // return chat
        return chat;
    }

    // Deleting single chat by id
    public void deleteChatById(BigInteger chatId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHATS, KEY_CHAT_ID + " = ?",
                new String[] { String.valueOf(chatId) });
        db.close();
    }

    // Deleting multiple chats by user name
    public void deleteChatsByUserName(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHATS, KEY_CHAT_USERNAME + " = ?",
                new String[] { userName });
        db.close();
    }


    // Getting All chats related to a specific userName
    public ArrayList<ChatModel> getChatsByUserName(String userName) {
        ArrayList<ChatModel> chats = new ArrayList<ChatModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHATS + " WHERE "+ KEY_CHAT_USERNAME +" = '"+userName+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0)
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ChatModel chat = new ChatModel();
                    chat.setId(new BigInteger(cursor.getString(0)));
                    chat.setUserName(cursor.getString(1));
                    chat.setText(cursor.getString(2));
                    chat.setType(cursor.getInt(3));
                    chat.setDateTime(cursor.getString(4));

                    // Adding chat to list
                    chats.add(chat);
                } while (cursor.moveToNext());
            }
        }


        // return chat list
        return chats;
    }

    // Getting All chats
    public ArrayList<ChatModel> getAllChats() {
        ArrayList<ChatModel> chats = new ArrayList<ChatModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHATS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0)
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ChatModel chat = new ChatModel();
                    chat.setId(new BigInteger(cursor.getString(0)));
                    chat.setUserName(cursor.getString(1));
                    chat.setText(cursor.getString(2));
                    chat.setType(cursor.getInt(3));
                    chat.setDateTime(cursor.getString(4));

                    // Adding chat to list
                    chats.add(chat);
                } while (cursor.moveToNext());
            }
        }


        // return chat list
        return chats;
    }

    /////////////////////meysam - chat table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
