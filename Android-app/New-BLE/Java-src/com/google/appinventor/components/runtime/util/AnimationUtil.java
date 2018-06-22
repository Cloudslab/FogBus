package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public final class AnimationUtil {
    private AnimationUtil() {
    }

    private static void ApplyHorizontalScrollAnimation(View view, boolean left, int speed) {
        float sign = left ? 1.0f : -1.0f;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setRepeatCount(-1);
        animationSet.setRepeatMode(1);
        TranslateAnimation move = new TranslateAnimation(2, 0.7f * sign, 2, sign * -0.7f, 2, 0.0f, 2, 0.0f);
        move.setStartOffset(0);
        move.setDuration((long) speed);
        move.setFillAfter(true);
        animationSet.addAnimation(move);
        view.startAnimation(animationSet);
    }

    public static void ApplyAnimation(View view, String animation) {
        if (animation.equals("ScrollRightSlow")) {
            ApplyHorizontalScrollAnimation(view, false, 8000);
        } else if (animation.equals("ScrollRight")) {
            ApplyHorizontalScrollAnimation(view, false, 4000);
        } else if (animation.equals("ScrollRightFast")) {
            ApplyHorizontalScrollAnimation(view, false, 1000);
        } else if (animation.equals("ScrollLeftSlow")) {
            ApplyHorizontalScrollAnimation(view, true, 8000);
        } else if (animation.equals("ScrollLeft")) {
            ApplyHorizontalScrollAnimation(view, true, 4000);
        } else if (animation.equals("ScrollLeftFast")) {
            ApplyHorizontalScrollAnimation(view, true, 1000);
        } else if (animation.equals("Stop")) {
            view.clearAnimation();
        }
    }

    public static void ApplyOpenScreenAnimation(Activity activity, String animType) {
        if (animType != null) {
            if (SdkLevel.getLevel() <= 4) {
                Log.e("AnimationUtil", "Screen animations are not available on android versions less than 2.0.");
                return;
            }
            int enter = 0;
            int exit = 0;
            if (animType.equalsIgnoreCase("fade")) {
                enter = activity.getResources().getIdentifier("fadein", "anim", activity.getPackageName());
                exit = activity.getResources().getIdentifier("hold", "anim", activity.getPackageName());
            } else if (animType.equalsIgnoreCase("zoom")) {
                exit = activity.getResources().getIdentifier("zoom_exit", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("zoom_enter", "anim", activity.getPackageName());
            } else if (animType.equalsIgnoreCase("slidehorizontal")) {
                exit = activity.getResources().getIdentifier("slide_exit", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("slide_enter", "anim", activity.getPackageName());
            } else if (animType.equalsIgnoreCase("slidevertical")) {
                exit = activity.getResources().getIdentifier("slide_v_exit", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("slide_v_enter", "anim", activity.getPackageName());
            } else if (!animType.equalsIgnoreCase("none")) {
                return;
            }
            EclairUtil.overridePendingTransitions(activity, enter, exit);
        }
    }

    public static void ApplyCloseScreenAnimation(Activity activity, String animType) {
        if (animType != null) {
            if (SdkLevel.getLevel() <= 4) {
                Log.e("AnimationUtil", "Screen animations are not available on android versions less than 2.0.");
                return;
            }
            int enter = 0;
            int exit = 0;
            if (animType.equalsIgnoreCase("fade")) {
                exit = activity.getResources().getIdentifier("fadeout", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("hold", "anim", activity.getPackageName());
            } else if (animType.equalsIgnoreCase("zoom")) {
                exit = activity.getResources().getIdentifier("zoom_exit_reverse", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("zoom_enter_reverse", "anim", activity.getPackageName());
            } else if (animType.equalsIgnoreCase("slidehorizontal")) {
                exit = activity.getResources().getIdentifier("slide_exit_reverse", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("slide_enter_reverse", "anim", activity.getPackageName());
            } else if (animType.equalsIgnoreCase("slidevertical")) {
                exit = activity.getResources().getIdentifier("slide_v_exit_reverse", "anim", activity.getPackageName());
                enter = activity.getResources().getIdentifier("slide_v_enter_reverse", "anim", activity.getPackageName());
            } else if (!animType.equalsIgnoreCase("none")) {
                return;
            }
            EclairUtil.overridePendingTransitions(activity, enter, exit);
        }
    }
}
