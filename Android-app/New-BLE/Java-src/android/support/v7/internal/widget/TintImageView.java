package android.support.v7.internal.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TintImageView extends ImageView {
    private static final int[] TINT_ATTRS = new int[]{16842964, 16843033};
    private final TintManager mTintManager;

    public TintImageView(Context context) {
        this(context, null);
    }

    public TintImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, TINT_ATTRS, defStyleAttr, 0);
        if (a.length() > 0) {
            if (a.hasValue(0)) {
                setBackgroundDrawable(a.getDrawable(0));
            }
            if (a.hasValue(1)) {
                setImageDrawable(a.getDrawable(1));
            }
        }
        a.recycle();
        this.mTintManager = a.getTintManager();
    }

    public void setImageResource(@DrawableRes int resId) {
        setImageDrawable(this.mTintManager.getDrawable(resId));
    }
}
