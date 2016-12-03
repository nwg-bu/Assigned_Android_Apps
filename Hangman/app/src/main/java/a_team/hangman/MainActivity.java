package a_team.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AlphabetsFragment.AlphabetsFragmentListener,HintFragment.HintFragmentListener {

    private ArrayList<Integer> disabledChars = new ArrayList<Integer>();
    // Register buttons, textview, & imageview
    private
        Button btnNewGame, btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ;
        TextView ltr1;
        ImageView imgHang;
        LinearLayout vRow1, vRow2, vRow3, hRow1, hRow2, hRow3, hRow4;
        String clue = "";
        TextView tvHint;

    // Register animation file
    Animation fadeoutanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Register buttons
        btnNewGame = (Button)findViewById(R.id.btnNewGame);

        btnNewGame.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent newGameIntent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                newGameIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newGameIntent);
            }
        });
    }

    @Override
    public void sendAlphabets(String alphabets) {
        AnimationFragment animationFragment = (AnimationFragment) getSupportFragmentManager().findFragmentById(R.id.fragAnim);
        animationFragment.setAlphabets(alphabets);
    }

    @Override
    public void sendHangManImg(int imgId) {
        AnimationFragment animationFragment = (AnimationFragment) getSupportFragmentManager().findFragmentById(R.id.fragAnim);
        animationFragment.setImgID(imgId);
    }

    @Override
    public int getHintIndex() {
        AlphabetsFragment alphabetsFragment = (AlphabetsFragment) getSupportFragmentManager().findFragmentById(R.id.fragAlpha);
        int index = alphabetsFragment.getHintIndex();
        return index;
    }
}
