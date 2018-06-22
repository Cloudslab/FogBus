package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.v7.internal.widget.TintImageView;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.Queue;

public final class ImageViewUtil {
    private static final boolean canUpdate;

    static {
        boolean updateable = false;
        try {
            TintImageView.class.getMethod("setImageTintMode", new Class[]{Mode.class});
            updateable = true;
        } catch (NoSuchMethodException e) {
        }
        canUpdate = updateable;
    }

    private ImageViewUtil() {
    }

    public static void setMenuButtonColor(Activity activity, int color) {
        if (canUpdate) {
            TintImageView overflowMenuView = findOverflowMenuView(activity);
            if (overflowMenuView != null) {
                ColorStateList stateList = new ColorStateList(new int[][]{new int[0]}, new int[]{color});
                try {
                    overflowMenuView.setImageTintMode(Mode.MULTIPLY);
                    overflowMenuView.setImageTintList(stateList);
                } catch (NoSuchMethodError e) {
                }
            }
        }
    }

    private static TintImageView findOverflowMenuView(Activity activity) {
        ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
        Queue<ViewGroup> children = new LinkedList();
        children.add(vg);
        while (children.size() > 0) {
            vg = (ViewGroup) children.poll();
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                if (child instanceof TintImageView) {
                    return (TintImageView) child;
                }
                if (child instanceof ViewGroup) {
                    children.add((ViewGroup) child);
                }
            }
        }
        return null;
    }
}
