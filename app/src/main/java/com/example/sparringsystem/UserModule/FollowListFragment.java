package com.example.sparringsystem.UserModule;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sparringsystem.MainActivity;
import com.example.sparringsystem.R;
import com.example.sparringsystem.customView.ShowCangItemView;
import com.example.sparringsystem.customView.UserItemView;
import com.example.sparringsystem.musicPlayer.Song;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowListFragment extends Fragment {
    LinearLayout linearLayout_followList;
    User currentUser;
    android.content.Context context;  // 用于获取资源
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowListFragment newInstance(String param1, String param2) {
        FollowListFragment fragment = new FollowListFragment();
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
        return inflater.inflate(R.layout.fragment_follow_list, container, false);
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout_followList = view.findViewById(R.id.linearLayout_followList);
        addFollow(currentUser.getFollowList(), context);
    }

    public void addFollow(ArrayList<User> followList, android.content.Context context) {
        for (User user : followList) {
            UserItemView userItemView = new UserItemView(context);
            Drawable image = user.getAvatarDrawable(context);
            String name = user.getUserName();
            int level = user.getLevel();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            userItemView.setCategory(user, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    // 跳转到用户详情页
                }
            });
            layoutParams.setMargins(0, 0, 0, 20);
            userItemView.setLayoutParams(layoutParams);
            linearLayout_followList.addView(userItemView);
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setContext(android.content.Context context) {
        this.context = context;
    }
}