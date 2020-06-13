package com.startlink.helper.core;

import com.startlink.helper.tools.FileHelper;
import com.startlink.helper.tools.JavaClassTransformHelper;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
        List<String> services = new ArrayList<>();
        // todo 创建文件夹
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
                        //todo 处理接口
                        jsOutDto = JavaClassTransformHelper.transformClass(javaClass);
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
        // 生成 package.json文件
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
