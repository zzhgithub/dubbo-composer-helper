package com.startlink.helper.tools;

import com.startlink.helper.core.Config;
import com.startlink.helper.core.JsOutDto;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class FileHelper {
    public static void deleteAll(String path) {
        File file = new File(path);
        if (!file.exists()) {
            //文件夹不存在继续
            System.err.println("The dir are not exists!");
            return;
        }
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for (String name : content) {
            File temp = new File(path, name);
            if (temp.isDirectory()) {//判断是否是目录
                deleteAll(temp.getAbsolutePath());
                temp.delete();//删除空目录
            } else {
                if (!temp.delete()) {//直接删除文件
                    System.err.println("Failed to delete " + name);
                    System.exit(0);
                }
            }
        }
    }

    public static void saveFile(JsOutDto jsOutDto, Config config) {
        if (Objects.nonNull(jsOutDto.getText())) {
            try {
                String path = config.getOutputDir()
                        + File.separator
                        + jsOutDto.getPath();

                File file = new File(path);
                if (!file.exists()) {
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(jsOutDto.getText().getBytes());
                fileOutputStream.close();
            } catch (Exception e) {
                System.err.println("创建文件失败：" + e.getMessage());
                System.exit(0);
            }
        }
    }
}
