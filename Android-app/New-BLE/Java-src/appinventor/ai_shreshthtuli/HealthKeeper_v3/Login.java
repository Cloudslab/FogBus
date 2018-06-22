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
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("g$utf").readResolve());
    static final FString Lit100 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit101 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418193), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418187);
    static final PairWithPosition Lit102 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418230), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418224);
    static final PairWithPosition Lit103 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418545);
    static final PairWithPosition Lit104 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418592), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418587), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418581);
    static final PairWithPosition Lit105 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418622), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418616);
    static final PairWithPosition Lit106 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418845);
    static final PairWithPosition Lit107 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418892), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418887), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418881);
    static final PairWithPosition Lit108 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418922), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418916);
    static final PairWithPosition Lit109 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418946), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418940);
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 49422);
    static final PairWithPosition Lit110 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418970), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418964);
    static final PairWithPosition Lit111 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 418987);
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("stop$Click").readResolve());
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("data").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit117;
    static final FString Lit118 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit119 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final PairWithPosition Lit12 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 49422);
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve());
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("device").readResolve());
    static final IntNum Lit124 = IntNum.make(-1050);
    static final IntNum Lit125;
    static final FString Lit126 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit127 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve());
    static final IntNum Lit129 = IntNum.make(-1050);
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("g$numbers").readResolve());
    static final FString Lit130 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit131 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit132;
    static final FString Lit133 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("Label4").readResolve());
    static final IntNum Lit136;
    static final FString Lit137 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit138 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit139;
    static final PairWithPosition Lit14 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 53641);
    static final FString Lit140 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit141 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("Label6").readResolve());
    static final IntNum Lit143;
    static final FString Lit144 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit145 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit146;
    static final FString Lit147 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit148 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit149 = ((SimpleSymbol) new SimpleSymbol("analyze").readResolve());
    static final PairWithPosition Lit15 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 53641);
    static final FString Lit150 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit151 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819541), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819535);
    static final PairWithPosition Lit152 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819578), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819572);
    static final PairWithPosition Lit153 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819948);
    static final PairWithPosition Lit154 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819995), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819990), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 819984);
    static final PairWithPosition Lit155 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820025), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820019);
    static final PairWithPosition Lit156 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820248);
    static final PairWithPosition Lit157 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820295), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820290), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820284);
    static final PairWithPosition Lit158 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820325), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820319);
    static final PairWithPosition Lit159 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820349), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820343);
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("g$value1").readResolve());
    static final PairWithPosition Lit160 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820392), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820386);
    static final PairWithPosition Lit161 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820416), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820410);
    static final PairWithPosition Lit162 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 820433);
    static final SimpleSymbol Lit163 = ((SimpleSymbol) new SimpleSymbol("analyze$Click").readResolve());
    static final FString Lit164 = new FString("edu.mit.appinventor.ble.BluetoothLE");
    static final FString Lit165 = new FString("edu.mit.appinventor.ble.BluetoothLE");
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("ConnectWithAddress").readResolve());
    static final PairWithPosition Lit167 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 847977);
    static final SimpleSymbol Lit168 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$DeviceFound").readResolve());
    static final SimpleSymbol Lit169 = ((SimpleSymbol) new SimpleSymbol("DeviceFound").readResolve());
    static final IntNum Lit17 = IntNum.make(0);
    static final SimpleSymbol Lit170 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$Connected").readResolve());
    static final SimpleSymbol Lit171 = ((SimpleSymbol) new SimpleSymbol("Connected").readResolve());
    static final SimpleSymbol Lit172 = ((SimpleSymbol) new SimpleSymbol("$stringValues").readResolve());
    static final PairWithPosition Lit173 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864406);
    static final PairWithPosition Lit174 = PairWithPosition.make(Lit44, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864433);
    static final PairWithPosition Lit175 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864728);
    static final PairWithPosition Lit176 = PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864765), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864757);
    static final PairWithPosition Lit177 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864786), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864780);
    static final PairWithPosition Lit178 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 864992);
    static final PairWithPosition Lit179 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865026), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865019), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865013);
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("g$value2").readResolve());
    static final PairWithPosition Lit180 = PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865313);
    static final PairWithPosition Lit181 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865346), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865340);
    static final PairWithPosition Lit182 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865626), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865620);
    static final PairWithPosition Lit183 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865737), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865731);
    static final PairWithPosition Lit184 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865761), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 865755);
    static final SimpleSymbol Lit185 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1$StringsReceived").readResolve());
    static final SimpleSymbol Lit186 = ((SimpleSymbol) new SimpleSymbol("StringsReceived").readResolve());
    static final FString Lit187 = new FString("com.google.appinventor.components.runtime.Clock");
    static final FString Lit188 = new FString("com.google.appinventor.components.runtime.Clock");
    static final PairWithPosition Lit189 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit218, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893077), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893071);
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("g$data1").readResolve());
    static final PairWithPosition Lit190 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit218, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893213), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893207);
    static final PairWithPosition Lit191;
    static final PairWithPosition Lit192 = PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893569), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893561);
    static final IntNum Lit193 = IntNum.make(60);
    static final PairWithPosition Lit194 = PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893596), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893588);
    static final IntNum Lit195 = IntNum.make(59);
    static final PairWithPosition Lit196 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893711), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893705);
    static final PairWithPosition Lit197 = PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893902), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893894);
    static final PairWithPosition Lit198 = PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893929), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893921);
    static final PairWithPosition Lit199;
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("g$data2").readResolve());
    static final PairWithPosition Lit200 = PairWithPosition.make(Lit42, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 894234), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 894226);
    static final IntNum Lit201 = IntNum.make(24);
    static final PairWithPosition Lit202;
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit207 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit208 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit209 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("g$url").readResolve());
    static final SimpleSymbol Lit210 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit211 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit212 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit213 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit214 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit215 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit216 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit217 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit218 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final IntNum Lit22 = IntNum.make(1);
    static final PairWithPosition Lit23 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74012), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74006);
    static final PairWithPosition Lit24 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74049), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74043);
    static final PairWithPosition Lit25 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74110), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74104);
    static final PairWithPosition Lit26 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74012), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74006);
    static final PairWithPosition Lit27 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74049), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74043);
    static final PairWithPosition Lit28 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74110), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 74104);
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("g$deviceID").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$deviceName").readResolve());
    static final IntNum Lit30 = IntNum.make(2);
    static final PairWithPosition Lit31 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 77993), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 77987);
    static final PairWithPosition Lit32 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 77993), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 77987);
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("g$numberoutput").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("g$firstval").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("p$convert").readResolve());
    static final PairWithPosition Lit36 = PairWithPosition.make(Lit218, PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90338), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90333);
    static final PairWithPosition Lit37 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90369), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90363);
    static final PairWithPosition Lit38 = PairWithPosition.make(Lit218, PairWithPosition.make(Lit217, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90338), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90333);
    static final PairWithPosition Lit39 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90369), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 90363);
    static final IntNum Lit4 = IntNum.make(3);
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final IntNum Lit41;
    static final SimpleSymbol Lit42;
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit44;
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit47;
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit49;
    static final PairWithPosition Lit5 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 32939), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 32933);
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("ShowStatusBar").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("Theme").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("WebViewer1").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("GoToUrl").readResolve());
    static final PairWithPosition Lit56 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 151639);
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("BluetoothLE1").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("StartScanning").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final PairWithPosition Lit6 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 32939), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 32933);
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("TimerEnabled").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Login$Initialize").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit63 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final FString Lit64 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit68 = IntNum.make(-2);
    static final FString Lit69 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$utfinput").readResolve());
    static final FString Lit70 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("back").readResolve());
    static final IntNum Lit72 = IntNum.make(-1050);
    static final SimpleSymbol Lit73 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit74 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit75 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 254030);
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("back$Click").readResolve());
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit78 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("logout").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("g$service_UUID").readResolve());
    static final IntNum Lit80 = IntNum.make(-1050);
    static final FString Lit81 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit82 = PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 299095);
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("logout$Click").readResolve());
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("start").readResolve());
    static final IntNum Lit89 = IntNum.make(-1050);
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("g$characteristic_UUID").readResolve());
    static final FString Lit90 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("hr").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("min").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("sec").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("RegisterForStrings").readResolve());
    static final PairWithPosition Lit95 = PairWithPosition.make(Lit47, PairWithPosition.make(Lit47, PairWithPosition.make(Lit44, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 373301), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 373296), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 373290);
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("start$Click").readResolve());
    static final FString Lit97 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("stop").readResolve());
    static final IntNum Lit99 = IntNum.make(-1050);
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
            return moduleMethod.selector == 75 ? this.$main.BluetoothLE1$StringsReceived(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 75) {
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
                    return Login.lambda19();
                case 33:
                    return Login.lambda18();
                case 34:
                    return Login.lambda20();
                case 35:
                    return this.$main.Login$Initialize();
                case 36:
                    return Login.lambda21();
                case 37:
                    return Login.lambda22();
                case 38:
                    return Login.lambda23();
                case 39:
                    return Login.lambda24();
                case 40:
                    return this.$main.back$Click();
                case 41:
                    return Login.lambda25();
                case 42:
                    return Login.lambda26();
                case 43:
                    return this.$main.logout$Click();
                case 44:
                    return Login.lambda27();
                case 45:
                    return Login.lambda28();
                case 46:
                    return Login.lambda29();
                case 47:
                    return Login.lambda30();
                case 48:
                    return this.$main.start$Click();
                case 49:
                    return Login.lambda31();
                case 50:
                    return Login.lambda32();
                case 51:
                    return this.$main.stop$Click();
                case 52:
                    return Login.lambda33();
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
                    return this.$main.analyze$Click();
                case 73:
                    return this.$main.BluetoothLE1$DeviceFound();
                case 74:
                    return this.$main.BluetoothLE1$Connected();
                case 76:
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
                case 76:
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
        Lit42 = simpleSymbol;
        Lit202 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit42, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 894261), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 894253);
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit47 = simpleSymbol;
        Lit199 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit47, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 894044), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 894038);
        SimpleSymbol simpleSymbol2 = Lit47;
        SimpleSymbol simpleSymbol3 = Lit47;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit44 = simpleSymbol;
        Lit191 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893391), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893386), "/tmp/1529670161599_0.4350797441431-0/youngandroidproject/../src/appinventor/ai_shreshthtuli/HealthKeeper_v3/Login.yail", 893380);
        int[] iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit146 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit143 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit139 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit136 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit132 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit125 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_LTGRAY;
        Lit117 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_DKGRAY;
        Lit49 = IntNum.make(iArr);
        iArr = new int[2];
        iArr[0] = Component.COLOR_CYAN;
        Lit41 = IntNum.make(iArr);
    }

    public Login() {
        ModuleInfo.register(this);
        ModuleBody appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame = new frame();
        this.android$Mnlog$Mnform = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 1, Lit205, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 2, Lit206, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 3, Lit207, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 5, Lit208, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 6, Lit209, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 7, Lit210, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 8, Lit211, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 9, Lit212, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 10, Lit213, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 11, Lit214, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 12, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 13, Lit215, 16388);
        this.lookup$Mnhandler = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 14, Lit216, 8194);
        PropertySet moduleMethod = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 15, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime744766471814722776.scm:553");
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
        lambda$Fn18 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 32, null, 0);
        lambda$Fn17 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 33, null, 0);
        lambda$Fn19 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 34, null, 0);
        this.Login$Initialize = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 35, Lit61, 0);
        lambda$Fn20 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 36, null, 0);
        lambda$Fn21 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 37, null, 0);
        lambda$Fn22 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 38, null, 0);
        lambda$Fn23 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 39, null, 0);
        this.back$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 40, Lit76, 0);
        lambda$Fn24 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 41, null, 0);
        lambda$Fn25 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 42, null, 0);
        this.logout$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 43, Lit83, 0);
        lambda$Fn26 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 44, null, 0);
        lambda$Fn27 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 45, null, 0);
        lambda$Fn28 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 46, null, 0);
        lambda$Fn29 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 47, null, 0);
        this.start$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 48, Lit96, 0);
        lambda$Fn30 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 49, null, 0);
        lambda$Fn31 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 50, null, 0);
        this.stop$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 51, Lit112, 0);
        lambda$Fn32 = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 52, null, 0);
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
        this.analyze$Click = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 72, Lit163, 0);
        this.BluetoothLE1$DeviceFound = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 73, Lit168, 0);
        this.BluetoothLE1$Connected = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 74, Lit170, 0);
        this.BluetoothLE1$StringsReceived = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 75, Lit185, 12291);
        this.Clock1$Timer = new ModuleMethod(appinventor_ai_shreshthtuli_HealthKeeper_v3_Login_frame, 76, Lit203, 0);
    }

    static Boolean lambda16() {
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
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit4), Lit5, "select list item")), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, ""), $result);
        } else {
            addToGlobalVars(Lit7, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit8, "46a970e0-0d5f-11e2-8b5e-0002a5d5c51b"), $result);
        } else {
            addToGlobalVars(Lit8, lambda$Fn4);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit9, "0aad7ea0-0d60-11e2-8e3c-0002a5d5c51b"), $result);
        } else {
            addToGlobalVars(Lit9, lambda$Fn5);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit10, runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("! \" # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~"), Lit11, "split at spaces")), $result);
        } else {
            addToGlobalVars(Lit10, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit13, runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126"), Lit14, "split at spaces")), $result);
        } else {
            addToGlobalVars(Lit13, lambda$Fn7);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit16, Lit17), $result);
        } else {
            addToGlobalVars(Lit16, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit18, Lit17), $result);
        } else {
            addToGlobalVars(Lit18, lambda$Fn9);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit19, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), $result);
        } else {
            addToGlobalVars(Lit19, lambda$Fn10);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit20, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), $result);
        } else {
            addToGlobalVars(Lit20, lambda$Fn11);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit21, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit22), Lit23, "select list item")), Lit24, "join"), "/HealthKeeper/RPi/Master/index.php"), Lit25, "join")), $result);
        } else {
            addToGlobalVars(Lit21, lambda$Fn12);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit29, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit30), Lit31, "select list item")), $result);
        } else {
            addToGlobalVars(Lit29, lambda$Fn13);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit33, ""), $result);
        } else {
            addToGlobalVars(Lit33, lambda$Fn14);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit34, Boolean.FALSE), $result);
        } else {
            addToGlobalVars(Lit34, lambda$Fn15);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit35, lambda$Fn16), $result);
        } else {
            addToGlobalVars(Lit35, lambda$Fn17);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit40, Lit41, Lit42);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit43, Boolean.TRUE, Lit44);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit45, Lit4, Lit42);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit46, "HealthKeeper_v3", Lit47);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit48, Lit49, Lit42);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit50, Boolean.FALSE, Lit44);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit51, "Responsive", Lit47);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit52, "AppTheme.Light.DarkActionBar", Lit47);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit53, "Session", Lit47), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn19));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit61, this.Login$Initialize);
        } else {
            addToFormEnvironment(Lit61, this.Login$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Login", "Initialize");
        } else {
            addToEvents(Lit0, Lit62);
        }
        this.WebViewer1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit63, Lit54, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit64, Lit54, Boolean.FALSE);
        }
        this.HorizontalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit65, Lit66, lambda$Fn20), $result);
        } else {
            addToComponents(Lit0, Lit69, Lit66, lambda$Fn21);
        }
        this.back = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit66, Lit70, Lit71, lambda$Fn22), $result);
        } else {
            addToComponents(Lit66, Lit74, Lit71, lambda$Fn23);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit76, this.back$Click);
        } else {
            addToFormEnvironment(Lit76, this.back$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "back", "Click");
        } else {
            addToEvents(Lit71, Lit77);
        }
        this.logout = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit66, Lit78, Lit79, lambda$Fn24), $result);
        } else {
            addToComponents(Lit66, Lit81, Lit79, lambda$Fn25);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit83, this.logout$Click);
        } else {
            addToFormEnvironment(Lit83, this.logout$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "logout", "Click");
        } else {
            addToEvents(Lit79, Lit77);
        }
        this.HorizontalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit84, Lit85, lambda$Fn26), $result);
        } else {
            addToComponents(Lit0, Lit86, Lit85, lambda$Fn27);
        }
        this.start = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit85, Lit87, Lit88, lambda$Fn28), $result);
        } else {
            addToComponents(Lit85, Lit90, Lit88, lambda$Fn29);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit96, this.start$Click);
        } else {
            addToFormEnvironment(Lit96, this.start$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "start", "Click");
        } else {
            addToEvents(Lit88, Lit77);
        }
        this.stop = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit85, Lit97, Lit98, lambda$Fn30), $result);
        } else {
            addToComponents(Lit85, Lit100, Lit98, lambda$Fn31);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit112, this.stop$Click);
        } else {
            addToFormEnvironment(Lit112, this.stop$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "stop", "Click");
        } else {
            addToEvents(Lit98, Lit77);
        }
        this.data = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit113, Lit114, lambda$Fn32), $result);
        } else {
            addToComponents(Lit0, Lit118, Lit114, lambda$Fn33);
        }
        this.HorizontalArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit119, Lit120, lambda$Fn34), $result);
        } else {
            addToComponents(Lit0, Lit121, Lit120, lambda$Fn35);
        }
        this.device = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit120, Lit122, Lit123, lambda$Fn36), $result);
        } else {
            addToComponents(Lit120, Lit126, Lit123, lambda$Fn37);
        }
        this.HorizontalArrangement4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit120, Lit127, Lit128, lambda$Fn38), $result);
        } else {
            addToComponents(Lit120, Lit130, Lit128, lambda$Fn39);
        }
        this.hr = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit128, Lit131, Lit91, lambda$Fn40), $result);
        } else {
            addToComponents(Lit128, Lit133, Lit91, lambda$Fn41);
        }
        this.Label4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit128, Lit134, Lit135, lambda$Fn42), $result);
        } else {
            addToComponents(Lit128, Lit137, Lit135, lambda$Fn43);
        }
        this.min = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit128, Lit138, Lit92, lambda$Fn44), $result);
        } else {
            addToComponents(Lit128, Lit140, Lit92, lambda$Fn45);
        }
        this.Label6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit128, Lit141, Lit142, lambda$Fn46), $result);
        } else {
            addToComponents(Lit128, Lit144, Lit142, lambda$Fn47);
        }
        this.sec = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit128, Lit145, Lit93, lambda$Fn48), $result);
        } else {
            addToComponents(Lit128, Lit147, Lit93, lambda$Fn49);
        }
        this.analyze = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit148, Lit149, lambda$Fn50), $result);
        } else {
            addToComponents(Lit0, Lit150, Lit149, lambda$Fn51);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit163, this.analyze$Click);
        } else {
            addToFormEnvironment(Lit163, this.analyze$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "analyze", "Click");
        } else {
            addToEvents(Lit149, Lit77);
        }
        this.BluetoothLE1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit164, Lit57, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit165, Lit57, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit168, this.BluetoothLE1$DeviceFound);
        } else {
            addToFormEnvironment(Lit168, this.BluetoothLE1$DeviceFound);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "DeviceFound");
        } else {
            addToEvents(Lit57, Lit169);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit170, this.BluetoothLE1$Connected);
        } else {
            addToFormEnvironment(Lit170, this.BluetoothLE1$Connected);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "Connected");
        } else {
            addToEvents(Lit57, Lit171);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit185, this.BluetoothLE1$StringsReceived);
        } else {
            addToFormEnvironment(Lit185, this.BluetoothLE1$StringsReceived);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "BluetoothLE1", "StringsReceived");
        } else {
            addToEvents(Lit57, Lit186);
        }
        this.Clock1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit187, Lit59, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit188, Lit59, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit203, this.Clock1$Timer);
        } else {
            addToFormEnvironment(Lit203, this.Clock1$Timer);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
        } else {
            addToEvents(Lit59, Lit204);
        }
        runtime.initRuntime();
    }

    static Object lambda3() {
        return runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit4), Lit6, "select list item");
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
        return runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("! \" # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~"), Lit12, "split at spaces");
    }

    static Object lambda8() {
        return runtime.callYailPrimitive(runtime.string$Mnsplit$Mnat$Mnspaces, LList.list1("33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126"), Lit15, "split at spaces");
    }

    static IntNum lambda9() {
        return Lit17;
    }

    static IntNum lambda10() {
        return Lit17;
    }

    static Object lambda11() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static Object lambda12() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static Object lambda13() {
        return runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit22), Lit26, "select list item")), Lit27, "join"), "/HealthKeeper/RPi/Master/index.php"), Lit28, "join");
    }

    static Object lambda14() {
        return runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit30), Lit32, "select list item");
    }

    static String lambda15() {
        return "";
    }

    static Object lambda17() {
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit33, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(runtime.yail$Mnlist$Mnindex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit36, "index in list")), Lit37, "select list item"));
    }

    static Procedure lambda18() {
        return lambda$Fn18;
    }

    static Object lambda19() {
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit33, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(runtime.yail$Mnlist$Mnindex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit38, "index in list")), Lit39, "select list item"));
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit40, Lit41, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit43, Boolean.TRUE, Lit44);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit45, Lit4, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit46, "HealthKeeper_v3", Lit47);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit48, Lit49, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit50, Boolean.FALSE, Lit44);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit51, "Responsive", Lit47);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit52, "AppTheme.Light.DarkActionBar", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit53, "Session", Lit47);
    }

    public Object Login$Initialize() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit54, Lit55, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit21, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit56);
        runtime.callComponentMethod(Lit57, Lit58, LList.Empty, LList.Empty);
        return runtime.setAndCoerceProperty$Ex(Lit59, Lit60, Boolean.FALSE, Lit44);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit45, Lit4, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit67, Lit68, Lit42);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit45, Lit4, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit67, Lit68, Lit42);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit71, Lit67, Lit72, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit71, Lit73, "Go Back", Lit47);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit71, Lit67, Lit72, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit71, Lit73, "Go Back", Lit47);
    }

    public Object back$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit75, "open another screen");
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit79, Lit67, Lit80, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit79, Lit73, "Logout", Lit47);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit79, Lit67, Lit80, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit79, Lit73, "Logout", Lit47);
    }

    public Object logout$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit54, Lit55, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit21, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit82);
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit67, Lit68, Lit42);
    }

    static Object lambda28() {
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit67, Lit68, Lit42);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit67, Lit89, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit73, "Start Recording", Lit47);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit67, Lit89, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit73, "Start Recording", Lit47);
    }

    public Object start$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit91, Lit73, Lit17, Lit47);
        runtime.setAndCoerceProperty$Ex(Lit92, Lit73, Lit17, Lit47);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit73, Lit17, Lit47);
        runtime.addGlobalVarToCurrentFormEnvironment(Lit19, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit20, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit34, Boolean.FALSE);
        runtime.setAndCoerceProperty$Ex(Lit59, Lit60, Boolean.TRUE, Lit44);
        return runtime.callComponentMethod(Lit57, Lit94, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit8, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.FALSE), Lit95);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit98, Lit67, Lit99, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit73, "Stop Recording", Lit47);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit98, Lit67, Lit99, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit73, "Stop Recording", Lit47);
    }

    public Object stop$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit59, Lit60, Boolean.FALSE, Lit44);
        return runtime.callComponentMethod(Lit54, Lit55, LList.list1(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit22), Lit101, "select list item")), Lit102, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("/HealthKeeper/RPi/Master/session.php/?data1=", runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnto$Mncsv$Mnrow, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit103, "list to csv row"), "\"", ""), Lit104, "replace all")), Lit105, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("&data2=", runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnto$Mncsv$Mnrow, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit20, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit106, "list to csv row"), "\"", ""), Lit107, "replace all")), Lit108, "join")), Lit109, "join")), Lit110, "join")), Lit111);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit114, Lit67, Lit68, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit73, "SpO2 :  %, BPM : ", Lit47);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit115, Lit22, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit114, Lit116, Lit117, Lit42);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit114, Lit67, Lit68, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit73, "SpO2 :  %, BPM : ", Lit47);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit115, Lit22, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit114, Lit116, Lit117, Lit42);
    }

    static Object lambda35() {
        return runtime.setAndCoerceProperty$Ex(Lit120, Lit67, Lit68, Lit42);
    }

    static Object lambda36() {
        return runtime.setAndCoerceProperty$Ex(Lit120, Lit67, Lit68, Lit42);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit123, Lit67, Lit124, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit123, Lit73, "Wait for device refresh", Lit47);
        runtime.setAndCoerceProperty$Ex(Lit123, Lit115, Lit22, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit116, Lit125, Lit42);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit123, Lit67, Lit124, Lit42);
        runtime.setAndCoerceProperty$Ex(Lit123, Lit73, "Wait for device refresh", Lit47);
        runtime.setAndCoerceProperty$Ex(Lit123, Lit115, Lit22, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit116, Lit125, Lit42);
    }

    static Object lambda39() {
        runtime.setAndCoerceProperty$Ex(Lit128, Lit45, Lit4, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit128, Lit67, Lit129, Lit42);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit128, Lit45, Lit4, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit128, Lit67, Lit129, Lit42);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit91, Lit73, "0", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit91, Lit116, Lit132, Lit42);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit91, Lit73, "0", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit91, Lit116, Lit132, Lit42);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit73, ":", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit116, Lit136, Lit42);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit73, ":", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit116, Lit136, Lit42);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit92, Lit73, "0", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit92, Lit116, Lit139, Lit42);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit92, Lit73, "0", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit92, Lit116, Lit139, Lit42);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit142, Lit73, ":", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit142, Lit116, Lit143, Lit42);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit142, Lit73, ":", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit142, Lit116, Lit143, Lit42);
    }

    static Object lambda49() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit73, "0", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit116, Lit146, Lit42);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit73, "0", Lit47);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit116, Lit146, Lit42);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit149, Lit67, Lit68, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit149, Lit73, "Analyze", Lit47);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit149, Lit67, Lit68, Lit42);
        return runtime.setAndCoerceProperty$Ex(Lit149, Lit73, "Analyze", Lit47);
    }

    public Object analyze$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit54, Lit55, LList.list1(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.get$Mnstart$Mnvalue, LList.Empty, LList.Empty, "get start value"), Lit22), Lit151, "select list item")), Lit152, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("/HealthKeeper/RPi/Master/session.php/?data1=", runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnto$Mncsv$Mnrow, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit153, "list to csv row"), "\"", ""), Lit154, "replace all")), Lit155, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("&data2=", runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnto$Mncsv$Mnrow, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit20, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit156, "list to csv row"), "\"", ""), Lit157, "replace all")), Lit158, "join")), Lit159, "join"), "&analyze=analyze"), Lit160, "join")), Lit161, "join")), Lit162);
    }

    public Object BluetoothLE1$DeviceFound() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit57, Lit166, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit29, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit167);
    }

    public Object BluetoothLE1$Connected() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit73, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit47);
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
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit172), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list1(runtime.callYailPrimitive(moduleMethod2, LList.list1(signalRuntimeError), Lit173, "is list empty?")), Lit174, "not") == Boolean.FALSE) {
            return Values.empty;
        }
        Object signalRuntimeError2;
        runtime.addGlobalVarToCurrentFormEnvironment(Lit34, Boolean.TRUE);
        Symbol symbol = Lit7;
        ModuleMethod moduleMethod3 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit172), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        AddOp addOp = AddOp.$Mn;
        ModuleMethod moduleMethod4 = runtime.yail$Mnlist$Mnlength;
        if ($stringValues instanceof Package) {
            signalRuntimeError2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit172), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError2 = $stringValues;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol, runtime.callYailPrimitive(moduleMethod3, LList.list2(signalRuntimeError, runtime.callYailPrimitive(addOp, LList.list2(runtime.callYailPrimitive(moduleMethod4, LList.list1(signalRuntimeError2), Lit175, "length of list"), Lit22), Lit176, "-")), Lit177, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit7, runtime.callYailPrimitive(runtime.string$Mnsubstring, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(strings.string$Mnlength, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit178, "length"), Lit22), Lit179, "segment"));
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit35, runtime.$Stthe$Mnnull$Mnvalue$St));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit16, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit33, runtime.$Stthe$Mnnull$Mnvalue$St));
        Symbol symbol2 = Lit7;
        moduleMethod2 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($stringValues instanceof Package) {
            signalRuntimeError = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit172), " is not bound in the current context"), "Unbound Variable");
        } else {
            signalRuntimeError = $stringValues;
        }
        moduleMethod3 = runtime.yail$Mnlist$Mnlength;
        if ($stringValues instanceof Package) {
            $stringValues = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit172), " is not bound in the current context"), "Unbound Variable");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(symbol2, runtime.callYailPrimitive(moduleMethod2, LList.list2(signalRuntimeError, runtime.callYailPrimitive(moduleMethod3, LList.list1($stringValues), Lit180, "length of list")), Lit181, "select list item"));
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit35, runtime.$Stthe$Mnnull$Mnvalue$St));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit18, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit33, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.setAndCoerceProperty$Ex(Lit114, Lit73, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("SpO2 : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit182, "join"), runtime.callYailPrimitive(strings.string$Mnappend, LList.list2(" %, BPM : ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit183, "join")), Lit184, "join"), Lit47);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        if (runtime.lookupGlobalVarInCurrentFormEnvironment(Lit34, runtime.$Stthe$Mnnull$Mnvalue$St) != Boolean.FALSE) {
            runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit189, "add items to list");
            runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit20, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit190, "add items to list");
            runtime.callComponentMethod(Lit57, Lit94, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit8, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.FALSE), Lit191);
        }
        runtime.setAndCoerceProperty$Ex(Lit93, Lit73, runtime.callYailPrimitive(DivideOp.modulo, LList.list2(runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.get$Mnproperty.apply2(Lit93, Lit73), Lit22), Lit192, "+"), Lit193), Lit194, "modulo"), Lit47);
        if (runtime.callYailPrimitive(strings.string$Eq$Qu, LList.list2(runtime.get$Mnproperty.apply2(Lit93, Lit73), Lit195), Lit196, "text=") == Boolean.FALSE) {
            return Values.empty;
        }
        runtime.setAndCoerceProperty$Ex(Lit92, Lit73, runtime.callYailPrimitive(DivideOp.modulo, LList.list2(runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.get$Mnproperty.apply2(Lit92, Lit73), Lit22), Lit197, "+"), Lit193), Lit198, "modulo"), Lit47);
        return runtime.callYailPrimitive(strings.string$Eq$Qu, LList.list2(runtime.get$Mnproperty.apply2(Lit92, Lit73), Lit195), Lit199, "text=") != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit91, Lit73, runtime.callYailPrimitive(DivideOp.modulo, LList.list2(runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.get$Mnproperty.apply2(Lit92, Lit73), Lit22), Lit200, "+"), Lit201), Lit202, "modulo"), Lit47) : Values.empty;
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
