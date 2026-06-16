package com.localx.test;

import java.util.Scanner;

public class Test {
    public static void main(String [] args) {
        //生成一个7位随机数字
        int num = (int)(Math.random() * 1000000);
        System.out. println("生成的彩票是：" + num);
        //键盘输入一个7位数表示拥护的彩票
        System.out.println("请输入7位数的彩票：");
        Scanner sc = new Scanner(System.in);
        int inputNum = sc.nextInt();
        //判断是否中奖
        if(num == inputNum) {
            System.out.println("恭喜你，中奖了！");
        } else {
            System.out.println("很遗憾，未中奖！");
        }
    }
}
