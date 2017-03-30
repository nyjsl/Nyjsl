package org.nyjsl.testpool.classloader;

/**
 * Created by pc on 2017/3/30.
 */

public class TestSingleton {
    /**
     * 如果这一句在最前面则输出结果为first = 1 second = 0 ,原因跟初始化顺序有关
     */
//    private static TestSingleton instance = new TestSingleton();

    public static int first;
    public static int second = 0;

    private static TestSingleton instance = new TestSingleton();

    private TestSingleton(){
        first++;
        second++;
    }

    public static TestSingleton getInstance() {
        return instance;
    }
}
