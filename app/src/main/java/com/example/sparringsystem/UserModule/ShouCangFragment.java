package com.example.sparringsystem.UserModule;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sparringsystem.ImageSource;
import com.example.sparringsystem.MainActivity;
import com.example.sparringsystem.PracticeModule.MusicPlayerFragment;
import com.example.sparringsystem.R;
import com.example.sparringsystem.customView.ShowCangItemView;
import com.example.sparringsystem.musicPlayer.Song;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShouCangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShouCangFragment extends Fragment {
    private static final String TAG = "ShouCangFragment";

    LinearLayout linearLayout_showCang;
    User currentUser;
    android.content.Context context;  // 用于获取资源
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShouCangFragment() {
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
    public static ShouCangFragment newInstance(String param1, String param2) {
        ShouCangFragment fragment = new ShouCangFragment();
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
        linearLayout_showCang = view.findViewById(R.id.linearLayout_shouCang);
        addSongCollection(currentUser.getSongCollection(), context);
    }

    private void addRecommendationCategoryItems(LinearLayout linearLayout) {
        String[] recommendationNames = {"收藏1", "收藏2", "收藏3", "收藏4"};
        ImageSource[] recommendationImages = {ImageSource.RECOMMENDATION1, ImageSource.RECOMMENDATION2, ImageSource.RECOMMENDATION3, ImageSource.RECOMMENDATION4};

        for (int i = 0; i < recommendationNames.length; i++) {
            ShowCangItemView showCangItemView = new ShowCangItemView(getContext());
            // 转为Drawable
            Drawable image = recommendationImages[i].getImageDrawable(requireContext());
            String name = recommendationNames[i];

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            showCangItemView.setCategory(name, image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.navigationToMusicPlayerFragment(name);
                }
            });
            // 布局管理
            layoutParams.setMargins(0, 0, 0, 20);
            showCangItemView.setLayoutParams(layoutParams);
            linearLayout.addView(showCangItemView);
            Log.d(TAG, recommendationNames[i] + " created with width: " + showCangItemView.getWidth() + " and height: " + showCangItemView.getHeight());
        }
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addSongCollection(ArrayList<Song> songCollection, android.content.Context context) {
        for (Song song : songCollection) {
            ShowCangItemView showCangItemView = new ShowCangItemView(context);
            Drawable image = song.getImageDrawable(context);
            String name = song.getTitle();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            showCangItemView.setCategory(name, image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.navigationToMusicPlayerFragment(name);
                }
            });
            layoutParams.setMargins(0, 0, 0, 20);
            showCangItemView.setLayoutParams(layoutParams);
            linearLayout_showCang.addView(showCangItemView);
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setContext(android.content.Context context) {
        this.context = context;
    }
}
