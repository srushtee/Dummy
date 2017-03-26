package com.example.srushtee.dummy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by srushtee on 26-03-2017.
 */

public class UserList extends ArrayAdapter<list_item> {

    private Activity context;
    private List<list_item> userList;

    public UserList(Activity context, List<list_item> userList) {
        super(context, R.layout.information, userList);
        this.context = context;
        this.userList = userList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.information, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);
        TextView textViewEmailId = (TextView) listViewItem.findViewById(R.id.textViewEmailId);
        list_item userData = userList.get(position);
        textViewName.setText(userData.getStatus());
        textViewAddress.setText(userData.getTitle());
        textViewEmailId.setText(userData.getTitle());

        return listViewItem;

    }
}



