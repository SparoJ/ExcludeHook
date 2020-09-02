package com.huawei.hms.api;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * description:
 * Created by sdh on 2020/8/14
 */
public class HookPackageManager {

    private static final String TAG = "HookPackageManager";

    public static void hookPackageManager() {
        try {
            final Object oldPM = ReflectUtil.getField("android.app.ActivityThread", null, "sPackageManager");
            Class iPM = Class.forName("android.content.pm.IPackageManager");
            Object newPM = Proxy.newProxyInstance(iPM.getClassLoader(), new Class[]{iPM}, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    Log.d(TAG, "method: " + method.getName());
//                    android.content.pm.PackageManager.getPackageInfo(java.lang.String, int)
                    if (TextUtils.equals(method.getName(), "getPackageInfo")) {
                        try {
                            return method.invoke(oldPM, objects);
                        } catch (Exception e) {
                            return null;
                        }
                    }
                    return method.invoke(oldPM, objects);
                }
            });
            ReflectUtil.setField("android.app.ActivityThread", null, "sPackageManager", newPM);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
