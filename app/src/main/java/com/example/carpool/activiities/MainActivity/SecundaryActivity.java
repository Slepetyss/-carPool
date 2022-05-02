package com.example.carpool.activiities.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.R;
import com.example.carpool.activiities.ActivityAuth;
import com.example.carpool.dictionaries.VehiclesClasses.Vehicle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleBicycle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleCar;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleHelicopter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecundaryActivity extends AppCompatActivity {

    private TextView name, sits;
    private String nameText, sitsText, vehicleID;
    private ArrayList<Vehicle> vehicleArrayList;
    private int position;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundary);

        name = findViewById(R.id.textViewName2);
        sits = findViewById(R.id.textViewSits2);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getData();
        setData();

    }

    private void getData() {
        if(getIntent().hasExtra("vehiclesList") & getIntent().hasExtra("vehiclePosition")) {

            vehicleArrayList = (ArrayList<Vehicle>)getIntent().getSerializableExtra("vehiclesList");
            position = (int)getIntent().getSerializableExtra("vehiclePosition");

            nameText = vehicleArrayList.get(position).getOwner();
            sitsText = vehicleArrayList.get(position).getCapacity();
            vehicleID = vehicleArrayList.get(position).getVehicleID();


        } else {
            Toast.makeText(this, "No data...", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        name.setText(nameText);
        sits.setText("Available Sits: " + sitsText);
    }

    public void bookRide(View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference docUserRef = firestore.collection("Users").document(currentUser.getUid());
        DocumentReference docVehicleRef = firestore.collection("Vehicles").document(vehicleID);

        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //What happens if array does not exist? --> add on user class
                        //Check for that
                        ArrayList<String> vehiclesID;
                        if (document.get("bookedRides") != null) {
                            vehiclesID = (ArrayList<String>) document.get("bookedRides");
                        } else {
                            vehiclesID = new ArrayList<String>();
                        }
                        vehiclesID.add(vehicleID);
                        Map<String, ArrayList<String>> data = new HashMap<>();
                        data.put("bookedRides", vehiclesID);
                        docUserRef.set(data, SetOptions.merge());

                    } else {
                        Log.d("SecundaryActivity", "No such document");
                    }
                } else {
                    Log.d("SecundaryActivity", "get failed with ", task.getException());
                }
            }
        });

        docVehicleRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //add currentUser UID to ridersUID
                        ArrayList<String> ridersUIDs;
                        if (document.get("ridersUIDs") != null) {
                            ridersUIDs = (ArrayList<String>) document.get("ridersUIDs");
                        } else {
                            ridersUIDs = new ArrayList<String>();
                        }
                        ridersUIDs.add(currentUser.getUid());
                        Map<String, ArrayList<String>> data = new HashMap<>();
                        data.put("ridersUIDs", ridersUIDs);
                        docVehicleRef.set(data, SetOptions.merge());

                        int capacity =  Integer.parseInt(document.getString("capacity")) - 1;
                        String newCapacity = Integer.toString(capacity);
                        Map<String, String> dataCapacity = new HashMap<>();
                        dataCapacity.put("capacity", newCapacity);
                        if (capacity > 0) {
                            docVehicleRef.set(dataCapacity, SetOptions.merge());
                        } else if (capacity == 0) {
                            Map<String, Boolean> dataOpen = new HashMap<>();
                            dataOpen.put("open", false);
                            docVehicleRef.set(dataOpen, SetOptions.merge());
                            docVehicleRef.set(dataCapacity, SetOptions.merge());

                        } else {
                            Map<String, Boolean> dataOpen = new HashMap<>();
                            dataOpen.put("open", false);
                            docVehicleRef.set(dataOpen, SetOptions.merge());
                        }

                        sits.setText("Available Sits: " + newCapacity);

                        if (capacity == 0) {
                            Toast.makeText(getApplicationContext(), "Vehicle booked!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }

                    } else {
                        Log.d("SecundaryActivity", "No such document");
                    }
                } else {
                    Log.d("SecundaryActivity", "get failed with ", task.getException());
                }
            }
        });

        Toast.makeText(getApplicationContext(), "Vehicle booked!", Toast.LENGTH_LONG).show();

    }


}