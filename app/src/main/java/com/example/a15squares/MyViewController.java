package com.example.a15squares;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyViewController implements View.OnClickListener{

    private ArrayList<Button> buttons;
    private ArrayList<Integer> squareNums;
    private Button emptyButton;
    private int toSwap;
    private boolean toggle;
    private Button clickedButton;
    private TextView displayWin;

    public MyViewController(ArrayList<Button> _buttons){
        buttons = _buttons;
        toggle = true;
        //TODO: Make a has empty spot method, if square is next to empty spot then set opacity to 0
        squareNums = new ArrayList<>(16);
        for(int i = 0; i < 15; i++) {
            squareNums.add(i + 1);
        }
        Collections.shuffle(squareNums);
        emptyButton = null;
        clickedButton = null;
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
        if(!hasEmptyNeighbor(v)){
            return;
        }else{
            String temp = String.valueOf(clickedButton.getText());
            clickedButton.setText(" ");
            emptyButton.setText(temp);
        }

        for(int i = 0; i < buttons.size(); i++) {
            if(!inRightPosition(i)) {
               buttons.get(i).setBackgroundColor(Color.GRAY);
            }else{
                buttons.get(i).setBackgroundColor(Color.GREEN);
            }
        }

        if(hasWinner()) {
            displayWin.setVisibility(View.VISIBLE);
        }else{
            displayWin.setVisibility(View.INVISIBLE);
        }
    }

    public boolean hasWinner() {
        for(int i = 0; i < buttons.size() - 1; i++) {
            if(!inRightPosition(i)) {return false;}
        }
        return true;
    }

    public boolean inRightPosition(int index) {
        if(String.valueOf(buttons.get(index).getText()).equals(String.valueOf(index+1))) {return true;}
        return false;
    }

    public boolean hasEmptyNeighbor(View view) {
        Button[][] temp = new Button[4][4];
        int listIndex = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                temp[i][j] = buttons.get(listIndex);
                listIndex++;
            }
        }

        int clickedButtonId = view.getId();
        clickedButton = null;

        int clickedIndex = 0;
        for(; clickedIndex < buttons.size(); clickedIndex++) {
            if(buttons.get(clickedIndex).getId() == clickedButtonId) {
                clickedButton = buttons.get(clickedIndex);
                break;
            }
        }

        boolean flag = false;
        for(int i = 0; i < temp.length; i++) {
            for(int j = 0; j < temp[i].length; j++) {
                if (temp[i][j].equals(clickedButton)) {
                    if(isValid(i+1,j,temp.length,temp[i].length)) {
                        if(String.valueOf(temp[i+1][j].getText()).equals(" ")){
                            emptyButton = temp[i+1][j];
                            return true;
                        }
                    }
                    if(isValid(i-1,j,temp.length,temp[i].length)) {
                        if(String.valueOf(temp[i-1][j].getText()).equals(" ")){
                            emptyButton = temp[i-1][j];
                            return true;
                        }
                    }
                    if(isValid(i,j+1,temp.length,temp[i].length)) {
                        if(String.valueOf(temp[i][j+1].getText()).equals(" ")){
                            emptyButton = temp[i][j+1];
                            return true;
                        }
                    }
                    if(isValid(i,j-1,temp.length,temp[i].length)) {
                        if(String.valueOf(temp[i][j-1].getText()).equals(" ")){
                            emptyButton = temp[i][j-1];
                            return true;
                        }
                    }
                }
            }
            if(flag){break;}
        }
        //find id of adjacent buttons
        return false;
    }

    public boolean isValid(int row, int col, int rowLength, int colLength) {
        if(row < 0 || row >= rowLength || col < 0 || col >= colLength){return false;}
        return true;
    }

    public void setDisplayWin(TextView view) {displayWin = view;}
}
