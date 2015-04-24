package com.cyo.ex7_2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {
    private Button btn_save, btn_clear;
    private EditText et_content;
    private final String FILE_NAME = "note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        readFile();

    }
    private void findViews(){
        et_content = (EditText)findViewById(R.id.et_content);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_clear = (Button)findViewById(R.id.btn_clear);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveFile();

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_content.setText("");
            }
        });
    }
    private void saveFile(){
        BufferedWriter bw = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(et_content.getText().toString());
        } catch (IOException ie) {
            Log.e("MainActivity", ie.toString());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ie) {
                    Log.e("MainActivity", ie.toString());
                }
            }
        }

    }


    private void readFile(){
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            br = new BufferedReader(new InputStreamReader(fis));
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
                sb.append("\n");
            }
        } catch (IOException ie) {
            Log.e("MainActivity", ie.toString());
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException ie) {
                    Log.e("MainActivity", ie.toString());
                }
            }
        }
        et_content.setText(sb);
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveFile();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
