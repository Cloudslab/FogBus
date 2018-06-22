package com.google.appinventor.components.runtime.util;

import android.os.Environment;
import com.google.appinventor.components.runtime.errors.RuntimeError;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileUtil {
    private static final String DIRECTORY_DOWNLOADS = "Downloads";
    private static final String DIRECTORY_PICTURES = "Pictures";
    private static final String DIRECTORY_RECORDINGS = "Recordings";
    private static final String DOCUMENT_DIRECTORY = "My Documents/";
    private static final String FILENAME_PREFIX = "app_inventor_";

    public static class FileException extends RuntimeError {
        private final int msgNumber;

        public FileException(int errorMsgNumber) {
            this.msgNumber = errorMsgNumber;
        }

        public int getErrorMessageNumber() {
            return this.msgNumber;
        }
    }

    private FileUtil() {
    }

    public static String getFileUrl(String localFileName) {
        return new File(localFileName).toURI().toString();
    }

    public static byte[] readFile(String inputFileName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(inputFileName);
        try {
            copy(in, out);
            return out.toByteArray();
        } finally {
            in.close();
        }
    }

    public static String downloadUrlToFile(String url, String outputFileName) throws IOException {
        InputStream in = new URL(url).openStream();
        try {
            String writeStreamToFile = writeStreamToFile(in, outputFileName);
            return writeStreamToFile;
        } finally {
            in.close();
        }
    }

    public static String writeFile(byte[] array, String outputFileName) throws IOException {
        InputStream in = new ByteArrayInputStream(array);
        try {
            String writeStreamToFile = writeStreamToFile(in, outputFileName);
            return writeStreamToFile;
        } finally {
            in.close();
        }
    }

    public static String copyFile(String inputFileName, String outputFileName) throws IOException {
        InputStream in = new FileInputStream(inputFileName);
        try {
            String writeStreamToFile = writeStreamToFile(in, outputFileName);
            return writeStreamToFile;
        } finally {
            in.close();
        }
    }

    public static String writeStreamToFile(InputStream in, String outputFileName) throws IOException {
        File file = new File(outputFileName);
        file.getParentFile().mkdirs();
        OutputStream out = new FileOutputStream(file);
        try {
            copy(in, out);
            String uri = file.toURI().toString();
            return uri;
        } finally {
            out.flush();
            out.close();
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        OutputStream out2 = new BufferedOutputStream(out, 4096);
        InputStream in2 = new BufferedInputStream(in, 4096);
        while (true) {
            int b = in2.read();
            if (b == -1) {
                out2.flush();
                return;
            }
            out2.write(b);
        }
    }

    public static File getPictureFile(String extension) throws IOException, FileException {
        return getFile(DIRECTORY_PICTURES, extension);
    }

    public static File getRecordingFile(String extension) throws IOException, FileException {
        return getFile(DIRECTORY_RECORDINGS, extension);
    }

    public static File getDownloadFile(String extension) throws IOException, FileException {
        return getFile(DIRECTORY_DOWNLOADS, extension);
    }

    private static File getFile(String category, String extension) throws IOException, FileException {
        return getExternalFile(DOCUMENT_DIRECTORY + category + "/" + FILENAME_PREFIX + System.currentTimeMillis() + "." + extension);
    }

    public static File getExternalFile(String fileName) throws IOException, FileException {
        checkExternalStorageWriteable();
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        File directory = file.getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Unable to create directory " + directory.getAbsolutePath());
        } else if (!file.exists() || file.delete()) {
            return file;
        } else {
            throw new IOException("Cannot overwrite existing file " + file.getAbsolutePath());
        }
    }

    public static void checkExternalStorageWriteable() throws FileException {
        String state = Environment.getExternalStorageState();
        if (!"mounted".equals(state)) {
            if ("mounted_ro".equals(state)) {
                throw new FileException(ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_READONLY);
            }
            throw new FileException(ErrorMessages.ERROR_MEDIA_EXTERNAL_STORAGE_NOT_AVAILABLE);
        }
    }
}
