package com.example.shahajalal.firebasereadwriteoperationtodatabase;

import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ValueEventListener {

    private TextView heading;
    private EditText headinginput;
    private Button submit;
    private RadioButton rbRed,rbBlue;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference mrootReference=firebaseDatabase.getReference();
    private DatabaseReference mheadingReference=mrootReference.child("heading");
    private DatabaseReference mcolorReference=mrootReference.child("color");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heading=findViewById(R.id.headingtextid);
        headinginput=findViewById(R.id.headinginputid);
        submit=findViewById(R.id.submitbtnid);
        rbRed = findViewById(R.id.rbRed);
        rbBlue=findViewById(R.id.rbBlue);
    }

    public void submitHeading(View view) {

        String heading=headinginput.getText().toString();
        mheadingReference.setValue(heading);
    }

    public void onRadioButtonClick(View view) {

        switch (view.getId()){
            case R.id.rbRed:
                mcolorReference.setValue("red");
                break;
            case R.id.rbBlue:
                mcolorReference.setValue("blue");
                break;

        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if(dataSnapshot.getValue(String.class)!=null){
            String key=dataSnapshot.getKey();
            if(key.equals("heading")){
                String heading1=dataSnapshot.getValue(String.class);
                heading.setText(heading1);
            }
            else if(key.equals("color")){
                String color=dataSnapshot.getValue(String.class);
                if(color.equals("red")){
                    heading.setTextColor(ContextCompat.getColor(this,R.color.colorRed));
                    rbRed.setChecked(true);
                }else if(color.equals("blue")){
                    heading.setTextColor(ContextCompat.getColor(this,R.color.colorBlue));
                    rbBlue.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mheadingReference.addValueEventListener(this);
        mcolorReference.addValueEventListener(this);
    }
}
