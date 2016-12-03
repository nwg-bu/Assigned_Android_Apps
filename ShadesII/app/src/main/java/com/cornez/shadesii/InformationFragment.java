package com.cornez.shadesii;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InformationFragment extends Fragment {

    public interface InformationFragmentListener{
        public String getInformation();
    }

    private InformationFragmentListener informationFragmentListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_fragment, container, false);

        String info = informationFragmentListener.getInformation();
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(info);
        return view;
    }

    public void setText (String item) {
       // TextView view = (TextView) getView().findViewById(R.id.textView1);
       // view.setText(item);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        informationFragmentListener = (InformationFragmentListener) context;
    }


}
