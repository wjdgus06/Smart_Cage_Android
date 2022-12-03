package com.example.reptile;

public class ManLVItem {

    /* 아이템의 정보를 담기 위한 클래스 */

    String cageName;

    public ManLVItem(String cageName){
        this.cageName = cageName;
    }

    public String getCage() {
        return cageName;
    }
    public void setCage(){
        this.cageName = cageName;
    }
}
