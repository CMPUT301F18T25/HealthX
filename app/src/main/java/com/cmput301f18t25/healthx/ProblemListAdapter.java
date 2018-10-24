package com.cmput301f18t25.healthx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ProblemListAdapter extends ArrayAdapter<Problem> {

    private Context ctx;
    int rsrc;

    ProblemListAdapter(Context context, int resource, ArrayList<Problem> objects) {
        super(context, resource, objects);
        ctx = context;
        rsrc = resource;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        String problemTitle = getItem(position).getTitle();
        String problemDescription = getItem(position).getDescription();
        Date problemDate = getItem(position).getDate();

        LayoutInflater inflater = LayoutInflater.from(ctx);

        convertView = inflater.inflate(rsrc, parent, false);



    }


}
