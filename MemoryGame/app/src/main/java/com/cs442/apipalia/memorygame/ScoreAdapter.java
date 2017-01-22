package com.cs442.apipalia.memorygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<Item> {

    int listItemLayout;

    public ScoreAdapter(Context context, int listItemLayout, List<Item> items) {
        super(context, listItemLayout, items);
        this.listItemLayout = listItemLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);
        final String userName = item.getName();
        final String userScore = item.getScore();
        LinearLayout linearLayout;
        if (convertView == null) {
            linearLayout = new LinearLayout(getContext());
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            li.inflate(listItemLayout, linearLayout, true);
        } else {
            linearLayout = (LinearLayout) convertView;
        }
        TextView userNameView = (TextView) linearLayout.findViewById(R.id.txt_topUsername);
        TextView userScoreView = (TextView) linearLayout.findViewById(R.id.txt_topUserScore);
        userNameView.setText(userName);
        userScoreView.setText(userScore);
        return linearLayout;
    }

}
