package com.startlink.helper.tools;

import com.startlink.helper.core.JsOutDto;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

import java.util.Objects;

/**
 * javaClass 变换助手
 */
public class JavaClassTransformHelper {


    /**
     * 变换 类文件
     *
     * @param javaClass
     * @return
     */
    public static JsOutDto transformClass(JavaClass javaClass) {
        JsOutDto jsOutDto = new JsOutDto();
        // package Name 用于生成路径
        String pakageName = javaClass.getPackageName();
        // 用于生成文件名和
        String className = javaClass.getClassName();

        // 生成目标代码
        StringBuilder builder = new StringBuilder();
        builder.append(JavascriptHelper.Exports);
        builder.append(JavascriptHelper.ObjectStart);
        builder.append(JavascriptHelper.JavaClassDef);
        builder.append(":");
        builder.append("\"" + className + "\"");
        builder.append(",\n");
        builder.append(JavascriptHelper.JavaClassContent);
        builder.append(": ");
        builder.append(JavascriptHelper.ObjectStart);
        Field[] fields = javaClass.getFields();
        if (fields.length > 0) {
            for (Field field : fields) {
                builder.append(field.getName());
                builder.append(": ");
                if (Objects.isNull(field.getGenericSignature())) {
                    //没有泛型简单处理
                    builder.append(JavascriptHelper.baseAndSimpleObject(field.getType().toString()));
                } else {
                    //todo 处理泛型的情况
                }
                builder.append(",");
            }
        }
        builder.append(JavascriptHelper.ObjectEnd);
        builder.append(JavascriptHelper.ObjectEnd);

        // 相对于项目 lib/ 下的地址
        jsOutDto.setPath(JavascriptHelper.getPatchByClassName(className));
        jsOutDto.setText(builder.toString());
        return jsOutDto;
    }

    /**
     * 变换 接口文件
     *
     * @return
     */
    public static String transformInterface() {
        return null;
    }


}
