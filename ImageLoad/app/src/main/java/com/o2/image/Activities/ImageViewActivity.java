package com.o2.image.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.o2.image.R;

import java.net.URL;

public class ImageViewActivity extends AppCompatActivity {
    private String IMAGE_JSON="http://bleepsclub.com/images/testimonials/t_1496807006_b03b886daab3bae91c283e3d1f8a52f5.jpg";
    ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myImage=(ImageView)findViewById(R.id.id_imageView);
        GetImage gi = new GetImage();
        gi.execute(IMAGE_JSON);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
        class GetImage extends AsyncTask<String,Void,Bitmap> {
           @Override
            protected Bitmap doInBackground(String... params) {
               Log.e("<<System>>","doInBackground"+params[0]);
                URL url = null;
                Bitmap image = null;
                String urlToImage = params[0];

                try {
                    url = new URL(urlToImage);
                    Log.e("<<System>>","doInBackground"+url.toString());
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
               return image;
            }

            @Override
            protected void onPostExecute(Bitmap image) {
                super.onPostExecute(image);
                Log.e("<<System>>", "doPostExecute");
                myImage.setImageBitmap(image);
            }
        }
}
