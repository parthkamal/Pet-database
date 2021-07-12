package com.parth.petsdatabase;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parth.petsdatabase.Data.PetContract;

public class CatalogActivity extends AppCompatActivity{

    private static final int PET_LOADER = 0;
    private  PetsCursorAdapter petsCursorAdapter;
    private Cursor cursor;


    private void SetAdapterToCatalogActivity(){
        //attaching cursor adapter to the list view
        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.lv_items);
        // Setup cursor adapter using cursor from last step
        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT };

        //for now we are not using the content providers and directly accessing the database by a raw query.
        Cursor cursor = getContentResolver().query(
                PetContract.PetEntry.CONTENT_URI,   // content Uri for the query
                projection,            // the projection of the query   basically the columns to return
                null,
                null,
                null);

        PetsCursorAdapter petsCursorAdapter = new PetsCursorAdapter(this,cursor);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(petsCursorAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(PetContract.PetEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        FloatingActionButton floatingActionButton = findViewById(R.id.catalog_floatingBtn);
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(CatalogActivity.this,EditorActivity.class)));

        SetAdapterToCatalogActivity();


    }

    @Override
    protected void onStart() {
        SetAdapterToCatalogActivity();
        super.onStart();
    }

    //this method will be called to represent the database of the app
    private void displayDatabaseInfo() {
        // we are directly using the content resolver

        //this is a projection of the table or in other word the attribute of the base columns
        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT };

        //for now we are not using the content providers and directly accessing the database by a raw query.
        Cursor cursor = getContentResolver().query(
                PetContract.PetEntry.CONTENT_URI,   // content Uri for the query
                projection,            // the projection of the query   basically the columns to return
                null,
                null,
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        try {

            displayView.setText("The pets table contains " + Integer.toString(cursor.getCount()).toString() + " pets.\n\n");
            displayView.append(PetContract.PetEntry._ID + " - " +
                    PetContract.PetEntry.COLUMN_PET_NAME + " - " +
                    PetContract.PetEntry.COLUMN_PET_BREED + " - " +
                    PetContract.PetEntry.COLUMN_PET_GENDER + " - " +
                    PetContract.PetEntry.COLUMN_PET_WEIGHT + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        currentGender + " - " +
                        currentWeight));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    //impleting the method to insert the dummy pet in the database
    private void insertDummyPet() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);

        //inserting the content values to the CONTENT_URI by calling the content resolver
        getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, values);
    }


    //inflating the options menu to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalog_menu,menu);
        return true;
    }

    private void deleteAllPets() {
        getContentResolver().delete(PetContract.PetEntry.CONTENT_URI, null, null);
        Toast.makeText(CatalogActivity.this,"Deleted all records in the table",Toast.LENGTH_SHORT).show();
    }

    //implementing options items selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.catalog_item_1:
                 insertDummyPet();
                 SetAdapterToCatalogActivity();
                 Toast.makeText(this,"added dummy data",Toast.LENGTH_SHORT).show();
                 break;
            case R.id.catalog_itme_2:
                deleteAllPets();
                SetAdapterToCatalogActivity();
            default:
        }
        return super.onOptionsItemSelected(item);
    }



}