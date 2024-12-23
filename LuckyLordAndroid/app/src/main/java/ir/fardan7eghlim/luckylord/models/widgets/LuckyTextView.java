package ir.fardan7eghlim.luckylord.models.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
/**
 * Created by Amir on 5/23/2017.
 */

public class LuckyTextView extends android.support.v7.widget.AppCompatTextView {
    public LuckyTextView(Context context) {
        super(context);
        init();
    }

    public LuckyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LuckyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Khandevane.ttf");
            setTypeface(tf);
//            int mInputType = this.getInputType();
//            setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//            setInputType(mInputType | InputType.TYPE_TE);
//            setInputType(mInputType | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        }
    }
}
