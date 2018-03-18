package com.siimk.garden;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class Garden implements Parcelable{
    private static final String TAG = "Garden";
    private ArrayList<Bed> beds = new ArrayList<>();
    private ArrayList<Bed> bedsCopy = new ArrayList<>();
    private String name;
    private double width;
    private double height;

    private double scaledWidth;
    private double scaledHeight;

    private double viewWidth;
    private double viewHeight;


    public Garden(String name, double width, double height){
        this.name = name;
        this.width = width;
        this.height = height;
        this.scaledWidth = width;
        this.scaledHeight = height;
    }

    protected Garden(Parcel in) {
        beds = in.createTypedArrayList(Bed.CREATOR);
        bedsCopy = in.createTypedArrayList(Bed.CREATOR);
        name = in.readString();
        width = in.readDouble();
        height = in.readDouble();
        scaledWidth = in.readDouble();
        scaledHeight = in.readDouble();
        viewHeight = in.readDouble();
        viewWidth = in.readDouble();
    }

    public static final Creator<Garden> CREATOR = new Creator<Garden>() {
        @Override
        public Garden createFromParcel(Parcel in) {
            return new Garden(in);
        }

        @Override
        public Garden[] newArray(int size) {
            return new Garden[size];
        }
    };

    public void addBed(Bed newBed){
        bedsCopy = beds;
        this.beds.add(newBed);
        ArrayList<Bed> removedBeds = new ArrayList<>();
        for(Bed bed : beds){
            if(!newBed.equals(bed) &&
                    newBed.getMinX() <= bed.getMinX() &&
                    newBed.getMaxX() >= bed.getMaxX() &&
                    newBed.getMinY() <= bed.getMinY() &&
                    newBed.getMaxY() >= bed.getMaxY()){
                removedBeds.add(bed);
            }
        }
        beds.removeAll(removedBeds);
    }

    public Bed chosenBed(float X, float Y){
        for(int i = beds.size()-1; i >= 0; i--){
            Bed bed = beds.get(i);
            if(bed.getMinX() <= X && bed.getMaxX() >= X && bed.getMinY() <= Y && bed.getMaxY() >= Y){
                return bed;
            }
        }
        Log.d(TAG, "chosenBed: You chose an area where are no flowerbeds.");
        return null;
    }

    public int size(){
        return beds.size();
    }

    public Bed getBed(int index){
        return beds.get(index);
    }

    public double getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(double viewWidth) {
        this.viewWidth = viewWidth;
    }

    public double getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(double viewHeight) {
        this.viewHeight = viewHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void previousSetup(){
        beds = bedsCopy;
    }

    public void setScaledWidth(double scaledWidth) {
        this.scaledWidth = scaledWidth;
    }

    public void setScaledHeight(double scaledHeight) {
        this.scaledHeight = scaledHeight;
    }

    public double getScaledWidth() {
        return scaledWidth;
    }

    public double getScaledHeight() {
        return scaledHeight;
    }

    public ArrayList<Bed> getBeds() {
        return beds;
    }

    public ArrayList<Bed> getBedsCopy() {
        return bedsCopy;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Bed getBed(Bed bed){
        for(Bed b : beds){
            if(b.equals(bed)){
                return b;
            }
        }
        return null;
    }

    public void remove(int position){
        beds.remove(position);
    }

    public void add(int position, Bed bed){
        this.beds.add(position, bed);
    }

    public int getPosition(Bed bed){
        for(int i = 0; i < beds.size(); i++){
            if(this.getBeds().get(i).equals(bed)){
                return i;
            }
        }
        return 999;
    }

    public boolean fingerCollidingWithBed(float x, float y){
        for(Bed bed : beds){
            if((bed.getMinX() <= x && bed.getMaxX() >= x) && (bed.getMinY() <= y && bed.getMaxY() >= y)){
                return true;
            }
//            if(bed.isDrawn()){
//                if((bed.getMinX() <= x && bed.getMaxX() >= x) && (bed.getMinY() <= y && bed.getMaxY() >= y)){
//                    return true;
//                }
//            }
        }
        return false;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedList(beds);
        dest.writeTypedList(bedsCopy);
        dest.writeString(name);
        dest.writeDouble(width);
        dest.writeDouble(height);
        dest.writeDouble(scaledWidth);
        dest.writeDouble(scaledHeight);
        dest.writeDouble(viewWidth);
        dest.writeDouble(viewHeight);
    }
}
