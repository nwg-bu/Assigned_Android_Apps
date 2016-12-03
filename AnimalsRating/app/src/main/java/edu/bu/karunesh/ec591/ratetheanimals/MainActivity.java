package edu.bu.karunesh.ec591.ratetheanimals;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LeftFragment.LeftFragmentListner, RightFragment.RightFragmentListner {

    public int currDrawableIndex;
    private ImageView imgView;
    private RatingBar ratingBar;
    private float[] ratings = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.imgView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratings[currDrawableIndex] = rating;
            }
        });

    }

    @Override
    public void sendIndexFromLeft(int index) {
        RightFragment rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.rightFragment);
        rightFragment.setCurrIndex(index);
        currDrawableIndex=index;
        ratingBar.setRating(ratings[currDrawableIndex]);
    }

    @Override
    public void sendImageFromLeft(Drawable image) {
        imgView.setImageDrawable(image);
    }

    @Override
    public void sendIndexFromRight(int index) {
        LeftFragment leftFragment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.leftFragment);
        leftFragment.setCurrIndex(index);
        currDrawableIndex=index;
        ratingBar.setRating(ratings[currDrawableIndex]);
    }

    @Override
    public void sendImageFromRight(Drawable image) {
        imgView.setImageDrawable(image);
    }
}
