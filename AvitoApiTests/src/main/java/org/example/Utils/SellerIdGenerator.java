package org.example.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class SellerIdGenerator {

    private SellerIdGenerator(){
    }

    public static int generateSellerId() {
        return ThreadLocalRandom.current().nextInt(111_111, 1_000_000);
    }
}
