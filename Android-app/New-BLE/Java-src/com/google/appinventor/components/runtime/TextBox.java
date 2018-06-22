package com.google.appinventor.components.runtime;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "<p>A box for the user to enter text.  The initial or user-entered text value is in the <code>Text</code> property.  If blank, the <code>Hint</code> property, which appears as faint text in the box, can provide the user with guidance as to what to type.</p><p>The <code>MultiLine</code> property determines if the text can havemore than one line.  For a single line text box, the keyboard will closeautomatically when the user presses the Done key.  To close the keyboard for multiline text boxes, the app should use  the HideKeyboard method or  rely on the user to press the Back key.</p><p>The <code> NumbersOnly</code> property restricts the keyboard to acceptnumeric input only.</p><p>Other properties affect the appearance of the text box (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be used (<code>Enabled</code>).</p><p>Text boxes are usually used with the <code>Button</code> component, with the user clicking on the button when text entry is complete.</p><p>If the text entered by the user should not be displayed, use <code>PasswordTextBox</code> instead.</p>", version = 5)
public final class TextBox extends TextBoxBase {
    private boolean acceptsNumbersOnly;
    private boolean multiLine;

    public TextBox(ComponentContainer container) {
        super(container, new EditText(container.$context()));
        NumbersOnly(false);
        MultiLine(false);
        this.view.setImeOptions(6);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true, then this text box accepts only numbers as keyboard input.  Numbers can include a decimal point and an optional leading minus sign.  This applies to keyboard input only.  Even if NumbersOnly is true, you can use [set Text to] to enter any text at all.")
    public boolean NumbersOnly() {
        return this.acceptsNumbersOnly;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(description = "If true, then this text box accepts only numbers as keyboard input.  Numbers can include a decimal point and an optional leading minus sign.  This applies to keyboard input only.  Even if NumbersOnly is true, you can use [set Text to] to enter any text at all.")
    public void NumbersOnly(boolean acceptsNumbersOnly) {
        if (acceptsNumbersOnly) {
            this.view.setInputType(12290);
        } else {
            this.view.setInputType(131073);
        }
        this.acceptsNumbersOnly = acceptsNumbersOnly;
    }

    @SimpleFunction(description = "Hide the keyboard.  Only multiline text boxes need this. Single line text boxes close the keyboard when the users presses the Done key.")
    public void HideKeyboard() {
        ((InputMethodManager) this.container.$context().getSystemService("input_method")).hideSoftInputFromWindow(this.view.getWindowToken(), 0);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true, then this text box accepts multiple lines of input, which are entered using the return key.  For single line text boxes there is a Done key instead of a return key, and pressing Done hides the keyboard.  The app should call the HideKeyboard method to hide the keyboard for a mutiline text box.")
    public boolean MultiLine() {
        return this.multiLine;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void MultiLine(boolean multiLine) {
        this.multiLine = multiLine;
        this.view.setSingleLine(!multiLine);
    }
}
