package com.parth.petsdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        FloatingActionButton floatingActionButton = findViewById(R.id.catalog_floatingBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CatalogActivity.this,EditorActivity.class));
            }
        });

    }


    //inflating the options menu to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalog_menu,menu);
        return true;
    }

    //implementing options items selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.catalog_item_1:
                Toast.makeText(this,"Item 1 selected",Toast.LENGTH_SHORT).show();
            case R.id.catalog_itme_2:
                Toast.makeText(this,"Item 2 selected",Toast.LENGTH_SHORT).show();
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}