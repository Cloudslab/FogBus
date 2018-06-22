package com.google.appinventor.components.runtime.util;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

public final class ViewUtil {
    private ViewUtil() {
    }

    private static int calculatePixels(View view, int sizeInDP) {
        return (int) (view.getContext().getResources().getDisplayMetrics().density * ((float) sizeInDP));
    }

    public static void setChildWidthForHorizontalLayout(View view, int width) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) layoutParams;
            switch (width) {
                case -2:
                    linearLayoutParams.width = 0;
                    linearLayoutParams.weight = 1.0f;
                    break;
                case -1:
                    linearLayoutParams.width = -2;
                    linearLayoutParams.weight = 0.0f;
                    break;
                default:
                    linearLayoutParams.width = calculatePixels(view, width);
                    linearLayoutParams.weight = 0.0f;
                    break;
            }
            view.requestLayout();
            return;
        }
        Log.e("ViewUtil", "The view does not have linear layout parameters");
    }

    public static void setChildHeightForHorizontalLayout(View view, int height) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) layoutParams;
            switch (height) {
                case -2:
                    linearLayoutParams.height = -1;
                    break;
                case -1:
                    linearLayoutParams.height = -2;
                    break;
                default:
                    linearLayoutParams.height = calculatePixels(view, height);
                    break;
            }
            view.requestLayout();
            return;
        }
        Log.e("ViewUtil", "The view does not have linear layout parameters");
    }

    public static void setChildWidthForVerticalLayout(View view, int width) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) layoutParams;
            switch (width) {
                case -2:
                    linearLayoutParams.width = -1;
                    break;
                case -1:
                    linearLayoutParams.width = -2;
                    break;
                default:
                    linearLayoutParams.width = calculatePixels(view, width);
                    break;
            }
            view.requestLayout();
            return;
        }
        Log.e("ViewUtil", "The view does not have linear layout parameters");
    }

    public static void setChildHeightForVerticalLayout(View view, int height) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) layoutParams;
            switch (height) {
                case -2:
                    linearLayoutParams.height = 0;
                    linearLayoutParams.weight = 1.0f;
                    break;
                case -1:
                    linearLayoutParams.height = -2;
                    linearLayoutParams.weight = 0.0f;
                    break;
                default:
                    linearLayoutParams.height = calculatePixels(view, height);
                    linearLayoutParams.weight = 0.0f;
                    break;
            }
            view.requestLayout();
            return;
        }
        Log.e("ViewUtil", "The view does not have linear layout parameters");
    }

    public static void setChildWidthForTableLayout(View view, int width) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof TableRow.LayoutParams) {
            TableRow.LayoutParams tableLayoutParams = (TableRow.LayoutParams) layoutParams;
            switch (width) {
                case -2:
                    tableLayoutParams.width = -1;
                    break;
                case -1:
                    tableLayoutParams.width = -2;
                    break;
                default:
                    tableLayoutParams.width = calculatePixels(view, width);
                    break;
            }
            view.requestLayout();
            return;
        }
        Log.e("ViewUtil", "The view does not have table layout parameters");
    }

    public static void setChildHeightForTableLayout(View view, int height) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof TableRow.LayoutParams) {
            TableRow.LayoutParams tableLayoutParams = (TableRow.LayoutParams) layoutParams;
            switch (height) {
                case -2:
                    tableLayoutParams.height = -1;
                    break;
                case -1:
                    tableLayoutParams.height = -2;
                    break;
                default:
                    tableLayoutParams.height = calculatePixels(view, height);
                    break;
            }
            view.requestLayout();
            return;
        }
        Log.e("ViewUtil", "The view does not have table layout parameters");
    }

    public static void setBackgroundImage(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
        view.requestLayout();
    }

    public static void setImage(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
        if (drawable != null) {
            view.setAdjustViewBounds(true);
        }
        view.requestLayout();
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
        view.invalidate();
    }
}
