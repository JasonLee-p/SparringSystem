package com.example.sparringsystem.AudioUtilsModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sparringsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioUtilsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioUtilsFragment extends Fragment {
    // 子Fragment
    private TuningFragment tuningFragment;
    private SpectrumFragment spectrumFragment;

    // 控件
    private Button spectrumButton;
    private Button tuningButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AudioUtilsFragment() {
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
    // TODO: Rename and change types and number of parameters
    public static AudioUtilsFragment newInstance(String param1, String param2) {
        AudioUtilsFragment fragment = new AudioUtilsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化子Fragment
        tuningFragment = new TuningFragment();
        spectrumFragment = new SpectrumFragment();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_utils, container, false);

        // 按钮点击事件
        spectrumButton = view.findViewById(R.id.spectrumButton);
        tuningButton = view.findViewById(R.id.tuningButton);

        spectrumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationToSpectrumFragment();
            }
        });
        tuningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationToTuningFragment();
            }
        });

        return view;
    }

    public void navigationToTuningFragment() {
        loadFragment(tuningFragment);
    }

    public void navigationToSpectrumFragment() {
        loadFragment(spectrumFragment);
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}