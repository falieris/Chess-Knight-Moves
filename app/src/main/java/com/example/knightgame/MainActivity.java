package com.example.knightgame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean start = false;
    private boolean end = false;
    private String startSquare;
    private String endSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void squareClick(View v){
        if(!start && !end){
            v.setBackgroundColor(Color.GREEN);
            start = true;
            startSquare = getResources().getResourceEntryName(v.getId());
            TextView instructionsTV = findViewById(R.id.instructions_textview);
            instructionsTV.setText(getString(R.string.set_end_point));
        } else if(start && !end) {
            v.setBackgroundColor(Color.RED);
            end = true;
            endSquare = getResources().getResourceEntryName(v.getId());
            TextView instructionsTV = findViewById(R.id.instructions_textview);
            instructionsTV.setText(getString(R.string.tap_clear));
            printResults(startSquare, endSquare);
        }
        v.setPadding(1,1,1,1);
    }

    public void clearBoard(View v){
        if(end){
            ImageView view = findViewById(getResources().getIdentifier(endSquare, "id", this.getPackageName()));
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setPadding(0,0,0,0);
            end = false;
        }
        if(start){
            ImageView view = findViewById(getResources().getIdentifier(startSquare, "id", this.getPackageName()));
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setPadding(0,0,0,0);
            start = false;
        }

        TextView resultTV = findViewById(R.id.result_textview);
        resultTV.setText("");

        TextView instructionsTV = findViewById(R.id.instructions_textview);
        instructionsTV.setText(getString(R.string.set_start_point));
    }

    private void printResults(String startSquare, String endSquare){
        String result = calculateKnightPaths(startSquare, endSquare);
        if(result.equalsIgnoreCase("")){
            result = getString(R.string.no_result);
        }

        TextView resultTV = findViewById(R.id.result_textview);
        resultTV.setMovementMethod(new ScrollingMovementMethod());
        resultTV.setText(result);
        resultTV.scrollTo(0,1);
        resultTV.scrollTo(0,0);
    }

    private String calculateKnightPaths(String startSquare, String endSquare){

        String firstStep="";
        String secondStep="";
        String thirdStep="";
        String result = "";

        // All possible moves of a knight
        int X[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for(int i=0;i<X.length;i++){
            try {
                firstStep = getSquareFromXY(getSquareX(startSquare) + X[i], getSquareY(startSquare) + Y[i]);
            } catch (Exception e){
                continue;
            }
            for(int j=0;j<X.length;j++){
                try {
                    secondStep = getSquareFromXY(getSquareX(firstStep) + X[j], getSquareY(firstStep) + Y[j]);
                } catch (Exception e){
                    continue;
                }
                for(int k=0;k<X.length;k++){
                    try {
                        thirdStep = getSquareFromXY(getSquareX(secondStep) + X[k], getSquareY(secondStep) + Y[k]);
                    } catch (Exception e) {
                        continue;
                    }
                    if (thirdStep.equalsIgnoreCase(endSquare)){
                        result = result.concat(startSquare+" -> "+firstStep+" -> "+secondStep+" -> "+thirdStep+"\n");
                    }
                }
            }
        }
        return result;
    }

    private int getSquareX(String square) throws Exception{
        Character x = square.charAt(0);
        switch (x){
            case 'A':
                return 1;
            case 'B':
                return 2;
            case 'C':
                return 3;
            case 'D':
                return 4;
            case 'E':
                return 5;
            case 'F':
                return 6;
            case 'G':
                return 7;
            case 'H':
                return 8;
        }
        throw new Exception();
    }

    private int getSquareY(String square) throws Exception{
        if(1 > Character.getNumericValue(square.charAt(1)) || 8 < Character.getNumericValue(square.charAt(1)))
            throw new Exception();
        return Character.getNumericValue(square.charAt(1));
    }

    private String getSquareFromXY(int x, int y) throws Exception{
        if(x<1 || x>8 || y<1 || y>8)
            throw  new Exception();
        String square = "";
        switch (x){
            case 1:
                square="A";
                break;
            case 2:
                square="B";
                break;
            case 3:
                square="C";
                break;
            case 4:
                square="D";
                break;
            case 5:
                square="E";
                break;
            case 6:
                square="F";
                break;
            case 7:
                square="G";
                break;
            case 8:
                square="H";
        }

        square = square.concat(""+y);
        return square;
    }
}
