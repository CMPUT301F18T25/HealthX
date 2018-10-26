package com.cmput301f18t25.healthx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    public ProblemListAdapter(List<Problem> problems){

        this.problems = problems;

    }
    @Override
    public ProblemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ProblemListAdapter.ViewHolder holder, final int position) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

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
                bundle.putString("Count",toView.getCount());
                bundle.putString("Date",toView.getDate());
                Intent intent = new Intent(v.getContext(), ViewRecordActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "View " + toView.getTitle(), Toast.LENGTH_SHORT).show();

            }

        });

    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
//
//        String problemTitle = getItem(position).getTitle();
//        String problemDescription = getItem(position).getDescription();
//        Integer problemCount = getItem(position).getCount();
//        Date problemDate = getItem(position).getDate();
//
//        // CREATE INFLATER FOR LAYOUT
//        LayoutInflater inflater = LayoutInflater.from(ctx);
//
//        // CREATE VIEW FROM INFLATER
//        convertView = inflater.inflate(rsrc, parent, false);
//
//        TextView pTitle = convertView.findViewById(R.id.problemTitle);
//        TextView pDescription = convertView.findViewById(R.id.problemDescription);
//        TextView pCount = convertView.findViewById(R.id.problemCount);
//        TextView pDate = convertView.findViewById(R.id.problemDate);
//
//        pTitle.setText(problemTitle);
//        pDescription.setText(problemDescription);
//        String strInt = String.valueOf(problemCount);
//        pCount.setText(strInt);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(problemDate);
//        pDate.setText(strDate);
//
//        return convertView;
//
//    }

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
