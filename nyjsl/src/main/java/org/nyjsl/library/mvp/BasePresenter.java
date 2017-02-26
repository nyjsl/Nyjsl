package org.nyjsl.library.mvp;

import org.greenrobot.eventbus.EventBus;
import org.nyjsl.library.BuildConfig;
import org.nyjsl.library.mvp.interfaces.IModel;
import org.nyjsl.library.mvp.interfaces.IPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by pc on 2017/2/9.
 */

public class BasePresenter<M extends IModel,V extends BaseView> implements IPresenter{

    protected final String TAG = this.getClass().getSimpleName();

    protected CompositeDisposable compositeDisposable;

    protected M mModel;
    protected V mView;


    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
        onStart();
    }

    @Override
    public void onStart() {
        if(useEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        if(useEventBus()){
            EventBus.getDefault().unregister(this);
        }
        disposeComposite();
        releaseObjects();
    }

    /**
     * 是否使用EventBus
     * @return
     */
    protected boolean useEventBus(){
        return BuildConfig.USE_EVENT_BUS;
    }

    protected void releaseObjects(){
        if(null != mModel){
            mModel.onDestroy();
            mModel = null;
        }
        mView = null;
        compositeDisposable = null;
    }

    protected void addDisposable(Disposable disposable){
        if(null == compositeDisposable){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void disposeComposite() {
        if(null != compositeDisposable){
            compositeDisposable.dispose();
        }
    }
}
