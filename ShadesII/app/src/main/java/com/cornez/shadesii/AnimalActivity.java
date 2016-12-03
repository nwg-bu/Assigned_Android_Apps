package com.cornez.shadesii;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimalActivity extends Activity {



    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Need to check if Activity has been switched to landscape mode
        // If yes, finished and go back to the start Activity
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.animal_fragment);

        //SHOW THE UP BUTTON IN THE ACTION BAR
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String informationValue = intent.getStringExtra("Information");

        ImageView view = (ImageView)findViewById(R.id.imgAnimal);

        if (informationValue.equals("Elephant")){
            view.setImageResource(R.drawable.animal_elephant);
        }
        if (informationValue.equals("Hippo")){
            view.setImageResource(R.drawable.animal_hippo);
        }
        if (informationValue.equals("Moose")){
            view.setImageResource(R.drawable.animal_moose);
        }
    }

}
