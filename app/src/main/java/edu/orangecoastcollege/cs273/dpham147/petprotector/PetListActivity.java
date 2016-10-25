package edu.orangecoastcollege.cs273.dpham147.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;

    // This member variable stores the URI to whatever image has been selected
    // Default is none.png (R.drawable.none)
    private Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);

        // Constructs full URI to any Android resource
        imageURI = getUriToResource(this, R.drawable.none);

        // Set the imageURI of the ImageView in code
        petImageView.setImageURI(imageURI);
    }

    public void selectPetImage(View view) {
        // List of all permissions we need to request from user
        ArrayList<String> permList = new ArrayList<>();

        // Start by seeing if we have permission to camera
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        // Check for permission to read storage
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        // Check for permission to write to storage
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If the list has items we need to repeat permissions from the list;
        int requestCode = 100;
        if (permList.size() > 0)
        {
            // Convert ArrayList to an Array of Strings
            String[] perms = new String[permList.size()];
            // Request permissions from user

            ActivityCompat.requestPermissions(this, permList.toArray(perms), requestCode);
        }

        // If we have all 3 permissions, open image gallery
        if (cameraPermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED
                && writePermission == PackageManager.PERMISSION_GRANTED)
        {
            // Use an intent to launch gallery and camera
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, requestCode);
        }
        else
            Toast.makeText(this, "Pet Protector requires camera and external storage permission.", Toast.LENGTH_LONG).show();


    }

    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + res.getResourcePackageName(resId) +
        "/" + res.getResourceTypeName(resId) +
        "/" + res.getResourceEntryName(resId));
    }
}
