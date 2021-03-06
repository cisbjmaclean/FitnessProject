package com.cis2250.network;

/**
 * Created by Dmitri on 22/01/14.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SyncStatusObserver;
import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP GET Request
    public JSONObject makeHttpRequest(String url) {

        // Making HTTP request
        try {
            // request method is GET
            DefaultHttpClient httpClient = new DefaultHttpClient();

            System.out.println(url);

            HttpGet httpGet = new HttpGet(url);
            System.out.println("1");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("2");
            HttpEntity httpEntity = httpResponse.getEntity();
            System.out.println("3");
            is = httpEntity.getContent();
            System.out.println("4");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try to parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            System.out.println("json="+json);
        } catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data " + e.toString());
            System.out.println(json);

        }
        // return JSON Object
        return jObj;
    }
}