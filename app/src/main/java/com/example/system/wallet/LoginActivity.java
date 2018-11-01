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

public class LoginActivity extends AppCompatActivity {

    private EditText passord, email;
    private Button loginbutton, exit, temp;
    private TextView reg;
    private ProgressBar progbar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();


        passord= (EditText)findViewById(R.id.loginpass);
        email= (EditText)findViewById(R.id.loginmail);
        loginbutton=(Button)findViewById(R.id.loginbutton);
        temp=(Button)findViewById(R.id.tempbutton);
        reg=(TextView)findViewById(R.id.logintoreg);
        progbar=(ProgressBar)findViewById(R.id.progressBar1);
        exit=(Button)findViewById(R.id.exit);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginvalidate();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, TemporaryActivity.class);
                startActivity(intent);
            }

        });
    }


    private  void loginvalidate() {


        String logpass = passord.getText().toString();
        String logemail = email.getText().toString();


        if (logpass.isEmpty()) {

            passord.setError("Password Required");
            passord.requestFocus();
            return;
        }

        if (logemail.isEmpty()) {

            email.setError("Email Required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(logemail).matches()) {
            email.setError("Enter Email in Correct form");
            email.requestFocus();
            return;
        }

        if (logpass.length() < 6) {
            passord.setError("Password need to be at least 6");
            passord.requestFocus();
            return;
        }

        Intent intent=new Intent(LoginActivity.this,IncomeActivity.class);
        intent.putExtra("mail",logemail);
        intent.putExtra("pass",logpass);

        progbar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(logemail,logpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(LoginActivity.this, UserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "User not found!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
