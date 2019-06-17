package com.example.provectus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<UserData> userData;
    private static CA_Userlist adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeXML();
        userData = new ArrayList<>();
        adapter = new CA_Userlist(userData, getApplicationContext());
        listView.setAdapter(adapter);
        testApi();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                testApi();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData data = userData.get(position);
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    private void initializeXML() {
        listView = findViewById(R.id.listView_users);
        swipeRefreshLayout = findViewById(R.id.swipe);
    }

    private void testApi() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL randomUserApi = new URL("https://randomuser.me/api/?results=20");
                    HttpsURLConnection connection = (HttpsURLConnection) randomUserApi.openConnection();
                    if (connection.getResponseCode() == 200){
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ( (line=bufferedReader.readLine()) != null ){
                            stringBuilder.append(line);
                        }
                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        userData.clear();
                        for (int i =0; i<jsonArray.length(); i++){
                            JSONObject user = jsonArray.getJSONObject(i);
                            makeFullData(user);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                        bufferedReader.close();
                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        Toast.makeText(getApplicationContext(), "Not successfull request", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeFullData(JSONObject user) throws JSONException, IOException {
        String name, email, street, city, postcode, username, password, dob, age, registered, phone;
        Bitmap medPicture, largePicture;

        JSONObject nameObject = user.getJSONObject("name");
        String titleName = nameObject.getString("title");
        String firstName = nameObject.getString("first");
        String lastName = nameObject.getString("last");
        name = titleName.substring(0,1).toUpperCase()+titleName.substring(1)+". "+
                firstName.substring(0,1).toUpperCase()+firstName.substring(1)+" "+
                lastName.substring(0,1).toUpperCase()+lastName.substring(1);

        email = user.getString("email");

        JSONObject locationObject = user.getJSONObject("location");
        street = locationObject.getString("street");
        String tmpCity = locationObject.getString("city");
        city = tmpCity.substring(0,1).toUpperCase()+tmpCity.substring(1);
        postcode = locationObject.getString("postcode");

        JSONObject loginObject = user.getJSONObject("login");
        username = loginObject.getString("username");
        password = loginObject.getString("password");

        JSONObject dobObject = user.getJSONObject("dob");
        dob = dobObject.getString("date").split("T")[0];
        age = dobObject.getString("age");

        JSONObject registeredObject = user.getJSONObject("registered");
        registered = registeredObject.getString("date").split("T")[0];

        phone = user.getString("phone");

        JSONObject pictureObject = user.getJSONObject("picture");
        URL medPictureUrl = new URL(pictureObject.getString("medium"));
        medPicture = BitmapFactory.decodeStream(medPictureUrl.openConnection().getInputStream());
        URL largePictureUrl = new URL(pictureObject.getString("large"));
        largePicture = BitmapFactory.decodeStream(largePictureUrl.openConnection().getInputStream());

        userData.add(new UserData(name, email, street, city, postcode, username, password, dob, age, registered, phone, medPicture, largePicture));
    }

}
