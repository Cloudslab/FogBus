package com.google.appinventor.components.annotations.androidmanifest;

public @interface IntentFilterElement {
    ActionElement[] actionElements();

    CategoryElement[] categoryElements() default {};

    DataElement[] dataElements() default {};

    String icon() default "";

    String label() default "";

    String priority() default "";
}
