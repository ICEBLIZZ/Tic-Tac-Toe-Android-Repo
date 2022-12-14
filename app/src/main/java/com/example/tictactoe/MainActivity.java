package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttons[][] = new Button[3][3];

    private boolean player1Turn = true;

    private Button resetButton;

    private int roundCount;

    private int player1Points, player2Points;

    private TextView player1Textview, player2Textview;

    // To retain game state use android:freezesText="true" in layout of every button,
    // and take care of the variable here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Textview = findViewById(R.id.text_vew_p1);
        player2Textview = findViewById(R.id.text_vew_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        //on buttons clicked
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }


    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void updatePointsText() {
        player1Textview.setText("Player 1: " + player1Points);
        player2Textview.setText("Player 2: " + player2Points);
        roundCount = 0;
        player1Turn = true;
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //rows
        for (int i = 0; i < 3; i++) {
            if ((field[i][0].equals(field[i][1])) &&
                    (field[i][0].equals(field[i][2])) && (!field[i][0].equals(""))) {
                return true;
            }
        }
        //columns
        for (int i = 0; i < 3; i++) {
            if ((field[0][i].equals(field[1][i])) &&
                    (field[0][i].equals(field[2][i])) && (!field[0][i].equals(""))) {
                return true;
            }
        }
        //diagonal right
        if ((field[0][0].equals(field[1][1])) &&
                (field[0][0].equals(field[2][2])) && (!field[0][0].equals(""))) {
            return true;
        }
        //diagonal left
        if ((field[0][2].equals(field[1][1])) &&
                (field[0][2].equals(field[2][0])) && (!field[0][2].equals(""))) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    //to preserve UI

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Round Count", roundCount);
        outState.putInt("player 1 points", player1Points);
        outState.putInt("player 2 points", player2Points);
        outState.putBoolean("player turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("Round Count");
        player1Points = savedInstanceState.getInt("player 1 points");
        player2Points = savedInstanceState.getInt("player 2 points");
        player1Turn = savedInstanceState.getBoolean("player turn");
    }
}