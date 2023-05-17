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

public class StatusActivity extends AppCompatActivity {
    // One Button
    Button BSelectImag;
    ImageView IVPreviewImag;
    int SELECT_PICTURE = 200;
    private static final int NOTIFICATION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    /*    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_global_FirstFragment);*/
        // register the UI widgets with their appropriate IDs
        BSelectImag = findViewById(R.id.BSelectImage2);
        IVPreviewImag = findViewById(R.id.IVPreviewImage2);
        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImag.setOnClickListener(new View.OnClickListener() {
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
//input,progress units that will be reported and output
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
                    IVPreviewImag.setImageURI(selectedImageUri);

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
                        JSONArray disease_details = new JSONArray()
                                .put("description")
                                 .put("treatment");
                        Log.d("HoneyPlant8", "imagePath: " + filePath);
                        try {
                            requestData.put("disease_details", disease_details);
                            Log.d("HoneyPlant9", "imagePath: " + filePath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("HoneyPlant10", "imagePath: " + filePath);
                        }
                        // send the request to the API
                        try {
                            int h=0;
                            Log.d("HoneyPlant109", "imagePath: " + filePath);
                            String jsonString = requestData.toString();
                            PostTask postTask = new PostTask();
                            postTask.execute("https://api.plant.id/v2/health_assessment", jsonString);
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
                            JSONObject suggestions = jsonResponse.getJSONObject("health_assessment");
                            Double prob=suggestions.getDouble("is_healthy_probability");
                            String check;
                            if(prob>0.5){
                                h=1;
                                check="Healthy";
                            }
                            else{
                                JSONArray suggestion = suggestions.getJSONArray("diseases");
                                JSONObject di=suggestion.getJSONObject(0);
                                check=di.getString("name");
                              //  Log.d("HoneyPlant20", "imagePath: " + suggestion.getDouble("probability"));
                              //  Log.d("MoneyPlant24", "imagePath: " + suggestion.getBoolean("p"));
                            }

                            TextView responseTextView = findViewById(R.id.responseTextView2);
                            if(h==1) {
                                responseTextView.setText("     Plant is " + check+"!");
                            }
                            else{
                                responseTextView.setText("   Diagnosed Disease: " + check);
                            }

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
