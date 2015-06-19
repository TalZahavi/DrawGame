package com.anyonecan.tal.drawgame;


import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OcrManager {
    public static TessBaseAPI baseApi;
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/DrawGame/";
    public static boolean isInit = false;


    public static void init() {
        baseApi = new TessBaseAPI();
        baseApi.setDebug(false);
        baseApi.init(DATA_PATH, "eng");
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");
        isInit = true;
    }
}
