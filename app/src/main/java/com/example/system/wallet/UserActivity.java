package com.example.system.wallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserActivity extends AppCompatActivity {

    private Button incomebutton, expencebutton, notebutton, creditbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        incomebutton=(Button)findViewById(R.id.income);
        expencebutton=(Button)findViewById(R.id.expence);
        notebutton=(Button)findViewById(R.id.note);
        creditbutton=(Button)findViewById(R.id.creditcard);


        incomebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserActivity.this, IncomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
