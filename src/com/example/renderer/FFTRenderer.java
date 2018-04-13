package com.example.renderer;

import com.example.dao.AudioData;
import com.example.dao.FFTData;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class FFTRenderer extends Renderer{
	private Paint mPaint;

	public FFTRenderer(Paint paint) {
		super();
	    mPaint = paint;
	}

	@Override
	public void onRender(Canvas canvas, AudioData data, Rect rect) {
		//do nothing
	}

	@Override
	public void onRender(Canvas canvas, FFTData data, Rect rect) {
		mPoints = new float[data.bytes.length * 4];  
       
        for (int i = 0; i < 9; i++) {  
      
            if (data.bytes[i] < 0)  
            	data.bytes[i] = 127;   
              
            mPoints[i * 4] = rect.width() * i / 9;  
            mPoints[i * 4 + 1] = rect.height() / 2;  
            mPoints[i * 4 + 2] = rect.width() * i /9;  
            mPoints[i * 4 + 3] = 2 +rect.height() / 2 + data.bytes[i];  
        }  
        canvas.drawLines(mPoints, mPaint); 
	}

}
