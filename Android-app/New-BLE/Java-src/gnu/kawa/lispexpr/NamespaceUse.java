package gnu.kawa.lispexpr;

import gnu.mapping.Namespace;

/* compiled from: LispPackage */
class NamespaceUse {
    Namespace imported;
    Namespace importing;
    NamespaceUse nextImported;
    NamespaceUse nextImporting;

    NamespaceUse() {
    }
}
