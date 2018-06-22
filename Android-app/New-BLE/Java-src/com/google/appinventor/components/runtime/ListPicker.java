package com.google.appinventor.components.runtime;

import android.content.Intent;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesActivities;
import com.google.appinventor.components.annotations.androidmanifest.ActivityElement;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.YailList;

@SimpleObject
@UsesActivities(activities = {@ActivityElement(configChanges = "orientation|keyboardHidden", name = "com.google.appinventor.components.runtime.ListPickerActivity", screenOrientation = "behind")})
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "<p>A button that, when clicked on, displays a list of texts for the user to choose among. The texts can be specified through the Designer or Blocks Editor by setting the <code>ElementsFromString</code> property to their string-separated concatenation (for example, <em>choice 1, choice 2, choice 3</em>) or by setting the <code>Elements</code> property to a List in the Blocks editor.</p><p>Setting property ShowFilterBar to true, will make the list searchable.  Other properties affect the appearance of the button (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be clicked on (<code>Enabled</code>).</p>", version = 9)
public class ListPicker extends Picker implements ActivityResultListener, Deleteable, OnResumeListener {
    private static final boolean DEFAULT_ENABLED = false;
    public static final int DEFAULT_ITEM_BACKGROUND_COLOR = -16777216;
    public static final int DEFAULT_ITEM_TEXT_COLOR = -1;
    static final String LIST_ACTIVITY_ANIM_TYPE = (LIST_ACTIVITY_CLASS + ".anim");
    static final String LIST_ACTIVITY_ARG_NAME = (LIST_ACTIVITY_CLASS + ".list");
    static final String LIST_ACTIVITY_BACKGROUND_COLOR = (LIST_ACTIVITY_CLASS + ".backgroundcolor");
    private static final String LIST_ACTIVITY_CLASS = ListPickerActivity.class.getName();
    static final String LIST_ACTIVITY_ITEM_TEXT_COLOR = (LIST_ACTIVITY_CLASS + ".itemtextcolor");
    static final String LIST_ACTIVITY_ORIENTATION_TYPE = (LIST_ACTIVITY_CLASS + ".orientation");
    static final String LIST_ACTIVITY_RESULT_INDEX = (LIST_ACTIVITY_CLASS + ".index");
    static final String LIST_ACTIVITY_RESULT_NAME = (LIST_ACTIVITY_CLASS + ".selection");
    static final String LIST_ACTIVITY_SHOW_SEARCH_BAR = (LIST_ACTIVITY_CLASS + ".search");
    static final String LIST_ACTIVITY_TITLE = (LIST_ACTIVITY_CLASS + ".title");
    private int itemBackgroundColor;
    private int itemTextColor;
    private YailList items = new YailList();
    private boolean resumedFromListFlag = false;
    private String selection;
    private int selectionIndex;
    private boolean showFilter = false;
    private String title = "";

    public ListPicker(ComponentContainer container) {
        super(container);
        SelectionIndex(0);
        this.itemTextColor = -1;
        this.itemBackgroundColor = -16777216;
        container.$form().registerForOnResume(this);
    }

    public void onResume() {
        if (this.resumedFromListFlag) {
            this.container.$form().getWindow().setSoftInputMode(3);
            this.resumedFromListFlag = false;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The selected item.  When directly changed by the programmer, the SelectionIndex property is also changed to the first item in the ListPicker with the given value.  If the value does not appear, SelectionIndex will be set to 0.")
    public String Selection() {
        return this.selection;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Selection(String value) {
        this.selection = value;
        this.selectionIndex = ElementsUtil.setSelectedIndexFromValue(value, this.items);
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void ShowFilterBar(boolean showFilter) {
        this.showFilter = showFilter;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns current state of ShowFilterBar indicating if Search Filter Bar will be displayed on ListPicker or not")
    public boolean ShowFilterBar() {
        return this.showFilter;
    }

    @DesignerProperty(defaultValue = "&HFFFFFFFF", editorType = "color")
    @SimpleProperty
    public void ItemTextColor(int argb) {
        this.itemTextColor = argb;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The text color of the ListPicker items.")
    public int ItemTextColor() {
        return this.itemTextColor;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void ItemBackgroundColor(int argb) {
        this.itemBackgroundColor = argb;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The background color of the ListPicker items.")
    public int ItemBackgroundColor() {
        return this.itemBackgroundColor;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The index of the currently selected item, starting at 1.  If no item is selected, the value will be 0.  If an attempt is made to set this to a number less than 1 or greater than the number of items in the ListPicker, SelectionIndex will be set to 0, and Selection will be set to the empty text.")
    public int SelectionIndex() {
        return this.selectionIndex;
    }

    @SimpleProperty
    public void SelectionIndex(int index) {
        this.selectionIndex = ElementsUtil.selectionIndex(index, this.items);
        this.selection = ElementsUtil.setSelectionFromIndex(index, this.items);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public YailList Elements() {
        return this.items;
    }

    @SimpleProperty
    public void Elements(YailList itemList) {
        this.items = ElementsUtil.elements(itemList, "ListPicker");
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void ElementsFromString(String itemstring) {
        this.items = ElementsUtil.elementsFromString(itemstring);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Optional title displayed at the top of the list of choices.")
    public String Title() {
        return this.title;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Title(String title) {
        this.title = title;
    }

    public Intent getIntent() {
        Intent intent = new Intent();
        intent.setClassName(this.container.$context(), LIST_ACTIVITY_CLASS);
        intent.putExtra(LIST_ACTIVITY_ARG_NAME, this.items.toStringArray());
        intent.putExtra(LIST_ACTIVITY_SHOW_SEARCH_BAR, String.valueOf(this.showFilter));
        if (!this.title.equals("")) {
            intent.putExtra(LIST_ACTIVITY_TITLE, this.title);
        }
        intent.putExtra(LIST_ACTIVITY_ANIM_TYPE, this.container.$form().getOpenAnimType());
        intent.putExtra(LIST_ACTIVITY_ORIENTATION_TYPE, this.container.$form().ScreenOrientation());
        intent.putExtra(LIST_ACTIVITY_ITEM_TEXT_COLOR, this.itemTextColor);
        intent.putExtra(LIST_ACTIVITY_BACKGROUND_COLOR, this.itemBackgroundColor);
        return intent;
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            if (data.hasExtra(LIST_ACTIVITY_RESULT_NAME)) {
                this.selection = data.getStringExtra(LIST_ACTIVITY_RESULT_NAME);
            } else {
                this.selection = "";
            }
            this.selectionIndex = data.getIntExtra(LIST_ACTIVITY_RESULT_INDEX, 0);
            AfterPicking();
            this.resumedFromListFlag = true;
        }
    }

    public void onDelete() {
        this.container.$form().unregisterForActivityResult(this);
    }
}
