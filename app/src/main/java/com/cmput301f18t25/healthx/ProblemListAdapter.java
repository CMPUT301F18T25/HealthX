package com.cmput301f18t25.healthx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {
    // private ProblemList mProblemList = ProblemList.getInstance();
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
        Integer problemCount = getItem(position).getCount();
        Date problemDate = getItem(position).getDate();

        // CREATE INFLATER FOR LAYOUT
        LayoutInflater inflater = LayoutInflater.from(ctx);

        // CREATE VIEW FROM INFLATER
        convertView = inflater.inflate(rsrc, parent, false);

        TextView pTitle = convertView.findViewById(R.id.problemTitle);
        TextView pDescription = convertView.findViewById(R.id.problemDescription);
        TextView pCount = convertView.findViewById(R.id.problemCount);
        TextView pDate = convertView.findViewById(R.id.problemDate);

        pTitle.setText(problemTitle);
        pDescription.setText(problemDescription);
        String strInt = String.valueOf(problemCount);
        pCount.setText(strInt);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(problemDate);
        pDate.setText(strDate);

        return convertView;

    }


}
