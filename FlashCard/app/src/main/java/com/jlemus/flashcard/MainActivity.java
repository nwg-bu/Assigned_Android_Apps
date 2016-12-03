package com.jlemus.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList randomNumberList;
    private Button btnGenRandProbs;
    private TextView tvNumerator;
    private TextView tvDenominator;
    private int numbersRemaining;
    private int userScore;
    private RadioButton radBtnAdd;
    private RadioButton radBtnSub;
    private TextView tvSign;
    private EditText etUserInput;
    private Button btnSubmit;
    private static int gameCounter = 9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent myIntent = getIntent();
        String myUserName = myIntent.getStringExtra("username");
        Toast.makeText(getApplicationContext(), "Welcome " + myUserName, Toast.LENGTH_LONG).show();
        //get refs to views that we will need
        tvNumerator = (TextView) findViewById(R.id.tvNumertor);
        tvDenominator = (TextView) findViewById(R.id.tvDenominator);
        tvSign = (TextView) findViewById(R.id.tvSign);
        btnGenRandProbs = (Button) findViewById(R.id.btnGenerateRandProbs);
        radBtnAdd = (RadioButton) findViewById(R.id.radBtnAdd);
        radBtnSub = (RadioButton) findViewById(R.id.radBtnSub);
        //final RadioGroup radGrp = (RadioGroup) findViewById(R.id.radGrpGroup);
        etUserInput = (EditText) findViewById(R.id.etUserInput);
        btnSubmit = (Button) findViewById(R.id.btnSubmitAnswer);
        numbersRemaining = 18;
        userScore = 0;

        //declare and initialize arraylist that will hold the 20 random integers
        randomNumberList = new ArrayList(20);
        final Random randNumGen = new Random();
        final Random randNumeratorGenIndex = new Random();
        final Random randDenominatorGenIndex = new Random();

        etUserInput.setEnabled(false);


        btnGenRandProbs.setOnClickListener(new View.OnClickListener() {
            //populate the arraylist
            @Override
            public void onClick(View v) {

                Random randNumber = new Random();

                int numerator = randNumber.nextInt(10);
                int denominator = randNumber.nextInt(10);

                String numeratorText = String.valueOf(numerator);
                String denominatorText = String.valueOf(denominator);

                tvNumerator.setText(numeratorText);
                tvDenominator.setText(denominatorText);
                //gameCounter--;
                Toast.makeText(getApplicationContext(), "Start the game!", Toast.LENGTH_SHORT).show();
                btnGenRandProbs.setEnabled(false);


                etUserInput.setEnabled(true);
            }
        });
        //addition radio button logic
        radBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSign.setText("+");
            }
        });

        //subtraction radio button logic
        radBtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSign.setText("-");
            }
        });


        //submit button here
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Please enter an answer!",Toast
//                        .LENGTH_SHORT).show();
                // check if user did not enter anything in the textbox
                if( etUserInput.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter an answer!",Toast
                            .LENGTH_SHORT).show();
                    return;
                }

                //check if no radio button is checked
                if(!radBtnAdd.isChecked() && !radBtnSub.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please select Addition or " +
                            "Subtraction", Toast.LENGTH_LONG).show();
                    return;
                }

                int userAnswer = Integer.parseInt(etUserInput.getText().toString());
                int numerator_input = Integer.parseInt(tvNumerator.getText().toString());
                int denominator_input = Integer.parseInt(tvDenominator.getText().toString());

                checkMath(userAnswer, numerator_input, denominator_input);

                if(gameCounter == 0){
                    gameOver();
                    return;
                }

                else{
                    gameLoop();
                    gameCounter--;
                    Log.d("gamecounter is ", String.valueOf(gameCounter));
                }


                etUserInput.setText("");
                //numbersRemaining -= 2;



            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameLoop(){

        Random randNumber = new Random();
        int numerator = 0;
        int denominator = 0;
        numerator = randNumber.nextInt(10);
        denominator = randNumber.nextInt(10);

        //makeNumeratorLargest(numerator, denominator);

        if(denominator > numerator){
            int temp = numerator;
            numerator = denominator;
            denominator = temp;
        }

        String numeratorText = String.valueOf(numerator);
        String denominatorText = String.valueOf(denominator);

        tvNumerator.setText(numeratorText);
        tvDenominator.setText(denominatorText);

        // store user answer here and get numerator and denominator and put in int vars

    }

    public void makeNumeratorLargest(int numerator, int denominator){
        if(denominator > numerator){
                    int temp = numerator;
                    numerator = denominator;
                    denominator = temp;
                }
        }

    public void gameOver(){
        Log.d("game over", "gameover");
        btnSubmit.setEnabled(false);
        Toast.makeText(getApplicationContext(), "You scored " + userScore + " out of 10.", Toast.LENGTH_LONG).show();
        return;
    }

    public void checkMath(int userAnswer, int numerator_input, int denominator_input){
        //math check for addition
        Log.d("user answer", String.valueOf(userAnswer));
        Log.d("numerator_input", String.valueOf(numerator_input));
        Log.d("denominator_input", String.valueOf(denominator_input));
        if(radBtnAdd.isChecked()){
            if(userAnswer == numerator_input + denominator_input){
                Log.d("add", "correct");
                userScore++;
            }
            else {
                Log.d("wrong", "wrong");
            }

        }

        //math check for subtraction
        if(radBtnSub.isChecked()){
            if(userAnswer == numerator_input - denominator_input){
                Log.d("sub", "correct");
                userScore++;
            }
            else {
                Log.d("sub", "wrong");
            }
        }
    }



}
