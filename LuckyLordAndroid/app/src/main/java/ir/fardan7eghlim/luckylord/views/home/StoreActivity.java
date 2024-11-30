package ir.fardan7eghlim.luckylord.views.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.IntentCompat;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.controllers.MarketController;
import ir.fardan7eghlim.luckylord.controllers.UserController;
import ir.fardan7eghlim.luckylord.models.LogModel;
import ir.fardan7eghlim.luckylord.models.MarketModel;
import ir.fardan7eghlim.luckylord.models.RequestRespondModel;
import ir.fardan7eghlim.luckylord.models.SessionModel;
import ir.fardan7eghlim.luckylord.models.widgets.LuckyTextView;
import ir.fardan7eghlim.luckylord.utils.DialogPopUpModel;
import ir.fardan7eghlim.luckylord.utils.billing.IabHelper;
import ir.fardan7eghlim.luckylord.utils.billing.IabResult;
import ir.fardan7eghlim.luckylord.utils.billing.Inventory;
import ir.fardan7eghlim.luckylord.utils.billing.Purchase;
import ir.fardan7eghlim.luckylord.utils.Anims;
import ir.fardan7eghlim.luckylord.utils.BaseActivity;
import ir.fardan7eghlim.luckylord.utils.DialogModel;
import ir.fardan7eghlim.luckylord.utils.Utility;
import ir.fardan7eghlim.luckylord.views.user.UserLoginActivity;

public class StoreActivity extends BaseActivity implements Observer {

    private AsyncTask<String, Integer, Boolean> asyncTask;
    private DialogModel DM;
    private int width;
    private int height;
    private Context cntx;

//    private int loadings;

    private static String hazel_convert_key;

    private static Inventory mInventory;
//    SessionModel session;
    private ArrayList<MarketModel> markets;
    HashMap<String,String> spinnerMap;

    MarketController mc;
    private boolean inCreating;

    TextView t_a;
    TextView t_b;
    TextView t_c;
    TextView t_d;
    TextView t_e;
    TextView t_f;
    TextView t_g;
    TextView t_h;


    // The helper object
    private IabHelper mHelper;
    // public key
    private String base64EncodedPublicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        DM=new DialogModel(StoreActivity.this);
        DM.show();

        inCreating = true;

        cntx = this;
//        session = new SessionModel(getApplicationContext());

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("market_activity_broadcast"));

//        loadings = 0;

        mc=new MarketController(StoreActivity.this);
        mc.addObserver(this);
        if(session.hasMarket())
        {
            MarketModel market = session.getMarket();
            mc.purchase(market);
        }

        if(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL) != 0 || session.getIntegerItem(SessionModel.KEY_FINAL_LUCK) != 0)
        {
            new UserController(getApplicationContext()).change(session.getIntegerItem(SessionModel.KEY_FINAL_HAZEL),session.getIntegerItem(SessionModel.KEY_FINAL_LUCK));
        }

        spinnerMap = new HashMap<String, String>();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


        t_a= (TextView) findViewById(R.id.shop_item_a);
        t_b= (TextView) findViewById(R.id.shop_item_b);
        t_c= (TextView) findViewById(R.id.shop_item_c);
        t_d= (TextView) findViewById(R.id.shop_item_d);
        t_e= (TextView) findViewById(R.id.shop_item_e);
        t_f= (TextView) findViewById(R.id.shop_item_f);
        t_g= (TextView) findViewById(R.id.shop_item_g);
        t_h= (TextView) findViewById(R.id.shop_item_h);

        t_a.setOnClickListener(onPurchaseClickListener);
        t_b.setOnClickListener(onPurchaseClickListener);
        t_c.setOnClickListener(onPurchaseClickListener);
        t_d.setOnClickListener(onPurchaseClickListener);
        t_e.setOnClickListener(onPurchaseClickListener);
        t_f.setOnClickListener(onPurchaseClickListener);

        t_g.setOnClickListener(onConvertClickListener);
        t_h.setOnClickListener(onConvertClickListener);

//        disableCartList();
        unavailableCartList();

        arangeGraphic_start();
        cloudMotion_a();

        //squirrel
        ImageView squirrel= (ImageView) findViewById(R.id.c_squirrel_s);
        squirrel.setImageResource(R.drawable.c_rabbit_anim_a);
        AnimationDrawable anim = (AnimationDrawable) squirrel.getDrawable();
        anim.start();

        ////request public key and list of purchases
//        loadings++;
        mc.index();
    }

    View.OnClickListener onPurchaseClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            DM.show();
//            loadings++;
            String hz_id = spinnerMap.get(v.getTag().toString());
            int x = 313;

            //BAZZAR API
            if (mHelper != null) {
                try {
                    mHelper.launchPurchaseFlow((Activity) cntx, hz_id, x, mPurchaseFinishedListener, null);
                }
                catch(IllegalStateException ex){
                    Utility.displayToast(StoreActivity.this, "لطفا چند ثانیه دیگه دوباره سعی کنید", Toast.LENGTH_SHORT);
//                    Toast.makeText(StoreActivity.this, "لطفا چند ثانیه دیگه دوباره سعی کنید", Toast.LENGTH_SHORT).show();
                    mHelper.flagEndAsync();

                    DM.hide();
                }
            }


        }
    };

    View.OnClickListener onConvertClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            final View v_temp = v;
            DialogPopUpModel.show(StoreActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No), true, false);
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        while(DialogPopUpModel.isUp()){
                            Thread.sleep(500);
                        }
                        if(!DialogPopUpModel.isUp()){
                            Thread.currentThread().interrupt();



                            if(DialogPopUpModel.dialog_result==1){
                                //yes

                                // loadings++;
                                String hz_id = spinnerMap.get(v_temp.getTag().toString());
                                if(session.getCurrentUser().getHazel()>=  MarketModel.getHazelConverted(hz_id))
                                {
                                    showLoading();
                                    hazel_convert_key = hz_id;
                                    mc.convert(hz_id);
                                }
                                else
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogPopUpModel.show(StoreActivity.this,getString(R.string.msg_NotEnoughHazel),getString(R.string.btn_OK),null,false,true);
                                        }
                                    });
                                }

                            }else{
                                //no
                                //do nothing
                            }

                            DialogPopUpModel.hide();
                        }
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    };
    private void cloudMotion_a(){
        asyncTask=new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                //anim one
                int i1 = new Random().nextInt(5 - 1) + 3;
                ImageView c_cloud_a_scu = (ImageView) findViewById(R.id.c_cloud_a_s);
                Anims.cloudMotion(c_cloud_a_scu,width+30.0f, -1*(width+30.0f),i1*10000);
                //anim one
                int i2 = new Random().nextInt(4 - 1) + 2;
                ImageView c_cloud_b_scu = (ImageView) findViewById(R.id.c_cloud_b_s);
                Anims.cloudMotion(c_cloud_b_scu,width+30.0f, -1*(width+30.0f),i2*10000);
                //anim one
                int i3 = new Random().nextInt(4 - 1) + 2;
                ImageView c_cloud_c_scu = (ImageView) findViewById(R.id.c_cloud_c_s);
                Anims.cloudMotion(c_cloud_c_scu, -1*(width+30.0f),width+30.0f,i3*10000);

                return null;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }
        };
        asyncTask.execute();
    }

    private void arangeGraphic_start() {
        final int p30_height= (int) (height*0.3);

        ImageView c_owl= (ImageView) findViewById(R.id.c_squirrel_s);
        android.view.ViewGroup.LayoutParams layoutParams = c_owl.getLayoutParams();
        layoutParams.height = p30_height;
        c_owl.setLayoutParams(layoutParams);

//        Space shop_down_space= (Space) findViewById(R.id.shop_down_space);
//        android.view.ViewGroup.LayoutParams layoutParams2 = shop_down_space.getLayoutParams();
//        layoutParams2.height = p30_height;
//        shop_down_space.setLayoutParams(layoutParams2);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void update(Observable o, Object arg) {
//        loadings--;
//        if(loadings <= 0)
        DM.hide();
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    Utility.finishActivity(StoreActivity.this);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_MARKET_INDEX))
                {
                    base64EncodedPublicKey = (String) ((ArrayList) arg).get(1);
                    final ArrayList<MarketModel> temp= (ArrayList<MarketModel>) ((ArrayList) arg).get(2);

                    if(temp == null || temp.size()==0){
                        Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                        Utility.finishActivity(StoreActivity.this);
                        return;
                    }

//                    t_a.setText(temp.get(0).getTitlePersian());
//                    t_b.setText(temp.get(1).getTitlePersian());
//                    t_c.setText(temp.get(2).getTitlePersian());
//                    t_d.setText(temp.get(3).getTitlePersian());
//                    t_e.setText(temp.get(4).getTitlePersian());
//                    t_f.setText(temp.get(5).getTitlePersian());


//                    t_g.setText(temp.get(0).getTitlePersian());
//                    t_h.setText(temp.get(1).getTitlePersian());
//
//                    spinnerMap.put("t_g",MarketModel.hz_lc_200);
//                    spinnerMap.put("t_h",MarketModel.hz_lc_2000);

                    enableCartList();
                    initialise(temp);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // compute your public key and store it in base64EncodedPublicKey
                            mHelper = new IabHelper(cntx.getApplicationContext(), base64EncodedPublicKey);
                        }
                    });


//                    DM.show();
//                    loadings++;
                    //check connection to service
                    if(mHelper == null)
                    {
                        Utility.displayToast(getApplicationContext(),getString(R.string.msg_ConnectionError), Toast.LENGTH_SHORT);
                        Utility.finishActivity(StoreActivity.this);
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                                    public void onIabSetupFinished(IabResult result) {
//                            loadings--;
                                        if (!result.isSuccess()) {

//                                if(loadings <= 0)
                                            DM.hide();
                                            // Oh noes, there was a problem.
                                            Utility.displayToast(getApplicationContext(),getString(R.string.msg_ConnectionError), Toast.LENGTH_SHORT);
                                            Utility.finishActivity(StoreActivity.this);
                                        }
                                        // Hooray, IAB is fully set up!
                                        //meysam - read list from bazar
//                                        mHelper.queryInventoryAsync(true, MarketModel.generateHzList(),mGotInventoryListener);
                                        mHelper.queryInventoryAsync(mGotInventoryListener);

                                        //////////////////////////////

                                        //meysam - read list from our own server
//                            enableCartList();
//                            initialise(markets);
//                            MarketModel.generateMarketHashMap_v1(temp, spinnerMap);
                                        ///////////////////////////////////////////////

                                    }
                                });
                            }
                        });

                    }

                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_MARKET_PURCHASE))
                {
                    Utility.displayToast(getApplicationContext(),getString(R.string.msg_PurchaseSuccess), Toast.LENGTH_SHORT);

                    enableCartList();
                    clearStats();


                    int price_at_all=(int)Double.parseDouble( session.getMarket().getAmount());
                    session.increaseHazel(String.valueOf(price_at_all));

                    session.removeMarket();
                }
                else if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_MARKET_CONVERT))
                {
                    Utility.displayToast(getApplicationContext(),getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);

                    enableCartList();

                    session.decreaseHazel(MarketModel.getHazelConverted(hazel_convert_key).toString());
                    session.increaseLuck(MarketModel.getLuckConverted(hazel_convert_key).toString());
                    hazel_convert_key = null;
                    session.removeMarket();
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespondModel.TAG_CHANGE_HAZEL_LUCK_USER))
                {

                    session.removeItem(SessionModel.KEY_FINAL_HAZEL);
                    session.removeItem(SessionModel.KEY_FINAL_LUCK);

                }

            }
            else if(arg instanceof Integer)
            {
                if(Integer.parseInt(arg.toString()) == RequestRespondModel.ERROR_AUTH_FAIL_CODE )
                {
                    Utility.displayToast(getApplicationContext(),getApplicationContext().getString(R.string.error_auth_fail), Toast.LENGTH_SHORT);
//                    SessionModel session = new SessionModel(getApplicationContext());
                    session.logoutUser();

                    Intent intents = new Intent(this, UserLoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    Utility.finishActivity(StoreActivity.this);
                }else {
                    Utility.displayToast(getApplicationContext(),new RequestRespondModel(this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                }
            }
            else
            {
                Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Utility.displayToast(getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    private void unavailableCartList()
    {
        t_a.setText("صبر کن...");
        t_b.setText("صبر کن...");
        t_c.setText("صبر کن...");
        t_d.setText("صبر کن...");
        t_e.setText("صبر کن...");
        t_f.setText("صبر کن...");

    }
    private void disableCartList()
    {
        t_a.setVisibility(View.GONE);
        t_b.setVisibility(View.GONE);
        t_c.setVisibility(View.GONE);
        t_d.setVisibility(View.GONE);
        t_e.setVisibility(View.GONE);
        t_f.setVisibility(View.GONE);

    }
    private void enableCartList()
    {
        t_a.setVisibility(View.VISIBLE);
        t_b.setVisibility(View.VISIBLE);
        t_c.setVisibility(View.VISIBLE);
        t_d.setVisibility(View.VISIBLE);
        t_e.setVisibility(View.VISIBLE);
        t_f.setVisibility(View.VISIBLE);
    }

    //فهرست کالاهای مصرف نشده کاربر
    //اگر درخواست لیست کالاها را بدهیم فعال ها را فقط می دهد
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {


            DM.hide();
            if (result.isFailure()) {
//                Utility.displayToast(getApplicationContext(), getString(R.string.error_bazzar_login_or_network_problem),Toast.LENGTH_SHORT);
//                DialogPopUpModel.show(getApplicationContext(),getString(R.string.error_bazzar_login_or_network_problem),getString(R.string.btn_OK),null,false,true);
//                LogModel log = new LogModel(cntx);
//                log.setErrorMessage("message: "+ getString(R.string.msg_ErrorCodeOneConnectingToMarket)+"متن:"+result.getMessage()+" CallStack: non, خطا در فروشگاه");
//                log.setContollerName(this.getClass().getName());
//                log.insert();
//                finish();
                return;
            }
            else {
                mInventory = inventory;
//                markets = MarketModel.generateMarketList(inventory);
//                MarketModel.generateMarketHashMap(inventory, spinnerMap);
//                fillBazarList(markets);
//                enableCartList();
                //if purchase was not consumed
                if(MarketModel.purchaseExist(inventory))
                {

                    String sku = MarketModel.getMarketTitle(inventory);
                    //check if session exist
                    if(session.hasMarket())
                    {
                        //session for market exist - it is not consumed
                        disableCartList();
                        unavailableCartList();

//                        loadings++;
                        DM.show();
                        mHelper.consumeAsync(mInventory.getPurchase(sku), mConsumeFinishedListener);
                    }
                    else
                    {
                        MarketModel market = new MarketModel();
                        market.setToken(inventory.getPurchase(sku).getToken());
                        market.setMarketId(inventory.getPurchase(sku).getSku());
                        market.setAmount(MarketModel.getAmountByCode(sku).toString());

                        session.saveMarket(market);

//                        pDialog.show();
                        mHelper.consumeAsync(mInventory.getPurchase(sku), mConsumeFinishedListener);
                    }
                }
                else
                {
                    //check if session exist
                    if(session.hasMarket())
                    {
                        //session for market exist
                        disableCartList();
                        unavailableCartList();
//                        loadings++;
                        DM.show();


                        mc.purchase(session.getMarket());

                    }
                }
            }
        }
    };

    //زمانی که یک خرید به پایان می رسد
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
//            loadings--;
//            if(loadings <= 0)
                DM.hide();

            if (result.isFailure()) {
//                Utility.displayToast(getApplicationContext(),getString(R.string.msg_ErrorCodeThreeConnectingToMarket),Toast.LENGTH_SHORT);
                LogModel log = new LogModel(cntx);
                log.setErrorMessage("message: "+ getString(R.string.msg_ErrorCodeThreeConnectingToMarket)+"متن:"+result.getMessage()+" CallStack: non, خطا در فروشگاه");
                log.setContollerName(this.getClass().getName());
                log.insert();
                return;
            }
            else
            {
                MarketModel market = new MarketModel();
                market.setToken(purchase.getToken());
                showDetailsOfMarket(market);
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }



        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase, IabResult result) {
//                    pDialog.dismiss();
                    if (result.isSuccess()) {
                        // provision the in-app purchase to the user
                        // (for example, credit 50 gold coins to player's character)
                        MarketModel market = new MarketModel();
                        market.setToken(purchase.getToken());
                        market.setMarketId(purchase.getSku());
                        market.setAmount(MarketModel.getAmountByCode(purchase.getSku()).toString());

                        if(!session.hasMarket())
                        {
                            session.saveMarket(market);
                        }

                        mc.purchase(market);
                    }
                    else {
                        // handle error

                        Utility.displayToast(cntx,getString(R.string.msg_ErrorCodeTwoConnectingToMarket),Toast.LENGTH_SHORT);
                        LogModel log = new LogModel(cntx);
                        log.setErrorMessage("message: "+ getString(R.string.msg_ErrorCodeTwoConnectingToMarket)+"متن:"+result.getMessage()+" CallStack: non, خطا در فروشگاه");
                        log.setContollerName(this.getClass().getName());
                        log.insert();
                        finish();
                    }
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        pDialog.dismiss();
//        Utility.displayToast(getApplicationContext(),"onActivityResult(" + requestCode + "," + resultCode + "," + data,Toast.LENGTH_SHORT);
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
//            Utility.displayToast(getApplicationContext(),"onActivityResult handled by IABUtil.",Toast.LENGTH_SHORT);
        }
    }
    private void clearStats()
    {
        mInventory = null;
    }


    private void fillBazarList(ArrayList<MarketModel> list) {


            t_a.setText(MarketModel.populateViewList("t_a",list));
            t_b.setText(MarketModel.populateViewList("t_b",list));
            t_c.setText(MarketModel.populateViewList("t_c",list));
            t_d.setText(MarketModel.populateViewList("t_d",list));
            t_e.setText(MarketModel.populateViewList("t_e",list));
            t_f.setText(MarketModel.populateViewList("t_f",list));



    }

    private void showDetailsOfMarket(MarketModel market) {
        final Dialog d2=new Dialog(cntx);
        d2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d2.setContentView(R.layout.message_dialog);
        TextView txt= (TextView) d2.findViewById(R.id.message_box_dialog);
        txt.setText(cntx.getString(R.string.lbl_followeup)+" "+ market.getToken());
        Button btn= (Button) d2.findViewById(R.id.btn_mess_01);
        btn.setText(cntx.getString(R.string.btn_OK));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.hide();
            }
        });
        d2.show();
    }

    private void showLoading()
    {
        Intent intent = new Intent("market_activity_broadcast");
        // You can also include some extra data.
        intent.putExtra("loading", "true");
        LocalBroadcastManager.getInstance(StoreActivity.this).sendBroadcast(intent);
    }


    public void onDestroy() {
        if(DM != null)
        {
            DM.dismiss();
            DM = null;
        }

        if(mHelper != null)
            mHelper.dispose();
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysamBroadcastReceiver);
        // Unregister since the activity is about to be closed.
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMeysam‌BaseBroadcastReceiver);
        super.onDestroy();

    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String isLoading = intent.getStringExtra("loading");
            if(isLoading.equals("true"))
            {
                DM.show();
            }

        }
    };

    private void initialise(ArrayList<MarketModel> markets)
    {
        t_a.setText(Utility.enToFa(markets.get(2).getTitlePersian() +System.getProperty("line.separator")+ markets.get(2).getCost()  +" "+ "تومان"));
        t_b.setText(Utility.enToFa(markets.get(3).getTitlePersian()+System.getProperty("line.separator")+ markets.get(3).getCost() +" "+ "تومان"));
        t_c.setText(Utility.enToFa(markets.get(4).getTitlePersian()+System.getProperty("line.separator")+ markets.get(4).getCost() +" "+ "تومان"));
        t_d.setText(Utility.enToFa(markets.get(5).getTitlePersian()+System.getProperty("line.separator")+ markets.get(5).getCost() +" "+ "تومان"));
        t_e.setText(Utility.enToFa(markets.get(6).getTitlePersian()+System.getProperty("line.separator")+ markets.get(6).getCost() +" "+ "تومان"));
        t_f.setText(Utility.enToFa(markets.get(7).getTitlePersian()+System.getProperty("line.separator")+ markets.get(7).getCost() +" "+ "تومان"));
        t_g.setText(Utility.enToFa(markets.get(0).getTitlePersian()));
        t_h.setText(Utility.enToFa(markets.get(1).getTitlePersian()));

        spinnerMap.put("t_a",markets.get(2).getMarketId());
        spinnerMap.put("t_b",markets.get(3).getMarketId());
        spinnerMap.put("t_c",markets.get(4).getMarketId());
        spinnerMap.put("t_d",markets.get(5).getMarketId());
        spinnerMap.put("t_e",markets.get(6).getMarketId());
        spinnerMap.put("t_f",markets.get(7).getMarketId());
        spinnerMap.put("t_g",markets.get(0).getMarketId());
        spinnerMap.put("t_h",markets.get(1).getMarketId());

    }

    @Override
    public void onPause() {
        if(mc != null)
        {
            mc.setCntx(null);
            mc.deleteObservers();
            mc = null;
        }

        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!inCreating)
        {


            mc = new MarketController(getApplicationContext());
            mc.addObserver(this);

            inCreating = false;


        }


    }


}
