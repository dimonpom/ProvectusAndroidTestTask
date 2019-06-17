package com.example.provectus;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class UserData implements Parcelable {
    private String name, email, street, city, postcode, username, password, dob, age, registered, phone;
    private Bitmap picture, largePicture;

    UserData(String name, String email, String street, String city, String postcode, String username, String password, String dob, String age, String registered, String phone, Bitmap picture, Bitmap largePicture) {
        this.name = name;
        this.email = email;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.age = age;
        this.registered = registered;
        this.phone = phone;
        this.picture = picture;
        this.largePicture = largePicture;
    }

    private UserData(Parcel in) {
        name = in.readString();
        email = in.readString();
        street = in.readString();
        city = in.readString();
        postcode = in.readString();
        username = in.readString();
        password = in.readString();
        dob = in.readString();
        age = in.readString();
        registered = in.readString();
        phone = in.readString();
        picture = in.readParcelable(Bitmap.class.getClassLoader());
        largePicture = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    String getAge() {
        return age;
    }

    Bitmap getLargePicture() {
        return largePicture;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    String getStreet() {
        return street;
    }

    String getCity() {
        return city;
    }

    String getPostcode() {
        return postcode;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getDob() {
        return dob;
    }

    String getRegistered() {
        return registered;
    }

    String getPhone() {
        return phone;
    }

    Bitmap getPicture() {
        return picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(postcode);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(dob);
        dest.writeString(age);
        dest.writeString(registered);
        dest.writeString(phone);
        dest.writeParcelable(picture, flags);
        dest.writeParcelable(largePicture, flags);
    }
}

