package com.example.a15squares;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyViewController implements View.OnClickListener{

    private ArrayList<Button> buttons;
    private ArrayList<Integer> squareNums;
    private int toSwap;
    private boolean toggle;

    public MyViewController(ArrayList<Button> _buttons){
        buttons = _buttons;
        toggle = true;
        //TODO: Fix setButtons method to randomly set numbers text
        //TODO: Make a has empty spot method, if square is next to empty spot then set opacity to 0
        //TODO: Initialize square with value 16 but set the text to ' '
        squareNums = new ArrayList<>(16);
        for(int i = 0; i < 15; i++) {
            squareNums.add(i + 1);
        }
        Log.d("shuffled",""+squareNums);
        Collections.shuffle(squareNums);
        Log.d("shuffled",""+squareNums);
        setButtons();
    }

    public void setButtons() {
        Random rand = new Random();
        squareNums.trimToSize();
        buttons.trimToSize();
        int randIndex = rand.nextInt(buttons.size());

        int squareIndex = 0;
        for(int i = 0; i < buttons.size(); i++) {
            if(i == randIndex) {
                buttons.get(i).setText(" ");
            }else{
                buttons.get(i).setText(""+squareNums.get(squareIndex));
                squareIndex++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int index = 0;
        int otherIndex = 0;
        if(toggle) {
            toSwap = v.getId();
        }else if(!toggle) {
            for(int i = 0; i < buttons.size(); i++) {
                if(buttons.get(i).getId() == toSwap) {
                    index = i;
                    break;
                }
            }
            for(int i = 0; i < buttons.size(); i++) {
                if(buttons.get(i).getId() == v.getId()) {
                    otherIndex = i;
                    break;
                }
            }
            if((buttons.get(index).getText() != " ") && (buttons.get(otherIndex).getText() != " ")) {return;}//do nothing
            else {
                String temp = (String) buttons.get(otherIndex).getText();
                buttons.get(otherIndex).setText(buttons.get(index).getText());
                buttons.get(index).setText(temp);

            }
        }
        toggle = !toggle;
    }

    public boolean hasWinner() {
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).getText() != String.valueOf(i)){return false;}
        }
        return true;
    }

    public boolean listFull() {
        for(Button button : buttons) {
            if(button.getText().equals("Button")){return false;}
        }
        return true;
    }
}
