package org.nyjsl.cache;

import android.content.Context;

import org.nyjsl.cache.factory.CacheProviderFactory;
import org.nyjsl.cache.manager.CacheManager;
import org.nyjsl.cache.provider.CacheManagerProvider;

/**
 * Created by pc on 2017/3/20.
 */

public class BaseCache<T> implements Cache<T> {


    protected transient Class klazz;

    public Class getKlazz() {
        return klazz;
    }

    private T t = null;

    public T getT() {
        return t;
    }

    protected transient Context context;

    protected @CacheProviderFactory.CACHE_PROVIDER int provider = CacheProviderFactory.CACHE_PROVIDER_DISK;

    protected @CacheManagerProvider.CACHE_MANAGER int manager = CacheManagerProvider.MANAGER_LRU;

    protected BaseCache(Context context) {
        this.context = context;
    }

    @Override
    public void create() {
        if(null != getKlazz()){
            final CacheManager cacheManager = getCacheManager();
            cacheManager.create(context,this);
        }
    }

    @Override
    public Cache<T> read() {
        if(null == getKlazz()){
            return null;
        }
        final CacheManager cacheManager = getCacheManager();
        return (BaseCache<T>) cacheManager.read(context,this);
    }

    @Override
    public void update() {
        create();
    }

    @Override
    public void delete() {
        final CacheManager cacheManager = getCacheManager();
        cacheManager.delete(context,this);
    }

    private CacheManager getCacheManager() {
        final CacheManagerProvider cacheProvider = CacheProviderFactory.buildCacheProvider(provider);
        return cacheProvider.provideCacheManager(manager);
    }

    public static final class Builder<T>{
        private BaseCache<T> cache;

        public Builder(Context context) {
            this.cache = new BaseCache<>(context);
        }

        public static Builder newBuilder(Context context){
            return new Builder(context);
        }

        public Builder buildProvider(@CacheProviderFactory.CACHE_PROVIDER int provider){
            cache.provider = provider;
            return this;
        }
        public Builder buildManager(@CacheManagerProvider.CACHE_MANAGER int manager){
            cache.manager = manager;
            return this;
        }

        /**
         * must called
         * @param t
         * @return
         */
        public Builder buildT(T t){
            cache.t = t;
            return buildClass((Class<T>) t.getClass());

        }
        /**
         * must called
         * @param klazz
         * @return
         */
        public Builder buildClass(Class<T> klazz){
            cache.klazz = klazz;
            return this;
        }


        public BaseCache<T> build()  {
            return cache;
        }
    }

}
