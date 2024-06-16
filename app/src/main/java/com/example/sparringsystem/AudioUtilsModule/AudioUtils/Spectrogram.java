package com.example.sparringsystem.AudioUtilsModule.AudioUtils;
import android.graphics.Bitmap;
import android.graphics.Color;

import org.jtransforms.fft.DoubleFFT_1D;
import java.util.Arrays;

public class Spectrogram {
    private int viewWidth;
    private int viewHeight;
    private static final int SAMPLE_RATE = 44100;
    private static final int FFT_SIZE = 1024;
    private static final int HOP_SIZE = 512;
    private double[][] stftResult;
    private double[][] spectrogram;
    private Bitmap spectrogramBitmap;

    // 频谱图对象，用于绘制频谱图
    public Spectrogram() {}
    Spectrogram(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }

    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    public Bitmap getSpectrogramBitmap(short[] buffer) {
        // 将short类型的PCM数据转换为double类型
        double[] doubleBuffer = new double[FFT_SIZE];
        for (int i = 0; i < FFT_SIZE; i++) {
            doubleBuffer[i] = buffer[i] / 32768.0;
        }

        // 计算STFT
        stftResult = stft(doubleBuffer, FFT_SIZE, HOP_SIZE);
        // 生成频谱图
        double[][] spectrogram = generateSpectrogram(stftResult);
        // 输出为位图
        spectrogramBitmap = generateBitmap(spectrogram);
        return spectrogramBitmap;
    }

    private double[][] generateSpectrogram(double[][] stftResult) {
        int numFrames = stftResult.length;
        spectrogram = new double[numFrames][FFT_SIZE / 2];

        for (int i = 0; i < numFrames; i++) {
            for (int j = 0; j < FFT_SIZE / 2; j++) {
                double real = stftResult[i][2 * j];
                double imag = stftResult[i][2 * j + 1];
                double magnitude = Math.sqrt(real * real + imag * imag); // 计算幅值
                spectrogram[i][j] = magnitude;
            }
        }
        return spectrogram;
    }

    private double[][] stft(double[] signal, int fftSize, int hopSize) {
        int numFrames = (signal.length - fftSize) / hopSize + 1;
        double[][] stftResult = new double[numFrames][fftSize];
        DoubleFFT_1D fft = new DoubleFFT_1D(fftSize);

        for (int i = 0; i < numFrames; i++) {
            double[] frame = Arrays.copyOfRange(signal, i * hopSize, i * hopSize + fftSize);
            fft.realForward(frame);
            stftResult[i] = frame;
        }

        return stftResult;
    }

    private Bitmap generateBitmap(double[][] spectrogram) {
        // 根据view的宽高创建位图
        if (viewWidth == 0 || viewHeight == 0) {
            return Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        }
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.BLACK);
        // 绘制频谱图
        for (int i = 0; i < spectrogram.length; i++) {
            for (int j = 0; j < spectrogram[i].length; j++) {
                // 计算颜色值
                int color = calculateColor(spectrogram[i][j]);
                // 绘制像素
                bitmap.setPixel(j, i, color);
            }
        }
        return bitmap;
    }

    private int calculateColor(double magnitude) {
        // 根据幅值计算颜色值
        if (magnitude < 0.1) {
            return 0xFF000000;
        } else if (magnitude < 0.2) {
            return 0xFF0000FF;
        } else if (magnitude < 0.3) {
            return 0xFF00FF00;
        } else {
            return 0xFFFF0000;
        }
    }

    public double[][] getStftResult() {
        return stftResult;
    }

    public double[][] getSpectrogram() {
        return spectrogram;
    }
}
