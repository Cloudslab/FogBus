package gnu.bytecode;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;

public class ClassTypeWriter extends PrintWriter {
    public static final int PRINT_CONSTANT_POOL = 1;
    public static final int PRINT_CONSTANT_POOL_INDEXES = 2;
    public static final int PRINT_EXTRAS = 8;
    public static final int PRINT_VERBOSE = 15;
    public static final int PRINT_VERSION = 4;
    ClassType ctype;
    int flags;

    public ClassTypeWriter(ClassType ctype, Writer stream, int flags) {
        super(stream);
        this.ctype = ctype;
        this.flags = flags;
    }

    public ClassTypeWriter(ClassType ctype, OutputStream stream, int flags) {
        super(stream);
        this.ctype = ctype;
        this.flags = flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public static void print(ClassType ctype, PrintWriter stream, int flags) {
        ClassTypeWriter writer = new ClassTypeWriter(ctype, (Writer) stream, flags);
        writer.print();
        writer.flush();
    }

    public static void print(ClassType ctype, PrintStream stream, int flags) {
        ClassTypeWriter writer = new ClassTypeWriter(ctype, (OutputStream) stream, flags);
        writer.print();
        writer.flush();
    }

    public void print() {
        if ((this.flags & 4) != 0) {
            print("Classfile format major version: ");
            print(this.ctype.getClassfileMajorVersion());
            print(", minor version: ");
            print(this.ctype.getClassfileMinorVersion());
            println('.');
        }
        if ((this.flags & 1) != 0) {
            printConstantPool();
        }
        printClassInfo();
        printFields();
        printMethods();
        printAttributes();
    }

    public void setClass(ClassType ctype) {
        this.ctype = ctype;
    }

    public void print(ClassType ctype) {
        this.ctype = ctype;
        print();
    }

    public void printAttributes() {
        AttrContainer attrs = this.ctype;
        println();
        print("Attributes (count: ");
        print(Attribute.count(attrs));
        println("):");
        printAttributes(attrs);
    }

    public void printAttributes(AttrContainer container) {
        for (Attribute attr = container.getAttributes(); attr != null; attr = attr.next) {
            attr.print(this);
        }
    }

    public void printClassInfo() {
        println();
        print("Access flags:");
        print(Access.toString(this.ctype.getModifiers(), Access.CLASS_CONTEXT));
        println();
        print("This class: ");
        printOptionalIndex(this.ctype.thisClassIndex);
        printConstantTersely(this.ctype.thisClassIndex, 7);
        print(" super: ");
        if (this.ctype.superClassIndex == -1) {
            print("<unknown>");
        } else if (this.ctype.superClassIndex == 0) {
            print("0");
        } else {
            printOptionalIndex(this.ctype.superClassIndex);
            printConstantTersely(this.ctype.superClassIndex, 7);
        }
        println();
        print("Interfaces (count: ");
        int[] interfaces = this.ctype.interfaceIndexes;
        int n_interfaces = interfaces == null ? 0 : interfaces.length;
        print(n_interfaces);
        print("):");
        println();
        for (int i = 0; i < n_interfaces; i++) {
            print("- Implements: ");
            int index = interfaces[i];
            printOptionalIndex(index);
            printConstantTersely(index, 7);
            println();
        }
    }

    public void printFields() {
        println();
        print("Fields (count: ");
        print(this.ctype.fields_count);
        print("):");
        println();
        int ifield = 0;
        for (Field field = this.ctype.fields; field != null; field = field.next) {
            print("Field name: ");
            if (field.name_index != 0) {
                printOptionalIndex(field.name_index);
            }
            print(field.getName());
            print(Access.toString(field.flags, Access.FIELD_CONTEXT));
            print(" Signature: ");
            if (field.signature_index != 0) {
                printOptionalIndex(field.signature_index);
            }
            printSignature(field.type);
            println();
            printAttributes(field);
            ifield++;
        }
    }

    public void printMethods() {
        println();
        print("Methods (count: ");
        print(this.ctype.methods_count);
        print("):");
        println();
        for (Method method = this.ctype.methods; method != null; method = method.next) {
            printMethod(method);
        }
    }

    public void printMethod(Method method) {
        println();
        print("Method name:");
        if (method.name_index != 0) {
            printOptionalIndex(method.name_index);
        }
        print('\"');
        print(method.getName());
        print('\"');
        print(Access.toString(method.access_flags, Access.METHOD_CONTEXT));
        print(" Signature: ");
        if (method.signature_index != 0) {
            printOptionalIndex(method.signature_index);
        }
        print('(');
        for (int i = 0; i < method.arg_types.length; i++) {
            if (i > 0) {
                print(',');
            }
            printSignature(method.arg_types[i]);
        }
        print(')');
        printSignature(method.return_type);
        println();
        printAttributes(method);
    }

    CpoolEntry getCpoolEntry(int index) {
        CpoolEntry[] pool = this.ctype.constants.pool;
        if (pool == null || index < 0 || index >= pool.length) {
            return null;
        }
        return pool[index];
    }

    final void printConstantTersely(CpoolEntry entry, int expected_tag) {
        if (entry == null) {
            print("<invalid constant index>");
        } else if (entry.getTag() != expected_tag) {
            print("<unexpected constant type ");
            entry.print(this, 1);
            print('>');
        } else {
            entry.print(this, 0);
        }
    }

    final void printConstantTersely(int index, int expected_tag) {
        printConstantTersely(getCpoolEntry(index), expected_tag);
    }

    final void printContantUtf8AsClass(int type_index) {
        CpoolEntry entry = getCpoolEntry(type_index);
        if (entry == null || entry.getTag() != 1) {
            printConstantTersely(type_index, 1);
            return;
        }
        String name = ((CpoolUtf8) entry).string;
        Type.printSignature(name, 0, name.length(), this);
    }

    final void printConstantOperand(int index) {
        print(' ');
        printOptionalIndex(index);
        CpoolEntry[] pool = this.ctype.constants.pool;
        if (pool != null && index >= 0 && index < pool.length) {
            CpoolEntry entry = pool[index];
            if (entry != null) {
                print('<');
                entry.print(this, 1);
                print('>');
                return;
            }
        }
        print("<invalid constant index>");
    }

    public final void printQuotedString(String string) {
        print('\"');
        int len = string.length();
        for (int i = 0; i < len; i++) {
            char ch = string.charAt(i);
            if (ch == '\"') {
                print("\\\"");
            } else if (ch < ' ' || ch >= '') {
                if (ch != '\n') {
                    print("\\u");
                    int j = 4;
                    while (true) {
                        j--;
                        if (j < 0) {
                            break;
                        }
                        print(Character.forDigit((ch >> (j * 4)) & 15, 16));
                    }
                } else {
                    print("\\n");
                }
            } else {
                print(ch);
            }
        }
        print('\"');
    }

    public void printConstantPool() {
        CpoolEntry[] pool = this.ctype.constants.pool;
        int length = this.ctype.constants.count;
        for (int i = 1; i <= length; i++) {
            CpoolEntry entry = pool[i];
            if (entry != null) {
                print('#');
                print(entry.index);
                print(": ");
                entry.print(this, 2);
                println();
            }
        }
    }

    public final void printOptionalIndex(int index) {
        if ((this.flags & 2) != 0) {
            print('#');
            print(index);
            print('=');
        }
    }

    public final void printOptionalIndex(CpoolEntry entry) {
        printOptionalIndex(entry.index);
    }

    void printName(String name) {
        print(name);
    }

    public final int printSignature(String sig, int pos) {
        int len = sig.length();
        if (pos >= len) {
            print("<empty signature>");
            return pos;
        }
        int sig_length = Type.signatureLength(sig, pos);
        if (sig_length > 0) {
            String name = Type.signatureToName(sig.substring(pos, pos + sig_length));
            if (name != null) {
                print(name);
                return pos + sig_length;
            }
        }
        char c = sig.charAt(pos);
        if (c != '(') {
            print(c);
            return pos + 1;
        }
        pos++;
        print(c);
        int nargs = 0;
        while (pos < len) {
            c = sig.charAt(pos);
            if (c == ')') {
                pos++;
                print(c);
                return printSignature(sig, pos);
            }
            int nargs2 = nargs + 1;
            if (nargs > 0) {
                print(',');
            }
            pos = printSignature(sig, pos);
            nargs = nargs2;
        }
        print("<truncated method signature>");
        return pos;
    }

    public final void printSignature(String sig) {
        int pos = printSignature(sig, 0);
        if (pos < sig.length()) {
            print("<trailing junk:");
            print(sig.substring(pos));
            print('>');
        }
    }

    public final void printSignature(Type type) {
        if (type == null) {
            print("<unknown type>");
        } else {
            printSignature(type.getSignature());
        }
    }

    public void printSpaces(int count) {
        while (true) {
            count--;
            if (count >= 0) {
                print(' ');
            } else {
                return;
            }
        }
    }
}
