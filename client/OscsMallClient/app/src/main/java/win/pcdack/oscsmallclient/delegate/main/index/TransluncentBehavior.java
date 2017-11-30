package win.pcdack.oscsmallclient.delegate.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import win.pcdack.creamsoda_core.ui.recycler.RgbValue;
import win.pcdack.creamsoda_core.util.color.HaxToNumUtils;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-10-22.
 *
 */
//其中Behavior中的泛型就是Taget View（目标控件）
@SuppressWarnings("unused")
public class TransluncentBehavior extends CoordinatorLayout.Behavior<Toolbar>{
    //记录Y轴滑动的距离
    private int mDistanceY=0;
    //颜色变化的速度
    private static final int SHOW_SPEED=3;
    private static boolean isFirst=false;
    //定义变化的颜色 深天蓝
    private static RgbValue RGB_VALUE=null;
    public TransluncentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isFirst){
            int red= HaxToNumUtils.getRed(ContextCompat.getColor(context, R.color.background_color));
            int green= HaxToNumUtils.getGreen(ContextCompat.getColor(context, R.color.background_color));
            int blue= HaxToNumUtils.getBlue(ContextCompat.getColor(context, R.color.background_color));
            RGB_VALUE= RgbValue.create(red,green,blue);
            isFirst=true;
        }

    }
    //依赖控件，在本例中表示的是RecyclerView
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId()== R.id.index_rv;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        mDistanceY+=dy;
        //toolbar的高度
        final int toolbarW=child.getBottom();

        if (mDistanceY>0 && mDistanceY<=toolbarW){
            //渐变颜色
            final float scale=(float) mDistanceY/toolbarW;
            final int alpha= (int) (scale*255);
            child.setBackgroundColor(Color.argb(alpha,RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.yellow()));
        }else if (mDistanceY > toolbarW){
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.yellow()));
        }
    }
}
