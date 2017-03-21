package org.nyjsl.cache.factory;

/**
 * Created by pc on 2017/3/20.
 */

import android.support.annotation.IntDef;

import org.nyjsl.cache.provider.CacheManagerProvider;
import org.nyjsl.cache.provider.DiskCacheManagerProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Factory for CacheManagerProvider
 */
public abstract class CacheProviderFactory {

    public static final int CACHE_PROVIDER_DISK = 0;
    public static final int CACHE_PROVIDER_DB = 1;

    public static CacheManagerProvider getCacheProvider(@CACHE_PROVIDER int provider){
        CacheManagerProvider result = null;
        if(provider == CACHE_PROVIDER_DISK){
            result = new DiskCacheManagerProvider<>();
        }else{
            result =  new DiskCacheManagerProvider<>();
        }
        return result;
    }


    @IntDef({CACHE_PROVIDER_DISK,CACHE_PROVIDER_DB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CACHE_PROVIDER{}

}
