package franklyn.unsplash;

import java.io.Serializable;

/**
 * Created by Franklyn on 10/29/2015.
 */
public class UnsplashPhoto implements Serializable {
    
    public String id;
    public int height;
    public int width;
    public String color;

    
    UnsplashUser user;
    UnsplashURL urls;
    UnsplashLink links;



    public String getId(){
        return id;
    }
    public int getHeight(){
    return height;
    }
    public int getWidth(){
    return width;
    }
    public String Color(){
        return color;
    }


    public void setId(String i){
        id = i;
    }
    public void setHeight(int h){
        height= h;
    }
    public void setWidth(int w){
        width = w;
    }
    public void setColor(String c){
        color = c;
    }


    public UnsplashUser getUser(){
        return user;
    }

    public UnsplashURL getUrls(){
        return  urls;
    }
    public UnsplashLink getLinks(){
        return links;
    }


    public void setUser(UnsplashUser u){
           user = u;
    }

    public void setUrls(UnsplashURL u){
        urls = u;
    }
    public void setLinks(UnsplashLink l){
        links = l;
    }
    
}
