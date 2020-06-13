package com.startlink.helper.tools;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数定义
 */
public class OptionHelper {

    public static List<Option> getOptionList() {
        List<Option> res = new ArrayList<>();
        res.add(Option.builder("t")
                .longOpt("target")
                .hasArg()
                .argName("target")
                .desc("target 翻译api文件的生成物路径")
                .build());
        res.add(Option.builder("c")
                .longOpt("classes")
                .hasArg()
                .argName("classes")
                .desc("target 翻译api文件的.class文件相对target地址" +
                        "\n默认情况下为classes")
                .build());
        res.add(Option.builder("o")
                .longOpt("output")
                .hasArg()
                .argName("output")
                .desc("生成文件路径")
                .build());

        res.add(Option.builder("h")
                .longOpt("help")
                .desc("[Dubbo Composer] Api Generator Helper")
                .build());
        return res;
    }

    public static Options getOptions() {
        Options options = new Options();
        getOptionList().forEach(option -> {
            options.addOption(option);
        });
        return options;
    }
}
