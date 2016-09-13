package franklyn.unsplash;

import java.io.Serializable;

/**
 * Created by Franklyn on 10/29/2015.
 */
public class UnsplashLink  implements Serializable {

    String self;
    String html;
    String download;
    String photos;

    public String getSelf(){ return self; }
    public String getHtml(){ return html; }
    public String getDownload(){ return download; }
    public String getPhotos(){ return photos; }

    public void setSelf(String v) { self= v; }
    public void setHtml(String v) { html= v; }
    public void setDownload(String v) { download = v; }
    public void setPhotos(String v) { photos = v; }
    
}
