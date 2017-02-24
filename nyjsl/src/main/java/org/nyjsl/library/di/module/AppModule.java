package org.nyjsl.library.di.module;

import android.app.Application;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pc on 2017/2/17.
 */
@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    public Application provideApplication(){
        return this.mApplication;
    }

    @Provides
    public Gson provideGson(){
        return new Gson();
    }
}
