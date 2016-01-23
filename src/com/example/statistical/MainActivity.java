package com.example.statistical;

import android.app.Activity;
import android.os.Bundle;

import com.example.statistical.view.HistogramView;
import com.example.statistical.view.HistogramView.HistogramItem;

public class MainActivity extends Activity {


	HistogramView histogramView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		histogramView1=(HistogramView)findViewById(R.id.histogramView1);
		HistogramItem[] data=new HistogramItem[]{
				histogramView1.new HistogramItem(10000, "周一"),
				histogramView1.new HistogramItem(8000, "周二"),
				histogramView1.new HistogramItem(6000, "周三"),
				histogramView1.new HistogramItem(3000, "周四"),
				histogramView1.new HistogramItem(9000, "周五")
		};
		histogramView1.setData(data);
	}

}
