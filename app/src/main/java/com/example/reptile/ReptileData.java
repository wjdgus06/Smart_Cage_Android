package com.example.reptile;

public class ReptileData {
    private String reptileName;
    private int imgPath;

    public ReptileData(String name, int imgPath){
        this.reptileName = name;
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
}
