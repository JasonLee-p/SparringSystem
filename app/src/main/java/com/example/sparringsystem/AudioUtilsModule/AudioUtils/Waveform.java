package com.example.sparringsystem.AudioUtilsModule.AudioUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Waveform {
    private int viewWidth;
    private int viewHeight;
    // 截取的长度
    static final int WAVEFORM_LENGTH = 256;
    private Bitmap waveformBitmap;

    // 频谱图对象，用于绘制频谱图
    public Waveform() {}
    public Waveform(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }

    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    public Bitmap getWaveformBitmap(short[] buffer, double db) {
        // 将short类型的PCM数据转换为double类型
        double[] doubleBuffer = new double[WAVEFORM_LENGTH];
        for (int i = 0; i < WAVEFORM_LENGTH; i++) {
            doubleBuffer[i] = buffer[i] / 32768.0;
        }
        // 生成波形图
        waveformBitmap = generateBitmap(doubleBuffer);
        return waveformBitmap;
    }

    private Bitmap generateBitmap(double[] buffer) {
        int width = viewWidth;
        int height = viewHeight;
        // 根据view的宽高创建位图
        if (viewWidth == 0 || viewHeight == 0) {
            return Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 清空为黑
        bitmap.eraseColor(Color.BLACK);
        // 画笔
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        // Path
        Path path = new Path();
        // 画布
        Canvas canvas = new Canvas(bitmap);
        // 将数据宽度缩放到view的宽度
        int step = buffer.length / width;
        for (int i = 0; i < width; i++) {
            int x = i;
            int y = (int) (height / 2 + buffer[i * step] * height / 2);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        canvas.drawPath(path, paint);
        return bitmap;
    }
}
