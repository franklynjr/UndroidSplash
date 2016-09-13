package franklyn.unsplash;

import java.util.Date;

/**
 * Created by Franklyn on 11/5/2015.
 */
public class UnsplashBatch {


     int id;
     int downloads;
     Date publishedAt;

     UnsplashCurator curator;
     UnsplashLink links;




    public int getId (){
        return id;
    }
    public int getDownloads(){
        return downloads;
    }

    public Date getPublishedAt (){
        return publishedAt;
    }

    public UnsplashCurator getCurator (){
        return curator;
    }

    public UnsplashLink getLinks (){
        return links;
    }



    public void setId (int i){
        id = i;
    }
    public void setDownloads(int d){
        downloads = d;
    }

    public void setPublishedAt (Date p){
        publishedAt = p;
    }

    public void setCurator (UnsplashCurator c){
        curator = c;
    }
    public void setLinks (UnsplashLink l){
        links = l;
    }
    
}
