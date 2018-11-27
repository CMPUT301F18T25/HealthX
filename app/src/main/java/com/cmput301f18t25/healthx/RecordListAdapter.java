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

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {

    // private ProblemList mProblemList = ProblemList.getInstance();

    public Context ctx;
    private List<Record> records;

    public RecordListAdapter(List<Record> records){

        this.records = records;

    }
    @Override
    public RecordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // CHANGE LAYOUT
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recordlist_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(RecordListAdapter.ViewHolder holder, final int position) {

        holder.rTitle.setText(records.get(position).getTitle());
        holder.rComment.setText(records.get(position).getComment());
        holder.rTimestamp.setText(records.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Record toView = records.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putString("Title",toView.getTitle());
//                bundle.putString("Comment",toView.getComment());
//                bundle.putString("Date", toView.getDate());

                // CHANGE ACTIVITY CLASS
                Intent intent = new Intent(v.getContext(), ViewCurrentRecord.class);
//                intent.putExtras(bundle);
                intent.putExtra("Record",toView );
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
