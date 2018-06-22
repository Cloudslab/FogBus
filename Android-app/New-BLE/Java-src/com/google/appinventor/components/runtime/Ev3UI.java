package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a LEGO MINDSTORMS EV3 robot, with functions to draw graphs on EV3 screen.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.WRITE_EXTERNAL_STORAGE,android.permission.READ_EXTERNAL_STORAGE")
public class Ev3UI extends LegoMindstormsEv3Base {
    public Ev3UI(ComponentContainer container) {
        super(container, "Ev3UI");
    }

    @SimpleFunction(description = "Draw a point on the screen.")
    public void DrawPoint(int color, int x, int y) {
        String functionName = "DrawPoint";
        if (color == 0 || color == 1) {
            int i = 0;
            int i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "cccc", Byte.valueOf((byte) 2), Byte.valueOf((byte) color), Short.valueOf((short) x), Short.valueOf((short) y)), false);
            i = 0;
            i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
    }

    @SimpleFunction(description = "Draw a built-in icon on screen.")
    public void DrawIcon(int color, int x, int y, int type, int no) {
        String functionName = "DrawIcon";
        if (color == 0 || color == 1) {
            int i = 0;
            int i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "cccccc", Byte.valueOf((byte) 6), Byte.valueOf((byte) color), Short.valueOf((short) x), Short.valueOf((short) y), Integer.valueOf(type), Integer.valueOf(no)), false);
            i = 0;
            i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
    }

    @SimpleFunction(description = "Draw a line on the screen.")
    public void DrawLine(int color, int x1, int y1, int x2, int y2) {
        String functionName = "DrawLine";
        if (color == 0 || color == 1) {
            int i = 0;
            int i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "cccccc", Byte.valueOf((byte) 3), Byte.valueOf((byte) color), Short.valueOf((short) x1), Short.valueOf((short) y1), Short.valueOf((short) x2), Short.valueOf((short) y2)), false);
            i = 0;
            i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
    }

    @SimpleFunction(description = "Draw a rectangle on the screen.")
    public void DrawRect(int color, int x, int y, int width, int height, boolean fill) {
        String functionName = "DrawRect";
        if (color == 0 || color == 1) {
            byte b;
            String str = "cccccc";
            Object[] objArr = new Object[6];
            if (fill) {
                b = (byte) 9;
            } else {
                b = (byte) 10;
            }
            objArr[0] = Byte.valueOf(b);
            objArr[1] = Byte.valueOf((byte) color);
            objArr[2] = Short.valueOf((short) x);
            objArr[3] = Short.valueOf((short) y);
            objArr[4] = Short.valueOf((short) width);
            objArr[5] = Short.valueOf((short) height);
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, 0, 0, str, objArr), false);
            int i = 0;
            int i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
    }

    @SimpleFunction(description = "Draw a circle on the screen.")
    public void DrawCircle(int color, int x, int y, int radius, boolean fill) {
        String functionName = "DrawCircle";
        if ((color == 0 || color == 1) && radius >= 0) {
            byte b;
            String str = "ccccc";
            Object[] objArr = new Object[5];
            if (fill) {
                b = (byte) 24;
            } else {
                b = (byte) 4;
            }
            objArr[0] = Byte.valueOf(b);
            objArr[1] = Byte.valueOf((byte) color);
            objArr[2] = Short.valueOf((short) x);
            objArr[3] = Short.valueOf((short) y);
            objArr[4] = Short.valueOf((short) radius);
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, 0, 0, str, objArr), false);
            int i = 0;
            int i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
    }

    @SimpleFunction(description = "Fill the screen with a color.")
    public void FillScreen(int color) {
        String functionName = "FillScreen";
        if (color == 0 || color == 1) {
            int i = 0;
            int i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "cccc", Byte.valueOf((byte) 19), Byte.valueOf((byte) color), Short.valueOf((short) 0), Short.valueOf((short) 0)), false);
            i = 0;
            i2 = 0;
            sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.UI_DRAW, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
    }
}
