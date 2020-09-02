本项目主要提供修改jar/aar/apk 中 class 文件的思路和实现方式

1.如果是未混淆的可直接通过jdgui/AndroidCrackTool工具反解修改+利用excludeAar中的gradle task 能力打jar/aar

2.如果是混淆的可通过项目中依赖的 javaassist等字节码编辑方式直接修改jar 再通过excludeAar中的aar task 打成aar
示例是修改混淆后类中的混淆方法，通过try/catch方式避免异常外抛

ExcludeAar方案使用方式参考 https://github.com/Siy-Wu/ExcludeAar 

