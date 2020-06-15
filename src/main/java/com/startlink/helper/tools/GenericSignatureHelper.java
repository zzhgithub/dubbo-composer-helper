package com.startlink.helper.tools;

import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 泛型定义处理
 */
public class GenericSignatureHelper {

    /**
     * 处理
     *
     * @param signature
     * @return
     */
    public static String dealMethodGenericSignature(String signature) {
        StringBuilder builder = new StringBuilder();
        int methodStart = signature.indexOf("(");
        int methodEnd = signature.indexOf(")");

        signature = StringUtils.substring(signature, methodStart + 1, methodEnd);
        List<String> params = splitSign(signature);
        for (String subSign : params) {
            if (subSign.contains("<")) {
                builder.append(dealGenericSignature(subSign));
            } else {
                subSign = StringUtils.substring(subSign, 1, subSign.length() - 1);
                builder.append(JavascriptHelper.baseAndSimpleObject(subSign.replace("/", ".")));
            }
            builder.append(",");
        }
        return builder.toString();
    }

    /**
     * 分割入参值的 签名
     *
     * @param signature
     * @return
     */
    private static List<String> splitSign(String signature) {
        List<String> res = new ArrayList<>();
        int time = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < signature.length(); i++) {
            char c = signature.charAt(i);
            builder.append(c);
            switch (c) {
                default:
                    break;
                case '<':
                    time += 1;
                    break;
                case '>':
                    time -= 1;
                    break;
                case ';':
                    if (time == 0) {
                        res.add(builder.toString());
                        //清空
                        builder.delete(0, builder.length());
                    }
            }
        }
        return res;
    }

    /**
     * 处理基础泛型
     * 比如 Lcom.util.List<>;
     *
     * @param sign
     * @return
     */
    public static String dealGenericSignature(String sign) {
        StringBuilder builder = new StringBuilder();
        if (sign.startsWith("L") && sign.endsWith(";")) {
            sign = StringUtils.substring(sign, 1, sign.length() - 1);
        }
        int start = StringUtils.indexOf(sign, "<");
        int end = StringUtils.lastIndexOf(sign, ">");

        String clazzName = StringUtils.substring(sign, 0, start);
        clazzName = clazzName.replace("/", ".");
        switch (clazzName) {
            case "java.util.List":
            case "java.util.Collection":
                builder.append(JavascriptHelper.ObjectStart);
                builder.append(JavascriptHelper.JavaClassDef);
                builder.append(" : ");
                builder.append("\"" + clazzName + "\"");
                builder.append(",");
                builder.append(JavascriptHelper.JavaClassContent);
                builder.append(" : ");
                builder.append(JavascriptHelper.ListStart);
                String innerType = StringUtils.substring(sign, start + 1, end);
                if (innerType.contains("<")) {
                    //内部还是泛型
                    builder.append(dealGenericSignature(innerType));
                } else {
                    // 一般类型
                    String innerClassName = StringUtils.substring(innerType, 1, innerType.length() - 1).replace("/", ".");
                    builder.append(JavascriptHelper.baseAndSimpleObject(innerClassName));
                }
                builder.append(JavascriptHelper.ListEnd);
                builder.append(",");
                builder.append(JavascriptHelper.ObjectEnd);
                break;
            default:
                // fixme 对一般和其他泛型的支持！
                System.err.println("泛型" + clazzName + "暂时不能正常处理");
                System.exit(0);
        }
        return builder.toString();
    }
}
