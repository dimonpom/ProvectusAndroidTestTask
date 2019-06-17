package com.example.provectus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private TextView fullname, dob, age, city, street, email, username, password, phone, regDate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initializeXML();

        Bundle args = getIntent().getExtras();
        if (args!=null){
            UserData userData = args.getParcelable("data");
            fullname.setText(userData.getName());
            dob.setText(userData.getDob());
            age.setText("Age: "+userData.getAge());
            city.setText("City: "+userData.getCity());
            street.setText("Street: "+userData.getStreet()+", "+userData.getPostcode());
            email.setText(userData.getEmail());
            username.setText(userData.getUsername());
            password.setText(userData.getPassword());
            phone.setText(userData.getPhone());
            regDate.setText(userData.getRegistered());
            imageView.setImageBitmap(userData.getLargePicture());
        }else
            Toast.makeText(getApplicationContext(), "Error when reading info", Toast.LENGTH_SHORT).show();
    }

    private void initializeXML() {
        fullname = findViewById(R.id.tV_fullname);
        dob = findViewById(R.id.tV_dob);
        age = findViewById(R.id.tV_age);
        city = findViewById(R.id.tV_city);
        street = findViewById(R.id.tV_street);
        email = findViewById(R.id.tV_email);
        username = findViewById(R.id.tV_username);
        password = findViewById(R.id.tV_password);
        phone = findViewById(R.id.tV_phone);
        regDate = findViewById(R.id.tV_regdate);
        imageView = findViewById(R.id.imageView2);
    }
}
