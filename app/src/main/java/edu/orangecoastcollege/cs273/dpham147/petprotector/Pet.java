package edu.orangecoastcollege.cs273.dpham147.petprotector;

import android.net.Uri;

/**
 * Created by Kyubey on 2016-10-27.
 */

public class Pet {

    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;

    public Pet()
    {

    }

    public Pet(int id, String name, String details, String phone, Uri imageURI){
        mId = id;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    public Pet(String name, String details, String phone, Uri imageURI) {
        this(-1, name, details, phone, imageURI);
    }

    public int getId()
    {
        return mId;
    }

    public String getName()
    {
        return mName;
    }

    public String getDetails()
    {
        return mDetails;
    }

    public String getPhone()
    {
        return mPhone;
    }

    public Uri getImageURI()
    {
        return mImageURI;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public void setDetails(String details)
    {
        mDetails = details;
    }

    public void setPhone(String phone)
    {
        mPhone = phone;
    }

    public void setImageURI(Uri imageURI)
    {
        mImageURI = imageURI;
    }

    public String toString()
    {
        return ("Pet{" +
                "id: " + mId + ", name: " + mName +
        ", details: " + mDetails +
        ", phone:" + mPhone +
        ", imageURI:" + mImageURI);
    }
}
