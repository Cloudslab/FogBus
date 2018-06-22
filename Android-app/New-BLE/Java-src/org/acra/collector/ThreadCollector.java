package org.acra.collector;

public class ThreadCollector {
    public static String collect(Thread t) {
        StringBuilder result = new StringBuilder();
        if (t != null) {
            result.append("id=").append(t.getId()).append("\n");
            result.append("name=").append(t.getName()).append("\n");
            result.append("priority=").append(t.getPriority()).append("\n");
            if (t.getThreadGroup() != null) {
                result.append("groupName=").append(t.getThreadGroup().getName()).append("\n");
            }
        } else {
            result.append("No broken thread, this might be a silent exception.");
        }
        return result.toString();
    }
}
