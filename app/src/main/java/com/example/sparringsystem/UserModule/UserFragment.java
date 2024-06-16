package com.example.sparringsystem.UserModule;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.sparringsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // 当前用户
    public User currentUser;
    public android.content.Context context;  // 用于获取资源

    // 子fragment
    public ShouCangFragment shouCangFragment;
    public FollowListFragment followListFragment;
    public FansListFragment fansListFragment;

    // 子控件
    private TextView userNameView;
    private ImageView userAvatarView;
    private TextView userFansNumView;
    private TextView userFollowNumView;
    private TextView userLevelView;
    private TextView userVipLevelView;
    private TextView userIdView;

    public UserFragment() {
        // Required empty public constructor
        shouCangFragment = new ShouCangFragment();
        fansListFragment = new FansListFragment();
        followListFragment = new FollowListFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Find the ListView and set an item click listener
        ListView listView = (ListView) view.findViewById(R.id.listView);
        String[] items = getResources().getStringArray(R.array.list_items);
        // 设置样式为list_item
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadFragmentFromListView(position);
            }
        });

        // 初始化子控件
        userNameView = view.findViewById(R.id.userNameView);
        userAvatarView = view.findViewById(R.id.userAvatarView);
        userFansNumView = view.findViewById(R.id.userFansNumView);
        userFollowNumView = view.findViewById(R.id.userFollowNumView);
        userLevelView = view.findViewById(R.id.userLevelView);
        userVipLevelView = view.findViewById(R.id.userVipLevelView);
        userIdView = view.findViewById(R.id.userIdView);
        setUser(currentUser, context);

        // 为粉丝数和关注数设置点击事件
        userFansNumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到粉丝列表
                loadFragment(fansListFragment);
            }
        });

        userFollowNumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到关注列表
                loadFragment(followListFragment);
            }
        });
        return view;
    }

    void loadFragmentFromListView(int position) {
        // Create a new instance of the fragment
        Fragment fragment;
        switch (position) {
            case 0: // 我的收藏
                fragment = ShouCangFragment.newInstance("param1", "param2");
                break;
            // ...其他case...
            default:
                fragment = new UserFragment(); // replace with your default fragment
                break;
        }

        // Replace the current fragment with the new one
        navigationToShouCangFragment("");
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void navigationToShouCangFragment(String name) {
        loadFragment(shouCangFragment);
    }

    public void setUser(User currentUser, android.content.Context context) {
        this.currentUser = currentUser;
        // 若还未初始化子控件，则不进行操作
        if (userNameView == null) {
            Log.e(TAG, "UserFragment not initialized");
            return;
        }
        // 设置当前用户
        userNameView.setText("昵称：" + currentUser.getUserName());
        userAvatarView.setImageResource(currentUser.getAvatarId());
        userFansNumView.setText("粉丝数：" + currentUser.getFansList().size());
        userFollowNumView.setText("关注数：" + currentUser.getFollowList().size());
        userVipLevelView.setText("VIP等级：" + currentUser.getVipLevel());
        userIdView.setText("用户ID：" + currentUser.getId());
        shouCangFragment.setCurrentUser(currentUser);
        fansListFragment.setCurrentUser(currentUser);
        followListFragment.setCurrentUser(currentUser);
        shouCangFragment.setContext(context);
        fansListFragment.setContext(context);
        followListFragment.setContext(context);
    }
}