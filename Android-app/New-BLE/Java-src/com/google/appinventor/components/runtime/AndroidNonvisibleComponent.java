package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public abstract class AndroidNonvisibleComponent implements Component {
    protected final Form form;

    protected AndroidNonvisibleComponent(Form form) {
        this.form = form;
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.form;
    }
}
