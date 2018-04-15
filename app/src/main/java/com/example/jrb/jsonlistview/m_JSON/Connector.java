package com.example.jrb.jsonlistview.m_JSON;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Connector {

    private Connector() {
    }

    public static Object connect(String jsonURL)
    {
        try
        {
            URL url=new URL(jsonURL);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //CON PROPS
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);

            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        }
    }

    public static String getData(Object connection) throws IOException {
        HttpURLConnection con = (HttpURLConnection) connection;
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //GET INPUT FROM STREAM
            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer jsonData = new StringBuffer();

            //READ
            while ((line = br.readLine()) != null) {
                jsonData.append(line).append("\n");
            }

            //CLOSE RESOURCES
            br.close();
            is.close();

            //RETURN JSON
            return jsonData.toString();
        } else {
            return "Error " + con.getResponseMessage();
        }
    }
}
