package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;

class DrawableWrapperDonut extends Drawable implements Callback, DrawableWrapper {
    static final Mode DEFAULT_MODE = Mode.SRC_IN;
    private boolean mColorFilterSet;
    private int mCurrentColor;
    private Mode mCurrentMode;
    Drawable mDrawable;
    private ColorStateList mTintList;
    private Mode mTintMode = DEFAULT_MODE;

    DrawableWrapperDonut(Drawable drawable) {
        setWrappedDrawable(drawable);
    }

    public void draw(Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    protected void onBoundsChange(Rect bounds) {
        this.mDrawable.setBounds(bounds);
    }

    public void setChangingConfigurations(int configs) {
        this.mDrawable.setChangingConfigurations(configs);
    }

    public int getChangingConfigurations() {
        return this.mDrawable.getChangingConfigurations();
    }

    public void setDither(boolean dither) {
        this.mDrawable.setDither(dither);
    }

    public void setFilterBitmap(boolean filter) {
        this.mDrawable.setFilterBitmap(filter);
    }

    public void setAlpha(int alpha) {
        this.mDrawable.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mDrawable.setColorFilter(cf);
    }

    public boolean isStateful() {
        return (this.mTintList != null && this.mTintList.isStateful()) || this.mDrawable.isStateful();
    }

    public boolean setState(int[] stateSet) {
        return updateTint(stateSet) || this.mDrawable.setState(stateSet);
    }

    public int[] getState() {
        return this.mDrawable.getState();
    }

    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisible(visible, restart) || this.mDrawable.setVisible(visible, restart);
    }

    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public boolean getPadding(Rect padding) {
        return this.mDrawable.getPadding(padding);
    }

    public Drawable mutate() {
        Drawable wrapped = this.mDrawable;
        Drawable mutated = wrapped.mutate();
        if (mutated != wrapped) {
            setWrappedDrawable(mutated);
        }
        return this;
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    protected boolean onLevelChange(int level) {
        return this.mDrawable.setLevel(level);
    }

    public void setTint(int tint) {
        setTintList(ColorStateList.valueOf(tint));
    }

    public void setTintList(ColorStateList tint) {
        this.mTintList = tint;
        updateTint(getState());
    }

    public void setTintMode(Mode tintMode) {
        this.mTintMode = tintMode;
        updateTint(getState());
    }

    private boolean updateTint(int[] state) {
        if (!(this.mTintList == null || this.mTintMode == null)) {
            int color = this.mTintList.getColorForState(state, this.mTintList.getDefaultColor());
            Mode mode = this.mTintMode;
            if (!(this.mColorFilterSet && color == this.mCurrentColor && mode == this.mCurrentMode)) {
                setColorFilter(color, mode);
                this.mCurrentColor = color;
                this.mCurrentMode = mode;
                this.mColorFilterSet = true;
                return true;
            }
        }
        return false;
    }

    public Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void setWrappedDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        invalidateSelf();
    }
}
