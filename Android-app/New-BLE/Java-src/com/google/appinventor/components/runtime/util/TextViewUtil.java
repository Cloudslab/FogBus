package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Html;
import android.widget.TextView;

public class TextViewUtil {
    private TextViewUtil() {
    }

    public static void setAlignment(TextView textview, int alignment, boolean centerVertically) {
        int horizontalGravity;
        switch (alignment) {
            case 0:
                horizontalGravity = 3;
                break;
            case 1:
                horizontalGravity = 1;
                break;
            case 2:
                horizontalGravity = 5;
                break;
            default:
                throw new IllegalArgumentException();
        }
        textview.setGravity(horizontalGravity | (centerVertically ? 16 : 48));
        textview.invalidate();
    }

    public static void setBackgroundColor(TextView textview, int argb) {
        textview.setBackgroundColor(argb);
        textview.invalidate();
    }

    public static boolean isEnabled(TextView textview) {
        return textview.isEnabled();
    }

    public static void setEnabled(TextView textview, boolean enabled) {
        textview.setEnabled(enabled);
        textview.invalidate();
    }

    public static float getFontSize(TextView textview, Context context) {
        return textview.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static void setFontSize(TextView textview, float size) {
        textview.setTextSize(size);
        textview.requestLayout();
    }

    public static void setFontTypeface(TextView textview, int typeface, boolean bold, boolean italic) {
        Typeface tf;
        switch (typeface) {
            case 0:
                tf = Typeface.DEFAULT;
                break;
            case 1:
                tf = Typeface.SANS_SERIF;
                break;
            case 2:
                tf = Typeface.SERIF;
                break;
            case 3:
                tf = Typeface.MONOSPACE;
                break;
            default:
                throw new IllegalArgumentException();
        }
        int style = 0;
        if (bold) {
            style = 0 | 1;
        }
        if (italic) {
            style |= 2;
        }
        textview.setTypeface(Typeface.create(tf, style));
        textview.requestLayout();
    }

    public static String getText(TextView textview) {
        return textview.getText().toString();
    }

    public static void setTextHTML(TextView textview, String text) {
        textview.setText(Html.fromHtml(text));
        textview.requestLayout();
    }

    public static void setText(TextView textview, String text) {
        textview.setText(text);
        textview.requestLayout();
    }

    public static void setPadding(TextView textview, int padding) {
        textview.setPadding(padding, padding, 0, 0);
        textview.requestLayout();
    }

    public static void setTextColor(TextView textview, int argb) {
        textview.setTextColor(argb);
        textview.invalidate();
    }

    public static void setTextColors(TextView textview, ColorStateList colorStateList) {
        textview.setTextColor(colorStateList);
    }
}
