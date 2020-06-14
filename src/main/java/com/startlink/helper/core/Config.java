package com.startlink.helper.core;

import com.startlink.helper.tools.JavascriptHelper;
import org.apache.commons.cli.CommandLine;

public class Config {

    public final static String defaultClasses = "classes";

    private String targetDir;
    private String outputDir;
    private String classesDir;

    private String appName;

    private Config() {
    }

    public Config(String targetDir, String outputDir, String classesDir) {
        this.targetDir = targetDir;
        this.outputDir = outputDir;
        this.classesDir = classesDir;
    }

    public static Config getInstance(CommandLine commandLine) {
        Config config = new Config();
        if (commandLine.hasOption("t")) {
            config.setTargetDir(commandLine.getOptionValue("t"));
        } else {
            System.err.println("找不到地址目标文件地址");
            System.exit(0);
        }

        if (commandLine.hasOption("c")) {
            config.setOutputDir(commandLine.getOptionValue("c"));
        } else {
            config.setClassesDir(defaultClasses);
        }

        if (commandLine.hasOption("o")) {
            config.setOutputDir(commandLine.getOptionValue("o"));
        } else {
            System.err.println("请指定输出文件的位置");
            System.exit(0);
        }

        if (commandLine.hasOption("n")) {
            config.setAppName(commandLine.getOptionValue("n"));
        } else {
            config.setAppName(JavascriptHelper.getServiceNameByClassName(config.getOutputDir(), "/"));
        }
        return config;
    }


    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getClassesDir() {
        return classesDir;
    }

    public void setClassesDir(String classesDir) {
        this.classesDir = classesDir;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "targetDir='" + targetDir + '\'' +
                ", outputDir='" + outputDir + '\'' +
                ", classesDir='" + classesDir + '\'' +
                '}';
    }
}
