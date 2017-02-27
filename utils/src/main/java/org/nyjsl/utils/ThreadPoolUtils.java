package org.nyjsl.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {

    /** recommend default thread pool size according to system available processors, {@link #getDefaultThreadPoolSize()} **/
    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

    private ThreadPoolUtils() {
        throw new AssertionError();
    }

    /**
     * get recommend default thread pool size
     * 
     * @return if 2 * availableProcessors + 1 less than 8, return it, else return 8;
     * @see {@link #getDefaultThreadPoolSize(int)} max is 8
     */
    public static int getDefaultThreadPoolSize() {
        return getDefaultThreadPoolSize(8);
    }

    /**
     * get recommend default thread pool size
     * 
     * @param max
     * @return if 2 * availableProcessors + 1 less than max, return it, else return max;
     */
    public static int getDefaultThreadPoolSize(int max) {
        int availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
        return availableProcessors > max ? max : availableProcessors;
    }

    public static ExecutorService singleThreadPool(){
        return Executors.newSingleThreadExecutor();
    }

    public static ExecutorService fixedThreadPool(){
        return Executors.newFixedThreadPool(getDefaultThreadPoolSize());
    }

    public static ExecutorService cachedThreadPool(){
        return Executors.newCachedThreadPool();
    }

}