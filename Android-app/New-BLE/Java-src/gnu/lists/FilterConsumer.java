package gnu.lists;

public class FilterConsumer implements XConsumer {
    protected Object attributeType;
    protected Consumer base;
    protected boolean inAttribute;
    protected boolean skipping;

    public FilterConsumer(Consumer base) {
        this.base = base;
    }

    protected void beforeContent() {
    }

    protected void beforeNode() {
    }

    public void write(int v) {
        beforeContent();
        if (!this.skipping) {
            this.base.write(v);
        }
    }

    public void writeBoolean(boolean v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeBoolean(v);
        }
    }

    public void writeFloat(float v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeFloat(v);
        }
    }

    public void writeDouble(double v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeDouble(v);
        }
    }

    public void writeInt(int v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeInt(v);
        }
    }

    public void writeLong(long v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeLong(v);
        }
    }

    public void startDocument() {
        if (!this.skipping) {
            this.base.startDocument();
        }
    }

    public void endDocument() {
        if (!this.skipping) {
            this.base.endDocument();
        }
    }

    public void startElement(Object type) {
        if (!this.skipping) {
            beforeNode();
            this.base.startElement(type);
        }
    }

    public void endElement() {
        if (!this.skipping) {
            this.base.endElement();
        }
    }

    public void startAttribute(Object attrType) {
        this.attributeType = attrType;
        this.inAttribute = true;
        if (!this.skipping) {
            beforeNode();
            this.base.startAttribute(attrType);
        }
    }

    public void endAttribute() {
        if (!this.skipping) {
            this.base.endAttribute();
        }
        this.inAttribute = false;
    }

    public void writeComment(char[] chars, int offset, int length) {
        if (!this.skipping) {
            beforeNode();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeComment(chars, offset, length);
            }
        }
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        if (!this.skipping) {
            beforeNode();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeProcessingInstruction(target, content, offset, length);
            }
        }
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        beforeContent();
        if (!this.skipping) {
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeCDATA(chars, offset, length);
            } else {
                this.base.write(chars, offset, length);
            }
        }
    }

    public void beginEntity(Object baseUri) {
        if (!this.skipping) {
            beforeNode();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).beginEntity(baseUri);
            }
        }
    }

    public void endEntity() {
        if (!this.skipping && (this.base instanceof XConsumer)) {
            ((XConsumer) this.base).endEntity();
        }
    }

    public void writeObject(Object v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeObject(v);
        }
    }

    public boolean ignoring() {
        return this.base.ignoring();
    }

    public void write(char[] buf, int off, int len) {
        beforeContent();
        if (!this.skipping) {
            this.base.write(buf, off, len);
        }
    }

    public void write(String str) {
        write((CharSequence) str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        beforeContent();
        if (!this.skipping) {
            this.base.write(str, start, length);
        }
    }

    public Consumer append(char c) {
        write((int) c);
        return this;
    }

    public Consumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, 0, csq.length());
        return this;
    }

    public Consumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, start, end - start);
        return this;
    }
}
