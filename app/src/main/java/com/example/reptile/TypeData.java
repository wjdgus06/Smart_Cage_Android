package com.example.reptile;

import java.util.ArrayList;

public class TypeData {
    private String typeName;
    private ArrayList<ReptileData> reptileList;

    public TypeData(String name, ArrayList<ReptileData> data){
        this.typeName = name;
        this.reptileList = data;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ArrayList<ReptileData> getReptileList() {
        return reptileList;
    }

    public void setReptileList(ArrayList<ReptileData> reptileList) {
        this.reptileList = reptileList;
    }
}
