package franklyn.unsplash;

import android.graphics.Bitmap;

/**
 * Created by Franklyn on 11/5/2015.
 */
public interface ImageDownloaded {
     void downloadCompleted(Bitmap bitmap, int index);
}
