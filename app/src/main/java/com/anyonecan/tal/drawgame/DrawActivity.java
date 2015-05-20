package com.anyonecan.tal.drawgame;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class DrawActivity extends ActionBarActivity implements View.OnClickListener {

    Button sendButton;
    DrawingView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = (DrawingView) findViewById(R.id.drawing);
        sendButton = (Button) findViewById(R.id.btn_send);
        sendButton.setOnClickListener(this);

        //TessBaseAPI baseApi = new TessBaseAPI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_draw, menu);
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

    public void onClick(View view) {

        if (view.getId() == R.id.btn_send) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                Log.d("AnyOneCan","Error creating media file, check storage permissions: ");
                Toast.makeText(getApplicationContext(),"Error creating media file, check storage permissions: ",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                drawView.setDrawingCacheEnabled(true);
                Bitmap b = drawView.getDrawingCache();
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("AnyOneCan", "File not found: " + e.getMessage());
                Toast.makeText(getApplicationContext(),"File not found: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.d("AnyOneCan", "Error accessing file: " + e.getMessage());
                Toast.makeText(getApplicationContext(),"Error accessing file:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }


    /** Create a File for saving an image or video */
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
        return mediaFile;
    }

}

