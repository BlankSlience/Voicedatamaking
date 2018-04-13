package com.example.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

public class WaveformView extends View {

    private byte[] mWaveform;
    private WaveformRenderer mRenderer;

    public WaveformView(Context context) {
        super(context);
    }

    public WaveformView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveformView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setRenderer(WaveformRenderer renderer) {
        mRenderer = renderer;
    }
    public void setWaveform(byte[] waveform) {
        // 数组复制
        mWaveform = Arrays.copyOf(waveform, waveform.length);
        invalidate(); // 设置波纹之后, 需要重绘
    }
    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRenderer != null) {
            mRenderer.render(canvas, mWaveform);
        }
    }
}
