package com.google.appinventor.components.runtime;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MediaUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MEDIA, description = "A special-purpose button. When the user taps an image picker, the device's image gallery appears, and the user can choose an image. After an image is picked, it is saved, and the <code>Selected</code> property will be the name of the file where the image is stored. In order to not fill up storage, a maximum of 10 images will be stored.  Picking more images will delete previous images, in order from oldest to newest.", version = 5)
@UsesPermissions(permissionNames = "android.permission.WRITE_EXTERNAL_STORAGE")
public class ImagePicker extends Picker implements ActivityResultListener {
    private static final String FILE_PREFIX = "picked_image";
    private static final String LOG_TAG = "ImagePicker";
    private static final String imagePickerDirectoryName = "/Pictures/_app_inventor_image_picker";
    private static int maxSavedFiles = 10;
    private String selectionSavedImage = "";
    private String selectionURI;

    class C02171 implements Comparator<File> {
        C02171() {
        }

        public int compare(File f1, File f2) {
            return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
        }
    }

    public ImagePicker(ComponentContainer container) {
        super(container);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Path to the file containing the image that was selected.")
    public String Selection() {
        return this.selectionSavedImage;
    }

    protected Intent getIntent() {
        return new Intent("android.intent.action.PICK", Media.INTERNAL_CONTENT_URI);
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            Uri selectedImage = data.getData();
            this.selectionURI = selectedImage.toString();
            Log.i(LOG_TAG, "selectionURI = " + this.selectionURI);
            ContentResolver cR = this.container.$context().getContentResolver();
            String extension = "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(cR.getType(selectedImage));
            Log.i(LOG_TAG, "extension = " + extension);
            saveSelectedImageToExternalStorage(extension);
            AfterPicking();
        }
    }

    private void saveSelectedImageToExternalStorage(String extension) {
        this.selectionSavedImage = "";
        try {
            File tempFile = MediaUtil.copyMediaToTempFile(this.container.$form(), this.selectionURI);
            Log.i(LOG_TAG, "temp file path is: " + tempFile.getPath());
            copyToExternalStorageAndDeleteSource(tempFile, extension);
        } catch (IOException e) {
            Log.i(LOG_TAG, "copyMediaToTempFile failed: " + e.getMessage());
            this.container.$form().dispatchErrorOccurredEvent(this, LOG_TAG, ErrorMessages.ERROR_CANNOT_COPY_MEDIA, e.getMessage());
        }
    }

    private void copyToExternalStorageAndDeleteSource(File source, String extension) {
        IOException e;
        File dest = null;
        File destDirectory = new File(Environment.getExternalStorageDirectory() + imagePickerDirectoryName);
        try {
            OutputStream outStream;
            InputStream inputStream;
            destDirectory.mkdirs();
            dest = File.createTempFile(FILE_PREFIX, extension, destDirectory);
            this.selectionSavedImage = dest.getPath();
            Log.i(LOG_TAG, "saved file path is: " + this.selectionSavedImage);
            InputStream inStream = new FileInputStream(source);
            try {
                outStream = new FileOutputStream(dest);
            } catch (IOException e2) {
                e = e2;
                inputStream = inStream;
                Log.i(LOG_TAG, "copyFile failed. " + ("destination is " + this.selectionSavedImage + ": " + "error is " + e.getMessage()));
                this.container.$form().dispatchErrorOccurredEvent(this, "SaveImage", ErrorMessages.ERROR_CANNOT_SAVE_IMAGE, err);
                this.selectionSavedImage = "";
                dest.delete();
                source.delete();
                trimDirectory(maxSavedFiles, destDirectory);
            }
            OutputStream outputStream;
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int length = inStream.read(buffer);
                    if (length <= 0) {
                        break;
                    }
                    outStream.write(buffer, 0, length);
                }
                inStream.close();
                outStream.close();
                Log.i(LOG_TAG, "Image was copied to " + this.selectionSavedImage);
                outputStream = outStream;
                inputStream = inStream;
            } catch (IOException e3) {
                e = e3;
                outputStream = outStream;
                inputStream = inStream;
                Log.i(LOG_TAG, "copyFile failed. " + ("destination is " + this.selectionSavedImage + ": " + "error is " + e.getMessage()));
                this.container.$form().dispatchErrorOccurredEvent(this, "SaveImage", ErrorMessages.ERROR_CANNOT_SAVE_IMAGE, err);
                this.selectionSavedImage = "";
                dest.delete();
                source.delete();
                trimDirectory(maxSavedFiles, destDirectory);
            }
        } catch (IOException e4) {
            e = e4;
            Log.i(LOG_TAG, "copyFile failed. " + ("destination is " + this.selectionSavedImage + ": " + "error is " + e.getMessage()));
            this.container.$form().dispatchErrorOccurredEvent(this, "SaveImage", ErrorMessages.ERROR_CANNOT_SAVE_IMAGE, err);
            this.selectionSavedImage = "";
            dest.delete();
            source.delete();
            trimDirectory(maxSavedFiles, destDirectory);
        }
        source.delete();
        trimDirectory(maxSavedFiles, destDirectory);
    }

    private void trimDirectory(int maxSavedFiles, File directory) {
        File[] files = directory.listFiles();
        Arrays.sort(files, new C02171());
        int excess = files.length - maxSavedFiles;
        for (int i = 0; i < excess; i++) {
            files[i].delete();
        }
    }
}
