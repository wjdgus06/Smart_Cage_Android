package com.example.reptile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrpData {
    private static ArrayList<String> typeList = new ArrayList<String>(){{
        add("lizard");
        add("snake");
<<<<<<< HEAD
        add("turtle");
=======
        add("testcameragrp2");
>>>>>>> f123739062f2276599b046419fa124184c0b7705
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
