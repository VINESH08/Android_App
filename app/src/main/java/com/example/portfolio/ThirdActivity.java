package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import com.bumptech.glide.Glide;

public class ThirdActivity extends AppCompatActivity {
    TextView nme;
    TextView ag;
    TextView pas;
    TextView stre;
    TextView weak;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        nme=findViewById(R.id.textView8);
        ag=findViewById(R.id.textView9);
        pas=findViewById(R.id.textView10);
        stre=findViewById(R.id.textView11);
        weak=findViewById(R.id.textView12);
        photo=findViewById(R.id.imageView2);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            nme.setText(extras.getString("nam"));
            ag.setText(extras.getString("ag"));
            pas.setText(extras.getString("pass"));
            stre.setText(extras.getString("stre"));
            weak.setText(extras.getString("weak"));
            String imageur=extras.getString("imageUrl");
            if (imageur != null && !imageur.isEmpty()) {
                Glide.with(this).load(imageur).into(photo);
            }
        }

    }


}