package win.pcdack.oscsmallclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.net.RestClient;
import win.pcdack.creamsoda_core.net.RestCreator;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;

/**
 * Created by pcdack on 17-11-10.
 * 测试Delegate
 */

public class TestDelegate extends CreamSodaDelegate {
    @Override
    public Object setLayout() {
        return R.layout.test_delegate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("https://www.baidu.com")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getProxyActivity(), "成功", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();
//        rxGetTest();
//        rxGetTest2();
    }
// // TODO: 17-11-10 RxGet测试
//    private void rxGetTest2() {
//        RxRestClient.builder()
//                .url("https://www.baidu.com")
//                .build()
//                .get()
//                 .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull String s) {
//                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//
//    private void rxGetTest() {
//        final WeakHashMap<String,Object> params=new WeakHashMap<>();
//        final Observable<String> observable= RestCreator.getRxRestService()
//                .get("https://www.baidu.com",params);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull String s) {
//                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
