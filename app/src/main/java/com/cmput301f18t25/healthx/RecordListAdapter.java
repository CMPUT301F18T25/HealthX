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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {

    // private ProblemList mProblemList = ProblemList.getInstance();

    public Context ctx;
    private List<Record> records;

    public RecordListAdapter(List<Record> records){

        this.records = records;

    }
    @Override
    public RecordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // CHANGE LAYOUT
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ProblemListAdapter.ViewHolder holder, final int position) {

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final String timestamp = dateFormat.format(date);
        holder.rTitle.setText(problems.get(position).getTitle());
        holder.rComment.setText(problems.get(position).getDescription());
        holder.rTimestamp.setText(dateFormat.format(timestamp));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Record toView = records.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("Title",toView.getTitle());
                bundle.putString("Comment",toView.getComment());
                bundle.putString("Date", timestamp);

                // CHANGE ACTIVITY CLASS
                Intent intent = new Intent(v.getContext(), ViewRecordActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "View " + toView.getTitle(), Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rTitle;
        public TextView rComment;
        public TextView rTimestamp;
        public Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.context = ctx;

            rTitle = itemView.findViewById(R.id.recordTitle);
            rComment = itemView.findViewById(R.id.recordComment);
            rTimestamp = itemView.findViewById(R.id.recordTimestamp);
            itemView.setClickable(true);

        }
    }
}
