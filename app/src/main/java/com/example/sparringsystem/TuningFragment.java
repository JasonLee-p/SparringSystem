package com.example.sparringsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TuningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TuningFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    // TODO: Rename and change types and number of parameters
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

//        // 设置顶部栏的回退按钮
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        // 按钮点击事件
        Button spectrumButton = view.findViewById(R.id.spectrumButton);
        spectrumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 导航到频谱仪界面
                // 日志
                Log.i("TuningFragment", "打开频谱仪");
                // 使用FragmentManager加载新的Fragment或启动新的Activity
            }
        });

        Button tuningButton = view.findViewById(R.id.tuningButton);
        tuningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 导航到调音界面
                //
                //
                // 使用FragmentManager加载新的Fragment或启动新的Activity
            }
        });

        return view;
    }
}