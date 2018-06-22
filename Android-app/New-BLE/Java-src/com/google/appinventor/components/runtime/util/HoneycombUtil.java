package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

public class HoneycombUtil {
    public static final int VIEWGROUP_MEASURED_HEIGHT_STATE_SHIFT = 16;

    private HoneycombUtil() {
    }

    public static int combineMeasuredStates(ViewGroup view, int curState, int newState) {
        return ViewGroup.combineMeasuredStates(curState, newState);
    }

    public static int getMeasuredState(View view) {
        return view.getMeasuredState();
    }

    public static int resolveSizeAndState(ViewGroup view, int maxWidth, int widthMeasureSpec, int childState) {
        return ViewGroup.resolveSizeAndState(maxWidth, widthMeasureSpec, childState);
    }

    public static void viewSetRotate(View view, double rotationAngle) {
        view.setRotation((float) rotationAngle);
    }

    public static Spinner makeSpinner(Context activity) {
        return new Spinner(activity, 0);
    }
}
