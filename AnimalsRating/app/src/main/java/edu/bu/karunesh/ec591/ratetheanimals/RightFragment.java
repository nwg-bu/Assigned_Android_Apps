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

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RightFragment extends Fragment {

    private int currDrawableIndex=0;
    ArrayList<Drawable> drawables;  //keeping track of our images
    private Button btnRight;

    public RightFragment() {
        // Required empty public constructor
    }

    public interface RightFragmentListner {
        public void sendIndexFromRight(int index);
        public void sendImageFromRight(Drawable image);
    }

    RightFragmentListner RFL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_right, container, false);
        currDrawableIndex = 0;
        getDrawables();
        btnRight = (Button) v.findViewById(R.id.btnRight);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currDrawableIndex == drawables.size()-1){
                    currDrawableIndex=0;
                }
                else{
                    currDrawableIndex++;
                }
                changePicture();
                RFL.sendIndexFromRight(currDrawableIndex);
            }
        });

        return v;
    }

    private void changePicture() {
        RFL.sendImageFromRight(drawables.get(currDrawableIndex));
    }

    private void getDrawables() {
        Field[] drawablesFields = R.drawable.class.getFields();
        drawables = new ArrayList<Drawable>();

        String fieldName;
        for (Field field : drawablesFields) {
            try {
                fieldName = field.getName();
                if (fieldName.startsWith("animals_"))  //only add drawable resources that have our prefix.
                    drawables.add(getResources().getDrawable(field.getInt(null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RFL = (RightFragmentListner) context;

    }

    public void setCurrIndex(int currIndex) {
        this.currDrawableIndex = currIndex;

    }

}
