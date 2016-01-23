package com.example.statistical.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class HistogramView extends View {
	 
    private Paint xLinePaint;// 坐标轴 轴线 画笔：
    private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
    private Paint titlePaint;// 绘制文本的画笔
    private Paint paint;// 矩形画笔 柱状图的样式信息
    private int[] aniProgress;// 实现动画的值
    private int maxStep=10;//最大数标
    private int max=0;//数据中最大值
    // 坐标轴底部的星期数
    private String[] xWeeks;
 
 
    public HistogramView(Context context) {
        super(context);
        init();
    }
 
    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    private void init() {
 
        xWeeks = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
        aniProgress = new int[] { 0, 0, 0, 0, 0, 0, 0 };
 
        xLinePaint = new Paint();
        hLinePaint = new Paint();
        titlePaint = new Paint();
        paint = new Paint();
 
        // 给画笔设置颜色
        xLinePaint.setColor(Color.DKGRAY);
        hLinePaint.setColor(Color.LTGRAY);
        titlePaint.setColor(Color.BLACK);
 
    }
    
    public void setData(HistogramItem[] data){
    	aniProgress=new int[data.length];
    	xWeeks=new String[data.length];
    	for(int i=0;i<data.length;i++){
    		aniProgress[i]=data[i].value;
    		xWeeks[i]=data[i].name;
    	}
    	for(int i:aniProgress){
    		if(i>max) max=i;
    	}
    	while(maxStep<max){
    		maxStep=maxStep*10;
    	}
    	
    	invalidate();
    }
 
 
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        int width = getWidth();
        int height = getHeight() - dp2px(50);
        // 绘制底部的线条
        canvas.drawLine(dp2px(30), height + dp2px(3), width - dp2px(30), height
                + dp2px(3), xLinePaint);
 
        int leftHeight = height - dp2px(5);// 左侧外周的 需要划分的高度：
 
        int hPerHeight = leftHeight / 5;// 分成五部分
 
        hLinePaint.setTextAlign(Align.CENTER);
        // 设置五条虚线
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(dp2px(50), dp2px(10) + i * hPerHeight, width
                    - dp2px(30), dp2px(10) + i * hPerHeight, hLinePaint);
        }
 
        // 绘制 Y 周坐标
        titlePaint.setTextAlign(Align.RIGHT);
        titlePaint.setTextSize(sp2px(12));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
        // 设置左部的数字
        for (int i = 0; i < 5; i++) {
            canvas.drawText((5-i)*(maxStep/5)+"", dp2px(50), dp2px(13) + i * hPerHeight,
                    titlePaint);
        }
 
        // 绘制 X 周 做坐标
        int xAxisLength = width - dp2px(30);
        int columCount = xWeeks.length + 1;
        int step = xAxisLength / columCount;
 
        // 设置底部的文字
        for (int i = 0; i < columCount - 1; i++) {
            // text, baseX, baseY, textPaint
            canvas.drawText(xWeeks[i], dp2px(25) + step * (i + 1), height
                    + dp2px(20), titlePaint);
        }
 
        // 绘制矩形
        if (aniProgress != null && aniProgress.length > 0) {
            for (int i = 0; i < aniProgress.length; i++) {// 循环遍历将7条柱状图形画出来
                int value = aniProgress[i];
                paint.setAntiAlias(true);// 抗锯齿效果
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(sp2px(10));// 字体大小
                paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
                Rect rect = new Rect();// 柱状图的形状
 
                rect.left = step * (i + 1);
                rect.right = dp2px(30) + step * (i + 1);
                int rh = (int) (leftHeight - leftHeight * value / maxStep);
                rect.top = rh + dp2px(10);
                rect.bottom = height;
 
                canvas.drawRect(rect, paint);
                canvas.drawText(value + "", dp2px(15) + step * (i + 1)
                		- dp2px(15), rh + dp2px(5), paint);
 
            }
        }
 
    }
 
    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }
 
    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }
 
    /**
     * 柱状类
     * @author jin
     *
     */
    public class HistogramItem{
    	private int value;//柱状的值
    	private String name;//柱状描述
    	
    	public HistogramItem(int value,String name) {
			// TODO Auto-generated constructor stub
    		this.value=value;
    		this.name=name;
		}

    }
 
}