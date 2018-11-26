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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    // private PatientList mPatientList = PatientList.getInstance();
    //<div>Icons made by <a href="https://www.flaticon.com/authors/popcorns-arts" title="Icon Pond">Icon Pond</a> from <a href="https://www.flaticon.com/" 		    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
    public Context ctx;
    private List<User> users;
    private Intent Pintent;

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
        holder.pUserId.setText(users.get(position).getId());
        holder.pUserEmail.setText(users.get(position).getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User toView = users.get(position);
                Bundle bundle = Pintent.getExtras();
                bundle.putString("PatientEmail",toView.getEmail());
                bundle.putString("PatientId",toView.getUsername());

                // CHANGE ACTIVITY CLASS
                Intent intent = new Intent(v.getContext(), ActivityViewPatientProblem.class);
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
        public TextView pUserEmail;
        public Context context;
        public Intent intent2;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.context = ctx;
            this.intent2 = Pintent;
            pName = itemView.findViewById(R.id.patientName);
            pUserId = itemView.findViewById(R.id.patientID);
            pUserEmail = itemView.findViewById(R.id.patientEmail);
            itemView.setClickable(true);

        }
    }

}
