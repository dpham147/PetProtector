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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;
    private static final int REQUEST_CODE = 100;

    private DBHelper db;
    private List<Pet> petList;
    private PetListAdapter petListAdapter;

    private ListView petsListView;
    private EditText nameEditText;
    private EditText detailEditText;
    private EditText phoneEditText;

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

        db = new DBHelper(this);
        petList = db.getAllPets();
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);

        petsListView = (ListView) findViewById(R.id.petListView);
        petsListView.setAdapter(petListAdapter);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        detailEditText = (EditText) findViewById(R.id.detailEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);

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

        if (permList.size() > 0)
        {
            // Convert ArrayList to an Array of Strings
            String[] perms = new String[permList.size()];
            // Request permissions from user

            ActivityCompat.requestPermissions(this, permList.toArray(perms), REQUEST_CODE);
        }

        // If we have all 3 permissions, open image gallery
        if (cameraPermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED
                && writePermission == PackageManager.PERMISSION_GRANTED)
        {
            // Use an intent to launch gallery and camera
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE);
        }
        else
            Toast.makeText(this, "Pet Protector requires camera and external storage permission.", Toast.LENGTH_LONG).show();


    }

    // Code to handle user closing image gallery by selecting an image or pressing back button
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Intent data is the URI selected from the image gallery
        // Decide if the user selected an image:
        if (data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Set imageURI to the data:
            imageURI = data.getData();
            petImageView.setImageURI(imageURI);
        }
    }

    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + res.getResourcePackageName(resId) +
        "/" + res.getResourceTypeName(resId) +
        "/" + res.getResourceEntryName(resId));
    }

    public void addPet (View view)
    {
        String name = nameEditText.getText().toString();
        String details = detailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (name.isEmpty() || details.isEmpty() || phone.isEmpty())
        {
            Toast.makeText(this, "Name, details, or phone number is blank.", Toast.LENGTH_SHORT).show();
        }
        else {
            Pet newPet = new Pet(name, details, phone, imageURI);
            db.addPet(newPet);
            petList.add(newPet);
            petListAdapter.notifyDataSetChanged();
        }

        nameEditText.setText("");
        detailEditText.setText("");
        phoneEditText.setText("");
        imageURI = getUriToResource(this, R.drawable.none);
        petImageView.setImageURI(imageURI);
    }

    public void viewPetDetails(View view)
    {
        Pet selectedPet = (Pet) petsListView.getTag();
        Intent toDetails = new Intent(this, PetDetailsActivity.class);
        toDetails.putExtra("Name", selectedPet.getName());
        toDetails.putExtra("Details", selectedPet.getDetails());
        toDetails.putExtra("Phone", selectedPet.getPhone());
        toDetails.putExtra("Image", selectedPet.getImageURI().toString());

        startActivity(toDetails);
    }
}
