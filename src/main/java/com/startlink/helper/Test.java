package com.startlink.helper;

import com.startlink.helper.core.Config;
import com.startlink.helper.core.Dealer;

public class Test {
    public static void main(String[] args) {
        Config config = new Config(
                "/Users/zhouzihao/lab/startlink-user-api/target/",
                "/Users/zhouzihao/lab/output",
                "classes"
        );
        Dealer dealer = new Dealer(config);
        dealer.deal();
    }
}
