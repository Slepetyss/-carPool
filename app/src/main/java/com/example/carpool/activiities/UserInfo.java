package com.example.carpool.activiities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.example.carpool.dictionaries.UserClasses.UserAnother;
import com.example.carpool.dictionaries.UserClasses.UserParent;
import com.example.carpool.dictionaries.UserClasses.UserStudent;
import com.example.carpool.dictionaries.UserClasses.UserTeacher;
import com.example.carpool.dictionaries.UserClasses.UserWorker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] bankNames={"Teacher","Student","Parent","Worker","Another"};
    //Commum variables:
    private TextView nameText;
    private EditText nameInput;
    //Parent:
    private TextView numberOfChildrenText;
    private EditText numberOfChildrenInput;
    //Teacher:
    private TextView inSchoolTitleText;
    private EditText inSchoolTitleInput;
    //Alumni:
    private TextView graduateYearText;
    private EditText graduateYearInput;
    //Student:
    private TextView graduatingYearText;
    private EditText graduatingYearInput;
    //Worker:
    private TextView jobNameText;
    private EditText jobNameInput;
    //Another:
    private TextView extraInfoText;
    private EditText extraInfoInput;

    private TextView neutralText;
    private EditText neutralInput;

    private String neutralInputText;

    private String selectedName;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private ArrayList<String> arrList;
    private LinearLayout layout;

    //Store all fields paths
    HashMap map = new HashMap<String,EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        layout = findViewById(R.id.linearLayout);
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
        //What is this for? no clue
    }

    public void addFields() {
        addCommonFields();
        switch (selectedName) {
            case "Teacher": addTeacherFields();
                            break;
            case "Student": addStudentFields();
                            break;
            case "Parent": addParentFields();
                            break;
            case "Worker": addWorkerFields();
                            break;
            case "Another": addAnotherFields();
                            break;
        }
    }

    public void addCommonFields() {
        layout.removeAllViewsInLayout();
        addText(nameText, "User Name: ");
        addInput(nameInput, "Lucas Slepetys");
    }

    private void addAnotherFields() {
        addText(extraInfoInput, "Extra Information: ");
        addInput(extraInfoInput, "I can back flip!");
    }

    private void addWorkerFields() {
        addText(jobNameInput, "Job Name: ");
        addInput(jobNameInput, "Chief");
    }

    private void addParentFields() {
        addText(numberOfChildrenInput, "Amount of children in school:");
        addInput(numberOfChildrenInput, "2");
    }

    private void addStudentFields() {
        addText(graduatingYearInput, "Year of Graduation: ");
        addInput(graduatingYearInput, "2024");
    }

    private void addTeacherFields() {
        addText(inSchoolTitleInput, "In School Title (Subject): ");
        addInput(inSchoolTitleInput, "Math");
    }

    public void addText(TextView field, String text) {
        neutralText = field;
        neutralText = new TextView(this);
        neutralText.setText(text);
        neutralText.setTypeface(null, Typeface.BOLD);
        neutralText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
        neutralText.setPadding(18,10,0,0);
        layout.addView(neutralText);
    }

    public void addInput(EditText field, String hint) {
        neutralInput = field;
        neutralInput = new EditText(this);
        neutralInput.setHint(hint);
        layout.addView(neutralInput);
        map.put(hint,neutralInput);
    }

    public void saveInfo(View v) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userString = ((EditText) map.get("Lucas Slepetys")).getText().toString();
        String userUid = currentUser.getUid();
        String userEmail = currentUser.getEmail();

        switch (selectedName) {
            case "Teacher":
                UserTeacher userTeacher = new UserTeacher(userUid, userString, userEmail, selectedName, arrList, arrList, ((EditText) map.get("Math")).getText().toString());
                System.out.println(userTeacher);
                firestore.collection("Users").document(currentUser.getUid()).set(userTeacher);
                switchPage();
                break;
            case "Student":
                UserStudent userStudent = new UserStudent(userUid, userString, userEmail, selectedName, arrList, arrList, ((EditText) map.get("2024")).getText().toString());
                System.out.println(userStudent);
                firestore.collection("Users").document(currentUser.getUid()).set(userStudent);
                switchPage();
                break;
            case "Parent":
                UserParent userParent = new UserParent(userUid, userString, userEmail, selectedName, arrList, arrList, ((EditText) map.get("2")).getText().toString());
                System.out.println(userParent);
                firestore.collection("Users").document(currentUser.getUid()).set(userParent);
                switchPage();
                break;
            case "Worker":
                UserWorker userWorker = new UserWorker(userUid, userString, userEmail, selectedName, arrList, arrList, ((EditText) map.get("Chief")).getText().toString());
                System.out.println(userWorker);
                firestore.collection("Users").document(currentUser.getUid()).set(userWorker);
                switchPage();
                break;
            case "Another":
                UserAnother userAnother = new UserAnother(userUid, userString, userEmail, selectedName, arrList, arrList, ((EditText) map.get("I can back flip!")).getText().toString());
                System.out.println(userAnother);
                firestore.collection("Users").document(currentUser.getUid()).set(userAnother);
                switchPage();
                break;
        }

    }

    private void switchPage() {
        Toast.makeText(getApplicationContext(), "User Info has been saved!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();

        Toast.makeText(getApplicationContext(), "Signing Out...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityAuth.class);
        startActivity(intent);

    }
}