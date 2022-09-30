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
    private Button clickedButton;
    private TextView displayWin;
    private Button resetButton;

    public MyViewController(ArrayList<Button> _buttons, Button _resetButton){
        buttons = _buttons;
        squareNums = new ArrayList<>(16);
        emptyButton = null;
        clickedButton = null;
        resetButton = _resetButton;
        for(int i = 0; i < 15; i++) {
            squareNums.add(i + 1);
        }
        Collections.shuffle(squareNums);
        //keep shuffling until a solvable board is reached
        while(!winnableBoard()) {
            Collections.shuffle(squareNums);
        }
        setButtons();
    }

    public void setButtons() {
        Random rand = new Random();
        squareNums.trimToSize();
        buttons.trimToSize();
        int randIndex = rand.nextInt(buttons.size());
        int squareIndex = 0;
        //initialize buttons with numbers and leaving one blank
        for(int i = 0; i < buttons.size(); i++) {
            if(i == randIndex) {
                buttons.get(i).setText(" ");
            }else{
                buttons.get(i).setText(""+squareNums.get(squareIndex));
                squareIndex++;
            }
        }
        //check if some buttons are already in the right spot
        for(int i = 0; i < buttons.size(); i++) {
            if(!inRightPosition(i)) {
                buttons.get(i).setBackgroundColor(Color.BLACK);
            }else{
                buttons.get(i).setBackgroundColor(0xFFFFA500);
            }
        }
    }

    @Override
    public void onClick(View v) {
        //resets the game by shuffling the board
        if(v.getId() == resetButton.getId()) {
            displayWin.setVisibility(View.INVISIBLE);
            Collections.shuffle(squareNums);
            while(!winnableBoard()) {
                Collections.shuffle(squareNums);
            }
            setButtons();
        }

        if(!hasEmptyNeighbor(v)){
            return;
        }else{
            //Swap the text between the two buttons
            String temp = String.valueOf(clickedButton.getText());
            clickedButton.setText(" ");
            emptyButton.setText(temp);
        }

        //check if they're in the right position after a swap
        for(int i = 0; i < buttons.size(); i++) {
            if(!inRightPosition(i)) {
               buttons.get(i).setBackgroundColor(Color.BLACK);
            }else{
                buttons.get(i).setBackgroundColor(0xFFFFA500);
            }
        }

        //see if user has solved it
        if(hasWinner()) {
            displayWin.setVisibility(View.VISIBLE);
        }else{
            displayWin.setVisibility(View.INVISIBLE);
        }
    }

    /**
     *
     * @return if all buttons are in the right spot, it returns true
     * if not, then it returns false
     */
    public boolean hasWinner() {
        for(int i = 0; i < buttons.size() - 1; i++) {
            if(!inRightPosition(i)) {return false;}
        }
        return true;
    }

    /**
     *
     * @param index the index of the button being looked at
     * @return true if number on button matches the index it is in in the arraylist,
     * returns false otherwise
     */
    public boolean inRightPosition(int index) {
        if(String.valueOf(buttons.get(index).getText()).equals(String.valueOf(index+1))) {return true;}
        return false;
    }

    /**
     *
     * @param view the current view/button being clicked on
     * @return returns true if there is a blank spot next to a button
     * returns false if not
     */
    public boolean hasEmptyNeighbor(View view) {
        Button[][] buttonsArray = new Button[4][4];
        int listIndex = 0;
        //initialize 2d array of the buttons since
        //using an array makes it easier to look at
        //what buttons are adjacent to the clicked button
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                buttonsArray[i][j] = buttons.get(listIndex);
                listIndex++;
            }
        }

        int clickedButtonId = view.getId();
        clickedButton = null;
        int clickedIndex = 0;
        //find the button that has been clicked on
        for(; clickedIndex < buttons.size(); clickedIndex++) {
            if(buttons.get(clickedIndex).getId() == clickedButtonId) {
                clickedButton = buttons.get(clickedIndex);
                break;
            }
        }

        //check if the clicked button has an empty neighbors
        for(int i = 0; i < buttonsArray.length; i++) {
            for(int j = 0; j < buttonsArray[i].length; j++) {
                if (buttonsArray[i][j].equals(clickedButton)) {
                    if(isValid(i+1,j,buttonsArray.length,buttonsArray[i].length)) {
                        if(String.valueOf(buttonsArray[i+1][j].getText()).equals(" ")){
                            emptyButton = buttonsArray[i+1][j];
                            return true;
                        }
                    }
                    if(isValid(i-1,j,buttonsArray.length,buttonsArray[i].length)) {
                        if(String.valueOf(buttonsArray[i-1][j].getText()).equals(" ")){
                            emptyButton = buttonsArray[i-1][j];
                            return true;
                        }
                    }
                    if(isValid(i,j+1,buttonsArray.length,buttonsArray[i].length)) {
                        if(String.valueOf(buttonsArray[i][j+1].getText()).equals(" ")){
                            emptyButton = buttonsArray[i][j+1];
                            return true;
                        }
                    }
                    if(isValid(i,j-1,buttonsArray.length,buttonsArray[i].length)) {
                        if(String.valueOf(buttonsArray[i][j-1].getText()).equals(" ")){
                            emptyButton = buttonsArray[i][j-1];
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @return returns true if the board is in a winnable position
     * returns false if the board is not winnable
     */
    public boolean winnableBoard() {
        setButtons();
        //get text on button and see how many numbers are less than the number on that square
        //do this for every square and get the total count, i.e look at the number on the square
        //and go to the right and see how many squares have a lower value
        int[] buttonVals = new int[buttons.size()];
        int i = 0;
        for(; i < buttonVals.length; i++){
            if(String.valueOf(buttons.get(i).getText()).equals(" ")){continue;}
            buttonVals[i] = Integer.parseInt(String.valueOf(buttons.get(i).getText()));
        }

        //calculate the inversion sum of the current board
        int inversionCount = 0;
        for(int j = 0; j < buttonVals.length; j++) {
            int currNum = buttonVals[j];
            for(int k = j; k < buttonVals.length; k++) {
                if(currNum > buttonVals[k] && buttonVals[k] != 0) {
                    inversionCount++;
                }
            }
        }
        //if inversion sum is even, return true, return false if odd
        return (inversionCount % 2) == 0;
    }

    /**
     *
     * @param row row index being looked at
     * @param col column index being looked at
     * @param rowLength maximum number of rows in the array
     * @param colLength maximum number of columnsin the array
     * @return true if the row and col indexes are in the bounds of the array
     * returns false if the indexes are out of bounds
     */
    public boolean isValid(int row, int col, int rowLength, int colLength) {
        if(row < 0 || row >= rowLength || col < 0 || col >= colLength){return false;}
        return true;
    }

    /**
     *
     * @param view the text to be displayed
     */
    public void setDisplayWin(TextView view) {displayWin = view;}
}
