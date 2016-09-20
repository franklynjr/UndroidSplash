package franklyn.unsplashclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import franklyn.unsplash.UnSplashJsonParser;
import franklyn.unsplash.UnsplashClient;
import franklyn.unsplash.UnsplashJsonReceived;
import franklyn.unsplash.UnsplashPhoto;

public class MainActivity extends FragmentActivity {

    FragmentTabHost tabHost;

    String appId = "73bae0808f6544513c4e0ed5f8861465efecb51f9af32bd4e637f5632c6d0bc0";

    //widgets
    SearchActivity searchActivity;

    FavoritesActivity favoritesActivity;
  ArrayList<UnsplashPhoto> favoritesPhotos = new ArrayList<UnsplashPhoto>();

    int searchPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (FragmentTabHost)this.findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        //Custom tabs
        View featuredTab = createCustomTab("Featured", R.drawable.feat_icon);
        View searchTab = createCustomTab("Search", R.drawable.search_icon);
        View favTab = createCustomTab("Favorite", R.drawable.fav_icon);

        //Add tabs
        tabHost.addTab(tabHost.newTabSpec("MainFeed").setIndicator(featuredTab), FeedActivity.class, null);
        tabHost.addTab(tabHost.newTabSpec("SearchFeed").setIndicator(searchTab), SearchActivity.class, null);
        tabHost.addTab(tabHost.newTabSpec("FavoritesFeed").setIndicator(favTab), FavoritesActivity.class, null);
        tabHost.getTabWidget().setStripEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle state){
        state.putSerializable("favorites", favoritesPhotos);
    }


    public void search(View view)
    {

        if(searchActivity.searchAdapter == null){
            searchActivity.searchAdapter  = new PhotosFeedAdapter(searchActivity.getActivity(), searchActivity.photos);
            searchActivity.setListAdapter(searchActivity.searchAdapter);
        }

        searchActivity.searchProgressBar.setVisibility(View.VISIBLE);

        UnsplashClient client = new UnsplashClient(appId);

        client.setJsonReceivedListener(new UnsplashJsonReceived() {

            @Override
            public void unsplashResult(String data, int RESULT_TYPE) {
                final String _data = data;
                switch (RESULT_TYPE) {
                    case UnsplashClient.PHOTOS_RESULT:
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //make sure the result batch is cleared
                                searchActivity.searchAdapter.clear();
                            }
                        });
                        UnSplashJsonParser parser = new UnSplashJsonParser();
                        final ArrayList<UnsplashPhoto> usPhtots = parser.toUnsplashPhotoArray(data);
                        for(UnsplashPhoto p : usPhtots)
                        {
                            UnsplashImageDownloader id = new UnsplashImageDownloader();
                            final UnsplashPhoto tag = p;
                            id.setViewModekImageDownloaded(new ViewModelImageDownloaded() {
                                @Override
                                public void getViewModel(RowViewModel vm) {
                                    final RowViewModel v = vm;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            v.tag = tag;
                                            v.isInFavorite = isFavorite(tag);
                                            searchActivity.searchAdapter.add(v);
                                        }
                                    });

                                }
                            }); try{
                            // allow the UI to update
                            Thread.sleep(500);
                        }catch (Exception ex){}
                            id.download(p);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(searchActivity != null)
                                    searchActivity.searchProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                        break;
                }
            }
        });

        client.searchPhotos(searchActivity.searchTerm.getText().toString(), searchPage);

    }



    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if(fragment.getClass() == SearchActivity.class)
        {
            searchActivity = ((SearchActivity)fragment);

            // searchSearchFragment = (ListFragment)fragment;
        }
        else if(fragment.getClass() == FavoritesActivity.class)
        {
            favoritesActivity = ((FavoritesActivity)fragment);
            // searchSearchFragment = (ListFragment)fragment;
        }
    }


    public void addToFav(View view)
    {
        UnsplashPhoto photo = (UnsplashPhoto)view.getTag();

        //Toggle favorite icon
        if(isFavorite(photo))
        {
                ((ImageView)view).setImageResource(R.drawable.add_fav);
                remove(photo);
        }
        else
        {
            if(favoritesActivity == null)
            {
                FavoritesSerializer serializer = new FavoritesSerializer();

                ArrayList<UnsplashPhoto> photos = (ArrayList<UnsplashPhoto>) serializer.DeSerializeFile(this);
                if(photos != null)
                {
                    photos.add(photo);
                }
                else
                {
                    photos = new ArrayList<UnsplashPhoto>();
                    photos.add(photo);
                }

                ((ImageView)view).setImageResource(R.drawable.remove_fav);
                serializer.SerializeFile(this, photos);
            }else
            {
                favoritesActivity.addFavorites(photo);
                ((ImageView)view).setImageResource(R.drawable.remove_fav);
            }
        }

       // boolean b = favoritesPhotos.contains(photo);



    }



    public boolean isFavorite(UnsplashPhoto photo)
    {



        if(favoritesActivity == null)
        {
            FavoritesSerializer serializer = new FavoritesSerializer();

            ArrayList<UnsplashPhoto> photos = (ArrayList<UnsplashPhoto>) serializer.DeSerializeFile(this);
            if(photos != null) {
                for (UnsplashPhoto p : photos) {
                    if (p.getId().equalsIgnoreCase(photo.getId()))
                        return true;
                }
            }

                return false;
        }else
        {
            if(favoritesActivity.unsplashPhotos != null){

                for(UnsplashPhoto p : favoritesActivity.unsplashPhotos)
                {
                    if( p.getId().equalsIgnoreCase(photo.getId()))
                        return true;
                }
            }


            return false;


        }

    }


    public void remove(UnsplashPhoto photo)
    {

        FavoritesSerializer serializer = new FavoritesSerializer();
        if(favoritesActivity == null)
        {
            int indx = indexof(photo);
            if(indx != -1)
            {


                ArrayList<UnsplashPhoto> photos = (ArrayList<UnsplashPhoto>) serializer.DeSerializeFile(this);
                photos.remove(indx);

                serializer.SerializeFile(this, photos);
            }

        }else
        {

            int i = indexof(photo);
            if(i != -1) {
                RowViewModel model = favoritesActivity.favoritesAdapter.getItem(i);
                favoritesActivity.unsplashPhotos.remove(i);
                favoritesActivity.favoritesAdapter.remove(model);


                serializer.SerializeFile(this, favoritesActivity.unsplashPhotos);
            }

        }
    }

    public int indexof(UnsplashPhoto photo)
    {
        if(favoritesActivity == null) {

            FavoritesSerializer serializer = new FavoritesSerializer();

            ArrayList<UnsplashPhoto> photos = (ArrayList<UnsplashPhoto>) serializer.DeSerializeFile(this);
            if(photos != null) {

                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getId().equalsIgnoreCase(photo.getId()))
                        return i;
                }
            }


            return -1;


        }else
        {
            PhotosFeedAdapter adapter = favoritesActivity.favoritesAdapter;

            for (int i = 0; i < favoritesActivity.favoritesAdapter.getCount(); i++) {
                UnsplashPhoto p = favoritesActivity.favoritesAdapter.getItem(i).tag;

                if (p.getId().equalsIgnoreCase(photo.getId()))
                    return i;
            }

            return -1;
        }
    }


    public View createCustomTab(String text, int id)
    {

        View tab = getLayoutInflater().inflate(R.layout.feed_tab, null);
        ((ImageView)tab.findViewById(R.id.icon)).setImageResource(id);
        ((TextView)tab.findViewById(R.id.text)).setText(text);

        return tab;
    }
}
