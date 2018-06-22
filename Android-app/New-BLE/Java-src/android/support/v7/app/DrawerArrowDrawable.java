package android.support.v7.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.C0111R;

abstract class DrawerArrowDrawable extends Drawable {
    private static final float ARROW_HEAD_ANGLE = ((float) Math.toRadians(45.0d));
    private final float mBarGap;
    private final float mBarSize;
    private final float mBarThickness;
    private float mCenterOffset;
    private float mMaxCutForBarSize;
    private final float mMiddleArrowSize;
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    private float mProgress;
    private final int mSize;
    private final boolean mSpin;
    private final float mTopBottomArrowSize;
    private boolean mVerticalMirror = false;

    abstract boolean isLayoutRtl();

    DrawerArrowDrawable(Context context) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(null, C0111R.styleable.DrawerArrowToggle, C0111R.attr.drawerArrowStyle, C0111R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(typedArray.getColor(C0111R.styleable.DrawerArrowToggle_color, 0));
        this.mSize = typedArray.getDimensionPixelSize(C0111R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.mBarSize = (float) Math.round(typedArray.getDimension(C0111R.styleable.DrawerArrowToggle_barSize, 0.0f));
        this.mTopBottomArrowSize = (float) Math.round(typedArray.getDimension(C0111R.styleable.DrawerArrowToggle_topBottomBarArrowSize, 0.0f));
        this.mBarThickness = typedArray.getDimension(C0111R.styleable.DrawerArrowToggle_thickness, 0.0f);
        this.mBarGap = (float) Math.round(typedArray.getDimension(C0111R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f));
        this.mSpin = typedArray.getBoolean(C0111R.styleable.DrawerArrowToggle_spinBars, true);
        this.mMiddleArrowSize = typedArray.getDimension(C0111R.styleable.DrawerArrowToggle_middleBarArrowSize, 0.0f);
        this.mCenterOffset = (float) ((((int) ((((float) this.mSize) - (this.mBarThickness * 3.0f)) - (this.mBarGap * 2.0f))) / 4) * 2);
        this.mCenterOffset = (float) (((double) this.mCenterOffset) + ((((double) this.mBarThickness) * 1.5d) + ((double) this.mBarGap)));
        typedArray.recycle();
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.MITER);
        this.mPaint.setStrokeCap(Cap.BUTT);
        this.mPaint.setStrokeWidth(this.mBarThickness);
        this.mMaxCutForBarSize = (float) (((double) (this.mBarThickness / 2.0f)) * Math.cos((double) ARROW_HEAD_ANGLE));
    }

    protected void setVerticalMirror(boolean verticalMirror) {
        this.mVerticalMirror = verticalMirror;
    }

    public void draw(Canvas canvas) {
        float f;
        float f2;
        Rect bounds = getBounds();
        boolean isRtl = isLayoutRtl();
        float arrowSize = lerp(this.mBarSize, this.mTopBottomArrowSize, this.mProgress);
        float middleBarSize = lerp(this.mBarSize, this.mMiddleArrowSize, this.mProgress);
        float middleBarCut = (float) Math.round(lerp(0.0f, this.mMaxCutForBarSize, this.mProgress));
        float rotation = lerp(0.0f, ARROW_HEAD_ANGLE, this.mProgress);
        if (isRtl) {
            f = 0.0f;
        } else {
            f = -180.0f;
        }
        if (isRtl) {
            f2 = 180.0f;
        } else {
            f2 = 0.0f;
        }
        float canvasRotate = lerp(f, f2, this.mProgress);
        float arrowWidth = (float) Math.round(((double) arrowSize) * Math.cos((double) rotation));
        float arrowHeight = (float) Math.round(((double) arrowSize) * Math.sin((double) rotation));
        this.mPath.rewind();
        float topBottomBarOffset = lerp(this.mBarGap + this.mBarThickness, -this.mMaxCutForBarSize, this.mProgress);
        float arrowEdge = (-middleBarSize) / 2.0f;
        this.mPath.moveTo(arrowEdge + middleBarCut, 0.0f);
        this.mPath.rLineTo(middleBarSize - (2.0f * middleBarCut), 0.0f);
        this.mPath.moveTo(arrowEdge, topBottomBarOffset);
        this.mPath.rLineTo(arrowWidth, arrowHeight);
        this.mPath.moveTo(arrowEdge, -topBottomBarOffset);
        this.mPath.rLineTo(arrowWidth, -arrowHeight);
        this.mPath.close();
        canvas.save();
        canvas.translate((float) bounds.centerX(), this.mCenterOffset);
        if (this.mSpin) {
            canvas.rotate(((float) ((this.mVerticalMirror ^ isRtl) != 0 ? -1 : 1)) * canvasRotate);
        } else if (isRtl) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public boolean isAutoMirrored() {
        return true;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public int getIntrinsicHeight() {
        return this.mSize;
    }

    public int getIntrinsicWidth() {
        return this.mSize;
    }

    public int getOpacity() {
        return -3;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidateSelf();
    }

    private static float lerp(float a, float b, float t) {
        return ((b - a) * t) + a;
    }
}
