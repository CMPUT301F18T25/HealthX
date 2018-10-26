package com.cmput301f18t25.healthx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {

    // private ProblemList mProblemList = ProblemList.getInstance();

    public Context ctx;
    private List<Problem> problems;

    public ProblemListAdapter(List<Problem> problems){

        this.problems = problems;

    }
    @Override
    public ProblemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

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
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pTitle;
        public TextView pDescription;
        public TextView pCount;
        public TextView pDate;
        public Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.context = ctx;

            pTitle = itemView.findViewById(R.id.problemTitle);
            pDescription = itemView.findViewById(R.id.problemDescription);
            pCount = itemView.findViewById(R.id.problemCount);
            pDate = itemView.findViewById(R.id.problemDate);
            itemView.setClickable(true);
            
        }

}
