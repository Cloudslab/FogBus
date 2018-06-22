package gnu.bytecode;

import android.support.v4.media.TransportMediator;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class AnnotationEntry implements Annotation {
    ClassType annotationType;
    LinkedHashMap<String, Object> elementsValue = new LinkedHashMap(10);

    public ClassType getAnnotationType() {
        return this.annotationType;
    }

    public void addMember(String name, Object value) {
        this.elementsValue.put(name, value);
    }

    public Class<? extends Annotation> annotationType() {
        return this.annotationType.getReflectClass();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AnnotationEntry)) {
            return false;
        }
        AnnotationEntry other = (AnnotationEntry) obj;
        if (!getAnnotationType().getName().equals(other.getAnnotationType().getName())) {
            return false;
        }
        for (Entry<String, Object> it : this.elementsValue.entrySet()) {
            String key = (String) it.getKey();
            Object value1 = it.getValue();
            Object value2 = other.elementsValue.get(key);
            if (value1 != value2) {
                if (value1 == null || value2 == null) {
                    return false;
                }
                if (!value1.equals(value2)) {
                    return false;
                }
            }
        }
        for (Entry<String, Object> it2 : other.elementsValue.entrySet()) {
            key = (String) it2.getKey();
            value2 = it2.getValue();
            value1 = this.elementsValue.get(key);
            if (value1 != value2) {
                if (value1 == null || value2 == null) {
                    return false;
                }
                if (!value1.equals(value2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 0;
        for (Entry<String, Object> it : this.elementsValue.entrySet()) {
            int khash = ((String) it.getKey()).hashCode();
            hash += (khash * TransportMediator.KEYCODE_MEDIA_PAUSE) ^ it.getValue().hashCode();
        }
        return hash;
    }

    public String toString() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append('@');
        sbuf.append(getAnnotationType().getName());
        sbuf.append('(');
        int count = 0;
        for (Entry<String, Object> it : this.elementsValue.entrySet()) {
            if (count > 0) {
                sbuf.append(", ");
            }
            sbuf.append((String) it.getKey());
            sbuf.append('=');
            sbuf.append(it.getValue());
            count++;
        }
        sbuf.append(')');
        return sbuf.toString();
    }
}
