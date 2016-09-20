package franklyn.unsplash;
/**
 * Created by Franklyn on 10/29/2015.
 */

import android.text.format.DateUtils;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public  class UnSplashJsonParser {



    public static UnsplashPhoto toUnsplashPhoto(JSONObject json){

    UnsplashPhoto photo =  new UnsplashPhoto();

        try {
            photo.setId(json.getString("id"));
            photo.setColor(json.getString("color"));
            photo.setHeight(json.getInt("height"));
            photo.setWidth(json.getInt("width"));


            photo.setUser(toUnsplashUser(json.getJSONObject("user")));
            photo.setUrls(toUnsplashUrl(json.getJSONObject("urls")));
            photo.setLinks(toUnsplashLink(json.getJSONObject("links")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return photo;
    }


    public static ArrayList<UnsplashPhoto> toUnsplashPhotoArray(String json){

        ArrayList<UnsplashPhoto> photos = new ArrayList<UnsplashPhoto>();
        try {
            JSONArray jsonArray= new JSONArray(json);

            JSONObject o;

            for( int i = 0; i < jsonArray.length(); i++){


                    o = jsonArray.getJSONObject(i);
                    photos.add(toUnsplashPhoto(o));



            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return photos;
    }





    public static ArrayList<UnsplashPhoto> toUnsplashPhotoArrayAsync(String json){

        ArrayList<UnsplashPhoto> photos = new ArrayList<UnsplashPhoto>();
        try {
            JSONArray jsonArray= new JSONArray(json);

            JSONObject o;

            for( int i = 0; i < jsonArray.length(); i++){

                //new Thread();
                o = jsonArray.getJSONObject(i);
                photos.add(toUnsplashPhoto(o));


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return photos;
    }


    public static ArrayList<UnsplashBatch> toUnsplashBatchArray(String json){

        ArrayList<UnsplashBatch> photos = new ArrayList<UnsplashBatch>();
        try {
            JSONArray jsonArray= new JSONArray(json);

            JSONObject o;
            for( int i = 0; i < jsonArray.length(); i++){


                o = jsonArray.getJSONObject(i);
                photos.add(toUnsplashBatch(o));


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return photos;
    }



    public static UnsplashUser  toUnsplashUser(JSONObject json){

        UnsplashUser user =  new UnsplashUser();

        try {


            user.setId(json.getString("id"));
            user.setUsername(json.getString("username"));
            user.setName(json.getString("name"));


            user.setLinks(toUnsplashLink(json.getJSONObject("links")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return user;
    }

    public static UnsplashLink  toUnsplashLink(JSONObject json){

        UnsplashLink user =  new UnsplashLink();

        try {



            user.setSelf(json.getString("self"));
            user.setHtml(json.getString("html"));
            user.setDownload(json.getString("download"));
            user.setPhotos(json.getString("photos"));



        } catch (JSONException e) {
           // e.printStackTrace();
        }


        return user;
    }

    public static UnsplashURL  toUnsplashUrl(JSONObject json){

        UnsplashURL urls =  new UnsplashURL();

        try {

            String regular;
            String small;
            String thumb;

            urls.setRegular(json.getString("regular"));
            urls.setSmall(json.getString("small"));
            urls.setThumb(json.getString("thumb"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return urls;
    }


    public static UnsplashBatch  toUnsplashBatch(JSONObject json){

        UnsplashBatch batch =  new UnsplashBatch();

        try {



            batch.setId(json.getInt("id"));
            batch.setDownloads(json.getInt("downloads"));
            batch.setLinks(toUnsplashLink(json.getJSONObject("links")));
            batch.setPublishedAt(Date.valueOf(json.getString("publishedAt")));
            batch.setCurator(toUnsplashCurator(json.getJSONObject("curtator")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return batch;
    }

    public static UnsplashCurator  toUnsplashCurator(JSONObject json){

        UnsplashCurator curator =  new UnsplashCurator();

        try {

            curator.setBio(json.getString("bio"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return curator;
    }
}
