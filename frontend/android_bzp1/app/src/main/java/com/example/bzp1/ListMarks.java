package com.example.bzp1;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListMarks{

    @SerializedName("ListMarks")
    @Expose
    private ArrayList<Mark> ListMarks= new ArrayList<Mark>();

    public ArrayList<Mark> getMarks() {
        return ListMarks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        ListMarks = marks;
    }
}

