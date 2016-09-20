package franklyn.unsplashclient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

import franklyn.unsplash.ImageDownloaded;
import franklyn.unsplash.UnsplashPhoto;

/**
 * Created by Franklyn on 11/2/2015.
 */



public class PhotosFeedAdapter extends ArrayAdapter<RowViewModel> {

    Activity activity;

    ArrayList<ImageDownloaded> waitQueue = new ArrayList<ImageDownloaded>();


    //    ArrayList<UnsplashPhoto> UnsplashPhotos;
    public PhotosFeedAdapter(Activity activity1, ArrayList<RowViewModel> photos) {

        super(activity1, R.layout.feed_row, photos);
        activity = activity1;

    }


    View row;
    RowViewHolder holder;

    public View getView(final int position, View convertView, ViewGroup viewGroup) {
//        ImageView image;
        TextView user;
        TextView photo_name;

        row = convertView;


        if (row == null) {
            row = activity.getLayoutInflater().inflate(R.layout.feed_row, viewGroup, false);
            ImageView imageView = (ImageView) row.findViewById(R.id.favIcon);
            imageView.setTag(getItem(position).tag);
        }

        holder = (RowViewHolder) row.getTag();
        //downloadImages();
        if (holder == null) {
            holder = new RowViewHolder(row);
            row.setTag(holder);
        }

        holder.image.setImageBitmap(getItem(position).image);
        holder.user.setText("Photo by: " + getItem(position).user);
        holder.favoriteImage.setImageResource(getItem(position).isInFavorite ? R.drawable.remove_fav : R.drawable.add_fav);
        holder.favoriteImage.setTag(getItem(position).tag);


        return row;
    }

}
