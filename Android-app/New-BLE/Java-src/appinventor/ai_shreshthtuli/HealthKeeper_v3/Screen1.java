package appinventor.ai_shreshthtuli.HealthKeeper_v3;

import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import edu.mit.appinventor.ble.BluetoothLE;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;

/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen1").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("g$utf").readResolve());
    static final PairWithPosition Lit100 = PairWithPosition.make(Lit34, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471573);
    static final PairWithPosition Lit101 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471784);
    static final PairWithPosition Lit102 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471820), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471814);
    static final PairWithPosition Lit103 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472035);
    static final IntNum Lit104 = IntNum.make(2);
    static final PairWithPosition Lit105 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472071), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472065);
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("Notifier1").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("ShowTextDialog").readResolve());
    static final PairWithPosition Lit108 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, PairWithPosition.make(Lit34, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472225), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472220), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472214);
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("ConnectWithAddress").readResolve());
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 57614);
    static final PairWithPosition Lit110 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472336);
    static final PairWithPosition Lit111 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472544), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 472538);
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("pairsensor$AfterPicking").readResolve());
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("AfterPicking").readResolve());
    static final FString Lit114 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final IntNum Lit116 = IntNum.make(-1015);
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit118 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit119 = IntNum.make(16);
    static final PairWithPosition Lit12 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 57614);
    static final IntNum Lit120;
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit123;
    static final FString Lit124 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit125 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("Label6").readResolve());
    static final IntNum Lit127;
    static final FString Lit128 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit129 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("g$numbers").readResolve());
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("ip").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("Hint").readResolve());
    static final IntNum Lit132;
    static final FString Lit133 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve());
    static final IntNum Lit136 = IntNum.make(-1005);
    static final FString Lit137 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit138 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit139 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve());
    static final PairWithPosition Lit14 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 61833);
    static final IntNum Lit140 = IntNum.make(-1019);
    static final FString Lit141 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit142 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit143 = ((SimpleSymbol) new SimpleSymbol("connect").readResolve());
    static final IntNum Lit144 = IntNum.make(-1050);
    static final FString Lit145 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit146 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856226), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856220);
    static final PairWithPosition Lit147 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856363), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856357);
    static final PairWithPosition Lit148 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856502), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856496);
    static final SimpleSymbol Lit149 = ((SimpleSymbol) new SimpleSymbol("Disconnect").readResolve());
    static final PairWithPosition Lit15 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 61833);
    static final PairWithPosition Lit150 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856713), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 856707);
    static final SimpleSymbol Lit151 = ((SimpleSymbol) new SimpleSymbol("connect$Click").readResolve());
    static final SimpleSymbol Lit152 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit153 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit154 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final IntNum Lit155 = IntNum.make(16777215);
    static final IntNum Lit156 = IntNum.make(10);
    static final IntNum Lit157 = IntNum.make(-1005);
    static final IntNum Lit158 = IntNum.make(-1050);
    static final IntNum Lit159;
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("g$masterIP").readResolve());
    static final FString Lit160 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit161 = ((SimpleSymbol) new SimpleSymbol("ClearAll").readResolve());
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve());
    static final FString Lit163 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit164 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement5").readResolve());
    static final FString Lit165 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit166 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit167 = ((SimpleSymbol) new SimpleSymbol("Label7").readResolve());
    static final IntNum Lit168 = IntNum.make(-1050);
    static final IntNum Lit169;
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("g$list").readResolve());
    static final FString Lit170 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit171 = new FString("edu.mit.appinventor.ble.BluetoothLE");
    static final FString Lit172 = new FString("edu.mit.appinventor.ble.BluetoothLE");
    static final SimpleSymbol Lit173 = ((SimpleSymbol) new SimpleSymbol("DeviceList").readResolve());
    static final PairWithPosition Lit174 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1052805);
    static final PairWithPosition Lit175 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053171);
    static final PairWithPosition Lit176 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053207), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053201);
    static final PairWithPosition Lit177 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053255), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053249);
    static final PairWithPosition Lit178 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053280), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053274);
    static final PairWithPosition Lit179 = PairWithPosition.make(Lit34, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053297);
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("g$exist").readResolve());
    static final PairWithPosition Lit180 = PairWithPosition.make(Lit258, PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053510), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053505);
    static final PairWithPosition Lit181 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053743);
    static final PairWithPosition Lit182 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053779), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053773);
    static final PairWithPosition Lit183 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053819), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053813);
    static final PairWithPosition Lit184 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053840), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053833), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1053827);
    static final SimpleSymbol Lit185 = ((SimpleSymbol) new SimpleSymbol("Elements").readResolve());
    static final SimpleSymbol Lit186;
    static final SimpleSymbol Lit187 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$DeviceFound").readResolve());
    static final SimpleSymbol Lit188 = ((SimpleSymbol) new SimpleSymbol("DeviceFound").readResolve());
    static final PairWithPosition Lit189 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061014), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061008);
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("g$jumper").readResolve());
    static final PairWithPosition Lit190 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061236), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061230);
    static final PairWithPosition Lit191 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061261), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061255);
    static final PairWithPosition Lit192 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061302), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1061296);
    static final SimpleSymbol Lit193 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$Connected").readResolve());
    static final SimpleSymbol Lit194 = ((SimpleSymbol) new SimpleSymbol("Connected").readResolve());
    static final SimpleSymbol Lit195 = ((SimpleSymbol) new SimpleSymbol("$stringValues").readResolve());
    static final PairWithPosition Lit196 = PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069206);
    static final PairWithPosition Lit197 = PairWithPosition.make(Lit34, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069233);
    static final PairWithPosition Lit198 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069503), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069497);
    static final PairWithPosition Lit199 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069533);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("g$service_UUID").readResolve());
    static final IntNum Lit200 = IntNum.make(4);
    static final PairWithPosition Lit201 = PairWithPosition.make(Lit258, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069559), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069554);
    static final PairWithPosition Lit202 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069757), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069751);
    static final PairWithPosition Lit203 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit32, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069804), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069797), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1069791);
    static final PairWithPosition Lit204 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070064), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070058);
    static final PairWithPosition Lit205 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit32, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070111), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070104), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070098);
    static final PairWithPosition Lit206 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070375), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070369);
    static final PairWithPosition Lit207 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070484), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070478);
    static final PairWithPosition Lit208 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070508), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070502);
    static final PairWithPosition Lit209 = PairWithPosition.make(Lit34, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070608);
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("g$characteristic_UUID").readResolve());
    static final PairWithPosition Lit210 = PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070937);
    static final PairWithPosition Lit211 = PairWithPosition.make(Lit32, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070974), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070966);
    static final PairWithPosition Lit212 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070995), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1070989);
    static final PairWithPosition Lit213 = PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071302);
    static final PairWithPosition Lit214 = PairWithPosition.make(Lit32, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071339), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071331);
    static final PairWithPosition Lit215 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071360), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071354);
    static final PairWithPosition Lit216 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071390);
    static final PairWithPosition Lit217 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit32, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071424), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071417), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071411);
    static final PairWithPosition Lit218 = PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071707);
    static final PairWithPosition Lit219 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071740), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1071734);
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("g$devices").readResolve());
    static final PairWithPosition Lit220 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1072013), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1072007);
    static final PairWithPosition Lit221 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1072122), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1072116);
    static final PairWithPosition Lit222 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1072146), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1072140);
    static final SimpleSymbol Lit223 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$StringsReceived").readResolve());
    static final SimpleSymbol Lit224 = ((SimpleSymbol) new SimpleSymbol("StringsReceived").readResolve());
    static final FString Lit225 = new FString("com.google.appinventor.components.runtime.Clock");
    static final FString Lit226 = new FString("com.google.appinventor.components.runtime.Clock");
    static final SimpleSymbol Lit227 = ((SimpleSymbol) new SimpleSymbol("IsDeviceConnected").readResolve());
    static final SimpleSymbol Lit228 = ((SimpleSymbol) new SimpleSymbol("RegisterForStrings").readResolve());
    static final PairWithPosition Lit229 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, PairWithPosition.make(Lit34, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1097946), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1097941), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1097935);
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("p$convert").readResolve());
    static final SimpleSymbol Lit230 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final SimpleSymbol Lit231 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final FString Lit232 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final FString Lit233 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final SimpleSymbol Lit234 = ((SimpleSymbol) new SimpleSymbol("$response").readResolve());
    static final PairWithPosition Lit235 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126552), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126546);
    static final PairWithPosition Lit236;
    static final SimpleSymbol Lit237 = ((SimpleSymbol) new SimpleSymbol("StoreValue").readResolve());
    static final PairWithPosition Lit238 = PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126823);
    static final PairWithPosition Lit239;
    static final PairWithPosition Lit24 = PairWithPosition.make(Lit258, PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94434), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94429);
    static final PairWithPosition Lit240 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126922), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126916);
    static final PairWithPosition Lit241;
    static final SimpleSymbol Lit242 = ((SimpleSymbol) new SimpleSymbol("Notifier1$AfterTextInput").readResolve());
    static final SimpleSymbol Lit243 = ((SimpleSymbol) new SimpleSymbol("AfterTextInput").readResolve());
    static final FString Lit244 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit245 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit246 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit247 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit248 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit249 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final PairWithPosition Lit25 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94465), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94459);
    static final SimpleSymbol Lit250 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit251 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit252 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit253 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit254 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit255 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit256 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit257 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit258 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final PairWithPosition Lit26 = PairWithPosition.make(Lit258, PairWithPosition.make(Lit186, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94434), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94429);
    static final PairWithPosition Lit27 = PairWithPosition.make(Lit186, PairWithPosition.make(Lit32, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94465), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 94459);
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("AboutScreen").readResolve());
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$bpm").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final IntNum Lit31;
    static final SimpleSymbol Lit32;
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit34;
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit36 = IntNum.make(3);
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit39;
    static final IntNum Lit4 = IntNum.make(0);
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("ShowStatusBar").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("Theme").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("device").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("g$o2").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("data").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("StartScanning").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("TimerEnabled").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit57 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("g$deviceName").readResolve());
    static final IntNum Lit60 = IntNum.make(-1005);
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit62 = IntNum.make(-2);
    static final FString Lit63 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit64 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit68 = IntNum.make(30);
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$deviceID").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit71 = IntNum.make(1);
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit73;
    static final FString Lit74 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final IntNum Lit77 = IntNum.make(42);
    static final IntNum Lit78;
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("g$utfinput").readResolve());
    static final FString Lit80 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("Label3").readResolve());
    static final IntNum Lit82;
    static final FString Lit83 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final IntNum Lit86 = IntNum.make(-1008);
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.ListPicker");
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("pairsensor").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("g$numberoutput").readResolve());
    static final IntNum Lit90 = IntNum.make(18);
    static final IntNum Lit91 = IntNum.make(-1050);
    static final FString Lit92 = new FString("com.google.appinventor.components.runtime.ListPicker");
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("TinyDB1").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("GetValue").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("$item").readResolve());
    static final PairWithPosition Lit96 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit258, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471227), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471221);
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("Selection").readResolve());
    static final PairWithPosition Lit98 = PairWithPosition.make(Lit29, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471279), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 471273);
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("GetTags").readResolve());
    public static Screen1 Screen1;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn36 = null;
    static final ModuleMethod lambda$Fn37 = null;
    static final ModuleMethod lambda$Fn38 = null;
    static final ModuleMethod lambda$Fn39 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn40 = null;
    static final ModuleMethod lambda$Fn41 = null;
    static final ModuleMethod lambda$Fn42 = null;
    static final ModuleMethod lambda$Fn43 = null;
    static final ModuleMethod lambda$Fn44 = null;
    static final ModuleMethod lambda$Fn45 = null;
    static final ModuleMethod lambda$Fn46 = null;
    static final ModuleMethod lambda$Fn47 = null;
    static final ModuleMethod lambda$Fn48 = null;
    static final ModuleMethod lambda$Fn49 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn50 = null;
    static final ModuleMethod lambda$Fn51 = null;
    static final ModuleMethod lambda$Fn52 = null;
    static final ModuleMethod lambda$Fn53 = null;
    static final ModuleMethod lambda$Fn54 = null;
    static final ModuleMethod lambda$Fn55 = null;
    static final ModuleMethod lambda$Fn56 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public BluetoothLE BluetoothLE1;
    public final ModuleMethod BluetoothLE1$Connected;
    public final ModuleMethod BluetoothLE1$DeviceFound;
    public final ModuleMethod BluetoothLE1$StringsReceived;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public Clock Clock1;
    public final ModuleMethod Clock1$Timer;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public HorizontalArrangement HorizontalArrangement4;
    public HorizontalArrangement HorizontalArrangement5;
    public Label Label1;
    public Label Label2;
    public Label Label3;
    public Label Label6;
    public Label Label7;
    public Notifier Notifier1;
    public final ModuleMethod Notifier1$AfterTextInput;
    public final ModuleMethod Screen1$Initialize;
    public TinyDB TinyDB1;
    public VerticalArrangement VerticalArrangement1;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public Button connect;
    public final ModuleMethod connect$Click;
    public Label data;
    public Label device;
    public final ModuleMethod dispatchEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public TextBox ip;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public ListPicker pairsensor;
    public final ModuleMethod pairsensor$AfterPicking;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main = this;

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 78 ? this.$main.BluetoothLE1$StringsReceived(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 78) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 15:
                    return Screen1.lambda2();
                case 16:
                    this.$main.$define();
                    return Values.empty;
                case 17:
                    return Screen1.lambda3();
                case 18:
                    return Screen1.lambda4();
                case 19:
                    return Screen1.lambda5();
                case 20:
                    return Screen1.lambda6();
                case 21:
                    return Screen1.lambda7();
                case 22:
                    return Screen1.lambda8();
                case 23:
                    return Screen1.lambda9();
                case 24:
                    return Screen1.lambda10();
                case 25:
                    return Screen1.lambda11();
                case 26:
                    return Screen1.lambda12();
                case 27:
                    return Screen1.lambda13();
                case 28:
                    return Screen1.lambda14();
                case 29:
                    return Screen1.lambda15();
                case 30:
                    return Screen1.lambda16();
                case 31:
                    return Screen1.lambda17();
                case 32:
                    return Screen1.lambda18();
                case 33:
                    return Screen1.lambda20();
                case 34:
                    return Screen1.lambda19();
                case 35:
                    return Screen1.lambda21();
                case 36:
                    return this.$main.Screen1$Initialize();
                case 37:
                    return Screen1.lambda22();
                case 38:
                    return Screen1.lambda23();
                case 39:
                    return Screen1.lambda24();
                case 40:
                    return Screen1.lambda25();
                case 41:
                    return Screen1.lambda26();
                case 42:
                    return Screen1.lambda27();
                case 43:
                    return Screen1.lambda28();
                case 44:
                    return Screen1.lambda29();
                case 45:
                    return Screen1.lambda30();
                case 46:
                    return Screen1.lambda31();
                case 47:
                    return Screen1.lambda32();
                case 48:
                    return Screen1.lambda33();
                case 50:
                    return this.$main.pairsensor$AfterPicking();
                case 51:
                    return Screen1.lambda35();
                case 52:
                    return Screen1.lambda36();
                case 53:
                    return Screen1.lambda37();
                case 54:
                    return Screen1.lambda38();
                case 55:
                    return Screen1.lambda39();
                case 56:
                    return Screen1.lambda40();
                case 57:
                    return Screen1.lambda41();
                case 58:
                    return Screen1.lambda42();
                case 59:
                    return Screen1.lambda43();
                case 60:
                    return Screen1.lambda44();
                case 61:
                    return Screen1.lambda45();
                case 62:
                    return Screen1.lambda46();
                case 63:
                    return Screen1.lambda47();
                case 64:
                    return Screen1.lambda48();
                case 65:
                    return Screen1.lambda49();
                case 66:
                    return Screen1.lambda50();
                case 67:
                    return this.$main.connect$Click();
                case 68:
                    return Screen1.lambda51();
                case 69:
                    return Screen1.lambda52();
                case 70:
                    return this.$main.Button1$Click();
                case 71:
                    return Screen1.lambda53();
                case 72:
                    return Screen1.lambda54();
                case 73:
                    return Screen1.lambda55();
                case 74:
                    return Screen1.lambda56();
                case 76:
                    return this.$main.BluetoothLE1$DeviceFound();
                case 77:
                    return this.$main.BluetoothLE1$Connected();
                case 79:
                    return this.$main.Clock1$Timer();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 15:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 16:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 17:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 18:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 20:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 22:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 23:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 24:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 25:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 26:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 27:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 28:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 29:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 30:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 31:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 32:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 33:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 34:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 35:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 36:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 37:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 38:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 39:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 40:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 41:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 42:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 43:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 44:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 45:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 46:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 47:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 48:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 50:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 51:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 52:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 53:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 54:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 55:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 56:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 57:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 58:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 59:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 60:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 61:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 62:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 63:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 64:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 65:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 66:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 67:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 68:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 69:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 70:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 71:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 72:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 73:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 74:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 76:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 77:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 79:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 3:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 10:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 49:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 75:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 80:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 3:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 6:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 14:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 8:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 13:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    if (!(obj4 instanceof String)) {
                        return -786428;
                    }
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case 3:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "lookup-in-form-environment", 1, obj);
                    }
                case 5:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "is-bound-in-form-environment", 1, obj);
                    }
                case 10:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case 11:
                    this.$main.sendError(obj);
                    return Values.empty;
                case 12:
                    this.$main.processException(obj);
                    return Values.empty;
                case 49:
                    return Screen1.lambda34(obj);
                case 75:
                    return Screen1.lambda57(obj);
                case 80:
                    return this.$main.Notifier1$AfterTextInput(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            switch (moduleMethod.selector) {
                case 8:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case 13:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "dispatchEvent", 1, obj);
                    }
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 2:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case 3:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 6:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "add-to-global-var-environment", 1, obj);
                    }
                case 7:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case 9:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case 14:
                    return this.$main.lookupHandler(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit29 = simpleSymbol;
        Lit241 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit29, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1127121), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1127115);
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("list").readResolve();
        Lit186 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit32 = simpleSymbol2;
        Lit239 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126859), "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126853);
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit34 = simpleSymbol;
        Lit236 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1529740887197_0.17582874307084995-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Screen1.yail", 1126569);
        int[] iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit169 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit159 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = -1;
        Lit132 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit127 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit123 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit120 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit82 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = -1;
        Lit78 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = -1;
        Lit73 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_DKGRAY;
        Lit39 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_CYAN;
        Lit31 = IntNum.make(iArr);
    }

    public Screen1() {
        ModuleInfo.register(this);
        ModuleBody appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame = new frame();
        this.android$Mnlog$Mnform = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 1, Lit246, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 2, Lit247, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 3, Lit248, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 5, Lit249, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 6, Lit250, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 7, Lit251, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 8, Lit252, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 9, Lit253, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 10, Lit254, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 11, Lit255, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 12, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 13, Lit256, 16388);
        this.lookup$Mnhandler = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 14, Lit257, 8194);
        PropertySet moduleMethod = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 15, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime5196592475468383179.scm:553");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 16, "$define", 0);
        lambda$Fn2 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 17, null, 0);
        lambda$Fn3 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 18, null, 0);
        lambda$Fn4 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 19, null, 0);
        lambda$Fn5 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 20, null, 0);
        lambda$Fn6 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 21, null, 0);
        lambda$Fn7 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 22, null, 0);
        lambda$Fn8 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 23, null, 0);
        lambda$Fn9 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 24, null, 0);
        lambda$Fn10 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 25, null, 0);
        lambda$Fn11 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 26, null, 0);
        lambda$Fn12 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 27, null, 0);
        lambda$Fn13 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 28, null, 0);
        lambda$Fn14 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 29, null, 0);
        lambda$Fn15 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 30, null, 0);
        lambda$Fn16 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 31, null, 0);
        lambda$Fn17 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 32, null, 0);
        lambda$Fn19 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 33, null, 0);
        lambda$Fn18 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 34, null, 0);
        lambda$Fn20 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 35, null, 0);
        this.Screen1$Initialize = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 36, Lit55, 0);
        lambda$Fn21 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 37, null, 0);
        lambda$Fn22 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 38, null, 0);
        lambda$Fn23 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 39, null, 0);
        lambda$Fn24 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 40, null, 0);
        lambda$Fn25 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 41, null, 0);
        lambda$Fn26 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 42, null, 0);
        lambda$Fn27 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 43, null, 0);
        lambda$Fn28 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 44, null, 0);
        lambda$Fn29 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 45, null, 0);
        lambda$Fn30 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 46, null, 0);
        lambda$Fn31 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 47, null, 0);
        lambda$Fn32 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 48, null, 0);
        lambda$Fn33 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 49, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.pairsensor$AfterPicking = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 50, Lit112, 0);
        lambda$Fn34 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 51, null, 0);
        lambda$Fn35 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 52, null, 0);
        lambda$Fn36 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 53, null, 0);
        lambda$Fn37 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 54, null, 0);
        lambda$Fn38 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 55, null, 0);
        lambda$Fn39 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 56, null, 0);
        lambda$Fn40 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 57, null, 0);
        lambda$Fn41 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 58, null, 0);
        lambda$Fn42 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 59, null, 0);
        lambda$Fn43 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 60, null, 0);
        lambda$Fn44 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 61, null, 0);
        lambda$Fn45 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 62, null, 0);
        lambda$Fn46 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 63, null, 0);
        lambda$Fn47 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 64, null, 0);
        lambda$Fn48 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 65, null, 0);
        lambda$Fn49 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 66, null, 0);
        this.connect$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 67, Lit151, 0);
        lambda$Fn50 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 68, null, 0);
        lambda$Fn51 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 69, null, 0);
        this.Button1$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 70, Lit162, 0);
        lambda$Fn52 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 71, null, 0);
        lambda$Fn53 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 72, null, 0);
        lambda$Fn54 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 73, null, 0);
        lambda$Fn55 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 74, null, 0);
        lambda$Fn56 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 75, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.BluetoothLE1$DeviceFound = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 76, Lit187, 0);
        this.BluetoothLE1$Connected = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 77, Lit193, 0);
        this.BluetoothLE1$StringsReceived = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 78, Lit223, 12291);
        this.Clock1$Timer = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 79, Lit230, 0);
        this.Notifier1$AfterTextInput = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Screen1_frame, 80, Lit242, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    static Boolean lambda13() {
        return Boolean.FALSE;
    }

    static Boolean lambda14() {
        return Boolean.FALSE;
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        Throwable th;
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th2) {
            th = th2;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            String str;
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
            FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
            if (stringAppend == null) {
                str = null;
            } else {
                str = stringAppend.toString();
            }
            this.global$Mnvar$Mnenvironment = Environment.make(str);
            Screen1 = null;
            this.form$Mnname$Mnsymbol = Lit0;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            find = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find).run();
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, Lit4), $result);
                } else {
                    addToGlobalVars(Lit3, lambda$Fn2);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, Lit4), $result);
                } else {
                    addToGlobalVars(Lit5, lambda$Fn3);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit6, ""), $result);
                } else {
                    addToGlobalVars(Lit6, lambda$Fn4);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, ""), $result);
                } else {
                    addToGlobalVars(Lit7, lambda$Fn5);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit8, ""), $result);
                } else {
                    addToGlobalVars(Lit8, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit9, ""), $result);
                } else {
                    addToGlobalVars(Lit9, lambda$Fn7);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit10, runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("! \" # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~"), Lit11, "split at spaces")), $result);
                } else {
                    addToGlobalVars(Lit10, lambda$Fn8);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit13, runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126"), Lit14, "split at spaces")), $result);
                } else {
                    addToGlobalVars(Lit13, lambda$Fn9);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit16, ""), $result);
                } else {
                    addToGlobalVars(Lit16, lambda$Fn10);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit17, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), $result);
                } else {
                    addToGlobalVars(Lit17, lambda$Fn11);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit18, Boolean.FALSE), $result);
                } else {
                    addToGlobalVars(Lit18, lambda$Fn12);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Boolean.FALSE), $result);
                } else {
                    addToGlobalVars(Lit19, lambda$Fn13);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit20, "46a970e0-0d5f-11e2-8b5e-0002a5d5c51b"), $result);
                } else {
                    addToGlobalVars(Lit20, lambda$Fn14);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit21, "0aad7ea0-0d60-11e2-8e3c-0002a5d5c51b"), $result);
                } else {
                    addToGlobalVars(Lit21, lambda$Fn15);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit22, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), $result);
                } else {
                    addToGlobalVars(Lit22, lambda$Fn16);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit23, lambda$Fn17), $result);
                } else {
                    addToGlobalVars(Lit23, lambda$Fn18);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit28, "HealthKeeper application for Sleep Apnea analysis using Fog Network", Lit29);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit30, Lit31, Lit32);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit33, Boolean.TRUE, Lit34);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit35, Lit36, Lit32);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit37, "HealthKeeper", Lit29);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit38, Lit39, Lit32);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit40, "heartbeat.png", Lit29);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit41, "fade", Lit29);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit42, "portrait", Lit29);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit43, Boolean.TRUE, Lit34);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit44, Boolean.FALSE, Lit34);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit45, "Responsive", Lit29);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit46, "AppTheme.Light.DarkActionBar", Lit29);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit47, "HealthKeeper", Lit29), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn20));
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit55, this.Screen1$Initialize);
                } else {
                    addToFormEnvironment(Lit55, this.Screen1$Initialize);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
                } else {
                    addToEvents(Lit0, Lit56);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit57, Lit58, lambda$Fn21), $result);
                } else {
                    addToComponents(Lit0, Lit63, Lit58, lambda$Fn22);
                }
                this.Label1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit64, Lit65, lambda$Fn23), $result);
                } else {
                    addToComponents(Lit0, Lit74, Lit65, lambda$Fn24);
                }
                this.Label2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit75, Lit76, lambda$Fn25), $result);
                } else {
                    addToComponents(Lit0, Lit79, Lit76, lambda$Fn26);
                }
                this.Label3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit80, Lit81, lambda$Fn27), $result);
                } else {
                    addToComponents(Lit0, Lit83, Lit81, lambda$Fn28);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit84, Lit85, lambda$Fn29), $result);
                } else {
                    addToComponents(Lit0, Lit87, Lit85, lambda$Fn30);
                }
                this.pairsensor = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit88, Lit89, lambda$Fn31), $result);
                } else {
                    addToComponents(Lit0, Lit92, Lit89, lambda$Fn32);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit112, this.pairsensor$AfterPicking);
                } else {
                    addToFormEnvironment(Lit112, this.pairsensor$AfterPicking);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "pairsensor", "AfterPicking");
                } else {
                    addToEvents(Lit89, Lit113);
                }
                this.VerticalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit114, Lit115, lambda$Fn34), $result);
                } else {
                    addToComponents(Lit0, Lit117, Lit115, lambda$Fn35);
                }
                this.device = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit115, Lit118, Lit48, lambda$Fn36), $result);
                } else {
                    addToComponents(Lit115, Lit121, Lit48, lambda$Fn37);
                }
                this.data = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit115, Lit122, Lit50, lambda$Fn38), $result);
                } else {
                    addToComponents(Lit115, Lit124, Lit50, lambda$Fn39);
                }
                this.Label6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit125, Lit126, lambda$Fn40), $result);
                } else {
                    addToComponents(Lit0, Lit128, Lit126, lambda$Fn41);
                }
                this.ip = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit129, Lit130, lambda$Fn42), $result);
                } else {
                    addToComponents(Lit0, Lit133, Lit130, lambda$Fn43);
                }
                this.HorizontalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit134, Lit135, lambda$Fn44), $result);
                } else {
                    addToComponents(Lit0, Lit137, Lit135, lambda$Fn45);
                }
                this.HorizontalArrangement4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit138, Lit139, lambda$Fn46), $result);
                } else {
                    addToComponents(Lit0, Lit141, Lit139, lambda$Fn47);
                }
                this.connect = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit139, Lit142, Lit143, lambda$Fn48), $result);
                } else {
                    addToComponents(Lit139, Lit145, Lit143, lambda$Fn49);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit151, this.connect$Click);
                } else {
                    addToFormEnvironment(Lit151, this.connect$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "connect", "Click");
                } else {
                    addToEvents(Lit143, Lit152);
                }
                this.Button1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit153, Lit154, lambda$Fn50), $result);
                } else {
                    addToComponents(Lit0, Lit160, Lit154, lambda$Fn51);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit162, this.Button1$Click);
                } else {
                    addToFormEnvironment(Lit162, this.Button1$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
                } else {
                    addToEvents(Lit154, Lit152);
                }
                this.HorizontalArrangement5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit163, Lit164, lambda$Fn52), $result);
                } else {
                    addToComponents(Lit0, Lit165, Lit164, lambda$Fn53);
                }
                this.Label7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit164, Lit166, Lit167, lambda$Fn54), $result);
                } else {
                    addToComponents(Lit164, Lit170, Lit167, lambda$Fn55);
                }
                this.BluetoothLE1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit171, Lit51, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit172, Lit51, Boolean.FALSE);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit187, this.BluetoothLE1$DeviceFound);
                } else {
                    addToFormEnvironment(Lit187, this.BluetoothLE1$DeviceFound);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "DeviceFound");
                } else {
                    addToEvents(Lit51, Lit188);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit193, this.BluetoothLE1$Connected);
                } else {
                    addToFormEnvironment(Lit193, this.BluetoothLE1$Connected);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "Connected");
                } else {
                    addToEvents(Lit51, Lit194);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit223, this.BluetoothLE1$StringsReceived);
                } else {
                    addToFormEnvironment(Lit223, this.BluetoothLE1$StringsReceived);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "StringsReceived");
                } else {
                    addToEvents(Lit51, Lit224);
                }
                this.Clock1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit225, Lit53, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit226, Lit53, Boolean.FALSE);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit230, this.Clock1$Timer);
                } else {
                    addToFormEnvironment(Lit230, this.Clock1$Timer);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
                } else {
                    addToEvents(Lit53, Lit231);
                }
                this.Notifier1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit232, Lit106, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit233, Lit106, Boolean.FALSE);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit242, this.Notifier1$AfterTextInput);
                } else {
                    addToFormEnvironment(Lit242, this.Notifier1$AfterTextInput);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Notifier1", "AfterTextInput");
                } else {
                    addToEvents(Lit106, Lit243);
                }
                this.TinyDB1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit244, Lit93, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit245, Lit93, Boolean.FALSE);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static IntNum lambda3() {
        return Lit4;
    }

    static IntNum lambda4() {
        return Lit4;
    }

    static String lambda5() {
        return "";
    }

    static String lambda6() {
        return "";
    }

    static String lambda7() {
        return "";
    }

    static String lambda8() {
        return "";
    }

    static Object lambda9() {
        return runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("! \" # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~"), Lit12, "split at spaces");
    }

    static Object lambda10() {
        return runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126"), Lit15, "split at spaces");
    }

    static String lambda11() {
        return "";
    }

    static Object lambda12() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static String lambda15() {
        return "46a970e0-0d5f-11e2-8b5e-0002a5d5c51b";
    }

    static String lambda16() {
        return "0aad7ea0-0d60-11e2-8e3c-0002a5d5c51b";
    }

    static Object lambda17() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static Object lambda18() {
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit9, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(runtime.yail$Mnlist$Mnindex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit8, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit24, "index in list")), Lit25, "select list item"));
    }

    static Procedure lambda19() {
        return lambda$Fn19;
    }

    static Object lambda20() {
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit9, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(runtime.yail$Mnlist$Mnindex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit8, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit26, "index in list")), Lit27, "select list item"));
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit28, "HealthKeeper application for Sleep Apnea analysis using Fog Network", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit30, Lit31, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit33, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit35, Lit36, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit37, "HealthKeeper", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit38, Lit39, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit40, "heartbeat.png", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit41, "fade", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit42, "portrait", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit43, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit44, Boolean.FALSE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit45, "Responsive", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit46, "AppTheme.Light.DarkActionBar", Lit29);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit47, "HealthKeeper", Lit29);
    }

    public Object Screen1$Initialize() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit48, Lit49, Boolean.FALSE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit49, Boolean.FALSE, Lit34);
        runtime.callComponentMethod(Lit51, Lit52, LList.Empty, LList.Empty);
        return runtime.setAndCoerceProperty$Ex(Lit53, Lit54, Boolean.FALSE, Lit34);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit58, Lit59, Lit60, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit58, Lit61, Lit62, Lit32);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit58, Lit59, Lit60, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit58, Lit61, Lit62, Lit32);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit65, Lit66, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit67, Lit68, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit69, "Welcome to", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit65, Lit72, Lit73, Lit32);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit65, Lit66, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit67, Lit68, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit69, "Welcome to", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit65, Lit72, Lit73, Lit32);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit76, Lit66, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit67, Lit77, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit69, "HealthKeeper", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit76, Lit72, Lit78, Lit32);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit76, Lit66, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit67, Lit77, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit69, "HealthKeeper", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit76, Lit72, Lit78, Lit32);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit81, Lit69, "Your health manager using Fog network", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit72, Lit82, Lit32);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit81, Lit69, "Your health manager using Fog network", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit72, Lit82, Lit32);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit59, Lit86, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit61, Lit62, Lit32);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit59, Lit86, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit61, Lit62, Lit32);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit89, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit61, Lit91, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit69, "Pair Sensor", Lit29);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit89, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit61, Lit91, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit69, "Pair Sensor", Lit29);
    }

    public Object pairsensor$AfterPicking() {
        runtime.setThisForm();
        runtime.yailForEach(lambda$Fn33, runtime.callComponentMethod(Lit93, Lit99, LList.Empty, LList.Empty));
        if (runtime.callYailPrimitive(runtime.yail$Mnnot, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit100, "not") != Boolean.FALSE) {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit7, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1(runtime.getProperty$1(Lit89, Lit97)), Lit101, "split at spaces"), Lit71), Lit102, "select list item"));
            runtime.addGlobalVarToCurrentFormEnvironment(Lit6, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1(runtime.getProperty$1(Lit89, Lit97)), Lit103, "split at spaces"), Lit104), Lit105, "select list item"));
            runtime.callComponentMethod(Lit106, Lit107, LList.list3("Enter name to save :", "Save Device?", Boolean.TRUE), Lit108);
        }
        runtime.callComponentMethod(Lit51, Lit109, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit110);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit49, Boolean.TRUE, Lit34);
        return runtime.setAndCoerceProperty$Ex(Lit48, Lit69, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("Connecting to : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit111, "join"), Lit29);
    }

    static Object lambda34(Object $item) {
        Object signalRuntimeError;
        ModuleMethod moduleMethod = strings.string$Eq$Qu;
        SimpleSymbol simpleSymbol = Lit93;
        SimpleSymbol simpleSymbol2 = Lit94;
        if ($item instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit95), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $item;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list2(signalRuntimeError, "NotFound"), Lit96), runtime.getProperty$1(Lit89, Lit97)), Lit98, "text=") == Boolean.FALSE) {
            return Values.empty;
        }
        Symbol symbol = Lit7;
        if ($item instanceof Package) {
            $item = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit95), " is not bound in the current context"), "Unbound Variable");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol, $item);
        runtime.addGlobalVarToCurrentFormEnvironment(Lit6, runtime.getProperty$1(Lit89, Lit97));
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit18, Boolean.TRUE);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit115, Lit35, Lit36, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit115, Lit59, Lit116, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit115, Lit61, Lit62, Lit32);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit115, Lit35, Lit36, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit115, Lit59, Lit116, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit115, Lit61, Lit62, Lit32);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit48, Lit67, Lit119, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit69, "Connected to :", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit48, Lit72, Lit120, Lit32);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit48, Lit67, Lit119, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit69, "Connected to :", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit48, Lit72, Lit120, Lit32);
    }

    static Object lambda39() {
        runtime.setAndCoerceProperty$Ex(Lit50, Lit69, "data", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit72, Lit123, Lit32);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit50, Lit69, "data", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit72, Lit123, Lit32);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit126, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit69, "Enter IP address of Master :", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit72, Lit127, Lit32);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit126, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit69, "Enter IP address of Master :", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit72, Lit127, Lit32);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit130, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit131, "Master IP", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit130, Lit72, Lit132, Lit32);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit130, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit131, "Master IP", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit130, Lit72, Lit132, Lit32);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit59, Lit136, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit61, Lit62, Lit32);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit59, Lit136, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit61, Lit62, Lit32);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit139, Lit35, Lit36, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit59, Lit140, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit139, Lit61, Lit62, Lit32);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit139, Lit35, Lit36, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit59, Lit140, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit139, Lit61, Lit62, Lit32);
    }

    static Object lambda49() {
        runtime.setAndCoerceProperty$Ex(Lit143, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit143, Lit61, Lit144, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit143, Lit69, "Connect", Lit29);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit143, Lit67, Lit90, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit143, Lit61, Lit144, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit143, Lit69, "Connect", Lit29);
    }

    public Object connect$Click() {
        runtime.setThisForm();
        runtime.addGlobalVarToCurrentFormEnvironment(Lit16, runtime.getProperty$1(Lit130, Lit69));
        runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit146, "add items to list");
        runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit147, "add items to list");
        runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit148, "add items to list");
        runtime.callComponentMethod(Lit51, Lit149, LList.Empty, LList.Empty);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen$Mnwith$Mnstart$Mnvalue, LList.list2("Login", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit150, "open another screen with start value");
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit154, Lit38, Lit155, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit67, Lit156, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit59, Lit157, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit61, Lit158, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit69, "Remove all saved devices", Lit29);
        return runtime.setAndCoerceProperty$Ex(Lit154, Lit72, Lit159, Lit32);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit154, Lit38, Lit155, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit67, Lit156, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit59, Lit157, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit61, Lit158, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit69, "Remove all saved devices", Lit29);
        return runtime.setAndCoerceProperty$Ex(Lit154, Lit72, Lit159, Lit32);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit93, Lit161, LList.Empty, LList.Empty);
    }

    static Object lambda53() {
        runtime.setAndCoerceProperty$Ex(Lit164, Lit35, Lit36, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit164, Lit61, Lit62, Lit32);
    }

    static Object lambda54() {
        runtime.setAndCoerceProperty$Ex(Lit164, Lit35, Lit36, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit164, Lit61, Lit62, Lit32);
    }

    static Object lambda55() {
        runtime.setAndCoerceProperty$Ex(Lit167, Lit61, Lit168, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit167, Lit69, "Developed by CLOUDS Lab", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit167, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit167, Lit72, Lit169, Lit32);
    }

    static Object lambda56() {
        runtime.setAndCoerceProperty$Ex(Lit167, Lit61, Lit168, Lit32);
        runtime.setAndCoerceProperty$Ex(Lit167, Lit69, "Developed by CLOUDS Lab", Lit29);
        runtime.setAndCoerceProperty$Ex(Lit167, Lit70, Lit71, Lit32);
        return runtime.setAndCoerceProperty$Ex(Lit167, Lit72, Lit169, Lit32);
    }

    public Object BluetoothLE1$DeviceFound() {
        runtime.setThisForm();
        runtime.addGlobalVarToCurrentFormEnvironment(Lit22, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnfrom$Mncsv$Mnrow, LList.list1(runtime.getProperty$1(Lit51, Lit173)), Lit174, "list from csv row"));
        runtime.yailForEach(lambda$Fn56, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit22, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit185, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit22, runtime.$Stthe$Mnnull$Mnvalue$St), Lit186);
    }

    static Object lambda57(Object $item) {
        Object signalRuntimeError;
        ModuleMethod moduleMethod = runtime.yail$Mnnot;
        ModuleMethod moduleMethod2 = strings.string$Eq$Qu;
        SimpleSymbol simpleSymbol = Lit93;
        SimpleSymbol simpleSymbol2 = Lit94;
        ModuleMethod moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
        ModuleMethod moduleMethod4 = runtime.string$Mnsplit$Mnat$Mnspaces;
        if ($item instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit95), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $item;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list1(runtime.callYailPrimitive(moduleMethod2, LList.list2(runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list2(runtime.callYailPrimitive(moduleMethod3, LList.list2(runtime.callYailPrimitive(moduleMethod4, LList.list1(signalRuntimeError), Lit175, "split at spaces"), Lit71), Lit176, "select list item"), "NotFound"), Lit177), "NotFound"), Lit178, "text=")), Lit179, "not") == Boolean.FALSE) {
            return Values.empty;
        }
        moduleMethod = runtime.yail$Mnlist$Mnset$Mnitem$Ex;
        Object lookupGlobalVarInCurrentFormEnvironment = runtime.lookupGlobalVarInCurrentFormEnvironment(Lit22, runtime.$Stthe$Mnnull$Mnvalue$St);
        ModuleMethod moduleMethod5 = runtime.yail$Mnlist$Mnindex;
        if ($item instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit95), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $item;
        }
        signalRuntimeError = runtime.callYailPrimitive(moduleMethod5, LList.list2(signalRuntimeError, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit22, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit180, "index in list");
        simpleSymbol = Lit93;
        simpleSymbol2 = Lit94;
        moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
        moduleMethod4 = runtime.string$Mnsplit$Mnat$Mnspaces;
        if ($item instanceof Package) {
            $item = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit95), " is not bound in the current context"), "Unbound Variable");
        }
        return runtime.callYailPrimitive(moduleMethod, LList.list3(lookupGlobalVarInCurrentFormEnvironment, signalRuntimeError, runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list2(runtime.callYailPrimitive(moduleMethod3, LList.list2(runtime.callYailPrimitive(moduleMethod4, LList.list1($item), Lit181, "split at spaces"), Lit71), Lit182, "select list item"), ""), Lit183)), Lit184, "replace list item");
    }

    public Object BluetoothLE1$Connected() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit48, Lit69, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("Connected to : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit189, "join"), Lit29);
        if (runtime.callYailPrimitive(strings.string$Eq$Qu, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St), ":"), Lit190, "split"), Lit71), Lit191, "select list item"), "A4"), Lit192, "text=") != Boolean.FALSE) {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit20, "cdeacb80-5235-4c07-8846-93a37ee6b86d");
            runtime.addGlobalVarToCurrentFormEnvironment(Lit21, "cdeacb81-5235-4c07-8846-93a37ee6b86d");
            runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Boolean.TRUE);
        }
        runtime.setAndCoerceProperty$Ex(Lit53, Lit54, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit49, Boolean.TRUE, Lit34);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit69, "SpO2 :  %, BPM :  ", Lit29);
    }

    public Object BluetoothLE1$StringsReceived(Object $serviceUuid, Object $characteristicUuid, Object $stringValues) {
        Object signalRuntimeError;
        runtime.sanitizeComponentData($serviceUuid);
        runtime.sanitizeComponentData($characteristicUuid);
        $stringValues = runtime.sanitizeComponentData($stringValues);
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnnot;
        ModuleMethod moduleMethod2 = runtime.yail$Mnlist$Mnempty$Qu;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list1(runtime.callYailPrimitive(moduleMethod2, LList.list1(signalRuntimeError), Lit196, "is list empty?")), Lit197, "not") == Boolean.FALSE) {
            return Values.empty;
        }
        ModuleMethod moduleMethod3;
        if (runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St) != Boolean.FALSE) {
            moduleMethod = runtime.yail$Mnequal$Qu;
            moduleMethod2 = strings.string$Mnlength;
            moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
            if ($stringValues instanceof Package) {
                signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
            } else {
                signalRuntimeError = $stringValues;
            }
            if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callYailPrimitive(moduleMethod2, LList.list1(runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, Lit71), Lit198, "select list item")), Lit199, "length"), Lit200), Lit201, "=") != Boolean.FALSE) {
                Symbol symbol = Lit8;
                moduleMethod2 = runtime.string$Mnsubstring;
                moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
                if ($stringValues instanceof Package) {
                    signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
                } else {
                    signalRuntimeError = $stringValues;
                }
                runtime.addGlobalVarToCurrentFormEnvironment(symbol, runtime.callYailPrimitive(moduleMethod2, LList.list3(runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, Lit71), Lit202, "select list item"), Lit36, Lit71), Lit203, "segment"));
                Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit23, runtime.$Stthe$Mnnull$Mnvalue$St));
                runtime.addGlobalVarToCurrentFormEnvironment(Lit5, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St));
                symbol = Lit8;
                moduleMethod2 = runtime.string$Mnsubstring;
                moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
                if ($stringValues instanceof Package) {
                    signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
                } else {
                    signalRuntimeError = $stringValues;
                }
                runtime.addGlobalVarToCurrentFormEnvironment(symbol, runtime.callYailPrimitive(moduleMethod2, LList.list3(runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, Lit71), Lit204, "select list item"), Lit104, Lit71), Lit205, "segment"));
                Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit23, runtime.$Stthe$Mnnull$Mnvalue$St));
                runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St));
                runtime.setAndCoerceProperty$Ex(Lit50, Lit69, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("SpO2 : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit206, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(" % , BPM : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit207, "join")), Lit208, "join"), Lit29);
            }
        }
        if (runtime.callYailPrimitive(runtime.yail$Mnnot, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit209, "not") == Boolean.FALSE) {
            return Values.empty;
        }
        Object signalRuntimeError2;
        Symbol symbol2 = Lit8;
        moduleMethod3 = runtime.string$Mnsubstring;
        ModuleMethod moduleMethod4 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        AddOp addOp = AddOp.$Mn;
        ModuleMethod moduleMethod5 = runtime.yail$Mnlist$Mnlength;
        if ($stringValues instanceof Package) {
            signalRuntimeError2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError2 = $stringValues;
        }
        Object callYailPrimitive = runtime.callYailPrimitive(moduleMethod4, LList.list2(signalRuntimeError, runtime.callYailPrimitive(addOp, LList.list2(runtime.callYailPrimitive(moduleMethod5, LList.list1(signalRuntimeError2), Lit210, "length of list"), Lit71), Lit211, "-")), Lit212, "select list item");
        ModuleMethod moduleMethod6 = strings.string$Mnlength;
        moduleMethod5 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        AddOp addOp2 = AddOp.$Mn;
        ModuleMethod moduleMethod7 = runtime.yail$Mnlist$Mnlength;
        if ($stringValues instanceof Package) {
            signalRuntimeError2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError2 = $stringValues;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol2, runtime.callYailPrimitive(moduleMethod3, LList.list3(callYailPrimitive, runtime.callYailPrimitive(moduleMethod6, LList.list1(runtime.callYailPrimitive(moduleMethod5, LList.list2(signalRuntimeError, runtime.callYailPrimitive(addOp2, LList.list2(runtime.callYailPrimitive(moduleMethod7, LList.list1(signalRuntimeError2), Lit213, "length of list"), Lit71), Lit214, "-")), Lit215, "select list item")), Lit216, "length"), Lit71), Lit217, "segment"));
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit23, runtime.$Stthe$Mnnull$Mnvalue$St));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit5, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St));
        symbol = Lit8;
        moduleMethod2 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        moduleMethod3 = runtime.yail$Mnlist$Mnlength;
        if ($stringValues instanceof Package) {
            $stringValues = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit195), " is not bound in the current context"), "Unbound Variable");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol, runtime.callYailPrimitive(moduleMethod2, LList.list2(signalRuntimeError, runtime.callYailPrimitive(moduleMethod3, LList.list1($stringValues), Lit218, "length of list")), Lit219, "select list item"));
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit23, runtime.$Stthe$Mnnull$Mnvalue$St));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit69, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("SpO2 : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit220, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(" % , BPM : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit221, "join")), Lit222, "join"), Lit29);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        return runtime.getProperty$1(Lit51, Lit227) != Boolean.FALSE ? runtime.callComponentMethod(Lit51, Lit228, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit20, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit21, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.FALSE), Lit229) : Values.empty;
    }

    public Object Notifier1$AfterTextInput(Object $response) {
        Object signalRuntimeError;
        $response = runtime.sanitizeComponentData($response);
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnnot;
        ModuleMethod moduleMethod2 = strings.string$Eq$Qu;
        if ($response instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit234), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $response;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list1(runtime.callYailPrimitive(moduleMethod2, LList.list2(signalRuntimeError, "Cancel"), Lit235, "text=")), Lit236, "not") == Boolean.FALSE) {
            return Values.empty;
        }
        SimpleSymbol simpleSymbol = Lit93;
        SimpleSymbol simpleSymbol2 = Lit237;
        Object callYailPrimitive = runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1(runtime.getProperty$1(Lit89, Lit97)), Lit238, "split at spaces"), Lit71), Lit239, "select list item");
        if ($response instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit234), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $response;
        }
        runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list2(callYailPrimitive, signalRuntimeError), Lit240);
        Symbol symbol = Lit6;
        if ($response instanceof Package) {
            $response = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit234), " is not bound in the current context"), "Unbound Variable");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol, $response);
        return runtime.setAndCoerceProperty$Ex(Lit48, Lit69, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("Connected to : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit241, "join"), Lit29);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        boolean x = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & 1;
        if (x) {
            if (!this.form$Mnenvironment.isBound(name)) {
                return default$Mnvalue;
            }
        } else if (!x) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = lists.cons(lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public void processException(Object ex) {
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            return false;
        } else if (lookupInFormEnvironment(registeredObject) != componentObject) {
            return false;
        } else {
            try {
                Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                return true;
            } catch (Throwable exception) {
                androidLogForm(exception.getMessage());
                exception.printStackTrace();
                processException(exception);
                return false;
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        String str = null;
        String obj = componentName == null ? null : componentName.toString();
        if (eventName != null) {
            str = eventName.toString();
        }
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj, str)));
    }

    public void $define() {
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            androidLogForm(exception.getMessage());
            processException(exception);
        }
        Screen1 = this;
        addToFormEnvironment(Lit0, this);
        Object obj = this.events$Mnto$Mnregister;
        while (obj != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj;
                Object event$Mninfo = arg0.getCar();
                Object apply1 = lists.car.apply1(event$Mninfo);
                String obj2 = apply1 == null ? null : apply1.toString();
                Object apply12 = lists.cdr.apply1(event$Mninfo);
                EventDispatcher.registerEventForDelegation(this, obj2, apply12 == null ? null : apply12.toString());
                obj = arg0.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, obj);
            }
        }
        addToGlobalVars(Lit2, lambda$Fn1);
        Screen1 closureEnv = this;
        obj = lists.reverse(this.global$Mnvars$Mnto$Mncreate);
        while (obj != LList.Empty) {
            try {
                arg0 = (Pair) obj;
                Object var$Mnval = arg0.getCar();
                Object var = lists.car.apply1(var$Mnval);
                addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(lists.cadr.apply1(var$Mnval)));
                obj = arg0.getCdr();
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, obj);
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "arg0", -2, obj);
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "add-to-form-environment", 0, component$Mnname);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "lookup-in-form-environment", 0, apply1);
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "arg0", -2, obj);
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "arg0", -2, obj);
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "add-to-global-var-environment", 0, var);
            } catch (ClassCastException e2222222) {
                throw new WrongType(e2222222, "arg0", -2, obj);
            } catch (YailRuntimeError exception2) {
                processException(exception2);
                return;
            }
        }
        obj = lists.reverse(this.form$Mndo$Mnafter$Mncreation);
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            misc.force(arg0.getCar());
            obj = arg0.getCdr();
        }
        LList component$Mndescriptors = lists.reverse(this.components$Mnto$Mncreate);
        closureEnv = this;
        obj = component$Mndescriptors;
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            Object component$Mninfo = arg0.getCar();
            Object component$Mnname = lists.caddr.apply1(component$Mninfo);
            lists.cadddr.apply1(component$Mninfo);
            Object component$Mnobject = Invoke.make.apply2(lists.cadr.apply1(component$Mninfo), lookupInFormEnvironment((Symbol) lists.car.apply1(component$Mninfo)));
            SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
            addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
            obj = arg0.getCdr();
        }
        obj = component$Mndescriptors;
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            component$Mninfo = arg0.getCar();
            lists.caddr.apply1(component$Mninfo);
            Boolean init$Mnthunk = lists.cadddr.apply1(component$Mninfo);
            if (init$Mnthunk != Boolean.FALSE) {
                Scheme.applyToArgs.apply1(init$Mnthunk);
            }
            obj = arg0.getCdr();
        }
        obj = component$Mndescriptors;
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            component$Mninfo = arg0.getCar();
            component$Mnname = lists.caddr.apply1(component$Mninfo);
            lists.cadddr.apply1(component$Mninfo);
            callInitialize(SlotGet.field.apply2(this, component$Mnname));
            obj = arg0.getCdr();
        }
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        Object car;
        LList symbols = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Pair result = LList.Empty;
        Object arg0 = symbols;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                car = arg02.getCar();
                try {
                    result = Pair.make(misc.symbol$To$String((Symbol) car), result);
                    arg0 = arg03;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        car = procedure.apply2(moduleMethod, LList.reverseInPlace(result));
        try {
            return misc.string$To$Symbol((CharSequence) car);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, car);
        }
    }

    static Object lambda2() {
        return null;
    }
}
