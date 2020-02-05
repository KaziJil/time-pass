package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView pimageview;
   private TextView pemailtv,pPhonetv,pNametv;


    public ProfileFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_profile, container, false);

       firebaseAuth=FirebaseAuth.getInstance();
       firebaseUser=firebaseAuth.getCurrentUser();
       firebaseDatabase=FirebaseDatabase.getInstance();
       databaseReference=firebaseDatabase.getReference();


       pimageview=view.findViewById(R.id.profilesImaveavater_id);
       pNametv=view.findViewById(R.id.pNameTVid);
       pemailtv=view.findViewById(R.id.pEmailTVid);
       pPhonetv=view.findViewById(R.id.pPhoneTVid);

       // Query query=databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    String name=""+ds.child("name").getValue();
                    String email= ""+ds.child("email").getValue();
                    String phone= ""+ds.child("phone").getValue();
                    String image=""+ds.child("image").getValue();

                    pNametv.setText(name);
                    pPhonetv.setText(phone);
                    pemailtv.setText(email);

                    try{

                        Picasso.get().load(image).into(pimageview);

                    } catch (Exception e){

                        Toast.makeText(getContext(), "Exception: "+e, Toast.LENGTH_SHORT).show();
                        Picasso.get().load(R.drawable.addphoto_icon).into(pimageview);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


}