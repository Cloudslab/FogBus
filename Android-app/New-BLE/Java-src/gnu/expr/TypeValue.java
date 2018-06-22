package gnu.expr;

import gnu.bytecode.Variable;
import gnu.mapping.Procedure;
import java.lang.reflect.Type;

public interface TypeValue extends Type {
    Expression convertValue(Expression expression);

    void emitIsInstance(Variable variable, Compilation compilation, Target target);

    void emitTestIf(Variable variable, Declaration declaration, Compilation compilation);

    Procedure getConstructor();

    gnu.bytecode.Type getImplementationType();
}
