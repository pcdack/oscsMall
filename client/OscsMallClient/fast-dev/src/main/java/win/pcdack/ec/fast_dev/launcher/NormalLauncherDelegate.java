package win.pcdack.ec.fast_dev.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.ec.fast_dev.type.TextType;
import win.pcdack.ec.fast_ec.R;
import win.pcdack.ec.fast_ec.R2;


/**
 * Created by pcdack on 17-11-11.
 *
 */

public abstract class NormalLauncherDelegate extends CreamSodaDelegate {
    @BindView(R2.id.icon_logo)
    IconTextView iconView;
    @BindView(R2.id.slogan_tv)
    TextView sloganTv;
    @BindView(R2.id.launcher_delegate_timer)
    TextView launcherTimer;
    @SuppressWarnings("unused")
    @OnClick(R2.id.launcher_delegate_timer)
    void onTimeClick(){
        if (disposable!=null)
            disposable.dispose();
        onTimeFinish();
    }

    public abstract Object iconTextOrResource();
    public abstract TextType sloganText();
    public abstract int delaySeconds();
    public abstract TextType timerText();
    public abstract void onTimeFinish();
    private Disposable disposable;
    @Override
    public Object setLayout() {
        return R.layout.delegate_normal_launcher;
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initIcon();
        initSlogan();
        initTime();
        int second = 1;
        final int delayTime=delaySeconds();
        Observable.interval(second, TimeUnit.SECONDS)
                .take(delayTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Long aLong) {
                        launcherTimer.setText("剩余" + (delayTime - aLong));
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        onTimeFinish();
                    }
                });
    }

    private void initTime() {
        if (timerText()!=null){
            TextType textType=timerText();
            launcherTimer.setTextColor(textType.getColor());
            launcherTimer.setTextSize(textType.getTextSize());
        }
    }


    private void initSlogan() {
        if (sloganText()!=null) {
            TextType type = sloganText();
            sloganTv.setText(new SpanUtils().append(type.getText()).setTypeface(type.getTypeface()).create());
            sloganTv.setTextColor(type.getColor());
            sloganTv.setTextSize(type.getTextSize());
        }
    }

    private void initIcon() {
        if (iconTextOrResource()!=null) {
            if (iconTextOrResource() instanceof TextType) {
                TextType textType= (TextType) iconTextOrResource();
                iconView.setText(textType.getText());
                iconView.setTextColor(textType.getColor());
                iconView.setTextSize(textType.getTextSize());
            } else if (iconTextOrResource() instanceof Integer)
                iconView.setBackgroundResource((int) iconTextOrResource());
        }
    }
}
