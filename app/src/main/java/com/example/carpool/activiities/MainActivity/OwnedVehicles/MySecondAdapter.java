package com.example.carpool.activiities.MainActivity.OwnedVehicles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;
import com.example.carpool.activiities.MainActivity.MyAdapter;
import com.example.carpool.dictionaries.VehiclesClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MySecondAdapter extends RecyclerView.Adapter<MySecondAdapter.MySecondViewHolder> {

    private ArrayList<Vehicle> vehicleDB;
    private Context context;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    public MySecondAdapter(Context ct, ArrayList<Vehicle> vehicleDB) {
        this.context = ct;
        this.vehicleDB = vehicleDB;
    };

    @NonNull
    @Override
    public MySecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_second_row, parent, false);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        return new MySecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySecondViewHolder holder, int position) {
        holder.vehicleName.setText(vehicleDB.get(position).getModel());
        holder.vehicleStatus.setText("Status: " + ((vehicleDB.get(position).isOpen()) ? "OPEN" : "CLOSE"));

        holder.secondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference docVehicleRef = firestore.collection("Vehicles").document(vehicleDB.get(position).getVehicleID());

                if (vehicleDB.get(position).isOpen()) {
                    docVehicleRef.update("open", false);
                    vehicleDB.get(position).setOpen(false);
                    Toast.makeText(context, "Updated! Vehicle is now close", Toast.LENGTH_LONG).show();
                } else {
                    docVehicleRef.update("open", true);
                    vehicleDB.get(position).setOpen(true);
                    Toast.makeText(context, "Updated! Vehicle is now open", Toast.LENGTH_LONG).show();
                }
                holder.vehicleStatus.setText("Status: " + ((vehicleDB.get(position).isOpen()) ? "OPEN" : "CLOSE"));



            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleDB.toArray().length;
    }

    public class MySecondViewHolder extends RecyclerView.ViewHolder {

        TextView vehicleName, vehicleStatus;
        ConstraintLayout secondLayout;

        public MySecondViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicleName = itemView.findViewById(R.id.textViewNameVehicle);
            vehicleStatus = itemView.findViewById(R.id.textViewStatusVehicle);
            secondLayout = itemView.findViewById(R.id.secondLayout);

        }

    }
}
