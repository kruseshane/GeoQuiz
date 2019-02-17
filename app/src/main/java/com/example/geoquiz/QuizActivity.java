package com.example.geoquiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView questionTV;
    private TextView questionNumberDisplay;
    private int currentIndex = 0;
    private QuestionBank qb;
    private Question [] questionBank;
    private int correctCount;
    private int incorrectCount;
    private int notAnsweredCount;
    private int cheatCount;
    private String playerName;
    private boolean cheated = false;

    private void updateQuestion() {
        int question = questionBank[currentIndex].getQuestion();
        questionTV.setText(question);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        playerName = intent.getStringExtra("name");

        qb = new QuestionBank();
        qb.populateBank();
        questionBank = qb.getBank();


        questionTV = findViewById(R.id.question_text);
        updateQuestion();

        questionNumberDisplay = findViewById(R.id.current_question);
        questionNumberDisplay.setText("Question: " + String.valueOf(currentIndex + 1) + "/" +
                String.valueOf(questionBank.length));

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionBank[currentIndex].isAnswer() && cheated) {
                    cheatCount++;
                    currentIndex ++;
                    cheated = false;
                    if (currentIndex < 10) {
                        updateQuestion();
                    }
                } else if (questionBank[currentIndex].isAnswer() && !cheated) {
                    System.out.println("Correct");
                    correctCount ++;
                    currentIndex ++;
                    if (currentIndex < 10) {
                        updateQuestion();
                    }
                } else if (!questionBank[currentIndex].isAnswer()) {
                    System.out.println("Incorrect");
                    incorrectCount ++;
                    currentIndex ++;
                    if (currentIndex < 10) {
                        updateQuestion();
                    }
                }
                if (currentIndex < 10) {
                    questionNumberDisplay.setText("Question: " + String.valueOf(currentIndex + 1) + "/" +
                            String.valueOf(questionBank.length));
                }

                if (currentIndex == (questionBank.length)) {
                    Intent intent = sendInfoToEndGameScreen(playerName, correctCount, incorrectCount,
                            cheatCount, notAnsweredCount, questionBank.length);
                    startActivity(intent);
                }
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!questionBank[currentIndex].isAnswer() && cheated) {
                    cheatCount++;
                    currentIndex ++;
                    cheated = false;
                    if (currentIndex < 10) {
                        updateQuestion();
                    }
                } else if (!questionBank[currentIndex].isAnswer() && !cheated) {
                    System.out.println("Correct");
                    correctCount ++;
                    currentIndex ++;
                    if (currentIndex < 10) {
                        updateQuestion();
                    }
                } else if (questionBank[currentIndex].isAnswer()) {
                    System.out.println("Incorrect");
                    incorrectCount ++;
                    currentIndex ++;
                    if (currentIndex < 10) {
                        updateQuestion();
                    }
                }

                if (currentIndex < 10) {
                    questionNumberDisplay.setText("Question: " + String.valueOf(currentIndex + 1) + "/" +
                            String.valueOf(questionBank.length));
                }

                if (currentIndex == (questionBank.length)) {
                    Intent intent = sendInfoToEndGameScreen(playerName, correctCount, incorrectCount,
                            cheatCount, notAnsweredCount, questionBank.length);
                    startActivity(intent);
                }
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == (questionBank.length - 1)) {
                    Intent intent = sendInfoToEndGameScreen(playerName, correctCount, incorrectCount,
                            cheatCount, notAnsweredCount, questionBank.length);
                    startActivity(intent);
                } else {
                    notAnsweredCount ++;
                    currentIndex = (currentIndex + 1) % questionBank.length;
                    updateQuestion();
                }
                questionNumberDisplay.setText("Question: " + String.valueOf(currentIndex + 1) + "/" +
                        String.valueOf(questionBank.length));
            }
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 0) {
                    Toast.makeText(QuizActivity.this, "Cannot go back", Toast.LENGTH_SHORT).show();
                } else {
                    notAnsweredCount --;
                    currentIndex = currentIndex - 1;
                    int question = questionBank[currentIndex].getQuestion();
                    questionNumberDisplay.setText("Question: " + String.valueOf(currentIndex + 1) + "/" +
                            String.valueOf(questionBank.length));
                    questionTV.setText(question);
                }
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra("currentQuestion", currentIndex);
                intent.putExtra("answer", questionBank[currentIndex].isAnswer());
                startActivityForResult(intent, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("Getting Result");
        if (requestCode == 123 && resultCode == RESULT_OK) {
            cheated = data.getBooleanExtra("cheated", false);
            System.out.println(cheated);
        }
    }

    private Intent sendInfoToEndGameScreen(String name, int correct, int incorrect, int cheated, int skipped, int quizLength) {
        Intent intent = new Intent(QuizActivity.this, EndGameActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("correct", correct);
        intent.putExtra("incorrect", incorrect);
        intent.putExtra("notAnswered", skipped);
        intent.putExtra("cheated", cheated);
        intent.putExtra("totalQuestionCount", quizLength);
        return intent;
    }
}
