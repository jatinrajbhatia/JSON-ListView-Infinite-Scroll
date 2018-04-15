package com.example.jrb.jsonlistview.m_JSON;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.jrb.jsonlistview.MainActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;


public class JSONDownloader extends AsyncTask<Void, Void, UserData> {

    private WeakReference<Context> contextRef;
    private String jsonURL;
    private MainActivity.OnDataLoaded onDataLoaded;

    private ProgressDialog pd;

    public JSONDownloader(Context context, String jsonURL, MainActivity.OnDataLoaded onDataLoaded) {
        contextRef = new WeakReference<>(context);
        this.jsonURL = jsonURL;
        this.onDataLoaded = onDataLoaded;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(contextRef.get());
        pd.setTitle("Download JSON");
        pd.setMessage("Downloading...Please wait");
        pd.show();
    }

    @Override
    protected UserData doInBackground(Void... voids) {
        String response = download();
        return parse(response);
    }

    @Override
    protected void onPostExecute(UserData userData) {
        if (TextUtils.isEmpty(userData.getMessage())) {
            onDataLoaded.success(userData);
        } else {
            onDataLoaded.error(userData.getMessage());
        }
        pd.dismiss();
    }

    private String download() {
        Object connection = Connector.connect(jsonURL);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }

        try {
            return Connector.getData(connection);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }

    private UserData parse(String jsonData) {
        UserParser parser = new UserParser();
        return parser.parse(jsonData);
    }
}
