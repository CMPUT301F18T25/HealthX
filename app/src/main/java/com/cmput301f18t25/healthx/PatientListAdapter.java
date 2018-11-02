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

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    // private PatientList mPatientList = PatientList.getInstance();

    public Context ctx;
    private List<User> users;

    // NEED TO DISTINGUISH EACH USER WHETHER HE IS A PATIENT
    public PatientListAdapter(List<User> users){

        this.users = users;

    }
    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // CHANGE LAYOUT
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientlist_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(PatientListAdapter.ViewHolder holder, final int position) {

        holder.pName.setText(users.get(position).getName());
        holder.pUserId.setText(users.get(position).getUserId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User toView = users.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("Name",toView.getName());
                bundle.putString("UserId",toView.getUserId());

                // CHANGE ACTIVITY CLASS
                Intent intent = new Intent(v.getContext(), ViewPatient.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "View " + toView.getName(), Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pName;
        public TextView pUserId;
        public Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.context = ctx;

            pName = itemView.findViewById(R.id.patientName);
            pUserId = itemView.findViewById(R.id.patientUserId);
            itemView.setClickable(true);

        }
    }

}
