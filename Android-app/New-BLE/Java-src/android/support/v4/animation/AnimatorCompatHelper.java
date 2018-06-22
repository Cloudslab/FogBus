package android.support.v4.animation;

import android.os.Build.VERSION;

public abstract class AnimatorCompatHelper {
    static AnimatorProvider IMPL;

    static {
        if (VERSION.SDK_INT >= 12) {
            IMPL = new HoneycombMr1AnimatorCompatProvider();
        } else {
            IMPL = new DonutAnimatorCompatProvider();
        }
    }

    public static ValueAnimatorCompat emptyValueAnimator() {
        return IMPL.emptyValueAnimator();
    }

    AnimatorCompatHelper() {
    }
}
