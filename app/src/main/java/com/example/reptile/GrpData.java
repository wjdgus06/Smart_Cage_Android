package com.example.reptile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrpData {
    private static ArrayList<String> typeList = new ArrayList<String>(){{
        add("lizard");
        add("snake");
        add("turtle");
        add("crocodile");
        add("frog");
    }};

    public GrpData(){

    }

    public ArrayList<String> getTypeList() {
        return typeList;
    }

    public void addTypeList(String newType){
        typeList.add(newType);
    }
}
