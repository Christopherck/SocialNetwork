package com.example.chasekidd.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity
{
    private EditText UserEmail, UserPassword, UserConfirmPassword;
    private Button CreateAccountButton;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        UserConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        CreateAccountButton = (Button) findViewById(R.id.register_create_account);
        loadingBar = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               CreateNewAccount();

            }
        });


    }

    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String passwprd = UserPassword.getText().toString();
        String confrimPassword = UserConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Input an Email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confrimPassword))
        {
            Toast.makeText(this, "Please Input a Password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confrimPassword))
        {
            Toast.makeText(this, "Please Confirm Password", Toast.LENGTH_SHORT).show();
        }
        else if (!passwprd.equals(confrimPassword))
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Hang on, creating your account");
            loadingBar.setMessage("It's happening we are creating your account");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);


          mAuth.createUserWithEmailAndPassword(email, passwprd)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task)
                      {
                          if(task.isSuccessful())
                          {
                              SendUserToSetupActivity();
                              Toast.makeText(RegisterActivity.this, "Thank You for Registering", Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                          }
                          else
                          {
                              String message = task.getException().getMessage();
                              Toast.makeText(RegisterActivity.this, "" +message, Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                          }
                      }
                  });
        }
    }

    private void SendUserToSetupActivity()
    {
        Intent setupIntent = new Intent(RegisterActivity.this, SetUpActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(setupIntent);
        finish();

    }
}
