package com.google.appinventor.components.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleBroadcastReceiver {
    String actions() default "";

    String className() default "";
}
