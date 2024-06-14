package com.example.sparringsystem.PracticeModule;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sparringsystem.ImageSource;
import com.example.sparringsystem.R;
import com.example.sparringsystem.customView.CategoryItemViewSingleLine;
import com.example.sparringsystem.customView.CustomCardView;

import java.util.List;

public class PracticeFragment extends Fragment {
    // 子界面
    private SongListFragment songListFragment;
    private MusicPlayerFragment musicPlayerFragment;

    // 推荐项
    private List<RecommendationItem> recommendationItems;


    public PracticeFragment() {
        // Required empty public constructor
    }

    public static PracticeFragment newInstance(String param1, String param2) {
        PracticeFragment fragment = new PracticeFragment();

        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化子界面
        songListFragment = new SongListFragment();
        musicPlayerFragment = new MusicPlayerFragment();

        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Adding category items to the grid layout
        LinearLayout recommendationLayout = view.findViewById(R.id.recommendation_layout);
        GridLayout instrumentGridLayout = view.findViewById(R.id.instrument_grid_layout);
        addRecommendationCategoryItems(recommendationLayout);
        addInstrumentCategoryItems(instrumentGridLayout);
    }

    private void addRecommendationCategoryItems(LinearLayout linearLayout) {
        String[] recommendationNames = {"推荐1", "推荐2", "推荐3", "推荐4"};
        ImageSource[] recommendationImages = {ImageSource.RECOMMENDATION1, ImageSource.RECOMMENDATION2, ImageSource.RECOMMENDATION3, ImageSource.RECOMMENDATION4};

        for (int i = 0; i < recommendationNames.length; i++) {
            CustomCardView recommendationItemView = new CustomCardView(getContext());
            Drawable image;
            // 转为Drawable
            android.content.Context context = getContext();
            if (recommendationImages[i].isResourceId()) {
                image = ContextCompat.getDrawable(context, recommendationImages[i].getImageResourceId());
            } else if (recommendationImages[i].isLocalPath()) {
                image = Drawable.createFromPath(recommendationImages[i].getLocalPath());
            } else if (recommendationImages[i].isUrl()) {
                // 从URL加载图片
                image = ContextCompat.getDrawable(context, R.drawable.unknown_album);
            } else {
                image = ContextCompat.getDrawable(context, R.drawable.unknown_album);
            }
            String name = recommendationNames[i];
            recommendationItemView.setCategory(name, image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigationToSongListFragment(name);
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            // 布局管理
            layoutParams.weight = 1;
            layoutParams.setMargins(0, 20, 30, 20);
            recommendationItemView.setLayoutParams(layoutParams);
            linearLayout.addView(recommendationItemView);
        }
    }

    private void addInstrumentCategoryItems(GridLayout gridLayout) {
        // Define your category items here
        String[] categoryNames = {"电吉他", "琵琶", "民谣吉他", "二胡"};
        int[] categoryImages = {R.drawable.i_guitar, R.drawable.i_pipa, R.drawable.i_guitar2, R.drawable.i_erhu};

        for (int i = 0; i < categoryNames.length; i++) {
            CategoryItemViewSingleLine categoryItemViewSingleLine = new CategoryItemViewSingleLine(getContext());
            Drawable image = ContextCompat.getDrawable(getContext(), categoryImages[i]);
            String name = categoryNames[i];
            categoryItemViewSingleLine.setCategory(name, image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigationToMusicPlayerFragment(name);
                }
            });
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = 0;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            layoutParams.setMargins(30, 20, 30, 20);
            categoryItemViewSingleLine.setLayoutParams(layoutParams);
            gridLayout.addView(categoryItemViewSingleLine);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void navigationToSongListFragment(String name) {
        // 显示SongListFragment
        loadFragment(songListFragment);
    }

    public void navigationToMusicPlayerFragment(String name) {
        // 显示MusicPlayerFragment
        loadFragment(musicPlayerFragment);
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
