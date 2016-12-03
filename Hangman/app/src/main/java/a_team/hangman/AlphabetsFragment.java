package a_team.hangman;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlphabetsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlphabetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlphabetsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public interface AlphabetsFragmentListener {
        public void sendAlphabets(String alphabet);
        public void sendHangManImg(int imgId);
    }

    private AlphabetsFragmentListener alphabetsFragmentListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Integer> disabledChars = new ArrayList<Integer>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int lettersRemaining = 0;
    private int chances = 6;

    private int gameNum = 0;

    private String answer = "";
    private String[] answers = new String[]{"APPLE", "CHAIR", "BOSTON", "EYE", "COFFEE", "ANDROID", "MOJITO"};


    private String hint = "";
    private int hintIndex;


    private
    Button btnNewGame, btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ;
    TextView ltr1;
    ImageView imgHang;
    Button disableBtn;
    LinearLayout vRow1, vRow2, vRow3;
    String clue = "";
    private OnFragmentInteractionListener mListener;

    Animation fadeoutanim;

    View view;

    public AlphabetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlphabetsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlphabetsFragment newInstance(String param1, String param2) {
        AlphabetsFragment fragment = new AlphabetsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public int getHintIndex() {
        return hintIndex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alphabets, container, false);

        imgHang = (ImageView)view.findViewById(R.id.imgHang);

        answer = getRandom(answers);
        lettersRemaining = answer.length();

        for(int i=0; i<answer.length(); i++){
            clue += "_";
        }

        alphabetsFragmentListener.sendAlphabets(clue);

        fadeoutanim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        btnA = (Button)view.findViewById(R.id.btnA);
        btnB = (Button)view.findViewById(R.id.btnB);
        btnC = (Button)view.findViewById(R.id.btnC);
        btnD = (Button)view.findViewById(R.id.btnD);
        btnE = (Button)view.findViewById(R.id.btnE);
        btnF = (Button)view.findViewById(R.id.btnF);
        btnG = (Button)view.findViewById(R.id.btnG);
        btnH = (Button)view.findViewById(R.id.btnH);
        btnI = (Button)view.findViewById(R.id.btnI);
        btnJ = (Button)view.findViewById(R.id.btnJ);
        btnK = (Button)view.findViewById(R.id.btnK);
        btnL = (Button)view.findViewById(R.id.btnL);
        btnM = (Button)view.findViewById(R.id.btnM);
        btnN = (Button)view.findViewById(R.id.btnN);
        btnO = (Button)view.findViewById(R.id.btnO);
        btnP = (Button)view.findViewById(R.id.btnP);
        btnQ = (Button)view.findViewById(R.id.btnQ);
        btnR = (Button)view.findViewById(R.id.btnR);
        btnS = (Button)view.findViewById(R.id.btnS);
        btnT = (Button)view.findViewById(R.id.btnT);
        btnU = (Button)view.findViewById(R.id.btnU);
        btnV = (Button)view.findViewById(R.id.btnV);
        btnW = (Button)view.findViewById(R.id.btnW);
        btnX = (Button)view.findViewById(R.id.btnX);
        btnY = (Button)view.findViewById(R.id.btnY);
        btnZ = (Button)view.findViewById(R.id.btnZ);

        // Register keyboard rows
        vRow1 = (LinearLayout)view.findViewById(R.id.vRow1);
        vRow2 = (LinearLayout)view.findViewById(R.id.vRow2);
        vRow3 = (LinearLayout)view.findViewById(R.id.vRow3);

        btnA.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnA.startAnimation(fadeoutanim);
                btnA.setEnabled(false);
                disabledChars.add(btnA.getId());
                if(answer.contains("A")){
                    int ind = answer.indexOf("A");
                    rightAnswer('A', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnB.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnB.startAnimation(fadeoutanim);
                btnB.setEnabled(false);
                disabledChars.add(btnB.getId());
                if(answer.contains("B")){
                    int ind = answer.indexOf("B");
                    rightAnswer('B', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnC.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnC.startAnimation(fadeoutanim);
                btnC.setEnabled(false);
                disabledChars.add(btnC.getId());
                if(answer.contains("C")){
                    int ind = answer.indexOf("C");
                    rightAnswer('C', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnD.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnD.startAnimation(fadeoutanim);
                btnD.setEnabled(false);
                disabledChars.add(btnD.getId());
                if(answer.contains("D")){
                    int ind = answer.indexOf("D");
                    rightAnswer('D', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnE.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnE.startAnimation(fadeoutanim);
                btnE.setEnabled(false);
                disabledChars.add(btnE.getId());
                if(answer.contains("E")){
                    int ind = answer.indexOf("E");
                    rightAnswer('E', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnF.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnF.startAnimation(fadeoutanim);
                btnF.setEnabled(false);
                disabledChars.add(btnF.getId());
                if(answer.contains("F")){
                    int ind = answer.indexOf("F");
                    rightAnswer('F', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnG.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnG.startAnimation(fadeoutanim);
                btnG.setEnabled(false);
                disabledChars.add(btnG.getId());
                if(answer.contains("G")){
                    int ind = answer.indexOf("G");
                    rightAnswer('G', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnH.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnH.startAnimation(fadeoutanim);
                btnH.setEnabled(false);
                disabledChars.add(btnH.getId());
                if(answer.contains("H")){
                    int ind = answer.indexOf("H");
                    rightAnswer('H', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnI.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnI.startAnimation(fadeoutanim);
                btnI.setEnabled(false);
                disabledChars.add(btnI.getId());
                if(answer.contains("I")){
                    int ind = answer.indexOf("I");
                    rightAnswer('I', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnJ.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnJ.startAnimation(fadeoutanim);
                btnJ.setEnabled(false);
                disabledChars.add(btnJ.getId());
                if(answer.contains("J")){
                    int ind = answer.indexOf("J");
                    rightAnswer('J', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnK.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnK.startAnimation(fadeoutanim);
                btnK.setEnabled(false);
                disabledChars.add(btnK.getId());
                if(answer.contains("K")){
                    int ind = answer.indexOf("K");
                    rightAnswer('K', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnL.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnL.startAnimation(fadeoutanim);
                btnL.setEnabled(false);
                disabledChars.add(btnL.getId());
                if(answer.contains("L")){
                    int ind = answer.indexOf("L");
                    rightAnswer('L', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnM.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnM.startAnimation(fadeoutanim);
                btnM.setEnabled(false);
                disabledChars.add(btnM.getId());
                if(answer.contains("M")){
                    int ind = answer.indexOf("M");
                    rightAnswer('M', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnN.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnN.startAnimation(fadeoutanim);
                btnN.setEnabled(false);
                disabledChars.add(btnN.getId());
                if(answer.contains("N")){
                    int ind = answer.indexOf("N");
                    rightAnswer('N', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnO.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnO.startAnimation(fadeoutanim);
                btnO.setEnabled(false);
                disabledChars.add(btnO.getId());
                if(answer.contains("O")){
                    int ind = answer.indexOf("O");
                    rightAnswer('O', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnP.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnP.startAnimation(fadeoutanim);
                btnP.setEnabled(false);
                disabledChars.add(btnP.getId());
                if(answer.contains("P")){
                    int ind = answer.indexOf("P");
                    rightAnswer('P', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnQ.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnQ.startAnimation(fadeoutanim);
                btnQ.setEnabled(false);
                disabledChars.add(btnQ.getId());
                if(answer.contains("Q")){
                    int ind = answer.indexOf("Q");
                    rightAnswer('Q', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnR.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnR.startAnimation(fadeoutanim);
                btnR.setEnabled(false);
                disabledChars.add(btnR.getId());
                if(answer.contains("R")){
                    int ind = answer.indexOf("R");
                    rightAnswer('R', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnS.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnS.startAnimation(fadeoutanim);
                btnS.setEnabled(false);
                disabledChars.add(btnS.getId());
                if(answer.contains("S")){
                    int ind = answer.indexOf("S");
                    rightAnswer('S', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnT.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnT.startAnimation(fadeoutanim);
                btnT.setEnabled(false);
                disabledChars.add(btnT.getId());
                if(answer.contains("T")){
                    int ind = answer.indexOf("T");
                    rightAnswer('T', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnU.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnU.startAnimation(fadeoutanim);
                btnU.setEnabled(false);
                disabledChars.add(btnU.getId());
                if(answer.contains("U")){
                    int ind = answer.indexOf("U");
                    rightAnswer('U', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnV.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnV.startAnimation(fadeoutanim);
                btnV.setEnabled(false);
                disabledChars.add(btnV.getId());
                if(answer.contains("V")){
                    int ind = answer.indexOf("V");
                    rightAnswer('V', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnW.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnW.startAnimation(fadeoutanim);
                btnW.setEnabled(false);
                disabledChars.add(btnW.getId());
                if(answer.contains("W")){
                    int ind = answer.indexOf("W");
                    rightAnswer('W', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnX.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnX.startAnimation(fadeoutanim);
                btnX.setEnabled(false);
                disabledChars.add(btnX.getId());
                if(answer.contains("X")){
                    int ind = answer.indexOf("X");
                    rightAnswer('X', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnY.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnY.startAnimation(fadeoutanim);
                btnY.setEnabled(false);
                disabledChars.add(btnY.getId());
                if(answer.contains("Y")){
                    int ind = answer.indexOf("Y");
                    rightAnswer('Y', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        btnZ.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //map animation & check if letter is in answer
                btnZ.startAnimation(fadeoutanim);
                btnZ.setEnabled(false);
                disabledChars.add(btnZ.getId());
                if(answer.contains("Z")){
                    int ind = answer.indexOf("Z");
                    rightAnswer('Z', ind);
                } else {
                    wrongAnswer();
                }
            }
        });

        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        alphabetsFragmentListener = (AlphabetsFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void rightAnswer(char ch, int ind) {
        for (int i=ind; i<answer.length(); i++){
            if(answer.charAt(i) == ch){
                if(i == 0){
                    String back = clue.substring(1);
                    clue = ch + back;
                } else if (i == answer.length()-1){
                    String front = clue.substring(0, i);
                    clue = front + ch;
                } else {
                    String front = clue.substring(0, i);
                    String back = clue.substring(i+1);
                    clue = front + ch + back;
                }
//                array[i].setText("" + ch + "");
                lettersRemaining--;
            }
        }
        //ltr1.setText(clue);
        alphabetsFragmentListener.sendAlphabets(clue);
        if(lettersRemaining == 0){
            youWin();
        }
    }

    private void wrongAnswer() {
        chances--;
        alterImg(chances);
        if (chances == 0) {
            gameOver();
        }
    }

    private void gameOver(){
        String end = "Game Over";
        // freeze buttons except for New Game?
        disableKeys();
    }

    private void youWin() {
        alphabetsFragmentListener.sendHangManImg(R.drawable.winner);
        //imgHang.setImageResource(R.drawable.winner);
        disableKeys();
    }

    private void disableKeys() {
       disableRow(vRow1);
       disableRow(vRow2);
       disableRow(vRow3);
    }

    private void disableRow(LinearLayout lin){
        for(int i=0; i< lin.getChildCount(); i++){
            View child = lin.getChildAt(i);
            child.setEnabled(false);
        }
    }

    private void disableButtons(ArrayList<Integer> savedDisabledChars) {

        for(int i : savedDisabledChars){
            view.findViewById(i);
            disableBtn.setEnabled(false);
            disableBtn.startAnimation(fadeoutanim);
        }
    }

    private void alterImg(int h){


        switch (h) {
            case 6:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang0);
                break;
            case 5:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang1);
                break;
            case 4:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang2);
                break;
            case 3:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang3);
                break;
            case 2:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang4);
                break;
            case 1:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang5);
                break;
            case 0:
                alphabetsFragmentListener.sendHangManImg(R.drawable.hang6);
                break;
        }
    }

    public String getRandom(String[] ansArray) {
        int rndm = new Random().nextInt(ansArray.length);
        hintIndex = rndm;
        return ansArray[rndm];
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("answerHintIndex", hintIndex);
        outState.putString("savedClue", clue);
        outState.putInt("savedChances", chances);
        outState.putIntegerArrayList("savedDisabledChars",disabledChars);
        outState.putInt("savedLettersRem", lettersRemaining);
    }
}
