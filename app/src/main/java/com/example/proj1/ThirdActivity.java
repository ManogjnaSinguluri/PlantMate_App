package com.example.proj1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Get the image resource identifier passed from the second activity
        Intent intent = getIntent();
        String imagename = intent.getStringExtra("imageResource");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath("mydb.db").getPath(),
                null,
                SQLiteDatabase.OPEN_READONLY);
        String soil="";
        String moisture="";
        String fertile="";
        String Shade="";
        String temp="";
        if (db != null) {
            // Query the database for the image corresponding to the username
            Cursor cursor = db.rawQuery("SELECT * FROM myplants WHERE plant_name = ?", new String[]{imagename});

            // Iterate over the results of the query and add the images to the list
            if (cursor.moveToFirst()) {
                Log.d("MovePlant1", "kool");
                do {
                    moisture=cursor.getString(9);
                    if(moisture==null){
                        moisture="not found in dataset!";
                    }
                    fertile=cursor.getString(8);
                    if(fertile==null){
                        fertile="not found in dataset!";
                    }
                    Shade=cursor.getString(10);
                    if(Shade==null){
                        Shade="not found in dataset!";
                    }
                    temp=cursor.getString(11);
                    if(temp==null){
                        temp="not found in dataset!";
                    }
                    if(cursor.getString(4).equals("Yes")){
                           soil="coarse,";
                    }
                    else{
                        soil=soil+"";
                    }
                    if(cursor.getString(5).equals("Yes")){
                        soil=soil+"medium,";
                    }
                    else{
                        soil=soil+"";
                    }
                    if(cursor.getString(6).equals("Yes")){
                        soil=soil+"fine";
                    }
                    else{
                        soil=soil+"";
                    }
                    if(soil==null){
                        soil="not found in dataset!";
                    }
                    byte[] imageBytes = cursor.getBlob(2);
                    Bitmap our_img = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
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
        // Find the ImageView in the layout
        TextView textview=findViewById(R.id.textView);

        // Set the Bitmap as the image for the ImageView
        textview.setText(imagename);
        TextView textview1=findViewById(R.id.tw1);

        // Set the Bitmap as the image for the ImageView
        textview1.setText("Soil type: "+soil);

        TextView textview2=findViewById(R.id.tw2);

        // Set the Bitmap as the image for the ImageView
        textview2.setText("Moisture requirement:"+moisture);

        TextView textview3=findViewById(R.id.tw3);

        // Set the Bitmap as the image for the ImageView
        textview3.setText("Shade Tolerance: "+Shade);

        TextView textview4=findViewById(R.id.tw4);
        // Set the Bitmap as the image for the ImageView

        textview4.setText("Maximum Temperature: "+temp);
        TextView textview5=findViewById(R.id.tw5);
        textview5.setText("Fertility requirement: "+fertile);


    }
}