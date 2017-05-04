package com.src_one.barcodescanner;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class Repository {
    private String url;
    private HttpURLConnection urlConnection;

    public String submitBarcode(String type, String value) {
        try {
            url = "http://192.168.1.103/api/barcode";
        } catch (Exception e) {
            e.getStackTrace();
        }

        try {
            try {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("type", type);
                jsonObj.put("value", value);

                urlConnection = (HttpURLConnection) Utils.makeRequest("POST", url, null, "application/json", jsonObj.toString());

            } catch (Exception e) {
                return "error";
            }

            InputStream inputStream;

            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            /*
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            String response = "";

            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
            return response;
            */
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
