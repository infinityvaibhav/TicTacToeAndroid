package com.example.oreocourse1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button playAgain;
    TextView winnerTextView;
    GridLayout mGridLayout;

    //0: yellow , 1: red, 2: empty
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPosition = {{0,1,2}, {3,4,5}, {6,7,8},{0,3, 6}, {1,4,7}, {2,5,8}, {0,4, 8}, {2,4,6}};
    boolean isGameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAgain = findViewById(R.id.button);
        winnerTextView = findViewById(R.id.textView);
        mGridLayout = findViewById(R.id.mGridLayout);
    }

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && isGameActive) {
            counter.setTranslationY(-2000);

            gameState[tappedCounter] = activePlayer;
            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(2000).rotation(180).setDuration(300);

            for (int[] winningPosition: winningPosition) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    String winner = (activePlayer == 1 ? getString(R.string.yellow) : getString(R.string.red)) + getString(R.string.user_has_won);

                    isGameActive = false;
                    gameEnded(winner);
                    break;
                } else {
                    boolean isGameOver = true;
                    for (int counterState : gameState ) {
                        if (counterState == 2) {
                            isGameOver = false;
                            break;
                        }
                    }
                    if (isGameOver) {
                        gameEnded(getString(R.string.match_tie));
                    }
                }
            }
        }
    }

    private void gameEnded(String winner) {


        playAgain.setVisibility(View.VISIBLE);
        winnerTextView.setVisibility(View.VISIBLE);
        winnerTextView.setText(winner);
    }

    public void playAgain(View view) {

        playAgain.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);
        for (int mGridChildPosition = 0; mGridChildPosition < mGridLayout.getChildCount(); mGridChildPosition++) {
            ImageView childImageView = (ImageView) mGridLayout.getChildAt(mGridChildPosition);
            childImageView.setImageDrawable(null);
            gameState[mGridChildPosition] = 2;
        }

        activePlayer = 0;
        isGameActive = true;
    }
}
