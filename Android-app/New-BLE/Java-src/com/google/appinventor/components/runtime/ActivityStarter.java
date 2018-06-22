package com.google.appinventor.components.runtime;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;

@SimpleObject
@DesignerComponent(category = ComponentCategory.CONNECTIVITY, description = "A component that can launch an activity using the <code>StartActivity</code> method. \n<p>Activities that can be launched include:<ul> <li> Starting another App Inventor for Android app. \n To do so, first      find out the <em>class</em> of the other application by      downloading the source code and using a file explorer or unzip      utility to find a file named      \"youngandroidproject/project.properties\".  \n The first line of      the file will start with \"main=\" and be followed by the class      name; for example,      <code>main=com.gmail.Bitdiddle.Ben.HelloPurr.Screen1</code>.       (The first components indicate that it was created by      Ben.Bitdiddle@gmail.com.)  \n To make your      <code>ActivityStarter</code> launch this application, set the      following properties: <ul>\n      <li> <code>ActivityPackage</code> to the class name, dropping the           last component (for example,           <code>com.gmail.Bitdiddle.Ben.HelloPurr</code>)</li>\n      <li> <code>ActivityClass</code> to the entire class name (for           example,           <code>com.gmail.Bitdiddle.Ben.HelloPurr.Screen1</code>)</li>      </ul></li> \n<li> Starting the camera application by setting the following      properties:<ul> \n     <li> <code>Action: android.intent.action.MAIN</code> </li> \n     <li> <code>ActivityPackage: com.android.camera</code> </li> \n     <li> <code>ActivityClass: com.android.camera.Camera</code></li>\n      </ul></li>\n<li> Performing web search.  Assuming the term you want to search      for is \"vampire\" (feel free to substitute your own choice), \n     set the properties to:\n<ul><code>     <li>Action: android.intent.action.WEB_SEARCH</li>      <li>ExtraKey: query</li>      <li>ExtraValue: vampire</li>      <li>ActivityPackage: com.google.android.providers.enhancedgooglesearch</li>     <li>ActivityClass: com.google.android.providers.enhancedgooglesearch.Launcher</li>      </code></ul></li> \n<li> Opening a browser to a specified web page.  Assuming the page you      want to go to is \"www.facebook.com\" (feel free to substitute      your own choice), set the properties to:\n<ul><code>      <li>Action: android.intent.action.VIEW</li>      <li>DataUri: http://www.facebook.com</li> </code> </ul> </li> </ul></p>", designerHelpDescription = "A component that can launch an activity using the <code>StartActivity</code> method.<p>Activities that can be launched include: <ul> \n<li> starting other App Inventor for Android apps </li> \n<li> starting the camera application </li> \n<li> performing web search </li> \n<li> opening a browser to a specified web page</li> \n<li> opening the map application to a specified location</li></ul> \nYou can also launch activities that return text data.  See the documentation on using the Activity Starter for examples.</p>", iconName = "images/activityStarter.png", nonVisible = true, version = 6)
public class ActivityStarter extends AndroidNonvisibleComponent implements ActivityResultListener, Component, Deleteable {
    private String action;
    private String activityClass;
    private String activityPackage;
    private final ComponentContainer container;
    private String dataType;
    private String dataUri;
    private String extraKey;
    private String extraValue;
    private YailList extras;
    private int requestCode;
    private String result = "";
    private Intent resultIntent;
    private String resultName;

    public ActivityStarter(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        Action("android.intent.action.MAIN");
        ActivityPackage("");
        ActivityClass("");
        DataUri("");
        DataType("");
        ExtraKey("");
        ExtraValue("");
        Extras(new YailList());
        ResultName("");
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Action() {
        return this.action;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Action(String action) {
        this.action = action.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the extra key that will be passed to the activity.\nDEPRECATED: New code should use Extras property instead.")
    public String ExtraKey() {
        return this.extraKey;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void ExtraKey(String extraKey) {
        this.extraKey = extraKey.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the extra value that will be passed to the activity.\nDEPRECATED: New code should use Extras property instead.")
    public String ExtraValue() {
        return this.extraValue;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void ExtraValue(String extraValue) {
        this.extraValue = extraValue.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ResultName() {
        return this.resultName;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void ResultName(String resultName) {
        this.resultName = resultName.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Result() {
        return this.result;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String DataUri() {
        return this.dataUri;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void DataUri(String dataUri) {
        this.dataUri = dataUri.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String DataType() {
        return this.dataType;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void DataType(String dataType) {
        this.dataType = dataType.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ActivityPackage() {
        return this.activityPackage;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void ActivityPackage(String activityPackage) {
        this.activityPackage = activityPackage.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ActivityClass() {
        return this.activityClass;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void ActivityClass(String activityClass) {
        this.activityClass = activityClass.trim();
    }

    @SimpleEvent(description = "Event raised after this ActivityStarter returns.")
    public void AfterActivity(String result) {
        EventDispatcher.dispatchEvent(this, "AfterActivity", result);
    }

    @SimpleEvent(description = "Event raised if this ActivityStarter returns because the activity was canceled.")
    public void ActivityCanceled() {
        EventDispatcher.dispatchEvent(this, "ActivityCanceled", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ResultType() {
        if (this.resultIntent != null) {
            String resultType = this.resultIntent.getType();
            if (resultType != null) {
                return resultType;
            }
        }
        return "";
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ResultUri() {
        if (this.resultIntent != null) {
            String resultUri = this.resultIntent.getDataString();
            if (resultUri != null) {
                return resultUri;
            }
        }
        return "";
    }

    @SimpleProperty
    public void Extras(YailList pairs) {
        Object[] arr$ = pairs.toArray();
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            Object pair = arr$[i$];
            boolean isYailList = pair instanceof YailList;
            boolean isPair = isYailList ? ((YailList) pair).size() == 2 : false;
            if (isYailList && isPair) {
                i$++;
            } else {
                throw new YailRuntimeError("Argument to Extras should be a list of pairs", "ActivityStarter Error");
            }
        }
        this.extras = pairs;
    }

    @SimpleProperty
    public YailList Extras() {
        return this.extras;
    }

    @SimpleFunction(description = "Returns the name of the activity that corresponds to this ActivityStarter, or an empty string if no corresponding activity can be found.")
    public String ResolveActivity() {
        ResolveInfo resolveInfo = this.container.$context().getPackageManager().resolveActivity(buildActivityIntent(), 0);
        if (resolveInfo == null || resolveInfo.activityInfo == null) {
            return "";
        }
        return resolveInfo.activityInfo.name;
    }

    @SimpleFunction(description = "Start the activity corresponding to this ActivityStarter.")
    public void StartActivity() {
        this.resultIntent = null;
        this.result = "";
        Intent intent = buildActivityIntent();
        if (this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
        }
        if (intent == null) {
            this.form.dispatchErrorOccurredEvent(this, "StartActivity", ErrorMessages.ERROR_ACTIVITY_STARTER_NO_ACTION_INFO, new Object[0]);
            return;
        }
        try {
            this.container.$context().startActivityForResult(intent, this.requestCode);
            AnimationUtil.ApplyOpenScreenAnimation(this.container.$context(), this.container.$form().getOpenAnimType());
        } catch (ActivityNotFoundException e) {
            this.form.dispatchErrorOccurredEvent(this, "StartActivity", ErrorMessages.ERROR_ACTIVITY_STARTER_NO_CORRESPONDING_ACTIVITY, new Object[0]);
        }
    }

    private Intent buildActivityIntent() {
        Uri uri;
        if (this.dataUri.length() != 0) {
            uri = Uri.parse(this.dataUri);
        } else {
            uri = null;
        }
        Intent intent = uri != null ? new Intent(this.action, uri) : new Intent(this.action);
        if (TextUtils.isEmpty(Action())) {
            return null;
        }
        if (this.dataType.length() != 0) {
            if (uri != null) {
                intent.setDataAndType(uri, this.dataType);
            } else {
                intent.setType(this.dataType);
            }
        }
        if (this.activityPackage.length() != 0 || this.activityClass.length() != 0) {
            intent.setComponent(new ComponentName(this.activityPackage, this.activityClass));
        } else if (Action() == "android.intent.action.MAIN") {
            return null;
        }
        if (!(this.extraKey.length() == 0 || this.extraValue.length() == 0)) {
            Log.i("ActivityStarter", "Adding extra, key = " + this.extraKey + " value = " + this.extraValue);
            intent.putExtra(this.extraKey, this.extraValue);
        }
        for (YailList castExtra : this.extras.toArray()) {
            String key = castExtra.getString(0);
            String value = castExtra.getString(1);
            if (!(key.length() == 0 || value.length() == 0)) {
                Log.i("ActivityStarter", "Adding extra (pairs), key = " + key + " value = " + value);
                intent.putExtra(key, value);
            }
        }
        return intent;
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode) {
            Log.i("ActivityStarter", "resultReturned - resultCode = " + resultCode);
            if (resultCode == -1) {
                this.resultIntent = data;
                if (this.resultName.length() == 0 || this.resultIntent == null || !this.resultIntent.hasExtra(this.resultName)) {
                    this.result = "";
                } else {
                    this.result = this.resultIntent.getStringExtra(this.resultName);
                }
                AfterActivity(this.result);
            } else if (resultCode == 0) {
                ActivityCanceled();
            }
        }
    }

    @SimpleEvent(description = "The ActivityError event is no longer used. Please use the Screen.ErrorOccurred event instead.", userVisible = false)
    public void ActivityError(String message) {
    }

    public void onDelete() {
        this.form.unregisterForActivityResult(this);
    }
}
