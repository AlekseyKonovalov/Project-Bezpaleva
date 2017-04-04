package com.example.bzp1;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserAPIget {
    @GET("/user?")
    Call<User> getUser(@Query("vkId") int vkId);
}
