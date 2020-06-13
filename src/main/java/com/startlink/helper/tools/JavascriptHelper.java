package com.startlink.helper.tools;


public class JavascriptHelper {

    // 导出语句
    public static final String Exports = "module.exports=";

    // 对象
    public static final String ObjectStart = "{\n";
    public static final String ObjectEnd = "}\n";

    // java 类
    public static final String JavaClassDef = "$class";
    public static final String JavaClassContent = "$";

    // require
    public static final String RequireStart = "require(\"";
    public static final String RequireEnd = "\")";

    public static final String DirName = "__dirname";
    public static final String libDir = "lib";
    public static final String JsExt = ".js";


    public static String baseAndSimpleObject(String typeName) {
        StringBuilder builder = new StringBuilder();
        // java.lang下的基础类型使用这种方法进行存储
        // FIXME Date 可以正常的转化吗？
        if (typeName.startsWith("java.lang")) {
            builder.append(ObjectStart);
            builder.append(JavaClassDef);
            builder.append(": ");
            builder.append("\"" + typeName + "\"");
            builder.append(ObjectEnd);
        } else {
            builder.append(getRequireByClassName(typeName));
        }

        return builder.toString();
    }

    /**
     * 根据报名获取 路径
     *
     * @param className
     * @return
     */
    public static String getPatchByClassName(String className) {
        return libDir + "/" + className.replace(".", "/") + JsExt;
    }

    public static String getRequireByClassName(String className) {
        return DirName + "/"
                + RequireStart
                + getPatchByClassName(className)
                + RequireEnd;
    }
}
