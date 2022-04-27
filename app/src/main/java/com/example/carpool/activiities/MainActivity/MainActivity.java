package com.example.carpool.activiities.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carpool.R;
import com.example.carpool.activiities.ActivityAuth;
import com.example.carpool.activiities.AddVehicle;
import com.example.carpool.dictionaries.VehiclesClasses.Vehicle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleBicycle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleCar;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleHelicopter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private EditText introField;
    private String userName;

    private ArrayList<Vehicle> vehiclesDB;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        introField = findViewById(R.id.introTextField);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.recyclerView);

        vehiclesDB = new ArrayList<Vehicle>();

        DocumentReference docRef = firestore.collection("Users").document(currentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DATA", "DocumentSnapshot data: " + document.getData().get("name"));
                        userName = document.getData().get("name").toString();
                        System.out.println(userName);
                        String show = "Hey " + userName + "! Here are your available rides:";
                        introField.setText(show);
                    } else {
                        Log.d("NO DATA", "No such document");
                    }
                } else {
                    Log.d("FAIL", "get failed with ", task.getException());
                }
            }
        });
        retrieveDB();
    }

    /* ToDo:
        1) Retrieve all vehicles from firebase:
            1.1) Check vehicle type and assign it to each individual class (Done)
        2) Add all vehicles classes to an arrayList (Done)
        3) Learn best method for using recyclerView
        4) Create UI for recyclerView
        5) Loop though vehicles arrayList and display them in recyclerView
        6) Make each view clickable
        7) Move to a different page after a view is clicked and display all info of vehicle there
            7.1) Loop though arrayList again to display info
        8) Book a ride (somehow no clue how)
     */

    public void retrieveDB() {
        vehiclesDB.clear();

        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();
        firestore.collection("Vehicles").whereEqualTo("open", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() & task.getResult() != null) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if(document.get("vehicleType").equals("HeliCopter")) {

                            vehiclesDB.add(document.toObject(VehicleHelicopter.class));

                        } else if(document.get("vehicleType").equals("Bycicle")) {

                            vehiclesDB.add(document.toObject(VehicleBicycle.class));

                        } else if (document.get("vehicleType").equals("Car")) {

                            vehiclesDB.add(document.toObject(VehicleCar.class));

                        } else {

                            vehiclesDB.add(document.toObject(Vehicle.class));

                        }
                    }
                    System.out.println(vehiclesDB);
                    getAllRidesTask.setResult(null);
                } else {
                    Log.d("MainActivity", "Failed to retrieve info from DB: ", task.getException());
            }
            }
        });
    }

    public void addNewRide(View v) {
        Toast.makeText(getApplicationContext(), "Add new ride page...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AddVehicle.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();

        Toast.makeText(getApplicationContext(), "Signing Out...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityAuth.class);
        startActivity(intent);
    }

}