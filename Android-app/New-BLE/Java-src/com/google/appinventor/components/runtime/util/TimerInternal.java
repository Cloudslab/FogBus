package com.google.appinventor.components.runtime.util;

import android.os.Handler;
import com.google.appinventor.components.runtime.AlarmHandler;

public final class TimerInternal implements Runnable {
    private AlarmHandler component;
    private boolean enabled;
    private Handler handler;
    private int interval;

    public TimerInternal(AlarmHandler component, boolean enabled, int interval) {
        this(component, enabled, interval, new Handler());
    }

    public TimerInternal(AlarmHandler component, boolean enabled, int interval, Handler handler) {
        this.handler = handler;
        this.component = component;
        this.enabled = enabled;
        this.interval = interval;
        if (enabled) {
            handler.postDelayed(this, (long) interval);
        }
    }

    public int Interval() {
        return this.interval;
    }

    public void Interval(int interval) {
        this.interval = interval;
        if (this.enabled) {
            this.handler.removeCallbacks(this);
            this.handler.postDelayed(this, (long) interval);
        }
    }

    public boolean Enabled() {
        return this.enabled;
    }

    public void Enabled(boolean enabled) {
        if (this.enabled) {
            this.handler.removeCallbacks(this);
        }
        this.enabled = enabled;
        if (enabled) {
            this.handler.postDelayed(this, (long) this.interval);
        }
    }

    public void run() {
        if (this.enabled) {
            this.component.alarm();
            if (this.enabled) {
                this.handler.postDelayed(this, (long) this.interval);
            }
        }
    }
}
