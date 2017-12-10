package win.pcdack.creamsoda_core.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;


/**
 * Created by pcdack on 17-12-5.
 *
 */

public class SingleWaveView extends View {
    private static int WAVE_PAINT_COLOR;
    private static int FOCUS_PAINT_COLOR;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 30;
    private static final int OFFSET_Y = 0;
    // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 3;

    private float mCycleFactorW;
    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private int mXOffsetSpeedOne;

    private int mXOneOffset;

    private Paint mWavePaint;
    private Paint mFocusPaint;
    private DrawFilter mDrawFilter;

    public SingleWaveView(Context context) {
        this(context,null);
    }

    public SingleWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        WAVE_PAINT_COLOR=0xa800cef7;
        initOffset();
        initPaint();
    }

    private void initOffset() {
        mXOffsetSpeedOne= ConvertUtils.dp2px(TRANSLATE_X_SPEED_ONE);
    }

    private void initPaint() {
        mWavePaint=new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mFocusPaint=new Paint();
        mFocusPaint.setAntiAlias(true);
        mFocusPaint.setStyle(Paint.Style.FILL);
        mFocusPaint.setColor(FOCUS_PAINT_COLOR);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        // 用于保存原始波纹的y值
        mYPositions = new float[mTotalWidth];
        // 用于保存波纹一的y值
        mResetOneYPositions = new float[mTotalWidth];
        // 将周期定为view总宽度
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);
        // 根据view总宽度得出所有对应的y值
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        resetPositionY();
        for (int i = 0; i < mTotalWidth; i++) {
            // 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
            // 绘制第一条水波纹
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - 300, i,mTotalHeight,mWavePaint);
        }
        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0;
        }
        // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
        postInvalidate();
    }
    private void resetPositionY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);
    }

}
