package ir.fardan7eghlim.luckylord.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.AppController;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;


/**
 * Created by Meysam on 2/16/2017.
 */

public class Utility {
    static int runningActivities = 0;
    /////////////////////////////////////
    public static int TIMEOUT_TIME = 5000;//meysam - in milis

    ///////////////////////////////////RESPONSE_CODES_BAZAR//////////////////////////////////////////////////////////////

    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_OK = 0;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE= 4;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int RESPONSE_CODE_BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;


    public static final int CUP_INDEX_1000_QUESTION_ANSWERED = 0;
    public static final int CUP_INDEX_DRAW_WINNER_ONCE = 1;//meysam - it is server side...
    public static final int CUP_INDEX_MATCH_WINNER_100_TIMES = 2;
    public static final int CUP_INDEX_MATCH_PERFECT_ANSWERS_ONCE = 3;
    public static final int CUP_INDEX_INVITE_ONE_PERSON = 4;//meysam - it is server side...
    public static final int CUP_INDEX_MATCH_WINNER_1000_TIMES = 5;
    public static final int CUP_INDEX_10000_QUESTION_ANSWERED = 6;
    public static final int CUP_INDEX_LEAGUE_1 = 7;//meysam - not for now...
    public static final int CUP_INDEX_LEAGUE_2 = 8;//meysam - not for now...

    public static final int CUP_DIALOG_TIME = 5000;
    public static final int LEVEL_DIALOG_TIME = 5000;


    public static final int FRIENDSHIP_REQUEST_COST = 70;
    public static final int CHAT_MESSAGE_COST = 1;

    public static final int LEVEL_CHANGE_SCALE = 5;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final int ALLOWED_FREE_MATCH_COUNT = 5;
    public static final int ALLOWED_FREE_MATCH_INTERVAL_TIME = 1440;//meysam - in minutes...
    public static final int ALLOWED_FREE_MATCH_COST = 50;//meysam

    public static boolean isEmailValid(String email)
    {
        // Replace this with our additional logic - meysam
        return email.contains("@");
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isPasswordValid(String password)
    {
        // Replace this with our additional logic - meysam
        return password.length() > 4;
    }

    public static String[] tokenDecode(String JWTEncoded) {
        String[] result = new String[2];
        try {
            String[] split = JWTEncoded.split("\\.");
            result[0]= getJson(split[0]);
            result[1]= getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            //Error
        }

        return result;
    }

    public static String generateVisitorCode(Context cntx) {
        String result ="Visitor_"+Utility.getDeviceCode(cntx)+"#"+Utility.getDeviceName();

        return result;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
    ///convert bitmap to string
    public static String getStringImage(Bitmap bmp){
        double z=150.0/(double)bmp.getWidth();
        bmp=FileProcessor.getResizedBitmap(bmp,(int)(bmp.getWidth()*z), (int)(bmp.getHeight()*z));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //convert String to bitmap
    public static Bitmap getBitmapImage(String temp){
        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String convertSec2Min(String sec)
    {
        try
        {   double minutes = TimeUnit.SECONDS.toMinutes(new Integer(sec));
            double seconds = new Integer(sec) - (minutes*60);
            String min = String.valueOf((int)minutes);
            if(minutes<10)
            {
                min = "0"+String.valueOf((int)minutes);
            }
            String seco = String.valueOf((int)seconds);
            if(seconds<10)
            {
                seco = "0"+String.valueOf((int)seconds);
            }
            return min+":"+seco;

        }
        catch (Exception ex)
        {
            return "00:00";
        }

    }

    //fix date formater
    public static String fixDateFormat_01(String date){
        String[] temp=date.split("/");
        String temp2=temp[0]+"/";
        if(temp[1].length()<2)
            temp2+="0"+temp[1]+"/";
        else
            temp2+=temp[1]+"/";
        if(temp[2].length()<2)
            temp2+="0"+temp[2];
        else
            temp2+=temp[2];
        return temp2;
    }
    public static String fixDateFormat_02(String date){
        String[] temp=date.split("-");
        String temp2=temp[0]+"/";
        if(temp[1].length()<2)
            temp2+="0"+temp[1]+"/";
        else
            temp2+=temp[1]+"/";
        if(temp[2].length()<2)
            temp2+="0"+temp[2];
        else
            temp2+=temp[2];
        return temp2;
    }
    //check network
    public static boolean isNetworkAvailable(Context context){
        boolean available = false;
        /** Getting the system's connectivity service */
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        /** Getting active network interface  to get the network's status */
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting())
            available = true;
        /** Returning the status of the network */
        return available;
    }

    public static boolean isInternetAvailable(final Context cntx) {

        final CountDownLatch latch = new CountDownLatch(1);
        final boolean[] value = new boolean[1];
        Thread uiThread = new HandlerThread("UIHandler"){
            @Override
            public void run(){
                try {
                    ConnectivityManager cm = (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();

                    if (netInfo != null && netInfo.isConnected()) {
                        // Network is available but check if we can get access from the
                        // network.
                        URL url = new URL("http://www.google.com");
                        HttpURLConnection urlc = (HttpURLConnection) url
                                .openConnection();
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(2000); // Timeout 2 seconds.
                        urlc.connect();

                        if (urlc.getResponseCode() == 200) // Successful response.
                        {
                            value[0] = true;
                        } else {
                            value[0] = false;
                        }
                    } else {
                        value[0] = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown(); // Release await() in the test thread.
            }
        };
        uiThread.start();
        try {
            latch.await(); // Wait for countDown() in the UI thread. Or could uiThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // value[0] holds 2 at this point.
        return value[0];

    }

    //search in List
    public static ArrayList<Object> searchInList(ArrayList<Object> main_list,ArrayList<String> list,String target){
        ArrayList<Object> returned_list=new ArrayList<>();

        for(int i=0;i<list.size();i++){
            if(list.get(i).contains(target)){
                returned_list.add(main_list.get(i));
            }
        }

        return  returned_list;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context cntx) {
        ActivityManager manager = (ActivityManager) cntx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.getClass().getName().equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String getDeviceCode(Context cntx)
    {
        return Settings.Secure.getString(cntx.getContentResolver(),
            Settings.Secure.ANDROID_ID);
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";
    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String faToEn(String num) {
        return num
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
    }
    public static String enToFa(String num) {
        return num
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
    }

    //check anim is over or not
    public static void checkIfAnimationDone(final Context context, AnimationDrawable anim){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(context,a);
                } else{
                    Utility.displayToast(context, "ANIMATION DONE!", Toast.LENGTH_SHORT);
                }
            }
        }, timeBetweenChecks);
    }

    public static Bitmap getBitmapFromAsset(Context context, String name) {
        AssetManager assetManager = context.getAssets();
        String filePath = "avatars/"+name+".bmp";
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }


    public static String convertDateTimeGorgeian2Persian(String gorgeianDateTime)
    {

        String[] datePart = gorgeianDateTime.split(" ");
        String[] Date=datePart[0].split("-");
        String temp="";
        CalendarTool ct=new CalendarTool();
        ct.setGregorianDate(new Integer(Date[0]),new Integer(Date[1]),new Integer(Date[2]));
        String newDateTime= ct.getIranianDate();
        temp=newDateTime + " " + datePart[1];

        return temp;

    }

    public static String convertDateGorgeian2Persian(String gorgeianDateTime)
    {

        String[] Date = gorgeianDateTime.split("-");
        CalendarTool ct=new CalendarTool();
        ct.setGregorianDate(new Integer(Date[0]),new Integer(Date[1]),new Integer(Date[2]));
        String newDateTime= ct.getIranianDate();

        return newDateTime;

    }

    public static String convertDatePersian2Gorgeian(String persianDateTime)
    {

        String[] Date = persianDateTime.split("/");
        CalendarTool ct=new CalendarTool();
        ct.setIranianDate(new Integer(Date[0]),new Integer(Date[1]),new Integer(Date[2]));
        String newDateTime= ct.getGregorianDate();

        return newDateTime;

    }

    public static void goToUrl (Context context,String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchBrowser);
    }

    public static void finishActivity(final Activity context){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.finish();
            }
        }, 800);
    }

    //check if user has enough hazsel
    public static boolean hasEnoughCoin(Context c,int amountNeeded){
        SessionModel session=new SessionModel(c);
        if(session.getCurrentUser().getHazel()>=amountNeeded){
            return true;
        }
        return false;
    }
    //check if user has enough luck
    public static boolean hasEnoughLuck(Context c,int amountNeeded){
        SessionModel session=new SessionModel(c);
        if(session.getCurrentUser().getLuck()>=amountNeeded){
            return true;
        }
        return false;
    }
    //check in coins
    public static void changeCoins(Context c,int amount){
        SessionModel session=new SessionModel(c);
        int s=(session.getCurrentUser().getHazel())+amount;
        // meysam :: save new Hazel in user-session
        session.saveItem(SessionModel.KEY_HAZEL,String.valueOf(s));
    }
    //check in lucks
    public static void changeLucks(Context c,int amount){
        SessionModel session=new SessionModel(c);
        int s=(session.getCurrentUser().getLuck())+amount;
        // meysam :: save new Lucks in user-session
        session.saveItem(SessionModel.KEY_LUCK,String.valueOf(s));
    }

    //store Bitmap in SD
    public static String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
    public static void store(Bitmap bm, String fileName){
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //capture from view
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

//    //for toast type : 0 is short and 1 is long
public static void displayToast(Context cntx, String toastMsg, int toastType){

    try {// try-catch to avoid stupid app crashes
        LayoutInflater inflater = LayoutInflater.from(cntx);

        View mainLayout = inflater.inflate(R.layout.toast_layout, null);
        View rootLayout = mainLayout.findViewById(R.id.toast_layout_root);


        TextView text = (TextView) mainLayout.findViewById(R.id.text);
        text.setText(toastMsg);

        Toast toast = new Toast(cntx);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//            toast.setGravity(Gravity.CENTER, 0, 0);

        if (toastType==0)//(isShort)
            toast.setDuration(Toast.LENGTH_SHORT);
        else
            toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(rootLayout);
        toast.show();
    }
    catch(Exception ex) {// to avoid stupid app crashes
//            Log.w(TAG, ex.toString());
    }
}

public static String getHintFromFile(Context cntx) {

    String result = "";

    ArrayList<String> list = new ArrayList<>();
    Scanner s = new Scanner(cntx.getResources().openRawResource(R.raw.tips));

    try
    {

     while (s.hasNext())
     {
         list.add(s.nextLine());
     }
        Random rnd = new Random(new Date().getTime());
        int x = rnd.nextInt(list.size());

        result = list.get(x);

    }
    catch (Exception ex)
    {
        //meysam - nothing for now
    }
    finally {
        s.close();
    }

    return result;

}

public static boolean isTimeSpent(String lastTime, int amountMinute)
{
    long currentDateTime = new Date().getTime();
    long pervouiseDateTime = new BigInteger(lastTime).longValue();
    if((currentDateTime - pervouiseDateTime) > (amountMinute * 60000) )
        return true;
    return false;
}

public static LogModel generateLogError(Exception ex)
{
    Context iCntx = AppController.getInstance().getApplicationContext();
    LogModel log = new LogModel(iCntx);
    String message = "message: خطای کلی رخ داد :" + (ex == null?"ندارد":ex.getMessage());
    message += "#LocalizedMessage:#"+(ex == null?"ندارد":ex.getLocalizedMessage());
    message += "#Cause:#"+(ex == null?"ندارد":(ex.getCause() == null?"ندارد":ex.getCause().getMessage()));

    String stackTrace = "";
    if(ex == null)
        message += " #CallStack:# " + "ندارد";
    else
    {
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            stackTrace += ex.getStackTrace()[i].toString() + "\n";
        }
        Throwable tmp = ex;
        int j = 0;
        while ((tmp = tmp.getCause()) != null && j < 5) {
            j++;
            stackTrace += "Coused by:\n";
            for (int i = 0; i < tmp.getStackTrace().length; i++) {
                stackTrace += tmp.getStackTrace()[i].toString() + "\n";
            }
        }


        message += " #CallStack:# " + stackTrace;

    }
    String deviceInfo = "";
    deviceInfo += "OS version: " + System.getProperty("os.version") + "\n";
    deviceInfo += "API level: " + Build.VERSION.SDK_INT + "\n";
    deviceInfo += "Manufacturer: " + Build.MANUFACTURER + "\n";
    deviceInfo += "Device: " + Build.DEVICE + "\n";
    deviceInfo += "Model: " + Build.MODEL + "\n";
    deviceInfo += "Product: " + Build.PRODUCT + "\n";

    PackageInfo pInfo = null;
    try {
        pInfo = iCntx.getPackageManager().getPackageInfo(iCntx.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }

    if(pInfo != null)
        deviceInfo += "App Version: " + pInfo.versionName + "\n";
    else
        deviceInfo += "App Version: " + "not Specified(pInfo is null)" + "\n";




    message += " #Device Info:# " + deviceInfo;


    log.setErrorMessage(message);
    if(ex != null)
            if(ex.getClass() != null)
                log.setContollerName(ex.getClass().getClass().getName());
    SessionModel session = new SessionModel(iCntx);
    if(session.getCurrentUser() != null)
    {
        log.setUserId(session.getCurrentUser().getId());
    }
    else
    {
        log.setUserId(null);
    }

    log.insert();

    return log;

}

    public static String convertArrayIntToStrig(ArrayList<Integer> array){
        String temp="";
        if(array!=null)
            for(int i=0;i<array.size();i++){
                temp+=array.get(i)+(i==array.size()?"":"_");
            }
        return temp;
    }

    public static String convertArrayStringToStrig(ArrayList<String> array){
        String temp="";
        if(array!=null)
            for(int i=0;i<array.size();i++){
                temp+=array.get(i)+(i==array.size()?"":"_");
            }
        return temp;
    }

    public static ArrayList<String> convertStringToArrayStrig(String underlineSeperatedValues){
        return new ArrayList<String>(Arrays.asList(underlineSeperatedValues.split("_")));
    }

    public static float getVersion(Context cntx)
    {
        PackageInfo pInfo = null;
        Float version = null;
        try {
            pInfo = cntx.getPackageManager().getPackageInfo(cntx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(pInfo != null)
        {
            String[] tmpVersion = pInfo.versionName.split("[.]");
            version = new Float(tmpVersion[0]+"."+tmpVersion[1]+tmpVersion[2]);
        }
        return version;
    }


    public static String getVersionString(Context cntx)
    {
        PackageInfo pInfo = null;
        Float version = null;
        try {
            pInfo = cntx.getPackageManager().getPackageInfo(cntx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(pInfo != null)
        {
            return pInfo.versionName;
        }
        else
        {
            return null;
        }

    }

    ///return boolian indicating as to increase level or not...
    public static boolean addLevelScore(Context cntx, Integer score)
    {
        SessionModel session = new SessionModel(cntx);
        int oldScore = Integer.valueOf(session.getCurrentUser().getLevelScore())+session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE);

        int newScore = oldScore + score;
        session.saveItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE,session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE)+score);
        Integer currentLevel = Integer.valueOf(session.getCurrentUser().getLevelScore());
        if(Math.floor(Math.sqrt(newScore/LEVEL_CHANGE_SCALE)) > Math.floor(Math.sqrt(currentLevel/LEVEL_CHANGE_SCALE)))
        {
           //meysam - new level reached...
            return true;
        }
        else
        {
            return false;
        }
    }

    public static ArrayList<Object> addCupsScore(Context cntx, String sessionKey)
    {
        ArrayList<Object> result = new ArrayList<>();
        SessionModel session = new SessionModel(cntx);
        Integer scoreCount = 0;
     if(sessionKey.equals(SessionModel.KEY_MATCH_ENTIRELY_TRUE_COUNT))
        {
            scoreCount = session.getIntegerItem(SessionModel.KEY_MATCH_ENTIRELY_TRUE_COUNT);
            scoreCount++;
            if(scoreCount >= 1)
            {
                //meysam - check if cup is not given then give it ...
                if(Character.toString(session.getCurrentUser().getCups().charAt(Utility.CUP_INDEX_MATCH_PERFECT_ANSWERS_ONCE)).equals("0"))
                {
                    //meysam - user earns this cup...
                    result.add(true);
                    result.add(Utility.CUP_INDEX_MATCH_PERFECT_ANSWERS_ONCE);

                    session.addCupToServerUpdate(Utility.CUP_INDEX_MATCH_PERFECT_ANSWERS_ONCE);
                }

            }
            session.saveItem(SessionModel.KEY_MATCH_ENTIRELY_TRUE_COUNT,scoreCount);
        }
        else if(sessionKey.equals(SessionModel.KEY_QUESTION_TRUE_COUNT))
        {
            scoreCount = session.getIntegerItem(SessionModel.KEY_QUESTION_TRUE_COUNT);
            scoreCount++;
            if(scoreCount >= 1000)
            {
                //meysam - check if cup is not given then give it ...
                if(Character.toString(session.getCurrentUser().getCups().charAt(Utility.CUP_INDEX_1000_QUESTION_ANSWERED)).equals("0"))
                {
                    //meysam - user earned this cup...
                    result.add(true);
                    result.add(Utility.CUP_INDEX_1000_QUESTION_ANSWERED);

                    session.addCupToServerUpdate(Utility.CUP_INDEX_1000_QUESTION_ANSWERED);
                }

            }
            else if(scoreCount >= 10000)
            {
                //meysam - check if cup is not given then give it ...
                if(Character.toString(session.getCurrentUser().getCups().charAt(Utility.CUP_INDEX_10000_QUESTION_ANSWERED)).equals("0"))
                {
                    //meysam - user earned this cup...
                    result.add(true);
                    result.add(Utility.CUP_INDEX_10000_QUESTION_ANSWERED);

                    session.addCupToServerUpdate(Utility.CUP_INDEX_10000_QUESTION_ANSWERED);
                }

            }
            session.saveItem(SessionModel.KEY_QUESTION_TRUE_COUNT,scoreCount);

        }
        else
         {
             //meysam - this must never happen...
             result.add(false);
         }

        if(result.size() == 0)
            result.add(false);
        return result;

    }

    public static double calculateScorePercent(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        Integer currentLevelScore = Integer.valueOf(session.getCurrentUser().getLevelScore());
        double currentLevel = Math.floor(Math.sqrt((double) currentLevelScore/LEVEL_CHANGE_SCALE));
        double nextLevel = currentLevel+1;
        double currentScore = Integer.valueOf(session.getCurrentUser().getLevelScore()) + session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE);

        return (((currentScore - Math.pow( currentLevel,2.0)*LEVEL_CHANGE_SCALE) * 100) /(Math.pow( nextLevel,2.0)*LEVEL_CHANGE_SCALE - Math.pow( currentLevel,2.0)*LEVEL_CHANGE_SCALE) );
    }

    public static boolean shouldLevelChange(Context cntx)
    {
        SessionModel session = new SessionModel(cntx);
        int oldScore = Integer.valueOf(session.getCurrentUser().getLevelScore());

        int newScore = oldScore+ session.getIntegerItem(SessionModel.KEY_LEVEL_SCORE_COUNT_FOR_SERVER_UPDATE);

        Integer currentLevel = Integer.valueOf(session.getCurrentUser().getLevelScore());
        if(Math.floor(Math.sqrt(newScore/LEVEL_CHANGE_SCALE)) > Math.floor(Math.sqrt(currentLevel/LEVEL_CHANGE_SCALE)))
        {
            //meysam - new level reached...
            return true;
        }
        else
        {
            return false;
        }

    }
    public static Integer getLevelDiffrence(Integer oldAmount, Integer newAmount)
    {

        double result = Math.floor(Math.sqrt(newAmount/LEVEL_CHANGE_SCALE)) - Math.floor(Math.sqrt(oldAmount/LEVEL_CHANGE_SCALE));

       return Integer.valueOf((int) result);

    }
    public static ArrayList<Integer> getChangedCups(Context cntx,String newCupsInput)
    {
        ArrayList<Integer> result = new ArrayList<>();
        SessionModel session = new SessionModel(cntx);
        String temp = session.getCurrentUser().getCups();
        String[] oldCups = temp.replaceAll("\\s+","").split("(?!^)");

        temp = newCupsInput;
        String[] newCups = temp.replaceAll("\\s+","").split("(?!^)");

        for(int i = 0; i < oldCups.length; i++)
        {
            if(Integer.valueOf(oldCups[i]) < Integer.valueOf(newCups[i]))
            {
                result.add(i);
            }
        }

        return result;

    }
    public static Boolean isGainedSpecificCups(Context cntx,Integer cupIndex)
    {
        ArrayList<Integer> result = new ArrayList<>();
        SessionModel session = new SessionModel(cntx);
        String temp = session.getCurrentUser().getCups();
        String[] oldCups = temp.split(",");
        if(oldCups[cupIndex].equals("1"))
        {
            return true;
        }
        return false;


    }
    public static Integer calculateLevel(Integer levelScore)
    {
        return Integer.valueOf(String.valueOf((int)Math.floor(Math.sqrt(levelScore/LEVEL_CHANGE_SCALE))));
    }

    public static String getCupTitleByCode(Context cntx, Integer cupCode)
    {
        String result = "";
        switch (cupCode)
        {
            case 0:
                result = cntx.getResources().getString(R.string.tlt_cup_1000_question_answered);
                break;
            case 1:
                result = cntx.getResources().getString(R.string.tlt_cup_draw_winner_once);
                break;
            case 2:
                result = cntx.getResources().getString(R.string.tlt_cup_match_winner_100_time);
                break;
            case 3:
                result = cntx.getResources().getString(R.string.tlt_cup_match_perfect_answers_once);
                break;
            case 4:
                result = cntx.getResources().getString(R.string.tlt_cup_invite_one_person);
                break;
            case 5:
                result = cntx.getResources().getString(R.string.tlt_cup_match_winner_1000_time);
                break;
            case 6:
                result = cntx.getResources().getString(R.string.tlt_cup_10000_question_answered);
                break;
            case 7:
                result = cntx.getResources().getString(R.string.tlt_cup_leage_1);
                break;
            case 8:
                result = cntx.getResources().getString(R.string.tlt_cup_leage_2);
                break;
            default:
                result = cntx.getResources().getSystem().getString(R.string.error_undefined);
                break;
        }

        return result;
    }

    //function for found is arabic or English
    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static String convertToCommaSeparatedString(ArrayList<Integer> inputList)
    {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < inputList.size(); i++)
        {

            result.append(i);
            if(i != (inputList.size()-1))
            {
                result.append(",");
            }

        }
        return result.toString();
    }

    public static String updateCupStringByArrayIndexes(String cups,ArrayList<Integer> inputList)
    {
        StringBuilder result = new StringBuilder(cups);

        for(int i = 0; i < inputList.size(); i++)
        {

            result.setCharAt(inputList.get(i), '1');

        }
        return result.toString();
    }
    public static String updateCupStringByCommaSeparatedIndexes(String cups,String commaSeparatedList)
    {
        StringBuilder result = new StringBuilder(cups);

        String[] commaseparatedCups = commaSeparatedList.replaceAll(",","").split("(?!^)");
        for(int i = 0; i < commaseparatedCups.length; i++)
        {

            result.setCharAt(Integer.valueOf( commaseparatedCups[i]), '1');

        }
        return result.toString();
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+100;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String unicodeToUft8(String unicodeString)
    {
        char aChar;
        int len = unicodeString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = unicodeString.charAt(x++);
            if (aChar == '\\') {
                aChar = unicodeString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = unicodeString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    public static boolean isMinuteLimitReached( String savedMilsec, String limitMin)
    {
        long diffMin = TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - Long.parseLong(savedMilsec));
        if(diffMin >= Integer.valueOf(limitMin))
        {
            return true;
        }
        return false;
    }

    public static boolean isPackageInstalled(Context context, String packagename) {
        try {
            context.getPackageManager().getApplicationInfo(packagename, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
