package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum ComponentCategory {
    USERINTERFACE("User Interface"),
    LAYOUT("Layout"),
    MEDIA("Media"),
    ANIMATION("Drawing and Animation"),
    MAPS("Maps"),
    SENSORS("Sensors"),
    SOCIAL("Social"),
    STORAGE("Storage"),
    CONNECTIVITY("Connectivity"),
    LEGOMINDSTORMS("LEGO® MINDSTORMS®"),
    EXPERIMENTAL("Experimental"),
    EXTENSION("Extension"),
    INTERNAL("For internal use only"),
    UNINITIALIZED("Uninitialized");
    
    private static final Map<String, String> DOC_MAP = null;
    private String name;

    static {
        DOC_MAP = new HashMap();
        DOC_MAP.put("User Interface", "userinterface");
        DOC_MAP.put("Layout", "layout");
        DOC_MAP.put("Media", "media");
        DOC_MAP.put("Drawing and Animation", "animation");
        DOC_MAP.put("Maps", "maps");
        DOC_MAP.put("Sensors", "sensors");
        DOC_MAP.put("Social", "social");
        DOC_MAP.put("Storage", "storage");
        DOC_MAP.put("Connectivity", "connectivity");
        DOC_MAP.put("LEGO® MINDSTORMS®", "legomindstorms");
        DOC_MAP.put("Experimental", "experimental");
        DOC_MAP.put("Extension", "extension");
    }

    private ComponentCategory(String categoryName) {
        this.name = categoryName;
    }

    public String getName() {
        return this.name;
    }

    public String getDocName() {
        return (String) DOC_MAP.get(this.name);
    }
}
