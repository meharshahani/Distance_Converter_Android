package com.example.distance_converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    private TextView history;
    TextView textView2;
    TextView textView3;
    Button button;
    Button clearButton;
    TextView output;
    EditText input;


    private static final String TAG = "MainActivity";
    public static double miles_to_km = 1.60934;
    public static double km_to_miles = 0.621371;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.conversion);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        button = (Button) findViewById(R.id.button);
        clearButton = (Button) findViewById(R.id.clearButton);

        history = findViewById(R.id.history);
        history.setMovementMethod(new ScrollingMovementMethod());


        button.setOnClickListener(new View.OnClickListener()
        {
            String prev_text = history.getText().toString();
            @Override
            public void onClick(View v)
            {

                String text = ((TextView) findViewById(R.id.textView2)).getText().toString();
                int radioId = radioGroup.getCheckedRadioButtonId();

                if(radioId == R.id.kmtomiles)
                {
                    EditText km_input = (EditText)findViewById(R.id.input);
                    TextView miles_output= (TextView)findViewById(R.id.output);

                    if(km_input.getText().toString().equals("-") || km_input.getText().toString().equals(".")){
                        Log.d(TAG, "onClick: text km input after = "+km_input.getText().toString());
                        km_input.setText(" ");
                        Toast.makeText(MainActivity.this, "Invalid input. Please enter a number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d(TAG, "onClick: text km input after = "+km_input.getText().toString());

                    double km_value = Double.parseDouble(km_input.getText().toString());
                    double mile = km_value * km_to_miles;


                    //Log.d(TAG, "kmToMileConvert: Mile: " + mile);

                    String mile_converted = String.format("%.1f", mile);
                    miles_output.setText(mile_converted);

                    String prev_text = history.getText().toString();
                    history.setText("KM to MI: " + km_input.getText().toString() + "=> " + miles_output.getText().toString() + "\n" + prev_text);

                    km_input.setText("");

                }
                else
                {
                    EditText miles_input = (EditText) findViewById(R.id.input);
                    TextView km_output = (TextView) findViewById(R.id.output);

                    if(miles_input.getText().toString().equals("-") || miles_input.getText().toString().equals("."))
                    {
                        Log.d(TAG, "onClick: input after= "+miles_input.getText().toString());
                        miles_input.setText("");
                        Toast.makeText(MainActivity.this, "Invalid input. PLease enter a number", Toast.LENGTH_SHORT).show();
                        return;
                    } 
                    Log.d(TAG, "onClick: input after= "+miles_input.getText().toString());
                    double miles_value = Double.parseDouble(miles_input.getText().toString());
                    double kilometer = miles_value * miles_to_km;

                    //Log.d(TAG, "doConvert: KM = " + kilometer);

                    String kilometer_converted = String.format("%.1f", kilometer);
                    km_output.setText(kilometer_converted);

                    String prev_text = history.getText().toString();
                    history.setText("MI to KM: " + miles_input.getText().toString() + "=> " + km_output.getText().toString() + "\n" + prev_text);
                    miles_input.setText(" ");
                }

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                output = findViewById(R.id.output);
                history.setText(" ");
                output.setText(" ");
            }
        });
    }

    public void checkButton(View v)
    {
        //boolean checked = ((RadioButton)v).isChecked();

        int radioId = radioGroup.getCheckedRadioButtonId();

         switch (radioId)
         {
                case R.id.kmtomiles:
                    textView2.setText("Kilometer value: ");
                    textView3.setText("Mile value: ");

                    //Toast.makeText(this, "KM to Miles clicked", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.milestokm:
                    textView2.setText("Mile value: ");
                    textView3.setText("Kilometer value: ");

                    //Toast.makeText(this, "Miles to KM clicked", Toast.LENGTH_SHORT).show();
                    break;

         }
        radioButton = findViewById(radioId);
        Log.d(TAG, "checkButton:Radio Button clicked : " + radioButton.getText());
    }

    //saves the data in an output state
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString("History", history.getText().toString());

        //Call this last in onSaveInstanceState
        super.onSaveInstanceState(outState);
    }

    //takes the data from the onSaveInstanceState and puts in back in the view
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        //Call super before everything else
        super.onRestoreInstanceState(savedInstanceState);

        history.setText(savedInstanceState.getString("History"));
    }
}