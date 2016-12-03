package edu.bu.karunesh.ec591.ratetheanimals;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class LeftFragment extends Fragment {

    private int currDrawableIndex;
    ArrayList<Drawable> drawables;  //keeping track of our images
    private Button btnLeft;

    public LeftFragment() {
        // Required empty public constructor
    }

    public void setCurrIndex(int currIndex) {
        this.currDrawableIndex = currIndex;
    }

    public interface LeftFragmentListner {
        public void sendIndexFromLeft(int index);
        public void sendImageFromLeft(Drawable image);
    }

    LeftFragmentListner LFL;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        LFL = (LeftFragmentListner) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_left, container, false);
        currDrawableIndex = 0;
        getDrawables();
        btnLeft = (Button) v.findViewById(R.id.btnLeft);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currDrawableIndex==0){
                    currDrawableIndex = drawables.size()-1;
                }
                else{
                    currDrawableIndex--;
                }
                changePicture();
                LFL.sendIndexFromLeft(currDrawableIndex);
            }
        });

        return v;
    }

    private void changePicture() {
        LFL.sendImageFromLeft(drawables.get(currDrawableIndex));
    }

    private void getDrawables() {
        Field[] drawablesFields = R.drawable.class.getFields();
        drawables = new ArrayList<Drawable>();

        String fieldName;
        for (Field field : drawablesFields) {
            try {
                fieldName = field.getName();
                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
                if (fieldName.startsWith("animals_"))  //only add drawable resources that have our prefix.
                    drawables.add(getResources().getDrawable(field.getInt(null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
