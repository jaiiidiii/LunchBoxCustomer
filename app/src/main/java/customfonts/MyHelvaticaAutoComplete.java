package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyHelvaticaAutoComplete extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public MyHelvaticaAutoComplete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyHelvaticaAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyHelvaticaAutoComplete(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "myfonts/Roboto-Medium.ttf");
            setTypeface(tf);
        }
    }

}