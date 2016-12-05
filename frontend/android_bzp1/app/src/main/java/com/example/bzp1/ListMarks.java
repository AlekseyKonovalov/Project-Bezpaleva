package com.example.bzp1;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListMarks{

    @SerializedName("ListMarks")
    @Expose
    private List<Mark> ListMarks= new ArrayList<Mark>();

    public List<Mark> getMarks() {
        return ListMarks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        ListMarks = marks;
    }
}

