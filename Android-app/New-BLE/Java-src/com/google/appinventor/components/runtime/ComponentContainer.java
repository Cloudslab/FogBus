package com.google.appinventor.components.runtime;

import android.app.Activity;

public interface ComponentContainer {
    void $add(AndroidViewComponent androidViewComponent);

    Activity $context();

    Form $form();

    int Height();

    int Width();

    void setChildHeight(AndroidViewComponent androidViewComponent, int i);

    void setChildWidth(AndroidViewComponent androidViewComponent, int i);
}
