package franklyn.unsplashclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import franklyn.unsplash.UnsplashPhoto;

/**
 * Created by Franklyn on 11/6/2015.
 */
public class FavoritesParcel implements Parcelable {
    ArrayList<UnsplashPhoto> data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(data.toArray());
    }

    public void add(UnsplashPhoto photo){
        data.add(photo);
    }
    public void remove(UnsplashPhoto photo){
        data.remove(photo);
    }

    public void remove(int i){
        data.remove(i);
    }

    public  ArrayList<UnsplashPhoto> getPhtotos()
    {
        return data;
    }

    public static final Parcelable.Creator<FavoritesParcel> CREATOR
            = new Parcelable.Creator<FavoritesParcel>() {
        public FavoritesParcel createFromParcel(Parcel in) {
            return new FavoritesParcel(in);
        }

        public FavoritesParcel[] newArray(int size) {
            return new FavoritesParcel[size];
        }
    };

    private FavoritesParcel(Parcel in) {

        data = new ArrayList<UnsplashPhoto>();

        Object[] photos = (UnsplashPhoto[])in.readArray(FavoritesParcel.class.getClassLoader());

        for (Object obj : photos){
            data.add((UnsplashPhoto)obj);
        }
    }


}
