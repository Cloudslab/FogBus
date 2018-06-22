package android.support.v7.internal.app;

import android.app.Notification.MediaStyle;
import android.media.session.MediaSession.Token;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;

public class NotificationCompatImpl21 {
    public static void addMediaStyle(NotificationBuilderWithBuilderAccessor b, int[] actionsToShowInCompact, Object token) {
        MediaStyle style = new MediaStyle(b.getBuilder());
        if (actionsToShowInCompact != null) {
            style.setShowActionsInCompactView(actionsToShowInCompact);
        }
        if (token != null) {
            style.setMediaSession((Token) token);
        }
    }
}
