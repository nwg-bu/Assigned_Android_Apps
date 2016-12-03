package com.cornez.shadesii;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;


public class MyActivity extends Activity implements
        MyListFragment.OnItemSelectedListener,InformationFragment.InformationFragmentListener, AnimalFragment.AnimalFragmentListener {


    private String link;
    private int imgResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


       /* if (getResources().getConfiguration().orientation == 0){
            if (getIntent().getStringExtra("Information").equals("Elephant")
                    || getIntent().getStringExtra("Information").equals("Hippo")
                    || getIntent().getStringExtra("Information").equals("Moose")){

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AnimalFragment fragment = new AnimalFragment();
                ft.replace(R.id.fragment_container, fragment, "animalFragment");
                ft.commit();
            } else {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                InformationFragment fragment = new InformationFragment();
                ft.replace(R.id.fragment_container, fragment, "infoFragment");
                ft.commit();
            }
        }*/
    }

    @Override
    public void onColorItemSelected(String link) {

        if (link.equals("Elephant") || link.equals("Moose") || link.equals("Hippo")){

            // if LANDSCAPE orientation
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
               /* AnimalFragment fragment2 = (AnimalFragment)getFragmentManager().findFragmentByTag("animalFragment");
                fragment2.setImg(link);*/
                AnimalFragment animalFragment = new AnimalFragment();
                animalFragment.setArguments(getIntent().getExtras());
                //getFragmentManager().beginTransaction().add(R.id.containerFrame, animalFragment).commit();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.containerFrame, animalFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                if(link.equals("Elephant")){
                    this.imgResource = R.drawable.animal_elephant;
                }else if(link.equals("Moose")){
                    this.imgResource = R.drawable.animal_moose;
                }else{
                    this.imgResource = R.drawable.animal_hippo;
                }
            }

            //A SINGLE-PANE CONFIGURATION -
            //  IF FRAGMENT 2 DOES NOT EXIST IN THIS LAYOUT, THEN ACTIVATE THE NEXT ACTIVITY
            else {
                Intent intent = new Intent(this, AnimalActivity.class);
                intent.putExtra("Information", link);
                startActivity(intent);
            }
        } else {
            //CHECK IF FRAGMENT2 EXISTS IN THIS LAYOUT

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                /*InformationFragment fragment2 = (InformationFragment)getFragmentManager().findFragmentByTag("infoFragment");
                fragment2.setText(link);*/

                InformationFragment informationFragment = new InformationFragment();
                informationFragment.setArguments(getIntent().getExtras());

                //getFragmentManager().beginTransaction().add(R.id.containerFrame, informationFragment).commit();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.containerFrame, informationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                this.link = link;
                //informationFragment.setText(link);
            }

            //A SINGLE-PANE CONFIGURATION -
            //  IF FRAGMENT 2 DOES NOT EXIST IN THIS LAYOUT, THEN ACTIVATE THE NEXT ACTIVITY
            else {
                Intent intent = new Intent(this, InformationActivity.class);
                intent.putExtra("Information", link);
                startActivity(intent);
            }
        }
    }


    @Override
    public String getInformation() {
        return this.link;
    }

    @Override
    public int getImageResource() {
        return this.imgResource;
    }
}
