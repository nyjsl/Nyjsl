package org.nyjsl.cache.provider;

import org.nyjsl.cache.Cache;
import org.nyjsl.cache.manager.CacheManager;
import org.nyjsl.cache.manager.DiskLruCacheManager;

/**
 * DiskCacheManagerProvider
 * 文件缓存Provider,这里可以提供
 * @param <T>
 */
public class DiskCacheManagerProvider<T extends Cache> implements CacheManagerProvider<T> {

    @Override
    public CacheManager<T> provideCacheManager(@CACHE_MANAGER int manager) {
       if (manager == MANAGER_LRU){
            return new DiskLruCacheManager<>();
       }else{
           return new DiskLruCacheManager<>();
       }
    }
}
