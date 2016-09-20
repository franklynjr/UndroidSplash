package franklyn.unsplash;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Franklyn on 10/29/2015.
 *
 * This class makes the calls to the unsplash api
 */
public class UnsplashClient extends AsyncTask<String, String, String>{

    UnsplashJsonReceived JsonReceivedTrigger;

    String appId = "";


    public UnsplashClient(String id) {
        appId = id;


    }

    public final static int CURATED_BATCHES_RESULT = 1;
    public final static int MESSAGE_RESULT = 4;
    public final static int LOAD_PROGRESS = 4;
    public final static int PHOTOS_RESULT = 2;
    public final static int SINGLE_PHOTO_RESULT = 3;

    int SEARCH_PER_PAGE = 50;
    /**
     *
     * @param page
     */
    public void getPhotos(int page) {
        //String result = doInBackground(new String[]{"https://api.unsplash.com/photos/?page=" + String.valueOf(page) + "&client_id=" + appId});


        final int p = page;
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {


                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet("https://api.unsplash.com/photos/?page=" + String.valueOf(p) + "&client_id=" + appId);

                HttpResponse response;
                HttpContext myContext = new BasicHttpContext();
                String data = "";


                try {
                    response = client.execute(getRequest, myContext);
                    HttpEntity e = response.getEntity();
                    InputStream streamReader = e.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
                    String line = reader.readLine();
                    do {
                        if (line != null) {
                            data += line;
                            line = reader.readLine();
                        }


                    } while (line != null);


                } catch (IOException e1) {
                    JsonReceivedTrigger.unsplashResult("Error loading feed", MESSAGE_RESULT);
                    e1.printStackTrace();
                }

                System.out.println(data);


                UnSplashJsonParser jsonParser = new UnSplashJsonParser();
                jsonParser.toUnsplashPhotoArray(data);

                if(JsonReceivedTrigger != null)
                    JsonReceivedTrigger.unsplashResult(data, PHOTOS_RESULT);


            }

        });

        t.start();


    }


    /**
     *
     * @param page
     */
    public void searchPhotos(String term, int page) {
        //String result = doInBackground(new String[]{"https://api.unsplash.com/photos/?page=" + String.valueOf(page) + "&client_id=" + appId});


        final int p = page;
        final String trm = term;
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {


                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet("https://api.unsplash.com/photos/search?query="+trm+"&page="+p+"&per_page=50&client_id=" + appId);

                HttpResponse response;
                HttpContext myContext = new BasicHttpContext();
                String data = "";


                try {
                    response = client.execute(getRequest, myContext);
                    HttpEntity e = response.getEntity();
                    InputStream streamReader = e.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
                    String line = reader.readLine();
                    do {
                        if (line != null) {
                            data += line;
                            line = reader.readLine();
                        }


                    } while (line != null);


                } catch (IOException e1) {
                    e1.printStackTrace();
                }



                UnSplashJsonParser jsonParser = new UnSplashJsonParser();
                jsonParser.toUnsplashPhotoArray(data);

                if(JsonReceivedTrigger != null)
                    JsonReceivedTrigger.unsplashResult(data, PHOTOS_RESULT);

            }

        });

        t.start();


    }


    public void getFeaturedPhotos(int page) {
        //String result = doInBackground(new String[]{"https://api.unsplash.com/photos/?page=" + String.valueOf(page) + "&client_id=" + appId});

        final int p = page;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet("https://api.unsplash.com/curated_batches?page="+String.valueOf(p)+"&client_id=" + appId);

                HttpResponse response;
                HttpContext myContext = new BasicHttpContext();
                String data = "";


                try {
                    response = client.execute(getRequest, myContext);
                    HttpEntity e = response.getEntity();
                    InputStream streamReader = e.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
                    String line = reader.readLine();
                    do {
                        if (line != null) {
                            data += line;
                            line = reader.readLine();
                        }


                    } while (line != null);


                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println(data);


                UnSplashJsonParser jsonParser = new UnSplashJsonParser();
                jsonParser.toUnsplashPhotoArray(data);

                if(JsonReceivedTrigger != null)
                    JsonReceivedTrigger.unsplashResult(data, CURATED_BATCHES_RESULT);


            }

        });

        t.start();


    }




    public void getBatchPhotos(int i) {
        //String result = doInBackground(new String[]{"https://api.unsplash.com/photos/?page=" + String.valueOf(page) + "&client_id=" + appId});

        final int id = i;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet("https://api.unsplash.com/curated_batches/"+String.valueOf(id)+"/photos?client_id=" + appId);

                HttpResponse response;
                HttpContext myContext = new BasicHttpContext();
                String data = "";


                try {
                    response = client.execute(getRequest, myContext);
                    HttpEntity e = response.getEntity();
                    InputStream streamReader = e.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
                    String line = reader.readLine();
                    do {
                        if (line != null) {
                            data += line;
                            line = reader.readLine();
                        }


                    } while (line != null);


                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println(data);


                UnSplashJsonParser jsonParser = new UnSplashJsonParser();
                jsonParser.toUnsplashPhotoArray(data);

                if(JsonReceivedTrigger != null)
                    JsonReceivedTrigger.unsplashResult(data, PHOTOS_RESULT);


            }

        });

        t.start();


    }



    @Override
    protected String doInBackground(String... params) {

        final String p = params[0];

//
//            try {
//
//
//                HttpClient client = new DefaultHttpClient();
//                HttpGet getRequest = new HttpGet(p);
//
//                HttpResponse response;
//                HttpContext myContext = new BasicHttpContext();
//                String data = "";
//
//
//                    response = client.execute(getRequest, myContext);
//                    HttpEntity e = response.getEntity();
//                    InputStream streamReader =  e.getContent();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
//                    String line = reader.readLine();
//                    do{
//                        if(line != null){
//                            data += line;
//                            line = reader.readLine();
//                        }
//
//
//                    }while(line != null);
//
//                    System.out.println(data);
//
//
//
//                //return data;
//
//    } catch (IOException e) {
//        e.printStackTrace();
//        System.out.println(e.getMessage());
//    }


        return "";


    }


    public void setJsonReceivedListener(UnsplashJsonReceived l){
        JsonReceivedTrigger = l;
    }
}
