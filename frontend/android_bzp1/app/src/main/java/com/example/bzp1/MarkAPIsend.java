package com.example.bzp1;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MarkAPIsend {
    @Multipart
    @POST("/mark?")
    Call<Mark> createMark(@Part("x") double x, @Part("y") double y, @Part("type")RequestBody type ,
                          @Part("desc") RequestBody desc,  @Part("userId") int userId, @Part("photoFile") File photoFile);
}
