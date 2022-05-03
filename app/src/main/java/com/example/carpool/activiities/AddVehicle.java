package com.example.carpool.activiities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.R;
import com.example.carpool.activiities.MainActivity.MainActivity;
import com.example.carpool.activiities.MainActivity.MyAdapter;
import com.example.carpool.activiities.MainActivity.OwnedVehicles.OwnedVehicles;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AddVehicle extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String[] bankNames={"HeliCopter","Car","Bycicle"};
    //Commum variables:
    private TextView ownerText;
    private EditText ownerInput;
    //-+-
    private TextView carModelText;
    private EditText carModelInput;
    //-+-
    private TextView capacityText;
    private EditText capacityInput;
    //-+-
    private TextView basePriceText;
    private EditText basePriceInput;

    //HeliCopter
    private TextView maxAltitudeText;
    private EditText maxAltitudeInput;
    //-+-
    private TextView maxAirSpeedText;
    private EditText maxAirSpeedInput;

    //Car
    private TextView rangeKmText;
    private EditText rangeKmInput;

    //Bycicle
    private TextView bicycleTypeText;
    private EditText bicycleTypeInput;
    //-+-
    private TextView weightCapacityText;
    private EditText weightCapacityInput;

    //Neutral fields:
    private TextView neutralText;
    private EditText neutralInput;

    private String neutralInputText;

    private String selectedName;
    private LinearLayout layout;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private String userName;

    private ArrayList<Vehicle> vehiclesDB;

    //Store all fields paths
    HashMap map = new HashMap<String,EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner2);
        layout = findViewById(R.id.linearLayout2);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
        selectedName = bankNames[position];
        addFields();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addFields() {
        addCommonFields();
        switch (selectedName) {
            case "HeliCopter":
                addHeliCopterFields();
                break;
            case "Car":
                addCarFields();
                break;
            case "Bycicle":
                addBycicleFields();
                break;
        }
    }

    public void addCommonFields() {
        layout.removeAllViewsInLayout();
        //Add each of the common fields:
        addText(ownerText, "Owner's name:");
        addInput(ownerInput, "Lucas Slepetys");
        //--
        addText(carModelText, "Model:");
        addInput(carModelInput, "Ferrari");
        //--
        addText(capacityText, "Vehicle Capacity:");
        addInput(capacityInput, "4");
        //--
        addText(basePriceText, "Base Price ($):");
        addInput(basePriceInput, "12345");
    }

    private void addHeliCopterFields() {

        addText(maxAltitudeText, "Max Altitude (M):");
        addInput(maxAltitudeInput, "5000");
        //--
        addText(maxAirSpeedText, "Max Speed (Km/h):");
        addInput(maxAirSpeedInput, "350");
    }

    private void addCarFields() {
        addText(rangeKmText, "Car Range (Km):");
        addInput(rangeKmInput, "1000");
    }

    private void addBycicleFields() {

        addText(bicycleTypeText, "Bicycle Type:");
        addInput(bicycleTypeInput, "Touring Bike");
        //--
        addText(weightCapacityText, "Weight Max Capacity (Kg):");
        addInput(weightCapacityInput, "150");
    }

    public void addText(TextView field, String text) {
        neutralText = field;
        neutralText = new TextView(this);
        neutralText.setText(text);
        neutralText.setTypeface(null, Typeface.BOLD);
        neutralText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        neutralText.setPadding(14,5,0,0);
        layout.addView(neutralText);
    }

    public void addInput(EditText field, String hint) {
        neutralInput = field;
        neutralInput = new EditText(this);
        neutralInput.setHint(hint);
        layout.addView(neutralInput);
        map.put(hint,neutralInput);
    }

    public void addVehicle(View v) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference vehicleRef = firestore.collection("Vehicles").document();
        String vehicleId = vehicleRef.getId();


        switch (selectedName) {
            case "HeliCopter":
                VehicleHelicopter vehicleHelicopter = new VehicleHelicopter(((EditText) map.get("Lucas Slepetys")).getText().toString(), ((EditText) map.get("Ferrari")).getText().toString(), ((EditText) map.get("4")).getText().toString(), selectedName, ((EditText) map.get("12345")).getText().toString(),vehicleId ,currentUser.getUid() ,((EditText) map.get("5000")).getText().toString(), ((EditText) map.get("350")).getText().toString());
                System.out.println(vehicleHelicopter);
                vehicleRef.set(vehicleHelicopter);
                switchPage();
                break;
            case "Car":
                VehicleCar vehicleCar = new VehicleCar(((EditText) map.get("Lucas Slepetys")).getText().toString(), ((EditText) map.get("Ferrari")).getText().toString(), ((EditText) map.get("4")).getText().toString(), selectedName, ((EditText) map.get("12345")).getText().toString(),vehicleId ,currentUser.getUid(), ((EditText) map.get("1000")).getText().toString());
                System.out.println(vehicleCar);
                vehicleRef.set(vehicleCar);
                switchPage();
                break;
            case "Bycicle":
                VehicleBicycle vehicleBicycle = new VehicleBicycle(((EditText) map.get("Lucas Slepetys")).getText().toString(), ((EditText) map.get("Ferrari")).getText().toString(), ((EditText) map.get("4")).getText().toString(), selectedName, ((EditText) map.get("12345")).getText().toString(),vehicleId ,currentUser.getUid(),((EditText) map.get("Touring Bike")).getText().toString(), ((EditText) map.get("150")).getText().toString());
                System.out.println(vehicleBicycle);
                vehicleRef.set(vehicleBicycle);
                switchPage();
                break;
        }
    }


    public void switchPage() {
        Toast.makeText(getApplicationContext(), "Vehicle added!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}