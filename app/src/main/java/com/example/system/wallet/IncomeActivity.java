package com.example.system.wallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class IncomeActivity extends AppCompatActivity {

    private EditText ammount;
    private Button income, showincome;
    private ProgressBar progbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Intent intent=getIntent();

        final String username=intent.getStringExtra("user");

        firebaseAuth=FirebaseAuth.getInstance();

        ammount=(EditText)findViewById(R.id.incomeeditText);
        income=(Button)findViewById(R.id.incomebutton);
        showincome=(Button)findViewById(R.id.showincomebutton);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_id=firebaseAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db= FirebaseDatabase.getInstance().getReference().child("User").child(user_id);

                String income=ammount.getText().toString();
                String user=username;

                Map newinfo= new HashMap();

                newinfo.put("Income Amount: ",income);
                newinfo.put("User Name:",user);

                current_user_db.setValue(newinfo);

            }
        });

    }
}
