package com.example.android.mobilecourse;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;
    private RobotAdapter adapter;
    private NetworkChangeReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.data_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(Objects.requireNonNull(getView()));
        loadRobots();
        registerNetworkMonitoring();
    }

    private void registerNetworkMonitoring() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new NetworkChangeReceiver(linearLayout);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(
                () -> {
                    loadRobots();
                    swipeRefreshLayout.setRefreshing(false);
                }
        );
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void loadRobots() {
        swipeRefreshLayout.setRefreshing(true);
        final ApiService apiService = getApplicationEx().getApiService();
        final Call<List<Robot>> call = apiService.getRobots();

        call.enqueue(new Callback<List<Robot>>() {
            @Override
            public void onResponse(final Call<List<Robot>> call,
                                   final Response<List<Robot>> response) {
                adapter = new RobotAdapter(item -> openRobotDetails(item), response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Robot>> call, Throwable t) {
                Snackbar.make(linearLayout, R.string.fail_to_update, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) Objects.requireNonNull(getActivity()).getApplication());
    }

    interface OnRobotClickListener {
        void onClick(Robot item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        }
    }

    private void initViews(View root) {
        recyclerView = root.findViewById(R.id.data_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        linearLayout = root.findViewById(R.id.linearLayout);
        swipeRefreshLayout = root.findViewById(R.id.data_list_swipe_refresh);
        setupSwipeToRefresh();
    }

    private void openRobotDetails(Robot item) {
        Intent robotDetailsIntent = new Intent(getContext(), RobotDetails.class);
        robotDetailsIntent.putExtra("robot_name", item.getRobotName());
        robotDetailsIntent.putExtra("robot_code", item.getRobotCode());
        robotDetailsIntent.putExtra("address", item.getAddress());
        robotDetailsIntent.putExtra("time", item.getTime());
        robotDetailsIntent.putExtra("trajectory", item.getTrajectory());
        robotDetailsIntent.putExtra("image", item.getPhotoUrl());
        startActivity(robotDetailsIntent);
    }
}