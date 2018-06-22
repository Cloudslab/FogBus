package com.google.appinventor.components.runtime;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.TextViewUtil;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "A Label displays a piece of text, which is specified through the <code>Text</code> property.  Other properties, all of which can be set in the Designer or Blocks Editor, control the appearance and placement of the text.", version = 4)
public final class Label extends AndroidViewComponent {
    private static final int DEFAULT_LABEL_MARGIN = 2;
    private int backgroundColor;
    private boolean bold;
    private int defaultLabelMarginInDp = 0;
    private int fontTypeface;
    private boolean hasMargins;
    private boolean htmlFormat;
    private boolean italic;
    private final LayoutParams linearLayoutParams;
    private int textAlignment;
    private int textColor;
    private final TextView view;

    public Label(ComponentContainer container) {
        super(container);
        this.view = new TextView(container.$context());
        container.$add(this);
        ViewGroup.LayoutParams lp = this.view.getLayoutParams();
        if (lp instanceof LayoutParams) {
            this.linearLayoutParams = (LayoutParams) lp;
            this.defaultLabelMarginInDp = dpToPx(this.view, 2);
        } else {
            this.defaultLabelMarginInDp = 0;
            this.linearLayoutParams = null;
            Log.e("Label", "Error: The label's view does not have linear layout parameters");
            new RuntimeException().printStackTrace();
        }
        TextAlignment(0);
        BackgroundColor(16777215);
        this.fontTypeface = 0;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
        FontSize(Component.FONT_DEFAULT_SIZE);
        Text("");
        TextColor(0);
        HTMLFormat(false);
        HasMargins(true);
    }

    private static int dpToPx(View view, int dp) {
        return Math.round(((float) dp) * view.getContext().getResources().getDisplayMetrics().density);
    }

    public View getView() {
        return this.view;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public int TextAlignment() {
        return this.textAlignment;
    }

    @DesignerProperty(defaultValue = "0", editorType = "textalignment")
    @SimpleProperty(userVisible = false)
    public void TextAlignment(int alignment) {
        this.textAlignment = alignment;
        TextViewUtil.setAlignment(this.view, alignment, false);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&H00FFFFFF", editorType = "color")
    @SimpleProperty
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        if (argb != 0) {
            TextViewUtil.setBackgroundColor(this.view, argb);
        } else {
            TextViewUtil.setBackgroundColor(this.view, 16777215);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public boolean FontBold() {
        return this.bold;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(userVisible = false)
    public void FontBold(boolean bold) {
        this.bold = bold;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public boolean FontItalic() {
        return this.italic;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(userVisible = false)
    public void FontItalic(boolean italic) {
        this.italic = italic;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Reports whether or not the label appears with margins.  All four margins (left, right, top, bottom) are the same.  This property has no effect in the designer, where labels are always shown with margins.", userVisible = true)
    public boolean HasMargins() {
        return this.hasMargins;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(userVisible = true)
    public void HasMargins(boolean hasMargins) {
        this.hasMargins = hasMargins;
        setLabelMargins(hasMargins);
    }

    private void setLabelMargins(boolean hasMargins) {
        int m = hasMargins ? this.defaultLabelMarginInDp : 0;
        this.linearLayoutParams.setMargins(m, m, m, m);
        this.view.invalidate();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float FontSize() {
        return TextViewUtil.getFontSize(this.view, this.container.$context());
    }

    @DesignerProperty(defaultValue = "14.0", editorType = "non_negative_float")
    @SimpleProperty
    public void FontSize(float size) {
        TextViewUtil.setFontSize(this.view, size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public int FontTypeface() {
        return this.fontTypeface;
    }

    @DesignerProperty(defaultValue = "0", editorType = "typeface")
    @SimpleProperty(userVisible = false)
    public void FontTypeface(int typeface) {
        this.fontTypeface = typeface;
        TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Text() {
        return TextViewUtil.getText(this.view);
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Text(String text) {
        if (this.htmlFormat) {
            TextViewUtil.setTextHTML(this.view, text);
        } else {
            TextViewUtil.setText(this.view, text);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If true, then this label will show html text else it will show plain text. Note: Not all HTML is supported.")
    public boolean HTMLFormat() {
        return this.htmlFormat;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(userVisible = false)
    public void HTMLFormat(boolean fmt) {
        this.htmlFormat = fmt;
        if (this.htmlFormat) {
            TextViewUtil.setTextHTML(this.view, TextViewUtil.getText(this.view));
            return;
        }
        TextViewUtil.setText(this.view, TextViewUtil.getText(this.view));
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int TextColor() {
        return this.textColor;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void TextColor(int argb) {
        this.textColor = argb;
        if (argb != 0) {
            TextViewUtil.setTextColor(this.view, argb);
        } else {
            TextViewUtil.setTextColor(this.view, this.container.$form().isDarkTheme() ? -1 : -16777216);
        }
    }
}
