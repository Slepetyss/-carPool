package com.example.carpool.activiities.MainActivity.OwnedVehicles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.carpool.R;
import com.example.carpool.activiities.MainActivity.MyAdapter;
import com.example.carpool.dictionaries.VehiclesClasses.Vehicle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleBicycle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleCar;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleHelicopter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OwnedVehicles extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Vehicle> vehicleDB;
    private Context ct;
    private TextView emptyView;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owned_vehicles);

        recyclerView = findViewById(R.id.recyclerView2);
        ct = this;

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        vehicleDB = new ArrayList<Vehicle>();
        emptyView = findViewById(R.id.empty_view);


        retrieveDB();

    }

    private void retrieveDB() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        vehicleDB.clear();
        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();
        firestore.collection("Vehicles").whereNotEqualTo("capacity", "0").whereEqualTo("ownerUID", currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() & task.getResult() != null) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if(document.get("vehicleType").equals("HeliCopter")) {

                            vehicleDB.add(document.toObject(VehicleHelicopter.class));

                        } else if(document.get("vehicleType").equals("Bycicle")) {

                            vehicleDB.add(document.toObject(VehicleBicycle.class));

                        } else if (document.get("vehicleType").equals("Car")) {

                            vehicleDB.add(document.toObject(VehicleCar.class));

                        } else {

                            vehicleDB.add(document.toObject(Vehicle.class));

                        }
                    }

                    System.out.println(vehicleDB);
                    System.out.println(vehicleDB.isEmpty());
                    if (vehicleDB.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    else {
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                    MySecondAdapter mySecondAdapter = new MySecondAdapter(ct, vehicleDB);
                    recyclerView.setAdapter(mySecondAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ct));


                    getAllRidesTask.setResult(null);
                } else {
                    Log.d("MainActivity", "Failed to retrieve info from DB: ", task.getException());
                }
            }
        });
    }
}