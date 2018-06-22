package com.google.appinventor.components.runtime;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Environment;
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
import com.google.appinventor.components.runtime.util.FileUtil;
import java.io.IOException;

@DesignerComponent(category = ComponentCategory.MEDIA, description = "<p>Multimedia component that records audio.</p>", iconName = "images/soundRecorder.png", nonVisible = true, version = 2)
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.RECORD_AUDIO,android.permission.WRITE_EXTERNAL_STORAGE,android.permission.READ_EXTERNAL_STORAGE")
public final class SoundRecorder extends AndroidNonvisibleComponent implements Component, OnErrorListener, OnInfoListener {
    private static final String TAG = "SoundRecorder";
    private RecordingController controller;
    private String savedRecording = "";

    private class RecordingController {
        final String file;
        final MediaRecorder recorder;

        RecordingController(String savedRecording) throws IOException {
            if (savedRecording.equals("")) {
                savedRecording = FileUtil.getRecordingFile("3gp").getAbsolutePath();
            }
            this.file = savedRecording;
            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(1);
            this.recorder.setOutputFormat(1);
            this.recorder.setAudioEncoder(1);
            Log.i(SoundRecorder.TAG, "Setting output file to " + this.file);
            this.recorder.setOutputFile(this.file);
            Log.i(SoundRecorder.TAG, "preparing");
            this.recorder.prepare();
            this.recorder.setOnErrorListener(SoundRecorder.this);
            this.recorder.setOnInfoListener(SoundRecorder.this);
        }

        void start() throws IllegalStateException {
            Log.i(SoundRecorder.TAG, "starting");
            try {
                this.recorder.start();
            } catch (IllegalStateException e) {
                Log.e(SoundRecorder.TAG, "got IllegalStateException. Are there two recorders running?", e);
                throw new IllegalStateException("Is there another recording running?");
            }
        }

        void stop() {
            this.recorder.setOnErrorListener(null);
            this.recorder.setOnInfoListener(null);
            this.recorder.stop();
            this.recorder.reset();
            this.recorder.release();
        }
    }

    public SoundRecorder(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Specifies the path to the file where the recording should be stored. If this proprety is the empty string, then starting a recording will create a file in an appropriate location.  If the property is not the empty string, it should specify a complete path to a file in an existing directory, including a file name with the extension .3gp.")
    public String SavedRecording() {
        return this.savedRecording;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void SavedRecording(String pathName) {
        this.savedRecording = pathName;
    }

    @SimpleFunction
    public void Start() {
        if (this.controller != null) {
            Log.i(TAG, "Start() called, but already recording to " + this.controller.file);
            return;
        }
        Log.i(TAG, "Start() called");
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                this.controller = new RecordingController(this.savedRecording);
                try {
                    this.controller.start();
                    StartedRecording();
                    return;
                } catch (Throwable t) {
                    this.controller = null;
                    this.form.dispatchErrorOccurredEvent(this, "Start", ErrorMessages.ERROR_SOUND_RECORDER_CANNOT_CREATE, t.getMessage());
                    return;
                }
            } catch (Throwable t2) {
                this.form.dispatchErrorOccurredEvent(this, "Start", ErrorMessages.ERROR_SOUND_RECORDER_CANNOT_CREATE, t2.getMessage());
                return;
            }
        }
        this.form.dispatchErrorOccurredEvent(this, "Start", ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_NOT_AVAILABLE, new Object[0]);
    }

    public void onError(MediaRecorder affectedRecorder, int what, int extra) {
        if (this.controller == null || affectedRecorder != this.controller.recorder) {
            Log.w(TAG, "onError called with wrong recorder. Ignoring.");
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "onError", ErrorMessages.ERROR_SOUND_RECORDER, new Object[0]);
        try {
            this.controller.stop();
        } catch (Throwable e) {
            Log.w(TAG, e.getMessage());
        } finally {
            this.controller = null;
            StoppedRecording();
        }
    }

    public void onInfo(MediaRecorder affectedRecorder, int what, int extra) {
        if (this.controller == null || affectedRecorder != this.controller.recorder) {
            Log.w(TAG, "onInfo called with wrong recorder. Ignoring.");
            return;
        }
        switch (what) {
            case 1:
                this.form.dispatchErrorOccurredEvent(this, "recording", ErrorMessages.ERROR_SOUND_RECORDER, new Object[0]);
                break;
            case 800:
                this.form.dispatchErrorOccurredEvent(this, "recording", ErrorMessages.ERROR_SOUND_RECORDER_MAX_DURATION_REACHED, new Object[0]);
                break;
            case ErrorMessages.ERROR_SOUND_RECORDER /*801*/:
                this.form.dispatchErrorOccurredEvent(this, "recording", ErrorMessages.ERROR_SOUND_RECORDER_MAX_FILESIZE_REACHED, new Object[0]);
                break;
            default:
                return;
        }
        try {
            Log.i(TAG, "Recoverable condition while recording. Will attempt to stop normally.");
            this.controller.recorder.stop();
        } catch (IllegalStateException e) {
            Log.i(TAG, "SoundRecorder was not in a recording state.", e);
            this.form.dispatchErrorOccurredEventDialog(this, "Stop", ErrorMessages.ERROR_SOUND_RECORDER_ILLEGAL_STOP, new Object[0]);
        } finally {
            this.controller = null;
            StoppedRecording();
        }
    }

    @SimpleFunction
    public void Stop() {
        if (this.controller == null) {
            Log.i(TAG, "Stop() called, but already stopped.");
            return;
        }
        try {
            Log.i(TAG, "Stop() called");
            Log.i(TAG, "stopping");
            this.controller.stop();
            Log.i(TAG, "Firing AfterSoundRecorded with " + this.controller.file);
            AfterSoundRecorded(this.controller.file);
        } catch (Throwable th) {
            this.form.dispatchErrorOccurredEvent(this, "Stop", ErrorMessages.ERROR_SOUND_RECORDER, new Object[0]);
        } finally {
            this.controller = null;
            StoppedRecording();
        }
    }

    @SimpleEvent(description = "Provides the location of the newly created sound.")
    public void AfterSoundRecorded(String sound) {
        EventDispatcher.dispatchEvent(this, "AfterSoundRecorded", sound);
    }

    @SimpleEvent(description = "Indicates that the recorder has started, and can be stopped.")
    public void StartedRecording() {
        EventDispatcher.dispatchEvent(this, "StartedRecording", new Object[0]);
    }

    @SimpleEvent(description = "Indicates that the recorder has stopped, and can be started again.")
    public void StoppedRecording() {
        EventDispatcher.dispatchEvent(this, "StoppedRecording", new Object[0]);
    }
}
