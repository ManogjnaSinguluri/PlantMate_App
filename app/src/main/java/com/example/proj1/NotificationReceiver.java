package com.example.proj1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";
    private static final int NOTIFICATION_ID = 1;
    private static final int CHANNEL_ID =1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Connect to the SQLite database
        //   SQLiteDatabase db = new PlantDbHelper(context).getReadableDatabase();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getApplicationContext().getDatabasePath("mydb.db").getPath(),
                null,
                SQLiteDatabase.OPEN_READONLY);
        // Execute a query to retrieve plant names with 'yes' column

        String[] projection = {
                "plant_name",
                "moisture"
        };

        String selection = "moisture = ?";
        String[] selectionArgs = {"Medium"};

        Cursor cursor = db.query(
                "myplants",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        // Build the notification message with the plant names
        StringBuilder message = new StringBuilder();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String plantName=cursor.getString(cursor.getColumnIndexOrThrow("plant_name"));
                message.append(plantName).append(", ");
            }
            message.deleteCharAt(message.length() - 1);
            message.deleteCharAt(message.length() - 1);
            message.append(" need water today!");
            // existing code to build the notification message
            // Create the notification channel
            createNotificationChannel(context);
            // display the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(CHANNEL_ID))
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Plants need to be watered")
                    .setContentText(message.toString())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            message.append("No plants need water today.");
        }

// Close the cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();

    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Plant Care Channel";
            String description = "Channel for Plant Care notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("plant_notifications", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
