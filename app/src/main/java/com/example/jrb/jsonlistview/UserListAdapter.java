package com.example.jrb.jsonlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by krishna on April 15, 2018
 */
public class UserListAdapter extends BaseAdapter {
    private final WeakReference<Context> contextRef;
    private final List<String> userList;

    public UserListAdapter(Context context, List<String> userList) {
        this.contextRef = new WeakReference<>(context);
        this.userList = userList;
    }

    public void addAll(List<String> userList) {
        this.userList.addAll(userList);
    }

    @Override
    public int getCount() {
        return (userList == null) ? 0 : userList.size();
    }

    @Override
    public String getItem(int position) {
        return userList == null ? "" : userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        UserVH userVH;
        if (convertView == null) {
            convertView = LayoutInflater.from(contextRef.get()).inflate(R.layout.item_user, viewGroup, false);
            userVH = new UserVH(convertView);
            convertView.setTag(userVH);
        } else {
            userVH = (UserVH) convertView.getTag();
        }
        userVH.tvUser.setText(getItem(position));
        return convertView;
    }

    private static class UserVH {
        private TextView tvUser;

        UserVH(View item) {
            tvUser = item.findViewById(R.id.tv_user);
        }
    }
}
