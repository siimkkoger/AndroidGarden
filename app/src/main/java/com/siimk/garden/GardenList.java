package com.siimk.garden;

import java.util.ArrayList;

/**
 * Created by siimk on 6/13/2017.
 */

public class GardenList {

    private static final String TAG = "GardenList";
    private ArrayList<Garden> gardenList = new ArrayList<>();
    private ArrayList<String> gardenNameList = new ArrayList<>();

    public GardenList(ArrayList<Garden> gardenList) {
        this.gardenList = gardenList;
    }

    public GardenList(){

    }

    public void add(Garden g){
        gardenList.add(g);
        gardenNameList.add(g.getName());
    }

    public void add(Garden g, int position){
        gardenList.add(position, g);
        gardenNameList.add(position, g.getName());
    }

    public Garden get(int index){
        return gardenList.get(index);
    }

    public void remove(int position){
        gardenList.remove(position);
        gardenNameList.remove(position);
    }

    public void remove(Garden g){
        int index = 0;
        if(gardenList.contains(g)){
            for(int i = 0; i < gardenList.size(); i++){
                if(gardenList.get(i).equals(g)){
                    index = i;
                    break;
                }
            }
            gardenList.remove(index);
            gardenNameList.remove(index);
        }
    }

    public ArrayList<Garden> getList() {
        return gardenList;
    }

    public ArrayList<String> getNameList() {
        return gardenNameList;
    }
}
