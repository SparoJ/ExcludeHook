//package com.huawei.hms.api;
//
//import javassist.CtMethod;
//
///**
// * description:
// * Created by sdh on 2020/8/14
// */
//public class HookTest {
//
////    public static void hookTest(Context context) {
////        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES)
////        if (packageInfo?.signatures != null && packageInfo.signatures.isNotEmpty()) {
////            return packageInfo.signatures[0].toByteArray();
////        }
////        Log.e("sdh", packageInfo.signatures)
////    }
//
//    public static void hook(String jar) {
//        // dir: jar包目录
//// charles.jar:  要修改的jar包名
////        HuaweiApiClientImpl.k
//        Clazz.initialize("libs", "hmssdk-base-2.6.3.301.jar")
//                .hackClass("com.huawei.hms.api", "HuaweiApiClientImpl", ctClass -> {
//
//                    CtMethod ctMethod = ctClass.getDeclaredMethod("k", null);
//                    ctMethod.setBody("{return \"Crack by me!\";}");
//
//                });
//    }
//}
