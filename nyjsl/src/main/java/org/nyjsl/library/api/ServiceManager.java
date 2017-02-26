package org.nyjsl.library.api;

import org.nyjsl.library.http.BaseServiceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by by on 2017/2/26.
 */
@Singleton
public class ServiceManager implements BaseServiceManager {

    public CommonService getCommonService() {
        return commonService;
    }

    private CommonService commonService;

    @Inject
    public ServiceManager(CommonService commonService) {
        this.commonService = commonService;
    }
}
