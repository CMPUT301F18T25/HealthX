/*
 * Class Name: ProblemRecordAdapter
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProblemRecordAdapter extends RecyclerView.Adapter<ProblemRecordAdapter.ViewHolder> {

    private List<Object> results;
    public Context ctx;
    private final int PROBLEM = 0;
    private final int RECORD = 1;
    private ViewHolder vh;
    ProblemList mProblemlist = ProblemList.getInstance();
    public ProblemRecordAdapter(List<Object> results) {
        this.results = results;
    }

    @Override
    public int getItemCount() {
        return this.results.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(results.get(position) instanceof Problem) {
            return PROBLEM;
        } else if(results.get(position) instanceof Record) {
            return RECORD;
        } else {
          return -1;
        }
    }

    @Override
    public ProblemRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case PROBLEM:
                View v1 = inflater.inflate(R.layout.problemlist_cardview, viewGroup, false);
                vh = new ViewHolder(v1);
                LinearLayout oneProblem = v1.findViewById(R.id.OneProblem);
                String color_string = "#c3b1e2";
                int myColor = Color.parseColor(color_string);
                oneProblem.setBackgroundColor(myColor);
                break;
            case RECORD:
                View v2 = inflater.inflate(R.layout.recordlist_cardview, viewGroup, false);
                vh = new ViewHolder(v2);
                break;
            default:
                break;
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(ProblemRecordAdapter.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case PROBLEM:
                Problem problem = (Problem) results.get(position);
                holder.pTitle.setText(problem.getTitle());
                holder.pDescription.setText(problem.getDescription());
                //holder.pCount.setText(problems.get(position).getCount());
                holder.pDate.setText(problem.getDate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Problem toView = (Problem) results.get(position);
                        Bundle bundle = new Bundle();

                        String problemid = toView.getId();
                        int problemListPosition = mProblemlist.getPositionByProblemId(problemid);

                        bundle.putString("ProblemID", problemid);
                        bundle.putInt("Position", problemListPosition);


                        Intent intent = new Intent(v.getContext(), ViewRecordList.class);
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                        Toast.makeText(v.getContext(), "View " + toView.getTitle(), Toast.LENGTH_SHORT).show();

                    }

                });
                break;

            case RECORD:


                Record record = (Record) results.get(position);
                holder.rTitle.setText(record.getTitle());
                holder.rComment.setText(record.getComment());
                holder.rTimestamp.setText(record.getDate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Record toView = (Record) results.get(position);


                        Intent intent = new Intent(v.getContext(), ViewCurrentRecord.class);

                        intent.putExtra("Record",toView );
                        v.getContext().startActivity(intent);
                        Toast.makeText(v.getContext(), "View " + toView.getTitle(), Toast.LENGTH_SHORT).show();

                    }

                });
                break;
            default:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView rTitle;
        public TextView rComment;
        public TextView rTimestamp;

        public TextView pTitle;
        public TextView pDescription;
        public TextView pCount;
        public TextView pDate;
        public Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.context = ctx;

            rTitle = itemView.findViewById(R.id.recordTitle);
            rComment = itemView.findViewById(R.id.recordComment);
            rTimestamp = itemView.findViewById(R.id.recordTimestamp);

            pTitle = itemView.findViewById(R.id.problemTitle);
            pDescription = itemView.findViewById(R.id.problemDescription);
            pCount = itemView.findViewById(R.id.problemCount);
            pDate = itemView.findViewById(R.id.problemDate);
            itemView.setClickable(true);
        }
    }
}