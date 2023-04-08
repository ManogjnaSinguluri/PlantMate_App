package com.example.proj1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
    public class LoginActivity extends AppCompatActivity {
        EditText username, password;
        Button btnlogin,signup1;
        ConnectionHelper DB;
        public static String un = "";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.loginf);

            username = (EditText) findViewById(R.id.username1);
            password = (EditText) findViewById(R.id.password1);
            btnlogin = (Button) findViewById(R.id.btnsignin1);
            signup1  = (Button) findViewById(R.id.btnsignup2) ;
            DB = new ConnectionHelper(this);
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user = username.getText().toString();
                    String pass = password.getText().toString();

                    if (user.equals("") || pass.equals(""))
                        Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    else {
                        Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                        if (checkuserpass == true) {
                            LoginActivity.this.un=user;
                            Log.d("ringPlant5", "imagePath: " + un);
                            Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SecondFragment.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            Log.d("ringPlant51", "imagePath: " + un);
            signup1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,Signupclass.class );
                    startActivity(intent);
                }
            });
        }

    }