package com.o2.image.model;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by O2 on 6/9/2017.
 */
public class SwipeModel extends AppCompatActivity {
    private static final String ALL_IMAGES_URL = "http://bleepsclub.com/api/getTestimonial";
    private static final String IMAGE_URL = "http://bleepsclub.com/images/testimonials/";
    private static final String JSON_DATA_ARRAY ="data";
    public static List<String> datas = new ArrayList<>();
    private static JSONArray jsonArray;
    private static String imagesDataJSON;
    private static int totalCount = 0;

    public static List<String> getImages(int count) {
        totalCount=count;
        getAllImages(totalCount);
        return datas;
    }

    private static void getAllImages(final int count) {
        class GetAllImages extends AsyncTask<String,Void,String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    if (con == null) {
                        new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    }
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                }catch(Exception e){
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                imagesDataJSON = s;
                try {
                    JSONObject jsonObject = new JSONObject(imagesDataJSON);
                    jsonArray = jsonObject.getJSONArray(JSON_DATA_ARRAY);
                    //totalCount = count > 2 ? totalCount++ : totalCount;
                    datas.clear();
                    for(int i = 0; i < totalCount; i++) {
                        datas.add(IMAGE_URL + jsonArray.get(i).toString());
                    }
//                    for (int i = totalCount; i <totalCount+10; i++) {
//                        datas.add(IMAGE_URL + jsonArray.get(i).toString());
//                    }
                }  catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetAllImages gai = new GetAllImages();
        gai.execute(ALL_IMAGES_URL);
    }

}
