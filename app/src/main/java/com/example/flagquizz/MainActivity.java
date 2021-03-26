package com.example.flagquizz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Global objects
    ImageView flag;
    EditText question;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    FloatingActionButton next;
    EditText currentQuestion;
    EditText totalAnsweredQuestion;
    EditText totalRightQuestion;
    MediaPlayer playingSound;
    Integer rightAnswerNumber;
    Integer baseAnswerNumber;
    Integer baseFlagNumber;
    Integer numberOfQuestion;
    Integer flagNumber;
    Integer totalRightAnswerNumber;
    Integer totalAnsweredNumber;
    Integer resetGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find objects
        flag = findViewById(R.id.imgFlag);
        question = findViewById(R.id.txtQuestion);
        option1 = findViewById(R.id.rbAnswer1);
        option2 = findViewById(R.id.rbAnswer2);
        option3 = findViewById(R.id.rbAnswer3);
        option4 = findViewById(R.id.rbAnswer4);
        next = findViewById(R.id.flBtnNext);
        currentQuestion = findViewById(R.id.txtQuestionNumber);
        totalAnsweredQuestion = findViewById(R.id.txtAnsweredQuestion);
        totalRightQuestion = findViewById(R.id.txtTotalRightAnswer);
        
        // Declare List of all option object to assign onChange Event Listener
        List<RadioButton> optionLst = new ArrayList<RadioButton>(
                Arrays.asList(option1, option2, option3, option4)
        );

        //Initialize variables
        baseAnswerNumber = option1.getId();
        baseFlagNumber = R.drawable.flag0;
        numberOfQuestion = 10;
        currentQuestion.setText("1");
        totalRightAnswerNumber = 0;
        totalAnsweredNumber = 1;
        flagNumber = 1;
        playingSound = null;
        resetGame = 0;

        //Initialize values for objects
        flag.setTag(R.drawable.flag0);
        totalAnsweredQuestion.setText(totalAnsweredNumber.toString());
        totalRightQuestion.setText(totalRightAnswerNumber.toString());
        UpdateQuiz(1);

        //Get data from EndGame Activity
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            resetGame = bundle.getInt("Reset");
        }

        //Reset if start a new game
        if(resetGame == 1){
            ResetGame();
        }


        //OnCheckedChange Event Listeners for all radio buttons
        for(RadioButton option : optionLst){
            option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        // Get answerNumber: from 1 - 4.
                        Integer answerNumber = option.getId() - baseAnswerNumber + 1;

                        // Get flagNumber: from 1 - 10.
                        flagNumber = (int)flag.getTag() - baseFlagNumber + 1;

                        // Get right Answer number of current flag based on R.Integer: from 1-4
                        rightAnswerNumber = GetRightAnswerNumber(flagNumber);

                        //Check to see if the answer is correct
                        if(answerNumber == rightAnswerNumber){
                            // Play victory sound & show right symbol
                            playingSound = PlayASound(R.raw.correct);
                            //Increase number of right answer
                            totalRightAnswerNumber++;
                            totalRightQuestion.setText(totalRightAnswerNumber.toString());
                        }else{
                            //Play fail sound & show wrong symbol
                            playingSound = PlayASound(R.raw.wrong);
                        }

                        //Set radio buttons unclickable
                        option1.setClickable(false);
                        option2.setClickable(false);
                        option3.setClickable(false);
                        option4.setClickable(false);


                    }
                }
            });
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Release playingSound resource
                playingSound = StopASound(playingSound);

                //Move to next question if available
                if(flagNumber != numberOfQuestion){
                    flagNumber++;
                    MoveToNextQuestion(flagNumber);
                    UpdateQuiz(flagNumber);

                    //Increase number of answered question
                    totalAnsweredNumber++;
                    totalAnsweredQuestion.setText(totalAnsweredNumber.toString());
                }else{
                    //Hide Floating Next button
                    next.setVisibility(View.INVISIBLE);

                    //Show the end game and final result
                    ShowEndGame(totalRightAnswerNumber);
                }

                //UnCheck all radio buttons
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);

                //Set radio buttons clickable
                option1.setClickable(true);
                option2.setClickable(true);
                option3.setClickable(true);
                option4.setClickable(true);
            }
        });
    }

    // Base on flagNumber, get the rightAnswerNumber of R.String
    private Integer GetRightAnswerNumber(int flagNumber){
        Integer tmp = 0;
        switch (flagNumber){
            case 1:
            case 5:
            case 9:
                tmp = getResources().getInteger(R.integer.question1_answer);
                break;
            case 2:
            case 6:
            case 10:
                tmp = getResources().getInteger(R.integer.question2_answer);
                break;
            case 3:
            case 7:
                tmp = getResources().getInteger(R.integer.question3_answer);
                break;
            case 4:
            case 8:
                tmp = getResources().getInteger(R.integer.question4_answer);
                break;
        }
        return tmp;
    }

    private void MoveToNextQuestion(Integer nextFlagNumber){
        //Set flag resource and tag: from flag0-flag9
        currentQuestion.setText(nextFlagNumber.toString());
        switch (nextFlagNumber){
            case 1:
                flag.setImageResource((R.drawable.flag0));
                flag.setTag(R.drawable.flag0);
                break;
            case 2:
                flag.setImageResource((R.drawable.flag1));
                flag.setTag(R.drawable.flag1);
                break;
            case 3:
                flag.setImageResource((R.drawable.flag2));
                flag.setTag(R.drawable.flag2);
                break;
            case 4:
                flag.setImageResource((R.drawable.flag3));
                flag.setTag(R.drawable.flag3);
                break;
            case 5:
                flag.setImageResource((R.drawable.flag4));
                flag.setTag(R.drawable.flag4);
                break;
            case 6:
                flag.setImageResource((R.drawable.flag5));
                flag.setTag(R.drawable.flag5);
                break;
            case 7:
                flag.setImageResource((R.drawable.flag6));
                flag.setTag(R.drawable.flag6);
                break;
            case 8:
                flag.setImageResource((R.drawable.flag7));
                flag.setTag(R.drawable.flag7);
                break;
            case 9:
                flag.setImageResource((R.drawable.flag8));
                flag.setTag(R.drawable.flag8);
                break;
            case 10:
                flag.setImageResource((R.drawable.flag9));
                flag.setTag(R.drawable.flag9);
                break;
        }
    }

    //Show the end game and final result
    private void ShowEndGame(int totalRightAnswerNumber){
        //Setup a timer before showing final result
        WaitSeconds(2, totalRightAnswerNumber);
    }

    //Wait for a couple seconds
    private void WaitSeconds(int sc, int transferData){
        CountDownTimer countDownTimer = new CountDownTimer(sc * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                // Start EndGame activity and send data to the activity
                Intent intent = new Intent(MainActivity.this, EndGame.class);
                intent.putExtra("TransferData", transferData);
                startActivity(intent);
            }
        }.start();

    }

    private void UpdateQuiz(int flagNumber){
        switch (flagNumber){
            case 1:
                question.setText(getText(R.string.question1).toString());
                option1.setText(getText(R.string.answer1_question1).toString());
                option2.setText(getText(R.string.answer2_question1).toString());
                option3.setText(getText(R.string.answer3_question1).toString());
                option4.setText(getText(R.string.answer4_question1).toString());
                break;
            case 2:
                question.setText(getText(R.string.question2).toString());
                option1.setText(getText(R.string.answer1_question2).toString());
                option2.setText(getText(R.string.answer2_question2).toString());
                option3.setText(getText(R.string.answer3_question2).toString());
                option4.setText(getText(R.string.answer4_question2).toString());
                break;
            case 3:
                question.setText(getText(R.string.question3).toString());
                option1.setText(getText(R.string.answer1_question3).toString());
                option2.setText(getText(R.string.answer2_question3).toString());
                option3.setText(getText(R.string.answer3_question3).toString());
                option4.setText(getText(R.string.answer4_question3).toString());
                break;
            case 4:
                question.setText(getText(R.string.question4).toString());
                option1.setText(getText(R.string.answer1_question4).toString());
                option2.setText(getText(R.string.answer2_question4).toString());
                option3.setText(getText(R.string.answer3_question4).toString());
                option4.setText(getText(R.string.answer4_question4).toString());
                break;
            case 5:
                question.setText(getText(R.string.question5).toString());
                option1.setText(getText(R.string.answer1_question5).toString());
                option2.setText(getText(R.string.answer2_question5).toString());
                option3.setText(getText(R.string.answer3_question5).toString());
                option4.setText(getText(R.string.answer4_question5).toString());
                break;
            case 6:
                question.setText(getText(R.string.question6).toString());
                option1.setText(getText(R.string.answer1_question6).toString());
                option2.setText(getText(R.string.answer2_question6).toString());
                option3.setText(getText(R.string.answer3_question6).toString());
                option4.setText(getText(R.string.answer4_question6).toString());
                break;
            case 7:
                question.setText(getText(R.string.question7).toString());
                option1.setText(getText(R.string.answer1_question7).toString());
                option2.setText(getText(R.string.answer2_question7).toString());
                option3.setText(getText(R.string.answer3_question7).toString());
                option4.setText(getText(R.string.answer4_question7).toString());
                break;
            case 8:
                question.setText(getText(R.string.question8).toString());
                option1.setText(getText(R.string.answer1_question8).toString());
                option2.setText(getText(R.string.answer2_question8).toString());
                option3.setText(getText(R.string.answer3_question8).toString());
                option4.setText(getText(R.string.answer4_question8).toString());
                break;
            case 9:
                question.setText(getText(R.string.question9).toString());
                option1.setText(getText(R.string.answer1_question9).toString());
                option2.setText(getText(R.string.answer2_question9).toString());
                option3.setText(getText(R.string.answer3_question9).toString());
                option4.setText(getText(R.string.answer4_question9).toString());
                break;
            case 10:
                question.setText(getText(R.string.question10).toString());
                option1.setText(getText(R.string.answer1_question10).toString());
                option2.setText(getText(R.string.answer2_question10).toString());
                option3.setText(getText(R.string.answer3_question10).toString());
                option4.setText(getText(R.string.answer4_question10).toString());
                break;
        }
    }

    // Play sound and stop sound
    private MediaPlayer PlayASound(int soundValue){
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, soundValue);
        mp.start();
        return mp;
    }
    private MediaPlayer StopASound(MediaPlayer mp){
        if(mp != null){
            mp.stop();
            mp.release();
            mp = null;
        }
        return mp;
    }

    // Reset game when user click new game in the end game activity
    private void ResetGame(){
          totalRightQuestion.setText("0");
          currentQuestion.setText("1");

    }
}