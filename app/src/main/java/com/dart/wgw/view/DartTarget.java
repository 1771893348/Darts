package com.dart.wgw.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.dart.wgw.R;
import com.dart.wgw.util.SizeUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Author:Admin
 * Time:2019/5/9 10:17
 * 描述：
 */
public class DartTarget extends View {


    private float mRadius; // 圆形半径
    private float mPadding; // 边距
    private float mTextSize; // 文字大小
    private float mHourPointWidth; // 时针宽度
    private float mMinutePointWidth; // 分针宽度
    private float mSecondPointWidth; // 秒针宽度
    private float mPointRadius;   // 指针圆角
    private float mPointEndLength; // 指针末尾长度

    private int mHourPointColor;  // 时针的颜色
    private int mMinutePointColor;  // 分针的颜色
    private int mSecondPointColor;  // 秒针的颜色
    private int mColorLong;    // 长线的颜色
    private int mColorShort;   // 短线的颜色

    private Paint mPaint; // 画笔
    private PaintFlagsDrawFilter mDrawFilter; // 为画布设置抗锯齿

    private int width; // 钟表的边长

    private String[] scoreArea={"20","1","18","4","13","6","10","15","2","17","3","19","7","16","8","11","14","9","12","5"};
    private int scoreSerial=-1;
    private int areaSerial = -1;
    private int mScore = -1;
    private int dartX = 0;
    private Bitmap dartBitmap;
    /**
     * 格式化小数点
     */
    private DecimalFormat dff;
    private RectF oval;
    private ArrayList<Integer> colors = new ArrayList<>();

    public DartTarget(Context context) {
        this(context, null);
    }

    public DartTarget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DartTarget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取属性
        obtainStyledAttrs(attrs);
        //初始化画笔
        initPaint();
        dartBitmap = turnDrawable(R.drawable.darts1);


        // 为画布实现抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        //测量手机的宽度
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;

        // 默认和屏幕的宽高最小值相等
        width = Math.min(widthPixels, heightPixels);
        dff = new DecimalFormat("0.0");
        for (int i = 1; i <= 40; i++){
            int r= (new Random().nextInt(100)+10)*i;
            int g= (new Random().nextInt(100)+10)*3*i;
            int b= (new Random().nextInt(100)+10)*2*i;
            int color = Color.rgb(r,g,b);
            if(Math.abs(r-g)>10&&Math.abs(r-b)>10&&Math.abs(b-g)>10){
                colors.add(color);
            }
        }
    }
    public void setDartX(int x){
        dartX = x;
    }
    private int count=10;
    public void setHighlight(String score,String area){
        if (null==score||score.equals("")){
            score="0";
        }
        mScore = Integer.parseInt(score);
        if (mScore%25!=0){
            for (int i=0;i<scoreArea.length;i++){
                if (scoreArea[i].equals(score)){
                    scoreSerial = i;
                    break;
                }
            }
            switch (area){
                case "a":
                    areaSerial = 1;
                    break;
                case "b":
                    areaSerial = 2;
                    break;
                case "c":
                    areaSerial = 3;
                    break;
                case "d":
                    areaSerial = 4;
                    break;
            }
        }else {
            scoreSerial=-1;
            areaSerial = -1;
        }
        count=10;
        new Thread(()->{
            while (count>0){
                postInvalidate();
                count--;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void setHighlight(String score,String area,int light){
        if (null==score||score.equals("")){
            score="0";
        }
        mScore = Integer.parseInt(score);
        if (mScore%25!=0){
            for (int i=0;i<scoreArea.length;i++){
                if (scoreArea[i].equals(score)){
                    scoreSerial = i;
                    break;
                }
            }
            switch (area){
                case "a":
                    areaSerial = 1;
                    break;
                case "b":
                    areaSerial = 2;
                    break;
                case "c":
                    areaSerial = 3;
                    break;
                case "d":
                    areaSerial = 4;
                    break;
            }
        }else {
            scoreSerial=-1;
            areaSerial = -1;
        }
        dartX = dartX+20;
        count=light*2;
        if (count>0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (count>0){

                        postInvalidate();
                        count--;
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }else {
            postInvalidate();
        }

    }
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    /**
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WatchBoard);
        mPadding = typedArray.getDimension(R.styleable.WatchBoard_wb_padding, DptoPx(10));
        mTextSize = typedArray.getDimension(R.styleable.WatchBoard_wb_text_size, SptoPx(16));
        mHourPointWidth = typedArray.getDimension(R.styleable.WatchBoard_wb_hour_pointer_width, DptoPx(5));
        mMinutePointWidth = typedArray.getDimension(R.styleable.WatchBoard_wb_minute_pointer_width, DptoPx(3));
        mSecondPointWidth = typedArray.getDimension(R.styleable.WatchBoard_wb_second_pointer_width, DptoPx(2));
        mPointRadius = typedArray.getDimension(R.styleable.WatchBoard_wb_pointer_corner_radius, DptoPx(10));
        mPointEndLength = typedArray.getDimension(R.styleable.WatchBoard_wb_pointer_end_length, DptoPx(10));

        mHourPointColor = typedArray.getColor(R.styleable.WatchBoard_wb_hour_pointer_color, Color.BLACK);
        mMinutePointColor = typedArray.getColor(R.styleable.WatchBoard_wb_minute_pointer_color, Color.BLACK);
        mSecondPointColor = typedArray.getColor(R.styleable.WatchBoard_wb_second_pointer_color, Color.RED);
        mColorLong = typedArray.getColor(R.styleable.WatchBoard_wb_scale_long_color, Color.argb(225, 0, 0, 0));
        mColorShort = typedArray.getColor(R.styleable.WatchBoard_wb_scale_short_color, Color.argb(125, 0, 0, 0));

        // 一定要回收
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (Math.min(w, h) - mPadding) / 2;
        mPointEndLength = mRadius / 6; // 设置成半径的六分之一
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int i = measureSize(widthMeasureSpec);
        int j = measureSize(heightMeasureSpec);
        Log.e("TAG", "WatchBoard onMeasure()i+++j ==" + i + "+++" + j);
//        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
//                width - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);

        setMeasuredDimension(i, j);
    }

    // 测量宽高和屏幕作对比
    private int measureSize(int measureSpec) {
        int size = MeasureSpec.getSize(measureSpec);
        width = Math.min(width, size);
        Log.e("TAG", "WatchBoard measureSize() width == " + width);
        return width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        // 绘制半径圆
        drawCircle(canvas);
        printScale(canvas);
        paintPie(canvas);
        paintDart(canvas);
//        // 绘制指针
//        printPointer(canvas);
//        // 每一秒刷新一次
//        postInvalidateDelayed(1000);
    }

    private void printPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);// 时
        int minute = calendar.get(Calendar.MINUTE);// 分
        int second = calendar.get(Calendar.SECOND);// 秒
        // 转过的角度
        float angleHour = (hour + (float) minute / 60) * 360 / 12;
        float angleMinute = (minute + (float) second / 60) * 360 / 60;
        int angleSecond = second * 360 / 60;

        // 绘制时针
        canvas.save();
        canvas.rotate(angleHour,width/2,width/2); // 旋转到时针的角度
        RectF rectHour = new RectF(width/2 -mHourPointWidth / 2, width/2 -mRadius * 3 / 5, width/2 +mHourPointWidth / 2, width/2 + mPointEndLength);
        mPaint.setColor(mHourPointColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mHourPointWidth);
        canvas.drawRoundRect(rectHour, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        // 绘制分针
        canvas.save();
        canvas.rotate(angleMinute,width/2,width/2); // 旋转到分针的角度
        RectF rectMinute = new RectF(width/2-mMinutePointWidth / 2, width/2-mRadius * 3.5f / 5, width/2+mMinutePointWidth / 2, width/2+mPointEndLength);
        mPaint.setColor(mMinutePointColor);
        mPaint.setStrokeWidth(mMinutePointWidth);
        canvas.drawRoundRect(rectMinute, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        // 绘制分针
        canvas.save();
        canvas.rotate(angleSecond,width/2,width/2); // 旋转到分针的角度
        RectF rectSecond = new RectF(width/2-mSecondPointWidth / 2, width/2-mRadius + DptoPx(10), width/2+mSecondPointWidth / 2, width/2+mPointEndLength);
        mPaint.setStrokeWidth(mSecondPointWidth);
        mPaint.setColor(mSecondPointColor);
        canvas.drawRoundRect(rectSecond, mPointRadius, mPointRadius, mPaint);
        canvas.restore();

        // 绘制原点
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/2, width/2, mSecondPointWidth * 4, mPaint);
    }

    private void printScale(Canvas canvas) {
        mPaint.setStrokeWidth(SizeUtils.Dp2Px(getContext(), 1));
        int lineWidth=0;
        for (int i = 0; i < 60; i++) {
            if (i % 3 == 0) {
                mPaint.setStrokeWidth(SizeUtils.Dp2Px(getContext(), 1.5f));
                mPaint.setColor(mColorLong);
//                lineWidth = 40;
                mTextSize = width/24;
                mPaint.setTextSize(mTextSize);
                String text = scoreArea[i / 3];//((i / 3) == 0 ? 20 : (i / 3)) + ""
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                mPaint.setColor(Color.BLACK);
                canvas.drawText(text, width / 2 - textBound.width() / 2, textBound.height() + mPadding, mPaint);

            }
//            else {
//                lineWidth = 30;
//                mPaint.setColor(mColorShort);
//                mPaint.setStrokeWidth(SizeUtils.Dp2Px(getContext(), 1));
//            }
//            canvas.drawLine(width / 2, mPadding, width / 2, mPadding + lineWidth, mPaint);
            canvas.rotate(6, width / 2, width / 2);
        }
    }
    private void paintPie(final Canvas mCanvas) {
            int i = 1;
            float currentAngle = -99f;
            oval = new RectF(0, 0, width, width);
            int side = width;
            while (i<=20) {
                int num = 1;
                float needDrawAngle = num * 1.0f / 20 * 360;
                if (needDrawAngle >= 0) {


                    mPaint.setColor(i%2==0?colors.get(1):colors.get(2));
                    if (i == scoreSerial+1 && areaSerial==4&&count%2==0){
                        mPaint.setColor(Color.GREEN);
                    }
                    oval = new RectF(side/12, side/12, side-side/12, side-side/12);
                    mCanvas.drawArc(oval, currentAngle, needDrawAngle - 1, true, mPaint);

                    mPaint.setColor(i%2==0?colors.get(3):colors.get(4));
                    if (i == scoreSerial+1 && areaSerial==3&&count%2==0){
                        mPaint.setColor(Color.GREEN);
                    }
                    oval = new RectF(side/8, side/8, side-side/8, side-side/8);
                    mCanvas.drawArc(oval, currentAngle, needDrawAngle - 1, true, mPaint);

                    mPaint.setColor(i%2==0?colors.get(5):colors.get(6));
                    if (i == scoreSerial+1 && areaSerial==2&&count%2==0){
                        mPaint.setColor(Color.GREEN);
                    }
                    oval = new RectF(side/4, side/4, side-side/4, side-side/4);
                    mCanvas.drawArc(oval, currentAngle, needDrawAngle - 1, true, mPaint);

                    mPaint.setColor(i%2==0?colors.get(7):colors.get(8));
                    if (i == scoreSerial+1 && areaSerial==1&&count%2==0){
                        mPaint.setColor(Color.GREEN);
                    }
                    oval = new RectF(side*7/24, side*7/24, side-side*7/24, side-side*7/24);
                    mCanvas.drawArc(oval, currentAngle, needDrawAngle - 1, true, mPaint);


//                    drawText(mCanvas, textAngle, kinds, needDrawAngle);
//                    drawCenterText(mCanvas, centerTitle, 0, 0, mTextPaint);

                }

                //  System.out.println("currentAngle: " + currentAngle + "needDrawAngle:" + needDrawAngle + "animatedValue:" + animatedValue);
                currentAngle = currentAngle + needDrawAngle;
                i++;
            }
            mPaint.setColor(Color.RED);
            if (mScore==25&&count%2==0){
                mPaint.setColor(Color.GREEN);
            }
            mPaint.setAlpha(255);
            mCanvas.drawCircle(width / 2, width / 2, side / 24, mPaint);

            mPaint.setColor(Color.BLACK);
            if (mScore==50&&count%2==0){
                mPaint.setColor(Color.GREEN);
            }
            mPaint.setAlpha(255);
            mCanvas.drawCircle(width / 2, width / 2, side / 48, mPaint);

    }
    Matrix matrix = new Matrix();
    private void paintDart(final Canvas mCanvas){
        int y = dartX;
        matrix.setRotate(45,0,dartBitmap.getHeight()/2);
        matrix.postTranslate(dartX,y);
        Log.d("wgw_paintDart","===="+dartX);
        mCanvas.drawBitmap(dartBitmap,matrix,mPaint);
    }
    private void drawCircle(Canvas canvas) {

        if (mScore == 0&&count%2==0){
            mPaint.setColor(Color.GREEN);
        }else {
            mPaint.setColor(Color.WHITE);
        }

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, width / 2, mRadius, mPaint);
    }

    private float SptoPx(int value) {
        return SizeUtils.Sp2Px(getContext(), value);
    }

    private float DptoPx(int value) {
        return SizeUtils.Dp2Px(getContext(), value);
    }

    private Bitmap turnDrawable(int drawable){
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, drawable);
        return bmp;
    }


}
