package com.example.sparringsystem.AudioUtilsModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private View waveformView;
    private TextView detectedNote;
    private ImageView keyboardView;
    private TextView frequencyDisplay;
    private TextView centsDeviation;
    private ImageView tuningMeter;

    private TuningProcessor tuningProcessor;

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

        waveformView = view.findViewById(R.id.waveform_view);
        detectedNote = view.findViewById(R.id.detected_note);
        keyboardView = view.findViewById(R.id.keyboard_view);
        tuningMeter = view.findViewById(R.id.tuning_meter);
        frequencyDisplay = view.findViewById(R.id.frequency_display);

        // 开始调音
        android.content.Context context = getContext();
        tuningProcessor = new TuningProcessor(context, getActivity(), detectedNote, waveformView, frequencyDisplay);
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
}
