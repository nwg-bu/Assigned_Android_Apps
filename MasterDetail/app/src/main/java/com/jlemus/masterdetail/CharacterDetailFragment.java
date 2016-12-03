package com.jlemus.masterdetail;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlemus.masterdetail.dummy.DummyContent;

/**
 * A fragment representing a single Character detail screen.
 * This fragment is either contained in a {@link CharacterListActivity}
 * in two-pane mode (on tablets) or a {@link CharacterDetailActivity}
 * on handsets.
 */
public class CharacterDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    public ImageView ivCharacterImageView;
    public TextView tvCharacterTextView;
    public WebView wvWebview;
    public TextView tvWebsiteURL;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CharacterDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.website_name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.character_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((WebView) rootView.findViewById(R.id.wvCharacterBio)).loadUrl(mItem.website_url);


            ivCharacterImageView = (ImageView) rootView.findViewById(R.id.ivCharacterImage);
            tvCharacterTextView = (TextView) rootView.findViewById(R.id.tvCharacterDetail);
            tvCharacterTextView.setText(mItem.info);
            wvWebview = (WebView) rootView.findViewById(R.id.wvCharacterBio);
            tvWebsiteURL = (TextView) rootView.findViewById(R.id.tvWebsiteURL);
            tvWebsiteURL.setText(mItem.website_url);
            Log.d("TAG", mItem.imageName);
            //ivCharacterImageView.setBackgroundResource;
            //ivCharacterImageView.setBackgroundResource(R.drawable.heisenberg);
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier(mItem
                    .imageName, "drawable", getContext().getPackageName()));

            ivCharacterImageView.setBackground(drawable);

//            tvCharacterTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    wvWebview.loadUrl(mItem.website_url);
//                }
//            });

            tvWebsiteURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wvWebview.loadUrl(mItem.website_url);
                }
            });


        }




        return rootView;
    }


}
