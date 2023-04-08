package com.example.proj1;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.facebook.stetho.Stetho;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Build;
import android.util.Base64;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.content.Intent;

import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;
import android.content.Context;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import androidx.fragment.app.FragmentTransaction;
import com.example.proj1.R;
import java.net.UnknownHostException;
import java.net.SocketException;
import androidx.fragment.app.FragmentTransaction;
import java.io.UnsupportedEncodingException;
import java.lang.NullPointerException;
import com.google.android.material.snackbar.Snackbar;
import android.content.pm.PackageManager;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import android.view.View;
import android.widget.TextView;
import android.os.AsyncTask;
import org.apache.commons.io.FileUtils;
import android.content.res.AssetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.database.Cursor;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.SQLTimeoutException;

import java.lang.NullPointerException;

import javax.net.ssl.SSLHandshakeException;

public class MainActivity extends LoginActivity {
    // One Button
    Button BSelectImage;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;
    private static final int NOTIFICATION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /*    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_global_FirstFragment);*/
        // register the UI widgets with their appropriate IDs
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SecondFragment.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Storage", "Storage Permission Granted");
                // Permission granted, proceed with accessing storage
            } else {
                Log.e("Storage", "Storage Permission Denied");

                // Permission not granted, handle accordingly
            }
        }
    }
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    private class PostTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            HttpURLConnection conn = null;
            String response = "";
            try {
                URL url = new URL(urls[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urls[1]);
                writer.flush();
                writer.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);

                response = null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return response;
        }

        protected void onPostExecute(String result) {
            // Update UI with response
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }
    public static void readExcelFromAssets(Context context, String fileName, String wanted,ContentValues val) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        HSSFWorkbook workbook = null;
        try {
            inputStream = assetManager.open(fileName);
            Log.i("excelreading", "Reading from Excel" + fileName);

            // Create instance having reference to .xls file
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = null;
            // Fetch sheet at position 'i' from the workbook
            sheet = workbook.getSheetAt(0);

            // Iterate through each row
            for (Row row : sheet) {
                // Check if the first cell contains the string "plant"
                if (row.getCell(0) != null && row.getCell(0).getCellType() == STRING && row.getCell(0).getStringCellValue().equalsIgnoreCase(wanted)) {
                    // Iterate through all the cells in the row and print the values
                    int c=0;
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                // Print cell value
                                val.put("temperature",String.valueOf(cell.getNumericCellValue()));
                                Log.i("excelreading", String.valueOf(cell.getNumericCellValue()));
                                c++;
                                break;

                            case STRING:
                                if(c==0){
                                    Log.i("excelreading", "Scientific Name:"+String.valueOf(cell.getStringCellValue()));
                                }
                                if(c==1){
                                    val.put("coarse_soil",String.valueOf(cell.getStringCellValue()));
                                    Log.i("excelreading", "Adapted to coarsed soils:"+String.valueOf(cell.getStringCellValue()));
                                }
                                if(c==2){
                                    val.put("medium_soil",String.valueOf(cell.getStringCellValue()));
                                    Log.i("excelreading", "Adapted to medium soils:"+String.valueOf(cell.getStringCellValue()));
                                }
                                if(c==3){
                                    val.put("fine_soil",String.valueOf(cell.getStringCellValue()));
                                    Log.i("excelreading", "Adapted to fine soils:"+String.valueOf(cell.getStringCellValue()));
                                }
                                if(c==4){
                                    val.put("drought",String.valueOf(cell.getStringCellValue()));
                                    Log.i("excelreading", "Drought tolerance:"+String.valueOf(cell.getStringCellValue()));
                                }
                                if(c==5){
                                    val.put("fertility",String.valueOf(cell.getStringCellValue()));
                                    Log.i("excelreading", "Fertility required:"+String.valueOf(cell.getStringCellValue()));
                                }
                                if(c==6){
                                    val.put("moisture",String.valueOf(cell.getStringCellValue()));
                                Log.i("excelreading", "Moisture use:"+String.valueOf(cell.getStringCellValue()));
                            }
                                if(c==7){
                                    val.put("shade",String.valueOf(cell.getStringCellValue()));
                                Log.i("excelreading", "Shade tolerance:"+String.valueOf(cell.getStringCellValue()));
                            }
                               // Log.i("excelreading", cell.getStringCellValue());
                                c++;
                                break;

                        }

                    }

                }
            }
        } catch (IOException e) {
            Log.e("excelreading", "Error Reading Exception: ", e);
        } catch (Exception e) {
            Log.e("excelreading", "Failed to read file due to Exception: ", e);
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);

                    // get the file path from the URI
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                        File file = new File(getFilesDir(), fileName);
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            FileOutputStream outputStream = new FileOutputStream(file);
                            int read = 0;
                            byte[] bytes = new byte[1024];
                            while ((read = inputStream.read(bytes)) != -1) {
                                outputStream.write(bytes, 0, read);
                            }
                            inputStream.close();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String filePath = file.getAbsolutePath();
                        Log.d("HoneyPlant", "imagePath: " + filePath);
                        // create a JSON object with the required parameters
                        String apiKey = "2Jidrrl9sKrNYU0hfjiAClAyYEUmH82dUHpnNeRCrJ1xifmYtt";
                        JSONObject requestData = new JSONObject();
                        try {
                            requestData.put("api_key", apiKey);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // add image data to the request
                        JSONArray images = new JSONArray();
                        //  File file = new File(filePath);
                        try {
                            byte[] fileBytes = FileUtils.readFileToByteArray(file);
                            String imageData = Base64.encodeToString(fileBytes, Base64.DEFAULT);
                            images.put(imageData);
                            Log.d("MoneyPlant", "imagePath: " + images);
                            // Do something with the file bytes
                        } catch (IOException e) {
                            // Handle the exception
                            e.printStackTrace();
                        }
                        try {
                            requestData.put("images", images);
                            Log.d("HoneyPlant1", "imagePath: " + filePath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("HoneyPlant2", "imagePath: " + filePath);
                        }

                        // add modifiers to the request
                        JSONArray modifiers = new JSONArray()
                                .put("crops_fast")
                                .put("similar_images");
                        Log.d("HoneyPlant3", "imagePath: " + filePath);
                        try {
                            requestData.put("modifiers", modifiers);
                            Log.d("HoneyPlant4", "imagePath: " + filePath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("HoneyPlant5", "imagePath: " + filePath);

                        }


                        // add language to the request
                        try {
                            requestData.put("plant_language", "en");
                            Log.d("HoneyPlant6", "imagePath: " + filePath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("HoneyPlant7", "imagePath: " + filePath);
                        }


                        // add plant details to the request
                        JSONArray plantDetails = new JSONArray()
                                .put("url");
                        Log.d("HoneyPlant8", "imagePath: " + filePath);
                        try {
                            requestData.put("plant_details", plantDetails);
                            Log.d("HoneyPlant9", "imagePath: " + filePath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("HoneyPlant10", "imagePath: " + filePath);
                        }
                        // send the request to the API
                        try {
                            Log.d("HoneyPlant109", "imagePath: " + filePath);
                            String jsonString = requestData.toString();
                            PostTask postTask = new PostTask();
                            postTask.execute("https://api.plant.id/v2/identify", jsonString);
                            String response = postTask.get();
                            // Use the response
                            if (response != null && !response.isEmpty()) {
                                Log.d("HoneyPlant11", "imagePath: " + response);
                            } else {
                                Log.d("HoneyPlant11", "Response is null or empty.");
                            }
// parse the JSON response
                            JSONObject jsonResponse = new JSONObject(response);
// get the "suggestions" array from the response
                            JSONArray suggestions = jsonResponse.getJSONArray("suggestions");
                            Log.d("HoneyPlant20", "imagePath: " + suggestions.length());
                            JSONObject suggestion = suggestions.getJSONObject(0);
                            Log.d("HoneyPlant20", "imagePath: " + suggestion.getDouble("probability"));
                            Log.d("MoneyPlant24", "imagePath: " + suggestion.getString("plant_name"));
                            // Specify the path and name of the Excel file
                           Thread thread = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                            byte[] fileBytes1 = FileUtils.readFileToByteArray(file);
                                            String imageData = Base64.encodeToString(fileBytes1, Base64.DEFAULT);
                                            images.put(imageData);
                                            Log.d("MoneyPlant", "imagePath: " + images);
                                            // Do something with the file bytes

                                        Log.d("database", "I am here 1!" );
// Open the database file
                                        Log.d("database", "I am here 39!"+un+"hello");
                                        SQLiteDatabase db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath("mydb.db").getPath(),
                                                null,
                                                SQLiteDatabase.OPEN_READWRITE);
                                    /*    Log.d("database", "I am here 89!" );

                                        String selection = "id = ?";
                                        String[] selectionArgs0 = { "1" };
                                        String[] selectionArgs1 = { "2" };
                                        String[] selectionArgs2 = { "3" };

                                       db.delete("myplants", selection, selectionArgs0);
                                        db.delete("myplants", selection, selectionArgs1);
                                        db.delete("myplants", selection, selectionArgs2);


                                        Log.d("database", "deleted SUCCESSFULLY" );*/
                                        //db.close();
                                      ContentValues values = new ContentValues();
                                        values.put("user",un);
                                        values.put("plant_name", suggestion.getString("plant_name"));
                                        values.put("img",fileBytes1);
                                        readExcelFromAssets(MainActivity.this, "MyPlant.xls",suggestion.getString("plant_name"),values);
                                        Log.d("database", "I am here 2!" );
                                        long newRowId = db.insert("myplants", null, values);
                                        Log.d("database", "I am here 3!" );

                                        db.close();
                                        Log.d("HoneyPlant14", "imagePath: " + filePath);

                                    } catch (Exception e) {
                                        Log.d("database", e.getMessage()+"I am here 7!" + e.getCause() );
                                        e.printStackTrace();
                                    }

                                }
                            });

                            thread.start();

                            double probability = suggestion.getDouble("probability");
                            String PlantName =suggestion.getString("plant_name");

                            TextView responseTextView = findViewById(R.id.responseTextView);
                            responseTextView.setText("Specie_Name:"+suggestion.getString("plant_name"));

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("HoneyPlant15", "imagePath: " + filePath);
                        }
                    }

                }

            }
        }
    }

}