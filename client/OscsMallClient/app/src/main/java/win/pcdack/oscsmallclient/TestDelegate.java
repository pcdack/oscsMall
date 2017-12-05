package win.pcdack.oscsmallclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;

/**
 * Created by pcdack on 17-11-10.
 * 测试Delegate
 */

public class TestDelegate extends CreamSodaDelegate {
    @Override
    public Object setLayout() {
        return R.layout.sign_in_nice_delegate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
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
