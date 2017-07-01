package com.example.shubham.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    Button startGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        startGame = (Button) findViewById(R.id.startGame);

    }
    public void clickedStart(View view)
    {
        Intent i= new Intent(this, MainActivity.class);

        final EditText playerx = (EditText) findViewById(R.id.playerx);
        final EditText playero = (EditText) findViewById(R.id.playero);
        String xname=playerx.getText().toString();
        String oname=playero.getText().toString();
        i.putExtra("playerx",xname);
        i.putExtra("playero",oname);
        startActivity(i);
    }
}
