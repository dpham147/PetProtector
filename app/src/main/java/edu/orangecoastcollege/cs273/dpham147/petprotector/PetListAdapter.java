package edu.orangecoastcollege.cs273.dpham147.petprotector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyubey on 2016-10-27.
 */

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context context;
    private List<Pet> petList = new ArrayList<Pet>();
    private int resourceId;

    private ImageView petListImageView;
    private TextView petListNameTextView;
    private TextView petListDetailsTextView;
    private LinearLayout petListLinearLayout;

    public PetListAdapter(Context c, int rId, List<Pet> pets)
    {
        super(c, rId, pets);
        context = c;
        resourceId = rId;
        petList = pets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pet selectedPet = petList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resourceId, null);

        petListImageView = (ImageView) view.findViewById(R.id.petListImageView);
        petListNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        petListDetailsTextView = (TextView) view.findViewById(R.id.petListDetailsTextView);
        petListLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);

        petListImageView.setImageURI(selectedPet.getImageURI());
        petListNameTextView.setText(selectedPet.getName());
        petListDetailsTextView.setText(selectedPet.getDetails());

        petListLinearLayout.setTag(selectedPet);

        return view;
    }
}
