package gnu.kawa.slib;

/* compiled from: srfi34.scm */
public class raise$Mnobject$Mnexception extends Throwable {
    public Object value;

    public raise$Mnobject$Mnexception(Object obj) {
        this.value = obj;
    }
}
