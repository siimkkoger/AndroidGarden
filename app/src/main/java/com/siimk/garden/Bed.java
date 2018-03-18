package com.siimk.garden;


import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.x;
import static android.R.attr.y;

public class Bed implements Parcelable{
    private String name;
    private String description = "";
    private String shortDesc = "";
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private boolean isDrawn = false;

    public Bed(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    protected Bed(Parcel in) {
        name = in.readString();
        description = in.readString();
        shortDesc = in.readString();
        minX = in.readInt();
        maxX = in.readInt();
        minY = in.readInt();
        maxY = in.readInt();
    }

    public String getDescription() {
        return description;
    }

    public void setDrawn(boolean drawn) {
        isDrawn = drawn;
    }

    public boolean isDrawn(){
        return this.isDrawn;
    }

    public boolean collidesFinger(){
        if((this.getMinX() <= x && this.getMaxX() >= x) && (this.getMinY() <= y && this.getMaxY() >= y)){
            return true;
        }else {
            return false;
        }
    }

    public static final Creator<Bed> CREATOR = new Creator<Bed>() {
        @Override
        public Bed createFromParcel(Parcel in) {
            return new Bed(in);
        }

        @Override
        public Bed[] newArray(int size) {
            return new Bed[size];
        }
    };

    public void setDescription(String description) {
        this.description = description;
        if(description.length() > 20){
            shortDesc = description.substring(0, 20) + "...";
        }else {
            shortDesc = description;
        }
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public String toString(){
        return minX + " " + minY + " " + maxX + " " + maxY + " " + isDrawn();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(shortDesc);
        dest.writeInt(minX);
        dest.writeInt(maxX);
        dest.writeInt(minY);
        dest.writeInt(maxY);
    }
}


























