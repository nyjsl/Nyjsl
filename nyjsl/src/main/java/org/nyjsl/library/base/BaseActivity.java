package org.nyjsl.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.nyjsl.library.di.component.AppComponent;
import org.nyjsl.library.mvp.interfaces.IPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pc on 2017/2/17.
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity {

    public BaseApplication mApplication;

    private Unbinder mUnbinder;

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        mApplication.getAppManager().addActivity(this);
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册到事件主线
        setContentView(initView());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        componentInject();//依赖注入
        initData();
    }
    /**
     * 依赖注入的入口
     */
    protected void componentInject(){
        setupActivityComponent(mApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);
    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.getAppManager().removeActivity(this);
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mApplication = null;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected abstract View initView();
    protected abstract void initData();
}
