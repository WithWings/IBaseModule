package com.withwings.basewidgets.textview.rise;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字自增长工具
 */
@SuppressWarnings({"SameParameterValue", "unused"})
@SuppressLint("AppCompatCustomView")
public class RiseNumberTextView extends TextView implements IRiseNumber {

    private static final int STOPPED = 0;

    private static final int RUNNING = 1;

    private int mPlayingState = STOPPED;

    private float number;

    private float fromNumber;

    /**
     * 动画播放时长
     */
    private long duration = 1500;
    /**
     * 1.int 2.float
     */
    private int numberType = 2;

    private DecimalFormat fnum;

    private EndListener mEndListener = null;

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

    // 是否展示钱符号
    private boolean mShowMoney;


    public RiseNumberTextView(Context context) {
        super(context);
    }

    /**
     * 使用xml布局文件默认的被调用的构造方法
     *
     * @param context 控件依赖的上下文
     * @param attr    属性值
     */
    public RiseNumberTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public RiseNumberTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    /**
     * 判断动画是否正在播放
     *
     * @return 是否还在运行
     */
    public boolean isRunning() {
        return (mPlayingState == RUNNING);
    }

    /**
     * *跑小数动画
     */
    private void runFloat() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromNumber, number);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                String text = fnum.format(Float.parseFloat(valueAnimator.getAnimatedValue().toString()));
                if (mShowMoney) {
                    text = moneyFormat(text);
                }
                setText(text);
                if (valueAnimator.getAnimatedFraction() >= 1) {
                    mPlayingState = STOPPED;
                    if (mEndListener != null)
                        mEndListener.onEndFinish();
                }
            }


        });

        valueAnimator.start();
    }

    /**
     * 跑整数动画
     */
    private void runInt() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) fromNumber, (int) number);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setText(valueAnimator.getAnimatedValue().toString());
                if (valueAnimator.getAnimatedFraction() >= 1) {
                    mPlayingState = STOPPED;
                    if (mEndListener != null)
                        mEndListener.onEndFinish();
                }
            }
        });
        valueAnimator.start();
    }

    static int sizeOfInt(int x) {
        for (int i = 0; ; i++) {
            if (x <= sizeTable[i])
                return i + 1;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fnum = new DecimalFormat("##0.00");
    }

    /**
     * 开始播放动画
     */
    @Override
    public void start() {

        if (!isRunning()) {
            mPlayingState = RUNNING;
            if (numberType == 1)
                runInt();
            else
                runFloat();
        }
    }

    /**
     * 设置一个小数进来
     */
    @Override
    public void withNumber(float number) {

        this.number = number;
        numberType = 2;
        if (number > 1000) {
            fromNumber = number - (float) Math.pow(10, sizeOfInt((int) number) - 1);
        } else {
            fromNumber = number / 2;
        }

    }

    /**
     * 设置一个整数进来
     */
    @Override
    public void withNumber(int number) {
        this.number = number;
        numberType = 1;
        if (number > 1000) {
            fromNumber = number - (float) Math.pow(10, sizeOfInt(number) - 2);
        } else {
            fromNumber = number / 2;
        }

    }

    /**
     * 设器置动画时长
     */
    @Override
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 设器置动画结束监听
     */
    @Override
    public void setOnEndListener(EndListener callback) {
        mEndListener = callback;
    }

    /**
     * 展示钱符号
     *
     * @param showMoney 是否展示，默认不展示
     */
    public void showMoney(boolean showMoney) {
        mShowMoney = showMoney;
    }

    /**
     * 定义动画结束接口
     */
    public interface EndListener {
        /**
         * 当动画播放结束时的回调方法
         */
        void onEndFinish();
    }

    /**
     * 千位分隔符保留两位小数
     *
     * @param number 金额
     * @return 格式化后的字符串
     */
    private String moneyFormat(String number) {
        return formatNumber(number, "#,##0.00;(#)");
    }

    private String formatNumber(String number, String format) {
        BigDecimal bigDecimal = new BigDecimal(number);
        //,代表分隔符
        //0.后面的##代表位数 如果换成0 效果就是位数不足0补齐
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(bigDecimal);
    }
}