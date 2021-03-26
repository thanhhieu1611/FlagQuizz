package com.example.flagquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EndGame extends AppCompatActivity {
    // Object declaration
    EditText result;
    ImageView picture;
    Button exitGame;
    Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        //Find objects
        result = findViewById(R.id.txtResult);
        picture = findViewById(R.id.imgResult);
        exitGame = findViewById(R.id.btnExit);
        newGame = findViewById(R.id.btnNewGame);

        //Get data from Main Activity
        Bundle bundle = getIntent().getExtras();
        Integer totalRightAnswer = bundle.getInt("TransferData");

        // Check to show suitable screen based on the total right answer
        if(totalRightAnswer > 6){
            picture.setImageResource(R.drawable.boom);
            result.setText(R.string.congrat);
        }else{
            picture.setImageResource(R.drawable.goodluck);
            result.setText(R.string.encourage);
        }

        // Click event listener to exit game
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Exit current activity
                //Runtime.getRuntime().exit(1);

                //OR
                //finish();
                //System.exit(0);

                //Exit all activities
                finishAffinity();
            }
        });

        // Click event listener to start a new game
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Main activity and reset game
                Intent intent = new Intent(EndGame.this, MainActivity.class);
                intent.putExtra("Reset", 1);
                startActivity(intent);
            }
        });
    }
}