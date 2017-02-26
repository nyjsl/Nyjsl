package org.nyjsl.library.di.module;

import org.nyjsl.library.api.CommonService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by by on 2017/2/26.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }
}
