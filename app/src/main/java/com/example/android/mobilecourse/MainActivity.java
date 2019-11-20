package com.example.android.mobilecourse;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;
    private RobotAdapter adapter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        registerNetworkMonitoring();
        loadRobots();

    }

    private void registerNetworkMonitoring() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver(linearLayout);
        this.registerReceiver(networkChangeReceiver, filter);
    }

    private void loadRobots() {
        swipeRefreshLayout.setRefreshing(true);
        final ApiService apiService = getApplicationEx().getApiService();
        final Call<List<Robot>> call = apiService.getRobots();

        call.enqueue(new Callback<List<Robot>>() {
            @Override
            public void onResponse(@NonNull final Call<List<Robot>> call,
                                   @NonNull final Response<List<Robot>> response) {
                adapter = new RobotAdapter(response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Robot>> call, @NonNull Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(linearLayout, R.string.failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.data_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        linearLayout = findViewById(R.id.linearLayout);
        swipeRefreshLayout = findViewById(R.id.data_list_swipe_refresh);
        setupSwipeToRefresh();
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

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }
}
