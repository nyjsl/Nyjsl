package org.nyjsl.testpool.classloader;

/**
 * Created by pc on 2017/3/30.
 */

public class TestClass1 {
    /**
     * 编译常量,不会引起类的初始化
     */
    public static final int compileConstant = 6/2;
    /**
     * 去掉final 引用会引起类的初始化
     */
    public static int noFinal = 6/2;

    static{
        System.out.println("this TestClass1 bolck");
    }
}
