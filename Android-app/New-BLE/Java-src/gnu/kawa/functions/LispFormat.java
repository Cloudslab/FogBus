package gnu.kawa.functions;

import android.support.v4.media.TransportMediator;
import gnu.bytecode.Access;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.math.IntNum;
import gnu.text.CaseConvertFormat;
import gnu.text.Char;
import gnu.text.CompoundFormat;
import gnu.text.FlushFormat;
import gnu.text.LiteralFormat;
import gnu.text.ReportFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Stack;
import java.util.Vector;

public class LispFormat extends CompoundFormat {
    public static final String paramFromCount = "<from count>";
    public static final String paramFromList = "<from list>";
    public static final String paramUnspecified = "<unspecified>";

    public LispFormat(char[] format, int offset, int length) throws ParseException {
        super(null, 0);
        int start_nesting = -1;
        int choices_seen = 0;
        StringBuffer stringBuffer = new StringBuffer(100);
        Vector stack = new Stack();
        int limit = offset + length;
        int i = offset;
        while (true) {
            if ((i >= limit || format[i] == '~') && stringBuffer.length() > 0) {
                stack.push(new LiteralFormat(stringBuffer));
                stringBuffer.setLength(0);
            }
            if (i < limit) {
                int i2 = i + 1;
                char ch = format[i];
                if (ch != '~') {
                    stringBuffer.append(ch);
                    i = i2;
                } else {
                    int speci = stack.size();
                    i = i2 + 1;
                    ch = format[i2];
                    while (true) {
                        boolean seenColon;
                        boolean seenAt;
                        int numParams;
                        Format fmt;
                        Format dfmt;
                        int count;
                        CaseConvertFormat caseConvertFormat;
                        LispChoiceFormat afmt;
                        LispPrettyFormat pfmt;
                        Format lfmt;
                        Format fmt2;
                        int argstart;
                        int argstart2;
                        int base;
                        int minWidth;
                        int padChar;
                        int commaChar;
                        int commaInterval;
                        int flags;
                        int param1;
                        int kind;
                        LispIterationFormat lfmt2;
                        int charVal;
                        if (ch == '#') {
                            stack.push(paramFromCount);
                            i2 = i + 1;
                            ch = format[i];
                            i = i2;
                        } else if (ch == 'v' || ch == 'V') {
                            stack.push(paramFromList);
                            i2 = i + 1;
                            ch = format[i];
                            i = i2;
                        } else if (ch == '-' || Character.digit(ch, 10) >= 0) {
                            boolean neg = ch == '-';
                            if (neg) {
                                i2 = i + 1;
                                ch = format[i];
                            } else {
                                i2 = i;
                            }
                            int val = 0;
                            int start = i2;
                            while (true) {
                                int dig = Character.digit(ch, 10);
                                if (dig < 0) {
                                    Object make;
                                    if (i2 - start < 8) {
                                        if (neg) {
                                            val = -val;
                                        }
                                        make = IntNum.make(val);
                                    } else {
                                        make = IntNum.valueOf(format, start, i2 - start, 10, neg);
                                    }
                                    stack.push(make);
                                    i = i2;
                                } else {
                                    val = (val * 10) + dig;
                                    i = i2 + 1;
                                    ch = format[i2];
                                    i2 = i;
                                }
                            }
                        } else if (ch == '\'') {
                            i2 = i + 1;
                            stack.push(Char.make(format[i]));
                            i = i2 + 1;
                            ch = format[i2];
                        } else if (ch == ',') {
                            stack.push(paramUnspecified);
                        } else {
                            i2 = i;
                            seenColon = false;
                            seenAt = false;
                            i = i2;
                            while (true) {
                                if (ch == ':') {
                                    seenColon = true;
                                } else if (ch != '@') {
                                    seenAt = true;
                                } else {
                                    ch = Character.toUpperCase(ch);
                                    numParams = stack.size() - speci;
                                    switch (ch) {
                                        case '\n':
                                            if (seenAt) {
                                                stringBuffer.append(ch);
                                            }
                                            if (!seenColon) {
                                                while (i < limit) {
                                                    i2 = i + 1;
                                                    if (!Character.isWhitespace(format[i])) {
                                                        i = i2 - 1;
                                                        break;
                                                    }
                                                    i = i2;
                                                }
                                                break;
                                            }
                                            continue;
                                            continue;
                                        case '!':
                                            fmt = FlushFormat.getInstance();
                                            break;
                                        case '$':
                                        case 'E':
                                        case 'F':
                                        case 'G':
                                            dfmt = new LispRealFormat();
                                            dfmt.op = ch;
                                            dfmt.arg1 = getParam(stack, speci);
                                            dfmt.arg2 = getParam(stack, speci + 1);
                                            dfmt.arg3 = getParam(stack, speci + 2);
                                            dfmt.arg4 = getParam(stack, speci + 3);
                                            if (ch != '$') {
                                                dfmt.arg5 = getParam(stack, speci + 4);
                                                if (ch == 'E' || ch == 'G') {
                                                    dfmt.arg6 = getParam(stack, speci + 5);
                                                    dfmt.arg7 = getParam(stack, speci + 6);
                                                }
                                            }
                                            dfmt.showPlus = seenAt;
                                            dfmt.internalPad = seenColon;
                                            if (dfmt.argsUsed != 0) {
                                                fmt = dfmt;
                                                break;
                                            } else {
                                                fmt = dfmt.resolve(null, 0);
                                                break;
                                            }
                                        case '%':
                                            count = getParam(stack, speci);
                                            if (count == -1073741824) {
                                                count = 1;
                                            }
                                            fmt = LispNewlineFormat.getInstance(count, 76);
                                            break;
                                        case '&':
                                            fmt = new LispFreshlineFormat(getParam(stack, speci));
                                            break;
                                        case '(':
                                            ch = seenColon ? seenAt ? 'U' : Access.CLASS_CONTEXT : seenAt ? 'T' : 'L';
                                            caseConvertFormat = new CaseConvertFormat(null, ch);
                                            stack.setSize(speci);
                                            stack.push(caseConvertFormat);
                                            stack.push(IntNum.make(start_nesting));
                                            start_nesting = speci;
                                            continue;
                                            continue;
                                        case ')':
                                            if (start_nesting >= 0 || !(stack.elementAt(start_nesting) instanceof CaseConvertFormat)) {
                                                throw new ParseException("saw ~) without matching ~(", i);
                                            }
                                            ((CaseConvertFormat) stack.elementAt(start_nesting)).setBaseFormat(popFormats(stack, start_nesting + 2, speci));
                                            start_nesting = ((IntNum) stack.pop()).intValue();
                                            continue;
                                            continue;
                                        case '*':
                                            fmt = new LispRepositionFormat(getParam(stack, speci), seenColon, seenAt);
                                            break;
                                        case ';':
                                            if (start_nesting < 0) {
                                                if (!(stack.elementAt(start_nesting) instanceof LispChoiceFormat)) {
                                                    afmt = (LispChoiceFormat) stack.elementAt(start_nesting);
                                                    if (seenColon) {
                                                        afmt.lastIsDefault = true;
                                                    }
                                                    stack.push(popFormats(stack, (start_nesting + 3) + choices_seen, speci));
                                                    choices_seen++;
                                                    continue;
                                                    continue;
                                                } else if (stack.elementAt(start_nesting) instanceof LispPrettyFormat) {
                                                    pfmt = (LispPrettyFormat) stack.elementAt(start_nesting);
                                                    if (seenAt) {
                                                        pfmt.perLine = true;
                                                    }
                                                    stack.push(popFormats(stack, (start_nesting + 3) + choices_seen, speci));
                                                    choices_seen++;
                                                    break;
                                                }
                                            }
                                            throw new ParseException("saw ~; without matching ~[ or ~<", i);
                                        case '<':
                                            pfmt = new LispPrettyFormat();
                                            pfmt.seenAt = seenAt;
                                            if (seenColon) {
                                                pfmt.prefix = "";
                                                pfmt.suffix = "";
                                            } else {
                                                pfmt.prefix = "(";
                                                pfmt.suffix = ")";
                                            }
                                            stack.setSize(speci);
                                            stack.push(pfmt);
                                            stack.push(IntNum.make(start_nesting));
                                            stack.push(IntNum.make(choices_seen));
                                            start_nesting = speci;
                                            choices_seen = 0;
                                            continue;
                                            continue;
                                        case '>':
                                            if (start_nesting >= 0 || !(stack.elementAt(start_nesting) instanceof LispPrettyFormat)) {
                                                throw new ParseException("saw ~> without matching ~<", i);
                                            }
                                            stack.push(popFormats(stack, (start_nesting + 3) + choices_seen, speci));
                                            pfmt = (LispPrettyFormat) stack.elementAt(start_nesting);
                                            pfmt.segments = getFormats(stack, start_nesting + 3, stack.size());
                                            stack.setSize(start_nesting + 3);
                                            start_nesting = ((IntNum) stack.pop()).intValue();
                                            start_nesting = ((IntNum) stack.pop()).intValue();
                                            if (seenColon) {
                                                int nsegments = pfmt.segments.length;
                                                if (nsegments <= 3) {
                                                    if (nsegments < 2) {
                                                        pfmt.body = pfmt.segments[0];
                                                    } else if (pfmt.segments[0] instanceof LiteralFormat) {
                                                        pfmt.prefix = ((LiteralFormat) pfmt.segments[0]).content();
                                                        pfmt.body = pfmt.segments[1];
                                                    } else {
                                                        throw new ParseException("prefix segment is not literal", i);
                                                    }
                                                    if (nsegments >= 3) {
                                                        if (pfmt.segments[2] instanceof LiteralFormat) {
                                                            pfmt.suffix = ((LiteralFormat) pfmt.segments[2]).content();
                                                            break;
                                                        }
                                                        throw new ParseException("suffix segment is not literal", i);
                                                    }
                                                    continue;
                                                    continue;
                                                } else {
                                                    throw new ParseException("too many segments in Logical Block format", i);
                                                }
                                            }
                                            throw new ParseException("not implemented: justfication i.e. ~<...~>", i);
                                            break;
                                        case '?':
                                            lfmt = new LispIterationFormat();
                                            lfmt.seenAt = seenAt;
                                            lfmt.maxIterations = 1;
                                            lfmt.atLeastOnce = true;
                                            fmt = lfmt;
                                            break;
                                        case 'A':
                                        case 'S':
                                        case 'W':
                                        case 'Y':
                                            fmt2 = ObjectFormat.getInstance(ch == 'A');
                                            if (numParams <= 0) {
                                                fmt = fmt2;
                                                break;
                                            } else {
                                                fmt = new LispObjectFormat((ReportFormat) fmt2, getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2), getParam(stack, speci + 3), seenAt ? 0 : 100);
                                                break;
                                            }
                                        case 'B':
                                        case 'D':
                                        case 'O':
                                        case 'R':
                                        case 'X':
                                            argstart = speci;
                                            if (ch == 'R') {
                                                argstart2 = argstart + 1;
                                                base = getParam(stack, argstart);
                                                argstart = argstart2;
                                            } else if (ch == 'D') {
                                                base = 10;
                                            } else if (ch == 'O') {
                                                base = 8;
                                            } else if (ch != 'X') {
                                                base = 16;
                                            } else {
                                                base = 2;
                                            }
                                            minWidth = getParam(stack, argstart);
                                            padChar = getParam(stack, argstart + 1);
                                            commaChar = getParam(stack, argstart + 2);
                                            commaInterval = getParam(stack, argstart + 3);
                                            flags = 0;
                                            if (seenColon) {
                                                flags = 0 | 1;
                                            }
                                            if (seenAt) {
                                                flags |= 2;
                                            }
                                            fmt = IntegerFormat.getInstance(base, minWidth, padChar, commaChar, commaInterval, flags);
                                            break;
                                        case 'C':
                                            fmt = LispCharacterFormat.getInstance(numParams <= 0 ? getParam(stack, speci) : -1610612736, 1, seenAt, seenColon);
                                            break;
                                        case 'I':
                                            param1 = getParam(stack, speci);
                                            if (param1 == -1073741824) {
                                                param1 = 0;
                                            }
                                            fmt = LispIndentFormat.getInstance(param1, seenColon);
                                            break;
                                        case 'P':
                                            fmt = LispPluralFormat.getInstance(seenColon, seenAt);
                                            break;
                                        case 'T':
                                            fmt = new LispTabulateFormat(getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2), seenAt);
                                            break;
                                        case '[':
                                            afmt = new LispChoiceFormat();
                                            afmt.param = getParam(stack, speci);
                                            if (afmt.param == -1073741824) {
                                                afmt.param = -1610612736;
                                            }
                                            if (seenColon) {
                                                afmt.testBoolean = true;
                                            }
                                            if (seenAt) {
                                                afmt.skipIfFalse = true;
                                            }
                                            stack.setSize(speci);
                                            stack.push(afmt);
                                            stack.push(IntNum.make(start_nesting));
                                            stack.push(IntNum.make(choices_seen));
                                            start_nesting = speci;
                                            choices_seen = 0;
                                            continue;
                                            continue;
                                        case ']':
                                            if (start_nesting >= 0 || !(stack.elementAt(start_nesting) instanceof LispChoiceFormat)) {
                                                throw new ParseException("saw ~] without matching ~[", i);
                                            }
                                            stack.push(popFormats(stack, (start_nesting + 3) + choices_seen, speci));
                                            ((LispChoiceFormat) stack.elementAt(start_nesting)).choices = getFormats(stack, start_nesting + 3, stack.size());
                                            stack.setSize(start_nesting + 3);
                                            choices_seen = ((IntNum) stack.pop()).intValue();
                                            start_nesting = ((IntNum) stack.pop()).intValue();
                                            continue;
                                            continue;
                                        case '^':
                                            fmt = new LispEscapeFormat(getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2));
                                            break;
                                        case '_':
                                            param1 = getParam(stack, speci);
                                            if (param1 == -1073741824) {
                                                param1 = 1;
                                            }
                                            if (seenColon || !seenAt) {
                                            }
                                            if (!seenAt && seenColon) {
                                                kind = 82;
                                            } else if (seenAt) {
                                                kind = 77;
                                            } else if (seenColon) {
                                                kind = 78;
                                            } else {
                                                kind = 70;
                                            }
                                            fmt = LispNewlineFormat.getInstance(param1, kind);
                                            break;
                                        case '{':
                                            lfmt2 = new LispIterationFormat();
                                            lfmt2.seenAt = seenAt;
                                            lfmt2.seenColon = seenColon;
                                            lfmt2.maxIterations = getParam(stack, speci);
                                            stack.setSize(speci);
                                            stack.push(lfmt2);
                                            stack.push(IntNum.make(start_nesting));
                                            start_nesting = speci;
                                            continue;
                                            continue;
                                        case '|':
                                            break;
                                        case '}':
                                            if (start_nesting >= 0 || !(stack.elementAt(start_nesting) instanceof LispIterationFormat)) {
                                                throw new ParseException("saw ~} without matching ~{", i);
                                            }
                                            lfmt2 = (LispIterationFormat) stack.elementAt(start_nesting);
                                            lfmt2.atLeastOnce = seenColon;
                                            if (speci > start_nesting + 2) {
                                                lfmt2.body = popFormats(stack, start_nesting + 2, speci);
                                            }
                                            start_nesting = ((IntNum) stack.pop()).intValue();
                                            continue;
                                            continue;
                                        case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                                            if (numParams == 0) {
                                                stringBuffer.append(ch);
                                                continue;
                                                continue;
                                            }
                                            break;
                                        default:
                                            throw new ParseException("unrecognized format specifier ~" + ch, i);
                                    }
                                    count = getParam(stack, speci);
                                    if (count == -1073741824) {
                                        count = 1;
                                    }
                                    charVal = getParam(stack, speci + 1);
                                    if (charVal == -1073741824) {
                                        charVal = ch != '|' ? 12 : TransportMediator.KEYCODE_MEDIA_PLAY;
                                    }
                                    fmt = LispCharacterFormat.getInstance(charVal, count, false, false);
                                    stack.setSize(speci);
                                    stack.push(fmt);
                                }
                                i2 = i + 1;
                                ch = format[i];
                                i = i2;
                            }
                        }
                        if (ch != ',') {
                            i2 = i;
                            seenColon = false;
                            seenAt = false;
                            i = i2;
                            while (true) {
                                if (ch == ':') {
                                    seenColon = true;
                                } else if (ch != '@') {
                                    ch = Character.toUpperCase(ch);
                                    numParams = stack.size() - speci;
                                    switch (ch) {
                                        case '\n':
                                            if (seenAt) {
                                                stringBuffer.append(ch);
                                            }
                                            if (!seenColon) {
                                                while (i < limit) {
                                                    i2 = i + 1;
                                                    if (!Character.isWhitespace(format[i])) {
                                                        i = i2 - 1;
                                                        break;
                                                    }
                                                    i = i2;
                                                }
                                                break;
                                            }
                                            continue;
                                            continue;
                                        case '!':
                                            fmt = FlushFormat.getInstance();
                                            break;
                                        case '$':
                                        case 'E':
                                        case 'F':
                                        case 'G':
                                            dfmt = new LispRealFormat();
                                            dfmt.op = ch;
                                            dfmt.arg1 = getParam(stack, speci);
                                            dfmt.arg2 = getParam(stack, speci + 1);
                                            dfmt.arg3 = getParam(stack, speci + 2);
                                            dfmt.arg4 = getParam(stack, speci + 3);
                                            if (ch != '$') {
                                                dfmt.arg5 = getParam(stack, speci + 4);
                                                dfmt.arg6 = getParam(stack, speci + 5);
                                                dfmt.arg7 = getParam(stack, speci + 6);
                                                break;
                                            }
                                            dfmt.showPlus = seenAt;
                                            dfmt.internalPad = seenColon;
                                            if (dfmt.argsUsed != 0) {
                                                fmt = dfmt.resolve(null, 0);
                                                break;
                                            } else {
                                                fmt = dfmt;
                                                break;
                                            }
                                        case '%':
                                            count = getParam(stack, speci);
                                            if (count == -1073741824) {
                                                count = 1;
                                            }
                                            fmt = LispNewlineFormat.getInstance(count, 76);
                                            break;
                                        case '&':
                                            fmt = new LispFreshlineFormat(getParam(stack, speci));
                                            break;
                                        case '(':
                                            if (seenColon) {
                                                if (seenAt) {
                                                }
                                            }
                                            caseConvertFormat = new CaseConvertFormat(null, ch);
                                            stack.setSize(speci);
                                            stack.push(caseConvertFormat);
                                            stack.push(IntNum.make(start_nesting));
                                            start_nesting = speci;
                                            continue;
                                            continue;
                                        case ')':
                                            if (start_nesting >= 0) {
                                                break;
                                            }
                                            throw new ParseException("saw ~) without matching ~(", i);
                                        case '*':
                                            fmt = new LispRepositionFormat(getParam(stack, speci), seenColon, seenAt);
                                            break;
                                        case ';':
                                            if (start_nesting < 0) {
                                                if (!(stack.elementAt(start_nesting) instanceof LispChoiceFormat)) {
                                                    if (stack.elementAt(start_nesting) instanceof LispPrettyFormat) {
                                                        pfmt = (LispPrettyFormat) stack.elementAt(start_nesting);
                                                        if (seenAt) {
                                                            pfmt.perLine = true;
                                                        }
                                                        stack.push(popFormats(stack, (start_nesting + 3) + choices_seen, speci));
                                                        choices_seen++;
                                                        break;
                                                    }
                                                } else {
                                                    afmt = (LispChoiceFormat) stack.elementAt(start_nesting);
                                                    if (seenColon) {
                                                        afmt.lastIsDefault = true;
                                                    }
                                                    stack.push(popFormats(stack, (start_nesting + 3) + choices_seen, speci));
                                                    choices_seen++;
                                                    continue;
                                                    continue;
                                                }
                                            }
                                            throw new ParseException("saw ~; without matching ~[ or ~<", i);
                                        case '<':
                                            pfmt = new LispPrettyFormat();
                                            pfmt.seenAt = seenAt;
                                            if (seenColon) {
                                                pfmt.prefix = "";
                                                pfmt.suffix = "";
                                            } else {
                                                pfmt.prefix = "(";
                                                pfmt.suffix = ")";
                                            }
                                            stack.setSize(speci);
                                            stack.push(pfmt);
                                            stack.push(IntNum.make(start_nesting));
                                            stack.push(IntNum.make(choices_seen));
                                            start_nesting = speci;
                                            choices_seen = 0;
                                            continue;
                                            continue;
                                        case '>':
                                            if (start_nesting >= 0) {
                                                break;
                                            }
                                            throw new ParseException("saw ~> without matching ~<", i);
                                        case '?':
                                            lfmt = new LispIterationFormat();
                                            lfmt.seenAt = seenAt;
                                            lfmt.maxIterations = 1;
                                            lfmt.atLeastOnce = true;
                                            fmt = lfmt;
                                            break;
                                        case 'A':
                                        case 'S':
                                        case 'W':
                                        case 'Y':
                                            if (ch == 'A') {
                                            }
                                            fmt2 = ObjectFormat.getInstance(ch == 'A');
                                            if (numParams <= 0) {
                                                if (seenAt) {
                                                }
                                                fmt = new LispObjectFormat((ReportFormat) fmt2, getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2), getParam(stack, speci + 3), seenAt ? 0 : 100);
                                                break;
                                            } else {
                                                fmt = fmt2;
                                                break;
                                            }
                                        case 'B':
                                        case 'D':
                                        case 'O':
                                        case 'R':
                                        case 'X':
                                            argstart = speci;
                                            if (ch == 'R') {
                                                argstart2 = argstart + 1;
                                                base = getParam(stack, argstart);
                                                argstart = argstart2;
                                            } else if (ch == 'D') {
                                                base = 10;
                                            } else if (ch == 'O') {
                                                base = 8;
                                            } else if (ch != 'X') {
                                                base = 2;
                                            } else {
                                                base = 16;
                                            }
                                            minWidth = getParam(stack, argstart);
                                            padChar = getParam(stack, argstart + 1);
                                            commaChar = getParam(stack, argstart + 2);
                                            commaInterval = getParam(stack, argstart + 3);
                                            flags = 0;
                                            if (seenColon) {
                                                flags = 0 | 1;
                                            }
                                            if (seenAt) {
                                                flags |= 2;
                                            }
                                            fmt = IntegerFormat.getInstance(base, minWidth, padChar, commaChar, commaInterval, flags);
                                            break;
                                        case 'C':
                                            if (numParams <= 0) {
                                            }
                                            fmt = LispCharacterFormat.getInstance(numParams <= 0 ? getParam(stack, speci) : -1610612736, 1, seenAt, seenColon);
                                            break;
                                        case 'I':
                                            param1 = getParam(stack, speci);
                                            if (param1 == -1073741824) {
                                                param1 = 0;
                                            }
                                            fmt = LispIndentFormat.getInstance(param1, seenColon);
                                            break;
                                        case 'P':
                                            fmt = LispPluralFormat.getInstance(seenColon, seenAt);
                                            break;
                                        case 'T':
                                            fmt = new LispTabulateFormat(getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2), seenAt);
                                            break;
                                        case '[':
                                            afmt = new LispChoiceFormat();
                                            afmt.param = getParam(stack, speci);
                                            if (afmt.param == -1073741824) {
                                                afmt.param = -1610612736;
                                            }
                                            if (seenColon) {
                                                afmt.testBoolean = true;
                                            }
                                            if (seenAt) {
                                                afmt.skipIfFalse = true;
                                            }
                                            stack.setSize(speci);
                                            stack.push(afmt);
                                            stack.push(IntNum.make(start_nesting));
                                            stack.push(IntNum.make(choices_seen));
                                            start_nesting = speci;
                                            choices_seen = 0;
                                            continue;
                                            continue;
                                        case ']':
                                            if (start_nesting >= 0) {
                                                break;
                                            }
                                            throw new ParseException("saw ~] without matching ~[", i);
                                        case '^':
                                            fmt = new LispEscapeFormat(getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2));
                                            break;
                                        case '_':
                                            param1 = getParam(stack, speci);
                                            if (param1 == -1073741824) {
                                                param1 = 1;
                                            }
                                            if (seenColon) {
                                            }
                                            if (!seenAt) {
                                            }
                                            if (seenAt) {
                                                kind = 77;
                                            } else if (seenColon) {
                                                kind = 78;
                                            } else {
                                                kind = 70;
                                            }
                                            fmt = LispNewlineFormat.getInstance(param1, kind);
                                            break;
                                        case '{':
                                            lfmt2 = new LispIterationFormat();
                                            lfmt2.seenAt = seenAt;
                                            lfmt2.seenColon = seenColon;
                                            lfmt2.maxIterations = getParam(stack, speci);
                                            stack.setSize(speci);
                                            stack.push(lfmt2);
                                            stack.push(IntNum.make(start_nesting));
                                            start_nesting = speci;
                                            continue;
                                            continue;
                                        case '|':
                                            break;
                                        case '}':
                                            if (start_nesting >= 0) {
                                                break;
                                            }
                                            throw new ParseException("saw ~} without matching ~{", i);
                                        case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                                            if (numParams == 0) {
                                                stringBuffer.append(ch);
                                                continue;
                                                continue;
                                            }
                                            break;
                                        default:
                                            throw new ParseException("unrecognized format specifier ~" + ch, i);
                                    }
                                    count = getParam(stack, speci);
                                    if (count == -1073741824) {
                                        count = 1;
                                    }
                                    charVal = getParam(stack, speci + 1);
                                    if (charVal == -1073741824) {
                                        if (ch != '|') {
                                        }
                                    }
                                    fmt = LispCharacterFormat.getInstance(charVal, count, false, false);
                                    stack.setSize(speci);
                                    stack.push(fmt);
                                } else {
                                    seenAt = true;
                                }
                                i2 = i + 1;
                                ch = format[i];
                                i = i2;
                            }
                        } else {
                            i2 = i + 1;
                            ch = format[i];
                            i = i2;
                        }
                    }
                }
            } else if (i > limit) {
                throw new IndexOutOfBoundsException();
            } else if (start_nesting >= 0) {
                throw new ParseException("missing ~] or ~}", i);
            } else {
                this.length = stack.size();
                this.formats = new Format[this.length];
                stack.copyInto(this.formats);
                return;
            }
        }
    }

    static Format[] getFormats(Vector vector, int start, int end) {
        Format[] f = new Format[(end - start)];
        for (int i = start; i < end; i++) {
            f[i - start] = (Format) vector.elementAt(i);
        }
        return f;
    }

    static Format popFormats(Vector vector, int start, int end) {
        Format f;
        if (end == start + 1) {
            f = (Format) vector.elementAt(start);
        } else {
            f = new CompoundFormat(getFormats(vector, start, end));
        }
        vector.setSize(start);
        return f;
    }

    public LispFormat(String str) throws ParseException {
        this(str.toCharArray());
    }

    public LispFormat(char[] format) throws ParseException {
        this(format, 0, format.length);
    }

    public static int getParam(Vector vec, int index) {
        if (index >= vec.size()) {
            return -1073741824;
        }
        String arg = vec.elementAt(index);
        if (arg == paramFromList) {
            return -1610612736;
        }
        if (arg == paramFromCount) {
            return ReportFormat.PARAM_FROM_COUNT;
        }
        if (arg != paramUnspecified) {
            return ReportFormat.getParam(arg, -1073741824);
        }
        return -1073741824;
    }

    public static Object[] asArray(Object arg) {
        if (arg instanceof Object[]) {
            return (Object[]) arg;
        }
        if (!(arg instanceof Sequence)) {
            return null;
        }
        Pair arg2;
        int count = ((Sequence) arg).size();
        Object[] arr = new Object[count];
        int i = 0;
        while (arg2 instanceof Pair) {
            Pair pair = arg2;
            int i2 = i + 1;
            arr[i] = pair.getCar();
            arg2 = pair.getCdr();
            i = i2;
        }
        if (i < count) {
            if (!(arg2 instanceof Sequence)) {
                return null;
            }
            int npairs = i;
            Sequence seq = arg2;
            while (i < count) {
                arr[i] = seq.get(npairs + i);
                i++;
            }
        }
        return arr;
    }
}
