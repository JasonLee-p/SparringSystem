package com.example.sparringsystem.PracticeModule;

import android.os.Bundle;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sparringsystem.MainActivity;
import com.example.sparringsystem.R;
import com.example.sparringsystem.customView.MusicItemView;
import com.example.sparringsystem.musicPlayer.Song;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongListFragment extends Fragment {

    LinearLayout linearLayout_songList;
    android.content.Context context;  // 用于获取资源
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SongListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongListFragment newInstance(String param1, String param2) {
        SongListFragment fragment = new SongListFragment();
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
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout_songList = view.findViewById(R.id.linearLayout_songList);

        // 给songListFragment暂时的地添加一个歌曲
        addSong(new Song(R.raw.sample_song, "sample_song", "Unknown Artist"), context);
    }

    /**
     * 加载Fragment，不创建新实例
     * {@link Fragment#onCreateView Fragment.onCreateView}
     *
     * @param fragment 需要加载的Fragment
     *
     * @return 无
     */
    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addSongs(ArrayList<Song> songList, android.content.Context context) {
        for (Song song : songList) {
            addSong(song, context);
        }
    }

    public void addSong(Song song, android.content.Context context){
        MusicItemView musicItemView = new MusicItemView(context);
        Drawable image = song.getImageDrawable(context);
        String name = song.getTitle();
        String artist = song.getArtist();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        musicItemView.setCategory(song, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                // 跳转到歌曲详情页
                mainActivity.navigationToMusicPlayerFragment(name);
            }
        });
        layoutParams.setMargins(0, 0, 0, 20);
        musicItemView.setLayoutParams(layoutParams);
        linearLayout_songList.addView(musicItemView);
    }

    public void setContext(android.content.Context context) {
        this.context = context;
    }
}
