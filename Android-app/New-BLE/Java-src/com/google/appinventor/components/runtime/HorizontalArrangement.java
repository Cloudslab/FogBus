package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.ComponentConstants;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LAYOUT, description = "<p>A formatting element in which to place components that should be displayed from left to right.  If you wish to have components displayed one over another, use <code>VerticalArrangement</code> instead.</p>", version = 3)
public class HorizontalArrangement extends HVArrangement {
    public HorizontalArrangement(ComponentContainer container) {
        super(container, 0, ComponentConstants.NONSCROLLABLE_ARRANGEMENT);
    }
}
