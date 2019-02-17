package com.example.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class EndGameActivity extends AppCompatActivity {

    private String playerName;
    private int correct;
    private int incorrect;
    private int notAnswered;
    private int cheated;
    private int totalQuestionCount;
    private TextView endGameText;
    private TextView endGameStats;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        playerName = intent.getStringExtra("name");
        correct = intent.getIntExtra("correct", 0);
        incorrect = intent.getIntExtra("incorrect", 0);
        notAnswered = intent.getIntExtra("notAnswered", 0);
        cheated = intent.getIntExtra("cheated", 0);
        totalQuestionCount = intent.getIntExtra("totalQuestionCount", 0);

        df = new DecimalFormat("#.#");
        double score = correct / Double.valueOf(totalQuestionCount) * 100;

        endGameText = findViewById(R.id.end_game_text);
        endGameText.setText("Thank you for playing GeoQuiz, " + playerName + "! You got a score of " +
                String.valueOf(df.format(score)) + "%");

        endGameStats = findViewById(R.id.end_game_stats);
        endGameStats.setText("Correct = " + String.valueOf(correct) + "\nIncorrect = " +
                String.valueOf(incorrect) + "\nNot Answered = " + String.valueOf(notAnswered) +
                "\nCheated = " + String.valueOf(cheated));




    }
}
