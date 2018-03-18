package com.siimk.garden;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GardenListActivity extends Activity {
    private static final String TAG = "GardenListActivity";
    String dataFileGarden = "gardenData";
    String dataFileBed = "bedData";
    final int REQUEST_NEW_GARDEN = 101;
    final int REQUEST_EDIT_GARDEN = 102;
    Button btnAddGarden;
    GardenList gardenList;
    ArrayAdapter<String> gardenListAdapter;
    ListView gardenListView;

    float gardenViewHeight;
    float gardenViewWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_list_layout);
        gardenList = new GardenList();
        readGardenDataFromTextFile();

        gardenListView = (ListView) findViewById(R.id.garden_list_main);
        gardenListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gardenList.getNameList());
        gardenListView.setAdapter(gardenListAdapter);
        gardenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: Clicked on garden: " + position);
                Intent viewGardenIntent = new Intent(GardenListActivity.this, ViewGardenActivity.class);
                Garden garden = gardenList.get(position);

                viewGardenIntent.putExtra("editGarden", garden);
                viewGardenIntent.putExtra("editGardenPosition", position);
                startActivityForResult(viewGardenIntent, REQUEST_EDIT_GARDEN);
            }
        });

        btnAddGarden = (Button) findViewById(R.id.buttonAddGarden);
        btnAddGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GardenListActivity.this, AddGardenActivity.class);
                startActivityForResult(intent, REQUEST_NEW_GARDEN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_NEW_GARDEN && resultCode == 101){
            Garden garden00x = data.getParcelableExtra("save_garden");
            gardenList.add(garden00x);
            gardenListAdapter.notifyDataSetChanged();
        }
        if(requestCode == REQUEST_EDIT_GARDEN){
            Garden garden00x = data.getParcelableExtra("save_garden");
            int position = data.getIntExtra("save_position", 0);
            if(resultCode == 103 || resultCode == 102){
                gardenList.remove(position);
                if(resultCode == 102){
                    gardenList.add(garden00x, position);
                }
            }
            gardenListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        saveTaskData();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void readGardenDataFromTextFile(){
        try {
            Scanner gardenScanner = new Scanner(openFileInput(dataFileGarden));
            while(gardenScanner.hasNextLine()){
                String[] slicedLine = gardenScanner.nextLine().split("~!~");
                Log.d(TAG, "readGardenDataFromTextFile: " + slicedLine);
                String name = slicedLine[0];
                Double width = Double.parseDouble(slicedLine[1]);
                Double height = Double.parseDouble(slicedLine[2]);
                Double scaledW = Double.parseDouble(slicedLine[3]);
                Double scaledH = Double.parseDouble(slicedLine[4]);
                Double viewW = Double.parseDouble(slicedLine[5]);
                Double viewH = Double.parseDouble(slicedLine[6]);

                Garden gardenAdded = new Garden(name, width, height);
                gardenAdded.setScaledWidth(scaledW);
                gardenAdded.setScaledHeight(scaledH);
                gardenAdded.setViewWidth(viewW);
                gardenAdded.setViewHeight(viewH);
                gardenList.add(gardenAdded);
            }
            gardenScanner.close();

            Scanner bedScanner = new Scanner(openFileInput(dataFileBed));
            int lineCounter = 0;
            while(bedScanner.hasNextLine()){
                String[] slicedLine = bedScanner.nextLine().split("~!~");

                int bedCount = slicedLine.length / 6;
                for(int bedNumber = 0; bedNumber < bedCount; bedNumber ++){
                    String description = slicedLine[bedNumber*6];
                    if(description.equals("aaa404bbb00x")){
                        description = "";
                    }
                    String shortDesc = slicedLine[1 + bedNumber*6];
                    if(shortDesc.equals("aaa404ccc00x")){
                        shortDesc = "";
                    }
                    int x = Integer.parseInt(slicedLine[2 + bedNumber*6]);
                    int X = Integer.parseInt(slicedLine[3 + bedNumber*6]);
                    int y = Integer.parseInt(slicedLine[4 + bedNumber*6]);
                    int Y = Integer.parseInt(slicedLine[5 + bedNumber*6]);
                    Bed bed = new Bed(x, X, y, Y);
                    bed.setDescription(description);
                    this.gardenList.get(lineCounter).addBed(bed);
                }

                lineCounter++;
            }
            bedScanner.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "readGardenDataFromTextFile: file TodoData.txt was not found ----- requires immediate action!");
        } catch (ArrayIndexOutOfBoundsException e){
            Log.d(TAG, "readGardenDataFromTextFile: sth is out of bounds ----- requires immediate attention!");
        }
    }

    private void saveTaskData(){
        try {
            PrintWriter gardenWriter = new PrintWriter(openFileOutput(dataFileGarden, Context.MODE_PRIVATE));
            PrintWriter bedWriter = new PrintWriter(openFileOutput(dataFileBed, Context.MODE_PRIVATE));

            for(Garden garden : this.gardenList.getList()){
                for(Bed bed : garden.getBeds()){
                    String description = bed.getDescription();
                    if(description.equals("")){
                        description = "aaa404bbb00x";
                    }
                    String shortDescription = bed.getShortDesc();
                    if(shortDescription.equals("")){
                        shortDescription = "aaa404ccc00x";
                    }
                    int x = bed.getMinX();
                    int X = bed.getMaxX();
                    int y = bed.getMinY();
                    int Y = bed.getMaxY();
                    bedWriter.print(description + "~!~" + shortDescription + "~!~" +
                            x + "~!~" +
                            X + "~!~" +
                            y + "~!~" + Y + "~!~");
                }
                bedWriter.print("\n");

                String name = garden.getName();
                String width = Double.toString(garden.getWidth());
                String height = Double.toString(garden.getHeight());
                String scaledW = Double.toString(garden.getScaledWidth());
                String scaledH = Double.toString(garden.getScaledHeight());
                String viewW = Double.toString(garden.getViewWidth());
                String viewH = Double.toString(garden.getViewHeight());

                gardenWriter.print(name + "~!~" +
                        width + "~!~" +
                        height + "~!~" +
                        scaledW + "~!~" + scaledH + "~!~" +
                        viewW + "~!~" + viewH + "\n");
                Log.d(TAG, "saveTaskData: " + name + "~!~" +
                        width + "~!~" +
                        height + "~!~" +
                        scaledW + "~!~" + scaledH +
                        viewW + "~!~" + viewH + "\n");

            }
            gardenWriter.close();
            bedWriter.close();

        } catch (FileNotFoundException e) {
            Log.d(TAG, "onPause: Sth wrong with writing into file TodoData.txt ----- requires immediate action!");
        }
    }
}



















































