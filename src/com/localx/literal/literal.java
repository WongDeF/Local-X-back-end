package com.localx.literal;

import java.math.BigDecimal;

public class literal {
    static BigDecimal wxWallet = BigDecimal.valueOf(0.0);
    static BigDecimal zfbWallet = BigDecimal.valueOf(10);
    static BigDecimal cardWallet = BigDecimal.valueOf(20);
    // 字面量
    static void main() {
        int a = 10;
        System.out.println(a);
        double b = 10.1;
        System.out.println(b);
        char c = 'a';
        System.out.println(c);
        boolean d = true;
        System.out.println(d);
        String e = "hello world";
        System.out.println(e);
        System.out.println(e.length());
        printTotalMoney();
        wxWallet = wxWallet.add(BigDecimal.valueOf(10));
        printTotalMoney();
        wxWallet = wxWallet.subtract(BigDecimal.valueOf(2.07));
        printTotalMoney();
    }
    public static void printTotalMoney() {
        BigDecimal talMoney = wxWallet.add(zfbWallet).add(cardWallet);
        System.out.println("总共有：" + talMoney);
    }
}
