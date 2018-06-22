package com.google.appinventor.components.annotations;

import com.google.appinventor.components.annotations.androidmanifest.ReceiverElement;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsesBroadcastReceivers {
    ReceiverElement[] receivers();
}
