package com.google.appinventor.components.runtime;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.google.appinventor.components.annotations.SimpleObject;
import gnu.expr.Declaration;

@SimpleObject
public final class LinearLayout implements Layout {
    private final android.widget.LinearLayout layoutManager;

    LinearLayout(Context context, int orientation) {
        this(context, orientation, null, null);
    }

    LinearLayout(Context context, int orientation, final Integer preferredEmptyWidth, final Integer preferredEmptyHeight) {
        if ((preferredEmptyWidth != null || preferredEmptyHeight == null) && (preferredEmptyWidth == null || preferredEmptyHeight != null)) {
            this.layoutManager = new android.widget.LinearLayout(context) {
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    if (preferredEmptyWidth == null || preferredEmptyHeight == null) {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    } else if (getChildCount() != 0) {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    } else {
                        setMeasuredDimension(getSize(widthMeasureSpec, preferredEmptyWidth.intValue()), getSize(heightMeasureSpec, preferredEmptyHeight.intValue()));
                    }
                }

                private int getSize(int measureSpec, int preferredSize) {
                    int specMode = MeasureSpec.getMode(measureSpec);
                    int specSize = MeasureSpec.getSize(measureSpec);
                    if (specMode == Declaration.MODULE_REFERENCE) {
                        return specSize;
                    }
                    int result = preferredSize;
                    if (specMode == Integer.MIN_VALUE) {
                        return Math.min(result, specSize);
                    }
                    return result;
                }
            };
            this.layoutManager.setOrientation(orientation == 0 ? 0 : 1);
            return;
        }
        throw new IllegalArgumentException("LinearLayout - preferredEmptyWidth and preferredEmptyHeight must be either both null or both not null");
    }

    public ViewGroup getLayoutManager() {
        return this.layoutManager;
    }

    public void add(AndroidViewComponent component) {
        this.layoutManager.addView(component.getView(), new LayoutParams(-2, -2, 0.0f));
    }

    public void setHorizontalGravity(int gravity) {
        this.layoutManager.setHorizontalGravity(gravity);
    }

    public void setVerticalGravity(int gravity) {
        this.layoutManager.setVerticalGravity(gravity);
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.layoutManager.setBaselineAligned(baselineAligned);
    }
}
