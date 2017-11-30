package win.pcdack.creamsoda_core.ui.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

/**
 * Created by pcdack on 17-11-3.
 *
 */

public class CircleTextView extends android.support.v7.widget.AppCompatTextView{
    private final Paint PAINT;
    private final PaintFlagsDrawFilter FLAG_FILTER;
    public CircleTextView(Context context) {
        this(context,null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        PAINT=new Paint();
        FLAG_FILTER=new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG |Paint.FILTER_BITMAP_FLAG);
        PAINT.setColor(Color.RED);
        PAINT.setAntiAlias(true);
    }

    public final void setCircleTextViewColor(@ColorInt int color){
        PAINT.setColor(color);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width=getMeasuredWidth();
        final int height=getMeasuredHeight();
        final int max=Math.max(width,height);
        setMeasuredDimension(max,max);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(FLAG_FILTER);
        canvas.drawCircle(getWidth()/2
                ,getHeight()/2
                ,Math.max(getWidth(),getHeight())/2
                ,PAINT);
        super.onDraw(canvas);
    }
}
