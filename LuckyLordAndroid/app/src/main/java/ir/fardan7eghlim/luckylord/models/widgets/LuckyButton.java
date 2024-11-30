package ir.fardan7eghlim.luckylord.models.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Amir on 5/23/2017.
 */

public class LuckyButton extends android.support.v7.widget.AppCompatButton {
    public LuckyButton(Context context) {
        super(context);
        init();
    }

    public LuckyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LuckyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Khandevane.ttf");
            setTypeface(tf);
        }
    }
}
