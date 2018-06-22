package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.CloudDBJedisListener;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONArray;
import org.json.JSONException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.exceptions.JedisNoScriptException;

@DesignerComponent(category = ComponentCategory.EXPERIMENTAL, description = "Non-visible component allowing you to store data on a Internet connected database server (using Redis software). This allows the users of your App to share data with each other. By default data will be stored in a server maintained by MIT, however you can setup and run your own server. Set the \"RedisServer\" property and \"RedisPort\" Property to access your own server.", designerHelpDescription = "Non-visible component that communicates with CloudDB server to store and retrieve information.", iconName = "images/cloudDB.png", nonVisible = true, version = 1)
@UsesLibraries(libraries = "jedis.jar")
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.ACCESS_NETWORK_STATE")
public final class CloudDB extends AndroidNonvisibleComponent implements Component, OnClearListener, OnDestroyListener {
    private static final String APPEND_SCRIPT = "local key = KEYS[1];local toAppend = cjson.decode(ARGV[1]);local project = ARGV[2];local currentValue = redis.call('get', project .. \":\" .. key);local newTable;local subTable = {};local subTable1 = {};if (currentValue == false) then   newTable = {};else   newTable = cjson.decode(currentValue);  if not (type(newTable) == 'table') then     return error('You can only append to a list');  end end table.insert(newTable, toAppend);local newValue = cjson.encode(newTable);redis.call('set', project .. \":\" .. key, newValue);table.insert(subTable1, newValue);table.insert(subTable, key);table.insert(subTable, subTable1);redis.call(\"publish\", project, cjson.encode(subTable));return newValue;";
    private static final String APPEND_SCRIPT_SHA1 = "d6cc0f65b29878589f00564d52c8654967e9bcf8";
    private static final String BINFILE_DIR = "/AppInventorBinaries";
    private static final String COMODO_ROOT = "-----BEGIN CERTIFICATE-----\nMIIENjCCAx6gAwIBAgIBATANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJTRTEU\nMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFkZFRydXN0IEV4dGVybmFs\nIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBFeHRlcm5hbCBDQSBSb290\nMB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFowbzELMAkGA1UEBhMCU0Ux\nFDASBgNVBAoTC0FkZFRydXN0IEFCMSYwJAYDVQQLEx1BZGRUcnVzdCBFeHRlcm5h\nbCBUVFAgTmV0d29yazEiMCAGA1UEAxMZQWRkVHJ1c3QgRXh0ZXJuYWwgQ0EgUm9v\ndDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALf3GjPm8gAELTngTlvt\nH7xsD821+iO2zt6bETOXpClMfZOfvUq8k+0DGuOPz+VtUFrWlymUWoCwSXrbLpX9\nuMq/NzgtHj6RQa1wVsfwTz/oMp50ysiQVOnGXw94nZpAPA6sYapeFI+eh6FqUNzX\nmk6vBbOmcZSccbNQYArHE504B4YCqOmoaSYYkKtMsE8jqzpPhNjfzp/haW+710LX\na0Tkx63ubUFfclpxCDezeWWkWaCUN/cALw3CknLa0Dhy2xSoRcRdKn23tNbE7qzN\nE0S3ySvdQwAl+mG5aWpYIxG3pzOPVnVZ9c0p10a3CitlttNCbxWyuHv77+ldU9U0\nWicCAwEAAaOB3DCB2TAdBgNVHQ4EFgQUrb2YejS0Jvf6xCZU7wO94CTLVBowCwYD\nVR0PBAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wgZkGA1UdIwSBkTCBjoAUrb2YejS0\nJvf6xCZU7wO94CTLVBqhc6RxMG8xCzAJBgNVBAYTAlNFMRQwEgYDVQQKEwtBZGRU\ncnVzdCBBQjEmMCQGA1UECxMdQWRkVHJ1c3QgRXh0ZXJuYWwgVFRQIE5ldHdvcmsx\nIjAgBgNVBAMTGUFkZFRydXN0IEV4dGVybmFsIENBIFJvb3SCAQEwDQYJKoZIhvcN\nAQEFBQADggEBALCb4IUlwtYj4g+WBpKdQZic2YR5gdkeWxQHIzZlj7DYd7usQWxH\nYINRsPkyPef89iYTx4AWpb9a/IfPeHmJIZriTAcKhjW88t5RxNKWt9x+Tu5w/Rw5\n6wwCURQtjr0W4MHfRnXnJK3s9EK0hZNwEGe6nQY1ShjTK3rMUUKhemPR5ruhxSvC\nNr4TDea9Y355e6cJDUCrat2PisP29owaQgVR1EX1n6diIWgVIEM8med8vSTYqZEX\nc4g/VhsxOBi0cQ+azcgOno4uG+GMmIPLHzHxREzGBHNJdmAPx/i9F4BrLunMTA5a\nmnkPIAou1Z5jJh5VkpTYghdae9C8x49OhgQ=\n-----END CERTIFICATE-----\n";
    private static final String COMODO_USRTRUST = "-----BEGIN CERTIFICATE-----\nMIIFdzCCBF+gAwIBAgIQE+oocFv07O0MNmMJgGFDNjANBgkqhkiG9w0BAQwFADBv\nMQswCQYDVQQGEwJTRTEUMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFk\nZFRydXN0IEV4dGVybmFsIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBF\neHRlcm5hbCBDQSBSb290MB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFow\ngYgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpOZXcgSmVyc2V5MRQwEgYDVQQHEwtK\nZXJzZXkgQ2l0eTEeMBwGA1UEChMVVGhlIFVTRVJUUlVTVCBOZXR3b3JrMS4wLAYD\nVQQDEyVVU0VSVHJ1c3QgUlNBIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MIICIjAN\nBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgBJlFzYOw9sIs9CsVw127c0n00yt\nUINh4qogTQktZAnczomfzD2p7PbPwdzx07HWezcoEStH2jnGvDoZtF+mvX2do2NC\ntnbyqTsrkfjib9DsFiCQCT7i6HTJGLSR1GJk23+jBvGIGGqQIjy8/hPwhxR79uQf\njtTkUcYRZ0YIUcuGFFQ/vDP+fmyc/xadGL1RjjWmp2bIcmfbIWax1Jt4A8BQOujM\n8Ny8nkz+rwWWNR9XWrf/zvk9tyy29lTdyOcSOk2uTIq3XJq0tyA9yn8iNK5+O2hm\nAUTnAU5GU5szYPeUvlM3kHND8zLDU+/bqv50TmnHa4xgk97Exwzf4TKuzJM7UXiV\nZ4vuPVb+DNBpDxsP8yUmazNt925H+nND5X4OpWaxKXwyhGNVicQNwZNUMBkTrNN9\nN6frXTpsNVzbQdcS2qlJC9/YgIoJk2KOtWbPJYjNhLixP6Q5D9kCnusSTJV882sF\nqV4Wg8y4Z+LoE53MW4LTTLPtW//e5XOsIzstAL81VXQJSdhJWBp/kjbmUZIO8yZ9\nHE0XvMnsQybQv0FfQKlERPSZ51eHnlAfV1SoPv10Yy+xUGUJ5lhCLkMaTLTwJUdZ\n+gQek9QmRkpQgbLevni3/GcV4clXhB4PY9bpYrrWX1Uu6lzGKAgEJTm4Diup8kyX\nHAc/DVL17e8vgg8CAwEAAaOB9DCB8TAfBgNVHSMEGDAWgBStvZh6NLQm9/rEJlTv\nA73gJMtUGjAdBgNVHQ4EFgQUU3m/WqorSs9UgOHYm8Cd8rIDZsswDgYDVR0PAQH/\nBAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wEQYDVR0gBAowCDAGBgRVHSAAMEQGA1Ud\nHwQ9MDswOaA3oDWGM2h0dHA6Ly9jcmwudXNlcnRydXN0LmNvbS9BZGRUcnVzdEV4\ndGVybmFsQ0FSb290LmNybDA1BggrBgEFBQcBAQQpMCcwJQYIKwYBBQUHMAGGGWh0\ndHA6Ly9vY3NwLnVzZXJ0cnVzdC5jb20wDQYJKoZIhvcNAQEMBQADggEBAJNl9jeD\nlQ9ew4IcH9Z35zyKwKoJ8OkLJvHgwmp1ocd5yblSYMgpEg7wrQPWCcR23+WmgZWn\nRtqCV6mVksW2jwMibDN3wXsyF24HzloUQToFJBv2FAY7qCUkDrvMKnXduXBBP3zQ\nYzYhBx9G/2CkkeFnvN4ffhkUyWNnkepnB2u0j4vAbkN9w6GAbLIevFOFfdyQoaS8\nLe9Gclc1Bb+7RrtubTeZtv8jkpHGbkD4jylW6l/VXxRTrPBPYer3IsynVgviuDQf\nJtl7GQVoP7o81DgGotPmjw7jtHFtQELFhLRAlSv0ZaBIefYdgWOWnU914Ph85I6p\n0fKtirOMxyHNwu8=\n-----END CERTIFICATE-----\n";
    private static final boolean DEBUG = false;
    private static final String DST_ROOT_X3 = "-----BEGIN CERTIFICATE-----\nMIIDSjCCAjKgAwIBAgIQRK+wgNajJ7qJMDmGLvhAazANBgkqhkiG9w0BAQUFADA/\nMSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\nDkRTVCBSb290IENBIFgzMB4XDTAwMDkzMDIxMTIxOVoXDTIxMDkzMDE0MDExNVow\nPzEkMCIGA1UEChMbRGlnaXRhbCBTaWduYXR1cmUgVHJ1c3QgQ28uMRcwFQYDVQQD\nEw5EU1QgUm9vdCBDQSBYMzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\nAN+v6ZdQCINXtMxiZfaQguzH0yxrMMpb7NnDfcdAwRgUi+DoM3ZJKuM/IUmTrE4O\nrz5Iy2Xu/NMhD2XSKtkyj4zl93ewEnu1lcCJo6m67XMuegwGMoOifooUMM0RoOEq\nOLl5CjH9UL2AZd+3UWODyOKIYepLYYHsUmu5ouJLGiifSKOeDNoJjj4XLh7dIN9b\nxiqKqy69cK3FCxolkHRyxXtqqzTWMIn/5WgTe1QLyNau7Fqckh49ZLOMxt+/yUFw\n7BZy1SbsOFU5Q9D8/RhcQPGX69Wam40dutolucbY38EVAjqr2m7xPi71XAicPNaD\naeQQmxkqtilX4+U9m5/wAl0CAwEAAaNCMEAwDwYDVR0TAQH/BAUwAwEB/zAOBgNV\nHQ8BAf8EBAMCAQYwHQYDVR0OBBYEFMSnsaR7LHH62+FLkHX/xBVghYkQMA0GCSqG\nSIb3DQEBBQUAA4IBAQCjGiybFwBcqR7uKGY3Or+Dxz9LwwmglSBd49lZRNI+DT69\nikugdB/OEIKcdBodfpga3csTS7MgROSR6cz8faXbauX+5v3gTt23ADq1cEmv8uXr\nAvHRAosZy5Q6XkjEGB5YGV8eAlrwDPGxrancWYaLbumR9YbK+rlmM6pZW87ipxZz\nR8srzJmwN0jP41ZL9c8PDHIyh8bwRLtTcm1D9SZImlJnt1ir/md2cXjbDaJWFBM5\nJDGFoqgCWjBH4d1QB7wCCZAA62RjYJsWvIjJEubSfZGL+T0yjWW06XyxV3bqxbYo\nOb8VZRzI9neWagqNdwvYkQsEjgfbKbYK7p2CNTUQ\n-----END CERTIFICATE-----\n";
    private static final String LOG_TAG = "CloudDB";
    private static final String MIT_CA = "-----BEGIN CERTIFICATE-----\nMIIFXjCCBEagAwIBAgIJAMLfrRWIaHLbMA0GCSqGSIb3DQEBCwUAMIHPMQswCQYD\nVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJyaWRnZTEuMCwGA1UE\nChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5vbG9neTEZMBcGA1UE\nCxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWREQiBDZXJ0aWZpY2F0\nZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkqhkiG9w0BCQEWC2pp\nc0BtaXQuZWR1MB4XDTE3MTIyMjIyMzkyOVoXDTI3MTIyMDIyMzkyOVowgc8xCzAJ\nBgNVBAYTAlVTMQswCQYDVQQIEwJNQTESMBAGA1UEBxMJQ2FtYnJpZGdlMS4wLAYD\nVQQKEyVNYXNzYWNodXNldHRzIEluc3RpdHV0ZSBvZiBUZWNobm9sb2d5MRkwFwYD\nVQQLExBNSVQgQXBwIEludmVudG9yMSYwJAYDVQQDEx1DbG91ZERCIENlcnRpZmlj\nYXRlIEF1dGhvcml0eTEQMA4GA1UEKRMHRWFzeVJTQTEaMBgGCSqGSIb3DQEJARYL\namlzQG1pdC5lZHUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDHzI3D\nFobNDv2HTWlDdedmbxZIJYSqWlzdRJC3oVJgCubdAs46WJRqUxDRWft9UpYGMKkw\nmYN8mdPby2m5OJagdVIZgnguB71zIQkC8yMzd94FC3gldX5m7R014D/0fkpzvsSt\n6fsNectJT0k7gPELOH6t4u6AUbvIsEX0nNyRWsmA/ucXCsDBwXyBJxfOKIQ9tDI4\n/WfcKk9JDpeMF7RP0CIOtlAPotKIaPoY1W3eMIi/0riOt5vTFsB8pxhxAVy0cfGX\niHukdrAkAJixTgkyS7wzk22xOeXVnRIzAMGK5xHMDw/HRQGTrUGfIXHENV3u+3Ae\nL5/ZoQwyZTixmQNzAgMBAAGjggE5MIIBNTAdBgNVHQ4EFgQUZfMKQXqtC5UJGFrZ\ngZE1nmlx+t8wggEEBgNVHSMEgfwwgfmAFGXzCkF6rQuVCRha2YGRNZ5pcfrfoYHV\npIHSMIHPMQswCQYDVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJy\naWRnZTEuMCwGA1UEChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5v\nbG9neTEZMBcGA1UECxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWRE\nQiBDZXJ0aWZpY2F0ZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkq\nhkiG9w0BCQEWC2ppc0BtaXQuZWR1ggkAwt+tFYhoctswDAYDVR0TBAUwAwEB/zAN\nBgkqhkiG9w0BAQsFAAOCAQEAIkKr3eIvwZO6a1Jsh3qXwveVnrqwxYvLw2IhTwNT\n/P6C5jbRnzUuDuzg5sEIpbBo/Bp3qIp7G5cdVOkIrqO7uCp6Kyc7d9lPsEe/cbF4\naNwNmdWroRN1y0tuMU6+z7frd5pOeAZP9E/DM/0Uaz4yVzwnlvZUttaLymyMhH54\nisGQKbAqHDFtKZvb6DxsHzrO2YgeaBAtjeVhPWiv8BhzbOo9+hhZvYHYtoM2W+Ze\nDHuvv0v+qouphftDKVBp16N8Pk5WgabTXzV6VcNee92iwbWYDEv06+S3AF/q2TBe\nxxXtAa5ywbp6IRF37QuQChcYnOx7zIylYI1PIENfQFC2BA==\n-----END CERTIFICATE-----\n";
    private static final String POP_FIRST_SCRIPT = "local key = KEYS[1];local project = ARGV[1];local currentValue = redis.call('get', project .. \":\" .. key);local decodedValue = cjson.decode(currentValue);local subTable = {};local subTable1 = {};if (type(decodedValue) == 'table') then   local removedValue = table.remove(decodedValue, 1);  local newValue = cjson.encode(decodedValue);  redis.call('set', project .. \":\" .. key, newValue);  table.insert(subTable, key);  table.insert(subTable1, newValue);  table.insert(subTable, subTable1);  redis.call(\"publish\", project, cjson.encode(subTable));  return cjson.encode(removedValue);else   return error('You can only remove elements from a list');end";
    private static final String POP_FIRST_SCRIPT_SHA1 = "ed4cb4717d157f447848fe03524da24e461028e1";
    private static final String SET_SUB_SCRIPT = "local key = KEYS[1];local value = ARGV[1];local topublish = cjson.decode(ARGV[2]);local project = ARGV[3];local newtable = {};table.insert(newtable, key);table.insert(newtable, topublish);redis.call(\"publish\", project, cjson.encode(newtable));return redis.call('set', project .. \":\" .. key, value);";
    private static final String SET_SUB_SCRIPT_SHA1 = "765978e4c340012f50733280368a0ccc4a14dfb7";
    private Jedis INSTANCE = null;
    private SSLSocketFactory SslSockFactory = null;
    private final Activity activity;
    private Handler androidUIHandler = new Handler();
    private volatile ExecutorService background = Executors.newSingleThreadExecutor();
    private ConnectivityManager cm;
    private volatile CloudDBJedisListener currentListener;
    private volatile boolean dead = false;
    private String defaultRedisServer = null;
    private boolean importProject = false;
    private boolean isPublic = false;
    private volatile boolean listenerRunning = false;
    private String projectID = "";
    private volatile int redisPort;
    private volatile String redisServer = "DEFAULT";
    private volatile boolean shutdown = false;
    private final List<storedValue> storeQueue = Collections.synchronizedList(new ArrayList());
    private String token = "";
    private boolean useDefault = true;
    private volatile boolean useSSL = true;

    class C01561 extends Thread {
        C01561() {
        }

        public void run() {
            Jedis jedis = CloudDB.this.getJedis(true);
            if (jedis != null) {
                try {
                    CloudDB.this.currentListener = new CloudDBJedisListener(CloudDB.this);
                    jedis.subscribe(CloudDB.this.currentListener, new String[]{CloudDB.this.projectID});
                } catch (Exception e) {
                    Log.e(CloudDB.LOG_TAG, "Error in listener thread", e);
                    try {
                        jedis.close();
                    } catch (Exception e2) {
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e3) {
                    }
                }
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e4) {
                }
            }
            CloudDB.this.listenerRunning = false;
            if (!CloudDB.this.dead && !CloudDB.this.shutdown) {
                CloudDB.this.startListener();
            }
        }
    }

    class C01572 implements Runnable {
        C01572() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r17 = this;
            r5 = 0;
            r3 = 0;
            r4 = 0;
        L_0x0003:
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ Exception -> 0x005d }
            r11 = r10.storeQueue;	 Catch:{ Exception -> 0x005d }
            monitor-enter(r11);	 Catch:{ Exception -> 0x005d }
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ all -> 0x005a }
            r10 = r10.storeQueue;	 Catch:{ all -> 0x005a }
            r6 = r10.size();	 Catch:{ all -> 0x005a }
            if (r6 != 0) goto L_0x004a;
        L_0x001a:
            r9 = 0;
        L_0x001b:
            monitor-exit(r11);	 Catch:{ all -> 0x005a }
            if (r9 != 0) goto L_0x007b;
        L_0x001e:
            if (r3 == 0) goto L_0x0049;
        L_0x0020:
            r2 = r5.toString();	 Catch:{ JedisException -> 0x0066 }
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ JedisException -> 0x0066 }
            r11 = "local key = KEYS[1];local value = ARGV[1];local topublish = cjson.decode(ARGV[2]);local project = ARGV[3];local newtable = {};table.insert(newtable, key);table.insert(newtable, topublish);redis.call(\"publish\", project, cjson.encode(newtable));return redis.call('set', project .. \":\" .. key, value);";
            r12 = "765978e4c340012f50733280368a0ccc4a14dfb7";
            r13 = 1;
            r14 = 4;
            r14 = new java.lang.String[r14];	 Catch:{ JedisException -> 0x0066 }
            r15 = 0;
            r14[r15] = r3;	 Catch:{ JedisException -> 0x0066 }
            r15 = 1;
            r14[r15] = r4;	 Catch:{ JedisException -> 0x0066 }
            r15 = 2;
            r14[r15] = r2;	 Catch:{ JedisException -> 0x0066 }
            r15 = 3;
            r0 = r17;
            r0 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ JedisException -> 0x0066 }
            r16 = r0;
            r16 = r16.projectID;	 Catch:{ JedisException -> 0x0066 }
            r14[r15] = r16;	 Catch:{ JedisException -> 0x0066 }
            r10.jEval(r11, r12, r13, r14);	 Catch:{ JedisException -> 0x0066 }
        L_0x0049:
            return;
        L_0x004a:
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ all -> 0x005a }
            r10 = r10.storeQueue;	 Catch:{ all -> 0x005a }
            r12 = 0;
            r9 = r10.remove(r12);	 Catch:{ all -> 0x005a }
            r9 = (com.google.appinventor.components.runtime.CloudDB.storedValue) r9;	 Catch:{ all -> 0x005a }
            goto L_0x001b;
        L_0x005a:
            r10 = move-exception;
            monitor-exit(r11);	 Catch:{ all -> 0x005a }
            throw r10;	 Catch:{ Exception -> 0x005d }
        L_0x005d:
            r1 = move-exception;
            r10 = "CloudDB";
            r11 = "Exception in store worker!";
            android.util.Log.e(r10, r11, r1);
            goto L_0x0049;
        L_0x0066:
            r1 = move-exception;
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ Exception -> 0x005d }
            r11 = r1.getMessage();	 Catch:{ Exception -> 0x005d }
            r10.CloudDBError(r11);	 Catch:{ Exception -> 0x005d }
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ Exception -> 0x005d }
            r11 = 1;
            r10.flushJedis(r11);	 Catch:{ Exception -> 0x005d }
            goto L_0x0049;
        L_0x007b:
            r7 = r9.getTag();	 Catch:{ Exception -> 0x005d }
            r8 = r9.getValueList();	 Catch:{ Exception -> 0x005d }
            if (r7 == 0) goto L_0x0087;
        L_0x0085:
            if (r8 != 0) goto L_0x0087;
        L_0x0087:
            if (r3 != 0) goto L_0x0092;
        L_0x0089:
            r3 = r7;
            r5 = r8;
            r10 = 0;
            r4 = r8.getString(r10);	 Catch:{ Exception -> 0x005d }
            goto L_0x0003;
        L_0x0092:
            r10 = r3.equals(r7);	 Catch:{ Exception -> 0x005d }
            if (r10 == 0) goto L_0x00a2;
        L_0x0098:
            r10 = 0;
            r4 = r8.getString(r10);	 Catch:{ Exception -> 0x005d }
            r5.put(r4);	 Catch:{ Exception -> 0x005d }
            goto L_0x0003;
        L_0x00a2:
            r2 = r5.toString();	 Catch:{ JedisException -> 0x00d4 }
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ JedisException -> 0x00d4 }
            r11 = "local key = KEYS[1];local value = ARGV[1];local topublish = cjson.decode(ARGV[2]);local project = ARGV[3];local newtable = {};table.insert(newtable, key);table.insert(newtable, topublish);redis.call(\"publish\", project, cjson.encode(newtable));return redis.call('set', project .. \":\" .. key, value);";
            r12 = "765978e4c340012f50733280368a0ccc4a14dfb7";
            r13 = 1;
            r14 = 4;
            r14 = new java.lang.String[r14];	 Catch:{ JedisException -> 0x00d4 }
            r15 = 0;
            r14[r15] = r3;	 Catch:{ JedisException -> 0x00d4 }
            r15 = 1;
            r14[r15] = r4;	 Catch:{ JedisException -> 0x00d4 }
            r15 = 2;
            r14[r15] = r2;	 Catch:{ JedisException -> 0x00d4 }
            r15 = 3;
            r0 = r17;
            r0 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ JedisException -> 0x00d4 }
            r16 = r0;
            r16 = r16.projectID;	 Catch:{ JedisException -> 0x00d4 }
            r14[r15] = r16;	 Catch:{ JedisException -> 0x00d4 }
            r10.jEval(r11, r12, r13, r14);	 Catch:{ JedisException -> 0x00d4 }
            r3 = r7;
            r5 = r8;
            r10 = 0;
            r4 = r8.getString(r10);	 Catch:{ Exception -> 0x005d }
            goto L_0x0003;
        L_0x00d4:
            r1 = move-exception;
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ Exception -> 0x005d }
            r11 = r1.getMessage();	 Catch:{ Exception -> 0x005d }
            r10.CloudDBError(r11);	 Catch:{ Exception -> 0x005d }
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ Exception -> 0x005d }
            r11 = 1;
            r10.flushJedis(r11);	 Catch:{ Exception -> 0x005d }
            r0 = r17;
            r10 = com.google.appinventor.components.runtime.CloudDB.this;	 Catch:{ Exception -> 0x005d }
            r10 = r10.storeQueue;	 Catch:{ Exception -> 0x005d }
            r10.clear();	 Catch:{ Exception -> 0x005d }
            goto L_0x0049;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.CloudDB.2.run():void");
        }
    }

    class C01658 implements Runnable {
        C01658() {
        }

        public void run() {
            try {
                final List<String> listValue = new ArrayList(CloudDB.this.getJedis().keys(CloudDB.this.projectID + ":*"));
                for (int i = 0; i < listValue.size(); i++) {
                    listValue.set(i, ((String) listValue.get(i)).substring((CloudDB.this.projectID + ":").length()));
                }
                CloudDB.this.androidUIHandler.post(new Runnable() {
                    public void run() {
                        CloudDB.this.TagList(listValue);
                    }
                });
            } catch (JedisException e) {
                CloudDB.this.CloudDBError(e.getMessage());
                CloudDB.this.flushJedis(true);
            }
        }
    }

    private static class storedValue {
        private String tag;
        private JSONArray valueList;

        storedValue(String tag, JSONArray valueList) {
            this.tag = tag;
            this.valueList = valueList;
        }

        public String getTag() {
            return this.tag;
        }

        public JSONArray getValueList() {
            return this.valueList;
        }
    }

    public CloudDB(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
        this.projectID = "";
        this.token = "";
        this.redisPort = 6381;
        this.cm = (ConnectivityManager) this.form.$context().getSystemService("connectivity");
    }

    public void Initialize() {
        if (this.currentListener == null) {
            startListener();
        }
        this.form.registerForOnClear(this);
        this.form.registerForOnDestroy(this);
    }

    private void stopListener() {
        if (this.currentListener != null) {
            this.currentListener.terminate();
            this.currentListener = null;
            this.listenerRunning = false;
        }
    }

    public void onClear() {
        this.shutdown = true;
        flushJedis(false);
    }

    public void onDestroy() {
        onClear();
    }

    private synchronized void startListener() {
        if (!this.listenerRunning) {
            this.listenerRunning = true;
            new C01561().start();
        }
    }

    @DesignerProperty(defaultValue = "DEFAULT", editorType = "string")
    public void RedisServer(String servername) {
        if (!servername.equals("DEFAULT")) {
            this.useDefault = false;
            if (!servername.equals(this.redisServer)) {
                this.redisServer = servername;
                flushJedis(true);
            }
        } else if (!this.useDefault) {
            this.useDefault = true;
            if (this.defaultRedisServer != null) {
                this.redisServer = this.defaultRedisServer;
            }
            flushJedis(true);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The Redis Server to use to store data. A setting of \"DEFAULT\" means that the MIT server will be used.")
    public String RedisServer() {
        if (this.redisServer.equals(this.defaultRedisServer)) {
            return "DEFAULT";
        }
        return this.redisServer;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The Default Redis Server to use.", userVisible = false)
    public void DefaultRedisServer(String server) {
        this.defaultRedisServer = server;
        if (this.useDefault) {
            this.redisServer = server;
        }
    }

    @DesignerProperty(defaultValue = "6381", editorType = "integer")
    public void RedisPort(int port) {
        if (port != this.redisPort) {
            this.redisPort = port;
            flushJedis(true);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The Redis Server port to use. Defaults to 6381")
    public int RedisPort() {
        return this.redisPort;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets the ProjectID for this CloudDB project.")
    public String ProjectID() {
        checkProjectIDNotBlank();
        return this.projectID;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    public void ProjectID(String id) {
        if (!this.projectID.equals(id)) {
            this.projectID = id;
        }
        if (this.projectID.equals("")) {
            throw new RuntimeException("CloudDB ProjectID property cannot be blank.");
        }
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    public void Token(String authToken) {
        if (!this.token.equals(authToken)) {
            this.token = authToken;
        }
        if (this.token.equals("")) {
            throw new RuntimeException("CloudDB Token property cannot be blank.");
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This field contains the authentication token used to login to the backed Redis server. For the \"DEFAULT\" server, do not edit this value, the system will fill it in for you. A system administrator may also provide a special value to you which can be used to share data between multiple projects from multiple people. If using your own Redis server, set a password in the server's config and enter it here.", userVisible = false)
    public String Token() {
        checkProjectIDNotBlank();
        return this.token;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    public void UseSSL(boolean useSSL) {
        if (this.useSSL != useSSL) {
            this.useSSL = useSSL;
            flushJedis(true);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set to true to use SSL to talk to CloudDB/Redis server. This should be set to True for the \"DEFAULT\" server.", userVisible = false)
    public boolean UseSSL() {
        return this.useSSL;
    }

    @SimpleFunction(description = "Store a value at a tag.")
    public void StoreValue(String tag, Object valueToStore) {
        String value;
        boolean isConnected = false;
        checkProjectIDNotBlank();
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isConnected = true;
        }
        if (valueToStore != null) {
            try {
                String strval = valueToStore.toString();
                if (strval.startsWith("file:///") || strval.startsWith("/storage")) {
                    value = JsonUtil.getJsonRepresentation(readFile(strval));
                } else {
                    value = JsonUtil.getJsonRepresentation(valueToStore);
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        }
        value = "";
        if (isConnected) {
            synchronized (this.storeQueue) {
                boolean kickit = false;
                if (this.storeQueue.size() == 0) {
                    kickit = true;
                }
                JSONArray valueList = new JSONArray();
                try {
                    valueList.put(0, value);
                    this.storeQueue.add(new storedValue(tag, valueList));
                    if (kickit) {
                        this.background.submit(new C01572());
                    }
                } catch (JSONException e2) {
                    throw new YailRuntimeError("JSON Error putting value.", "value is not convertable");
                }
            }
            return;
        }
        CloudDBError("Cannot store values off-line.");
    }

    @SimpleFunction(description = "Get the Value for a tag, doesn't return the value but will cause a GotValue event to fire when the value is looked up.")
    public void GetValue(final String tag, final Object valueIfTagNotThere) {
        checkProjectIDNotBlank();
        final AtomicReference<Object> value = new AtomicReference();
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (isConnected) {
            this.background.submit(new Runnable() {

                class C01581 implements Runnable {
                    C01581() {
                    }

                    public void run() {
                        CloudDB.this.GotValue(tag, value.get());
                    }
                }

                public void run() {
                    try {
                        String returnValue = CloudDB.this.getJedis().get(CloudDB.this.projectID + ":" + tag);
                        if (returnValue != null) {
                            String val = CloudDB.this.getJsonRepresenationIfValueFileName(returnValue);
                            if (val != null) {
                                value.set(val);
                            } else {
                                value.set(returnValue);
                            }
                        } else {
                            value.set(JsonUtil.getJsonRepresentation(valueIfTagNotThere));
                        }
                        CloudDB.this.androidUIHandler.post(new C01581());
                    } catch (JSONException e) {
                        CloudDB.this.CloudDBError("JSON conversion error for " + tag);
                    } catch (NullPointerException e2) {
                        CloudDB.this.CloudDBError("System Error getting tag " + tag);
                        CloudDB.this.flushJedis(true);
                    } catch (JedisException e3) {
                        Log.e(CloudDB.LOG_TAG, "Exception in GetValue", e3);
                        CloudDB.this.CloudDBError(e3.getMessage());
                        CloudDB.this.flushJedis(true);
                    }
                }
            });
        } else {
            CloudDBError("Cannot fetch variables while off-line.");
        }
    }

    @SimpleFunction(description = "returns True if we are on the network and will likely be able to connect to the CloudDB server.")
    public boolean CloudConnected() {
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @SimpleEvent(description = "Event triggered by the \"RemoveFirstFromList\" function. The argument \"value\" is the object that was the first in the list, and which is now removed.")
    public void FirstRemoved(Object value) {
        checkProjectIDNotBlank();
        if (value != null) {
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "error while converting to JSON...", e);
                return;
            }
        }
        final Object sValue = value;
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(CloudDB.this, "FirstRemoved", sValue);
            }
        });
    }

    @SimpleFunction(description = "Return the first element of a list and atomically remove it. If two devices use this function simultaneously, one will get the first element and the the other will get the second element, or an error if there is no available element. When the element is available, the \"FirstRemoved\" event will be triggered.")
    public void RemoveFirstFromList(String tag) {
        checkProjectIDNotBlank();
        final String key = tag;
        this.background.submit(new Runnable() {
            public void run() {
                Jedis jedis = CloudDB.this.getJedis();
                try {
                    CloudDB.this.FirstRemoved(CloudDB.this.jEval(CloudDB.POP_FIRST_SCRIPT, CloudDB.POP_FIRST_SCRIPT_SHA1, 1, key, CloudDB.this.projectID));
                } catch (JedisException e) {
                    CloudDB.this.CloudDBError(e.getMessage());
                    CloudDB.this.flushJedis(true);
                }
            }
        });
    }

    @SimpleFunction(description = "Append a value to the end of a list atomically. If two devices use this function simultaneously, both will be appended and no data lost.")
    public void AppendValueToList(String tag, Object itemToAdd) {
        checkProjectIDNotBlank();
        String itemObject = new Object();
        if (itemToAdd != null) {
            try {
                itemObject = JsonUtil.getJsonRepresentation(itemToAdd);
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        }
        final String item = itemObject;
        final String key = tag;
        this.background.submit(new Runnable() {
            public void run() {
                Jedis jedis = CloudDB.this.getJedis();
                try {
                    CloudDB.this.jEval(CloudDB.APPEND_SCRIPT, CloudDB.APPEND_SCRIPT_SHA1, 1, key, item, CloudDB.this.projectID);
                } catch (JedisException e) {
                    CloudDB.this.CloudDBError(e.getMessage());
                    CloudDB.this.flushJedis(true);
                }
            }
        });
    }

    @SimpleEvent
    public void GotValue(String tag, Object value) {
        checkProjectIDNotBlank();
        if (value == null) {
            CloudDBError("Trouble getting " + tag + " from the server.");
            return;
        }
        if (value != null) {
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value);
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Retrieval Error.");
            }
        }
        EventDispatcher.dispatchEvent(this, "GotValue", tag, value);
    }

    @SimpleFunction(description = "Remove the tag from CloudDB")
    public void ClearTag(final String tag) {
        checkProjectIDNotBlank();
        this.background.submit(new Runnable() {
            public void run() {
                try {
                    CloudDB.this.getJedis().del(CloudDB.this.projectID + ":" + tag);
                } catch (Exception e) {
                    CloudDB.this.CloudDBError(e.getMessage());
                    CloudDB.this.flushJedis(true);
                }
            }
        });
    }

    @SimpleFunction(description = "Get the list of tags for this application. When complete a \"TagList\" event will be triggered with the list of known tags.")
    public void GetTagList() {
        checkProjectIDNotBlank();
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (isConnected) {
            this.background.submit(new C01658());
        } else {
            CloudDBError("Not connected to the Internet, cannot list tags");
        }
    }

    @SimpleEvent(description = "Event triggered when we have received the list of known tags. Used with the \"GetTagList\" Function.")
    public void TagList(List<String> value) {
        checkProjectIDNotBlank();
        EventDispatcher.dispatchEvent(this, "TagList", value);
    }

    @SimpleEvent
    public void DataChanged(final String tag, Object value) {
        Object tagValue;
        String tagValue2 = "";
        if (value != null) {
            try {
                if (value instanceof String) {
                    tagValue = JsonUtil.getObjectFromJson((String) value);
                    this.androidUIHandler.post(new Runnable() {
                        public void run() {
                            EventDispatcher.dispatchEvent(CloudDB.this, "DataChanged", tag, tagValue);
                        }
                    });
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Retrieval Error.");
            }
        }
        String tagValue3 = tagValue2;
        this.androidUIHandler.post(/* anonymous class already generated */);
    }

    @SimpleEvent(description = "Indicates that an error occurred while communicating with the CloudDB Redis server.")
    public void CloudDBError(final String message) {
        Log.e(LOG_TAG, message);
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                if (!EventDispatcher.dispatchEvent(CloudDB.this, "CloudDBError", message)) {
                    new Notifier(CloudDB.this.form).ShowAlert("CloudDBError: " + message);
                }
            }
        });
    }

    private void checkProjectIDNotBlank() {
        if (this.projectID.equals("")) {
            throw new RuntimeException("CloudDB ProjectID property cannot be blank.");
        } else if (this.token.equals("")) {
            throw new RuntimeException("CloudDB Token property cannot be blank");
        }
    }

    public Jedis getJedis(boolean createNew) {
        if (this.dead) {
            return null;
        }
        try {
            Jedis jedis;
            if (this.useSSL) {
                ensureSslSockFactory();
                jedis = new Jedis(this.redisServer, this.redisPort, true, this.SslSockFactory, null, null);
            } else {
                jedis = new Jedis(this.redisServer, this.redisPort, false);
            }
            if (this.token.substring(0, 1).equals("%")) {
                jedis.auth(this.token.substring(1));
                return jedis;
            }
            jedis.auth(this.token);
            return jedis;
        } catch (JedisConnectionException e) {
            Log.e(LOG_TAG, "in getJedis()", e);
            CloudDBError(e.getMessage());
            return null;
        } catch (JedisDataException e2) {
            Log.e(LOG_TAG, "in getJedis()", e2);
            CloudDBError(e2.getMessage() + " CloudDB disabled, restart to re-enable.");
            this.dead = true;
            return null;
        }
    }

    public synchronized Jedis getJedis() {
        if (this.INSTANCE == null) {
            this.INSTANCE = getJedis(true);
        }
        return this.INSTANCE;
    }

    private void flushJedis(boolean restartListener) {
        if (this.INSTANCE != null) {
            try {
                this.INSTANCE.close();
            } catch (Exception e) {
            }
            this.INSTANCE = null;
            this.androidUIHandler.post(new Runnable() {
                public void run() {
                    List<Runnable> tasks = CloudDB.this.background.shutdownNow();
                    CloudDB.this.background = Executors.newSingleThreadExecutor();
                }
            });
            stopListener();
            if (restartListener) {
                startListener();
            }
        }
    }

    private YailList readFile(String fileName) {
        String originalFileName = fileName;
        try {
            if (fileName.startsWith("file://")) {
                fileName = fileName.substring(7);
            }
            if (fileName.startsWith("/")) {
                File inputFile = new File(fileName);
                if (inputFile.isFile()) {
                    String extension = getFileExtension(fileName);
                    FileInputStream inputStream = new FileInputStream(inputFile);
                    byte[] content = new byte[((int) inputFile.length())];
                    if (((long) inputStream.read(content)) != inputFile.length()) {
                        throw new YailRuntimeError("Did not read complete file!", "Read");
                    }
                    inputStream.close();
                    String encodedContent = Base64.encodeToString(content, 0);
                    return YailList.makeList(new Object[]{"." + extension, encodedContent});
                }
                throw new YailRuntimeError("Cannot find file", "ReadFrom");
            }
            throw new YailRuntimeError("Invalid fileName, was " + originalFileName, "ReadFrom");
        } catch (FileNotFoundException e) {
            throw new YailRuntimeError(e.getMessage(), "Read");
        } catch (IOException e2) {
            throw new YailRuntimeError(e2.getMessage(), "Read");
        }
    }

    private String writeFile(String input, String fileExtension) {
        try {
            if (fileExtension.length() != 3) {
                throw new YailRuntimeError("File Extension must be three characters", "Write Error");
            }
            byte[] content = Base64.decode(input, 0);
            File destDirectory = new File(Environment.getExternalStorageDirectory() + BINFILE_DIR);
            destDirectory.mkdirs();
            File dest = File.createTempFile("BinFile", "." + fileExtension, destDirectory);
            FileOutputStream outStream = new FileOutputStream(dest);
            outStream.write(content);
            outStream.close();
            String retval = dest.toURI().toASCIIString();
            trimDirectory(20, destDirectory);
            return retval;
        } catch (Exception e) {
            throw new YailRuntimeError(e.getMessage(), "Write");
        }
    }

    private void trimDirectory(int maxSavedFiles, File directory) {
        File[] files = directory.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
            }
        });
        int excess = files.length - maxSavedFiles;
        for (int i = 0; i < excess; i++) {
            files[i].delete();
        }
    }

    private String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    private String getJsonRepresenationIfValueFileName(String value) {
        try {
            List<String> valueList = JsonUtil.getStringListFromJsonArray(new JSONArray(value));
            if (valueList.size() != 2) {
                return null;
            }
            if (!((String) valueList.get(0)).startsWith(".")) {
                return null;
            }
            String filename = writeFile((String) valueList.get(1), ((String) valueList.get(0)).substring(1));
            System.out.println("Filename Written: " + filename);
            return JsonUtil.getJsonRepresentation(filename.replace("file:/", "file:///"));
        } catch (JSONException e) {
            return null;
        }
    }

    public ExecutorService getBackground() {
        return this.background;
    }

    public Object jEval(String script, String scriptsha1, int argcount, String... args) throws JedisException {
        Jedis jedis = getJedis();
        try {
            return jedis.evalsha(scriptsha1, argcount, args);
        } catch (JedisNoScriptException e) {
            return jedis.eval(script, argcount, args);
        }
    }

    private synchronized void ensureSslSockFactory() {
        if (this.SslSockFactory == null) {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream caInput = new ByteArrayInputStream(COMODO_ROOT.getBytes("UTF-8"));
                Certificate ca = cf.generateCertificate(caInput);
                caInput.close();
                caInput = new ByteArrayInputStream(COMODO_USRTRUST.getBytes("UTF-8"));
                Certificate inter = cf.generateCertificate(caInput);
                caInput.close();
                caInput = new ByteArrayInputStream(DST_ROOT_X3.getBytes("UTF-8"));
                Certificate dstx3 = cf.generateCertificate(caInput);
                caInput.close();
                caInput = new ByteArrayInputStream(MIT_CA.getBytes("UTF-8"));
                Certificate mitca = cf.generateCertificate(caInput);
                caInput.close();
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                int count = 1;
                for (X509Certificate cert : getSystemCertificates()) {
                    keyStore.setCertificateEntry("root" + count, cert);
                    count++;
                }
                keyStore.setCertificateEntry("comodo", ca);
                keyStore.setCertificateEntry("inter", inter);
                keyStore.setCertificateEntry("dstx3", dstx3);
                keyStore.setCertificateEntry("mitca", mitca);
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, tmf.getTrustManagers(), null);
                this.SslSockFactory = ctx.getSocketFactory();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not setup SSL Trust Store for CloudDB", e);
                throw new YailRuntimeError("Could Not setup SSL Trust Store for CloudDB: ", e.getMessage());
            }
        }
    }

    private X509Certificate[] getSystemCertificates() {
        try {
            TrustManagerFactory otmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            otmf.init((KeyStore) null);
            return otmf.getTrustManagers()[0].getAcceptedIssuers();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Getting System Certificates", e);
            return new X509Certificate[0];
        }
    }
}
