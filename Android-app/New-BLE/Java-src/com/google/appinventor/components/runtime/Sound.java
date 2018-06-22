package com.google.appinventor.components.runtime;

import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@DesignerComponent(category = ComponentCategory.MEDIA, description = "<p>A multimedia component that plays sound files and optionally vibrates for the number of milliseconds (thousandths of a second) specified in the Blocks Editor.  The name of the sound file to play can be specified either in the Designer or in the Blocks Editor.</p> <p>For supported sound file formats, see <a href=\"http://developer.android.com/guide/appendix/media-formats.html\" target=\"_blank\">Android Supported Media Formats</a>.</p><p>This <code>Sound</code> component is best for short sound files, such as sound effects, while the <code>Player</code> component is more efficient for longer sounds, such as songs.</p><p>You might get an error if you attempt to play a sound immeditely after setting the source.</p>", iconName = "images/soundEffect.png", nonVisible = true, version = 3)
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.VIBRATE, android.permission.INTERNET")
public class Sound extends AndroidNonvisibleComponent implements Component, OnResumeListener, OnStopListener, OnDestroyListener, Deleteable {
    private static final int LOOP_MODE_NO_LOOP = 0;
    private static final int MAX_PLAY_DELAY_RETRIES = 10;
    private static final int MAX_STREAMS = 10;
    private static final float PLAYBACK_RATE_NORMAL = 1.0f;
    private static final int PLAY_DELAY_LENGTH = 50;
    private static final float VOLUME_FULL = 1.0f;
    private int delayRetries;
    private boolean loadComplete;
    private int minimumInterval;
    private final Handler playWaitHandler;
    private int soundId;
    private final Map<String, Integer> soundMap;
    private SoundPool soundPool;
    private String sourcePath;
    private int streamId;
    private final Component thisComponent;
    private long timeLastPlayed;
    private final Vibrator vibe;
    private final boolean waitForLoadToComplete;

    class C02591 implements Runnable {
        C02591() {
        }

        public void run() {
            if (Sound.this.loadComplete) {
                Sound.this.playAndCheckResult();
            } else if (Sound.this.delayRetries > 0) {
                Sound.this.delayRetries = Sound.this.delayRetries - 1;
                Sound.this.playWhenLoadComplete();
            } else {
                Sound.this.form.dispatchErrorOccurredEvent(Sound.this.thisComponent, "Play", ErrorMessages.ERROR_SOUND_NOT_READY, Sound.this.sourcePath);
            }
        }
    }

    private class OnLoadHelper {

        class C02601 implements OnLoadCompleteListener {
            C02601() {
            }

            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Sound.this.loadComplete = true;
            }
        }

        private OnLoadHelper() {
        }

        public void setOnloadCompleteListener(SoundPool soundPool) {
            soundPool.setOnLoadCompleteListener(new C02601());
        }
    }

    public Sound(ComponentContainer container) {
        super(container.$form());
        this.waitForLoadToComplete = SdkLevel.getLevel() >= 8;
        this.playWaitHandler = new Handler();
        this.thisComponent = this;
        this.soundPool = new SoundPool(10, 3, 0);
        this.soundMap = new HashMap();
        this.vibe = (Vibrator) this.form.getSystemService("vibrator");
        this.sourcePath = "";
        this.loadComplete = true;
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.form.registerForOnDestroy(this);
        this.form.setVolumeControlStream(3);
        MinimumInterval(500);
        if (this.waitForLoadToComplete) {
            new OnLoadHelper().setOnloadCompleteListener(this.soundPool);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The name of the sound file.  Only certain formats are supported.  See http://developer.android.com/guide/appendix/media-formats.html.")
    public String Source() {
        return this.sourcePath;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty
    public void Source(String path) {
        if (path == null) {
            path = "";
        }
        this.sourcePath = path;
        if (this.streamId != 0) {
            this.soundPool.stop(this.streamId);
            this.streamId = 0;
        }
        this.soundId = 0;
        if (this.sourcePath.length() != 0) {
            Integer existingSoundId = (Integer) this.soundMap.get(this.sourcePath);
            if (existingSoundId != null) {
                this.soundId = existingSoundId.intValue();
                return;
            }
            Log.i("Sound", "No existing sound with path " + this.sourcePath + ".");
            try {
                int newSoundId = MediaUtil.loadSoundPool(this.soundPool, this.form, this.sourcePath);
                if (newSoundId != 0) {
                    this.soundMap.put(this.sourcePath, Integer.valueOf(newSoundId));
                    Log.i("Sound", "Successfully began loading sound: setting soundId to " + newSoundId + ".");
                    this.soundId = newSoundId;
                    this.loadComplete = false;
                    return;
                }
                this.form.dispatchErrorOccurredEvent(this, "Source", ErrorMessages.ERROR_UNABLE_TO_LOAD_MEDIA, this.sourcePath);
            } catch (IOException e) {
                this.form.dispatchErrorOccurredEvent(this, "Source", ErrorMessages.ERROR_UNABLE_TO_LOAD_MEDIA, this.sourcePath);
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The minimum interval, in milliseconds, between sounds.  If you play a sound, all further Play() calls will be ignored until the interval has elapsed.")
    public int MinimumInterval() {
        return this.minimumInterval;
    }

    @DesignerProperty(defaultValue = "500", editorType = "non_negative_integer")
    @SimpleProperty
    public void MinimumInterval(int interval) {
        this.minimumInterval = interval;
    }

    @SimpleFunction(description = "Plays the sound specified by the Source property.")
    public void Play() {
        if (this.soundId != 0) {
            long currentTime = System.currentTimeMillis();
            if (this.timeLastPlayed == 0 || currentTime >= this.timeLastPlayed + ((long) this.minimumInterval)) {
                this.timeLastPlayed = currentTime;
                this.delayRetries = 10;
                playWhenLoadComplete();
                return;
            }
            Log.i("Sound", "Unable to play because MinimumInterval has not elapsed since last play.");
            return;
        }
        Log.i("Sound", "Sound Id was 0. Did you remember to set the Source property?");
        this.form.dispatchErrorOccurredEvent(this, "Play", ErrorMessages.ERROR_UNABLE_TO_PLAY_MEDIA, this.sourcePath);
    }

    private void playWhenLoadComplete() {
        if (this.loadComplete || !this.waitForLoadToComplete) {
            playAndCheckResult();
            return;
        }
        Log.i("Sound", "Sound not ready:  retrying.  Remaining retries = " + this.delayRetries);
        this.playWaitHandler.postDelayed(new C02591(), 50);
    }

    private void playAndCheckResult() {
        this.streamId = this.soundPool.play(this.soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        Log.i("Sound", "SoundPool.play returned stream id " + this.streamId);
        if (this.streamId == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Play", ErrorMessages.ERROR_UNABLE_TO_PLAY_MEDIA, this.sourcePath);
        }
    }

    @SimpleFunction(description = "Pauses playing the sound if it is being played.")
    public void Pause() {
        if (this.streamId != 0) {
            this.soundPool.pause(this.streamId);
        } else {
            Log.i("Sound", "Unable to pause. Did you remember to call the Play function?");
        }
    }

    @SimpleFunction(description = "Resumes playing the sound after a pause.")
    public void Resume() {
        if (this.streamId != 0) {
            this.soundPool.resume(this.streamId);
        } else {
            Log.i("Sound", "Unable to resume. Did you remember to call the Play function?");
        }
    }

    @SimpleFunction(description = "Stops playing the sound if it is being played.")
    public void Stop() {
        if (this.streamId != 0) {
            this.soundPool.stop(this.streamId);
            this.streamId = 0;
            return;
        }
        Log.i("Sound", "Unable to stop. Did you remember to call the Play function?");
    }

    @SimpleFunction(description = "Vibrates for the specified number of milliseconds.")
    public void Vibrate(int millisecs) {
        this.vibe.vibrate((long) millisecs);
    }

    @SimpleEvent(description = "The SoundError event is no longer used. Please use the Screen.ErrorOccurred event instead.", userVisible = false)
    public void SoundError(String message) {
    }

    public void onStop() {
        Log.i("Sound", "Got onStop");
        if (this.streamId != 0) {
            this.soundPool.pause(this.streamId);
        }
    }

    public void onResume() {
        Log.i("Sound", "Got onResume");
        if (this.streamId != 0) {
            this.soundPool.resume(this.streamId);
        }
    }

    public void onDestroy() {
        prepareToDie();
    }

    public void onDelete() {
        prepareToDie();
    }

    private void prepareToDie() {
        if (this.streamId != 0) {
            this.soundPool.stop(this.streamId);
            this.soundPool.unload(this.streamId);
        }
        this.soundPool.release();
        this.vibe.cancel();
        this.soundPool = null;
    }
}
