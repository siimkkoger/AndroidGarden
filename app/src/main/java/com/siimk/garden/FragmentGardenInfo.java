package com.siimk.garden;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by siimk on 6/11/2017.
 */

public class FragmentGardenInfo extends Fragment {
    private static final String TAG = "Tab2Fragment";

    final int REQUEST_EDIT_GARDEN = 102;
    Bed bed;
    EditText notesField;
    Button btnSaveInfo;
    Button btnDeleteGarden;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.garden_info_layout, container, false);
        notesField = (EditText)view.findViewById(R.id.editTextDescription);

        bed = getArguments().getParcelable("send_bed");
        if(bed == null){
            throw new RuntimeException("Bed object wasn't given to Fragment(GardenInfo)");
        }

        if(!bed.getDescription().equals("")){
            notesField.setText(bed.getDescription());
        }


        btnSaveInfo = (Button) view.findViewById(R.id.buttonSaveGardenInfo);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BedTabActivity bta1 = (BedTabActivity)getActivity();
                Log.d(TAG, "onClick: " + notesField.getText().toString());
                bta1.onSave(notesField.getText().toString());
            }
        });

        btnDeleteGarden = (Button) view.findViewById(R.id.buttonDeleteGarden);
        btnDeleteGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalFunctions.alertbox(getContext(), "Caution!", "You are going to delete this bed, proceed?");
                BedTabActivity bta2 = (BedTabActivity)getActivity();
                bta2.onDelete();
            }
        });

        return view;
    }
}































