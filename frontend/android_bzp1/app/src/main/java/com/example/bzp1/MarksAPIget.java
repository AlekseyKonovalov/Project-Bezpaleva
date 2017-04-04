package com.example.bzp1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarksAPIget {
    @GET("/mark?")
    Call<List<Mark>> getMarks(@Query("x") double x, @Query("y") double y, @Query("rad")int rad);
}



