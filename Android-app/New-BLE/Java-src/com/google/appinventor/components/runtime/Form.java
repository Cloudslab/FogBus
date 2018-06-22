package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity.Theme;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.collect.Sets;
import com.google.appinventor.components.runtime.multidex.MultiDex;
import com.google.appinventor.components.runtime.util.AlignmentUtil;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.PaintUtil;
import com.google.appinventor.components.runtime.util.ScreenDensityUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;

@DesignerComponent(androidMinSdk = 7, category = ComponentCategory.LAYOUT, description = "Top-level component containing all other components in the program", showOnPalette = false, version = 23)
@UsesLibraries(libraries = "appcompat-v7.aar, support-v4.aar")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.ACCESS_WIFI_STATE,android.permission.ACCESS_NETWORK_STATE")
public class Form extends AppInventorCompatActivity implements Component, ComponentContainer, HandlesEventDispatching, OnGlobalLayoutListener {
    public static final String APPINVENTOR_URL_SCHEME = "appinventor";
    private static final String ARGUMENT_NAME = "APP_INVENTOR_START";
    private static final int DEFAULT_ACCENT_COLOR = PaintUtil.hexStringToInt(ComponentConstants.DEFAULT_ACCENT_COLOR);
    private static final int DEFAULT_PRIMARY_COLOR_DARK = PaintUtil.hexStringToInt(ComponentConstants.DEFAULT_PRIMARY_DARK_COLOR);
    private static final String LOG_TAG = "Form";
    private static final String RESULT_NAME = "APP_INVENTOR_RESULT";
    private static final int SWITCH_FORM_REQUEST_CODE = 1;
    private static boolean _initialized = false;
    protected static Form activeForm;
    private static boolean applicationIsBeingClosed;
    private static boolean isClassicTheme;
    private static long minimumToastWait = 10000000000L;
    private static int nextRequestCode = 2;
    private static boolean sCompatibilityMode;
    private static boolean showListsAsJson = false;
    private String aboutScreen;
    private int accentColor = DEFAULT_ACCENT_COLOR;
    private boolean actionBarEnabled = false;
    private final HashMap<Integer, ActivityResultListener> activityResultMap = Maps.newHashMap();
    private AlignmentUtil alignmentSetter;
    private final Handler androidUIHandler = new Handler();
    private int backgroundColor;
    private Drawable backgroundDrawable;
    private String backgroundImagePath = "";
    private String closeAnimType;
    private float compatScalingFactor;
    private float deviceDensity;
    private ArrayList<PercentStorageRecord> dimChanges = new ArrayList();
    private int formHeight;
    protected String formName;
    private int formWidth;
    private FrameLayout frameLayout;
    private FullScreenVideoUtil fullScreenVideoUtil;
    private int horizontalAlignment;
    private boolean keyboardShown = false;
    private long lastToastTime = (System.nanoTime() - minimumToastWait);
    private String nextFormName;
    private final Set<OnClearListener> onClearListeners = Sets.newHashSet();
    private final Set<OnCreateOptionsMenuListener> onCreateOptionsMenuListeners = Sets.newHashSet();
    private final Set<OnDestroyListener> onDestroyListeners = Sets.newHashSet();
    private final Set<OnInitializeListener> onInitializeListeners = Sets.newHashSet();
    private final Set<OnNewIntentListener> onNewIntentListeners = Sets.newHashSet();
    private final Set<OnOptionsItemSelectedListener> onOptionsItemSelectedListeners = Sets.newHashSet();
    private final Set<OnPauseListener> onPauseListeners = Sets.newHashSet();
    private final Set<OnResumeListener> onResumeListeners = Sets.newHashSet();
    private final Set<OnStopListener> onStopListeners = Sets.newHashSet();
    private String openAnimType;
    private int primaryColor = DEFAULT_PRIMARY_COLOR;
    private int primaryColorDark = DEFAULT_PRIMARY_COLOR_DARK;
    private ProgressDialog progress;
    private ScaledFrameLayout scaleLayout;
    private boolean screenInitialized;
    private boolean scrollable;
    private boolean showStatusBar = true;
    private boolean showTitle = true;
    protected String startupValue = "";
    protected String title = "";
    private boolean usesDarkTheme;
    private boolean usesDefaultBackground;
    private int verticalAlignment;
    private LinearLayout viewLayout;
    private String yandexTranslateTagline = "";

    class C01962 implements Runnable {
        C01962() {
        }

        public void run() {
            if (Form.this.frameLayout == null || Form.this.frameLayout.getWidth() == 0 || Form.this.frameLayout.getHeight() == 0) {
                Form.this.androidUIHandler.post(this);
                return;
            }
            EventDispatcher.dispatchEvent(Form.this, "Initialize", new Object[0]);
            if (Form.sCompatibilityMode) {
                Form.this.Sizing("Fixed");
            } else {
                Form.this.Sizing("Responsive");
            }
            Form.this.screenInitialized = true;
            for (OnInitializeListener onInitializeListener : Form.this.onInitializeListeners) {
                onInitializeListener.onInitialize();
            }
            if (Form.activeForm instanceof ReplForm) {
                ((ReplForm) Form.activeForm).HandleReturnValues();
            }
        }
    }

    class C01995 implements Runnable {
        C01995() {
        }

        public void run() {
            if (Form.this.frameLayout == null || Form.this.frameLayout.getWidth() == 0 || Form.this.frameLayout.getHeight() == 0) {
                Form.this.androidUIHandler.post(this);
                return;
            }
            if (Form.sCompatibilityMode) {
                Form.this.Sizing("Fixed");
            } else {
                Form.this.Sizing("Responsive");
            }
            Form.this.ReplayFormOrientation();
            Form.this.frameWithTitle.requestLayout();
        }
    }

    class C02028 implements OnMenuItemClickListener {
        C02028() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            Form.this.showExitApplicationNotification();
            return true;
        }
    }

    class C02039 implements OnMenuItemClickListener {
        C02039() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            Form.this.showAboutApplicationNotification();
            return true;
        }
    }

    private static class MultiDexInstaller extends AsyncTask<Form, Void, Boolean> {
        Form ourForm;

        private MultiDexInstaller() {
        }

        protected Boolean doInBackground(Form... form) {
            this.ourForm = form[0];
            Log.d(Form.LOG_TAG, "Doing Full MultiDex Install");
            MultiDex.install(this.ourForm, true);
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean v) {
            this.ourForm.onCreateFinish();
        }
    }

    public static class PercentStorageRecord {
        AndroidViewComponent component;
        Dim dim;
        int length;

        public enum Dim {
            HEIGHT,
            WIDTH
        }

        public PercentStorageRecord(AndroidViewComponent component, int length, Dim dim) {
            this.component = component;
            this.length = length;
            this.dim = dim;
        }
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String className = getClass().getName();
        this.formName = className.substring(className.lastIndexOf(46) + 1);
        Log.d(LOG_TAG, "Form " + this.formName + " got onCreate");
        activeForm = this;
        Log.i(LOG_TAG, "activeForm is now " + activeForm.formName);
        this.deviceDensity = getResources().getDisplayMetrics().density;
        Log.d(LOG_TAG, "deviceDensity = " + this.deviceDensity);
        this.compatScalingFactor = ScreenDensityUtil.computeCompatibleScaling(this);
        Log.i(LOG_TAG, "compatScalingFactor = " + this.compatScalingFactor);
        this.viewLayout = new LinearLayout(this, 1);
        this.alignmentSetter = new AlignmentUtil(this.viewLayout);
        this.progress = null;
        if (_initialized || !this.formName.equals("Screen1")) {
            Log.d(LOG_TAG, "NO MULTI: _initialized = " + _initialized + " formName = " + this.formName);
            _initialized = true;
            onCreateFinish();
            return;
        }
        Log.d(LOG_TAG, "MULTI: _initialized = " + _initialized + " formName = " + this.formName);
        _initialized = true;
        if (ReplApplication.installed) {
            Log.d(LOG_TAG, "MultiDex already installed.");
            onCreateFinish();
            return;
        }
        this.progress = ProgressDialog.show(this, "Please Wait...", "Installation Finishing");
        this.progress.show();
        new MultiDexInstaller().execute(new Form[]{this});
    }

    void onCreateFinish() {
        Log.d(LOG_TAG, "onCreateFinish called " + System.currentTimeMillis());
        if (this.progress != null) {
            this.progress.dismiss();
        }
        defaultPropertyValues();
        Intent startIntent = getIntent();
        if (startIntent != null && startIntent.hasExtra(ARGUMENT_NAME)) {
            this.startupValue = startIntent.getStringExtra(ARGUMENT_NAME);
        }
        this.fullScreenVideoUtil = new FullScreenVideoUtil(this, this.androidUIHandler);
        getWindow().setSoftInputMode(getWindow().getAttributes().softInputMode | 16);
        $define();
        Initialize();
    }

    private void defaultPropertyValues() {
        if (isRepl()) {
            ActionBar(this.actionBarEnabled);
        } else {
            ActionBar(getSupportActionBar() != null);
        }
        Scrollable(false);
        Sizing("Fixed");
        BackgroundImage("");
        AboutScreen("");
        BackgroundImage("");
        AlignHorizontal(1);
        AlignVertical(1);
        Title("");
        ShowStatusBar(true);
        TitleVisible(true);
        ShowListsAsJson(false);
        ActionBar(false);
        AccentColor(DEFAULT_ACCENT_COLOR);
        PrimaryColor(DEFAULT_PRIMARY_COLOR);
        PrimaryColorDark(DEFAULT_PRIMARY_COLOR_DARK);
        Theme(ComponentConstants.DEFAULT_THEME);
        ScreenOrientation("unspecified");
        BackgroundColor(0);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(LOG_TAG, "onConfigurationChanged() called");
        final int newOrientation = newConfig.orientation;
        if (newOrientation == 2 || newOrientation == 1) {
            this.androidUIHandler.post(new Runnable() {
                public void run() {
                    boolean dispatchEventNow = false;
                    if (Form.this.frameLayout != null) {
                        if (newOrientation == 2) {
                            if (Form.this.frameLayout.getWidth() >= Form.this.frameLayout.getHeight()) {
                                dispatchEventNow = true;
                            }
                        } else if (Form.this.frameLayout.getHeight() >= Form.this.frameLayout.getWidth()) {
                            dispatchEventNow = true;
                        }
                    }
                    if (dispatchEventNow) {
                        Form.this.recomputeLayout();
                        final FrameLayout savedLayout = Form.this.frameLayout;
                        Form.this.androidUIHandler.postDelayed(new Runnable() {
                            public void run() {
                                if (savedLayout != null) {
                                    savedLayout.invalidate();
                                }
                            }
                        }, 100);
                        Form.this.ScreenOrientationChanged();
                        return;
                    }
                    Form.this.androidUIHandler.post(this);
                }
            });
        }
    }

    public void onGlobalLayout() {
        int heightDiff = this.scaleLayout.getRootView().getHeight() - this.scaleLayout.getHeight();
        int[] position = new int[2];
        this.scaleLayout.getLocationInWindow(position);
        int contentViewTop = position[1];
        Log.d(LOG_TAG, "onGlobalLayout(): heightdiff = " + heightDiff + " contentViewTop = " + contentViewTop);
        if (heightDiff <= contentViewTop) {
            Log.d(LOG_TAG, "keyboard hidden!");
            if (this.keyboardShown) {
                this.keyboardShown = false;
                if (sCompatibilityMode) {
                    this.scaleLayout.setScale(this.compatScalingFactor);
                    this.scaleLayout.invalidate();
                    return;
                }
                return;
            }
            return;
        }
        int keyboardHeight = heightDiff - contentViewTop;
        Log.d(LOG_TAG, "keyboard shown!");
        this.keyboardShown = true;
        if (this.scaleLayout != null) {
            this.scaleLayout.setScale(1.0f);
            this.scaleLayout.invalidate();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (BackPressed()) {
            return true;
        }
        boolean handled = super.onKeyDown(keyCode, event);
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
        return handled;
    }

    @SimpleEvent(description = "Device back button pressed.")
    public boolean BackPressed() {
        return EventDispatcher.dispatchEvent(this, "BackPressed", new Object[0]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG_TAG, "Form " + this.formName + " got onActivityResult, requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == 1) {
            String resultString;
            if (data == null || !data.hasExtra(RESULT_NAME)) {
                resultString = "";
            } else {
                resultString = data.getStringExtra(RESULT_NAME);
            }
            OtherScreenClosed(this.nextFormName, decodeJSONStringForForm(resultString, "other screen closed"));
            return;
        }
        ActivityResultListener component = (ActivityResultListener) this.activityResultMap.get(Integer.valueOf(requestCode));
        if (component != null) {
            component.resultReturned(requestCode, resultCode, data);
        }
    }

    private static Object decodeJSONStringForForm(String jsonString, String functionName) {
        Log.i(LOG_TAG, "decodeJSONStringForForm -- decoding JSON representation:" + jsonString);
        Object obj = "";
        try {
            obj = JsonUtil.getObjectFromJson(jsonString);
            Log.i(LOG_TAG, "decodeJSONStringForForm -- got decoded JSON:" + obj.toString());
            return obj;
        } catch (JSONException e) {
            activeForm.dispatchErrorOccurredEvent(activeForm, functionName, ErrorMessages.ERROR_SCREEN_BAD_VALUE_RECEIVED, jsonString);
            return obj;
        }
    }

    public int registerForActivityResult(ActivityResultListener listener) {
        int requestCode = generateNewRequestCode();
        this.activityResultMap.put(Integer.valueOf(requestCode), listener);
        return requestCode;
    }

    public void unregisterForActivityResult(ActivityResultListener listener) {
        List<Integer> keysToDelete = Lists.newArrayList();
        for (Entry<Integer, ActivityResultListener> mapEntry : this.activityResultMap.entrySet()) {
            if (listener.equals(mapEntry.getValue())) {
                keysToDelete.add(mapEntry.getKey());
            }
        }
        for (Integer key : keysToDelete) {
            this.activityResultMap.remove(key);
        }
    }

    void ReplayFormOrientation() {
        Log.d(LOG_TAG, "ReplayFormOrientation()");
        ArrayList<PercentStorageRecord> temp = (ArrayList) this.dimChanges.clone();
        this.dimChanges.clear();
        for (int i = 0; i < temp.size(); i++) {
            PercentStorageRecord r = (PercentStorageRecord) temp.get(i);
            if (r.dim == Dim.HEIGHT) {
                r.component.Height(r.length);
            } else {
                r.component.Width(r.length);
            }
        }
    }

    public void registerPercentLength(AndroidViewComponent component, int length, Dim dim) {
        this.dimChanges.add(new PercentStorageRecord(component, length, dim));
    }

    private static int generateNewRequestCode() {
        int i = nextRequestCode;
        nextRequestCode = i + 1;
        return i;
    }

    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "Form " + this.formName + " got onResume");
        activeForm = this;
        if (applicationIsBeingClosed) {
            closeApplication();
            return;
        }
        for (OnResumeListener onResumeListener : this.onResumeListeners) {
            onResumeListener.onResume();
        }
    }

    public void registerForOnResume(OnResumeListener component) {
        this.onResumeListeners.add(component);
    }

    public void registerForOnInitialize(OnInitializeListener component) {
        this.onInitializeListeners.add(component);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, "Form " + this.formName + " got onNewIntent " + intent);
        for (OnNewIntentListener onNewIntentListener : this.onNewIntentListeners) {
            onNewIntentListener.onNewIntent(intent);
        }
    }

    public void registerForOnNewIntent(OnNewIntentListener component) {
        this.onNewIntentListeners.add(component);
    }

    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "Form " + this.formName + " got onPause");
        for (OnPauseListener onPauseListener : this.onPauseListeners) {
            onPauseListener.onPause();
        }
    }

    public void registerForOnPause(OnPauseListener component) {
        this.onPauseListeners.add(component);
    }

    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "Form " + this.formName + " got onStop");
        for (OnStopListener onStopListener : this.onStopListeners) {
            onStopListener.onStop();
        }
    }

    public void registerForOnStop(OnStopListener component) {
        this.onStopListeners.add(component);
    }

    public void registerForOnClear(OnClearListener component) {
        this.onClearListeners.add(component);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "Form " + this.formName + " got onDestroy");
        EventDispatcher.removeDispatchDelegate(this);
        for (OnDestroyListener onDestroyListener : this.onDestroyListeners) {
            onDestroyListener.onDestroy();
        }
    }

    public void registerForOnDestroy(OnDestroyListener component) {
        this.onDestroyListeners.add(component);
    }

    public void registerForOnCreateOptionsMenu(OnCreateOptionsMenuListener component) {
        this.onCreateOptionsMenuListeners.add(component);
    }

    public void registerForOnOptionsItemSelected(OnOptionsItemSelectedListener component) {
        this.onOptionsItemSelectedListeners.add(component);
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG /*189*/:
                return this.fullScreenVideoUtil.createFullScreenVideoDialog();
            default:
                return super.onCreateDialog(id);
        }
    }

    public void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG /*189*/:
                this.fullScreenVideoUtil.prepareFullScreenVideoDialog(dialog);
                return;
            default:
                super.onPrepareDialog(id, dialog);
                return;
        }
    }

    protected void $define() {
        throw new UnsupportedOperationException();
    }

    public boolean canDispatchEvent(Component component, String eventName) {
        boolean canDispatch = this.screenInitialized || (component == this && eventName.equals("Initialize"));
        if (canDispatch) {
            activeForm = this;
        }
        return canDispatch;
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] args) {
        throw new UnsupportedOperationException();
    }

    @SimpleEvent(description = "Screen starting")
    public void Initialize() {
        this.androidUIHandler.post(new C01962());
    }

    @SimpleEvent(description = "Screen orientation changed")
    public void ScreenOrientationChanged() {
        EventDispatcher.dispatchEvent(this, "ScreenOrientationChanged", new Object[0]);
    }

    @SimpleEvent(description = "Event raised when an error occurs. Only some errors will raise this condition.  For those errors, the system will show a notification by default.  You can use this event handler to prescribe an error behavior different than the default.")
    public void ErrorOccurred(Component component, String functionName, int errorNumber, String message) {
        String componentType = component.getClass().getName();
        Log.e(LOG_TAG, "Form " + this.formName + " ErrorOccurred, errorNumber = " + errorNumber + ", componentType = " + componentType.substring(componentType.lastIndexOf(".") + 1) + ", functionName = " + functionName + ", messages = " + message);
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", component, functionName, Integer.valueOf(errorNumber), message) && this.screenInitialized) {
            new Notifier(this).ShowAlert("Error " + errorNumber + ": " + message);
        }
    }

    public void ErrorOccurredDialog(Component component, String functionName, int errorNumber, String message, String title, String buttonText) {
        String componentType = component.getClass().getName();
        Log.e(LOG_TAG, "Form " + this.formName + " ErrorOccurred, errorNumber = " + errorNumber + ", componentType = " + componentType.substring(componentType.lastIndexOf(".") + 1) + ", functionName = " + functionName + ", messages = " + message);
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", component, functionName, Integer.valueOf(errorNumber), message) && this.screenInitialized) {
            new Notifier(this).ShowMessageDialog("Error " + errorNumber + ": " + message, title, buttonText);
        }
    }

    public void dispatchErrorOccurredEvent(Component component, String functionName, int errorNumber, Object... messageArgs) {
        final int i = errorNumber;
        final Object[] objArr = messageArgs;
        final Component component2 = component;
        final String str = functionName;
        runOnUiThread(new Runnable() {
            public void run() {
                Form.this.ErrorOccurred(component2, str, i, ErrorMessages.formatMessage(i, objArr));
            }
        });
    }

    public void dispatchErrorOccurredEventDialog(Component component, String functionName, int errorNumber, Object... messageArgs) {
        final int i = errorNumber;
        final Object[] objArr = messageArgs;
        final Component component2 = component;
        final String str = functionName;
        runOnUiThread(new Runnable() {
            public void run() {
                Form.this.ErrorOccurredDialog(component2, str, i, ErrorMessages.formatMessage(i, objArr), "Error in " + str, "Dismiss");
            }
        });
    }

    public void runtimeFormErrorOccurredEvent(String functionName, int errorNumber, String message) {
        Log.d("FORM_RUNTIME_ERROR", "functionName is " + functionName);
        Log.d("FORM_RUNTIME_ERROR", "errorNumber is " + errorNumber);
        Log.d("FORM_RUNTIME_ERROR", "message is " + message);
        dispatchErrorOccurredEvent(activeForm, functionName, errorNumber, message);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "When checked, there will be a vertical scrollbar on the screen, and the height of the application can exceed the physical height of the device. When unchecked, the application height is constrained to the height of the device.")
    public boolean Scrollable() {
        return this.scrollable;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void Scrollable(boolean scrollable) {
        if (this.scrollable != scrollable || this.frameLayout == null) {
            this.scrollable = scrollable;
            recomputeLayout();
        }
    }

    private void recomputeLayout() {
        FrameLayout scrollView;
        Log.d(LOG_TAG, "recomputeLayout called");
        if (this.frameLayout != null) {
            this.frameLayout.removeAllViews();
        }
        this.frameWithTitle.removeAllViews();
        if (!(!isAppCompatMode() || isClassicTheme || this.titleBar == null)) {
            try {
                this.frameWithTitle.addView(this.titleBar, new LayoutParams(-1, -2));
            } catch (IllegalStateException e) {
            }
        }
        if (this.scrollable) {
            scrollView = new ScrollView(this);
        } else {
            scrollView = new FrameLayout(this);
        }
        this.frameLayout = scrollView;
        this.frameLayout.addView(this.viewLayout.getLayoutManager(), new LayoutParams(-1, -1));
        setBackground(this.frameLayout);
        Log.d(LOG_TAG, "About to create a new ScaledFrameLayout");
        this.scaleLayout = new ScaledFrameLayout(this);
        this.scaleLayout.addView(this.frameLayout, new LayoutParams(-1, -1));
        this.frameWithTitle.addView(this.scaleLayout, new LayoutParams(-1, -1));
        this.frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.scaleLayout.requestLayout();
        this.androidUIHandler.post(new C01995());
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&HFFFFFFFF", editorType = "color")
    @SimpleProperty
    public void BackgroundColor(int argb) {
        if (argb == 0) {
            this.usesDefaultBackground = true;
        } else {
            this.usesDefaultBackground = false;
            this.backgroundColor = argb;
        }
        setBackground(this.frameLayout);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The screen background image.")
    public String BackgroundImage() {
        return this.backgroundImagePath;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The screen background image.")
    public void BackgroundImage(String path) {
        if (path == null) {
            path = "";
        }
        this.backgroundImagePath = path;
        try {
            this.backgroundDrawable = MediaUtil.getBitmapDrawable(this, this.backgroundImagePath);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to load " + this.backgroundImagePath);
            this.backgroundDrawable = null;
        }
        setBackground(this.frameLayout);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The caption for the form, which apears in the title bar")
    public String Title() {
        return getTitle().toString();
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Title(String title) {
        this.title = title;
        if (this.titleBar != null) {
            this.titleBar.setText(title);
        }
        setTitle(title);
        updateTitle();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Information about the screen.  It appears when \"About this Application\" is selected from the system menu. Use it to inform people about your app.  In multiple screen apps, each screen has its own AboutScreen info.")
    public String AboutScreen() {
        return this.aboutScreen;
    }

    @DesignerProperty(defaultValue = "", editorType = "textArea")
    @SimpleProperty
    public void AboutScreen(String aboutScreen) {
        this.aboutScreen = aboutScreen;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The title bar is the top gray bar on the screen. This property reports whether the title bar is visible.")
    public boolean TitleVisible() {
        return this.showTitle;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void TitleVisible(boolean show) {
        if (show == this.showTitle) {
            return;
        }
        if (this.actionBarEnabled) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                if (show) {
                    actionBar.show();
                } else {
                    actionBar.hide();
                }
                this.showTitle = show;
                return;
            }
            return;
        }
        this.showTitle = show;
        maybeShowTitleBar();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The status bar is the topmost bar on the screen. This property reports whether the status bar is visible.")
    public boolean ShowStatusBar() {
        return this.showStatusBar;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ShowStatusBar(boolean show) {
        if (show != this.showStatusBar) {
            if (show) {
                getWindow().addFlags(2048);
                getWindow().clearFlags(1024);
            } else {
                getWindow().addFlags(1024);
                getWindow().clearFlags(2048);
            }
            this.showStatusBar = show;
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The requested screen orientation, specified as a text value.  Commonly used values are landscape, portrait, sensor, user and unspecified.  See the Android developer documentation for ActivityInfo.Screen_Orientation for the complete list of possible settings.")
    public String ScreenOrientation() {
        switch (getRequestedOrientation()) {
            case -1:
                return "unspecified";
            case 0:
                return "landscape";
            case 1:
                return "portrait";
            case 2:
                return "user";
            case 3:
                return "behind";
            case 4:
                return "sensor";
            case 5:
                return "nosensor";
            case 6:
                return "sensorLandscape";
            case 7:
                return "sensorPortrait";
            case 8:
                return "reverseLandscape";
            case 9:
                return "reversePortrait";
            case 10:
                return "fullSensor";
            default:
                return "unspecified";
        }
    }

    @DesignerProperty(defaultValue = "unspecified", editorType = "screen_orientation")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ScreenOrientation(String screenOrientation) {
        if (screenOrientation.equalsIgnoreCase("behind")) {
            setRequestedOrientation(3);
        } else if (screenOrientation.equalsIgnoreCase("landscape")) {
            setRequestedOrientation(0);
        } else if (screenOrientation.equalsIgnoreCase("nosensor")) {
            setRequestedOrientation(5);
        } else if (screenOrientation.equalsIgnoreCase("portrait")) {
            setRequestedOrientation(1);
        } else if (screenOrientation.equalsIgnoreCase("sensor")) {
            setRequestedOrientation(4);
        } else if (screenOrientation.equalsIgnoreCase("unspecified")) {
            setRequestedOrientation(-1);
        } else if (screenOrientation.equalsIgnoreCase("user")) {
            setRequestedOrientation(2);
        } else if (SdkLevel.getLevel() < 9) {
            dispatchErrorOccurredEvent(this, "ScreenOrientation", ErrorMessages.ERROR_INVALID_SCREEN_ORIENTATION, screenOrientation);
        } else if (screenOrientation.equalsIgnoreCase("fullSensor")) {
            setRequestedOrientation(10);
        } else if (screenOrientation.equalsIgnoreCase("reverseLandscape")) {
            setRequestedOrientation(8);
        } else if (screenOrientation.equalsIgnoreCase("reversePortrait")) {
            setRequestedOrientation(9);
        } else if (screenOrientation.equalsIgnoreCase("sensorLandscape")) {
            setRequestedOrientation(6);
        } else if (screenOrientation.equalsIgnoreCase("sensorPortrait")) {
            setRequestedOrientation(7);
        } else {
            dispatchErrorOccurredEvent(this, "ScreenOrientation", ErrorMessages.ERROR_INVALID_SCREEN_ORIENTATION, screenOrientation);
        }
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(userVisible = false)
    public void ActionBar(boolean enabled) {
        if (this.actionBarEnabled != enabled) {
            setActionBarEnabled(enabled);
            ActionBar actionBar;
            if (enabled) {
                hideTitleBar();
                actionBar = getSupportActionBar();
                if (actionBar == null) {
                    dispatchErrorOccurredEvent(this, "ActionBar", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, new Object[0]);
                    this.actionBarEnabled = false;
                    return;
                } else if (this.showTitle) {
                    actionBar.show();
                } else {
                    actionBar.hide();
                }
            } else {
                maybeShowTitleBar();
                actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }
            }
            this.actionBarEnabled = enabled;
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how contents of the screen are aligned  horizontally. The choices are: 1 = left aligned, 2 = horizontally centered,  3 = right aligned.")
    public int AlignHorizontal() {
        return this.horizontalAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "horizontal_alignment")
    @SimpleProperty
    public void AlignHorizontal(int alignment) {
        try {
            this.alignmentSetter.setHorizontalAlignment(alignment);
            this.horizontalAlignment = alignment;
        } catch (IllegalArgumentException e) {
            dispatchErrorOccurredEvent(this, "HorizontalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_HORIZONTAL_ALIGNMENT, Integer.valueOf(alignment));
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how the contents of the arrangement are aligned vertically. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom. Vertical alignment has no effect if the screen is scrollable.")
    public int AlignVertical() {
        return this.verticalAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "vertical_alignment")
    @SimpleProperty
    public void AlignVertical(int alignment) {
        try {
            this.alignmentSetter.setVerticalAlignment(alignment);
            this.verticalAlignment = alignment;
        } catch (IllegalArgumentException e) {
            dispatchErrorOccurredEvent(this, "VerticalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT, Integer.valueOf(alignment));
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The animation for switching to another screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none")
    public String OpenScreenAnimation() {
        return this.openAnimType;
    }

    @DesignerProperty(defaultValue = "default", editorType = "screen_animation")
    @SimpleProperty
    public void OpenScreenAnimation(String animType) {
        if (animType == "default" || animType == "fade" || animType == "zoom" || animType == "slidehorizontal" || animType == "slidevertical" || animType == "none") {
            this.openAnimType = animType;
            return;
        }
        dispatchErrorOccurredEvent(this, "Screen", ErrorMessages.ERROR_SCREEN_INVALID_ANIMATION, animType);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The animation for closing current screen and returning  to the previous screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none")
    public String CloseScreenAnimation() {
        return this.closeAnimType;
    }

    @DesignerProperty(defaultValue = "default", editorType = "screen_animation")
    @SimpleProperty
    public void CloseScreenAnimation(String animType) {
        if (animType == "default" || animType == "fade" || animType == "zoom" || animType == "slidehorizontal" || animType == "slidevertical" || animType == "none") {
            this.closeAnimType = animType;
            return;
        }
        dispatchErrorOccurredEvent(this, "Screen", ErrorMessages.ERROR_SCREEN_INVALID_ANIMATION, animType);
    }

    public String getOpenAnimType() {
        return this.openAnimType;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty(userVisible = false)
    public void Icon(String name) {
    }

    @DesignerProperty(defaultValue = "1", editorType = "non_negative_integer")
    @SimpleProperty(description = "An integer value which must be incremented each time a new Android Application Package File (APK) is created for the Google Play Store.", userVisible = false)
    public void VersionCode(int vCode) {
    }

    @DesignerProperty(defaultValue = "1.0", editorType = "string")
    @SimpleProperty(description = "A string which can be changed to allow Google Play Store users to distinguish between different versions of the App.", userVisible = false)
    public void VersionName(String vName) {
    }

    @DesignerProperty(defaultValue = "Fixed", editorType = "sizing")
    @SimpleProperty(description = "If set to fixed,  screen layouts will be created for a single fixed-size screen and autoscaled. If set to responsive, screen layouts will use the actual resolution of the device.  See the documentation on responsive design in App Inventor for more information. This property appears on Screen1 only and controls the sizing for all screens in the app.", userVisible = false)
    public void Sizing(String value) {
        Log.d(LOG_TAG, "Sizing(" + value + ")");
        this.formWidth = (int) (((float) getResources().getDisplayMetrics().widthPixels) / this.deviceDensity);
        this.formHeight = (int) (((float) getResources().getDisplayMetrics().heightPixels) / this.deviceDensity);
        if (value.equals("Fixed")) {
            sCompatibilityMode = true;
            this.formWidth = (int) (((float) this.formWidth) / this.compatScalingFactor);
            this.formHeight = (int) (((float) this.formHeight) / this.compatScalingFactor);
        } else {
            sCompatibilityMode = false;
        }
        this.scaleLayout.setScale(sCompatibilityMode ? this.compatScalingFactor : 1.0f);
        if (this.frameLayout != null) {
            this.frameLayout.invalidate();
        }
        Log.d(LOG_TAG, "formWidth = " + this.formWidth + " formHeight = " + this.formHeight);
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If false, lists will be converted to strings using Lisp notation, i.e., as symbols separated by spaces, e.g., (a 1 b2 (c d). If true, lists will appear as in Json or Python, e.g.  [\"a\", 1, \"b\", 2, [\"c\", \"d\"]].  This property appears only in Screen 1, and the value for Screen 1 determines the behavior for all screens. The property defaults to \"false\" meaning that the App Inventor programmer must explicitly set it to \"true\" if JSON/Python syntax is desired. At some point in the future we will alter the system so that new projects are created with this property set to \"true\" by default. Existing projects will not be impacted. The App Inventor programmer can also set it back to \"false\" in newer projects if desired. ", userVisible = false)
    public void ShowListsAsJson(boolean asJson) {
        showListsAsJson = asJson;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public boolean ShowListsAsJson() {
        return showListsAsJson;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "This is the display name of the installed application in the phone.If the AppName is blank, it will be set to the name of the project when the project is built.", userVisible = false)
    public void AppName(String aName) {
    }

    @DesignerProperty(defaultValue = "&HFF3F51B5", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is the primary color used for Material UI elements, such as the ActionBar.", userVisible = false)
    public void PrimaryColor(int color) {
        setPrimaryColor(color);
    }

    @SimpleProperty
    public int PrimaryColor() {
        return this.primaryColor;
    }

    @DesignerProperty(defaultValue = "&HFF303F9F", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is the primary color used for darker elements in Material UI.", userVisible = false)
    public void PrimaryColorDark(int color) {
        this.primaryColorDark = color;
    }

    @SimpleProperty
    public int PrimaryColorDark() {
        return this.primaryColorDark;
    }

    @DesignerProperty(defaultValue = "&HFFFF4081", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is the accent color used for highlights and other user interface accents.", userVisible = false)
    public void AccentColor(int color) {
        this.accentColor = color;
    }

    @SimpleProperty
    public int AccentColor() {
        return this.accentColor;
    }

    @DesignerProperty(defaultValue = "Classic", editorType = "theme")
    @SimpleProperty(description = "Sets the theme used by the application.", userVisible = false)
    public void Theme(String theme) {
        if (this.usesDefaultBackground) {
            if (theme.equalsIgnoreCase("AppTheme")) {
                this.backgroundColor = -16777216;
            } else {
                this.backgroundColor = -1;
            }
            setBackground(this.frameLayout);
        }
        this.usesDarkTheme = false;
        if (theme.equals(ComponentConstants.DEFAULT_THEME)) {
            setAppInventorTheme(Theme.CLASSIC);
        } else if (theme.equals("AppTheme.Light.DarkActionBar")) {
            setAppInventorTheme(Theme.DEVICE_DEFAULT);
        } else if (theme.equals("AppTheme.Light")) {
            setAppInventorTheme(Theme.BLACK_TITLE_TEXT);
        } else if (theme.equals("AppTheme")) {
            this.usesDarkTheme = true;
            setAppInventorTheme(Theme.DARK);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Screen width (x-size).")
    public int Width() {
        Log.d(LOG_TAG, "Form.Width = " + this.formWidth);
        return this.formWidth;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Screen height (y-size).")
    public int Height() {
        Log.d(LOG_TAG, "Form.Height = " + this.formHeight);
        return this.formHeight;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "A URL to use to populate the Tutorial Sidebar while editing a project. Used as a teaching aid.", userVisible = false)
    public void TutorialURL(String url) {
    }

    public static void switchForm(String nextFormName) {
        if (activeForm != null) {
            activeForm.startNewForm(nextFormName, null);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void switchFormWithStartValue(String nextFormName, Object startValue) {
        Log.i(LOG_TAG, "Open another screen with start value:" + nextFormName);
        if (activeForm != null) {
            activeForm.startNewForm(nextFormName, startValue);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void startNewForm(String nextFormName, Object startupValue) {
        String jValue;
        Log.i(LOG_TAG, "startNewForm:" + nextFormName);
        Intent activityIntent = new Intent();
        activityIntent.setClassName(this, getPackageName() + "." + nextFormName);
        String functionName = startupValue == null ? "open another screen" : "open another screen with start value";
        if (startupValue != null) {
            Log.i(LOG_TAG, "StartNewForm about to JSON encode:" + startupValue);
            jValue = jsonEncodeForForm(startupValue, functionName);
            Log.i(LOG_TAG, "StartNewForm got JSON encoding:" + jValue);
        } else {
            jValue = "";
        }
        activityIntent.putExtra(ARGUMENT_NAME, jValue);
        this.nextFormName = nextFormName;
        Log.i(LOG_TAG, "about to start new form" + nextFormName);
        try {
            Log.i(LOG_TAG, "startNewForm starting activity:" + activityIntent);
            startActivityForResult(activityIntent, 1);
            AnimationUtil.ApplyOpenScreenAnimation(this, this.openAnimType);
        } catch (ActivityNotFoundException e) {
            dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_SCREEN_NOT_FOUND, nextFormName);
        }
    }

    protected static String jsonEncodeForForm(Object value, String functionName) {
        String jsonResult = "";
        Log.i(LOG_TAG, "jsonEncodeForForm -- creating JSON representation:" + value.toString());
        try {
            jsonResult = JsonUtil.getJsonRepresentation(value);
            Log.i(LOG_TAG, "jsonEncodeForForm -- got JSON representation:" + jsonResult);
            return jsonResult;
        } catch (JSONException e) {
            activeForm.dispatchErrorOccurredEvent(activeForm, functionName, ErrorMessages.ERROR_SCREEN_BAD_VALUE_FOR_SENDING, value.toString());
            return jsonResult;
        }
    }

    @SimpleEvent(description = "Event raised when another screen has closed and control has returned to this screen.")
    public void OtherScreenClosed(String otherScreenName, Object result) {
        Log.i(LOG_TAG, "Form " + this.formName + " OtherScreenClosed, otherScreenName = " + otherScreenName + ", result = " + result.toString());
        EventDispatcher.dispatchEvent(this, "OtherScreenClosed", otherScreenName, result);
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this;
    }

    public Activity $context() {
        return this;
    }

    public Form $form() {
        return this;
    }

    public void $add(AndroidViewComponent component) {
        this.viewLayout.add(component);
    }

    public float deviceDensity() {
        return this.deviceDensity;
    }

    public float compatScalingFactor() {
        return this.compatScalingFactor;
    }

    public void setChildWidth(final AndroidViewComponent component, int width) {
        int cWidth = Width();
        if (cWidth == 0) {
            final int fWidth = width;
            this.androidUIHandler.postDelayed(new Runnable() {
                public void run() {
                    System.err.println("(Form)Width not stable yet... trying again");
                    Form.this.setChildWidth(component, fWidth);
                }
            }, 100);
        }
        System.err.println("Form.setChildWidth(): width = " + width + " parent Width = " + cWidth + " child = " + component);
        if (width <= Component.LENGTH_PERCENT_TAG) {
            width = ((-(width + 1000)) * cWidth) / 100;
        }
        component.setLastWidth(width);
        ViewUtil.setChildWidthForVerticalLayout(component.getView(), width);
    }

    public void setChildHeight(final AndroidViewComponent component, int height) {
        if (Height() == 0) {
            final int fHeight = height;
            this.androidUIHandler.postDelayed(new Runnable() {
                public void run() {
                    System.err.println("(Form)Height not stable yet... trying again");
                    Form.this.setChildHeight(component, fHeight);
                }
            }, 100);
        }
        if (height <= Component.LENGTH_PERCENT_TAG) {
            height = (Height() * (-(height + 1000))) / 100;
        }
        component.setLastHeight(height);
        ViewUtil.setChildHeightForVerticalLayout(component.getView(), height);
    }

    public static Form getActiveForm() {
        return activeForm;
    }

    public static String getStartText() {
        if (activeForm != null) {
            return activeForm.startupValue;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static Object getStartValue() {
        if (activeForm != null) {
            return decodeJSONStringForForm(activeForm.startupValue, "get start value");
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivity() {
        if (activeForm != null) {
            activeForm.closeForm(null);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivityWithResult(Object result) {
        if (activeForm == null) {
            throw new IllegalStateException("activeForm is null");
        } else if (activeForm instanceof ReplForm) {
            ((ReplForm) activeForm).setResult(result);
            activeForm.closeForm(null);
        } else {
            String jString = jsonEncodeForForm(result, "close screen with value");
            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_NAME, jString);
            activeForm.closeForm(resultIntent);
        }
    }

    public static void finishActivityWithTextResult(String result) {
        if (activeForm != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_NAME, result);
            activeForm.closeForm(resultIntent);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void closeForm(Intent resultIntent) {
        if (resultIntent != null) {
            setResult(-1, resultIntent);
        }
        finish();
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
    }

    public static void finishApplication() {
        if (activeForm != null) {
            activeForm.closeApplicationFromBlocks();
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void closeApplicationFromBlocks() {
        closeApplication();
    }

    private void closeApplicationFromMenu() {
        closeApplication();
    }

    private void closeApplication() {
        applicationIsBeingClosed = true;
        finish();
        if (this.formName.equals("Screen1")) {
            System.exit(0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        addExitButtonToMenu(menu);
        addAboutInfoToMenu(menu);
        for (OnCreateOptionsMenuListener onCreateOptionsMenuListener : this.onCreateOptionsMenuListeners) {
            onCreateOptionsMenuListener.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public void addExitButtonToMenu(Menu menu) {
        menu.add(0, 0, 1, "Stop this application").setOnMenuItemClickListener(new C02028()).setIcon(17301594);
    }

    public void addAboutInfoToMenu(Menu menu) {
        menu.add(0, 0, 2, "About this application").setOnMenuItemClickListener(new C02039()).setIcon(17301651);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        for (OnOptionsItemSelectedListener onOptionsItemSelectedListener : this.onOptionsItemSelectedListeners) {
            if (onOptionsItemSelectedListener.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    private void showExitApplicationNotification() {
        Runnable stopApplication = new Runnable() {
            public void run() {
                Form.this.closeApplicationFromMenu();
            }
        };
        Runnable doNothing = new Runnable() {
            public void run() {
            }
        };
        Notifier.twoButtonDialog(this, "Stop this application and exit? You'll need to relaunch the application to use it again.", "Stop application?", "Stop and exit", "Don't stop", false, stopApplication, doNothing, doNothing);
    }

    void setYandexTranslateTagline() {
        this.yandexTranslateTagline = "<p><small>Language translation powered by Yandex.Translate</small></p>";
    }

    private void showAboutApplicationNotification() {
        Notifier.oneButtonAlert(this, (this.aboutScreen + "<p><small><em>Invented with MIT App Inventor<br>appinventor.mit.edu</em></small></p>" + this.yandexTranslateTagline).replaceAll("\\n", "<br>"), "About this app", "Got it");
    }

    public void clear() {
        Log.d(LOG_TAG, "Form " + this.formName + " clear called");
        this.viewLayout.getLayoutManager().removeAllViews();
        if (this.frameLayout != null) {
            this.frameLayout.removeAllViews();
            this.frameLayout = null;
        }
        defaultPropertyValues();
        this.onStopListeners.clear();
        this.onNewIntentListeners.clear();
        this.onResumeListeners.clear();
        this.onPauseListeners.clear();
        this.onDestroyListeners.clear();
        this.onInitializeListeners.clear();
        this.onCreateOptionsMenuListeners.clear();
        this.onOptionsItemSelectedListeners.clear();
        this.screenInitialized = false;
        for (OnClearListener onClearListener : this.onClearListeners) {
            onClearListener.onClear();
        }
        this.onClearListeners.clear();
        System.err.println("Form.clear() About to do moby GC!");
        System.gc();
        this.dimChanges.clear();
    }

    public void deleteComponent(Object component) {
        if (component instanceof OnStopListener) {
            OnStopListener onStopListener = (OnStopListener) component;
            if (this.onStopListeners.contains(onStopListener)) {
                this.onStopListeners.remove(onStopListener);
            }
        }
        if (component instanceof OnNewIntentListener) {
            OnNewIntentListener onNewIntentListener = (OnNewIntentListener) component;
            if (this.onNewIntentListeners.contains(onNewIntentListener)) {
                this.onNewIntentListeners.remove(onNewIntentListener);
            }
        }
        if (component instanceof OnResumeListener) {
            OnResumeListener onResumeListener = (OnResumeListener) component;
            if (this.onResumeListeners.contains(onResumeListener)) {
                this.onResumeListeners.remove(onResumeListener);
            }
        }
        if (component instanceof OnPauseListener) {
            OnPauseListener onPauseListener = (OnPauseListener) component;
            if (this.onPauseListeners.contains(onPauseListener)) {
                this.onPauseListeners.remove(onPauseListener);
            }
        }
        if (component instanceof OnDestroyListener) {
            OnDestroyListener onDestroyListener = (OnDestroyListener) component;
            if (this.onDestroyListeners.contains(onDestroyListener)) {
                this.onDestroyListeners.remove(onDestroyListener);
            }
        }
        if (component instanceof OnInitializeListener) {
            OnInitializeListener onInitializeListener = (OnInitializeListener) component;
            if (this.onInitializeListeners.contains(onInitializeListener)) {
                this.onInitializeListeners.remove(onInitializeListener);
            }
        }
        if (component instanceof OnCreateOptionsMenuListener) {
            OnCreateOptionsMenuListener onCreateOptionsMenuListener = (OnCreateOptionsMenuListener) component;
            if (this.onCreateOptionsMenuListeners.contains(onCreateOptionsMenuListener)) {
                this.onCreateOptionsMenuListeners.remove(onCreateOptionsMenuListener);
            }
        }
        if (component instanceof OnOptionsItemSelectedListener) {
            OnOptionsItemSelectedListener onOptionsItemSelectedListener = (OnOptionsItemSelectedListener) component;
            if (this.onOptionsItemSelectedListeners.contains(onOptionsItemSelectedListener)) {
                this.onOptionsItemSelectedListeners.remove(onOptionsItemSelectedListener);
            }
        }
        if (component instanceof Deleteable) {
            ((Deleteable) component).onDelete();
        }
    }

    public void dontGrabTouchEventsForComponent() {
        this.frameLayout.requestDisallowInterceptTouchEvent(true);
    }

    protected boolean toastAllowed() {
        long now = System.nanoTime();
        if (now <= this.lastToastTime + minimumToastWait) {
            return false;
        }
        this.lastToastTime = now;
        return true;
    }

    public void callInitialize(Object component) throws Throwable {
        try {
            Method method = component.getClass().getMethod("Initialize", (Class[]) null);
            try {
                Log.i(LOG_TAG, "calling Initialize method for Object " + component.toString());
                method.invoke(component, (Object[]) null);
            } catch (InvocationTargetException e) {
                Log.i(LOG_TAG, "invoke exception: " + e.getMessage());
                throw e.getTargetException();
            }
        } catch (SecurityException e2) {
            Log.i(LOG_TAG, "Security exception " + e2.getMessage());
        } catch (NoSuchMethodException e3) {
        }
    }

    public synchronized Bundle fullScreenVideoAction(int action, VideoPlayer source, Object data) {
        return this.fullScreenVideoUtil.performAction(action, source, data);
    }

    private void setBackground(View bgview) {
        int i = -1;
        Drawable setDraw = this.backgroundDrawable;
        if (this.backgroundImagePath == "" || setDraw == null) {
            if (this.backgroundColor != 0) {
                i = this.backgroundColor;
            }
            setDraw = new ColorDrawable(i);
        } else {
            setDraw = this.backgroundDrawable.getConstantState().newDrawable();
            if (this.backgroundColor != 0) {
                i = this.backgroundColor;
            }
            setDraw.setColorFilter(i, Mode.DST_OVER);
        }
        ViewUtil.setBackgroundImage(bgview, setDraw);
        bgview.invalidate();
    }

    public static boolean getCompatibilityMode() {
        return sCompatibilityMode;
    }

    @SimpleFunction(description = "Hide the onscreen soft keyboard.")
    public void HideKeyboard() {
        View view = getCurrentFocus();
        if (view == null) {
            view = this.frameLayout;
        }
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void updateTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.title);
        }
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void maybeShowTitleBar() {
        if (this.showTitle) {
            super.maybeShowTitleBar();
        } else {
            super.hideTitleBar();
        }
    }

    public boolean isDarkTheme() {
        return this.usesDarkTheme;
    }
}
