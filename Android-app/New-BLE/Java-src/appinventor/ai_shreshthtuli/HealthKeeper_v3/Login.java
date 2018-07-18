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
import com.google.appinventor.components.runtime.WebViewer;
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
import gnu.kawa.functions.DivideOp;
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

/* compiled from: Login.yail */
public class Login extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Login").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("g$numbers").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("RegisterForStrings").readResolve());
    static final PairWithPosition Lit101 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, PairWithPosition.make(Lit45, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377992), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377987), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377981);
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("start$Click").readResolve());
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("stop").readResolve());
    static final IntNum Lit105 = IntNum.make(-1050);
    static final FString Lit106 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("stop$Click").readResolve());
    static final FString Lit108 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("data").readResolve());
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 53641);
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit112;
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit114 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve());
    static final FString Lit116 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("device").readResolve());
    static final IntNum Lit119 = IntNum.make(-1050);
    static final PairWithPosition Lit12 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 53641);
    static final IntNum Lit120;
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve());
    static final IntNum Lit124 = IntNum.make(-1050);
    static final FString Lit125 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit126 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit127;
    static final FString Lit128 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit129 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("g$value1").readResolve());
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("Label4").readResolve());
    static final IntNum Lit131;
    static final FString Lit132 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit133 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit134;
    static final FString Lit135 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit136 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit137 = ((SimpleSymbol) new SimpleSymbol("Label6").readResolve());
    static final IntNum Lit138;
    static final FString Lit139 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit14 = IntNum.make(0);
    static final FString Lit140 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit141;
    static final FString Lit142 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit143 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("analyze").readResolve());
    static final FString Lit145 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit146 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 823637), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 823631);
    static final PairWithPosition Lit147 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 823674), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 823668);
    static final PairWithPosition Lit148 = PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824044);
    static final PairWithPosition Lit149 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824091), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824086), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824080);
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("g$value2").readResolve());
    static final PairWithPosition Lit150 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824121), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824115);
    static final PairWithPosition Lit151 = PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824344);
    static final PairWithPosition Lit152 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824391), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824386), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824380);
    static final PairWithPosition Lit153 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824421), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824415);
    static final PairWithPosition Lit154 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824445), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824439);
    static final PairWithPosition Lit155 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824488), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824482);
    static final PairWithPosition Lit156 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824512), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824506);
    static final PairWithPosition Lit157 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 824529);
    static final SimpleSymbol Lit158 = ((SimpleSymbol) new SimpleSymbol("analyze$Click").readResolve());
    static final FString Lit159 = new FString("edu.mit.appinventor.ble.BluetoothLE");
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("g$data1").readResolve());
    static final FString Lit160 = new FString("edu.mit.appinventor.ble.BluetoothLE");
    static final SimpleSymbol Lit161 = ((SimpleSymbol) new SimpleSymbol("ConnectWithAddress").readResolve());
    static final PairWithPosition Lit162 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 852073);
    static final SimpleSymbol Lit163 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$DeviceFound").readResolve());
    static final SimpleSymbol Lit164 = ((SimpleSymbol) new SimpleSymbol("DeviceFound").readResolve());
    static final SimpleSymbol Lit165 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$Connected").readResolve());
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("Connected").readResolve());
    static final SimpleSymbol Lit167 = ((SimpleSymbol) new SimpleSymbol("$stringValues").readResolve());
    static final PairWithPosition Lit168 = PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868502);
    static final PairWithPosition Lit169 = PairWithPosition.make(Lit45, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868529);
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("g$data2").readResolve());
    static final PairWithPosition Lit170 = PairWithPosition.make(Lit45, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868630);
    static final PairWithPosition Lit171 = PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868925);
    static final PairWithPosition Lit172 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868962), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868954);
    static final PairWithPosition Lit173 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868983), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 868977);
    static final PairWithPosition Lit174 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869189);
    static final PairWithPosition Lit175 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869223), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869216), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869210);
    static final PairWithPosition Lit176 = PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869510);
    static final PairWithPosition Lit177 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869543), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869537);
    static final PairWithPosition Lit178 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869823), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869817);
    static final PairWithPosition Lit179 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869934), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869928);
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("g$jumper").readResolve());
    static final PairWithPosition Lit180 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869958), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 869952);
    static final PairWithPosition Lit181 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870225), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870219);
    static final PairWithPosition Lit182 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870255);
    static final IntNum Lit183 = IntNum.make(4);
    static final PairWithPosition Lit184 = PairWithPosition.make(Lit224, PairWithPosition.make(Lit224, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870281), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870276);
    static final PairWithPosition Lit185 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870503), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870497);
    static final PairWithPosition Lit186 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870550), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870543), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870537);
    static final PairWithPosition Lit187 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870814), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870808);
    static final PairWithPosition Lit188 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870861), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870854), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 870848);
    static final PairWithPosition Lit189 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 871132), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 871126);
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("g$url").readResolve());
    static final PairWithPosition Lit190 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 871243), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 871237);
    static final PairWithPosition Lit191 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 871267), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 871261);
    static final SimpleSymbol Lit192 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$StringsReceived").readResolve());
    static final SimpleSymbol Lit193 = ((SimpleSymbol) new SimpleSymbol("StringsReceived").readResolve());
    static final FString Lit194 = new FString("com.google.appinventor.components.runtime.Clock");
    static final FString Lit195 = new FString("com.google.appinventor.components.runtime.Clock");
    static final PairWithPosition Lit196 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit224, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897173), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897167);
    static final PairWithPosition Lit197 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit224, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897309), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897303);
    static final PairWithPosition Lit198;
    static final PairWithPosition Lit199 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897665), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897657);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final IntNum Lit20 = IntNum.make(1);
    static final IntNum Lit200 = IntNum.make(60);
    static final PairWithPosition Lit201 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897692), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897684);
    static final PairWithPosition Lit202 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897806), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897800);
    static final PairWithPosition Lit203 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897997), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897989);
    static final PairWithPosition Lit204 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898024), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898016);
    static final PairWithPosition Lit205;
    static final PairWithPosition Lit206 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898328), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898320);
    static final IntNum Lit207 = IntNum.make(24);
    static final PairWithPosition Lit208;
    static final SimpleSymbol Lit209 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final PairWithPosition Lit21 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78108), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78102);
    static final SimpleSymbol Lit210 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final SimpleSymbol Lit211 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit212 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit213 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit214 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit215 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit216 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit217 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit218 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit219 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final PairWithPosition Lit22 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78145), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78139);
    static final SimpleSymbol Lit220 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit221 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit222 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit223 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit224 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final PairWithPosition Lit23 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78206), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78200);
    static final PairWithPosition Lit24 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78108), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78102);
    static final PairWithPosition Lit25 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78145), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78139);
    static final PairWithPosition Lit26 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78206), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 78200);
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("g$deviceID").readResolve());
    static final IntNum Lit28 = IntNum.make(2);
    static final PairWithPosition Lit29 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 82089), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 82083);
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$utfinput").readResolve());
    static final PairWithPosition Lit30 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 82089), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 82083);
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("g$deviceName").readResolve());
    static final IntNum Lit32 = IntNum.make(3);
    static final PairWithPosition Lit33 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 86187), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 86181);
    static final PairWithPosition Lit34 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 86187), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 86181);
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("g$firstval").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("p$convert").readResolve());
    static final PairWithPosition Lit37 = PairWithPosition.make(Lit224, PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94434), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94429);
    static final PairWithPosition Lit38 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94465), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94459);
    static final PairWithPosition Lit39 = PairWithPosition.make(Lit224, PairWithPosition.make(Lit223, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94434), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94429);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$numberoutput").readResolve());
    static final PairWithPosition Lit40 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94465), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 94459);
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final IntNum Lit42;
    static final SimpleSymbol Lit43;
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit45;
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit48;
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("g$service_UUID").readResolve());
    static final IntNum Lit50;
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("ShowStatusBar").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("Theme").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("WebViewer1").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("GoToUrl").readResolve());
    static final PairWithPosition Lit57 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 155735);
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("StartScanning").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("g$characteristic_UUID").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("TimerEnabled").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("Login$Initialize").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit64 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final FString Lit66 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit69 = IntNum.make(-2);
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$utf").readResolve());
    static final FString Lit70 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit71 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("back").readResolve());
    static final IntNum Lit73 = IntNum.make(-1050);
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit76 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 258126);
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("back$Click").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit8 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 49422);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("logout").readResolve());
    static final IntNum Lit81 = IntNum.make(-1050);
    static final FString Lit82 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit83 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 303191);
    static final SimpleSymbol Lit84 = ((SimpleSymbol) new SimpleSymbol("logout$Click").readResolve());
    static final FString Lit85 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("start").readResolve());
    static final PairWithPosition Lit9 = PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 49422);
    static final IntNum Lit90 = IntNum.make(-1050);
    static final FString Lit91 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("CurrentUrl").readResolve());
    static final PairWithPosition Lit93 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 376955), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 376949);
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("hr").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("min").readResolve());
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("sec").readResolve());
    static final PairWithPosition Lit97 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377531), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377525);
    static final PairWithPosition Lit98 = PairWithPosition.make(Lit223, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377556), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377550);
    static final PairWithPosition Lit99 = PairWithPosition.make(Lit48, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377597), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 377591);
    public static Login Login;
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
    public Clock Clock1;
    public final ModuleMethod Clock1$Timer;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public HorizontalArrangement HorizontalArrangement4;
    public Label Label4;
    public Label Label6;
    public final ModuleMethod Login$Initialize;
    public WebViewer WebViewer1;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public Button analyze;
    public final ModuleMethod analyze$Click;
    public final ModuleMethod android$Mnlog$Mnform;
    public Button back;
    public final ModuleMethod back$Click;
    public LList components$Mnto$Mncreate;
    public Label data;
    public Label device;
    public final ModuleMethod dispatchEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public Label hr;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public Button logout;
    public final ModuleMethod logout$Click;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public Label min;
    public final ModuleMethod process$Mnexception;
    public Label sec;
    public final ModuleMethod send$Mnerror;
    public Button start;
    public final ModuleMethod start$Click;
    public Button stop;
    public final ModuleMethod stop$Click;

    /* compiled from: Login.yail */
    public class frame extends ModuleBody {
        Login $main = this;

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 76 ? this.$main.BluetoothLE1$StringsReceived(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 76) {
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
                    return Login.lambda2();
                case 16:
                    this.$main.$define();
                    return Values.empty;
                case 17:
                    return Login.lambda3();
                case 18:
                    return Login.lambda4();
                case 19:
                    return Login.lambda5();
                case 20:
                    return Login.lambda6();
                case 21:
                    return Login.lambda7();
                case 22:
                    return Login.lambda8();
                case 23:
                    return Login.lambda9();
                case 24:
                    return Login.lambda10();
                case 25:
                    return Login.lambda11();
                case 26:
                    return Login.lambda12();
                case 27:
                    return Login.lambda13();
                case 28:
                    return Login.lambda14();
                case 29:
                    return Login.lambda15();
                case 30:
                    return Login.lambda16();
                case 31:
                    return Login.lambda17();
                case 32:
                    return Login.lambda18();
                case 33:
                    return Login.lambda20();
                case 34:
                    return Login.lambda19();
                case 35:
                    return Login.lambda21();
                case 36:
                    return this.$main.Login$Initialize();
                case 37:
                    return Login.lambda22();
                case 38:
                    return Login.lambda23();
                case 39:
                    return Login.lambda24();
                case 40:
                    return Login.lambda25();
                case 41:
                    return this.$main.back$Click();
                case 42:
                    return Login.lambda26();
                case 43:
                    return Login.lambda27();
                case 44:
                    return this.$main.logout$Click();
                case 45:
                    return Login.lambda28();
                case 46:
                    return Login.lambda29();
                case 47:
                    return Login.lambda30();
                case 48:
                    return Login.lambda31();
                case 49:
                    return this.$main.start$Click();
                case 50:
                    return Login.lambda32();
                case 51:
                    return Login.lambda33();
                case 52:
                    return this.$main.stop$Click();
                case 53:
                    return Login.lambda34();
                case 54:
                    return Login.lambda35();
                case 55:
                    return Login.lambda36();
                case 56:
                    return Login.lambda37();
                case 57:
                    return Login.lambda38();
                case 58:
                    return Login.lambda39();
                case 59:
                    return Login.lambda40();
                case 60:
                    return Login.lambda41();
                case 61:
                    return Login.lambda42();
                case 62:
                    return Login.lambda43();
                case 63:
                    return Login.lambda44();
                case 64:
                    return Login.lambda45();
                case 65:
                    return Login.lambda46();
                case 66:
                    return Login.lambda47();
                case 67:
                    return Login.lambda48();
                case 68:
                    return Login.lambda49();
                case 69:
                    return Login.lambda50();
                case 70:
                    return Login.lambda51();
                case 71:
                    return Login.lambda52();
                case 72:
                    return Login.lambda53();
                case 73:
                    return this.$main.analyze$Click();
                case 74:
                    return this.$main.BluetoothLE1$DeviceFound();
                case 75:
                    return this.$main.BluetoothLE1$Connected();
                case 77:
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
                case 49:
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
                case 75:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 77:
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
                    if (!(obj instanceof Login)) {
                        return -786431;
                    }
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
                    if (!(obj instanceof Login)) {
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
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit43 = simpleSymbol;
        Lit208 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit43, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898355), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898347);
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit48 = simpleSymbol;
        Lit205 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit48, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898138), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 898132);
        SimpleSymbol simpleSymbol2 = Lit48;
        SimpleSymbol simpleSymbol3 = Lit48;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit45 = simpleSymbol;
        Lit198 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897487), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897482), "/tmp/1531906953024_0.3374809615347075-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 897476);
        int[] iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit141 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit138 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit134 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit131 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit127 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit120 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit112 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_DKGRAY;
        Lit50 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_CYAN;
        Lit42 = IntNum.make(iArr);
    }

    public Login() {
        ModuleInfo.register(this);
        ModuleBody appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame = new frame();
        this.android$Mnlog$Mnform = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 1, Lit211, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 2, Lit212, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 3, Lit213, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 5, Lit214, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 6, Lit215, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 7, Lit216, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 8, Lit217, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 9, Lit218, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 10, Lit219, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 11, Lit220, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 12, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 13, Lit221, 16388);
        this.lookup$Mnhandler = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 14, Lit222, 8194);
        PropertySet moduleMethod = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 15, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime8548536386995405510.scm:553");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 16, "$define", 0);
        lambda$Fn2 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 17, null, 0);
        lambda$Fn3 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 18, null, 0);
        lambda$Fn4 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 19, null, 0);
        lambda$Fn5 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 20, null, 0);
        lambda$Fn6 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 21, null, 0);
        lambda$Fn7 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 22, null, 0);
        lambda$Fn8 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 23, null, 0);
        lambda$Fn9 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 24, null, 0);
        lambda$Fn10 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 25, null, 0);
        lambda$Fn11 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 26, null, 0);
        lambda$Fn12 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 27, null, 0);
        lambda$Fn13 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 28, null, 0);
        lambda$Fn14 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 29, null, 0);
        lambda$Fn15 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 30, null, 0);
        lambda$Fn16 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 31, null, 0);
        lambda$Fn17 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 32, null, 0);
        lambda$Fn19 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 33, null, 0);
        lambda$Fn18 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 34, null, 0);
        lambda$Fn20 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 35, null, 0);
        this.Login$Initialize = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 36, Lit62, 0);
        lambda$Fn21 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 37, null, 0);
        lambda$Fn22 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 38, null, 0);
        lambda$Fn23 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 39, null, 0);
        lambda$Fn24 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 40, null, 0);
        this.back$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 41, Lit77, 0);
        lambda$Fn25 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 42, null, 0);
        lambda$Fn26 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 43, null, 0);
        this.logout$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 44, Lit84, 0);
        lambda$Fn27 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 45, null, 0);
        lambda$Fn28 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 46, null, 0);
        lambda$Fn29 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 47, null, 0);
        lambda$Fn30 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 48, null, 0);
        this.start$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 49, Lit102, 0);
        lambda$Fn31 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 50, null, 0);
        lambda$Fn32 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 51, null, 0);
        this.stop$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 52, Lit107, 0);
        lambda$Fn33 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 53, null, 0);
        lambda$Fn34 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 54, null, 0);
        lambda$Fn35 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 55, null, 0);
        lambda$Fn36 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 56, null, 0);
        lambda$Fn37 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 57, null, 0);
        lambda$Fn38 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 58, null, 0);
        lambda$Fn39 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 59, null, 0);
        lambda$Fn40 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 60, null, 0);
        lambda$Fn41 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 61, null, 0);
        lambda$Fn42 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 62, null, 0);
        lambda$Fn43 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 63, null, 0);
        lambda$Fn44 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 64, null, 0);
        lambda$Fn45 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 65, null, 0);
        lambda$Fn46 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 66, null, 0);
        lambda$Fn47 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 67, null, 0);
        lambda$Fn48 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 68, null, 0);
        lambda$Fn49 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 69, null, 0);
        lambda$Fn50 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 70, null, 0);
        lambda$Fn51 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 71, null, 0);
        lambda$Fn52 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 72, null, 0);
        this.analyze$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 73, Lit158, 0);
        this.BluetoothLE1$DeviceFound = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 74, Lit163, 0);
        this.BluetoothLE1$Connected = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 75, Lit165, 0);
        this.BluetoothLE1$StringsReceived = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 76, Lit192, 12291);
        this.Clock1$Timer = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 77, Lit209, 0);
    }

    static Boolean lambda13() {
        return Boolean.FALSE;
    }

    static Boolean lambda17() {
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
        String str;
        Consumer $result = $ctx.consumer;
        runtime.$instance.run();
        this.$Stdebug$Mnform$St = Boolean.FALSE;
        this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
        FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
        if (stringAppend == null) {
            str = null;
        } else {
            str = stringAppend.toString();
        }
        this.global$Mnvar$Mnenvironment = Environment.make(str);
        Login = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, ""), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, ""), $result);
        } else {
            addToGlobalVars(Lit4, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, "46a970e0-0d5f-11e2-8b5e-0002a5d5c51b"), $result);
        } else {
            addToGlobalVars(Lit5, lambda$Fn4);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit6, "0aad7ea0-0d60-11e2-8e3c-0002a5d5c51b"), $result);
        } else {
            addToGlobalVars(Lit6, lambda$Fn5);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("! \" # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~"), Lit8, "split at spaces")), $result);
        } else {
            addToGlobalVars(Lit7, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit10, runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126"), Lit11, "split at spaces")), $result);
        } else {
            addToGlobalVars(Lit10, lambda$Fn7);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit13, Lit14), $result);
        } else {
            addToGlobalVars(Lit13, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit15, Lit14), $result);
        } else {
            addToGlobalVars(Lit15, lambda$Fn9);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit16, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), $result);
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
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit19, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit20), Lit21, "select list item")), Lit22, "join"), "/HealthKeeper/RPi/Master/index.php"), Lit23, "join")), $result);
        } else {
            addToGlobalVars(Lit19, lambda$Fn13);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit27, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit28), Lit29, "select list item")), $result);
        } else {
            addToGlobalVars(Lit27, lambda$Fn14);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit31, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit32), Lit33, "select list item")), $result);
        } else {
            addToGlobalVars(Lit31, lambda$Fn15);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit35, Boolean.FALSE), $result);
        } else {
            addToGlobalVars(Lit35, lambda$Fn16);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit36, lambda$Fn17), $result);
        } else {
            addToGlobalVars(Lit36, lambda$Fn18);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit41, Lit42, Lit43);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit44, Boolean.TRUE, Lit45);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit46, Lit32, Lit43);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit47, "HealthKeeper_v3", Lit48);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit49, Lit50, Lit43);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit51, Boolean.FALSE, Lit45);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit52, "Responsive", Lit48);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit53, "AppTheme.Light.DarkActionBar", Lit48);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit54, "Session", Lit48), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn20));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit62, this.Login$Initialize);
        } else {
            addToFormEnvironment(Lit62, this.Login$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Login", "Initialize");
        } else {
            addToEvents(Lit0, Lit63);
        }
        this.WebViewer1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit64, Lit55, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit65, Lit55, Boolean.FALSE);
        }
        this.HorizontalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit66, Lit67, lambda$Fn21), $result);
        } else {
            addToComponents(Lit0, Lit70, Lit67, lambda$Fn22);
        }
        this.back = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit67, Lit71, Lit72, lambda$Fn23), $result);
        } else {
            addToComponents(Lit67, Lit75, Lit72, lambda$Fn24);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit77, this.back$Click);
        } else {
            addToFormEnvironment(Lit77, this.back$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "back", "Click");
        } else {
            addToEvents(Lit72, Lit78);
        }
        this.logout = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit67, Lit79, Lit80, lambda$Fn25), $result);
        } else {
            addToComponents(Lit67, Lit82, Lit80, lambda$Fn26);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit84, this.logout$Click);
        } else {
            addToFormEnvironment(Lit84, this.logout$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "logout", "Click");
        } else {
            addToEvents(Lit80, Lit78);
        }
        this.HorizontalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit85, Lit86, lambda$Fn27), $result);
        } else {
            addToComponents(Lit0, Lit87, Lit86, lambda$Fn28);
        }
        this.start = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit86, Lit88, Lit89, lambda$Fn29), $result);
        } else {
            addToComponents(Lit86, Lit91, Lit89, lambda$Fn30);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit102, this.start$Click);
        } else {
            addToFormEnvironment(Lit102, this.start$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "start", "Click");
        } else {
            addToEvents(Lit89, Lit78);
        }
        this.stop = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit86, Lit103, Lit104, lambda$Fn31), $result);
        } else {
            addToComponents(Lit86, Lit106, Lit104, lambda$Fn32);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit107, this.stop$Click);
        } else {
            addToFormEnvironment(Lit107, this.stop$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "stop", "Click");
        } else {
            addToEvents(Lit104, Lit78);
        }
        this.data = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit108, Lit109, lambda$Fn33), $result);
        } else {
            addToComponents(Lit0, Lit113, Lit109, lambda$Fn34);
        }
        this.HorizontalArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit114, Lit115, lambda$Fn35), $result);
        } else {
            addToComponents(Lit0, Lit116, Lit115, lambda$Fn36);
        }
        this.device = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit115, Lit117, Lit118, lambda$Fn37), $result);
        } else {
            addToComponents(Lit115, Lit121, Lit118, lambda$Fn38);
        }
        this.HorizontalArrangement4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit115, Lit122, Lit123, lambda$Fn39), $result);
        } else {
            addToComponents(Lit115, Lit125, Lit123, lambda$Fn40);
        }
        this.hr = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit123, Lit126, Lit94, lambda$Fn41), $result);
        } else {
            addToComponents(Lit123, Lit128, Lit94, lambda$Fn42);
        }
        this.Label4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit123, Lit129, Lit130, lambda$Fn43), $result);
        } else {
            addToComponents(Lit123, Lit132, Lit130, lambda$Fn44);
        }
        this.min = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit123, Lit133, Lit95, lambda$Fn45), $result);
        } else {
            addToComponents(Lit123, Lit135, Lit95, lambda$Fn46);
        }
        this.Label6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit123, Lit136, Lit137, lambda$Fn47), $result);
        } else {
            addToComponents(Lit123, Lit139, Lit137, lambda$Fn48);
        }
        this.sec = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit123, Lit140, Lit96, lambda$Fn49), $result);
        } else {
            addToComponents(Lit123, Lit142, Lit96, lambda$Fn50);
        }
        this.analyze = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit143, Lit144, lambda$Fn51), $result);
        } else {
            addToComponents(Lit0, Lit145, Lit144, lambda$Fn52);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit158, this.analyze$Click);
        } else {
            addToFormEnvironment(Lit158, this.analyze$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "analyze", "Click");
        } else {
            addToEvents(Lit144, Lit78);
        }
        this.BluetoothLE1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit159, Lit58, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit160, Lit58, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit163, this.BluetoothLE1$DeviceFound);
        } else {
            addToFormEnvironment(Lit163, this.BluetoothLE1$DeviceFound);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "DeviceFound");
        } else {
            addToEvents(Lit58, Lit164);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit165, this.BluetoothLE1$Connected);
        } else {
            addToFormEnvironment(Lit165, this.BluetoothLE1$Connected);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "Connected");
        } else {
            addToEvents(Lit58, Lit166);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit192, this.BluetoothLE1$StringsReceived);
        } else {
            addToFormEnvironment(Lit192, this.BluetoothLE1$StringsReceived);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "StringsReceived");
        } else {
            addToEvents(Lit58, Lit193);
        }
        this.Clock1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit194, Lit60, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit195, Lit60, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit209, this.Clock1$Timer);
        } else {
            addToFormEnvironment(Lit209, this.Clock1$Timer);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
        } else {
            addToEvents(Lit60, Lit210);
        }
        runtime.initRuntime();
    }

    static String lambda3() {
        return "";
    }

    static String lambda4() {
        return "";
    }

    static String lambda5() {
        return "46a970e0-0d5f-11e2-8b5e-0002a5d5c51b";
    }

    static String lambda6() {
        return "0aad7ea0-0d60-11e2-8e3c-0002a5d5c51b";
    }

    static Object lambda7() {
        return runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("! \" # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~"), Lit9, "split at spaces");
    }

    static Object lambda8() {
        return runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126"), Lit12, "split at spaces");
    }

    static IntNum lambda9() {
        return Lit14;
    }

    static IntNum lambda10() {
        return Lit14;
    }

    static Object lambda11() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static Object lambda12() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static Object lambda14() {
        return runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit20), Lit24, "select list item")), Lit25, "join"), "/HealthKeeper/RPi/Master/index.php"), Lit26, "join");
    }

    static Object lambda15() {
        return runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit28), Lit30, "select list item");
    }

    static Object lambda16() {
        return runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit32), Lit34, "select list item");
    }

    static Object lambda18() {
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit4, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(runtime.yail$Mnlist$Mnindex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit37, "index in list")), Lit38, "select list item"));
    }

    static Procedure lambda19() {
        return lambda$Fn19;
    }

    static Object lambda20() {
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit4, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(runtime.yail$Mnlist$Mnindex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit39, "index in list")), Lit40, "select list item"));
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit41, Lit42, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit44, Boolean.TRUE, Lit45);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit46, Lit32, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit47, "HealthKeeper_v3", Lit48);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit49, Lit50, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit51, Boolean.FALSE, Lit45);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit52, "Responsive", Lit48);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit53, "AppTheme.Light.DarkActionBar", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit54, "Session", Lit48);
    }

    public Object Login$Initialize() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit55, Lit56, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit57);
        runtime.callComponentMethod(Lit58, Lit59, LList.Empty, LList.Empty);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit61, Boolean.FALSE, Lit45);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit46, Lit32, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit68, Lit69, Lit43);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit46, Lit32, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit68, Lit69, Lit43);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit72, Lit68, Lit73, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit72, Lit74, "Go Back", Lit48);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit72, Lit68, Lit73, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit72, Lit74, "Go Back", Lit48);
    }

    public Object back$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit76, "open another screen");
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit68, Lit81, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit74, "Logout", Lit48);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit68, Lit81, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit74, "Logout", Lit48);
    }

    public Object logout$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit55, Lit56, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit83);
    }

    static Object lambda28() {
        return runtime.setAndCoerceProperty$Ex(Lit86, Lit68, Lit69, Lit43);
    }

    static Object lambda29() {
        return runtime.setAndCoerceProperty$Ex(Lit86, Lit68, Lit69, Lit43);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit89, Lit68, Lit90, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit74, "Start Recording", Lit48);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit89, Lit68, Lit90, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit74, "Start Recording", Lit48);
    }

    public Object start$Click() {
        runtime.setThisForm();
        if (runtime.callYailPrimitive(runtime.string$Mncontains, LList.list2(runtime.get$Mnproperty.apply2(Lit55, Lit92), "session"), Lit93, "contains") == Boolean.FALSE) {
            return Values.empty;
        }
        runtime.setAndCoerceProperty$Ex(Lit94, Lit74, Lit14, Lit48);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit74, Lit14, Lit48);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit74, Lit14, Lit48);
        runtime.addGlobalVarToCurrentFormEnvironment(Lit16, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit17, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit35, Boolean.FALSE);
        if (runtime.callYailPrimitive(strings.string$Eq$Qu, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit27, runtime.$Stthe$Mnnull$Mnvalue$St), ":"), Lit97, "split"), Lit20), Lit98, "select list item"), "A4"), Lit99, "text=") != Boolean.FALSE) {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit5, "cdeacb80-5235-4c07-8846-93a37ee6b86d");
            runtime.addGlobalVarToCurrentFormEnvironment(Lit6, "cdeacb81-5235-4c07-8846-93a37ee6b86d");
            runtime.addGlobalVarToCurrentFormEnvironment(Lit18, Boolean.TRUE);
        }
        runtime.setAndCoerceProperty$Ex(Lit60, Lit61, Boolean.TRUE, Lit45);
        return runtime.callComponentMethod(Lit58, Lit100, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.FALSE), Lit101);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit68, Lit105, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit74, "Stop Recording", Lit48);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit68, Lit105, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit74, "Stop Recording", Lit48);
    }

    public Object stop$Click() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit61, Boolean.FALSE, Lit45);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit109, Lit68, Lit69, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit109, Lit74, "SpO2 :  %, BPM : ", Lit48);
        runtime.setAndCoerceProperty$Ex(Lit109, Lit110, Lit20, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit109, Lit111, Lit112, Lit43);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit109, Lit68, Lit69, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit109, Lit74, "SpO2 :  %, BPM : ", Lit48);
        runtime.setAndCoerceProperty$Ex(Lit109, Lit110, Lit20, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit109, Lit111, Lit112, Lit43);
    }

    static Object lambda36() {
        return runtime.setAndCoerceProperty$Ex(Lit115, Lit68, Lit69, Lit43);
    }

    static Object lambda37() {
        return runtime.setAndCoerceProperty$Ex(Lit115, Lit68, Lit69, Lit43);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit118, Lit68, Lit119, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit74, "Wait for device refresh", Lit48);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit110, Lit20, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit111, Lit120, Lit43);
    }

    static Object lambda39() {
        runtime.setAndCoerceProperty$Ex(Lit118, Lit68, Lit119, Lit43);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit74, "Wait for device refresh", Lit48);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit110, Lit20, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit111, Lit120, Lit43);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit123, Lit46, Lit32, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit68, Lit124, Lit43);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit123, Lit46, Lit32, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit68, Lit124, Lit43);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit94, Lit74, "0", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit94, Lit111, Lit127, Lit43);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit94, Lit74, "0", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit94, Lit111, Lit127, Lit43);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit130, Lit74, ":", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit130, Lit111, Lit131, Lit43);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit130, Lit74, ":", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit130, Lit111, Lit131, Lit43);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit95, Lit74, "0", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit95, Lit111, Lit134, Lit43);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit95, Lit74, "0", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit95, Lit111, Lit134, Lit43);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit137, Lit74, ":", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit137, Lit111, Lit138, Lit43);
    }

    static Object lambda49() {
        runtime.setAndCoerceProperty$Ex(Lit137, Lit74, ":", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit137, Lit111, Lit138, Lit43);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit96, Lit74, "0", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit111, Lit141, Lit43);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit96, Lit74, "0", Lit48);
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit111, Lit141, Lit43);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit144, Lit68, Lit69, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit74, "Analyze", Lit48);
    }

    static Object lambda53() {
        runtime.setAndCoerceProperty$Ex(Lit144, Lit68, Lit69, Lit43);
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit74, "Analyze", Lit48);
    }

    public Object analyze$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit55, Lit56, LList.list1(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit20), Lit146, "select list item")), Lit147, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("/HealthKeeper/RPi/Master/session.php/?data1=", runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnto$Mncsv$Mnrow, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit148, "list to csv row"), "\"", ""), Lit149, "replace all")), Lit150, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("&data2=", runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnto$Mncsv$Mnrow, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit151, "list to csv row"), "\"", ""), Lit152, "replace all")), Lit153, "join")), Lit154, "join"), "&analyze=analyze"), Lit155, "join")), Lit156, "join")), Lit157);
    }

    public Object BluetoothLE1$DeviceFound() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit58, Lit161, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit27, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit162);
    }

    public Object BluetoothLE1$Connected() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit74, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit31, runtime.$Stthe$Mnnull$Mnvalue$St), Lit48);
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
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list1(runtime.callYailPrimitive(moduleMethod2, LList.list1(signalRuntimeError), Lit168, "is list empty?")), Lit169, "not") == Boolean.FALSE) {
            return Values.empty;
        }
        if (runtime.callYailPrimitive(runtime.yail$Mnnot, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit170, "not") != Boolean.FALSE) {
            Object signalRuntimeError2;
            runtime.addGlobalVarToCurrentFormEnvironment(Lit35, Boolean.TRUE);
            Symbol symbol = Lit3;
            ModuleMethod moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
            if ($stringValues instanceof Package) {
                signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
            } else {
                signalRuntimeError = $stringValues;
            }
            AddOp addOp = AddOp.$Mn;
            ModuleMethod moduleMethod4 = runtime.yail$Mnlist$Mnlength;
            if ($stringValues instanceof Package) {
                signalRuntimeError2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
            } else {
                signalRuntimeError2 = $stringValues;
            }
            runtime.addGlobalVarToCurrentFormEnvironment(symbol, runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, runtime.callYailPrimitive(addOp, LList.list2(runtime.callYailPrimitive(moduleMethod4, LList.list1(signalRuntimeError2), Lit171, "length of list"), Lit20), Lit172, "-")), Lit173, "select list item"));
            runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(runtime.string$Mnsubstring, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(strings.string$Mnlength, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit174, "length"), Lit20), Lit175, "segment"));
            Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit36, runtime.$Stthe$Mnnull$Mnvalue$St));
            runtime.addGlobalVarToCurrentFormEnvironment(Lit13, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St));
            symbol = Lit3;
            moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
            if ($stringValues instanceof Package) {
                signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
            } else {
                signalRuntimeError = $stringValues;
            }
            ModuleMethod moduleMethod5 = runtime.yail$Mnlist$Mnlength;
            if ($stringValues instanceof Package) {
                signalRuntimeError2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
            } else {
                signalRuntimeError2 = $stringValues;
            }
            runtime.addGlobalVarToCurrentFormEnvironment(symbol, runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, runtime.callYailPrimitive(moduleMethod5, LList.list1(signalRuntimeError2), Lit176, "length of list")), Lit177, "select list item"));
            Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit36, runtime.$Stthe$Mnnull$Mnvalue$St));
            runtime.addGlobalVarToCurrentFormEnvironment(Lit15, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St));
            runtime.setAndCoerceProperty$Ex(Lit109, Lit74, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("SpO2 : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit178, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(" %, BPM : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit179, "join")), Lit180, "join"), Lit48);
        }
        if (runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, runtime.$Stthe$Mnnull$Mnvalue$St) == Boolean.FALSE) {
            return Values.empty;
        }
        moduleMethod = runtime.yail$Mnequal$Qu;
        moduleMethod2 = strings.string$Mnlength;
        moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callYailPrimitive(moduleMethod2, LList.list1(runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, Lit20), Lit181, "select list item")), Lit182, "length"), Lit183), Lit184, "=") == Boolean.FALSE) {
            return Values.empty;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(Lit35, Boolean.TRUE);
        Symbol symbol2 = Lit3;
        moduleMethod2 = runtime.string$Mnsubstring;
        moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol2, runtime.callYailPrimitive(moduleMethod2, LList.list3(runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, Lit20), Lit185, "select list item"), Lit32, Lit20), Lit186, "segment"));
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit36, runtime.$Stthe$Mnnull$Mnvalue$St));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit13, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St));
        Symbol symbol3 = Lit3;
        moduleMethod = runtime.string$Mnsubstring;
        moduleMethod2 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            $stringValues = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit167), " is not bound in the current context"), "Unbound Variable");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol3, runtime.callYailPrimitive(moduleMethod, LList.list3(runtime.callYailPrimitive(moduleMethod2, LList.list2($stringValues, Lit20), Lit187, "select list item"), Lit28, Lit20), Lit188, "segment"));
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit36, runtime.$Stthe$Mnnull$Mnvalue$St));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit15, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.setAndCoerceProperty$Ex(Lit109, Lit74, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("SpO2 : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit189, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(" %, BPM : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit190, "join")), Lit191, "join"), Lit48);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        if (runtime.lookupGlobalVarInCurrentFormEnvironment(Lit35, runtime.$Stthe$Mnnull$Mnvalue$St) != Boolean.FALSE) {
            runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit196, "add items to list");
            runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit197, "add items to list");
            runtime.callComponentMethod(Lit58, Lit100, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.FALSE), Lit198);
        }
        runtime.setAndCoerceProperty$Ex(Lit96, Lit74, runtime.callYailPrimitive(DivideOp.modulo, LList.list2(runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.get$Mnproperty.apply2(Lit96, Lit74), Lit20), Lit199, "+"), Lit200), Lit201, "modulo"), Lit48);
        if (runtime.callYailPrimitive(strings.string$Eq$Qu, LList.list2(runtime.get$Mnproperty.apply2(Lit96, Lit74), Lit14), Lit202, "text=") == Boolean.FALSE) {
            return Values.empty;
        }
        runtime.setAndCoerceProperty$Ex(Lit95, Lit74, runtime.callYailPrimitive(DivideOp.modulo, LList.list2(runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.get$Mnproperty.apply2(Lit95, Lit74), Lit20), Lit203, "+"), Lit200), Lit204, "modulo"), Lit48);
        return runtime.callYailPrimitive(strings.string$Eq$Qu, LList.list2(runtime.get$Mnproperty.apply2(Lit95, Lit74), Lit14), Lit205, "text=") != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit94, Lit74, runtime.callYailPrimitive(DivideOp.modulo, LList.list2(runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.get$Mnproperty.apply2(Lit95, Lit74), Lit20), Lit206, "+"), Lit207), Lit208, "modulo"), Lit48) : Values.empty;
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
        Login = this;
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
        Login closureEnv = this;
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
