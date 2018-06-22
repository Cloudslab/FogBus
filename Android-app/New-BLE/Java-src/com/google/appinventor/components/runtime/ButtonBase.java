package com.google.appinventor.components.runtime;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.runtime.util.IceCreamSandwichUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;

@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public abstract class ButtonBase extends AndroidViewComponent implements OnClickListener, OnFocusChangeListener, OnLongClickListener, OnTouchListener {
    private static final String LOG_TAG = "ButtonBase";
    private static final float[] ROUNDED_CORNERS_ARRAY = new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f};
    private static final float ROUNDED_CORNERS_RADIUS = 10.0f;
    private static final int SHAPED_DEFAULT_BACKGROUND_COLOR = -3355444;
    private int backgroundColor;
    private Drawable backgroundImageDrawable;
    private boolean bold;
    private Drawable defaultButtonDrawable;
    private ColorStateList defaultColorStateList;
    private int fontTypeface;
    private String imagePath = "";
    private boolean italic;
    private int shape;
    private boolean showFeedback = true;
    private int textAlignment;
    private int textColor;
    private final Button view;

    public abstract void click();

    public ButtonBase(ComponentContainer container) {
        super(container);
        this.view = new Button(container.$context());
        this.defaultButtonDrawable = this.view.getBackground();
        this.defaultColorStateList = this.view.getTextColors();
        container.$add(this);
        this.view.setOnClickListener(this);
        this.view.setOnFocusChangeListener(this);
        this.view.setOnLongClickListener(this);
        this.view.setOnTouchListener(this);
        IceCreamSandwichUtil.setAllCaps(this.view, false);
        TextAlignment(1);
        BackgroundColor(0);
        Image("");
        Enabled(true);
        this.fontTypeface = 0;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
        FontSize(Component.FONT_DEFAULT_SIZE);
        Text("");
        TextColor(0);
        Shape(0);
    }

    public boolean onTouch(View view, MotionEvent me) {
        if (me.getAction() == 0) {
            if (ShowFeedback()) {
                view.getBackground().setAlpha(70);
                view.invalidate();
            }
            TouchDown();
        } else if (me.getAction() == 1 || me.getAction() == 3) {
            if (ShowFeedback()) {
                view.getBackground().setAlpha(255);
                view.invalidate();
            }
            TouchUp();
        }
        return false;
    }

    public View getView() {
        return this.view;
    }

    @SimpleEvent(description = "Indicates that the button was pressed down.")
    public void TouchDown() {
        EventDispatcher.dispatchEvent(this, "TouchDown", new Object[0]);
    }

    @SimpleEvent(description = "Indicates that a button has been released.")
    public void TouchUp() {
        EventDispatcher.dispatchEvent(this, "TouchUp", new Object[0]);
    }

    @SimpleEvent(description = "Indicates the cursor moved over the button so it is now possible to click it.")
    public void GotFocus() {
        EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
    }

    @SimpleEvent(description = "Indicates the cursor moved away from the button so it is now no longer possible to click it.")
    public void LostFocus() {
        EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Left, center, or right.", userVisible = false)
    public int TextAlignment() {
        return this.textAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "textalignment")
    @SimpleProperty(userVisible = false)
    public void TextAlignment(int alignment) {
        this.textAlignment = alignment;
        TextViewUtil.setAlignment(this.view, alignment, true);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public int Shape() {
        return this.shape;
    }

    @DesignerProperty(defaultValue = "0", editorType = "button_shape")
    @SimpleProperty(description = "Specifies the button's shape (default, rounded, rectangular, oval). The shape will not be visible if an Image is being displayed.", userVisible = false)
    public void Shape(int shape) {
        this.shape = shape;
        updateAppearance();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Image to display on button.")
    public String Image() {
        return this.imagePath;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty(description = "Specifies the path of the button's image.  If there is both an Image and a BackgroundColor, only the Image will be visible.")
    public void Image(String path) {
        if (!path.equals(this.imagePath) || this.backgroundImageDrawable == null) {
            if (path == null) {
                path = "";
            }
            this.imagePath = path;
            this.backgroundImageDrawable = null;
            if (this.imagePath.length() > 0) {
                try {
                    this.backgroundImageDrawable = MediaUtil.getBitmapDrawable(this.container.$form(), this.imagePath);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Unable to load " + this.imagePath);
                }
            }
            updateAppearance();
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the button's background color")
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty(description = "Specifies the button's background color. The background color will not be visible if an Image is being displayed.")
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        updateAppearance();
    }

    private void updateAppearance() {
        if (this.backgroundImageDrawable != null) {
            ViewUtil.setBackgroundImage(this.view, this.backgroundImageDrawable);
        } else if (this.shape != 0) {
            setShape();
        } else if (this.backgroundColor == 0) {
            ViewUtil.setBackgroundDrawable(this.view, this.defaultButtonDrawable);
        } else {
            ViewUtil.setBackgroundDrawable(this.view, null);
            TextViewUtil.setBackgroundColor(this.view, this.backgroundColor);
        }
    }

    private void setShape() {
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.getPaint().setColor(this.backgroundColor == 0 ? -3355444 : this.backgroundColor);
        switch (this.shape) {
            case 1:
                drawable.setShape(new RoundRectShape(ROUNDED_CORNERS_ARRAY, null, null));
                break;
            case 2:
                drawable.setShape(new RectShape());
                break;
            case 3:
                drawable.setShape(new OvalShape());
                break;
            default:
                throw new IllegalArgumentException();
        }
        this.view.setBackgroundDrawable(drawable);
        this.view.invalidate();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If set, user can tap check box to cause action.")
    public boolean Enabled() {
        return TextViewUtil.isEnabled(this.view);
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void Enabled(boolean enabled) {
        TextViewUtil.setEnabled(this.view, enabled);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If set, button text is displayed in bold.")
    public boolean FontBold() {
        return this.bold;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void FontBold(boolean bold) {
        this.bold = bold;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, bold, this.italic);
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(description = "Specifies if a visual feedback should be shown  for a button that as an image as background.")
    public void ShowFeedback(boolean showFeedback) {
        this.showFeedback = showFeedback;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the button's visual feedback state")
    public boolean ShowFeedback() {
        return this.showFeedback;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If set, button text is displayed in italics.")
    public boolean FontItalic() {
        return this.italic;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void FontItalic(boolean italic) {
        this.italic = italic;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Point size for button text.")
    public float FontSize() {
        return TextViewUtil.getFontSize(this.view, this.container.$context());
    }

    @DesignerProperty(defaultValue = "14.0", editorType = "non_negative_float")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void FontSize(float size) {
        TextViewUtil.setFontSize(this.view, size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Font family for button text.", userVisible = false)
    public int FontTypeface() {
        return this.fontTypeface;
    }

    @DesignerProperty(defaultValue = "0", editorType = "typeface")
    @SimpleProperty(userVisible = false)
    public void FontTypeface(int typeface) {
        this.fontTypeface = typeface;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text to display on button.")
    public String Text() {
        return TextViewUtil.getText(this.view);
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Text(String text) {
        TextViewUtil.setText(this.view, text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Color for button text.")
    public int TextColor() {
        return this.textColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty
    public void TextColor(int argb) {
        this.textColor = argb;
        if (argb != 0) {
            TextViewUtil.setTextColor(this.view, argb);
        } else {
            TextViewUtil.setTextColors(this.view, this.defaultColorStateList);
        }
    }

    public boolean longClick() {
        return false;
    }

    public void onClick(View view) {
        click();
    }

    public void onFocusChange(View previouslyFocused, boolean gainFocus) {
        if (gainFocus) {
            GotFocus();
        } else {
            LostFocus();
        }
    }

    public boolean onLongClick(View view) {
        return longClick();
    }
}
