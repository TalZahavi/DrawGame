package com.anyonecan.tal.drawgame;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OcrManager {
    public static TessBaseAPI baseApi;
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/DrawGame/";
    public static boolean isInit = false;


    public static void init(Context myContext) {
        loadTrainDataFile(myContext);
        baseApi = new TessBaseAPI();
        baseApi.setDebug(false);
        baseApi.init(DATA_PATH, "eng");
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");
        isInit = true;
    }

    /**
     * Load the train data file for the OCR algorithm.
     * If the file already on the storage - does nothing.
     */
    private static void loadTrainDataFile(Context myContext) {
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

                AssetManager assetManager = myContext.getAssets();
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
