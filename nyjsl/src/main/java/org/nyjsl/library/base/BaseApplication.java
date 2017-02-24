package org.nyjsl.library.base;

import android.app.Application;

import org.nyjsl.library.di.module.AppModule;

/**
 * Created by pc on 2017/2/17.
 */

public abstract class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        mAppModule = new AppModule(this);
//        DaggerAppComponent.builder().appModule(mAppModule).build().inject(this);
    }
    private AppModule mAppModule = null;
    public AppModule getAppModule(){
        return mAppModule;
    }
}
