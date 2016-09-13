package franklyn.unsplashclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;
import java.net.URLConnection;

import franklyn.unsplash.UnsplashPhoto;

/**
 * Created by Franklyn on 11/6/2015.
 */
public class UnsplashImageDownloader {
    ViewModelImageDownloaded viewModelImageDownloaded;

    public void setViewModekImageDownloaded(ViewModelImageDownloaded i){
        viewModelImageDownloaded = i;
    }
    public void download(UnsplashPhoto p){
        final  UnsplashPhoto photo = p;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    URL myUrl = new URL(photo.getUrls().getThumb());
                    URLConnection connection = myUrl.openConnection();
                    Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());

                    RowViewModel model = new RowViewModel();
                    model.user = photo.getUser().getName();
//                    model.likes = photo.get
                     model.image = bitmap;


                    if(viewModelImageDownloaded != null)
                        viewModelImageDownloaded.getViewModel(model);

                    Thread.sleep(2000);
                }catch(Exception ex){

                }

            }
        }).start();

    }
}
