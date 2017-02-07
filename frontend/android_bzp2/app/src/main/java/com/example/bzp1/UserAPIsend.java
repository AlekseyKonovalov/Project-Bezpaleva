package com.example.bzp1;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPIsend {
    @POST("/user?")
    Call<User> sendUser(@Query("fName") String fName, @Query("lName") String lName, @Query("vkId") int vkId);
}
