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
	 
    private Paint xLinePaint;// ������ ���� ���ʣ�
    private Paint hLinePaint;// ������ˮƽ�ڲ� ���߻���
    private Paint titlePaint;// �����ı��Ļ���
    private Paint paint;// ���λ��� ��״ͼ����ʽ��Ϣ
    private int[] aniProgress;// ʵ�ֶ�����ֵ
    private int maxStep=10;//�������
    private int max=0;//���������ֵ
    // ������ײ���������
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
 
        xWeeks = new String[] { "��һ", "�ܶ�", "����", "����", "����", "����", "����" };
        aniProgress = new int[] { 0, 0, 0, 0, 0, 0, 0 };
 
        xLinePaint = new Paint();
        hLinePaint = new Paint();
        titlePaint = new Paint();
        paint = new Paint();
 
        // ������������ɫ
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
        // ���Ƶײ�������
        canvas.drawLine(dp2px(30), height + dp2px(3), width - dp2px(30), height
                + dp2px(3), xLinePaint);
 
        int leftHeight = height - dp2px(5);// ������ܵ� ��Ҫ���ֵĸ߶ȣ�
 
        int hPerHeight = leftHeight / 5;// �ֳ��岿��
 
        hLinePaint.setTextAlign(Align.CENTER);
        // ������������
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(dp2px(50), dp2px(10) + i * hPerHeight, width
                    - dp2px(30), dp2px(10) + i * hPerHeight, hLinePaint);
        }
 
        // ���� Y ������
        titlePaint.setTextAlign(Align.RIGHT);
        titlePaint.setTextSize(sp2px(12));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
        // �����󲿵�����
        for (int i = 0; i < 5; i++) {
            canvas.drawText((5-i)*(maxStep/5)+"", dp2px(50), dp2px(13) + i * hPerHeight,
                    titlePaint);
        }
 
        // ���� X �� ������
        int xAxisLength = width - dp2px(30);
        int columCount = xWeeks.length + 1;
        int step = xAxisLength / columCount;
 
        // ���õײ�������
        for (int i = 0; i < columCount - 1; i++) {
            // text, baseX, baseY, textPaint
            canvas.drawText(xWeeks[i], dp2px(25) + step * (i + 1), height
                    + dp2px(20), titlePaint);
        }
 
        // ���ƾ���
        if (aniProgress != null && aniProgress.length > 0) {
            for (int i = 0; i < aniProgress.length; i++) {// ѭ��������7����״ͼ�λ�����
                int value = aniProgress[i];
                paint.setAntiAlias(true);// �����Ч��
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(sp2px(10));// �����С
                paint.setColor(Color.parseColor("#6DCAEC"));// ������ɫ
                Rect rect = new Rect();// ��״ͼ����״
 
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
     * ��״��
     * @author jin
     *
     */
    public class HistogramItem{
    	private int value;//��״��ֵ
    	private String name;//��״����
    	
    	public HistogramItem(int value,String name) {
			// TODO Auto-generated constructor stub
    		this.value=value;
    		this.name=name;
		}

    }
 
}