package gnu.kawa.swingviews;

import gnu.lists.CharBuffer;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.undo.UndoableEdit;

public class SwingContent implements Content {
    public final CharBuffer buffer;

    public SwingContent(CharBuffer buffer) {
        this.buffer = buffer;
    }

    public SwingContent(int initialSize) {
        CharBuffer b = new CharBuffer(initialSize);
        b.gapEnd = initialSize - 1;
        b.getArray()[b.gapEnd] = '\n';
        this.buffer = b;
    }

    public SwingContent() {
        this(100);
    }

    public int length() {
        return this.buffer.length();
    }

    public void getChars(int where, int len, Segment txt) throws BadLocationException {
        CharBuffer b = this.buffer;
        int start = b.getSegment(where, len);
        if (start < 0) {
            throw new BadLocationException("invalid offset", where);
        }
        txt.offset = start;
        txt.array = b.getArray();
        txt.count = len;
    }

    public String getString(int where, int len) throws BadLocationException {
        CharBuffer b = this.buffer;
        int start = b.getSegment(where, len);
        if (start >= 0) {
            return new String(b.getArray(), start, len);
        }
        throw new BadLocationException("invalid offset", where);
    }

    public UndoableEdit remove(int where, int nitems) throws BadLocationException {
        CharBuffer b = this.buffer;
        if (nitems < 0 || where < 0 || where + nitems > b.length()) {
            throw new BadLocationException("invalid remove", where);
        }
        b.delete(where, nitems);
        GapUndoableEdit undo = new GapUndoableEdit(where);
        undo.content = this;
        undo.data = new String(b.getArray(), b.gapEnd - nitems, nitems);
        undo.nitems = nitems;
        undo.isInsertion = false;
        return undo;
    }

    public UndoableEdit insertString(int where, String str, boolean beforeMarkers) throws BadLocationException {
        CharBuffer b = this.buffer;
        if (where < 0 || where > b.length()) {
            throw new BadLocationException("bad insert", where);
        }
        b.insert(where, str, beforeMarkers);
        GapUndoableEdit undo = new GapUndoableEdit(where);
        undo.content = this;
        undo.data = str;
        undo.nitems = str.length();
        undo.isInsertion = true;
        return undo;
    }

    public UndoableEdit insertString(int where, String str) throws BadLocationException {
        return insertString(where, str, false);
    }

    public Position createPosition(int offset) throws BadLocationException {
        CharBuffer b = this.buffer;
        if (offset >= 0 && offset <= b.length()) {
            return new GapPosition(b, offset);
        }
        throw new BadLocationException("bad offset to createPosition", offset);
    }
}
