package org.nyjsl.cache.provider;

import android.support.annotation.IntDef;

import org.nyjsl.cache.Cache;
import org.nyjsl.cache.manager.CacheManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  CacheManagerProvider
 * 提供不同种类的缓存策略
 * 现在只提供实现类DiskCacheManager
 * @param <T> 保存的数据
 */
public interface CacheManagerProvider<T extends Cache> {
    /**
     * 普通缓存类型
     */
    int MANAGER_NORMAL = 0;
    /**
     * 使用LRU算法的缓存
     */
    int MANAGER_LRU = 1;

    CacheManager<T> getCacheManager(@CACHE_MANAGER int manager);

    @IntDef({MANAGER_NORMAL,MANAGER_LRU})
    @Retention(RetentionPolicy.SOURCE)
    @interface CACHE_MANAGER{}

}
