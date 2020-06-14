package com.startlink.helper;


import com.startlink.helper.core.Config;
import com.startlink.helper.core.Dealer;
import com.startlink.helper.tools.OptionHelper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Application {
    public static void main(String[] args) {
        // 获取参数
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        Options options = OptionHelper.getOptions();

        CommandLine result = null;
        try {
            result = parser.parse(options, args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            formatter.printHelp("DubboComposer Api Gen Helper", options, true);
            System.exit(1);
        }

        // 参数辨认
        // 如果存在 -h --help 参数
        if (result.hasOption("h")) {
            formatter.printHelp("DubboComposer Api Gen Helper", options, true);
            System.exit(0);
        }

        Config config = Config.getInstance(result);
        // tmp 测试打印
//        System.out.println(config);
        // 请求主方法
        Dealer dealer = new Dealer(config);
        dealer.deal();
    }
}
