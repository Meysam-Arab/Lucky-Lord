package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.fardan7eghlim.luckylord.interfaces.MarketInterface;
import ir.fardan7eghlim.luckylord.utils.billing.Inventory;

/**
 * Created by Meysam on 12/20/2016.
 */

public class MarketModel implements MarketInterface {



    public static String CAFEBAZAR = "cafe_bazar";

    public static final String hz_1500 = "HAZEL_1500";
    public static final String hz_3300 = "HAZEL_3300";
    public static final String hz_8700 = "HAZEL_8700";
    public static final String hz_18000 = "HAZEL_18000";
    public static final String hz_42000 = "HAZEL_42000";
    public static final String hz_75000 = "HAZEL_75000";

    public static final String hz_lc_200 = "hz_lc_200";
    public static final String hz_lc_2000 = "hz_lc_2000";


    public String getMarketId() {
        return MarketId;
    }

    public void setMarketId(String marketId) {
        MarketId = marketId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDescriptionPersian() {
        return DescriptionPersian;
    }

    public void setDescriptionPersian(String descriptionPersian) {
        DescriptionPersian = descriptionPersian;
    }

    public String getDescriptionEnglish() {
        return DescriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        DescriptionEnglish = descriptionEnglish;
    }

    public String getTitlePersian() {
        return TitlePersian;
    }

    public void setTitlePersian(String titlePersian) {
        TitlePersian = titlePersian;
    }

    public String getTitleEnglish() {
        return TitleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        TitleEnglish = titleEnglish;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPayload() {
        return Payload;
    }

    public void setPayload(String payload) {
        Payload = payload;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    private String MarketId;
    private String  Amount;
    private String Cost;
    private String DescriptionPersian;
    private String DescriptionEnglish;
    private String TitlePersian;
    private String TitleEnglish;
    private String Token;
    private String Payload;


//    private Context cntx;


    public MarketModel()
    {
        MarketId = null;
        Amount = null;
        DescriptionEnglish = null;
        DescriptionPersian = null;
        TitleEnglish = null;
        TitlePersian = null;
        Cost = null;

//        this.cntx = null;
    }

//    public MarketModel(Context cntx)
//    {
//        MarketId = null;
//        Amount = null;
//        DescriptionEnglish = null;
//        DescriptionPersian = null;
//        TitleEnglish = null;
//        TitlePersian = null;
//        this.cntx = cntx;
//        Cost = null;
//
//    }

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

    public static ArrayList<String> generateHzList()
    {
        ArrayList<String> hz_list = new ArrayList<>();
        hz_list.add(hz_1500);
        hz_list.add(hz_3300);
        hz_list.add(hz_8700);
        hz_list.add(hz_18000);
        hz_list.add(hz_42000);
        hz_list.add(hz_75000);

        return hz_list;
    }

    public static ArrayList<MarketModel> generateMarketList(Inventory inv)
    {
        ArrayList<MarketModel> mr_list = new ArrayList<>();
        MarketModel mr_tmp;
        if(inv.getSkuDetails(MarketModel.hz_1500) != null)
        {
            mr_tmp = new MarketModel();
            mr_tmp.setMarketId(MarketModel.hz_1500);
            mr_tmp.setAmount(inv.getSkuDetails(MarketModel.hz_1500).getPrice());
            mr_tmp.setTitlePersian(inv.getSkuDetails(MarketModel.hz_1500).getTitle());
            mr_list.add(mr_tmp);

        }
        if(inv.getSkuDetails(MarketModel.hz_3300) != null)
        {
            mr_tmp = new MarketModel();
            mr_tmp.setMarketId(MarketModel.hz_3300);
            mr_tmp.setAmount(inv.getSkuDetails(MarketModel.hz_3300).getPrice());
            mr_tmp.setTitlePersian(inv.getSkuDetails(MarketModel.hz_3300).getTitle());
            mr_list.add(mr_tmp);

        }
        if(inv.getSkuDetails(MarketModel.hz_8700) != null)
        {
            mr_tmp = new MarketModel();
            mr_tmp.setMarketId(MarketModel.hz_8700);
            mr_tmp.setAmount(inv.getSkuDetails(MarketModel.hz_8700).getPrice());
            mr_tmp.setTitlePersian(inv.getSkuDetails(MarketModel.hz_8700).getTitle());
            mr_list.add(mr_tmp);
        }
        if(inv.getSkuDetails(MarketModel.hz_18000) != null)
        {
            mr_tmp = new MarketModel();
            mr_tmp.setMarketId(MarketModel.hz_18000);
            mr_tmp.setAmount(inv.getSkuDetails(MarketModel.hz_18000).getPrice());
            mr_tmp.setTitlePersian(inv.getSkuDetails(MarketModel.hz_18000).getTitle());
            mr_list.add(mr_tmp);
        }
        if(inv.getSkuDetails(MarketModel.hz_42000) != null)
        {
            mr_tmp = new MarketModel();
            mr_tmp.setMarketId(MarketModel.hz_42000);
            mr_tmp.setAmount(inv.getSkuDetails(MarketModel.hz_42000).getPrice());
            mr_tmp.setTitlePersian(inv.getSkuDetails(MarketModel.hz_42000).getTitle());
            mr_list.add(mr_tmp);
        }
        if(inv.getSkuDetails(MarketModel.hz_75000) != null)
        {
            mr_tmp = new MarketModel();
            mr_tmp.setMarketId(MarketModel.hz_75000);
            mr_tmp.setAmount(inv.getSkuDetails(MarketModel.hz_75000).getPrice());
            mr_tmp.setTitlePersian(inv.getSkuDetails(MarketModel.hz_75000).getTitle());
            mr_list.add(mr_tmp);
        }

        return mr_list;
    }

    public static HashMap<String,String> generateMarketHashMap(Inventory inv,HashMap<String, String> spinnerMap)
    {

        if(inv.getSkuDetails(MarketModel.hz_1500) != null)
        {
            spinnerMap.put("t_a",MarketModel.hz_1500);

        }
        if(inv.getSkuDetails(MarketModel.hz_3300) != null)
        {
            spinnerMap.put("t_b",MarketModel.hz_3300);

        }
        if(inv.getSkuDetails(MarketModel.hz_8700) != null)
        {
            spinnerMap.put("t_c",MarketModel.hz_8700);
        }
        if(inv.getSkuDetails(MarketModel.hz_18000) != null)
        {
            spinnerMap.put("t_d",MarketModel.hz_18000);
        }
        if(inv.getSkuDetails(MarketModel.hz_42000) != null)
        {
            spinnerMap.put("t_e",MarketModel.hz_42000);
        }
        if(inv.getSkuDetails(MarketModel.hz_75000) != null)
        {
            spinnerMap.put("t_f",MarketModel.hz_75000);
        }

        return spinnerMap;
    }
    public static HashMap<String,String> generateMarketHashMap_v1(ArrayList<MarketModel> inv,HashMap<String, String> spinnerMap)
    {

        for(int i = 0; i < inv.size();i++)
        {
            if(inv.get(i).getMarketId().equals(MarketModel.hz_1500))
            {
                spinnerMap.put("t_a",MarketModel.hz_1500);

            }
            if(inv.get(i).getMarketId().equals(MarketModel.hz_3300))
            {
                spinnerMap.put("t_b",MarketModel.hz_3300);

            }
            if(inv.get(i).getMarketId().equals(MarketModel.hz_8700))
            {
                spinnerMap.put("t_c",MarketModel.hz_8700);
            }
            if(inv.get(i).getMarketId().equals(MarketModel.hz_18000))
            {
                spinnerMap.put("t_d",MarketModel.hz_18000);
            }
            if(inv.get(i).getMarketId().equals(MarketModel.hz_42000))
            {
                spinnerMap.put("t_e",MarketModel.hz_42000);
            }
            if(inv.get(i).getMarketId().equals(MarketModel.hz_75000))
            {
                spinnerMap.put("t_f",MarketModel.hz_75000);
            }
        }


        return spinnerMap;
    }
    public static ArrayList<String> generateMarketTitleList(ArrayList<MarketModel> markets)
    {
        ArrayList<String> results = new ArrayList<>();
        for(int i=0; i < markets.size(); i++)
        {
            results.add(markets.get(i).getTitlePersian());
        }
        return results;
    }

    public static Boolean purchaseExist(Inventory inv)
    {
        if(inv.hasPurchase(hz_1500))
            return true;
        if(inv.hasPurchase(hz_3300))
            return true;
        if(inv.hasPurchase(hz_8700))
            return true;
        if(inv.hasPurchase(hz_18000))
            return true;
        if(inv.hasPurchase(hz_42000))
            return true;
        if(inv.hasPurchase(hz_75000))
            return true;
        return false;

    }

    public static String getMarketTitle(Inventory inv) {
        if(inv.hasPurchase(hz_1500))
            return hz_1500;
        if(inv.hasPurchase(hz_3300))
            return hz_3300;
        if(inv.hasPurchase(hz_8700))
            return hz_8700;
        if(inv.hasPurchase(hz_18000))
            return hz_18000;
        if(inv.hasPurchase(hz_42000))
            return hz_42000;
        if(inv.hasPurchase(hz_75000))
            return hz_75000;

        return "0";
    }

    public static Double getAmountByCode(String code)
    {
        if(code.equals(hz_1500))
            return 1000.0;
        if(code.equals(hz_3300))
            return 2000.0;
        if(code.equals(hz_8700))
            return 5000.0;
        if(code.equals(hz_18000))
            return 10000.0;
        if(code.equals(hz_42000))
            return 20000.0;
        if(code.equals(hz_75000))
            return 30000.0;

        return 0.0;
    }

    public static String getTextByView(String ViewId, MarketModel market)
    {
        switch(ViewId) {
            case "t_a":
                if(market.getMarketId().equals(MarketModel.hz_1500))
                    return market.getTitlePersian()+ "\n" + market.getAmount();
                else
                    return "نا موجود";
            case "t_b":
                if(market.getMarketId().equals(MarketModel.hz_3300))
                    return market.getTitlePersian()+ "\n" + market.getAmount();
                  else
                return "نا موجود";
            case "t_c":
                if(market.getMarketId().equals(MarketModel.hz_8700))
                    return market.getTitlePersian()+ "\n" + market.getAmount();
                else
                    return "نا موجود";
            case "t_d":
                if(market.getMarketId().equals(MarketModel.hz_18000))
                    return market.getTitlePersian()+ "\n" + market.getAmount();
                else
                    return "نا موجود";
            case "t_e":
                if(market.getMarketId().equals(MarketModel.hz_42000))
                    return market.getTitlePersian()+ "\n" + market.getAmount();
                else
                    return "نا موجود";
            case "t_f":
                if(market.getMarketId().equals(MarketModel.hz_75000))
                    return market.getTitlePersian()+ "\n" + market.getAmount();
                else
                    return "نا موجود";
            default:
                return "نا موجود";
        }
    }

    public static String populateViewList(String ViewId, ArrayList<MarketModel> markets)
    {
        String result = "نا موجود";

        for(int i=0; i<markets.size();i++)
        {
            if(ViewId.equals("t_a"))
            {
                if(markets.get(i).getMarketId().equals(MarketModel.hz_1500))
                    result = markets.get(i).getTitlePersian()+ "\n" + markets.get(i).getAmount();
//                else
//                    result = "نا موجود";
            }
            if(ViewId.equals("t_b")) {
                if(markets.get(i).getMarketId().equals(MarketModel.hz_3300))
                    result = markets.get(i).getTitlePersian()+ "\n" + markets.get(i).getAmount();
//                else
//                    result = "نا موجود";
            }
            if(ViewId.equals("t_c")) {
                if(markets.get(i).getMarketId().equals(MarketModel.hz_8700))
                    result = markets.get(i).getTitlePersian()+ "\n" + markets.get(i).getAmount();
//                else
//                    result = "نا موجود";
            }
            if(ViewId.equals("t_d")) {
                if(markets.get(i).getMarketId().equals(MarketModel.hz_18000))
                    result = markets.get(i).getTitlePersian()+ "\n" + markets.get(i).getAmount();
//                else
//                    result = "نا موجود";
            }
            if(ViewId.equals("t_e")) {
                if(markets.get(i).getMarketId().equals(MarketModel.hz_42000))
                    result = markets.get(i).getTitlePersian()+ "\n" + markets.get(i).getAmount();
//                else
//                    result = "نا موجود";
            }
            if(ViewId.equals("t_f")) {
                if(markets.get(i).getMarketId().equals(MarketModel.hz_75000))
                    result = markets.get(i).getTitlePersian()+ "\n" + markets.get(i).getAmount();
//                else
//                    result = "نا موجود";
            }
        }
        return result;
    }
    public static Integer getLuckConverted(String converterKey)
    {
        switch(converterKey) {
            case MarketModel.hz_lc_200:

                    return 1;

            case MarketModel.hz_lc_2000:

                    return 12;
            default:
                return 0;
        }
    }

    public static Integer getHazelConverted(String converterKey)
    {
        switch(converterKey) {
            case MarketModel.hz_lc_200:

                return 200;

            case MarketModel.hz_lc_2000:

                return 2000;
            default:
                return 0;
        }
    }

}