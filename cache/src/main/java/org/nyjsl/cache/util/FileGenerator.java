package org.nyjsl.cache.util;

import android.content.Context;

import org.nyjsl.cache.BaseCache;
import org.nyjsl.cache.BaseCacheList;
import org.nyjsl.cache.Cache;

import java.io.File;

/**
 * 文件名生成器
 */
public class FileGenerator {

    /**
     * 根据类名生成唯一的文件名,同一个类生成的文件名相同
     *  如果B继承A
     *  A a = new A();
     *  B b = new B();
     *  A c = new B();
     *  b c 相同, a与b c不同
     * @param t
     * @param <T> 类
     * @return
     */
    public static <T extends Cache> String generateUniqueFileName(T t) {
        Class klazz = ((BaseCache) t).getKlazz();
        String key = klazz.getSimpleName();
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        if (t instanceof BaseCacheList) {
            localFilename += "list";
        }
        return localFilename;
    }

    /**
     * 默认缓存文件夹
     */
    public static String DEFAULT_DISK_CACHE_DIR = "disk_cache";

    /**
     * 获取默认文件 缓存目录 disk_cache
     * @param context
     * @return
     */
    public static File getDefaultCacheDirectory(Context context) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + DEFAULT_DISK_CACHE_DIR);
    }


    /**
     * 获取类对应的缓存目录 /dis_cache/t
     * @param context
     * @return
     */
    public static <T extends Cache> File getTypedCacheDirrectory(Context context, T t) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + DEFAULT_DISK_CACHE_DIR + File.separator + generateUniqueFileName(t));
    }



}
