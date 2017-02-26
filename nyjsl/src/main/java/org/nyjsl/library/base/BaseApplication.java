package org.nyjsl.library.base;

import android.app.Application;

import org.nyjsl.library.di.component.AppComponent;
import org.nyjsl.library.di.component.DaggerAppComponent;
import org.nyjsl.library.di.module.AppModule;
import org.nyjsl.library.di.module.ClientModule;

/**
 * Created by pc on 2017/2/17.
 */

public abstract class BaseApplication extends Application{

    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private ClientModule mClientModule;

    public ClientModule getClientModule() {
        return mClientModule;
    }

    private AppManager mAppManager;

    public AppManager getAppManager() {
        return mAppManager;
    }

    private AppModule mAppModule;

    public AppModule getAppModule(){
        return mAppModule;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppModule = new AppModule(this);
        mAppManager = new AppManager(this);
        mClientModule = new ClientModule(mAppManager);
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(mAppModule)
                .clientModule(mClientModule)
                .build();
        appComponent.inject(this);
    }

}
