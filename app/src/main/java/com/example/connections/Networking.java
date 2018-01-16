package com.example.connections;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class Networking {


    public static ArrayList<?> fetchData(String requestUrl, String type) {
        URL url = createUrl(requestUrl);
        ArrayList<?> connections = null;
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {

        }
        if (type.equals("TCP")) connections = extractTCPConnectionFromJson(jsonResponse);
        else if (type.equals("UDP")) connections = extractUDPConnectionFromJson(jsonResponse);
        return connections;
    }

    private static URL createUrl(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);

        } catch (MalformedURLException e) {
            Log.e("EJ", "Error while creating URL,", e);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("EJ", "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("EJ", "Problem retrieving the earthquake JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return jsonResponse;
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<TCPConnection> extractTCPConnectionFromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        ArrayList<TCPConnection> connections = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < json.length(); i++) {
                JSONObject connection = jsonArray.getJSONObject(i);

                String state = connection.getString("Connection State");
                String localAddress = connection.getString("Local Address");
                String localPort = connection.getString("Local Port");
                String remAddress = connection.getString("Remote Address");
                String remPort = connection.getString("Remote Port");
                connections.add(new TCPConnection(
                        (state.substring(1, state.length() - 1)),
                        localAddress.substring(1, localAddress.length() - 1),
                        localPort.substring(1, localPort.length() - 1),
                        remAddress.substring(1, remAddress.length() - 1),
                        remPort.substring(1, remPort.length() - 1)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return connections;
    }


    private static ArrayList<UDPConnection> extractUDPConnectionFromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        ArrayList<UDPConnection> connections = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < json.length(); i++) {
                JSONObject connection = jsonArray.getJSONObject(i);

                String address = connection.getString("UDP Local Address");
                String port = connection.getString("UDP Local Port");
                connections.add(new UDPConnection(
                        address.substring(1,address.length()-1),
                        port.substring(1,port.length()-1)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return connections;
    }


}
