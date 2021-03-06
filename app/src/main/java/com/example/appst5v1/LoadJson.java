package com.example.appst5v1;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

public class LoadJson {

    public static JSONObject[] getJsonArrayFromUrl(String url) {
        String js = getStringFromUrl(url);
        if(js==null || js.equals("")) return null;
        js = js.substring(1,js.length()-1);
        
        String[] tab = js.split("[}]," );
        
        JSONObject[] res = new JSONObject[tab.length];
         
        
        for(int i = 0;i<res.length;i++){
            try {
                if (i != res.length) {
                    res[i] = new JSONObject(tab[i] + "}");
                } else {
                    res[i] = new JSONObject(tab[i]);
                }
            }catch(JSONException e){
                e.printStackTrace();
                res[i] = null;
            }
        }
        return res;
    }

    public static String getStringFromUrl(String url) {
        return loadJson(url);
    }


    public static String loadJson(String url){
            final AsyncTask<Void, Void, String> mTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                try {
                    return getJsonFromServer(url);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }
        };


        try {
            return mTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        String finalResult = "";
        for(String result = inputStream.readLine();result !=null; result = inputStream.readLine()){
            finalResult+=result;
        }

        return finalResult;
    }
}
