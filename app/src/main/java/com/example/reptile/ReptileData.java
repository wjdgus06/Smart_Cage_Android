package com.example.reptile;

public class ReptileData {
    private String reptileName;
    private String grpName;
    private int imgPath;

    public ReptileData(String name, String groupName, int imgPath){
        this.reptileName = name;
        this.grpName = groupName;
        this.imgPath = imgPath;
    }

    public String getReptileName() {
        return reptileName;
    }

    public void setReptileName(String reptileName) {
        this.reptileName = reptileName;
    }

    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }
}
