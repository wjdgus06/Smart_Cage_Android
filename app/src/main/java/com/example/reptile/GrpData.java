package com.example.reptile;

public class GrpData {
    private String[] typeList;

    public GrpData(){
        this.typeList = new String[]{"test-grp1", "test-grp2"};
    }

    public String[] getTypeList() {
        return typeList;
    }

    public void setTypeList(String[] typeList) {
        this.typeList = typeList;
    }
}
