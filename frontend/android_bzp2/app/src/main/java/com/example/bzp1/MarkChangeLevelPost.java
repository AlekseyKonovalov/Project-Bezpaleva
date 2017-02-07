package com.example.bzp1;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MarkChangeLevelPost {
    @POST("/changeMark?")
    Call<Mark> sendMark(@Query("id") int id, @Query("irrel") int irrel);
}
