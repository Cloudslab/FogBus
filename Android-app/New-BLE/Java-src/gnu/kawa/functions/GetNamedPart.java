package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.SlotGet;
import gnu.mapping.HasNamedParts;
import gnu.mapping.HasSetter;
import gnu.mapping.MethodProc;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.Values;

public class GetNamedPart extends Procedure2 implements HasSetter {
    public static final String CAST_METHOD_NAME = "@";
    public static final String CLASSTYPE_FOR = "<>";
    public static final String INSTANCEOF_METHOD_NAME = "instance?";
    public static final GetNamedPart getNamedPart = new GetNamedPart();

    static {
        getNamedPart.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateGetNamedPart");
    }

    public Object apply2(Object container, Object part) throws Throwable {
        if (container instanceof Values) {
            Object[] values = ((Values) container).getValues();
            Values result = new Values();
            for (Object apply2 : values) {
                Values.writeValues(apply2(apply2, part), result);
            }
            return result.canonicalize();
        }
        Symbol sym;
        if (part instanceof Symbol) {
            sym = (Symbol) part;
        } else {
            sym = Namespace.EmptyNamespace.getSymbol(part.toString().intern());
        }
        return getNamedPart(container, sym);
    }

    public static Object getTypePart(Type type, String name) throws Throwable {
        if (name.equals(CLASSTYPE_FOR)) {
            return type;
        }
        if (type instanceof ObjectType) {
            if (name.equals(INSTANCEOF_METHOD_NAME)) {
                return new NamedPart(type, name, Access.INNERCLASS_CONTEXT);
            }
            if (name.equals(CAST_METHOD_NAME)) {
                return new NamedPart(type, name, Access.CLASS_CONTEXT);
            }
            if (name.equals("new")) {
                return new NamedPart(type, name, 'N');
            }
            if (name.equals(".length") || (name.length() > 1 && name.charAt(0) == '.' && (type instanceof ClassType))) {
                return new NamedPart(type, name, 'D');
            }
        }
        if (!(type instanceof ClassType)) {
            return getMemberPart(type, name);
        }
        try {
            return SlotGet.staticField(type, name);
        } catch (Throwable th) {
            return ClassMethods.apply(ClassMethods.classMethods, type, name);
        }
    }

    public static Object getNamedPart(Object container, Symbol part) throws Throwable {
        String name = part.getName();
        if (container instanceof HasNamedParts) {
            return ((HasNamedParts) container).get(name);
        }
        if (container instanceof Class) {
            container = Type.make((Class) container);
        }
        if (container instanceof Package) {
            try {
                return ObjectType.getContextClass(((Package) container).getName() + '.' + name);
            } catch (Throwable th) {
            }
        }
        if (container instanceof Type) {
            return getTypePart((Type) container, name);
        }
        return getMemberPart(container, part.toString());
    }

    public static Object getMemberPart(Object container, String name) throws Throwable {
        try {
            return SlotGet.field(container, name);
        } catch (Throwable th) {
            MethodProc methods = ClassMethods.apply((ClassType) Type.make(container.getClass()), Compilation.mangleName(name), '\u0000', Language.getDefaultLanguage());
            if (methods != null) {
                return new NamedPart(container, name, Access.METHOD_CONTEXT, methods);
            }
            RuntimeException runtimeException = new RuntimeException("no part '" + name + "' in " + container);
        }
    }

    public Procedure getSetter() {
        return SetNamedPart.setNamedPart;
    }
}
