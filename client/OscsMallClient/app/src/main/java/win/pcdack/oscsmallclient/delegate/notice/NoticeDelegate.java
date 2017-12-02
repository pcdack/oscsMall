package win.pcdack.oscsmallclient.delegate.notice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-12-2.
 *
 */

public class NoticeDelegate  extends CreamSodaDelegate{
    // TODO: 17-12-2 由于时间原因并没有做
    @OnClick(R.id.back_icon)
    void onBackClick(){
        getSupportDelegate().pop();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_notice_empty;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }
}
