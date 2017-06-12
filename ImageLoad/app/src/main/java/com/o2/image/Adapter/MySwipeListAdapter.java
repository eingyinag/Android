package com.o2.image.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.o2.image.R;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by O2 on 6/8/2017.
 */
public class MySwipeListAdapter extends BaseAdapter {

    private Context myContext;
    public List<String> myDatas;
    public static List<Bitmap> imageList = new ArrayList<>();

    ImageView imageView;
    public MySwipeListAdapter(Context context,List<String> datas) {
      this.myDatas = datas;
      this.myContext = context;
    }

    @Override
    public int getCount() {
        Log.e("<<Check Size>>",""+this.myDatas.size());
        return this.myDatas.size();

    }

    @Override
    public Object getItem(int position) {
        return this.myDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_swipe, null);
        }
        imageView = (ImageView) convertView.findViewById(R.id.id_item_image);
        Log.e("<<SystemPost>",""+position);
        Log.e("<<SystemLoad>> ", myDatas.get(position));
        String imageUrl=myDatas.get(position);
        GetImage g=new GetImage(imageView);
        g.execute(imageUrl);
        return convertView;
    }

    class GetImage extends AsyncTask<String,Void,Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;
        public GetImage(ImageView v){
            imageViewWeakReference=new WeakReference<ImageView>(v);
        }
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
            if(imageViewWeakReference!=null){
                ImageView v=imageViewWeakReference.get();
                if(v!=null){
                    if(image!=null){
                        v.setImageBitmap(image);
                        imageList.add(image);
                    }
                }
            }
        }
    }
}
