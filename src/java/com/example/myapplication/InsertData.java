package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InsertData extends AppCompatActivity {
    Button btnInsert, btnView, btnAdd;
    EditText name, status, items, NewValue;
    DatabaseReference databaseUsers;
    ArrayList<String> additionalValues;
    EditText usernameField;
    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        btnInsert = findViewById(R.id.btninsert);
        btnView = findViewById(R.id.btnview);
        btnAdd = findViewById(R.id.btnAdd);
        usernameField = findViewById(R.id.edtname);
        NewValue = findViewById(R.id.edtNewValue);
        name = findViewById(R.id.edtname);
        status = findViewById(R.id.edtstatus);
        items = findViewById(R.id.edtitems);
        databaseUsers = FirebaseDatabase.getInstance().getReference();
        additionalValues = new ArrayList<>();

        databaseUsers.child("users").orderByKey().limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User lastUser = snapshot.getValue(User.class);
                            if (lastUser != null) {
                                String lastUsername = lastUser.getName();
                                usernameField.setText(lastUsername);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsertData.this, Userlist.class));
                finish();
            }
        });
    }

    private void addData() {
        String newValue = NewValue.getText().toString();
        if (!newValue.isEmpty()) {
            additionalValues.add(counter + ". " + newValue);
            counter++;
            updateDisplayTextView();
            NewValue.setText("");
        }
    }

    private void updateDisplayTextView() {
        StringBuilder displayText = new StringBuilder();
        for (String value : additionalValues) {
            displayText.append(value).append("\n");
        }
        TextView displayTextView = findViewById(R.id.addedValuesText);
        displayTextView.setText(displayText.toString().trim());
    }

    private void insertData() {
        String username = name.getText().toString();
        String usernameValue = name.getText().toString();
        String usermail = status.getText().toString();
        String userage = items.getText().toString();

        StringBuilder ageBuilder = new StringBuilder(userage);
        for (String value : additionalValues) {
            ageBuilder.append(", ").append(value);
        }
        String finalUserAge = ageBuilder.toString();

        String id = databaseUsers.child("users").push().getKey();

        User user = new User(username, usermail, finalUserAge);
        user.setUserId(id);
        name.setText("");

        user.setUserId(id);

        databaseUsers.child("users").child(id).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(InsertData.this, "User Details Inserted", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            status.setText("");
                            items.setText("");
                            additionalValues.clear();
                        } else {
                            Toast.makeText(InsertData.this, "Failed to insert User Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}