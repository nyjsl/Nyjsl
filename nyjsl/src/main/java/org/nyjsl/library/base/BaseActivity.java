package org.nyjsl.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.nyjsl.library.mvp.interfaces.IPresenter;

import javax.inject.Inject;

/**
 * Created by pc on 2017/2/17.
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity {

    public BaseApplication mApplication;

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
    }


}
