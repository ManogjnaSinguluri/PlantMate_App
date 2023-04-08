package com.example.proj1;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj1.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends LoginActivity {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<ImageItem> imageList;
    private String username = un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        // Get the username from the previous activity
        username = un;
        Log.d("MovePlant1", "koo" + un);
        // Initialize the RecyclerView and the list of images
        recyclerView = findViewById(R.id.recycler_view);
        imageList = new ArrayList<>();

        // Initialize the adapter with the empty list of images
        adapter = new ImageAdapter(imageList);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView




        // Open the database for reading
        SQLiteDatabase db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath("mydb.db").getPath(),
                null,
                SQLiteDatabase.OPEN_READONLY);
        if (db != null) {
            // Query the database for the image corresponding to the username
            Cursor cursor = db.rawQuery("SELECT * FROM myplants WHERE user = ?", new String[]{username});

            // Iterate over the results of the query and add the images to the list
            if (cursor.moveToFirst()) {
                Log.d("MovePlant1", "kool");
                do {

                    String p_name = cursor.getString(3);
                    byte[] imageBytes = cursor.getBlob(2);
                    Bitmap our_img = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imageList.add(new ImageItem(p_name, our_img));
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No data is there :(", Toast.LENGTH_SHORT).show();
            }
            Log.d("MovePlant1", "coke");
            // Close the cursor and the database
            cursor.close();
        } else {
            Toast.makeText(this, "Database not found!", Toast.LENGTH_SHORT).show();
        }
        db.close();
        ImageAdapter objadap=new ImageAdapter(imageList);
        recyclerView.hasFixedSize();
    //    int spanCount = 2; // number of columns in the grid
        // Create a new LinearLayoutManager with vertical orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

// Set the layout manager to the RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
     //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
        recyclerView.setAdapter(objadap);
        // Notify the adapter that the data set has changed
      //  adapter.notifyDataSetChanged();

        // Set the click listener for the RecyclerView items
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SecondFragment.this, ThirdActivity.class);

                // Get the clicked item
                ImageItem clickedItem = imageList.get(position);

                // Add the image resource id to the intent
                intent.putExtra("imageResource", clickedItem.getImageDes());

                // Start the new activity with the intent
                startActivity(intent);
            }
        });

        ImageButton button = findViewById(R.id.add_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondFragment.this, MainActivity.class));
                finish();
            }
        });
    }

}