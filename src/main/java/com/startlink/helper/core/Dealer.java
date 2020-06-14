package com.startlink.helper.core;

import com.startlink.helper.tools.FileHelper;
import com.startlink.helper.tools.JavaClassTransformHelper;
import com.startlink.helper.tools.JavascriptHelper;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dealer {

    private Config config;

    public Dealer(Config config) {
        this.config = config;
    }

    /**
     * 处理代码
     */
    public void deal() {
        // 删除源项目
        FileHelper.deleteAll(config.getOutputDir());
        // 遍历路径
        String path = config.getTargetDir() + File.separator + config.getClassesDir();
        File file = new File(path);
        // 存放全部接口数据
        List<ServiceDto> services = new ArrayList<>();
        traverse(file, f -> {
            //处理文件
            String fileName = f.getName();
            if (fileName.endsWith(".class")) {
                try (FileInputStream fileInputStream = new FileInputStream(f)) {
                    ClassParser parser = new ClassParser(fileInputStream, fileName);
                    JavaClass javaClass = parser.parse();
                    JsOutDto jsOutDto = null;
                    if (javaClass.isClass()) {
                        jsOutDto = JavaClassTransformHelper.transformClass(javaClass);
                    }
                    if (javaClass.isInterface()) {
                        jsOutDto = JavaClassTransformHelper.transformInterface(javaClass);
                        ServiceDto serviceDto = new ServiceDto();
                        serviceDto.setServiceName(JavascriptHelper.getServiceNameByClassName(javaClass.getClassName()));
                        serviceDto.setRequireString(JavascriptHelper.getRequireByClassName(javaClass.getClassName()));
                        services.add(serviceDto);
                    }

                    if (Objects.nonNull(jsOutDto)) {
                        // 保存文件
                        FileHelper.saveFile(jsOutDto, config);
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        // 接口数据生成入口文件
        JavaClassTransformHelper.saveIndex(services, config);
        // 生成 package.json文件
        JavaClassTransformHelper.savePackageJson(config);
        // Readme 文件
        // todo git忽略文件
    }

    public void traverse(File file, DealerFile dealerFile) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                traverse(f, dealerFile);
            }
            if (f.isFile()) {
                dealerFile.dealWith(f);
            }
        }
    }

    @FunctionalInterface
    interface DealerFile {
        void dealWith(File file);
    }
}
