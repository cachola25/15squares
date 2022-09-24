package com.example.a15squares;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Random;

public class MyViewController implements View.OnClickListener{

    private ArrayList<Button> buttons;
    private int toSwap;
    private boolean toggle;

    public MyViewController(ArrayList<Button> _buttons){
        buttons = _buttons;
        toggle = true;
        setButtons();
    }

    public void setButtons() {
        int textNum = 0;
        int index = 0;
        Random rand = new Random();
        int random = rand.nextInt(16) + 1;
        ArrayList<Integer> nums = new ArrayList<>();

        int loop = 0;
        while(!listFull()) {
            if(!nums.contains(random)) {
                if (loop == 15) {
                    break;
                }
                buttons.get(loop).setText(""+random);
                loop++;
                index++;
                nums.add(random);
            }else{
                while(nums.contains(random)) {
                    random = rand.nextInt(16) + 1;
                }
            }
        }
        buttons.get(index).setText(" ");
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
            if(button.getText() == "Button"){return false;}
        }
        return true;
    }
}
