package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimplePropertyCopier;
import com.google.appinventor.components.runtime.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyUtil {
    public static Component copyComponentProperties(Component source, Component target) throws Throwable {
        if (source.getClass().equals(target.getClass())) {
            Class componentClass = source.getClass();
            for (Method componentMethod : componentClass.getMethods()) {
                if (componentMethod.isAnnotationPresent(SimpleProperty.class) && componentMethod.getParameterTypes().length == 1) {
                    Method propertySetterMethod = componentMethod;
                    try {
                        String propertyName = propertySetterMethod.getName();
                        Method propertyCopierMethod = getPropertyCopierMethod("Copy" + propertyName, componentClass);
                        if (propertyCopierMethod != null) {
                            propertyCopierMethod.invoke(target, new Object[]{source});
                        } else {
                            Method propertyGetterMethod = componentClass.getMethod(propertyName, new Class[0]);
                            Class propertySetterParameterType = propertySetterMethod.getParameterTypes()[0];
                            if (propertyGetterMethod.isAnnotationPresent(SimpleProperty.class) && propertySetterParameterType.isAssignableFrom(propertyGetterMethod.getReturnType())) {
                                Object propertyValue = propertyGetterMethod.invoke(source, new Object[0]);
                                propertySetterMethod.invoke(target, new Object[]{propertyValue});
                            }
                        }
                    } catch (NoSuchMethodException e) {
                    } catch (InvocationTargetException e2) {
                        throw e2.getCause();
                    }
                }
            }
            return target;
        }
        throw new IllegalArgumentException("Source and target classes must be identical");
    }

    private static Method getPropertyCopierMethod(String copierMethodName, Class componentClass) {
        do {
            try {
                Method propertyCopierMethod = componentClass.getMethod(copierMethodName, new Class[]{componentClass});
                if (propertyCopierMethod.isAnnotationPresent(SimplePropertyCopier.class)) {
                    return propertyCopierMethod;
                }
            } catch (NoSuchMethodException e) {
            }
            componentClass = componentClass.getSuperclass();
        } while (componentClass != null);
        return null;
    }
}
