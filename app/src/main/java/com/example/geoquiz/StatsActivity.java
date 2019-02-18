package com.example.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    private TextView endGameStats;
    private int correct;
    private int incorrect;
    private int notAnswered;
    private int cheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getPlayerInfo();

        endGameStats = findViewById(R.id.end_game_stats);
        endGameStats.setText("Correct = " + String.valueOf(correct) + "\nIncorrect = " +
                String.valueOf(incorrect) + "\nNot Answered = " + String.valueOf(notAnswered) +
                "\nCheated = " + String.valueOf(cheated));
    }

    private void getPlayerInfo() {
        Intent intent = getIntent();
        correct = intent.getIntExtra("correct", 0);
        incorrect = intent.getIntExtra("incorrect", 0);
        notAnswered = intent.getIntExtra("notAnswered", 0);
        cheated = intent.getIntExtra("cheated", 0);
    }
}
