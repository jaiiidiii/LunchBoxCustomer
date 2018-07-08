package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Regular_CheckBox extends android.support.v7.widget.AppCompatCheckBox {
    public Regular_CheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Regular_CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Regular_CheckBox(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "myfonts/Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }

}