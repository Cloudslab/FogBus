package android.support.v7.internal.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;

public class TintContextWrapper extends ContextWrapper {
    private Resources mResources;

    static class TintResources extends ResourcesWrapper {
        private final TintManager mTintManager;

        public TintResources(Resources resources, TintManager tintManager) {
            super(resources);
            this.mTintManager = tintManager;
        }

        public Drawable getDrawable(int id) throws NotFoundException {
            Drawable d = super.getDrawable(id);
            if (d != null) {
                this.mTintManager.tintDrawableUsingColorFilter(id, d);
            }
            return d;
        }
    }

    public static Context wrap(Context context) {
        if (context instanceof TintContextWrapper) {
            return context;
        }
        return new TintContextWrapper(context);
    }

    private TintContextWrapper(Context base) {
        super(base);
    }

    public Resources getResources() {
        if (this.mResources == null) {
            this.mResources = new TintResources(super.getResources(), TintManager.get(this));
        }
        return this.mResources;
    }
}
