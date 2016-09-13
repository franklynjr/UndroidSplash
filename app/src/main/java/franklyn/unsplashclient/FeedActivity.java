package franklyn.unsplashclient;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import franklyn.unsplash.*;

public class FeedActivity extends ListFragment {


    int page = 1;
    PhotosFeedAdapter feedAdapter;
    ListView listView;
    ArrayList<RowViewModel> unsplashPhotos = new ArrayList<RowViewModel>();
    String app_id = "73bae0808f6544513c4e0ed5f8861465efecb51f9af32bd4e637f5632c6d0bc0";
    ProgressBar downloadProgress;
    LinearLayout topBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feed, null, false);


        topBar = (LinearLayout) view.findViewById(R.id.topBar);
        downloadProgress = (ProgressBar) view.findViewById(R.id.progress);

        if (feedAdapter == null) {
            feedAdapter = new PhotosFeedAdapter(FeedActivity.this.getActivity(), unsplashPhotos);
            listView = (ListView) view.findViewById(android.R.id.list);

            setListAdapter(feedAdapter);
        } else {
            updateFavoriteIcons();
            hideProgress();
        }

//
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                  //only loads when there is nothing in the list
                    if (feedAdapter.getCount() == 0) {
//                        loadPhotos();
                        loadFeaturedPhotos();
                    }
                } catch (Exception ex) {

                }

            }
        }).start();
//


        return view;
    }

    public void updateFavoriteIcons() {

        favoritesLoaded = false;
        for (int i = 0; i < feedAdapter.getCount(); i++) {

            final int j = i;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    RowViewModel item = feedAdapter.getItem(j);
                    item.isInFavorite = isFavorite(item.tag);
                }
            }).start();
            try{

            }catch(Exception ex){}
        }
    }

    int photosRecieved = 10; //Default 10
    int batchesRecied;  //Default 10

    public void loadPhotos() {

        UnsplashClient client = new UnsplashClient(app_id);

        client.getPhotos(page);
        client.setJsonReceivedListener(new UnsplashJsonReceived() {
            @Override
            public void unsplashResult(String data, int type) {
                switch (type) {
                    case UnsplashClient.PHOTOS_RESULT: {


                        UnSplashJsonParser parser = new UnSplashJsonParser();
                        final ArrayList<UnsplashPhoto> usPhtots = parser.toUnsplashPhotoArray(data);
                        photosRecieved = usPhtots.size();
                        downloadProgress.setMax(photosRecieved);
                        for (UnsplashPhoto p : usPhtots) {
                            UnsplashImageDownloader id = new UnsplashImageDownloader();
                            final UnsplashPhoto tag = p;
                            id.setViewModekImageDownloaded(new ViewModelImageDownloaded() {
                                @Override
                                public void getViewModel(RowViewModel vm) {
                                    final RowViewModel v = vm;
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            v.tag = tag;
                                            v.isInFavorite = isFavorite(tag);
                                            feedAdapter.add(v);
                                            downloadProgress.setProgress(downloadProgress.getProgress() + 1);
                                        }
                                    });

                                }
                            });
                            try {
                                // allow the UI to update
                                Thread.sleep(500);
                            } catch (Exception ex) {
                            }
                            id.download(p);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // downloads completed
                                hideProgress();
                            }
                        });
                    }

                    break;
                }
            }
        });
    }


    public void loadFeaturedPhotos() {

        final UnsplashClient client = new UnsplashClient(app_id);

        client.getFeaturedPhotos(page);
        client.setJsonReceivedListener(new UnsplashJsonReceived() {
            @Override
            public void unsplashResult(String data, int type) {
                final UnSplashJsonParser parser = new UnSplashJsonParser();
                switch (type) {
                    case UnsplashClient.CURATED_BATCHES_RESULT: {

                        final String _data = data;

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ArrayList<UnsplashBatch> batches = parser.toUnsplashBatchArray(_data);
                                        batchesRecied = batches.size();
                                        for (UnsplashBatch batch :batches) {
                                            client.getBatchPhotos(batch.getId());
                                        }
                                    }
                                }).start();

                            }
                        });
                    }
                    break;
                    case UnsplashClient.PHOTOS_RESULT: {

                        final ArrayList<UnsplashPhoto> usPhtots = parser.toUnsplashPhotoArray(data);

                        for (UnsplashPhoto p : usPhtots) {
                            UnsplashImageDownloader id = new UnsplashImageDownloader();
                            final UnsplashPhoto tag = p;
                            id.setViewModekImageDownloaded(new ViewModelImageDownloaded() {
                                @Override
                                public void getViewModel(RowViewModel vm) {
                                    final RowViewModel v = vm;
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            v.tag = tag;
                                            v.isInFavorite = isFavorite(tag);
                                            feedAdapter.add(v);
                                            int pS = unsplashPhotos.size();
                                            int a = feedAdapter.getCount();
                                        }
                                    });
                                    try {
                                        Thread.sleep(500);
                                    } catch (Exception ex) {
                                    }
                                }
                            });
                            id.download(p);
                        }


                    }
                    break;
                }
            }
        });
    }


    public void loadMore() {
        page++;
        loadPhotos();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {

        state.putSerializable("favorites", unsplashPhotos);

    }


    boolean favoritesLoaded = false;
    ArrayList<UnsplashPhoto> favoritePhotos;

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

    private void hideProgress()
    {
        topBar.setVisibility(View.INVISIBLE);
        downloadProgress.setVisibility(View.INVISIBLE);
    }

}
