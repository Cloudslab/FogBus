package gnu.bytecode;

import android.support.v4.internal.view.SupportMenu;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConstantPool {
    public static final byte CLASS = (byte) 7;
    public static final byte DOUBLE = (byte) 6;
    public static final byte FIELDREF = (byte) 9;
    public static final byte FLOAT = (byte) 4;
    public static final byte INTEGER = (byte) 3;
    public static final byte INTERFACE_METHODREF = (byte) 11;
    public static final byte LONG = (byte) 5;
    public static final byte METHODREF = (byte) 10;
    public static final byte NAME_AND_TYPE = (byte) 12;
    public static final byte STRING = (byte) 8;
    public static final byte UTF8 = (byte) 1;
    int count;
    CpoolEntry[] hashTab;
    boolean locked;
    CpoolEntry[] pool;

    public final int getCount() {
        return this.count;
    }

    public final CpoolEntry getPoolEntry(int index) {
        return this.pool[index];
    }

    void rehash() {
        int i;
        CpoolEntry entry;
        if (this.hashTab == null && this.count > 0) {
            i = this.pool.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                entry = this.pool[i];
                if (entry != null) {
                    entry.hashCode();
                }
            }
        }
        this.hashTab = new CpoolEntry[(this.count < 5 ? 101 : this.count * 2)];
        if (this.pool != null) {
            i = this.pool.length;
            while (true) {
                i--;
                if (i >= 0) {
                    entry = this.pool[i];
                    if (entry != null) {
                        entry.add_hashed(this);
                    }
                } else {
                    return;
                }
            }
        }
    }

    public CpoolUtf8 addUtf8(String s) {
        s = s.intern();
        int h = s.hashCode();
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolUtf8)) {
                CpoolUtf8 utf = (CpoolUtf8) entry;
                if (utf.string == s) {
                    return utf;
                }
            }
            entry = entry.next;
        }
        if (!this.locked) {
            return new CpoolUtf8(this, h, s);
        }
        throw new Error("adding new Utf8 entry to locked contant pool: " + s);
    }

    public CpoolClass addClass(ObjectType otype) {
        CpoolClass entry = addClass(addUtf8(otype.getInternalName()));
        entry.clas = otype;
        return entry;
    }

    public CpoolClass addClass(CpoolUtf8 name) {
        int h = CpoolClass.hashCode(name);
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolClass)) {
                CpoolClass ent = (CpoolClass) entry;
                if (ent.name == name) {
                    return ent;
                }
            }
            entry = entry.next;
        }
        return new CpoolClass(this, h, name);
    }

    CpoolValue1 addValue1(int tag, int val) {
        int h = CpoolValue1.hashCode(val);
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolValue1)) {
                CpoolValue1 ent = (CpoolValue1) entry;
                if (ent.tag == tag && ent.value == val) {
                    return ent;
                }
            }
            entry = entry.next;
        }
        return new CpoolValue1(this, tag, h, val);
    }

    CpoolValue2 addValue2(int tag, long val) {
        int h = CpoolValue2.hashCode(val);
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolValue2)) {
                CpoolValue2 ent = (CpoolValue2) entry;
                if (ent.tag == tag && ent.value == val) {
                    return ent;
                }
            }
            entry = entry.next;
        }
        return new CpoolValue2(this, tag, h, val);
    }

    public CpoolValue1 addInt(int val) {
        return addValue1(3, val);
    }

    public CpoolValue2 addLong(long val) {
        return addValue2(5, val);
    }

    public CpoolValue1 addFloat(float val) {
        return addValue1(4, Float.floatToIntBits(val));
    }

    public CpoolValue2 addDouble(double val) {
        return addValue2(6, Double.doubleToLongBits(val));
    }

    public final CpoolString addString(String string) {
        return addString(addUtf8(string));
    }

    public CpoolString addString(CpoolUtf8 str) {
        int h = CpoolString.hashCode(str);
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolString)) {
                CpoolString ent = (CpoolString) entry;
                if (ent.str == str) {
                    return ent;
                }
            }
            entry = entry.next;
        }
        return new CpoolString(this, h, str);
    }

    public CpoolNameAndType addNameAndType(Method method) {
        return addNameAndType(addUtf8(method.getName()), addUtf8(method.getSignature()));
    }

    public CpoolNameAndType addNameAndType(Field field) {
        return addNameAndType(addUtf8(field.getName()), addUtf8(field.getSignature()));
    }

    public CpoolNameAndType addNameAndType(CpoolUtf8 name, CpoolUtf8 type) {
        int h = CpoolNameAndType.hashCode(name, type);
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolNameAndType) && ((CpoolNameAndType) entry).name == name && ((CpoolNameAndType) entry).type == type) {
                return (CpoolNameAndType) entry;
            }
            entry = entry.next;
        }
        return new CpoolNameAndType(this, h, name, type);
    }

    public CpoolRef addRef(int tag, CpoolClass clas, CpoolNameAndType nameAndType) {
        int h = CpoolRef.hashCode(clas, nameAndType);
        if (this.hashTab == null) {
            rehash();
        }
        CpoolEntry entry = this.hashTab[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & h) % this.hashTab.length];
        while (entry != null) {
            if (h == entry.hash && (entry instanceof CpoolRef)) {
                CpoolRef ref = (CpoolRef) entry;
                if (ref.tag == tag && ref.clas == clas && ref.nameAndType == nameAndType) {
                    return ref;
                }
            }
            entry = entry.next;
        }
        return new CpoolRef(this, h, tag, clas, nameAndType);
    }

    public CpoolRef addMethodRef(Method method) {
        int tag;
        CpoolClass clas = addClass(method.classfile);
        if ((method.getDeclaringClass().getModifiers() & 512) == 0) {
            tag = 10;
        } else {
            tag = 11;
        }
        return addRef(tag, clas, addNameAndType(method));
    }

    public CpoolRef addFieldRef(Field field) {
        return addRef(9, addClass(field.owner), addNameAndType(field));
    }

    void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.count + 1);
        for (int i = 1; i <= this.count; i++) {
            CpoolEntry entry = this.pool[i];
            if (entry != null) {
                entry.write(dstr);
            }
        }
        this.locked = true;
    }

    CpoolEntry getForced(int index, int tag) {
        index &= SupportMenu.USER_MASK;
        CpoolEntry entry = this.pool[index];
        if (entry == null) {
            if (this.locked) {
                throw new Error("adding new entry to locked contant pool");
            }
            switch (tag) {
                case 1:
                    entry = new CpoolUtf8();
                    break;
                case 3:
                case 4:
                    entry = new CpoolValue1(tag);
                    break;
                case 5:
                case 6:
                    entry = new CpoolValue2(tag);
                    break;
                case 7:
                    entry = new CpoolClass();
                    break;
                case 8:
                    entry = new CpoolString();
                    break;
                case 9:
                case 10:
                case 11:
                    entry = new CpoolRef(tag);
                    break;
                case 12:
                    entry = new CpoolNameAndType();
                    break;
            }
            this.pool[index] = entry;
            entry.index = index;
        } else if (entry.getTag() != tag) {
            throw new ClassFormatError("conflicting constant pool tags at " + index);
        }
        return entry;
    }

    CpoolClass getForcedClass(int index) {
        return (CpoolClass) getForced(index, 7);
    }

    public ConstantPool(DataInputStream dstr) throws IOException {
        this.count = dstr.readUnsignedShort() - 1;
        this.pool = new CpoolEntry[(this.count + 1)];
        int i = 1;
        while (i <= this.count) {
            byte tag = dstr.readByte();
            CpoolEntry entry = getForced(i, tag);
            switch (tag) {
                case (byte) 1:
                    ((CpoolUtf8) entry).string = dstr.readUTF();
                    break;
                case (byte) 3:
                case (byte) 4:
                    ((CpoolValue1) entry).value = dstr.readInt();
                    break;
                case (byte) 5:
                case (byte) 6:
                    ((CpoolValue2) entry).value = dstr.readLong();
                    i++;
                    break;
                case (byte) 7:
                    ((CpoolClass) entry).name = (CpoolUtf8) getForced(dstr.readUnsignedShort(), 1);
                    break;
                case (byte) 8:
                    ((CpoolString) entry).str = (CpoolUtf8) getForced(dstr.readUnsignedShort(), 1);
                    break;
                case (byte) 9:
                case (byte) 10:
                case (byte) 11:
                    CpoolRef ref = (CpoolRef) entry;
                    ref.clas = getForcedClass(dstr.readUnsignedShort());
                    ref.nameAndType = (CpoolNameAndType) getForced(dstr.readUnsignedShort(), 12);
                    break;
                case (byte) 12:
                    CpoolNameAndType ntyp = (CpoolNameAndType) entry;
                    ntyp.name = (CpoolUtf8) getForced(dstr.readUnsignedShort(), 1);
                    ntyp.type = (CpoolUtf8) getForced(dstr.readUnsignedShort(), 1);
                    break;
                default:
                    break;
            }
            i++;
        }
    }
}
