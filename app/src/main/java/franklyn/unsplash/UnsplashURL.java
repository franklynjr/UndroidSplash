package franklyn.unsplash;

import java.io.Serializable;

/**
 * Created by Franklyn on 10/29/2015.
 */
public class UnsplashURL  implements Serializable {

    String regular;
    String small;
    String thumb;

    public String getRegular() { return regular; }
    public String getSmall() { return small; }
    public String getThumb() { return thumb; }

    public void setRegular(String v) { regular= v; }
    public void setSmall(String v) { small= v; }
    public void setThumb(String v) { thumb= v; }

}
