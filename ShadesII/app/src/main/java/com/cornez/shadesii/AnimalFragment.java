package com.cornez.shadesii;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class AnimalFragment extends Fragment {

    public interface AnimalFragmentListener{
        public int getImageResource();
    }

    private AnimalFragmentListener animalFragmentListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animal_fragment, container, false);

        int imageResource = animalFragmentListener.getImageResource();

        ImageView imageView = (ImageView) view.findViewById(R.id.imgAnimal);
        imageView.setImageResource(imageResource);
        return view;
    }

    /*public void setImg (String item) {

        //String res = "R.drawable.animal_" + item;
        ImageView view = (ImageView)getView().findViewById(R.id.imgAnimal);
        //view.setImageResource(getResources().getIdentifier(res, "drawable", getActivity().getPackageName()));

        if (item.equals("Elephant")){
            view.setImageResource(R.drawable.animal_elephant);
        }
        if (item.equals("Hippo")){
            view.setImageResource(R.drawable.animal_hippo);
        }
        if (item.equals("Moose")){
            view.setImageResource(R.drawable.animal_moose);
        }
    }*/

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        animalFragmentListener = (AnimalFragmentListener) context;
    }

}
