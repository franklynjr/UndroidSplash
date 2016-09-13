package franklyn.unsplashclient;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import franklyn.unsplash.UnsplashPhoto;

public class FavoritesActivity extends ListFragment {

    ListView listView;

    ArrayList<UnsplashPhoto> unsplashPhotos = new ArrayList<UnsplashPhoto>();
    ArrayList<RowViewModel> favoritePhotos = new ArrayList<RowViewModel>();
    PhotosFeedAdapter favoritesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);
        listView = (ListView)view.findViewById(android.R.id.list);


        if(favoritesAdapter == null)
        {
            favoritesAdapter = new PhotosFeedAdapter(FavoritesActivity.this.getActivity(), favoritePhotos);
            this.setListAdapter(favoritesAdapter);
            loadFavorites();
        }

        return view;
    }



    public void loadFavorites(){
        FavoritesSerializer serializer = new FavoritesSerializer();
        unsplashPhotos = (ArrayList<UnsplashPhoto>)serializer.DeSerializeFile(this.getContext());
        if(unsplashPhotos != null) {
            for (UnsplashPhoto p : unsplashPhotos) {

                final UnsplashPhoto photo = p;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        UnsplashImageDownloader downloader = new UnsplashImageDownloader();

                        downloader.setViewModekImageDownloaded(new ViewModelImageDownloaded() {

                            @Override
                            public void getViewModel(RowViewModel vm) {

                                final RowViewModel v = vm;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        v.tag = photo;
                                        v.isInFavorite = true;
                                        favoritesAdapter.add(v);
                                    }
                                });
                                try{

                                    Thread.sleep(500);
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        });

                        downloader.download(photo);

                    }
                }).start();

            }
        }
    }

    public void addFavorites(UnsplashPhoto p){
        if(favoritesAdapter == null)
        {
            favoritePhotos = new ArrayList<RowViewModel>();
            unsplashPhotos = new ArrayList<UnsplashPhoto>();
            favoritesAdapter = new PhotosFeedAdapter(FavoritesActivity.this.getActivity(), favoritePhotos);
        }

        unsplashPhotos.add(p);

        FavoritesSerializer serializer = new FavoritesSerializer();

         UnsplashImageDownloader downloader = new UnsplashImageDownloader();
            final UnsplashPhoto photo = p;
            downloader.setViewModekImageDownloaded(new ViewModelImageDownloaded() {

                @Override
                public void getViewModel(RowViewModel vm) {
                    final RowViewModel v = vm;
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            v.tag = photo;
                            v.isInFavorite = true;
                            favoritesAdapter.add(v);
                        }
                    });
                }
            });
        downloader.download(p);
        serializer.SerializeFile(this.getContext(), unsplashPhotos);
    }



}
