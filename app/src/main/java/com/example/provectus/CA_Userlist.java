package com.example.provectus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CA_Userlist extends ArrayAdapter<UserData>{

    private static class ViewHolder{
        TextView txtCity, txtName, txtEmail;
        ImageView image;
    }

    CA_Userlist(ArrayList<UserData> data, Context context) {
        super(context, R.layout.list_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserData userData = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtCity = convertView.findViewById(R.id.tV_city);
            viewHolder.txtName = convertView.findViewById(R.id.tV_name);
            viewHolder.txtEmail = convertView.findViewById(R.id.tV_email);
            viewHolder.image = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtCity.setText("City: "+userData.getCity());
        viewHolder.txtName.setText(userData.getName());
        viewHolder.txtEmail.setText(userData.getEmail());
        viewHolder.image.setImageBitmap(userData.getPicture());
        return convertView;
    }
}
