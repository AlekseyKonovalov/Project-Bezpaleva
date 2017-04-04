package com.example.bzp1;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MarkChangeLevelPost {
    @POST("/deleteMark?")
    Call<Mark> sendMark(@Query("idMark") int id, @Query("key") String key);
}
