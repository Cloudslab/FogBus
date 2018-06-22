package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

@SimpleObject
@DesignerComponent(category = ComponentCategory.STORAGE, description = "Non-visible component for storing and retrieving files. Use this component to write or read files on your device. The default behaviour is to write files to the private data directory associated with your App. The Companion is special cased to write files to /sdcard/AppInventor/data to facilitate debugging. If the file path starts with a slash (/), then the file is created relative to /sdcard. For example writing a file to /myFile.txt will write the file in /sdcard/myFile.txt.", iconName = "images/file.png", nonVisible = true, version = 2)
@UsesPermissions(permissionNames = "android.permission.WRITE_EXTERNAL_STORAGE, android.permission.READ_EXTERNAL_STORAGE")
public class File extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "FileComponent";
    public static final String NO_ASSETS = "No_Assets";
    private final int BUFFER_LENGTH = 4096;
    private final Activity activity;
    private boolean isRepl = false;

    public File(ComponentContainer container) {
        super(container.$form());
        if (this.form instanceof ReplForm) {
            this.isRepl = true;
        }
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Saves text to a file. If the filename begins with a slash (/) the file is written to the sdcard. For example writing to /myFile.txt will write the file to /sdcard/myFile.txt. If the filename does not start with a slash, it will be written in the programs private data directory where it will not be accessible to other programs on the phone. There is a special exception for the AI Companion where these files are written to /sdcard/AppInventor/data to facilitate debugging. Note that this block will overwrite a file if it already exists.\n\nIf you want to add content to a file use the append block.")
    public void SaveFile(String text, String fileName) {
        if (fileName.startsWith("/")) {
            FileUtil.checkExternalStorageWriteable();
        }
        Write(fileName, text, false);
    }

    @SimpleFunction(description = "Appends text to the end of a file storage, creating the file if it does not exist. See the help text under SaveFile for information about where files are written.")
    public void AppendToFile(String text, String fileName) {
        if (fileName.startsWith("/")) {
            FileUtil.checkExternalStorageWriteable();
        }
        Write(fileName, text, true);
    }

    @SimpleFunction(description = "Reads text from a file in storage. Prefix the filename with / to read from a specific file on the SD card. for instance /myFile.txt will read the file /sdcard/myFile.txt. To read assets packaged with an application (also works for the Companion) start the filename with // (two slashes). If a filename does not start with a slash, it will be read from the applications private storage (for packaged apps) and from /sdcard/AppInventor/data for the Companion.")
    public void ReadFrom(final String fileName) {
        try {
            InputStream inputStream;
            if (!fileName.startsWith("//")) {
                String filepath = AbsoluteFileName(fileName);
                Log.d(LOG_TAG, "filepath = " + filepath);
                inputStream = new FileInputStream(filepath);
            } else if (this.isRepl) {
                inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/AppInventor/assets/" + fileName);
            } else {
                inputStream = this.form.getAssets().open(fileName.substring(2));
            }
            final InputStream asyncInputStream = inputStream;
            AsynchUtil.runAsynchronously(new Runnable() {
                public void run() {
                    File.this.AsyncRead(asyncInputStream, fileName);
                }
            });
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "FileNotFoundException", e);
            this.form.dispatchErrorOccurredEvent(this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, fileName);
        } catch (IOException e2) {
            Log.e(LOG_TAG, "IOException", e2);
            this.form.dispatchErrorOccurredEvent(this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, fileName);
        }
    }

    @SimpleFunction(description = "Deletes a file from storage. Prefix the filename with / to delete a specific file in the SD card, for instance /myFile.txt. will delete the file /sdcard/myFile.txt. If the file does not begin with a /, then the file located in the programs private storage will be deleted. Starting the file with // is an error because assets files cannot be deleted.")
    public void Delete(String fileName) {
        if (fileName.startsWith("//")) {
            this.form.dispatchErrorOccurredEvent(this, "DeleteFile", ErrorMessages.ERROR_CANNOT_DELETE_ASSET, fileName);
            return;
        }
        new java.io.File(AbsoluteFileName(fileName)).delete();
    }

    private void Write(final String filename, final String text, final boolean append) {
        if (!filename.startsWith("//")) {
            AsynchUtil.runAsynchronously(new Runnable() {

                class C01761 implements Runnable {
                    C01761() {
                    }

                    public void run() {
                        File.this.AfterFileSaved(filename);
                    }
                }

                public void run() {
                    java.io.File file = new java.io.File(File.this.AbsoluteFileName(filename));
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            if (append) {
                                File.this.form.dispatchErrorOccurredEvent(File.this, "AppendTo", ErrorMessages.ERROR_CANNOT_CREATE_FILE, filepath);
                                return;
                            }
                            File.this.form.dispatchErrorOccurredEvent(File.this, "SaveFile", ErrorMessages.ERROR_CANNOT_CREATE_FILE, filepath);
                            return;
                        }
                    }
                    try {
                        FileOutputStream fileWriter = new FileOutputStream(file, append);
                        OutputStreamWriter out = new OutputStreamWriter(fileWriter);
                        out.write(text);
                        out.flush();
                        out.close();
                        fileWriter.close();
                        File.this.activity.runOnUiThread(new C01761());
                    } catch (IOException e2) {
                        if (append) {
                            File.this.form.dispatchErrorOccurredEvent(File.this, "AppendTo", ErrorMessages.ERROR_CANNOT_WRITE_TO_FILE, filepath);
                            return;
                        }
                        File.this.form.dispatchErrorOccurredEvent(File.this, "SaveFile", ErrorMessages.ERROR_CANNOT_WRITE_TO_FILE, filepath);
                    }
                }
            });
        } else if (append) {
            this.form.dispatchErrorOccurredEvent(this, "AppendTo", ErrorMessages.ERROR_CANNOT_WRITE_ASSET, filename);
        } else {
            this.form.dispatchErrorOccurredEvent(this, "SaveFile", ErrorMessages.ERROR_CANNOT_WRITE_ASSET, filename);
        }
    }

    private String normalizeNewLines(String s) {
        return s.replaceAll("\r\n", "\n");
    }

    private void AsyncRead(InputStream fileInput, String fileName) {
        FileNotFoundException e;
        Throwable th;
        InputStreamReader input = null;
        try {
            InputStreamReader input2 = new InputStreamReader(fileInput);
            try {
                StringWriter output = new StringWriter();
                char[] buffer = new char[4096];
                while (true) {
                    int length = input2.read(buffer, 0, 4096);
                    if (length <= 0) {
                        break;
                    }
                    output.write(buffer, 0, length);
                }
                final String text = normalizeNewLines(output.toString());
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        File.this.GotText(text);
                    }
                });
                if (input2 != null) {
                    try {
                        input2.close();
                        input = input2;
                        return;
                    } catch (IOException e2) {
                        input = input2;
                        return;
                    }
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                input = input2;
                try {
                    Log.e(LOG_TAG, "FileNotFoundException", e);
                    this.form.dispatchErrorOccurredEvent(this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, fileName);
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e = e6;
                input = input2;
                Log.e(LOG_TAG, "IOException", e);
                this.form.dispatchErrorOccurredEvent(this, "ReadFrom", ErrorMessages.ERROR_CANNOT_READ_FILE, fileName);
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e7) {
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                input = input2;
                if (input != null) {
                    input.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e8) {
            e = e8;
            Log.e(LOG_TAG, "FileNotFoundException", e);
            this.form.dispatchErrorOccurredEvent(this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, fileName);
            if (input != null) {
                input.close();
            }
        } catch (IOException e9) {
            e = e9;
            Log.e(LOG_TAG, "IOException", e);
            this.form.dispatchErrorOccurredEvent(this, "ReadFrom", ErrorMessages.ERROR_CANNOT_READ_FILE, fileName);
            if (input != null) {
                input.close();
            }
        }
    }

    @SimpleEvent(description = "Event indicating that the contents from the file have been read.")
    public void GotText(String text) {
        EventDispatcher.dispatchEvent(this, "GotText", text);
    }

    @SimpleEvent(description = "Event indicating that the contents of the file have been written.")
    public void AfterFileSaved(String fileName) {
        EventDispatcher.dispatchEvent(this, "AfterFileSaved", fileName);
    }

    private String AbsoluteFileName(String filename) {
        if (filename.startsWith("/")) {
            return Environment.getExternalStorageDirectory().getPath() + filename;
        }
        java.io.File dirPath = this.activity.getFilesDir();
        if (this.isRepl) {
            dirPath = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/AppInventor/data/");
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
        }
        return dirPath.getPath() + "/" + filename;
    }
}
