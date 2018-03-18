package com.siimk.garden;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import static com.siimk.garden.GlobalFunctions.isNumeric;


public class AddGardenActivity extends Activity {

    private static final String TAG = "AddGardenActivity";
    final String SAVE_GARDEN = "save_garden";
    final int SAVE_GARDEN_CODE = 101;
    Button btnConfirmSize;
    Button btnConfirmGarden;
    Button btnReverse;
    EditText etWidth;
    EditText etHeight;
    EditText etName;
    BedDrawableView gardenView;
    ViewGroup drawGardenLayuot;
    LinearLayout gardenLayout;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_garden_layout);

        etWidth = (EditText) findViewById(R.id.width);
        etHeight = (EditText) findViewById(R.id.height);

        final Context context = this;
        btnConfirmSize = (Button) findViewById(R.id.buttonConfirmSize);
        btnConfirmSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawGardenLayuot = (ViewGroup) findViewById(R.id.linearLayoutGarden);
                String widthText = etWidth.getText().toString();
                String heightText = etHeight.getText().toString();
                if(widthText.length() > 0 && isNumeric(widthText) &&
                        heightText.length() > 0 && isNumeric(heightText)){
                    if(drawGardenLayuot.getChildCount() > 0){
                        drawGardenLayuot.removeAllViews();
                    }
                    int w, h;
                    w = Integer.parseInt(widthText);
                    h = Integer.parseInt(heightText);
                    gardenView = new BedDrawableView(context, w, h);
                    drawGardenLayuot.addView(gardenView);
                    etWidth.setText("");
                    etHeight.setText("");
                }else {
                    GlobalFunctions.alertbox(context, "Oops!", "Please enter the width and lenght of the garden.");
                }
            }
        });

        btnConfirmGarden = (Button) findViewById(R.id.buttonConfirmGarden);
        btnConfirmGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName = (EditText) findViewById(R.id.etBedName);
                if(gardenView == null || gardenView.getDrawnGarden() == null || etName.getText().toString().equals("")){
                    onBackPressed();
                }else {
                    Intent saveGardenIntent = new Intent();
                    Garden drawnGarden = gardenView.getDrawnGarden();
                    Log.d(TAG, "onClick: garden info: " + drawnGarden.getWidth() + " " + drawnGarden.getHeight());
                    Log.d(TAG, "onClick: garden info: " + drawnGarden.getScaledWidth() + " " + drawnGarden.getScaledHeight());
                    drawnGarden.setName(etName.getText().toString());

                    saveGardenIntent.putExtra(SAVE_GARDEN, drawnGarden);
//                    saveGardenIntent.putExtra("save_height", gardenView.getHeight());
//                    saveGardenIntent.putExtra("save_width", gardenView.getWidth());
                    setResult(SAVE_GARDEN_CODE, saveGardenIntent);
                    finish();
                }
            }
        });

        btnReverse = (Button) findViewById(R.id.buttonPrevious);
        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: " + drawGardenLayuot.getChildCount());
                //Log.d(TAG, "onClick: " + gardenView);
                if(drawGardenLayuot.getChildCount() > 0 && gardenView != null){
                    drawGardenLayuot.removeAllViews();
                    //Log.d(TAG, "onClick: childcount2 " + drawGardenLayuot.getChildCount());
                    //Log.d(TAG, "onClick: ...1 " + gardenView.getDrawnGarden().previousSetup().size());
                    Log.d(TAG, "onClick: " + gardenView.getDrawnGarden().size());
                    gardenView.getDrawnGarden().remove(gardenView.getDrawnGarden().size()-1);
                    Log.d(TAG, "onClick: " + gardenView.getDrawnGarden().size());
                    drawGardenLayuot.addView(gardenView);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}


























