package com.example.distribution_sales_techfira.util;

import java.util.Random;

public class PurchaeUtil {

    public static String generatePurchaseNumber() {
        int number = new Random().nextInt(9000) + 1000; //it  Generates a 4-digit number after AP
        return "AP" + number;
    }

    private PurchaeUtil(){

    }
}
