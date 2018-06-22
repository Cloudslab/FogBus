package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.ComponentConstants;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LAYOUT, description = "<p>A formatting element in which to place components that should be displayed one below another.  (The first child component is stored on top, the second beneath it, etc.)  If you wish to have components displayed next to one another, use <code>HorizontalArrangement</code> instead.</p><p> This version is scrollable", version = 1)
public class VerticalScrollArrangement extends HVArrangement {
    public VerticalScrollArrangement(ComponentContainer container) {
        super(container, 1, ComponentConstants.SCROLLABLE_ARRANGEMENT);
    }
}
