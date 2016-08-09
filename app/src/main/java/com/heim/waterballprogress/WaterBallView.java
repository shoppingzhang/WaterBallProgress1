package com.heim.waterballprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.NumberFormat;

/**
 * Created by zxp on 2016/6/29 0029.
 */
public class WaterBallView extends View {

    private Paint mPaint;
    private Paint mPaint1;
    private int   mViewWidth;
    private int   mViewHeight;
    private float mR;
    private float mPaddingY;
    private float mX;
    private Path mPath = new Path();
    private RectF mRectF;
    private PointF mPointF = new PointF(0,0);
    private int mColor;
    private int mMax;
    private int mProgress;
    private float percent;  //百分比


    public WaterBallView(Context context) {
        super(context);
        init();
    }

    public WaterBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterBallView);
        mMax = typedArray.getInt(R.styleable.WaterBallView_max, 0);
        mProgress = typedArray.getInt(R.styleable.WaterBallView_progress, 0);
        int color = typedArray.getColor(R.styleable.WaterBallView_fillColor, 0);
        mColor = color;

    setMax(mMax);
    setProgress(mProgress);
    init();
}

    public WaterBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();          //圆的画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(50);

        mPaint1 = new Paint(); //填充的画笔
        mPaint1.setColor(mColor);
        mPaint1.setStrokeWidth(8);
        mPaint1.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;   //控件的宽高
        mViewHeight = h;

        mViewWidth = w - getPaddingLeft() - getPaddingRight();
        mViewHeight = h - getPaddingBottom() - getPaddingTop();

        mR = Math.min(mViewWidth, mViewHeight) * 0.4f;  //球的半径

        mRectF = new RectF(-mR, -mR, mR, mR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        percent= mProgress *1.0f/ mMax;
        canvas.translate(mViewWidth / 2, mViewHeight / 2);  //将坐标的原点移动到球的圆心
        canvas.drawCircle(0, 0, mR, mPaint);
        mPaddingY = mR * (1 - 2 * percent);      //水平面最左边的点的y坐标
        double cosAngle = Math.acos(mPaddingY / mR);
        mX = (float) (mR * Math.sin(cosAngle));  //水平面最左边的点的x坐标
        mPath.addArc(mRectF, 90 - (float) Math.toDegrees(cosAngle),
                (float) Math.toDegrees(cosAngle) * 2);
        mPath.moveTo(-mX, mPaddingY);  //起始点
        mPath.rQuadTo(mX / 2, -mR / 8, mX, 0);
        mPath.rQuadTo(mX / 2, mR / 8, mX, 0);
        canvas.drawPath(mPath, mPaint1);
        mPath.rewind();
        NumberFormat instance = NumberFormat.getPercentInstance();
        instance.setMinimumFractionDigits(1);
        textCener(Paint.Align.CENTER, new String[]{instance.format(percent)}, canvas, mPointF, mPaint);
    }

    protected void textCener(Paint.Align align, String[] strings, Canvas canvas,
                             PointF point, Paint paint) {
        mPaint.setTextAlign(align);
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float top = metrics.top;
        float bottom = metrics.bottom;
        int length = strings.length;
        float total = (length - 1) * (-top + bottom) + (-metrics.ascent + metrics.descent);
        float offset = total / 2 - bottom;
        for (int i = 0; i < length; i++) {
            float yAix = -(length - 1 - i) * (-top + bottom) + offset;
            canvas.drawText(strings[i], point.x, point.y + yAix, paint);

        }
    }

    protected void setProgress(int progress) {
        this.mProgress=progress;
        postInvalidate();
    }

    protected void setMax(int sum) {
         mMax=sum;
    }

    protected void setFillColor(int color) {
        mColor = color;
    }
}
