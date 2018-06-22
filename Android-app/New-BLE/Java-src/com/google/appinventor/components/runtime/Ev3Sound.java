package com.google.appinventor.components.runtime;

import android.support.v4.internal.view.SupportMenu;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to sound functionalities on LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.WRITE_EXTERNAL_STORAGE,android.permission.READ_EXTERNAL_STORAGE")
public class Ev3Sound extends LegoMindstormsEv3Base {
    public Ev3Sound(ComponentContainer container) {
        super(container, "Ev3Sound");
    }

    @SimpleFunction(description = "Make the robot play a tone.")
    public void PlayTone(int volume, int frequency, int milliseconds) {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        if (volume < 0 || volume > 100 || frequency < 250 || frequency > 10000 || milliseconds < 0 || milliseconds > SupportMenu.USER_MASK) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
            return;
        }
        int i = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -108, true, 0, i, "cccc", Byte.valueOf((byte) 1), Byte.valueOf((byte) volume), Short.valueOf((short) frequency), Short.valueOf((short) milliseconds)), true);
    }

    @SimpleFunction(description = "Stop any sound on the robot.")
    public void StopSound() {
        int i = 0;
        int i2 = 0;
        sendCommand(Thread.currentThread().getStackTrace()[1].getMethodName(), Ev3BinaryParser.encodeDirectCommand((byte) -108, false, i, i2, "c", Byte.valueOf((byte) 0)), false);
    }
}
