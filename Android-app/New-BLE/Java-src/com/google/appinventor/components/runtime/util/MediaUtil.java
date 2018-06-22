package com.google.appinventor.components.runtime.util;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.VideoView;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MediaUtil {
    private static final String LOG_TAG = "MediaUtil";
    private static String REPL_ASSET_DIR = null;
    private static ConcurrentHashMap<String, String> pathCache = new ConcurrentHashMap(2);
    private static final Map<String, File> tempFileMap = new HashMap();

    private static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0;
            while (totalBytesSkipped < n) {
                long bytesSkipped = this.in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0) {
                    if (read() < 0) {
                        break;
                    }
                    bytesSkipped = 1;
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    private enum MediaSource {
        ASSET,
        REPL_ASSET,
        SDCARD,
        FILE_URL,
        URL,
        CONTENT_URI,
        CONTACT_URI
    }

    private static class Synchronizer<T> {
        private String error;
        private volatile boolean finished;
        private T result;

        private Synchronizer() {
            this.finished = false;
        }

        public synchronized void waitfor() {
            while (!this.finished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }

        public synchronized void wakeup(T result) {
            this.finished = true;
            this.result = result;
            notifyAll();
        }

        public synchronized void error(String error) {
            this.finished = true;
            this.error = error;
            notifyAll();
        }

        public T getResult() {
            return this.result;
        }

        public String getError() {
            return this.error;
        }
    }

    private MediaUtil() {
    }

    private static String replAssetPath(String assetName) {
        if (REPL_ASSET_DIR == null) {
            REPL_ASSET_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppInventor/assets/";
        }
        return REPL_ASSET_DIR + assetName;
    }

    static String fileUrlToFilePath(String mediaPath) throws IOException {
        try {
            return new File(new URL(mediaPath).toURI()).getAbsolutePath();
        } catch (IllegalArgumentException e) {
            throw new IOException("Unable to determine file path of file url " + mediaPath);
        } catch (Exception e2) {
            throw new IOException("Unable to determine file path of file url " + mediaPath);
        }
    }

    private static MediaSource determineMediaSource(Form form, String mediaPath) {
        if (mediaPath.startsWith("/sdcard/") || mediaPath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath())) {
            return MediaSource.SDCARD;
        }
        if (mediaPath.startsWith("content://contacts/")) {
            return MediaSource.CONTACT_URI;
        }
        if (mediaPath.startsWith("content://")) {
            return MediaSource.CONTENT_URI;
        }
        try {
            URL url = new URL(mediaPath);
            if (mediaPath.startsWith("file:")) {
                return MediaSource.FILE_URL;
            }
            return MediaSource.URL;
        } catch (MalformedURLException e) {
            if (!(form instanceof ReplForm)) {
                return MediaSource.ASSET;
            }
            if (((ReplForm) form).isAssetsLoaded()) {
                return MediaSource.REPL_ASSET;
            }
            return MediaSource.ASSET;
        }
    }

    private static String findCaseinsensitivePath(Form form, String mediaPath) throws IOException {
        if (!pathCache.containsKey(mediaPath)) {
            String newPath = findCaseinsensitivePathWithoutCache(form, mediaPath);
            if (newPath == null) {
                return null;
            }
            pathCache.put(mediaPath, newPath);
        }
        return (String) pathCache.get(mediaPath);
    }

    private static String findCaseinsensitivePathWithoutCache(Form form, String mediaPath) throws IOException {
        String[] mediaPathlist = form.getAssets().list("");
        int l = Array.getLength(mediaPathlist);
        for (int i = 0; i < l; i++) {
            String temp = mediaPathlist[i];
            if (temp.equalsIgnoreCase(mediaPath)) {
                return temp;
            }
        }
        return null;
    }

    private static InputStream getAssetsIgnoreCaseInputStream(Form form, String mediaPath) throws IOException {
        try {
            return form.getAssets().open(mediaPath);
        } catch (IOException e) {
            String path = findCaseinsensitivePath(form, mediaPath);
            if (path != null) {
                return form.getAssets().open(path);
            }
            throw e;
        }
    }

    private static InputStream openMedia(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        switch (mediaSource) {
            case ASSET:
                return getAssetsIgnoreCaseInputStream(form, mediaPath);
            case REPL_ASSET:
                return new FileInputStream(replAssetPath(mediaPath));
            case SDCARD:
                return new FileInputStream(mediaPath);
            case FILE_URL:
            case URL:
                return new URL(mediaPath).openStream();
            case CONTENT_URI:
                return form.getContentResolver().openInputStream(Uri.parse(mediaPath));
            case CONTACT_URI:
                InputStream is;
                if (SdkLevel.getLevel() >= 12) {
                    is = HoneycombMR1Util.openContactPhotoInputStreamHelper(form.getContentResolver(), Uri.parse(mediaPath));
                } else {
                    is = People.openContactPhotoInputStream(form.getContentResolver(), Uri.parse(mediaPath));
                }
                if (is != null) {
                    return is;
                }
                throw new IOException("Unable to open contact photo " + mediaPath + ".");
            default:
                throw new IOException("Unable to open media " + mediaPath + ".");
        }
    }

    public static InputStream openMedia(Form form, String mediaPath) throws IOException {
        return openMedia(form, mediaPath, determineMediaSource(form, mediaPath));
    }

    public static File copyMediaToTempFile(Form form, String mediaPath) throws IOException {
        return copyMediaToTempFile(form, mediaPath, determineMediaSource(form, mediaPath));
    }

    private static File copyMediaToTempFile(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        InputStream in = openMedia(form, mediaPath, mediaSource);
        File file = null;
        try {
            file = File.createTempFile("AI_Media_", null);
            file.deleteOnExit();
            FileUtil.writeStreamToFile(in, file.getAbsolutePath());
            in.close();
            return file;
        } catch (IOException e) {
            if (file != null) {
                Log.e(LOG_TAG, "Could not copy media " + mediaPath + " to temp file " + file.getAbsolutePath());
                file.delete();
            } else {
                Log.e(LOG_TAG, "Could not copy media " + mediaPath + " to temp file.");
            }
            throw e;
        } catch (Throwable th) {
            in.close();
        }
    }

    private static File cacheMediaTempFile(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        File tempFile = (File) tempFileMap.get(mediaPath);
        if (tempFile != null && tempFile.exists()) {
            return tempFile;
        }
        Log.i(LOG_TAG, "Copying media " + mediaPath + " to temp file...");
        tempFile = copyMediaToTempFile(form, mediaPath, mediaSource);
        Log.i(LOG_TAG, "Finished copying media " + mediaPath + " to temp file " + tempFile.getAbsolutePath());
        tempFileMap.put(mediaPath, tempFile);
        return tempFile;
    }

    public static BitmapDrawable getBitmapDrawable(Form form, String mediaPath) throws IOException {
        BitmapDrawable bitmapDrawable = null;
        if (!(mediaPath == null || mediaPath.length() == 0)) {
            final Synchronizer syncer = new Synchronizer();
            getBitmapDrawableAsync(form, mediaPath, new AsyncCallbackPair<BitmapDrawable>() {
                public void onFailure(String message) {
                    syncer.error(message);
                }

                public void onSuccess(BitmapDrawable result) {
                    syncer.wakeup(result);
                }
            });
            syncer.waitfor();
            bitmapDrawable = (BitmapDrawable) syncer.getResult();
            if (bitmapDrawable == null) {
                throw new IOException(syncer.getError());
            }
        }
        return bitmapDrawable;
    }

    public static void getBitmapDrawableAsync(final Form form, final String mediaPath, final AsyncCallbackPair<BitmapDrawable> continuation) {
        if (mediaPath == null || mediaPath.length() == 0) {
            continuation.onSuccess(null);
            return;
        }
        final MediaSource mediaSource = determineMediaSource(form, mediaPath);
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                Log.d(MediaUtil.LOG_TAG, "mediaPath = " + mediaPath);
                InputStream is = null;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                try {
                    is = MediaUtil.openMedia(form, mediaPath, mediaSource);
                    while (true) {
                        int read = is.read(buf);
                        if (read <= 0) {
                            break;
                        }
                        bos.write(buf, 0, read);
                    }
                    buf = bos.toByteArray();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e2) {
                    }
                    ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                    try {
                        bis.mark(buf.length);
                        Options options = MediaUtil.getBitmapOptions(form, bis, mediaPath);
                        bis.reset();
                        BitmapDrawable originalBitmapDrawable = new BitmapDrawable(form.getResources(), MediaUtil.decodeStream(bis, null, options));
                        originalBitmapDrawable.setTargetDensity(form.getResources().getDisplayMetrics());
                        if (options.inSampleSize != 1 || form.deviceDensity() == 1.0f) {
                            continuation.onSuccess(originalBitmapDrawable);
                            if (bis != null) {
                                try {
                                    bis.close();
                                    return;
                                } catch (IOException e3) {
                                    Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e3);
                                    return;
                                }
                            }
                            return;
                        }
                        int scaledWidth = (int) (form.deviceDensity() * ((float) originalBitmapDrawable.getIntrinsicWidth()));
                        int scaledHeight = (int) (form.deviceDensity() * ((float) originalBitmapDrawable.getIntrinsicHeight()));
                        Log.d(MediaUtil.LOG_TAG, "form.deviceDensity() = " + form.deviceDensity());
                        Log.d(MediaUtil.LOG_TAG, "originalBitmapDrawable.getIntrinsicWidth() = " + originalBitmapDrawable.getIntrinsicWidth());
                        Log.d(MediaUtil.LOG_TAG, "originalBitmapDrawable.getIntrinsicHeight() = " + originalBitmapDrawable.getIntrinsicHeight());
                        BitmapDrawable scaledBitmapDrawable = new BitmapDrawable(form.getResources(), Bitmap.createScaledBitmap(originalBitmapDrawable.getBitmap(), scaledWidth, scaledHeight, false));
                        scaledBitmapDrawable.setTargetDensity(form.getResources().getDisplayMetrics());
                        System.gc();
                        continuation.onSuccess(scaledBitmapDrawable);
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e32) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e32);
                            }
                        }
                    } catch (Exception e4) {
                        Log.w(MediaUtil.LOG_TAG, "Exception while loading media.", e4);
                        continuation.onFailure(e4.getMessage());
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e322) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e322);
                            }
                        }
                    } catch (Throwable th) {
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e3222) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e3222);
                            }
                        }
                    }
                } catch (IOException e32222) {
                    if (mediaSource == MediaSource.CONTACT_URI) {
                        continuation.onSuccess(new BitmapDrawable(form.getResources(), BitmapFactory.decodeResource(form.getResources(), 17301606, null)));
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e322222) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e322222);
                            }
                        }
                        try {
                            bos.close();
                        } catch (IOException e5) {
                        }
                        return;
                    }
                    Log.d(MediaUtil.LOG_TAG, "IOException reading file.", e322222);
                    continuation.onFailure(e322222.getMessage());
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e3222222) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e3222222);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e6) {
                    }
                } catch (Throwable th2) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e32222222) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e32222222);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e7) {
                    }
                }
            }
        });
    }

    private static Bitmap decodeStream(InputStream is, Rect outPadding, Options opts) {
        return BitmapFactory.decodeStream(new FlushedInputStream(is), outPadding, opts);
    }

    private static Options getBitmapOptions(Form form, InputStream is, String mediaPath) {
        int maxWidth;
        int maxHeight;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        decodeStream(is, null, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        Display display = ((WindowManager) form.getSystemService("window")).getDefaultDisplay();
        if (Form.getCompatibilityMode()) {
            maxWidth = 720;
            maxHeight = 840;
        } else {
            maxWidth = (int) (((float) display.getWidth()) / form.deviceDensity());
            maxHeight = (int) (((float) display.getHeight()) / form.deviceDensity());
        }
        int sampleSize = 1;
        while (imageWidth / sampleSize > maxWidth && imageHeight / sampleSize > maxHeight) {
            sampleSize *= 2;
        }
        options = new Options();
        Log.d(LOG_TAG, "getBitmapOptions: sampleSize = " + sampleSize + " mediaPath = " + mediaPath + " maxWidth = " + maxWidth + " maxHeight = " + maxHeight + " display width = " + display.getWidth() + " display height = " + display.getHeight());
        options.inSampleSize = sampleSize;
        return options;
    }

    private static AssetFileDescriptor getAssetsIgnoreCaseAfd(Form form, String mediaPath) throws IOException {
        try {
            return form.getAssets().openFd(mediaPath);
        } catch (IOException e) {
            String path = findCaseinsensitivePath(form, mediaPath);
            if (path != null) {
                return form.getAssets().openFd(path);
            }
            throw e;
        }
    }

    public static int loadSoundPool(SoundPool soundPool, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        switch (mediaSource) {
            case ASSET:
                return soundPool.load(getAssetsIgnoreCaseAfd(form, mediaPath), 1);
            case REPL_ASSET:
                return soundPool.load(replAssetPath(mediaPath), 1);
            case SDCARD:
                return soundPool.load(mediaPath, 1);
            case FILE_URL:
                return soundPool.load(fileUrlToFilePath(mediaPath), 1);
            case URL:
            case CONTENT_URI:
                return soundPool.load(cacheMediaTempFile(form, mediaPath, mediaSource).getAbsolutePath(), 1);
            case CONTACT_URI:
                throw new IOException("Unable to load audio for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load audio " + mediaPath + ".");
        }
    }

    public static void loadMediaPlayer(MediaPlayer mediaPlayer, Form form, String mediaPath) throws IOException {
        switch (determineMediaSource(form, mediaPath)) {
            case ASSET:
                AssetFileDescriptor afd = getAssetsIgnoreCaseAfd(form, mediaPath);
                try {
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    return;
                } finally {
                    afd.close();
                }
            case REPL_ASSET:
                mediaPlayer.setDataSource(replAssetPath(mediaPath));
                return;
            case SDCARD:
                mediaPlayer.setDataSource(mediaPath);
                return;
            case FILE_URL:
                mediaPlayer.setDataSource(fileUrlToFilePath(mediaPath));
                return;
            case URL:
                mediaPlayer.setDataSource(mediaPath);
                return;
            case CONTENT_URI:
                mediaPlayer.setDataSource(form, Uri.parse(mediaPath));
                return;
            case CONTACT_URI:
                throw new IOException("Unable to load audio or video for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load audio or video " + mediaPath + ".");
        }
    }

    public static void loadVideoView(VideoView videoView, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        switch (mediaSource) {
            case ASSET:
            case URL:
                videoView.setVideoPath(cacheMediaTempFile(form, mediaPath, mediaSource).getAbsolutePath());
                return;
            case REPL_ASSET:
                videoView.setVideoPath(replAssetPath(mediaPath));
                return;
            case SDCARD:
                videoView.setVideoPath(mediaPath);
                return;
            case FILE_URL:
                videoView.setVideoPath(fileUrlToFilePath(mediaPath));
                return;
            case CONTENT_URI:
                videoView.setVideoURI(Uri.parse(mediaPath));
                return;
            case CONTACT_URI:
                throw new IOException("Unable to load video for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load video " + mediaPath + ".");
        }
    }
}
