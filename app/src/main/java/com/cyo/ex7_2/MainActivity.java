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
    //    要儲存輸入內容的檔案名稱
    private final String FILE_NAME = "note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
//        在Activity onCreate時就先讀取檔案內容
        readFile();

    }

    private void findViews() {
        et_content = (EditText) findViewById(R.id.et_content);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                按下儲存後執行saveFile
                saveFile();

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              add clear按鈕
                et_content.setText("");
            }
        });
    }

//    ------------------------------------儲存檔案的方法---------------------------------------
//    用BufferedWriter去把記憶體的內容寫入到檔案中，再由 FileOutputStream 中繼 BufferedWriter和檔案

    private void saveFile() {
//      先建立一支BufferedWriter的空水管
        BufferedWriter bw = null;
        try {
//          建立File輸出水管 放入 檔案名稱、參數
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//            再將fos和bw做連接
            bw = new BufferedWriter(new OutputStreamWriter(fos));
//            在記憶體到檔案的管子都接妥後，開始用BufferedWriter把檔案輸出
            bw.write(et_content.getText().toString());
//            輸出IO皆要加入IOException，以處理錯誤的情況
        } catch (IOException ie) {
            Log.e("MainActivity", ie.toString());
        } finally {
//            最後一定要記得做關閉管子
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ie) {
//                    Log.e  紀錄error
                    Log.e("MainActivity", ie.toString());
                }
            }
        }

    }
//------------------------------------讀取檔案--------------------------------------

    private void readFile() {
//        建立待會用來存放字串的StringBuilder，比用字串相加的效能好
//        開始建立連接檔案到記憶體的水管 Reader、Input
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
//            連接檔案，用Android內建的方法openFileInput
            FileInputStream fis = openFileInput(FILE_NAME);
            br = new BufferedReader(new InputStreamReader(fis));
            String text;
//            建立字串text暫存BufferedReader的方法readline一次讀取一行
//            直到讀不到東西 null跳出結束
            while ((text = br.readLine()) != null) {
//                每次讀到候用append加到後面
                sb.append(text);
//                讀完一行之後加入換行符號
                sb.append("\n");
            }
//            IOException及close
        } catch (IOException ie) {
            Log.e("MainActivity", ie.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ie) {
                    Log.e("MainActivity", ie.toString());
                }
            }
        }
        et_content.setText(sb);
    }

    //    ------------------------------------切換頁面時儲存檔案----------------------------------
//    利用Activity生命週期，在切換頁面時呼叫onPause方法，執行檔案儲存的動作
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
