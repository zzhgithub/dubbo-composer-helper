package com.startlink.helper.tools;

import com.startlink.helper.core.Config;
import com.startlink.helper.core.JsOutDto;
import com.startlink.helper.core.ServiceDto;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
//        String pakageName = javaClass.getPackageName();
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
                    builder.append(GenericSignatureHelper.dealGenericSignature(field.getGenericSignature()));
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
    public static JsOutDto transformInterface(JavaClass javaClass) {
        String className = javaClass.getClassName();
        String serviceName = JavascriptHelper.getServiceNameByClassName(className);

        StringBuilder builder = new StringBuilder();
        builder.append(JavascriptHelper.Const);
        builder.append(" ");
        builder.append(serviceName);
        builder.append(" = ");
        builder.append(JavascriptHelper.ObjectStart);

        builder.append("name : ");
        builder.append("\"" + className + "\"");
        builder.append(",");

        builder.append("group : ");
        builder.append("\"\"");
        builder.append(",");

        // FIXME 最近版本 不能进行切换
        builder.append("version : ");
        builder.append("\"" + JavascriptHelper.Latest + "\"");
        builder.append(",");

        builder.append("methods : ");
        builder.append(JavascriptHelper.ObjectStart);
        // 方法
        Method[] methods = javaClass.getMethods();
        if (methods.length > 0) {
            for (Method method : methods) {
                String methodName = method.getName();
//                System.out.println(method.getName());
                builder.append(methodName);
                builder.append(" : ");
                builder.append(JavascriptHelper.ObjectStart);
                builder.append(JavascriptHelper.ParamsType);
                builder.append(" : ");
                builder.append(JavascriptHelper.ListStart);
                // 处理入参定义有泛型的处理泛型
                if (Objects.nonNull(method.getGenericSignature())) {
                    builder.append(GenericSignatureHelper.dealMethodGenericSignature(method.getGenericSignature()));
                }
                builder.append(JavascriptHelper.ListEnd);
                builder.append(",");
                builder.append(JavascriptHelper.ObjectEnd);
                builder.append(",");
            }
        }
        builder.append(JavascriptHelper.ObjectEnd);
        builder.append(",");

        builder.append(JavascriptHelper.ObjectEnd);
        builder.append("\n");
        builder.append(JavascriptHelper.Exports);
        builder.append(JavascriptHelper.ObjectStart);
        builder.append(serviceName);
        builder.append(JavascriptHelper.ObjectEnd);

        JsOutDto jsOutDto = new JsOutDto();
        jsOutDto.setText(builder.toString());
        jsOutDto.setPath(JavascriptHelper.getPatchByClassName(className));
        return jsOutDto;
    }


    public static void saveIndex(List<ServiceDto> serviceDtoList, Config config) {
        StringBuilder builder = new StringBuilder();

        // 添加全局路径
        builder.append(JavascriptHelper.RootDir);
        builder.append(" = ");
        builder.append(JavascriptHelper.DirName);
        builder.append(";\n");

        for (ServiceDto serviceDto : serviceDtoList) {
            builder.append(JavascriptHelper.Const);
            builder.append(" ");
            builder.append(JavascriptHelper.ObjectStart);
            builder.append(" " + serviceDto.getServiceName() + " ");
            builder.append(JavascriptHelper.ObjectEnd);
            builder.append(" = ");
            builder.append(serviceDto.getRequireString());
            builder.append(";\n");
        }

        builder.append("\n");
        builder.append(JavascriptHelper.Exports);
        builder.append(JavascriptHelper.ObjectStart);
        List<String> s = serviceDtoList.stream().map(ServiceDto::getServiceName).collect(Collectors.toList());
        builder.append(StringUtils.join(s, ","));
        builder.append(JavascriptHelper.ObjectEnd);
        JsOutDto jsOutDto = new JsOutDto();
        jsOutDto.setPath("index.js");
        jsOutDto.setText(builder.toString());
        FileHelper.saveFile(jsOutDto, config);
    }

    public static void savePackageJson(Config config) {
        //fixme 支持version的写入
        String text = JavascriptHelper.getPackageJsonText(config.getAppName(), "0.0.1");
        JsOutDto jsOutDto = new JsOutDto();
        jsOutDto.setText(text);
        jsOutDto.setPath("package.json");
        FileHelper.saveFile(jsOutDto, config);
    }

}
