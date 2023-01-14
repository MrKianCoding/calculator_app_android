package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity {

    TextView textViewSolution, textViewResult;
    Button button_ac, button_c, button_percent, button_dot;
    Button button_divide, button_multiply, button_plus, button_minus, button_equal;
    Button button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSolution = findViewById(R.id.textViewSolution);
        textViewResult = findViewById(R.id.textViewResult);

        assignId(button_ac, R.id.button_ac);
        assignId(button_c, R.id.button_c);
        assignId(button_percent, R.id.button_percent);
        assignId(button_dot, R.id.button_dot);

        assignId(button_divide, R.id.button_divide);
        assignId(button_multiply, R.id.button_multiply);
        assignId(button_plus, R.id.button_plus);
        assignId(button_minus, R.id.button_minus);
        assignId(button_equal, R.id.button_equal);

        assignId(button_0, R.id.button_0);
        assignId(button_1, R.id.button_1);
        assignId(button_2, R.id.button_2);
        assignId(button_3, R.id.button_3);
        assignId(button_4, R.id.button_4);
        assignId(button_5, R.id.button_5);
        assignId(button_6, R.id.button_6);
        assignId(button_7, R.id.button_7);
        assignId(button_8, R.id.button_8);
        assignId(button_9, R.id.button_9);
    }


    void assignId(Button button, int id) {
        button = findViewById(id);
        button.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        String dataToCalculate = textViewSolution.getText().toString();
        if (buttonText.equals("AC")) {
            textViewSolution.setText("");
            textViewResult.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            textViewSolution.setText(textViewResult.getText().toString());
            return;
        }
        if (buttonText.equals("C")) {
            if (!textViewSolution.getText().toString().trim().isEmpty()){
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        textViewSolution.setText(dataToCalculate);

        if (!dataToCalculate.trim().isEmpty()){
            String finalResult = getResult(dataToCalculate);
            if (!finalResult.equals("Error")){
                textViewResult.setText(finalResult);
            }
        } else {
            textViewResult.setText("0");
        }

    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";

        }
    }


}