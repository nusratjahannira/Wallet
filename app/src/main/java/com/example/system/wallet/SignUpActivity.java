package com.example.system.wallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, passord, email;
    private Button regbutton;
    private ProgressBar progbar;
    private TextView loginpage;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username= (EditText)findViewById(R.id.reguser);
        passord= (EditText)findViewById(R.id.regpass);
        email= (EditText)findViewById(R.id.regemail);
        regbutton=(Button)findViewById(R.id.regbutton);
        loginpage=(TextView)findViewById(R.id.regtologin);
        progbar=(ProgressBar)findViewById(R.id.progressBar2);

        firebaseAuth= FirebaseAuth.getInstance();

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUpActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }



    private  void validate(){


        String regname=username.getText().toString();
        String regpass=passord.getText().toString();
        String regemail=email.getText().toString();


        if(regname.isEmpty()){

            username.setError("User Name Required");
            username.requestFocus();
            return;
        }

        if(regpass.isEmpty()){

            passord.setError("Password Required");
            passord.requestFocus();
            return;
        }

        if(regemail.isEmpty()){

            email.setError("Email Required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(regemail).matches()){
            email.setError("Enter Email in Correct form");
            email.requestFocus();
            return;
        }

        if(regpass.length()<6){
            passord.setError("Password need to be at least 6");
            passord.requestFocus();
            return;
        }

        progbar.setVisibility(View.VISIBLE);
        Intent intent=new Intent(SignUpActivity.this,IncomeActivity.class);
        intent.putExtra("user",regname);


        firebaseAuth.createUserWithEmailAndPassword(regemail,regpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progbar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Toast.makeText(SignUpActivity.this,"Successfully!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(SignUpActivity.this,"Email Alread Resigtered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Unsuccessfully!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
