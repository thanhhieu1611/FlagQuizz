package com.example.flagquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameInstruction extends AppCompatActivity {

    //Global object
    Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_instruction);
        HideActionBar();

        //Find objects
        closeBtn = findViewById(R.id.btnCloseInstruction);

        // Close button click event
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameInstruction.this, MainActivity.class));
            }
        });
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}