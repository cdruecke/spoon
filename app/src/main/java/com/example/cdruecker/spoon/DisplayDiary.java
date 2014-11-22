package com.example.cdruecker.spoon;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cdruecker on 7/30/14.
 */
public class DisplayDiary extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public DisplayDiary(Context context, String[] values) {
        super(context, R.layout.diary_entry, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.diary_entry, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageButton imageView = (ImageButton) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);
        // TODO: Incorporate database and headers
        if (s.equals(context.getString(R.string.breakfast))) {
            imageView.setVisibility(View.INVISIBLE);
            System.out.println(R.string.breakfast);
        } else if (s.equals(context.getString(R.string.lunch))) {
            imageView.setVisibility(View.INVISIBLE);
        } else if (s.equals(context.getString(R.string.dinner))) {
            imageView.setVisibility(View.INVISIBLE);
        } else if (s.equals(context.getString(R.string.snacks))) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setImageResource(R.drawable.ic_action_edit);
        }

        return rowView;
    }
}