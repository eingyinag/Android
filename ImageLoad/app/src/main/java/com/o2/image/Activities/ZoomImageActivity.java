package com.o2.image.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.o2.image.Adapter.MySwipeListAdapter;
import com.o2.image.R;
import com.o2.image.view.TouchImageView;

public class ZoomImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resize_image);

        // get intent data
        Intent myfullIntent = getIntent();
        int position = myfullIntent.getExtras().getInt("id");

        Log.e("Zoom_Position", "" + position);

        TouchImageView image = (TouchImageView) findViewById(R.id.id_touch_image_view);
        image.setImageBitmap(MySwipeListAdapter.imageList.get(position));
        image.setMaxZoom(4f);

    }

}
