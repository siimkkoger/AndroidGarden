package com.siimk.garden;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewGardenActivity extends AppCompatActivity{
    private static final String TAG = "ViewGardenActivity";
    final String SAVE_GARDEN = "save_garden";
    final int SAVE_GARDEN_CODE = 102;
    final int DELETE_GARDEN_CODE = 103;
    Button btnDelete;
    Button btnSave;
    EditText etName;
    TextView W;
    TextView H;
    BedView gardenView;
    ViewGroup gardenLayout;
    final Context context = this;
    Garden garden;
    int gardenPosition;
    int bedPosition;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_view_layout);
        Bundle gardenData = getIntent().getExtras();
        gardenPosition = gardenData.getInt("editGardenPosition");
        garden = gardenData.getParcelable("editGarden");

        //W = (TextView) findViewById(R.id.tvW);
        //H = (TextView) findViewById(R.id.tvH);
        //W.setText(Double.toString(garden.getWidth()));
        //H.setText(Double.toString(garden.getHeight()));
        etName = (EditText) findViewById(R.id.etBedName);
        etName.setText(garden.getName());
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnSave = (Button) findViewById(R.id.buttonSave);
        gardenView = new BedView(context, garden, true, new Bed(0, 0, 0, 0));
        gardenLayout = (ViewGroup) findViewById(R.id.llgardenMap);
        gardenLayout.addView(gardenView);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveGardenIntent = new Intent();
                saveGardenIntent.putExtra(SAVE_GARDEN, garden);
                saveGardenIntent.putExtra("save_position", gardenPosition);
                setResult(DELETE_GARDEN_CODE, saveGardenIntent);
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().equals(" ")){
                    GlobalFunctions.alertbox(context, "Oops!", "Please choose a name for your garden");
                }else{
                    garden.setName(etName.getText().toString());
                    Intent saveGardenIntent = new Intent();
                    saveGardenIntent.putExtra(SAVE_GARDEN, garden);
                    saveGardenIntent.putExtra("save_position", gardenPosition);
                    setResult(SAVE_GARDEN_CODE, saveGardenIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 404){
            garden = data.getParcelableExtra("save_garden");
            if(gardenLayout.getChildCount() > 0){
                gardenLayout.removeAllViews();
            }
            gardenLayout.addView(new BedView(context, garden, true, new Bed(0, 0, 0, 0)));
        }

    }

    @Override
    public void onBackPressed() {
        if(etName.getText().toString().equals(" ")){
            GlobalFunctions.alertbox(context, "Oops!", "Please choose a name for your garden");
        }else{
            garden.setName(etName.getText().toString());
            Intent saveGardenIntent = new Intent();
            saveGardenIntent.putExtra(SAVE_GARDEN, garden);
            saveGardenIntent.putExtra("save_position", gardenPosition);
            setResult(SAVE_GARDEN_CODE, saveGardenIntent);
            finish();
            super.onBackPressed();
        }
    }

}























