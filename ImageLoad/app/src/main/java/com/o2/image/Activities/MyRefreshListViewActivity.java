package com.o2.image.Activities;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import com.o2.image.Adapter.MySwipeListAdapter;
import com.o2.image.R;
import com.o2.image.model.SwipeModel;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

public class MyRefreshListViewActivity extends AppCompatActivity {
    private SwipeRefreshLayout myRefreshLayout;
    private ListView myListView;
    private MySwipeListAdapter mySwipeListAdapter;
    private int offset = 0;
    private final int MAX = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_refresh_list_view);

        myRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_layout);
        myListView = (ListView) findViewById(R.id.id_swipe_list_view);

        if(!isInternetOn()){
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        } else {
            mySwipeListAdapter = new MySwipeListAdapter(this, SwipeModel.getImages(offset));

            myRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new MyBackgroundTask().execute();
                }
            });
            myListView.setAdapter(mySwipeListAdapter);

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Sending image id to ZoomImageActivity
                    Intent myImageView = new Intent(getApplicationContext(), ZoomImageActivity.class);

                    // Passing array index
                    myImageView.putExtra("id", position);
                    startActivity(myImageView);
                }
            });
        }
    }

    // Thread
    private class MyBackgroundTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            offset += MAX;
            return SwipeModel.getImages(offset);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
//            mySwipeListAdapter = new MySwipeListAdapter(getApplicationContext(), SwipeModel.datas);
            mySwipeListAdapter = new MySwipeListAdapter(getApplicationContext(), strings);
            myListView.setAdapter(mySwipeListAdapter);
            myRefreshLayout.setRefreshing(false);
        }
    }

    private boolean isInternetOn() {

        ConnectivityManager con_manager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);


            if (con_manager.getActiveNetworkInfo() == null) {
                Toast.makeText(getApplicationContext(), R.string.alert_internet_access, Toast.LENGTH_LONG).show();
                return false;
            }

        return true;
    }
}