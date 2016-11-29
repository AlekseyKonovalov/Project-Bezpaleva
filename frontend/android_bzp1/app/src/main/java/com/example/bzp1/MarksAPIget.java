package com.example.bzp1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MarksAPIget {
    @GET("/mark?x=55.177635&y=61.331487&rad=10000")
    Call<List<Mark>> getMarks();
}



