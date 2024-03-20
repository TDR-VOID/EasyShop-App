package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    FloatingActionButton deleteButton;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Userlist.this, InsertData.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lists View");

        recyclerView = findViewById(R.id.recycleview);
        deleteButton = findViewById(R.id.deleteButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the last item from Firebase
                removeLastItemFromFirebaseAndRV();

                // Remove the last item from the RecyclerView
                removeLastItemFromRecyclerView();
            }
        });
    }

    private void removeLastItemFromFirebaseAndRV() {
        if (!list.isEmpty()) {
            User lastItem = list.get(list.size() - 1);
            String lastItemId = lastItem.getUserId(); // Ensure getUserId() returns the correct ID
            Toast.makeText(Userlist.this, lastItemId, Toast.LENGTH_SHORT).show();
            if (lastItemId != null) {
                DatabaseReference userRef = databaseReference.child(lastItemId);
                userRef.removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Userlist.this, "Item deleted from Firebase", Toast.LENGTH_SHORT).show();
                                    list.remove(lastItem);
                                    adapter.notifyItemRemoved(list.size());
                                } else {
                                    Toast.makeText(Userlist.this, "Failed to delete from Firebase", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    private void removeLastItemFromRecyclerView() {
        if (!list.isEmpty()) {
            list.remove(list.size() - 1);
            adapter.notifyItemRemoved(list.size());
        }
    }



    }
