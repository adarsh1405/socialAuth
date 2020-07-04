package com.adarsh.socialauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fbprofile extends AppCompatActivity {
    TextView tname,tmail;
    Button fblogout;
    FirebaseAuth mAuth;
    ImageView img;
    private GoogleSignInAccount mGoogleSignInclient;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbprofile);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser muser= mAuth.getCurrentUser();
        img=findViewById(R.id.img);
        tname=findViewById(R.id.name);
        tmail=findViewById(R.id.mail);
        fblogout=findViewById(R.id.logout);
        fblogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();
               mAuth.signOut();
                LoginManager.getInstance().logOut();

                finish();
            }
        });

        if(muser!=null){
            String name=muser.getDisplayName();
            String mail=muser.getEmail();
            String imgURL=muser.getPhotoUrl().toString();

            Glide.with(this).load(imgURL).into(img);
            tname.setText(name);
            tmail.setText(mail);
        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(fbprofile.this,MainActivity.class));
                        finish();
                    }
                });
    }
}