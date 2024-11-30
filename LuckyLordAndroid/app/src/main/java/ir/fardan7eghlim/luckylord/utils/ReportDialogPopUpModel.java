

/**
 * Created by Meysam on 02/08/2017.
 */


package ir.fardan7eghlim.luckylord.utils;

        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.graphics.drawable.ColorDrawable;
        import android.view.View;
        import android.view.Window;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.TextView;

        import java.util.HashMap;

        import ir.fardan7eghlim.luckylord.R;
        import ir.fardan7eghlim.luckylord.models.QuestionModel;
        import ir.fardan7eghlim.luckylord.models.widgets.LuckyEditText;


public class ReportDialogPopUpModel {
    public static Dialog dialog;
    public static int dialog_result;
    public static int code;
    public static String description;

    public static int DARHAM_TABLE_TYPE = -1;
    public static int GUESS_WORD_TYPE = -2;
    public static int FIND_WORD_TYPE = -3;
    public static int CROSS_TABLE_TYPE = -4;

    private static Spinner spReportType;
    private static LuckyEditText lteReportDescription;

    private static HashMap<Integer,String> spinnerMap;

    public static void show(Context context, String title, String descriptionHint, final Boolean showSpinner){
        // meysam - report for new word in find word activity
        dialog_result=0;

        code=-1;
        description = "";

        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.report_dialog);

        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //When you touch outside of dialog bounds,
                        //the dialog gets canceled and this method executes.

                        hide();
                    }
                }
        );

        if(showSpinner)
        {
            fillSpinner(context);
            spReportType.setVisibility(View.VISIBLE);
        }
        else
        {
            spReportType= (Spinner) dialog.findViewById(R.id.sp_report_type);
            spReportType.setVisibility(View.GONE);
        }


        TextView txt= (TextView) dialog.findViewById(R.id.report_box_dialog);
        txt.setText(title);
        Button btn_ok= (Button) dialog.findViewById(R.id.btn_dialog_report_ok);

        lteReportDescription = (LuckyEditText) dialog.findViewById(R.id.let_description_report_dialog);

        if(descriptionHint != null)
            lteReportDescription.setHint(descriptionHint);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_result=1;
                if(showSpinner)
                    code=Integer.valueOf(spReportType.getSelectedItemPosition());
                description = lteReportDescription.getText().toString();
                dialog.dismiss();

            }
        });
        Button btn_cancel= (Button) dialog.findViewById(R.id.btn_dialog_report_cancel);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog_result=2;
            }
        });

        dialog.show();

    }


    public static void show(Context context){
        dialog_result=0;

        code=-1;
        description = "";

        dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.report_dialog);

        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //When you touch outside of dialog bounds,
                        //the dialog gets canceled and this method executes.

                        hide();
                    }
                }
        );

        fillSpinner(context);

        TextView txt= (TextView) dialog.findViewById(R.id.report_box_dialog);
        txt.setText("گزارش مشکل");
        Button btn_ok= (Button) dialog.findViewById(R.id.btn_dialog_report_ok);

        lteReportDescription = (LuckyEditText) dialog.findViewById(R.id.let_description_report_dialog);

//        btn_ok.setText(context.getString(R.string.lbl_send));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_result=1;
                code=Integer.valueOf(spReportType.getSelectedItemPosition());
                description = lteReportDescription.getText().toString();
                dialog.dismiss();

            }
        });
//        if( context.getString(R.string.lbl_exit)!=null){
            Button btn_cancel= (Button) dialog.findViewById(R.id.btn_dialog_report_cancel);
//            btn_cancel.setText( context.getString(R.string.lbl_exit));
            btn_cancel.setVisibility(View.VISIBLE);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    dialog_result=2;
                }
            });
//        }

        dialog.show();
    }
    public static void hide(){
        if(dialog != null)
            dialog.dismiss();
        dialog=null;
        clearResources();
    }
    public static boolean isUp(){
        if(dialog == null)
            return false;
        return dialog.isShowing();
    }

    private static void fillSpinner(Context cntx)
    {

        spinnerMap = QuestionModel.generateReportHashMap(cntx);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(cntx, R.layout.spinner_01,QuestionModel.generateReportTitleList(cntx));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReportType= (Spinner) dialog.findViewById(R.id.sp_report_type);
        spReportType.setAdapter(dataAdapter);

    }

    public static void clearResources()
    {
        spReportType = null;
        lteReportDescription = null;
        spinnerMap = null;
    }
}

