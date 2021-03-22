package com.example.flagquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class EndGame extends AppCompatActivity {
    // Object declaration
    EditText result;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        //Find objects
        result = findViewById(R.id.txtResult);
        picture = findViewById(R.id.imgResult);

        //Get data from Main Activity
        Bundle bundle = getIntent().getExtras();
        Integer totalRightAnswer = bundle.getInt("TransferData");

        if(totalRightAnswer > 6){
            picture.setImageResource(R.drawable.boom);
            result.setText(R.string.congrat);
        }else{
            picture.setImageResource(R.drawable.goodluck);
            result.setText(R.string.encourage);
        }


    }
}