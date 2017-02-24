package org.nyjsl.library.di.module;

import org.nyjsl.library.base.AppManager;

import dagger.Module;

/**
 * Created by pc on 2017/2/17.
 */
@Module
public class ClientModule {

    private static final int TIME_OUT = 30;

    private AppManager mAppManager;

    public ClientModule(AppManager mAppManager) {
        this.mAppManager = mAppManager;
    }

//    @Singleton
//    @Provides
//    public Retrofit provideRetrfit(Retrofit.Builder builder, OkHttpClient client, HttpUrl baseUrl){
//        return builder.baseUrl(baseUrl).client(client).addCallAdapterFactory()
//    }
//

}
