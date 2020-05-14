package com.example.keszpenzkezeles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "balance.txt";
    EditText addAmount;
    TextView myBalance;
    ImageView addButton;
    ImageView removeButton;

    ImageView cash_20000;
    ImageView cash_10000;
    ImageView cash_5000;
    ImageView cash_2000;
    ImageView cash_1000;
    ImageView cash_500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!fileExists(FILE_NAME)) {
            save(0);
        }

        addAmount = findViewById(R.id.addAmount);

        myBalance = findViewById(R.id.myBalance);
        myBalance.setText(displayNumber(load()));

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation("+", v);
            }
        });

        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation("-", v);
            }
        });

        cash_20000 = findViewById(R.id.cash_20000);
        cash_10000 = findViewById(R.id.cash_10000);
        cash_5000 = findViewById(R.id.cash_5000);
        cash_2000 = findViewById(R.id.cash_2000);
        cash_1000 = findViewById(R.id.cash_1000);
        cash_500 = findViewById(R.id.cash_500);

        cash_20000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtration(20000);
            }
        });
        cash_10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtration(10000);
            }
        });
        cash_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtration(5000);
            }
        });
        cash_2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtration(2000);
            }
        });
        cash_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtration(1000);
            }
        });
        cash_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtration(500);
            }
        });
    }

    public void save(int cash) {
        String text = String.valueOf(cash);
        FileOutputStream output = null;
        try {
            output = openFileOutput(FILE_NAME, MODE_PRIVATE);
            output.write(text.getBytes());
            //inputText.getText().clear();
            if(cash != 0) {
                Toast.makeText(this, "Az új egyenleged: "+ displayNumber(cash) + ".", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int load() {
        FileInputStream input = null;

        try {
            input = openFileInput(FILE_NAME);
            InputStreamReader iReader = new InputStreamReader(input);
            BufferedReader bReader = new BufferedReader(iReader);
            StringBuilder sBuilder = new StringBuilder();

            String text;
            while ((text = bReader.readLine()) != null) {
                sBuilder.append(text);
            }
            String CashString = sBuilder.toString();

            return Integer.parseInt(CashString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return 0;
    }

    public boolean fileExists(String filename) {
        File file = getBaseContext().getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public void addition(int num) {
        int amount = load()+num;
        save(amount);
        myBalance.setText(displayNumber(load()));
        displayNumber(amount);
    }

    public void subtration(int num) {
        int amount = load()-num;
        save(amount);
        myBalance.setText(displayNumber(load()));
        displayNumber(amount);
    }

    public String displayNumber(int num) {
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(num);
        formattedNumber = formattedNumber.replace(",", " ") + " Ft";
        return formattedNumber;
    }

    public void operation(String operator, View v) {
        if(addAmount.getText().length() > 0) {
            switch(operator) {
                case "+":
                    addition(Integer.parseInt(addAmount.getText().toString()));
                    break;
                case "-":
                    subtration(Integer.parseInt(addAmount.getText().toString()));
                    break;
            }
            addAmount.getText().clear();
        } else {
            Toast.makeText(MainActivity.this, "Meg kell adnod egy összeget.", Toast.LENGTH_SHORT).show();
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}