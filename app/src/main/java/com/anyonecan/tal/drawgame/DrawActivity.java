package com.anyonecan.tal.drawgame;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    DrawingView drawView;
    Bitmap b;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = (DrawingView) findViewById(R.id.drawing);
        sendButton = (Button) findViewById(R.id.btn_send);
        sendButton.setOnClickListener(this);


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
            }
            catch (IOException e) {}

            TessBaseAPI baseApi = new TessBaseAPI();

            // DATA_PATH = Path to the storage
            // lang = for which the language data exists, usually "eng"
            baseApi.setDebug(true);
            baseApi.init(DATA_PATH, "eng");
//            // Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
            baseApi.setImage(b);
            String recognizedText = baseApi.getUTF8Text();
            baseApi.end();


            if ( "eng".equalsIgnoreCase("eng") ) {
                recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
            }

            recognizedText = recognizedText.trim();

            Toast.makeText(getApplicationContext(),recognizedText, Toast.LENGTH_LONG).show();
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
        path = Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files/"+"AnyOneCan_"+ timeStamp +".png";
        return mediaFile;
    }

}

