package com.example.sparringsystem.AudioUtilsModule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.AudioFormat;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.sparringsystem.AudioUtilsModule.AudioUtils.Spectrogram;
import com.example.sparringsystem.AudioUtilsModule.AudioUtils.Waveform;

public class TuningProcessor {

    public static final String TAG = "TuningProcessor";
    public static final int SAMPLE_RATE = 22050;
    public static final int BUFFER_SIZE = 2048;

    private AudioRecord audioRecord;

    private boolean isRecording;

    private double noteDetectMinFreq = 27.5;  // A0
    private double noteDetectMaxFreq = 4186.01;  // C8

    private Waveform waveformHandler;
    private Spectrogram spectrogramHandler;
    private double currentFreq = 440.0;
    private String currentNote = "A4";
    private final TuningFragment tuningFragment;

    public TuningProcessor(TuningFragment fragment) {
        this.tuningFragment = fragment;
        this.waveformHandler = new Waveform();
        this.spectrogramHandler = new Spectrogram();
        initRecorder(fragment.getContext(), fragment.getActivity());
    }

    public void initRecorder(android.content.Context context, android.app.Activity activity) {
        if (!checkPermission(context)) {
            requestPermission(activity);
        } else {
            initializeAudioRecord();
        }
    }

    public boolean checkPermission(android.content.Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(android.app.Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
    }

    @SuppressLint("MissingPermission")
    private void initializeAudioRecord() {
        try {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    BUFFER_SIZE
            );

            if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "AudioRecord initialization failed");
                return;
            }

            audioRecord.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {
                @Override
                public void onMarkerReached(AudioRecord recorder) {
                }

                @Override
                public void onPeriodicNotification(AudioRecord recorder) {
                    short[] buffer = new short[BUFFER_SIZE];
                    int read = audioRecord.read(buffer, 0, buffer.length);
                    if (read > 0) {
                        processBuffer(buffer);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error initializing AudioRecord", e);
        }
    }

    public void startTuning() {
        if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "AudioRecord is not initialized");
            return;
        }
        audioRecord.startRecording();
        isRecording = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                short[] buffer = new short[BUFFER_SIZE];
                while (isRecording) {
                    synchronized (audioRecord) {
                        int read = audioRecord.read(buffer, 0, buffer.length);
                        if (read > 0) {
                            processBuffer(buffer);
                        }
                    }
                }
            }
        }).start();
    }

    private void processBuffer(short[] buffer) {
        // 计算RMS值
        double rms = calculateRMS(buffer);
        // 将RMS值转换为dB值
        double db = 20 * Math.log10(rms);

        spectrogramHandler.resize(tuningFragment.spectrumView.getWidth(), tuningFragment.spectrumView.getHeight());
        waveformHandler.resize(tuningFragment.waveformView.getWidth(), tuningFragment.waveformView.getHeight());
        Bitmap spectrogramBitmap = spectrogramHandler.getSpectrogramBitmap(buffer);
        double[] doubleBuffer = spectrogramHandler.getStftResult()[0];
        currentFreq = detectFreq(doubleBuffer);
        currentNote = convertMidiToNoteName(convertFreqToMidi(currentFreq));
        Bitmap waveformBitmap = waveformHandler.getWaveformBitmap(buffer, db);

        // 更新UI
        tuningFragment.updateValues(db, waveformBitmap, spectrogramBitmap, currentNote, currentFreq);
    }

    public void stopTuning() {
        isRecording = false;
        if (audioRecord != null) {
            try {
                audioRecord.stop();
                audioRecord.release();
            } catch (Exception e) {
                Log.e(TAG, "Error stopping AudioRecord", e);
            }
        }
    }

    private double calculateRMS(short[] buffer) {
        double sum = 0;
        for (short sample : buffer) {
            sum += sample * sample;
        }
        double mean = sum / buffer.length;
        return Math.sqrt(mean);
    }

    private double detectFreq(double[] fftResult) {
        double maxFreq = 0;
        double maxAmp = 0;
        for (int i = 0; i < fftResult.length / 2; i++) {
            double freq = i * SAMPLE_RATE / fftResult.length;
            if (freq < noteDetectMinFreq || freq > noteDetectMaxFreq) {
                continue;
            }
            double amp = Math.sqrt(fftResult[2 * i] * fftResult[2 * i] + fftResult[2 * i + 1] * fftResult[2 * i + 1]);
            if (amp > maxAmp) {
                maxAmp = amp;
                maxFreq = freq;
            }
        }
        return maxFreq;
    }

    private int convertFreqToMidi(double freq) {
        return (int) Math.round(12 * Math.log(freq / 440.0) / Math.log(2) + 69);
    }

    private String convertMidiToNoteName(int midiNum) {
        String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        return noteNames[midiNum % 12] + (midiNum / 12 - 1);
    }

    public double getCurrentFreq() {
        return currentFreq;
    }

    public String getCurrentNote() {
        return currentNote;
    }
}
