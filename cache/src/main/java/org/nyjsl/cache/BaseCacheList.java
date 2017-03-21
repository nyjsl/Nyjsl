package org.nyjsl.cache;

import android.content.Context;

import org.nyjsl.cache.factory.CacheProviderFactory;
import org.nyjsl.cache.provider.CacheManagerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by pc on 2017/3/20.
 */

public class BaseCacheList<K,V> extends BaseCache<V> {

    private final HashMap<K,V> cacheList =new HashMap<>();

    private BaseCacheList(Context context) {
        super(context);
    }

    public void put(K key,V t) {
        cacheList.put(key, t);
    }

    public ArrayList<V> getAll() {
        ArrayList<V> list = new ArrayList<>();
        Set<Map.Entry<K, V>> sets = cacheList.entrySet();
        for (Map.Entry<K, V> entry : sets) {
            list.add(entry.getValue());
        }
        return list;
    }
    public boolean containsKey(K key){
        return cacheList.containsKey(key);
    }

    public static final class Builder<K,V>{
        private BaseCacheList<K,V> cache;

        public Builder(Context context) {
            this.cache = new BaseCacheList<>(context);
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
         * must be called
         * @param klazz
         * @return
         */
        public Builder buildClass(Class<V> klazz){
            cache.klazz = klazz;
            return this;
        }

        public BaseCacheList<K,V> build()  {
            return cache;
        }
    }
}
