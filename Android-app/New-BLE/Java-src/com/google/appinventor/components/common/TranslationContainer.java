package com.google.appinventor.components.common;

import com.google.appinventor.components.runtime.Texting;
import java.util.HashMap;
import java.util.Map;

public class TranslationContainer {
    private Map<String, String> CompTransMap = new HashMap();

    public TranslationContainer() {
        this.CompTransMap.put("Basic", "基本");
        this.CompTransMap.put("Media", "媒体");
        this.CompTransMap.put("Animation", "动画");
        this.CompTransMap.put("Social", "社交的");
        this.CompTransMap.put("Sensors", "传感器");
        this.CompTransMap.put("Screen Arrangement", "屏幕布局");
        this.CompTransMap.put("LEGO® MINDSTORMS®", "乐高机器人套件®");
        this.CompTransMap.put("Other stuff", "其他东西");
        this.CompTransMap.put("Not ready for prime time", "测试中的套件");
        this.CompTransMap.put("Old stuff", "旧东西");
        this.CompTransMap.put("Button", "按钮");
        this.CompTransMap.put("Canvas", "画布");
        this.CompTransMap.put("CheckBox", "复选框");
        this.CompTransMap.put("Clock", "时钟");
        this.CompTransMap.put("Image", "图像");
        this.CompTransMap.put("Label", "便签");
        this.CompTransMap.put("ListPicker", "列表选择器");
        this.CompTransMap.put("PasswordTextBox", "密码框");
        this.CompTransMap.put("TextBox", "文本框");
        this.CompTransMap.put("TinyDB", "细小数据库");
        this.CompTransMap.put("Camcorder", "摄像机");
        this.CompTransMap.put("Camera", "相机");
        this.CompTransMap.put("ImagePicker", "画像选择器");
        this.CompTransMap.put("Player", "播放器");
        this.CompTransMap.put("Sound", "声音");
        this.CompTransMap.put("VideoPlayer", "媒体播放器");
        this.CompTransMap.put("Ball", "球");
        this.CompTransMap.put("ImageSprite", "图片精灵");
        this.CompTransMap.put("ContactPicker", "联系信息选择器");
        this.CompTransMap.put("EmailPicker", "邮件选择器");
        this.CompTransMap.put("PhoneCall", "电话");
        this.CompTransMap.put("PhoneNumberPicker", "电话号码选择器");
        this.CompTransMap.put(Texting.META_DATA_SMS_VALUE, "信息");
        this.CompTransMap.put("Twitter", "Twitter");
        this.CompTransMap.put("AccelerometerSensor", "加速度传感器");
        this.CompTransMap.put("LocationSensor", "位置传感器");
        this.CompTransMap.put("OrientationSensor", "方向传感器");
        this.CompTransMap.put("HorizontalArrangement", "水平排列");
        this.CompTransMap.put("TableArrangement", "表安排");
        this.CompTransMap.put("VerticalArrangement", "竖向布置");
        this.CompTransMap.put("NxtColorSensor", "Nxt颜色传感器");
        this.CompTransMap.put("NxtDirectCommands", "Nxt直接命令");
        this.CompTransMap.put("NxtDrive", "Nxt驱动");
        this.CompTransMap.put("NxtLightSensor", "Nxt光传感器");
        this.CompTransMap.put("NxtSoundSensor", "Nxt声音传感器");
        this.CompTransMap.put("NxtTouchSensor", "Nxt触摸传感器");
        this.CompTransMap.put("NxtUltrasonicSensor", "Nxt超声波传感器");
        this.CompTransMap.put("ActivityStarter", "活动启动");
        this.CompTransMap.put("BarcodeScanner", "条码扫描器");
        this.CompTransMap.put(PropertyTypeConstants.PROPERTY_TYPE_BLUETOOTHCLIENT, "蓝牙客户");
        this.CompTransMap.put("BluetoothServer", "蓝牙服务器");
        this.CompTransMap.put("Notifier", "通告人");
        this.CompTransMap.put("SpeechRecognizer", "语音识别");
        this.CompTransMap.put("TextToSpeech", "文本到语音");
        this.CompTransMap.put("TinyWebDB", "细小网络数据库");
        this.CompTransMap.put("Web", "网络");
        this.CompTransMap.put("FusiontablesControl", "Fusiontables控制");
        this.CompTransMap.put("GameClient", "游戏客户端");
        this.CompTransMap.put("SoundRecorder", "声音记录器");
        this.CompTransMap.put("Voting", "投票");
        this.CompTransMap.put("WebViewer", "网页浏览器");
    }

    public String getCorrespondingString(String key) {
        if (this.CompTransMap.containsKey(key)) {
            return (String) this.CompTransMap.get(key);
        }
        return "Missing name";
    }
}
