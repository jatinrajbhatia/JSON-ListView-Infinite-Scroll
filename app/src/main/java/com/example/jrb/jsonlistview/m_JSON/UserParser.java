package com.example.jrb.jsonlistview.m_JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on April 15, 2018
 */
public class UserParser {
    public UserData parse(String jsonData) {
        UserData data = new UserData();
        List<String> users = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");
                users.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            data.setMessage(e.getMessage());
        }
        data.setUsers(users);
        return data;
    }
}
