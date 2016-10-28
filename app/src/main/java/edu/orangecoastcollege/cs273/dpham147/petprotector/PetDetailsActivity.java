package edu.orangecoastcollege.cs273.dpham147.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {

    private ImageView petDetailsImageView;
    private TextView petDetailsNameTextView;
    private TextView petDetailsDetailsTextView;
    private TextView petDetailsPhoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        Intent fromActivity = getIntent();
        petDetailsNameTextView.setText(fromActivity.getStringExtra("Name"));
        petDetailsDetailsTextView.setText(fromActivity.getStringExtra("Details"));
        petDetailsPhoneTextView.setText(fromActivity.getStringExtra("Phone"));
        petDetailsImageView.setImageURI(Uri.parse(fromActivity.getStringExtra("Image")));
    }
}
