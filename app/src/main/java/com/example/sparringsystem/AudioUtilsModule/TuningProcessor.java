// File: com/example/sparringsystem/AudioUtilsModule/TuningProcessor.java

package com.example.sparringsystem.AudioUtilsModule;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.AudioFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import org.jtransforms.fft.DoubleFFT_1D;
import org.jtransforms.fft.DoubleFFT_2D;

import java.util.ArrayList;
import java.util.List;


public class TuningProcessor {

    public static final String TAG = "TuningProcessor";
    private AudioRecord audioRecord;
    private TextView detectedNote;
    private View waveFontView;
    private Bitmap gridBitmap;
    private Canvas canvas = new Canvas();
    private Bitmap bitmap = Bitmap.createBitmap(1024, 512, Bitmap.Config.ARGB_8888);
    private BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
    private Paint paint = new Paint();
    private Drawable waveformDrawable;
    private TextView frequencyDisplay;

    private int bufferSize;
    private int sampleRate = 44100;
    private boolean isRecording;
    private double currentFreq = 440.0;
    private String currentNote = "A4";

    public TuningProcessor(android.content.Context context, android.app.Activity activity, TextView detectedNote, View keyboardView, TextView frequencyDisplay) {
        bufferSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
        );
        Log.d(TAG, "Buffer size: " + bufferSize);
        this.detectedNote = detectedNote;
        this.waveFontView = keyboardView;
        this.frequencyDisplay = frequencyDisplay;
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        initRecorder(context, activity);
    }

    public void initRecorder(android.content.Context context, android.app.Activity activity) {
        // 检查权限
        if (!checkPermission(context)) {
            if (!requestPermission(activity)){
                return;
            }
        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        );
    }

    public boolean checkPermission(android.content.Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean requestPermission(android.app.Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            return ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void initGridBitmap() {
        int width = waveFontView.getWidth();
        int height = waveFontView.getHeight();
        if (width == 0 || height == 0) {
            Log.e(TAG, "ImageView has no size");
            return;
        }
        gridBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        gridBitmap.eraseColor(Color.BLACK);
        Canvas _canvas = new Canvas(gridBitmap);

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1);

        // 计算八度频率点
        double baseFrequency = 440.0; // 基准频率为440Hz
        double minFrequency = 27.5;   // 起始频率为A0
        double maxFrequency = 4186.0; // 最高频率为C8

        List<Integer> octavePositions = new ArrayList<>();
        for (double freq = minFrequency; freq <= maxFrequency; freq *= 2) {
            int x = (int) ((Math.log(freq / minFrequency) / Math.log(maxFrequency / minFrequency)) * width);
            octavePositions.add(x);
        }

        // 绘制八度网格线
        for (int x : octavePositions) {
            _canvas.drawLine(x, 0, x, height, gridPaint);
        }

        for (int y = 0; y < height; y += 10) {
            _canvas.drawLine(0, y, width, y, gridPaint);
        }
    }

    public void bitMapResize(int width, int height) {
        if (bitmap.getWidth() == width && bitmap.getHeight() == height) {
            return;
        }
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        paint = new Paint();
    }

    public void startTuning() {
        if (audioRecord == null) {
            Log.e(TAG, "AudioRecord is not initialized");
            return;
        }
        audioRecord.startRecording();
        isRecording = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                short[] buffer = new short[bufferSize];
                while (isRecording) {
                    int read = audioRecord.read(buffer, 0, buffer.length);
                    if (read > 0) {
                        processBuffer(buffer);
                    }
                }
            }
        }).start();
    }

    public void stopTuning() {
        isRecording = false;
        audioRecord.stop();
    }

    // Placeholder method for pitch detection
    private double detectPitch(short[] buffer) {
        // Implement pitch detection algorithm here
        return 440.0;
    }

    private void processBuffer(short[] buffer) {
        // 渲染波形并显示
        renderWaveform(buffer);
        // 检测音高
        currentFreq = detectPitch(buffer);
        // 转化为文字
        int midiNum = convertFreqToMidi(currentFreq);
        currentNote = convertMidiToNoteName(midiNum);
        // 通知UI更新
        updateUI();
    }

    private int convertFreqToMidi(double freq) {
        return (int) Math.round(12 * Math.log(freq / 440.0) / Math.log(2) + 69);
    }

    private String convertMidiToNoteName(int midiNum) {
        String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        return noteNames[midiNum % 12] + (midiNum / 12 - 1);
    }

    private void renderWaveform(short[] buffer) {
        if (waveFontView == null || gridBitmap == null) {
            initGridBitmap();
            Log.e(TAG, "ImageView is not initialized");
            return;
        }
        int width = waveFontView.getWidth();
        int height = waveFontView.getHeight();
        bitMapResize(width, height);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        canvas.drawBitmap(gridBitmap, 0, 0, paint);

        double[] waveform = new double[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            waveform[i] = buffer[i];
        }
        DoubleFFT_1D fft = new DoubleFFT_1D(buffer.length);
        fft.realForward(waveform);
        Path waveformPath = new Path();
        waveformPath.moveTo(0, height);
        for (int i = 0; i < buffer.length / 2; i++) {
            double magnitude = Math.sqrt(waveform[2 * i] * waveform[2 * i] + waveform[2 * i + 1] * waveform[2 * i + 1]);
            double x = (double) i / buffer.length * width;
            double y = height - magnitude * height;
            waveformPath.lineTo((float) x, (float) y);
        }



        waveformPath.lineTo(width, height);
        waveformPath.close();
        canvas.drawPath(waveformPath, paint);

        // 设置Bitmap到waveformDrawable
        waveformDrawable = new BitmapDrawable(waveFontView.getResources(), bitmap);
        // 通知主线程更新UI
        waveFontView.post(new Runnable() {
            @Override
            public void run() {
                waveFontView.setBackground(waveformDrawable);
                waveFontView.invalidate();
            }
        });
    }


    private void updateUI() {
        if (detectedNote == null || frequencyDisplay == null) {
            Log.e(TAG, "TextView is not initialized");
            return;
        }
        // 在协程中更新UI
        detectedNote.post(new Runnable() {
            @Override
            public void run() {
                detectedNote.setText(currentNote);
                detectedNote.invalidate();
            }
        });
        frequencyDisplay.post(new Runnable() {
            @Override
            public void run() {
                frequencyDisplay.setText(String.format("%.2f Hz", currentFreq));
                frequencyDisplay.invalidate();
            }
        });
    }

    public Drawable getWaveformDrawable() {
        return waveformDrawable;
    }
}
