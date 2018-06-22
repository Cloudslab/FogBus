package com.google.appinventor.components.runtime;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import com.google.appinventor.components.runtime.util.YailList;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "<p>A spinner component that displays a pop-up with a list of elements. These elements can be set in the Designer or Blocks Editor by setting the<code>ElementsFromString</code> property to a string-separated concatenation (for example, <em>choice 1, choice 2, choice 3</em>) or by setting the <code>Elements</code> property to a List in the Blocks editor. Spinners are created with the first item already selected. So selecting  it does not generate an After Picking event. Consequently it's useful to make the  first Spinner item be a non-choice like \"Select from below...\". </p>", iconName = "images/spinner.png", nonVisible = false, version = 1)
public final class Spinner extends AndroidViewComponent implements OnItemSelectedListener {
    private ArrayAdapter<String> adapter;
    private YailList items = new YailList();
    private int oldAdapterCount;
    private int oldSelectionIndex;
    private final android.widget.Spinner view;

    public Spinner(ComponentContainer container) {
        super(container);
        if (VERSION.SDK_INT < 11) {
            this.view = new android.widget.Spinner(container.$context());
        } else {
            this.view = HoneycombUtil.makeSpinner(container.$context());
        }
        this.adapter = new ArrayAdapter(container.$context(), 17367048);
        this.adapter.setDropDownViewResource(17367058);
        this.view.setAdapter(this.adapter);
        this.view.setOnItemSelectedListener(this);
        container.$add(this);
        Prompt("");
        this.oldSelectionIndex = SelectionIndex();
    }

    public View getView() {
        return this.view;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the current selected item in the spinner ")
    public String Selection() {
        return SelectionIndex() == 0 ? "" : (String) this.view.getItemAtPosition(SelectionIndex() - 1);
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the selected item in the spinner")
    public void Selection(String value) {
        SelectionIndex(ElementsUtil.setSelectedIndexFromValue(value, this.items));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The index of the currently selected item, starting at 1. If no item is selected, the value will be 0.")
    public int SelectionIndex() {
        return ElementsUtil.selectionIndex(this.view.getSelectedItemPosition() + 1, this.items);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the spinner selection to the element at the given index.If an attempt is made to set this to a number less than 1 or greater than the number of items in the Spinner, SelectionIndex will be set to 0, and Selection will be set to empty.")
    public void SelectionIndex(int index) {
        this.oldSelectionIndex = SelectionIndex();
        this.view.setSelection(ElementsUtil.selectionIndex(index, this.items) - 1);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "returns a list of text elements to be picked from.")
    public YailList Elements() {
        return this.items;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "adds the passed text element to the Spinner list")
    public void Elements(YailList itemList) {
        if (itemList.size() == 0) {
            SelectionIndex(0);
        } else if (itemList.size() < this.items.size() && SelectionIndex() == this.items.size()) {
            SelectionIndex(itemList.size());
        }
        this.items = ElementsUtil.elements(itemList, "Spinner");
        setAdapterData(itemList.toStringArray());
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "sets the Spinner list to the elements passed in the comma-separated string")
    public void ElementsFromString(String itemstring) {
        Elements(ElementsUtil.elementsFromString(itemstring));
    }

    private void setAdapterData(String[] theItems) {
        this.oldAdapterCount = this.adapter.getCount();
        this.adapter.clear();
        for (Object add : theItems) {
            this.adapter.add(add);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text with the current title for the Spinner window")
    public String Prompt() {
        return this.view.getPrompt().toString();
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "sets the Spinner window prompt to the given tittle")
    public void Prompt(String str) {
        this.view.setPrompt(str);
    }

    @SimpleFunction(description = "displays the dropdown list for selection, same action as when the user clicks on the spinner.")
    public void DisplayDropdown() {
        this.view.performClick();
    }

    @SimpleEvent(description = "Event called after the user selects an item from the dropdown list.")
    public void AfterSelecting(String selection) {
        EventDispatcher.dispatchEvent(this, "AfterSelecting", selection);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (!(this.oldAdapterCount == 0 && this.adapter.getCount() > 0 && this.oldSelectionIndex == 0) && (this.oldAdapterCount <= this.adapter.getCount() || this.oldSelectionIndex <= this.adapter.getCount())) {
            SelectionIndex(position + 1);
            AfterSelecting(Selection());
            return;
        }
        SelectionIndex(position + 1);
        this.oldAdapterCount = this.adapter.getCount();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        this.view.setSelection(0);
    }
}
