package com.example.a15squares;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Jayven Cachola
 * September 29 2022
 * Enhancements: Only displays a solvable board
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button10 = findViewById(R.id.button10);
        Button button11 = findViewById(R.id.button11);
        Button button12 = findViewById(R.id.button12);
        Button button13 = findViewById(R.id.button13);
        Button button14 = findViewById(R.id.button14);
        Button button15 = findViewById(R.id.button15);
        Button button16 = findViewById(R.id.button16);
        Button resetButton = findViewById(R.id.resetButton);

        ArrayList<Button> buttons = new ArrayList<>();
        //add buttons to an arraylist
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        buttons.add(button10);
        buttons.add(button11);
        buttons.add(button12);
        buttons.add(button13);
        buttons.add(button14);
        buttons.add(button15);
        buttons.add(button16);
        buttons.trimToSize();

        for(int i = 0; i <  buttons.size(); i++) {
            buttons.get(i).setBackgroundColor(Color.BLACK);
            buttons.get(i).setTextColor(Color.WHITE);
            buttons.get(i).setTextSize(30);
        }
        MyViewController controller = new MyViewController(buttons,resetButton);
        //set buttons to controller
        for(int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setOnClickListener(controller);
        }
        resetButton.setOnClickListener(controller);
        TextView displayWin = findViewById(R.id.displayWin);
        controller.setDisplayWin(displayWin);
    }
}