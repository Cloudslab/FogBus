package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Stack;

public class Method implements AttrContainer, Member {
    int access_flags;
    Type[] arg_types;
    Attribute attributes;
    ClassType classfile;
    CodeAttr code = this;
    ExceptionsAttr exceptions;
    private String name;
    int name_index;
    Method next;
    Type return_type;
    String signature;
    int signature_index;

    public final Attribute getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    public final ExceptionsAttr getExceptionAttr() {
        return this.exceptions;
    }

    public void setExceptions(short[] exn_indices) {
        if (this.exceptions == null) {
            this.exceptions = new ExceptionsAttr(this);
        }
        this.exceptions.setExceptions(exn_indices, this.classfile);
    }

    public void setExceptions(ClassType[] exn_types) {
        if (this.exceptions == null) {
            this.exceptions = new ExceptionsAttr(this);
        }
        this.exceptions.setExceptions(exn_types);
    }

    public final CodeAttr getCode() {
        return this.code;
    }

    private Method() {
    }

    public static Method makeCloneMethod(Type returnType) {
        Method method = new Method();
        method.name = "clone";
        method.access_flags = 1;
        method.arg_types = Type.typeArray0;
        method.return_type = returnType;
        method.classfile = Type.pointer_type;
        return method;
    }

    public Method(Method base, ClassType clas) {
        this.arg_types = base.arg_types;
        this.return_type = base.return_type;
        this.name = base.name;
        this.access_flags = base.access_flags;
        this.classfile = clas;
    }

    Method(ClassType clfile, int flags) {
        if (clfile.last_method == null) {
            clfile.methods = this;
        } else {
            clfile.last_method.next = this;
        }
        clfile.last_method = this;
        clfile.methods_count++;
        this.access_flags = flags;
        this.classfile = clfile;
    }

    public final void setStaticFlag(boolean is_static) {
        if (is_static) {
            this.access_flags |= 8;
        } else {
            this.access_flags ^= -9;
        }
    }

    public final boolean getStaticFlag() {
        return (this.access_flags & 8) != 0;
    }

    public final boolean isAbstract() {
        return (this.access_flags & 1024) != 0;
    }

    public int getModifiers() {
        return this.access_flags;
    }

    public void setModifiers(int modifiers) {
        this.access_flags = modifiers;
    }

    public final ConstantPool getConstants() {
        return this.classfile.constants;
    }

    public Scope pushScope() {
        prepareCode(0);
        return this.code.pushScope();
    }

    public final boolean reachableHere() {
        return this.code.reachableHere();
    }

    public Scope popScope() {
        return this.code.popScope();
    }

    public void allocate_local(Variable local) {
        local.allocateLocal(this.code);
    }

    public void initCode() {
        if (this.classfile.constants == null) {
            this.classfile.constants = new ConstantPool();
        }
        prepareCode(0);
        this.code.sourceDbgExt = this.classfile.sourceDbgExt;
        this.code.noteParamTypes();
        this.code.pushScope();
    }

    public void init_param_slots() {
        startCode();
    }

    public CodeAttr startCode() {
        initCode();
        this.code.addParamLocals();
        return this.code;
    }

    void kill_local(Variable var) {
        var.freeLocal(this.code);
    }

    void prepareCode(int max_size) {
        if (this.code == null) {
            this.code = new CodeAttr(this);
        }
        this.code.reserve(max_size);
    }

    void instruction_start_hook(int max_size) {
        prepareCode(max_size);
    }

    final Type pop_stack_type() {
        return this.code.popType();
    }

    final void push_stack_type(Type type) {
        this.code.pushType(type);
    }

    public void compile_checkcast(Type type) {
        this.code.emitCheckcast(type);
    }

    public void maybe_compile_checkcast(Type type) {
        if (type != this.code.topType()) {
            this.code.emitCheckcast(type);
        }
    }

    public void push_var(Variable var) {
        this.code.emitLoad(var);
    }

    public void compile_push_value(Variable var) {
        this.code.emitLoad(var);
    }

    public void compile_store_value(Variable var) {
        this.code.emitStore(var);
    }

    public void compile_push_this() {
        this.code.emitPushThis();
    }

    void write(DataOutputStream dstr, ClassType classfile) throws IOException {
        dstr.writeShort(this.access_flags);
        dstr.writeShort(this.name_index);
        dstr.writeShort(this.signature_index);
        Attribute.writeAll(this, dstr);
    }

    public static String makeSignature(Type[] arg_types, Type return_type) {
        StringBuilder buf = new StringBuilder(100);
        buf.append('(');
        for (Type signature : arg_types) {
            buf.append(signature.getSignature());
        }
        buf.append(')');
        buf.append(return_type.getSignature());
        return buf.toString();
    }

    public String getSignature() {
        if (this.signature == null) {
            this.signature = makeSignature(this.arg_types, this.return_type);
        }
        return this.signature;
    }

    public void setSignature(String signature) {
        int len = signature.length();
        if (len < 3 || signature.charAt(0) != '(') {
            throw new ClassFormatError("bad method signature");
        }
        int pos = 1;
        Stack<Type> types = new Stack();
        while (true) {
            int arg_sig_len = Type.signatureLength(signature, pos);
            if (arg_sig_len < 0) {
                break;
            }
            types.push(Type.signatureToType(signature, pos, arg_sig_len));
            pos += arg_sig_len;
        }
        if (pos >= len || signature.charAt(pos) != ')') {
            throw new ClassFormatError("bad method signature");
        }
        this.arg_types = new Type[types.size()];
        int i = types.size();
        while (true) {
            i--;
            if (i >= 0) {
                this.arg_types[i] = (Type) types.pop();
            } else {
                this.return_type = Type.signatureToType(signature, pos + 1, (len - pos) - 1);
                return;
            }
        }
    }

    public void setSignature(int signature_index) {
        CpoolUtf8 sigConstant = (CpoolUtf8) getConstants().getForced(signature_index, 1);
        this.signature_index = signature_index;
        setSignature(sigConstant.string);
    }

    void assignConstants() {
        ConstantPool constants = getConstants();
        if (this.name_index == 0 && this.name != null) {
            this.name_index = constants.addUtf8(this.name).index;
        }
        if (this.signature_index == 0) {
            this.signature_index = constants.addUtf8(getSignature()).index;
        }
        Attribute.assignConstants(this, this.classfile);
    }

    public ClassType getDeclaringClass() {
        return this.classfile;
    }

    public final Type getReturnType() {
        return this.return_type;
    }

    public final Type[] getParameterTypes() {
        return this.arg_types;
    }

    public final ClassType[] getExceptions() {
        if (this.exceptions == null) {
            return null;
        }
        return this.exceptions.getExceptions();
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setName(int name_index) {
        if (name_index <= 0) {
            this.name = null;
        } else {
            this.name = ((CpoolUtf8) getConstants().getForced(name_index, 1)).string;
        }
        this.name_index = name_index;
    }

    public final Method getNext() {
        return this.next;
    }

    public void listParameters(StringBuffer sbuf) {
        int args_count = this.arg_types.length;
        sbuf.append('(');
        for (int i = 0; i < args_count; i++) {
            if (i > 0) {
                sbuf.append(',');
            }
            sbuf.append(this.arg_types[i].getName());
        }
        sbuf.append(')');
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append(getDeclaringClass().getName());
        sbuf.append('.');
        sbuf.append(this.name);
        if (this.arg_types != null) {
            listParameters(sbuf);
            sbuf.append(this.return_type.getName());
        }
        return sbuf.toString();
    }

    public void cleanupAfterCompilation() {
        this.attributes = null;
        this.exceptions = null;
        this.code = null;
    }
}
