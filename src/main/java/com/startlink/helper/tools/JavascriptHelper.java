package com.startlink.helper.tools;


import org.apache.commons.lang3.StringUtils;

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
    public static final String RequireStart = "require(";
    public static final String RequireEnd = "\")";

    public static final String DirName = "__dirname";
    public static final String libDir = "lib";
    public static final String JsExt = ".js";

    // Array
    public static final String ListStart = "[\n";
    public static final String ListEnd = "]\n";

    // const
    public static final String Const = "const";
    public static final String Latest = "LATEST";
    public static final String ParamsType = "paramsType";

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
        return RequireStart
                + DirName + " + \"/"
                + getPatchByClassName(className)
                + RequireEnd;
    }

    /**
     * 通过类名 获取 服务名
     *
     * @param className
     * @return
     */
    public static String getServiceNameByClassName(String className) {
        return getServiceNameByClassName(className,".");
    }
    public static String getServiceNameByClassName(String className,String x) {
        String[] tmp = StringUtils.split(className, x);
        return tmp[tmp.length - 1];
    }

    public static String getPackageJsonText(String name, String version) {
        String base = "{\n" +
                "  \"name\": \"${name}\",\n" +
                "  \"version\": \"${version}\",\n" +
                "  \"description\": \"\",\n" +
                "  \"main\": \"index.js\",\n" +
                "  \"directories\": {\n" +
                "    \"lib\": \"lib\"\n" +
                "  },\n" +
                "  \"scripts\": {\n" +
                "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n" +
                "  },\n" +
                "  \"author\": \"\",\n" +
                "  \"license\": \"ISC\"\n" +
                "}\n";
        base = StringUtils.replace(base, "${name}", name);
        base = StringUtils.replace(base, "${version}", version);
        return base;
    }
}
