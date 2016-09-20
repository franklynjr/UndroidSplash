package franklyn.unsplash;

import java.io.Serializable;

/**
 * Created by Franklyn on 10/29/2015.
 */
public class UnsplashUser  implements Serializable {

    String id;
    String username;
    String name;
    UnsplashLink links;


    public String getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getName(){
        return name;
    }
    public UnsplashLink getLinks(){
        return links;
    }

    public void setId(String i)
    {
        id = i;
    }

    public void setUsername(String u)
    {
        username = u;
    }

    public void setName(String n)
    {
        name = n;
    }

    public void setLinks(UnsplashLink l)
    {
        links = l;
    }

}
