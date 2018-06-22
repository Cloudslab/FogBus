package com.google.appinventor.components.annotations.androidmanifest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataElement {
    String host() default "";

    String mimeType() default "";

    String path() default "";

    String pathPattern() default "";

    String pathPrefix() default "";

    String port() default "";

    String scheme() default "";
}
