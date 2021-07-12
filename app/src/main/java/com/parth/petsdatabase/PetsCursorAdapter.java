package com.parth.petsdatabase;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.parth.petsdatabase.Data.PetContract;

public class PetsCursorAdapter extends CursorAdapter {
    public PetsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_items,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvPetName = (TextView) view.findViewById(R.id.tvPetName);
        TextView tvPetBreed = (TextView) view.findViewById(R.id.tvPetBreed);
        // Extract properties from cursor
        String PetName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_NAME));
        String PetBreed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_BREED));

        // Populate fields with extracted properties
        tvPetName.setText(PetName);
        tvPetBreed.setText(PetBreed);

    }
}
