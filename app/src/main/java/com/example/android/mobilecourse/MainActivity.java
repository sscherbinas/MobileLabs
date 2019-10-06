package com.example.android.mobilecourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView welcomeText;
    TextView signoutButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        welcomeText = (TextView) findViewById(R.id.welcome_text);
        signoutButton = (TextView) findViewById(R.id.btn_signout);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                welcomeText.setText(getString(R.string.Welcome) + name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(getString(R.string.database_read_failure) + databaseError.getCode());
            }
        });

        signoutButton.setOnClickListener(v -> {
            if(mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
