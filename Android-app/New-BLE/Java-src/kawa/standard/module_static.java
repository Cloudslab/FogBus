package kawa.standard;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_static extends Syntax {
    public static final module_static module_static = new module_static();

    static {
        module_static.setName("module-static");
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        LList list = st.getCdr();
        if (defs instanceof ModuleExp) {
            ModuleExp mexp = (ModuleExp) defs;
            if (list instanceof Pair) {
                st = (Pair) list;
                if (st.getCdr() == LList.Empty && (st.getCar() instanceof Boolean)) {
                    if (st.getCar() == Boolean.FALSE) {
                        mexp.setFlag(65536);
                    } else {
                        mexp.setFlag(32768);
                    }
                    if (mexp.getFlag(65536) && mexp.getFlag(32768)) {
                        tr.error('e', "inconsistent module-static specifiers");
                        return true;
                    }
                }
            }
            if (list instanceof Pair) {
                st = (Pair) list;
                if (st.getCdr() == LList.Empty && (st.getCar() instanceof Pair)) {
                    st = (Pair) st.getCar();
                    if (tr.matches(st.getCar(), LispLanguage.quote_sym)) {
                        LList st2 = (Pair) st.getCdr();
                        if (st2 != LList.Empty && (st2.getCar() instanceof SimpleSymbol) && st2.getCar().toString() == "init-run") {
                            mexp.setFlag(32768);
                            mexp.setFlag(262144);
                            return mexp.getFlag(65536) ? true : true;
                        } else {
                            tr.error('e', "invalid quoted symbol for '" + getName() + '\'');
                            return false;
                        }
                    }
                }
            }
            mexp.setFlag(65536);
            while (list != LList.Empty) {
                if (list instanceof Pair) {
                    Object st3 = (Pair) list;
                    if (st3.getCar() instanceof Symbol) {
                        Declaration decl = defs.getNoDefine((Symbol) st3.getCar());
                        if (decl.getFlag(512)) {
                            Translator.setLine(decl, st3);
                        }
                        decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                        list = st3.getCdr();
                    }
                }
                tr.error('e', "invalid syntax in '" + getName() + '\'');
                return false;
            }
            if (mexp.getFlag(65536)) {
            }
        }
        tr.error('e', "'" + getName() + "' not at module level");
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
