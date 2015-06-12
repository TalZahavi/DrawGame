package com.anyonecan.tal.drawgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button card1;
    Button card2;
    Button card3;
    Button card4;
    Button card5;
    Button card6;
    Button card7;
    Button card8;
    Button card9;
    Button card10;

    boolean continueMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        continueMusic = true;

        card1 = (Button) findViewById(R.id.btn_card1);
        card2 = (Button) findViewById(R.id.btn_card2);
        card3 = (Button) findViewById(R.id.btn_card3);
        card4 = (Button) findViewById(R.id.btn_card4);
        card5 = (Button) findViewById(R.id.btn_card5);
        card6 = (Button) findViewById(R.id.btn_card6);
        card7 = (Button) findViewById(R.id.btn_card7);
        card8 = (Button) findViewById(R.id.btn_card8);
        card9 = (Button) findViewById(R.id.btn_card9);
        card10 = (Button) findViewById(R.id.btn_card10);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
        card7.setOnClickListener(this);
        card8.setOnClickListener(this);
        card9.setOnClickListener(this);
        card10.setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),DrawActivity.class);
        if (view.getId() == R.id.btn_card1) {
            intent.putExtra("intent_var","1");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card2) {
            intent.putExtra("intent_var","2");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card3) {
            intent.putExtra("intent_var","3");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card4) {
            intent.putExtra("intent_var","4");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card5) {
            intent.putExtra("intent_var","5");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card6) {
            intent.putExtra("intent_var","6");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card7) {
            intent.putExtra("intent_var","7");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card8) {
            intent.putExtra("intent_var","8");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card9) {
            intent.putExtra("intent_var","9");
            startActivity(intent);
            //finish();
        }
        if (view.getId() == R.id.btn_card10) {
            intent.putExtra("intent_var", "10");
            startActivity(intent);
            //finish();
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (!continueMusic) {
//            MusicManager.pause();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        continueMusic = false;
//        MusicManager.start(this, MusicManager.MUSIC_MENU);
//    }

}
