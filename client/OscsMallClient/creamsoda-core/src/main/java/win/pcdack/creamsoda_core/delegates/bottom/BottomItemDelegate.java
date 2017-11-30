package win.pcdack.creamsoda_core.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;


/**
 * Created by pcdack on 17-10-20.
 *
 */

public abstract class BottomItemDelegate extends CreamSodaDelegate implements View.OnKeyListener{
    private long mExitTime=0;
    private static final int EXIT_TIME=2000;
//
//    @Override
//    public boolean onBackPressedSupport() {
//        if (System.currentTimeMillis()-mExitTime <EXIT_TIME){
//            _mActivity.finish();
//        }else {
//            mExitTime=System.currentTimeMillis();
//            Toast.makeText(_mActivity, "双击退出应用", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

    @Override
    public void onResume() {
        super.onResume();
        final View rootView=getView();
        if (rootView!=null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis()-mExitTime)>EXIT_TIME){
                Toast.makeText(getContext(), "双击退出", Toast.LENGTH_SHORT).show();
                mExitTime=System.currentTimeMillis();
            }else {
                _mActivity.finish();
                if (mExitTime!=0)
                    mExitTime=0;
            }
            return true;
        }
        return false;

    }
}
