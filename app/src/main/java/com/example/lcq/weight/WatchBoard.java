package com.example.lcq.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.lcq.R;
import com.example.lcq.utils.DeviceInfo;

import java.util.Calendar;

public class WatchBoard extends View {
    public WatchBoard(Context context) {
        super(context);
    }

    public WatchBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public WatchBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public WatchBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private float mRadius; // 圆形半径
    private float mPadding; // 边距
    private float mTextSize; // 文字大小
    private float mHourPointWidth; // 时针宽度
    private float mMinutePointWidth; // 分针宽度
    private float mSecondPointWidth; // 秒针宽度
    private float mPointRadius; // 指针圆角
    private float mPointEndLength; // 指针末尾长度
    private int mHourPointColor; // 时针的颜色
    private int mMinutePointColor; // 分针的颜色
    private int mSecondPointColor; // 秒针的颜色
    private int mColorLong; // 长线的颜色
    private int mColorShort; // 短线的颜色
    private Paint mPaint; // 画笔
    private PaintFlagsDrawFilter mDrawFilter; // 为画布设置抗锯齿
    int width = 0;

    private void init(AttributeSet attrs) {
        // 获取属性
        obtainStyledAttrs(attrs);
        // 初始化画笔
        initPaint();
        // 为画布实现抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        // 测量手机的宽度
        int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
        // 默认和屏幕的宽高最小值相等
        width = Math.min(widthPixels, heightPixels);
    }

    /**
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WatchBoard);
        mPadding = typedArray.getDimension(R.styleable.WatchBoard_wb_padding, DeviceInfo.dp2px(getContext(), 10));
        mTextSize = typedArray.getDimension(R.styleable.WatchBoard_wb_text_size, DeviceInfo.dp2px(getContext(), 16));
        mHourPointWidth = typedArray.getDimension(R.styleable.WatchBoard_wb_hour_pointer_width, DeviceInfo.dp2px(getContext(), 5));
        mMinutePointWidth = typedArray.getDimension(R.styleable.WatchBoard_wb_minute_pointer_width, DeviceInfo.dp2px(getContext(), 3));
        mSecondPointWidth = typedArray.getDimension(R.styleable.WatchBoard_wb_second_pointer_width, DeviceInfo.dp2px(getContext(), 2));
        mPointRadius = typedArray.getDimension(R.styleable.WatchBoard_wb_pointer_corner_radius, DeviceInfo.dp2px(getContext(), 10));
        mPointEndLength = typedArray.getDimension(R.styleable.WatchBoard_wb_pointer_end_length, DeviceInfo.dp2px(getContext(), 10));
        mHourPointColor = typedArray.getColor(R.styleable.WatchBoard_wb_hour_pointer_color, Color.BLACK);
        mMinutePointColor = typedArray.getColor(R.styleable.WatchBoard_wb_minute_pointer_color, Color.BLACK);
        mSecondPointColor = typedArray.getColor(R.styleable.WatchBoard_wb_second_pointer_color, Color.RED);
        mColorLong = typedArray.getColor(R.styleable.WatchBoard_wb_scale_long_color, Color.argb(225, 0, 0, 0));
        mColorShort = typedArray.getColor(R.styleable.WatchBoard_wb_scale_short_color, Color.argb(125, 0, 0, 0)); // 一定要回收 typedArray.recycle(); }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 传入相同的数width，height,确保是正方形背景
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    // 这里不用管测量模式是什么，因为咱们有屏幕短边保底，只取其中一个小值即可。测量宽高和屏幕短边作对比，返回最小值
    private int measureSize(int measureSpec) {
        int size = MeasureSpec.getSize(measureSpec);
        width = Math.min(width, size);
        return width;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (Math.min(w, h) - mPadding) / 2;
        mPointEndLength = mRadius / 6; // 设置成半径的六分之一
    }


    // 绘制半径圆
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, width / 2, mRadius, mPaint);
    }

    /**
     * 绘制刻度和数字
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        mPaint.setStrokeWidth(DeviceInfo.dp2px(getContext(), 1));
        int lineWidth;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(DeviceInfo.dp2px(getContext(), 1.5f));
                mPaint.setColor(mColorLong);
                lineWidth = 40;
            } else {
                lineWidth = 30;
                mPaint.setColor(mColorShort);
                mPaint.setStrokeWidth(DeviceInfo.dp2px(getContext(), 1));
            }
            canvas.drawLine(width / 2, mPadding, width / 2, mPadding + lineWidth, mPaint);
            canvas.rotate(6, width / 2, width / 2);
        }
        mPaint.setStrokeWidth(DeviceInfo.dp2px(getContext(), 1));
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(DeviceInfo.dp2px(getContext(), 1.5f));
                mPaint.setColor(mColorLong);
                lineWidth = 40;
                // 这里是字体的绘制
                mPaint.setTextSize(mTextSize);
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                mPaint.setColor(Color.BLACK);
                canvas.drawText(text, width / 2 - textBound.width() / 2, textBound.height() + DeviceInfo.dp2px(getContext(), 5) + lineWidth + mPadding, mPaint);
            } else {
                lineWidth = 30;
                mPaint.setColor(mColorShort);
                mPaint.setStrokeWidth(DeviceInfo.dp2px(getContext(), 1));
            }
            canvas.drawLine(width / 2, mPadding, width / 2, mPadding + lineWidth, mPaint);
            canvas.rotate(6, width / 2, width / 2);
        }
    }

    /**
     * 绘制指针和远点
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        // 时
        int minute = calendar.get(Calendar.MINUTE);
        // 分
        int second = calendar.get(Calendar.SECOND);
        // 秒 // 转过的角度
        float angleHour = (hour + (float) minute / 60) * 360 / 12;
        float angleMinute = (minute + (float) second / 60) * 360 / 60;
        int angleSecond = second * 360 / 60;
        // 绘制时针
        canvas.save();
        canvas.rotate(angleHour, width / 2, width / 2);
        // 旋转到时针的角度
        RectF rectHour = new RectF(width / 2 - mHourPointWidth / 2, width / 2 - mRadius * 3 / 5, width / 2 + mHourPointWidth / 2, width / 2 + mPointEndLength);
        mPaint.setColor(mHourPointColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mHourPointWidth);
        canvas.drawRoundRect(rectHour, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        // 绘制分针
        canvas.save();
        canvas.rotate(angleMinute, width / 2, width / 2);
        // 旋转到分针的角度
        RectF rectMinute = new RectF(width / 2 - mMinutePointWidth / 2, width / 2 - mRadius * 3.5f / 5, width / 2 + mMinutePointWidth / 2, width / 2 + mPointEndLength);
        mPaint.setColor(mMinutePointColor);
        mPaint.setStrokeWidth(mMinutePointWidth);
        canvas.drawRoundRect(rectMinute, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        // 绘制分针
        canvas.save();
        canvas.rotate(angleSecond, width / 2, width / 2);
        // 旋转到分针的角度
        RectF rectSecond = new RectF(width / 2 - mSecondPointWidth / 2, width / 2 - mRadius + DeviceInfo.dp2px(getContext(), 10), width / 2 + mSecondPointWidth / 2, width / 2 + mPointEndLength);
        mPaint.setStrokeWidth(mSecondPointWidth);
        mPaint.setColor(mSecondPointColor);
        canvas.drawRoundRect(rectSecond, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        // 绘制原点
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, width / 2, mSecondPointWidth * 4, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        // 绘制半径圆
        drawCircle(canvas);
        // 绘制刻度尺
        drawScale(canvas);
        // 绘制指针
        drawPointer(canvas);
        // 每一秒刷新一次
        postInvalidateDelayed(1000); }
    }

