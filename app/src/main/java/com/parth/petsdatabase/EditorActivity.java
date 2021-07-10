package com.parth.petsdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parth.petsdatabase.Data.PetContract;
import com.parth.petsdatabase.Data.PetDatabaseOpenHelper;

public class EditorActivity extends AppCompatActivity {
    private int mGender= PetContract.PetEntry.GENDER_UNKNOWN;
    private EditText mNameEditText,mBreedEditText,mWeightEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //using find view by id for edit text
        mNameEditText =findViewById(R.id.edPetName);
        mBreedEditText = findViewById(R.id.edPetBreed);
        mWeightEditText =findViewById(R.id.edPetWeight);


        /**  creating the spinner**/
        Spinner spinner = (Spinner) findViewById(R.id.spinnerGender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //setting the on item selected click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String genderSelected = adapterView.getItemAtPosition(i).toString();
                if(!TextUtils.isEmpty(genderSelected)){
                    if (genderSelected.equals(getString(R.string.gender_male))) {
                        mGender = PetContract.PetEntry.GENDER_MALE;
                    } else if (genderSelected.equals(getString(R.string.gender_female))) {
                        mGender = PetContract.PetEntry.GENDER_FEMALE;
                    } else {
                        mGender = PetContract.PetEntry.GENDER_UNKNOWN;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mGender = PetContract.PetEntry.GENDER_UNKNOWN;

            }
        });

    }

    private void insertPet() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String breedString = mBreedEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        int weight = Integer.parseInt(weightString);

        // Create database helper
        PetDatabaseOpenHelper mDbHelper =new PetDatabaseOpenHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, nameString);
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, breedString);
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, mGender);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, weight);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete:
                Toast.makeText(this,"Deleted the Record",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save:
                insertPet();
                Toast.makeText(this, "Saved the Record", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}