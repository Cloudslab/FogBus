package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window.Callback;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.runtime.util.PaintUtil;

public class AppInventorCompatActivity extends Activity implements AppCompatCallback {
    static final int DEFAULT_PRIMARY_COLOR = PaintUtil.hexStringToInt(ComponentConstants.DEFAULT_PRIMARY_COLOR);
    private static final String LOG_TAG = AppInventorCompatActivity.class.getSimpleName();
    private static boolean actionBarEnabled;
    private static boolean classicMode;
    private static Theme currentTheme = Theme.PACKAGED;
    private static int primaryColor;
    private AppCompatDelegate appCompatDelegate;
    LinearLayout frameWithTitle;
    TextView titleBar;

    public enum Theme {
        PACKAGED,
        CLASSIC,
        DEVICE_DEFAULT,
        BLACK_TITLE_TEXT,
        DARK
    }

    public void onCreate(Bundle icicle) {
        if (currentTheme != Theme.PACKAGED) {
            applyTheme();
        }
        Callback classicCallback = getWindow().getCallback();
        this.appCompatDelegate = AppCompatDelegate.create((Activity) this, (AppCompatCallback) this);
        try {
            this.appCompatDelegate.onCreate(icicle);
        } catch (IllegalStateException e) {
            this.appCompatDelegate = null;
            classicMode = true;
            getWindow().setCallback(classicCallback);
        }
        super.onCreate(icicle);
        this.frameWithTitle = new LinearLayout(this);
        this.frameWithTitle.setOrientation(1);
        this.titleBar = (TextView) findViewById(16908310);
        if (shouldCreateTitleBar()) {
            this.titleBar = new TextView(this);
            this.titleBar.setBackgroundResource(17301653);
            this.titleBar.setTextAppearance(this, 16973907);
            this.titleBar.setGravity(16);
            this.titleBar.setSingleLine();
            this.titleBar.setShadowLayer(2.0f, 0.0f, 0.0f, -1157627904);
            if (!isClassicMode()) {
                this.frameWithTitle.addView(this.titleBar, new LayoutParams(-1, -2));
            }
        }
        setContentView(this.frameWithTitle);
    }

    public final boolean isAppCompatMode() {
        return this.appCompatDelegate != null;
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.onPostCreate(savedInstanceState);
        }
    }

    protected void onPostResume() {
        super.onPostResume();
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.onPostResume();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.onConfigurationChanged(newConfig);
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.onStop();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.onDestroy();
        }
    }

    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.setTitle(title);
        }
        if (isAppCompatMode() && this.titleBar != null) {
            this.titleBar.setText(title);
        }
    }

    public void onSupportActionModeStarted(ActionMode actionMode) {
    }

    public void onSupportActionModeFinished(ActionMode actionMode) {
    }

    @Nullable
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    public void setContentView(View view) {
        if (view != this.frameWithTitle) {
            this.frameWithTitle.addView(view, new FrameLayout.LayoutParams(-1, -1));
            view = this.frameWithTitle;
        }
        if (this.appCompatDelegate != null) {
            this.appCompatDelegate.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    public ActionBar getSupportActionBar() {
        return this.appCompatDelegate == null ? null : this.appCompatDelegate.getSupportActionBar();
    }

    protected static boolean isActionBarEnabled() {
        return actionBarEnabled;
    }

    protected void setActionBarEnabled(boolean enabled) {
        actionBarEnabled = enabled;
    }

    public static boolean isClassicMode() {
        return classicMode;
    }

    protected void setClassicMode(boolean classic) {
        if (isRepl()) {
            classicMode = classic;
        }
    }

    protected static int getPrimaryColor() {
        return primaryColor;
    }

    protected void setPrimaryColor(int color) {
        int newColor;
        ActionBar actionBar = getSupportActionBar();
        if (color == 0) {
            newColor = DEFAULT_PRIMARY_COLOR;
        } else {
            newColor = color;
        }
        if (actionBar != null && newColor != primaryColor) {
            primaryColor = newColor;
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
        }
    }

    protected boolean isRepl() {
        return false;
    }

    protected void hideTitleBar() {
        if (this.titleBar == null) {
            return;
        }
        if (this.titleBar.getParent() != this.frameWithTitle) {
            ((View) this.titleBar.getParent()).setVisibility(8);
        } else {
            this.titleBar.setVisibility(8);
        }
    }

    protected void maybeShowTitleBar() {
        Log.d(LOG_TAG, "maybeShowTitleBar");
        if (this.titleBar != null) {
            this.titleBar.setVisibility(0);
            Log.d(LOG_TAG, "titleBar visible");
            if (this.titleBar.getParent() != null && this.titleBar.getParent() != this.frameWithTitle) {
                Log.d(LOG_TAG, "Setting parent visible");
                ((View) this.titleBar.getParent()).setVisibility(0);
            }
        }
    }

    protected void styleTitleBar() {
        ActionBar actionBar = getSupportActionBar();
        Log.d(LOG_TAG, "actionBarEnabled = " + actionBarEnabled);
        Log.d(LOG_TAG, "!classicMode = " + (!classicMode));
        Log.d(LOG_TAG, "actionBar = " + actionBar);
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getPrimaryColor()));
            if (actionBarEnabled) {
                actionBar.show();
                hideTitleBar();
                return;
            }
            actionBar.hide();
        }
        maybeShowTitleBar();
    }

    protected void setAppInventorTheme(Theme theme) {
        if (Form.getActiveForm().isRepl() && theme != currentTheme) {
            currentTheme = theme;
            applyTheme();
        }
    }

    private void applyTheme() {
        Log.d(LOG_TAG, "applyTheme " + currentTheme);
        setClassicMode(false);
        switch (currentTheme) {
            case CLASSIC:
                setClassicMode(true);
                setTheme(16973829);
                return;
            case DEVICE_DEFAULT:
            case BLACK_TITLE_TEXT:
                setTheme(16974124);
                return;
            case DARK:
                setTheme(16974121);
                return;
            default:
                return;
        }
    }

    private boolean shouldCreateTitleBar() {
        if (isAppCompatMode() && (getSupportActionBar() == null || !isActionBarEnabled())) {
            return true;
        }
        if (this.titleBar == null && isRepl()) {
            return true;
        }
        return false;
    }
}
