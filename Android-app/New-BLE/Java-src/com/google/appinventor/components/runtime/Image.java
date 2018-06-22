package com.google.appinventor.components.runtime;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "Component for displaying images.  The picture to display, and other aspects of the Image's appearance, can be specified in the Designer or in the Blocks Editor.", version = 3)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class Image extends AndroidViewComponent {
    private String picturePath = "";
    private double rotationAngle = 0.0d;
    private int scalingMode = 0;
    private final ImageView view;

    public Image(ComponentContainer container) {
        super(container);
        this.view = new ImageView(container.$context()) {
            public boolean verifyDrawable(Drawable dr) {
                super.verifyDrawable(dr);
                return true;
            }
        };
        this.view.setFocusable(true);
        container.$add(this);
    }

    public View getView() {
        return this.view;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Picture() {
        return this.picturePath;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty
    public void Picture(String path) {
        Drawable drawable;
        if (path == null) {
            path = "";
        }
        this.picturePath = path;
        try {
            drawable = MediaUtil.getBitmapDrawable(this.container.$form(), this.picturePath);
        } catch (IOException e) {
            Log.e("Image", "Unable to load " + this.picturePath);
            drawable = null;
        }
        ViewUtil.setImage(this.view, drawable);
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "float")
    @SimpleProperty
    public void RotationAngle(double rotationAngle) {
        if (this.rotationAngle != rotationAngle) {
            if (SdkLevel.getLevel() < 11) {
                this.container.$form().dispatchErrorOccurredEvent(this, "RotationAngle", ErrorMessages.ERROR_IMAGE_CANNOT_ROTATE, new Object[0]);
                return;
            }
            HoneycombUtil.viewSetRotate(this.view, rotationAngle);
            this.rotationAngle = rotationAngle;
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The angle at which the image picture appears rotated. This rotation does not appear on the designer screen, only on the device.")
    public double RotationAngle() {
        return this.rotationAngle;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(description = "Specifies whether the image should be resized to match the size of the ImageView.")
    public void ScalePictureToFit(boolean scale) {
        if (scale) {
            this.view.setScaleType(ScaleType.FIT_XY);
        } else {
            this.view.setScaleType(ScaleType.FIT_CENTER);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is a limited form of animation that can attach a small number of motion types to images.  The allowable motions are ScrollRightSlow, ScrollRight, ScrollRightFast, ScrollLeftSlow, ScrollLeft, ScrollLeftFast, and Stop")
    public void Animation(String animation) {
        AnimationUtil.ApplyAnimation(this.view, animation);
    }

    @Deprecated
    @SimpleProperty(description = "This property determines how the picture scales according to the Height or Width of the Image. Scale proportionally (0) preserves the picture aspect ratio. Scale to fit (1) matches the Image area, even if the aspect ratio changes.")
    public void Scaling(int mode) {
        switch (mode) {
            case 0:
                this.view.setScaleType(ScaleType.FIT_CENTER);
                break;
            case 1:
                this.view.setScaleType(ScaleType.FIT_XY);
                break;
            default:
                throw new IllegalArgumentError("Illegal scaling mode: " + mode);
        }
        this.scalingMode = mode;
    }

    @SimpleProperty
    public int Scaling() {
        return this.scalingMode;
    }
}
