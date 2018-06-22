package android.support.v7.internal.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v7.appcompat.C0111R;
import android.widget.RemoteViews;
import java.text.NumberFormat;
import java.util.List;

public class NotificationCompatImplBase {
    static final int MAX_MEDIA_BUTTONS = 5;
    static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

    public static <T extends Action> void overrideContentView(NotificationBuilderWithBuilderAccessor builder, Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, List<T> actions, int[] actionsToShowInCompact, boolean showCancelButton, PendingIntent cancelButtonIntent) {
        builder.getBuilder().setContent(generateContentView(context, contentTitle, contentText, contentInfo, number, largeIcon, subText, useChronometer, when, actions, actionsToShowInCompact, showCancelButton, cancelButtonIntent));
        if (showCancelButton) {
            builder.getBuilder().setOngoing(true);
        }
    }

    private static <T extends Action> RemoteViews generateContentView(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, List<T> actions, int[] actionsToShowInCompact, boolean showCancelButton, PendingIntent cancelButtonIntent) {
        int N;
        RemoteViews view = applyStandardTemplate(context, contentTitle, contentText, contentInfo, number, largeIcon, subText, useChronometer, when, C0111R.layout.notification_template_media, true);
        int numActions = actions.size();
        if (actionsToShowInCompact == null) {
            N = 0;
        } else {
            N = Math.min(actionsToShowInCompact.length, 3);
        }
        view.removeAllViews(C0111R.id.media_actions);
        if (N > 0) {
            for (int i = 0; i < N; i++) {
                if (i >= numActions) {
                    throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(numActions - 1)}));
                }
                Context context2 = context;
                RemoteViews button = generateMediaActionButton(context2, (Action) actions.get(actionsToShowInCompact[i]));
                view.addView(C0111R.id.media_actions, button);
            }
        }
        if (showCancelButton) {
            view.setViewVisibility(C0111R.id.end_padder, 8);
            view.setViewVisibility(C0111R.id.cancel_action, 0);
            view.setOnClickPendingIntent(C0111R.id.cancel_action, cancelButtonIntent);
            view.setInt(C0111R.id.cancel_action, "setAlpha", context.getResources().getInteger(C0111R.integer.cancel_button_image_alpha));
        } else {
            view.setViewVisibility(C0111R.id.end_padder, 0);
            view.setViewVisibility(C0111R.id.cancel_action, 8);
        }
        return view;
    }

    public static <T extends Action> void overrideBigContentView(Notification n, Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, List<T> actions, boolean showCancelButton, PendingIntent cancelButtonIntent) {
        n.bigContentView = generateBigContentView(context, contentTitle, contentText, contentInfo, number, largeIcon, subText, useChronometer, when, actions, showCancelButton, cancelButtonIntent);
        if (showCancelButton) {
            n.flags |= 2;
        }
    }

    private static <T extends Action> RemoteViews generateBigContentView(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, List<T> actions, boolean showCancelButton, PendingIntent cancelButtonIntent) {
        int actionCount = Math.min(actions.size(), 5);
        RemoteViews big = applyStandardTemplate(context, contentTitle, contentText, contentInfo, number, largeIcon, subText, useChronometer, when, getBigLayoutResource(actionCount), false);
        big.removeAllViews(C0111R.id.media_actions);
        if (actionCount > 0) {
            for (int i = 0; i < actionCount; i++) {
                big.addView(C0111R.id.media_actions, generateMediaActionButton(context, (Action) actions.get(i)));
            }
        }
        if (showCancelButton) {
            big.setViewVisibility(C0111R.id.cancel_action, 0);
            big.setInt(C0111R.id.cancel_action, "setAlpha", context.getResources().getInteger(C0111R.integer.cancel_button_image_alpha));
            big.setOnClickPendingIntent(C0111R.id.cancel_action, cancelButtonIntent);
        } else {
            big.setViewVisibility(C0111R.id.cancel_action, 8);
        }
        return big;
    }

    private static RemoteViews generateMediaActionButton(Context context, Action action) {
        boolean tombstone = action.getActionIntent() == null;
        RemoteViews button = new RemoteViews(context.getPackageName(), C0111R.layout.notification_media_action);
        button.setImageViewResource(C0111R.id.action0, action.getIcon());
        if (!tombstone) {
            button.setOnClickPendingIntent(C0111R.id.action0, action.getActionIntent());
        }
        if (VERSION.SDK_INT >= 15) {
            button.setContentDescription(C0111R.id.action0, action.getTitle());
        }
        return button;
    }

    private static int getBigLayoutResource(int actionCount) {
        if (actionCount <= 3) {
            return C0111R.layout.notification_template_big_media_narrow;
        }
        return C0111R.layout.notification_template_big_media;
    }

    private static RemoteViews applyStandardTemplate(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int resId, boolean fitIn1U) {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), resId);
        boolean showLine3 = false;
        boolean showLine2 = false;
        if (largeIcon == null || VERSION.SDK_INT < 16) {
            contentView.setViewVisibility(C0111R.id.icon, 8);
        } else {
            contentView.setImageViewBitmap(C0111R.id.icon, largeIcon);
        }
        if (contentTitle != null) {
            contentView.setTextViewText(C0111R.id.title, contentTitle);
        }
        if (contentText != null) {
            contentView.setTextViewText(C0111R.id.text, contentText);
            showLine3 = true;
        }
        if (contentInfo != null) {
            contentView.setTextViewText(C0111R.id.info, contentInfo);
            contentView.setViewVisibility(C0111R.id.info, 0);
            showLine3 = true;
        } else if (number > 0) {
            if (number > context.getResources().getInteger(C0111R.integer.status_bar_notification_info_maxnum)) {
                contentView.setTextViewText(C0111R.id.info, context.getResources().getString(C0111R.string.status_bar_notification_info_overflow));
            } else {
                contentView.setTextViewText(C0111R.id.info, NumberFormat.getIntegerInstance().format((long) number));
            }
            contentView.setViewVisibility(C0111R.id.info, 0);
            showLine3 = true;
        } else {
            contentView.setViewVisibility(C0111R.id.info, 8);
        }
        if (subText != null && VERSION.SDK_INT >= 16) {
            contentView.setTextViewText(C0111R.id.text, subText);
            if (contentText != null) {
                contentView.setTextViewText(C0111R.id.text2, contentText);
                contentView.setViewVisibility(C0111R.id.text2, 0);
                showLine2 = true;
            } else {
                contentView.setViewVisibility(C0111R.id.text2, 8);
            }
        }
        if (showLine2 && VERSION.SDK_INT >= 16) {
            if (fitIn1U) {
                contentView.setTextViewTextSize(C0111R.id.text, 0, (float) context.getResources().getDimensionPixelSize(C0111R.dimen.notification_subtext_size));
            }
            contentView.setViewPadding(C0111R.id.line1, 0, 0, 0, 0);
        }
        if (when != 0) {
            if (useChronometer) {
                contentView.setViewVisibility(C0111R.id.chronometer, 0);
                contentView.setLong(C0111R.id.chronometer, "setBase", (SystemClock.elapsedRealtime() - System.currentTimeMillis()) + when);
                contentView.setBoolean(C0111R.id.chronometer, "setStarted", true);
            } else {
                contentView.setViewVisibility(C0111R.id.time, 0);
                contentView.setLong(C0111R.id.time, "setTime", when);
            }
        }
        contentView.setViewVisibility(C0111R.id.line3, showLine3 ? 0 : 8);
        return contentView;
    }
}
