/*
 * Class Name: ProblemListAdapter
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is the adapter for the problem recycler view.
 *
 * @author Ivan
 * @author Cecilia
 * @version 1.0
 *
 */
public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {

    // private ProblemList mProblemList = ProblemList.getInstance();

    public Context ctx;
    private List<Problem> problems;
    private boolean isDoctor;
    private ArrayList<Record> recordList = new ArrayList<Record>();
    public ProblemListAdapter(List<Problem> problems, boolean isDoctor){

        this.problems = problems;
        this.isDoctor = isDoctor;
    }

    @NonNull
    @Override
    public ProblemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // CHANGE LAYOUT
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.problemlist_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ProblemListAdapter.ViewHolder holder, final int position) {
        String problemId = problems.get(position).getId();
        holder.pTitle.setText(problems.get(position).getTitle());
        holder.pDescription.setText(problems.get(position).getDescription());

        try {
            recordList = new ElasticSearchRecordController.GetRecordsTask().execute(problemId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.pCount.setText(String.valueOf(recordList.size()));
        holder.pDate.setText(problems.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Problem toView = problems.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("ProblemID", toView.getId());
                bundle.putBoolean("isDoctor",isDoctor);
                bundle.putInt("Position", position);

                Intent intent = new Intent(v.getContext(), ViewRecordList.class);
                intent.putExtras(bundle);

                ((Activity) v.getContext()).startActivityForResult(intent, 10);
                Toast.makeText(v.getContext(), "View " + toView.getTitle(), Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pTitle;
        public TextView pDescription;
        public TextView pCount;
        public TextView pDate;
        public LinearLayout OneProblem;
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
}