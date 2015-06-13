package com.anyonecan.tal.drawgame;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawActivity extends ActionBarActivity implements View.OnClickListener {
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/DrawGame/";
    Button sendButton;
    Button reloadButton;
    Button resetButton;
    DrawingView drawView;
    Bitmap b;
    String path;
    ImageView  number;

    //boolean continueMusic;
    String numberToCheck;
    MediaPlayer mp;
    MediaPlayer kidsYay;
    MediaPlayer rightAnswerMp;
    MediaPlayer tryAgainPlayer;

    ScaleOnClick sendButtonAnim;
    ScaleOnClick reloadButtonAnim;
    ScaleOnClick resetButtonAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = (DrawingView) findViewById(R.id.drawing);
        sendButton = (Button) findViewById(R.id.btn_send);
        reloadButton = (Button) findViewById(R.id.btn_reload);
        resetButton = (Button) findViewById(R.id.btn_reset);
        number = (ImageView) findViewById(R.id.number_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            numberToCheck = extras.getString("intent_var");

            if (numberToCheck.equals("1")) {
                number.setImageResource(R.drawable.shade_one);
                mp = MediaPlayer.create(this, R.raw.draw1);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis1);

            }
            else if (numberToCheck.equals("2")) {
                number.setImageResource(R.drawable.shade_two);
                mp = MediaPlayer.create(this, R.raw.draw2);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis2);

            }
            else if (numberToCheck.equals("3")) {
                number.setImageResource(R.drawable.shade_three);
                mp = MediaPlayer.create(this, R.raw.draw3);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis3);

            }
            else if (numberToCheck.equals("4")) {
                number.setImageResource(R.drawable.shade_four);
                mp = MediaPlayer.create(this, R.raw.draw4);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis4);

            }
            else if (numberToCheck.equals("5")) {
                number.setImageResource(R.drawable.shade_five);
                mp = MediaPlayer.create(this, R.raw.draw5);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis5);

            }
            else if (numberToCheck.equals("6")) {
                number.setImageResource(R.drawable.shade_six);
                mp = MediaPlayer.create(this, R.raw.draw6);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis6);

            }
            else if (numberToCheck.equals("7")) {
                number.setImageResource(R.drawable.shade_seven);
                mp = MediaPlayer.create(this, R.raw.draw7);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis7);

            }
            else if (numberToCheck.equals("8")) {
                number.setImageResource(R.drawable.shade_eight);
                mp = MediaPlayer.create(this, R.raw.draw8);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis8);

            }
            else if (numberToCheck.equals("9")) {
                number.setImageResource(R.drawable.shade_nine);
                mp = MediaPlayer.create(this, R.raw.draw9);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis9);

            }
            else {
                number.setImageResource(R.drawable.shade_ten);
                mp = MediaPlayer.create(this, R.raw.draw10);
                mp.start();
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis10);

            }
        }

        sendButton.setOnClickListener(this);
        reloadButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        tryAgainPlayer = MediaPlayer.create(this, R.raw.tryagain);
        kidsYay = MediaPlayer.create(this,R.raw.yay);
        kidsYay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                rightAnswerMp.start();
                Toast.makeText(getApplicationContext(),"This is number " + numberToCheck + "!\n" + "     Good Job!", Toast.LENGTH_LONG).show();
            }
        });

        sendButtonAnim = new ScaleOnClick(sendButton);
        reloadButtonAnim = new ScaleOnClick(reloadButton);
        resetButtonAnim = new ScaleOnClick(resetButton);

        loadTrainDataFile();

    }

    public void onClick(View view) {

        if (view.getId() == R.id.btn_send) {

            sendButtonAnim.click();

            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                Log.d("AnyOneCan","Error creating media file, check storage permissions: ");
                Toast.makeText(getApplicationContext(),"Error creating media file, check storage permissions: ",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                number.setVisibility(View.INVISIBLE);
                FileOutputStream fos = new FileOutputStream(pictureFile);
                drawView.setDrawingCacheEnabled(true);
                drawView.buildDrawingCache(true);
                b = drawView.getDrawingCache();
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();

            } catch (FileNotFoundException e) {
                Log.d("AnyOneCan", "File not found: " + e.getMessage());
                Toast.makeText(getApplicationContext(),"File not found: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.d("AnyOneCan", "Error accessing file: " + e.getMessage());
                Toast.makeText(getApplicationContext(),"Error accessing file:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            ////////////////////////////////////

            try{
                ExifInterface exif = new ExifInterface(path);
                int exifOrientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                int rotate = 0;

                switch (exifOrientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                }

                if (rotate != 0) {
                    int w = b.getWidth();
                    int h = b.getHeight();

                    // Setting pre rotate
                    Matrix mtx = new Matrix();
                    mtx.preRotate(rotate);

                    // Rotating Bitmap & convert to ARGB_8888, required by tess
                    b = Bitmap.createBitmap(b, 0, 0, w, h, mtx, false);
                }
                b = b.copy(Bitmap.Config.ARGB_8888, true);
                drawView.setDrawingCacheEnabled(false);
            }
            catch (IOException e) {}

            TessBaseAPI baseApi = new TessBaseAPI();

            baseApi.setDebug(false);
            baseApi.init(DATA_PATH, "eng");
            baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");
            baseApi.setImage(b);
            String recognizedText = baseApi.getUTF8Text();
            baseApi.end();

            if (recognizedText.equals(numberToCheck)) {
                kidsYay.start();
                //Toast.makeText(getApplicationContext(),"This is number " + numberToCheck + "!\n" + "     Good Job!", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);
                //finish();
            }

            else {
                number.setVisibility(View.VISIBLE);
                drawView.startNew();
                Toast.makeText(getApplicationContext(),"Try Again...", Toast.LENGTH_LONG).show();
                tryAgainPlayer.start();
            }

            pictureFile.delete();
        }

        if (view.getId() == R.id.btn_reload) {
            reloadButtonAnim.click();
            mp.start();
        }

        if (view.getId() == R.id.btn_reset) {
            resetButtonAnim.click();
            number.setVisibility(View.VISIBLE);
            drawView.startNew();
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
//        MusicManager.start(this, MusicManager.MUSIC_GAME);
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch(keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                continueMusic = false;
//                break;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


    /**
      * Create a File for saving an image or video
      */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="AnyOneCan_"+ timeStamp +".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        path = Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files/"+"AnyOneCan_"+ timeStamp +".png";
        return mediaFile;
    }

    /**
     * Load the train data file for the OCR algorithm.
     * If the file already on the storage - does nothing.
     */
    private void loadTrainDataFile() {
        String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
        for (String p : paths){
            File dir = new File(p);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v("AnyOneCan", "ERROR: Creation of directory " + p + " on sdcard failed");
                    return;
                } else {
                    Log.v("AnyOneCan", "Created directory " + p + " on sdcard");
                }
            }
        }

        if (!(new File(DATA_PATH + "tessdata/eng.traineddata"))
                .exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("eng.traineddata");
                // GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/eng.traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                // while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                // gin.close();
                out.close();

                Log.d("AnyOneCan", "Copied eng.traineddata");
            } catch (IOException e) {
                Log.d("AnyOneCan",
                        "Was unable to copy eng.traineddata "
                                + e.toString());
            }
        }
    }

}

