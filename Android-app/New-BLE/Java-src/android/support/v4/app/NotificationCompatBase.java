package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;

public class NotificationCompatBase {

    public static abstract class Action {

        public interface Factory {
            Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr);

            Action[] newArray(int i);
        }

        public abstract PendingIntent getActionIntent();

        public abstract Bundle getExtras();

        public abstract int getIcon();

        public abstract RemoteInput[] getRemoteInputs();

        public abstract CharSequence getTitle();
    }

    public static abstract class UnreadConversation {

        public interface Factory {
            UnreadConversation build(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j);
        }

        abstract long getLatestTimestamp();

        abstract String[] getMessages();

        abstract String getParticipant();

        abstract String[] getParticipants();

        abstract PendingIntent getReadPendingIntent();

        abstract RemoteInput getRemoteInput();

        abstract PendingIntent getReplyPendingIntent();
    }
}
