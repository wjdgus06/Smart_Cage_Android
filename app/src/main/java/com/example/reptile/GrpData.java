package com.example.reptile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrpData {
    private ArrayList<String> typeList;

    public GrpData(){
        this.typeList = new ArrayList<>();
        typeList.add("test-grp1");
        typeList.add("test-grp2");
    }

    public ArrayList<String> getTypeList() {
        return typeList;
    }

    public void addTypeList(String newType){
        typeList.add(newType);
    }
}
