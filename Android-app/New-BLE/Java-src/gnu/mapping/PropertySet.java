package gnu.mapping;

public abstract class PropertySet implements Named {
    public static final Symbol nameKey = Namespace.EmptyNamespace.getSymbol("name");
    private Object[] properties;

    public String getName() {
        Object symbol = getProperty(nameKey, null);
        if (symbol == null) {
            return null;
        }
        return symbol instanceof Symbol ? ((Symbol) symbol).getName() : symbol.toString();
    }

    public Object getSymbol() {
        return getProperty(nameKey, null);
    }

    public final void setSymbol(Object name) {
        setProperty(nameKey, name);
    }

    public final void setName(String name) {
        setProperty(nameKey, name);
    }

    public Object getProperty(Object key, Object defaultValue) {
        if (this.properties == null) {
            return defaultValue;
        }
        int i = this.properties.length;
        do {
            i -= 2;
            if (i < 0) {
                return defaultValue;
            }
        } while (this.properties[i] != key);
        return this.properties[i + 1];
    }

    public synchronized void setProperty(Object key, Object value) {
        this.properties = setProperty(this.properties, key, value);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object[] setProperty(java.lang.Object[] r7, java.lang.Object r8, java.lang.Object r9) {
        /*
        r6 = 0;
        r4 = r7;
        if (r4 != 0) goto L_0x0011;
    L_0x0004:
        r5 = 10;
        r4 = new java.lang.Object[r5];
        r7 = r4;
        r0 = 0;
    L_0x000a:
        r4[r0] = r8;
        r5 = r0 + 1;
        r4[r5] = r9;
    L_0x0010:
        return r7;
    L_0x0011:
        r0 = -1;
        r1 = r4.length;
    L_0x0013:
        r1 = r1 + -2;
        if (r1 < 0) goto L_0x0028;
    L_0x0017:
        r2 = r4[r1];
        if (r2 != r8) goto L_0x0024;
    L_0x001b:
        r5 = r1 + 1;
        r3 = r4[r5];
        r5 = r1 + 1;
        r4[r5] = r9;
        goto L_0x0010;
    L_0x0024:
        if (r2 != 0) goto L_0x0013;
    L_0x0026:
        r0 = r1;
        goto L_0x0013;
    L_0x0028:
        if (r0 >= 0) goto L_0x000a;
    L_0x002a:
        r0 = r4.length;
        r5 = r0 * 2;
        r7 = new java.lang.Object[r5];
        java.lang.System.arraycopy(r4, r6, r7, r6, r0);
        r4 = r7;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.PropertySet.setProperty(java.lang.Object[], java.lang.Object, java.lang.Object):java.lang.Object[]");
    }

    public Object removeProperty(Object key) {
        Object[] props = this.properties;
        if (props == null) {
            return null;
        }
        int i = props.length;
        do {
            i -= 2;
            if (i < 0) {
                return null;
            }
        } while (props[i] != key);
        Object old = props[i + 1];
        props[i] = null;
        props[i + 1] = null;
        return old;
    }
}
