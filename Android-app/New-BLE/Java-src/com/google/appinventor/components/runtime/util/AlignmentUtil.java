package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.LinearLayout;

public class AlignmentUtil {
    LinearLayout viewLayout;

    public AlignmentUtil(LinearLayout viewLayout) {
        this.viewLayout = viewLayout;
    }

    public void setHorizontalAlignment(int alignment) throws IllegalArgumentException {
        switch (alignment) {
            case 1:
                this.viewLayout.setHorizontalGravity(3);
                return;
            case 2:
                this.viewLayout.setHorizontalGravity(5);
                return;
            case 3:
                this.viewLayout.setHorizontalGravity(1);
                return;
            default:
                throw new IllegalArgumentException("Bad value to setHorizontalAlignment: " + alignment);
        }
    }

    public void setVerticalAlignment(int alignment) throws IllegalArgumentException {
        switch (alignment) {
            case 1:
                this.viewLayout.setVerticalGravity(48);
                return;
            case 2:
                this.viewLayout.setVerticalGravity(16);
                return;
            case 3:
                this.viewLayout.setVerticalGravity(80);
                return;
            default:
                throw new IllegalArgumentException("Bad value to setVerticalAlignment: " + alignment);
        }
    }
}
