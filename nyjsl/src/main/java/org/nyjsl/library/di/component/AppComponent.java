package org.nyjsl.library.di.component;

import android.app.Application;

import com.google.gson.Gson;

import org.nyjsl.library.api.ServiceManager;
import org.nyjsl.library.base.AppManager;
import org.nyjsl.library.di.module.AppModule;
import org.nyjsl.library.di.module.ClientModule;
import org.nyjsl.library.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pc on 2017/2/17.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class})
public interface AppComponent {

    Application application();

    Gson gson();

    void inject(Application application);

    // ===========================华丽的分割线====================================

    AppManager appManager();

    // ==========================华丽的分割线====================================

    ServiceManager serviceManager();

}
