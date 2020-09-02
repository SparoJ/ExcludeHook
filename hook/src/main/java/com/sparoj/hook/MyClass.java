package com.sparoj.hook;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class MyClass {

    public static void main(String[] args) {
        hook("s");
    }

    public static void hook(String jar) {
        // dir: jar包目录
// charles.jar:  要修改的jar包名
//        HuaweiApiClientImpl.k
        ClassPool pool = new ClassPool();
//        /Users/shandahua/Documents/3rd_Project/ExcludeAar-master_new/hook/libs/hmssdk-base-2.6.3.301.jar!/com/huawei/hms/c/g.class
        Clazz.initialize("/Users/shandahua/Downloads/com/", "hmssdk-base-2.6.3.301.jar")
                .hackClass("com.huawei.hms.c", "g", ctClass -> {

                    CtMethod ctMethod = ctClass.getDeclaredMethod("c"); //
//                    ctMethod.setBody("{return \"Crack by me!\";}");
                    CtClass etype = ClassPool.getDefault().get("java.lang.Exception");
                    ctMethod.addCatch("{ System.out.println($e); return \"\"; }", etype); //throw $e;
//                    ctMethod.insertAfter("{ return \"Crack by me!\";}");
//                    ctMethod.setBody("{String var1 = null;\n" +
//                            "      try {\n" +
//                            "          var1 = (new g(this.a)).c(this.a.getPackageName());\n" +
//                            "      } catch (Exception e1) {\n" +
//                            "          e1.printStackTrace();\n" +
//                            "      }\n" +
//                            "      String var2 = var1 == null ? \"\" : var1;\n" +
//                            "    String var3 = this.n == null ? null : this.n.getSubAppID();\n" +
//                            "    return new ConnectInfo(this.getApiNameList(), this.k, var2, var3);}");
                });
    }
}
