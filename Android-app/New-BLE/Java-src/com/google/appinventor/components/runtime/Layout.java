package com.google.appinventor.components.runtime;

import android.view.ViewGroup;

public interface Layout {
    void add(AndroidViewComponent androidViewComponent);

    ViewGroup getLayoutManager();
}
