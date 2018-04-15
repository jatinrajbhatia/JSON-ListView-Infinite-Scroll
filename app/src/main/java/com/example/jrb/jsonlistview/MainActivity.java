package com.example.jrb.jsonlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jrb.jsonlistview.m_JSON.JSONDownloader;
import com.example.jrb.jsonlistview.m_JSON.UserData;

public class MainActivity extends AppCompatActivity {

    String jsonURL="http://jsonplaceholder.typicode.com/users";
    ListView lv;
    UserListAdapter adapter;
    UserData userData;

    private OnDataLoaded onDataLoaded = new OnDataLoaded() {
        @Override
        public void success(final UserData userData) {
            if (MainActivity.this.userData == null) {
                MainActivity.this.userData = userData;
            } else {
                MainActivity.this.userData.getUsers().addAll(userData.getUsers());
            }

            if (adapter == null) {
                adapter = new UserListAdapter(MainActivity.this, userData.getUsers());
                lv.setAdapter(adapter);
            } else {
                adapter.addAll(userData.getUsers());
                adapter.notifyDataSetChanged();
            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, userData.getUsers().get(i), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void error(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv= (ListView) findViewById(R.id.lv);
        new JSONDownloader(MainActivity.this, jsonURL, onDataLoaded).execute();

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView lw, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    new JSONDownloader(MainActivity.this, jsonURL, onDataLoaded).execute();
                }
            }
        });
    }

    public interface OnDataLoaded {
        void success(UserData userData);

        void error(String message);
    }
}
