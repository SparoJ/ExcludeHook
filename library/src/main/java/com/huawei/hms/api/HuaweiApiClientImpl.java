//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.huawei.hms.api;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.api.Api.ApiOptions;
import com.huawei.hms.api.HuaweiApiClient.ConnectionCallbacks;
import com.huawei.hms.api.HuaweiApiClient.OnConnectionFailedListener;
import com.huawei.hms.c.g;
import com.huawei.hms.c.j;
import com.huawei.hms.core.aidl.RequestHeader;
import com.huawei.hms.core.aidl.e;
import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.a.a;
import com.huawei.hms.support.api.client.BundleResult;
import com.huawei.hms.support.api.client.InnerApiClient;
import com.huawei.hms.support.api.client.InnerPendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.auth.PermissionInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.core.CheckConnectInfo;
import com.huawei.hms.support.api.entity.core.ConnectInfo;
import com.huawei.hms.support.api.entity.core.ConnectResp;
import com.huawei.hms.support.api.entity.core.DisconnectInfo;
import com.huawei.hms.support.api.entity.core.DisconnectResp;
import com.huawei.hms.support.api.entity.core.JosGetNoticeResp;
import com.huawei.hms.update.provider.UpdateProvider;
import com.huawei.updatesdk.UpdateSdkAPI;
import com.huawei.updatesdk.service.otaupdate.CheckUpdateCallBack;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HuaweiApiClientImpl extends HuaweiApiClient implements ServiceConnection, InnerApiClient {
  private final Context a;
  private String b;
  private String c;
  private final String d;
  private volatile e e;
  private String f;
  private WeakReference<Activity> g;
  private WeakReference<Activity> h;
  private boolean i = false;
  private AtomicInteger j = new AtomicInteger(1);
  private List<Scope> k;
  private List<PermissionInfo> l;
  private Map<Api<?>, ApiOptions> m;
  private SubAppInfo n;
  private long o = 0L;
  private int p = 0;
  private ConnectionCallbacks q;
  private OnConnectionFailedListener r;
  private Handler s = null;
  private static final Object t = new Object();
  private CheckUpdatelistener u = null;
  private CheckUpdateCallBack v = new f(this);

  public HuaweiApiClientImpl(Context var1) {
    this.a = var1;
    this.d = com.huawei.hms.c.j.a(var1);
    this.b = this.d;
    this.c = com.huawei.hms.c.j.c(var1);
  }

  public Context getContext() {
    return this.a;
  }

  public String getPackageName() {
    return this.a.getPackageName();
  }

  public String getAppID() {
    return this.b;
  }

  public String getCpID() {
    return this.c;
  }

  public String getTransportName() {
    return IPCTransport.class.getName();
  }

  public final SubAppInfo getSubAppInfo() {
    return this.n;
  }

  public List<Scope> getScopes() {
    return this.k;
  }

  public List<PermissionInfo> getPermissionInfos() {
    return this.l;
  }

  public Map<Api<?>, ApiOptions> getApiMap() {
    return this.m;
  }

  public e getService() {
    return this.e;
  }

  public String getSessionId() {
    return this.f;
  }

  public void setHasShowNotice(boolean var1) {
    this.i = var1;
  }

  public void connect(Activity var1) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "====== HMSSDK version: 20603301 ======");
    int var2 = this.j.get();
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter connect, Connection Status: " + var2);
    if (var2 != 3 && var2 != 5 && var2 != 2 && var2 != 4) {
      com.huawei.hms.support.log.a.a("HuaweiApiClientImpl", "connect activity:" + var1);
      this.g = new WeakReference(var1);
      this.h = new WeakReference(var1);
      this.b = TextUtils.isEmpty(this.d) ? com.huawei.hms.c.j.a(this.a) : this.d;
      int var3 = this.a();
      com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "connect minVersion:" + var3);
      HuaweiApiAvailability.setServicesVersionCode(var3);
      int var4 = HuaweiMobileServicesUtil.isHuaweiMobileServicesAvailable(this.a, var3);
      com.huawei.hms.support.log.a.a("HuaweiApiClientImpl", "In connect, isHuaweiMobileServicesAvailable result: " + var4);
      g var5 = new g(this.a);
      this.p = var5.b("com.huawei.hwid");
      if (var4 == 0) {
        this.a(5);
        if (!this.d()) {
          this.a(1);
          com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "In connect, bind core service fail");
          if (this.r != null) {
            this.r.onConnectionFailed(new ConnectionResult(6));
          }
        } else {
          this.e();
        }
      } else if (this.r != null) {
        this.r.onConnectionFailed(new ConnectionResult(var4));
      }

    }
  }

  private int a() {
    int var1 = com.huawei.hms.c.j.b(this.a);
    if (var1 != 0 && var1 >= 20503000) {
      return var1;
    } else {
      int var2 = this.b();
      if (this.c()) {
        if (var2 < 20503000) {
          var2 = 20503000;
        }

        return var2;
      } else {
        if (var2 < 20600000) {
          var2 = 20600000;
        }

        return var2;
      }
    }
  }

  private int b() {
    Map var1 = this.getApiMap();
    if (var1 == null) {
      return 0;
    } else {
      Set var2 = var1.keySet();
      int var3 = 0;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
        Api var5 = (Api)var4.next();
        String var6 = var5.getApiName();
        if (!TextUtils.isEmpty(var6)) {
          Map var7 = HuaweiApiAvailability.getApiMap();
          Integer var8 = (Integer)var7.get(var6);
          if (var8 != null) {
            int var9 = var8;
            if (var9 > var3) {
              var3 = var9;
            }
          }
        }
      }

      return var3;
    }
  }

  private boolean c() {
    if (this.m != null) {
      Iterator var1 = this.m.keySet().iterator();

      while(var1.hasNext()) {
        Api var2 = (Api)var1.next();
        if ("HuaweiGame.API".equals(var2.getApiName())) {
          return true;
        }
      }
    }

    return false;
  }

  private void a(int var1) {
    this.j.set(var1);
  }

  private boolean d() {
    Intent var1 = new Intent("com.huawei.hms.core.aidlservice");
    var1.setPackage("com.huawei.hwid");
    return this.a.bindService(var1, this, 1);
  }

  private void e() {
    Object var1 = t;
    synchronized(t) {
      if (this.s != null) {
        this.s.removeMessages(2);
      } else {
        this.s = new Handler(Looper.getMainLooper(), new com.huawei.hms.api.e(this));
      }

      this.s.sendEmptyMessageDelayed(2, 3000L);
    }
  }

  private void f() {
    Object var1 = t;
    synchronized(t) {
      if (this.s != null) {
        this.s.removeMessages(2);
        this.s = null;
      }

    }
  }

  public void disconnect() {
    int var1 = this.j.get();
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter disconnect, Connection Status: " + var1);
    switch(var1) {
      case 1:
      case 4:
      default:
        break;
      case 2:
        this.a(4);
        break;
      case 3:
        this.a(4);
        this.h();
        break;
      case 5:
        this.f();
        this.a(4);
    }

  }

  public boolean isConnected() {
    if (this.p == 0) {
      g var1 = new g(this.a);
      this.p = var1.b("com.huawei.hwid");
    }

    if (this.p < 20504000) {
      long var7 = System.currentTimeMillis() - this.o;
      if (var7 > 0L && var7 < 300000L) {
        return this.innerIsConnected();
      } else {
        if (this.innerIsConnected()) {
          InnerPendingResult var3 = com.huawei.hms.support.api.a.a.a(this, new CheckConnectInfo());
          ResolveResult var4 = (ResolveResult)var3.awaitOnAnyThread(2000L, TimeUnit.MILLISECONDS);
          Status var5 = var4.getStatus();
          if (var5.isSuccess()) {
            this.o = System.currentTimeMillis();
            return true;
          }

          int var6 = var5.getStatusCode();
          com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "isConnected is false, statuscode:" + var6);
          if (var6 != 907135004) {
            this.l();
            this.a(1);
            this.o = System.currentTimeMillis();
          }
        }

        return false;
      }
    } else {
      return this.innerIsConnected();
    }
  }

  public boolean isConnecting() {
    int var1 = this.j.get();
    return var1 == 2 || var1 == 5;
  }

  public boolean innerIsConnected() {
    return this.j.get() == 3 || this.j.get() == 4;
  }

  public void setApiMap(Map<Api<?>, ApiOptions> var1) {
    this.m = var1;
  }

  public void setScopes(List<Scope> var1) {
    this.k = var1;
  }

  public void setPermissionInfos(List<PermissionInfo> var1) {
    this.l = var1;
  }

  public boolean setSubAppInfo(SubAppInfo var1) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter setSubAppInfo");
    if (var1 == null) {
      com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "subAppInfo is null");
      return false;
    } else {
      String var2 = var1.getSubAppID();
      if (TextUtils.isEmpty(var2)) {
        com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "subAppId is empty");
        return false;
      } else {
        String var3 = TextUtils.isEmpty(this.d) ? com.huawei.hms.c.j.a(this.a) : this.d;
        if (var2.equals(var3)) {
          com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "subAppId is host appid");
          return false;
        } else {
          this.n = new SubAppInfo(var1);
          return true;
        }
      }
    }
  }

  public void checkUpdate(Activity var1, CheckUpdatelistener var2) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter checkUpdate");
    if (var2 == null) {
      com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "listener is null!");
    } else if (var1 != null && !var1.isFinishing()) {
      this.u = var2;
      UpdateSdkAPI.checkClientOTAUpdate(var1, this.v, true, 0, true);
      this.g();
    } else {
      com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "checkUpdate, activity is illegal: " + var1);
      var2.onResult(-1);
    }
  }

  public void onResume(Activity var1) {
    if (var1 != null) {
      com.huawei.hms.support.log.a.a("HuaweiApiClientImpl", "onResume");
      this.h = new WeakReference(var1);
    }

  }

  public void onPause(Activity var1) {
    com.huawei.hms.support.log.a.a("HuaweiApiClientImpl", "onPause");
  }

  public Activity getTopActivity() {
    return (Activity)this.h.get();
  }

  private void g() {
    if (!com.huawei.hms.support.b.a.a().b()) {
      HashMap var1 = new HashMap();
      var1.put("package", this.getPackageName());
      var1.put("sdk_ver", String.valueOf(20603301));
      String var2 = null;
      SubAppInfo var3 = this.getSubAppInfo();
      if (var3 != null) {
        var2 = var3.getSubAppID();
      }

      if (var2 == null) {
        var2 = this.getAppID();
      }

      var1.put("app_id", var2);
      String var4 = "core.checkUpdate";
      String[] var5 = var4.split("\\.");
      if (var5.length == 2) {
        var1.put("service", var5[0]);
        var1.put("api_name", var5[1]);
      }

      var1.put("result", "0");
      var1.put("cost_time", "0");
      com.huawei.hms.support.b.a.a().a(this.getContext(), "HMS_SDK_API_CALL", var1);
      com.huawei.hms.c.b.a(this.getContext(), UpdateProvider.getLocalFile(this.getContext(), "hms/config.txt"), UpdateProvider.getLocalFile(this.getContext(), "hms/HwMobileServiceReport.txt"), var4, System.currentTimeMillis(), 0);
    }
  }

  public void setConnectionCallbacks(ConnectionCallbacks var1) {
    this.q = var1;
  }

  public void setConnectionFailedListener(OnConnectionFailedListener var1) {
    this.r = var1;
  }

  private void h() {
    DisconnectInfo var1 = this.i();
    com.huawei.hms.support.api.a.a.a(this, var1).setResultCallback(new HuaweiApiClientImpl.b((com.huawei.hms.api.e)null));
  }

  private DisconnectInfo i() {
    ArrayList var1 = new ArrayList();
    if (this.m != null) {
      Iterator var2 = this.m.keySet().iterator();

      while(var2.hasNext()) {
        Api var3 = (Api)var2.next();
        var1.add(var3.getApiName());
      }
    }

    return new DisconnectInfo(this.k, var1);
  }

  private void a(ResolveResult<DisconnectResp> var1) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onDisconnectionResult, disconnect from server result: " + var1.getStatus().getStatusCode());
    this.l();
    this.a(1);
  }

  public void onServiceConnected(ComponentName var1, IBinder var2) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onServiceConnected.");
    this.f();
    this.e = com.huawei.hms.core.aidl.e.a.a(var2);
    if (this.e == null) {
      com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "In onServiceConnected, mCoreService must not be null.");
      this.l();
      this.a(1);
      if (this.r != null) {
        this.r.onConnectionFailed(new ConnectionResult(10));
      }

    } else {
      if (this.j.get() == 5) {
        this.a(2);
        this.j();
      } else if (this.j.get() != 3) {
        this.l();
      }

    }
  }

  private void j() {
    com.huawei.hms.support.log.a.a("HuaweiApiClientImpl", "Enter sendConnectApiServceRequest.");
    ConnectInfo var1 = this.k();
    com.huawei.hms.support.api.a.a.a(this, var1).setResultCallback(new HuaweiApiClientImpl.a((com.huawei.hms.api.e)null));
  }

  private ConnectInfo k() {
      String var1 = null;
      try {
          var1 = (new g(this.a)).c(this.a.getPackageName());
      } catch (Exception e1) {
          e1.printStackTrace();
      }
      String var2 = var1 == null ? "" : var1;
    String var3 = this.n == null ? null : this.n.getSubAppID();
    return new ConnectInfo(this.getApiNameList(), this.k, var2, var3);
  }

  private void b(ResolveResult<ConnectResp> var1) {
    ConnectResp var2 = (ConnectResp)var1.getValue();
    if (var2 != null) {
      this.f = var2.sessionId;
    }

    String var3 = this.n == null ? null : this.n.getSubAppID();
    if (!TextUtils.isEmpty(var3)) {
      this.b = var3;
    }

    int var4 = var1.getStatus().getStatusCode();
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onConnectionResult, connect to server result: " + var4);
    if (Status.SUCCESS.equals(var1.getStatus())) {
      if (var1.getValue() != null) {
        ProtocolNegotiate.getInstance().negotiate(((ConnectResp)var1.getValue()).protocolVersion);
      }

      this.a(3);
      if (this.q != null) {
        this.q.onConnected();
      }

      this.m();
    } else {
      if (var1.getStatus() != null && var1.getStatus().getStatusCode() == 1001) {
        this.l();
        this.a(1);
        if (this.q != null) {
          this.q.onConnectionSuspended(3);
        }

        return;
      }

      this.l();
      this.a(1);
      if (this.r != null) {
        this.r.onConnectionFailed(new ConnectionResult(var4));
      }
    }

  }

  private void l() {
    com.huawei.hms.c.j.a(this.a, this);
  }

  private void m() {
    if (this.i) {
      com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Connect notice has been shown.");
    } else {
      if (HuaweiApiAvailability.getInstance().isHuaweiMobileNoticeAvailable(this.a) == 0) {
        com.huawei.hms.support.api.a.a.a
        com.huawei.hms.support.api.a.a.a(this, 0, "2.6.3.301").setResultCallback(new HuaweiApiClientImpl.c((com.huawei.hms.api.e)null));
      }

    }
  }

  public void onServiceDisconnected(ComponentName var1) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onServiceDisconnected.");
    this.e = null;
    this.a(1);
    if (this.q != null) {
      this.q.onConnectionSuspended(1);
    }

  }

  public int asyncRequest(Bundle var1, String var2, int var3, ResultCallback<BundleResult> var4) {
    com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter asyncRequest.");
    if (var4 != null && var2 != null && var1 != null) {
      if (!this.innerIsConnected()) {
        com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "client is unConnect.");
        return 907135003;
      } else {
        com.huawei.hms.core.aidl.b var5 = new com.huawei.hms.core.aidl.b(var2, var3);
        com.huawei.hms.core.aidl.f var6 = com.huawei.hms.core.aidl.a.a(var5.c());
        var5.a(var1);
        RequestHeader var7 = new RequestHeader(this.getAppID(), this.getPackageName(), 20603301, this.getSessionId());
        var7.setApiNameList(this.getApiNameList());
        var5.b = var6.a(var7, new Bundle());

        try {
          this.getService().a(var5, new com.huawei.hms.api.g(this, var4));
          return 0;
        } catch (RemoteException var9) {
          com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "remote exception:" + var9.getMessage());
          return 907135001;
        }
      }
    } else {
      com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "arguments is invalid.");
      return 907135000;
    }
  }

  public List<String> getApiNameList() {
    ArrayList var1 = new ArrayList();
    if (this.m != null) {
      Iterator var2 = this.m.keySet().iterator();

      while(var2.hasNext()) {
        Api var3 = (Api)var2.next();
        var1.add(var3.getApiName());
      }
    }

    return var1;
  }

  private class c implements ResultCallback<ResolveResult<JosGetNoticeResp>> {
    private c() {
    }

    public void a(ResolveResult<JosGetNoticeResp> var1) {
      if (var1 != null && var1.getStatus().isSuccess()) {
        JosGetNoticeResp var2 = (JosGetNoticeResp)var1.getValue();
        Intent var3 = var2.getNoticeIntent();
        if (var3 != null && var2.getStatusCode() == 0) {
          com.huawei.hms.support.log.a.a("HuaweiApiClientImpl", "get notice has intent.");
          Activity var4 = com.huawei.hms.c.j.a((Activity)HuaweiApiClientImpl.this.g.get(), HuaweiApiClientImpl.this.getTopActivity());
          if (var4 == null) {
            com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "showNotice no valid activity!");
            return;
          }

          HuaweiApiClientImpl.this.i = true;
          var4.startActivity(var3);
        }
      }

    }
  }

  private class a implements ResultCallback<ResolveResult<ConnectResp>> {
    private a() {
    }

    public void a(ResolveResult<ConnectResp> var1) {
      (new Handler(Looper.getMainLooper())).post(new h(this, var1));
    }
  }

  private class b implements ResultCallback<ResolveResult<DisconnectResp>> {
    private b() {
    }

    public void a(ResolveResult<DisconnectResp> var1) {
      (new Handler(Looper.getMainLooper())).post(new i(this, var1));
    }
  }
}
