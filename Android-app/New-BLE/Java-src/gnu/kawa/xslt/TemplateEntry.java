package gnu.kawa.xslt;

import gnu.mapping.Procedure;

/* compiled from: TemplateTable */
class TemplateEntry {
    TemplateEntry next;
    String pattern;
    double priority;
    Procedure procedure;

    TemplateEntry() {
    }
}
