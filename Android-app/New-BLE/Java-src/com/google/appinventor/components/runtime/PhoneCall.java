package com.google.appinventor.components.runtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.PhoneCallUtil;

@DesignerComponent(category = ComponentCategory.SOCIAL, description = "<p>A non-visible component that makes a phone call to the number specified in the <code>PhoneNumber</code> property, which can be set either in the Designer or Blocks Editor. The component has a <code>MakePhoneCall</code> method, enabling the program to launch a phone call.</p><p>Often, this component is used with the <code>ContactPicker</code> component, which lets the user select a contact from the ones stored on the phone and sets the <code>PhoneNumber</code> property to the contact's phone number.</p><p>To directly specify the phone number (e.g., 650-555-1212), set the <code>PhoneNumber</code> property to a Text with the specified digits (e.g., \"6505551212\").  Dashes, dots, and parentheses may be included (e.g., \"(650)-555-1212\") but will be ignored; spaces may not be included.</p>", iconName = "images/phoneCall.png", nonVisible = true, version = 2)
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.CALL_PHONE, android.permission.READ_PHONE_STATE, android.permission.PROCESS_OUTGOING_CALLS")
public class PhoneCall extends AndroidNonvisibleComponent implements Component, OnDestroyListener {
    private final CallStateReceiver callStateReceiver = new CallStateReceiver();
    private final Context context;
    private String phoneNumber;

    private class CallStateReceiver extends BroadcastReceiver {
        private String number = "";
        private int status = 0;

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.PHONE_STATE".equals(action)) {
                String state = intent.getStringExtra("state");
                if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                    this.status = 1;
                    this.number = intent.getStringExtra("incoming_number");
                    PhoneCall.this.PhoneCallStarted(1, this.number);
                } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                    if (this.status == 1) {
                        this.status = 3;
                        PhoneCall.this.IncomingCallAnswered(this.number);
                    }
                } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                    if (this.status == 1) {
                        PhoneCall.this.PhoneCallEnded(1, this.number);
                    } else if (this.status == 3) {
                        PhoneCall.this.PhoneCallEnded(2, this.number);
                    } else if (this.status == 2) {
                        PhoneCall.this.PhoneCallEnded(3, this.number);
                    }
                    this.status = 0;
                    this.number = "";
                }
            } else if ("android.intent.action.NEW_OUTGOING_CALL".equals(action)) {
                this.status = 2;
                this.number = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
                PhoneCall.this.PhoneCallStarted(2, this.number);
            }
        }
    }

    public PhoneCall(ComponentContainer container) {
        super(container.$form());
        this.context = container.$context();
        this.form.registerForOnDestroy(this);
        PhoneNumber("");
        registerCallStateMonitor();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String PhoneNumber() {
        return this.phoneNumber;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @SimpleFunction
    public void MakePhoneCall() {
        PhoneCallUtil.makePhoneCall(this.context, this.phoneNumber);
    }

    @SimpleEvent(description = "Event indicating that a phonecall has started. If status is 1, incoming call is ringing; if status is 2, outgoing call is dialled. phoneNumber is the incoming/outgoing phone number.")
    public void PhoneCallStarted(int status, String phoneNumber) {
        EventDispatcher.dispatchEvent(this, "PhoneCallStarted", Integer.valueOf(status), phoneNumber);
    }

    @SimpleEvent(description = "Event indicating that a phone call has ended. If status is 1, incoming call is missed or rejected; if status is 2, incoming call is answered before hanging up; if status is 3, outgoing call is hung up. phoneNumber is the ended call phone number.")
    public void PhoneCallEnded(int status, String phoneNumber) {
        EventDispatcher.dispatchEvent(this, "PhoneCallEnded", Integer.valueOf(status), phoneNumber);
    }

    @SimpleEvent(description = "Event indicating that an incoming phone call is answered. phoneNumber is the incoming call phone number.")
    public void IncomingCallAnswered(String phoneNumber) {
        EventDispatcher.dispatchEvent(this, "IncomingCallAnswered", phoneNumber);
    }

    private void registerCallStateMonitor() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        this.context.registerReceiver(this.callStateReceiver, intentFilter);
    }

    private void unregisterCallStateMonitor() {
        this.context.unregisterReceiver(this.callStateReceiver);
    }

    public void onDestroy() {
        unregisterCallStateMonitor();
    }
}
