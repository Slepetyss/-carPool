package com.example.carpool.activiities.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;
import com.example.carpool.dictionaries.VehiclesClasses.Vehicle;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Vehicle> vehicleDB;


    public MyAdapter(Context context, ArrayList<Vehicle> vehicleDB) {
        this.context = context;
        this.vehicleDB = vehicleDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myTextName.setText(vehicleDB.get(position).getOwner());
        holder.myTextAvailableSits.setText("Available Sits: " + vehicleDB.get(position).getCapacity());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecundaryActivity.class);

                intent.putExtra("vehiclesList", vehicleDB);
                intent.putExtra("vehiclePosition", position);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleDB.toArray().length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myTextName, myTextAvailableSits;
//        ImageView myImage;

        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextName = itemView.findViewById(R.id.textViewNameVehicle);
            myTextAvailableSits = itemView.findViewById(R.id.textViewStatusVehicle);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }



}
