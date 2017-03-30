package org.nyjsl.testpool.classloader;

/**
 * Created by pc on 2017/3/30.
 * 类的初始化步骤:
 *      在没有父类的情况下:
 *              1.类的静态属性
 *              2.类的静态代码块
 *              3.类的非静态属性
 *              4.类的非静态代码块
 *              5.构造方法
 *      在有父类的情况下
 *              1.父类的静态属性
 *              2.父类的静态代码块
 *              3.子类的静态属性
 *              4.子类的静态代码块
 *              5.父类的非静态属性
 *              6.父类的非静态代码块
 *              7.父类的构造方法
 *              8.子类的非静态属性
 *              9.子类的非静态代码块
 *              10.子类的构造方法
 */
public class TestClassLoader {

    public static void main(String[] args) {
        System.out.println(TestClass1.compileConstant);
        System.out.println(TestClass1.noFinal);
        System.out.println(TestClass2.runtimeConstant);

        System.out.println("fisrt:"+TestSingleton.getInstance().first);
        System.out.println("second:"+TestSingleton.getInstance().second);
    }
}
