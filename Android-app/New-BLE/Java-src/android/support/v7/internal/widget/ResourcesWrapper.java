package android.support.v7.internal.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

class ResourcesWrapper extends Resources {
    private final Resources mResources;

    public ResourcesWrapper(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mResources = resources;
    }

    public CharSequence getText(int id) throws NotFoundException {
        return this.mResources.getText(id);
    }

    public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
        return this.mResources.getQuantityText(id, quantity);
    }

    public String getString(int id) throws NotFoundException {
        return this.mResources.getString(id);
    }

    public String getString(int id, Object... formatArgs) throws NotFoundException {
        return this.mResources.getString(id, formatArgs);
    }

    public String getQuantityString(int id, int quantity, Object... formatArgs) throws NotFoundException {
        return this.mResources.getQuantityString(id, quantity, formatArgs);
    }

    public String getQuantityString(int id, int quantity) throws NotFoundException {
        return this.mResources.getQuantityString(id, quantity);
    }

    public CharSequence getText(int id, CharSequence def) {
        return this.mResources.getText(id, def);
    }

    public CharSequence[] getTextArray(int id) throws NotFoundException {
        return this.mResources.getTextArray(id);
    }

    public String[] getStringArray(int id) throws NotFoundException {
        return this.mResources.getStringArray(id);
    }

    public int[] getIntArray(int id) throws NotFoundException {
        return this.mResources.getIntArray(id);
    }

    public TypedArray obtainTypedArray(int id) throws NotFoundException {
        return this.mResources.obtainTypedArray(id);
    }

    public float getDimension(int id) throws NotFoundException {
        return this.mResources.getDimension(id);
    }

    public int getDimensionPixelOffset(int id) throws NotFoundException {
        return this.mResources.getDimensionPixelOffset(id);
    }

    public int getDimensionPixelSize(int id) throws NotFoundException {
        return this.mResources.getDimensionPixelSize(id);
    }

    public float getFraction(int id, int base, int pbase) {
        return this.mResources.getFraction(id, base, pbase);
    }

    public Drawable getDrawable(int id) throws NotFoundException {
        return this.mResources.getDrawable(id);
    }

    public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
        return this.mResources.getDrawable(id, theme);
    }

    public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
        return this.mResources.getDrawableForDensity(id, density);
    }

    public Drawable getDrawableForDensity(int id, int density, Theme theme) {
        return this.mResources.getDrawableForDensity(id, density, theme);
    }

    public Movie getMovie(int id) throws NotFoundException {
        return this.mResources.getMovie(id);
    }

    public int getColor(int id) throws NotFoundException {
        return this.mResources.getColor(id);
    }

    public ColorStateList getColorStateList(int id) throws NotFoundException {
        return this.mResources.getColorStateList(id);
    }

    public boolean getBoolean(int id) throws NotFoundException {
        return this.mResources.getBoolean(id);
    }

    public int getInteger(int id) throws NotFoundException {
        return this.mResources.getInteger(id);
    }

    public XmlResourceParser getLayout(int id) throws NotFoundException {
        return this.mResources.getLayout(id);
    }

    public XmlResourceParser getAnimation(int id) throws NotFoundException {
        return this.mResources.getAnimation(id);
    }

    public XmlResourceParser getXml(int id) throws NotFoundException {
        return this.mResources.getXml(id);
    }

    public InputStream openRawResource(int id) throws NotFoundException {
        return this.mResources.openRawResource(id);
    }

    public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
        return this.mResources.openRawResource(id, value);
    }

    public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
        return this.mResources.openRawResourceFd(id);
    }

    public void getValue(int id, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        this.mResources.getValue(id, outValue, resolveRefs);
    }

    public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        this.mResources.getValueForDensity(id, density, outValue, resolveRefs);
    }

    public void getValue(String name, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        this.mResources.getValue(name, outValue, resolveRefs);
    }

    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        return this.mResources.obtainAttributes(set, attrs);
    }

    public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
        super.updateConfiguration(config, metrics);
        if (this.mResources != null) {
            this.mResources.updateConfiguration(config, metrics);
        }
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.mResources.getDisplayMetrics();
    }

    public Configuration getConfiguration() {
        return this.mResources.getConfiguration();
    }

    public int getIdentifier(String name, String defType, String defPackage) {
        return this.mResources.getIdentifier(name, defType, defPackage);
    }

    public String getResourceName(int resid) throws NotFoundException {
        return this.mResources.getResourceName(resid);
    }

    public String getResourcePackageName(int resid) throws NotFoundException {
        return this.mResources.getResourcePackageName(resid);
    }

    public String getResourceTypeName(int resid) throws NotFoundException {
        return this.mResources.getResourceTypeName(resid);
    }

    public String getResourceEntryName(int resid) throws NotFoundException {
        return this.mResources.getResourceEntryName(resid);
    }

    public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) throws XmlPullParserException, IOException {
        this.mResources.parseBundleExtras(parser, outBundle);
    }

    public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) throws XmlPullParserException {
        this.mResources.parseBundleExtra(tagName, attrs, outBundle);
    }
}
