package kawa.lang;

public interface SyntaxForm {
    Object getDatum();

    TemplateScope getScope();
}
