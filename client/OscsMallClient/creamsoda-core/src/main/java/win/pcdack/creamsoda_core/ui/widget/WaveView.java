package win.pcdack.creamsoda_core.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.ConvertUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.R;

/**
 * Created by pcdack on 17-12-4.
 *
 */

public class WaveView extends View {
    private int waveColor=0x880000aa;

    private int mViewHeight,mViewWidth;

    private Paint mPaint;
    //贝塞尔曲线
    private Path mPath;

    //一个波浪的长度
    private int mWaveLength = 1000;
    //波纹个数
    private int mWaveCount;
    //平移偏移量
    private int mOffset;
    //波纹的中间轴
    private int mCenterY;
    private float mCenterYOffset; //相对于View中间的位置
    //波纹的大小
    private float mWaveSize;
    //横坐标的偏移量
    private float speed;

    private DrawFilter mDrawFilter;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(context,attrs);
        mPath=new Path();
        initPaint();

    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs, R.styleable.WaveView,0,0);
        try {
            waveColor=typedArray.getColor(R.styleable.WaveView_waveColor,0x880000aa);
            mCenterYOffset=typedArray.getDimension(R.styleable.WaveView_waveHeightInScreen,0.0f);
            mWaveSize=typedArray.getFloat(R.styleable.WaveView_waveSize,1.0f);
            // TODO: 17-12-5 获取波数

        }finally {
            typedArray.recycle();
        }
    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(waveColor);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight=h;
        mViewWidth=w;
        //加1.5：至少保证波纹有2个，至少2个才能实现平移效果
        mWaveCount = (int) Math.round(mViewWidth / mWaveLength + 1.5);
        //获取一个波的波长
        mCenterY = mViewHeight/ 2+ConvertUtils.dp2px(mCenterYOffset);

    }
    public void setSpeed(int speed){
        this.speed=speed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        //从canvas层面去锯齿
        canvas.setDrawFilter(mDrawFilter);
        mPath.reset();
        mPath.moveTo(-mWaveLength+ mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            //正弦曲线
            mPath.quadTo((-mWaveLength * 3 / 4 )+(i * mWaveLength) + mOffset, mCenterY+ 60*mWaveSize, (-mWaveLength / 2)+(i * mWaveLength) + mOffset, mCenterY);
            mPath.quadTo((-mWaveLength / 4 )+(i * mWaveLength) + mOffset, mCenterY- 60*mWaveSize, i * mWaveLength + mOffset, mCenterY);
        }
        mPath.lineTo(mViewWidth, mViewHeight);
        mPath.lineTo(0, mViewHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    public void changePosition(){
        translatePosition();
    }

    private void translatePosition() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        if (speed==0){
            speed=1000;
        }
        animator.setDuration((long) speed);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

}
