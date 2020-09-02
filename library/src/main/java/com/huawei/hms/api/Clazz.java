//package com.huawei.hms.api;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableOnSubscribe;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.NotFoundException;
//
//public class Clazz {
//
//    private String JAR_DIR = "";
//    private String JAR_NAME = "";
//    private String PKGNAME = "";
//    private String CLASSNAME = "";
//
//    private Clazz() {
//    }
//
//    public interface Builder {
//        Clazz CLAZZ = new Clazz();
//    }
//
//    public interface Presenter {
//        void onHack(CtClass ctClass) throws Exception;
//    }
//
//    public static Clazz initialize(String libDir, String libName) {
//        Clazz clazz = Builder.CLAZZ;
//
//        clazz.JAR_DIR = libDir;
//        clazz.JAR_NAME = libName;
//        return clazz;
//    }
//
//
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public void hackClass(String pkgName, String className, Presenter presenter) {
//
//        PKGNAME = pkgName;
//        CLASSNAME = className;
//
//        // jar包路径
//        // class位置
//        // 输入class路径, jar uvf命令替换jar
////            String command = String.format("cd %s; jar uvf %s %s", JAR_DIR, JAR_NAME, clazzPath);
////            String[] cmd = {"/bin/sh", "-c", command};
//        Observable.create((ObservableOnSubscribe<String>) emitter -> {
//            // jar包路径
//            String jar = getJarPath();
//            File file = new File(jar);
//            if (file.exists()) {
//                emitter.onNext(jar);
//                emitter.onComplete();
//            } else {
//                emitter.onError(new FileNotFoundException(jar + " NOT FOUND!"));
//            }
//
//        }).map(jarPath -> {
//            ClassPool classPool = ClassPool.getDefault();
//
//            classPool.insertClassPath(jarPath);
//
//            // class位置
//            String clazz = getClazzName();
//            CtClass ctClass = classPool.get(clazz);
//
//            if (presenter != null) {
//                presenter.onHack(ctClass);
//            }
//            return ctClass.toBytecode();
//        }).map(bytes -> {
//            String clazzPath = getClazzPath();
//
//            File dstFile = new File(clazzPath);
//            if (!dstFile.getParentFile().exists()) {
//                dstFile.getParentFile().mkdirs();
//            }
//            FileOutputStream output = new FileOutputStream(dstFile);
//
//            output.write(bytes);
//            output.flush();
//            return dstFile.getAbsolutePath();
//        }).map(savePath -> {  // 输入class路径, jar uvf命令替换jar
//            String clazzPath = getClazzPath();
//
////            String command = String.format("cd %s; jar uvf %s %s", JAR_DIR, JAR_NAME, clazzPath);
////            String[] cmd = {"/bin/sh", "-c", command};
//            String command = String.format("jar uvf %s %s", getJarPath(), clazzPath);
//
//            return exec(command);
//
//        }).subscribe(status -> {
//            if (status == 0) {
//                String command = String.format("rm -rf %s", getClazzRootPath());
//                Runtime.getRuntime().exec(command);
//            }
//            System.out.printf("status: %d\t%s\n", status, Thread.currentThread().getName());
//        });
//
//    }
//
//    private String getJarPath() {
//        return String.format("%s/%s", JAR_DIR, JAR_NAME);
//    }
//
//    private String getClazzRootPath() {
//        String rootPath;
//        int index = PKGNAME.indexOf(".");
//        if (index > 0) {
//            rootPath = PKGNAME.substring(0, index);
//        } else {
//            rootPath = getClazzPath();
//        }
//
//        return rootPath;
//    }
//
//    private String getClazzPath() {
//        String clazzPath;
//        if (PKGNAME.isEmpty()) {
//            clazzPath = String.format("%s.class", CLASSNAME);
//        } else {
//            clazzPath = String.format("%s/%s.class", PKGNAME.replace(".", "/"), CLASSNAME);
//        }
//        return clazzPath;
//    }
//
//    private String getClazzName() {
//        String clazzName;
//        if (PKGNAME.isEmpty()) {
//            clazzName = CLASSNAME;
//        } else {
//            clazzName = String.format("%s.%s", PKGNAME, CLASSNAME);
//        }
//        return clazzName;
//    }
//
//
//    public static int exec(String... commands) throws IOException, InterruptedException {
//        int status = 1;
//        if (commands == null) {
//            return status;
//        }
//        File wd = new File(".");
//        Process proc = Runtime.getRuntime().exec("/bin/bash", null);
//        if (proc != null) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
//            for (String command : commands) {
//                out.println(command);
//            }
//            out.println("exit");
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            status = proc.waitFor();
//            in.close();
//            out.close();
//            proc.destroy();
//        }
//        return status;
//    }
//
//    public static int exec2(String... commands) throws IOException, InterruptedException {
//        int status = 1;
//        if (commands == null) {
//            return status;
//        }
//        String command = String.join(";", commands);
//        String[] cmd = {"/bin/sh", "-c", command};
//        Process proc = Runtime.getRuntime().exec(cmd);
//        if (proc != null) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            status = proc.waitFor();
//            in.close();
//            proc.destroy();
//        }
//        return status;
//    }
//
//
//    public static String readContent(String path) throws IOException, NotFoundException {
//
//        File file = new File(path);
//
//        if (!file.exists()) {
//            throw new NotFoundException("Not Found: " + file.getAbsolutePath());
//        }
//        BufferedReader in = new BufferedReader(new FileReader(file));
//        String line;
//
//        StringBuilder buffer = new StringBuilder();
//
//        while ((line = in.readLine()) != null) {
//            buffer.append(line).append("\n");
//        }
//        in.close();
//        return buffer.toString();
//
//    }
//}
