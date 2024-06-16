package com.example.sparringsystem.AudioUtilsModule;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sparringsystem.R;

public class TuningFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView tunerTitle;
    private TextView settings;
    private ProgressBar levelDisplay;
    View waveformView;
    View spectrumView;
    private TextView detectedNote;
    private ImageView keyboardView;
    private TextView frequencyDisplay;
    private TextView centsDeviation;
    private ImageView tuningMeter;

    private TuningProcessor tuningProcessor;
    private double[][] melFilterBank;

    public TuningFragment() {
        // Required empty public constructor
    }

    public static TuningFragment newInstance(String param1, String param2) {
        TuningFragment fragment = new TuningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuning, container, false);

        levelDisplay = view.findViewById(R.id.level_display);
        waveformView = view.findViewById(R.id.waveform_view);
        spectrumView = view.findViewById(R.id.spectrum_view);
        detectedNote = view.findViewById(R.id.detected_note);
        keyboardView = view.findViewById(R.id.keyboard_view);
        tuningMeter = view.findViewById(R.id.tuning_meter);
        frequencyDisplay = view.findViewById(R.id.frequency_display);

        // 开始调音
        tuningProcessor = new TuningProcessor(this);
        tuningProcessor.startTuning();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tuningProcessor.startTuning();
    }

    @Override
    public void onPause() {
        super.onPause();
        tuningProcessor.stopTuning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tuningProcessor.stopTuning();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tuningProcessor.stopTuning();
    }

    public void updateValues(double db, Bitmap waveformBitmap, Bitmap spectrogramBitmap, String currentNote, double currentFreq) {
        // 在非UI线程中更新UI
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                levelDisplay.setProgress((int) db);
                waveformView.setBackground(new BitmapDrawable(getResources(), waveformBitmap));
                spectrumView.setBackground(new BitmapDrawable(getResources(), spectrogramBitmap));
                detectedNote.setText(currentNote);
                frequencyDisplay.setText(String.format("%.2f Hz", currentFreq));
            }
        });
    }
}
