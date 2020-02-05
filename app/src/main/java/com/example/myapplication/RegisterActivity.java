package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG ="RegisterActivity";
    Context context;
    TextInputEditText remail,rpass;
    ProgressDialog progressDialog;
    TextView alredyTV;
    Button rregbtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Action bar and it's title

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create Account");

        mAuth = FirebaseAuth.getInstance();
         progressDialog=new ProgressDialog(this);



        remail=findViewById(R.id.remail_id);
        rpass=findViewById(R.id.rpass_id);
        rregbtn=findViewById(R.id.rregbtn_id);
        alredyTV=findViewById(R.id.alredy_acnt_id);


        progressDialog.setMessage("Registering user....");

        rregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=remail.getText().toString();
                String pass=rpass.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    remail.setError("Invalid Email");
                    remail.setFocusable(true);
                }

                else if(pass.length()<6){

                    rpass.setError("Passsword length at least 6 characters");
                    rpass.setFocusable(true);
                }
                else {

                    registerUser(email,pass);
                }
            }
        });

        alredyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String email,String pass){

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email=user.getEmail();
                            String uid=user.getUid();

                            HashMap<Object,String>hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","name");
                            hashMap.put("phone","phone");// set after
                            hashMap.put("image","image");

                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            //paht to store user date named "Users

                            DatabaseReference reference=database.getReference("Users");
                            //put the  data in hashmap

                            reference.child(uid).setValue(hashMap);



                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                            finish();

                        } else {

                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
