package com.example.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class EndGameActivity extends AppCompatActivity {

    private String playerName;
    private int correct;
    private int incorrect;
    private int totalQuestionCount;
    private TextView endGameText;
    private TextView medalMessage;
    private ImageView medal;
    private Button mPlayAgainButton;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        medalMessage = findViewById(R.id.medal_message);
        medal = findViewById(R.id.medal_view);

        getPlayerInfo();

        df = new DecimalFormat("#.#");
        double score = correct / Double.valueOf(totalQuestionCount) * 100;

        endGameText = findViewById(R.id.end_game_text);
        endGameText.setText("Thank you for playing GeoQuiz, " + playerName + "! You got a score of " +
                String.valueOf(df.format(score)) + "%");

        if (score >= 90) {
            medalMessage.setText(getString(R.string.gold_message));
            medal.setImageResource(R.mipmap.gold_medal);
        } else if (score < 90 && score >= 70) {
            medalMessage.setText(getString(R.string.silver_message));
            medal.setImageResource(R.mipmap.silver_medal);
        } else if (score < 70 && score >= 50) {
            medalMessage.setText(getString(R.string.bronze_message));
            medal.setImageResource(R.mipmap.bronze_medal);
        } else {
            medalMessage.setText(getString(R.string.no_medal_message));
            medal.setImageResource(R.mipmap.no_medal);
        }

        mPlayAgainButton = findViewById(R.id.play_again_button);
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndGameActivity.this, QuizActivity.class);
                intent.putExtra("name", playerName);
                startActivity(intent);
            }
        });

    }

    private void getPlayerInfo() {
        Intent intent = getIntent();
        playerName = intent.getStringExtra("name");
        correct = intent.getIntExtra("correct", 0);
        incorrect = intent.getIntExtra("incorrect", 0);
        totalQuestionCount = intent.getIntExtra("totalQuestionCount", 0);
    }
}
