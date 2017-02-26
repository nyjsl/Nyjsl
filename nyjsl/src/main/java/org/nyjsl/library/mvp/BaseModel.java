package org.nyjsl.library.mvp;

import org.nyjsl.library.http.BaseCacheManager;
import org.nyjsl.library.http.BaseServiceManager;
import org.nyjsl.library.mvp.interfaces.IModel;

/**
 * Created by pc on 2017/2/9.
 */

public class BaseModel<S extends BaseServiceManager,C extends BaseCacheManager> implements IModel{

    protected S mBaseServiceManager;
    protected C mBaseCacheManager;

    public BaseModel(S baseServiceManager, C baseCacheManager) {
        this.mBaseServiceManager = baseServiceManager;
        this.mBaseCacheManager = baseCacheManager;
    }


    @Override
    public void onDestroy() {
        if (mBaseServiceManager != null) {
            mBaseServiceManager = null;
        }
        if (mBaseCacheManager != null) {
            mBaseCacheManager = null;
        }
    }
}
