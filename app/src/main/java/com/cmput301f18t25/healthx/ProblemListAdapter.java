package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {

    // private ProblemList mProblemList = ProblemList.getInstance();

    public Context ctx;
    private List<Problem> problems;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public ProblemListAdapter(List<Problem> problems){

        this.problems = problems;

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
        
        holder.pTitle.setText(problems.get(position).getTitle());
        holder.pDescription.setText(problems.get(position).getDescription());
        holder.pCount.setText(problems.get(position).getCount());
        holder.pDate.setText(dateFormat.format(problems.get(position).getDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Problem toView = problems.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("Title",toView.getTitle());
                bundle.putString("Description",toView.getDescription());
                bundle.putString("Count",Integer.toString(toView.getCount()));
                ///////////////////////////////
                /// Date OR STRING
                ///////////////////////////////////

                bundle.putString("Date",toView.getDate().toString());

                // CHANGE ACTIVITY CLASS
                Intent intent = new Intent(v.getContext(), ViewProblemList.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
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
