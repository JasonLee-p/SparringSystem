package com.example.sparringsystem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sparringsystem.customView.CategoryItemViewDoubleLine;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Adding category items to the grid layout
        LinearLayout personalRecommendationLayout = view.findViewById(R.id.linearlayout_personalRecommendation);
        addPersonalRecommendationCategoryItems(personalRecommendationLayout);
    }

    public void addPersonalRecommendationCategoryItems(LinearLayout linearLayout) {
        String[] line1Text = {"每日推荐", "私人漫游", "民谣专场", "舒缓轻音"};
        String[] line2Text = {"符合你口味的新鲜好歌", "多种模式随心播放", "进入民谣的浪漫世界", "放松心情"};
        ImageSource[] imageSources = {
            new ImageSource(R.drawable.personal_r_1, "每日推荐"),
            new ImageSource(R.drawable.personal_r_2, "私人漫游"),
            new ImageSource(R.drawable.personal_r_3, "民谣专场"),
            new ImageSource(R.drawable.personal_r_4, "舒缓轻音")
        };
        for (int i = 0; i < line1Text.length; i++) {
            CategoryItemViewDoubleLine categoryItemViewDoubleLine = new CategoryItemViewDoubleLine(getContext());
            String name = line1Text[i];
            Drawable image = imageSources[i].getImageDrawable(getContext());
            categoryItemViewDoubleLine.setCategory(name, line2Text[i], image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).navigationToMusicPlayerFragment(name);
                }
            });
            // 布局管理
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.setMargins(0, 20, 30, 0);
            categoryItemViewDoubleLine.setLayoutParams(layoutParams);
            linearLayout.addView(categoryItemViewDoubleLine);
        }
    }
}