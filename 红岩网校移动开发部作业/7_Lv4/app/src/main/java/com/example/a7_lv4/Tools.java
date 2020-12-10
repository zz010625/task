package com.example.a7_lv4;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Tools {
    public void sendPostNetRequest(
            String mUrl, HashMap<String, String> data, Handler handler) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(mUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.connect();
                    OutputStream outputStream = connection.getOutputStream();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Map.Entry<String, String> entry :
                            data.entrySet()) {
                        stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

                    }
                    outputStream.write(stringBuilder.substring(0, stringBuilder.length() - 1).getBytes());
                    InputStream inputStream = connection.getInputStream();
                    String json = StreamToJSON(inputStream);
                    JSONObject jsonObject = new JSONObject(json);
                    Message message = handler.obtainMessage();
                    message.obj = jsonObject;
                    handler.sendMessage(message);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public String StreamToJSON(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
