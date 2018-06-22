package android.support.v7.internal.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.appcompat.C0111R;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.util.Map;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private final Object[] mConstructorArgs = new Object[2];

    public final View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs, boolean inheritContext, boolean readAndroidTheme, boolean readAppTheme) {
        Context originalContext = context;
        if (inheritContext && parent != null) {
            context = parent.getContext();
        }
        if (readAndroidTheme || readAppTheme) {
            context = themifyContext(context, attrs, readAndroidTheme, readAppTheme);
        }
        Object obj = -1;
        switch (name.hashCode()) {
            case -1946472170:
                if (name.equals("RatingBar")) {
                    obj = 7;
                    break;
                }
                break;
            case -1455429095:
                if (name.equals("CheckedTextView")) {
                    obj = 4;
                    break;
                }
                break;
            case -1346021293:
                if (name.equals("MultiAutoCompleteTextView")) {
                    obj = 6;
                    break;
                }
                break;
            case -938935918:
                if (name.equals("TextView")) {
                    obj = 9;
                    break;
                }
                break;
            case -339785223:
                if (name.equals("Spinner")) {
                    obj = 1;
                    break;
                }
                break;
            case 776382189:
                if (name.equals("RadioButton")) {
                    obj = 3;
                    break;
                }
                break;
            case 1413872058:
                if (name.equals("AutoCompleteTextView")) {
                    obj = 5;
                    break;
                }
                break;
            case 1601505219:
                if (name.equals("CheckBox")) {
                    obj = 2;
                    break;
                }
                break;
            case 1666676343:
                if (name.equals("EditText")) {
                    obj = null;
                    break;
                }
                break;
            case 2001146706:
                if (name.equals("Button")) {
                    obj = 8;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                return new AppCompatEditText(context, attrs);
            case 1:
                return new AppCompatSpinner(context, attrs);
            case 2:
                return new AppCompatCheckBox(context, attrs);
            case 3:
                return new AppCompatRadioButton(context, attrs);
            case 4:
                return new AppCompatCheckedTextView(context, attrs);
            case 5:
                return new AppCompatAutoCompleteTextView(context, attrs);
            case 6:
                return new AppCompatMultiAutoCompleteTextView(context, attrs);
            case 7:
                return new AppCompatRatingBar(context, attrs);
            case 8:
                return new AppCompatButton(context, attrs);
            case 9:
                return new AppCompatTextView(context, attrs);
            default:
                if (originalContext != context) {
                    return createViewFromTag(context, name, attrs);
                }
                return null;
        }
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            View createView;
            if (-1 == name.indexOf(46)) {
                createView = createView(context, name, "android.widget.");
                return createView;
            }
            createView = createView(context, name, null);
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return createView;
        } catch (Exception e) {
            return null;
        } finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix) throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = (Constructor) sConstructorMap.get(name);
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(prefix != null ? prefix + name : name).asSubclass(View.class).getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0111R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(C0111R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0) {
            themeId = a.getResourceId(C0111R.styleable.View_theme, 0);
            if (themeId != 0) {
                Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
            }
        }
        a.recycle();
        if (themeId == 0) {
            return context;
        }
        if ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == themeId) {
            return context;
        }
        return new ContextThemeWrapper(context, themeId);
    }
}
