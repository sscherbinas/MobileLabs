package com.example.android.mobilecourse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;

public interface ApiService {
    @GET("robot")
    Call<List<Robot>> getRobots();
}
