package com.anyonecan.tal.drawgame;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ImmersiveActivity implements View.OnClickListener {

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

    Button musicButton;
    Button goBackButton;

    Rect rect;

    boolean continueMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        card1 = (Button) findViewById(R.id.btn_card1);
        card1.setOnTouchListener(listener("1"));
        card2 = (Button) findViewById(R.id.btn_card2);
        card2.setOnTouchListener(listener("2"));
        card3 = (Button) findViewById(R.id.btn_card3);
        card3.setOnTouchListener(listener("3"));
        card4 = (Button) findViewById(R.id.btn_card4);
        card4.setOnTouchListener(listener("4"));
        card5 = (Button) findViewById(R.id.btn_card5);
        card5.setOnTouchListener(listener("5"));
        card6 = (Button) findViewById(R.id.btn_card6);
        card6.setOnTouchListener(listener("6"));
        card7 = (Button) findViewById(R.id.btn_card7);
        card7.setOnTouchListener(listener("7"));
        card8 = (Button) findViewById(R.id.btn_card8);
        card8.setOnTouchListener(listener("8"));
        card9 = (Button) findViewById(R.id.btn_card9);
        card9.setOnTouchListener(listener("9"));
        card10 = (Button) findViewById(R.id.btn_card10);
        card10.setOnTouchListener(listener("10"));

        musicButton = (Button) findViewById(R.id.play);
        goBackButton = (Button) findViewById(R.id.back);

        musicButton.setOnClickListener(this);
        goBackButton.setOnClickListener(this);

//        card1.setOnClickListener(this);
//        card2.setOnClickListener(this);
//        card3.setOnClickListener(this);
//        card4.setOnClickListener(this);
//        card5.setOnClickListener(this);
//        card6.setOnClickListener(this);
//        card7.setOnClickListener(this);
//        card8.setOnClickListener(this);
//        card9.setOnClickListener(this);
//        card10.setOnClickListener(this);

        if (!OcrManager.isInit) {
            OcrManager.init(this);
        }

    }

    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            OcrManager.baseApi.end();
            finish();
        }

        if (view.getId() == R.id.play) {
            if (!MusicManager.musicStopByMe) {
                if (!continueMusic) {
                    MusicManager.pause();
                    MusicManager.musicStopByMe = true;
                    musicButton.setBackgroundResource(R.drawable.play);
                }
            }
            else {
                continueMusic = false;
                MusicManager.start(this, MusicManager.MUSIC_GAME);
                MusicManager.musicStopByMe = false;
                musicButton.setBackgroundResource(R.drawable.pause);
            }
        }
    }

//    public void onClick(View view) {
//        Intent intent = new Intent(getApplicationContext(),DrawActivity.class);
//        if (view.getId() == R.id.btn_card1) {
//            intent.putExtra("intent_var","1");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card2) {
//            intent.putExtra("intent_var","2");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card3) {
//            intent.putExtra("intent_var","3");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card4) {
//            intent.putExtra("intent_var","4");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card5) {
//            intent.putExtra("intent_var", "5");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card6) {
//            intent.putExtra("intent_var","6");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card7) {
//            intent.putExtra("intent_var","7");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card8) {
//            intent.putExtra("intent_var","8");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card9) {
//            intent.putExtra("intent_var","9");
//            startActivity(intent);
//            //finish();
//        }
//        if (view.getId() == R.id.btn_card10) {
//            intent.putExtra("intent_var", "10");
//            startActivity(intent);
//            //finish();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!MusicManager.musicStopByMe) {
            continueMusic = false;
            MusicManager.start(this, MusicManager.MUSIC_GAME);
        }

        if (MusicManager.musicStopByMe) {
            musicButton.setBackgroundResource(R.drawable.play);
        }
        else {
            musicButton.setBackgroundResource(R.drawable.pause);
        }

    }

    public View.OnTouchListener listener(String number) {
        final String num = number;
    View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            int x=0;
            int y=0;
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    view.setScaleX(0.8f);
                    view.setScaleY(0.8f);
                    x = view.getLeft();
                    y = view.getTop();
                    rect = new Rect(x,y,x+view.getWidth(),y+view.getHeight());
                    return false;

                case MotionEvent.ACTION_UP:
                    view.setScaleX(1f);
                    view.setScaleY(1f);
                    if(rect.contains(x + (int) event.getX(), y + (int) event.getY())) {
                        Intent intent = new Intent(getApplicationContext(),DrawActivity.class);
                        intent.putExtra("intent_var",num);
                        startActivity(intent);
                        break;
                    }
                    return true;

                case MotionEvent.ACTION_MOVE:
                    if(!rect.contains(x + (int) event.getX(), y + (int) event.getY())){
                        view.setScaleX(1f);
                        view.setScaleY(1f);
                        return true;
                    }

                case MotionEvent.ACTION_CANCEL:
                    if(!rect.contains(x + (int) event.getX(), y + (int) event.getY())) {
                        view.setScaleX(1f);
                        view.setScaleY(1f);
                        return true;
                    }
            }
            return false;
        }
    };
    return listener;
}

    /**TODO:
     * Dont forget to clean the BaseAPI after going back from this screen!!
     */

}
