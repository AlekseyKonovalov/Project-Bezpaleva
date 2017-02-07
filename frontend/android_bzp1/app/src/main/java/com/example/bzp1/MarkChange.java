package com.example.bzp1;


import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MarkChange {
    @Multipart
    @POST("/changeMark?")
    Call<Mark> changeMark(@Query("id") int id,
                        @Part("type") RequestBody type ,
                        @Part("desc") RequestBody desc
                      );
}
