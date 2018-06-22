package gnu.bytecode;

public class SwitchState {
    Label after_label;
    Label cases_label;
    Label defaultLabel;
    Label[] labels;
    int maxValue;
    int minValue;
    int numCases = 0;
    TryState outerTry;
    Label switch_label;
    int[] values;

    public int getMaxValue() {
        return this.maxValue;
    }

    public int getNumCases() {
        return this.numCases;
    }

    public SwitchState(CodeAttr code) {
        this.switch_label = new Label(code);
        this.cases_label = new Label(code);
        this.after_label = new Label(code);
        this.outerTry = code.try_stack;
    }

    public void switchValuePushed(CodeAttr code) {
        code.popType();
        this.cases_label.setTypes(code);
        code.fixupChain(this.cases_label, this.switch_label);
    }

    public boolean addCase(int value, CodeAttr code) {
        Label label = new Label(code);
        label.setTypes(this.cases_label);
        label.define(code);
        return insertCase(value, label, code);
    }

    public boolean addCaseGoto(int value, CodeAttr code, Label label) {
        boolean ok = insertCase(value, label, code);
        label.setTypes(this.cases_label);
        code.setUnreachable();
        return ok;
    }

    public void addDefault(CodeAttr code) {
        Label label = new Label(code);
        label.setTypes(this.cases_label);
        label.define(code);
        if (this.defaultLabel != null) {
            throw new Error();
        }
        this.defaultLabel = label;
    }

    public boolean insertCase(int value, Label label, CodeAttr code) {
        if (this.values == null) {
            this.values = new int[10];
            this.labels = new Label[10];
            this.numCases = 1;
            this.maxValue = value;
            this.minValue = value;
            this.values[0] = value;
            this.labels[0] = label;
            return true;
        }
        int copyBefore;
        int[] old_values = this.values;
        Label[] old_labels = this.labels;
        if (this.numCases >= this.values.length) {
            this.values = new int[(this.numCases * 2)];
            this.labels = new Label[(this.numCases * 2)];
        }
        if (value < this.minValue) {
            copyBefore = 0;
            this.minValue = value;
        } else if (value > this.maxValue) {
            copyBefore = this.numCases;
            this.maxValue = value;
        } else {
            int low = 0;
            int hi = this.numCases - 1;
            copyBefore = 0;
            while (low <= hi) {
                copyBefore = (low + hi) >>> 1;
                if (old_values[copyBefore] >= value) {
                    hi = copyBefore - 1;
                } else {
                    copyBefore++;
                    low = copyBefore;
                }
            }
            if (value == old_values[copyBefore]) {
                return false;
            }
        }
        int copyAfter = this.numCases - copyBefore;
        System.arraycopy(old_values, copyBefore, this.values, copyBefore + 1, copyAfter);
        System.arraycopy(old_values, 0, this.values, 0, copyBefore);
        this.values[copyBefore] = value;
        System.arraycopy(old_labels, copyBefore, this.labels, copyBefore + 1, copyAfter);
        System.arraycopy(old_labels, 0, this.labels, 0, copyBefore);
        this.labels[copyBefore] = label;
        this.numCases++;
        return true;
    }

    public void exitSwitch(CodeAttr code) {
        if (this.outerTry != code.try_stack) {
            throw new Error("exitSwitch cannot exit through a try");
        }
        code.emitGoto(this.after_label);
    }

    public void finish(CodeAttr code) {
        if (this.defaultLabel == null) {
            this.defaultLabel = new Label(code);
            this.defaultLabel.define(code);
            Type ex = ClassType.make("java.lang.RuntimeException");
            code.emitNew(ex);
            code.emitDup(ex);
            code.emitPushString("bad case value!");
            code.emitInvokeSpecial(ex.addMethod("<init>", 1, new Type[]{Type.string_type}, Type.voidType));
            code.emitThrow();
        }
        code.fixupChain(this.switch_label, this.after_label);
        if (this.numCases <= 1) {
            code.pushType(Type.intType);
            if (this.numCases == 1) {
                if (this.minValue == 0) {
                    code.emitIfIntEqZero();
                } else {
                    code.emitPushInt(this.minValue);
                    code.emitIfEq();
                }
                code.emitGoto(this.labels[0]);
                code.emitElse();
                code.emitGoto(this.defaultLabel);
                code.emitFi();
            } else {
                code.emitPop(1);
                code.emitGoto(this.defaultLabel);
            }
        } else if (this.numCases * 2 >= this.maxValue - this.minValue) {
            code.reserve((((this.maxValue - this.minValue) + 1) * 4) + 13);
            code.fixupAdd(2, null);
            code.put1(170);
            code.fixupAdd(3, this.defaultLabel);
            code.PC += 4;
            code.put4(this.minValue);
            code.put4(this.maxValue);
            index = 0;
            for (int i = this.minValue; i <= this.maxValue; i++) {
                Label lab;
                if (this.values[index] == i) {
                    int index = index + 1;
                    lab = this.labels[index];
                    index = index;
                } else {
                    lab = this.defaultLabel;
                }
                code.fixupAdd(3, lab);
                code.PC += 4;
            }
        } else {
            code.reserve((this.numCases * 8) + 9);
            code.fixupAdd(2, null);
            code.put1(171);
            code.fixupAdd(3, this.defaultLabel);
            code.PC += 4;
            code.put4(this.numCases);
            for (index = 0; index < this.numCases; index++) {
                code.put4(this.values[index]);
                code.fixupAdd(3, this.labels[index]);
                code.PC += 4;
            }
        }
        code.fixupChain(this.after_label, this.cases_label);
    }
}
