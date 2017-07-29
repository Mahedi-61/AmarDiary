package bd.org.fulkuri.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewWithBanglaFont extends TextView
{

    private Context c;

    public TextViewWithBanglaFont(Context context)
    {
        super(context);
        c = context;
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Siyamrupali.ttf"));
    }

    public TextViewWithBanglaFont(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        c = context;
        setTypeface(Typeface.createFromAsset(c.getAssets(), "fonts/Siyamrupali.ttf"));
    }

    public TextViewWithBanglaFont(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        c = context;
        setTypeface(Typeface.createFromAsset(c.getAssets(), "fonts/Siyamrupali.ttf"));
    }
}
