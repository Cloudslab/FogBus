package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.http.SslError;
import android.view.Display;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Player;
import com.google.appinventor.components.runtime.Player.State;

public class FroyoUtil {
    private FroyoUtil() {
    }

    public static int getRotation(Display display) {
        return display.getRotation();
    }

    public static AudioManager setAudioManager(Activity activity) {
        return (AudioManager) activity.getSystemService("audio");
    }

    public static Object setAudioFocusChangeListener(final Player player) {
        return new OnAudioFocusChangeListener() {
            private boolean playbackFlag = false;

            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case -3:
                    case -2:
                        if (player != null && player.playerState == State.PLAYING) {
                            player.pause();
                            this.playbackFlag = true;
                            return;
                        }
                        return;
                    case -1:
                        this.playbackFlag = false;
                        player.OtherPlayerStarted();
                        return;
                    case 1:
                        if (player != null && this.playbackFlag && player.playerState == State.PAUSED_BY_EVENT) {
                            player.Start();
                            this.playbackFlag = false;
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public static boolean focusRequestGranted(AudioManager am, Object afChangeListener) {
        if (am.requestAudioFocus((OnAudioFocusChangeListener) afChangeListener, 3, 1) == 1) {
            return true;
        }
        return false;
    }

    public static void abandonFocus(AudioManager am, Object afChangeListener) {
        am.abandonAudioFocus((OnAudioFocusChangeListener) afChangeListener);
    }

    public static WebViewClient getWebViewClient(final boolean ignoreErrors, final boolean followLinks, final Form form, final Component component) {
        return new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !followLinks;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (ignoreErrors) {
                    handler.proceed();
                    return;
                }
                handler.cancel();
                form.dispatchErrorOccurredEvent(component, "WebView", ErrorMessages.ERROR_WEBVIEW_SSL_ERROR, new Object[0]);
            }
        };
    }
}
