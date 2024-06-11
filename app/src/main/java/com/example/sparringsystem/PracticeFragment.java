package com.example.sparringsystem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sparringsystem.customView.CategoryItemView;

public class PracticeFragment extends Fragment {
    // 子界面
    private SongListFragment songListFragment;
    private MusicPlayerFragment musicPlayerFragment;

    //    private MusicPlayer musicPlayer;
    //    private Handler handler = new Handler();
    //    private SeekBar seekBar;
    //    private TextView textTime;
    //    private Runnable updateSeekBar;

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
        GridLayout gridLayout = view.findViewById(R.id.grid_layout);
        addCategoryItems(gridLayout);
    }

    private void addCategoryItems(GridLayout gridLayout) {
        // Define your category items here
        String[] categoryNames = {"电吉他", "琵琶", "民谣吉他", "二胡"};
        int[] categoryImages = {R.drawable.i_guitar, R.drawable.i_pipa, R.drawable.i_guitar2, R.drawable.i_erhu};

        for (int i = 0; i < categoryNames.length; i++) {
            CategoryItemView categoryItemView = new CategoryItemView(getContext());
            Drawable image = ContextCompat.getDrawable(getContext(), categoryImages[i]);
            String name = categoryNames[i];
            categoryItemView.setCategory(name, image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).navigationToSongListFragment(name);
                }
            });
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = 0;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            layoutParams.setMargins(15, 15, 15, 15);
            categoryItemView.setLayoutParams(layoutParams);
            gridLayout.addView(categoryItemView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void navigationToSongListFragment(String name) {
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
