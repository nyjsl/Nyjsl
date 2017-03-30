package org.nyjsl.testpool.classloader;

import java.util.Random;

/**
 * Created by pc on 2017/3/30.
 */

public class TestClass2 {
    /**
     * 运行时常量,直接调用会引起类的初始化
     */
    public static final int runtimeConstant = new Random().nextInt(100)/2;

    static{
        System.out.println("this is TestClass2 block");
    }
}
