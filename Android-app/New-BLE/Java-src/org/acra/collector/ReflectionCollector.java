package org.acra.collector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class ReflectionCollector {
    ReflectionCollector() {
    }

    public static String collectConstants(Class<?> someClass) {
        StringBuilder result = new StringBuilder();
        for (Field field : someClass.getFields()) {
            result.append(field.getName()).append("=");
            try {
                result.append(field.get(null).toString());
            } catch (IllegalArgumentException e) {
                result.append("N/A");
            } catch (IllegalAccessException e2) {
                result.append("N/A");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static String collectStaticGettersResults(Class<?> someClass) {
        StringBuilder result = new StringBuilder();
        for (Method method : someClass.getMethods()) {
            if (method.getParameterTypes().length == 0 && ((method.getName().startsWith("get") || method.getName().startsWith("is")) && !method.getName().equals("getClass"))) {
                try {
                    result.append(method.getName());
                    result.append('=');
                    result.append(method.invoke(null, (Object[]) null));
                    result.append("\n");
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e2) {
                } catch (InvocationTargetException e3) {
                }
            }
        }
        return result.toString();
    }
}
