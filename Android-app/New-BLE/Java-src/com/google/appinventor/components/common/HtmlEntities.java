package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlEntities {
    private static final Pattern HTML_ENTITY_PATTERN = Pattern.compile("&(#?[0-9a-zA-Z]+);");
    private static final Map<String, Character> lookup = new HashMap();

    static {
        lookup.put("Agrave", Character.valueOf('À'));
        lookup.put("agrave", Character.valueOf('à'));
        lookup.put("Aacute", Character.valueOf('Á'));
        lookup.put("aacute", Character.valueOf('á'));
        lookup.put("Acirc", Character.valueOf('Â'));
        lookup.put("acirc", Character.valueOf('â'));
        lookup.put("Atilde", Character.valueOf('Ã'));
        lookup.put("atilde", Character.valueOf('ã'));
        lookup.put("Auml", Character.valueOf('Ä'));
        lookup.put("auml", Character.valueOf('ä'));
        lookup.put("Aring", Character.valueOf('Å'));
        lookup.put("aring", Character.valueOf('å'));
        lookup.put("AElig", Character.valueOf('Æ'));
        lookup.put("aelig", Character.valueOf('æ'));
        lookup.put("Ccedil", Character.valueOf('Ç'));
        lookup.put("ccedil", Character.valueOf('ç'));
        lookup.put("Egrave", Character.valueOf('È'));
        lookup.put("egrave", Character.valueOf('è'));
        lookup.put("Eacute", Character.valueOf('É'));
        lookup.put("eacute", Character.valueOf('é'));
        lookup.put("Ecirc", Character.valueOf('Ê'));
        lookup.put("ecirc", Character.valueOf('ê'));
        lookup.put("Euml", Character.valueOf('Ë'));
        lookup.put("euml", Character.valueOf('ë'));
        lookup.put("Igrave", Character.valueOf('Ì'));
        lookup.put("igrave", Character.valueOf('ì'));
        lookup.put("Iacute", Character.valueOf('Í'));
        lookup.put("iacute", Character.valueOf('í'));
        lookup.put("Icirc", Character.valueOf('Î'));
        lookup.put("icirc", Character.valueOf('î'));
        lookup.put("Iuml", Character.valueOf('Ï'));
        lookup.put("iuml", Character.valueOf('ï'));
        lookup.put("ETH", Character.valueOf('Ð'));
        lookup.put("eth", Character.valueOf('ð'));
        lookup.put("Ntilde", Character.valueOf('Ñ'));
        lookup.put("ntilde", Character.valueOf('ñ'));
        lookup.put("Ograve", Character.valueOf('Ò'));
        lookup.put("ograve", Character.valueOf('ò'));
        lookup.put("Oacute", Character.valueOf('Ó'));
        lookup.put("oacute", Character.valueOf('ó'));
        lookup.put("Ocirc", Character.valueOf('Ô'));
        lookup.put("ocirc", Character.valueOf('ô'));
        lookup.put("Otilde", Character.valueOf('Õ'));
        lookup.put("otilde", Character.valueOf('õ'));
        lookup.put("Ouml", Character.valueOf('Ö'));
        lookup.put("ouml", Character.valueOf('ö'));
        lookup.put("Oslash", Character.valueOf('Ø'));
        lookup.put("oslash", Character.valueOf('ø'));
        lookup.put("Ugrave", Character.valueOf('Ù'));
        lookup.put("ugrave", Character.valueOf('ù'));
        lookup.put("Uacute", Character.valueOf('Ú'));
        lookup.put("uacute", Character.valueOf('ú'));
        lookup.put("Ucirc", Character.valueOf('Û'));
        lookup.put("ucirc", Character.valueOf('û'));
        lookup.put("Uuml", Character.valueOf('Ü'));
        lookup.put("uuml", Character.valueOf('ü'));
        lookup.put("Yacute", Character.valueOf('Ý'));
        lookup.put("yacute", Character.valueOf('ý'));
        lookup.put("THORN", Character.valueOf('Þ'));
        lookup.put("thorn", Character.valueOf('þ'));
        lookup.put("szlig", Character.valueOf('ß'));
        lookup.put("yuml", Character.valueOf('ÿ'));
        lookup.put("Yuml", Character.valueOf('Ÿ'));
        lookup.put("OElig", Character.valueOf('Œ'));
        lookup.put("oelig", Character.valueOf('œ'));
        lookup.put("Scaron", Character.valueOf('Š'));
        lookup.put("scaron", Character.valueOf('š'));
        lookup.put("Alpha", Character.valueOf('Α'));
        lookup.put("Beta", Character.valueOf('Β'));
        lookup.put("Gamma", Character.valueOf('Γ'));
        lookup.put("Delta", Character.valueOf('Δ'));
        lookup.put("Epsilon", Character.valueOf('Ε'));
        lookup.put("Zeta", Character.valueOf('Ζ'));
        lookup.put("Eta", Character.valueOf('Η'));
        lookup.put("Theta", Character.valueOf('Θ'));
        lookup.put("Iota", Character.valueOf('Ι'));
        lookup.put("Kappa", Character.valueOf('Κ'));
        lookup.put("Lambda", Character.valueOf('Λ'));
        lookup.put("Mu", Character.valueOf('Μ'));
        lookup.put("Nu", Character.valueOf('Ν'));
        lookup.put("Xi", Character.valueOf('Ξ'));
        lookup.put("Omicron", Character.valueOf('Ο'));
        lookup.put("Pi", Character.valueOf('Π'));
        lookup.put("Rho", Character.valueOf('Ρ'));
        lookup.put("Sigma", Character.valueOf('Σ'));
        lookup.put("Tau", Character.valueOf('Τ'));
        lookup.put("Upsilon", Character.valueOf('Υ'));
        lookup.put("Phi", Character.valueOf('Φ'));
        lookup.put("Chi", Character.valueOf('Χ'));
        lookup.put("Psi", Character.valueOf('Ψ'));
        lookup.put("Omega", Character.valueOf('Ω'));
        lookup.put("alpha", Character.valueOf('α'));
        lookup.put("beta", Character.valueOf('β'));
        lookup.put("gamma", Character.valueOf('γ'));
        lookup.put("delta", Character.valueOf('δ'));
        lookup.put("epsilon", Character.valueOf('ε'));
        lookup.put("zeta", Character.valueOf('ζ'));
        lookup.put("eta", Character.valueOf('η'));
        lookup.put("theta", Character.valueOf('θ'));
        lookup.put("iota", Character.valueOf('ι'));
        lookup.put("kappa", Character.valueOf('κ'));
        lookup.put("lambda", Character.valueOf('λ'));
        lookup.put("mu", Character.valueOf('μ'));
        lookup.put("nu", Character.valueOf('ν'));
        lookup.put("xi", Character.valueOf('ξ'));
        lookup.put("omicron", Character.valueOf('ο'));
        lookup.put("pi", Character.valueOf('π'));
        lookup.put("rho", Character.valueOf('ρ'));
        lookup.put("sigmaf", Character.valueOf('ς'));
        lookup.put("sigma", Character.valueOf('σ'));
        lookup.put("tau", Character.valueOf('τ'));
        lookup.put("upsilon", Character.valueOf('υ'));
        lookup.put("phi", Character.valueOf('φ'));
        lookup.put("chi", Character.valueOf('χ'));
        lookup.put("psi", Character.valueOf('ψ'));
        lookup.put("omega", Character.valueOf('ω'));
        lookup.put("thetasym", Character.valueOf('ϑ'));
        lookup.put("upsih", Character.valueOf('ϒ'));
        lookup.put("piv", Character.valueOf('ϖ'));
        lookup.put("iexcl", Character.valueOf('¡'));
        lookup.put("cent", Character.valueOf('¢'));
        lookup.put("pound", Character.valueOf('£'));
        lookup.put("curren", Character.valueOf('¤'));
        lookup.put("yen", Character.valueOf('¥'));
        lookup.put("brvbar", Character.valueOf('¦'));
        lookup.put("sect", Character.valueOf('§'));
        lookup.put("uml", Character.valueOf('¨'));
        lookup.put("copy", Character.valueOf('©'));
        lookup.put("ordf", Character.valueOf('ª'));
        lookup.put("laquo", Character.valueOf('«'));
        lookup.put("not", Character.valueOf('¬'));
        lookup.put("shy", Character.valueOf('­'));
        lookup.put("reg", Character.valueOf('®'));
        lookup.put("macr", Character.valueOf('¯'));
        lookup.put("deg", Character.valueOf('°'));
        lookup.put("plusmn", Character.valueOf('±'));
        lookup.put("sup2", Character.valueOf('²'));
        lookup.put("sup3", Character.valueOf('³'));
        lookup.put("acute", Character.valueOf('´'));
        lookup.put("micro", Character.valueOf('µ'));
        lookup.put("para", Character.valueOf('¶'));
        lookup.put("middot", Character.valueOf('·'));
        lookup.put("cedil", Character.valueOf('¸'));
        lookup.put("sup1", Character.valueOf('¹'));
        lookup.put("ordm", Character.valueOf('º'));
        lookup.put("raquo", Character.valueOf('»'));
        lookup.put("frac14", Character.valueOf('¼'));
        lookup.put("frac12", Character.valueOf('½'));
        lookup.put("frac34", Character.valueOf('¾'));
        lookup.put("iquest", Character.valueOf('¿'));
        lookup.put("times", Character.valueOf('×'));
        lookup.put("divide", Character.valueOf('÷'));
        lookup.put("fnof", Character.valueOf('ƒ'));
        lookup.put("circ", Character.valueOf('ˆ'));
        lookup.put("tilde", Character.valueOf('˜'));
        lookup.put("lrm", Character.valueOf('‎'));
        lookup.put("rlm", Character.valueOf('‏'));
        lookup.put("ndash", Character.valueOf('–'));
        lookup.put("endash", Character.valueOf('–'));
        lookup.put("mdash", Character.valueOf('—'));
        lookup.put("emdash", Character.valueOf('—'));
        lookup.put("lsquo", Character.valueOf('‘'));
        lookup.put("rsquo", Character.valueOf('’'));
        lookup.put("sbquo", Character.valueOf('‚'));
        lookup.put("ldquo", Character.valueOf('“'));
        lookup.put("rdquo", Character.valueOf('”'));
        lookup.put("bdquo", Character.valueOf('„'));
        lookup.put("dagger", Character.valueOf('†'));
        lookup.put("Dagger", Character.valueOf('‡'));
        lookup.put("bull", Character.valueOf('•'));
        lookup.put("hellip", Character.valueOf('…'));
        lookup.put("permil", Character.valueOf('‰'));
        lookup.put("prime", Character.valueOf('′'));
        lookup.put("Prime", Character.valueOf('″'));
        lookup.put("lsaquo", Character.valueOf('‹'));
        lookup.put("rsaquo", Character.valueOf('›'));
        lookup.put("oline", Character.valueOf('‾'));
        lookup.put("frasl", Character.valueOf('⁄'));
        lookup.put("euro", Character.valueOf('€'));
        lookup.put("image", Character.valueOf('ℑ'));
        lookup.put("weierp", Character.valueOf('℘'));
        lookup.put("real", Character.valueOf('ℜ'));
        lookup.put("trade", Character.valueOf('™'));
        lookup.put("alefsym", Character.valueOf('ℵ'));
        lookup.put("larr", Character.valueOf('←'));
        lookup.put("uarr", Character.valueOf('↑'));
        lookup.put("rarr", Character.valueOf('→'));
        lookup.put("darr", Character.valueOf('↓'));
        lookup.put("harr", Character.valueOf('↔'));
        lookup.put("crarr", Character.valueOf('↵'));
        lookup.put("lArr", Character.valueOf('⇐'));
        lookup.put("uArr", Character.valueOf('⇑'));
        lookup.put("rArr", Character.valueOf('⇒'));
        lookup.put("dArr", Character.valueOf('⇓'));
        lookup.put("hArr", Character.valueOf('⇔'));
        lookup.put("forall", Character.valueOf('∀'));
        lookup.put("part", Character.valueOf('∂'));
        lookup.put("exist", Character.valueOf('∃'));
        lookup.put("empty", Character.valueOf('∅'));
        lookup.put("nabla", Character.valueOf('∇'));
        lookup.put("isin", Character.valueOf('∈'));
        lookup.put("notin", Character.valueOf('∉'));
        lookup.put("ni", Character.valueOf('∋'));
        lookup.put("prod", Character.valueOf('∏'));
        lookup.put("sum", Character.valueOf('∑'));
        lookup.put("minus", Character.valueOf('−'));
        lookup.put("lowast", Character.valueOf('∗'));
        lookup.put("radic", Character.valueOf('√'));
        lookup.put("prop", Character.valueOf('∝'));
        lookup.put("infin", Character.valueOf('∞'));
        lookup.put("ang", Character.valueOf('∠'));
        lookup.put("and", Character.valueOf('∧'));
        lookup.put("or", Character.valueOf('∨'));
        lookup.put("cap", Character.valueOf('∩'));
        lookup.put("cup", Character.valueOf('∪'));
        lookup.put("int", Character.valueOf('∫'));
        lookup.put("there4", Character.valueOf('∴'));
        lookup.put("sim", Character.valueOf('∼'));
        lookup.put("cong", Character.valueOf('≅'));
        lookup.put("asymp", Character.valueOf('≈'));
        lookup.put("ne", Character.valueOf('≠'));
        lookup.put("equiv", Character.valueOf('≡'));
        lookup.put("le", Character.valueOf('≤'));
        lookup.put("ge", Character.valueOf('≥'));
        lookup.put("sub", Character.valueOf('⊂'));
        lookup.put("sup", Character.valueOf('⊃'));
        lookup.put("nsub", Character.valueOf('⊄'));
        lookup.put("sube", Character.valueOf('⊆'));
        lookup.put("supe", Character.valueOf('⊇'));
        lookup.put("oplus", Character.valueOf('⊕'));
        lookup.put("otimes", Character.valueOf('⊗'));
        lookup.put("perp", Character.valueOf('⊥'));
        lookup.put("sdot", Character.valueOf('⋅'));
        lookup.put("lceil", Character.valueOf('⌈'));
        lookup.put("rceil", Character.valueOf('⌉'));
        lookup.put("lfloor", Character.valueOf('⌊'));
        lookup.put("rfloor", Character.valueOf('⌋'));
        lookup.put("lang", Character.valueOf('〈'));
        lookup.put("rang", Character.valueOf('〉'));
        lookup.put("loz", Character.valueOf('◊'));
        lookup.put("spades", Character.valueOf('♠'));
        lookup.put("clubs", Character.valueOf('♣'));
        lookup.put("hearts", Character.valueOf('♥'));
        lookup.put("diams", Character.valueOf('♦'));
        lookup.put("gt", Character.valueOf('>'));
        lookup.put("GT", Character.valueOf('>'));
        lookup.put("lt", Character.valueOf('<'));
        lookup.put("LT", Character.valueOf('<'));
        lookup.put("quot", Character.valueOf('\"'));
        lookup.put("QUOT", Character.valueOf('\"'));
        lookup.put("amp", Character.valueOf('&'));
        lookup.put("AMP", Character.valueOf('&'));
        lookup.put("apos", Character.valueOf('\''));
        lookup.put("nbsp", Character.valueOf(' '));
        lookup.put("ensp", Character.valueOf(' '));
        lookup.put("emsp", Character.valueOf(' '));
        lookup.put("thinsp", Character.valueOf(' '));
        lookup.put("zwnj", Character.valueOf('‌'));
        lookup.put("zwj", Character.valueOf('‍'));
    }

    public static Character toCharacter(String entityName) {
        return (Character) lookup.get(entityName);
    }

    public static String decodeHtmlText(String htmlText) {
        if (htmlText.length() == 0 || htmlText.indexOf(38) == -1) {
            return htmlText;
        }
        StringBuilder output = new StringBuilder();
        int lastMatchEnd = 0;
        Matcher matcher = HTML_ENTITY_PATTERN.matcher(htmlText);
        while (matcher.find()) {
            String entity = matcher.group(1);
            Character convertedEntity = null;
            if (entity.startsWith("#x")) {
                String hhhh = entity.substring(2);
                try {
                    System.out.println("hex number is " + hhhh);
                    convertedEntity = Character.valueOf((char) Integer.parseInt(hhhh, 16));
                } catch (NumberFormatException e) {
                }
            } else if (entity.startsWith("#")) {
                try {
                    convertedEntity = Character.valueOf((char) Integer.parseInt(entity.substring(1)));
                } catch (NumberFormatException e2) {
                }
            } else {
                convertedEntity = (Character) lookup.get(entity);
            }
            if (convertedEntity != null) {
                output.append(htmlText.substring(lastMatchEnd, matcher.start()));
                output.append(convertedEntity);
                lastMatchEnd = matcher.end();
            }
        }
        if (lastMatchEnd < htmlText.length()) {
            output.append(htmlText.substring(lastMatchEnd));
        }
        return output.toString();
    }
}
