package com.example.sparringsystem.AudioUtilsModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sparringsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TuningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TuningFragment extends Fragment {

    // Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
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

    public TuningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TuningFragment.
     */
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tuning, container, false);

        // Initialize views
        tunerTitle = view.findViewById(R.id.tuner_title);
        settings = view.findViewById(R.id.settings);
        waveformView = view.findViewById(R.id.waveform_view);
        detectedNote = view.findViewById(R.id.detected_note);
        keyboardView = view.findViewById(R.id.keyboard_view);
        frequencyDisplay = view.findViewById(R.id.frequency_display);
        centsDeviation = view.findViewById(R.id.cents_deviation);
        tuningMeter = view.findViewById(R.id.tuning_meter);

        // Add your logic here

        return view;
    }
}
