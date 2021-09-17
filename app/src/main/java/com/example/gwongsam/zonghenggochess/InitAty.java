/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.example.gwongsam.zonghenggochess;

import android.app.Activity;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class InitAty extends Activity {
    long lastBackPressed;
    
    public InitAty() {
        lastBackPressed = 0;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);
    }
    
    public void onButtonClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    public void onButtonClick2(View v) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if((currentTime - lastBackPressed) < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "\u518d\u6309\u4e00\u6b21\u9000\u51fa\u7a0b\u5e8f", 0x0).show();
        }
        lastBackPressed = currentTime;
    }
}