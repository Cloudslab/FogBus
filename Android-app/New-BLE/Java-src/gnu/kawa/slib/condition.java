package gnu.kawa.slib;

/* compiled from: conditions.scm */
public class condition extends RuntimeException {
    public Object type$Mnfield$Mnalist;

    /* compiled from: conditions.scm */
    public class Mntype {
        public Object all$Mnfields;
        public Object fields;
        public Object name;
        public Object supertype;

        public Mntype(Object obj, Object obj2, Object obj3, Object obj4) {
            this.name = obj;
            this.supertype = obj2;
            this.fields = obj3;
            this.all$Mnfields = obj4;
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer("#<condition-type ");
            sbuf.append(this.name);
            sbuf.append(">");
            return sbuf.toString();
        }
    }

    public condition(Object obj) {
        this.type$Mnfield$Mnalist = obj;
    }
}
