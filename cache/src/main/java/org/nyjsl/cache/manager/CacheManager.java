package org.nyjsl.cache.manager;

import android.content.Context;

import org.nyjsl.cache.Cache;

/**
 * Created by pc on 2017/3/20.
 */

public interface CacheManager<T extends Cache> {
    /**
     * 保存对应的数据T到缓存
     * @param t
     */
    void create(Context context, T t);

    /**
     *从缓存读取数据T
     * @return
     */
    T read(Context context, T t);

    /**
     * 更新缓存中的数据T
     * @param t
     */
    void update(Context context, T t);

    /**
     * 删除缓存中的数据T
     * @param context
     */
    void delete(Context context, T t);

}
