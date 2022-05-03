package com.example.carpool.activiities.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.R;
import com.example.carpool.dictionaries.VehiclesClasses.Vehicle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleBicycle;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleCar;
import com.example.carpool.dictionaries.VehiclesClasses.VehicleHelicopter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecundaryActivity extends AppCompatActivity {

    private TextView name, sits, price, extra;
    private String nameText, sitsText, vehicleID, priceText, extraText;
    private ImageView myImage;
    private ArrayList<Vehicle> vehicleArrayList;
    private int position;
    private int images[];

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundary);

        name = findViewById(R.id.textViewName);
        sits = findViewById(R.id.textViewSits2);
        price = findViewById(R.id.textViewPrice);
        extra = findViewById(R.id.textViewExtra);
        myImage = findViewById(R.id.imageView);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getData();
        setData();

    }

    private void getData() {
        if(getIntent().hasExtra("vehiclesList") & getIntent().hasExtra("vehiclePosition") & getIntent().hasExtra("images")) {

            vehicleArrayList = (ArrayList<Vehicle>)getIntent().getSerializableExtra("vehiclesList");
            position = (int)getIntent().getSerializableExtra("vehiclePosition");
            images = (int[])getIntent().getSerializableExtra("images");


            nameText = vehicleArrayList.get(position).getOwner();
            sitsText = vehicleArrayList.get(position).getCapacity();
            priceText = vehicleArrayList.get(position).getBasePrice();
            vehicleID = vehicleArrayList.get(position).getVehicleID();

            if (vehicleArrayList.get(position).getVehicleType().equals("HeliCopter")) {
                myImage.setImageResource(images[2]);
                VehicleHelicopter vehicleHelicopter = (VehicleHelicopter) vehicleArrayList.get(position);
                extraText = "Max Air Speed: " + vehicleHelicopter.getMaxAirSpeed() + "Km/h";
            } else if (vehicleArrayList.get(position).getVehicleType().equals("Bycicle")) {
                myImage.setImageResource(images[0]);
                VehicleBicycle vehicleBicycle = (VehicleBicycle) vehicleArrayList.get(position);
                extraText = "Max Weight: " + vehicleBicycle.getWeightCapacity() + "Kg";
            } else if (vehicleArrayList.get(position).getVehicleType().equals("Car")) {
                myImage.setImageResource(images[1]);
                VehicleCar vehicleCar = (VehicleCar) vehicleArrayList.get(position);
                extraText = "Driving Range: " + vehicleCar.getRangeKm() + "Km";
            }


        } else {
            Toast.makeText(this, "No data...", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        name.setText("Owner: " + nameText);
        System.out.println(nameText);
        sits.setText("Available Sits: " + sitsText);
        extra.setText(extraText);
        price.setText("$" + priceText);

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