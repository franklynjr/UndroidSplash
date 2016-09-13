package franklyn.unsplashclient;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import franklyn.unsplash.UnsplashPhoto;

public class SearchActivity extends ListFragment {

    TextView searchTerm;
    ProgressBar searchProgressBar;
    ArrayList<UnsplashPhoto> favoritePhotos = new ArrayList<UnsplashPhoto>();
    ArrayList<RowViewModel> photos = new ArrayList<RowViewModel>();
    ArrayList<UnsplashPhoto> unsplashPhotos;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        if(searchAdapter == null){

            searchProgressBar = (ProgressBar)view.findViewById(R.id.progress);
            searchTerm = (TextView)view.findViewById(R.id.searchTerm);

            searchAdapter = new PhotosFeedAdapter(SearchActivity.this.getActivity(), photos);
            listView = (ListView) view.findViewById(android.R.id.list);
            setListAdapter(searchAdapter);

        }else{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateFavoriteIcons();
                }
            }).start();
        }



        return view;
    }

    boolean favoritesLoaded = false;
    PhotosFeedAdapter searchAdapter;

    public void updateFavoriteIcons() {

        favoritesLoaded = false;
        for (int i = 0; i < searchAdapter.getCount(); i++) {

            final int j = i;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    RowViewModel item = searchAdapter.getItem(j);
                    item.isInFavorite = isFavorite(item.tag);
                }
            }).start();
            try{
                Thread.sleep(500);
            }catch(Exception ex){}
        }
    }


    public boolean isFavorite(UnsplashPhoto photo) {
        //prevent repeated filesystem r/w
        if (!favoritesLoaded) {
            FavoritesSerializer serializer = new FavoritesSerializer();

            favoritePhotos = (ArrayList<UnsplashPhoto>) serializer.DeSerializeFile(this.getContext());
            favoritesLoaded = true;
        }

        if (favoritePhotos != null) {

            for (UnsplashPhoto p : favoritePhotos) {
                if (p.getId().equalsIgnoreCase(photo.getId()))
                    return true;
            }
        }


        return false;


    }
}
