package com.example.sparringsystem.UserModule;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sparringsystem.ImageSource;
import com.example.sparringsystem.R;
import com.example.sparringsystem.customView.CustomCardView;
import com.example.sparringsystem.customView.ShowCangItemView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowCangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCangFragment extends Fragment {

    LinearLayout linearLayout_showCang;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowCangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowCangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCangFragment newInstance(String param1, String param2) {
        ShowCangFragment fragment = new ShowCangFragment();
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
        return inflater.inflate(R.layout.fragment_show_cang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout_showCang = view.findViewById(R.id.linearLayout_showCang);

        addRecommendationCategoryItems(linearLayout_showCang);
    }

    private void addRecommendationCategoryItems(LinearLayout linearLayout) {
        String[] recommendationNames = {"推荐1", "推荐2", "推荐3", "推荐4"};
        ImageSource[] recommendationImages = {ImageSource.RECOMMENDATION1, ImageSource.RECOMMENDATION2, ImageSource.RECOMMENDATION3, ImageSource.RECOMMENDATION4};

        for (int i = 0; i < recommendationNames.length; i++) {
            ShowCangItemView showCangItemView = new ShowCangItemView(getContext());
            // 转为Drawable
            android.content.Context context = getContext();
            Drawable image = recommendationImages[i].getImageDrawable(getContext());
            String name = recommendationNames[i];

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            // 布局管理
            layoutParams.weight = 1;
            layoutParams.setMargins(0, 20, 30, 20);
            showCangItemView.setLayoutParams(layoutParams);
            linearLayout.addView(showCangItemView);
//            Log.d("ShouCangFragment", recommendationNames[i] + "created");
        }
    }
}