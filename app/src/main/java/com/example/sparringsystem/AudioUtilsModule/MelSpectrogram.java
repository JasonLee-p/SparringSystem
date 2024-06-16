package com.example.sparringsystem.AudioUtilsModule;

import org.jtransforms.fft.DoubleFFT_1D;
import java.util.Arrays;


public class MelSpectrogram {

    private static final int SAMPLE_RATE = 44100;
    private static final int FFT_SIZE = 1024;
    private static final int MEL_BANDS = 40;
    private static final int HOP_SIZE = 512;

    public static void main(String[] args) {
        // 假设 buffer 是从麦克风读取的短音频片段
        short[] buffer = new short[FFT_SIZE];
        // 你可以从 audioRecord 读取数据
        // audioRecord.read(buffer, 0, buffer.length);

        // 将short类型的PCM数据转换为double类型
        double[] doubleBuffer = new double[FFT_SIZE];
        for (int i = 0; i < buffer.length; i++) {
            doubleBuffer[i] = buffer[i];
        }

        // 计算STFT
        double[][] stftResult = stft(doubleBuffer, FFT_SIZE, HOP_SIZE);

        // 计算梅尔频谱
        double[][] melSpectrogram = melSpectrogram(stftResult, SAMPLE_RATE, FFT_SIZE, MEL_BANDS);

        // 输出或使用梅尔频谱数据
        for (int i = 0; i < melSpectrogram.length; i++) {
            System.out.println("Mel band " + i + ": " + Arrays.toString(melSpectrogram[i]));
        }
    }

    private static double[][] stft(double[] signal, int fftSize, int hopSize) {
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

    private static double[][] melSpectrogram(double[][] stftResult, int sampleRate, int fftSize, int melBands) {
        int numFrames = stftResult.length;
        double[][] melSpectrogram = new double[numFrames][melBands];

        double[][] melFilterBank = createMelFilterBank(sampleRate, fftSize, melBands);

        for (int i = 0; i < numFrames; i++) {
            for (int j = 0; j < melBands; j++) {
                double melEnergy = 0;
                for (int k = 0; k < fftSize / 2 + 1; k++) {
                    melEnergy += Math.abs(stftResult[i][k]) * melFilterBank[j][k];
                }
                melSpectrogram[i][j] = melEnergy;
            }
        }

        return melSpectrogram;
    }

    private static double[][] createMelFilterBank(int sampleRate, int fftSize, int melBands) {
        double[][] melFilterBank = new double[melBands][fftSize / 2 + 1];
        double[] melFrequencies = melScale(melBands + 2, sampleRate, fftSize);

        for (int i = 1; i <= melBands; i++) {
            int left = (int) Math.floor(melFrequencies[i - 1]);
            int center = (int) Math.floor(melFrequencies[i]);
            int right = (int) Math.floor(melFrequencies[i + 1]);

            for (int j = left; j < center; j++) {
                melFilterBank[i - 1][j] = (j - melFrequencies[i - 1]) / (melFrequencies[i] - melFrequencies[i - 1]);
            }
            for (int j = center; j < right; j++) {
                melFilterBank[i - 1][j] = (melFrequencies[i + 1] - j) / (melFrequencies[i + 1] - melFrequencies[i]);
            }
        }

        return melFilterBank;
    }

    private static double[] melScale(int numMelBands, int sampleRate, int fftSize) {
        double[] melFrequencies = new double[numMelBands];
        double melMax = 2595 * Math.log10(1 + (sampleRate / 2) / 700.0);
        double melMin = 0.0;
        double melSpacing = (melMax - melMin) / (numMelBands - 1);

        for (int i = 0; i < numMelBands; i++) {
            double mel = melMin + i * melSpacing;
            melFrequencies[i] = 700 * (Math.pow(10, mel / 2595) - 1);
        }

        for (int i = 0; i < numMelBands; i++) {
            melFrequencies[i] = Math.floor((fftSize + 1) * melFrequencies[i] / sampleRate);
        }

        return melFrequencies;
    }
}
