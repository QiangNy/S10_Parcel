package com.ludeng.s10_pview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;


/**
 * Created by chen on 16-10-15.
 */
public class S2View extends View implements GestureDetector.OnGestureListener {
    private static final String TAG =S2View.class.getName() ;
    //设置颜色
    private int ColorPick[] = {Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.parseColor("#ffA8D7A7")};
    //中心位置
    private int centerX;
    private int centerY;
    //背景矩形
    RectF backGround;
    float centerLine[];

    //画笔
    private Paint mPaintRct;
    private Paint mPaintLine;
    //动画高度
    private final int bGroundWidth = 100;
    private final int secLineWidth = 50;
    private final int thrLineWidth = 20;

    //画笔宽度
    private int mPainRctWidth = 1;
    private int mPainLinWidth = 5;
    private SmHandler mHandler;
    private SmRunnable run;
    private Context mContext;
    private Scroller mScroller;
    private GestureDetectorCompat mGestureDetector;

    public S2View(Context context) {
        super(context);
        initView(context, null);
    }

    public S2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public S2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public S2View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        //初始化矩形画笔  Paint.ANTI_ALIAS_FLAG去锯齿
        mPaintRct = new Paint();
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔颜色
        mPaintRct.setColor(Color.RED);
        mPaintLine.setColor(ColorPick[1]);
        //设置画笔锯齿
        mPaintRct.setAntiAlias(true);
        //设置画笔实心

        mPaintLine.setStyle(Paint.Style.STROKE);
        //设置画笔宽度
        mPaintRct.setStrokeWidth(mPainRctWidth);
        mPaintLine.setStrokeWidth(mPainLinWidth);

        mPaintRct.setColor(ColorPick[ColorPick.length - 1]);
        mPaintLine.setColor(Color.RED);
        //设置异步线程
        HandlerThread handlerThread = new HandlerThread("syn s2view");
        handlerThread.start();
        mHandler = new SmHandler(handlerThread.getLooper());
        run = new SmRunnable();
        mHandler.post(run);

        //添加手势识别
        mGestureDetector = new GestureDetectorCompat(context, this);
        //添加scroller
     //   mScroller = new Scroller(mContext);
    }

    //设置布局宽度,高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取中心点
        centerX = w >> 1;
        centerY = h >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画一个长的矩形为背景
        backGround = new RectF(0, getHeight() - bGroundWidth, getWidth(), getHeight());
        //中心线位置
        centerLine = new float[]{centerX, backGround.bottom, centerX, backGround.top};
        canvas.drawColor(Color.GRAY);
        //绘制矩形
        canvas.drawRect(backGround, mPaintRct);
        canvas.drawLines(centerLine, mPaintLine);
        //  List<float[]> lines = new ArrayList<float[]>();
        for (int i = 1; i < 50; i++) {
            float[] itemLine = new float[]{
                    centerX - i * dip2px(mContext, 10), backGround.bottom,
                    centerX - i * dip2px(mContext, 10), backGround.bottom - thrLineWidth};
            //    lines.add(itemLine);
            canvas.drawLines(itemLine, mPaintLine);
        }
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("myviewtest","onTouchEvent");
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        //1000ms内滑向destX,效果是慢慢滑动
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY,5000);
        invalidate();
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            //自定义尺寸
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            //相当于wrap_content
            case MeasureSpec.AT_MOST:
                //相当于match_parent
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


    class SmHandler extends Handler {
        public SmHandler(Looper looper) {
            super(looper);
        }
    }

    class SmRunnable implements Runnable {

        @Override
        public void run() {
            //设置矩形
            //    mHandler.postDelayed(run,1000);
            //    postInvalidate();
        }
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
