package org.nyjsl.library.di.module;

import org.nyjsl.library.base.AppManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 2017/2/17.
 */
@Module
public class ClientModule {

    private static final int TIME_OUT = 15;

    private static final String BASE_URL = "";

    private AppManager mAppManager;

    public ClientModule(AppManager mAppManager) {
        this.mAppManager = mAppManager;
    }

    @Singleton
    @Provides
    AppManager provideAppManager(){
        return mAppManager;
    }

    /**
     * Retrofit2
     * @param builder
     * @param client
     * @param baseUrl
     * @return
     */
    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl baseUrl){
        return builder.baseUrl(baseUrl).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava2
                .addConverterFactory(GsonConverterFactory.create())        //Gson
                .build();
    }
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    /**
     * OkHttpClient
     * @param builder
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        return builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS).build();
    }
    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse(BASE_URL);
    }

}
