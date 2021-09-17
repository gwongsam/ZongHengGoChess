package com.example.gwongsam.zonghenggochess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    private Button alter;
    private Button bai;
    private GameView game;
    private Button hei;
    private Button refresh;
    private Button regret;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = (GameView)findViewById(R.id.mGameView);
        regret = (Button)findViewById(R.id.regret);
        regret.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                game.Regret();
            }
        });
        refresh = (Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                game.RestartGame();
            }
        });
        hei = (Button)findViewById(R.id.hei);
        hei.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                game.HeiOnly();
            }
        });
        bai = (Button)findViewById(R.id.bai);
        bai.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                game.BaiOnly();
            }
        });
        alter = (Button)findViewById(R.id.alter);
        alter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                game.AlterOnly();
            }
        });
    }
}
