package com.cmput301f18t25.healthx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class ProblemListAdapter extends ArrayAdapter<Problem> {
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
        // Integer problemCount = getItem(position).getCount();
        Date problemDate = getItem(position).getDate();

        // CREATE INFLATOR FOR LAYOUT
        LayoutInflater inflater = LayoutInflater.from(ctx);

        // CREATE VIEW FROM INFLATER
        convertView = inflater.inflate(rsrc, parent, false);

        TextView pTitle = convertView.findViewById(R.id.problemTitle);
        TextView pDescription = convertView.findViewById(R.id.problemDescription);
        // TextView pCount = convertView.findViewById(R.id.problemCount);
        TextView pDate = convertView.findViewById(R.id.problemDate);

        pTitle.setText(problemTitle);
        pDescription.setText(problemDescription);
        // pCount.setText(Integer.toString(problemCount));
        // DATE NEEDS TO BE STRING
        // pDate.setText(problemDate);

        return convertView;


    }


}
