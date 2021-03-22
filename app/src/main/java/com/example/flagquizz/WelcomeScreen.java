package com.example.flagquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.Timer;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        HideActionBar();
        try{
            //Need to adjust time here before submit
            WaitSeconds(1);
        }catch(Exception exception){
            Toast.makeText(WelcomeScreen.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    //Wait for a couple seconds
    private void WaitSeconds(int sc){
        CountDownTimer countDownTimer = new CountDownTimer(sc * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(WelcomeScreen.this, GameInstruction.class));
            }
        }.start();

    }
}