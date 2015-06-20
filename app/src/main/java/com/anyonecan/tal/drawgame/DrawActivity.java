package com.anyonecan.tal.drawgame;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawActivity extends ImmersiveActivity implements View.OnClickListener {
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/DrawGame/";
    Button sendButton;
    Button reloadButton;
    Button resetButton;
    Button goBackButton;

    DrawingView drawView;
    Bitmap b;
    String path;
    ImageView  number;

    String numberToCheck;
    MediaPlayer mp;
    MediaPlayer kidsYay;
    MediaPlayer kidsWrong;
    MediaPlayer drumMp;
    MediaPlayer rightAnswerMp;
    MediaPlayer deleteMp;
    MediaPlayer tryAgainPlayer;

    ScaleOnClick sendButtonAnim;
    ScaleOnClick reloadButtonAnim;
    ScaleOnClick resetButtonAnim;

    //TessBaseAPI baseApi;

    boolean continueMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = (DrawingView) findViewById(R.id.drawing);
        sendButton = (Button) findViewById(R.id.btn_send);
        reloadButton = (Button) findViewById(R.id.btn_reload);
        resetButton = (Button) findViewById(R.id.btn_reset);
        goBackButton = (Button) findViewById(R.id.btn_goBack);
        number = (ImageView) findViewById(R.id.number_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            numberToCheck = extras.getString("intent_var");

            if (numberToCheck.equals("1")) {
                number.setImageResource(R.drawable.shade_one);
                mp = MediaPlayer.create(this, R.raw.draw1);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis1);

            }
            else if (numberToCheck.equals("2")) {
                number.setImageResource(R.drawable.shade_two);
                mp = MediaPlayer.create(this, R.raw.draw2);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis2);

            }
            else if (numberToCheck.equals("3")) {
                number.setImageResource(R.drawable.shade_three);
                mp = MediaPlayer.create(this, R.raw.draw3);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis3);

            }
            else if (numberToCheck.equals("4")) {
                number.setImageResource(R.drawable.shade_four);
                mp = MediaPlayer.create(this, R.raw.draw4);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis4);

            }
            else if (numberToCheck.equals("5")) {
                number.setImageResource(R.drawable.shade_five);
                mp = MediaPlayer.create(this, R.raw.draw5);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis5);

            }
            else if (numberToCheck.equals("6")) {
                number.setImageResource(R.drawable.shade_six);
                mp = MediaPlayer.create(this, R.raw.draw6);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis6);

            }
            else if (numberToCheck.equals("7")) {
                number.setImageResource(R.drawable.shade_seven);
                mp = MediaPlayer.create(this, R.raw.draw7);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis7);

            }
            else if (numberToCheck.equals("8")) {
                number.setImageResource(R.drawable.shade_eight);
                mp = MediaPlayer.create(this, R.raw.draw8);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis8);

            }
            else if (numberToCheck.equals("9")) {
                number.setImageResource(R.drawable.shade_nine);
                mp = MediaPlayer.create(this, R.raw.draw9);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis9);

            }
            else {
                number.setImageResource(R.drawable.shade_ten);
                mp = MediaPlayer.create(this, R.raw.draw10);
                rightAnswerMp = MediaPlayer.create(this,R.raw.thisis10);

            }
        }

        number.setVisibility(View.INVISIBLE);

        sendButton.setOnClickListener(this);
        reloadButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        goBackButton.setOnClickListener(this);

        tryAgainPlayer = MediaPlayer.create(this, R.raw.tryagain);
        kidsWrong = MediaPlayer.create(this,R.raw.boing_x);
        deleteMp = MediaPlayer.create(this,R.raw.golf_swing);
        drumMp = MediaPlayer.create(this,R.raw.drum_roll_y);
        kidsYay = MediaPlayer.create(this,R.raw.yay);
        kidsYay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                rightAnswerMp.start();
                Toast toast = Toast.makeText(getApplicationContext(),"This is number " + numberToCheck + "!\n" + "     Good Job!", Toast.LENGTH_LONG);
                LinearLayout linearLayout = (LinearLayout) toast.getView();
                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                messageTextView.setTextSize(25);
                toast.show();
            }
        });

        sendButtonAnim = new ScaleOnClick(sendButton);
        reloadButtonAnim = new ScaleOnClick(reloadButton);
        resetButtonAnim = new ScaleOnClick(resetButton);


        rightAnswerMp.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                MusicManager.updateVolume(1);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                //OcrManager.baseApi.end();
                finish();
            }} );

//        loadTrainDataFile();
//        baseApi = new TessBaseAPI();
//        baseApi.setDebug(false);
//        baseApi.init(DATA_PATH, "eng");
//        baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");

        MusicManager.updateVolume(0.2f);
        mp.start();

        tryAgainPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sendButton.setClickable(true);
                reloadButton.setClickable(true);
                resetButton.setClickable(true);
                goBackButton.setClickable(true);
                drawView.setDisabled(false);
                MusicManager.updateVolume(1);
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sendButton.setClickable(true);
                reloadButton.setClickable(true);
                resetButton.setClickable(true);
                goBackButton.setClickable(true);
                drawView.setDisabled(false);
                MusicManager.updateVolume(1);
            }
        });

    }

    public void onClick(View view) {

        if (view.getId() == R.id.btn_send) {
            sendButtonAnim.click(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    drawView.setDisabled(true);
                    sendButton.setClickable(false);
                    reloadButton.setClickable(false);
                    resetButton.setClickable(false);
                    goBackButton.setClickable(false);
                    MusicManager.updateVolume(0.2f);
                    drumMp.start();
                }

                @Override
                public void onAnimationEnd(Animation animation) {

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

                    OcrManager.baseApi.setImage(b);
                    String recognizedText = OcrManager.baseApi.getUTF8Text();
                    //baseApi.end();

                    if (recognizedText.equals(numberToCheck)) {
                        new ParticleSystem(DrawActivity.this, 200, R.drawable.star_pink, 10000)
                                .setSpeedRange(0.2f, 0.5f)
                                .oneShot(drawView, 200);
                        kidsYay.start();
                    }

                    else {
                        number.setVisibility(View.VISIBLE);
                        kidsWrong.start();
                        drawView.startNew();
                        Toast toast = Toast.makeText(getApplicationContext(),"Try Again...", Toast.LENGTH_LONG);
                        LinearLayout linearLayout = (LinearLayout) toast.getView();
                        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                        messageTextView.setTextSize(25);
                        toast.show();
                        tryAgainPlayer.start();
                    }

                    pictureFile.delete();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        if (view.getId() == R.id.btn_reload) {
            drawView.setDisabled(true);
            sendButton.setClickable(false);
            reloadButton.setClickable(false);
            resetButton.setClickable(false);
            goBackButton.setClickable(false);
            reloadButtonAnim.click();
            MusicManager.updateVolume(0.2f);
            mp.start();
        }

        if (view.getId() == R.id.btn_reset) {
            resetButtonAnim.click(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    drawView.setDisabled(true);
                    sendButton.setClickable(false);
                    reloadButton.setClickable(false);
                    resetButton.setClickable(false);
                    goBackButton.setClickable(false);
                    deleteMp.start();
                    number.setVisibility(View.VISIBLE);
                    drawView.startNew();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    sendButton.setClickable(true);
                    reloadButton.setClickable(true);
                    resetButton.setClickable(true);
                    goBackButton.setClickable(true);
                    drawView.setDisabled(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        if (view.getId() == R.id.btn_goBack) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            //OcrManager.baseApi.end();
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        continueMusic = true;
        return super.onKeyDown(keyCode, event);
    }

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
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);
    }

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

